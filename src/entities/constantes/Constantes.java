package entities.constantes;

import java.util.Arrays;
import java.util.List;

/*
 * @author Dani-Fla-Mathi
 */
public class Constantes {

	/*
     *                   CONEXION BASE DATOS
	 */
	public static final String HIBERNATE = "hibernate.cfg.xml";

	/*
     *                          GENERAL
	 */
	public static final String VIVIENDA = "Vivienda";

	/*
     *                          RUTAS
	 */
	public static final String PAGINA_ROOT = "/web/fragmentos/";
	public static final String IMAGEN_ROOT = "/web/images/";
	public static final String CSS_ROOT = "/web/css/";
	public static final String LOGO = "/web/images/Vivienda-Icon.png";

	/*
     *                          MENUS
	 */
	public static final String EXTENSION_FXML = ".fxml";
	public static final String EXTENSION_PNG = ".png";
	public static final String MENU_ADMINISTRACION = "MenuAdministracion";
	public static final String MENU_MANTENIMIENTO = "MenuMantenimiento";
	public static final String MENU_CONFIGURACION = "MenuConfiguracion";
	public static final String MENU_PRINCIPAL = "MenuPrincipal";

	public static final String PAGINA_LOGIN = Constantes.PAGINA_ROOT + "login2" + Constantes.EXTENSION_FXML;
	public static final String PAGINA_URGENCIA = Constantes.PAGINA_ROOT + "urgencia" + Constantes.EXTENSION_FXML;

	/*
     *                         PAGINAS
	 */
	public static final String PAGINA_FORM_MENU = Constantes.PAGINA_ROOT + "formMenu" + Constantes.EXTENSION_FXML;
	public static final String PAGINA_MAIN = Constantes.PAGINA_ROOT + "main" + Constantes.EXTENSION_FXML;
	public static final String PAGINA_DIALOG = Constantes.PAGINA_ROOT + "dialog" + Constantes.EXTENSION_FXML;

	/*
     *                         IMAGENES
	 */
	public static final String STAR0 = IMAGEN_ROOT + "star0" + EXTENSION_PNG;
	public static final String STAR0M = IMAGEN_ROOT + "star0m" + EXTENSION_PNG;
	public static final String STAR1 = IMAGEN_ROOT + "star1" + EXTENSION_PNG;
	public static final String STAR1M = IMAGEN_ROOT + "star1m" + EXTENSION_PNG;
	public static final String STAR2 = IMAGEN_ROOT + "star2" + EXTENSION_PNG;
	public static final String STAR2M = IMAGEN_ROOT + "star2m" + EXTENSION_PNG;
	public static final String STAR3 = IMAGEN_ROOT + "star3" + EXTENSION_PNG;
	public static final String STAR3M = IMAGEN_ROOT + "star3m" + EXTENSION_PNG;
	public static final String STAR4 = IMAGEN_ROOT + "star4" + EXTENSION_PNG;
	public static final String STAR4M = IMAGEN_ROOT + "star4m" + EXTENSION_PNG;
	public static final String STAR5 = IMAGEN_ROOT + "star5" + EXTENSION_PNG;

	public static final String STEP1 = IMAGEN_ROOT + "step1" + EXTENSION_PNG;
	public static final String STEP2 = IMAGEN_ROOT + "step2" + EXTENSION_PNG;
	public static final String STEP3 = IMAGEN_ROOT + "step3" + EXTENSION_PNG;

	/*
     *                             CSS
	 */
	public static final String myDialogs = Constantes.CSS_ROOT + "myDialogs.css";

	/*
     *                             LISTAS
	 */
	public static final List<String> LISTA_LICENCIA_BAJA = Arrays.asList("Licencia", "Baja");
	public static final List<String> LISTA_BLOCKS = Arrays.asList("A", "B", "C", "D", "E");
	public static final List<String> LISTA_TIPO_CONVENIOS = Arrays.asList("Limite Cuotas",
		"Limite Fecha",
		"Limite Monto");
	public static final List<String> LISTA_REPORTES = Arrays.asList("Convenios Activos",
		"Gastos Comunes",
		"Historico Convenios",
		"Otros Gastos no pagos",
		"Otros gastos pagos",
		"Resumen Deudas",
		"Todos los Convenios");
	public static final List<Integer> LISTA_TORRES = Arrays.asList(1, 2, 3, 4, 5, 6);
	public static final List<String> LISTA_TIPO_ARCHIVO_IMPORTAR = Arrays.asList("Unidad");

	/*
     *                             OTROS
	 */
	public static final String DECIMAL_FORMAT = "####0.00";
	public static final String DECIMAL_FORMAT_SHORT = "#.#";
	public static final String PATH = "C:/Users/danie/test.txt";
	public static final String SOURCE_ENCUESTA_TEST = "https://docs.google.com/forms/d/e/1FAIpQLSesUlcnT3x7pJUODIGWsis1czpXuUVOnMQnuT6DrI6zEOm_EQ/viewform?embedded=true";
	public static final String EXCEL = "Libro Excel 97-2003";
	public static final String EXTENSION_EXCEL = "*.xls";
	public static final String TRABAJANDO_ESPERE = "trabajando, espere por favor";
	public static final String ELEGIR_ARCHIVO = "Debe elegir un archivo";
	public static final String PRONTO_CARGAR = "Pronto para cargar";
	public static final String CARGANDO = "Cargando...";

	/*
     *                             VERSION
	 */
	public static final String VER = "5.2.4";
	public static final String VER_ERORES = "524-";
	public static final String VERSION = "Versión: " + VER;

	/*
     *                             VALORES GASTOS COMUNES
	 */
	public static final int NO_PAGO = 1;
	public static final int PAGO = 2;
	public static final int CONVENIO = 3;
	public static final int NOTA_CREDITO = 4;

	public static final int COMPARA_EQUAL = 0;
	public static final int COMPARA_MORE_THAN = 1;
	public static final int COMPARA_LESS_THAN = 2;
	public static final int COMPARA_MORE_EQUAL = 3;
	public static final int COMPARA_LESS_EQUAL = 4;

	public static final boolean NOT_IN = false;
	public static final boolean IN = true;

	public static final boolean CON_EDIFICIO = true;
	public static final boolean SIN_EDIFICIO = false;

	public static final Integer PERMISO_ADMIN = 7;
	public static final Integer PERMISO_OPERADOR = 3;
	public static final Integer PERMISO_VER = 1;

}
