package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tipoduracion;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TipoDuracionBean implements TipoDuracionLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public TipoDuracionBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Tipoduracion tipoDuracion) throws ServiceException {
        correcto=false;
        try{            
            session.save(tipoDuracion);
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
    public boolean eliminar(Tipoduracion tipoDuracion) throws ServiceException {
        try{
            tipoDuracion.setActivo(false);
            session.update(tipoDuracion);
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
    public boolean modificar(Tipoduracion tipoDuracion) throws ServiceException {
        try{            
            session.update(tipoDuracion);
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
    public List<Tipoduracion> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Tipoduracion");         
            List<Tipoduracion> tipoduracion=query.list();
            session.close();        
            return tipoduracion;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Tipoduracion traerTipoduracionXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Tipoduracion tipoduracion where tipoduracion.IdTipoduracion=:id");            
        query.setParameter("id", Id);        
        Tipoduracion tipoduracion=(Tipoduracion) query.uniqueResult();
        session.close();
        return tipoduracion;
    }
    
}
