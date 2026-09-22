package com.hiportafolio.service;

import com.hiportafolio.dao.ContenidoDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Contenido;
import com.hiportafolio.util.ValidationUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ContenidoService - Lógica de negocio para gestión de contenidos educativos.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class ContenidoService {

    private static final Logger LOGGER = Logger.getLogger(ContenidoService.class.getName());
    private final ContenidoDAO contenidoDAO = new ContenidoDAO();

    public List<Contenido> listar() throws ApplicationException {
        try {
            return contenidoDAO.listar();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar contenidos", e);
            throw new ApplicationException("Error al obtener los contenidos.", e);
        }
    }

    public List<Contenido> listarPorSemana(int idSemana) throws ApplicationException {
        try {
            return contenidoDAO.listarPorSemana(idSemana);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar contenidos por semana", e);
            throw new ApplicationException("Error al obtener los contenidos de la semana.", e);
        }
    }

    public Contenido buscarPorId(int idContenido) throws ApplicationException {
        try {
            Contenido c = contenidoDAO.buscarPorId(idContenido);
            if (c == null) {
                throw new ApplicationException(
                    ApplicationException.ENTITY_NOT_FOUND,
                    "No se encontró el contenido con ID: " + idContenido
                );
            }
            return c;
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar contenido", e);
            throw new ApplicationException("Error al buscar el contenido.", e);
        }
    }

    public void crearContenido(String titulo, String descripcion, String contenido,
                                String aprendizaje, String reflexion, String referencias,
                                String idSemana, String orden) throws ApplicationException {
        String error = ValidationUtil.validarFormularioContenido(titulo, idSemana);
        if (error != null) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR, error);
        }
        try {
            Contenido c = new Contenido();
            c.setTitulo(titulo.trim());
            c.setDescripcion(descripcion);
            c.setContenido(contenido);
            c.setAprendizaje(aprendizaje);
            c.setReflexion(reflexion);
            c.setReferencias(referencias);
            c.setIdSemana(ValidationUtil.parseIntSeguro(idSemana));
            c.setOrden(ValidationUtil.parseIntSeguro(orden) > 0 ? ValidationUtil.parseIntSeguro(orden) : 1);
            c.setEstado(true);
            contenidoDAO.guardar(c);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al crear contenido", e);
            throw new ApplicationException("Error al crear el contenido.", e);
        }
    }

    public void actualizarContenido(int idContenido, String titulo, String descripcion,
                                     String contenido, String aprendizaje, String reflexion,
                                     String referencias, String idSemana,
                                     String orden, boolean estado) throws ApplicationException {
        String error = ValidationUtil.validarFormularioContenido(titulo, idSemana);
        if (error != null) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR, error);
        }
        try {
            Contenido c = buscarPorId(idContenido);
            c.setTitulo(titulo.trim());
            c.setDescripcion(descripcion);
            c.setContenido(contenido);
            c.setAprendizaje(aprendizaje);
            c.setReflexion(reflexion);
            c.setReferencias(referencias);
            c.setIdSemana(ValidationUtil.parseIntSeguro(idSemana));
            c.setOrden(ValidationUtil.parseIntSeguro(orden) > 0 ? ValidationUtil.parseIntSeguro(orden) : 1);
            c.setEstado(estado);
            contenidoDAO.actualizar(c);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar contenido", e);
            throw new ApplicationException("Error al actualizar el contenido.", e);
        }
    }

    public void eliminarContenido(int idContenido) throws ApplicationException {
        try {
            buscarPorId(idContenido);
            contenidoDAO.eliminar(idContenido);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar contenido", e);
            throw new ApplicationException("Error al eliminar el contenido.", e);
        }
    }

    public int contar() throws ApplicationException {
        try {
            return contenidoDAO.contar();
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al contar contenidos.", e);
        }
    }
}
