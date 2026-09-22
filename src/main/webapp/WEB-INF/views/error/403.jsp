<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>403 — Acceso Denegado</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css"></head>
<body style="min-height:100vh;display:flex;align-items:center;justify-content:center;background:var(--bg-primary)">
<div style="text-align:center;padding:3rem">
  <div style="font-family:var(--font-tech);font-size:6rem;font-weight:800;color:rgba(255,68,68,0.15);line-height:1;margin-bottom:1rem">403</div>
  <div style="font-family:var(--font-tech);font-size:1.2rem;font-weight:700;color:#FF4444;letter-spacing:3px;margin-bottom:0.5rem">ACCESO DENEGADO</div>
  <div style="font-family:var(--font-tech);font-size:0.65rem;letter-spacing:2px;color:var(--text-muted);margin-bottom:2rem">NO TIENES PERMISOS PARA ACCEDER A ESTA SECCIÓN</div>
  <div style="width:60px;height:60px;border-radius:50%;background:rgba(255,68,68,0.1);border:2px solid rgba(255,68,68,0.4);display:flex;align-items:center;justify-content:center;margin:0 auto 2rem">
    <i class="bi bi-shield-lock-fill" style="font-size:1.8rem;color:#FF4444"></i>
  </div>
  <div style="display:flex;gap:1rem;justify-content:center;flex-wrap:wrap">
    <a href="${pageContext.request.contextPath}/portafolio" class="btn-primary-hp"><i class="bi bi-house me-1"></i>INICIO</a>
    <a href="${pageContext.request.contextPath}/login" class="btn-secondary-hp"><i class="bi bi-terminal me-1"></i>LOGIN</a>
  </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body></html>
