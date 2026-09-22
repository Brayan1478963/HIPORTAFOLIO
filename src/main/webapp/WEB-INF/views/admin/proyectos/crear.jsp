<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nuevo Proyecto — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/proyectos?accion=listar">Proyectos</a><span class="sep">/</span><span>Nuevo</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <div class="page-header"><div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">NUEVO PROYECTO</h1></div></div>
  <c:if test="${not empty error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="admin-panel">
    <div class="admin-panel-header"><div class="admin-panel-title"><i class="bi bi-kanban"></i>DATOS DEL PROYECTO</div></div>
    <div class="admin-panel-body">
      <form action="${pageContext.request.contextPath}/admin/proyectos" method="post">
        <input type="hidden" name="accion" value="guardar">
        <div class="hp-form-grid cols-2">
          <div class="hp-field">
            <label class="hp-label">Nombre <span class="required">*</span></label>
            <input type="text" name="nombre" class="hp-input" required maxlength="200" placeholder="Nombre del proyecto">
          </div>
          <div class="hp-field">
            <label class="hp-label">Estado</label>
            <select name="estado" class="hp-select">
              <option value="en_desarrollo">En Desarrollo</option>
              <option value="completado">Completado</option>
              <option value="pausado">Pausado</option>
            </select>
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Descripción</label>
            <textarea name="descripcion" class="hp-textarea" rows="3" placeholder="Descripción del proyecto..."></textarea>
          </div>
          <div class="hp-field">
            <label class="hp-label">Objetivo</label>
            <textarea name="objetivo" class="hp-textarea" rows="3" placeholder="Objetivo principal..."></textarea>
          </div>
          <div class="hp-field">
            <label class="hp-label">Problema que resuelve</label>
            <textarea name="problema" class="hp-textarea" rows="3" placeholder="Problema que resuelve..."></textarea>
          </div>
          <div class="hp-field">
            <label class="hp-label">Arquitectura</label>
            <input type="text" name="arquitectura" class="hp-input" placeholder="Ej: MVC por capas">
          </div>
          <div class="hp-field">
            <label class="hp-label">Funcionalidades</label>
            <textarea name="funcionalidades" class="hp-textarea" rows="3" placeholder="Lista de funcionalidades..."></textarea>
          </div>
          <div class="hp-field">
            <label class="hp-label">Repositorio (URL)</label>
            <input type="url" name="repositorio" class="hp-input" placeholder="https://github.com/...">
          </div>
          <div class="hp-field">
            <label class="hp-label">Demo (URL)</label>
            <input type="url" name="demo" class="hp-input" placeholder="https://...">
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Tecnologías</label>
            <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(160px,1fr));gap:0.5rem;padding:1rem;background:var(--bg-secondary);border-radius:6px;border:1px solid var(--border-dim)">
              <c:forEach var="t" items="${tecnologias}">
              <div class="hp-checkbox-wrap">
                <input type="checkbox" class="hp-checkbox" name="tecnologias" value="${t.idTecnologia}" id="tec${t.idTecnologia}">
                <label class="hp-checkbox-label" for="tec${t.idTecnologia}">${t.nombre}</label>
              </div>
              </c:forEach>
            </div>
          </div>
          <div class="hp-field"><div class="hp-toggle-wrap">
            <label class="hp-toggle"><input type="checkbox" name="activo" checked><span class="hp-toggle-slider"></span></label>
            <span class="hp-toggle-label">Visible en portafolio</span>
          </div></div>
        </div>
        <div class="hp-form-actions">
          <button type="submit" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-save me-1"></i>GUARDAR</button>
          <a href="${pageContext.request.contextPath}/admin/proyectos?accion=listar" class="topbar-btn"><i class="bi bi-x me-1"></i>CANCELAR</a>
        </div>
      </form>
    </div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-proyectos')&&document.getElementById('nav-proyectos').classList.add('active');</script>
</body></html>
