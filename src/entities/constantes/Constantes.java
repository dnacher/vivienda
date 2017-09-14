package entities.constantes;

import java.util.Arrays;
import java.util.List;

/*
 * @author Dani-Fla-Mathi
 */
public class Constantes {
    
    /*
     *                          CONEXION BASE DATOS
     */
    
    public static final String HIBERNATE="hibernate.cfg.xml";
    
    /*
     *                          GENERAL
     */
    
    public static final String VIVIENDA="Vivienda";
    
    /*
     *                          RUTAS
     */
    public static final String PAGINA_ROOT="/web/fragmentos/";
    public static final String IMAGEN_ROOT="/web/images/"; 
    public static final String CSS_ROOT="/web/css/";
        
   /*
    *                           MENUS
    */
    public static final String EXTENSION_FXML=".fxml";
    public static final String EXTENSION_PNG=".png";
    public static final String MENU_ADMINISTRACION="MenuAdministracion";
    public static final String MENU_MANTENIMIENTO="MenuMantenimiento";
    public static final String MENU_CONFIGURACION="MenuConfiguracion";
    public static final String MENU_PRINCIPAL="MenuPrincipal";
    
    public static final String PAGINA_LOGIN=Constantes.PAGINA_ROOT + "login2" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_URGENCIA=Constantes.PAGINA_ROOT + "urgencia" + Constantes.EXTENSION_FXML;
       
   /*
    *                          PAGINAS
    */    
    
    public static final String PAGINA_COTIZACION=Constantes.PAGINA_ROOT + "Cotizacion" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_LISTA_PRECIOS=Constantes.PAGINA_ROOT + "listaPrecios" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_MATERIALES=Constantes.PAGINA_ROOT + "materiales" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_REPORTES_MANTENIMIENTO=Constantes.PAGINA_ROOT + "reportesMantenimiento" + Constantes.EXTENSION_FXML;    
    public static final String PAGINA_TRABAJO=Constantes.PAGINA_ROOT + "trabajo" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_UNIDADES=Constantes.PAGINA_ROOT + "unidades" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_USUARIOS=Constantes.PAGINA_ROOT + "usuarios" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_TIPO_USUARIOS=Constantes.PAGINA_ROOT + "tipoUsuarios" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_CARGAS_MASIVAS=Constantes.PAGINA_ROOT + "cargasMasivas" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_CONVENIOS=Constantes.PAGINA_ROOT + "convenios" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_COTIZACIONES=Constantes.PAGINA_ROOT + "cotizaciones" + Constantes.EXTENSION_FXML;    
    public static final String PAGINA_GASTOS_COMUNES=Constantes.PAGINA_ROOT + "gastosComunes" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_FORMULARIO_GASTOS_COMUNES=Constantes.PAGINA_ROOT + "formularioGastosComunes" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_REPORTES_ADMINISTRACION=Constantes.PAGINA_ROOT + "reportesAdministracion" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_CONFIGURACION_USUARIOS=Constantes.PAGINA_ROOT + "configuracionUsuarios" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_CONCEPTO=Constantes.PAGINA_ROOT + "concepto" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_CUOTA_CONVENIO=Constantes.PAGINA_ROOT + "cuotaConvenio" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_ESTADO=Constantes.PAGINA_ROOT + "estado" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_GRUPO=Constantes.PAGINA_ROOT + "grupo" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_MONTO=Constantes.PAGINA_ROOT + "monto" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_OTROS_GASTOS=Constantes.PAGINA_ROOT + "otrosGastos" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_REGLAS_BONIFICACION=Constantes.PAGINA_ROOT + "reglasBonificacion" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_TECNICO=Constantes.PAGINA_ROOT + "tecnico" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_TIPO_BONIFICACION=Constantes.PAGINA_ROOT + "tipoBonificacion" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_TIPO_DURACION=Constantes.PAGINA_ROOT + "tipoDuracion" + Constantes.EXTENSION_FXML;    
    public static final String PAGINA_FORM_MENU=Constantes.PAGINA_ROOT + "formMenu" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_MAIN=Constantes.PAGINA_ROOT + "main" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_CUSTOMER=Constantes.PAGINA_ROOT + "customer" + Constantes.EXTENSION_FXML;
    public static final String PAGINA_DIALOG=Constantes.PAGINA_ROOT + "dialog" + Constantes.EXTENSION_FXML;
    
