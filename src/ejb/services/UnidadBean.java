package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class UnidadBean implements UnidadLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public UnidadBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Unidad unidad) throws ServiceException {
        correcto=false;
        try{            
            session.save(unidad);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Unidad unidad) throws ServiceException {
        try{
            unidad.setActivo(false);
            session.update(unidad);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public boolean modificar(Unidad unidad) throws ServiceException {
        try{            
            session.update(unidad);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public List<Unidad> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Usuario");         
            List<Unidad> unidades=query.list();
            session.close();        
            return unidades;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Unidad traerUsuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Unidad unidades=(Unidad) query.uniqueResult();
        session.close();
        return unidades;
    }
    
}
