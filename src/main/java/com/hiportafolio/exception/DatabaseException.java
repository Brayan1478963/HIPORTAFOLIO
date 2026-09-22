package com.hiportafolio.exception;

/**
 * DatabaseException - Excepción para errores de acceso a base de datos.
 *
 * Responsabilidad: Encapsular errores de la capa DAO para que las capas
 * superiores (Service, Controller) no dependan de SQLException directamente.
 * Esto desacopla la implementación de la base de datos del resto del sistema.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class DatabaseException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor con mensaje descriptivo.
     *
     * @param message descripción del error
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa original.
     * Preserva la traza completa del error para debugging.
     *
     * @param message descripción del error
     * @param cause   excepción original (ej: SQLException)
     */
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor solo con causa original.
     *
     * @param cause excepción original
     */
    public DatabaseException(Throwable cause) {
        super(cause);
    }
}
