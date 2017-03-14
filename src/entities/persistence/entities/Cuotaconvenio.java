package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1

import java.util.Date;




/**
 * Cuotaconvenio generated by hbm2java
 */
public class Cuotaconvenio  implements java.io.Serializable {


     private CuotaconvenioId id;
     private Convenio convenio;
     private Monto monto;
     private Integer numeroCuota;
     private String descripcion;
     private Integer pago;
     private Boolean tieneBonificacion;
     private Date fechaPago;
     
    public Cuotaconvenio() {
    }

	
    public Cuotaconvenio(CuotaconvenioId id, Convenio convenio, Monto monto) {
        this.id = id;
        this.convenio = convenio;
        this.monto = monto;
    }
    public Cuotaconvenio(CuotaconvenioId id, Convenio convenio, Monto monto, Integer numeroCuota, String descripcion, Integer pago, Boolean tieneBonificacion, Date fechaPago) {
       this.id = id;
       this.convenio = convenio;
       this.monto = monto;
       this.numeroCuota = numeroCuota;
       this.descripcion = descripcion;
       this.pago = pago;
       this.tieneBonificacion = tieneBonificacion;
       this.fechaPago=fechaPago;
    }
   
    public CuotaconvenioId getId() {
        return this.id;
    }
    
    public void setId(CuotaconvenioId id) {
        this.id = id;
    }
    public Convenio getConvenio() {
        return this.convenio;
    }
    
    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }
    public Monto getMonto() {
        return this.monto;
    }
    
    public void setMonto(Monto monto) {
        this.monto = monto;
    }
    public Integer getNumeroCuota() {
        return this.numeroCuota;
    }
    
    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getPago() {
        return this.pago;
    }
    
    public void setPago(Integer pago) {
        this.pago = pago;
    }
    public Boolean getTieneBonificacion() {
        return this.tieneBonificacion;
    }
    
    public void setTieneBonificacion(Boolean tieneBonificacion) {
        this.tieneBonificacion = tieneBonificacion;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }




}


