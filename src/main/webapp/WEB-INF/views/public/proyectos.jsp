<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Proyectos — HiPortafolio</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div id="hp-cursor"></div><div id="hp-cursor-ring"></div>
<nav class="hp-navbar scrolled">
  <div class="nav-inner">
    <a href="${pageContext.request.contextPath}/portafolio" class="hp-logo"><span class="logo-dot"></span>HIPORTAFOLIO</a>
    <ul class="hp-nav-links">
      <li><a href="${pageContext.request.contextPath}/portafolio">Inicio</a></li>
      <li><a href="${pageContext.request.contextPath}/portafolio?vista=sobre-mi">Sobre mí</a></li>
      <li><a href="${pageContext.request.contextPath}/unidades">Unidades</a></li>
      <li><a href="${pageContext.request.contextPath}/proyectos" class="active">Proyectos</a></li>
      <li><a href="${pageContext.request.contextPath}/tecnologias">Tecnologías</a></li>
      <li><a href="${pageContext.request.contextPath}/evidencias">Evidencias</a></li>
    </ul>
    <div class="hp-nav-actions">
      <button class="hp-theme-btn" id="hp-theme-btn" aria-label="Cambiar tema"><i class="bi bi-sun"></i></button>
      <button class="hp-sound-btn" id="hp-sound-btn" aria-label="Sonido"><i class="bi bi-volume-up"></i></button>
      <a href="${pageContext.request.contextPath}/login" class="btn-nav-login">ACCEDER</a>
      <button class="hp-hamburger" id="hp-hamburger" aria-label="Menú"><span></span><span></span><span></span></button>
    </div>
  </div>
</nav>
<div class="hp-mobile-menu" id="hp-mobile-menu">
  <a href="${pageContext.request.contextPath}/portafolio">Inicio</a>
  <a href="${pageContext.request.contextPath}/unidades">Unidades</a>
  <a href="${pageContext.request.contextPath}/proyectos" class="active">Proyectos</a>
  <a href="${pageContext.request.contextPath}/login">Acceder</a>
</div>

<div class="hp-page-content">
  <section style="background:var(--bg-secondary);border-bottom:1px solid var(--border-dim);padding:3rem 0">
    <div class="hp-container">
      <span class="hp-section-tag">// MISSION BOARD</span>
      <h1 style="font-family:var(--font-tech);font-size:clamp(1.5rem,4vw,2.5rem);font-weight:800;color:var(--text-primary);letter-spacing:3px;margin-top:0.5rem">
        PROYECTOS <span style="color:var(--green-main)">ACADÉMICOS</span>
      </h1>
      <p style="font-size:0.85rem;color:var(--text-secondary);margin-top:0.5rem">Proyectos desarrollados durante el curso de Arquitectura de Software</p>
    </div>
  </section>

  <div class="hp-section">
    <div class="hp-container">
      <div class="hp-project-grid">
        <c:forEach var="p" items="${proyectos}" varStatus="st">
        <div class="hp-mission-card reveal" style="animation-delay:${st.index * 0.1}s">
          <div class="hp-mission-header">
            <div>
              <span class="hp-mission-tag">// PROYECTO ${st.count}</span>
              <div class="hp-mission-title">${p.nombre}</div>
            </div>
            <span class="hp-status-badge ${p.estado}">${p.estadoEtiqueta}</span>
          </div>
          <div class="hp-mission-body">
            <p>${p.descripcion}</p>
            <c:if test="${not empty p.objetivo}">
              <div style="margin-top:1rem;padding:0.75rem;background:var(--bg-tertiary);border-radius:4px;border-left:2px solid var(--border-main)">
                <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:2px;color:var(--text-muted);margin-bottom:0.4rem">OBJETIVO</div>
                <p style="font-size:0.78rem;color:var(--text-secondary);margin:0;line-height:1.6">${p.objetivo}</p>
              </div>
            </c:if>
            <c:if test="${not empty p.arquitectura}">
              <div style="margin-top:0.75rem">
                <span class="hp-badge hp-badge-tech"><i class="bi bi-diagram-3 me-1"></i>${p.arquitectura}</span>
              </div>
            </c:if>
            <c:if test="${not empty p.tecnologias}">
              <div style="margin-top:0.75rem;display:flex;flex-wrap:wrap;gap:0.4rem">
                <c:forEach var="t" items="${p.tecnologias}">
                  <span class="hp-badge hp-badge-gray">${t.nombre}</span>
                </c:forEach>
              </div>
            </c:if>
          </div>
          <div class="hp-mission-footer">
            <c:if test="${not empty p.repositorio}">
              <a href="${p.repositorio}" target="_blank" rel="noopener" class="btn-secondary-hp btn-sm-hp">
                <i class="bi bi-github"></i> REPO
              </a>
            </c:if>
            <c:if test="${not empty p.demo}">
              <a href="${p.demo}" target="_blank" rel="noopener" class="btn-primary-hp btn-sm-hp">
                <i class="bi bi-play-circle"></i> DEMO
              </a>
            </c:if>
          </div>
        </div>
        </c:forEach>
        <c:if test="${empty proyectos}">
          <div style="grid-column:1/-1;text-align:center;padding:5rem 2rem">
            <i class="bi bi-kanban" style="font-size:3rem;color:var(--text-muted);display:block;margin-bottom:1rem;opacity:0.4"></i>
            <div style="font-family:var(--font-tech);font-size:0.7rem;letter-spacing:2px;color:var(--text-muted)">SIN PROYECTOS REGISTRADOS</div>
          </div>
        </c:if>
      </div>
    </div>
  </div>
</div>

<footer class="hp-footer"><div class="hp-container"><div class="hp-footer-inner">
  <div class="hp-footer-brand"><span style="width:8px;height:8px;background:var(--green-main);border-radius:50%;display:inline-block"></span>HIPORTAFOLIO</div>
  <div class="hp-footer-text">Brayan Apomayta Cuba · UPLA · Arquitectura de Software</div>
</div></div></footer>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body></html>
