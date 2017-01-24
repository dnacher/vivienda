package ejb.services;

import entities.persistence.entities.Monto;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface MontoLocal {
    
    public boolean guardar(Monto monto)throws ServiceException;
    public boolean eliminar(Monto monto)throws ServiceException;
    public boolean modificar(Monto monto)throws ServiceException;
    public List<Monto> traerTodos()throws ServiceException;
    public Monto traerMontoXId(int Id)throws ServiceException;
}
