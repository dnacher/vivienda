package ejb.services;

import entities.persistence.entities.Gastoscomunes;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface GastosComunesLocal {
    
    public boolean guardar(Gastoscomunes gastosComunes)throws ServiceException;
    public boolean eliminar(Gastoscomunes gastosComunes)throws ServiceException;
    public boolean modificar(Gastoscomunes gastosComunes)throws ServiceException;
    public List<Gastoscomunes> traerTodos()throws ServiceException;
    public Gastoscomunes traerUsuarioXId(int Id)throws ServiceException;
}
