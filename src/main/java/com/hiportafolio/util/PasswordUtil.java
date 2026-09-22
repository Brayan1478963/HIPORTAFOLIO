package com.hiportafolio.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * PasswordUtil - Utilidad para manejo seguro de contraseñas.
 *
 * Responsabilidad: Encapsular el algoritmo de hashing BCrypt para que
 * ninguna otra capa del sistema necesite conocer los detalles de
 * implementación del hashing.
 *
 * Características de BCrypt:
 *   - Adaptativo: el factor de costo puede aumentarse con el tiempo
 *   - Incluye salt automáticamente (no requiere generación manual)
 *   - Resistente a ataques de fuerza bruta y rainbow tables
 *   - Factor de costo 12: ~300ms por hash (adecuado para login)
 *
 * REGLA DE SEGURIDAD: NUNCA guardar contraseñas en texto plano.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public final class PasswordUtil {

    /** Factor de costo BCrypt (2^12 = 4096 iteraciones) */
    private static final int BCRYPT_ROUNDS = 12;

    /** Constructor privado: clase utilitaria, no instanciar */
    private PasswordUtil() {
        throw new UnsupportedOperationException("Clase utilitaria, no instanciar.");
    }

    /**
     * Genera el hash BCrypt de una contraseña en texto plano.
     * Cada llamada produce un hash diferente (salt aleatorio incluido).
     *
     * @param passwordPlain contraseña en texto plano (no puede ser null ni vacía)
     * @return hash BCrypt listo para almacenar en base de datos
     * @throws IllegalArgumentException si la contraseña es null o vacía
     */
    public static String hashPassword(String passwordPlain) {
        if (passwordPlain == null || passwordPlain.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede ser nula o vacía.");
        }
        return BCrypt.hashpw(passwordPlain, BCrypt.gensalt(BCRYPT_ROUNDS));
    }

    /**
     * Verifica si una contraseña en texto plano coincide con un hash BCrypt almacenado.
     *
     * @param passwordPlain contraseña ingresada por el usuario (texto plano)
     * @param passwordHash  hash almacenado en la base de datos
     * @return true si la contraseña coincide con el hash, false en caso contrario
     */
    public static boolean verificarPassword(String passwordPlain, String passwordHash) {
        if (passwordPlain == null || passwordPlain.isEmpty()) {
            return false;
        }
        if (passwordHash == null || passwordHash.isEmpty()) {
            return false;
        }
        try {
            return BCrypt.checkpw(passwordPlain, passwordHash);
        } catch (Exception e) {
            // Hash malformado u otro error: retornar false de manera segura
            return false;
        }
    }

    /**
     * Valida que una contraseña cumpla los requisitos mínimos de seguridad.
     * Requisitos: mínimo 8 caracteres, al menos 1 letra y 1 número.
     *
     * @param password contraseña a validar
     * @return true si cumple los requisitos, false si no
     */
    public static boolean esPasswordValido(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean tieneLetra  = password.chars().anyMatch(Character::isLetter);
        boolean tieneNumero = password.chars().anyMatch(Character::isDigit);
        return tieneLetra && tieneNumero;
    }

    /**
     * Retorna el mensaje de error cuando una contraseña no es válida.
     *
     * @return descripción de los requisitos mínimos
     */
    public static String getMensajeRequisitos() {
        return "La contraseña debe tener al menos 8 caracteres, incluyendo al menos 1 letra y 1 número.";
    }
}
