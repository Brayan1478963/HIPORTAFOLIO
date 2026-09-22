package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Contenido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ContenidoDAO - Acceso a datos para la entidad Contenido.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class ContenidoDAO {

    private static final Logger LOGGER = Logger.getLogger(ContenidoDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    private static final String SQL_LISTAR =
        "SELECT c.id_contenido, c.id_semana, c.titulo, c.descripcion, c.contenido, " +
        "       c.aprendizaje, c.reflexion, c.referencias, c.orden, c.estado, " +
        "       c.fecha_creacion, c.fecha_actualizacion, s.titulo AS titulo_semana " +
        "FROM contenidos c " +
        "INNER JOIN semanas s ON c.id_semana = s.id_semana " +
        "ORDER BY c.id_semana ASC, c.orden ASC";

    private static final String SQL_LISTAR_POR_SEMANA =
        "SELECT c.id_contenido, c.id_semana, c.titulo, c.descripcion, c.contenido, " +
        "       c.aprendizaje, c.reflexion, c.referencias, c.orden, c.estado, " +
        "       c.fecha_creacion, c.fecha_actualizacion, s.titulo AS titulo_semana " +
        "FROM contenidos c " +
        "INNER JOIN semanas s ON c.id_semana = s.id_semana " +
        "WHERE c.id_semana = ? AND c.estado = 1 " +
        "ORDER BY c.orden ASC";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT c.id_contenido, c.id_semana, c.titulo, c.descripcion, c.contenido, " +
        "       c.aprendizaje, c.reflexion, c.referencias, c.orden, c.estado, " +
        "       c.fecha_creacion, c.fecha_actualizacion, s.titulo AS titulo_semana " +
        "FROM contenidos c " +
        "INNER JOIN semanas s ON c.id_semana = s.id_semana " +
        "WHERE c.id_contenido = ?";

    private static final String SQL_INSERTAR =
        "INSERT INTO contenidos (id_semana, titulo, descripcion, contenido, " +
        "aprendizaje, reflexion, referencias, orden, estado) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE contenidos SET id_semana = ?, titulo = ?, descripcion = ?, " +
        "contenido = ?, aprendizaje = ?, reflexion = ?, referencias = ?, " +
        "orden = ?, estado = ? WHERE id_contenido = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM contenidos WHERE id_contenido = ?";

    private static final String SQL_CONTAR =
        "SELECT COUNT(*) FROM contenidos WHERE estado = 1";

    public List<Contenido> listar() throws DatabaseException {
        List<Contenido> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar contenidos", e);
            throw new DatabaseException("Error al obtener los contenidos.", e);
        }
        return lista;
    }

    public List<Contenido> listarPorSemana(int idSemana) throws DatabaseException {
        List<Contenido> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_POR_SEMANA)) {
            ps.setInt(1, idSemana);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar contenidos por semana", e);
            throw new DatabaseException("Error al obtener los contenidos de la semana.", e);
        }
        return lista;
    }

    public Contenido buscarPorId(int idContenido) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            ps.setInt(1, idContenido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar contenido por id", e);
            throw new DatabaseException("Error al buscar el contenido.", e);
        }
        return null;
    }

    public int guardar(Contenido c) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1,    c.getIdSemana());
            ps.setString(2, c.getTitulo().trim());
            ps.setString(3, c.getDescripcion());
            ps.setString(4, c.getContenido());
            ps.setString(5, c.getAprendizaje());
            ps.setString(6, c.getReflexion());
            ps.setString(7, c.getReferencias());
            ps.setInt(8,    c.getOrden() > 0 ? c.getOrden() : 1);
            ps.setBoolean(9, c.isEstado());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar contenido", e);
            throw new DatabaseException("Error al guardar el contenido.", e);
        }
        return -1;
    }

    public boolean actualizar(Contenido c) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setInt(1,    c.getIdSemana());
            ps.setString(2, c.getTitulo().trim());
            ps.setString(3, c.getDescripcion());
            ps.setString(4, c.getContenido());
            ps.setString(5, c.getAprendizaje());
            ps.setString(6, c.getReflexion());
            ps.setString(7, c.getReferencias());
            ps.setInt(8,    c.getOrden() > 0 ? c.getOrden() : 1);
            ps.setBoolean(9, c.isEstado());
            ps.setInt(10,   c.getIdContenido());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar contenido", e);
            throw new DatabaseException("Error al actualizar el contenido.", e);
        }
    }

    public boolean eliminar(int idContenido) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, idContenido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar contenido", e);
            throw new DatabaseException("Error al eliminar el contenido.", e);
        }
    }

    public int contar() throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar contenidos", e);
            throw new DatabaseException("Error al contar los contenidos.", e);
        }
        return 0;
    }

    private Contenido mapearResultSet(ResultSet rs) throws SQLException {
        Contenido c = new Contenido();
        c.setIdContenido(rs.getInt("id_contenido"));
        c.setIdSemana(rs.getInt("id_semana"));
        c.setTitulo(rs.getString("titulo"));
        c.setDescripcion(rs.getString("descripcion"));
        c.setContenido(rs.getString("contenido"));
        c.setAprendizaje(rs.getString("aprendizaje"));
        c.setReflexion(rs.getString("reflexion"));
        c.setReferencias(rs.getString("referencias"));
        c.setOrden(rs.getInt("orden"));
        c.setEstado(rs.getBoolean("estado"));
        c.setTituloSemana(rs.getString("titulo_semana"));
        Timestamp ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) c.setFechaCreacion(ts.toLocalDateTime());
        Timestamp tsAct = rs.getTimestamp("fecha_actualizacion");
        if (tsAct != null) c.setFechaActualizacion(tsAct.toLocalDateTime());
        return c;
    }
}
