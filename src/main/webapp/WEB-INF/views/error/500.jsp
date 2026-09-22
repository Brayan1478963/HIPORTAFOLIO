<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>500 — Error del sistema</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"></head>
<body style="min-height:100vh;display:flex;align-items:center;justify-content:center;background:var(--bg-primary)">
<div style="text-align:center;padding:3rem;max-width:500px">
  <div style="font-family:var(--font-tech);font-size:6rem;font-weight:800;color:rgba(255,68,68,0.08);line-height:1;margin-bottom:1rem">500</div>
  <div style="font-family:var(--font-tech);font-size:1.2rem;font-weight:700;color:#FF4444;letter-spacing:3px;margin-bottom:0.5rem">ERROR DEL SISTEMA</div>
  <div style="font-family:var(--font-tech);font-size:0.65rem;letter-spacing:2px;color:var(--text-muted);margin-bottom:2rem">OCURRIÓ UN ERROR INTERNO — INTENTA NUEVAMENTE</div>
  <div style="width:60px;height:60px;border-radius:50%;background:rgba(255,68,68,0.08);border:2px solid rgba(255,68,68,0.3);display:flex;align-items:center;justify-content:center;margin:0 auto 1.5rem">
    <i class="bi bi-exclamation-triangle-fill" style="font-size:1.8rem;color:#FF4444"></i>
  </div>
  <c:if test="${not empty error}">
    <div style="background:rgba(255,68,68,0.06);border:1px solid rgba(255,68,68,0.2);border-radius:6px;padding:0.75rem;margin-bottom:1.5rem;font-size:0.78rem;color:#FF8888;font-family:var(--font-tech);letter-spacing:1px">${error}</div>
  </c:if>
  <div style="display:flex;gap:1rem;justify-content:center;flex-wrap:wrap">
    <a href="${pageContext.request.contextPath}/portafolio" class="btn-primary-hp"><i class="bi bi-house me-1"></i>INICIO</a>
    <a href="javascript:history.back()" class="btn-secondary-hp"><i class="bi bi-arrow-left me-1"></i>VOLVER</a>
  </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body></html>
