package entities.persistence.entities;
// Generated 26-oct-2017 20:25:54 by Hibernate Tools 4.3.1



/**
 * Permisosusuario generated by hbm2java
 */
public class Permisosusuario  implements java.io.Serializable {


     private PermisosusuarioId id;
     private Tipousuario tipousuario;
     private int permiso;

    public Permisosusuario() {
    }

    public Permisosusuario(PermisosusuarioId id, Tipousuario tipousuario, int permiso) {
       this.id = id;
       this.tipousuario = tipousuario;
       this.permiso = permiso;
    }
   
    public PermisosusuarioId getId() {
        return this.id;
    }
    
    public void setId(PermisosusuarioId id) {
        this.id = id;
    }
    public Tipousuario getTipousuario() {
        return this.tipousuario;
    }
    
    public void setTipousuario(Tipousuario tipousuario) {
        this.tipousuario = tipousuario;
    }
    public int getPermiso() {
        return this.permiso;
    }
    
    public void setPermiso(int permiso) {
        this.permiso = permiso;
    }

    public String getPagina(){
        return this.id.getPagina();
    }


}


