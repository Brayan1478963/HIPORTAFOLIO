-- ================================================================
-- HiPortafolio — fix-datos.sql
-- Activa tecnologías, proyectos y corrige hashes de contraseñas
-- EJECUTAR en MySQL Workbench o consola MySQL
-- ================================================================
USE hiportafolio;

-- 1. Activar TODAS las tecnologías
UPDATE tecnologias SET estado = 1;

-- 2. Activar TODOS los proyectos
UPDATE proyectos SET activo = 1;

-- 3. Activar TODAS las unidades y semanas
UPDATE unidades SET estado = 1;
UPDATE semanas  SET estado = 1;

-- 4. Actualizar contraseñas con hashes BCrypt REALES
-- Admin:   admin@hiportafolio.com  → Admin123!
-- Usuario: usuario@hiportafolio.com → Usuario123!
-- Hash generado con BCrypt factor 12

UPDATE usuarios
SET password_hash = '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8wBHmHKDDK5OVq0.Wy2'
WHERE correo = 'admin@hiportafolio.com';

UPDATE usuarios
SET password_hash = '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.'
WHERE correo = 'usuario@hiportafolio.com';

-- 5. Si no existen usuarios, crearlos
INSERT IGNORE INTO usuarios (nombre, apellido, correo, password_hash, id_rol, estado)
VALUES
('Administrador', 'Sistema',   'admin@hiportafolio.com',   '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8wBHmHKDDK5OVq0.Wy2', 1, 1),
('Usuario',       'Demo',      'usuario@hiportafolio.com', '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',   2, 1);

-- 6. Verificación final
SELECT 'usuarios'     AS tabla, COUNT(*) AS total FROM usuarios
UNION ALL SELECT 'tecnologias',  COUNT(*) FROM tecnologias  WHERE estado = 1
UNION ALL SELECT 'proyectos',    COUNT(*) FROM proyectos    WHERE activo = 1
UNION ALL SELECT 'unidades',     COUNT(*) FROM unidades     WHERE estado = 1
UNION ALL SELECT 'semanas',      COUNT(*) FROM semanas      WHERE estado = 1;

-- ================================================================
-- CREDENCIALES TRAS EJECUTAR ESTE SCRIPT:
--   admin@hiportafolio.com   → Admin123!
--   usuario@hiportafolio.com → Usuario123!
-- ================================================================
