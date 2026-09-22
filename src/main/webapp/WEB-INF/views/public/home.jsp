<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>HiPortafolio — Brayan Apomayta Cuba</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/devicon.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css?v=20260922-5">
</head>
<body>

<!-- CURSOR -->
<div id="hp-cursor"></div>
<div id="hp-cursor-ring"></div>

<!-- INTRO SPLASH -->
<div id="hp-intro">
  <div class="intro-core"></div>
  <div class="intro-title">HIPORTAFOLIO</div>
  <div class="intro-text" id="intro-line-1"></div>
  <div class="intro-text" id="intro-line-2"></div>
  <div class="intro-text" id="intro-line-3"></div>
  <div class="intro-text" id="intro-line-4"></div>
  <button class="intro-skip" onclick="document.getElementById('hp-intro').classList.add('hidden');sessionStorage.setItem('hp-intro-seen','1')">OMITIR</button>
</div>

<!-- NAVBAR -->
<nav class="hp-navbar" role="navigation" aria-label="Navegación principal">
  <div class="nav-inner">
    <a href="${pageContext.request.contextPath}/portafolio" class="hp-logo" aria-label="HiPortafolio inicio">
      <span class="logo-dot" aria-hidden="true"></span>
      HIPORTAFOLIO
    </a>

    <ul class="hp-nav-links" role="list">
      <li><a href="${pageContext.request.contextPath}/portafolio" class="active">Inicio</a></li>
      <li><a href="${pageContext.request.contextPath}/portafolio?vista=sobre-mi">Sobre mí</a></li>
      <li><a href="${pageContext.request.contextPath}/unidades">Unidades</a></li>
      <li><a href="${pageContext.request.contextPath}/proyectos">Proyectos</a></li>
      <li><a href="${pageContext.request.contextPath}/tecnologias">Tecnologías</a></li>
      <li><a href="${pageContext.request.contextPath}/evidencias">Evidencias</a></li>
    </ul>

    <div class="hp-nav-actions">
      <button class="hp-theme-btn" id="hp-theme-btn" aria-label="Cambiar tema">
        <i class="bi bi-sun"></i>
      </button>

      <button class="hp-sound-btn" id="hp-sound-btn" aria-label="Activar sonido">
        <i class="bi bi-volume-up"></i>
      </button>

      <a href="${pageContext.request.contextPath}/login" class="btn-nav-login">
        <i class="bi bi-terminal me-1"></i>ACCEDER
      </a>

      <button class="hp-hamburger" id="hp-hamburger" aria-label="Menú" aria-expanded="false">
        <span></span>
        <span></span>
        <span></span>
      </button>
    </div>
  </div>
</nav>

<!-- MOBILE MENU -->
<div class="hp-mobile-menu" id="hp-mobile-menu" role="navigation">
  <a href="${pageContext.request.contextPath}/portafolio">Inicio</a>
  <a href="${pageContext.request.contextPath}/portafolio?vista=sobre-mi">Sobre mí</a>
  <a href="${pageContext.request.contextPath}/unidades">Unidades</a>
  <a href="${pageContext.request.contextPath}/proyectos">Proyectos</a>
  <a href="${pageContext.request.contextPath}/tecnologias">Tecnologías</a>
  <a href="${pageContext.request.contextPath}/evidencias">Evidencias</a>
  <a href="${pageContext.request.contextPath}/login">Acceder</a>
</div>

