package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Historialtrabajo;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class HistorialTrabajoBean implements HistorialTrabajoLocal{
    
    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public HistorialTrabajoBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Historialtrabajo historialTrabajo) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(historialTrabajo);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("HistorialTrabajo");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;   
    }    

    @Override
    public boolean modificar(Historialtrabajo historialTrabajo) throws ServiceException {
        try{            
            sc.useSession().update(historialTrabajo);
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
    public List<Historialtrabajo> traerTodos() throws ServiceException {
        try{
            Query query= sc.useSession().createQuery("from Historialtrabajo");         
            List<Historialtrabajo> historialTrabajos=query.list();
            //session.close();        
            sc.closeSession();
            return historialTrabajos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Historialtrabajo traerHistorialtrabajoXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Historialtrabajo historialTrabajo where historialTrabajo.IdHistorialtrabajo=:id");            
        query.setParameter("id", Id);        
        Historialtrabajo historialTrabajo=(Historialtrabajo) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return historialTrabajo;
    }
    
}
