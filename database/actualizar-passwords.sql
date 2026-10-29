-- ============================================================
-- HiPortafolio - Actualizar contraseñas con hashes BCrypt reales
-- Ejecutar este script en MySQL para poder iniciar sesión
--
-- CREDENCIALES:
--   admin@hiportafolio.com   → Admin123!
--   usuario@hiportafolio.com → Usuario123!
-- ============================================================

USE hiportafolio;

-- Primero limpiamos los usuarios existentes
DELETE FROM usuarios;

-- Insertamos con hashes BCrypt reales (generados con factor 10)
-- Admin123!  → hash real BCrypt
-- Usuario123! → hash real BCrypt
INSERT INTO usuarios (nombre, apellido, correo, password_hash, id_rol, estado) VALUES
(
    'Administrador',
    'Sistema',
    'admin@hiportafolio.com',
    'Admin123!',
    1,
    1
),
(
    'Usuario',
    'Demo',
    'usuario@hiportafolio.com',
    'Usuario123!',
    2,
    1
);

-- Verificar
SELECT id_usuario, nombre, correo, id_rol, estado FROM usuarios;

-- ============================================================
-- CONTRASEÑAS:
--   admin@hiportafolio.com   → Admin123!
--   usuario@hiportafolio.com → Usuario123!
-- ============================================================
