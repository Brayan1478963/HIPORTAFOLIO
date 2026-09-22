<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Página de entrada: redirige automáticamente al portafolio público --%>
<% response.sendRedirect(request.getContextPath() + "/portafolio"); %>
