<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Editar Evidencia — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/evidencias?accion=listar">Evidencias</a><span class="sep">/</span><span>Editar</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <div class="page-header"><div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">EDITAR EVIDENCIA</h1></div></div>
  <c:if test="${not empty error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="admin-panel" style="max-width:700px">
    <div class="admin-panel-header"><div class="admin-panel-title"><i class="bi bi-pencil-square"></i>${entidad.titulo}</div></div>
    <div class="admin-panel-body">
      <form action="${pageContext.request.contextPath}/admin/evidencias" method="post">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="id" value="${entidad.idEvidencia}">
        <div class="hp-form-grid cols-2">
          <div class="hp-field">
            <label class="hp-label">Semana <span class="required">*</span></label>
            <select name="idSemana" class="hp-select" required>
              <c:forEach var="s" items="${semanas}"><option value="${s.idSemana}" ${s.idSemana eq entidad.idSemana ? 'selected':''}>S${s.numeroFormateado} — ${fn:substring(s.titulo,0,35)}</option></c:forEach>
            </select>
          </div>
          <div class="hp-field">
            <label class="hp-label">Tipo</label>
            <select name="tipo" class="hp-select">
              <option value="imagen"    ${entidad.tipo eq 'imagen'    ? 'selected':''}>Imagen</option>
              <option value="pdf"       ${entidad.tipo eq 'pdf'       ? 'selected':''}>PDF</option>
              <option value="documento" ${entidad.tipo eq 'documento' ? 'selected':''}>Documento</option>
              <option value="diagrama"  ${entidad.tipo eq 'diagrama'  ? 'selected':''}>Diagrama</option>
              <option value="captura"   ${entidad.tipo eq 'captura'   ? 'selected':''}>Captura</option>
            </select>
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Título <span class="required">*</span></label>
            <input type="text" name="titulo" class="hp-input" required value="${entidad.titulo}">
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Descripción</label>
            <textarea name="descripcion" class="hp-textarea" rows="3">${entidad.descripcion}</textarea>
          </div>
          <div class="hp-field">
            <label class="hp-label">Fecha</label>
            <input type="date" name="fechaEvidencia" class="hp-input" value="${entidad.fechaEvidencia}">
          </div>
          <div class="hp-field">
            <label class="hp-label">ID Archivo adjunto</label>
            <input type="number" name="idArchivo" class="hp-input" value="${entidad.idArchivo}" min="0">
            <span class="hp-field-hint">0 = sin archivo</span>
          </div>
          <div class="hp-field"><div class="hp-toggle-wrap">
            <label class="hp-toggle"><input type="checkbox" name="estado" ${entidad.estado ? 'checked':''}><span class="hp-toggle-slider"></span></label>
            <span class="hp-toggle-label">Activa</span>
          </div></div>
        </div>
        <div class="hp-form-actions">
          <button type="submit" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-save me-1"></i>ACTUALIZAR</button>
          <a href="${pageContext.request.contextPath}/admin/evidencias?accion=listar" class="topbar-btn"><i class="bi bi-x me-1"></i>CANCELAR</a>
        </div>
      </form>
    </div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-evidencias')&&document.getElementById('nav-evidencias').classList.add('active');</script>
</body></html>
