package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Reglabonificacion;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ReglaBonificacionBean implements ReglaBonificacionLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public ReglaBonificacionBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Reglabonificacion reglaBonificacion) throws ServiceException {
        correcto=false;
        try{            
            session.save(reglaBonificacion);
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
    public boolean eliminar(Reglabonificacion reglaBonificacion) throws ServiceException {
        try{
            reglaBonificacion.setActivo(false);
            session.update(reglaBonificacion);
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
    public boolean modificar(Reglabonificacion reglaBonificacion) throws ServiceException {
        try{            
            session.update(reglaBonificacion);
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
    public List<Reglabonificacion> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Reglabonificacion");         
            List<Reglabonificacion> reglaBonificacion=query.list();
            session.close();        
            return reglaBonificacion;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Reglabonificacion traerReglabonificacionXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Reglabonificacion reglaBonificacion where reglaBonificacion.IdReglaBonificacion=:id");            
        query.setParameter("id", Id);        
        Reglabonificacion reglaBonificacion=(Reglabonificacion) query.uniqueResult();
        session.close();
        return reglaBonificacion;
    }
    
}
