package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Unidad generated by hbm2java
 */
public class Unidad  implements java.io.Serializable {


     private int idUnidad;
     private String block;
     private Integer torre;
     private Integer puerta;
     private Integer habitaciones;
     private String nombre;
     private String apellido;
     private Integer telefono;
     private String mail;
     private Boolean propietarioInquilino;
     private Date fechaIngreso;
     private Boolean activo;
     private Set gastoscomuneses = new HashSet(0);
     private Set convenios = new HashSet(0);
     private Set otrosgastoses = new HashSet(0);
     private Set trabajos = new HashSet(0);

    public Unidad() {
    }

	
    public Unidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }
    public Unidad(int idUnidad, String block, Integer torre, Integer puerta, String nombre, String apellido, Integer telefono, String mail, Boolean propietarioInquilino, Date fechaIngreso, Boolean activo, Set gastoscomuneses, Set convenios, Set otrosgastoses, Set trabajos) {
       this.idUnidad = idUnidad;
       this.block = block;
       this.torre = torre;
       this.puerta = puerta;
       this.nombre = nombre;
       this.apellido = apellido;
       this.telefono = telefono;
       this.mail = mail;
       this.propietarioInquilino = propietarioInquilino;
       this.fechaIngreso = fechaIngreso;
       this.activo = activo;
       this.gastoscomuneses = gastoscomuneses;
       this.convenios = convenios;
       this.otrosgastoses = otrosgastoses;
       this.trabajos = trabajos;
    }
   
    public int getIdUnidad() {
        return this.idUnidad;
    }
    
    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }
    public String getBlock() {
        return this.block;
    }
    
    public void setBlock(String block) {
        this.block = block;
    }
    public Integer getTorre() {
        return this.torre;
    }
    
    public void setTorre(Integer torre) {
        this.torre = torre;
    }
    public Integer getPuerta() {
        return this.puerta;
    }
    
    public void setPuerta(Integer puerta) {
        this.puerta = puerta;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return this.apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public Integer getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
    public String getMail() {
        return this.mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
    public Boolean getPropietarioInquilino() {
        return this.propietarioInquilino;
    }
    
    public void setPropietarioInquilino(Boolean propietarioInquilino) {
        this.propietarioInquilino = propietarioInquilino;
    }
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public Boolean getActivo() {
        return this.activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public Set getGastoscomuneses() {
        return this.gastoscomuneses;
    }
    
    public void setGastoscomuneses(Set gastoscomuneses) {
        this.gastoscomuneses = gastoscomuneses;
    }
    public Set getConvenios() {
        return this.convenios;
    }
    
    public void setConvenios(Set convenios) {
        this.convenios = convenios;
    }
    public Set getOtrosgastoses() {
        return this.otrosgastoses;
    }
    
    public void setOtrosgastoses(Set otrosgastoses) {
        this.otrosgastoses = otrosgastoses;
    }
    public Set getTrabajos() {
        return this.trabajos;
    }
    
    public void setTrabajos(Set trabajos) {
        this.trabajos = trabajos;
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " " + block + torre + " " + puerta;
    }
}


