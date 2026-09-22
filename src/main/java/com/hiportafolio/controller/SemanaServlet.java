package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.model.Semana;
import com.hiportafolio.service.ContenidoService;
import com.hiportafolio.service.EvidenciaService;
import com.hiportafolio.service.SemanaService;
import com.hiportafolio.service.UnidadService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SemanaServlet - Maneja semanas en modo admin y público.
 *
 * PÚBLICO: GET /semanas?id=X
 * ADMIN:   CRUD /admin/semanas
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class SemanaServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(SemanaServlet.class.getName());

    private final SemanaService semanaService = new SemanaService();
    private final UnidadService unidadService = new UnidadService();
    private final ContenidoService contenidoService = new ContenidoService();
    private final EvidenciaService evidenciaService = new EvidenciaService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        boolean esAdmin = req.getRequestURI().contains("/admin/");
        String accion = req.getParameter(Constants.PARAM_ACCION);

        if (accion == null) {
            accion = Constants.ACCION_LISTAR;
        }

        try {
            if (esAdmin) {
                manejarAdminGet(req, resp, accion);
            } else {
                manejarPublicGet(req, resp);
            }

        } catch (ApplicationException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error en SemanaServlet GET",
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter(Constants.PARAM_ACCION);
        String ctx = req.getContextPath();

        try {
            switch (accion != null ? accion : "") {

                case Constants.ACCION_GUARDAR -> {

                    semanaService.crearSemana(
                            req.getParameter("numero"),
                            req.getParameter("titulo"),
                            req.getParameter("descripcion"),
                            req.getParameter("objetivo"),
                            req.getParameter("idUnidad")
                    );

                    resp.sendRedirect(
                            ctx
                            + Constants.URL_ADMIN_SEMANAS
                            + "?accion=listar&mensaje="
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

                    semanaService.actualizarSemana(
                            id,
                            req.getParameter("numero"),
                            req.getParameter("titulo"),
                            req.getParameter("descripcion"),
                            req.getParameter("objetivo"),
                            req.getParameter("idUnidad"),
                            estado
                    );

                    resp.sendRedirect(
                            ctx
                            + Constants.URL_ADMIN_SEMANAS
                            + "?accion=listar&mensaje="
                            + encode(Constants.MSG_ACTUALIZADO)
                    );
                }

                case Constants.ACCION_ELIMINAR -> {

                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    semanaService.eliminarSemana(id);

                    resp.sendRedirect(
                            ctx
                            + Constants.URL_ADMIN_SEMANAS
                            + "?accion=listar&mensaje="
                            + encode(Constants.MSG_ELIMINADO)
                    );
                }

                default -> resp.sendRedirect(
                        ctx + Constants.URL_ADMIN_SEMANAS
                );
            }

        } catch (ApplicationException e) {

            LOGGER.log(
                    Level.WARNING,
                    "Error en SemanaServlet POST",
                    e
            );

            req.setAttribute(
                    Constants.ATTR_ERROR,
                    e.getMessage()
            );

            req.getRequestDispatcher(
                    Constants.VIEW_ADM_SEM_CREAR
            ).forward(req, resp);
        }
    }

    private void manejarAdminGet(
            HttpServletRequest req,
            HttpServletResponse resp,
            String accion
    ) throws ApplicationException, ServletException, IOException {

        switch (accion) {

            case Constants.ACCION_LISTAR -> {

                req.setAttribute(
                        Constants.ATTR_SEMANAS,
                        semanaService.listar()
                );

                req.setAttribute(
                        Constants.ATTR_TITULO_PAGINA,
                        "Gestión de Semanas"
                );

                req.getRequestDispatcher(
                        Constants.VIEW_ADM_SEM_LIST
                ).forward(req, resp);
            }

            case Constants.ACCION_NUEVA -> {

                req.setAttribute(
                        Constants.ATTR_UNIDADES,
                        unidadService.listar()
                );

                req.setAttribute(
                        Constants.ATTR_TITULO_PAGINA,
                        "Nueva Semana"
                );

                req.getRequestDispatcher(
                        Constants.VIEW_ADM_SEM_CREAR
                ).forward(req, resp);
            }

            case Constants.ACCION_EDITAR -> {

                int id = ValidationUtil.parseIntSeguro(
                        req.getParameter(Constants.PARAM_ID)
                );

                req.setAttribute(
                        Constants.ATTR_SEMANA,
                        semanaService.buscarPorId(id)
                );

                req.setAttribute(
                        Constants.ATTR_UNIDADES,
                        unidadService.listar()
                );

                req.setAttribute(
                        Constants.ATTR_TITULO_PAGINA,
                        "Editar Semana"
                );

                req.getRequestDispatcher(
                        Constants.VIEW_ADM_SEM_EDIT
                ).forward(req, resp);
            }

            default -> resp.sendRedirect(
                    req.getContextPath()
                    + Constants.URL_ADMIN_SEMANAS
            );
        }
    }

    /**
     * Vista pública de una semana:
     * carga contenidos, evidencias y navegación.
     */
    private void manejarPublicGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ApplicationException, ServletException, IOException {

        int id = ValidationUtil.parseIntSeguro(
                req.getParameter(Constants.PARAM_ID)
        );

        if (id <= 0) {
            resp.sendRedirect(
                    req.getContextPath()
                    + Constants.URL_UNIDADES
            );
            return;
        }

        Semana semana = semanaService.buscarPorId(id);

        Semana anterior =
                semanaService.buscarAnterior(
                        semana.getNumero()
                );

        Semana siguiente =
                semanaService.buscarSiguiente(
                        semana.getNumero()
                );

        req.setAttribute(
                Constants.ATTR_SEMANA,
                semana
        );

        req.setAttribute(
                Constants.ATTR_CONTENIDOS,
                contenidoService.listarPorSemana(id)
        );

        req.setAttribute(
                Constants.ATTR_EVIDENCIAS,
                evidenciaService.listarPorSemana(id)
        );

        req.setAttribute(
                Constants.ATTR_SEMANA_ANT,
                anterior
        );

        req.setAttribute(
                Constants.ATTR_SEMANA_SIG,
                siguiente
        );

        req.setAttribute(
                Constants.ATTR_TITULO_PAGINA,
                semana.getTituloCompleto()
        );

        req.getRequestDispatcher(
                Constants.VIEW_SEMANA_DET
        ).forward(req, resp);
    }

    private String encode(String texto) {
        return URLEncoder.encode(
                texto,
                StandardCharsets.UTF_8
        );
    }
}