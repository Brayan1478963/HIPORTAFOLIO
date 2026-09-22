package com.hiportafolio.util;

import java.util.regex.Pattern;

/**
 * ValidationUtil - Utilidades de validación de datos de entrada.
 *
 * Responsabilidad: Centralizar toda la lógica de validación del sistema
 * para evitar duplicación y garantizar consistencia.
 *
 * Aplica el principio DRY: cada regla de validación se define una sola vez.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public final class ValidationUtil {

    /** Patrón para validar formato de correo electrónico */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"
    );

    /** Patrón para detectar caracteres peligrosos (XSS básico) */
    private static final Pattern XSS_PATTERN = Pattern.compile(
        "<script|</script|javascript:|on\\w+\\s*=",
        Pattern.CASE_INSENSITIVE
    );

    /** Longitudes máximas */
    public static final int MAX_NOMBRE    = 100;
    public static final int MAX_CORREO    = 150;
    public static final int MAX_TITULO    = 300;
    public static final int MAX_DESC      = 5000;
    public static final int MAX_CONTENIDO = 100000;

    /** Constructor privado: clase utilitaria */
    private ValidationUtil() {
        throw new UnsupportedOperationException("Clase utilitaria, no instanciar.");
    }

    // =========================================================
    // VALIDACIONES GENERALES
    // =========================================================

    /**
     * Verifica si un String es null, vacío o solo espacios.
     *
     * @param valor texto a evaluar
     * @return true si está vacío o null
     */
    public static boolean estaVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    /**
     * Limpia espacios al inicio y fin. Retorna null si la entrada es null.
     *
     * @param valor texto a limpiar
     * @return texto sin espacios extremos, o null si era null
     */
    public static String limpiar(String valor) {
        return valor != null ? valor.trim() : null;
    }

    /**
     * Verifica que un String no supere una longitud máxima.
     *
     * @param valor     texto a evaluar
     * @param maxLength longitud máxima permitida
     * @return true si la longitud es válida (incluyendo null/vacío)
     */
    public static boolean longitudValida(String valor, int maxLength) {
        if (valor == null) return true;
        return valor.trim().length() <= maxLength;
    }

    /**
     * Verifica si un entero es positivo (mayor que cero).
     *
     * @param valor número a evaluar
     * @return true si es mayor que cero
     */
    public static boolean esPositivo(int valor) {
        return valor > 0;
    }

    // =========================================================
    // VALIDACIONES ESPECÍFICAS
    // =========================================================

    /**
     * Valida el formato de un correo electrónico.
     *
     * @param correo correo a validar
     * @return true si tiene formato válido
     */
    public static boolean esCorreoValido(String correo) {
        if (estaVacio(correo)) return false;
        return EMAIL_PATTERN.matcher(correo.trim()).matches();
    }

    /**
     * Detecta posibles intentos de XSS en un texto de entrada.
     *
     * @param texto texto a verificar
     * @return true si se detectan patrones sospechosos
     */
    public static boolean tieneXSS(String texto) {
        if (texto == null) return false;
        return XSS_PATTERN.matcher(texto).find();
    }

    /**
     * Sanitiza texto eliminando caracteres HTML peligrosos.
     * Convierte < > & " ' a sus entidades HTML correspondientes.
     *
     * @param texto texto de entrada
     * @return texto sanitizado
     */
    public static String sanitizarHtml(String texto) {
        if (texto == null) return null;
        return texto
            .replace("&",  "&amp;")
            .replace("<",  "&lt;")
            .replace(">",  "&gt;")
            .replace("\"", "&quot;")
            .replace("'",  "&#x27;");
    }

    /**
     * Convierte un String a entero de manera segura.
     * Retorna -1 si el String no es un número válido.
     *
     * @param valor String con el número
     * @return int parseado, o -1 si no es válido
     */
    public static int parseIntSeguro(String valor) {
        if (estaVacio(valor)) return -1;
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // =========================================================
    // VALIDACIONES DE FORMULARIOS
    // =========================================================

    /**
     * Valida los campos del formulario de login.
     *
     * @param correo   correo ingresado
     * @param password contraseña ingresada
     * @return null si todo es válido, mensaje de error si hay problemas
     */
    public static String validarFormularioLogin(String correo, String password) {
        if (estaVacio(correo) || estaVacio(password)) {
            return Constants.ERR_CAMPOS_VACIOS;
        }
        if (!esCorreoValido(correo)) {
            return "El formato del correo electrónico no es válido.";
        }
        if (password.trim().length() < 3) {
            return "La contraseña es demasiado corta.";
        }
        return null; // sin errores
    }

    /**
     * Valida los campos para crear/editar un usuario.
     *
     * @param nombre   nombre del usuario
     * @param apellido apellido del usuario
     * @param correo   correo del usuario
     * @param password contraseña (puede ser null en edición)
     * @param esEdicion true si es actualización (password opcional)
     * @return null si válido, mensaje de error si hay problemas
     */
    public static String validarFormularioUsuario(String nombre, String apellido,
                                                   String correo, String password,
                                                   boolean esEdicion) {
        if (estaVacio(nombre) || estaVacio(apellido) || estaVacio(correo)) {
            return Constants.ERR_CAMPOS_VACIOS;
        }
        if (!longitudValida(nombre, MAX_NOMBRE)) {
            return "El nombre no puede superar " + MAX_NOMBRE + " caracteres.";
        }
        if (!esCorreoValido(correo)) {
            return "El formato del correo electrónico no es válido.";
        }
        if (!esEdicion && estaVacio(password)) {
            return "La contraseña es obligatoria al crear un usuario.";
        }
        if (!esEdicion && !PasswordUtil.esPasswordValido(password)) {
            return PasswordUtil.getMensajeRequisitos();
        }
        if (tieneXSS(nombre) || tieneXSS(apellido)) {
            return "Los datos ingresados contienen caracteres no permitidos.";
        }
        return null;
    }

    /**
     * Valida los campos para crear/editar una unidad.
     *
     * @param numero  número de la unidad
     * @param titulo  título de la unidad
     * @return null si válido, mensaje de error si hay problemas
     */
    public static String validarFormularioUnidad(String numero, String titulo) {
        if (estaVacio(titulo)) {
            return "El título de la unidad es obligatorio.";
        }
        int num = parseIntSeguro(numero);
        if (num < 1 || num > 10) {
            return "El número de unidad debe ser un valor entre 1 y 10.";
        }
        if (!longitudValida(titulo, MAX_TITULO)) {
            return "El título no puede superar " + MAX_TITULO + " caracteres.";
        }
        return null;
    }

    /**
     * Valida los campos para crear/editar una semana.
     *
     * @param numero    número de la semana
     * @param titulo    título de la semana
     * @param idUnidad  id de la unidad a la que pertenece
     * @return null si válido, mensaje de error si hay problemas
     */
    public static String validarFormularioSemana(String numero, String titulo, String idUnidad) {
        if (estaVacio(titulo) || estaVacio(idUnidad)) {
            return Constants.ERR_CAMPOS_VACIOS;
        }
        int num = parseIntSeguro(numero);
        if (num < 1 || num > 16) {
            return "El número de semana debe estar entre 1 y 16.";
        }
        int idUni = parseIntSeguro(idUnidad);
        if (idUni <= 0) {
            return "Debe seleccionar una unidad válida.";
        }
        if (!longitudValida(titulo, MAX_TITULO)) {
            return "El título no puede superar " + MAX_TITULO + " caracteres.";
        }
        return null;
    }

    /**
     * Valida los campos para crear/editar un contenido.
     *
     * @param titulo    título del contenido
     * @param idSemana  id de la semana a la que pertenece
     * @return null si válido, mensaje de error si hay problemas
     */
    public static String validarFormularioContenido(String titulo, String idSemana) {
        if (estaVacio(titulo) || estaVacio(idSemana)) {
            return Constants.ERR_CAMPOS_VACIOS;
        }
        int idSem = parseIntSeguro(idSemana);
        if (idSem <= 0) {
            return "Debe seleccionar una semana válida.";
        }
        if (!longitudValida(titulo, MAX_TITULO)) {
            return "El título no puede superar " + MAX_TITULO + " caracteres.";
        }
        return null;
    }

    /**
     * Valida la extensión de un archivo subido.
     *
     * @param nombreArchivo nombre original del archivo
     * @return true si la extensión está permitida
     */
    public static boolean esExtensionPermitida(String nombreArchivo) {
        if (estaVacio(nombreArchivo)) return false;
        String ext = obtenerExtension(nombreArchivo).toLowerCase();
        for (String permitida : Constants.ALLOWED_EXTENSIONS) {
            if (permitida.equals(ext)) return true;
        }
        return false;
    }

    /**
     * Valida el tipo MIME de un archivo.
     *
     * @param mimeType tipo MIME a validar
     * @return true si el MIME está permitido
     */
    public static boolean esMimePermitido(String mimeType) {
        if (estaVacio(mimeType)) return false;
        for (String permitido : Constants.ALLOWED_MIME_TYPES) {
            if (permitido.equalsIgnoreCase(mimeType.trim())) return true;
        }
        return false;
    }

    /**
     * Extrae la extensión de un nombre de archivo.
     *
     * @param nombreArchivo nombre del archivo
     * @return extensión sin punto, en minúsculas; o cadena vacía si no tiene
     */
    public static String obtenerExtension(String nombreArchivo) {
        if (estaVacio(nombreArchivo)) return "";
        int ultimoPunto = nombreArchivo.lastIndexOf('.');
        if (ultimoPunto < 0 || ultimoPunto == nombreArchivo.length() - 1) return "";
        return nombreArchivo.substring(ultimoPunto + 1).toLowerCase();
    }
}
