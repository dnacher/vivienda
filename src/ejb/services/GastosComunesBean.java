package ejb.services;

import UtilsGeneral.ConfiguracionControl;
import entities.constantes.ConstantesEtiquetas;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Gastoscomunes;
import exceptions.ServiceException;
import java.util.List;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class GastosComunesBean implements GastosComunesLocal{
    
    //public Session session;
    public Transaction tx;
    public boolean correcto;
    SessionConnection sc;
    
    public GastosComunesBean(){
        sc=new SessionConnection();
        //session = sc.useSession();
        //tx= session.beginTransaction();
        tx = sc.useSession().beginTransaction();
        correcto=false;
    }

    @Override
    public boolean guardar(Gastoscomunes gastosComunes) throws ServiceException {
        correcto=false;
        try{            
            sc.useSession().save(gastosComunes);
            tx.commit();
            sc.closeSession();
            ConfiguracionControl.ActualizaId("GastosComunes");           
            correcto=true;          
        }
        catch(Exception ex){
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
            int ind=lista.size()-1;
            ultimoId = lista.get(ind).getId().getIdGastosComunes();
            ConfiguracionControl.ActualizaIdXId(ConstantesEtiquetas.GASTOS_COMUNES,ultimoId);
            correcto = true;
        } catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
        return correcto;
    }

    @Override
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

    @Override
    public boolean modificar(Gastoscomunes gastosComunes) throws ServiceException {
      /*  try{            
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

    @Override
    public List<Gastoscomunes> traerTodos() throws ServiceException {
      /*  try{
            Query query= session.createQuery("from Gastoscomunes");         
            List<Gastoscomunes> gastosComunes=query.list();
            session.close();        
            return gastosComunes;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }*/
      return null;
    }

    @Override
    public Gastoscomunes traerUsuarioXId(int Id) throws ServiceException {
       /* Query query= session.createQuery("from Gastoscomunes gastosComunes where gastosComunes.IdGastoscomunes=:id");            
        query.setParameter("id", Id);        
        Gastoscomunes gastosComunes=(Gastoscomunes) query.uniqueResult();
        session.close();
        return gastosComunes;*/
        return null;
    }
    
}
