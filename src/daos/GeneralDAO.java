/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import exceptions.DAOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Daniel
 */
public class GeneralDAO <T> implements Serializable{
    
     private EntityManager em;
    private final Class<T> clazz;

    public GeneralDAO(EntityManager em, Class clase) {
        this.em = em;
        clazz = clase;
    }

    public Collection<T> obtenerTodos() throws DAOException {
        try {
            String consulta = clazz.getSimpleName() + ".findAll";
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " parametrized type " + consulta);
            return em.createNamedQuery(consulta).getResultList();
        } catch (Exception ex) {
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(),ex);
            throw new DAOException(ex.getMessage());
        }
    }
    
    public Collection<T> obtenerTodosOrderBy(String orderBy) throws DAOException {
        try {
		String consulta = "select s from " + clazz.getName() + " s ORDER BY s."+orderBy;
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " parametrized type " + consulta);
            return em.createQuery(consulta).getResultList();
        } catch (Exception ex) {
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(),ex);
            throw new DAOException(ex.getMessage());
        }
    }
    
    public Collection<T> obtenerHabilitados() throws DAOException {
        try {
            String consulta = clazz.getSimpleName() + ".findHabilitados";
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " parametrized type " + consulta);
            return em.createNamedQuery(consulta).getResultList();
        } catch (Exception ex) {
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage());
            throw new DAOException(ex.getMessage());
        }
    }
    
    public Collection<T> obtenerHabilitadosOrderBy(String orderBy, String campoHab) throws DAOException {
        try {
		String consulta = "select s from " + clazz.getName() + " s WHERE s."+campoHab+" = true ORDER BY s."+orderBy;
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " parametrized type " + consulta);
            return em.createQuery(consulta).getResultList();
        } catch (Exception ex) {
             Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(),ex);
            throw new DAOException(ex.getMessage());
        }
    }
/*
    public Collection<T> busquedaSimple(String codigo, String nombre) {
        String campoCodigo = ReflectionUtils.obtenerNombreCampoAnotado(clazz, AtributoCodigo.class);
        String campoNombre = ReflectionUtils.obtenerNombreCampoAnotado(clazz, AtributoNombre.class);
        String consulta = "select s from " + clazz.getName() + " s ";
        String cadena = "";
        boolean conAnd = false;
        boolean conWhere = false;
        if (!SofisStringUtils.esVacioONulo(codigo)) {
            conWhere = true;
            cadena = cadena + " (s." + campoCodigo + "=:codigo) ";
            conAnd = true;
        }
        if (!SofisStringUtils.esVacioONulo(nombre)) {
            conWhere = true;
            if (conAnd) {
                cadena += " and ";
            }
            cadena = cadena + " (s." + campoNombre + " like :nombre) ";
        }
        if (conWhere) {
            consulta = consulta + " where " + cadena;
        }
        Query query = em.createQuery(consulta);
        if (!SofisStringUtils.esVacioONulo(codigo)) {
            query.setParameter("codigo", codigo);
        }
        if (!SofisStringUtils.esVacioONulo(nombre)) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        return query.getResultList();
    }

    public Collection<T> busquedaSimple(Integer codigo, String nombre) {
        String campoCodigo = ReflectionUtils.obtenerNombreCampoAnotado(clazz, AtributoCodigo.class);
        String campoNombre = ReflectionUtils.obtenerNombreCampoAnotado(clazz, AtributoNombre.class);
        String consulta = "select s from " + clazz.getName() + " s ";
        String cadena = "";
        boolean conAnd = false;
        boolean conWhere = false;
        if (codigo != null) {
            conWhere = true;
            cadena = cadena + " (s." + campoCodigo + "=:codigo) ";
            conAnd = true;
        }
        if (!SofisStringUtils.esVacioONulo(nombre)) {
            conWhere = true;
            if (conAnd) {
                cadena += " and ";
            }
            cadena = cadena + " (s." + campoNombre + " like :nombre) ";
        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " cadena " + cadena);
        if (conWhere) {
            consulta = consulta + " where " + cadena;
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " consulta " + consulta);

        }
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, " EM " + em);
        Query query = em.createQuery(consulta);
        if (codigo != null) {
            query.setParameter("codigo", codigo);
        }
        if (!SofisStringUtils.esVacioONulo(nombre)) {
            query.setParameter("nombre", "%" + nombre + "%");
        }
        return query.getResultList();
    }
    
    public T guardar(T elemento) throws TechnicalException {
        try {
            Field campoId = ReflectionUtils.obtenerCampoAnotado(clazz, Id.class);
            Object value = campoId.get(elemento);
            if (value == null) {
                em.persist(elemento);
            } else {
                elemento = em.merge(elemento);
            }
        } catch (Exception ex) {
            Logger.getLogger(CodigueraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new TechnicalException(ex);
        }
        return elemento;
    }

    public void eliminar(T elemento) throws TechnicalException {
        try {
            Field campoId = ReflectionUtils.obtenerCampoAnotado(clazz, Id.class);
            Object value = campoId.get(elemento);
            elemento = em.find(clazz, value);
            em.remove(elemento);
            em.flush();
        } catch (Exception ex) {
            Logger.getLogger(CodigueraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new TechnicalException(ex);
        }
    }
    
    public T obtenerPorId(Integer id) throws TechnicalException {
        try {
            return  em.find(clazz,id);
        } catch (Exception ex) {
            Logger.getLogger(CodigueraDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new TechnicalException(ex);
        }
    }
    
    public Collection<T> obtenerPorCampo(Integer dato, String campo) {
        String consulta = "select s from " + clazz.getName() + " s ";
        String cadena = "";
        boolean conWhere = false;
        if (dato != null) {
            conWhere = true;
            cadena = cadena + " (s." + campo + "=:dato) ";
        }
        if (conWhere) {
            consulta = consulta + " where " + cadena;
        }
        Query query = em.createQuery(consulta);
        if (dato != null) {
            query.setParameter("dato", dato);
        }
        return query.getResultList();
    }*/
}
