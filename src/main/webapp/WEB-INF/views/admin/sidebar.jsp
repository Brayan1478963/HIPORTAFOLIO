<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%-- Sidebar + cursor Omnitrix para todas las páginas admin --%>
<div id="hp-cursor"></div>
<div id="hp-cursor-ring"></div>
<aside id="admin-sidebar" class="admin-sidebar" role="navigation" aria-label="Panel de administración">
  <div class="sidebar-header">
    <div class="sidebar-logo-icon"><span>HP</span></div>
    <div>
      <div class="sidebar-logo-text">HIPORTAFOLIO</div>
      <span class="sidebar-logo-sub">CONTROL CENTER</span>
    </div>
  </div>

  <nav class="sidebar-nav">
    <div class="sidebar-section-label">PRINCIPAL</div>

    <a href="${pageContext.request.contextPath}/admin/dashboard" class="sidebar-item" id="nav-dashboard">
      <i class="bi bi-speedometer2"></i><span class="item-label">Dashboard</span>
    </a>

    <div class="sidebar-section-label">GESTIÓN</div>

    <a href="${pageContext.request.contextPath}/admin/usuarios?accion=listar" class="sidebar-item" id="nav-usuarios">
      <i class="bi bi-people"></i><span class="item-label">Usuarios</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/unidades?accion=listar" class="sidebar-item" id="nav-unidades">
      <i class="bi bi-journal-bookmark"></i><span class="item-label">Unidades</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/semanas?accion=listar" class="sidebar-item" id="nav-semanas">
      <i class="bi bi-calendar3-week"></i><span class="item-label">Semanas</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/contenidos?accion=listar" class="sidebar-item" id="nav-contenidos">
      <i class="bi bi-file-text"></i><span class="item-label">Contenidos</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/evidencias?accion=listar" class="sidebar-item" id="nav-evidencias">
      <i class="bi bi-camera"></i><span class="item-label">Evidencias</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/archivos" class="sidebar-item" id="nav-archivos">
      <i class="bi bi-folder"></i><span class="item-label">Archivos</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/proyectos?accion=listar" class="sidebar-item" id="nav-proyectos">
      <i class="bi bi-kanban"></i><span class="item-label">Proyectos</span>
    </a>
    <a href="${pageContext.request.contextPath}/admin/tecnologias?accion=listar" class="sidebar-item" id="nav-tecnologias">
      <i class="bi bi-cpu"></i><span class="item-label">Tecnologías</span>
    </a>

    <div class="sidebar-section-label">ACCESOS</div>

    <a href="${pageContext.request.contextPath}/portafolio" class="sidebar-item" target="_blank">
      <i class="bi bi-eye"></i><span class="item-label">Ver Portafolio</span>
    </a>
    <a href="${pageContext.request.contextPath}/logout" class="sidebar-item" style="color:rgba(255,102,102,0.7)">
      <i class="bi bi-box-arrow-right" style="color:rgba(255,102,102,0.7)"></i><span class="item-label">Cerrar Sesión</span>
    </a>
  </nav>

  <div class="sidebar-footer">
    <button id="sidebar-collapse-btn" class="sidebar-collapse-btn" aria-label="Colapsar sidebar">
      <i class="bi bi-chevron-left"></i>
    </button>
  </div>
</aside>
<div id="admin-overlay" class="admin-overlay" aria-hidden="true"></div>
