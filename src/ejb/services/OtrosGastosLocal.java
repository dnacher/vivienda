package ejb.services;

import entities.persistence.entities.Otrosgastos;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface OtrosGastosLocal {
    
    public boolean guardar(Otrosgastos otrosGastos)throws ServiceException;
    public boolean eliminar(Otrosgastos otrosGastos)throws ServiceException;
    public boolean modificar(Otrosgastos otrosGastos)throws ServiceException;
    public List<Otrosgastos> traerTodos()throws ServiceException;
    public Otrosgastos traerOtrosgastosXId(int Id)throws ServiceException;
}
