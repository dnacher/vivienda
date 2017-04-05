package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tecnico;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TecnicoBean implements TecnicoLocal{
    
    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public TecnicoBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Tecnico tecnico) throws ServiceException {
        correcto=false;
        try{
            tecnico.setCalificacion(0);
            sc.useSession().save(tecnico);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("Tecnico");
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
            sc.useSession().update(tecnico);
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
    public boolean modificar(Tecnico tecnico) throws ServiceException {
        try{            
            sc.useSession().update(tecnico);
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
    public List<Tecnico> traerTodos() throws ServiceException {
        try{
            Query query= sc.useSession().createQuery("from Tecnico");         
            List<Tecnico> tecnicos=query.list();
            //session.close();        
            sc.closeSession();
            return tecnicos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Tecnico traerTecnicoXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Tecnico tecnico where tecnico.idTecnico=:id");            
        query.setParameter("id", Id);        
        Tecnico tecnicos=(Tecnico) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return tecnicos;
    }
    
}
