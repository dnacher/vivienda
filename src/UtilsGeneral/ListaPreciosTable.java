
package UtilsGeneral;

import ejb.services.ListaPreciosBean;
import entities.persistence.entities.Listaprecios;
import exceptions.ServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaPreciosTable {
    private int id;
    private String material;
    private Integer precio;
    private Integer cantidad;
    private Date fecha;
    private Boolean activo;
     
    public ListaPreciosTable(int id,String material,Integer precio,Integer cantidad,Date fecha,Boolean activo){
         this.id=id;
         this.material=material;
         this.precio=precio;
         this.cantidad=cantidad;
         this.fecha=fecha;
         this.activo=activo;       
    }
     
     public ListaPreciosTable(){
     
     }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
     
     public static List<ListaPreciosTable> devuelveTodosPrecios(){
         List<ListaPreciosTable> listaFinal=new ArrayList<>();
        try {            
            ListaPreciosBean lpb=new ListaPreciosBean();
            List<Listaprecios> lista=lpb.traerTodos();
            for(Listaprecios l: lista){
                ListaPreciosTable lpt=new ListaPreciosTable();
                lpt.setId(l.getId().getIdlistaPrecios());
                lpt.setActivo(l.getActivo());
                lpt.setCantidad(l.getCantidad());
                lpt.setFecha(l.getFecha());
                lpt.setMaterial(l.getMaterial().getNombre());
                lpt.setPrecio(l.getPrecio());
                listaFinal.add(lpt);
            }
        } catch (ServiceException ex) {
            Logger.getLogger(ListaPreciosTable.class.getName()).log(Level.SEVERE, null, ex);
        }
         return listaFinal;     
     }
     
}
