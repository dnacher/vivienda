package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tipobonificacion;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TipoBonificacionBean implements TipoBonificacionLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public TipoBonificacionBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Tipobonificacion tipoBonificacion) throws ServiceException {
        correcto=false;
        try{            
            session.save(tipoBonificacion);
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
    public boolean eliminar(Tipobonificacion tipoBonificacion) throws ServiceException {
        try{
            tipoBonificacion.setActivo(false);
            session.update(tipoBonificacion);
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
    public boolean modificar(Tipobonificacion tipoBonificacion) throws ServiceException {
        try{            
            session.update(tipoBonificacion);
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
    public List<Tipobonificacion> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Tipobonificacion");         
            List<Tipobonificacion> tipoBonificaciones=query.list();
            session.close();        
            return tipoBonificaciones;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Tipobonificacion traerTipobonificacionXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Tipobonificacion tipoBonificaciones where tipoBonificaciones.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Tipobonificacion tipoBonificaciones=(Tipobonificacion) query.uniqueResult();
        session.close();
        return tipoBonificaciones;
    }
    
}
