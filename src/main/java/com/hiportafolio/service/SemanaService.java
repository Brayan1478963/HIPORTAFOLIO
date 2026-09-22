package com.hiportafolio.service;

import com.hiportafolio.dao.SemanaDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Semana;
import com.hiportafolio.util.ValidationUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SemanaService - Lógica de negocio para gestión de semanas académicas.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class SemanaService {

    private static final Logger LOGGER = Logger.getLogger(SemanaService.class.getName());
    private final SemanaDAO semanaDAO = new SemanaDAO();

    public List<Semana> listar() throws ApplicationException {
        try {
            return semanaDAO.listar();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar semanas", e);
            throw new ApplicationException("Error al obtener las semanas.", e);
        }
    }

    public List<Semana> listarPorUnidad(int idUnidad) throws ApplicationException {
        try {
            return semanaDAO.listarPorUnidad(idUnidad);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar semanas por unidad", e);
            throw new ApplicationException("Error al obtener las semanas de la unidad.", e);
        }
    }

    public Semana buscarPorId(int idSemana) throws ApplicationException {
        try {
            Semana semana = semanaDAO.buscarPorId(idSemana);
            if (semana == null) {
                throw new ApplicationException(
                    ApplicationException.ENTITY_NOT_FOUND,
                    "No se encontró la semana con ID: " + idSemana
                );
            }
            return semana;
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar semana", e);
            throw new ApplicationException("Error al buscar la semana.", e);
        }
    }

    /** Retorna la semana anterior para navegación secuencial. */
    public Semana buscarAnterior(int numeroActual) throws ApplicationException {
        try {
            return semanaDAO.buscarAnterior(numeroActual);
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al buscar semana anterior.", e);
        }
    }

    /** Retorna la semana siguiente para navegación secuencial. */
    public Semana buscarSiguiente(int numeroActual) throws ApplicationException {
        try {
            return semanaDAO.buscarSiguiente(numeroActual);
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al buscar semana siguiente.", e);
        }
    }

    public void crearSemana(String numero, String titulo, String descripcion,
                             String objetivo, String idUnidad) throws ApplicationException {
        String error = ValidationUtil.validarFormularioSemana(numero, titulo, idUnidad);
        if (error != null) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR, error);
        }
        try {
            Semana s = new Semana();
            s.setNumero(ValidationUtil.parseIntSeguro(numero));
            s.setTitulo(titulo.trim());
            s.setDescripcion(descripcion);
            s.setObjetivo(objetivo);
            s.setIdUnidad(ValidationUtil.parseIntSeguro(idUnidad));
            s.setEstado(true);
            semanaDAO.guardar(s);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al crear semana", e);
            throw new ApplicationException("Error al crear la semana.", e);
        }
    }

    public void actualizarSemana(int idSemana, String numero, String titulo,
                                  String descripcion, String objetivo,
                                  String idUnidad, boolean estado) throws ApplicationException {
        String error = ValidationUtil.validarFormularioSemana(numero, titulo, idUnidad);
        if (error != null) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR, error);
        }
        try {
            Semana s = buscarPorId(idSemana);
            s.setNumero(ValidationUtil.parseIntSeguro(numero));
            s.setTitulo(titulo.trim());
            s.setDescripcion(descripcion);
            s.setObjetivo(objetivo);
            s.setIdUnidad(ValidationUtil.parseIntSeguro(idUnidad));
            s.setEstado(estado);
            semanaDAO.actualizar(s);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar semana", e);
            throw new ApplicationException("Error al actualizar la semana.", e);
        }
    }

    public void eliminarSemana(int idSemana) throws ApplicationException {
        try {
            buscarPorId(idSemana);
            semanaDAO.eliminar(idSemana);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar semana", e);
            throw new ApplicationException("Error al eliminar la semana.", e);
        }
    }

    public int contar() throws ApplicationException {
        try {
            return semanaDAO.contar();
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al contar semanas.", e);
        }
    }
}
