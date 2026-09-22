package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.service.ContenidoService;
import com.hiportafolio.service.SemanaService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ContenidoServlet - CRUD de contenidos educativos.
 * @author HiPortafolio
 */
public class ContenidoServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(ContenidoServlet.class.getName());

    private final ContenidoService contenidoService = new ContenidoService();
    private final SemanaService semanaService = new SemanaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter(Constants.PARAM_ACCION);

        if (accion == null) {
            accion = Constants.ACCION_LISTAR;
        }

        try {
            switch (accion) {

                case Constants.ACCION_LISTAR -> {
                    req.setAttribute(
                            Constants.ATTR_LISTA,
                            contenidoService.listar()
                    );

                    req.setAttribute(
                            Constants.ATTR_TITULO_PAGINA,
                            "Gestión de Contenidos"
                    );

                    req.getRequestDispatcher(
                            Constants.VIEW_ADM_CONT_LIST
                    ).forward(req, resp);
                }

                case Constants.ACCION_NUEVA -> {
                    // Carga segura de semanas con manejo defensivo para prevenir Error 500
                    try {
                        req.setAttribute(
                                Constants.ATTR_SEMANAS,
                                semanaService.listar()
                        );
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, "No se pudieron cargar las semanas, enviando lista vacía", e);
                        req.setAttribute(Constants.ATTR_SEMANAS, new ArrayList<>());
                    }

                    req.setAttribute(
                            Constants.ATTR_TITULO_PAGINA,
                            "Nuevo Contenido"
                    );

                    req.getRequestDispatcher(
                            Constants.VIEW_ADM_CONT_CREAR
                    ).forward(req, resp);
                }

                case Constants.ACCION_EDITAR -> {
                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    req.setAttribute(
                            Constants.ATTR_ENTIDAD,
                            contenidoService.buscarPorId(id)
                    );

                    // Carga segura de semanas también en edición
                    try {
                        req.setAttribute(
                                Constants.ATTR_SEMANAS,
                                semanaService.listar()
                        );
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING, "No se pudieron cargar las semanas en edición, enviando lista vacía", e);
                        req.setAttribute(Constants.ATTR_SEMANAS, new ArrayList<>());
                    }

                    req.setAttribute(
                            Constants.ATTR_TITULO_PAGINA,
                            "Editar Contenido"
                    );

                    req.getRequestDispatcher(
                            Constants.VIEW_ADM_CONT_EDIT
                    ).forward(req, resp);
                }

                default -> resp.sendRedirect(
                        req.getContextPath() + "/admin/contenidos"
                );
            }

        } catch (Exception e) { 
            
            // ESTO IMPRIMIRÁ EL ERROR EXACTO EN TU CONSOLA DE NETBEANS
            System.err.println("=== ERROR FATAL CAPTURADO EN CONTENIDO SERVLET GET ===");
            e.printStackTrace(); 
            
            LOGGER.log(
                    Level.SEVERE,
                    "Error crítico en ContenidoServlet GET",
                    e
            );

            req.setAttribute(
                    Constants.ATTR_ERROR,
                    e.getMessage() != null ? e.getMessage() : e.getClass().getName()
            );

            req.getRequestDispatcher(
                    Constants.VIEW_ERROR_500
            ).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter(Constants.PARAM_ACCION);
        String ctx = req.getContextPath();

        try {
            switch (accion != null ? accion : "") {

                case Constants.ACCION_GUARDAR -> {

                    contenidoService.crearContenido(
                            req.getParameter("titulo"),
                            req.getParameter("descripcion"),
                            req.getParameter("contenido"),
                            req.getParameter("aprendizaje"),
                            req.getParameter("reflexion"),
                            req.getParameter("referencias"),
                            req.getParameter("idSemana"),
                            req.getParameter("orden")
                    );

                    resp.sendRedirect(
                            ctx
                            + "/admin/contenidos?accion=listar&mensaje="
                            + encode(Constants.MSG_GUARDADO)
                    );
                }

                case Constants.ACCION_ACTUALIZAR -> {

                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    boolean estado =
                            "on".equals(req.getParameter("estado"))
                            || "true".equals(req.getParameter("estado"));

                    contenidoService.actualizarContenido(
                            id,
                            req.getParameter("titulo"),
                            req.getParameter("descripcion"),
                            req.getParameter("contenido"),
                            req.getParameter("aprendizaje"),
                            req.getParameter("reflexion"),
                            req.getParameter("referencias"),
                            req.getParameter("idSemana"),
                            req.getParameter("orden"),
                            estado
                    );

                    resp.sendRedirect(
                            ctx
                            + "/admin/contenidos?accion=listar&mensaje="
                            + encode(Constants.MSG_ACTUALIZADO)
                    );
                }

                case Constants.ACCION_ELIMINAR -> {

                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    contenidoService.eliminarContenido(id);

                    resp.sendRedirect(
                            ctx
                            + "/admin/contenidos?accion=listar&mensaje="
                            + encode(Constants.MSG_ELIMINADO)
                    );
                }

                default -> resp.sendRedirect(
                        ctx + "/admin/contenidos"
                );
            }

        } catch (Exception e) {

            System.err.println("=== ERROR FATAL CAPTURADO EN CONTENIDO SERVLET POST ===");
            e.printStackTrace();

            LOGGER.log(
                    Level.WARNING,
                    "Error en ContenidoServlet POST",
                    e
            );

            req.setAttribute(
                    Constants.ATTR_ERROR,
                    e.getMessage() != null ? e.getMessage() : e.getClass().getName()
            );

            try {
                req.setAttribute(Constants.ATTR_SEMANAS, semanaService.listar());
            } catch (Exception ex) {
                req.setAttribute(Constants.ATTR_SEMANAS, new ArrayList<>());
            }

            req.getRequestDispatcher(
                    Constants.VIEW_ADM_CONT_CREAR
            ).forward(req, resp);
        }
    }

    private String encode(String texto) {
        return URLEncoder.encode(
                texto,
                StandardCharsets.UTF_8
        );
    }
}