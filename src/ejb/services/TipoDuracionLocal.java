package ejb.services;

import entities.persistence.entities.Tipoduracion;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface TipoDuracionLocal {
    
    public boolean guardar(Tipoduracion tipoDuracion)throws ServiceException;
    public boolean eliminar(Tipoduracion tipoDuracion)throws ServiceException;
    public boolean modificar(Tipoduracion tipoDuracion)throws ServiceException;
    public List<Tipoduracion> traerTodos()throws ServiceException;
    public Tipoduracion traerTipoduracionXId(int Id)throws ServiceException;
}
