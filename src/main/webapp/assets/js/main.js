/* ================================================================
   HiPortafolio — main.js
   Cursor · Intro · Tema · Sonido · Animaciones · Reveal
   ================================================================ */
(function () {
  'use strict';

  /* ---- TEMA ---- */
  const THEME_KEY = 'hp-theme';
  function getTheme() { return localStorage.getItem(THEME_KEY) || 'dark'; }
  function applyTheme(t) {
    document.documentElement.setAttribute('data-theme', t);
    localStorage.setItem(THEME_KEY, t);
    const btn = document.getElementById('hp-theme-btn');
    if (btn) btn.innerHTML = t === 'dark' ? '<i class="bi bi-sun"></i>' : '<i class="bi bi-moon-stars"></i>';
  }
  applyTheme(getTheme());

  /* ---- SONIDO ---- */
  const SOUND_KEY = 'hp-sound';
  let soundOn = localStorage.getItem(SOUND_KEY) !== 'off';
  const AudioCtx = window.AudioContext || window.webkitAudioContext;
  let ctx;
  function getCtx() { if (!ctx && AudioCtx) ctx = new AudioCtx(); return ctx; }
  function playClick() {
    if (!soundOn) return;
    const c = getCtx(); if (!c) return;
    const o = c.createOscillator(), g = c.createGain();
    o.connect(g); g.connect(c.destination);
    o.type = 'square'; o.frequency.setValueAtTime(880, c.currentTime);
    o.frequency.exponentialRampToValueAtTime(440, c.currentTime + 0.08);
    g.gain.setValueAtTime(0.06, c.currentTime);
    g.gain.exponentialRampToValueAtTime(0.001, c.currentTime + 0.08);
    o.start(); o.stop(c.currentTime + 0.08);
  }
  function playHover() {
    if (!soundOn) return;
    const c = getCtx(); if (!c) return;
    const o = c.createOscillator(), g = c.createGain();
    o.connect(g); g.connect(c.destination);
    o.type = 'sine'; o.frequency.setValueAtTime(660, c.currentTime);
    g.gain.setValueAtTime(0.03, c.currentTime);
    g.gain.exponentialRampToValueAtTime(0.001, c.currentTime + 0.05);
    o.start(); o.stop(c.currentTime + 0.05);
  }
  function toggleSound() {
    soundOn = !soundOn;
    localStorage.setItem(SOUND_KEY, soundOn ? 'on' : 'off');
    const btn = document.getElementById('hp-sound-btn');
    if (btn) {
      btn.innerHTML = soundOn ? '<i class="bi bi-volume-up"></i>' : '<i class="bi bi-volume-mute"></i>';
      btn.classList.toggle('active', soundOn);
    }
    playClick();
  }

  /* ---- CURSOR ---- */
  const isTouchDevice = () => window.matchMedia('(hover: none)').matches;
  let cursorDot, cursorRing, mouseX = 0, mouseY = 0, ringX = 0, ringY = 0;
  function initCursor() {
    if (isTouchDevice()) return;
    cursorDot  = document.getElementById('hp-cursor');
    cursorRing = document.getElementById('hp-cursor-ring');
    if (!cursorDot || !cursorRing) return;
    document.addEventListener('mousemove', e => { mouseX = e.clientX; mouseY = e.clientY; });
    document.querySelectorAll('a,button,[role="button"],.hp-card,.sidebar-item,.hp-tech-module,.hp-mission-card,.admin-stat-card,.hp-week-btn,.login-demo-item').forEach(el => {
      el.addEventListener('mouseenter', () => {
        cursorRing.classList.add('hovering');
        cursorDot.classList.add('hovering');
        playHover();
      });
      el.addEventListener('mouseleave', () => {
        cursorRing.classList.remove('hovering');
        cursorDot.classList.remove('hovering');
      });
    });
    animateCursor();
  }
  function animateCursor() {
    if (!cursorDot) return;
    cursorDot.style.left  = mouseX + 'px';
    cursorDot.style.top   = mouseY + 'px';
    ringX += (mouseX - ringX) * 0.12;
    ringY += (mouseY - ringY) * 0.12;
    cursorRing.style.left = ringX + 'px';
    cursorRing.style.top  = ringY + 'px';
    requestAnimationFrame(animateCursor);
  }

  /* ---- INTRO ---- */
  const INTRO_KEY = 'hp-intro-seen';
  function initIntro() {
    const intro = document.getElementById('hp-intro');
    if (!intro) return;
    const seen = sessionStorage.getItem(INTRO_KEY);
    if (seen) { intro.classList.add('hidden'); return; }

    // Secuencia de mensajes animados
    const msgs = ['INICIALIZANDO SISTEMA...', 'CARGANDO MÓDULOS...', 'HIPORTAFOLIO', 'SYSTEM ONLINE ■'];
    const lines = [
      document.getElementById('intro-line-1'),
      document.getElementById('intro-line-2'),
      document.getElementById('intro-line-3'),
      document.getElementById('intro-line-4')
    ];

    let i = 0;
    function tick() {
      if (i < lines.length && lines[i]) {
        lines[i].textContent = msgs[i] || '';
        i++;
        setTimeout(tick, 480);
      } else {
        // Auto-cerrar después de mostrar todos los mensajes
        setTimeout(closeIntro, 700);
      }
    }
    setTimeout(tick, 200);

    // Botón omitir — visible solo después de 1 segundo
    const skip = intro.querySelector('.intro-skip');
    if (skip) {
      skip.style.opacity = '0';
      skip.style.pointerEvents = 'none';
      setTimeout(() => {
        skip.style.opacity = '1';
        skip.style.pointerEvents = 'auto';
        skip.style.transition = 'opacity 0.4s';
      }, 1000);
      skip.addEventListener('click', closeIntro);
    }
  }
  function closeIntro() {
    const intro = document.getElementById('hp-intro');
    if (!intro) return;
    intro.classList.add('hidden');
    sessionStorage.setItem(INTRO_KEY, '1');
  }

  /* ---- NAVBAR SCROLL ---- */
  function initNavbar() {
    const nav = document.querySelector('.hp-navbar');
    if (!nav) return;
    const onScroll = () => nav.classList.toggle('scrolled', window.scrollY > 40);
    window.addEventListener('scroll', onScroll, { passive: true });
    onScroll();
  }

  /* ---- SELECTOR DE ALIENIGENAS ---- */
  function initAlienSelector() {
    const aliens = document.querySelectorAll('.omni-alien');
    if (!aliens.length) return;
    const selector = document.querySelector('.omni-alien-selector');
    const core = document.querySelector('.omni-core');
    if (selector && core) core.parentNode.appendChild(selector);
    let active = 0;
    function selectAlien(index) {
      aliens.forEach((alien, i) => {
        const isActive = i === index;
        alien.classList.toggle('active', isActive);
        alien.classList.remove('carousel-enter');
        alien.setAttribute('opacity', isActive ? '1' : '0');
        alien.style.opacity = isActive ? '1' : '0';
        alien.style.visibility = isActive ? 'visible' : 'hidden';
        alien.style.display = isActive ? 'block' : 'none';
        if (isActive) {
          void alien.getBoundingClientRect();
          alien.classList.add('carousel-enter');
        }
      });
    }
    selectAlien(active);
    window.setInterval(() => {
      active = (active + 1) % aliens.length;
      selectAlien(active);
    }, 2500);
  }

  /* ---- HAMBURGER ---- */
  function initHamburger() {
    const btn  = document.getElementById('hp-hamburger');
    const menu = document.getElementById('hp-mobile-menu');
    if (!btn || !menu) return;
    btn.addEventListener('click', () => {
      const open = btn.classList.toggle('open');
      menu.classList.toggle('open', open);
      playClick();
    });
    document.addEventListener('click', e => {
      if (!btn.contains(e.target) && !menu.contains(e.target)) {
        btn.classList.remove('open');
        menu.classList.remove('open');
      }
    });
  }

  /* ---- ACTIVE NAV ---- */
  function initActiveNav() {
    const path = window.location.pathname + window.location.search;
    document.querySelectorAll('.hp-nav-links a, .hp-mobile-menu a').forEach(a => {
      const href = a.getAttribute('href') || '';
      if (href && path.includes(href.split('?')[0]) && href !== '#') {
        a.classList.add('active');
      }
    });
  }

  /* ---- REVEAL ON SCROLL ---- */
  function initReveal() {
    const els = document.querySelectorAll('.reveal');
    if (!els.length) return;
    const obs = new IntersectionObserver((entries) => {
      entries.forEach(e => { if (e.isIntersecting) { e.target.classList.add('visible'); obs.unobserve(e.target); } });
    }, { threshold: 0.1 });
    els.forEach(el => obs.observe(el));
  }

  /* ---- SOUND BTN ---- */
  function initSoundBtn() {
    const btn = document.getElementById('hp-sound-btn');
    if (!btn) return;
    btn.innerHTML = soundOn ? '<i class="bi bi-volume-up"></i>' : '<i class="bi bi-volume-mute"></i>';
    btn.classList.toggle('active', soundOn);
    btn.addEventListener('click', toggleSound);
  }

  /* ---- THEME BTN ---- */
  function initThemeBtn() {
    const btn = document.getElementById('hp-theme-btn');
    if (!btn) return;
    applyTheme(getTheme());
    btn.addEventListener('click', () => { applyTheme(getTheme() === 'dark' ? 'light' : 'dark'); playClick(); });
  }

  /* ---- CLICK SOUND en botones ---- */
  function initClickSounds() {
    document.addEventListener('click', e => {
      const t = e.target.closest('a,button,.btn-primary-hp,.btn-secondary-hp,.hp-filter-btn');
      if (t) playClick();
    });
  }

  /* ---- FILTROS EVIDENCIAS ---- */
  function initFilters() {
    document.querySelectorAll('.hp-filter-btn').forEach(btn => {
      btn.addEventListener('click', function () {
        document.querySelectorAll('.hp-filter-btn').forEach(b => b.classList.remove('active'));
        this.classList.add('active');
        const filter = this.dataset.filter;
        document.querySelectorAll('[data-filter-item]').forEach(item => {
          if (filter === 'all' || item.dataset.filterItem === filter) {
            item.style.display = '';
          } else {
            item.style.display = 'none';
          }
        });
        playClick();
      });
    });
  }

  /* ---- DEMO CREDS LOGIN ---- */
  function initDemoCreds() {
    document.querySelectorAll('.login-demo-item').forEach(item => {
      item.addEventListener('click', function () {
        const email = this.dataset.email;
        const pass  = this.dataset.pass;
        const eIn = document.getElementById('correo');
        const pIn = document.getElementById('password');
        if (eIn && email) eIn.value = email;
        if (pIn && pass)  pIn.value = pass;
        playClick();
      });
    });
  }

  /* ---- INIT ---- */
  document.addEventListener('DOMContentLoaded', () => {
    initIntro();
    initCursor();
    initNavbar();
    initAlienSelector();
    initHamburger();
    initActiveNav();
    initReveal();
    initSoundBtn();
    initThemeBtn();
    initClickSounds();
    initFilters();
    initDemoCreds();
  });
})();
