# HiPortafolio

> Sistema Web de Portafolio Académico Personal  
> Curso: Arquitectura de Software — Universidad Peruana Los Andes (UPLA)  
> Carrera: Ingeniería de Sistemas y Computación

---

## Descripción

HiPortafolio es un sistema web profesional y funcional que permite gestionar y presentar el portafolio académico del curso de Arquitectura de Software. Implementa una arquitectura **MVC por capas** con Java, JSP, Servlets y MySQL, demostrando los conocimientos adquiridos en las 4 unidades del curso.

---

## Objetivo

Crear un sistema web dinámico que permita al estudiante administrar y presentar su portafolio académico con las 4 unidades, 16 semanas, contenidos, evidencias, proyectos y tecnologías del curso.

---

## Características

- ✅ Autenticación y autorización con roles (ADMIN / USUARIO)
- ✅ Panel administrativo con CRUD completo
- ✅ 4 Unidades y 16 Semanas académicas dinámicas (desde MySQL)
- ✅ Gestión de contenidos, evidencias y archivos
- ✅ Portafolio público responsive con Bootstrap 5
- ✅ Seguridad: BCrypt, PreparedStatement, Filters, Session
- ✅ Arquitectura MVC por capas (Model, View, Controller, Service, DAO)
- ✅ Despliegue con Docker y docker-compose

---

## Tecnologías

| Categoría     | Tecnología              |
|---------------|------------------------|
| Backend       | Java 17, Servlets, JSP |
| Base de datos | MySQL 8.0, JDBC        |
| Frontend      | Bootstrap 5, JS, CSS3  |
| Build         | Maven, Apache Tomcat 10|
| Seguridad     | BCrypt (jBCrypt)       |
| DevOps        | Docker, docker-compose |

---

## Arquitectura

```
JSP (View)
  ↓
Servlet (Controller)
  ↓
Service (Business Logic)
  ↓
DAO (Data Access)
  ↓
MySQL (Database)
```

Capas: `config` · `model` · `dao` · `service` · `controller` · `filter` · `util` · `exception`

---

## Estructura del Proyecto

```
HiPortafolio/
├── Dockerfile
├── docker-compose.yml
├── README.md
├── .gitignore
├── pom.xml
├── database/
│   ├── schema.sql
│   ├── data.sql
│   └── database.sql
└── src/main/
    ├── java/com/hiportafolio/
    │   ├── config/       ← DatabaseConfig.java
    │   ├── model/        ← Entidades Java (POO)
    │   ├── dao/          ← Acceso a MySQL (JDBC)
    │   ├── service/      ← Lógica de negocio
    │   ├── controller/   ← Servlets HTTP
    │   ├── filter/       ← Seguridad (Auth, Admin, Encoding)
    │   ├── util/         ← Utilidades (Password, File, Validation)
    │   └── exception/    ← Excepciones personalizadas
    └── webapp/
        ├── index.jsp
        ├── assets/css/   ← style.css, admin.css, login.css
        ├── assets/js/    ← main.js, admin.js, validation.js
        ├── uploads/      ← Archivos subidos
        └── WEB-INF/views/
            ├── auth/     ← login.jsp
            ├── public/   ← home, sobre-mi, proyectos, tecnologias
            ├── unidades/ ← listado, detalle, semana
            ├── evidencias/
            ├── admin/    ← dashboard + CRUD completo
            └── error/    ← 403, 404, 500
```

---

## Base de Datos

**Nombre:** `hiportafolio`

**Tablas principales:**

| Tabla                | Descripción                        |
|----------------------|------------------------------------|
| `roles`              | Roles del sistema (ADMIN, USUARIO) |
| `usuarios`           | Usuarios con hash BCrypt           |
| `unidades`           | 4 unidades académicas              |
| `semanas`            | 16 semanas del curso               |
| `contenidos`         | Contenido educativo por semana     |
| `evidencias`         | Evidencias de aprendizaje          |
| `archivos`           | Metadatos de archivos subidos      |
| `proyectos`          | Proyectos del portafolio           |
| `tecnologias`        | Tecnologías del portafolio         |
| `proyecto_tecnologia`| Relación N:M proyectos-tecnologías |
| `categorias`         | Categorías de tecnologías          |

---

## Instalación

### Requisitos previos

- Java 17+
- Maven 3.8+
- MySQL 8.0+
- Apache Tomcat 10.1+
- Docker (opcional)

### Sin Docker

