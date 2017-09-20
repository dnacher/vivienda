/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.enums;

/**
 *
 * @author Dani-Fla-Mathi
 */
public enum MenuConfiguracion {

    Inicio("Inicio", "Inicio"),
    Grupo("Grupo", "Grupo"),
    Urgencia("Urgencia", "Urgencia"),
    Concepto("Concepto", "Concepto"),
    Usuarios("Usuario", "Usuario"),
    TipoUsuario("TipoUsuario", "Tipo Usuario"),
    TipoDuracion("TipoDuracion", "Tipo Duracion"),
    CargarMasivas("CargarMasivas", "Cargar Masivas"),
    ImportardeExcel("ImportarExcel", "Importar Excel"),
    Seguridad("Seguridad", "Seguridad");

    private final String pagina;
    private final String menu;

    MenuConfiguracion(String pagina, String menu) {
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
