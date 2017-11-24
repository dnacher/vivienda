package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Listaprecios;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class ListaPreciosBean {
    
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public ListaPreciosBean(){
        sc=new SessionConnection();
        tx= sc.useSession().beginTransaction();
        correcto=false;
    }

    
    public boolean guardar(Listaprecios listaPrecios) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(listaPrecios);
            tx.commit();
            sc.closeSession();
            correcto=true;
            ConfiguracionControl.ActualizaId(ConstantesEtiquetas.LISTA_PRECIOS);
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());                    
        }
        return correcto;   
    }

    
    public boolean eliminar(Listaprecios listaPrecios) throws ServiceException {
        try{
            listaPrecios.setActivo(false);
            sc.useSession().update(listaPrecios);
            tx.commit();
            sc.closeSession();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public boolean modificar(Listaprecios listaPrecios) throws ServiceException {
        try{            
            sc.useSession().update(listaPrecios);
            tx.commit();
            sc.closeSession();
            correcto=true;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    
    public List<Listaprecios> traerTodos() throws ServiceException {
        try{
            Query query= sc.useSession().createQuery("from Listaprecios");         
            List<Listaprecios> listaPrecios=query.list();
            sc.closeSession();
            return listaPrecios;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    
    public Listaprecios traerListapreciosXId(int Id) throws ServiceException {
        Query query= sc.useSession().createQuery("from Listaprecios listaPrecios where listaPrecios.IdListaprecios=:id");            
        query.setParameter("id", Id);        
        Listaprecios listaPrecios=(Listaprecios) query.uniqueResult();
        sc.closeSession();
        return listaPrecios;
    }
    
}
