package ejb.services;

import ejb.utils.UtilsConfiguracion;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Unidad;
import exceptions.ServiceException;
import static java.lang.Math.toIntExact;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Daniel
 */
public class UnidadBean implements UnidadLocal{
    
    public Session session;
    public Transaction tx;
    public boolean correcto;
    
    public UnidadBean(){
        try{      
            session = SessionConnection.getConnection().useSession();
            tx= session.beginTransaction();
            correcto=false;
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        
    }

    @Override
    public boolean guardar(Unidad unidad) throws ServiceException {
        correcto=false;
        try{            
            session.save(unidad);
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
    public boolean eliminar(Unidad unidad) throws ServiceException {
        try{
            unidad.setActivo(false);
            session.update(unidad);
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
    public boolean modificar(Unidad unidad) throws ServiceException {
        try{            
            session.update(unidad);
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
    public List<Unidad> traerTodos() throws ServiceException {
        try{
            Query query= session.createQuery("from Unidad");         
            List<Unidad> unidades=query.list();
            session.close();        
            return unidades;
        }
        catch(Exception ex){
            throw new ServiceException(ex.getMessage());
        }
    }

    @Override
    public Unidad traerUnidadXId(int Id) throws ServiceException {
        Query query= session.createQuery("from Unidad unidad where unidad.IdUnidad=:id");            
        query.setParameter("id", Id);        
        Unidad unidades=(Unidad) query.uniqueResult();
        session.close();
        return unidades;
    }
    
    public int totalUnidades(String block, int torre){        
        if(block.equals("") && torre==0){        
            Query query = session.createQuery("select count(*) from Unidad unidad");
            Long count = (Long)query.uniqueResult();       
            int retorno=toIntExact(count);
            session.close();
            return retorno;
        }
        else{
            Query query = session.createQuery("select count(*) from Unidad unidad where unidad.block=:elBlock and unidad.torre=:laTorre");
            query.setParameter("elBlock", block);
            query.setParameter("laTorre", torre);
            Long count = (Long)query.uniqueResult();       
            int retorno=toIntExact(count);
            session.close();
            return retorno;
         }
    }
    
    public int totalUnidadesNoPago(String block, int torre){        
        if(block.equals("") && torre==0){        
            Query query = session.createQuery("select count(*) from Unidad unidad");
            Long count = (Long)query.uniqueResult();       
            int retorno=toIntExact(count);
            session.close();
            return retorno;
        }
        else{
            Query query = session.createQuery("select count(*) from Unidad unidad "
                                            + "where unidad.block=:elBlock "
                                            + "and unidad.torre=:laTorre "
                                            + "and unidad.idUnidad NOT IN ("
                                                                  + "SELECT gastoscomunes.unidad "
                                                                  + "FROM Gastoscomunes gastoscomunes "
                                                                  + "WHERE gastoscomunes.periodo=:periodo "
                                                                  + "AND gastoscomunes.estado=:est)");
            query.setParameter("est", 2);
            query.setParameter("periodo", UtilsConfiguracion.devuelvePeriodoActual());
            query.setParameter("elBlock", block);
            query.setParameter("laTorre", torre);
            Long count = (Long)query.uniqueResult();       
            int retorno=toIntExact(count);
            session.close();
            return retorno;
         }
    }
    
    public List<Unidad> TraeUnidadesGastosComunesNoPago(){        
        List<Unidad> list= new ArrayList<>();        
        try{                
        Query query= session.createQuery("SELECT unidad FROM Unidad unidad "
                                       + "WHERE unidad.idUnidad NOT IN ("
                                                                  + "SELECT gastoscomunes.unidad "
                                                                  + "FROM Gastoscomunes gastoscomunes "
                                                                  + "WHERE gastoscomunes.periodo=:periodo "
                                                                  + "AND gastoscomunes.estado=:est)");
        query.setParameter("est", 2);
        query.setParameter("periodo", UtilsConfiguracion.devuelvePeriodoActual());
        list= query.list();
                       
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        finally{           
            session.close();
        }
        return list;
    }
    
    public List<Unidad> TraeUnidadesXBlockTorreNoPago(String block, int torre){        
        List<Unidad> lista=new ArrayList<>();        
        try{
        Query query= session.createQuery("SELECT unidad FROM Unidad unidad "
                                       + "WHERE Block=:block "
                                       + "AND Torre=:torre "
                                       + "AND unidad.idUnidad NOT IN (SELECT gastoscomunes.unidad "
                                                               + "FROM Gastoscomunes gastoscomunes "
                                                               + "WHERE gastoscomunes.periodo=:periodo "
                                                               + "AND gastoscomunes.estado=:est)");            
        query.setParameter("block", block);
        query.setParameter("torre", torre);
        query.setParameter("periodo", UtilsConfiguracion.devuelvePeriodoActual());
        //estado 2 pago al estar en el "not in" trae los que estan pagos
        query.setParameter("est", 2);
        lista=query.list();           
        session.close();       
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return lista;
    }
    
    public List<Unidad> TraeUnidadesConvenio(){        
        List<Unidad> list= new ArrayList<>();        
        try{                
        Query query= session.createQuery("SELECT unidad FROM Unidad unidad "
                                       + "WHERE unidad.idUnidad IN ("
                                                                  + "SELECT gastoscomunes.unidad "
                                                                  + "FROM Gastoscomunes gastoscomunes "
                                                                  + "WHERE gastoscomunes.periodo<:periodo "
                                                                  + "AND gastoscomunes.estado=:est)");
        query.setParameter("est", 1);
        query.setParameter("periodo", UtilsConfiguracion.devuelvePeriodoActual());
        list= query.list();                       
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        finally{           
            session.close();
        }
        return list;
    }
    
    public List<Unidad> TraeUnidadesConvenioXBlockTorre(String block, int torre){        
        List<Unidad> list= new ArrayList<>();        
        String consulta="";        
        try{
            consulta="SELECT unidad "
                   + "FROM Unidad unidad ";
            if(!block.isEmpty()){
                    consulta+="WHERE unidad.block=:block ";                    
                if(torre!=0){
                    consulta+="AND unidad.torre=:torre ";                    
                }
            }else{
                if(torre!=0){
                    consulta+="WHERE unidad.torre=:torre ";                    
                }
            }
            if(block.isEmpty() && torre==0){
                consulta+="WHERE unidad IN (";
            }
            else{
                consulta+="AND unidad IN (";
            }
            
            
                             consulta+="SELECT gastoscomunes.unidad "
                                     + "FROM Gastoscomunes gastoscomunes "
                                     + "WHERE gastoscomunes.periodo<:periodo "
                                     + "AND gastoscomunes.estado=:est)";
        Query query=session.createQuery(consulta);        
        query.setParameter("est", 1);
        query.setParameter("periodo", UtilsConfiguracion.devuelvePeriodoActual());
        if(!block.equals("")){
            query.setParameter("block", block);
        }
        if(torre!=0){
            query.setParameter("torre", torre);
        }
        list= query.list();                       
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        finally{           
            session.close();
        }
        return list;
    }
    
    public List<Unidad> TraeUnidadesConConvenioXBlockTorre(String block, int torre){
        List<Unidad> list= new ArrayList<>();        
        String consulta="";        
        try{
            consulta="SELECT unidad "
                   + "FROM Unidad unidad ";
            if(!block.isEmpty()){
                    consulta+="WHERE unidad.block=:block ";                    
                if(torre!=0){
                    consulta+="AND unidad.torre=:torre ";                    
                }
            }else{
                if(torre!=0){
                    consulta+="WHERE unidad.torre=:torre ";                    
                }
            }
            if(block.isEmpty() && torre==0){
                consulta+="WHERE unidad IN (";
            }
            else{
                consulta+="AND unidad IN (";
            }          
                             consulta+="SELECT convenio.unidad "
                                     + "FROM Convenio convenio)";
        Query query=session.createQuery(consulta);        
        if(!block.equals("")){
            query.setParameter("block", block);
        }
        if(torre!=0){
            query.setParameter("torre", torre);
        }
        list= query.list();                       
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        finally{           
            session.close();
        }
        return list;
    }
    
    public Long TraeTotalImporteXUnidadParaConvenio(Unidad unidad){
        Long total = 0L;        
        try{                
        Query query= session.createQuery("SELECT sum(gc.monto_1) as total FROM Gastoscomunes gc "
                                       + "WHERE gc.unidad=:unidad "
                                       + "AND gc.periodo<:periodo "
                                       + "AND gc.estado=:est)");
        query.setParameter("unidad", unidad);
        query.setParameter("periodo", UtilsConfiguracion.devuelvePeriodoActual());
        query.setParameter("est", 1);
        total= (Long) query.uniqueResult();                       
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        finally{           
            session.close();
        }
        return total;
    }
    
       
        public void actualizaGastosComunesAConvenios(Unidad unidad){
            Query query= session.createQuery("update Gastoscomunes set estado = :nuevoEstado "
                             + "where estado = :elEstado "
                             + "and unidad=:laUnidad "
                             + "and periodo<:elPeriodo");
            query.setInteger("nuevoEstado", 3 );
            query.setInteger( "elEstado", 1 );
            query.setInteger("elPeriodo", UtilsConfiguracion.devuelvePeriodoActual());
            query.setParameter("laUnidad", unidad);
            query.executeUpdate();
            tx.commit();
            session.close();         
        }   
}
