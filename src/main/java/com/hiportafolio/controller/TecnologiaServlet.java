package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.service.TecnologiaService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TecnologiaServlet - CRUD tecnologías (admin) y vista pública.
 * @author HiPortafolio
 */
public class TecnologiaServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(TecnologiaServlet.class.getName());
    private final TecnologiaService tecnologiaService = new TecnologiaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        boolean esAdmin = req.getRequestURI().contains("/admin/");
        String accion   = req.getParameter(Constants.PARAM_ACCION);
        if (accion == null) accion = Constants.ACCION_LISTAR;
        try {
            if (esAdmin) {
                switch (accion) {
                    case Constants.ACCION_LISTAR -> {
                        req.setAttribute(Constants.ATTR_LISTA, tecnologiaService.listar());
                        req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Gestión de Tecnologías");
                        req.getRequestDispatcher(Constants.VIEW_ADM_TEC_LIST).forward(req, resp);
                    }
                    case Constants.ACCION_NUEVA -> {
                        req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Nueva Tecnología");
                        req.getRequestDispatcher(Constants.VIEW_ADM_TEC_CREAR).forward(req, resp);
                    }
                    case Constants.ACCION_EDITAR -> {
                        int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                        req.setAttribute(Constants.ATTR_ENTIDAD, tecnologiaService.buscarPorId(id));
                        req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Editar Tecnología");
                        req.getRequestDispatcher(Constants.VIEW_ADM_TEC_EDIT).forward(req, resp);
                    }
                    default -> resp.sendRedirect(req.getContextPath() + "/admin/tecnologias");
                }
            } else {
                // Vista pública: mostrar todas las tecnologías activas
                // Si no hay activas, mostrar todas para no quedar en blanco
                java.util.List<com.hiportafolio.model.Tecnologia> tecList = tecnologiaService.listarActivas();
                if (tecList.isEmpty()) {
                    tecList = tecnologiaService.listar();
                }
                req.setAttribute(Constants.ATTR_TECNOLOGIAS, tecList);
                req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Tecnologías");
                req.getRequestDispatcher(Constants.VIEW_TECNOLOGIAS_PUB).forward(req, resp);
            }
        } catch (ApplicationException e) {
            LOGGER.log(Level.SEVERE, "Error en TecnologiaServlet GET", e);
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
                    tecnologiaService.crearTecnologia(
                        req.getParameter("nombre"), req.getParameter("descripcion"),
                        req.getParameter("icono"), req.getParameter("nivel"),
                        req.getParameter("idCategoria"), req.getParameter("orden")
                    );
                    resp.sendRedirect(ctx + "/admin/tecnologias?accion=listar&mensaje=" +
                        encode(Constants.MSG_GUARDADO));
                }
                case Constants.ACCION_ACTUALIZAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    boolean estado = "on".equals(req.getParameter("estado")) || "true".equals(req.getParameter("estado"));
                    tecnologiaService.actualizarTecnologia(id,
                        req.getParameter("nombre"), req.getParameter("descripcion"),
                        req.getParameter("icono"), req.getParameter("nivel"),
                        req.getParameter("idCategoria"), req.getParameter("orden"), estado
                    );
                    resp.sendRedirect(ctx + "/admin/tecnologias?accion=listar&mensaje=" +
                        encode(Constants.MSG_ACTUALIZADO));
                }
                case Constants.ACCION_ELIMINAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    tecnologiaService.eliminarTecnologia(id);
                    resp.sendRedirect(ctx + "/admin/tecnologias?accion=listar&mensaje=" +
                        encode(Constants.MSG_ELIMINADO));
                }
                default -> resp.sendRedirect(ctx + "/admin/tecnologias");
            }
        } catch (ApplicationException e) {
            LOGGER.log(Level.WARNING, "Error en TecnologiaServlet POST: " + e.getMessage());
            req.setAttribute(Constants.ATTR_ERROR, e.getMessage());
            req.getRequestDispatcher(Constants.VIEW_ADM_TEC_CREAR).forward(req, resp);
        }
    }

    private String encode(String s) throws java.io.UnsupportedEncodingException {
        return java.net.URLEncoder.encode(s, "UTF-8");
    }
}
