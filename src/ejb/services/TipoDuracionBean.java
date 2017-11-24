package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tipoduracion;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TipoDuracionBean {

    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public TipoDuracionBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Tipoduracion tipoDuracion) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(tipoDuracion);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.TIPO_DURACION);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Tipoduracion tipoDuracion) throws ServiceException {
        try {
            tipoDuracion.setActivo(false);
            sc.useSession().update(tipoDuracion);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Tipoduracion tipoDuracion) throws ServiceException {
        try {
            sc.useSession().update(tipoDuracion);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Tipoduracion> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Tipoduracion");
            List<Tipoduracion> tipoduracion = query.list();
            sc.closeSession();
            return tipoduracion;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Tipoduracion traerTipoduracionXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Tipoduracion tipoduracion where tipoduracion.IdTipoduracion=:id");
        query.setParameter("id", Id);
        Tipoduracion tipoduracion = (Tipoduracion) query.uniqueResult();
        sc.closeSession();
        return tipoduracion;
    }

}