<!-- HERO -->
<section class="hp-hero" id="inicio" aria-label="Presentación">
  <div class="hp-container">
    <div class="hp-hero-inner">

      <!-- Texto -->
      <div class="hp-hero-text">

        <div class="hp-hero-tag">
          Portafolio Académico · UPLA · 2026
        </div>

        <h1 class="hp-hero-name">
          <span class="accent">BRAYAN</span><br>
          APOMAYTA CUBA
        </h1>

        <p class="hp-hero-role">
          Ingeniería de Sistemas y Computación
        </p>

        <p class="hp-hero-desc">
          Hola, soy Brayan. Este es mi portafolio de Arquitectura de Software —
          un sistema web que documenta mi aprendizaje a través de 4 unidades
          y 16 semanas del curso.
        </p>

        <div class="hp-hero-actions">

          <a href="${pageContext.request.contextPath}/unidades" class="btn-primary-hp">
            <i class="bi bi-journal-bookmark"></i>
            EXPLORAR PORTAFOLIO
          </a>

          <a href="${pageContext.request.contextPath}/proyectos" class="btn-secondary-hp">
            <i class="bi bi-kanban"></i>
            VER PROYECTOS
          </a>

        </div>

        <div class="hp-hero-meta">

          <div class="hp-hero-meta-item">
            <i class="bi bi-building" aria-hidden="true"></i>
            UPLA
          </div>

          <div class="hp-hero-meta-item">
            <i class="bi bi-mortarboard" aria-hidden="true"></i>
            VIII Ciclo
          </div>

          <div class="hp-hero-meta-item">
            <i class="bi bi-geo-alt" aria-hidden="true"></i>
            Huancayo, Perú
          </div>

        </div>

      </div>

      <!-- OMNITRIX SVG -->
      <div class="hp-omnitrix" aria-hidden="true">

        <svg width="580" height="380" viewBox="0 0 580 380">

          <defs>

            <radialGradient id="coreGrad" cx="50%" cy="40%" r="50%">
              <stop offset="0%" stop-color="#7CFF00" stop-opacity="1"/>
              <stop offset="40%" stop-color="#39FF88" stop-opacity="0.8"/>
              <stop offset="100%" stop-color="#126B35" stop-opacity="0.3"/>
            </radialGradient>

            <radialGradient id="glowGrad" cx="50%" cy="50%" r="50%">
              <stop offset="0%" stop-color="#7CFF00" stop-opacity="0.15"/>
              <stop offset="100%" stop-color="#7CFF00" stop-opacity="0"/>
            </radialGradient>

            <radialGradient id="alienPlatformGrad" cx="50%" cy="45%" r="60%">
              <stop offset="0%" stop-color="#F4FFF0" stop-opacity="0.95"/>
              <stop offset="28%" stop-color="#7CFF00" stop-opacity="0.9"/>
              <stop offset="100%" stop-color="#39FF88" stop-opacity="0"/>
            </radialGradient>

            <filter id="glow">

              <feGaussianBlur
                stdDeviation="3"
                result="blur"/>

              <feMerge>
                <feMergeNode in="blur"/>
                <feMergeNode in="SourceGraphic"/>
              </feMerge>

            </filter>

          </defs>

          <!-- Glow background -->
          <circle
            cx="190"
            cy="190"
            r="170"
            fill="url(#glowGrad)"
          />

          <!-- Anillo exterior -->
          <g
            class="omni-ring-outer"
            style="transform-origin:190px 190px"
          >

            <circle
              cx="190"
              cy="190"
              r="155"
              fill="none"
              stroke="rgba(124,255,0,0.15)"
              stroke-width="1"
            />

            <c:forEach begin="0" end="23" var="i">

              <line
                x1="190"
                y1="36"
                x2="190"
                y2="44"
                stroke="rgba(124,255,0,0.4)"
                stroke-width="1.5"
                transform="rotate(${i * 15} 190 190)"
              />

            </c:forEach>

            <!-- Segmentos arco -->
            <path
              d="M190,40 A150,150 0 0,1 320,130"
              fill="none"
              stroke="rgba(124,255,0,0.5)"
              stroke-width="2"
              stroke-dasharray="8,4"
            />

            <path
              d="M320,250 A150,150 0 0,1 190,340"
              fill="none"
              stroke="rgba(124,255,0,0.5)"
              stroke-width="2"
              stroke-dasharray="8,4"
            />

            <path
              d="M60,250 A150,150 0 0,1 190,40"
              fill="none"
              stroke="rgba(124,255,0,0.3)"
              stroke-width="1"
              stroke-dasharray="4,8"
            />

          </g>

          <!-- Anillo medio -->
          <g
            class="omni-ring-mid"
            style="transform-origin:190px 190px"
          >

            <circle
              cx="190"
              cy="190"
              r="120"
              fill="none"
              stroke="rgba(57,255,136,0.2)"
              stroke-width="1.5"
              stroke-dasharray="6,3"
            />

            <circle
              cx="190"
              cy="70"
              r="5"
              fill="rgba(57,255,136,0.6)"
              filter="url(#glow)"
            />

            <circle
              cx="310"
              cy="190"
              r="5"
              fill="rgba(57,255,136,0.6)"
              filter="url(#glow)"
            />

            <circle
              cx="190"
              cy="310"
              r="5"
              fill="rgba(57,255,136,0.6)"
              filter="url(#glow)"
            />

            <circle
              cx="70"
              cy="190"
              r="5"
              fill="rgba(57,255,136,0.6)"
              filter="url(#glow)"
            />

          </g>

          <!-- Anillo interior -->
          <g
            class="omni-ring-inner"
            style="transform-origin:190px 190px"
          >

            <circle
              cx="190"
              cy="190"
              r="85"
              fill="none"
              stroke="rgba(124,255,0,0.3)"
              stroke-width="1"
            />

            <c:forEach begin="0" end="7" var="i">

              <rect
                x="187"
                y="106"
                width="6"
                height="12"
                rx="1"
                fill="rgba(124,255,0,0.5)"
                transform="rotate(${i * 45} 190 190)"
              />

            </c:forEach>

          </g>

          <!-- Selector de siluetas alienígenas originales -->
          <g class="omni-alien-selector" transform="translate(250 0)">
            <ellipse class="alien-aura" cx="190" cy="177" rx="64" ry="104"/>
            <ellipse class="alien-lock-ring" cx="190" cy="177" rx="67" ry="108"/>
            <ellipse class="alien-platform" cx="190" cy="282" rx="50" ry="10"/>
            <image class="omni-alien" data-alien="XRL8" href="${pageContext.request.contextPath}/assets/img/xrl8.png" x="88" y="48" width="205" height="235" preserveAspectRatio="xMidYMid meet"/>
            <image class="omni-alien" data-alien="FUEGO" href="${pageContext.request.contextPath}/assets/img/fuego.png" x="88" y="48" width="205" height="235" preserveAspectRatio="xMidYMid meet"/>
            <image class="omni-alien" data-alien="DIAMANTE" href="${pageContext.request.contextPath}/assets/img/diamante.png" x="88" y="48" width="205" height="235" preserveAspectRatio="xMidYMid meet"/>
            <image class="omni-alien" data-alien="BESTIA" href="${pageContext.request.contextPath}/assets/img/bestia.png" x="88" y="48" width="205" height="235" preserveAspectRatio="xMidYMid meet"/>
            <image class="omni-alien" data-alien="ALIEN X" href="${pageContext.request.contextPath}/assets/img/alienx.png" x="88" y="48" width="205" height="235" preserveAspectRatio="xMidYMid meet"/>
          </g>

          <!-- Rayo de selección hacia el alienígena activo -->
          <line class="omni-beam" x1="346" y1="190" x2="405" y2="190"/>
          <g class="omni-beam-particles">
            <circle cx="377" cy="181" r="2"/>
            <circle cx="391" cy="198" r="1.5"/>
            <circle cx="402" cy="186" r="1"/>
          </g>

          <!-- Scan line radial -->
          <line
            class="omni-scan"
            x1="190"
            y1="190"
            x2="190"
            y2="50"
            stroke="rgba(124,255,0,0.4)"
            stroke-width="1.5"
            style="transform-origin:190px 190px; animation: omniRotate 4s linear infinite"
          />

          <!-- Núcleo -->
          <g
            class="omni-core"
            style="transform-origin:190px 190px"
          >

            <!-- Hexágono exterior -->
            <polygon
              points="190,140 232,165 232,215 190,240 148,215 148,165"
              fill="rgba(18,107,53,0.4)"
              stroke="rgba(124,255,0,0.6)"
              stroke-width="2"
            />

            <!-- Hexágono interior -->
            <polygon
              points="190,158 212,170 212,194 190,206 168,194 168,170"
              fill="rgba(18,107,53,0.7)"
              stroke="rgba(124,255,0,0.8)"
              stroke-width="1.5"
            />

            <!-- Núcleo central -->
            <circle
              cx="190"
              cy="190"
              r="22"
              fill="url(#coreGrad)"
              filter="url(#glow)"
            />

            <!-- Símbolo HP -->
            <text
              x="190"
              y="196"
              text-anchor="middle"
              font-family="'Orbitron',monospace"
              font-size="12"
              font-weight="800"
              fill="#000"
              letter-spacing="1"
            >
              HP
            </text>

          </g>

          <!-- Indicadores HUD -->
          <g
            font-family="'Orbitron',monospace"
            font-size="8"
            fill="rgba(124,255,0,0.5)"
            letter-spacing="1"
          >

            <text x="195" y="28">
              UNIT:04
            </text>

            <text x="320" y="195">
              SEM:16
            </text>

            <text x="100" y="365">
              SYS:OK
            </text>

            <text
              x="18"
              y="195"
              transform="rotate(-90 18 195)"
            >
              ARCH.SW
            </text>

          </g>

          <!-- Indicador de cambio de alienígena -->
          <g class="omni-alien-beacon" aria-label="Alienígena activo">
            <line x1="346" y1="190" x2="363" y2="190" stroke="var(--green-main)" stroke-width="1"/>
            <circle cx="369" cy="190" r="5" fill="var(--green-main)"/>
            <circle cx="369" cy="190" r="10" fill="none" stroke="var(--green-main)" stroke-width="1"/>
            <circle cx="369" cy="190" r="16" fill="none" stroke="var(--green-tech)" stroke-width="1" stroke-dasharray="2 4"/>
          </g>

          <!-- Líneas HUD -->
          <line
            x1="190"
            y1="36"
            x2="190"
            y2="50"
            stroke="rgba(124,255,0,0.4)"
            stroke-width="1"
          />

          <line
            x1="320"
            y1="190"
            x2="306"
            y2="190"
            stroke="rgba(124,255,0,0.4)"
            stroke-width="1"
          />

          <line
            x1="60"
            y1="190"
            x2="74"
            y2="190"
            stroke="rgba(124,255,0,0.4)"
            stroke-width="1"
          />

          <line
            x1="190"
            y1="340"
            x2="190"
            y2="326"
            stroke="rgba(124,255,0,0.4)"
            stroke-width="1"
          />

        </svg>

      </div>

    </div>
  </div>
