package com.hiportafolio.controller;

import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.model.Usuario;
import com.hiportafolio.service.AuthService;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * LoginServlet - Controlador del proceso de autenticación.
 *
 * GET  /login → muestra el formulario de login
 * POST /login → procesa credenciales, crea sesión, redirige según rol
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class LoginServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Si ya hay sesión activa, redirigir según rol
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(Constants.SESSION_USUARIO) instanceof Usuario u) {
            redirigirPorRol(u, req, resp);
            return;
        }

        // Mostrar mensaje de sesión expirada si corresponde
        String expired = req.getParameter("expired");
        if ("true".equals(expired)) {
            req.setAttribute(Constants.ATTR_MENSAJE, Constants.ERR_SESSION_EXPIRED);
        }

        req.getRequestDispatcher(Constants.VIEW_LOGIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String correo   = ValidationUtil.limpiar(req.getParameter("correo"));
        String password = req.getParameter("password"); // no limpiar la contraseña

        // Validación básica de campos
        String errorValidacion = ValidationUtil.validarFormularioLogin(correo, password);
        if (errorValidacion != null) {
            req.setAttribute(Constants.ATTR_ERROR, errorValidacion);
            req.getRequestDispatcher(Constants.VIEW_LOGIN).forward(req, resp);
            return;
        }

        try {
            // Autenticar mediante el servicio
            Usuario usuario = authService.autenticar(correo, password);

            // Crear sesión y almacenar el usuario autenticado
            HttpSession session = req.getSession(true);
            session.invalidate(); // invalidar sesión anterior (prevención de session fixation)
            session = req.getSession(true);
            session.setAttribute(Constants.SESSION_USUARIO, usuario);
            session.setAttribute(Constants.SESSION_ROL,     usuario.getNombreRol());
            session.setAttribute(Constants.SESSION_USER_ID, usuario.getIdUsuario());
            session.setAttribute(Constants.SESSION_USER_NAME, usuario.getNombreCompleto());
            session.setMaxInactiveInterval(Constants.SESSION_TIMEOUT_SECONDS);

            LOGGER.info("Login exitoso: " + correo + " [" + usuario.getNombreRol() + "]");
            redirigirPorRol(usuario, req, resp);

        } catch (ApplicationException e) {
            LOGGER.log(Level.WARNING, "Login fallido para: " + correo + " → " + e.getMessage());
            req.setAttribute(Constants.ATTR_ERROR, e.getMessage());
            req.getRequestDispatcher(Constants.VIEW_LOGIN).forward(req, resp);
        }
    }

    /** Redirige al usuario según su rol después del login. */
    private void redirigirPorRol(Usuario usuario, HttpServletRequest req,
                                  HttpServletResponse resp) throws IOException {
        String ctx = req.getContextPath();
        if (Constants.ROL_ADMIN.equalsIgnoreCase(usuario.getNombreRol())) {
            resp.sendRedirect(ctx + Constants.URL_ADMIN);
        } else {
            resp.sendRedirect(ctx + Constants.URL_PORTAFOLIO);
        }
    }
}
