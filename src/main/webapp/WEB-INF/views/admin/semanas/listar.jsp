<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Semanas — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a><span class="sep">/</span><span>Semanas</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <c:if test="${not empty param.mensaje}"><div class="hp-alert hp-alert-success"><i class="bi bi-check-circle-fill"></i><span>${param.mensaje}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="page-header">
    <div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">SEMANAS</h1></div>
    <a href="${pageContext.request.contextPath}/admin/semanas?accion=nueva" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-plus-circle me-1"></i>NUEVA SEMANA</a>
  </div>
  <div class="admin-panel">
    <div class="admin-panel-header">
      <div class="admin-panel-title"><i class="bi bi-calendar3-week"></i>SEMANAS DEL CURSO</div>
      <div class="table-search"><i class="bi bi-search"></i><input type="text" class="table-search-input" data-table=".hp-table" placeholder="Buscar..."></div>
    </div>
    <div class="admin-panel-body-p0"><div class="hp-table-wrapper">
      <table class="hp-table">
        <thead><tr><th>N°</th><th>Título</th><th>Unidad</th><th>Estado</th><th style="text-align:right">Acciones</th></tr></thead>
        <tbody>
          <c:forEach var="s" items="${semanas}">
          <tr>
            <td><span class="hp-badge hp-badge-tech" style="font-size:0.6rem">S${s.numeroFormateado}</span></td>
            <td class="td-primary">${s.titulo}</td>
            <td><span class="hp-badge hp-badge-gray">UNIDAD ${s.idUnidad}</span></td>
            <td><span class="hp-badge ${s.estado ? 'hp-badge-green' : 'hp-badge-gray'}">${s.estado ? 'ACTIVA' : 'INACTIVA'}</span></td>
            <td><div class="table-actions">
              <a href="${pageContext.request.contextPath}/admin/semanas?accion=editar&id=${s.idSemana}" class="tbl-btn edit" title="Editar"><i class="bi bi-pencil"></i></a>
              <form method="post" action="${pageContext.request.contextPath}/admin/semanas" class="d-inline">
                <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="${s.idSemana}">
                <button class="tbl-btn delete" data-confirm="¿Eliminar semana ${s.numero}?" title="Eliminar"><i class="bi bi-trash"></i></button>
              </form>
            </div></td>
          </tr>
          </c:forEach>
          <c:if test="${empty semanas}"><tr><td colspan="5"><div class="hp-empty"><i class="bi bi-calendar3-week"></i><h4>SIN SEMANAS</h4></div></td></tr></c:if>
        </tbody>
      </table>
    </div></div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-semanas')&&document.getElementById('nav-semanas').classList.add('active');</script>
</body></html>
