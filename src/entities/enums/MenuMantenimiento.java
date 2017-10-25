package entities.enums;

/**
 *
 * @author Dani-Fla-Mathi
 */
public enum MenuMantenimiento {

	Inicio("Inicio", "Inicio", "Mantenimiento"),
	Tecnico("Tecnico", "Tecnico", "Mantenimiento"),
	CotizacionTrabajo("Cotizacion", "Cotizacion Trabajo", "Mantenimiento"),
	Materiales("Material", "Materiales", "Mantenimiento"),
	Grupo("Grupo", "Grupo", "Mantenimiento"),
	TecnicoModificacion("BajaLicencia", "Baja/Licencia Tecnico", "Mantenimiento"),
	Estado("Estado", "Estado", "Mantenimiento"),
	ListaPrecios("ListaPrecios", "Lista de Precios", "Mantenimiento");

	private final String pagina;
	private final String menu;
	private final String carpeta;

	MenuMantenimiento(String pagina, String menu, String carpeta) {
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
