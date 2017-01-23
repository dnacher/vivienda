package ejb.services;

import entities.persistence.entities.Tecnico;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface TecnicoLocal {
    
    public boolean guardar(Tecnico tecnico)throws ServiceException;
    public boolean eliminar(Tecnico tecnico)throws ServiceException;
    public boolean modificar(Tecnico tecnico)throws ServiceException;
    public List<Tecnico> traerTodos()throws ServiceException;
    public Tecnico traerUsuarioXId(int Id)throws ServiceException;
}
