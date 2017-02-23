package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Convenio;
import entities.persistence.entities.Cuotaconvenio;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import static java.lang.Math.toIntExact;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class CuotaConvenioBean implements CuotaConvenioLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public CuotaConvenioBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Cuotaconvenio cuotaConvenio) throws ServiceException {
        correcto=false;
        try{            
            session.save(cuotaConvenio);
            tx.commit();
            session.close();
            ConfiguracionControl.ActualizaId("CuotaConvenio");
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }    

    @Override
    public boolean modificar(Cuotaconvenio cuotaConvenio) throws ServiceException {
        try{            
            session.update(cuotaConvenio);
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
    public List<Cuotaconvenio> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Cuotaconvenio");         
            List<Cuotaconvenio> cuotaConvenios=query.list();
            session.close();        
            return cuotaConvenios;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Cuotaconvenio traerCuotaconvenioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Cuotaconvenio cuotaConvenio where cuotaConvenio.IdCuotaconvenio=:id");
        query.setParameter("id", Id);        
        Cuotaconvenio cuotaConvenio=(Cuotaconvenio) query.uniqueResult();
        session.close();        
        return cuotaConvenio;
    }
    
    public Convenio traerConvenioXUnidad(Unidad unidad) throws ServiceException {
        Query query= session.createQuery("from Convenio convenio where convenio.unidad=:unidad");
        query.setParameter("unidad", unidad);
        Convenio convenio=(Convenio) query.uniqueResult();
        session.close();
        return convenio;
    }
    
    public int devuelveCantidadCuotas(Unidad unidad){
        int cantidad=-1;
        Query query = session.createQuery("select count(*) from Cuotaconvenio cuotaConvenio "
                                        + "where cuotaConvenio.convenio.unidad=:unidad");
        query.setParameter("unidad", unidad);
        Long count = (Long)query.uniqueResult();       
        cantidad=toIntExact(count);
        session.close();
        return cantidad;
    }
    
     public int devuelveTotalCuotas(Unidad unidad){
        int cantidad=-1;
        Query query = session.createQuery("SELECT sum(cuotaConvenio.pago) as total from Cuotaconvenio cuotaConvenio "
                                        + "where cuotaConvenio.convenio.unidad=:unidad");
        query.setParameter("unidad", unidad);
        Long count = (Long)query.uniqueResult();
        if(count!=null){
            cantidad=toIntExact(count);
        }else{
            cantidad=0;
        }        
        session.close();
        return cantidad;
    }
    
}
