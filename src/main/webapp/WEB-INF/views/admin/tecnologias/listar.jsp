<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Tecnologías — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a><span class="sep">/</span><span>Tecnologías</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <c:if test="${not empty param.mensaje}"><div class="hp-alert hp-alert-success"><i class="bi bi-check-circle-fill"></i><span>${param.mensaje}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="page-header">
    <div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">TECNOLOGÍAS</h1></div>
    <a href="${pageContext.request.contextPath}/admin/tecnologias?accion=nueva" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-plus-circle me-1"></i>NUEVA</a>
  </div>
  <div class="admin-panel">
    <div class="admin-panel-header">
      <div class="admin-panel-title"><i class="bi bi-cpu"></i>TECH MODULES</div>
      <div class="table-search"><i class="bi bi-search"></i><input type="text" class="table-search-input" data-table=".hp-table" placeholder="Buscar..."></div>
    </div>
    <div class="admin-panel-body-p0"><div class="hp-table-wrapper">
      <table class="hp-table">
        <thead><tr><th>Nombre</th><th>Categoría</th><th>Nivel</th><th>Orden</th><th>Estado</th><th style="text-align:right">Acciones</th></tr></thead>
        <tbody>
          <c:forEach var="t" items="${lista}">
          <tr>
            <td class="td-primary">${t.nombre}</td>
            <td><span class="hp-badge hp-badge-gray">${not empty t.nombreCategoria ? t.nombreCategoria : '—'}</span></td>
            <td><span class="hp-badge ${t.nivel eq 'Avanzado' ? 'hp-badge-green' : t.nivel eq 'Intermedio' ? 'hp-badge-tech' : 'hp-badge-gray'}">${not empty t.nivel ? t.nivel : '—'}</span></td>
            <td class="td-mono">${t.orden}</td>
            <td><span class="hp-badge ${t.estado ? 'hp-badge-green' : 'hp-badge-gray'}">${t.estado ? 'ACTIVA':'INACTIVA'}</span></td>
            <td><div class="table-actions">
              <a href="${pageContext.request.contextPath}/admin/tecnologias?accion=editar&id=${t.idTecnologia}" class="tbl-btn edit"><i class="bi bi-pencil"></i></a>
              <form method="post" action="${pageContext.request.contextPath}/admin/tecnologias" class="d-inline">
                <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="${t.idTecnologia}">
                <button class="tbl-btn delete" data-confirm="¿Eliminar tecnología ${t.nombre}?"><i class="bi bi-trash"></i></button>
              </form>
            </div></td>
          </tr>
          </c:forEach>
          <c:if test="${empty lista}"><tr><td colspan="6"><div class="hp-empty"><i class="bi bi-cpu"></i><h4>SIN TECNOLOGÍAS</h4></div></td></tr></c:if>
        </tbody>
      </table>
    </div></div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-tecnologias')&&document.getElementById('nav-tecnologias').classList.add('active');</script>
</body></html>
