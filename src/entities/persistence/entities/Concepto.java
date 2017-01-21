package entities.persistence.entities;
// Generated 21-ene-2017 0:24:10 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Concepto generated by hbm2java
 */
public class Concepto  implements java.io.Serializable {


     private int idconcepto;
     private String nombre;
     private String descripcion;
     private boolean activo;
     private Set otrosgastoses = new HashSet(0);

    public Concepto() {
    }

	
    public Concepto(int idconcepto, boolean activo) {
        this.idconcepto = idconcepto;
        this.activo = activo;
    }
    public Concepto(int idconcepto, String nombre, String descripcion, boolean activo, Set otrosgastoses) {
       this.idconcepto = idconcepto;
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.activo = activo;
       this.otrosgastoses = otrosgastoses;
    }
   
    public int getIdconcepto() {
        return this.idconcepto;
    }
    
    public void setIdconcepto(int idconcepto) {
        this.idconcepto = idconcepto;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public boolean isActivo() {
        return this.activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public Set getOtrosgastoses() {
        return this.otrosgastoses;
    }
    
    public void setOtrosgastoses(Set otrosgastoses) {
        this.otrosgastoses = otrosgastoses;
    }




}

