/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
