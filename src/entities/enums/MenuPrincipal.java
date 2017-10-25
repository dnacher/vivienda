package entities.enums;

/**
 *
 * @author Daniel
 */
public enum MenuPrincipal {

    Inicio("Inicio", "Inicio","Principal"),
    Administracion("Administracion", "Administracion","Principal"),
    Mantenimiento("Mantenimiento", "Mantenimiento","Principal"),
    Reportes("Reportes", "Reportes","Principal"),
    Configuracion("Configuracion", "Configuracion","Principal");

    private final String pagina;
    private final String menu;
	private final String carpeta;

    MenuPrincipal(String pagina, String menu, String carpeta) {
        this.pagina = pagina;
        this.menu = menu;
		this.carpeta = carpeta;
    }

    public String getPagina() {
        return pagina;
    }

    public String getMenu() {
        return menu;
    }

	public String getCarpeta() {
		return carpeta;
	}	
	
}
