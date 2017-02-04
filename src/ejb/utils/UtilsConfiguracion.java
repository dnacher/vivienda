package ejb.utils;

import entities.persistence.entities.Configuracion;
import java.util.Calendar;
import java.util.Date;
import viviendas.Viviendas;
/**
 *
 * @author Dani-Fla-Mathi
 */
public class UtilsConfiguracion {
    
    public static int TraeIdConfiguracion(String tabla){
        int id=0;
        for(Configuracion c: Viviendas.listaConfiguracion){
            if(c.getNombreTabla().equals(tabla)){
                id=c.getId();
                break;
            }
        }
        return id;
    }
    
    public static void actualizaIdConfiguracion(String tabla){
        for(int i=0;i<Viviendas.listaConfiguracion.size();i++){
            Configuracion c=Viviendas.listaConfiguracion.get(i);
            if(c.getNombreTabla().equals(tabla)){
                int ind=c.getId()+1;
                c.setId(ind);
                Viviendas.listaConfiguracion.set(i, c);
                break;
            }
        }
    }
    
    public static boolean esNumero(String s){
        boolean esNumero=false;
        int num;
        try{
            num=Integer.valueOf(s);
            esNumero=true;
        }
        catch(Exception ex){
            esNumero=false;
        }
        return esNumero;
    }
    
    public static int traePeriodo(int y, int m){ 
        int periodo=0; 
        if(m>9){
            periodo=Integer.valueOf(String.valueOf(y) + String.valueOf(m)); 
        } else{ 
            periodo=Integer.valueOf(String.valueOf(y) + "0" + String.valueOf(m)); 
        } 
        return periodo; 
    }
    
    public static int devuelvePeriodoActual(){
        int periodoActual=0;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month=Calendar.getInstance().get(Calendar.MONTH)+1;        
        periodoActual=traePeriodo(year, month);
        return periodoActual;
    }
}
