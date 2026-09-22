package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.service.ProyectoService;
import com.hiportafolio.service.TecnologiaService;
import com.hiportafolio.service.UnidadService;
import com.hiportafolio.util.Constants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PortafolioServlet - Página principal del portafolio público.
 *
 * GET /portafolio
 * Muestra el inicio del portafolio con unidades, proyectos y tecnologías.
 *
 * También permite mostrar las vistas:
 * - Sobre mí
 * - Contacto
 *
 * @author HiPortafolio
 */
public class PortafolioServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(PortafolioServlet.class.getName());

    private final UnidadService unidadService = new UnidadService();
    private final ProyectoService proyectoService = new ProyectoService();
    private final TecnologiaService tecnologiaService = new TecnologiaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener parámetro opcional "vista"
        String vista = req.getParameter("vista");

        try {

            // =========================
            // VISTA: SOBRE MÍ
            // =========================
            if ("sobre-mi".equals(vista)) {

                req.setAttribute(
                        Constants.ATTR_TITULO_PAGINA,
                        "Sobre Mí"
                );

                req.getRequestDispatcher(
                        Constants.VIEW_SOBRE_MI
                ).forward(req, resp);

                return;
            }

            // =========================
            // VISTA: CONTACTO
            // =========================
            if ("contacto".equals(vista)) {

                req.setAttribute(
                        Constants.ATTR_TITULO_PAGINA,
                        "Contacto"
                );

                req.getRequestDispatcher(
                        Constants.VIEW_CONTACTO
                ).forward(req, resp);

                return;
            }

            // =========================
            // HOME PRINCIPAL
            // =========================

            req.setAttribute(
                    Constants.ATTR_UNIDADES,
                    unidadService.listarActivas()
            );

            req.setAttribute(
                    Constants.ATTR_PROYECTOS,
                    proyectoService.listarActivos()
            );

            req.setAttribute(
                    Constants.ATTR_TECNOLOGIAS,
                    tecnologiaService.listarActivas()
            );

            req.setAttribute(
                    Constants.ATTR_TITULO_PAGINA,
                    "HiPortafolio"
            );

            req.getRequestDispatcher(
                    Constants.VIEW_HOME
            ).forward(req, resp);

        } catch (ApplicationException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error en PortafolioServlet",
                    e
            );

            req.setAttribute(
                    Constants.ATTR_ERROR,
                    e.getMessage()
            );

            req.getRequestDispatcher(
                    Constants.VIEW_ERROR_500
            ).forward(req, resp);
        }
    }
}