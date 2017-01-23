package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Concepto;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ConceptoBean implements ConceptoLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public ConceptoBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Concepto concepto) throws ServiceException {
        correcto=false;
        try{            
            session.save(concepto);
            tx.commit();
            session.close();
            correcto=true;
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
            session.update(concepto);
            tx.commit();
            session.close();
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
            session.update(concepto);
            tx.commit();
            session.close();
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
            Query query= session.createQuery("from Usuario");         
            List<Concepto> conceptos=query.list();
            session.close();        
            return conceptos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Concepto traerConceptoXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Concepto concepto=(Concepto) query.uniqueResult();
        session.close();        
        return concepto;
    }
    
}
