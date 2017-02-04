package UtilsGeneral;

import entities.hibernate.SessionConnection;
import entities.persistence.entities.Configuracion;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ConfiguracionControl {
    
    public static int traeUltimoId(String tabla){
        Configuracion c;
        int i=-1;
        //SessionFactory sf= NewHibernateUtil.getSessionFactory();
        //Session session;
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
    
    public static Date TraeFecha(LocalDate localDate){         
         Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
         return date;
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
