package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Configuracion;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ConfiguracionBean implements ConfiguracionLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public ConfiguracionBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Configuracion configuracion) throws ServiceException {
        correcto=false;
        try{            
            session.save(configuracion);
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
    public boolean modificar(Configuracion configuracion) throws ServiceException {
        try{            
            session.update(configuracion);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }
    
       public List<Configuracion> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Configuracion");         
            List<Configuracion> convenios=query.list();
            session.close();        
            return convenios;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
       }
        
        public Configuracion traerConfiguracionXTabla(String tabla) throws ServiceException {
            try{
                Query query= session.createQuery("from Configuracion configuracion where configuracion.nombreTabla=:tabla");
                query.setParameter("tabla", tabla);
                Configuracion configuracion=(Configuracion)query.uniqueResult();
                session.close();        
                return configuracion;
            }
            catch(Exception ex){
                throw new ServiceException(ex.getMessage());
            }
        }
        
         public void actualizaConfiguracionXTabla(String tabla) throws ServiceException {
            try{
                Query query= session.createQuery("from Configuracion configuracion where configuracion.nombreTabla=:tabla");
                query.setParameter("tabla", tabla);
                Configuracion configuracion=(Configuracion)query.uniqueResult();
                int nuevoindex=configuracion.getId()+1;
                configuracion.setId(nuevoindex);
                modificar(configuracion);
            }
            catch(Exception ex){
                throw new ServiceException(ex.getMessage());
            }
        }
    
}
