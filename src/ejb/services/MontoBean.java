package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Monto;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class MontoBean implements MontoLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public MontoBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Monto monto) throws ServiceException {
        correcto=false;
        try{            
            session.save(monto);
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
    public boolean eliminar(Monto monto) throws ServiceException {
        try{
            monto.setActivo(false);
            session.update(monto);
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
    public boolean modificar(Monto monto) throws ServiceException {
        try{            
            session.update(monto);
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
    public List<Monto> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Monto");         
            List<Monto> listaMontos=query.list();
            session.close();        
            return listaMontos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Monto traerMontoXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Monto monto where monto.IdMonto=:id");            
        query.setParameter("id", Id);        
        Monto listaMontos=(Monto) query.uniqueResult();
        session.close();
        return listaMontos;
    }
    
}
