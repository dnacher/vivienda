package entities.enums;

/**
 *
 * @author Dani-Fla-Mathi
 */
public enum MenuAdministracion {

    Inicio("Inicio", "Inicio", "Administracion"),
    Unidades("Unidades", "Unidades", "Administracion"),
    GastosComunes("GastosComunes", "Gastos Comunes", "Administracion"),
    NotaCreditoGC("NotaCreditoGC","Nota Credito GC", "Administracion"),
    OtrosGastos("OtrosGastos", "Otros Gastos", "Administracion"),
    Convenios("Convenios", "Convenios", "Administracion"),
    PagoConvenios("PagoConvenios", "Pago Convenios", "Administracion"),
    ReglaBonificacion("ReglaBonificacion", "Regla Bonificacion", "Administracion"),
    TipoBonificacion("TipoBonificacion", "Tipo de Bonificacion", "Administracion"),
    ValorGastosComunes("ValorGastosComunes", "Valor Gastos Comunes", "Administracion");

    private final String pagina;
	private final String menu;
	private final String carpeta;

	MenuAdministracion(String pagina, String menu, String carpeta) {
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
