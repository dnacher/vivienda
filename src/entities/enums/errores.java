/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.enums;

/**
 *
 * @author Daniel
 */
public enum errores {
    
    VERIFICAR("Verificar", 0),
    FALTA_SECUENCIA("falta secuencia",1),
    FALTA_TIPO_MONEDA("falta tipo de moneda",2),
    FALTA_MONTO("falta monto",3),
    FALTA_FECHA("falta fecha",4),                                
    FALTA_CONCEPTO("falta concepto",5),
    FALTA_UNIDAD("falta unidad",6),
    FECHAFIN_MENOR_INICIO("La fecha fin no puede ser menor a la de comienzo",7),
    ERROR("error",8),
    WARNING("warning",9);
    
    private final String error;
    private final int errorNumero;
    
    errores(String error, int errorNumero){
         this.error = error;
         this.errorNumero=errorNumero;
    }
    
    public String getError(){
        return error;
    }
    
    public int getErrorNumero(){
        return errorNumero;
    }
}
