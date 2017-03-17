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
    
    FALTA_SECUENCIA("falta secuencia",1),
    FALTA_TIPO_MONEDA("falta tipo de moneda",2),
    FALTA_MONTO("falta monto",3),
    FALTA_FECHA("falta fecha",4),                                
    FALTA_CONCEPTO("falta concepto",5),
    FALTA_UNIDAD("falta unidad",6);
    
    private String error;
    private int errorNumero;
    
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
