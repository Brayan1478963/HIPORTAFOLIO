<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Usuarios — Admin</title>
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
      <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
      <span class="sep">/</span><span>Usuarios</span>
    </div>
  </div>
  <div class="topbar-right">
    <div class="topbar-user"><span class="topbar-user-dot"></span><span>${sessionScope.userName}</span></div>
    <button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a>
  </div>
</header>
<main class="admin-content">
  <c:if test="${not empty param.mensaje}"><div class="hp-alert hp-alert-success"><i class="bi bi-check-circle-fill"></i><span>${param.mensaje}</span><button class="hp-alert-close">×</button></div></c:if>
  <c:if test="${not empty param.error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${param.error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="page-header">
    <div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">USUARIOS</h1></div>
    <a href="${pageContext.request.contextPath}/admin/usuarios?accion=nueva" class="topbar-btn"><i class="bi bi-plus-circle me-1"></i>NUEVO USUARIO</a>
  </div>
  <div class="admin-panel">
    <div class="admin-panel-header">
      <div class="admin-panel-title"><i class="bi bi-people"></i>LISTA DE USUARIOS</div>
      <div class="table-search">
        <i class="bi bi-search"></i>
        <input type="text" class="table-search-input" data-table=".hp-table" placeholder="Buscar usuario...">
      </div>
    </div>
    <div class="admin-panel-body-p0">
      <div class="hp-table-wrapper">
        <table class="hp-table">
          <thead><tr><th>#</th><th>Nombre</th><th>Correo</th><th>Rol</th><th>Estado</th><th>Registrado</th><th style="text-align:right">Acciones</th></tr></thead>
          <tbody>
            <c:forEach var="u" items="${usuarios}" varStatus="st">
            <tr>
              <td class="td-mono">${st.count}</td>
              <td class="td-primary">${u.nombre} ${u.apellido}</td>
              <td><small>${u.correo}</small></td>
              <td><span class="hp-badge ${u.nombreRol eq 'ADMIN' ? 'hp-badge-red' : 'hp-badge-green'}">${u.nombreRol}</span></td>
              <td><span class="hp-badge ${u.estado ? 'hp-badge-green' : 'hp-badge-gray'}">${u.estado ? 'ACTIVO' : 'INACTIVO'}</span></td>
              <td style="font-size:0.75rem;color:var(--text-muted)">${u.fechaCreacion}</td>
              <td>
                <div class="table-actions">
                  <a href="${pageContext.request.contextPath}/admin/usuarios?accion=editar&id=${u.idUsuario}" class="tbl-btn edit" title="Editar"><i class="bi bi-pencil"></i></a>
                  <c:if test="${u.estado}">
                    <form method="post" action="${pageContext.request.contextPath}/admin/usuarios" class="d-inline">
                      <input type="hidden" name="accion" value="desactivar"><input type="hidden" name="id" value="${u.idUsuario}">
                      <button class="tbl-btn" title="Desactivar"><i class="bi bi-pause-circle"></i></button>
                    </form>
                  </c:if>
                  <c:if test="${!u.estado}">
                    <form method="post" action="${pageContext.request.contextPath}/admin/usuarios" class="d-inline">
                      <input type="hidden" name="accion" value="activar"><input type="hidden" name="id" value="${u.idUsuario}">
                      <button class="tbl-btn" title="Activar"><i class="bi bi-play-circle"></i></button>
                    </form>
                  </c:if>
                  <form method="post" action="${pageContext.request.contextPath}/admin/usuarios" class="d-inline">
                    <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="${u.idUsuario}">
                    <button class="tbl-btn delete" data-confirm="¿Eliminar usuario ${u.nombre}?" title="Eliminar"><i class="bi bi-trash"></i></button>
                  </form>
                </div>
              </td>
            </tr>
            </c:forEach>
            <c:if test="${empty usuarios}">
              <tr><td colspan="7"><div class="hp-empty"><i class="bi bi-people"></i><h4>SIN USUARIOS</h4><p>No hay usuarios registrados.</p></div></td></tr>
            </c:if>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</main>
</div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-usuarios')&&document.getElementById('nav-usuarios').classList.add('active');</script>
</body></html>
