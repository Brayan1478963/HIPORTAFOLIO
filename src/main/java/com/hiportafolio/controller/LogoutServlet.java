package com.hiportafolio.controller;

import com.hiportafolio.util.Constants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * LogoutServlet - Cierra la sesión del usuario de forma segura.
 *
 * GET /logout → invalida la sesión y redirige al login
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class LogoutServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            String usuario = (String) session.getAttribute(Constants.SESSION_USER_NAME);
            session.invalidate();
            LOGGER.info("Sesión cerrada para: " + usuario);
        }

        // Limpiar cookies de sesión
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                    resp.addCookie(cookie);
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + Constants.URL_LOGIN + "?logout=true");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
