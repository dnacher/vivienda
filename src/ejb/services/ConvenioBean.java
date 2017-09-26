package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Convenio;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ConvenioBean implements ConvenioLocal {

    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public ConvenioBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    @Override
    public boolean guardar(Convenio convenio) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(convenio);
            tx.commit();
            sc.closeSession();
            UnidadBean ub = new UnidadBean();
            ub.actualizaGastosComunesAConvenios(convenio.getUnidad());            
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.CONVENIO_UPPER);
        } catch (Exception ex) {            
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Convenio convenio) throws ServiceException {
        try {
            convenio.setActivo(false);
            sc.useSession().update(convenio);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public boolean modificar(Convenio convenio) throws ServiceException {
        try {
            sc.useSession().update(convenio);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public List<Convenio> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Convenio");
            List<Convenio> convenios = query.list();
            sc.closeSession();
            return convenios;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Convenio traerConvenioXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Convenio convenio where convenio.IdConvenio=:id");
        query.setParameter("id", Id);
        Convenio convenio = (Convenio) query.uniqueResult();
        sc.closeSession();
        return convenio;
    }

    public Convenio traeConvenioXUnidad(Unidad unidad) {
        try {
        } catch (Exception ex) {
        }
        Query query = sc.useSession().createQuery("from Convenio convenio "
                + "where convenio.unidad=:unidad "
                + "and convenio.activo=true");
        query.setParameter("unidad", unidad);
        Convenio convenio = (Convenio) query.uniqueResult();
        sc.closeSession();
        return convenio;
    }

}
