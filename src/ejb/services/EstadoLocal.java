package ejb.services;

import entities.persistence.entities.Estado;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface EstadoLocal {
    
    public boolean guardar(Estado estado)throws ServiceException;
    public boolean eliminar(Estado estado)throws ServiceException;
    public boolean modificar(Estado estado)throws ServiceException;
    public List<Estado> traerTodos()throws ServiceException;
    public Estado traerEstadoBeanXId(int Id)throws ServiceException;
}
