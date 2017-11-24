package entities.enums;

import entities.constantes.Constantes;

/**
 *
 * @author Daniel
 */
public enum errores {

	//Login											000 - 020
	ERROR_LOGIN_GENERAL("Hubo un error General", 0),
	ERROR_LOGIN_FALTAN_DATOS_NOMBRE("Falta Ingresar el nombre de usuario", 1),
	ERROR_LOGIN_FALTAN_DATOS_PASS("Falta Ingresar la contraseña", 2),
	ERROR_LOGIN_DATOS_INCORRECTOS("El nombre o contraseña son incorrectos", 3),
	//Administracion	
	//Administracion Unidades						021 - 040
	ERROR_ADMINISTRACION_UNIDADES_GENERAL("Hubo un error General", 21),
	ERROR_ADMINISTRACION_UNIDADES_FALTAN_DATOS("Falta Ingresar Datos", 22),
	ERROR_ADMINISTRACION_UNIDADES_OBJETO_YA_EXISTE("Ya existe esta Unidad", 23),
	//Administracion Gastos Comunes					

	//Administracion Nota Credito Gastos Comunes	041 - 060
	//Administracion Otros Gastos					061 - 080
	//Administracion Convenios						081 - 100
	//Administracion Pago Convenios					101 - 120
	//Administracion Regla Bonificaciones			121 - 140
	//Administracion Tipo Bonificacion				141 - 160
	//Administracion Valor Gastos Comunes			161 - 180

	//Mantenimiento
	//Mantenimiento Tecnico							200 - 220
	//Mantenimiento Cotizacion						221 - 240
	//Mantenimiento Materiales						241 - 260
	//Mantenimiento Grupo							261 - 280
	//Mantenimiento Tecnico Modificacion			281 - 300
	//Mantenimiento Estado							301 - 320
	//Mantenimiento Lista Precios					321 - 340

	//Configuracion
	//Configuracion Grupo							200 - 220
	//Configuracion Urgencia						200 - 220
	//Configuracion Concepto						200 - 220
	//Configuracion Usuarios						200 - 220
	//Configuracion Tipo Usuasrios					200 - 220
	//Configuracion Tipo Duracion					200 - 220
	//Configuracion Importar Excel					200 - 220
	//Configuracion Seguridad						200 - 220

	VERIFICAR("Verificar", 0),
	FALTA_SECUENCIA("falta secuencia", 1),
	FALTA_TIPO_MONEDA("falta tipo de moneda", 2),
	FALTA_MONTO("falta monto", 3),
	FALTA_FECHA("falta fecha", 4),
	FALTA_CONCEPTO("falta concepto", 5),
	FALTA_UNIDAD("falta unidad", 6),
	FECHAFIN_MENOR_INICIO("La fecha fin no puede ser menor a la de comienzo", 7),
	ERROR("error", 8),
	WARNING("warning", 9),
	ERROR_CERRAR_MES("Hubo un error al cerrar el mes", 100);

	private final String error;
	private final int codigo;
	private final String errorCodigo;

	errores(String error, int codigo) {
		this.error = error;
		this.codigo = codigo;
		this.errorCodigo = error + " - " + Constantes.VER_ERORES + codigo;
	}

	public String getError() {
		return error;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getErrorCodigo() {
		return errorCodigo;
	}
	
	
}
