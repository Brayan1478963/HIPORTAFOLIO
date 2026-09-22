package com.hiportafolio.service;

import com.hiportafolio.dao.ProyectoDAO;
import com.hiportafolio.dao.TecnologiaDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Proyecto;
import com.hiportafolio.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ProyectoService - Lógica de negocio para gestión de proyectos.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class ProyectoService {

    private static final Logger LOGGER = Logger.getLogger(ProyectoService.class.getName());
    private final ProyectoDAO    proyectoDAO    = new ProyectoDAO();
    private final TecnologiaDAO  tecnologiaDAO  = new TecnologiaDAO();

    public List<Proyecto> listar() throws ApplicationException {
        try {
            List<Proyecto> lista = proyectoDAO.listar();
            cargarTecnologias(lista);
            return lista;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar proyectos", e);
            throw new ApplicationException("Error al obtener los proyectos.", e);
        }
    }

    public List<Proyecto> listarActivos() throws ApplicationException {
        try {
            List<Proyecto> lista = proyectoDAO.listarActivos();
            cargarTecnologias(lista);
            return lista;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar proyectos activos", e);
            throw new ApplicationException("Error al obtener los proyectos.", e);
        }
    }

    public Proyecto buscarPorId(int idProyecto) throws ApplicationException {
        try {
            Proyecto p = proyectoDAO.buscarPorId(idProyecto);
            if (p == null) {
                throw new ApplicationException(
                    ApplicationException.ENTITY_NOT_FOUND,
                    "No se encontró el proyecto con ID: " + idProyecto
                );
            }
            p.setTecnologias(tecnologiaDAO.listarPorProyecto(idProyecto));
            return p;
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar proyecto", e);
            throw new ApplicationException("Error al buscar el proyecto.", e);
        }
    }

    public void crearProyecto(Proyecto proyecto, List<Integer> idsTecnologias)
            throws ApplicationException {
        if (ValidationUtil.estaVacio(proyecto.getNombre())) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR,
                "El nombre del proyecto es obligatorio.");
        }
        try {
            int idGenerado = proyectoDAO.guardar(proyecto);
            proyecto.setIdProyecto(idGenerado);
            if (idsTecnologias != null && !idsTecnologias.isEmpty()) {
                proyectoDAO.actualizarTecnologias(idGenerado, idsTecnologias);
            }
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al crear proyecto", e);
            throw new ApplicationException("Error al crear el proyecto.", e);
        }
    }

    public void actualizarProyecto(Proyecto proyecto, List<Integer> idsTecnologias)
            throws ApplicationException {
        if (ValidationUtil.estaVacio(proyecto.getNombre())) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR,
                "El nombre del proyecto es obligatorio.");
        }
        try {
            buscarPorId(proyecto.getIdProyecto()); // verificar que existe
            proyectoDAO.actualizar(proyecto);
            proyectoDAO.actualizarTecnologias(proyecto.getIdProyecto(),
                idsTecnologias != null ? idsTecnologias : new ArrayList<>());
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar proyecto", e);
            throw new ApplicationException("Error al actualizar el proyecto.", e);
        }
    }

    public void eliminarProyecto(int idProyecto) throws ApplicationException {
        try {
            buscarPorId(idProyecto);
            proyectoDAO.eliminar(idProyecto);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar proyecto", e);
            throw new ApplicationException("Error al eliminar el proyecto.", e);
        }
    }

    public int contar() throws ApplicationException {
        try {
            return proyectoDAO.contar();
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al contar proyectos.", e);
        }
    }

    /** Carga las tecnologías de cada proyecto en la lista. */
    private void cargarTecnologias(List<Proyecto> lista) throws DatabaseException {
        for (Proyecto p : lista) {
            p.setTecnologias(tecnologiaDAO.listarPorProyecto(p.getIdProyecto()));
        }
    }
}
