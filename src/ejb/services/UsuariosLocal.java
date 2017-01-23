package ejb.services;

import entities.persistence.entities.Usuario;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Dani-Fla-Mathi
 */
public interface UsuariosLocal {
    
    public boolean guardar(Usuario usuario)throws ServiceException;
    public boolean eliminar(Usuario usuario)throws ServiceException;
    public boolean modificar(Usuario usuario)throws ServiceException;
    public List<Usuario> traerTodos()throws ServiceException;
    public Usuario traerUsuarioXId(int Id)throws ServiceException;
    public Usuario traerUsuarioXNombre(String nombre) throws ServiceException;
}
