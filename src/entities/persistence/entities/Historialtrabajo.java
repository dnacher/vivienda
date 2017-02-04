package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Historialtrabajo generated by hbm2java
 */
public class Historialtrabajo  implements java.io.Serializable {


     private HistorialtrabajoId id;
     private Tecnico tecnico;
     private Trabajo trabajo;
     private String descripcion;
     private Date fecha;

    public Historialtrabajo() {
    }

	
    public Historialtrabajo(HistorialtrabajoId id, Tecnico tecnico, Trabajo trabajo) {
        this.id = id;
        this.tecnico = tecnico;
        this.trabajo = trabajo;
    }
    public Historialtrabajo(HistorialtrabajoId id, Tecnico tecnico, Trabajo trabajo, String descripcion, Date fecha) {
       this.id = id;
       this.tecnico = tecnico;
       this.trabajo = trabajo;
       this.descripcion = descripcion;
       this.fecha = fecha;
    }
   
    public HistorialtrabajoId getId() {
        return this.id;
    }
    
    public void setId(HistorialtrabajoId id) {
        this.id = id;
    }
    public Tecnico getTecnico() {
        return this.tecnico;
    }
    
    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }
    public Trabajo getTrabajo() {
        return this.trabajo;
    }
    
    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }




}


