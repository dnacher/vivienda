package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tipobonificacion;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TipoBonificacionBean implements TipoBonificacionLocal{
    
    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public TipoBonificacionBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Tipobonificacion tipoBonificacion) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(tipoBonificacion);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("TipoBonificacion");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto; 
    }

    @Override
    public boolean eliminar(Tipobonificacion tipoBonificacion) throws ServiceException {
        try{
            tipoBonificacion.setActivo(false);
            sc.useSession().update(tipoBonificacion);
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
    public boolean modificar(Tipobonificacion tipoBonificacion) throws ServiceException {
        try{            
            sc.useSession().update(tipoBonificacion);
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
    public List<Tipobonificacion> traerTodos() throws ServiceException {
        try{
            Query query= sc.useSession().createQuery("from Tipobonificacion");         
            List<Tipobonificacion> tipoBonificaciones=query.list();
            //session.close();        
            sc.closeSession();
            return tipoBonificaciones;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Tipobonificacion traerTipobonificacionXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Tipobonificacion tipoBonificaciones where tipoBonificaciones.IdUsuario=:id");            
        query.setParameter("id", Id);        
        Tipobonificacion tipoBonificaciones=(Tipobonificacion) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return tipoBonificaciones;
    }
    
}
