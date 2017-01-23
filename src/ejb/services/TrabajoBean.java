package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Trabajo;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TrabajoBean implements TrabajoLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public TrabajoBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Trabajo trabajo) throws ServiceException {
        correcto=false;
        try{            
            session.save(trabajo);
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
    public boolean modificar(Trabajo trabajo) throws ServiceException {
        try{            
            session.update(trabajo);
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
    public List<Trabajo> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Usuario");         
            List<Trabajo> trabajos=query.list();
            session.close();        
            return trabajos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Trabajo traerUsuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Trabajo trabajos=(Trabajo) query.uniqueResult();
        session.close();
        return trabajos;
    }
    
}
