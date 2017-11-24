package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Configuracion;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ConfiguracionBean{

    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public ConfiguracionBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Configuracion configuracion) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(configuracion);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Configuracion configuracion) throws ServiceException {
        try {
            sc.useSession().update(configuracion);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    public List<Configuracion> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Configuracion");
            List<Configuracion> convenios = query.list();
            sc.closeSession();
            return convenios;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<Configuracion> traerValorGastosComunes() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Configuracion configuracion where configuracion.nombreTabla=1 OR configuracion.nombreTabla=2 OR configuracion.nombreTabla=3 OR configuracion.nombreTabla=4 OR configuracion.nombreTabla=5");
            List<Configuracion> convenios = query.list();
            sc.closeSession();
            return convenios;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public Configuracion traerConfiguracionXTabla(String tabla) throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Configuracion configuracion where configuracion.nombreTabla=:tabla");
            query.setParameter("tabla", tabla);
            Configuracion configuracion = (Configuracion) query.uniqueResult();
            sc.closeSession();
            return configuracion;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public List<Configuracion> traerValorHabitaciones() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Configuracion configuracion where configuracion.nombreTabla in (1,2,3,4,5,6)");
            List<Configuracion> habitaciones = query.list();
            sc.closeSession();
            return habitaciones;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public void actualizaConfiguracionXTabla(String tabla) throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Configuracion configuracion where configuracion.nombreTabla=:tabla");
            query.setParameter("tabla", tabla);
            Configuracion configuracion = (Configuracion) query.uniqueResult();
            int nuevoindex = configuracion.getId() + 1;
            configuracion.setId(nuevoindex);
            modificar(configuracion);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

}
