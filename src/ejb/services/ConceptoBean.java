package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Concepto;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ConceptoBean{

    
    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;

    public ConceptoBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    public boolean guardar(Concepto concepto) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(concepto);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.CONCEPTO);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    public boolean eliminar(Concepto concepto) throws ServiceException {
        try {
            concepto.setActivo(false);
            sc.useSession().update(concepto);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    public boolean modificar(Concepto concepto) throws ServiceException {
        try {
            sc.useSession().update(concepto);
            tx.commit();
            //session.close();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    public List<Concepto> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Concepto");
            List<Concepto> conceptos = query.list();
            sc.closeSession();
            return conceptos;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public Concepto traerConceptoXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Concepto concepto where concepto.IdConcepto=:id");
        query.setParameter("id", Id);
        Concepto concepto = (Concepto) query.uniqueResult();
        sc.closeSession();
        return concepto;
    }

}
