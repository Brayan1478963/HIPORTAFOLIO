package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.model.Usuario;
import com.hiportafolio.service.ArchivoService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.FileUtil;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ArchivoServlet - Lista y sube archivos en el panel administrativo.
 * @author HiPortafolio
 */
@MultipartConfig(maxFileSize = 10485760, maxRequestSize = 20971520)
public class ArchivoServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ArchivoServlet.class.getName());
    private final ArchivoService archivoService = new ArchivoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute(Constants.ATTR_ARCHIVOS, archivoService.listar());
            req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Gestión de Archivos");
            req.getRequestDispatcher(Constants.VIEW_ADM_ARCH_LIST).forward(req, resp);
        } catch (ApplicationException e) {
            LOGGER.log(Level.SEVERE, "Error al listar archivos", e);
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
            if (Constants.ACCION_GUARDAR.equals(accion)) {
                Part part = req.getPart("archivo");
                if (part == null || part.getSize() == 0) {
                    req.setAttribute(Constants.ATTR_ERROR, "Debe seleccionar un archivo.");
                    doGet(req, resp);
                    return;
                }
                String tipo      = req.getParameter("tipo") != null ? req.getParameter("tipo") : "documentos";
                String uploadPath = FileUtil.getRutaUpload(getServletContext().getRealPath("/"), tipo);
                Usuario sesion   = getSesion(req);
                archivoService.subirArchivo(part, uploadPath,
                    sesion != null ? sesion.getIdUsuario() : 0, tipo);
                resp.sendRedirect(ctx + "/admin/archivos?mensaje=" +
                    encode(Constants.MSG_ARCHIVO_SUBIDO));
            } else {
                resp.sendRedirect(ctx + "/admin/archivos");
            }
        } catch (ApplicationException e) {
            LOGGER.log(Level.WARNING, "Error subiendo archivo: " + e.getMessage());
            req.setAttribute(Constants.ATTR_ERROR, e.getMessage());
            doGet(req, resp);
        }
    }

    private Usuario getSesion(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        if (s == null) return null;
        Object o = s.getAttribute(Constants.SESSION_USUARIO);
        return o instanceof Usuario u ? u : null;
    }

    private String encode(String s) throws java.io.UnsupportedEncodingException {
        return java.net.URLEncoder.encode(s, "UTF-8");
    }
}
