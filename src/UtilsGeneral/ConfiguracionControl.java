package UtilsGeneral;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Configuracion;
import exceptions.ConectarException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;

public class ConfiguracionControl {
    
    public static int traeUltimoId(String tabla){
        Configuracion c;
        int i=-1;
        Session session = SessionConnection.getConnection().useSession();
        Query query= session.createQuery("from Configuracion where NombreTabla=:name");            
        query.setParameter("name", tabla);
        c=(Configuracion)query.uniqueResult();           
        session.close();
        if(c!=null){
            i=c.getId();
        }
        return i;
    }
    
    public Connection conectar() throws ConectarException{
        Connection conexion= null;         
        try {           
            Session session = SessionConnection.getConnection().useSession();              
            SessionFactoryImplementor sessionFactoryImplementation = (SessionFactoryImplementor) session.getSessionFactory();
            ConnectionProvider connectionProvider = sessionFactoryImplementation.getConnectionProvider();            
            conexion=connectionProvider.getConnection();
        } catch (SQLException ex) {
           throw new ConectarException(ex.getMessage());
        }
        return conexion;
    }
    
    public static void ActualizaId(String tabla){
        int i=traeUltimoId(tabla);
        Configuracion c;
        if(i!=-1){
            Session session = SessionConnection.getConnection().useSession();
            Query query= session.createQuery("from Configuracion where NombreTabla=:name");            
            query.setParameter("name", tabla);
            c=(Configuracion)query.uniqueResult(); 
            i++;
            c.setId(i);
            Transaction tx= session.beginTransaction(); 
            session.update(c);
            tx.commit();
        }
    }
    
    public static void ActualizaIdXId(String tabla, int id){
        int i=traeUltimoId(tabla);
        Configuracion c;
        if(i!=-1){
            Session session = SessionConnection.getConnection().useSession();
            Query query= session.createQuery("from Configuracion where NombreTabla=:name");            
            query.setParameter("name", tabla);
            c=(Configuracion)query.uniqueResult();             
            c.setId(id);
            Transaction tx= session.beginTransaction(); 
            session.update(c);
            tx.commit();
        }
    }
    
    public static Date TraeFecha(LocalDate localDate){         
         Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
         return date;
    }
    
    public void generarReporte(String reporte){       
        try{
            JasperReport jr= (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/"+ reporte +".jasper"));
            JasperPrint jp= JasperFillManager.fillReport(jr, null, conectar());
            JasperViewer jv= new JasperViewer(jp, false);           
            jv.setVisible(true);
            jv.setTitle(reporte);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public void generarReporteConParametros(String reporte,Map<String,Object> parameters){       
        try{
            /*
            String logo="/web/images/Vivienda.png";         
            parameters.put("logo",this.getClass().getResourceAsStream(logo));
            parameters.put("idUnidad",0);
            parameters.put("periodo",201702);*/
            JasperReport jr= (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/"+ reporte +".jasper"));
            JasperPrint jp= JasperFillManager.fillReport(jr, parameters, conectar());
            JasperViewer jv= new JasperViewer(jp, false);           
            jv.setVisible(true);
            jv.setTitle(reporte);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }      
         
    }
  /*  public static void actualizaBonificacion(String tabla, int bonificacion){
        Configuracion c;
            /*SessionFactory sf= NewHibernateUtil.getSessionFactory();
            Session session;*/
    /*        Session session = SessionConnection.getConnection().useSession();
            Query query= session.createQuery("from Configuracion where NombreTabla=:name");            
            query.setParameter("name", tabla);
            c=(Configuracion)query.uniqueResult();             
            c.setId(bonificacion);
            Transaction tx= session.beginTransaction(); 
            session.update(c);
            tx.commit();        
    }
    
    public static int StringAInt(String numero){
        int num=-1;
        try{
            num=Integer.parseInt(numero);
        }
        catch(Exception ex){
            ex.getMessage();
        }
        return num;
    }
    
   
    
    public void generarReporte(String reporte){
       
        try{
            JasperReport jr= (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/"+ reporte +".jasper"));
            JasperPrint jp= JasperFillManager.fillReport(jr, null, lc.conectar());
            JasperViewer jv= new JasperViewer(jp, false);           
            jv.setVisible(true);
            jv.setTitle(reporte);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }      
         
    }
    
    public static List<String> mesesLista(Unidad uni){
        List<String>retorno= new ArrayList<String>();
        Date d=uni.getFechaIngreso();
        String str=d.toString();
        String[] list=str.split("-");
        int YearInicial=Integer.parseInt(list[0]);
        int MonthInicial=Integer.parseInt(list[1]);
        int yearNow = Calendar.getInstance().get(Calendar.YEAR);
        int monthNow=Calendar.getInstance().get(Calendar.MONTH)+1;
       while(YearInicial!=yearNow || MonthInicial!=monthNow){       
           retorno.add(YearInicial + "/" + MonthInicial);
           if(MonthInicial==12){
           YearInicial++;           
           MonthInicial=1;
           retorno.add(YearInicial + "/" + MonthInicial);
           }           
           MonthInicial++;
       }
       return retorno;
    }
    
    public static String mesesString(Unidad uni){        
        String ret="";
        Date d=uni.getFechaIngreso();
        String str=d.toString();
        String[] list=str.split("-");
        int YearInicial=Integer.parseInt(list[0]);
        int MonthInicial=Integer.parseInt(list[1]);
        int yearNow = Calendar.getInstance().get(Calendar.YEAR);
        int monthNow=Calendar.getInstance().get(Calendar.MONTH)+1;
       while(YearInicial!=yearNow || MonthInicial!=monthNow -1){           
           ret= ret + "\"" + YearInicial + "/" + MonthInicial + "\"" + ",";          
           if(MonthInicial==12){
           YearInicial++;           
           MonthInicial=1;
           ret= ret + YearInicial + "/" + MonthInicial + ",";          
           }           
           MonthInicial++;
       }
       ret=ret + "\"" + yearNow + "/" + MonthInicial + "\"";
       return ret;
    }*/
}
