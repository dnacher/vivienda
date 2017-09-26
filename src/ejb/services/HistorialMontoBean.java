package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Historialmonto;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class HistorialMontoBean implements HistorialMontoLocal{
    
    SessionConnection sc;
    public Transaction tx;
    public boolean correcto;
    
    public HistorialMontoBean(){
        sc=new SessionConnection();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Historialmonto historialMonto) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(historialMonto);
            tx.commit();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.HISTORIAL_MONTO);
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;        
    }

    @Override
    public boolean eliminar(Historialmonto historialMonto) throws ServiceException {
        try{
            historialMonto.setActivo(false);
            sc.useSession().update(historialMonto);
            tx.commit();
            sc.closeSession();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public boolean modificar(Historialmonto historialMonto) throws ServiceException {
        try{            
            sc.useSession().update(historialMonto);
            tx.commit();
            sc.closeSession();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public List<Historialmonto> traerTodos() throws ServiceException {
        try{        
            Query query= sc.useSession().createQuery("from Historialmonto");         
            List<Historialmonto> historialMontos=query.list();
            sc.closeSession();
            return historialMontos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Historialmonto traerHistorialmontoXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Historialmonto historialMonto where historialMonto.IdHistorialmonto=:id");
        query.setParameter("id", Id);        
        Historialmonto historialMonto=(Historialmonto) query.uniqueResult();
        sc.closeSession();
        return historialMonto;
    }
    
}
