package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Grupo generated by hbm2java
 */
public class Grupo  implements java.io.Serializable {


     private int idgrupo;
     private String nombre;
     private String descripcion;
     private boolean activo;
     private Set trabajos = new HashSet(0);

    public Grupo() {
    }

	
    public Grupo(int idgrupo, boolean activo) {
        this.idgrupo = idgrupo;
        this.activo = activo;
    }
    public Grupo(int idgrupo, String nombre, String descripcion, boolean activo, Set trabajos) {
       this.idgrupo = idgrupo;
       this.nombre = nombre;
       this.descripcion = descripcion;
       this.activo = activo;
       this.trabajos = trabajos;
    }
   
	// <editor-fold defaultstate="collapsed" desc="Getters and Setters"> 
	
    public int getIdgrupo() {
        return this.idgrupo;
    }
    
    public void setIdgrupo(int idgrupo) {
        this.idgrupo = idgrupo;
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
    public Set getTrabajos() {
        return this.trabajos;
    }
    
    public void setTrabajos(Set trabajos) {
        this.trabajos = trabajos;
    }
	
	// </editor-fold>

    @Override
    public String toString() {
        return nombre;
    }
	
}


