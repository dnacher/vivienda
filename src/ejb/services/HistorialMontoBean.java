package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Historialmonto;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class HistorialMontoBean implements HistorialMontoLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public HistorialMontoBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Historialmonto historialMonto) throws ServiceException {
        correcto=false;
        try{            
            session.save(historialMonto);
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
    public boolean eliminar(Historialmonto historialMonto) throws ServiceException {
        try{
            historialMonto.setActivo(false);
            session.update(historialMonto);
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
    public boolean modificar(Historialmonto historialMonto) throws ServiceException {
        try{            
            session.update(historialMonto);
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
    public List<Historialmonto> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Usuario");         
            List<Historialmonto> historialMontos=query.list();
            session.close();        
            return historialMontos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Historialmonto traerUsuarioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Usuario usuario where usuario.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Historialmonto historialMonto=(Historialmonto) query.uniqueResult();
        session.close();
        return historialMonto;
    }
    
}
