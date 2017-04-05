package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Estado;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class EstadoBean implements EstadoLocal{
    
    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public EstadoBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Estado estado) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(estado);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("Estado");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Estado estado) throws ServiceException {
        try{
            estado.setActivo(false);
            sc.useSession().update(estado);
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
    public boolean modificar(Estado estado) throws ServiceException {
        try{            
            sc.useSession().update(estado);
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
    public List<Estado> traerTodos() throws ServiceException {
        try{
            Query query= sc.useSession().createQuery("from Estado estado order by estado.orden");
            List<Estado> estados=query.list();
            //session.close();        
            sc.closeSession();
            return estados;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Estado traerEstadoBeanXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Estado estado where estado.IdEstado=:id");            
        query.setParameter("id", Id);        
        Estado estado=(Estado) query.uniqueResult();
        //session.close();        
        sc.closeSession();
        return estado;
    }
    
}
