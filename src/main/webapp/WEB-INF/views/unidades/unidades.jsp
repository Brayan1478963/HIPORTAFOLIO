<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Unidades — HiPortafolio</title>
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
      <li><a href="${pageContext.request.contextPath}/unidades" class="active">Unidades</a></li>
      <li><a href="${pageContext.request.contextPath}/proyectos">Proyectos</a></li>
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
  <a href="${pageContext.request.contextPath}/unidades" class="active">Unidades</a>
  <a href="${pageContext.request.contextPath}/proyectos">Proyectos</a>
  <a href="${pageContext.request.contextPath}/login">Acceder</a>
</div>

<div class="hp-page-content">
  <section style="background:var(--bg-secondary);border-bottom:1px solid var(--border-dim);padding:3rem 0">
    <div class="hp-container">
      <span class="hp-section-tag">// NIVEL MAP</span>
      <h1 style="font-family:var(--font-tech);font-size:clamp(1.5rem,4vw,2.5rem);font-weight:800;color:var(--text-primary);letter-spacing:3px;margin-top:0.5rem">
        RUTA DE <span style="color:var(--green-main)">APRENDIZAJE</span>
      </h1>
      <p style="font-size:0.9rem;color:var(--text-secondary);margin-top:0.5rem">4 Unidades · 16 Semanas · Arquitectura de Software</p>
    </div>
  </section>

  <div class="hp-section">
    <div class="hp-container">
      <div class="hp-level-map">
        <c:forEach var="u" items="${unidades}" varStatus="st">
        <div class="hp-level-row reveal">
          <div class="hp-unit-card">
            <div class="hp-unit-badge">${u.numeroARomano}</div>
            <div class="hp-unit-num">UNIDAD ${u.numeroARomano}</div>
            <div class="hp-unit-title">${u.titulo}</div>
            <a href="${pageContext.request.contextPath}/unidades?accion=detalle&id=${u.idUnidad}"
               class="btn-primary-hp btn-sm-hp" style="margin-top:1rem">
              <i class="bi bi-arrow-right"></i> ENTRAR
            </a>
          </div>

          <!-- Nodos semanas -->
          <div class="hp-weeks-rail">
            <c:forEach var="s" items="${u.semanas}">
            <div class="hp-week-node">
              <a href="${pageContext.request.contextPath}/semanas?id=${s.idSemana}" class="hp-week-btn" title="${s.titulo}">
                ${s.numeroFormateado}
              </a>
              <span class="hp-week-label">${fn:substring(s.titulo,0,18)}</span>
            </div>
            </c:forEach>
            <c:if test="${empty u.semanas}">
              <span style="font-family:var(--font-tech);font-size:0.6rem;color:var(--text-muted);letter-spacing:1px">SIN SEMANAS</span>
            </c:if>
          </div>
        </div>

        <c:if test="${not st.last}">
          <div class="hp-level-connector" style="margin-bottom:0"></div>
        </c:if>
        </c:forEach>
      </div>
    </div>
  </div>
</div>

<footer class="hp-footer"><div class="hp-container"><div class="hp-footer-inner">
  <div class="hp-footer-brand"><span style="width:8px;height:8px;background:var(--green-main);border-radius:50%;display:inline-block"></span>HIPORTAFOLIO</div>
  <div class="hp-footer-text">Brayan Apomayta Cuba · UPLA</div>
</div></div></footer>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body></html>
