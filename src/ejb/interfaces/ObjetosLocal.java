/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.interfaces;

import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Dani-Fla-Mathi
 */
public interface ObjetosLocal <T>{
    
    public boolean guardar(Class<T> t)throws ServiceException;
    public boolean eliminar(Class<T> t)throws ServiceException;
    public boolean modificar(Class<T> t)throws ServiceException;
    public List<Class<T>> traerTodos()throws ServiceException;
    public Class<T> traerUsuarioXId(int Id)throws ServiceException;
    
}
