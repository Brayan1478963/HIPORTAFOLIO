<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Editar Usuario — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body">
<div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left">
    <button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb">
      <a href="${pageContext.request.contextPath}/admin/usuarios?accion=listar">Usuarios</a>
      <span class="sep">/</span><span>Editar</span>
    </div>
  </div>
  <div class="topbar-right">
    <button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a>
  </div>
</header>
<main class="admin-content">
  <div class="page-header">
    <div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">EDITAR USUARIO</h1></div>
  </div>
  <c:if test="${not empty error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="admin-panel" style="max-width:640px">
    <div class="admin-panel-header">
      <div class="admin-panel-title"><i class="bi bi-pencil-square"></i>EDITAR: ${usuarioObj.nombre} ${usuarioObj.apellido}</div>
    </div>
    <div class="admin-panel-body">
      <form action="${pageContext.request.contextPath}/admin/usuarios" method="post">
        <input type="hidden" name="accion" value="actualizar">
        <input type="hidden" name="id" value="${usuarioObj.idUsuario}">
        <div class="hp-form-grid cols-2">
          <div class="hp-field">
            <label class="hp-label">Nombre <span class="required">*</span></label>
            <input type="text" name="nombre" class="hp-input" required value="${usuarioObj.nombre}">
          </div>
          <div class="hp-field">
            <label class="hp-label">Apellido <span class="required">*</span></label>
            <input type="text" name="apellido" class="hp-input" required value="${usuarioObj.apellido}">
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label">Correo <span class="required">*</span></label>
            <input type="email" name="correo" class="hp-input" required value="${usuarioObj.correo}">
          </div>
          <div class="hp-field">
            <label class="hp-label">Nueva Contraseña <span style="color:var(--text-muted);font-size:0.55rem">(opcional)</span></label>
            <div style="position:relative">
              <input type="password" id="pwd" name="password" class="hp-input" minlength="8" placeholder="Dejar vacío para no cambiar" style="padding-right:2.5rem">
              <button type="button" class="hp-pass-toggle" data-target="pwd" style="position:absolute;right:0.75rem;top:50%;transform:translateY(-50%);background:none;border:none;color:var(--text-muted);cursor:pointer">
                <i class="bi bi-eye"></i>
              </button>
            </div>
          </div>
          <div class="hp-field">
            <label class="hp-label">Rol <span class="required">*</span></label>
            <select name="idRol" class="hp-select" required>
              <c:forEach var="r" items="${roles}">
                <option value="${r.idRol}" ${r.idRol eq usuarioObj.idRol ? 'selected' : ''}>${r.nombre}</option>
              </c:forEach>
            </select>
          </div>
          <div class="hp-field hp-form-col-full">
            <div class="hp-toggle-wrap">
              <label class="hp-toggle">
                <input type="checkbox" name="estadoCheck" ${usuarioObj.estado ? 'checked' : ''}>
                <span class="hp-toggle-slider"></span>
              </label>
              <span class="hp-toggle-label">Usuario Activo</span>
            </div>
          </div>
        </div>
        <div class="hp-form-actions">
          <button type="submit" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-save me-1"></i>ACTUALIZAR</button>
          <a href="${pageContext.request.contextPath}/admin/usuarios?accion=listar" class="topbar-btn"><i class="bi bi-x me-1"></i>CANCELAR</a>
        </div>
      </form>
    </div>
  </div>
</main>
</div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-usuarios')&&document.getElementById('nav-usuarios').classList.add('active');</script>
</body></html>
