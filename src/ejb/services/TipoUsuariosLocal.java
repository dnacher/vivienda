package ejb.services;

import entities.persistence.entities.Tipousuario;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface TipoUsuariosLocal {
    
    public boolean guardar(Tipousuario tipoUsuario)throws ServiceException;
    public boolean eliminar(Tipousuario tipoUsuario)throws ServiceException;
    public boolean modificar(Tipousuario tipoUsuario)throws ServiceException;
    public List<Tipousuario> traerTodos()throws ServiceException;
    public Tipousuario traerUsuarioXId(int Id)throws ServiceException;
}
