package com.hiportafolio.util;

/**
 * Constants - Constantes globales de la aplicación.
 *
 * Responsabilidad: Centralizar todos los literales reutilizados a través
 * del sistema para evitar duplicación (principio DRY) y facilitar
 * el mantenimiento.
 *
 * Organización por secciones:
 *   - Roles
 *   - Atributos de sesión
 *   - Acciones CRUD
 *   - Rutas/URLs
 *   - Vistas JSP
 *   - Archivos
 *   - Mensajes
 *   - Configuración
 *
 * @author HiPortafolio
 * @version 1.0
 */
public final class Constants {

    /** Clase utilitaria: no debe instanciarse */
    private Constants() {
        throw new UnsupportedOperationException("Clase de constantes, no instanciar.");
    }

    // =========================================================
    // ROLES DEL SISTEMA
    // =========================================================
    public static final String ROL_ADMIN   = "ADMIN";
    public static final String ROL_USUARIO = "USUARIO";

    // =========================================================
    // ATRIBUTOS DE SESIÓN (HttpSession)
    // =========================================================
    public static final String SESSION_USUARIO    = "usuario";
    public static final String SESSION_ROL        = "rol";
    public static final String SESSION_USER_ID    = "userId";
    public static final String SESSION_USER_NAME  = "userName";

    // =========================================================
    // PARÁMETROS DE REQUEST
    // =========================================================
    public static final String PARAM_ACCION       = "accion";
    public static final String PARAM_ID           = "id";
    public static final String PARAM_CORREO       = "correo";
    public static final String PARAM_PASSWORD     = "password";
    public static final String PARAM_NOMBRE       = "nombre";
    public static final String PARAM_PAGE         = "page";
    public static final String PARAM_SIZE         = "size";

    // =========================================================
    // ACCIONES CRUD (valor del parámetro "accion")
    // =========================================================
    public static final String ACCION_LISTAR      = "listar";
    public static final String ACCION_NUEVA       = "nueva";
    public static final String ACCION_GUARDAR     = "guardar";
    public static final String ACCION_EDITAR      = "editar";
    public static final String ACCION_ACTUALIZAR  = "actualizar";
    public static final String ACCION_ELIMINAR    = "eliminar";
    public static final String ACCION_DETALLE     = "detalle";
    public static final String ACCION_ACTIVAR     = "activar";
    public static final String ACCION_DESACTIVAR  = "desactivar";

    // =========================================================
    // ATRIBUTOS DE REQUEST (para pasar datos a JSP)
    // =========================================================
    public static final String ATTR_LISTA         = "lista";
    public static final String ATTR_ENTIDAD       = "entidad";
    public static final String ATTR_UNIDAD        = "unidad";
    public static final String ATTR_SEMANA        = "semana";
    public static final String ATTR_CONTENIDO     = "contenido";
    public static final String ATTR_EVIDENCIA     = "evidencia";
    public static final String ATTR_PROYECTO      = "proyecto";
    public static final String ATTR_TECNOLOGIA    = "tecnologia";
    public static final String ATTR_ARCHIVO       = "archivo";
    public static final String ATTR_USUARIO       = "usuarioObj";
    public static final String ATTR_UNIDADES      = "unidades";
    public static final String ATTR_SEMANAS       = "semanas";
    public static final String ATTR_CONTENIDOS    = "contenidos";
    public static final String ATTR_EVIDENCIAS    = "evidencias";
    public static final String ATTR_PROYECTOS     = "proyectos";
    public static final String ATTR_TECNOLOGIAS   = "tecnologias";
    public static final String ATTR_ARCHIVOS      = "archivos";
    public static final String ATTR_USUARIOS      = "usuarios";
    public static final String ATTR_ROLES         = "roles";
    public static final String ATTR_CATEGORIAS    = "categorias";
    public static final String ATTR_MENSAJE       = "mensaje";
    public static final String ATTR_ERROR         = "error";
    public static final String ATTR_TITULO_PAGINA = "tituloPagina";
    public static final String ATTR_SEMANA_ANT    = "semanaAnterior";
    public static final String ATTR_SEMANA_SIG    = "semanaSiguiente";
    public static final String ATTR_STATS         = "stats";

