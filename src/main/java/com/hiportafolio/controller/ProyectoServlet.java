package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.model.Proyecto;
import com.hiportafolio.service.ProyectoService;
import com.hiportafolio.service.TecnologiaService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ProyectoServlet - CRUD proyectos (admin) y vista pública.
 * @author HiPortafolio
 */
public class ProyectoServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(ProyectoServlet.class.getName());

    private final ProyectoService proyectoService = new ProyectoService();
    private final TecnologiaService tecnologiaService = new TecnologiaService();

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

                switch (accion) {

                    case Constants.ACCION_LISTAR -> {
                        req.setAttribute(
                                Constants.ATTR_LISTA,
                                proyectoService.listar()
                        );

                        req.setAttribute(
                                Constants.ATTR_TITULO_PAGINA,
                                "Gestión de Proyectos"
                        );

                        req.getRequestDispatcher(
                                Constants.VIEW_ADM_PROY_LIST
                        ).forward(req, resp);
                    }

                    case Constants.ACCION_NUEVA -> {
                        req.setAttribute(
                                Constants.ATTR_TECNOLOGIAS,
                                tecnologiaService.listarActivas()
                        );

                        req.setAttribute(
                                Constants.ATTR_TITULO_PAGINA,
                                "Nuevo Proyecto"
                        );

                        req.getRequestDispatcher(
                                Constants.VIEW_ADM_PROY_CREAR
                        ).forward(req, resp);
                    }

                    case Constants.ACCION_EDITAR -> {
                        int id = ValidationUtil.parseIntSeguro(
                                req.getParameter(Constants.PARAM_ID)
                        );

                        req.setAttribute(
                                Constants.ATTR_ENTIDAD,
                                proyectoService.buscarPorId(id)
                        );

                        req.setAttribute(
                                Constants.ATTR_TECNOLOGIAS,
                                tecnologiaService.listarActivas()
                        );

                        req.setAttribute(
                                Constants.ATTR_TITULO_PAGINA,
                                "Editar Proyecto"
                        );

                        req.getRequestDispatcher(
                                Constants.VIEW_ADM_PROY_EDIT
                        ).forward(req, resp);
                    }

                    default -> resp.sendRedirect(
                            req.getContextPath() + "/admin/proyectos"
                    );
                }

            } else {

                if (Constants.ACCION_DETALLE.equals(accion)) {

                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    req.setAttribute(
                            Constants.ATTR_PROYECTO,
                            proyectoService.buscarPorId(id)
                    );

                    req.getRequestDispatcher(
                            Constants.VIEW_PROYECTOS_PUB
                    ).forward(req, resp);

                } else {
                    // Vista pública: activos primero, si no hay mostrar todos
                    java.util.List<com.hiportafolio.model.Proyecto> pList = proyectoService.listarActivos();
                    if (pList.isEmpty()) pList = proyectoService.listar();
                    req.setAttribute(Constants.ATTR_PROYECTOS, pList);
                    req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Proyectos");
                    req.getRequestDispatcher(Constants.VIEW_PROYECTOS_PUB).forward(req, resp);
                }
            }

        } catch (ApplicationException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error en ProyectoServlet GET",
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
            List<Integer> tecIds =
                    parseTecnologias(req.getParameterValues("tecnologias"));

            switch (accion != null ? accion : "") {

                case Constants.ACCION_GUARDAR -> {

                    Proyecto p = construirProyecto(req, 0);

                    proyectoService.crearProyecto(
                            p,
                            tecIds
                    );

                    resp.sendRedirect(
                            ctx
                            + "/admin/proyectos?accion=listar&mensaje="
                            + encode(Constants.MSG_GUARDADO)
                    );
                }

                case Constants.ACCION_ACTUALIZAR -> {

                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    Proyecto p = construirProyecto(
                            req,
                            id
                    );

                    proyectoService.actualizarProyecto(
                            p,
                            tecIds
                    );

                    resp.sendRedirect(
                            ctx
                            + "/admin/proyectos?accion=listar&mensaje="
                            + encode(Constants.MSG_ACTUALIZADO)
                    );
                }

                case Constants.ACCION_ELIMINAR -> {

                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    proyectoService.eliminarProyecto(id);

                    resp.sendRedirect(
                            ctx
                            + "/admin/proyectos?accion=listar&mensaje="
                            + encode(Constants.MSG_ELIMINADO)
                    );
                }

                default -> resp.sendRedirect(
                        ctx + "/admin/proyectos"
                );
            }

        } catch (ApplicationException e) {

            LOGGER.log(
                    Level.WARNING,
                    "Error en ProyectoServlet POST",
                    e
            );

            req.setAttribute(
                    Constants.ATTR_ERROR,
                    e.getMessage()
            );

            req.getRequestDispatcher(
                    Constants.VIEW_ADM_PROY_CREAR
            ).forward(req, resp);
        }
    }

    private Proyecto construirProyecto(
            HttpServletRequest req,
            int id
    ) {

        Proyecto p = new Proyecto();

        p.setIdProyecto(id);
        p.setNombre(req.getParameter("nombre"));
        p.setDescripcion(req.getParameter("descripcion"));
        p.setObjetivo(req.getParameter("objetivo"));
        p.setProblema(req.getParameter("problema"));
        p.setArquitectura(req.getParameter("arquitectura"));
        p.setFuncionalidades(req.getParameter("funcionalidades"));
        p.setRepositorio(req.getParameter("repositorio"));
        p.setDemo(req.getParameter("demo"));
        p.setImagen(req.getParameter("imagen"));

        p.setEstado(
                req.getParameter("estado") != null
                        ? req.getParameter("estado")
                        : "en_desarrollo"
        );

        p.setOrden(
                ValidationUtil.parseIntSeguro(
                        req.getParameter("orden")
                )
        );

        p.setActivo(
                // Si el parámetro no llega (checkbox no marcado), default = true
                !"false".equals(req.getParameter("activo"))
        );

        String fechaInicio = req.getParameter("fechaInicio");

        if (!ValidationUtil.estaVacio(fechaInicio)) {
            try {
                p.setFechaInicio(
                        LocalDate.parse(fechaInicio)
                );
            } catch (Exception ignored) {
            }
        }

        String fechaFin = req.getParameter("fechaFin");

        if (!ValidationUtil.estaVacio(fechaFin)) {
            try {
                p.setFechaFin(
                        LocalDate.parse(fechaFin)
                );
            } catch (Exception ignored) {
            }
        }

        return p;
    }

    private List<Integer> parseTecnologias(String[] valores) {

        List<Integer> ids = new ArrayList<>();

        if (valores == null) {
            return ids;
        }

        for (String valor : valores) {

            int id = ValidationUtil.parseIntSeguro(valor);

            if (id > 0) {
                ids.add(id);
            }
        }

        return ids;
    }

    private String encode(String texto) {

        return URLEncoder.encode(
                texto,
                StandardCharsets.UTF_8
        );
    }
}