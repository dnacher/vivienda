package ejb.services;

import entities.persistence.entities.Material;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface MaterialLocal {
    
    public boolean guardar(Material material)throws ServiceException;
    public boolean eliminar(Material material)throws ServiceException;
    public boolean modificar(Material material)throws ServiceException;
    public List<Material> traerTodos()throws ServiceException;
    public Material traerMaterialXId(int Id)throws ServiceException;
}
