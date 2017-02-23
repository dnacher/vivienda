package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tipousuario;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TipoUsuarioBean implements TipoUsuariosLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public TipoUsuarioBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Tipousuario tipoUsuario) throws ServiceException {
        correcto=false;
        try{            
            session.save(tipoUsuario);
            tx.commit();
            session.close();
            correcto=true;
            ConfiguracionControl.ActualizaId("TipoUsuario");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Tipousuario tipoUsuario) throws ServiceException {
        try{
            tipoUsuario.setActivo(false);
            session.update(tipoUsuario);
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
    public boolean modificar(Tipousuario tipoUsuario) throws ServiceException {
        try{            
            session.update(tipoUsuario);
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
    public List<Tipousuario> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Tipousuario");         
            List<Tipousuario> tipoUsuario=query.list();
            session.close();        
            return tipoUsuario;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Tipousuario traerTipousuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Tipousuario tipoUsuario where tipoUsuario.IdTipoUsuario=:id");            
        query.setParameter("id", Id);        
        Tipousuario tipoUsuario=(Tipousuario) query.uniqueResult();
        session.close();
        return tipoUsuario;
    }
    
}
