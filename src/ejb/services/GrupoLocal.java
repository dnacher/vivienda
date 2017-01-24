package ejb.services;

import entities.persistence.entities.Grupo;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface GrupoLocal {
    
    public boolean guardar(Grupo grupo)throws ServiceException;
    public boolean eliminar(Grupo grupo)throws ServiceException;
    public boolean modificar(Grupo grupo)throws ServiceException;
    public List<Grupo> traerTodos()throws ServiceException;
    public Grupo traerGrupoXId(int Id)throws ServiceException;
}
