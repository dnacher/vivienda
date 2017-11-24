package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Tecnico;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class TecnicoBean {

    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public TecnicoBean() {

        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Tecnico tecnico) throws ServiceException {
        correcto = false;
        try {
            tecnico.setCalificacion(0);
            sc.useSession().save(tecnico);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.TECNICO);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Tecnico tecnico) throws ServiceException {
        try {
            tecnico.setActivo(false);
            sc.useSession().update(tecnico);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Tecnico tecnico) throws ServiceException {
        try {
            sc.useSession().update(tecnico);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Tecnico> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Tecnico");
            List<Tecnico> tecnicos = query.list();
            sc.closeSession();
            return tecnicos;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Tecnico traerTecnicoXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Tecnico tecnico where tecnico.idTecnico=:id");
        query.setParameter("id", Id);
        Tecnico tecnicos = (Tecnico) query.uniqueResult();
        sc.closeSession();
        return tecnicos;
    }

}
