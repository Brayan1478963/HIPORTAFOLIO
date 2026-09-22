package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.service.ArchivoService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EliminarArchivoServlet - Elimina un archivo del disco y de la BD.
 * POST /admin/archivos/eliminar?id=X
 * @author HiPortafolio
 */
public class EliminarArchivoServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(EliminarArchivoServlet.class.getName());
    private final ArchivoService archivoService = new ArchivoService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
        String ctx = req.getContextPath();
        if (id <= 0) {
            resp.sendRedirect(ctx + "/admin/archivos");
            return;
        }
        try {
            String uploadRoot = getServletContext().getRealPath("/") + java.io.File.separator;
            archivoService.eliminarArchivo(id, uploadRoot);
            resp.sendRedirect(ctx + "/admin/archivos?mensaje=" +
                java.net.URLEncoder.encode(Constants.MSG_ELIMINADO, "UTF-8"));
        } catch (ApplicationException e) {
            LOGGER.log(Level.WARNING, "Error al eliminar archivo id=" + id + ": " + e.getMessage());
            resp.sendRedirect(ctx + "/admin/archivos?error=" +
                java.net.URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }
}
