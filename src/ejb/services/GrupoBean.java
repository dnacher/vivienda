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
public class GrupoBean{

    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;

    public GrupoBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Grupo grupo) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(grupo);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.GRUPO);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Grupo grupo) throws ServiceException {
        try {
            grupo.setActivo(false);
            sc.useSession().update(grupo);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Grupo grupo) throws ServiceException {
        try {
            sc.useSession().update(grupo);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Grupo> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Grupo");
            List<Grupo> grupos = query.list();
            sc.closeSession();
            return grupos;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Grupo traerGrupoXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Grupo grupo where grupo.IdGrupo=:id");
        query.setParameter("id", Id);
        Grupo grupo = (Grupo) query.uniqueResult();
        sc.closeSession();
        return grupo;
    }
}
