package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Tecnologia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TecnologiaDAO - Acceso a datos para la entidad Tecnologia.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class TecnologiaDAO {

    private static final Logger LOGGER = Logger.getLogger(TecnologiaDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    private static final String SQL_LISTAR =
        "SELECT t.id_tecnologia, t.id_categoria, t.nombre, t.descripcion, " +
        "       t.icono, t.nivel, t.estado, t.orden, c.nombre AS nombre_categoria " +
        "FROM tecnologias t " +
        "LEFT JOIN categorias c ON t.id_categoria = c.id_categoria " +
        "ORDER BY t.orden ASC, t.nombre ASC";

    private static final String SQL_LISTAR_ACTIVAS =
        "SELECT t.id_tecnologia, t.id_categoria, t.nombre, t.descripcion, " +
        "       t.icono, t.nivel, t.estado, t.orden, c.nombre AS nombre_categoria " +
        "FROM tecnologias t " +
        "LEFT JOIN categorias c ON t.id_categoria = c.id_categoria " +
        "WHERE t.estado = 1 " +
        "ORDER BY t.orden ASC, t.nombre ASC";

    private static final String SQL_LISTAR_POR_PROYECTO =
        "SELECT t.id_tecnologia, t.id_categoria, t.nombre, t.descripcion, " +
        "       t.icono, t.nivel, t.estado, t.orden, c.nombre AS nombre_categoria " +
        "FROM tecnologias t " +
        "LEFT JOIN categorias c ON t.id_categoria = c.id_categoria " +
        "INNER JOIN proyecto_tecnologia pt ON t.id_tecnologia = pt.id_tecnologia " +
        "WHERE pt.id_proyecto = ? " +
        "ORDER BY t.orden ASC";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT t.id_tecnologia, t.id_categoria, t.nombre, t.descripcion, " +
        "       t.icono, t.nivel, t.estado, t.orden, c.nombre AS nombre_categoria " +
        "FROM tecnologias t " +
        "LEFT JOIN categorias c ON t.id_categoria = c.id_categoria " +
        "WHERE t.id_tecnologia = ?";

    private static final String SQL_INSERTAR =
        "INSERT INTO tecnologias (id_categoria, nombre, descripcion, icono, nivel, estado, orden) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE tecnologias SET id_categoria = ?, nombre = ?, descripcion = ?, " +
        "icono = ?, nivel = ?, estado = ?, orden = ? WHERE id_tecnologia = ?";

    private static final String SQL_ELIMINAR =
        "DELETE FROM tecnologias WHERE id_tecnologia = ?";

    private static final String SQL_CONTAR =
        "SELECT COUNT(*) FROM tecnologias WHERE estado = 1";

    public List<Tecnologia> listar() throws DatabaseException {
        List<Tecnologia> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar tecnologias", e);
            throw new DatabaseException("Error al obtener las tecnologías.", e);
        }
        return lista;
    }

    public List<Tecnologia> listarActivas() throws DatabaseException {
        List<Tecnologia> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_ACTIVAS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar tecnologias activas", e);
            throw new DatabaseException("Error al obtener las tecnologías activas.", e);
        }
        return lista;
    }

    public List<Tecnologia> listarPorProyecto(int idProyecto) throws DatabaseException {
        List<Tecnologia> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_POR_PROYECTO)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar tecnologias por proyecto", e);
            throw new DatabaseException("Error al obtener las tecnologías del proyecto.", e);
        }
        return lista;
    }

    public Tecnologia buscarPorId(int idTecnologia) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            ps.setInt(1, idTecnologia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar tecnologia por id", e);
            throw new DatabaseException("Error al buscar la tecnología.", e);
        }
        return null;
    }

    public int guardar(Tecnologia t) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            if (t.getIdCategoria() > 0) ps.setInt(1, t.getIdCategoria());
            else                         ps.setNull(1, Types.INTEGER);
            ps.setString(2, t.getNombre().trim());
            ps.setString(3, t.getDescripcion());
            ps.setString(4, t.getIcono());
            ps.setString(5, t.getNivel());
            ps.setBoolean(6, t.isEstado());
            ps.setInt(7, t.getOrden() > 0 ? t.getOrden() : 1);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar tecnologia", e);
            throw new DatabaseException("Error al guardar la tecnología.", e);
        }
        return -1;
    }

    public boolean actualizar(Tecnologia t) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ACTUALIZAR)) {
            if (t.getIdCategoria() > 0) ps.setInt(1, t.getIdCategoria());
            else                         ps.setNull(1, Types.INTEGER);
            ps.setString(2, t.getNombre().trim());
            ps.setString(3, t.getDescripcion());
            ps.setString(4, t.getIcono());
            ps.setString(5, t.getNivel());
            ps.setBoolean(6, t.isEstado());
            ps.setInt(7, t.getOrden() > 0 ? t.getOrden() : 1);
            ps.setInt(8, t.getIdTecnologia());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar tecnologia", e);
            throw new DatabaseException("Error al actualizar la tecnología.", e);
        }
    }

    public boolean eliminar(int idTecnologia) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, idTecnologia);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar tecnologia", e);
            throw new DatabaseException("Error al eliminar la tecnología.", e);
        }
    }

    public int contar() throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar tecnologias", e);
            throw new DatabaseException("Error al contar las tecnologías.", e);
        }
        return 0;
    }

    private Tecnologia mapearResultSet(ResultSet rs) throws SQLException {
        Tecnologia t = new Tecnologia();
        t.setIdTecnologia(rs.getInt("id_tecnologia"));
        t.setIdCategoria(rs.getInt("id_categoria"));
        t.setNombre(rs.getString("nombre"));
        t.setDescripcion(rs.getString("descripcion"));
        t.setIcono(rs.getString("icono"));
        t.setNivel(rs.getString("nivel"));
        t.setEstado(rs.getBoolean("estado"));
        t.setOrden(rs.getInt("orden"));
        t.setNombreCategoria(rs.getString("nombre_categoria"));
        return t;
    }
}
