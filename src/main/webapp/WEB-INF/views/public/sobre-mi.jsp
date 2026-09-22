<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sobre mí — Brayan Apomayta Cuba</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div id="hp-cursor"></div><div id="hp-cursor-ring"></div>
<nav class="hp-navbar scrolled" role="navigation">
  <div class="nav-inner">
    <a href="${pageContext.request.contextPath}/portafolio" class="hp-logo"><span class="logo-dot"></span>HIPORTAFOLIO</a>
    <ul class="hp-nav-links">
      <li><a href="${pageContext.request.contextPath}/portafolio">Inicio</a></li>
      <li><a href="${pageContext.request.contextPath}/portafolio?vista=sobre-mi" class="active">Sobre mí</a></li>
      <li><a href="${pageContext.request.contextPath}/unidades">Unidades</a></li>
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
  <a href="${pageContext.request.contextPath}/portafolio?vista=sobre-mi" class="active">Sobre mí</a>
  <a href="${pageContext.request.contextPath}/unidades">Unidades</a>
  <a href="${pageContext.request.contextPath}/login">Acceder</a>
</div>

<div class="hp-page-content">
  <!-- Header -->
  <section style="background:var(--bg-secondary);border-bottom:1px solid var(--border-dim);padding:3rem 0">
    <div class="hp-container">
      <span class="hp-section-tag">// PLAYER PROFILE</span>
      <h1 style="font-family:var(--font-tech);font-size:clamp(1.5rem,4vw,2.5rem);font-weight:800;color:var(--text-primary);letter-spacing:3px;margin-top:0.5rem">
        SOBRE <span style="color:var(--green-main)">MÍ</span>
      </h1>
    </div>
  </section>

  <div class="hp-section">
    <div class="hp-container">
      <div style="display:grid;grid-template-columns:300px 1fr;gap:3rem;align-items:start" class="reveal">

        <!-- FOTO + identidad -->
        <div style="text-align:center">
          <div style="position:relative;display:inline-block;margin-bottom:1.5rem">
            <div style="width:200px;height:200px;position:relative;margin:0 auto">
              <!-- Anillos decorativos -->
              <div style="position:absolute;inset:-16px;border-radius:50%;border:2px solid rgba(124,255,0,0.25);animation:introRing 12s linear infinite;pointer-events:none"></div>
              <div style="position:absolute;inset:-28px;border-radius:50%;border:1px dashed rgba(124,255,0,0.12);animation:introRing 20s linear infinite reverse;pointer-events:none"></div>
              <!-- Foto de perfil -->
              <div style="width:200px;height:200px;border-radius:50%;background:var(--bg-tertiary);border:3px solid rgba(124,255,0,0.5);display:flex;align-items:center;justify-content:center;overflow:hidden;box-shadow:0 0 30px rgba(124,255,0,0.15)">
                <img src="${pageContext.request.contextPath}/assets/img/perfilb.png" alt="Perfil de Brayan Apomayta" style="width:100%;height:100%;object-fit:cover;display:block">
              </div>
              <!-- Corner indicators -->
              <div style="position:absolute;top:6px;left:6px;width:14px;height:14px;border-top:2px solid var(--green-main);border-left:2px solid var(--green-main)"></div>
              <div style="position:absolute;top:6px;right:6px;width:14px;height:14px;border-top:2px solid var(--green-main);border-right:2px solid var(--green-main)"></div>
              <div style="position:absolute;bottom:6px;left:6px;width:14px;height:14px;border-bottom:2px solid var(--green-main);border-left:2px solid var(--green-main)"></div>
              <div style="position:absolute;bottom:6px;right:6px;width:14px;height:14px;border-bottom:2px solid var(--green-main);border-right:2px solid var(--green-main)"></div>
            </div>
          </div>

          <div style="font-family:var(--font-tech);font-size:1.1rem;font-weight:800;color:var(--text-primary);letter-spacing:2px;margin-bottom:0.2rem">BRAYAN APOMAYTA</div>
          <div style="font-family:var(--font-tech);font-size:1rem;font-weight:700;color:var(--green-main);letter-spacing:2px">CUBA</div>
          <div style="font-size:0.78rem;color:var(--text-secondary);margin-top:0.4rem">Estudiante · VIII Ciclo · 21 años</div>

          <!-- Status online -->
          <div style="display:inline-flex;align-items:center;gap:0.5rem;margin-top:0.75rem;padding:0.35rem 0.9rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:20px">
            <span style="width:7px;height:7px;background:var(--green-main);border-radius:50%;box-shadow:0 0 6px var(--green-main);animation:statusBlink 1.5s ease-in-out infinite"></span>
            <span style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:2px;color:var(--green-main)">ONLINE</span>
          </div>

          <!-- Redes sociales -->
          <div style="display:flex;gap:0.75rem;justify-content:center;margin-top:1.25rem">
            <a href="#" style="width:40px;height:40px;border:1px solid var(--border-dim);border-radius:6px;display:flex;align-items:center;justify-content:center;color:var(--text-muted);text-decoration:none;font-size:1.1rem;transition:all 0.2s" title="Facebook (próximamente)"
               onmouseenter="this.style.borderColor='var(--border-main)';this.style.color='var(--green-main)';this.style.background='var(--green-glow-sm)'"
               onmouseleave="this.style.borderColor='var(--border-dim)';this.style.color='var(--text-muted)';this.style.background='transparent'">
              <i class="bi bi-facebook"></i>
            </a>
            <a href="#" style="width:40px;height:40px;border:1px solid var(--border-dim);border-radius:6px;display:flex;align-items:center;justify-content:center;color:var(--text-muted);text-decoration:none;font-size:1.1rem;transition:all 0.2s" title="Instagram (próximamente)"
               onmouseenter="this.style.borderColor='var(--border-main)';this.style.color='var(--green-main)';this.style.background='var(--green-glow-sm)'"
               onmouseleave="this.style.borderColor='var(--border-dim)';this.style.color='var(--text-muted)';this.style.background='transparent'">
              <i class="bi bi-instagram"></i>
            </a>
          </div>
        </div>

        <!-- INFO principal -->
        <div>
          <!-- Data cards grid -->
          <div style="display:grid;grid-template-columns:repeat(2,1fr);gap:0.75rem;margin-bottom:2rem">
            <div class="hp-card" style="padding:1rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.4rem">Universidad</div>
              <div style="font-size:0.82rem;color:var(--text-primary);font-weight:500;line-height:1.4">Universidad Peruana Los Andes</div>
            </div>
            <div class="hp-card" style="padding:1rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.4rem">Carrera</div>
              <div style="font-size:0.82rem;color:var(--text-primary);font-weight:500;line-height:1.4">Ing. de Sistemas y Computación</div>
            </div>
            <div class="hp-card" style="padding:1rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.4rem">Curso</div>
              <div style="font-size:0.82rem;color:var(--text-primary);font-weight:500">Arquitectura de Software</div>
            </div>
            <div class="hp-card" style="padding:1rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.4rem">Ciclo / Edad</div>
              <div style="font-size:0.82rem;color:var(--text-primary);font-weight:500">VIII Ciclo · 21 años</div>
            </div>
            <div class="hp-card" style="padding:1rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.4rem">Ubicación</div>
              <div style="font-size:0.82rem;color:var(--text-primary);font-weight:500"><i class="bi bi-geo-alt me-1" style="color:var(--green-tech)"></i>Huancayo, Perú</div>
            </div>
            <div class="hp-card" style="padding:1rem">
              <div style="font-family:var(--font-tech);font-size:0.5rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.4rem">Año</div>
              <div style="font-size:0.82rem;color:var(--text-primary);font-weight:500">2026</div>
            </div>
          </div>

          <!-- Descripción personal -->
          <div class="hp-card" style="border-left:3px solid var(--green-main);margin-bottom:1.5rem">
            <div style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:0.75rem">// DESCRIPCIÓN</div>
            <p style="font-size:0.9rem;color:var(--text-secondary);line-height:1.85;margin:0">
              Soy Brayan Apomayta Cuba, estudiante de Ingeniería de Sistemas y Computación en la UPLA.
              Me apasiona el desarrollo de software, el diseño de sistemas escalables y la resolución de problemas complejos a través de la tecnología.
              Tengo interés especial en arquitectura de software, desarrollo web backend con Java y la implementación de buenas prácticas de ingeniería.
              Este portafolio es el resultado de mi aprendizaje durante el curso de Arquitectura de Software, donde apliqué conceptos de diseño por capas, patrones arquitectónicos y tecnologías modernas para construir un sistema web real y funcional desde cero.
            </p>
          </div>

          <!-- Habilidades técnicas -->
          <div class="hp-card" style="margin-bottom:1.5rem">
            <div style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:1rem">// HABILIDADES TÉCNICAS</div>
            <div style="display:flex;flex-wrap:wrap;gap:0.5rem">
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">Java</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">JSP / Servlets</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">MySQL</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">HTML5 / CSS3</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">JavaScript</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">Bootstrap 5</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">Maven</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">Git</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">Docker</span>
              <span style="font-family:var(--font-tech);font-size:0.6rem;padding:0.3rem 0.8rem;background:rgba(124,255,0,0.08);border:1px solid rgba(124,255,0,0.2);border-radius:4px;color:var(--green-tech);letter-spacing:1px">Arquitectura MVC</span>
            </div>
          </div>

          <!-- Objetivos del portafolio -->
          <div class="hp-card">
            <div style="font-family:var(--font-tech);font-size:0.55rem;letter-spacing:3px;color:var(--green-main);text-transform:uppercase;margin-bottom:1rem">// OBJETIVOS DEL PORTAFOLIO</div>
            <div style="display:flex;flex-direction:column;gap:0.6rem">
              <div style="display:flex;align-items:flex-start;gap:0.75rem;font-size:0.83rem;color:var(--text-secondary)">
                <i class="bi bi-check-circle-fill" style="color:var(--green-main);flex-shrink:0;margin-top:2px"></i>
                Documentar el aprendizaje de las 4 unidades del curso de Arquitectura de Software
              </div>
              <div style="display:flex;align-items:flex-start;gap:0.75rem;font-size:0.83rem;color:var(--text-secondary)">
                <i class="bi bi-check-circle-fill" style="color:var(--green-main);flex-shrink:0;margin-top:2px"></i>
                Implementar un sistema web profesional y funcional usando Java, JSP, Servlets y MySQL
              </div>
              <div style="display:flex;align-items:flex-start;gap:0.75rem;font-size:0.83rem;color:var(--text-secondary)">
                <i class="bi bi-check-circle-fill" style="color:var(--green-main);flex-shrink:0;margin-top:2px"></i>
                Aplicar arquitectura MVC por capas, principios SOLID y patrones de diseño
              </div>
              <div style="display:flex;align-items:flex-start;gap:0.75rem;font-size:0.83rem;color:var(--text-secondary)">
                <i class="bi bi-check-circle-fill" style="color:var(--green-main);flex-shrink:0;margin-top:2px"></i>
                Demostrar capacidad para construir sistemas escalables, seguros y mantenibles
              </div>
              <div style="display:flex;align-items:flex-start;gap:0.75rem;font-size:0.83rem;color:var(--text-secondary)">
                <i class="bi bi-check-circle-fill" style="color:var(--green-main);flex-shrink:0;margin-top:2px"></i>
                Gestionar evidencias, proyectos y tecnologías de forma dinámica desde base de datos
              </div>
            </div>
          </div>

          <div style="display:flex;gap:1rem;flex-wrap:wrap;margin-top:2rem">
            <a href="${pageContext.request.contextPath}/unidades" class="btn-primary-hp"><i class="bi bi-journal-bookmark me-1"></i>VER UNIDADES</a>
            <a href="${pageContext.request.contextPath}/portafolio?vista=contacto" class="btn-secondary-hp"><i class="bi bi-envelope me-1"></i>CONTACTO</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<footer class="hp-footer">
  <div class="hp-container">
    <div class="hp-footer-inner">
      <div class="hp-footer-brand"><span style="width:8px;height:8px;background:var(--green-main);border-radius:50%;display:inline-block;box-shadow:0 0 6px var(--green-main)"></span>HIPORTAFOLIO</div>
      <div class="hp-footer-text">Brayan Apomayta Cuba · UPLA · Arquitectura de Software · 2026</div>
    </div>
  </div>
</footer>
<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>
