package ejb.services;

import entities.persistence.entities.Audit;
import exceptions.ServiceException;

/**
 *
 * @author Dani-Fla-Mathi
 */
public interface AuditLocal {
    
    public boolean guardar(Audit audit)throws ServiceException;    
    public boolean modificar(Audit audit)throws ServiceException;
  
    
}
