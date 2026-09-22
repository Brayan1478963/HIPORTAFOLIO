package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.model.Usuario;
import com.hiportafolio.service.ArchivoService;
import com.hiportafolio.service.EvidenciaService;
import com.hiportafolio.service.SemanaService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.FileUtil;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EvidenciaServlet - CRUD de evidencias (admin) y vista pública.
 * @author HiPortafolio
 */
@MultipartConfig(
        maxFileSize = 10485760,
        maxRequestSize = 20971520
)
public class EvidenciaServlet extends HttpServlet {

    private static final Logger LOGGER =
            Logger.getLogger(EvidenciaServlet.class.getName());

    private final EvidenciaService evidenciaService = new EvidenciaService();
    private final SemanaService semanaService = new SemanaService();
    private final ArchivoService archivoService = new ArchivoService();

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
                                evidenciaService.listar()
                        );

                        req.setAttribute(
                                Constants.ATTR_TITULO_PAGINA,
                                "Gestión de Evidencias"
                        );

                        req.getRequestDispatcher(
                                Constants.VIEW_ADM_EVI_LIST
                        ).forward(req, resp);
                    }

                    case Constants.ACCION_NUEVA -> {
                        req.setAttribute(
                                Constants.ATTR_SEMANAS,
                                semanaService.listar()
                        );

                        req.setAttribute(
                                Constants.ATTR_ARCHIVOS,
                                archivoService.listar()
                        );

                        req.setAttribute(
                                Constants.ATTR_TITULO_PAGINA,
                                "Nueva Evidencia"
                        );

                        req.getRequestDispatcher(
                                Constants.VIEW_ADM_EVI_CREAR
                        ).forward(req, resp);
                    }

                    case Constants.ACCION_EDITAR -> {
                        int id = ValidationUtil.parseIntSeguro(
                                req.getParameter(Constants.PARAM_ID)
                        );

                        req.setAttribute(
                                Constants.ATTR_ENTIDAD,
                                evidenciaService.buscarPorId(id)
                        );

                        req.setAttribute(
                                Constants.ATTR_SEMANAS,
                                semanaService.listar()
                        );

                        req.setAttribute(
                                Constants.ATTR_ARCHIVOS,
                                archivoService.listar()
                        );

                        req.setAttribute(
                                Constants.ATTR_TITULO_PAGINA,
                                "Editar Evidencia"
                        );

                        req.getRequestDispatcher(
                                Constants.VIEW_ADM_EVI_EDIT
                        ).forward(req, resp);
                    }

                    default -> resp.sendRedirect(
                            req.getContextPath() + "/admin/evidencias"
                    );
                }

            } else {

                // Vista pública
                String subAccion = req.getParameter(Constants.PARAM_ACCION);

                if (Constants.ACCION_DETALLE.equals(subAccion)) {

                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    req.setAttribute(
                            Constants.ATTR_EVIDENCIA,
                            evidenciaService.buscarPorId(id)
                    );

                    req.getRequestDispatcher(
                            Constants.VIEW_EVIDENCIA_DET
                    ).forward(req, resp);

                } else {

                    req.setAttribute(
                            Constants.ATTR_LISTA,
                            evidenciaService.listarActivas()
                    );

                    req.setAttribute(
                            Constants.ATTR_TITULO_PAGINA,
                            "Evidencias de Aprendizaje"
                    );

                    req.getRequestDispatcher(
                            Constants.VIEW_EVIDENCIAS_PUB
                    ).forward(req, resp);
                }
            }

        } catch (ApplicationException e) {

            LOGGER.log(
                    Level.SEVERE,
                    "Error en EvidenciaServlet GET",
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

                    // Si hay archivo adjunto, subirlo primero
                    Part part = req.getPart("archivo");

                    int idArchivo = 0;

                    if (part != null && part.getSize() > 0) {

                        String uploadPath = FileUtil.getRutaUpload(
                                getServletContext().getRealPath("/"),
                                "evidencias"
                        );

                        Usuario sesion = obtenerUsuarioSesion(req);

                        var archivo = archivoService.subirArchivo(
                                part,
                                uploadPath,
                                sesion != null ? sesion.getIdUsuario() : 0,
                                "evidencias"
                        );

                        idArchivo = archivo.getIdArchivo();
                    }

                    evidenciaService.crearEvidencia(
                            req.getParameter("titulo"),
                            req.getParameter("descripcion"),
                            req.getParameter("tipo"),
                            req.getParameter("idSemana"),
                            String.valueOf(idArchivo),
                            req.getParameter("fechaEvidencia")
                    );

                    resp.sendRedirect(
                            ctx
                            + "/admin/evidencias?accion=listar&mensaje="
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

                    evidenciaService.actualizarEvidencia(
                            id,
                            req.getParameter("titulo"),
                            req.getParameter("descripcion"),
                            req.getParameter("tipo"),
                            req.getParameter("idSemana"),
                            req.getParameter("idArchivo"),
                            req.getParameter("fechaEvidencia"),
                            estado
                    );

                    resp.sendRedirect(
                            ctx
                            + "/admin/evidencias?accion=listar&mensaje="
                            + encode(Constants.MSG_ACTUALIZADO)
                    );
                }

                case Constants.ACCION_ELIMINAR -> {

                    int id = ValidationUtil.parseIntSeguro(
                            req.getParameter(Constants.PARAM_ID)
                    );

                    evidenciaService.eliminarEvidencia(id);

                    resp.sendRedirect(
                            ctx
                            + "/admin/evidencias?accion=listar&mensaje="
                            + encode(Constants.MSG_ELIMINADO)
                    );
                }

                default -> resp.sendRedirect(
                        ctx + "/admin/evidencias"
                );
            }

        } catch (ApplicationException e) {

            LOGGER.log(
                    Level.WARNING,
                    "Error en EvidenciaServlet POST",
                    e
            );

            req.setAttribute(
                    Constants.ATTR_ERROR,
                    e.getMessage()
            );

            req.getRequestDispatcher(
                    Constants.VIEW_ADM_EVI_CREAR
            ).forward(req, resp);
        }
    }

    private Usuario obtenerUsuarioSesion(HttpServletRequest req) {

        Object obj = req.getSession(false) != null
                ? req.getSession(false).getAttribute(Constants.SESSION_USUARIO)
                : null;

        return obj instanceof Usuario u ? u : null;
    }

    private String encode(String texto) {
        return URLEncoder.encode(
                texto,
                StandardCharsets.UTF_8
        );
    }
}