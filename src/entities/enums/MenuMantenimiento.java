package entities.enums;

/**
 *
 * @author Dani-Fla-Mathi
 */
public enum MenuMantenimiento {

    Inicio("Inicio", "Inicio"),
    Tecnico("Tecnico", "Tecnico"),
    CotizacionTrabajo("Cotizacion", "Cotizacion Trabajo"),
    Materiales("Material", "Materiales"),
    Grupo("Grupo", "Grupo"),
    TecnicoModificacion("BajaLicencia", "Baja/Licencia Tecnico"),
    Estado("Estado", "Estado"),
    ListaPrecios("ListaPrecios", "Lista de Precios");

    private final String pagina;
    private final String menu;

    MenuMantenimiento(String pagina, String menu) {
        this.pagina = pagina;
        this.menu = menu;
    }

    public String getPagina() {
        return pagina;
    }

    public String getMenu() {
        return menu;
    }
}
