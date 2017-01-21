package entities.persistence.entities;
// Generated 21-ene-2017 0:24:10 by Hibernate Tools 4.3.1



/**
 * GastoscomunesId generated by hbm2java
 */
public class GastoscomunesId  implements java.io.Serializable {


     private int idtable1;
     private int unidadIdUnidad;

    public GastoscomunesId() {
    }

    public GastoscomunesId(int idtable1, int unidadIdUnidad) {
       this.idtable1 = idtable1;
       this.unidadIdUnidad = unidadIdUnidad;
    }
   
    public int getIdtable1() {
        return this.idtable1;
    }
    
    public void setIdtable1(int idtable1) {
        this.idtable1 = idtable1;
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
         
		 return (this.getIdtable1()==castOther.getIdtable1())
 && (this.getUnidadIdUnidad()==castOther.getUnidadIdUnidad());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdtable1();
         result = 37 * result + this.getUnidadIdUnidad();
         return result;
   }   


}

