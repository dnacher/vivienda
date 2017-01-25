package entities.persistence.entities;
// Generated 24-ene-2017 23:29:40 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Estado generated by hbm2java
 */
public class Estado  implements java.io.Serializable {


     private int idestado;
     private String nombre;
     private String descripcion;
     private Integer orden;
     private boolean activo;
     private Set trabajos = new HashSet(0);

    public Estado() {
    }

	
    public Estado(int idestado, boolean activo) {
        this.idestado = idestado;
        this.activo = activo;
    }
    public Estado(int idestado, String nombre, String descripcion, Integer orden, boolean activo, Set trabajos) {
       this.idestado = idestado;
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.orden = orden;
       this.activo = activo;
       this.trabajos = trabajos;
    }
   
    public int getIdestado() {
        return this.idestado;
    }
    
    public void setIdestado(int idestado) {
        this.idestado = idestado;
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
    public Integer getOrden() {
        return this.orden;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    public boolean isActivo() {
        return this.activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public Set getTrabajos() {
        return this.trabajos;
    }
    
    public void setTrabajos(Set trabajos) {
        this.trabajos = trabajos;
    }




}


