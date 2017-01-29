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
         ReglaBonificacion("ReglaBonificacion","Regla Bonificacion"),
                       Materiales("Materiales","        Materiales"),
                               Estado("Estado","            Estado"),
                           Reportes("Reportes","          Reportes"),
                                 Grupo("Grupo","             Grupo"),
                           Urgencia("Urgencia","          Urgencia"),
                           Concepto("Concepto","          Concepto"),
                            Usuarios("Usuario","          Usuarios"),
                     TipoUsuario("TipoUsuario","      Tipo Usuario"),
                   TipoDuracion("TipoDuracion","     Tipo Duracion"),
                 Configuracion("Configuracion","     Configuracion");
    /*ListaPrecios("ListaPrecios","Lista Precios"),
    
    Usuarios("Usuarios","Usuarios"),
    
    CargarMasivas("CargarMasivas","Cargar Masivas"),
    
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
