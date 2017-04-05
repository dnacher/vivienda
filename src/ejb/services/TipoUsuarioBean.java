package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tipousuario;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TipoUsuarioBean implements TipoUsuariosLocal{
    
    //public Session session;
    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;
    
    public TipoUsuarioBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();        
        //tx= session.beginTransaction();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Tipousuario tipoUsuario) throws ServiceException {
        correcto=false;
        try{            
            //session.save(tipoUsuario);
            sc.useSession().save(tipoUsuario);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("TipoUsuario");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Tipousuario tipoUsuario) throws ServiceException {
        try{
            tipoUsuario.setActivo(false);
            //session.update(tipoUsuario);
            sc.useSession().update(tipoUsuario);
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
    public boolean modificar(Tipousuario tipoUsuario) throws ServiceException {
        try{            
            //session.update(tipoUsuario);
            sc.useSession().update(tipoUsuario);
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
    public List<Tipousuario> traerTodos() throws ServiceException {
        try{
            //Query query= session.createQuery("from Tipousuario");         
            Query query= sc.useSession().createQuery("from Tipousuario");         
            List<Tipousuario> tipoUsuario=query.list();
            //session.close();        
            sc.closeSession();
            return tipoUsuario;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Tipousuario traerTipousuarioXId(int Id) throws ServiceException {
        //Query query= session.createQuery("from Tipousuario tipoUsuario where tipoUsuario.IdTipoUsuario=:id");            
        Query query= sc.useSession().createQuery("from Tipousuario tipoUsuario where tipoUsuario.IdTipoUsuario=:id");
        query.setParameter("id", Id);        
        Tipousuario tipoUsuario=(Tipousuario) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return tipoUsuario;
    }
    
}
