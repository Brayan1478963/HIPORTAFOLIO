/* ================================================================
   HiPortafolio — admin.js
   Sidebar · Alertas · Tema · Búsqueda · Modales
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

  /* ---- SIDEBAR ---- */
  const SIDEBAR_KEY = 'hp-sidebar-collapsed';
  function initSidebar() {
    const sidebar  = document.getElementById('admin-sidebar');
    const main     = document.getElementById('admin-main');
    const colBtn   = document.getElementById('sidebar-collapse-btn');
    const mobileBtn= document.getElementById('sidebar-mobile-btn');
    const overlay  = document.getElementById('admin-overlay');
    if (!sidebar) return;

    // Estado guardado
    const isCollapsed = localStorage.getItem(SIDEBAR_KEY) === '1';
    if (isCollapsed) { sidebar.classList.add('collapsed'); main && main.classList.add('expanded'); }

    // Collapse desktop
    if (colBtn) {
      colBtn.addEventListener('click', () => {
        const c = sidebar.classList.toggle('collapsed');
        main && main.classList.toggle('expanded', c);
        localStorage.setItem(SIDEBAR_KEY, c ? '1' : '0');
        const icon = colBtn.querySelector('i');
        if (icon) icon.className = c ? 'bi bi-chevron-right' : 'bi bi-chevron-left';
      });
    }

    // Mobile open/close
    if (mobileBtn) {
      mobileBtn.addEventListener('click', () => {
        sidebar.classList.toggle('mobile-open');
        overlay && overlay.classList.toggle('show');
      });
    }
    if (overlay) {
      overlay.addEventListener('click', () => {
        sidebar.classList.remove('mobile-open');
        overlay.classList.remove('show');
      });
    }

    // Sub-menus
    document.querySelectorAll('.sidebar-item[data-toggle]').forEach(item => {
      item.addEventListener('click', function (e) {
        e.preventDefault();
        const target = document.getElementById(this.dataset.toggle);
        if (target) target.classList.toggle('open');
        this.classList.toggle('open');
      });
    });

    // Active link
    const path = window.location.pathname + window.location.search;
    document.querySelectorAll('.sidebar-item[href]').forEach(a => {
      const href = a.getAttribute('href') || '';
      if (href && path.includes(href.split('?')[0]) && href.length > 1) {
        a.classList.add('active');
        // Abrir sub-menú padre si existe
        const sub = a.closest('.sidebar-sub');
        if (sub) sub.classList.add('open');
      }
    });
  }

  /* ---- ALERTAS AUTO-CERRAR ---- */
  function initAlerts() {
    document.querySelectorAll('.hp-alert').forEach(alert => {
      const close = alert.querySelector('.hp-alert-close');
      if (close) close.addEventListener('click', () => alert.remove());
      if (alert.classList.contains('hp-alert-success')) {
        setTimeout(() => { if (alert.parentNode) alert.remove(); }, 4000);
      }
    });
  }

  /* ---- TEMA BTN ---- */
  function initThemeBtn() {
    const btn = document.getElementById('hp-theme-btn');
    if (!btn) return;
    btn.addEventListener('click', () => applyTheme(getTheme() === 'dark' ? 'light' : 'dark'));
  }

  /* ---- BÚSQUEDA EN TABLA ---- */
  function initTableSearch() {
    document.querySelectorAll('.table-search-input').forEach(input => {
      input.addEventListener('input', function () {
        const q = this.value.toLowerCase();
        const table = document.querySelector(this.dataset.table || '.hp-table');
        if (!table) return;
        table.querySelectorAll('tbody tr').forEach(row => {
          row.style.display = row.textContent.toLowerCase().includes(q) ? '' : 'none';
        });
      });
    });
  }

  /* ---- CONFIRMAR ELIMINAR ---- */
  function initDeleteConfirm() {
    document.querySelectorAll('[data-confirm]').forEach(btn => {
      btn.addEventListener('click', function (e) {
        const msg = this.dataset.confirm || '¿Confirmar esta acción?';
        if (!confirm(msg)) e.preventDefault();
      });
    });
  }

  /* ---- MODAL ---- */
  function initModals() {
    document.querySelectorAll('[data-modal-open]').forEach(btn => {
      btn.addEventListener('click', () => {
        const modal = document.getElementById(btn.dataset.modalOpen);
        if (modal) modal.classList.add('open');
      });
    });
    document.querySelectorAll('[data-modal-close], .hp-modal-backdrop').forEach(el => {
      el.addEventListener('click', function (e) {
        if (e.target === this || this.hasAttribute('data-modal-close')) {
          const backdrop = this.closest('.hp-modal-backdrop') || document.getElementById(this.dataset.modalClose);
          if (backdrop) backdrop.classList.remove('open');
        }
      });
    });
    document.addEventListener('keydown', e => {
      if (e.key === 'Escape') {
        document.querySelectorAll('.hp-modal-backdrop.open').forEach(m => m.classList.remove('open'));
      }
    });
  }

  /* ---- PASSWORD TOGGLE ---- */
  function initPassToggle() {
    document.querySelectorAll('.hp-pass-toggle').forEach(btn => {
      btn.addEventListener('click', function () {
        const input = document.getElementById(this.dataset.target);
        if (!input) return;
        const isText = input.type === 'text';
        input.type = isText ? 'password' : 'text';
        const icon = this.querySelector('i');
        if (icon) icon.className = isText ? 'bi bi-eye' : 'bi bi-eye-slash';
      });
    });
  }

  /* ---- TOPBAR MOBILE BTN ---- */
  function initTopbarMobile() {
    const btn = document.getElementById('sidebar-mobile-btn');
    if (!btn) return;
    btn.addEventListener('click', () => {
      const sidebar = document.getElementById('admin-sidebar');
      const overlay = document.getElementById('admin-overlay');
      if (sidebar) sidebar.classList.toggle('mobile-open');
      if (overlay) overlay.classList.toggle('show');
    });
  }

  /* ---- CURSOR OMNITRIX EN ADMIN ---- */
  function initAdminCursor() {
    if (window.matchMedia('(hover: none)').matches) return;
    // Crear cursor si no existe (admin no carga main.js)
    if (!document.getElementById('hp-cursor')) {
      const dot  = document.createElement('div'); dot.id  = 'hp-cursor';
      const ring = document.createElement('div'); ring.id = 'hp-cursor-ring';
      document.body.appendChild(dot);
      document.body.appendChild(ring);
    }
    const dot  = document.getElementById('hp-cursor');
    const ring = document.getElementById('hp-cursor-ring');
    if (!dot || !ring) return;

    let mx=0, my=0, rx=0, ry=0;
    document.addEventListener('mousemove', e => { mx=e.clientX; my=e.clientY; dot.style.left=mx+'px'; dot.style.top=my+'px'; });
    (function loop(){ rx+=(mx-rx)*0.12; ry+=(my-ry)*0.12; ring.style.left=rx+'px'; ring.style.top=ry+'px'; requestAnimationFrame(loop); })();

    document.querySelectorAll('a,button,.admin-stat-card,.sidebar-item,.tbl-btn,.topbar-btn,.hp-card').forEach(el => {
      el.addEventListener('mouseenter', () => { ring.classList.add('hovering'); dot.classList.add('hovering'); });
      el.addEventListener('mouseleave', () => { ring.classList.remove('hovering'); dot.classList.remove('hovering'); });
    });
  }

  /* ---- INIT ---- */
  document.addEventListener('DOMContentLoaded', () => {
    initSidebar();
    initAlerts();
    initThemeBtn();
    initTableSearch();
    initDeleteConfirm();
    initModals();
    initPassToggle();
    initTopbarMobile();
    initAdminCursor();
  });
})();
