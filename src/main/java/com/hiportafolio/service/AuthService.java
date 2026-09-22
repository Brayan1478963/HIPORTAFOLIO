package com.hiportafolio.service;

import com.hiportafolio.dao.UsuarioDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Usuario;
import com.hiportafolio.util.PasswordUtil;
import com.hiportafolio.util.ValidationUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AuthService - Lógica de negocio para autenticación y autorización.
 *
 * Responsabilidad: Orquestar el proceso de login, verificación de credenciales,
 * control de estado del usuario y determinación de rol.
 *
 * Flujo de autenticación:
 *   1. Validar formato de campos
 *   2. Buscar usuario por correo en BD
 *   3. Verificar hash BCrypt de la contraseña
 *   4. Verificar que el usuario esté activo
 *   5. Retornar el objeto Usuario con su rol
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class AuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Autentica un usuario con su correo y contraseña.
     *
     * @param correo   correo electrónico ingresado
     * @param password contraseña en texto plano ingresada
     * @return Usuario autenticado con su rol
     * @throws ApplicationException si las credenciales son incorrectas, el usuario
     *                              no existe o está inactivo
     */
    public Usuario autenticar(String correo, String password) throws ApplicationException {
        // 1. Validar que los campos no estén vacíos
        if (ValidationUtil.estaVacio(correo) || ValidationUtil.estaVacio(password)) {
            throw new ApplicationException(
                ApplicationException.AUTH_INVALID_CREDENTIALS,
                "El correo y la contraseña son obligatorios."
            );
        }

        // 2. Buscar usuario por correo
        Usuario usuario;
        try {
            usuario = usuarioDAO.buscarPorCorreo(correo.trim().toLowerCase());
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error de base de datos al autenticar", e);
            throw new ApplicationException(
                "Error interno al procesar la autenticación. Intente nuevamente.", e
            );
        }

        // 3. Verificar que el usuario exista
        if (usuario == null) {
            throw new ApplicationException(
                ApplicationException.AUTH_INVALID_CREDENTIALS,
                "Correo o contraseña incorrectos."
            );
        }

        // 4. Verificar contraseña con BCrypt
        if (!PasswordUtil.verificarPassword(password, usuario.getPasswordHash())) {
            throw new ApplicationException(
                ApplicationException.AUTH_INVALID_CREDENTIALS,
                "Correo o contraseña incorrectos."
            );
        }

        // 5. Verificar que el usuario esté activo
        if (!usuario.isEstado()) {
            throw new ApplicationException(
                ApplicationException.AUTH_USER_INACTIVE,
                "Tu cuenta está desactivada. Contacta al administrador."
            );
        }

        LOGGER.info("Usuario autenticado correctamente: " + correo);
        return usuario;
    }

    /**
     * Verifica si un usuario tiene el rol de administrador.
     *
     * @param usuario usuario a verificar
     * @return true si tiene rol ADMIN
     */
    public boolean esAdministrador(Usuario usuario) {
        return usuario != null && usuario.esAdmin();
    }

    /**
     * Verifica si la sesión contiene un usuario autenticado y activo.
     *
     * @param usuario objeto usuario de la sesión (puede ser null)
     * @return true si la sesión es válida
     */
    public boolean sesionValida(Object usuario) {
        return usuario instanceof Usuario;
    }
}
