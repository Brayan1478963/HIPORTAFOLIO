package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.service.SemanaService;
import com.hiportafolio.service.UnidadService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UnidadServlet - Maneja las unidades académicas en modo admin y público.
 *
 * ADMIN:  GET/POST /admin/unidades  → CRUD
 * PÚBLICO: GET /unidades            → listar y ver detalle
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class UnidadServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UnidadServlet.class.getName());
    private final UnidadService unidadService = new UnidadService();
    private final SemanaService semanaService = new SemanaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean esAdmin = req.getRequestURI().contains("/admin/");
        String accion   = req.getParameter(Constants.PARAM_ACCION);
        if (accion == null) accion = Constants.ACCION_LISTAR;

        try {
            if (esAdmin) {
                manejarAdminGet(req, resp, accion);
            } else {
                manejarPublicGet(req, resp, accion);
            }
        } catch (ApplicationException e) {
            LOGGER.log(Level.SEVERE, "Error en UnidadServlet GET", e);
            req.setAttribute(Constants.ATTR_ERROR, e.getMessage());
            req.getRequestDispatcher(Constants.VIEW_ERROR_500).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter(Constants.PARAM_ACCION);
        String ctx    = req.getContextPath();

        try {
            switch (accion != null ? accion : "") {
                case Constants.ACCION_GUARDAR -> {
                    unidadService.crearUnidad(
                        req.getParameter("numero"),
                        req.getParameter("titulo"),
                        req.getParameter("descripcion")
                    );
                    resp.sendRedirect(ctx + Constants.URL_ADMIN_UNIDADES +
                        "?accion=listar&mensaje=" + encode(Constants.MSG_GUARDADO));
                }
                case Constants.ACCION_ACTUALIZAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    boolean estado = "on".equals(req.getParameter("estado")) ||
                                     "true".equals(req.getParameter("estado"));
                    unidadService.actualizarUnidad(id,
                        req.getParameter("numero"), req.getParameter("titulo"),
                        req.getParameter("descripcion"), estado);
                    resp.sendRedirect(ctx + Constants.URL_ADMIN_UNIDADES +
                        "?accion=listar&mensaje=" + encode(Constants.MSG_ACTUALIZADO));
                }
                case Constants.ACCION_ELIMINAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    unidadService.eliminarUnidad(id);
                    resp.sendRedirect(ctx + Constants.URL_ADMIN_UNIDADES +
                        "?accion=listar&mensaje=" + encode(Constants.MSG_ELIMINADO));
                }
                default -> resp.sendRedirect(ctx + Constants.URL_ADMIN_UNIDADES);
            }
        } catch (ApplicationException e) {
            LOGGER.log(Level.WARNING, "Error en UnidadServlet POST: " + e.getMessage());
            req.setAttribute(Constants.ATTR_ERROR, e.getMessage());
            req.getRequestDispatcher(Constants.VIEW_ADM_UNI_CREAR).forward(req, resp);
        }
    }

    private void manejarAdminGet(HttpServletRequest req, HttpServletResponse resp, String accion)
            throws ApplicationException, ServletException, IOException {
        switch (accion) {
            case Constants.ACCION_LISTAR -> {
                req.setAttribute(Constants.ATTR_UNIDADES, unidadService.listar());
                req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Gestión de Unidades");
                req.getRequestDispatcher(Constants.VIEW_ADM_UNI_LIST).forward(req, resp);
            }
            case Constants.ACCION_NUEVA -> {
                req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Nueva Unidad");
                req.getRequestDispatcher(Constants.VIEW_ADM_UNI_CREAR).forward(req, resp);
            }
            case Constants.ACCION_EDITAR -> {
                int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                req.setAttribute(Constants.ATTR_UNIDAD, unidadService.buscarPorId(id));
                req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Editar Unidad");
                req.getRequestDispatcher(Constants.VIEW_ADM_UNI_EDIT).forward(req, resp);
            }
            default -> resp.sendRedirect(req.getContextPath() + Constants.URL_ADMIN_UNIDADES);
        }
    }

    private void manejarPublicGet(HttpServletRequest req, HttpServletResponse resp, String accion)
            throws ApplicationException, ServletException, IOException {
        if (Constants.ACCION_DETALLE.equals(accion)) {
            int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
            com.hiportafolio.model.Unidad unidad = unidadService.buscarPorId(id);
            java.util.List<com.hiportafolio.model.Semana> semanas = semanaService.listarPorUnidad(id);
            req.setAttribute(Constants.ATTR_UNIDAD,  unidad);
            req.setAttribute(Constants.ATTR_SEMANAS, semanas);
            req.setAttribute(Constants.ATTR_TITULO_PAGINA, unidad.getTituloCompleto());
            req.getRequestDispatcher(Constants.VIEW_UNIDAD_DET).forward(req, resp);
        } else {
            // Cargar unidades con sus semanas para el mapa de niveles
            java.util.List<com.hiportafolio.model.Unidad> unidades = unidadService.listarActivas();
            for (com.hiportafolio.model.Unidad u : unidades) {
                u.setSemanas(semanaService.listarPorUnidad(u.getIdUnidad()));
            }
            req.setAttribute(Constants.ATTR_UNIDADES, unidades);
            req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Unidades Académicas");
            req.getRequestDispatcher(Constants.VIEW_UNIDADES).forward(req, resp);
        }
    }

    private String encode(String s) throws java.io.UnsupportedEncodingException {
        return java.net.URLEncoder.encode(s, "UTF-8");
    }
}
