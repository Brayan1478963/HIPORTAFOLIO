-- ============================================================
-- HiPortafolio - DATA.SQL
-- Datos iniciales del sistema
-- IMPORTANTE: Ejecutar DESPUÉS de schema.sql
-- ============================================================

USE hiportafolio;

-- ============================================================
-- ROLES
-- ============================================================
INSERT INTO roles (id_rol, nombre, descripcion) VALUES
(1, 'ADMIN',   'Administrador con acceso total al sistema'),
(2, 'USUARIO', 'Usuario con acceso de solo lectura al portafolio');

-- ============================================================
-- USUARIOS
-- Contraseñas hasheadas con BCrypt (factor 12):
--   admin123  → hash incluido abajo
--   usuario123 → hash incluido abajo
-- NUNCA almacenar contraseñas en texto plano
-- ============================================================
INSERT INTO usuarios (nombre, apellido, correo, password_hash, id_rol, estado) VALUES
(
    'Administrador',
    'HiPortafolio',
    'admin@hiportafolio.com',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8wBHmHKDDK5OVq0.Wy2',
    1,
    1
),
(
    'Usuario',
    'Demo',
    'usuario@hiportafolio.com',
    '$2a$12$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.',
    2,
    1
);
-- Nota: Los hashes anteriores son de ejemplo. Al ejecutar data.sql
-- la aplicación generará hashes reales usando PasswordUtil al registrar usuarios.
-- Para pruebas iniciales, usar el script database.sql que incluye hashes reales.

-- ============================================================
-- CATEGORÍAS DE TECNOLOGÍAS
-- ============================================================
INSERT INTO categorias (nombre, descripcion) VALUES
('Backend',     'Tecnologías del lado del servidor'),
('Frontend',    'Tecnologías del lado del cliente'),
('Base de datos', 'Sistemas de gestión de base de datos'),
('Herramientas', 'Herramientas de desarrollo y construcción'),
('DevOps',      'Herramientas de despliegue y operaciones');

-- ============================================================
-- TECNOLOGÍAS
-- ============================================================
INSERT INTO tecnologias (id_categoria, nombre, descripcion, icono, nivel, orden) VALUES
(1, 'Java',        'Lenguaje de programación orientado a objetos, principal tecnología del backend', 'devicon-java-plain', 'Intermedio', 1),
(1, 'JSP',         'Jakarta Server Pages para generación dinámica de vistas web', 'devicon-java-plain', 'Intermedio', 2),
(1, 'Servlets',    'Componentes Java para manejo de peticiones HTTP en el servidor', 'devicon-java-plain', 'Intermedio', 3),
(3, 'MySQL',       'Sistema de gestión de base de datos relacional open source', 'devicon-mysql-plain', 'Intermedio', 4),
(2, 'HTML5',       'Lenguaje de marcado estándar para la estructura de páginas web', 'devicon-html5-plain', 'Avanzado', 5),
(2, 'CSS3',        'Hojas de estilo en cascada para diseño y presentación web', 'devicon-css3-plain', 'Avanzado', 6),
(2, 'JavaScript',  'Lenguaje de programación para interactividad en el cliente', 'devicon-javascript-plain', 'Intermedio', 7),
(2, 'Bootstrap 5', 'Framework CSS para diseño responsive y componentes UI modernos', 'devicon-bootstrap-plain', 'Intermedio', 8),
(4, 'Maven',       'Herramienta de gestión y construcción de proyectos Java', 'devicon-maven-plain', 'Básico', 9),
(4, 'Git',         'Sistema de control de versiones distribuido', 'devicon-git-plain', 'Intermedio', 10),
(4, 'Apache Tomcat', 'Servidor web y contenedor de Servlets Jakarta EE', 'devicon-tomcat-line', 'Básico', 11),
(5, 'Docker',      'Plataforma de contenedores para despliegue portable de aplicaciones', 'devicon-docker-plain', 'Básico', 12),
(1, 'JDBC',        'API de Java para conectividad con bases de datos relacionales', 'devicon-java-plain', 'Intermedio', 13);

