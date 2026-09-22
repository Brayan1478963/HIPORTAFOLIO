/* =============================================
   HiPortafolio - validation.js
   Validaciones del lado del cliente
   ============================================= */

document.addEventListener('DOMContentLoaded', function () {

  // Activar validación Bootstrap en todos los formularios con clase needs-validation
  document.querySelectorAll('form.needs-validation').forEach(form => {
    form.addEventListener('submit', function (e) {
      if (!form.checkValidity()) {
        e.preventDefault();
        e.stopPropagation();
      }
      form.classList.add('was-validated');
    });
  });

  // Validar que contraseñas coincidan si existe campo confirmación
  const pwd     = document.getElementById('password');
  const pwdConf = document.getElementById('passwordConfirm');
  if (pwd && pwdConf) {
    pwdConf.addEventListener('input', function () {
      if (pwd.value !== pwdConf.value) {
        pwdConf.setCustomValidity('Las contraseñas no coinciden.');
      } else {
        pwdConf.setCustomValidity('');
      }
    });
  }

  // Preview de imagen antes de subir
  const fileInput = document.querySelector('input[type="file"][data-preview]');
  if (fileInput) {
    fileInput.addEventListener('change', function () {
      const previewId = this.dataset.preview;
      const preview   = document.getElementById(previewId);
      if (preview && this.files && this.files[0]) {
        const reader = new FileReader();
        reader.onload = e => { preview.src = e.target.result; preview.style.display = 'block'; };
        reader.readAsDataURL(this.files[0]);
      }
    });
  }

  // Contador de caracteres para textareas con data-maxlength
  document.querySelectorAll('textarea[data-maxlength]').forEach(ta => {
    const max     = parseInt(ta.dataset.maxlength);
    const counter = document.createElement('small');
    counter.className = 'text-muted form-text';
    counter.textContent = `0 / ${max}`;
    ta.parentNode.appendChild(counter);
    ta.addEventListener('input', () => {
      const len = ta.value.length;
      counter.textContent = `${len} / ${max}`;
      counter.className = len > max * 0.9 ? 'text-warning form-text' : 'text-muted form-text';
    });
  });

});