    // =========================================================
    // RUTAS URL (mapeos de Servlets)
    // =========================================================
    public static final String URL_LOGIN          = "/login";
    public static final String URL_LOGOUT         = "/logout";
    public static final String URL_PORTAFOLIO     = "/portafolio";
    public static final String URL_UNIDADES       = "/unidades";
    public static final String URL_SEMANAS        = "/semanas";
    public static final String URL_PROYECTOS      = "/proyectos";
    public static final String URL_TECNOLOGIAS    = "/tecnologias";
    public static final String URL_EVIDENCIAS     = "/evidencias";
    public static final String URL_ADMIN          = "/admin/dashboard";
    public static final String URL_ADMIN_USUARIOS = "/admin/usuarios";
    public static final String URL_ADMIN_UNIDADES = "/admin/unidades";
    public static final String URL_ADMIN_SEMANAS  = "/admin/semanas";
    public static final String URL_ARCHIVOS_VER   = "/archivos/ver";
    public static final String URL_ARCHIVOS_DOWN  = "/archivos/descargar";

    // =========================================================
    // RUTAS DE VISTAS JSP (WEB-INF)
    // =========================================================
    public static final String VIEW_LOGIN           = "/WEB-INF/views/auth/login.jsp";
    public static final String VIEW_HOME            = "/WEB-INF/views/public/home.jsp";
    public static final String VIEW_SOBRE_MI        = "/WEB-INF/views/public/sobre-mi.jsp";
    public static final String VIEW_PROYECTOS_PUB   = "/WEB-INF/views/public/proyectos.jsp";
    public static final String VIEW_TECNOLOGIAS_PUB = "/WEB-INF/views/public/tecnologias.jsp";
    public static final String VIEW_CONTACTO        = "/WEB-INF/views/public/contacto.jsp";
    public static final String VIEW_UNIDADES        = "/WEB-INF/views/unidades/unidades.jsp";
    public static final String VIEW_UNIDAD_DET      = "/WEB-INF/views/unidades/unidad-detalle.jsp";
    public static final String VIEW_SEMANA_DET      = "/WEB-INF/views/unidades/semana-detalle.jsp";
    public static final String VIEW_EVIDENCIAS_PUB  = "/WEB-INF/views/evidencias/evidencias.jsp";
    public static final String VIEW_EVIDENCIA_DET   = "/WEB-INF/views/evidencias/evidencia-detalle.jsp";
    public static final String VIEW_DASHBOARD       = "/WEB-INF/views/admin/dashboard.jsp";
    public static final String VIEW_ADM_USR_LIST    = "/WEB-INF/views/admin/usuarios/listar.jsp";
    public static final String VIEW_ADM_USR_CREAR   = "/WEB-INF/views/admin/usuarios/crear.jsp";
    public static final String VIEW_ADM_USR_EDIT    = "/WEB-INF/views/admin/usuarios/editar.jsp";
    public static final String VIEW_ADM_UNI_LIST    = "/WEB-INF/views/admin/unidades/listar.jsp";
    public static final String VIEW_ADM_UNI_CREAR   = "/WEB-INF/views/admin/unidades/crear.jsp";
    public static final String VIEW_ADM_UNI_EDIT    = "/WEB-INF/views/admin/unidades/editar.jsp";
    public static final String VIEW_ADM_SEM_LIST    = "/WEB-INF/views/admin/semanas/listar.jsp";
    public static final String VIEW_ADM_SEM_CREAR   = "/WEB-INF/views/admin/semanas/crear.jsp";
    public static final String VIEW_ADM_SEM_EDIT    = "/WEB-INF/views/admin/semanas/editar.jsp";
    public static final String VIEW_ADM_CONT_LIST   = "/WEB-INF/views/admin/contenidos/listar.jsp";
    public static final String VIEW_ADM_CONT_CREAR  = "/WEB-INF/views/admin/contenidos/crear.jsp";
    public static final String VIEW_ADM_CONT_EDIT   = "/WEB-INF/views/admin/contenidos/editar.jsp";
    public static final String VIEW_ADM_EVI_LIST    = "/WEB-INF/views/admin/evidencias/listar.jsp";
    public static final String VIEW_ADM_EVI_CREAR   = "/WEB-INF/views/admin/evidencias/crear.jsp";
    public static final String VIEW_ADM_EVI_EDIT    = "/WEB-INF/views/admin/evidencias/editar.jsp";
    public static final String VIEW_ADM_ARCH_LIST   = "/WEB-INF/views/admin/archivos/listar.jsp";
    public static final String VIEW_ADM_PROY_LIST   = "/WEB-INF/views/admin/proyectos/listar.jsp";
    public static final String VIEW_ADM_PROY_CREAR  = "/WEB-INF/views/admin/proyectos/crear.jsp";
    public static final String VIEW_ADM_PROY_EDIT   = "/WEB-INF/views/admin/proyectos/editar.jsp";
    public static final String VIEW_ADM_TEC_LIST    = "/WEB-INF/views/admin/tecnologias/listar.jsp";
    public static final String VIEW_ADM_TEC_CREAR   = "/WEB-INF/views/admin/tecnologias/crear.jsp";
    public static final String VIEW_ADM_TEC_EDIT    = "/WEB-INF/views/admin/tecnologias/editar.jsp";
    public static final String VIEW_ERROR_403       = "/WEB-INF/views/error/403.jsp";
    public static final String VIEW_ERROR_404       = "/WEB-INF/views/error/404.jsp";
    public static final String VIEW_ERROR_500       = "/WEB-INF/views/error/500.jsp";

