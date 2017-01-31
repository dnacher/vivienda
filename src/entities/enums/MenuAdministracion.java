package entities.enums;

/**
 *
 * @author Dani-Fla-Mathi
 */
public enum MenuAdministracion {
    
                          Inicio("Inicio","Inicio"),
                      Unidades("Unidades","Unidades"),
            GastosComunes("GastosComunes","Gastos Comunes"),
                    Convenios("Convenios","Convenios"),
    ReglaBonificacion("ReglaBonificacion","Regla Bonificacion"),
                          Estado("Estado","Estado");
    
    private final String pagina;
    private final String menu;
    
    MenuAdministracion(String pagina, String menu){
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
