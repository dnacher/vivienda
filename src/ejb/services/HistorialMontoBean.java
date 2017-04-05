package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Historialmonto;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class HistorialMontoBean implements HistorialMontoLocal{
    
    //public Session session;
    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;
    
    public HistorialMontoBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        //tx= session.beginTransaction();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Historialmonto historialMonto) throws ServiceException {
        correcto=false;
        try{            
            //session.save(historialMonto);
            sc.useSession().save(historialMonto);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("HistorialMonto");
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
            //session.update(historialMonto);
            sc.useSession().update(historialMonto);
            tx.commit();
            //session.close();
            sc.closeSession();
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
            //session.update(historialMonto);
            sc.useSession().update(historialMonto);
            tx.commit();
            //session.close();
            sc.closeSession();
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
            //Query query= session.createQuery("from Historialmonto");         
            Query query= sc.useSession().createQuery("from Historialmonto");         
            List<Historialmonto> historialMontos=query.list();
            //session.close();        
            sc.closeSession();
            return historialMontos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Historialmonto traerHistorialmontoXId(int Id) throws ServiceException {
        //Query query= session.createQuery("from Historialmonto historialMonto where historialMonto.IdHistorialmonto=:id");            
        Query query= sc.useSession().createQuery("from Historialmonto historialMonto where historialMonto.IdHistorialmonto=:id");
        query.setParameter("id", Id);        
        Historialmonto historialMonto=(Historialmonto) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return historialMonto;
    }
    
}
