package entities.enums;

/**
 *
 * @author Daniel
 */
public enum MenuPrincipal {

    Inicio("Inicio", "Inicio"),
    Administracion("Administracion", "Administracion"),
    Mantenimiento("Mantenimiento", "Mantenimiento"),
    Reportes("Reportes", "Reportes"),
    Configuracion("Configuracion", "Configuracion");

    private final String pagina;
    private final String menu;

    MenuPrincipal(String pagina, String menu) {
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
