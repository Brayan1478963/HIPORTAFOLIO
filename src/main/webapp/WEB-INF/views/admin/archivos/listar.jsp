<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es" data-theme="dark">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Archivos — Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css"></head>
<body class="admin-body"><div class="admin-layout">
<%@ include file="../sidebar.jsp" %>
<div class="admin-main" id="admin-main">
<header class="admin-topbar">
  <div class="topbar-left"><button id="sidebar-mobile-btn" class="topbar-btn"><i class="bi bi-list fs-5"></i></button>
    <div class="topbar-breadcrumb"><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a><span class="sep">/</span><span>Archivos</span></div></div>
  <div class="topbar-right"><button class="topbar-btn" id="hp-theme-btn"><i class="bi bi-sun"></i></button>
    <a href="${pageContext.request.contextPath}/logout" class="topbar-btn danger"><i class="bi bi-box-arrow-right"></i></a></div>
</header>
<main class="admin-content">
  <c:if test="${not empty param.mensaje}"><div class="hp-alert hp-alert-success"><i class="bi bi-check-circle-fill"></i><span>${param.mensaje}</span><button class="hp-alert-close">×</button></div></c:if>
  <c:if test="${not empty error}"><div class="hp-alert hp-alert-danger"><i class="bi bi-exclamation-triangle-fill"></i><span>${error}</span><button class="hp-alert-close">×</button></div></c:if>
  <div class="page-header"><div><span class="page-tag">// GESTIÓN</span><h1 class="page-title">ARCHIVOS</h1></div></div>

  <!-- Subir archivo -->
  <div class="admin-panel" style="max-width:700px;margin-bottom:1.5rem">
    <div class="admin-panel-header"><div class="admin-panel-title"><i class="bi bi-upload"></i>SUBIR ARCHIVO</div></div>
    <div class="admin-panel-body">
      <form action="${pageContext.request.contextPath}/admin/archivos" method="post" enctype="multipart/form-data">
        <input type="hidden" name="accion" value="guardar">
        <div class="hp-form-grid cols-2">
          <div class="hp-field">
            <label class="hp-label">Tipo</label>
            <select name="tipo" class="hp-select">
              <option value="evidencias">Evidencia</option>
              <option value="proyectos">Proyecto</option>
              <option value="documentos">Documento</option>
            </select>
          </div>
          <div class="hp-field">
            <label class="hp-label">Archivo <span class="required">*</span></label>
            <!-- Zona de drop futurista -->
            <div id="dropZone" style="border:2px dashed var(--border-main);border-radius:8px;padding:1.5rem;text-align:center;cursor:pointer;transition:all 0.2s;background:var(--bg-secondary);position:relative"
                 onclick="document.getElementById('fileInput').click()"
                 ondragover="event.preventDefault();this.style.borderColor='var(--green-main)';this.style.background='rgba(124,255,0,0.06)'"
                 ondragleave="this.style.borderColor='var(--border-main)';this.style.background='var(--bg-secondary)'"
                 ondrop="event.preventDefault();this.style.borderColor='var(--border-main)';this.style.background='var(--bg-secondary)';handleDrop(event)">
              <i class="bi bi-cloud-upload" style="font-size:2rem;color:var(--green-tech);display:block;margin-bottom:0.5rem"></i>
              <div id="dropText" style="font-family:var(--font-tech);font-size:0.65rem;letter-spacing:2px;color:var(--text-secondary)">ARRASTRA UN ARCHIVO AQUÍ</div>
              <div style="font-size:0.7rem;color:var(--text-muted);margin-top:0.3rem">o haz clic para seleccionar</div>
              <input type="file" id="fileInput" name="archivo" required accept=".pdf,.doc,.docx,.xls,.xlsx,.jpg,.jpeg,.png,.gif,.zip,.txt"
                     style="position:absolute;inset:0;opacity:0;cursor:pointer;width:100%;height:100%"
                     onchange="updateDropText(this)">
            </div>
            <span class="hp-field-hint"><i class="bi bi-info-circle me-1"></i>Máx. 10 MB · PDF, Word, Excel, Imágenes, ZIP</span>
          </div>
        </div>
        <div class="hp-form-actions">
          <button type="submit" class="topbar-btn" style="border-color:var(--green-main);color:var(--green-main)"><i class="bi bi-upload me-1"></i>SUBIR ARCHIVO</button>
        </div>
      </form>
    </div>
  </div>

  <!-- Lista archivos -->
  <div class="admin-panel">
    <div class="admin-panel-header">
      <div class="admin-panel-title"><i class="bi bi-folder"></i>ARCHIVOS ALMACENADOS</div>
      <div class="table-search"><i class="bi bi-search"></i><input type="text" class="table-search-input" data-table=".hp-table" placeholder="Buscar..."></div>
    </div>
    <div class="admin-panel-body-p0"><div class="hp-table-wrapper">
      <table class="hp-table">
        <thead><tr><th>Nombre</th><th>Tipo MIME</th><th>Tamaño</th><th>Subido por</th><th>Fecha</th><th style="text-align:right">Acciones</th></tr></thead>
        <tbody>
          <c:forEach var="a" items="${archivos}">
          <tr>
            <td class="td-primary"><i class="${a.iconoTipo} me-2" style="color:var(--green-tech)"></i>${a.nombreOriginal}</td>
            <td style="font-size:0.72rem;color:var(--text-muted)">${a.tipoMime}</td>
            <td><span class="hp-badge hp-badge-gray">${a.tamanoFormateado}</span></td>
            <td style="font-size:0.75rem;color:var(--text-muted)">${a.nombreUsuario}</td>
            <td style="font-size:0.72rem;color:var(--text-muted)">${a.fechaSubida}</td>
            <td><div class="table-actions">
              <a href="${pageContext.request.contextPath}/archivos/ver?id=${a.idArchivo}" target="_blank" class="tbl-btn" title="Ver"><i class="bi bi-eye"></i></a>
              <a href="${pageContext.request.contextPath}/archivos/descargar?id=${a.idArchivo}" class="tbl-btn" title="Descargar"><i class="bi bi-download"></i></a>
              <form method="post" action="${pageContext.request.contextPath}/admin/archivos/eliminar" class="d-inline">
                <input type="hidden" name="id" value="${a.idArchivo}">
                <button class="tbl-btn delete" data-confirm="¿Eliminar archivo permanentemente?" title="Eliminar"><i class="bi bi-trash"></i></button>
              </form>
            </div></td>
          </tr>
          </c:forEach>
          <c:if test="${empty archivos}"><tr><td colspan="6"><div class="hp-empty"><i class="bi bi-folder-x"></i><h4>SIN ARCHIVOS</h4><p>Sube el primer archivo.</p></div></td></tr></c:if>
        </tbody>
      </table>
    </div></div>
  </div>
</main></div></div>
<script src="${pageContext.request.contextPath}/assets/js/admin.js"></script>
<script>
document.getElementById('nav-archivos')&&document.getElementById('nav-archivos').classList.add('active');
function updateDropText(input) {
  const zone = document.getElementById('dropText');
  if (input.files && input.files[0]) {
    zone.textContent = '✓ ' + input.files[0].name;
    zone.style.color = 'var(--green-main)';
    document.getElementById('dropZone').style.borderColor = 'var(--green-main)';
    document.getElementById('dropZone').style.background = 'rgba(124,255,0,0.06)';
  }
}
function handleDrop(e) {
  const file = e.dataTransfer.files[0];
  if (file) {
    const dt = new DataTransfer();
    dt.items.add(file);
    document.getElementById('fileInput').files = dt.files;
    updateDropText(document.getElementById('fileInput'));
  }
}
</script>
</body></html>
