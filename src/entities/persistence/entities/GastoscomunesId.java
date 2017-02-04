package entities.persistence.entities;
// Generated 03-feb-2017 21:29:15 by Hibernate Tools 4.3.1



/**
 * GastoscomunesId generated by hbm2java
 */
public class GastoscomunesId  implements java.io.Serializable {


     private int idGastosComunes;
     private int unidadIdUnidad;

    public GastoscomunesId() {
    }

    public GastoscomunesId(int idGastosComunes, int unidadIdUnidad) {
       this.idGastosComunes = idGastosComunes;
       this.unidadIdUnidad = unidadIdUnidad;
    }
   
    public int getIdGastosComunes() {
        return this.idGastosComunes;
    }
    
    public void setIdGastosComunes(int idGastosComunes) {
        this.idGastosComunes = idGastosComunes;
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
		 if ( !(other instanceof GastoscomunesId) ) return false;
		 GastoscomunesId castOther = ( GastoscomunesId ) other; 
         
		 return (this.getIdGastosComunes()==castOther.getIdGastosComunes())
 && (this.getUnidadIdUnidad()==castOther.getUnidadIdUnidad());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdGastosComunes();
         result = 37 * result + this.getUnidadIdUnidad();
         return result;
   }   


}


