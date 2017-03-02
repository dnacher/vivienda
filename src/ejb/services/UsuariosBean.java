package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Usuario;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Dani-Fla-Mathi
 */
public class UsuariosBean implements UsuariosLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public UsuariosBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Usuario usuario) throws ServiceException {
        correcto=false;
        try{            
            session.save(usuario);
            tx.commit();
            session.close();
            correcto=true;
            ConfiguracionControl.ActualizaId("Usuario");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Usuario usuario) throws ServiceException {
        try{
            usuario.setActivo(false);
            session.update(usuario);
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
    public boolean modificar(Usuario usuario) throws ServiceException {
        try{            
            session.update(usuario);
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
    public List<Usuario> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Usuario");         
            List<Usuario> usuarios=query.list();
            session.close();        
            return usuarios;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Usuario traerUsuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Usuario usuario=(Usuario) query.uniqueResult();
        session.close();        
        return usuario;
    }
    
    @Override
     public Usuario traerUsuarioXNombre(String nombre) throws ServiceException {
       Query query= session.createQuery("from Usuario usuario where usuario.nombre=:nombre");            
        query.setParameter("nombre", nombre);        
        Usuario usuario=(Usuario) query.uniqueResult();
        session.close();        
        return usuario;    
    }
     
     
    
    
}
