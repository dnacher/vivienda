package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Permisosusuario;
import entities.persistence.entities.Tipousuario;
import entities.persistence.entities.Usuario;
import exceptions.ServiceException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Dani-Fla-Mathi
 */
public class UsuariosBean implements UsuariosLocal{
    
    //public Session session;
    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;
    
    public UsuariosBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        //tx= session.beginTransaction();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Usuario usuario) throws ServiceException {
        correcto=false;
        try{            
            //session.save(usuario);
            sc.useSession().save(usuario);
            tx.commit();
            //session.close();
            sc.closeSession();
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
            //session.update(usuario);
            sc.useSession().update(usuario);
            tx.commit();
            //session.close();
            sc.closeSession();
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
            //session.update(usuario);
            sc.useSession().update(usuario);
            tx.commit();
            //session.close();
            sc.closeSession();
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
            //Query query= session.createQuery("from Usuario");         
            Query query= sc.useSession().createQuery("from Usuario");
            List<Usuario> usuarios=query.list();
            //session.close();        
            sc.closeSession();
            return usuarios;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Usuario traerUsuarioXId(int Id) throws ServiceException {
        //Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        Query query= sc.useSession().createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Usuario usuario=(Usuario) query.uniqueResult();
        //session.close();        
        sc.closeSession();
        return usuario;
    }
    
    @Override
     public Usuario traerUsuarioXNombre(String nombre) throws ServiceException {
        //Query query= session.createQuery("from Usuario usuario where usuario.nombre=:nombre");            
        Query query= sc.useSession().createQuery("from Usuario usuario where usuario.nombre=:nombre");            
        query.setParameter("nombre", nombre);        
        Usuario usuario=(Usuario) query.uniqueResult();
        //session.close();        
        sc.closeSession();
        return usuario;    
    }
     
    public List<Permisosusuario> TraePermisos(Tipousuario tipoUsuario){
        List<Permisosusuario> lista=new ArrayList<>();
        //Query query= session.createQuery("from Permisosusuario pu where pu.tipousuario=:tipoUsuario");
        Query query= sc.useSession().createQuery("from Permisosusuario pu where pu.tipousuario=:tipoUsuario");
        query.setParameter("tipoUsuario", tipoUsuario);
        lista=query.list();
        //session.close();
        sc.closeSession();
        return lista;
    }
    
    public boolean guardaPermisos(List<Permisosusuario> lista)throws ServiceException{
        try{  
            for(Permisosusuario pu: lista){
                //session.save(pu);
                sc.useSession().save(pu);
            }            
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;            
        }
        catch(Exception ex){            
            throw new ServiceException(ex.getMessage());            
        }        
        return correcto;
    }
    
     public boolean EliminaPermisos(Tipousuario tipoUsuario)throws ServiceException{
        try{  
            String consulta="delete from Permisosusuario pu where pu.tipousuario=:tipoUsuario";           
            //Query query= session.createQuery(consulta);
            Query query= sc.useSession().createQuery(consulta);
            query.setParameter("tipoUsuario", tipoUsuario);
            query.executeUpdate();            
            //session.close();
            sc.closeSession();
            correcto=true; 
        }
        catch(Exception ex){
            System.out.println(ex);
            System.out.println(ex.getMessage());
            throw new ServiceException(ex.getMessage());            
        }        
        return correcto;
    }
    
}
