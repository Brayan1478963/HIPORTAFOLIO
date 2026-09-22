package com.hiportafolio.service;

import com.hiportafolio.dao.UnidadDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Unidad;
import com.hiportafolio.util.ValidationUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UnidadService - Lógica de negocio para gestión de unidades académicas.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class UnidadService {

    private static final Logger LOGGER = Logger.getLogger(UnidadService.class.getName());
    private final UnidadDAO unidadDAO = new UnidadDAO();

    public List<Unidad> listar() throws ApplicationException {
        try {
            return unidadDAO.listar();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar unidades", e);
            throw new ApplicationException("Error al obtener las unidades.", e);
        }
    }

    public List<Unidad> listarActivas() throws ApplicationException {
        try {
            return unidadDAO.listarActivas();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar unidades activas", e);
            throw new ApplicationException("Error al obtener las unidades.", e);
        }
    }

    public Unidad buscarPorId(int idUnidad) throws ApplicationException {
        try {
            Unidad unidad = unidadDAO.buscarPorId(idUnidad);
            if (unidad == null) {
                throw new ApplicationException(
                    ApplicationException.ENTITY_NOT_FOUND,
                    "No se encontró la unidad con ID: " + idUnidad
                );
            }
            return unidad;
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar unidad", e);
            throw new ApplicationException("Error al buscar la unidad.", e);
        }
    }

    public void crearUnidad(String numero, String titulo, String descripcion)
            throws ApplicationException {
        String error = ValidationUtil.validarFormularioUnidad(numero, titulo);
        if (error != null) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR, error);
        }
        try {
            Unidad u = new Unidad();
            u.setNumero(ValidationUtil.parseIntSeguro(numero));
            u.setTitulo(titulo.trim());
            u.setDescripcion(descripcion);
            u.setEstado(true);
            unidadDAO.guardar(u);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al crear unidad", e);
            throw new ApplicationException("Error al crear la unidad.", e);
        }
    }

    public void actualizarUnidad(int idUnidad, String numero, String titulo,
                                  String descripcion, boolean estado)
            throws ApplicationException {
        String error = ValidationUtil.validarFormularioUnidad(numero, titulo);
        if (error != null) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR, error);
        }
        try {
            Unidad u = buscarPorId(idUnidad);
            u.setNumero(ValidationUtil.parseIntSeguro(numero));
            u.setTitulo(titulo.trim());
            u.setDescripcion(descripcion);
            u.setEstado(estado);
            unidadDAO.actualizar(u);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar unidad", e);
            throw new ApplicationException("Error al actualizar la unidad.", e);
        }
    }

    public void eliminarUnidad(int idUnidad) throws ApplicationException {
        try {
            buscarPorId(idUnidad);
            unidadDAO.eliminar(idUnidad);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar unidad", e);
            throw new ApplicationException("Error al eliminar la unidad.", e);
        }
    }

    public int contar() throws ApplicationException {
        try {
            return unidadDAO.contar();
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al contar unidades.", e);
        }
    }
}
