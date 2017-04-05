package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Concepto;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ConceptoBean implements ConceptoLocal{
    
    //public Session session;
    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;
    
    public ConceptoBean(){
        sc=new SessionConnection();
        //session = sc.useSession();
        //session = SessionConnection.getConnection().useSession();
        //tx= session.beginTransaction();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Concepto concepto) throws ServiceException {
        correcto=false;
        try{            
            //session.save(concepto);
            sc.useSession().save(concepto);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("Concepto");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Concepto concepto) throws ServiceException {
        try{
            concepto.setActivo(false);
            //session.update(concepto);
            sc.useSession().update(concepto);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public boolean modificar(Concepto concepto) throws ServiceException {
        try{            
            //session.update(concepto);
            sc.useSession().update(concepto);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public List<Concepto> traerTodos() throws ServiceException {
        try{
            //Query query= session.createQuery("from Concepto");         
            Query query= sc.useSession().createQuery("from Concepto");
            List<Concepto> conceptos=query.list();
            //session.close();        
            sc.closeSession();
            return conceptos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Concepto traerConceptoXId(int Id) throws ServiceException {
        //Query query= session.createQuery("from Concepto concepto where concepto.IdConcepto=:id");            
        Query query= sc.useSession().createQuery("from Concepto concepto where concepto.IdConcepto=:id");
        query.setParameter("id", Id);        
        Concepto concepto=(Concepto) query.uniqueResult();
        //session.close();        
        sc.closeSession();
        return concepto;
    }
    
}
