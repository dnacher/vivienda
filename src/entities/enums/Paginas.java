package entities.enums;

/**
 *
 * @author Daniel
 */
public enum Paginas {
    
                               Inicio("Inicio","            Inicio"),
                           Unidades("Unidades","          Unidades"),
                 GastosComunes("GastosComunes","    Gastos Comunes"),
                         Convenios("Convenios","         Convenios"),

                             Tecnico("Tecnico","           Tecnico"),
         CotizacionTrabajo("CotizacionTrabajo","Cotizacion Trabajo"),
                     BajaTecnico("BajaTecnico","      Baja Tecnico"),
                           Licencia("Licencia","          Licencia"),

                       Materiales("Materiales","        Materiales"),

                           Reportes("Reportes","          Reportes"),

                           Urgencia("Urgencia","          Urgencia"),

                 Configuracion("Configuracion","Configuracion     ");
    /*ListaPrecios("ListaPrecios","Lista Precios"),
    TipoDuracion("TipoDuracion","Tipo Duracion"),
    Usuarios("Usuarios","Usuarios"),
    TipoUsuario("TipoUsuario","Tipo Usuario"),
    CargarMasivas("CargarMasivas","Cargar Masivas"),
    Concepto("Concepto","Concepto"),
    Grupo("Grupo","Grupo");*/
    
    private final String pagina;
    private final String menu;
    
    Paginas(String pagina, String menu){
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
