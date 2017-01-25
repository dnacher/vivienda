package entities.persistence.entities;
// Generated 24-ene-2017 23:29:40 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Listaprecios generated by hbm2java
 */
public class Listaprecios  implements java.io.Serializable {


     private ListapreciosId id;
     private Material material;
     private Integer precio;
     private Integer cantidad;
     private Date fecha;
     private Boolean activo;

    public Listaprecios() {
    }

	
    public Listaprecios(ListapreciosId id, Material material) {
        this.id = id;
        this.material = material;
    }
    public Listaprecios(ListapreciosId id, Material material, Integer precio, Integer cantidad, Date fecha, Boolean activo) {
       this.id = id;
       this.material = material;
       this.precio = precio;
       this.cantidad = cantidad;
       this.fecha = fecha;
       this.activo = activo;
    }
   
    public ListapreciosId getId() {
        return this.id;
    }
    
    public void setId(ListapreciosId id) {
        this.id = id;
    }
    public Material getMaterial() {
        return this.material;
    }
    
    public void setMaterial(Material material) {
        this.material = material;
    }
    public Integer getPrecio() {
        return this.precio;
    }
    
    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
    public Integer getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Boolean getActivo() {
        return this.activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }




}


