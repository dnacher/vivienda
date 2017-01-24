package ejb.services;

import entities.persistence.entities.Reglabonificacion;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface ReglaBonificacionLocal {
    
    public boolean guardar(Reglabonificacion reglaBonificacion)throws ServiceException;
    public boolean eliminar(Reglabonificacion reglaBonificacion)throws ServiceException;
    public boolean modificar(Reglabonificacion reglaBonificacion)throws ServiceException;
    public List<Reglabonificacion> traerTodos()throws ServiceException;
    public Reglabonificacion traerReglabonificacionXId(int Id)throws ServiceException;
}
