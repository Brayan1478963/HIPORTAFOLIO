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
 * AdminFilter - Verifica que el usuario autenticado tenga el rol ADMIN.
 *
 * Responsabilidad: Proteger el panel administrativo (/admin/*) de accesos
 * por usuarios con rol USUARIO. Asume que AuthenticationFilter ya verificó
 * que hay una sesión activa (se ejecuta después en la cadena).
 *
 * Flujo:
 *   Request a /admin/*  (ya pasó por AuthenticationFilter)
 *     ↓
 *   ¿usuario.getNombreRol() == "ADMIN"?
 *     SÍ → continúa
 *     NO → forward a 403.jsp
 *
 * @author HiPortafolio
 * @version 1.0
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(AdminFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  httpReq  = (HttpServletRequest)  request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession(false);
        boolean esAdmin = false;

        if (session != null) {
            Object obj = session.getAttribute(Constants.SESSION_USUARIO);
            if (obj instanceof Usuario usuario) {
                esAdmin = Constants.ROL_ADMIN.equalsIgnoreCase(usuario.getNombreRol());
            }
        }

        if (esAdmin) {
            chain.doFilter(request, response);
        } else {
            LOGGER.warning("Acceso denegado (no es ADMIN) a: " + httpReq.getRequestURI());
            // Forward a la página de error 403 (no redirect para preservar contexto)
            httpReq.setAttribute("mensajeError", Constants.ERR_NO_AUTORIZADO);
            httpReq.getRequestDispatcher(Constants.VIEW_ERROR_403)
                   .forward(httpReq, httpResp);
        }
    }

    @Override
    public void destroy() {}
}
