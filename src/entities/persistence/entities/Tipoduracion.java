package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Tipoduracion generated by hbm2java
 */
public class Tipoduracion  implements java.io.Serializable {


     private int idtipoDuracion;
     private String nombre;
     private boolean activo;
     private String descripcion;
     private Set trabajos = new HashSet(0);

    public Tipoduracion() {
    }

	
    public Tipoduracion(int idtipoDuracion, boolean activo) {
        this.idtipoDuracion = idtipoDuracion;
        this.activo = activo;
    }
    public Tipoduracion(int idtipoDuracion, String nombre, boolean activo, String descripcion, Set trabajos) {
       this.idtipoDuracion = idtipoDuracion;
       this.nombre = nombre;
       this.activo = activo;
       this.descripcion = descripcion;
       this.trabajos = trabajos;
    }
   
    public int getIdtipoDuracion() {
        return this.idtipoDuracion;
    }
    
    public void setIdtipoDuracion(int idtipoDuracion) {
        this.idtipoDuracion = idtipoDuracion;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public boolean isActivo() {
        return this.activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Set getTrabajos() {
        return this.trabajos;
    }
    
    public void setTrabajos(Set trabajos) {
        this.trabajos = trabajos;
    }

    @Override
    public String toString() {
        return nombre;
    }   

}


