package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Otrosgastos;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class OtrosGastosBean {

    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public OtrosGastosBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Otrosgastos otrosGastos) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(otrosGastos);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.OTROS_GASTOS);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Otrosgastos otrosGastos) throws ServiceException {
        try {
            otrosGastos.setActivo(false);
            sc.useSession().update(otrosGastos);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Otrosgastos otrosGastos) throws ServiceException {
        try {
            sc.useSession().update(otrosGastos);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Otrosgastos> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Otrosgastos");
            List<Otrosgastos> otrosGastos = query.list();
            sc.closeSession();
            return otrosGastos;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Otrosgastos traerOtrosgastosXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Otrosgastos otrosGastos where otrosGastos.IdOtrosgastos=:id");
        query.setParameter("id", Id);
        Otrosgastos otrosGastos = (Otrosgastos) query.uniqueResult();
        sc.closeSession();
        return otrosGastos;
    }

}
