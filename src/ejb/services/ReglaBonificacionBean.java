package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Reglabonificacion;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ReglaBonificacionBean {

    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public ReglaBonificacionBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Reglabonificacion reglaBonificacion) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(reglaBonificacion);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.REGLA_BONIFICACION);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Reglabonificacion reglaBonificacion) throws ServiceException {
        try {
            reglaBonificacion.setActivo(false);
            sc.useSession().update(reglaBonificacion);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Reglabonificacion reglaBonificacion) throws ServiceException {
        try {
            sc.useSession().update(reglaBonificacion);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Reglabonificacion> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Reglabonificacion");
            List<Reglabonificacion> reglaBonificacion = query.list();
            sc.closeSession();
            return reglaBonificacion;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public Reglabonificacion traeBonificacionesHabitaciones(int habitacion) throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Reglabonificacion rb where rb.habitaciones=:habitacion");
            query.setParameter("habitacion", habitacion);
            Reglabonificacion reglaBonificacion = (Reglabonificacion) query.uniqueResult();
            sc.closeSession();
            return reglaBonificacion;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public boolean verificaUnicoHabitaciones(Reglabonificacion rb) throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Reglabonificacion rb where rb.habitaciones=:id");
            query.setParameter("id", rb.getHabitaciones());
            Reglabonificacion reglaBonificacion = (Reglabonificacion) query.uniqueResult();
            sc.closeSession();
            if (reglaBonificacion != null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Reglabonificacion traerReglabonificacionXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Reglabonificacion reglaBonificacion where reglaBonificacion.IdReglaBonificacion=:id");
        query.setParameter("id", Id);
        Reglabonificacion reglaBonificacion = (Reglabonificacion) query.uniqueResult();
        sc.closeSession();
        return reglaBonificacion;
    }

}
