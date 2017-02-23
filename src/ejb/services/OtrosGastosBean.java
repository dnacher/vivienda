package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Otrosgastos;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class OtrosGastosBean implements OtrosGastosLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public OtrosGastosBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Otrosgastos otrosGastos) throws ServiceException {
        correcto=false;
        try{            
            session.save(otrosGastos);
            tx.commit();
            session.close();
            correcto=true;
            ConfiguracionControl.ActualizaId("OtrosGastos");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto; 
    }

    @Override
    public boolean eliminar(Otrosgastos otrosGastos) throws ServiceException {
        try{
            otrosGastos.setActivo(false);
            session.update(otrosGastos);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public boolean modificar(Otrosgastos otrosGastos) throws ServiceException {
        try{            
            session.update(otrosGastos);
            tx.commit();
            session.close();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
    public List<Otrosgastos> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Otrosgastos");         
            List<Otrosgastos> otrosGastos=query.list();
            session.close();        
            return otrosGastos;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Otrosgastos traerOtrosgastosXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Otrosgastos otrosGastos where otrosGastos.IdOtrosgastos=:id");            
        query.setParameter("id", Id);        
        Otrosgastos otrosGastos=(Otrosgastos) query.uniqueResult();
        session.close();
        return otrosGastos;
    }
    
}
