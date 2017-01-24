package ejb.services;

import entities.persistence.entities.Urgencia;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface UrgenciaLocal {
    
    public boolean guardar(Urgencia urgencia)throws ServiceException;
    public boolean eliminar(Urgencia urgencia)throws ServiceException;
    public boolean modificar(Urgencia urgencia)throws ServiceException;
    public List<Urgencia> traerTodos()throws ServiceException;
    public Urgencia traerUrgenciaXId(int Id)throws ServiceException;
}
