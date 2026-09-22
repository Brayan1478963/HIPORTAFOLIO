package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.model.Usuario;
import com.hiportafolio.service.UsuarioService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UsuarioServlet - CRUD de usuarios para el panel administrativo.
 *
 * GET  /admin/usuarios?accion=listar     → listar usuarios
 * GET  /admin/usuarios?accion=nueva      → formulario crear
 * GET  /admin/usuarios?accion=editar&id= → formulario editar
 * POST /admin/usuarios?accion=guardar    → crear usuario
 * POST /admin/usuarios?accion=actualizar → actualizar usuario
 * POST /admin/usuarios?accion=eliminar   → eliminar usuario
 * POST /admin/usuarios?accion=activar    → activar usuario
 * POST /admin/usuarios?accion=desactivar → desactivar usuario
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class UsuarioServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UsuarioServlet.class.getName());
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter(Constants.PARAM_ACCION);
        if (accion == null) accion = Constants.ACCION_LISTAR;

        try {
            switch (accion) {
                case Constants.ACCION_LISTAR -> {
                    req.setAttribute(Constants.ATTR_USUARIOS, usuarioService.listar());
                    req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Gestión de Usuarios");
                    req.getRequestDispatcher(Constants.VIEW_ADM_USR_LIST).forward(req, resp);
                }
                case Constants.ACCION_NUEVA -> {
                    req.setAttribute(Constants.ATTR_ROLES, usuarioService.listarRoles());
                    req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Nuevo Usuario");
                    req.getRequestDispatcher(Constants.VIEW_ADM_USR_CREAR).forward(req, resp);
                }
                case Constants.ACCION_EDITAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    if (id <= 0) { resp.sendRedirect(req.getContextPath() + Constants.URL_ADMIN_USUARIOS); return; }
                    req.setAttribute(Constants.ATTR_USUARIO, usuarioService.buscarPorId(id));
                    req.setAttribute(Constants.ATTR_ROLES,   usuarioService.listarRoles());
                    req.setAttribute(Constants.ATTR_TITULO_PAGINA, "Editar Usuario");
                    req.getRequestDispatcher(Constants.VIEW_ADM_USR_EDIT).forward(req, resp);
                }
                default -> resp.sendRedirect(req.getContextPath() + Constants.URL_ADMIN_USUARIOS);
            }
        } catch (ApplicationException e) {
            LOGGER.log(Level.SEVERE, "Error en UsuarioServlet GET", e);
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
            switch (accion != null ? accion : "") {
                case Constants.ACCION_GUARDAR -> {
                    usuarioService.registrarUsuario(
                        req.getParameter("nombre"),
                        req.getParameter("apellido"),
                        req.getParameter("correo"),
                        req.getParameter("password"),
                        ValidationUtil.parseIntSeguro(req.getParameter("idRol"))
                    );
                    resp.sendRedirect(ctx + Constants.URL_ADMIN_USUARIOS +
                        "?accion=listar&mensaje=" + encodeMsg(Constants.MSG_GUARDADO));
                }
                case Constants.ACCION_ACTUALIZAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    usuarioService.actualizarUsuario(
                        id,
                        req.getParameter("nombre"),
                        req.getParameter("apellido"),
                        req.getParameter("correo"),
                        req.getParameter("password"),
                        ValidationUtil.parseIntSeguro(req.getParameter("idRol"))
                    );
                    resp.sendRedirect(ctx + Constants.URL_ADMIN_USUARIOS +
                        "?accion=listar&mensaje=" + encodeMsg(Constants.MSG_ACTUALIZADO));
                }
                case Constants.ACCION_ELIMINAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    // Evitar que el admin elimine su propia cuenta
                    Usuario sesion = (Usuario) req.getSession().getAttribute(Constants.SESSION_USUARIO);
                    if (sesion != null && sesion.getIdUsuario() == id) {
                        resp.sendRedirect(ctx + Constants.URL_ADMIN_USUARIOS +
                            "?accion=listar&error=No puedes eliminar tu propia cuenta.");
                        return;
                    }
                    usuarioService.eliminarUsuario(id);
                    resp.sendRedirect(ctx + Constants.URL_ADMIN_USUARIOS +
                        "?accion=listar&mensaje=" + encodeMsg(Constants.MSG_ELIMINADO));
                }
                case Constants.ACCION_ACTIVAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    usuarioService.cambiarEstado(id, true);
                    resp.sendRedirect(ctx + Constants.URL_ADMIN_USUARIOS +
                        "?accion=listar&mensaje=" + encodeMsg(Constants.MSG_ACTIVADO));
                }
                case Constants.ACCION_DESACTIVAR -> {
                    int id = ValidationUtil.parseIntSeguro(req.getParameter(Constants.PARAM_ID));
                    usuarioService.cambiarEstado(id, false);
                    resp.sendRedirect(ctx + Constants.URL_ADMIN_USUARIOS +
                        "?accion=listar&mensaje=" + encodeMsg(Constants.MSG_DESACTIVADO));
                }
                default -> resp.sendRedirect(ctx + Constants.URL_ADMIN_USUARIOS);
            }
        } catch (ApplicationException e) {
            LOGGER.log(Level.WARNING, "Error en UsuarioServlet POST: " + e.getMessage());
            req.setAttribute(Constants.ATTR_ERROR, e.getMessage());
            try {
                req.setAttribute(Constants.ATTR_ROLES, usuarioService.listarRoles());
            } catch (ApplicationException ignored) {}
            req.getRequestDispatcher(
                Constants.ACCION_ACTUALIZAR.equals(accion)
                    ? Constants.VIEW_ADM_USR_EDIT
                    : Constants.VIEW_ADM_USR_CREAR
            ).forward(req, resp);
        }
    }

    private String encodeMsg(String msg) throws java.io.UnsupportedEncodingException {
        return java.net.URLEncoder.encode(msg, "UTF-8");
    }
}
