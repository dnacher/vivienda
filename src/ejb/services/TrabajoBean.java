package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Trabajo;
import entities.persistence.entities.Trabajoxmaterial;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import static java.lang.Math.toIntExact;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TrabajoBean implements TrabajoLocal{
    
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public TrabajoBean(){
        sc=new SessionConnection();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Trabajo trabajo) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(trabajo);
            tx.commit();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.TRABAJO_UPPER);
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }   

    @Override
    public boolean modificar(Trabajo trabajo) throws ServiceException {
        try{       
            sc.useSession().update(trabajo);
            tx.commit();
            sc.closeSession();
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
            //Query query= session.createQuery("from Trabajo");         
            Query query= sc.useSession().createQuery("from Trabajo");
            List<Trabajo> trabajos=query.list();
            //session.close();        
            sc.closeSession();
            return trabajos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Trabajo traerTrabajoXId(int Id) throws ServiceException {
        //Query query= session.createQuery("from Trabajo trabajo where trabajo.IdTrabajo=:id");            
        Query query= sc.useSession().createQuery("from Trabajo trabajo where trabajo.IdTrabajo=:id");            
        query.setParameter("id", Id);        
        Trabajo trabajos=(Trabajo) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return trabajos;
    }
    
    public boolean UnidadesConTrabajoActivo(Unidad unidad){           
            boolean tieneTrabajos=false;
            boolean activo=true;
            String consulta="select count(*) from Trabajo trabajo "
                          + "where trabajo.unidad=:unidad "
                          + "and trabajo.activo=:activo";
            //Query query = session.createQuery(consulta);
            Query query = sc.useSession().createQuery(consulta);
            query.setParameter("unidad", unidad);
            query.setParameter("activo", activo);
            Long count = (Long)query.uniqueResult();
            int retorno=toIntExact(count);
            if(retorno>0){
                tieneTrabajos=true;
            }
            //session.close();
            sc.closeSession();
            return tieneTrabajos;
    }
    
    public Trabajo traeTrabajo(Unidad unidad){           
            Trabajo trabajo=null;
            boolean activo=true;
            String consulta="from Trabajo trabajo "
                          + "where trabajo.unidad=:unidad "
                          + "and trabajo.activo=:activo";
            //Query query = session.createQuery(consulta);
            Query query = sc.useSession().createQuery(consulta);
            query.setParameter("unidad", unidad);
            query.setParameter("activo", activo);
            trabajo = (Trabajo)query.uniqueResult();           
            //session.close();
            sc.closeSession();
            return trabajo;
    }
    
    public void cargaMaterialesEnTrabajo(List<Trabajoxmaterial> lista)throws ServiceException{        
        correcto=false;
        try{  
            for(Trabajoxmaterial tm: lista){
                //session.save(tm);
                sc.useSession().save(tm);
            }            
            tx.commit();
            //session.close();
            sc.closeSession();
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
