package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Evidencia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EvidenciaDAO - Acceso a datos para la entidad Evidencia.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class EvidenciaDAO {

    private static final Logger LOGGER = Logger.getLogger(EvidenciaDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    private static final String COLS =
        "e.id_evidencia, e.id_semana, e.id_archivo, e.titulo, e.descripcion, " +
        "e.tipo, e.fecha_evidencia, e.estado, e.fecha_creacion, " +
        "s.titulo AS titulo_semana, s.numero AS numero_semana, u.titulo AS titulo_unidad";

    private static final String JOINS =
        "FROM evidencias e " +
        "INNER JOIN semanas  s ON e.id_semana  = s.id_semana " +
        "INNER JOIN unidades u ON s.id_unidad  = u.id_unidad ";

    private static final String SQL_LISTAR =
        "SELECT " + COLS + " " + JOINS + "ORDER BY e.fecha_creacion DESC";

    private static final String SQL_LISTAR_ACTIVAS =
        "SELECT " + COLS + " " + JOINS + "WHERE e.estado = 1 ORDER BY e.fecha_creacion DESC";

    private static final String SQL_LISTAR_POR_SEMANA =
        "SELECT " + COLS + " " + JOINS +
        "WHERE e.id_semana = ? AND e.estado = 1 ORDER BY e.fecha_creacion DESC";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT " + COLS + " " + JOINS + "WHERE e.id_evidencia = ?";

    private static final String SQL_INSERTAR =
        "INSERT INTO evidencias (id_semana, id_archivo, titulo, descripcion, tipo, fecha_evidencia, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE evidencias SET id_semana = ?, id_archivo = ?, titulo = ?, " +
        "descripcion = ?, tipo = ?, fecha_evidencia = ?, estado = ? " +
        "WHERE id_evidencia = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM evidencias WHERE id_evidencia = ?";

    private static final String SQL_CONTAR =
        "SELECT COUNT(*) FROM evidencias WHERE estado = 1";

    public List<Evidencia> listar() throws DatabaseException {
        List<Evidencia> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar evidencias", e);
            throw new DatabaseException("Error al obtener las evidencias.", e);
        }
        return lista;
    }

    public List<Evidencia> listarActivas() throws DatabaseException {
        List<Evidencia> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_ACTIVAS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar evidencias activas", e);
            throw new DatabaseException("Error al obtener las evidencias activas.", e);
        }
        return lista;
    }

    public List<Evidencia> listarPorSemana(int idSemana) throws DatabaseException {
        List<Evidencia> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_POR_SEMANA)) {
            ps.setInt(1, idSemana);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar evidencias por semana", e);
            throw new DatabaseException("Error al obtener las evidencias de la semana.", e);
        }
        return lista;
    }

    public Evidencia buscarPorId(int idEvidencia) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            ps.setInt(1, idEvidencia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar evidencia por id", e);
            throw new DatabaseException("Error al buscar la evidencia.", e);
        }
        return null;
    }

    public int guardar(Evidencia ev) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ev.getIdSemana());
            if (ev.getIdArchivo() > 0) ps.setInt(2, ev.getIdArchivo());
            else                        ps.setNull(2, Types.INTEGER);
            ps.setString(3, ev.getTitulo().trim());
            ps.setString(4, ev.getDescripcion());
            ps.setString(5, ev.getTipo());
            if (ev.getFechaEvidencia() != null) ps.setDate(6, Date.valueOf(ev.getFechaEvidencia()));
            else                                 ps.setNull(6, Types.DATE);
            ps.setBoolean(7, ev.isEstado());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar evidencia", e);
            throw new DatabaseException("Error al guardar la evidencia.", e);
        }
        return -1;
    }

    public boolean actualizar(Evidencia ev) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setInt(1, ev.getIdSemana());
            if (ev.getIdArchivo() > 0) ps.setInt(2, ev.getIdArchivo());
            else                        ps.setNull(2, Types.INTEGER);
            ps.setString(3, ev.getTitulo().trim());
            ps.setString(4, ev.getDescripcion());
            ps.setString(5, ev.getTipo());
            if (ev.getFechaEvidencia() != null) ps.setDate(6, Date.valueOf(ev.getFechaEvidencia()));
            else                                 ps.setNull(6, Types.DATE);
            ps.setBoolean(7, ev.isEstado());
            ps.setInt(8, ev.getIdEvidencia());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar evidencia", e);
            throw new DatabaseException("Error al actualizar la evidencia.", e);
        }
    }

    public boolean eliminar(int idEvidencia) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, idEvidencia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar evidencia", e);
            throw new DatabaseException("Error al eliminar la evidencia.", e);
        }
    }

    public int contar() throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar evidencias", e);
            throw new DatabaseException("Error al contar las evidencias.", e);
        }
        return 0;
    }

    private Evidencia mapearResultSet(ResultSet rs) throws SQLException {
        Evidencia ev = new Evidencia();
        ev.setIdEvidencia(rs.getInt("id_evidencia"));
        ev.setIdSemana(rs.getInt("id_semana"));
        ev.setIdArchivo(rs.getInt("id_archivo"));
        ev.setTitulo(rs.getString("titulo"));
        ev.setDescripcion(rs.getString("descripcion"));
        ev.setTipo(rs.getString("tipo"));
        Date d = rs.getDate("fecha_evidencia");
        if (d != null) ev.setFechaEvidencia(d.toLocalDate());
        ev.setEstado(rs.getBoolean("estado"));
        Timestamp ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) ev.setFechaCreacion(ts.toLocalDateTime());
        ev.setTituloSemana(rs.getString("titulo_semana"));
        ev.setNumeroSemana(rs.getInt("numero_semana"));
        ev.setTituloUnidad(rs.getString("titulo_unidad"));
        return ev;
    }
}