</section>

<!-- HUD STATS -->
<section
  class="hp-section-sm"
  aria-label="Estadísticas"
>

  <div class="hp-container">

    <div class="hp-hud-stats reveal">

      <div class="hp-hud-item">

        <div class="hp-hud-value">
          04
        </div>

        <div class="hp-hud-label">
          Unidades
        </div>

      </div>

      <div class="hp-hud-item">

        <div class="hp-hud-value">
          16
        </div>

        <div class="hp-hud-label">
          Semanas
        </div>

      </div>

      <div class="hp-hud-item">

        <div class="hp-hud-value">
          ${not empty proyectos ? fn:length(proyectos) : '01'}
        </div>

        <div class="hp-hud-label">
          Proyectos
        </div>

      </div>

      <div class="hp-hud-item">

        <div class="hp-hud-value">
          13
        </div>

        <div class="hp-hud-label">
          Tecnologías
        </div>

      </div>

    </div>

  </div>

</section>

<!-- TECNOLOGÍAS -->
<section
  class="hp-section"
  aria-labelledby="tec-title"
>

  <div class="hp-container">

    <div class="hp-section-header reveal">

      <span class="hp-section-tag">
        // TECH STACK
      </span>

      <h2
        class="hp-section-title"
        id="tec-title"
      >
        TECH <span>MODULES</span>
      </h2>

      <p class="hp-section-desc">
        Tecnologías utilizadas en el desarrollo del portafolio y el curso
      </p>

    </div>

    <div class="hp-tech-grid reveal">

      <c:forEach var="t" items="${tecnologias}">

        <a
          href="${pageContext.request.contextPath}/tecnologias"
          class="hp-tech-module"
          title="${t.descripcion}"
        >

          <c:choose>

            <c:when test="${not empty t.icono}">

              <i class="${t.icono} colored hp-tech-icon"></i>

            </c:when>

            <c:otherwise>

              <i
                class="bi bi-cpu-fill hp-tech-icon"
                style="color:var(--green-tech)"
              ></i>

            </c:otherwise>

          </c:choose>

          <span class="hp-tech-name">
            ${t.nombre}
          </span>

          <c:choose>

            <c:when test="${t.nivel eq 'Avanzado'}">

              <span class="hp-tech-level avanzado">
                ${t.nivel}
              </span>

            </c:when>

            <c:when test="${t.nivel eq 'Intermedio'}">

              <span class="hp-tech-level intermedio">
                ${t.nivel}
              </span>

            </c:when>

            <c:otherwise>

              <span class="hp-tech-level basico">
                ${not empty t.nivel ? t.nivel : 'Básico'}
              </span>

            </c:otherwise>

          </c:choose>

        </a>

      </c:forEach>

    </div>

    <div
      class="text-center mt-4 reveal"
      style="margin-top:2rem"
    >

      <a
        href="${pageContext.request.contextPath}/tecnologias"
        class="btn-secondary-hp"
      >

        <i class="bi bi-grid-3x3-gap"></i>

        VER TODAS LAS TECNOLOGÍAS

      </a>

    </div>

  </div>

