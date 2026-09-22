package com.hiportafolio.service;

import com.hiportafolio.dao.TecnologiaDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Tecnologia;
import com.hiportafolio.util.ValidationUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TecnologiaService - Lógica de negocio para gestión de tecnologías.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class TecnologiaService {

    private static final Logger LOGGER = Logger.getLogger(TecnologiaService.class.getName());
    private final TecnologiaDAO tecnologiaDAO = new TecnologiaDAO();

    public List<Tecnologia> listar() throws ApplicationException {
        try {
            return tecnologiaDAO.listar();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar tecnologias", e);
            throw new ApplicationException("Error al obtener las tecnologías.", e);
        }
    }

    public List<Tecnologia> listarActivas() throws ApplicationException {
        try {
            return tecnologiaDAO.listarActivas();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar tecnologias activas", e);
            throw new ApplicationException("Error al obtener las tecnologías.", e);
        }
    }

    public Tecnologia buscarPorId(int idTecnologia) throws ApplicationException {
        try {
            Tecnologia t = tecnologiaDAO.buscarPorId(idTecnologia);
            if (t == null) {
                throw new ApplicationException(
                    ApplicationException.ENTITY_NOT_FOUND,
                    "No se encontró la tecnología con ID: " + idTecnologia
                );
            }
            return t;
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar tecnologia", e);
            throw new ApplicationException("Error al buscar la tecnología.", e);
        }
    }

    public void crearTecnologia(String nombre, String descripcion, String icono,
                                 String nivel, String idCategoria, String orden)
            throws ApplicationException {
        if (ValidationUtil.estaVacio(nombre)) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR,
                "El nombre de la tecnología es obligatorio.");
        }
        try {
            Tecnologia t = new Tecnologia();
            t.setNombre(nombre.trim());
            t.setDescripcion(descripcion);
            t.setIcono(icono);
            t.setNivel(nivel);
            t.setIdCategoria(ValidationUtil.parseIntSeguro(idCategoria));
            t.setOrden(ValidationUtil.parseIntSeguro(orden) > 0 ? ValidationUtil.parseIntSeguro(orden) : 1);
            t.setEstado(true);
            tecnologiaDAO.guardar(t);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al crear tecnologia", e);
            throw new ApplicationException("Error al crear la tecnología.", e);
        }
    }

    public void actualizarTecnologia(int idTecnologia, String nombre, String descripcion,
                                      String icono, String nivel, String idCategoria,
                                      String orden, boolean estado) throws ApplicationException {
        if (ValidationUtil.estaVacio(nombre)) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR,
                "El nombre de la tecnología es obligatorio.");
        }
        try {
            Tecnologia t = buscarPorId(idTecnologia);
            t.setNombre(nombre.trim());
            t.setDescripcion(descripcion);
            t.setIcono(icono);
            t.setNivel(nivel);
            t.setIdCategoria(ValidationUtil.parseIntSeguro(idCategoria));
            t.setOrden(ValidationUtil.parseIntSeguro(orden) > 0 ? ValidationUtil.parseIntSeguro(orden) : 1);
            t.setEstado(estado);
            tecnologiaDAO.actualizar(t);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar tecnologia", e);
            throw new ApplicationException("Error al actualizar la tecnología.", e);
        }
    }

    public void eliminarTecnologia(int idTecnologia) throws ApplicationException {
        try {
            buscarPorId(idTecnologia);
            tecnologiaDAO.eliminar(idTecnologia);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar tecnologia", e);
            throw new ApplicationException("Error al eliminar la tecnología.", e);
        }
    }

    public int contar() throws ApplicationException {
        try {
            return tecnologiaDAO.contar();
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al contar tecnologías.", e);
        }
    }
}
