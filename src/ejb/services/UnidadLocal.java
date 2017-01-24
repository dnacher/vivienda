package ejb.services;

import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface UnidadLocal {
    
    public boolean guardar(Unidad unidad)throws ServiceException;
    public boolean eliminar(Unidad unidad)throws ServiceException;
    public boolean modificar(Unidad unidad)throws ServiceException;
    public List<Unidad> traerTodos()throws ServiceException;
    public Unidad traerUnidadXId(int Id)throws ServiceException;
}
