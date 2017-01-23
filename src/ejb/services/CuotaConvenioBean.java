package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Cuotaconvenio;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class CuotaConvenioBean implements CuotaConvenioLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public CuotaConvenioBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Cuotaconvenio cuotaConvenio) throws ServiceException {
        correcto=false;
        try{            
            session.save(cuotaConvenio);
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
    public boolean modificar(Cuotaconvenio cuotaConvenio) throws ServiceException {
        try{            
            session.update(cuotaConvenio);
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
    public List<Cuotaconvenio> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Cuotaconvenio");         
            List<Cuotaconvenio> convenios=query.list();
            session.close();        
            return convenios;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Cuotaconvenio traerCuotaconvenioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Cuotaconvenio cuotaConvenio=(Cuotaconvenio) query.uniqueResult();
        session.close();        
        return cuotaConvenio;
    }
    
}
