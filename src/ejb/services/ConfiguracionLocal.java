package ejb.services;

import entities.persistence.entities.Configuracion;
import exceptions.ServiceException;

/**
 *
 * @author Dani-Fla-Mathi
 */
public interface ConfiguracionLocal {
    
    public boolean guardar(Configuracion configuracion)throws ServiceException;    
    public boolean modificar(Configuracion configuracion)throws ServiceException;
    
}