1. Clonar el repositorio
2. Crear base de datos ejecutando `database/database.sql` en MySQL
3. Configurar variables de entorno:
   ```
   DB_HOST=localhost
   DB_PORT=3306
   DB_NAME=hiportafolio
   DB_USER=root
   DB_PASSWORD=tu_password
   ```
4. Compilar con Maven:
   ```bash
   mvn clean package
   ```
5. Desplegar el WAR en Tomcat:
   ```
   cp target/HiPortafolio.war $TOMCAT_HOME/webapps/
   ```
6. Acceder: `http://localhost:8080/HiPortafolio`

---

## Docker

### Ejecución completa (App + MySQL)

```bash
# 1. Compilar el WAR
mvn clean package -DskipTests

# 2. Levantar contenedores
docker-compose up -d

# 3. Verificar
docker-compose logs -f
```

### Acceso

- **Aplicación:** http://localhost:8080
- **MySQL:** localhost:3307

### Detener

```bash
docker-compose down
```

---

## Login

| Rol    | Correo                      | Contraseña   |
|--------|-----------------------------|--------------|
| ADMIN  | admin@hiportafolio.com      | Admin123!    |
| USUARIO| usuario@hiportafolio.com    | Usuario123!  |

---

## Roles

### ADMIN
- Acceso completo al panel administrativo
- CRUD de: usuarios, unidades, semanas, contenidos, evidencias, archivos, proyectos, tecnologías
- Ver estadísticas del dashboard

### USUARIO
- Ver portafolio público
- Navegar unidades, semanas y contenidos
- Ver evidencias, proyectos y tecnologías
- Descargar archivos permitidos

---

## Rutas principales

| Ruta                   | Descripción            | Acceso    |
|------------------------|------------------------|-----------|
| `/portafolio`          | Home del portafolio    | Público   |
| `/unidades`            | Lista de 4 unidades    | Público   |
| `/semanas?id=X`        | Detalle de semana      | Público   |
| `/proyectos`           | Lista de proyectos     | Público   |
| `/tecnologias`         | Lista de tecnologías   | Público   |
| `/login`               | Formulario login       | Público   |
| `/admin/dashboard`     | Panel administrativo   | ADMIN     |
| `/admin/usuarios`      | CRUD usuarios          | ADMIN     |
| `/admin/unidades`      | CRUD unidades          | ADMIN     |
| `/admin/semanas`       | CRUD semanas           | ADMIN     |
| `/admin/contenidos`    | CRUD contenidos        | ADMIN     |
| `/admin/evidencias`    | CRUD evidencias        | ADMIN     |
| `/admin/archivos`      | Gestión de archivos    | ADMIN     |
| `/admin/proyectos`     | CRUD proyectos         | ADMIN     |
| `/admin/tecnologias`   | CRUD tecnologías       | ADMIN     |

---

## Seguridad

- Contraseñas hasheadas con **BCrypt** (factor 12)
- **PreparedStatement** en todas las consultas SQL (prevención SQL Injection)
- **HttpSession** con timeout de 30 minutos
- **Filters** de autenticación y autorización
- Cabeceras de seguridad HTTP (X-Frame-Options, X-XSS-Protection)
- Prevención de Session Fixation en login
- Validación de tipo y tamaño de archivos subidos
- Variables de entorno para credenciales (nunca hardcoded)

---

## UML

Los diagramas UML se encuentran en `docs/uml/`:

1. Diagrama de Casos de Uso
2. Diagrama de Clases
3. Diagrama de Paquetes
4. Diagrama de Componentes
5. Diagrama de Despliegue
6. Secuencia: Login
7. Secuencia: Administración CRUD
8. Secuencia: Consulta de Semanas

---

## Relación con el Curso

| Unidad | Tema                          | Demostración en el sistema              |
|--------|-------------------------------|-----------------------------------------|
| I      | Fundamentos y Estándares      | Arquitectura documentada, patrones MVC  |
| II     | POO y Modelado UML            | Clases Java, herencia, encapsulamiento  |
| III    | Comunicación e Integración    | APIs REST implícitas, JDBC, HTTP        |
| IV     | Frameworks y Buenas Prácticas | Bootstrap, Maven, SOLID, DRY           |

---

## Autor

**Estudiante de Ingeniería de Sistemas y Computación**  
Universidad Peruana Los Andes — UPLA  
Curso: Arquitectura de Software — 2026

---

*HiPortafolio — Sistema desarrollado como proyecto integrador del curso de Arquitectura de Software*
