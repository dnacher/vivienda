package ejb.services;

import entities.persistence.entities.Convenio;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Dani-Fla-Mathi
 */
public interface ConvenioLocal {
    
    public boolean guardar(Convenio convenio)throws ServiceException;
    public boolean eliminar(Convenio convenio)throws ServiceException;
    public boolean modificar(Convenio convenio)throws ServiceException;
    public List<Convenio> traerTodos()throws ServiceException;
    public Convenio traerUsuarioXId(int Id)throws ServiceException;
    
}
