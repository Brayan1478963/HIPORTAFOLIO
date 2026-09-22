<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Unidad ${unidad.numeroARomano} — HiPortafolio</title>
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
      <li><a href="${pageContext.request.contextPath}/unidades" class="active">Unidades</a></li>
      <li><a href="${pageContext.request.contextPath}/proyectos">Proyectos</a></li>
      <li><a href="${pageContext.request.contextPath}/tecnologias">Tecnologías</a></li>
    </ul>
    <div class="hp-nav-actions">
      <button class="hp-theme-btn" id="hp-theme-btn" aria-label="Cambiar tema"><i class="bi bi-sun"></i></button>
      <a href="${pageContext.request.contextPath}/login" class="btn-nav-login">ACCEDER</a>
      <button class="hp-hamburger" id="hp-hamburger" aria-label="Menú"><span></span><span></span><span></span></button>
    </div>
  </div>
</nav>
<div class="hp-mobile-menu" id="hp-mobile-menu">
  <a href="${pageContext.request.contextPath}/portafolio">Inicio</a>
  <a href="${pageContext.request.contextPath}/unidades">Unidades</a>
</div>

<div class="hp-page-content">
  <section style="background:var(--bg-secondary);border-bottom:1px solid var(--border-dim);padding:3rem 0">
    <div class="hp-container">
      <nav aria-label="Breadcrumb" style="margin-bottom:1rem">
        <div style="display:flex;align-items:center;gap:0.5rem;font-family:var(--font-tech);font-size:0.6rem;color:var(--text-muted);letter-spacing:1px">
          <a href="${pageContext.request.contextPath}/unidades" style="color:var(--text-muted);text-decoration:none">UNIDADES</a>
          <i class="bi bi-chevron-right" style="font-size:0.5rem"></i>
          <span style="color:var(--green-main)">UNIDAD ${unidad.numeroARomano}</span>
        </div>
      </nav>
      <div style="display:flex;align-items:center;gap:2rem;flex-wrap:wrap">
        <div style="font-family:var(--font-tech);font-size:4rem;font-weight:800;color:var(--green-main);opacity:0.3;line-height:1">${unidad.numeroARomano}</div>
        <div>
          <span class="hp-section-tag" style="display:block;margin-bottom:0.5rem">UNIDAD ${unidad.numeroARomano}</span>
          <h1 style="font-family:var(--font-tech);font-size:clamp(1rem,3vw,1.6rem);font-weight:700;color:var(--text-primary);letter-spacing:2px;line-height:1.3">${unidad.titulo}</h1>
          <p style="font-size:0.85rem;color:var(--text-secondary);margin-top:0.75rem;max-width:600px">${unidad.descripcion}</p>
        </div>
      </div>
    </div>
  </section>

  <div class="hp-section">
    <div class="hp-container">
      <div style="font-family:var(--font-tech);font-size:0.65rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:2rem">
        <i class="bi bi-calendar3-week me-2"></i>SEMANAS DE LA UNIDAD
      </div>
      <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(280px,1fr));gap:1.25rem">
        <c:forEach var="s" items="${semanas}" varStatus="st">
        <div class="hp-card reveal">
          <div style="display:flex;align-items:center;gap:1rem;margin-bottom:1rem">
            <div style="width:44px;height:44px;border-radius:50%;background:var(--bg-tertiary);border:2px solid var(--border-main);display:flex;align-items:center;justify-content:center;font-family:var(--font-tech);font-size:0.7rem;font-weight:700;color:var(--green-main);flex-shrink:0">
              ${s.numeroFormateado}
            </div>
            <div>
              <div style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:2px;color:var(--text-muted);text-transform:uppercase">SEMANA ${s.numero}</div>
              <div class="hp-card-title" style="margin-bottom:0;font-size:0.75rem">${s.titulo}</div>
            </div>
          </div>
          <c:if test="${not empty s.objetivo}">
            <div style="background:var(--bg-tertiary);border-radius:4px;padding:0.75rem;margin-bottom:1rem;border-left:2px solid var(--border-main)">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:2px;color:var(--text-muted);margin-bottom:0.3rem">OBJETIVO</div>
              <p style="font-size:0.75rem;color:var(--text-secondary);line-height:1.6;margin:0">${fn:substring(s.objetivo,0,120)}</p>
            </div>
          </c:if>
          <a href="${pageContext.request.contextPath}/semanas?id=${s.idSemana}" class="btn-primary-hp btn-sm-hp">
            <i class="bi bi-arrow-right-circle"></i> VER CONTENIDO
          </a>
        </div>
        </c:forEach>
        <c:if test="${empty semanas}">
          <div style="grid-column:1/-1;text-align:center;padding:3rem;color:var(--text-muted);font-family:var(--font-tech);font-size:0.7rem;letter-spacing:2px">
            <i class="bi bi-calendar-x" style="font-size:2rem;display:block;margin-bottom:1rem;opacity:0.4"></i>
            SIN SEMANAS REGISTRADAS
          </div>
        </c:if>
      </div>
      <div style="margin-top:2rem">
        <a href="${pageContext.request.contextPath}/unidades" class="btn-secondary-hp">
          <i class="bi bi-arrow-left"></i> VOLVER A UNIDADES
        </a>
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
