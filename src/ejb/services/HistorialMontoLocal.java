package ejb.services;

import entities.persistence.entities.Historialmonto;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface HistorialMontoLocal {
    
    public boolean guardar(Historialmonto historialMonto)throws ServiceException;
    public boolean eliminar(Historialmonto historialMonto)throws ServiceException;
    public boolean modificar(Historialmonto historialMonto)throws ServiceException;
    public List<Historialmonto> traerTodos()throws ServiceException;
    public Historialmonto traerUsuarioXId(int Id)throws ServiceException;
}
