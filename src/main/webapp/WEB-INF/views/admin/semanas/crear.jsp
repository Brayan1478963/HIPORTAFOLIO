<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nueva Semana — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/semanas?accion=listar">Semanas</a><span class="sep">/</span><span>Nueva</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <div class="page-header"><div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">NUEVA SEMANA</h1></div></div>
  <c:if test="${not empty error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="admin-panel" style="max-width:700px">
    <div class="admin-panel-header"><div class="admin-panel-title"><i class="bi bi-calendar-plus"></i>DATOS DE LA SEMANA</div></div>
    <div class="admin-panel-body">
      <form action="${pageContext.request.contextPath}/admin/semanas" method="post">
        <input type="hidden" name="accion" value="guardar">
        <div class="hp-form-grid cols-2">
          <div class="hp-field">
            <label class="hp-label">N° Semana <span class="required">*</span></label>
            <select name="numero" class="hp-select" required>
              <option value="">-- Seleccionar --</option>
              <c:forEach begin="1" end="16" var="n"><option value="${n}">Semana ${n < 10 ? '0' : ''}${n}</option></c:forEach>
            </select>
          </div>
          <div class="hp-field">
            <label class="hp-label">Unidad <span class="required">*</span></label>
            <select name="idUnidad" class="hp-select" required>
              <option value="">-- Seleccionar --</option>
              <c:forEach var="u" items="${unidades}"><option value="${u.idUnidad}">Unidad ${u.numeroARomano} — ${fn:substring(u.titulo,0,30)}</option></c:forEach>
            </select>
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Título <span class="required">*</span></label>
            <input type="text" name="titulo" class="hp-input" required maxlength="300" placeholder="Ej: Introducción a la Arquitectura de Software">
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Descripción</label>
            <textarea name="descripcion" class="hp-textarea" rows="3" placeholder="Descripción general de la semana..."></textarea>
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Objetivo</label>
            <textarea name="objetivo" class="hp-textarea" rows="3" placeholder="Objetivo de aprendizaje..."></textarea>
          </div>
        </div>
        <div class="hp-form-actions">
          <button type="submit" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-save me-1"></i>GUARDAR</button>
          <a href="${pageContext.request.contextPath}/admin/semanas?accion=listar" class="topbar-btn"><i class="bi bi-x me-1"></i>CANCELAR</a>
        </div>
      </form>
    </div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-semanas')&&document.getElementById('nav-semanas').classList.add('active');</script>
</body></html>
