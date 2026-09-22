package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Unidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UnidadDAO - Acceso a datos para la entidad Unidad.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class UnidadDAO {

    private static final Logger LOGGER = Logger.getLogger(UnidadDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    private static final String SQL_LISTAR =
        "SELECT id_unidad, numero, titulo, descripcion, estado, fecha_creacion " +
        "FROM unidades ORDER BY numero ASC";

    private static final String SQL_LISTAR_ACTIVAS =
        "SELECT id_unidad, numero, titulo, descripcion, estado, fecha_creacion " +
        "FROM unidades WHERE estado = 1 ORDER BY numero ASC";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT id_unidad, numero, titulo, descripcion, estado, fecha_creacion " +
        "FROM unidades WHERE id_unidad = ?";

    private static final String SQL_INSERTAR =
        "INSERT INTO unidades (numero, titulo, descripcion, estado) VALUES (?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE unidades SET numero = ?, titulo = ?, descripcion = ?, estado = ? " +
        "WHERE id_unidad = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM unidades WHERE id_unidad = ?";

    private static final String SQL_CONTAR =
        "SELECT COUNT(*) FROM unidades WHERE estado = 1";

    public List<Unidad> listar() throws DatabaseException {
        List<Unidad> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar unidades", e);
            throw new DatabaseException("Error al obtener las unidades.", e);
        }
        return lista;
    }

    public List<Unidad> listarActivas() throws DatabaseException {
        List<Unidad> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_ACTIVAS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar unidades activas", e);
            throw new DatabaseException("Error al obtener las unidades activas.", e);
        }
        return lista;
    }

    public Unidad buscarPorId(int idUnidad) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            ps.setInt(1, idUnidad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar unidad por id: " + idUnidad, e);
            throw new DatabaseException("Error al buscar la unidad.", e);
        }
        return null;
    }

    public int guardar(Unidad unidad) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,    unidad.getNumero());
            ps.setString(2, unidad.getTitulo().trim());
            ps.setString(3, unidad.getDescripcion());
            ps.setBoolean(4, unidad.isEstado());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar unidad", e);
            throw new DatabaseException("Error al guardar la unidad.", e);
        }
        return -1;
    }

    public boolean actualizar(Unidad unidad) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setInt(1,    unidad.getNumero());
            ps.setString(2, unidad.getTitulo().trim());
            ps.setString(3, unidad.getDescripcion());
            ps.setBoolean(4, unidad.isEstado());
            ps.setInt(5,    unidad.getIdUnidad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar unidad", e);
            throw new DatabaseException("Error al actualizar la unidad.", e);
        }
    }

    public boolean eliminar(int idUnidad) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, idUnidad);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar unidad", e);
            throw new DatabaseException("Error al eliminar la unidad.", e);
        }
    }

    public int contar() throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar unidades", e);
            throw new DatabaseException("Error al contar las unidades.", e);
        }
        return 0;
    }

    private Unidad mapearResultSet(ResultSet rs) throws SQLException {
        Unidad u = new Unidad();
        u.setIdUnidad(rs.getInt("id_unidad"));
        u.setNumero(rs.getInt("numero"));
        u.setTitulo(rs.getString("titulo"));
        u.setDescripcion(rs.getString("descripcion"));
        u.setEstado(rs.getBoolean("estado"));
        Timestamp ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) u.setFechaCreacion(ts.toLocalDateTime());
        return u;
    }
}
