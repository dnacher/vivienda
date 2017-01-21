/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class DAOException extends Exception{
    
    public static final   String CODIGO_OK = "OK";
    public static final   String CODIGO_ERROR="ERR";
    public static final   String EJB_NO_ENCONTRADO="EJB_NO_ENCONTRADO";
    
    
    private String codigo=CODIGO_OK;
    private String codigoExcepcion="00";
    private Exception ex;
    List<String> errores=new ArrayList();
    
    public DAOException() {
        super("error en generalException");
    }
    
    public DAOException(String msg) {
        super(msg);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }
    
    public void addError(String codigoError) {
        this.codigo="ERR";
        if (this.errores==null) {
            this.errores=new ArrayList();
        }
        this.errores.add(codigoError);
    }

    public String getCodigoExcepcion() {
        return codigoExcepcion;
    }

    public void setCodigoExcepcion(String codigoExcepcion) {
        this.codigoExcepcion = codigoExcepcion;
    }
}
