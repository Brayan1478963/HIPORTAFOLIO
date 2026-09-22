<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard — HiPortafolio Admin</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body class="admin-body">
<div id="hp-cursor"></div>
<div id="hp-cursor-ring"></div>
<div class="admin-layout">
  <%@ include file="sidebar.jsp" %>
  <div class="admin-main" id="admin-main">

    <!-- Topbar -->
    <header class="admin-topbar">
      <div class="topbar-left">
        <button id="sidebar-mobile-btn" class="topbar-btn" aria-label="Menú"><i class="bi bi-list fs-5"></i></button>
        <div class="topbar-title">DASHBOARD</div>
      </div>
      <div class="topbar-right">
        <div class="topbar-user">
          <span class="topbar-user-dot"></span>
          <span>${sessionScope.userName}</span>
          <span class="hp-badge hp-badge-green" style="font-size:0.5rem">${sessionScope.rol}</span>
        </div>
        <button class="topbar-btn" id="hp-theme-btn" aria-label="Cambiar tema"><i class="bi bi-sun"></i></button>
        <a href="${pageContext.request.contextPath}/portafolio" class="topbar-btn" target="_blank" title="Ver portafolio">
          <i class="bi bi-eye"></i>
        </a>
        <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger" title="Cerrar sesión">
          <i class="bi bi-box-arrow-right"></i>
        </a>
      </div>
    </header>

    <main class="admin-content">
      <!-- Page header -->
      <div class="page-header">
        <div class="page-header-left">
          <span class="page-tag">// CONTROL CENTER</span>
          <h1 class="page-title">DASHBOARD</h1>
          <p class="page-desc">Panel de administración — HiPortafolio</p>
        </div>
        <div style="display:flex;gap:0.5rem;flex-wrap:wrap">
          <a href="${pageContext.request.contextPath}/admin/contenidos?accion=nueva" class="topbar-btn">
            <i class="bi bi-plus-circle me-1"></i>NUEVO CONTENIDO
          </a>
        </div>
      </div>

      <!-- Stat cards -->
      <div class="admin-stat-grid">
        <a href="${pageContext.request.contextPath}/admin/usuarios?accion=listar" class="admin-stat-card">
          <div class="stat-card-icon"><i class="bi bi-people-fill"></i></div>
          <div class="stat-card-value">${stats.usuarios}</div>
          <div class="stat-card-label">USUARIOS</div>
          <div class="stat-card-action"><i class="bi bi-arrow-right me-1"></i>GESTIONAR</div>
        </a>
        <a href="${pageContext.request.contextPath}/admin/unidades?accion=listar" class="admin-stat-card">
          <div class="stat-card-icon"><i class="bi bi-journal-bookmark-fill"></i></div>
          <div class="stat-card-value">${stats.unidades}</div>
          <div class="stat-card-label">UNIDADES</div>
          <div class="stat-card-action"><i class="bi bi-arrow-right me-1"></i>GESTIONAR</div>
        </a>
        <a href="${pageContext.request.contextPath}/admin/semanas?accion=listar" class="admin-stat-card">
          <div class="stat-card-icon"><i class="bi bi-calendar3-week-fill"></i></div>
          <div class="stat-card-value">${stats.semanas}</div>
          <div class="stat-card-label">SEMANAS</div>
          <div class="stat-card-action"><i class="bi bi-arrow-right me-1"></i>GESTIONAR</div>
        </a>
        <a href="${pageContext.request.contextPath}/admin/contenidos?accion=listar" class="admin-stat-card">
          <div class="stat-card-icon"><i class="bi bi-file-text-fill"></i></div>
          <div class="stat-card-value">${stats.contenidos}</div>
          <div class="stat-card-label">CONTENIDOS</div>
          <div class="stat-card-action"><i class="bi bi-arrow-right me-1"></i>GESTIONAR</div>
        </a>
        <a href="${pageContext.request.contextPath}/admin/evidencias?accion=listar" class="admin-stat-card">
          <div class="stat-card-icon"><i class="bi bi-camera-fill"></i></div>
          <div class="stat-card-value">${stats.evidencias}</div>
          <div class="stat-card-label">EVIDENCIAS</div>
          <div class="stat-card-action"><i class="bi bi-arrow-right me-1"></i>GESTIONAR</div>
        </a>
        <a href="${pageContext.request.contextPath}/admin/proyectos?accion=listar" class="admin-stat-card">
          <div class="stat-card-icon"><i class="bi bi-kanban-fill"></i></div>
          <div class="stat-card-value">${stats.proyectos}</div>
          <div class="stat-card-label">PROYECTOS</div>
          <div class="stat-card-action"><i class="bi bi-arrow-right me-1"></i>GESTIONAR</div>
        </a>
        <a href="${pageContext.request.contextPath}/admin/tecnologias?accion=listar" class="admin-stat-card">
          <div class="stat-card-icon"><i class="bi bi-cpu-fill"></i></div>
          <div class="stat-card-value">${stats.tecnologias}</div>
          <div class="stat-card-label">TECNOLOGÍAS</div>
          <div class="stat-card-action"><i class="bi bi-arrow-right me-1"></i>GESTIONAR</div>
        </a>
        <a href="${pageContext.request.contextPath}/admin/archivos" class="admin-stat-card">
          <div class="stat-card-icon"><i class="bi bi-folder-fill"></i></div>
          <div class="stat-card-value">${stats.archivos}</div>
          <div class="stat-card-label">ARCHIVOS</div>
          <div class="stat-card-action"><i class="bi bi-arrow-right me-1"></i>GESTIONAR</div>
        </a>
      </div>

      <!-- Acciones rápidas -->
      <div class="admin-panel">
        <div class="admin-panel-header">
          <div class="admin-panel-title"><i class="bi bi-lightning-fill"></i>ACCIONES RÁPIDAS</div>
        </div>
        <div class="admin-panel-body">
          <div class="quick-actions-grid">
            <a href="${pageContext.request.contextPath}/admin/unidades?accion=nueva"   class="topbar-btn"><i class="bi bi-plus me-1"></i>Nueva Unidad</a>
            <a href="${pageContext.request.contextPath}/admin/semanas?accion=nueva"    class="topbar-btn"><i class="bi bi-plus me-1"></i>Nueva Semana</a>
            <a href="${pageContext.request.contextPath}/admin/contenidos?accion=nueva" class="topbar-btn"><i class="bi bi-plus me-1"></i>Nuevo Contenido</a>
            <a href="${pageContext.request.contextPath}/admin/evidencias?accion=nueva" class="topbar-btn"><i class="bi bi-plus me-1"></i>Nueva Evidencia</a>
            <a href="${pageContext.request.contextPath}/admin/proyectos?accion=nueva"  class="topbar-btn"><i class="bi bi-plus me-1"></i>Nuevo Proyecto</a>
            <a href="${pageContext.request.contextPath}/admin/usuarios?accion=nueva"   class="topbar-btn"><i class="bi bi-plus me-1"></i>Nuevo Usuario</a>
          </div>
        </div>
      </div>

    </main>
  </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>
  // Marcar dashboard activo
  document.getElementById('nav-dashboard') && document.getElementById('nav-dashboard').classList.add('active');
</script>
</body>
</html>
