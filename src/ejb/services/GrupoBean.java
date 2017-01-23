package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Grupo;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class GrupoBean implements GrupoLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public GrupoBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Grupo grupo) throws ServiceException {
        correcto=false;
        try{            
            session.save(grupo);
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
    public boolean eliminar(Grupo grupo) throws ServiceException {
        try{
            grupo.setActivo(false);
            session.update(grupo);
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
    public boolean modificar(Grupo grupo) throws ServiceException {
        try{            
            session.update(grupo);
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
    public List<Grupo> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Usuario");         
            List<Grupo> grupos=query.list();
            session.close();        
            return grupos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Grupo traerUsuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Grupo grupo=(Grupo) query.uniqueResult();
        session.close();
        return grupo;
    }
    
}
