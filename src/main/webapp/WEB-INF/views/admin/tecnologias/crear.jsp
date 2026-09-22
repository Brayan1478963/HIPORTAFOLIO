<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nueva Tecnología — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/tecnologias?accion=listar">Tecnologías</a><span class="sep">/</span><span>Nueva</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <div class="page-header"><div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">NUEVA TECNOLOGÍA</h1></div></div>
  <c:if test="${not empty error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="admin-panel" style="max-width:600px">
    <div class="admin-panel-header"><div class="admin-panel-title"><i class="bi bi-cpu"></i>DATOS DE LA TECNOLOGÍA</div></div>
    <div class="admin-panel-body">
      <form action="${pageContext.request.contextPath}/admin/tecnologias" method="post">
        <input type="hidden" name="accion" value="guardar">
        <div class="hp-form-grid cols-2">
          <div class="hp-field">
            <label class="hp-label">Nombre <span class="required">*</span></label>
            <input type="text" name="nombre" class="hp-input" required maxlength="100" placeholder="Ej: Java">
          </div>
          <div class="hp-field">
            <label class="hp-label">Nivel</label>
            <select name="nivel" class="hp-select">
              <option value="Básico">Básico</option>
              <option value="Intermedio" selected>Intermedio</option>
              <option value="Avanzado">Avanzado</option>
            </select>
          </div>
          <div class="hp-field">
            <label class="hp-label">Categoría (ID)</label>
            <input type="number" name="idCategoria" class="hp-input" placeholder="1=Backend, 2=Frontend...">
            <span class="hp-field-hint">1=Backend · 2=Frontend · 3=Base de datos · 4=Herramientas · 5=DevOps</span>
          </div>
          <div class="hp-field">
            <label class="hp-label">Orden</label>
            <input type="number" name="orden" class="hp-input" value="1" min="1">
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Descripción</label>
            <textarea name="descripcion" class="hp-textarea" rows="3" placeholder="Descripción de la tecnología..."></textarea>
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Ícono CSS (devicon)</label>
            <input type="text" name="icono" class="hp-input" placeholder="Ej: devicon-java-plain">
            <span class="hp-field-hint">Ver iconos en <strong>devicon.dev</strong></span>
          </div>
        </div>
        <div class="hp-form-actions">
          <button type="submit" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-save me-1"></i>GUARDAR</button>
          <a href="${pageContext.request.contextPath}/admin/tecnologias?accion=listar" class="topbar-btn"><i class="bi bi-x me-1"></i>CANCELAR</a>
        </div>
      </form>
    </div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-tecnologias')&&document.getElementById('nav-tecnologias').classList.add('active');</script>
</body></html>
