package entities.enums;

/**
 *
 * @author Dani-Fla-Mathi
 */
public enum MenuMantenimiento {
    
                          Inicio("Inicio","Inicio"),
                        Tecnico("Tecnico","Tecnico"),
    CotizacionTrabajo("CotizacionTrabajo","Cotizacion Trabajo"),
                  Materiales("Material","Materiales"),
                            Grupo("Grupo","Grupo"),
              ListaPrecios("ListaPrecios","Lista Precios"),
                BajaTecnico("BajaTecnico","Baja Tecnico"),
                      Licencia("Licencia","Licencia");
        
                   
    
    private final String pagina;
    private final String menu;
    
    MenuMantenimiento(String pagina, String menu){
         this.pagina = pagina;
        this.menu = menu;
    }
    
    public String getPagina(){
        return pagina;
    }
    
    public String getMenu(){
        return menu;
    }
}
