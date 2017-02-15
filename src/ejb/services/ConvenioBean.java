package ejb.services;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Convenio;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ConvenioBean implements ConvenioLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public ConvenioBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Convenio convenio) throws ServiceException {
        correcto=false;
        try{            
            session.save(convenio);
            tx.commit();
            session.close();
            UnidadBean ub=new UnidadBean();
            ub.actualizaGastosComunesAConvenios(convenio.getUnidad());
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;
    }

    @Override
    public boolean eliminar(Convenio convenio) throws ServiceException {
        try{
            convenio.setActivo(false);
            session.update(convenio);
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
    public boolean modificar(Convenio convenio) throws ServiceException {
        try{            
            session.update(convenio);
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
    public List<Convenio> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Convenio");         
            List<Convenio> convenios=query.list();
            session.close();        
            return convenios;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Convenio traerConvenioXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Convenio convenio where convenio.IdConvenio=:id");            
        query.setParameter("id", Id);        
        Convenio convenio=(Convenio) query.uniqueResult();
        session.close();        
        return convenio;
    }
    
}
