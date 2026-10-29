-- HiPortafolio - Restaurar las 4 unidades y las 16 semanas en Supabase (PostgreSQL)
-- Seguro para volver a ejecutar: no borra ni modifica semanas existentes.
-- Ejecutar en Supabase > SQL Editor. Inserta solo unidades/semanas que falten.

BEGIN;

-- Crear las unidades faltantes sin duplicar las que ya existen.
INSERT INTO public.unidades (numero, titulo, descripcion)
SELECT v.numero, v.titulo, v.descripcion
FROM (VALUES
 (1, 'FUNDAMENTOS DE LA ARQUITECTURA DE SOFTWARE Y ESTÁNDARES INTERNACIONALES',
     'Introducción a los conceptos fundamentales de la arquitectura de software, principios, atributos de calidad, estándares internacionales, estilos y patrones arquitectónicos, y documentación arquitectónica.'),
 (2, 'MODELADO DE LA ARQUITECTURA DE SOFTWARE MEDIANTE PROGRAMACIÓN ORIENTADA A OBJETOS',
     'Aplicación de los principios de POO a la arquitectura de software, modelado con UML, diseño de componentes y capas, y elaboración del modelo arquitectónico del sistema.'),
 (3, 'COMUNICACIÓN E INTEGRACIÓN DE ARQUITECTURAS DE SOFTWARE',
     'Fundamentos de comunicación entre arquitecturas, métodos y tecnologías de integración, diseño de interfaces y transmisión de datos, e implementación de la comunicación arquitectónica.'),
 (4, 'FRAMEWORKS Y ESTÁNDARES PARA LA IMPLEMENTACIÓN DE ARQUITECTURAS DE SOFTWARE',
     'Fundamentos de frameworks de arquitectura, normas y buenas prácticas, implementación utilizando frameworks, y evaluación y optimización de la arquitectura de software.')
) AS v(numero, titulo, descripcion)
WHERE NOT EXISTS (
  SELECT 1 FROM public.unidades u WHERE u.numero = v.numero
);

