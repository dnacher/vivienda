package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Configuracion;
import exceptions.ServiceException;
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
    
}
