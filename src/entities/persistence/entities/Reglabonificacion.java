package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Reglabonificacion generated by hbm2java
 */
public class Reglabonificacion  implements java.io.Serializable {


     private int idreglaBonificacion;
     private String descripcion;
     private Integer diaApagar;
     private Integer tipoBonificacion;
     private Integer valor;
     private Monto monto;  
     private Integer habitaciones;
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
   
	// <editor-fold defaultstate="collapsed" desc="Getters and Setters"> 
	
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

    public Integer getTipoBonificacion() {
        return tipoBonificacion;
    }

    public void setTipoBonificacion(Integer tipoBonificacion) {
        this.tipoBonificacion = tipoBonificacion;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Monto getMonto() {
        return monto;
    }

    public void setMonto(Monto monto) {
        this.monto = monto;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }   
	
	// </editor-fold>

    @Override
    public String toString() {
        return descripcion;
    }

}


