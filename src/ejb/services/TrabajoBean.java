package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.NewHibernateUtil;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Trabajo;
import entities.persistence.entities.Trabajoxmaterial;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import static java.lang.Math.toIntExact;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TrabajoBean implements TrabajoLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public TrabajoBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Trabajo trabajo) throws ServiceException {
        correcto=false;
        try{            
            session.save(trabajo);
            tx.commit();
            session.close();
            correcto=true;
            ConfiguracionControl.ActualizaId("Trabajo");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }   

    @Override
    public boolean modificar(Trabajo trabajo) throws ServiceException {
        try{            
            session.update(trabajo);
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
    public List<Trabajo> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Trabajo");         
            List<Trabajo> trabajos=query.list();
            session.close();        
            return trabajos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Trabajo traerTrabajoXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Trabajo trabajo where trabajo.IdTrabajo=:id");            
        query.setParameter("id", Id);        
        Trabajo trabajos=(Trabajo) query.uniqueResult();
        session.close();
        return trabajos;
    }
    
    public boolean UnidadesConTrabajoActivo(Unidad unidad){           
            boolean tieneTrabajos=false;
            boolean activo=true;
            String consulta="select count(*) from Trabajo trabajo "
                          + "where trabajo.unidad=:unidad "
                          + "and trabajo.activo=:activo";
            Query query = session.createQuery(consulta);
            query.setParameter("unidad", unidad);
            query.setParameter("activo", activo);
            Long count = (Long)query.uniqueResult();
            int retorno=toIntExact(count);
            if(retorno>0){
                tieneTrabajos=true;
            }
            session.close();
            return tieneTrabajos;
    }
    
    public Trabajo traeTrabajo(Unidad unidad){           
            Trabajo trabajo=null;
            boolean activo=true;
            String consulta="from Trabajo trabajo "
                          + "where trabajo.unidad=:unidad "
                          + "and trabajo.activo=:activo";
            Query query = session.createQuery(consulta);
            query.setParameter("unidad", unidad);
            query.setParameter("activo", activo);
            trabajo = (Trabajo)query.uniqueResult();           
            session.close();
            return trabajo;
    }
    
    public void cargaMaterialesEnTrabajo(List<Trabajoxmaterial> lista)throws ServiceException{        
        correcto=false;
        try{  
            for(Trabajoxmaterial tm: lista){
                session.save(tm);
            }            
            tx.commit();
            session.close();
            correcto=true;
            //ConfiguracionControl.ActualizaId("Trabajo");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            throw new ServiceException(ex.getMessage());  
            
        }
     //   return correcto;     
    }
    
}
