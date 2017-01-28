package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Urgencia;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class UrgenciaBean implements UrgenciaLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public UrgenciaBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Urgencia urgencia) throws ServiceException {
        correcto=false;
        try{            
            session.save(urgencia);
            tx.commit();
            session.close();
            ConfiguracionBean cb=new ConfiguracionBean();
            cb.actualizaConfiguracionXTabla("Urgencia");
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Urgencia urgencia) throws ServiceException {
        try{
            urgencia.setActivo(false);
            session.update(urgencia);
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
    public boolean modificar(Urgencia urgencia) throws ServiceException {
        try{            
            session.update(urgencia);
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
    public List<Urgencia> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Urgencia");         
            List<Urgencia> urgencias=query.list();
            session.close();        
            return urgencias;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Urgencia traerUrgenciaXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Urgencia urgencia where urgencia.IdUrgencia=:id");            
        query.setParameter("id", Id);        
        Urgencia urgencias=(Urgencia) query.uniqueResult();
        session.close();
        return urgencias;
    }
    
}
