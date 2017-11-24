package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
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
public class UsuariosBean {

    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;

    public UsuariosBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    public void reconectar() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Usuario usuario) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(usuario);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.USUARIO);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Usuario usuario) throws ServiceException {
        try {
            usuario.setActivo(false);
            sc.useSession().update(usuario);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Usuario usuario) throws ServiceException {
        try {
            sc.useSession().update(usuario);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Usuario> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Usuario");
            List<Usuario> usuarios = query.list();
            sc.closeSession();
            return usuarios;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Usuario traerUsuarioXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Usuario usuario where usuario.IdUsuario=:id");
        query.setParameter("id", Id);
        Usuario usuario = (Usuario) query.uniqueResult();
        sc.closeSession();
        return usuario;
    }

    
    public Usuario traerUsuarioXNombre(String nombre) throws ServiceException {
        Query query = sc.useSession().createQuery("from Usuario usuario where usuario.nombre=:nombre");
        query.setParameter("nombre", nombre);
        Usuario usuario = (Usuario) query.uniqueResult();
        sc.closeSession();
        return usuario;
    }

    public List<Permisosusuario> TraePermisos(Tipousuario tipoUsuario) {
        List<Permisosusuario> lista = new ArrayList<>();
        Query query = sc.useSession().createQuery("from Permisosusuario pu where pu.tipousuario=:tipoUsuario");
        query.setParameter("tipoUsuario", tipoUsuario);
        lista = query.list();
        sc.closeSession();
        return lista;
    }

    public boolean guardaPermisos(List<Permisosusuario> lista) throws ServiceException {
        try {
            for (Permisosusuario pu : lista) {
                sc.useSession().save(pu);
            }
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    public boolean EliminaPermisos(Tipousuario tipoUsuario) throws ServiceException {
        try {
            List<Permisosusuario> permisos = TraePermisos(tipoUsuario);
            reconectar();
            if (permisos != null && permisos.size() > 0) {
                for (Permisosusuario pu : permisos) {
                    sc.useSession().delete(pu);
                }
            }
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println(ex.getMessage());
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

}
