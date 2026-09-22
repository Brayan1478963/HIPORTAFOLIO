-- ============================================================
-- HiPortafolio - SCHEMA.SQL
-- Base de datos: hiportafolio
-- Motor: MySQL 8.0+
-- Descripción: Estructura completa normalizada (3NF)
-- Curso: Arquitectura de Software - UPLA
-- ============================================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS hiportafolio
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hiportafolio;

-- ============================================================
-- TABLA: roles
-- Descripción: Define los roles del sistema (ADMIN, USUARIO)
-- ============================================================
CREATE TABLE IF NOT EXISTS roles (
    id_rol      INT          NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(50)  NOT NULL,
    descripcion VARCHAR(255) NULL,
    estado      TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_roles      PRIMARY KEY (id_rol),
    CONSTRAINT uq_roles_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: usuarios
-- Descripción: Usuarios del sistema con autenticación
-- ============================================================
CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario     INT          NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(100) NOT NULL,
    apellido       VARCHAR(100) NOT NULL,
    correo         VARCHAR(150) NOT NULL,
    password_hash  VARCHAR(255) NOT NULL COMMENT 'BCrypt hash, nunca texto plano',
    id_rol         INT          NOT NULL DEFAULT 2 COMMENT '1=ADMIN, 2=USUARIO',
    estado         TINYINT(1)   NOT NULL DEFAULT 1 COMMENT '1=activo, 0=inactivo',
    fecha_creacion DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_usuarios     PRIMARY KEY (id_usuario),
    CONSTRAINT uq_usuarios_correo UNIQUE (correo),
    CONSTRAINT fk_usuarios_rol FOREIGN KEY (id_rol)
        REFERENCES roles(id_rol)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: categorias
-- Descripción: Categorías para clasificar tecnologías
-- ============================================================
CREATE TABLE IF NOT EXISTS categorias (
    id_categoria INT          NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(100) NOT NULL,
    descripcion  VARCHAR(255) NULL,
    CONSTRAINT pk_categorias PRIMARY KEY (id_categoria),
    CONSTRAINT uq_categorias_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: unidades
-- Descripción: Las 4 unidades académicas del curso
-- ============================================================
CREATE TABLE IF NOT EXISTS unidades (
    id_unidad   INT          NOT NULL AUTO_INCREMENT,
    numero      INT          NOT NULL COMMENT 'Número de unidad: 1, 2, 3, 4',
    titulo      VARCHAR(300) NOT NULL,
    descripcion TEXT         NULL,
    estado      TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_unidades   PRIMARY KEY (id_unidad),
    CONSTRAINT uq_unidades_numero UNIQUE (numero)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: semanas
-- Descripción: Las 16 semanas del curso (4 por unidad)
-- ============================================================
CREATE TABLE IF NOT EXISTS semanas (
    id_semana   INT          NOT NULL AUTO_INCREMENT,
    id_unidad   INT          NOT NULL,
    numero      INT          NOT NULL COMMENT 'Número de semana: 1 al 16',
    titulo      VARCHAR(300) NOT NULL,
    descripcion TEXT         NULL,
    objetivo    TEXT         NULL,
    estado      TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_semanas    PRIMARY KEY (id_semana),
    CONSTRAINT uq_semanas_numero UNIQUE (numero),
    CONSTRAINT fk_semanas_unidad FOREIGN KEY (id_unidad)
        REFERENCES unidades(id_unidad)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: contenidos
-- Descripción: Contenido educativo de cada semana
-- ============================================================
CREATE TABLE IF NOT EXISTS contenidos (
    id_contenido INT          NOT NULL AUTO_INCREMENT,
    id_semana    INT          NOT NULL,
    titulo       VARCHAR(300) NOT NULL,
    descripcion  TEXT         NULL,
    contenido    LONGTEXT     NULL COMMENT 'Texto principal del contenido',
    aprendizaje  TEXT         NULL COMMENT 'Aprendizaje obtenido',
    reflexion    TEXT         NULL COMMENT 'Reflexión personal',
    referencias  TEXT         NULL COMMENT 'Bibliografía y referencias',
    orden        INT          NOT NULL DEFAULT 1,
    estado       TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_contenidos  PRIMARY KEY (id_contenido),
    CONSTRAINT fk_contenidos_semana FOREIGN KEY (id_semana)
        REFERENCES semanas(id_semana)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: archivos
-- Descripción: Metadatos de archivos subidos al sistema
-- ============================================================
CREATE TABLE IF NOT EXISTS archivos (
    id_archivo       INT          NOT NULL AUTO_INCREMENT,
    nombre_original  VARCHAR(255) NOT NULL COMMENT 'Nombre original del archivo',
    nombre_sistema   VARCHAR(255) NOT NULL COMMENT 'Nombre generado internamente (UUID)',
    ruta             VARCHAR(500) NOT NULL COMMENT 'Ruta relativa en el servidor',
    tipo_mime        VARCHAR(100) NOT NULL,
    tamano           BIGINT       NOT NULL COMMENT 'Tamaño en bytes',
    id_usuario       INT          NULL COMMENT 'Usuario que subió el archivo',
    estado           TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_subida     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_archivos PRIMARY KEY (id_archivo),
    CONSTRAINT uq_archivos_nombre_sistema UNIQUE (nombre_sistema),
    CONSTRAINT fk_archivos_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: evidencias
-- Descripción: Evidencias de aprendizaje por semana
-- ============================================================
CREATE TABLE IF NOT EXISTS evidencias (
    id_evidencia INT          NOT NULL AUTO_INCREMENT,
    id_semana    INT          NOT NULL,
    id_archivo   INT          NULL COMMENT 'Archivo adjunto opcional',
    titulo       VARCHAR(300) NOT NULL,
    descripcion  TEXT         NULL,
    tipo         VARCHAR(100) NULL COMMENT 'imagen, pdf, documento, diagrama, captura',
    fecha_evidencia DATE       NULL,
    estado       TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_evidencias  PRIMARY KEY (id_evidencia),
    CONSTRAINT fk_evidencias_semana FOREIGN KEY (id_semana)
        REFERENCES semanas(id_semana)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_evidencias_archivo FOREIGN KEY (id_archivo)
        REFERENCES archivos(id_archivo)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: tecnologias
-- Descripción: Tecnologías que se muestran en el portafolio
-- ============================================================
CREATE TABLE IF NOT EXISTS tecnologias (
    id_tecnologia INT          NOT NULL AUTO_INCREMENT,
    id_categoria  INT          NULL,
    nombre        VARCHAR(100) NOT NULL,
    descripcion   TEXT         NULL,
    icono         VARCHAR(255) NULL COMMENT 'Clase CSS del ícono (ej: devicons)',
    nivel         VARCHAR(50)  NULL COMMENT 'Básico, Intermedio, Avanzado',
    estado        TINYINT(1)   NOT NULL DEFAULT 1,
    orden         INT          NOT NULL DEFAULT 1,
    CONSTRAINT pk_tecnologias PRIMARY KEY (id_tecnologia),
    CONSTRAINT fk_tecnologias_categoria FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria)
        ON UPDATE CASCADE
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: proyectos
-- Descripción: Proyectos académicos del portafolio
-- ============================================================
CREATE TABLE IF NOT EXISTS proyectos (
    id_proyecto  INT          NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(200) NOT NULL,
    descripcion  TEXT         NULL,
    objetivo     TEXT         NULL,
    problema     TEXT         NULL COMMENT 'Problema que resuelve',
    arquitectura VARCHAR(200) NULL COMMENT 'Arquitectura utilizada',
    funcionalidades TEXT      NULL,
    repositorio  VARCHAR(500) NULL COMMENT 'URL repositorio Git',
    demo         VARCHAR(500) NULL COMMENT 'URL demo',
    imagen       VARCHAR(500) NULL COMMENT 'Ruta de imagen del proyecto',
    estado       VARCHAR(50)  NOT NULL DEFAULT 'en_desarrollo' COMMENT 'en_desarrollo, completado, pausado',
    fecha_inicio DATE         NULL,
    fecha_fin    DATE         NULL,
    orden        INT          NOT NULL DEFAULT 1,
    activo       TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_proyectos  PRIMARY KEY (id_proyecto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- TABLA: proyecto_tecnologia (relación N:M)
-- Descripción: Qué tecnologías usa cada proyecto
-- ============================================================
CREATE TABLE IF NOT EXISTS proyecto_tecnologia (
    id_proyecto   INT NOT NULL,
    id_tecnologia INT NOT NULL,
    CONSTRAINT pk_proyecto_tecnologia PRIMARY KEY (id_proyecto, id_tecnologia),
    CONSTRAINT fk_pt_proyecto FOREIGN KEY (id_proyecto)
        REFERENCES proyectos(id_proyecto)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fk_pt_tecnologia FOREIGN KEY (id_tecnologia)
        REFERENCES tecnologias(id_tecnologia)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================================
-- ÍNDICES adicionales para mejorar rendimiento de consultas
-- ============================================================
CREATE INDEX idx_semanas_unidad   ON semanas(id_unidad);
CREATE INDEX idx_contenidos_semana ON contenidos(id_semana);
CREATE INDEX idx_evidencias_semana ON evidencias(id_semana);
CREATE INDEX idx_archivos_usuario  ON archivos(id_usuario);
CREATE INDEX idx_usuarios_correo   ON usuarios(correo);
CREATE INDEX idx_usuarios_estado   ON usuarios(estado);
CREATE INDEX idx_proyectos_activo  ON proyectos(activo);
CREATE INDEX idx_tecnologias_cat   ON tecnologias(id_categoria);
