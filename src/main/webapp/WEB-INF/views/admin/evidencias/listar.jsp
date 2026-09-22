<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Evidencias — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a><span class="sep">/</span><span>Evidencias</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <c:if test="${not empty param.mensaje}"><div class="hp-alert hp-alert-success"><i class="bi bi-check-circle-fill"></i><span>${param.mensaje}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="page-header">
    <div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">EVIDENCIAS</h1></div>
    <a href="${pageContext.request.contextPath}/admin/evidencias?accion=nueva" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-plus-circle me-1"></i>NUEVA</a>
  </div>
  <div class="admin-panel">
    <div class="admin-panel-header">
      <div class="admin-panel-title"><i class="bi bi-camera"></i>EVIDENCIAS DE APRENDIZAJE</div>
      <div class="table-search"><i class="bi bi-search"></i><input type="text" class="table-search-input" data-table=".hp-table" placeholder="Buscar..."></div>
    </div>
    <div class="admin-panel-body-p0"><div class="hp-table-wrapper">
      <table class="hp-table">
        <thead><tr><th>Título</th><th>Semana</th><th>Tipo</th><th>Fecha</th><th>Estado</th><th style="text-align:right">Acciones</th></tr></thead>
        <tbody>
          <c:forEach var="ev" items="${lista}">
          <tr>
            <td class="td-primary">${ev.titulo}</td>
            <td><span class="hp-badge hp-badge-tech" style="font-size:0.55rem">Sem.${ev.numeroSemana}</span></td>
            <td><span class="hp-badge ${ev.tipo eq 'pdf' ? 'hp-badge-red' : 'hp-badge-green'}">${ev.tipoEtiqueta}</span></td>
            <td style="font-size:0.75rem;color:var(--text-muted)">${ev.fechaEvidencia}</td>
            <td><span class="hp-badge ${ev.estado ? 'hp-badge-green' : 'hp-badge-gray'}">${ev.estado ? 'ACTIVA':'INACTIVA'}</span></td>
            <td><div class="table-actions">
              <a href="${pageContext.request.contextPath}/admin/evidencias?accion=editar&id=${ev.idEvidencia}" class="tbl-btn edit"><i class="bi bi-pencil"></i></a>
              <form method="post" action="${pageContext.request.contextPath}/admin/evidencias" class="d-inline">
                <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="${ev.idEvidencia}">
                <button class="tbl-btn delete" data-confirm="¿Eliminar evidencia?"><i class="bi bi-trash"></i></button>
              </form>
            </div></td>
          </tr>
          </c:forEach>
          <c:if test="${empty lista}"><tr><td colspan="6"><div class="hp-empty"><i class="bi bi-camera"></i><h4>SIN EVIDENCIAS</h4></div></td></tr></c:if>
        </tbody>
      </table>
    </div></div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-evidencias')&&document.getElementById('nav-evidencias').classList.add('active');</script>
</body></html>
