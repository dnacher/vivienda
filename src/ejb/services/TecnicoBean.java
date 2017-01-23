package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tecnico;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TecnicoBean implements TecnicoLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public TecnicoBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Tecnico tecnico) throws ServiceException {
        correcto=false;
        try{            
            session.save(tecnico);
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
    public boolean eliminar(Tecnico tecnico) throws ServiceException {
        try{
            tecnico.setActivo(false);
            session.update(tecnico);
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
    public boolean modificar(Tecnico tecnico) throws ServiceException {
        try{            
            session.update(tecnico);
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
    public List<Tecnico> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Usuario");         
            List<Tecnico> tecnicos=query.list();
            session.close();        
            return tecnicos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Tecnico traerUsuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Tecnico tecnicos=(Tecnico) query.uniqueResult();
        session.close();
        return tecnicos;
    }
    
}
