package entities.persistence.entities;
// Generated 21-ene-2017 0:24:10 by Hibernate Tools 4.3.1



/**
 * CuotaconvenioId generated by hbm2java
 */
public class CuotaconvenioId  implements java.io.Serializable {


     private int idcuotaConvenio;
     private int convenioIdconvenio;
     private int convenioUnidadIdUnidad;

    public CuotaconvenioId() {
    }

    public CuotaconvenioId(int idcuotaConvenio, int convenioIdconvenio, int convenioUnidadIdUnidad) {
       this.idcuotaConvenio = idcuotaConvenio;
       this.convenioIdconvenio = convenioIdconvenio;
       this.convenioUnidadIdUnidad = convenioUnidadIdUnidad;
    }
   
    public int getIdcuotaConvenio() {
        return this.idcuotaConvenio;
    }
    
    public void setIdcuotaConvenio(int idcuotaConvenio) {
        this.idcuotaConvenio = idcuotaConvenio;
    }
    public int getConvenioIdconvenio() {
        return this.convenioIdconvenio;
    }
    
    public void setConvenioIdconvenio(int convenioIdconvenio) {
        this.convenioIdconvenio = convenioIdconvenio;
    }
    public int getConvenioUnidadIdUnidad() {
        return this.convenioUnidadIdUnidad;
    }
    
    public void setConvenioUnidadIdUnidad(int convenioUnidadIdUnidad) {
        this.convenioUnidadIdUnidad = convenioUnidadIdUnidad;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CuotaconvenioId) ) return false;
		 CuotaconvenioId castOther = ( CuotaconvenioId ) other; 
         
		 return (this.getIdcuotaConvenio()==castOther.getIdcuotaConvenio())
 && (this.getConvenioIdconvenio()==castOther.getConvenioIdconvenio())
 && (this.getConvenioUnidadIdUnidad()==castOther.getConvenioUnidadIdUnidad());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdcuotaConvenio();
         result = 37 * result + this.getConvenioIdconvenio();
         result = 37 * result + this.getConvenioUnidadIdUnidad();
         return result;
   }   


}


