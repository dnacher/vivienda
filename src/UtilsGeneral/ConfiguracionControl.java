package UtilsGeneral;

import ejb.services.MontoBean;
import entities.hibernate.NewHibernateUtil;
import entities.hibernate.SessionConnection;
import entities.persistence.entities.Configuracion;
import entities.persistence.entities.Monto;
import entities.persistence.entities.Reglabonificacion;
import eu.hansolo.enzo.notification.Notification;
import exceptions.ServiceException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionImpl;

public class ConfiguracionControl {

    public static final Notification.Notifier notifier = Notification.Notifier.INSTANCE;

    public static int traeUltimoId(String tabla) {
        Configuracion c;
        int i = -1;
        SessionConnection sc = new SessionConnection();
        Session session = sc.useSession();
        Query query = session.createQuery("from Configuracion where NombreTabla=:name");
        query.setParameter("name", tabla);
        c = (Configuracion) query.uniqueResult();
        session.close();
        if (c != null) {
            i = c.getId();
        }
        return i;
    }

    public static void ActualizaId(String tabla) {
        int i = traeUltimoId(tabla);
        Configuracion c;
        if (i != -1) {
            SessionConnection sc = new SessionConnection();
            Query query = sc.useSession().createQuery("from Configuracion where NombreTabla=:name");
            query.setParameter("name", tabla);
            c = (Configuracion) query.uniqueResult();
            i++;
            c.setId(i);
            Transaction tx = sc.useSession().beginTransaction();
            sc.useSession().update(c);
            tx.commit();
            sc.closeSession();
        }
    }

    public static void ActualizaIdXId(String tabla, int id) {
        int i = traeUltimoId(tabla);
        Configuracion c;
        if (i != -1) {
            SessionConnection sc = new SessionConnection();
            Session session = sc.useSession();
            Query query = session.createQuery("from Configuracion where NombreTabla=:name");
            query.setParameter("name", tabla);
            c = (Configuracion) query.uniqueResult();
            c.setId(id);
            Transaction tx = session.beginTransaction();
            session.update(c);
            tx.commit();
            session.close();
        }
    }

    public static Date TraeFecha(LocalDate localDate) {
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public void generarReporte(String reporte) {
        try {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Connection con = ((SessionImpl) session).connection();
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/" + reporte + ".jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jr, null, con);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            jv.setTitle(reporte);
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void generarReporteConParametros(String reporte, HashMap parameters) {
        try {
            Session session = NewHibernateUtil.getSessionFactory().openSession();
            Connection con = ((SessionImpl) session).connection();
            JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResource("/reportes/" + reporte + ".jasper"));
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, con);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);
            jv.setTitle(reporte);
            session.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean esNumero(String s) {
        boolean esNumero;
        int num;
        try {
            num = Integer.valueOf(s);
            esNumero = true;
        } catch (Exception ex) {
            esNumero = false;
        }
        return esNumero;
    }

    public static int traePeriodo(int y, int m) {
        int periodo;
        if (m > 9) {
            periodo = Integer.valueOf(String.valueOf(y) + String.valueOf(m));
        } else {
            periodo = Integer.valueOf(String.valueOf(y) + "0" + String.valueOf(m));
        }
        return periodo;
    }

    public static int devuelvePeriodoActual() {
        int periodoActual;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        periodoActual = traePeriodo(year, month);
        return periodoActual;
    }

    public int devuelveCuotas(int mesInicial, int mesFinal, int anioInicial, int anioFinal) {
        int cuotas = 0;
        boolean flagMesInicial = true;
        for (int y = anioInicial; y <= anioFinal; y++) {
            if (anioFinal != y) {
                if (flagMesInicial) {
                    for (int m = mesInicial; m <= 12; m++) {
                        cuotas++;
                    }
                    flagMesInicial = false;
                } else {
                    for (int m = 1; m <= 12; m++) {
                        cuotas++;
                    }
                }
            } else if (flagMesInicial) {
                for (int m = mesInicial; m <= mesFinal; m++) {
                    cuotas++;
                }
                flagMesInicial = false;
            } else {
                for (int m = 1; m <= mesFinal; m++) {
                    cuotas++;
                }
            }
        }
        return cuotas;
    }

    /**
     * Esta funcion sirve para saber si a la fecha tiene o no bonificacion
     *
     * @param rb Reglabonificacion
     * @return boolean true or false
     */
    public static boolean tieneBonificacion(Reglabonificacion rb) {
        boolean tiene = false;
        int day = Calendar.getInstance().get(Calendar.DATE);
        if (day <= rb.getDiaApagar()) {
            tiene = true;
        }
        return tiene;
    }

    /**
     * Esta funcion devuelve un objeto Monto con moneda pesos
     *
     * @return boolean true or false
     * @throws exceptions.ServiceException
     */
    public Monto traeMontoPesos() throws ServiceException {
        MontoBean mb = new MontoBean();
        Monto monto = mb.traerMontoXId(1);
        return monto;
    }

    /**
     * Esta funcion devuelve el mes escrito en funcion del numero Ej. 1 devuelve
     * Enero.
     *
     * @param mes
     * @return String true or false
     */
    public static String devuelveMesEscrito(int mes) {
        String mesEscrito;
        switch (mes) {
            case 1:
                mesEscrito = "Enero";
                break;
            case 2:
                mesEscrito = "Febrero";
                break;
            case 3:
                mesEscrito = "Marzo";
                break;
            case 4:
                mesEscrito = "Abril";
                break;
            case 5:
                mesEscrito = "Mayo";
                break;
            case 6:
                mesEscrito = "Junio";
                break;
            case 7:
                mesEscrito = "Julio";
                break;
            case 8:
                mesEscrito = "Agosto";
                break;
            case 9:
                mesEscrito = "Setiembre";
                break;
            case 10:
                mesEscrito = "Octubre";
                break;
            case 11:
                mesEscrito = "Noviembre";
                break;
            default:
                mesEscrito = "Diciembre";
                break;
        }
        return mesEscrito;
    }

    /**
     * Esta funcion devuelve Verdadero si la Regla cumple el dia a pagar
     *
     * @param rb
     * @return Boolean true or false
     */
    public static boolean esBonificacion(Reglabonificacion rb) {
        boolean esBonificacion = false;
        if (rb != null) {
            int dia = Calendar.getInstance().get(Calendar.DATE);
            if (dia <= rb.getDiaApagar()) {
                esBonificacion = true;
            }
        }
        return esBonificacion;
    }

    /**
     * Esta funcion devuelve el valor de descuento de acuerdo al tipo de
     * bonificacion
     *
     * @param rb
     * @param subTotal
     * @return Boolean true or false
     */
    public static int calculaBonificacion(Reglabonificacion rb, int subTotal) {
        int total = 0;
        switch (rb.getTipoBonificacion()) {
            case 0: //Valor
            case 2: //Habitaciones
                total = rb.getValor();
                break;
            case 1: //Porcentaje
                total = subTotal * (rb.getValor() / 100);
                break;
        }
        return total;
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
