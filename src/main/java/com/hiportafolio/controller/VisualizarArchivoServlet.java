package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.model.Archivo;
import com.hiportafolio.service.ArchivoService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.*;
import java.nio.file.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * VisualizarArchivoServlet - Sirve el archivo para visualización inline en el browser.
 * GET /archivos/ver?id=X
 * @author HiPortafolio
 */

public class VisualizarArchivoServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(VisualizarArchivoServlet.class.getName());
    private final ArchivoService archivoService = new ArchivoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
        if (id <= 0) { resp.sendError(HttpServletResponse.SC_BAD_REQUEST); return; }

        try {
            Archivo archivo = archivoService.buscarPorId(id);
            String rutaBase = getServletContext().getRealPath("/");
                Path ruta = Paths.get(rutaBase, "uploads",
                    archivo.getRuta().replace("/", File.separator));

            if (!Files.exists(ruta)) { resp.sendError(HttpServletResponse.SC_NOT_FOUND); return; }

            resp.setContentType(archivo.getTipoMime());
            resp.setContentLengthLong(archivo.getTamano());
            resp.setHeader("Content-Disposition",
                "inline; filename=\"" + archivo.getNombreOriginal() + "\"");

            Files.copy(ruta, resp.getOutputStream());

        } catch (ApplicationException e) {
            LOGGER.log(Level.WARNING, "Archivo no encontrado: id=" + id);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
