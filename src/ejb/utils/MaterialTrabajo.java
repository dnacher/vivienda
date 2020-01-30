package ejb.utils;

/**
 *
 * @author Daniel
 */
public class MaterialTrabajo {
    
	
    private String nombre;
    private String descripcion;
    private int cantidad;
    
    public MaterialTrabajo(){    
    }
    
    public MaterialTrabajo(String nombre, String descripcion, int cantidad){
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.descripcion=descripcion;
    }

	
	// <editor-fold defaultstate="collapsed" desc="Getters and Setters"> 
	// </editor-fold>
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }   
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
