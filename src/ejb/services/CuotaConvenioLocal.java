package ejb.services;

import entities.persistence.entities.Cuotaconvenio;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface CuotaConvenioLocal {
    
    public boolean guardar(Cuotaconvenio cuotaConvenio)throws ServiceException;    
    public boolean modificar(Cuotaconvenio cuotaConvenio)throws ServiceException;
    public List<Cuotaconvenio> traerTodos()throws ServiceException;
    public Cuotaconvenio traerCuotaconvenioXId(int Id)throws ServiceException;
}