    // =========================================================
    // CONFIGURACIÓN DE ARCHIVOS
    // =========================================================
    public static final long   FILE_MAX_SIZE_BYTES    = 10 * 1024 * 1024; // 10 MB
    public static final String UPLOAD_DIR_EVIDENCIAS  = "uploads/evidencias/";
    public static final String UPLOAD_DIR_PROYECTOS   = "uploads/proyectos/";
    public static final String UPLOAD_DIR_DOCUMENTOS  = "uploads/documentos/";

    /** Extensiones permitidas para subida de archivos */
    public static final String[] ALLOWED_EXTENSIONS = {
        "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx",
        "jpg", "jpeg", "png", "gif", "svg", "webp",
        "zip", "txt", "md"
    };

    /** MIME types permitidos */
    public static final String[] ALLOWED_MIME_TYPES = {
        "application/pdf",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.ms-excel",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.ms-powerpoint",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "image/jpeg", "image/png", "image/gif", "image/svg+xml", "image/webp",
        "application/zip",
        "text/plain", "text/markdown"
    };

    // =========================================================
    // MENSAJES DE ÉXITO
    // =========================================================
    public static final String MSG_GUARDADO      = "Registro guardado correctamente.";
    public static final String MSG_ACTUALIZADO   = "Registro actualizado correctamente.";
    public static final String MSG_ELIMINADO     = "Registro eliminado correctamente.";
    public static final String MSG_ACTIVADO      = "Usuario activado correctamente.";
    public static final String MSG_DESACTIVADO   = "Usuario desactivado correctamente.";
    public static final String MSG_ROL_CAMBIADO  = "Rol actualizado correctamente.";
    public static final String MSG_ARCHIVO_SUBIDO = "Archivo subido correctamente.";

    // =========================================================
    // MENSAJES DE ERROR
    // =========================================================
    public static final String ERR_LOGIN_INVALID   = "Correo o contraseña incorrectos.";
    public static final String ERR_LOGIN_INACTIVE  = "Tu cuenta está desactivada. Contacta al administrador.";
    public static final String ERR_SESSION_EXPIRED = "Tu sesión ha expirado. Por favor inicia sesión nuevamente.";
    public static final String ERR_NO_AUTORIZADO   = "No tienes permisos para acceder a esta sección.";
    public static final String ERR_CAMPOS_VACIOS   = "Todos los campos obligatorios deben completarse.";
    public static final String ERR_CORREO_EXISTE   = "Ya existe un usuario registrado con ese correo.";
    public static final String ERR_FILE_TIPO       = "El tipo de archivo no está permitido.";
    public static final String ERR_FILE_TAMANO     = "El archivo supera el tamaño máximo permitido (10 MB).";
    public static final String ERR_DB              = "Error interno del sistema. Por favor intente más tarde.";
    public static final String ERR_NOT_FOUND       = "El registro solicitado no fue encontrado.";

    // =========================================================
    // CONFIGURACIÓN DE PAGINACIÓN
    // =========================================================
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE      = 1;

    // =========================================================
    // CONFIGURACIÓN DE SESIÓN
    // =========================================================
    public static final int SESSION_TIMEOUT_SECONDS = 1800; // 30 minutos

    // =========================================================
    // INFORMACIÓN DE LA APLICACIÓN
    // =========================================================
    public static final String APP_NAME        = "HiPortafolio";
    public static final String APP_VERSION     = "1.0.0";
    public static final String APP_DESCRIPTION = "Sistema Web de Portafolio Académico Personal";
    public static final String APP_COURSE      = "Arquitectura de Software";
    public static final String APP_UNIVERSITY  = "Universidad Peruana Los Andes (UPLA)";
    public static final String APP_CAREER      = "Ingeniería de Sistemas y Computación";
}