-- Insertar únicamente las semanas que no existan, usando los títulos y objetivos originales.
WITH semanas_base(id_unidad_numero, numero, titulo, descripcion, objetivo) AS (
 VALUES
 (1, 1, 'Introducción a la Arquitectura de Software',
  'Exploración de los conceptos fundamentales de la arquitectura de software, su importancia en el ciclo de vida del desarrollo y su impacto en la calidad del producto final.',
  'Comprender qué es la arquitectura de software, sus objetivos, elementos fundamentales y su relevancia en el desarrollo de sistemas de calidad.'),
 (1, 2, 'Principios, Atributos de Calidad y Estándares Internacionales',
  'Estudio de los principios arquitectónicos que guían el diseño de sistemas, los atributos de calidad como rendimiento, seguridad y mantenibilidad, y los estándares internacionales aplicables.',
  'Identificar y aplicar los principios arquitectónicos, comprender los atributos de calidad del software y conocer los estándares internacionales relevantes.'),
 (1, 3, 'Estilos y Patrones Arquitectónicos',
  'Análisis comparativo de los principales estilos arquitectónicos (cliente-servidor, microservicios, capas, eventos) y patrones arquitectónicos aplicados a casos reales de la industria.',
  'Distinguir entre estilos y patrones arquitectónicos, comparar sus características y justificar la selección de una arquitectura para un proyecto específico.'),
 (1, 4, 'Documentación y Representación Arquitectónica',
  'Técnicas y herramientas para documentar arquitecturas de software, incluyendo vistas arquitectónicas, diagramas UML y decisiones de diseño.',
  'Elaborar documentación arquitectónica completa, aplicando buenas prácticas y estándares de representación como UML y vistas 4+1.'),
 (2, 5, 'Principios de POO aplicados a la Arquitectura de Software',
  'Aplicación de los cuatro pilares de la Programación Orientada a Objetos (abstracción, encapsulamiento, herencia, polimorfismo) en el contexto del diseño arquitectónico.',
  'Aplicar correctamente los principios de POO en el diseño de componentes arquitectónicos, identificando responsabilidades y relaciones entre clases.'),
 (2, 6, 'Modelado Arquitectónico con UML',
  'Uso del Lenguaje Unificado de Modelado para representar la arquitectura del sistema mediante diagramas de casos de uso, clases, paquetes y componentes.',
  'Elaborar diagramas UML que representen fielmente la arquitectura del sistema, incluyendo actores, componentes y sus interacciones.'),
 (2, 7, 'Diseño de Componentes y Capas de la Arquitectura',
  'Diseño detallado de los componentes del sistema aplicando los principios de cohesión alta y bajo acoplamiento, implementando el patrón arquitectónico MVC por capas.',
  'Diseñar componentes con responsabilidades bien definidas, implementar la separación de capas (Model, View, Controller, Service, DAO) y justificar las decisiones de diseño.'),
 (2, 8, 'Elaboración y Validación del Modelo Arquitectónico',
  'Integración de todos los modelos arquitectónicos elaborados, validación contra los requisitos funcionales y no funcionales, y documentación del modelo arquitectónico final.',
  'Validar que el modelo arquitectónico satisface los requisitos del sistema, integrando los distintos diagramas y documentando las decisiones finales de diseño.'),
 (3, 9, 'Fundamentos de la Comunicación entre Arquitecturas de Software',
  'Principios de la comunicación entre componentes de software, protocolos de comunicación, modelos de interacción sincrónica y asincrónica, y flujos de información entre capas.',
  'Comprender los mecanismos de comunicación entre componentes arquitectónicos, identificar protocolos apropiados y diseñar flujos de información eficientes.'),
 (3, 10, 'Métodos y Tecnologías para la Integración de Sistemas',
  'Estudio de las tecnologías de integración disponibles: servicios web SOAP, APIs REST, mensajería asincrónica (JMS, AMQP), y comparación de alternativas para la selección tecnológica.',
  'Comparar las diferentes tecnologías de integración, seleccionar la más apropiada para un contexto dado y justificar la decisión desde una perspectiva arquitectónica.'),
 (3, 11, 'Diseño de Interfaces y Transmisión de Datos',
  'Diseño de contratos de interfaz entre componentes, formatos de intercambio de datos (JSON, XML), diseño de APIs RESTful y principios de interoperabilidad.',
  'Diseñar interfaces de comunicación entre componentes, definir contratos claros y aplicar estándares de interoperabilidad para el intercambio de datos.'),
 (3, 12, 'Implementación y Validación de la Comunicación Arquitectónica',
  'Implementación práctica de los mecanismos de comunicación diseñados, pruebas de integración, validación de integridad y disponibilidad, y medición de eficiencia.',
  'Implementar y validar la comunicación entre los componentes arquitectónicos, ejecutar pruebas de integración y verificar que se cumplan los atributos de calidad definidos.'),
 (4, 13, 'Fundamentos de Frameworks de Arquitectura de Software',
  'Conceptualización de los frameworks de arquitectura de software, sus características, ventajas y desventajas, ámbitos de aplicación y criterios de selección tecnológica.',
  'Comprender qué es un framework de arquitectura, evaluar sus ventajas y desventajas, y aplicar criterios objetivos para la selección tecnológica en un proyecto Java.'),
 (4, 14, 'Normas y Buenas Prácticas en Arquitectura de Software',
  'Estudio de las normas internacionales aplicables (ISO/IEC 25010, IEEE 1471), buenas prácticas de desarrollo, principios SOLID, DRY, KISS, y su aplicación para garantizar calidad.',
  'Aplicar normas internacionales y buenas prácticas arquitectónicas en el diseño del sistema, asegurando calidad, seguridad, interoperabilidad y rendimiento.'),
 (4, 15, 'Implementación de la Arquitectura utilizando Frameworks',
  'Implementación completa del sistema HiPortafolio aplicando el framework seleccionado, integrando todos los componentes arquitectónicos con sus patrones y principios definidos.',
  'Implementar la arquitectura del sistema de manera completa y funcional, integrando todos los componentes, patrones y principios arquitectónicos estudiados durante el curso.'),
 (4, 16, 'Evaluación y Optimización de la Arquitectura de Software',
  'Evaluación final de la arquitectura implementada mediante métricas de calidad, pruebas de rendimiento, análisis de mantenibilidad, seguridad y escalabilidad, y propuestas de mejora.',
  'Evaluar la arquitectura implementada contra los atributos de calidad definidos, identificar cuellos de botella y oportunidades de optimización, y documentar las mejoras propuestas.')
)
INSERT INTO public.semanas (id_unidad, numero, titulo, descripcion, objetivo, estado)
SELECT u.id_unidad, b.numero, b.titulo, b.descripcion, b.objetivo, 1
FROM semanas_base b
JOIN public.unidades u ON u.numero = b.id_unidad_numero
WHERE NOT EXISTS (
  SELECT 1 FROM public.semanas s
  WHERE s.id_unidad = u.id_unidad AND s.numero = b.numero
);

COMMIT;

-- Verificación: deben aparecer 4 unidades y 16 semanas en total.
SELECT u.numero AS unidad, u.titulo AS titulo_unidad,
       COUNT(s.id_semana) AS total_semanas,
       MIN(s.numero) AS primera_semana, MAX(s.numero) AS ultima_semana
FROM public.unidades u
LEFT JOIN public.semanas s ON s.id_unidad = u.id_unidad
GROUP BY u.numero, u.titulo
ORDER BY u.numero;
