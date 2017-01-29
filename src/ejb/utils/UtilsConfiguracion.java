package ejb.utils;

import entities.persistence.entities.Configuracion;
import java.util.List;
import viviendas.Viviendas;
/**
 *
 * @author Dani-Fla-Mathi
 */
public class UtilsConfiguracion {
    
    public static int TraeIdConfiguracion(String tabla){
        int index=0;
        for(Configuracion c: Viviendas.listaConfiguracion){
            if(c.getNombreTabla().equals(tabla)){
                index=c.getIndex();
                break;
            }
        }
        return index;
    }
    
    public static void actualizaIdConfiguracion(String tabla){
        for(int i=0;i<Viviendas.listaConfiguracion.size();i++){
            Configuracion c=Viviendas.listaConfiguracion.get(i);
            if(c.getNombreTabla().equals(tabla)){
                int ind=c.getIndex()+1;
                c.setIndex(ind);
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
}
