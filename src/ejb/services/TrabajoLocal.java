package ejb.services;

import entities.persistence.entities.Trabajo;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface TrabajoLocal {
    
    public boolean guardar(Trabajo trabajo)throws ServiceException;
    public boolean modificar(Trabajo trabajo)throws ServiceException;
    public List<Trabajo> traerTodos()throws ServiceException;
    public Trabajo traerTrabajoXId(int Id)throws ServiceException;
}
