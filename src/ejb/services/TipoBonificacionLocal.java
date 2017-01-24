package ejb.services;

import entities.persistence.entities.Tipobonificacion;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface TipoBonificacionLocal {
    
    public boolean guardar(Tipobonificacion tipoBonificacion)throws ServiceException;
    public boolean eliminar(Tipobonificacion tipoBonificacion)throws ServiceException;
    public boolean modificar(Tipobonificacion tipoBonificacion)throws ServiceException;
    public List<Tipobonificacion> traerTodos()throws ServiceException;
    public Tipobonificacion traerTipobonificacionXId(int Id)throws ServiceException;
}
