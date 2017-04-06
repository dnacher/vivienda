/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.validaciones;

import ejb.services.UnidadBean;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;

/**
 *
 * @author Dani-Fla-Mathi
 */
public class UnidadValidationUnique {
    
    public boolean validarUnidad(Unidad unidad) throws ServiceException{
        boolean retorno=false;
        Unidad uni;
        UnidadBean ub= new UnidadBean();
        uni=ub.TraeUnidadesXBlockTorrePuerta(unidad);
        if(uni!=null){
            retorno=true;
        }
        return retorno;
    }
}