-- ============================================================
-- UNIDADES ACADÉMICAS
-- ============================================================
INSERT INTO unidades (numero, titulo, descripcion) VALUES
(1,
 'FUNDAMENTOS DE LA ARQUITECTURA DE SOFTWARE Y ESTÁNDARES INTERNACIONALES',
 'Introducción a los conceptos fundamentales de la arquitectura de software, principios, atributos de calidad, estándares internacionales, estilos y patrones arquitectónicos, y documentación arquitectónica.'),
(2,
 'MODELADO DE LA ARQUITECTURA DE SOFTWARE MEDIANTE PROGRAMACIÓN ORIENTADA A OBJETOS',
 'Aplicación de los principios de POO a la arquitectura de software, modelado con UML, diseño de componentes y capas, y elaboración del modelo arquitectónico del sistema.'),
(3,
 'COMUNICACIÓN E INTEGRACIÓN DE ARQUITECTURAS DE SOFTWARE',
 'Fundamentos de comunicación entre arquitecturas, métodos y tecnologías de integración, diseño de interfaces y transmisión de datos, e implementación de la comunicación arquitectónica.'),
(4,
 'FRAMEWORKS Y ESTÁNDARES PARA LA IMPLEMENTACIÓN DE ARQUITECTURAS DE SOFTWARE',
 'Fundamentos de frameworks de arquitectura, normas y buenas prácticas, implementación utilizando frameworks, y evaluación y optimización de la arquitectura de software.');

-- ============================================================
-- SEMANAS (16 en total, 4 por unidad)
-- ============================================================

-- UNIDAD I - Semanas 1 al 4
INSERT INTO semanas (id_unidad, numero, titulo, descripcion, objetivo) VALUES
(1, 1,
 'Introducción a la Arquitectura de Software',
 'Exploración de los conceptos fundamentales de la arquitectura de software, su importancia en el ciclo de vida del desarrollo y su impacto en la calidad del producto final.',
 'Comprender qué es la arquitectura de software, sus objetivos, elementos fundamentales y su relevancia en el desarrollo de sistemas de calidad.'),

(1, 2,
 'Principios, Atributos de Calidad y Estándares Internacionales',
 'Estudio de los principios arquitectónicos que guían el diseño de sistemas, los atributos de calidad como rendimiento, seguridad y mantenibilidad, y los estándares internacionales aplicables.',
 'Identificar y aplicar los principios arquitectónicos, comprender los atributos de calidad del software y conocer los estándares internacionales relevantes.'),

(1, 3,
 'Estilos y Patrones Arquitectónicos',
 'Análisis comparativo de los principales estilos arquitectónicos (cliente-servidor, microservicios, capas, eventos) y patrones arquitectónicos aplicados a casos reales de la industria.',
 'Distinguir entre estilos y patrones arquitectónicos, comparar sus características y justificar la selección de una arquitectura para un proyecto específico.'),

(1, 4,
 'Documentación y Representación Arquitectónica',
 'Técnicas y herramientas para documentar arquitecturas de software, incluyendo vistas arquitectónicas, diagramas UML y decisiones de diseño.',
 'Elaborar documentación arquitectónica completa, aplicando buenas prácticas y estándares de representación como UML y vistas 4+1.');

-- UNIDAD II - Semanas 5 al 8
INSERT INTO semanas (id_unidad, numero, titulo, descripcion, objetivo) VALUES
(2, 5,
 'Principios de POO aplicados a la Arquitectura de Software',
 'Aplicación de los cuatro pilares de la Programación Orientada a Objetos (abstracción, encapsulamiento, herencia, polimorfismo) en el contexto del diseño arquitectónico.',
 'Aplicar correctamente los principios de POO en el diseño de componentes arquitectónicos, identificando responsabilidades y relaciones entre clases.'),

(2, 6,
 'Modelado Arquitectónico con UML',
 'Uso del Lenguaje Unificado de Modelado para representar la arquitectura del sistema mediante diagramas de casos de uso, clases, paquetes y componentes.',
 'Elaborar diagramas UML que representen fielmente la arquitectura del sistema, incluyendo actores, componentes y sus interacciones.'),

(2, 7,
 'Diseño de Componentes y Capas de la Arquitectura',
 'Diseño detallado de los componentes del sistema aplicando los principios de cohesión alta y bajo acoplamiento, implementando el patrón arquitectónico MVC por capas.',
 'Diseñar componentes con responsabilidades bien definidas, implementar la separación de capas (Model, View, Controller, Service, DAO) y justificar las decisiones de diseño.'),

