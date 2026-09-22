<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Proyectos — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a><span class="sep">/</span><span>Proyectos</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <c:if test="${not empty param.mensaje}"><div class="hp-alert hp-alert-success"><i class="bi bi-check-circle-fill"></i><span>${param.mensaje}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="page-header">
    <div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">PROYECTOS</h1></div>
    <a href="${pageContext.request.contextPath}/admin/proyectos?accion=nueva" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-plus-circle me-1"></i>NUEVO</a>
  </div>
  <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(300px,1fr));gap:1rem">
    <c:forEach var="p" items="${lista}">
    <div class="admin-panel" style="margin-bottom:0">
      <div class="admin-panel-header">
        <div class="admin-panel-title" style="font-size:0.7rem">${p.nombre}</div>
        <span class="hp-badge ${p.estado eq 'completado' ? 'hp-badge-green' : p.estado eq 'en_desarrollo' ? 'hp-badge-tech' : 'hp-badge-gray'}">${p.estadoEtiqueta}</span>
      </div>
      <div class="admin-panel-body">
        <p style="font-size:0.78rem;color:var(--text-secondary);margin-bottom:0.75rem">${fn:substring(p.descripcion,0,100)}</p>
        <c:if test="${not empty p.tecnologias}">
          <div style="display:flex;flex-wrap:wrap;gap:0.3rem;margin-bottom:0.75rem">
            <c:forEach var="t" items="${p.tecnologias}"><span class="hp-badge hp-badge-gray" style="font-size:0.5rem">${t.nombre}</span></c:forEach>
          </div>
        </c:if>
        <div style="display:flex;gap:0.5rem;flex-wrap:wrap">
          <a href="${pageContext.request.contextPath}/admin/proyectos?accion=editar&id=${p.idProyecto}" class="tbl-btn edit" title="Editar"><i class="bi bi-pencil"></i></a>
          <form method="post" action="${pageContext.request.contextPath}/admin/proyectos" class="d-inline">
            <input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="${p.idProyecto}">
            <button class="tbl-btn delete" data-confirm="¿Eliminar proyecto ${p.nombre}?"><i class="bi bi-trash"></i></button>
          </form>
          <c:if test="${not empty p.repositorio}"><a href="${p.repositorio}" target="_blank" class="tbl-btn" title="Repositorio"><i class="bi bi-github"></i></a></c:if>
        </div>
      </div>
    </div>
    </c:forEach>
    <c:if test="${empty lista}">
      <div style="grid-column:1/-1"><div class="hp-empty"><i class="bi bi-kanban"></i><h4>SIN PROYECTOS</h4></div></div>
    </c:if>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>document.getElementById('nav-proyectos')&&document.getElementById('nav-proyectos').classList.add('active');</script>
</body></html>
