/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
