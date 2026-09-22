<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html><html lang="es">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${evidencia.titulo} — HiPortafolio</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet"></head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top shadow">
  <div class="container">
    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/portafolio"><i class="bi bi-mortarboard-fill text-primary me-2"></i>HiPortafolio</a>
  </div>
</nav>
<div style="padding-top:76px;" class="container py-5">
  <nav aria-label="breadcrumb">
    <ol class="breadcrumb"><li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/evidencias">Evidencias</a></li>
    <li class="breadcrumb-item active">${evidencia.titulo}</li></ol>
  </nav>
  <div class="card border-0 shadow-sm">
    <div class="card-body p-4">
      <div class="d-flex justify-content-between align-items-start mb-3">
        <h4 class="fw-bold">${evidencia.titulo}</h4>
        <span class="badge ${evidencia.badgeClase} fs-6">${evidencia.tipoEtiqueta}</span>
      </div>
      <p class="text-muted">${evidencia.descripcion}</p>
      <div class="row g-3 mb-4">
        <div class="col-md-4"><div class="p-3 bg-light rounded"><small class="text-muted fw-semibold">Semana</small><div>Semana ${evidencia.numeroSemana} — ${evidencia.tituloSemana}</div></div></div>
        <div class="col-md-4"><div class="p-3 bg-light rounded"><small class="text-muted fw-semibold">Unidad</small><div>${evidencia.tituloUnidad}</div></div></div>
        <div class="col-md-4"><div class="p-3 bg-light rounded"><small class="text-muted fw-semibold">Fecha</small><div>${evidencia.fechaEvidencia}</div></div></div>
      </div>
      <c:if test="${evidencia.idArchivo > 0}">
        <div class="d-flex gap-2">
          <a href="${pageContext.request.contextPath}/archivos/ver?id=${evidencia.idArchivo}" target="_blank" class="btn btn-primary"><i class="bi bi-eye me-1"></i>Ver Archivo</a>
          <a href="${pageContext.request.contextPath}/archivos/descargar?id=${evidencia.idArchivo}" class="btn btn-outline-secondary"><i class="bi bi-download me-1"></i>Descargar</a>
        </div>
      </c:if>
    </div>
  </div>
  <a href="${pageContext.request.contextPath}/evidencias" class="btn btn-outline-secondary mt-3"><i class="bi bi-arrow-left me-1"></i>Volver</a>
</div>
<footer class="bg-dark text-white py-4 mt-5"><div class="container text-center"><p class="mb-0 text-secondary small">HiPortafolio — UPLA</p></div></footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
