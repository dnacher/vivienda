package entities.persistence.entities;
// Generated 21-ene-2017 0:24:10 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Reglabonificacion generated by hbm2java
 */
public class Reglabonificacion  implements java.io.Serializable {


     private int idreglaBonificacion;
     private String descripcion;
     private Integer diaApagar;
     private boolean activo;
     private Set convenios = new HashSet(0);

    public Reglabonificacion() {
    }

	
    public Reglabonificacion(int idreglaBonificacion, boolean activo) {
        this.idreglaBonificacion = idreglaBonificacion;
        this.activo = activo;
    }
    public Reglabonificacion(int idreglaBonificacion, String descripcion, Integer diaApagar, boolean activo, Set convenios) {
       this.idreglaBonificacion = idreglaBonificacion;
       this.descripcion = descripcion;
       this.diaApagar = diaApagar;
       this.activo = activo;
       this.convenios = convenios;
    }
   
    public int getIdreglaBonificacion() {
        return this.idreglaBonificacion;
    }
    
    public void setIdreglaBonificacion(int idreglaBonificacion) {
        this.idreglaBonificacion = idreglaBonificacion;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getDiaApagar() {
        return this.diaApagar;
    }
    
    public void setDiaApagar(Integer diaApagar) {
        this.diaApagar = diaApagar;
    }
    public boolean isActivo() {
        return this.activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public Set getConvenios() {
        return this.convenios;
    }
    
    public void setConvenios(Set convenios) {
        this.convenios = convenios;
    }




}

