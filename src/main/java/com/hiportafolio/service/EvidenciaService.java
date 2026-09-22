package com.hiportafolio.service;

import com.hiportafolio.dao.EvidenciaDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Evidencia;
import com.hiportafolio.util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EvidenciaService - Lógica de negocio para gestión de evidencias de aprendizaje.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class EvidenciaService {

    private static final Logger LOGGER = Logger.getLogger(EvidenciaService.class.getName());
    private final EvidenciaDAO evidenciaDAO = new EvidenciaDAO();

    public List<Evidencia> listar() throws ApplicationException {
        try {
            return evidenciaDAO.listar();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar evidencias", e);
            throw new ApplicationException("Error al obtener las evidencias.", e);
        }
    }

    public List<Evidencia> listarActivas() throws ApplicationException {
        try {
            return evidenciaDAO.listarActivas();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar evidencias activas", e);
            throw new ApplicationException("Error al obtener las evidencias activas.", e);
        }
    }

    public List<Evidencia> listarPorSemana(int idSemana) throws ApplicationException {
        try {
            return evidenciaDAO.listarPorSemana(idSemana);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar evidencias por semana", e);
            throw new ApplicationException("Error al obtener las evidencias de la semana.", e);
        }
    }

    public Evidencia buscarPorId(int idEvidencia) throws ApplicationException {
        try {
            Evidencia ev = evidenciaDAO.buscarPorId(idEvidencia);
            if (ev == null) {
                throw new ApplicationException(
                    ApplicationException.ENTITY_NOT_FOUND,
                    "No se encontró la evidencia con ID: " + idEvidencia
                );
            }
            return ev;
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar evidencia", e);
            throw new ApplicationException("Error al buscar la evidencia.", e);
        }
    }

    public void crearEvidencia(String titulo, String descripcion, String tipo,
                                String idSemana, String idArchivo, String fechaStr)
            throws ApplicationException {
        if (ValidationUtil.estaVacio(titulo) || ValidationUtil.estaVacio(idSemana)) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR,
                "El título y la semana son obligatorios.");
        }
        try {
            Evidencia ev = new Evidencia();
            ev.setTitulo(titulo.trim());
            ev.setDescripcion(descripcion);
            ev.setTipo(tipo);
            ev.setIdSemana(ValidationUtil.parseIntSeguro(idSemana));
            ev.setIdArchivo(ValidationUtil.parseIntSeguro(idArchivo));
            if (!ValidationUtil.estaVacio(fechaStr)) {
                try { ev.setFechaEvidencia(LocalDate.parse(fechaStr)); } catch (Exception ignored) {}
            }
            ev.setEstado(true);
            evidenciaDAO.guardar(ev);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al crear evidencia", e);
            throw new ApplicationException("Error al crear la evidencia.", e);
        }
    }

    public void actualizarEvidencia(int idEvidencia, String titulo, String descripcion,
                                     String tipo, String idSemana, String idArchivo,
                                     String fechaStr, boolean estado) throws ApplicationException {
        if (ValidationUtil.estaVacio(titulo) || ValidationUtil.estaVacio(idSemana)) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR,
                "El título y la semana son obligatorios.");
        }
        try {
            Evidencia ev = buscarPorId(idEvidencia);
            ev.setTitulo(titulo.trim());
            ev.setDescripcion(descripcion);
            ev.setTipo(tipo);
            ev.setIdSemana(ValidationUtil.parseIntSeguro(idSemana));
            ev.setIdArchivo(ValidationUtil.parseIntSeguro(idArchivo));
            if (!ValidationUtil.estaVacio(fechaStr)) {
                try { ev.setFechaEvidencia(LocalDate.parse(fechaStr)); } catch (Exception ignored) {}
            }
            ev.setEstado(estado);
            evidenciaDAO.actualizar(ev);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar evidencia", e);
            throw new ApplicationException("Error al actualizar la evidencia.", e);
        }
    }

    public void eliminarEvidencia(int idEvidencia) throws ApplicationException {
        try {
            buscarPorId(idEvidencia);
            evidenciaDAO.eliminar(idEvidencia);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar evidencia", e);
            throw new ApplicationException("Error al eliminar la evidencia.", e);
        }
    }

    public int contar() throws ApplicationException {
        try {
            return evidenciaDAO.contar();
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al contar evidencias.", e);
        }
    }
}
