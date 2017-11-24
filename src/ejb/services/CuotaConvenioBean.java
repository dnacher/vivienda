package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Convenio;
import entities.persistence.entities.Cuotaconvenio;
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
public class CuotaConvenioBean{

    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public CuotaConvenioBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Cuotaconvenio cuotaConvenio) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(cuotaConvenio);
            tx.commit();
            sc.closeSession();
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.CUOTA_CONVENIO);
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Cuotaconvenio cuotaConvenio) throws ServiceException {
        try {
            sc.useSession().update(cuotaConvenio);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Cuotaconvenio> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Cuotaconvenio");
            List<Cuotaconvenio> cuotaConvenios = query.list();
            sc.closeSession();
            return cuotaConvenios;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Cuotaconvenio traerCuotaconvenioXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Cuotaconvenio cuotaConvenio where cuotaConvenio.IdCuotaconvenio=:id");
        query.setParameter("id", Id);
        Cuotaconvenio cuotaConvenio = (Cuotaconvenio) query.uniqueResult();
        sc.closeSession();
        return cuotaConvenio;
    }

    public Convenio traerConvenioXUnidad(Unidad unidad) throws ServiceException {
        Query query = sc.useSession().createQuery("from Convenio convenio where convenio.unidad=:unidad");
        query.setParameter("unidad", unidad);
        Convenio convenio = (Convenio) query.uniqueResult();
        sc.closeSession();
        return convenio;
    }

    public int devuelveCantidadCuotas(Unidad unidad) {
        int cantidad = -1;
        Query query = sc.useSession().createQuery("select count(*) from Cuotaconvenio cuotaConvenio "
                + "where cuotaConvenio.convenio.unidad=:unidad");
        query.setParameter("unidad", unidad);
        Long count = (Long) query.uniqueResult();
        cantidad = toIntExact(count);
        sc.closeSession();
        return cantidad;
    }

    public int devuelveTotalCuotas(Unidad unidad) {
        int cantidad = -1;
        Query query = sc.useSession().createQuery("SELECT sum(cuotaConvenio.pago) as total from Cuotaconvenio cuotaConvenio "
                + "where cuotaConvenio.convenio.unidad=:unidad");
        query.setParameter("unidad", unidad);
        Long count = (Long) query.uniqueResult();
        if (count != null) {
            cantidad = toIntExact(count);
        } else {
            cantidad = 0;
        }
        sc.closeSession();
        return cantidad;
    }

}
