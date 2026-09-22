package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UsuarioDAO - Acceso a datos para la entidad Usuario.
 *
 * Responsabilidad: ÚNICA y EXCLUSIVAMENTE operaciones SQL sobre la tabla usuarios.
 * Toda la lógica de negocio pertenece a UsuarioService, no aquí.
 *
 * Usa PreparedStatement en TODAS las operaciones para prevenir SQL Injection.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class UsuarioDAO {

    private static final Logger LOGGER = Logger.getLogger(UsuarioDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    // SQL constantes — nunca concatenar parámetros de usuario en SQL
    private static final String SQL_LISTAR =
        "SELECT u.id_usuario, u.nombre, u.apellido, u.correo, u.password_hash, " +
        "       u.id_rol, r.nombre AS nombre_rol, u.estado, u.fecha_creacion " +
        "FROM usuarios u " +
        "INNER JOIN roles r ON u.id_rol = r.id_rol " +
        "ORDER BY u.fecha_creacion DESC";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT u.id_usuario, u.nombre, u.apellido, u.correo, u.password_hash, " +
        "       u.id_rol, r.nombre AS nombre_rol, u.estado, u.fecha_creacion " +
        "FROM usuarios u " +
        "INNER JOIN roles r ON u.id_rol = r.id_rol " +
        "WHERE u.id_usuario = ?";

    private static final String SQL_BUSCAR_POR_CORREO =
        "SELECT u.id_usuario, u.nombre, u.apellido, u.correo, u.password_hash, " +
        "       u.id_rol, r.nombre AS nombre_rol, u.estado, u.fecha_creacion " +
        "FROM usuarios u " +
        "INNER JOIN roles r ON u.id_rol = r.id_rol " +
        "WHERE u.correo = ?";

    private static final String SQL_INSERTAR =
        "INSERT INTO usuarios (nombre, apellido, correo, password_hash, id_rol, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE usuarios SET nombre = ?, apellido = ?, correo = ?, id_rol = ?, estado = ? " +
        "WHERE id_usuario = ?";

    private static final String SQL_ACTUALIZAR_PASSWORD =
        "UPDATE usuarios SET password_hash = ? WHERE id_usuario = ?";

    private static final String SQL_CAMBIAR_ESTADO =
        "UPDATE usuarios SET estado = ? WHERE id_usuario = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM usuarios WHERE id_usuario = ?";

    private static final String SQL_EXISTE_CORREO =
        "SELECT COUNT(*) FROM usuarios WHERE correo = ? AND id_usuario != ?";

    private static final String SQL_CONTAR =
        "SELECT COUNT(*) FROM usuarios";

    /**
     * Retorna todos los usuarios del sistema con su rol.
     */
    public List<Usuario> listar() throws DatabaseException {
        List<Usuario> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar usuarios", e);
            throw new DatabaseException("Error al obtener la lista de usuarios.", e);
        }
        return lista;
    }

    /**
     * Busca un usuario por su ID.
     */
    public Usuario buscarPorId(int idUsuario) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar usuario por id: " + idUsuario, e);
            throw new DatabaseException("Error al buscar el usuario.", e);
        }
        return null;
    }

    /**
     * Busca un usuario por su correo electrónico (usado en autenticación).
     */
    public Usuario buscarPorCorreo(String correo) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_CORREO)) {

            ps.setString(1, correo.trim().toLowerCase());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar usuario por correo", e);
            throw new DatabaseException("Error al buscar el usuario por correo.", e);
        }
        return null;
    }

    /**
     * Inserta un nuevo usuario en la base de datos.
     * Retorna el ID generado automáticamente.
     */
    public int guardar(Usuario usuario) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombre().trim());
            ps.setString(2, usuario.getApellido().trim());
            ps.setString(3, usuario.getCorreo().trim().toLowerCase());
            ps.setString(4, usuario.getPasswordHash());
            ps.setInt(5,    usuario.getIdRol());
            ps.setBoolean(6, usuario.isEstado());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar usuario", e);
            throw new DatabaseException("Error al guardar el usuario.", e);
        }
        return -1;
    }

    /**
     * Actualiza los datos de un usuario existente (sin cambiar contraseña).
     */
    public boolean actualizar(Usuario usuario) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ACTUALIZAR)) {

            ps.setString(1, usuario.getNombre().trim());
            ps.setString(2, usuario.getApellido().trim());
            ps.setString(3, usuario.getCorreo().trim().toLowerCase());
            ps.setInt(4,    usuario.getIdRol());
            ps.setBoolean(5, usuario.isEstado());
            ps.setInt(6,    usuario.getIdUsuario());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar usuario", e);
            throw new DatabaseException("Error al actualizar el usuario.", e);
        }
    }

    /**
     * Actualiza la contraseña de un usuario (hash BCrypt).
     */
    public boolean actualizarPassword(int idUsuario, String nuevoHash) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ACTUALIZAR_PASSWORD)) {

            ps.setString(1, nuevoHash);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar contraseña", e);
            throw new DatabaseException("Error al actualizar la contraseña.", e);
        }
    }

    /**
     * Cambia el estado activo/inactivo de un usuario.
     */
    public boolean cambiarEstado(int idUsuario, boolean nuevoEstado) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CAMBIAR_ESTADO)) {

            ps.setBoolean(1, nuevoEstado);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cambiar estado del usuario", e);
            throw new DatabaseException("Error al cambiar el estado del usuario.", e);
        }
    }

    /**
     * Elimina un usuario por su ID.
     */
    public boolean eliminar(int idUsuario) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {

            ps.setInt(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar usuario", e);
            throw new DatabaseException("Error al eliminar el usuario.", e);
        }
    }

    /**
     * Verifica si ya existe un usuario con el correo dado (excluyendo un ID opcional).
     * Usado para validar duplicados al crear/editar.
     */
    public boolean existeCorreo(String correo, int idExcluir) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_EXISTE_CORREO)) {

            ps.setString(1, correo.trim().toLowerCase());
            ps.setInt(2, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar correo duplicado", e);
            throw new DatabaseException("Error al verificar el correo.", e);
        }
        return false;
    }

    /**
     * Retorna el total de usuarios registrados (para el dashboard).
     */
    public int contar() throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar usuarios", e);
            throw new DatabaseException("Error al contar los usuarios.", e);
        }
        return 0;
    }

    /**
     * Mapea una fila del ResultSet a un objeto Usuario.
     * Método privado: encapsula el detalle del mapeo.
     */
    private Usuario mapearResultSet(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setCorreo(rs.getString("correo"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setIdRol(rs.getInt("id_rol"));
        u.setNombreRol(rs.getString("nombre_rol"));
        u.setEstado(rs.getBoolean("estado"));
        Timestamp ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) u.setFechaCreacion(ts.toLocalDateTime());
        return u;
    }
}
