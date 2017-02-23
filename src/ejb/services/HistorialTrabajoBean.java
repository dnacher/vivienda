package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Historialtrabajo;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class HistorialTrabajoBean implements HistorialTrabajoLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public HistorialTrabajoBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Historialtrabajo historialTrabajo) throws ServiceException {
        correcto=false;
        try{            
            session.save(historialTrabajo);
            tx.commit();
            session.close();
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
            session.update(historialTrabajo);
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
    public List<Historialtrabajo> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Historialtrabajo");         
            List<Historialtrabajo> historialTrabajos=query.list();
            session.close();        
            return historialTrabajos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Historialtrabajo traerHistorialtrabajoXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Historialtrabajo historialTrabajo where historialTrabajo.IdHistorialtrabajo=:id");            
        query.setParameter("id", Id);        
        Historialtrabajo historialTrabajo=(Historialtrabajo) query.uniqueResult();
        session.close();
        return historialTrabajo;
    }
    
}
