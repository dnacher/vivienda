package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Gastoscomunes;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class GastosComunesBean implements GastosComunesLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public GastosComunesBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Gastoscomunes gastosComunes) throws ServiceException {
        correcto=false;
        try{            
            session.save(gastosComunes);
            tx.commit();
            session.close();
            ConfiguracionControl.ActualizaId("GastosComunes");
            correcto=true;            
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Gastoscomunes gastosComunes) throws ServiceException {
        try{
            gastosComunes.setActivo(false);
            session.update(gastosComunes);
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
    public boolean modificar(Gastoscomunes gastosComunes) throws ServiceException {
        try{            
            session.update(gastosComunes);
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
    public List<Gastoscomunes> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Gastoscomunes");         
            List<Gastoscomunes> gastosComunes=query.list();
            session.close();        
            return gastosComunes;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Gastoscomunes traerUsuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Gastoscomunes gastosComunes where gastosComunes.IdGastoscomunes=:id");            
        query.setParameter("id", Id);        
        Gastoscomunes gastosComunes=(Gastoscomunes) query.uniqueResult();
        session.close();
        return gastosComunes;
    }
    
}
