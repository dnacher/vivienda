package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Otrosgastos;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class OtrosGastosBean implements OtrosGastosLocal{
    
    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public OtrosGastosBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Otrosgastos otrosGastos) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(otrosGastos);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("OtrosGastos");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto; 
    }

    @Override
    public boolean eliminar(Otrosgastos otrosGastos) throws ServiceException {
        try{
            otrosGastos.setActivo(false);
            sc.useSession().update(otrosGastos);
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
    public boolean modificar(Otrosgastos otrosGastos) throws ServiceException {
        try{            
            sc.useSession().update(otrosGastos);
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
    public List<Otrosgastos> traerTodos() throws ServiceException {
        try{
            Query query= sc.useSession().createQuery("from Otrosgastos");         
            List<Otrosgastos> otrosGastos=query.list();
            //session.close();        
            sc.closeSession();
            return otrosGastos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Otrosgastos traerOtrosgastosXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Otrosgastos otrosGastos where otrosGastos.IdOtrosgastos=:id");            
        query.setParameter("id", Id);        
        Otrosgastos otrosGastos=(Otrosgastos) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return otrosGastos;
    }
    
}
