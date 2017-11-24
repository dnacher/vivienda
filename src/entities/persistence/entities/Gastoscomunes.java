package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Gastoscomunes generated by hbm2java
 */
public class Gastoscomunes  implements java.io.Serializable {


     private GastoscomunesId id;
     private Monto monto;
     private Unidad unidad;
     private Integer monto_1;
     private Boolean isBonificacion;
     private Integer descuento;
     private Integer estado;
     private Date fechaPago;
     private Integer periodo;
     private String descripcion;
     private Boolean activo;

    public Gastoscomunes() {
    }

	
    public Gastoscomunes(GastoscomunesId id, Monto monto, Unidad unidad, Date fechaPago) {
        this.id = id;
        this.monto = monto;
        this.unidad = unidad;
        this.fechaPago = fechaPago;
    }
    public Gastoscomunes(GastoscomunesId id, Monto monto, Unidad unidad, Integer monto_1, Boolean isBonificacion, Integer estado, Date fechaPago, Integer periodo,String descripcion, Boolean activo) {
       this.id = id;
       this.monto = monto;
       this.unidad = unidad;
       this.monto_1 = monto_1;
       this.isBonificacion = isBonificacion;
       this.estado = estado;
       this.fechaPago = fechaPago;
       this.periodo = periodo;
       this.descripcion=descripcion;
       this.activo = activo;
    }
   
	// <editor-fold defaultstate="collapsed" desc="Getters and Setters"> 
	
    public GastoscomunesId getId() {
        return this.id;
    }
    
    public void setId(GastoscomunesId id) {
        this.id = id;
    }
    public Monto getMonto() {
        return this.monto;
    }
    
    public void setMonto(Monto monto) {
        this.monto = monto;
    }
    public Unidad getUnidad() {
        return this.unidad;
    }
    
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }
    public Integer getMonto_1() {
        return this.monto_1;
    }
    
    public void setMonto_1(Integer monto_1) {
        this.monto_1 = monto_1;
    }
    public Boolean getIsBonificacion() {
        return this.isBonificacion;
    }
    
    public void setIsBonificacion(Boolean isBonificacion) {
        this.isBonificacion = isBonificacion;
    }
    public Integer getEstado() {
        return this.estado;
    }
    
    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    public Date getFechaPago() {
        return this.fechaPago;
    }
    
    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }
    public Integer getPeriodo() {
        return this.periodo;
    }
    
    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }
    public Boolean getActivo() {
        return this.activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

	// </editor-fold>

}


