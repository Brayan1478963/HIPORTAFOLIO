<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Semana ${semana.numeroFormateado} — HiPortafolio</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
  <style>
    .contenido-texto { line-height:1.8; color:var(--text-secondary); }
    .contenido-texto h2,.contenido-texto h3 { font-family:var(--font-tech); color:var(--green-main); margin:1.5rem 0 0.75rem; font-size:1rem; letter-spacing:1px; }
    .contenido-texto ul { padding-left:1.5rem; margin:0.5rem 0; }
    .contenido-texto li { margin-bottom:0.4rem; }
    .contenido-texto p  { margin-bottom:0.75rem; }
  </style>
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
  <section style="background:var(--bg-secondary);border-bottom:3px solid var(--green-dark);padding:2.5rem 0">
    <div class="hp-container">
      <nav aria-label="Breadcrumb" style="margin-bottom:1rem">
        <div style="display:flex;align-items:center;gap:0.5rem;font-family:var(--font-tech);font-size:0.6rem;color:var(--text-muted);letter-spacing:1px">
          <a href="${pageContext.request.contextPath}/unidades" style="color:var(--text-muted);text-decoration:none">UNIDADES</a>
          <i class="bi bi-chevron-right" style="font-size:0.5rem"></i>
          <a href="${pageContext.request.contextPath}/unidades?accion=detalle&id=${semana.idUnidad}" style="color:var(--text-muted);text-decoration:none">UNIDAD ${semana.idUnidad}</a>
          <i class="bi bi-chevron-right" style="font-size:0.5rem"></i>
          <span style="color:var(--green-main)">SEMANA ${semana.numeroFormateado}</span>
        </div>
      </nav>
      <div style="display:flex;align-items:center;gap:1.25rem;flex-wrap:wrap">
        <div style="width:56px;height:56px;border-radius:50%;background:var(--green-dark);border:2px solid var(--green-main);display:flex;align-items:center;justify-content:center;font-family:var(--font-tech);font-size:1rem;font-weight:800;color:var(--green-main);box-shadow:0 0 16px var(--green-glow);flex-shrink:0">
          ${semana.numeroFormateado}
        </div>
        <div>
          <div style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.4rem">SEMANA ${semana.numero} DE 16</div>
          <h1 style="font-family:var(--font-tech);font-size:clamp(0.9rem,2.5vw,1.4rem);font-weight:700;color:var(--text-primary);letter-spacing:2px">${semana.titulo}</h1>
        </div>
        <div style="margin-left:auto;text-align:right">
          <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:2px;color:var(--text-muted);margin-bottom:0.4rem">PROGRESO</div>
          <div style="width:160px;height:4px;background:var(--bg-tertiary);border-radius:2px;overflow:hidden">
            <div style="width:${(semana.numero / 16.0) * 100}%;height:100%;background:var(--green-main);box-shadow:0 0 6px var(--green-main)"></div>
          </div>
          <div style="font-family:var(--font-tech);font-size:0.55rem;color:var(--text-muted);margin-top:0.3rem">${semana.numero}/16</div>
        </div>
      </div>
    </div>
  </section>

  <div class="hp-section">
    <div class="hp-container">
      <div style="display:grid;grid-template-columns:1fr 300px;gap:2rem;align-items:start">
        <div>
          <c:if test="${not empty semana.objetivo}">
          <div style="background:var(--bg-card);border:1px solid var(--border-card);border-left:3px solid var(--green-main);border-radius:8px;padding:1.5rem;margin-bottom:1.5rem">
            <div style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.75rem"><i class="bi bi-bullseye me-2"></i>OBJETIVO DE LA SEMANA</div>
            <p style="font-size:0.9rem;color:var(--text-secondary);line-height:1.7;margin:0">${semana.objetivo}</p>
          </div>
          </c:if>

          <c:if test="${not empty semana.descripcion}">
          <div style="background:var(--bg-card);border:1px solid var(--border-card);border-radius:8px;padding:1.5rem;margin-bottom:1.5rem">
            <div style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.75rem"><i class="bi bi-info-circle me-2"></i>DESCRIPCIÓN</div>
            <p style="font-size:0.9rem;color:var(--text-secondary);line-height:1.7;margin:0">${semana.descripcion}</p>
          </div>
          </c:if>

          <c:if test="${not empty contenidos}">
          <h2 style="font-family:var(--font-tech);font-size:0.7rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:1.25rem">
            <i class="bi bi-file-text me-2"></i>CONTENIDOS
          </h2>
          <c:forEach var="cont" items="${contenidos}" varStatus="st">
          <div class="hp-card reveal" style="margin-bottom:1.25rem">
            <div style="font-family:var(--font-tech);font-size:0.7rem;font-weight:600;color:var(--green-main);margin-bottom:1rem;letter-spacing:1px">
              <i class="bi bi-bookmark-fill me-2"></i>${cont.titulo}
            </div>
            <c:if test="${not empty cont.descripcion}">
              <p style="font-size:0.85rem;color:var(--text-secondary);margin-bottom:1rem">${cont.descripcion}</p>
            </c:if>
            <c:if test="${not empty cont.contenido}">
              <div class="contenido-texto" style="font-size:0.875rem">${cont.contenido}</div>
            </c:if>
            <c:if test="${not empty cont.aprendizaje}">
            <div style="background:rgba(124,255,0,0.05);border:1px solid rgba(124,255,0,0.15);border-radius:6px;padding:1rem;margin-top:1rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:2px;color:var(--green-main);margin-bottom:0.5rem"><i class="bi bi-lightbulb me-1"></i>APRENDIZAJE OBTENIDO</div>
              <p style="font-size:0.82rem;color:var(--text-secondary);margin:0;line-height:1.7">${cont.aprendizaje}</p>
            </div>
            </c:if>
            <c:if test="${not empty cont.reflexion}">
            <div style="background:rgba(57,255,136,0.04);border:1px solid rgba(57,255,136,0.12);border-radius:6px;padding:1rem;margin-top:0.75rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:2px;color:var(--green-tech);margin-bottom:0.5rem"><i class="bi bi-chat-quote me-1"></i>REFLEXIÓN PERSONAL</div>
              <p style="font-size:0.82rem;color:var(--text-secondary);margin:0;line-height:1.7;font-style:italic">${cont.reflexion}</p>
            </div>
            </c:if>
            <c:if test="${not empty cont.referencias}">
            <div style="background:var(--bg-tertiary);border-radius:4px;padding:0.75rem;margin-top:0.75rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:2px;color:var(--text-muted);margin-bottom:0.4rem"><i class="bi bi-book me-1"></i>REFERENCIAS</div>
              <p style="font-size:0.75rem;color:var(--text-muted);margin:0;line-height:1.6">${cont.referencias}</p>
            </div>
            </c:if>
          </div>
          </c:forEach>
          </c:if>

          <c:if test="${not empty evidencias}">
          <h2 style="font-family:var(--font-tech);font-size:0.7rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:1.25rem;margin-top:2rem">
            <i class="bi bi-camera me-2"></i>EVIDENCIAS
          </h2>
          <div style="display:grid;grid-template-columns:repeat(auto-fill,minmax(240px,1fr));gap:1rem">
            <c:forEach var="ev" items="${evidencias}">
            <div class="hp-evidence-card">
              <div class="hp-evidence-type-bar"></div>
              <div class="hp-evidence-body">
                <div class="hp-evidence-meta">
                  <span class="hp-badge hp-badge-green" style="font-size:0.5rem">${ev.tipoEtiqueta}</span>
                  <span style="font-size:0.65rem;color:var(--text-muted)">${ev.fechaEvidencia}</span>
                </div>
                <div class="hp-evidence-title">${ev.titulo}</div>
                <p class="hp-evidence-desc">${ev.descripcion}</p>
              </div>
              <c:if test="${ev.idArchivo > 0}">
              <div class="hp-evidence-footer">
                <a href="${pageContext.request.contextPath}/archivos/ver?id=${ev.idArchivo}" target="_blank" class="btn-secondary-hp btn-sm-hp"><i class="bi bi-eye"></i> VER</a>
                <a href="${pageContext.request.contextPath}/archivos/descargar?id=${ev.idArchivo}" class="btn-secondary-hp btn-sm-hp"><i class="bi bi-download"></i></a>
              </div>
              </c:if>
            </div>
            </c:forEach>
          </div>
          </c:if>
        </div>

        <div style="position:sticky;top:80px">
          <div style="background:var(--bg-card);border:1px solid var(--border-card);border-radius:8px;padding:1.25rem">
            <div style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:1rem">INFO</div>
            <div style="display:flex;flex-direction:column;gap:0.75rem">
              <div style="display:flex;justify-content:space-between;border-bottom:1px solid var(--border-dim);padding-bottom:0.5rem">
                <span style="font-size:0.75rem;color:var(--text-muted);font-family:var(--font-tech)">SEMANA</span>
                <span style="font-family:var(--font-tech);font-size:0.9rem;font-weight:700;color:var(--green-main)">${semana.numeroFormateado}</span>
              </div>
              <div style="display:flex;justify-content:space-between;border-bottom:1px solid var(--border-dim);padding-bottom:0.5rem">
                <span style="font-size:0.75rem;color:var(--text-muted);font-family:var(--font-tech)">UNIDAD</span>
                <span style="font-family:var(--font-tech);font-size:0.8rem;color:var(--text-secondary)">${semana.idUnidad}</span>
              </div>
              <div style="display:flex;justify-content:space-between">
                <span style="font-size:0.75rem;color:var(--text-muted);font-family:var(--font-tech)">PROGRESO</span>
                <span style="font-family:var(--font-tech);font-size:0.8rem;color:var(--text-secondary)">${semana.numero}/16</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Navegación semanas -->
      <div style="display:flex;justify-content:space-between;align-items:center;margin-top:3rem;padding-top:2rem;border-top:1px solid var(--border-dim);flex-wrap:wrap;gap:1rem">
        <div>
          <c:if test="${not empty semanaAnterior}">
          <a href="${pageContext.request.contextPath}/semanas?id=${semanaAnterior.idSemana}" class="btn-secondary-hp">
            <i class="bi bi-arrow-left"></i> S${semanaAnterior.numeroFormateado} — ${fn:substring(semanaAnterior.titulo,0,25)}
          </a>
          </c:if>
        </div>
        <a href="${pageContext.request.contextPath}/unidades?accion=detalle&id=${semana.idUnidad}" class="btn-secondary-hp">
          <i class="bi bi-grid-3x3"></i> VOLVER A UNIDAD
        </a>
        <div>
          <c:if test="${not empty semanaSiguiente}">
          <a href="${pageContext.request.contextPath}/semanas?id=${semanaSiguiente.idSemana}" class="btn-secondary-hp">
            S${semanaSiguiente.numeroFormateado} — ${fn:substring(semanaSiguiente.titulo,0,25)} <i class="bi bi-arrow-right"></i>
          </a>
          </c:if>
        </div>
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