    /*
     *                          IMAGENES
     */
    public static final String STAR0=IMAGEN_ROOT + "star0" + EXTENSION_PNG;
    public static final String STAR0M=IMAGEN_ROOT + "star0m" + EXTENSION_PNG;
    public static final String STAR1=IMAGEN_ROOT + "star1" + EXTENSION_PNG;
    public static final String STAR1M=IMAGEN_ROOT + "star1m" + EXTENSION_PNG;
    public static final String STAR2=IMAGEN_ROOT + "star2" + EXTENSION_PNG;
    public static final String STAR2M=IMAGEN_ROOT + "star2m" + EXTENSION_PNG;
    public static final String STAR3=IMAGEN_ROOT + "star3" + EXTENSION_PNG;
    public static final String STAR3M=IMAGEN_ROOT + "star3m" + EXTENSION_PNG;
    public static final String STAR4=IMAGEN_ROOT + "star4" + EXTENSION_PNG;
    public static final String STAR4M=IMAGEN_ROOT + "star4m" + EXTENSION_PNG;
    public static final String STAR5=IMAGEN_ROOT + "star5" + EXTENSION_PNG;
    
    public static final String STEP1=IMAGEN_ROOT + "step1" + EXTENSION_PNG;
    public static final String STEP2=IMAGEN_ROOT + "step2" + EXTENSION_PNG;
    public static final String STEP3=IMAGEN_ROOT + "step3" + EXTENSION_PNG;
    
    /*
     *                             CSS
     */
    public static final String myDialogs=Constantes.CSS_ROOT + "myDialogs.css";
    
    /*
     *                             LISTAS
     */
    public static final List<String> LISTA_LICENCIA_BAJA=Arrays.asList("Licencia","Baja");
    public static final List<String> LISTA_BLOCKS=Arrays.asList("A","B","C","D","E");
    public static final List<String> LISTA_TIPO_CONVENIOS=Arrays.asList("Limite Cuotas",
                                                                        "Limite Fecha",
                                                                        "Limite Monto");
    public static final List<String> LISTA_REPORTES=Arrays.asList("Convenios Activos",
                                                                  "Gastos Comunes",
                                                                  "Historico Convenios", 
                                                                  "Otros Gastos no pagos", 
                                                                  "Otros gastos pagos",
                                                                  "Resumen Deudas", 
                                                                  "Todos los Convenios");
    public static final List<Integer> LISTA_TORRES=Arrays.asList(1,2,3,4,5,6);
    public static final List<String> LISTA_TIPO_ARCHIVO_IMPORTAR=Arrays.asList("Unidad");
    
    /*
     *                             OTROS
     */
    
    public static final String DECIMAL_FORMAT="####0.00";
    public static final String DECIMAL_FORMAT_SHORT="#.#";
    public static final String PATH="C:/desarrollo/test.txt";
    public static final String SOURCE_ENCUESTA_TEST="https://docs.google.com/forms/d/e/1FAIpQLSesUlcnT3x7pJUODIGWsis1czpXuUVOnMQnuT6DrI6zEOm_EQ/viewform?embedded=true";
    public static final String EXCEL="Libro Excel 97-2003";
    public static final String EXTENSION_EXCEL="*.xls";
    public static final String TRABAJANDO_ESPERE="trabajando, espere por favor";
    public static final String PRONTO_CARGAR="Pronto para cargar";
    public static final String CARGANDO="Cargando...";    
    
    /*
     *                             VERSION
     */
    public static final String VER="5.2.4";
    public static final String VERSION="Versión: " + VER;    
    
}
