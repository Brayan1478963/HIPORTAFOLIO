package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.service.*;
import com.hiportafolio.util.Constants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DashboardServlet - Panel de control administrativo.
 *
 * GET /admin/dashboard → carga estadísticas y muestra el dashboard
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class DashboardServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DashboardServlet.class.getName());

    private final UsuarioService    usuarioService    = new UsuarioService();
    private final UnidadService     unidadService     = new UnidadService();
    private final SemanaService     semanaService     = new SemanaService();
    private final ContenidoService  contenidoService  = new ContenidoService();
    private final EvidenciaService  evidenciaService  = new EvidenciaService();
    private final ProyectoService   proyectoService   = new ProyectoService();
    private final TecnologiaService tecnologiaService = new TecnologiaService();
    private final ArchivoService    archivoService    = new ArchivoService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // Cargar contadores para las tarjetas del dashboard
            Map<String, Integer> stats = new HashMap<>();
            stats.put("usuarios",    usuarioService.contar());
            stats.put("unidades",    unidadService.contar());
            stats.put("semanas",     semanaService.contar());
            stats.put("contenidos",  contenidoService.contar());
            stats.put("evidencias",  evidenciaService.contar());
            stats.put("proyectos",   proyectoService.contar());
            stats.put("tecnologias", tecnologiaService.contar());
            stats.put("archivos",    archivoService.contar());

            req.setAttribute(Constants.ATTR_STATS, stats);
            req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Dashboard");
            req.getRequestDispatcher(Constants.VIEW_DASHBOARD).forward(req, resp);

        } catch (ApplicationException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar dashboard", e);
            req.setAttribute(Constants.ATTR_ERROR, Constants.ERR_DB);
            req.getRequestDispatcher(Constants.VIEW_ERROR_500).forward(req, resp);
        }
    }
}
