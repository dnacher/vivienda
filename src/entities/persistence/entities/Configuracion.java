package entities.persistence.entities;
// Generated 21-ene-2017 0:24:10 by Hibernate Tools 4.3.1



/**
 * Configuracion generated by hbm2java
 */
public class Configuracion  implements java.io.Serializable {


     private String nombreTabla;
     private Integer index;

    public Configuracion() {
    }

	
    public Configuracion(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }
    public Configuracion(String nombreTabla, Integer index) {
       this.nombreTabla = nombreTabla;
       this.index = index;
    }
   
    public String getNombreTabla() {
        return this.nombreTabla;
    }
    
    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }
    public Integer getIndex() {
        return this.index;
    }
    
    public void setIndex(Integer index) {
        this.index = index;
    }




}

