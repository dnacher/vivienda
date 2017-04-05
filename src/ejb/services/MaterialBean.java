package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Material;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class MaterialBean implements MaterialLocal{
    
    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public MaterialBean(){
        //session = SessionConnection.getConnection().useSession();
        sc=new SessionConnection();
        //session = sc.useSession();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Material material) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(material);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId("Material");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;   
    }

    @Override
    public boolean eliminar(Material material) throws ServiceException {
        try{
            material.setActivo(false);
            sc.useSession().update(material);
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
    public boolean modificar(Material material) throws ServiceException {
        try{            
            sc.useSession().update(material);
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
    
    public boolean modificarTodos(List<Material> materiales) throws ServiceException {
        try{
            for(Material m:materiales){
                sc.useSession().update(m);
            }
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
    public List<Material> traerTodos() throws ServiceException {
        try{
            Query query= sc.useSession().createQuery("from Material");         
            List<Material> listaMateriales=query.list();
            //session.close();        
            sc.closeSession();
            return listaMateriales;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Material traerMaterialXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Material material where material.IdMaterial=:id");            
        query.setParameter("id", Id);        
        Material listaMateriales=(Material) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return listaMateriales;
    }
    
}
