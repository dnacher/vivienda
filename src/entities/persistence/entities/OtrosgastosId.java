package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1



/**
 * OtrosgastosId generated by hbm2java
 */
public class OtrosgastosId  implements java.io.Serializable {


     private int idotrosGastos;
     private int unidadIdUnidad;

    public OtrosgastosId() {
    }

    public OtrosgastosId(int idotrosGastos, int unidadIdUnidad) {
       this.idotrosGastos = idotrosGastos;
       this.unidadIdUnidad = unidadIdUnidad;
    }
   
    public int getIdotrosGastos() {
        return this.idotrosGastos;
    }
    
    public void setIdotrosGastos(int idotrosGastos) {
        this.idotrosGastos = idotrosGastos;
    }
    public int getUnidadIdUnidad() {
        return this.unidadIdUnidad;
    }
    
    public void setUnidadIdUnidad(int unidadIdUnidad) {
        this.unidadIdUnidad = unidadIdUnidad;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof OtrosgastosId) ) return false;
		 OtrosgastosId castOther = ( OtrosgastosId ) other; 
         
		 return (this.getIdotrosGastos()==castOther.getIdotrosGastos())
 && (this.getUnidadIdUnidad()==castOther.getUnidadIdUnidad());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdotrosGastos();
         result = 37 * result + this.getUnidadIdUnidad();
         return result;
   }   


}


