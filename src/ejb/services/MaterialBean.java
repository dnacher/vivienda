package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Material;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class MaterialBean implements MaterialLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public MaterialBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Material material) throws ServiceException {
        correcto=false;
        try{            
            session.save(material);
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
    public boolean eliminar(Material material) throws ServiceException {
        try{
            material.setActivo(false);
            session.update(material);
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
    public boolean modificar(Material material) throws ServiceException {
        try{            
            session.update(material);
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
    public List<Material> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Usuario");         
            List<Material> listaMateriales=query.list();
            session.close();        
            return listaMateriales;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Material traerUsuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Material listaMateriales=(Material) query.uniqueResult();
        session.close();
        return listaMateriales;
    }
    
}
