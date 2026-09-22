<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es" data-theme="dark">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Acceder — HiPortafolio</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/login.css">
</head>
<body class="login-body">
<div id="hp-cursor"></div><div id="hp-cursor-ring"></div>
<div class="login-scanline" aria-hidden="true"></div>
<button class="login-theme-btn" id="login-theme-btn" type="button" aria-label="Cambiar tema"><i class="bi bi-sun"></i></button>

<div class="login-container">

  <!-- Header HUD -->
  <div class="login-hud-header">
    <div class="login-core" aria-hidden="true">
      <div class="login-core-inner"></div>
    </div>
    <div class="login-system-name">HIPORTAFOLIO</div>
    <div class="login-system-tag">ACCESS TERMINAL</div>
    <div class="login-system-status">
      <span class="status-dot" aria-hidden="true"></span>
      SYSTEM READY
    </div>
  </div>

  <!-- Card terminal -->
  <div class="login-card" role="main">

    <!-- Alertas -->
    <c:if test="${not empty error}">
      <div class="login-alert login-alert-danger" role="alert">
        <i class="bi bi-exclamation-triangle-fill"></i>
        <span>${error}</span>
      </div>
    </c:if>
    <c:if test="${not empty mensaje}">
      <div class="login-alert login-alert-info" role="alert">
        <i class="bi bi-info-circle-fill"></i>
        <span>${mensaje}</span>
      </div>
    </c:if>
    <c:if test="${param.logout eq 'true'}">
      <div class="login-alert login-alert-info" role="alert">
        <i class="bi bi-check-circle-fill"></i>
        <span>Sesión cerrada correctamente.</span>
      </div>
    </c:if>
    <c:if test="${param.expired eq 'true'}">
      <div class="login-alert login-alert-danger" role="alert">
        <i class="bi bi-clock-fill"></i>
        <span>Tu sesión expiró. Inicia sesión nuevamente.</span>
      </div>
    </c:if>

    <!-- Formulario -->
    <form action="${pageContext.request.contextPath}/login" method="post" class="login-form" id="loginForm" novalidate>

      <div class="login-field">
        <label class="login-label" for="correo">
          <i class="bi bi-envelope me-1"></i>CORREO ELECTRÓNICO
        </label>
        <div class="login-input-wrap">
          <input type="email" id="correo" name="correo" class="login-input with-icon-left"
                 placeholder="usuario@ejemplo.com"
                 value="${not empty param.correo ? param.correo : ''}"
                 required autocomplete="email" autofocus>
          <i class="bi bi-person login-input-icon-left" aria-hidden="true"></i>
        </div>
      </div>

      <div class="login-field">
        <label class="login-label" for="password">
          <i class="bi bi-lock me-1"></i>CONTRASEÑA
        </label>
        <div class="login-input-wrap">
          <input type="password" id="password" name="password" class="login-input with-icon-left"
                 placeholder="••••••••" required autocomplete="current-password">
          <i class="bi bi-lock login-input-icon-left" aria-hidden="true"></i>
          <button type="button" class="login-pass-toggle" id="togglePass" aria-label="Mostrar contraseña">
            <i class="bi bi-eye" id="togglePassIcon"></i>
          </button>
        </div>
      </div>

      <button type="submit" class="login-submit" id="loginBtn">
        <i class="bi bi-terminal"></i>
        INICIAR SESIÓN
      </button>
    </form>

  </div><!-- /card -->

  <!-- Demo credentials -->
  <div class="login-demo">
    <div class="login-demo-title"><i class="bi bi-info-circle me-1"></i>CUENTAS DE PRUEBA</div>
    <div class="login-demo-grid">
      <div class="login-demo-item"
           data-email="admin@hiportafolio.com"
           data-pass="Admin123!"
           role="button" tabindex="0" title="Usar credenciales admin">
        <span class="role">ADMIN</span>
        <span class="cred">admin@hiportafolio.com</span>
        <span class="cred" style="color:rgba(154,168,160,0.6)">Admin123!</span>
      </div>
      <div class="login-demo-item"
           data-email="usuario@hiportafolio.com"
           data-pass="Usuario123!"
           role="button" tabindex="0" title="Usar credenciales usuario">
        <span class="role" style="color:var(--green-tech)">USUARIO</span>
        <span class="cred">usuario@hiportafolio.com</span>
        <span class="cred" style="color:rgba(154,168,160,0.6)">Usuario123!</span>
      </div>
    </div>
  </div>

  <div class="login-back">
    <a href="${pageContext.request.contextPath}/portafolio">
      <i class="bi bi-arrow-left"></i> VOLVER AL PORTAFOLIO
    </a>
  </div>

</div><!-- /container -->

<script>
  // Toggle password
  document.getElementById('togglePass').addEventListener('click', function(){
    const inp = document.getElementById('password');
    const icon = document.getElementById('togglePassIcon');
    if(inp.type==='password'){ inp.type='text'; icon.className='bi bi-eye-slash'; }
    else { inp.type='password'; icon.className='bi bi-eye'; }
  });
  // Spinner on submit
  document.getElementById('loginForm').addEventListener('submit', function(){
    const btn = document.getElementById('loginBtn');
    btn.disabled = true;
    btn.innerHTML = '<span style="display:inline-block;width:14px;height:14px;border:2px solid #000;border-top-color:transparent;border-radius:50%;animation:introRing 0.6s linear infinite"></span> VERIFICANDO...';
  });
  // Demo creds — keyboard support
  document.querySelectorAll('.login-demo-item').forEach(item => {
    item.addEventListener('keydown', e => { if(e.key==='Enter'||e.key===' ') item.click(); });
  });
  // Apply theme
  const themeBtn = document.getElementById('login-theme-btn');
  function applyLoginTheme(theme) {
    document.documentElement.setAttribute('data-theme', theme);
    localStorage.setItem('hp-theme', theme);
    themeBtn.innerHTML = theme === 'dark' ? '<i class="bi bi-sun"></i>' : '<i class="bi bi-moon-stars"></i>';
  }
  applyLoginTheme(localStorage.getItem('hp-theme')||'dark');
  themeBtn.addEventListener('click', function() {
    applyLoginTheme(document.documentElement.getAttribute('data-theme') === 'dark' ? 'light' : 'dark');
  });
  // Demo click
  document.querySelectorAll('.login-demo-item').forEach(item => {
    item.addEventListener('click', function(){
      document.getElementById('correo').value   = this.dataset.email;
      document.getElementById('password').value = this.dataset.pass;
    });
  });
  // Cursor
  const dot=document.getElementById('hp-cursor'), ring=document.getElementById('hp-cursor-ring');
  if(dot && ring && window.matchMedia('(hover:hover)').matches){
    let rx=0,ry=0,mx=0,my=0;
    document.addEventListener('mousemove',e=>{mx=e.clientX;my=e.clientY;dot.style.left=mx+'px';dot.style.top=my+'px';});
    (function loop(){ rx+=(mx-rx)*0.12; ry+=(my-ry)*0.12; ring.style.left=rx+'px'; ring.style.top=ry+'px'; requestAnimationFrame(loop); })();
    document.querySelectorAll('a,button,input,.login-demo-item').forEach(el=>{
      el.addEventListener('mouseenter',()=>ring.classList.add('hovering'));
      el.addEventListener('mouseleave',()=>ring.classList.remove('hovering'));
    });
  }
</script>
</body>
</html>
