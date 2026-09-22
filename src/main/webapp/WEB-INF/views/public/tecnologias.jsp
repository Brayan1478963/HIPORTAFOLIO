<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Tecnologías — HiPortafolio</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/devicon.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div id="hp-cursor"></div><div id="hp-cursor-ring"></div>
<nav class="hp-navbar scrolled">
  <div class="nav-inner">
    <a href="${pageContext.request.contextPath}/portafolio" class="hp-logo"><span class="logo-dot"></span>HIPORTAFOLIO</a>
    <ul class="hp-nav-links">
      <li><a href="${pageContext.request.contextPath}/portafolio">Inicio</a></li>
      <li><a href="${pageContext.request.contextPath}/unidades">Unidades</a></li>
      <li><a href="${pageContext.request.contextPath}/proyectos">Proyectos</a></li>
      <li><a href="${pageContext.request.contextPath}/tecnologias" class="active">Tecnologías</a></li>
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
  <a href="${pageContext.request.contextPath}/tecnologias" class="active">Tecnologías</a>
  <a href="${pageContext.request.contextPath}/login">Acceder</a>
</div>

<div class="hp-page-content">
  <section style="background:var(--bg-secondary);border-bottom:1px solid var(--border-dim);padding:3rem 0">
    <div class="hp-container">
      <span class="hp-section-tag">// TECH MODULES UNLOCKED</span>
      <h1 style="font-family:var(--font-tech);font-size:clamp(1.5rem,4vw,2.5rem);font-weight:800;color:var(--text-primary);letter-spacing:3px;margin-top:0.5rem">
        STACK <span style="color:var(--green-main)">TECNOLÓGICO</span>
      </h1>
      <p style="font-size:0.85rem;color:var(--text-secondary);margin-top:0.5rem">Tecnologías utilizadas en el portafolio y el curso de Arquitectura de Software</p>
    </div>
  </section>

  <div class="hp-section">
    <div class="hp-container">
      <div class="hp-tech-grid reveal">
        <c:forEach var="t" items="${tecnologias}" varStatus="st">
        <div class="hp-tech-module" style="animation-delay:${st.index * 0.05}s" title="${t.descripcion}">
          <c:choose>
            <c:when test="${not empty t.icono}">
              <i class="${t.icono} colored hp-tech-icon"></i>
            </c:when>
            <c:otherwise>
              <i class="bi bi-cpu-fill hp-tech-icon" style="color:var(--green-tech)"></i>
            </c:otherwise>
          </c:choose>
          <span class="hp-tech-name">${t.nombre}</span>
          <c:choose>
            <c:when test="${t.nivel eq 'Avanzado'}"><span class="hp-tech-level avanzado">${t.nivel}</span></c:when>
            <c:when test="${t.nivel eq 'Intermedio'}"><span class="hp-tech-level intermedio">${t.nivel}</span></c:when>
            <c:otherwise><span class="hp-tech-level basico">${not empty t.nivel ? t.nivel : 'Básico'}</span></c:otherwise>
          </c:choose>
          <c:if test="${not empty t.descripcion}">
            <p style="font-size:0.65rem;color:var(--text-muted);margin-top:0.6rem;line-height:1.5;margin-bottom:0">${fn:substring(t.descripcion,0,60)}...</p>
          </c:if>
        </div>
        </c:forEach>
        <c:if test="${empty tecnologias}">
          <div style="grid-column:1/-1;text-align:center;padding:4rem;color:var(--text-muted);font-family:var(--font-tech);font-size:0.7rem;letter-spacing:2px">
            <i class="bi bi-cpu" style="font-size:2.5rem;display:block;margin-bottom:1rem;opacity:0.4"></i>SIN TECNOLOGÍAS
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