(2, 8,
 'Elaboración y Validación del Modelo Arquitectónico',
 'Integración de todos los modelos arquitectónicos elaborados, validación contra los requisitos funcionales y no funcionales, y documentación del modelo arquitectónico final.',
 'Validar que el modelo arquitectónico satisface los requisitos del sistema, integrando los distintos diagramas y documentando las decisiones finales de diseño.');

-- UNIDAD III - Semanas 9 al 12
INSERT INTO semanas (id_unidad, numero, titulo, descripcion, objetivo) VALUES
(3, 9,
 'Fundamentos de la Comunicación entre Arquitecturas de Software',
 'Principios de la comunicación entre componentes de software, protocolos de comunicación, modelos de interacción sincrónica y asincrónica, y flujos de información entre capas.',
 'Comprender los mecanismos de comunicación entre componentes arquitectónicos, identificar protocolos apropiados y diseñar flujos de información eficientes.'),

(3, 10,
 'Métodos y Tecnologías para la Integración de Sistemas',
 'Estudio de las tecnologías de integración disponibles: servicios web SOAP, APIs REST, mensajería asincrónica (JMS, AMQP), y comparación de alternativas para la selección tecnológica.',
 'Comparar las diferentes tecnologías de integración, seleccionar la más apropiada para un contexto dado y justificar la decisión desde una perspectiva arquitectónica.'),

(3, 11,
 'Diseño de Interfaces y Transmisión de Datos',
 'Diseño de contratos de interfaz entre componentes, formatos de intercambio de datos (JSON, XML), diseño de APIs RESTful y principios de interoperabilidad.',
 'Diseñar interfaces de comunicación entre componentes, definir contratos claros y aplicar estándares de interoperabilidad para el intercambio de datos.'),

(3, 12,
 'Implementación y Validación de la Comunicación Arquitectónica',
 'Implementación práctica de los mecanismos de comunicación diseñados, pruebas de integración, validación de integridad y disponibilidad, y medición de eficiencia.',
 'Implementar y validar la comunicación entre los componentes del sistema, ejecutar pruebas de integración y verificar que se cumplan los atributos de calidad definidos.');

-- UNIDAD IV - Semanas 13 al 16
INSERT INTO semanas (id_unidad, numero, titulo, descripcion, objetivo) VALUES
(4, 13,
 'Fundamentos de Frameworks de Arquitectura de Software',
 'Conceptualización de los frameworks de arquitectura de software, sus características, ventajas y desventajas, ámbitos de aplicación y criterios de selección tecnológica.',
 'Comprender qué es un framework de arquitectura, evaluar sus ventajas y desventajas, y aplicar criterios objetivos para la selección tecnológica en un proyecto Java.'),

(4, 14,
 'Normas y Buenas Prácticas en Arquitectura de Software',
 'Estudio de las normas internacionales aplicables (ISO/IEC 25010, IEEE 1471), buenas prácticas de desarrollo, principios SOLID, DRY, KISS, y su aplicación para garantizar calidad.',
 'Aplicar normas internacionales y buenas prácticas arquitectónicas en el diseño del sistema, asegurando calidad, seguridad, interoperabilidad y rendimiento.'),

(4, 15,
 'Implementación de la Arquitectura utilizando Frameworks',
 'Implementación completa del sistema HiPortafolio aplicando el framework seleccionado, integrando todos los componentes arquitectónicos con sus patrones y principios definidos.',
 'Implementar la arquitectura del sistema de manera completa y funcional, integrando todos los componentes, patrones y principios arquitectónicos estudiados durante el curso.'),

(4, 16,
 'Evaluación y Optimización de la Arquitectura de Software',
 'Evaluación final de la arquitectura implementada mediante métricas de calidad, pruebas de rendimiento, análisis de mantenibilidad, seguridad y escalabilidad, y propuestas de mejora.',
 'Evaluar la arquitectura implementada contra los atributos de calidad definidos, identificar cuellos de botella y oportunidades de optimización, y documentar las mejoras propuestas.');

