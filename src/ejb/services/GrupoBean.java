package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Grupo;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class GrupoBean implements GrupoLocal{
    
    //public Session session;
    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;
    
    public GrupoBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        //tx= session.beginTransaction();
        tx = sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Grupo grupo) throws ServiceException {
        correcto=false;
        try{            
            //session.save(grupo);
            sc.useSession().save(grupo);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.GRUPO);
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
            //session.update(grupo);
            sc.useSession().update(grupo);
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
    public boolean modificar(Grupo grupo) throws ServiceException {
        try{            
            //session.update(grupo);
            sc.useSession().update(grupo);
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
    public List<Grupo> traerTodos() throws ServiceException {
        try{
            //Query query= session.createQuery("from Grupo");         
            Query query= sc.useSession().createQuery("from Grupo");
            List<Grupo> grupos=query.list();
            //session.close();
            sc.closeSession();
            return grupos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Grupo traerGrupoXId(int Id) throws ServiceException {
        //Query query= session.createQuery("from Grupo grupo where grupo.IdGrupo=:id");            
        Query query= sc.useSession().createQuery("from Grupo grupo where grupo.IdGrupo=:id");
        query.setParameter("id", Id);        
        Grupo grupo=(Grupo) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return grupo;
    }    
}
