<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nuevo Usuario — Admin</title>
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
      <span class="sep">/</span><span>Nuevo</span>
    </div>
  </div>
  <div class="topbar-right">
    <button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a>
  </div>
</header>
<main class="admin-content">
  <div class="page-header">
    <div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">NUEVO USUARIO</h1></div>
  </div>
  <c:if test="${not empty error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="admin-panel" style="max-width:640px">
    <div class="admin-panel-header">
      <div class="admin-panel-title"><i class="bi bi-person-plus"></i>DATOS DEL USUARIO</div>
    </div>
    <div class="admin-panel-body">
      <form action="${pageContext.request.contextPath}/admin/usuarios" method="post" novalidate>
        <input type="hidden" name="accion" value="guardar">
        <div class="hp-form-grid cols-2">
          <div class="hp-field">
            <label class="hp-label" for="nombre">Nombre <span class="required">*</span></label>
            <input type="text" id="nombre" name="nombre" class="hp-input" required maxlength="100" value="${param.nombre}" placeholder="Nombre">
          </div>
          <div class="hp-field">
            <label class="hp-label" for="apellido">Apellido <span class="required">*</span></label>
            <input type="text" id="apellido" name="apellido" class="hp-input" required maxlength="100" value="${param.apellido}" placeholder="Apellido">
          </div>
          <div class="hp-field hp-form-col-full">
            <label class="hp-label" for="correo">Correo <span class="required">*</span></label>
            <input type="email" id="correo" name="correo" class="hp-input" required maxlength="150" value="${param.correo}" placeholder="correo@ejemplo.com">
          </div>
          <div class="hp-field">
            <label class="hp-label" for="password">Contraseña <span class="required">*</span></label>
            <div style="position:relative">
              <input type="password" id="password" name="password" class="hp-input" required minlength="8" placeholder="Mín. 8 caracteres" style="padding-right:2.5rem">
              <button type="button" class="hp-pass-toggle" data-target="password" style="position:absolute;right:0.75rem;top:50%;transform:translateY(-50%);background:none;border:none;color:var(--text-muted);cursor:pointer">
                <i class="bi bi-eye"></i>
              </button>
            </div>
            <span class="hp-field-hint">Mínimo 8 caracteres, incluye letras y números</span>
          </div>
          <div class="hp-field">
            <label class="hp-label" for="idRol">Rol <span class="required">*</span></label>
            <select id="idRol" name="idRol" class="hp-select" required>
              <option value="">-- Seleccionar --</option>
              <c:forEach var="r" items="${roles}">
                <option value="${r.idRol}">${r.nombre}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="hp-form-actions">
          <button type="submit" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-save me-1"></i>GUARDAR</button>
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