-- ============================================================
-- CONTENIDOS INICIALES (Semana 1 como ejemplo completo)
-- ============================================================
INSERT INTO contenidos (id_semana, titulo, descripcion, contenido, aprendizaje, reflexion, referencias, orden) VALUES
(1,
 'Conceptos fundamentales de Arquitectura de Software',
 'Introducción a los conceptos base que definen qué es la arquitectura de software y por qué es importante.',
 '## ¿Qué es la Arquitectura de Software?\n\nLa arquitectura de software es la estructura fundamental de un sistema de software, incluyendo sus componentes, las propiedades visibles externamente de esos componentes y las relaciones entre ellos.\n\n### Definición según IEEE 1471\nSegún el estándar IEEE 1471, la arquitectura de software se define como "la organización fundamental de un sistema encarnada en sus componentes, sus relaciones entre sí y con el entorno, y los principios que guían su diseño y evolución".\n\n### Elementos fundamentales\n- **Componentes**: Unidades computacionales o de almacenamiento de datos\n- **Conectores**: Mecanismos de comunicación e interacción entre componentes\n- **Configuración**: Arreglo topológico de componentes y conectores\n\n### Importancia\n1. Define la estructura del sistema antes de la implementación\n2. Facilita la comunicación entre stakeholders\n3. Permite el análisis temprano de atributos de calidad\n4. Guía el desarrollo y la evolución del sistema\n\n### Aplicación en HiPortafolio\nEste sistema implementa una arquitectura MVC por capas, separando claramente las responsabilidades entre la vista (JSP), el control (Servlets), la lógica de negocio (Services) y el acceso a datos (DAO).',
 'Comprensión de que la arquitectura de software no es simplemente el código, sino las decisiones estructurales que determinan la calidad, mantenibilidad y escalabilidad del sistema. La arquitectura es una abstracción del sistema que permite razonar sobre él sin necesidad de conocer todos sus detalles de implementación.',
 'Esta semana me permitió entender por qué la arquitectura es tan importante. Antes pensaba que bastaba con que el código funcionara, pero ahora comprendo que las decisiones arquitectónicas tempranas tienen un impacto enorme en el costo de mantenimiento y evolución del sistema a largo plazo.',
 '- Bass, L., Clements, P., & Kazman, R. (2012). Software Architecture in Practice (3rd ed.). Addison-Wesley.\n- IEEE Std 1471-2000. IEEE Recommended Practice for Architectural Description of Software-Intensive Systems.\n- Garlan, D., & Shaw, M. (1993). An Introduction to Software Architecture.',
 1);

-- ============================================================
-- PROYECTO PRINCIPAL: HiPortafolio
-- ============================================================
INSERT INTO proyectos (nombre, descripcion, objetivo, problema, arquitectura, funcionalidades, repositorio, estado, orden) VALUES
(
    'HiPortafolio',
    'Sistema Web de Portafolio Académico Personal desarrollado como proyecto integrador del curso de Arquitectura de Software. Implementa una arquitectura MVC por capas con Java, JSP, Servlets y MySQL.',
    'Demostrar los conocimientos adquiridos durante el curso de Arquitectura de Software mediante la implementación de un sistema web real, funcional y profesional que sirva como portafolio académico personal.',
    'La necesidad de contar con un portafolio académico dinámico y administrable que permita presentar el aprendizaje adquirido durante el curso, con capacidad de actualización sin modificar el código fuente.',
    'MVC por capas (Model-View-Controller con capas Service y DAO), implementando los principios SOLID, alta cohesión y bajo acoplamiento.',
    '- Autenticación y autorización con roles (ADMIN/USUARIO)\n- Panel administrativo con CRUD completo\n- Gestión de 4 unidades y 16 semanas académicas\n- Gestión de contenidos, evidencias y archivos\n- Portafolio público responsive\n- Despliegue con Docker',
    'https://github.com/usuario/hiportafolio',
    'completado',
    1
);

-- Asignar tecnologías al proyecto HiPortafolio
INSERT INTO proyecto_tecnologia (id_proyecto, id_tecnologia) VALUES
(1, 1),  -- Java
(1, 2),  -- JSP
(1, 3),  -- Servlets
(1, 4),  -- MySQL
(1, 5),  -- HTML5
(1, 6),  -- CSS3
(1, 7),  -- JavaScript
(1, 8),  -- Bootstrap 5
(1, 9),  -- Maven
(1, 10), -- Git
(1, 11), -- Apache Tomcat
(1, 12), -- Docker
(1, 13); -- JDBC
