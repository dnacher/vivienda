package ejb.services;

import entities.persistence.entities.Listaprecios;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface ListaPreciosLocal {
    
    public boolean guardar(Listaprecios listaPrecios)throws ServiceException;
    public boolean eliminar(Listaprecios listaPrecios)throws ServiceException;
    public boolean modificar(Listaprecios listaPrecios)throws ServiceException;
    public List<Listaprecios> traerTodos()throws ServiceException;
    public Listaprecios traerListapreciosXId(int Id)throws ServiceException;
}
