package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.service.ArchivoService;
import com.hiportafolio.util.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Catálogo público de trabajos y documentos publicados por el administrador. */
public class TrabajosPublicosServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(TrabajosPublicosServlet.class.getName());
    private final ArchivoService service = new ArchivoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute(Constants.ATTR_ARCHIVOS, service.listar());
            req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Trabajos publicados");
            req.getRequestDispatcher("/WEB-INF/views/public/trabajos.jsp").forward(req, resp);
        } catch (ApplicationException e) {
            LOG.log(Level.SEVERE, "No se pudieron cargar los trabajos públicos", e);
            req.setAttribute(Constants.ATTR_ERROR, "No se pudieron cargar los trabajos. Inténtalo más tarde.");
            req.getRequestDispatcher(Constants.VIEW_ERROR_500).forward(req, resp);
        }
    }
}
