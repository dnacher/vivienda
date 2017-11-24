package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Urgencia;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class UrgenciaBean {

    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public UrgenciaBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Urgencia urgencia) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(urgencia);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.URGENCIA);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Urgencia urgencia) throws ServiceException {
        try {
            urgencia.setActivo(false);
            //session.update(urgencia);
            sc.useSession().update(urgencia);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Urgencia urgencia) throws ServiceException {
        try {
            //session.update(urgencia);
            sc.useSession().update(urgencia);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Urgencia> traerTodos() throws ServiceException {
        try {
            //Query query= session.createQuery("from Urgencia");         
            Query query = sc.useSession().createQuery("from Urgencia");
            List<Urgencia> urgencias = query.list();
            //session.close();        
            sc.closeSession();
            return urgencias;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Urgencia traerUrgenciaXId(int Id) throws ServiceException {
        //Query query= session.createQuery("from Urgencia urgencia where urgencia.IdUrgencia=:id");            
        Query query = sc.useSession().createQuery("from Urgencia urgencia where urgencia.IdUrgencia=:id");
        query.setParameter("id", Id);
        Urgencia urgencias = (Urgencia) query.uniqueResult();
        //session.close();
        sc.closeSession();
        return urgencias;
    }

}
