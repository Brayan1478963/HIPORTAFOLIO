<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html><html lang="es">
<head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contacto — HiPortafolio</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet"></head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top shadow">
  <div class="container">
    <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/portafolio"><i class="bi bi-mortarboard-fill text-primary me-2"></i>HiPortafolio</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu"><span class="navbar-toggler-icon"></span></button>
    <div class="collapse navbar-collapse" id="navMenu">
      <ul class="navbar-nav ms-auto">
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/portafolio">Inicio</a></li>
        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/unidades">Unidades</a></li>
        <li class="nav-item"><a class="nav-link btn btn-outline-primary btn-sm px-3 ms-2" href="${pageContext.request.contextPath}/login">Login</a></li>
      </ul>
    </div>
  </div>
</nav>
<div style="padding-top:76px;">
  <div class="bg-dark text-white py-5"><div class="container text-center">
    <h1 class="fw-bold"><i class="bi bi-envelope me-2"></i>Contacto</h1>
    <p class="lead text-white-50 mb-0">Universidad Peruana Los Andes — Arquitectura de Software</p>
  </div></div>
  <div class="container py-5"><div class="row justify-content-center">
    <div class="col-lg-6">
      <div class="card border-0 shadow-sm">
        <div class="card-body p-5 text-center">
          <i class="bi bi-person-circle text-primary" style="font-size:5rem;"></i>
          <h4 class="fw-bold mt-3 mb-1">Estudiante UPLA</h4>
          <p class="text-muted mb-4">Ingeniería de Sistemas y Computación</p>
          <div class="d-flex flex-column gap-3">
            <div class="p-3 bg-light rounded d-flex align-items-center gap-3">
              <i class="bi bi-building text-primary fs-4"></i>
              <div class="text-start"><div class="fw-semibold small">Universidad</div><div class="text-muted small">Universidad Peruana Los Andes (UPLA)</div></div>
            </div>
            <div class="p-3 bg-light rounded d-flex align-items-center gap-3">
              <i class="bi bi-book text-success fs-4"></i>
              <div class="text-start"><div class="fw-semibold small">Curso</div><div class="text-muted small">Arquitectura de Software</div></div>
            </div>
            <div class="p-3 bg-light rounded d-flex align-items-center gap-3">
              <i class="bi bi-calendar text-info fs-4"></i>
              <div class="text-start"><div class="fw-semibold small">Ciclo</div><div class="text-muted small">2026</div></div>
            </div>
          </div>
          <div class="mt-4">
            <a href="${pageContext.request.contextPath}/portafolio" class="btn btn-primary btn-lg">
              <i class="bi bi-arrow-left me-2"></i>Volver al Portafolio
            </a>
          </div>
        </div>
      </div>
    </div>
  </div></div>
</div>
<footer class="bg-dark text-white py-4"><div class="container text-center"><p class="mb-0 text-secondary small">HiPortafolio — UPLA | Arquitectura de Software</p></div></footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
