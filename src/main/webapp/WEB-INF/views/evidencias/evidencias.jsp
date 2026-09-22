<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Evidencias — HiPortafolio</title>
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
      <li><a href="${pageContext.request.contextPath}/unidades">Unidades</a></li>
      <li><a href="${pageContext.request.contextPath}/proyectos">Proyectos</a></li>
      <li><a href="${pageContext.request.contextPath}/tecnologias">Tecnologías</a></li>
      <li><a href="${pageContext.request.contextPath}/evidencias" class="active">Evidencias</a></li>
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
  <a href="${pageContext.request.contextPath}/evidencias" class="active">Evidencias</a>
  <a href="${pageContext.request.contextPath}/login">Acceder</a>
</div>

<div class="hp-page-content">
  <section style="background:var(--bg-secondary);border-bottom:1px solid var(--border-dim);padding:3rem 0">
    <div class="hp-container">
      <span class="hp-section-tag">// GALERÍA TECNOLÓGICA</span>
      <h1 style="font-family:var(--font-tech);font-size:clamp(1.5rem,4vw,2.5rem);font-weight:800;color:var(--text-primary);letter-spacing:3px;margin-top:0.5rem">
        EVIDENCIAS DE <span style="color:var(--green-main)">APRENDIZAJE</span>
      </h1>
      <p style="font-size:0.85rem;color:var(--text-secondary);margin-top:0.5rem">Documentación del proceso de aprendizaje durante el curso</p>
    </div>
  </section>

  <div class="hp-section">
    <div class="hp-container">
      <!-- Filtros -->
      <div class="hp-filter-bar reveal">
        <button class="hp-filter-btn active" data-filter="all">TODAS</button>
        <button class="hp-filter-btn" data-filter="imagen">IMÁGENES</button>
        <button class="hp-filter-btn" data-filter="pdf">PDF</button>
        <button class="hp-filter-btn" data-filter="documento">DOCUMENTOS</button>
        <button class="hp-filter-btn" data-filter="diagrama">DIAGRAMAS</button>
        <button class="hp-filter-btn" data-filter="captura">CAPTURAS</button>
      </div>

      <div class="hp-evidence-grid">
        <c:forEach var="ev" items="${lista}" varStatus="st">
        <div class="hp-evidence-card reveal" data-filter-item="${ev.tipo}" style="animation-delay:${st.index * 0.05}s">
          <div class="hp-evidence-type-bar" style="background:${ev.tipo eq 'pdf' ? '#FF4444' : ev.tipo eq 'diagrama' ? '#FFB400' : ev.tipo eq 'imagen' ? 'var(--green-tech)' : 'var(--green-main)'}"></div>
          <div class="hp-evidence-body">
            <div class="hp-evidence-meta">
              <span class="hp-badge ${ev.tipo eq 'pdf' ? 'hp-badge-red' : 'hp-badge-green'}">${ev.tipoEtiqueta}</span>
              <span style="font-size:0.65rem;color:var(--text-muted);font-family:var(--font-tech)">SEM.${ev.numeroSemana}</span>
            </div>
            <div class="hp-evidence-title">${ev.titulo}</div>
            <p class="hp-evidence-desc">${ev.descripcion}</p>
            <div style="font-size:0.7rem;color:var(--text-muted);margin-top:0.5rem;font-family:var(--font-tech);letter-spacing:1px">
              <i class="bi bi-bookmark me-1"></i>${fn:substring(ev.tituloSemana,0,35)}
            </div>
          </div>
          <c:if test="${ev.idArchivo > 0}">
          <div class="hp-evidence-footer">
            <a href="${pageContext.request.contextPath}/archivos/ver?id=${ev.idArchivo}" target="_blank" class="btn-secondary-hp btn-sm-hp">
              <i class="bi bi-eye"></i> VER
            </a>
            <a href="${pageContext.request.contextPath}/archivos/descargar?id=${ev.idArchivo}" class="btn-secondary-hp btn-sm-hp">
              <i class="bi bi-download"></i>
            </a>
          </div>
          </c:if>
        </div>
        </c:forEach>
        <c:if test="${empty lista}">
          <div style="grid-column:1/-1;text-align:center;padding:5rem 2rem">
            <i class="bi bi-camera-fill" style="font-size:3rem;color:var(--text-muted);display:block;margin-bottom:1rem;opacity:0.4"></i>
            <div style="font-family:var(--font-tech);font-size:0.7rem;letter-spacing:2px;color:var(--text-muted)">SIN EVIDENCIAS REGISTRADAS</div>
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
