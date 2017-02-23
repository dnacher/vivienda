package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Listaprecios;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ListaPreciosBean implements ListaPreciosLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public ListaPreciosBean(){
        session = SessionConnection.getConnection().useSession();
        tx= session.beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Listaprecios listaPrecios) throws ServiceException {
        correcto=false;
        try{            
            session.save(listaPrecios);
            tx.commit();
            session.close();
            correcto=true;
            ConfiguracionControl.ActualizaId("ListaPrecios");
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;   
    }

    @Override
    public boolean eliminar(Listaprecios listaPrecios) throws ServiceException {
        try{
            listaPrecios.setActivo(false);
            session.update(listaPrecios);
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
    public boolean modificar(Listaprecios listaPrecios) throws ServiceException {
        try{            
            session.update(listaPrecios);
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
    public List<Listaprecios> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Listaprecios");         
            List<Listaprecios> listaPrecios=query.list();
            session.close();        
            return listaPrecios;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Listaprecios traerListapreciosXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Listaprecios listaPrecios where listaPrecios.IdListaprecios=:id");            
        query.setParameter("id", Id);        
        Listaprecios listaPrecios=(Listaprecios) query.uniqueResult();
        session.close();
        return listaPrecios;
    }
    
}
