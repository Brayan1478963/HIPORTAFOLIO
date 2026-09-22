<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nuevo Contenido — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/contenidos?accion=listar">Contenidos</a><span class="sep">/</span><span>Nuevo</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <div class="page-header"><div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">NUEVO CONTENIDO</h1></div></div>
  <c:if test="${not empty error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="admin-panel">
    <div class="admin-panel-header"><div class="admin-panel-title"><i class="bi bi-file-plus"></i>DATOS DEL CONTENIDO</div></div>
    <div class="admin-panel-body">
      <form action="${pageContext.request.contextPath}/admin/contenidos" method="post">
        <input type="hidden" name="accion" value="guardar">
        <div class="hp-form-grid cols-2">
          <div class="hp-field">
            <label class="hp-label">Semana <span class="required">*</span></label>
            <select name="idSemana" class="hp-select" required>
              <option value="">-- Seleccionar --</option>
              <c:forEach var="s" items="${semanas}"><option value="${s.idSemana}">S${s.numeroFormateado} — ${fn:substring(s.titulo,0,40)}</option></c:forEach>
            </select>
          </div>
          <div class="hp-field">
            <label class="hp-label">Orden</label>
            <input type="number" name="orden" class="hp-input" value="1" min="1" max="99">
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Título <span class="required">*</span></label>
            <input type="text" name="titulo" class="hp-input" required maxlength="300" placeholder="Título del contenido">
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Descripción breve</label>
            <textarea name="descripcion" class="hp-textarea" rows="2" placeholder="Descripción corta..."></textarea>
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Contenido principal</label>
            <textarea name="contenido" class="hp-textarea" rows="10" placeholder="Desarrolla el contenido académico de la semana..."></textarea>
          </div>
          <div class="hp-field">
            <label class="hp-label">Aprendizaje obtenido</label>
            <textarea name="aprendizaje" class="hp-textarea" rows="4" placeholder="¿Qué aprendiste esta semana?"></textarea>
          </div>
          <div class="hp-field">
            <label class="hp-label">Reflexión personal</label>
            <textarea name="reflexion" class="hp-textarea" rows="4" placeholder="Tu reflexión sobre el tema..."></textarea>
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Referencias bibliográficas</label>
            <textarea name="referencias" class="hp-textarea" rows="3" placeholder="Libros, artículos, URLs..."></textarea>
          </div>
        </div>
        <div class="hp-form-actions">
          <button type="submit" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-save me-1"></i>GUARDAR</button>
          <a href="${pageContext.request.contextPath}/admin/contenidos?accion=listar" class="topbar-btn"><i class="bi bi-x me-1"></i>CANCELAR</a>
        </div>
      </form>
    </div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-contenidos')&&document.getElementById('nav-contenidos').classList.add('active');</script>
</body></html>
