package com.hiportafolio.filter;

import com.hiportafolio.model.Usuario;
import com.hiportafolio.util.Constants;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * AuthenticationFilter - Verifica que el usuario haya iniciado sesión.
 *
 * Responsabilidad: Proteger las rutas /admin/* y /usuario/* del acceso
 * sin autenticación. Si no hay sesión activa, redirige al login.
 *
 * Flujo:
 *   Request a /admin/* o /usuario/*
 *     ↓
 *   ¿Existe sesión con atributo "usuario"?
 *     SÍ → continúa la cadena de filtros
 *     NO → redirect /login?expired=true
 *
 * @author HiPortafolio
 * @version 1.0
 */
@WebFilter(filterName = "AuthenticationFilter",
           urlPatterns = {"/admin/*", "/usuario/*"})
public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  httpReq  = (HttpServletRequest)  request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession(false); // no crear sesión nueva
        boolean autenticado = false;

        if (session != null) {
            Object usuarioSesion = session.getAttribute(Constants.SESSION_USUARIO);
            autenticado = (usuarioSesion instanceof Usuario);
        }

        if (autenticado) {
            // Evitar cacheo de páginas protegidas
            httpResp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            httpResp.setHeader("Pragma", "no-cache");
            httpResp.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        } else {
            LOGGER.info("Acceso no autenticado a: " + httpReq.getRequestURI() + " → redirect login");
            String contextPath = httpReq.getContextPath();

            // Distinguir entre sesión expirada y primer acceso
            if (session != null) {
                httpResp.sendRedirect(contextPath + Constants.URL_LOGIN + "?expired=true");
            } else {
                httpResp.sendRedirect(contextPath + Constants.URL_LOGIN);
            }
        }
    }

    @Override
    public void destroy() {}
}
