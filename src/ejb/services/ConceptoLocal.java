package ejb.services;

import entities.persistence.entities.Concepto;
import exceptions.ServiceException;
import java.util.List;

/**
 *
 * @author Dani-Fla-Mathi
 */
public interface ConceptoLocal {
    
    public boolean guardar(Concepto concepto)throws ServiceException;
    public boolean eliminar(Concepto usuario)throws ServiceException;
    public boolean modificar(Concepto usuario)throws ServiceException;
    public List<Concepto> traerTodos()throws ServiceException;
    public Concepto traerUsuarioXId(int Id)throws ServiceException;
    
}
