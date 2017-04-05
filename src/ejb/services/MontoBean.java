package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Monto;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class MontoBean implements MontoLocal{
    
    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public MontoBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Monto monto) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(monto);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("Monto");
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
            sc.useSession().update(monto);
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
    public boolean modificar(Monto monto) throws ServiceException {
        try{            
            sc.useSession().update(monto);
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
    public List<Monto> traerTodos() throws ServiceException {
        try{
            Query query= sc.useSession().createQuery("from Monto");         
            List<Monto> listaMontos=query.list();
            //session.close();        
            sc.closeSession();
            return listaMontos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Monto traerMontoXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Monto monto where monto.idmonto=:id");            
        query.setParameter("id", Id);        
        Monto listaMontos=(Monto) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return listaMontos;
    }
    
}
