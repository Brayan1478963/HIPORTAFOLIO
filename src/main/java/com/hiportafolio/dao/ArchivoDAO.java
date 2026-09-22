package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Archivo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ArchivoDAO - Acceso a datos para la entidad Archivo.
 *
 * Gestiona los metadatos de archivos físicos almacenados en uploads/.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class ArchivoDAO {

    private static final Logger LOGGER = Logger.getLogger(ArchivoDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    private static final String SQL_LISTAR =
        "SELECT a.id_archivo, a.nombre_original, a.nombre_sistema, a.ruta, " +
        "       a.tipo_mime, a.tamano, a.id_usuario, a.estado, a.fecha_subida, " +
        "       CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario " +
        "FROM archivos a " +
        "LEFT JOIN usuarios u ON a.id_usuario = u.id_usuario " +
        "WHERE a.estado = 1 " +
        "ORDER BY a.fecha_subida DESC";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT a.id_archivo, a.nombre_original, a.nombre_sistema, a.ruta, " +
        "       a.tipo_mime, a.tamano, a.id_usuario, a.estado, a.fecha_subida, " +
        "       CONCAT(u.nombre, ' ', u.apellido) AS nombre_usuario " +
        "FROM archivos a " +
        "LEFT JOIN usuarios u ON a.id_usuario = u.id_usuario " +
        "WHERE a.id_archivo = ?";

    private static final String SQL_INSERTAR =
        "INSERT INTO archivos (nombre_original, nombre_sistema, ruta, tipo_mime, tamano, id_usuario, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?, 1)";

    private static final String SQL_ELIMINAR_LOGICO =
        "UPDATE archivos SET estado = 0 WHERE id_archivo = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM archivos WHERE id_archivo = ?";

    private static final String SQL_CONTAR =
        "SELECT COUNT(*) FROM archivos WHERE estado = 1";

    public List<Archivo> listar() throws DatabaseException {
        List<Archivo> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar archivos", e);
            throw new DatabaseException("Error al obtener los archivos.", e);
        }
        return lista;
    }

    public Archivo buscarPorId(int idArchivo) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            ps.setInt(1, idArchivo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar archivo por id", e);
            throw new DatabaseException("Error al buscar el archivo.", e);
        }
        return null;
    }

    public int guardar(Archivo archivo) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, archivo.getNombreOriginal());
            ps.setString(2, archivo.getNombreSistema());
            ps.setString(3, archivo.getRuta());
            ps.setString(4, archivo.getTipoMime());
            ps.setLong(5,   archivo.getTamano());
            if (archivo.getIdUsuario() > 0) {
                ps.setInt(6, archivo.getIdUsuario());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar archivo", e);
            throw new DatabaseException("Error al guardar el archivo.", e);
        }
        return -1;
    }

    /** Eliminación lógica: marca el registro como inactivo sin borrarlo. */
    public boolean eliminarLogico(int idArchivo) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR_LOGICO)) {
            ps.setInt(1, idArchivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar logicamente archivo", e);
            throw new DatabaseException("Error al eliminar el archivo.", e);
        }
    }

    /** Eliminación física del registro. Úsese con precaución. */
    public boolean eliminar(int idArchivo) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, idArchivo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar archivo", e);
            throw new DatabaseException("Error al eliminar el archivo.", e);
        }
    }

    public int contar() throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar archivos", e);
            throw new DatabaseException("Error al contar los archivos.", e);
        }
        return 0;
    }

    private Archivo mapearResultSet(ResultSet rs) throws SQLException {
        Archivo a = new Archivo();
        a.setIdArchivo(rs.getInt("id_archivo"));
        a.setNombreOriginal(rs.getString("nombre_original"));
        a.setNombreSistema(rs.getString("nombre_sistema"));
        a.setRuta(rs.getString("ruta"));
        a.setTipoMime(rs.getString("tipo_mime"));
        a.setTamano(rs.getLong("tamano"));
        a.setIdUsuario(rs.getInt("id_usuario"));
        a.setNombreUsuario(rs.getString("nombre_usuario"));
        a.setEstado(rs.getBoolean("estado"));
        Timestamp ts = rs.getTimestamp("fecha_subida");
        if (ts != null) a.setFechaSubida(ts.toLocalDateTime());
        return a;
    }
}
