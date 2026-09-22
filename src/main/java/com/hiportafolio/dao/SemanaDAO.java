package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Semana;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SemanaDAO - Acceso a datos para la entidad Semana.
 *
 * Incluye consultas especiales para navegación secuencial
 * (semana anterior / semana siguiente) del portafolio público.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class SemanaDAO {

    private static final Logger LOGGER = Logger.getLogger(SemanaDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    private static final String SQL_LISTAR =
        "SELECT s.id_semana, s.id_unidad, s.numero, s.titulo, s.descripcion, " +
        "       s.objetivo, s.estado, s.fecha_creacion, u.titulo AS titulo_unidad " +
        "FROM semanas s " +
        "INNER JOIN unidades u ON s.id_unidad = u.id_unidad " +
        "ORDER BY s.numero ASC";

    private static final String SQL_LISTAR_POR_UNIDAD =
        "SELECT s.id_semana, s.id_unidad, s.numero, s.titulo, s.descripcion, " +
        "       s.objetivo, s.estado, s.fecha_creacion, u.titulo AS titulo_unidad " +
        "FROM semanas s " +
        "INNER JOIN unidades u ON s.id_unidad = u.id_unidad " +
        "WHERE s.id_unidad = ? AND s.estado = 1 " +
        "ORDER BY s.numero ASC";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT s.id_semana, s.id_unidad, s.numero, s.titulo, s.descripcion, " +
        "       s.objetivo, s.estado, s.fecha_creacion, u.titulo AS titulo_unidad " +
        "FROM semanas s " +
        "INNER JOIN unidades u ON s.id_unidad = u.id_unidad " +
        "WHERE s.id_semana = ?";

    private static final String SQL_ANTERIOR =
        "SELECT s.id_semana, s.id_unidad, s.numero, s.titulo, s.descripcion, " +
        "       s.objetivo, s.estado, s.fecha_creacion, u.titulo AS titulo_unidad " +
        "FROM semanas s " +
        "INNER JOIN unidades u ON s.id_unidad = u.id_unidad " +
        "WHERE s.numero < ? AND s.estado = 1 " +
        "ORDER BY s.numero DESC LIMIT 1";

    private static final String SQL_SIGUIENTE =
        "SELECT s.id_semana, s.id_unidad, s.numero, s.titulo, s.descripcion, " +
        "       s.objetivo, s.estado, s.fecha_creacion, u.titulo AS titulo_unidad " +
        "FROM semanas s " +
        "INNER JOIN unidades u ON s.id_unidad = u.id_unidad " +
        "WHERE s.numero > ? AND s.estado = 1 " +
        "ORDER BY s.numero ASC LIMIT 1";

    private static final String SQL_INSERTAR =
        "INSERT INTO semanas (id_unidad, numero, titulo, descripcion, objetivo, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE semanas SET id_unidad = ?, numero = ?, titulo = ?, " +
        "descripcion = ?, objetivo = ?, estado = ? WHERE id_semana = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM semanas WHERE id_semana = ?";

    private static final String SQL_CONTAR =
        "SELECT COUNT(*) FROM semanas WHERE estado = 1";

    public List<Semana> listar() throws DatabaseException {
        List<Semana> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar semanas", e);
            throw new DatabaseException("Error al obtener las semanas.", e);
        }
        return lista;
    }

    public List<Semana> listarPorUnidad(int idUnidad) throws DatabaseException {
        List<Semana> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_POR_UNIDAD)) {
            ps.setInt(1, idUnidad);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar semanas por unidad", e);
            throw new DatabaseException("Error al obtener las semanas de la unidad.", e);
        }
        return lista;
    }

    public Semana buscarPorId(int idSemana) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            ps.setInt(1, idSemana);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar semana por id: " + idSemana, e);
            throw new DatabaseException("Error al buscar la semana.", e);
        }
        return null;
    }

    /** Retorna la semana con número inmediatamente anterior (para navegación). */
    public Semana buscarAnterior(int numeroActual) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ANTERIOR)) {
            ps.setInt(1, numeroActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar semana anterior", e);
            throw new DatabaseException("Error al buscar la semana anterior.", e);
        }
        return null;
    }

    /** Retorna la semana con número inmediatamente siguiente (para navegación). */
    public Semana buscarSiguiente(int numeroActual) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SIGUIENTE)) {
            ps.setInt(1, numeroActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar semana siguiente", e);
            throw new DatabaseException("Error al buscar la semana siguiente.", e);
        }
        return null;
    }

    public int guardar(Semana semana) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,    semana.getIdUnidad());
            ps.setInt(2,    semana.getNumero());
            ps.setString(3, semana.getTitulo().trim());
            ps.setString(4, semana.getDescripcion());
            ps.setString(5, semana.getObjetivo());
            ps.setBoolean(6, semana.isEstado());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar semana", e);
            throw new DatabaseException("Error al guardar la semana.", e);
        }
        return -1;
    }

    public boolean actualizar(Semana semana) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setInt(1,    semana.getIdUnidad());
            ps.setInt(2,    semana.getNumero());
            ps.setString(3, semana.getTitulo().trim());
            ps.setString(4, semana.getDescripcion());
            ps.setString(5, semana.getObjetivo());
            ps.setBoolean(6, semana.isEstado());
            ps.setInt(7,    semana.getIdSemana());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar semana", e);
            throw new DatabaseException("Error al actualizar la semana.", e);
        }
    }

    public boolean eliminar(int idSemana) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, idSemana);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar semana", e);
            throw new DatabaseException("Error al eliminar la semana.", e);
        }
    }

    public int contar() throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar semanas", e);
            throw new DatabaseException("Error al contar las semanas.", e);
        }
        return 0;
    }

    private Semana mapearResultSet(ResultSet rs) throws SQLException {
        Semana s = new Semana();
        s.setIdSemana(rs.getInt("id_semana"));
        s.setIdUnidad(rs.getInt("id_unidad"));
        s.setNumero(rs.getInt("numero"));
        s.setTitulo(rs.getString("titulo"));
        s.setDescripcion(rs.getString("descripcion"));
        s.setObjetivo(rs.getString("objetivo"));
        s.setEstado(rs.getBoolean("estado"));
        Timestamp ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) s.setFechaCreacion(ts.toLocalDateTime());
        // titulo_unidad viene del JOIN (puede no estar en todas las queries)
        try { s.getUnidad(); } catch (Exception ignored) {}
        return s;
    }
}
