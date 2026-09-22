package com.hiportafolio.service;

import com.hiportafolio.dao.RolDAO;
import com.hiportafolio.dao.UsuarioDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Rol;
import com.hiportafolio.model.Usuario;
import com.hiportafolio.util.PasswordUtil;
import com.hiportafolio.util.ValidationUtil;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UsuarioService - Lógica de negocio para gestión de usuarios.
 *
 * Responsabilidad: Validar reglas de negocio antes de delegar al DAO.
 * Ejemplo: verificar que el correo no esté duplicado antes de registrar.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class UsuarioService {

    private static final Logger LOGGER = Logger.getLogger(UsuarioService.class.getName());
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final RolDAO rolDAO         = new RolDAO();

    /**
     * Retorna la lista completa de usuarios con sus roles.
     */
    public List<Usuario> listar() throws ApplicationException {
        try {
            return usuarioDAO.listar();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar usuarios", e);
            throw new ApplicationException("Error al obtener la lista de usuarios.", e);
        }
    }

    /**
     * Busca un usuario por su ID.
     *
     * @throws ApplicationException si no existe
     */
    public Usuario buscarPorId(int idUsuario) throws ApplicationException {
        try {
            Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
            if (usuario == null) {
                throw new ApplicationException(
                    ApplicationException.USER_NOT_FOUND,
                    "No se encontró el usuario con ID: " + idUsuario
                );
            }
            return usuario;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar usuario por id", e);
            throw new ApplicationException("Error al buscar el usuario.", e);
        }
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Valida: campos obligatorios, formato correo, password seguro, correo único.
     *
     * @param nombre   nombre del usuario
     * @param apellido apellido
     * @param correo   correo electrónico
     * @param password contraseña en texto plano (se hashea antes de guardar)
     * @param idRol    ID del rol a asignar
     * @throws ApplicationException si alguna validación falla
     */
    public Usuario registrarUsuario(String nombre, String apellido, String correo,
                                    String password, int idRol) throws ApplicationException {
        // Validar formulario
        String error = ValidationUtil.validarFormularioUsuario(nombre, apellido, correo, password, false);
        if (error != null) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR, error);
        }

        try {
            // Verificar que el correo no esté en uso
            if (usuarioDAO.existeCorreo(correo.trim().toLowerCase(), 0)) {
                throw new ApplicationException(
                    ApplicationException.USER_EMAIL_EXISTS,
                    "Ya existe un usuario registrado con ese correo electrónico."
                );
            }

            // Crear entidad con hash de contraseña
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre.trim());
            nuevo.setApellido(apellido.trim());
            nuevo.setCorreo(correo.trim().toLowerCase());
            nuevo.setPasswordHash(PasswordUtil.hashPassword(password));
            nuevo.setIdRol(idRol > 0 ? idRol : 2); // default: USUARIO
            nuevo.setEstado(true);

            int idGenerado = usuarioDAO.guardar(nuevo);
            nuevo.setIdUsuario(idGenerado);
            LOGGER.info("Usuario registrado: " + correo);
            return nuevo;

        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error de base de datos al registrar usuario", e);
            throw new ApplicationException("Error al registrar el usuario.", e);
        }
    }

    /**
     * Actualiza los datos de un usuario existente.
     * Si se proporciona nueva contraseña, se re-hashea.
     */
    public void actualizarUsuario(int idUsuario, String nombre, String apellido,
                                   String correo, String nuevaPassword, int idRol)
            throws ApplicationException {

        String error = ValidationUtil.validarFormularioUsuario(nombre, apellido, correo, nuevaPassword, true);
        if (error != null) {
            throw new ApplicationException(ApplicationException.VALIDATION_ERROR, error);
        }

        try {
            // Verificar que el correo no esté en uso por OTRO usuario
            if (usuarioDAO.existeCorreo(correo.trim().toLowerCase(), idUsuario)) {
                throw new ApplicationException(
                    ApplicationException.USER_EMAIL_EXISTS,
                    "Ya existe otro usuario registrado con ese correo electrónico."
                );
            }

            Usuario usuario = buscarPorId(idUsuario);
            usuario.setNombre(nombre.trim());
            usuario.setApellido(apellido.trim());
            usuario.setCorreo(correo.trim().toLowerCase());
            usuario.setIdRol(idRol > 0 ? idRol : usuario.getIdRol());

            usuarioDAO.actualizar(usuario);

            // Actualizar contraseña solo si se proporcionó una nueva
            if (!ValidationUtil.estaVacio(nuevaPassword)) {
                if (!PasswordUtil.esPasswordValido(nuevaPassword)) {
                    throw new ApplicationException(
                        ApplicationException.VALIDATION_ERROR,
                        PasswordUtil.getMensajeRequisitos()
                    );
                }
                usuarioDAO.actualizarPassword(idUsuario, PasswordUtil.hashPassword(nuevaPassword));
            }

            LOGGER.info("Usuario actualizado: id=" + idUsuario);

        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar usuario", e);
            throw new ApplicationException("Error al actualizar el usuario.", e);
        }
    }

    /**
     * Elimina un usuario por su ID.
     */
    public void eliminarUsuario(int idUsuario) throws ApplicationException {
        try {
            buscarPorId(idUsuario); // verifica que exista
            usuarioDAO.eliminar(idUsuario);
            LOGGER.info("Usuario eliminado: id=" + idUsuario);
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar usuario", e);
            throw new ApplicationException("Error al eliminar el usuario.", e);
        }
    }

    /**
     * Activa o desactiva un usuario.
     */
    public void cambiarEstado(int idUsuario, boolean nuevoEstado) throws ApplicationException {
        try {
            usuarioDAO.cambiarEstado(idUsuario, nuevoEstado);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al cambiar estado de usuario", e);
            throw new ApplicationException("Error al cambiar el estado del usuario.", e);
        }
    }

    /**
     * Retorna todos los roles disponibles (para los formularios).
     */
    public List<Rol> listarRoles() throws ApplicationException {
        try {
            return rolDAO.listar();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar roles", e);
            throw new ApplicationException("Error al obtener los roles.", e);
        }
    }

    /**
     * Retorna el total de usuarios (para el dashboard).
     */
    public int contar() throws ApplicationException {
        try {
            return usuarioDAO.contar();
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al contar usuarios.", e);
        }
    }
}
