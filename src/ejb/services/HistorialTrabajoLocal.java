package ejb.services;

import entities.persistence.entities.Historialtrabajo;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface HistorialTrabajoLocal {
    
    public boolean guardar(Historialtrabajo historialTrabajo)throws ServiceException;
    public boolean modificar(Historialtrabajo historialTrabajo)throws ServiceException;
    public List<Historialtrabajo> traerTodos()throws ServiceException;
    public Historialtrabajo traerHistorialtrabajoXId(int Id)throws ServiceException;
}
