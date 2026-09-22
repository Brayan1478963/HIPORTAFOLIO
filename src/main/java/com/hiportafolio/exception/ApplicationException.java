package com.hiportafolio.exception;

/**
 * ApplicationException - Excepción para errores de lógica de negocio.
 *
 * Responsabilidad: Representar errores producidos en la capa Service
 * como validaciones fallidas, estados inválidos o reglas de negocio
 * violadas (ej: correo ya registrado, contraseña incorrecta).
 *
 * A diferencia de DatabaseException, no implica fallo de infraestructura
 * sino condiciones de error esperadas en el dominio del negocio.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class ApplicationException extends Exception {

    private static final long serialVersionUID = 1L;

    /** Código de error opcional para identificar el tipo de fallo */
    private final String errorCode;

    /**
     * Constructor con mensaje descriptivo.
     *
     * @param message descripción del error de negocio
     */
    public ApplicationException(String message) {
        super(message);
        this.errorCode = "APP_ERROR";
    }

    /**
     * Constructor con código de error y mensaje.
     *
     * @param errorCode código identificador del error (ej: "AUTH_INVALID_CREDENTIALS")
     * @param message   descripción del error
     */
    public ApplicationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message descripción del error
     * @param cause   excepción original
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "APP_ERROR";
    }

    /**
     * Constructor completo.
     *
     * @param errorCode código del error
     * @param message   descripción del error
     * @param cause     excepción original
     */
    public ApplicationException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Retorna el código de error asociado.
     *
     * @return código de error
     */
    public String getErrorCode() {
        return errorCode;
    }

    // Códigos de error predefinidos para uso consistente en toda la aplicación
    public static final String AUTH_INVALID_CREDENTIALS = "AUTH_INVALID_CREDENTIALS";
    public static final String AUTH_USER_INACTIVE        = "AUTH_USER_INACTIVE";
    public static final String AUTH_USER_NOT_FOUND       = "AUTH_USER_NOT_FOUND";
    public static final String USER_EMAIL_EXISTS         = "USER_EMAIL_EXISTS";
    public static final String USER_NOT_FOUND            = "USER_NOT_FOUND";
    public static final String VALIDATION_ERROR          = "VALIDATION_ERROR";
    public static final String FILE_INVALID_TYPE         = "FILE_INVALID_TYPE";
    public static final String FILE_TOO_LARGE            = "FILE_TOO_LARGE";
    public static final String ENTITY_NOT_FOUND          = "ENTITY_NOT_FOUND";
    public static final String UNAUTHORIZED              = "UNAUTHORIZED";
}
