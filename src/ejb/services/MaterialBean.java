package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Material;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class MaterialBean {

    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public MaterialBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Material material) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(material);
            tx.commit();
            sc.closeSession();
            correcto = true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.MATERIAL_UPPER);
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Material material) throws ServiceException {
        try {
            material.setActivo(false);
            sc.useSession().update(material);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Material material) throws ServiceException {
        try {
            sc.useSession().update(material);
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    public boolean modificarTodos(List<Material> materiales) throws ServiceException {
        try {
            for (Material m : materiales) {
                sc.useSession().update(m);
            }
            tx.commit();
            sc.closeSession();
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Material> traerTodos() throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("from Material");
            List<Material> listaMateriales = query.list();
            sc.closeSession();
            return listaMateriales;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Material traerMaterialXId(int Id) throws ServiceException {
        Query query = sc.useSession().createQuery("from Material material where material.IdMaterial=:id");
        query.setParameter("id", Id);
        Material listaMateriales = (Material) query.uniqueResult();
        sc.closeSession();
        return listaMateriales;
    }

}