</section>

<!-- UNIDADES -->
<section
  class="hp-section"
  style="background:var(--bg-secondary)"
  aria-labelledby="uni-title"
>

  <div class="hp-container">

    <div class="hp-section-header reveal">

      <span class="hp-section-tag">
        // NIVEL MAP
      </span>

      <h2
        class="hp-section-title"
        id="uni-title"
      >
        UNIDADES <span>ACADÉMICAS</span>
      </h2>

      <p class="hp-section-desc">
        4 unidades · 16 semanas de aprendizaje en Arquitectura de Software
      </p>

    </div>

    <div
      class="row-units reveal"
      style="
        display:grid;
        grid-template-columns:repeat(auto-fill,minmax(260px,1fr));
        gap:1.25rem;
      "
    >

      <c:forEach var="u" items="${unidades}">

        <a
          href="${pageContext.request.contextPath}/unidades?accion=detalle&id=${u.idUnidad}"
          class="hp-card"
          style="text-decoration:none"
        >

          <div
            style="
              display:flex;
              align-items:center;
              gap:1rem;
              margin-bottom:1rem;
            "
          >

            <div
              style="
                font-family:var(--font-tech);
                font-size:2rem;
                font-weight:800;
                color:var(--green-main);
                opacity:0.6;
                line-height:1;
              "
            >
              ${u.numeroARomano}
            </div>

            <div>

              <div
                style="
                  font-family:var(--font-tech);
                  font-size:0.55rem;
                  letter-spacing:3px;
                  color:var(--green-main);
                  text-transform:uppercase;
                "
              >
                UNIDAD ${u.numeroARomano}
              </div>

              <div
                style="
                  font-family:var(--font-tech);
                  font-size:0.78rem;
                  font-weight:600;
                  color:var(--text-primary);
                  letter-spacing:1px;
                  line-height:1.3;
                "
              >
                ${u.titulo}
              </div>

            </div>

          </div>

          <p
            style="
              font-size:0.78rem;
              color:var(--text-secondary);
              line-height:1.6;
              margin-bottom:1rem;
            "
          >
            ${fn:substring(u.descripcion, 0, 100)}...
          </p>

          <span class="hp-badge hp-badge-green">

            <i class="bi bi-calendar3 me-1"></i>

            4 SEMANAS

          </span>

        </a>

      </c:forEach>

    </div>

    <div
      style="text-align:center;margin-top:2.5rem"
      class="reveal"
    >

      <a
        href="${pageContext.request.contextPath}/unidades"
        class="btn-primary-hp"
      >

        <i class="bi bi-journal-bookmark"></i>

        EXPLORAR TODAS LAS UNIDADES

      </a>

    </div>

  </div>

</section>

<!-- FOOTER -->
<footer class="hp-footer">

  <div class="hp-container">

    <div class="hp-footer-inner">

      <div>

        <div class="hp-footer-brand">

          <span
            style="
              width:8px;
              height:8px;
              background:var(--green-main);
              border-radius:50%;
              display:inline-block;
              box-shadow:0 0 6px var(--green-main);
            "
          ></span>

          HIPORTAFOLIO

        </div>

        <div
          class="hp-footer-text"
          style="margin-top:0.4rem"
        >
          Brayan Apomayta Cuba · UPLA · Arquitectura de Software
        </div>

      </div>

      <div class="hp-footer-text">
        VIII Ciclo · Ingeniería de Sistemas · Huancayo, Perú
      </div>

    </div>

  </div>

</footer>

<script src="${pageContext.request.contextPath}/assets/js/main.js?v=20260922-5"></script>

</body>
</html>