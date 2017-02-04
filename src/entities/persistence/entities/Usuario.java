package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private int idUsuario;
     private Tipousuario tipousuario;
     private String nombre;
     private String password;
     private boolean activo;
     private Set audits = new HashSet(0);

    public Usuario() {
    }

	
    public Usuario(int idUsuario, Tipousuario tipousuario, boolean activo) {
        this.idUsuario = idUsuario;
        this.tipousuario = tipousuario;
        this.activo = activo;
    }
    public Usuario(int idUsuario, Tipousuario tipousuario, String nombre, String password, boolean activo, Set audits) {
       this.idUsuario = idUsuario;
       this.tipousuario = tipousuario;
       this.nombre = nombre;
       this.password = password;
       this.activo = activo;
       this.audits = audits;
    }
   
    public int getIdUsuario() {
        return this.idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Tipousuario getTipousuario() {
        return this.tipousuario;
    }
    
    public void setTipousuario(Tipousuario tipousuario) {
        this.tipousuario = tipousuario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isActivo() {
        return this.activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public Set getAudits() {
        return this.audits;
    }
    
    public void setAudits(Set audits) {
        this.audits = audits;
    }




}


