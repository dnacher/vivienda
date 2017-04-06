/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.enums;

/**
 *
 * @author Daniel
 */
public enum Mensajes {
    
    LICENCIA("Licencia"),
    CONFIRMA_LICENCIA("Confirmar Licencia"),
    DESEA_CONFIRMAR_LICENCIA("¿Desea confirmar la licencia?"),
    BAJA("Baja"),
    CONFIRMA_BAJA("Confirmar Baja"),
    DESEA_CONFIRMAR_BAJA("¿Desea confirmar la baja?"),
    BAJA_CORRECTO("Se ha dado de baja correctamente. "),
    MY_DIALOG("myDialog"),
    CONFIRMA("Confirma"),
    NOMBRE("Nombre"),
    APELLIDO("Apellido"),
    TELEFONO("Telefono"),
    EMAIL("Email"),
    BLOCK("Block"),
    TORRE("Torre"),
    PUERTA("Puerta"),
    CALIFICACION("Calificacion"),
    ESTADO("Estado"),
    FECHA_INICIO("Fecha de inicio"),
    FECHA_FIN("Fecha fin"),
    OK("tick"),
    DEBE_SELECCIONAR_TECNICO("Debe seleccionar un Tecnico"),
    DEBE_SELECCIONAR_UNIDAD("Debe seleccionar una Unidad"),
    UNIDAD_TRABAJOS_ACTIVOS("La unidad no tiene trabajos activos"),
    CUOTAS("Cuotas"),
    FECHA("Fecha"),
    MONTO("Monto"),
    CUOTA_APROXIMADA("Cuota aproximada: $"),
    VALOR_NUMERICO("debe ser un valor numerico"),
    NO_VACIO("No debe estar vacio"),
    NO_FECHA("No hay fecha seleccionada"),
    CUOTAS_APROX("Numero de cuotas aproximadas: "),
    BLOCKYTORRE("Debe seleccionar valores de Block y/o Torre para buscar"),    
    ESPACIO(" "),
    VACIO("");
    
    private String mensaje;
    
    
    Mensajes(String mensaje){
         this.mensaje = mensaje;       
    }
    
    public String getMensaje(){
        return mensaje;
    } 
}
