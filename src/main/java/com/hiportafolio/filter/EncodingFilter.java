package com.hiportafolio.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * EncodingFilter - UTF-8 encoding para peticiones/respuestas.
 * NO sobreescribe el Content-Type de recursos estáticos (CSS, JS, imágenes).
 */
@WebFilter(filterName = "EncodingFilter", urlPatterns = "/*")
public class EncodingFilter implements Filter {

    private String encoding = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String enc = filterConfig.getInitParameter("encoding");
        if (enc != null && !enc.isEmpty()) {
            this.encoding = enc;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  httpReq  = (HttpServletRequest)  request;
        HttpServletResponse httpResp = (HttpServletResponse) response;

        // Encoding en el request siempre
        if (httpReq.getCharacterEncoding() == null) {
            httpReq.setCharacterEncoding(encoding);
        }

        // Detectar si es un recurso estático (CSS, JS, imágenes, fuentes)
        String uri = httpReq.getRequestURI().toLowerCase();
        boolean isStatic = uri.endsWith(".css") || uri.endsWith(".js")
                || uri.endsWith(".png") || uri.endsWith(".jpg")
                || uri.endsWith(".jpeg") || uri.endsWith(".gif")
                || uri.endsWith(".svg") || uri.endsWith(".ico")
                || uri.endsWith(".woff") || uri.endsWith(".woff2")
                || uri.endsWith(".ttf") || uri.endsWith(".eot")
                || uri.endsWith(".map");

        if (!isStatic) {
            // Solo para JSPs/Servlets: cabeceras de seguridad y encoding de respuesta
            httpResp.setCharacterEncoding(encoding);
            httpResp.setHeader("X-Frame-Options", "SAMEORIGIN");
            httpResp.setHeader("X-XSS-Protection", "1; mode=block");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
