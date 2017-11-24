package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Gastoscomunes;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class GastosComunesBean{

    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;

    public GastosComunesBean() {
        sc = new SessionConnection();
        tx = sc.useSession().beginTransaction();
        correcto = false;
    }

    
    public boolean guardar(Gastoscomunes gastosComunes) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().save(gastosComunes);
            tx.commit();
            sc.closeSession();
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.GASTOS_COMUNES);
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    public boolean guardarGastos(List<Gastoscomunes> lista) throws ServiceException {
        try {
            int ultimoId;
            for (Gastoscomunes gc : lista) {
                sc.useSession().save(gc);
            }
            tx.commit();
            sc.closeSession();
            int ind = lista.size() - 1;
            ultimoId = lista.get(ind).getId().getIdGastosComunes() + 1;
            ConfiguracionControl.ActualizaIdXId(ConstantesEtiquetas.GASTOS_COMUNES, ultimoId);
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean eliminar(Gastoscomunes gastosComunes) throws ServiceException {
        /*    try{
            gastosComunes.setActivo(false);
            session.update(gastosComunes);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;*/
        return true;
    }

    
    public boolean modificar(Gastoscomunes gastosComunes) throws ServiceException {
        correcto = false;
        try {
            sc.useSession().update(gastosComunes);
            tx.commit();
            sc.closeSession();
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.GASTOS_COMUNES);
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
//    public List<Gastoscomunes> traerTodos() throws ServiceException {
//        /*  try{
//            Query query= session.createQuery("from Gastoscomunes");         
//            List<Gastoscomunes> gastosComunes=query.list();
//            session.close();        
//            return gastosComunes;
//        }
//        catch(Exception ex){
//            throw new ServiceException(ex.getMessage());
//        }*/
//        return null;
//    }
    
    public Gastoscomunes traerGCXUnidad(Unidad unidad, int periodo) throws ServiceException {
        try {
            Query query = sc.useSession().createQuery("FROM Gastoscomunes gc where gc.unidad=:unidad AND gc.periodo=:periodo");
            query.setParameter("unidad", unidad);
            query.setParameter("periodo", periodo);
            Gastoscomunes gc = (Gastoscomunes)query.uniqueResult();
            sc.closeSession();
            return gc;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    
//    public Gastoscomunes traerUsuarioXId(int Id) throws ServiceException {
//        /* Query query= session.createQuery("from Gastoscomunes gastosComunes where gastosComunes.IdGastoscomunes=:id");            
//        query.setParameter("id", Id);        
//        Gastoscomunes gastosComunes=(Gastoscomunes) query.uniqueResult();
//        session.close();
//        return gastosComunes;*/
//        return null;
//    }

}
