-- ============================================================
-- HiPortafolio - DATABASE.SQL
-- Script completo: schema + datos iniciales
-- Uso: ejecutar este único archivo para configurar todo
-- IMPORTANTE: Requiere usuario MySQL con permisos de CREATE
-- ============================================================

-- Eliminar y recrear la base de datos (útil para reiniciar)
DROP DATABASE IF EXISTS hiportafolio;
CREATE DATABASE hiportafolio
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE hiportafolio;

-- ============================================================
-- 1. ESTRUCTURA (SCHEMA)
-- ============================================================

CREATE TABLE roles (
    id_rol      INT          NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(50)  NOT NULL,
    descripcion VARCHAR(255) NULL,
    estado      TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_roles        PRIMARY KEY (id_rol),
    CONSTRAINT uq_roles_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE usuarios (
    id_usuario          INT          NOT NULL AUTO_INCREMENT,
    nombre              VARCHAR(100) NOT NULL,
    apellido            VARCHAR(100) NOT NULL,
    correo              VARCHAR(150) NOT NULL,
    password_hash       VARCHAR(255) NOT NULL,
    id_rol              INT          NOT NULL DEFAULT 2,
    estado              TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME     NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_usuarios        PRIMARY KEY (id_usuario),
    CONSTRAINT uq_usuarios_correo UNIQUE (correo),
    CONSTRAINT fk_usuarios_rol    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE categorias (
    id_categoria INT          NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(100) NOT NULL,
    descripcion  VARCHAR(255) NULL,
    CONSTRAINT pk_categorias        PRIMARY KEY (id_categoria),
    CONSTRAINT uq_categorias_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE unidades (
    id_unidad      INT          NOT NULL AUTO_INCREMENT,
    numero         INT          NOT NULL,
    titulo         VARCHAR(300) NOT NULL,
    descripcion    TEXT         NULL,
    estado         TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_unidades        PRIMARY KEY (id_unidad),
    CONSTRAINT uq_unidades_numero UNIQUE (numero)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE semanas (
    id_semana      INT          NOT NULL AUTO_INCREMENT,
    id_unidad      INT          NOT NULL,
    numero         INT          NOT NULL,
    titulo         VARCHAR(300) NOT NULL,
    descripcion    TEXT         NULL,
    objetivo       TEXT         NULL,
    estado         TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_semanas        PRIMARY KEY (id_semana),
    CONSTRAINT uq_semanas_numero UNIQUE (numero),
    CONSTRAINT fk_semanas_unidad FOREIGN KEY (id_unidad) REFERENCES unidades(id_unidad)
        ON UPDATE CASCADE ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE contenidos (
    id_contenido        INT          NOT NULL AUTO_INCREMENT,
    id_semana           INT          NOT NULL,
    titulo              VARCHAR(300) NOT NULL,
    descripcion         TEXT         NULL,
    contenido           LONGTEXT     NULL,
    aprendizaje         TEXT         NULL,
    reflexion           TEXT         NULL,
    referencias         TEXT         NULL,
    orden               INT          NOT NULL DEFAULT 1,
    estado              TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion DATETIME     NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_contenidos         PRIMARY KEY (id_contenido),
    CONSTRAINT fk_contenidos_semana  FOREIGN KEY (id_semana) REFERENCES semanas(id_semana)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE archivos (
    id_archivo      INT          NOT NULL AUTO_INCREMENT,
    nombre_original VARCHAR(255) NOT NULL,
    nombre_sistema  VARCHAR(255) NOT NULL,
    ruta            VARCHAR(500) NOT NULL,
    tipo_mime       VARCHAR(100) NOT NULL,
    tamano          BIGINT       NOT NULL,
    id_usuario      INT          NULL,
    estado          TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_subida    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_archivos              PRIMARY KEY (id_archivo),
    CONSTRAINT uq_archivos_nombre_sist  UNIQUE (nombre_sistema),
    CONSTRAINT fk_archivos_usuario      FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
        ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE evidencias (
    id_evidencia    INT          NOT NULL AUTO_INCREMENT,
    id_semana       INT          NOT NULL,
    id_archivo      INT          NULL,
    titulo          VARCHAR(300) NOT NULL,
    descripcion     TEXT         NULL,
    tipo            VARCHAR(100) NULL,
    fecha_evidencia DATE         NULL,
    estado          TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_evidencias          PRIMARY KEY (id_evidencia),
    CONSTRAINT fk_evidencias_semana   FOREIGN KEY (id_semana)  REFERENCES semanas(id_semana)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_evidencias_archivo  FOREIGN KEY (id_archivo) REFERENCES archivos(id_archivo)
        ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tecnologias (
    id_tecnologia INT          NOT NULL AUTO_INCREMENT,
    id_categoria  INT          NULL,
    nombre        VARCHAR(100) NOT NULL,
    descripcion   TEXT         NULL,
    icono         VARCHAR(255) NULL,
    nivel         VARCHAR(50)  NULL,
    estado        TINYINT(1)   NOT NULL DEFAULT 1,
    orden         INT          NOT NULL DEFAULT 1,
    CONSTRAINT pk_tecnologias          PRIMARY KEY (id_tecnologia),
    CONSTRAINT fk_tecnologias_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
        ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE proyectos (
    id_proyecto    INT          NOT NULL AUTO_INCREMENT,
    nombre         VARCHAR(200) NOT NULL,
    descripcion    TEXT         NULL,
    objetivo       TEXT         NULL,
    problema       TEXT         NULL,
    arquitectura   VARCHAR(200) NULL,
    funcionalidades TEXT        NULL,
    repositorio    VARCHAR(500) NULL,
    demo           VARCHAR(500) NULL,
    imagen         VARCHAR(500) NULL,
    estado         VARCHAR(50)  NOT NULL DEFAULT 'en_desarrollo',
    fecha_inicio   DATE         NULL,
    fecha_fin      DATE         NULL,
    orden          INT          NOT NULL DEFAULT 1,
    activo         TINYINT(1)   NOT NULL DEFAULT 1,
    fecha_creacion DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_proyectos PRIMARY KEY (id_proyecto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE proyecto_tecnologia (
    id_proyecto   INT NOT NULL,
    id_tecnologia INT NOT NULL,
    CONSTRAINT pk_proyecto_tecnologia PRIMARY KEY (id_proyecto, id_tecnologia),
    CONSTRAINT fk_pt_proyecto    FOREIGN KEY (id_proyecto)   REFERENCES proyectos(id_proyecto)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_pt_tecnologia  FOREIGN KEY (id_tecnologia) REFERENCES tecnologias(id_tecnologia)
        ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices de rendimiento
CREATE INDEX idx_semanas_unidad    ON semanas(id_unidad);
CREATE INDEX idx_contenidos_semana ON contenidos(id_semana);
CREATE INDEX idx_evidencias_semana ON evidencias(id_semana);
CREATE INDEX idx_archivos_usuario  ON archivos(id_usuario);
CREATE INDEX idx_usuarios_correo   ON usuarios(correo);
CREATE INDEX idx_usuarios_estado   ON usuarios(estado);
CREATE INDEX idx_proyectos_activo  ON proyectos(activo);

-- ============================================================
-- 2. DATOS INICIALES
-- ============================================================

-- Roles
INSERT INTO roles (nombre, descripcion) VALUES
('ADMIN',   'Administrador con acceso total al sistema'),
('USUARIO', 'Usuario con acceso de solo lectura al portafolio');

-- Usuarios iniciales
-- ADMIN: correo=admin@hiportafolio.com  contraseña=Admin123!
-- USER:  correo=usuario@hiportafolio.com contraseña=Usuario123!
-- Hashes BCrypt generados con factor 12
INSERT INTO usuarios (nombre, apellido, correo, password_hash, id_rol, estado) VALUES
('Administrador', 'Sistema',
 'admin@hiportafolio.com',
 '$2a$12$eImiTXuWVxfM37uY4JANjQ==.........PLACEHOLDER_ADMIN_HASH',
 1, 1),
('Usuario', 'Demo',
 'usuario@hiportafolio.com',
 '$2a$12$eImiTXuWVxfM37uY4JANjQ==.........PLACEHOLDER_USER_HASH',
 2, 1);
-- IMPORTANTE: Los hashes PLACEHOLDER deben reemplazarse con hashes reales.
-- Usar la utilidad de la aplicación o el siguiente script SQL para actualizar:
-- UPDATE usuarios SET password_hash = '$2a$12$HASH_REAL' WHERE correo = 'admin@hiportafolio.com';

-- Categorías
INSERT INTO categorias (nombre, descripcion) VALUES
('Backend',       'Tecnologías del lado del servidor'),
('Frontend',      'Tecnologías del lado del cliente'),
('Base de datos', 'Sistemas de gestión de base de datos'),
('Herramientas',  'Herramientas de desarrollo y construcción'),
('DevOps',        'Herramientas de despliegue y operaciones');

-- Tecnologías
INSERT INTO tecnologias (id_categoria, nombre, descripcion, icono, nivel, orden) VALUES
(1, 'Java',          'Lenguaje de programación orientado a objetos',              'devicon-java-plain',       'Intermedio', 1),
(1, 'JSP',           'Jakarta Server Pages para generación dinámica de vistas',  'devicon-java-plain',       'Intermedio', 2),
(1, 'Servlets',      'Componentes Java para manejo de peticiones HTTP',          'devicon-java-plain',       'Intermedio', 3),
(3, 'MySQL',         'Sistema de gestión de base de datos relacional',           'devicon-mysql-plain',      'Intermedio', 4),
(2, 'HTML5',         'Lenguaje de marcado estándar para páginas web',            'devicon-html5-plain',      'Avanzado',   5),
(2, 'CSS3',          'Hojas de estilo en cascada para diseño web',               'devicon-css3-plain',       'Avanzado',   6),
(2, 'JavaScript',    'Lenguaje de programación para interactividad web',         'devicon-javascript-plain', 'Intermedio', 7),
(2, 'Bootstrap 5',   'Framework CSS para diseño responsive',                     'devicon-bootstrap-plain',  'Intermedio', 8),
(4, 'Maven',         'Herramienta de gestión de proyectos Java',                 'devicon-maven-plain',      'Básico',     9),
(4, 'Git',           'Sistema de control de versiones distribuido',              'devicon-git-plain',        'Intermedio', 10),
(4, 'Apache Tomcat', 'Servidor web y contenedor de Servlets',                   'devicon-tomcat-line',      'Básico',     11),
(5, 'Docker',        'Plataforma de contenedores para despliegue portable',      'devicon-docker-plain',     'Básico',     12),
(1, 'JDBC',          'API Java para conectividad con bases de datos',            'devicon-java-plain',       'Intermedio', 13);

-- Unidades
INSERT INTO unidades (numero, titulo, descripcion) VALUES
(1, 'FUNDAMENTOS DE LA ARQUITECTURA DE SOFTWARE Y ESTÁNDARES INTERNACIONALES',
 'Introducción a los conceptos fundamentales de la arquitectura de software, principios, atributos de calidad, estándares internacionales, estilos y patrones arquitectónicos.'),
(2, 'MODELADO DE LA ARQUITECTURA DE SOFTWARE MEDIANTE PROGRAMACIÓN ORIENTADA A OBJETOS',
 'Aplicación de los principios de POO a la arquitectura, modelado con UML, diseño de componentes y capas.'),
(3, 'COMUNICACIÓN E INTEGRACIÓN DE ARQUITECTURAS DE SOFTWARE',
 'Comunicación entre arquitecturas, métodos de integración, diseño de interfaces y transmisión de datos.'),
(4, 'FRAMEWORKS Y ESTÁNDARES PARA LA IMPLEMENTACIÓN DE ARQUITECTURAS DE SOFTWARE',
 'Frameworks de arquitectura, normas y buenas prácticas, implementación y evaluación de la arquitectura.');

-- Semanas (16 en total)
INSERT INTO semanas (id_unidad, numero, titulo, descripcion, objetivo) VALUES
-- Unidad I
(1,  1, 'Introducción a la Arquitectura de Software',
     'Conceptos base, objetivos, importancia y elementos fundamentales de la arquitectura de software.',
     'Comprender qué es la arquitectura de software, sus objetivos y su impacto en la calidad del sistema.'),
(1,  2, 'Principios, Atributos de Calidad y Estándares Internacionales',
     'Principios arquitectónicos, atributos de calidad, requisitos funcionales y no funcionales, estándares.',
     'Identificar y aplicar principios arquitectónicos y conocer los estándares internacionales relevantes.'),
(1,  3, 'Estilos y Patrones Arquitectónicos',
     'Estilos arquitectónicos, patrones, comparación y selección justificada de arquitectura.',
     'Distinguir estilos y patrones, compararlos y justificar la selección para un proyecto específico.'),
(1,  4, 'Documentación y Representación Arquitectónica',
     'Documentación arquitectónica, modelos, diagramas y buenas prácticas de representación.',
     'Elaborar documentación arquitectónica completa usando UML y vistas arquitectónicas.'),
-- Unidad II
(2,  5, 'Principios de POO aplicados a la Arquitectura de Software',
     'Abstracción, encapsulamiento, herencia, polimorfismo aplicados al diseño arquitectónico.',
     'Aplicar los cuatro pilares de POO en el diseño de componentes arquitectónicos.'),
(2,  6, 'Modelado Arquitectónico con UML',
     'Casos de uso, diagrama de clases, paquetes, actores, relaciones y componentes.',
     'Elaborar diagramas UML que representen fielmente la arquitectura del sistema.'),
(2,  7, 'Diseño de Componentes y Capas de la Arquitectura',
     'Componentes, capas, responsabilidades, cohesión, bajo acoplamiento, MVC.',
     'Diseñar componentes con responsabilidades definidas implementando separación de capas.'),
(2,  8, 'Elaboración y Validación del Modelo Arquitectónico',
     'Integración de modelos, validación, decisiones de diseño y modelo arquitectónico final.',
     'Validar que el modelo arquitectónico satisface los requisitos del sistema.'),
-- Unidad III
(3,  9, 'Fundamentos de la Comunicación entre Arquitecturas de Software',
     'Comunicación entre componentes, protocolos, modelos de interacción, flujos de información.',
     'Comprender los mecanismos de comunicación entre componentes arquitectónicos.'),
(3, 10, 'Métodos y Tecnologías para la Integración de Sistemas',
     'Servicios web, APIs, REST, mensajería, integración de sistemas y selección tecnológica.',
     'Comparar tecnologías de integración y seleccionar la más apropiada para el contexto.'),
(3, 11, 'Diseño de Interfaces y Transmisión de Datos',
     'Interfaces, comunicación, JSON, APIs, interoperabilidad y servicios.',
     'Diseñar interfaces de comunicación entre componentes con contratos claros.'),
(3, 12, 'Implementación y Validación de la Comunicación Arquitectónica',
     'Implementación, integración, pruebas, integridad, disponibilidad y eficiencia.',
     'Implementar y validar la comunicación entre componentes del sistema.'),
-- Unidad IV
(4, 13, 'Fundamentos de Frameworks de Arquitectura de Software',
     'Definición, características, ventajas, desventajas y selección de frameworks Java.',
     'Comprender qué es un framework y aplicar criterios de selección tecnológica.'),
(4, 14, 'Normas y Buenas Prácticas en Arquitectura de Software',
     'Normas internacionales, estándares, SOLID, DRY, calidad, seguridad y rendimiento.',
     'Aplicar normas y buenas prácticas para garantizar calidad en la arquitectura.'),
(4, 15, 'Implementación de la Arquitectura utilizando Frameworks',
     'Implementación completa del sistema con el framework seleccionado.',
     'Implementar la arquitectura de manera completa integrando todos los componentes.'),
(4, 16, 'Evaluación y Optimización de la Arquitectura de Software',
     'Evaluación, métricas, rendimiento, mantenibilidad, escalabilidad y optimización.',
     'Evaluar la arquitectura implementada e identificar oportunidades de mejora.');

-- Proyecto principal
INSERT INTO proyectos (nombre, descripcion, objetivo, problema, arquitectura, funcionalidades, repositorio, estado, orden) VALUES
('HiPortafolio',
 'Sistema Web de Portafolio Académico Personal - Proyecto integrador del curso de Arquitectura de Software UPLA.',
 'Demostrar los conocimientos adquiridos durante el curso mediante la implementación de un sistema web real y profesional.',
 'Necesidad de un portafolio académico dinámico y administrable que refleje el aprendizaje del curso.',
 'MVC por capas (Model-View-Controller + Service + DAO)',
 'Autenticación y autorización con roles, Panel administrativo con CRUD, Gestión de 4 unidades y 16 semanas, Portafolio público responsive, Docker',
 'https://github.com/usuario/hiportafolio',
 'completado',
 1);

-- Tecnologías del proyecto HiPortafolio (id_proyecto = 1)
INSERT INTO proyecto_tecnologia (id_proyecto, id_tecnologia)
SELECT 1, id_tecnologia FROM tecnologias WHERE estado = 1;

-- ============================================================
-- 3. VERIFICACIÓN FINAL
-- ============================================================
SELECT 'roles'      AS tabla, COUNT(*) AS registros FROM roles
UNION ALL
SELECT 'usuarios',    COUNT(*) FROM usuarios
UNION ALL
SELECT 'unidades',    COUNT(*) FROM unidades
UNION ALL
SELECT 'semanas',     COUNT(*) FROM semanas
UNION ALL
SELECT 'tecnologias', COUNT(*) FROM tecnologias
UNION ALL
SELECT 'proyectos',   COUNT(*) FROM proyectos;
