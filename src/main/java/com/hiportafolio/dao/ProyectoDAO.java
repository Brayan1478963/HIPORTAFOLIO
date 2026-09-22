package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Proyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ProyectoDAO - Acceso a datos para la entidad Proyecto.
 *
 * Gestiona la relación N:M con tecnologías via tabla proyecto_tecnologia.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class ProyectoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProyectoDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    private static final String SQL_LISTAR =
        "SELECT id_proyecto, nombre, descripcion, objetivo, problema, arquitectura, " +
        "funcionalidades, repositorio, demo, imagen, estado, fecha_inicio, " +
        "fecha_fin, orden, activo, fecha_creacion " +
        "FROM proyectos ORDER BY orden ASC, fecha_creacion DESC";

    private static final String SQL_LISTAR_ACTIVOS =
        "SELECT id_proyecto, nombre, descripcion, objetivo, problema, arquitectura, " +
        "funcionalidades, repositorio, demo, imagen, estado, fecha_inicio, " +
        "fecha_fin, orden, activo, fecha_creacion " +
        "FROM proyectos WHERE activo = 1 ORDER BY orden ASC";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT id_proyecto, nombre, descripcion, objetivo, problema, arquitectura, " +
        "funcionalidades, repositorio, demo, imagen, estado, fecha_inicio, " +
        "fecha_fin, orden, activo, fecha_creacion " +
        "FROM proyectos WHERE id_proyecto = ?";

    private static final String SQL_INSERTAR =
        "INSERT INTO proyectos (nombre, descripcion, objetivo, problema, arquitectura, " +
        "funcionalidades, repositorio, demo, imagen, estado, fecha_inicio, fecha_fin, orden, activo) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ACTUALIZAR =
        "UPDATE proyectos SET nombre = ?, descripcion = ?, objetivo = ?, problema = ?, " +
        "arquitectura = ?, funcionalidades = ?, repositorio = ?, demo = ?, imagen = ?, " +
        "estado = ?, fecha_inicio = ?, fecha_fin = ?, orden = ?, activo = ? " +
        "WHERE id_proyecto = ?";

    private static final String SQL_ELIMINAR = "DELETE FROM proyectos WHERE id_proyecto = ?";
    private static final String SQL_CONTAR   = "SELECT COUNT(*) FROM proyectos WHERE activo = 1";

    // Relación N:M con tecnologías
    private static final String SQL_ASIGNAR_TEC =
        "INSERT IGNORE INTO proyecto_tecnologia (id_proyecto, id_tecnologia) VALUES (?, ?)";
    private static final String SQL_QUITAR_TODAS_TEC =
        "DELETE FROM proyecto_tecnologia WHERE id_proyecto = ?";

    public List<Proyecto> listar() throws DatabaseException {
        List<Proyecto> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar proyectos", e);
            throw new DatabaseException("Error al obtener los proyectos.", e);
        }
        return lista;
    }

    public List<Proyecto> listarActivos() throws DatabaseException {
        List<Proyecto> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR_ACTIVOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapearResultSet(rs));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar proyectos activos", e);
            throw new DatabaseException("Error al obtener los proyectos activos.", e);
        }
        return lista;
    }

    public Proyecto buscarPorId(int idProyecto) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar proyecto por id", e);
            throw new DatabaseException("Error al buscar el proyecto.", e);
        }
        return null;
    }

    public int guardar(Proyecto p) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERTAR,
                     Statement.RETURN_GENERATED_KEYS)) {
            setParams(ps, p, false);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar proyecto", e);
            throw new DatabaseException("Error al guardar el proyecto.", e);
        }
        return -1;
    }

    public boolean actualizar(Proyecto p) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ACTUALIZAR)) {
            setParams(ps, p, true);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar proyecto", e);
            throw new DatabaseException("Error al actualizar el proyecto.", e);
        }
    }

    public boolean eliminar(int idProyecto) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, idProyecto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar proyecto", e);
            throw new DatabaseException("Error al eliminar el proyecto.", e);
        }
    }

    /** Reemplaza todas las tecnologías asignadas al proyecto. */
    public void actualizarTecnologias(int idProyecto, List<Integer> idsTecnologias)
            throws DatabaseException {
        try (Connection conn = db.getConnection()) {
            // 1. Quitar todas las relaciones actuales
            try (PreparedStatement ps = conn.prepareStatement(SQL_QUITAR_TODAS_TEC)) {
                ps.setInt(1, idProyecto);
                ps.executeUpdate();
            }
            // 2. Insertar las nuevas relaciones
            if (idsTecnologias != null && !idsTecnologias.isEmpty()) {
                try (PreparedStatement ps = conn.prepareStatement(SQL_ASIGNAR_TEC)) {
                    for (int idTec : idsTecnologias) {
                        ps.setInt(1, idProyecto);
                        ps.setInt(2, idTec);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar tecnologias del proyecto", e);
            throw new DatabaseException("Error al actualizar las tecnologías del proyecto.", e);
        }
    }

    public int contar() throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_CONTAR);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al contar proyectos", e);
            throw new DatabaseException("Error al contar los proyectos.", e);
        }
        return 0;
    }

    /** Establece parámetros del PreparedStatement para INSERT y UPDATE. */
    private void setParams(PreparedStatement ps, Proyecto p, boolean esUpdate)
            throws SQLException {
        ps.setString(1, p.getNombre().trim());
        ps.setString(2, p.getDescripcion());
        ps.setString(3, p.getObjetivo());
        ps.setString(4, p.getProblema());
        ps.setString(5, p.getArquitectura());
        ps.setString(6, p.getFuncionalidades());
        ps.setString(7, p.getRepositorio());
        ps.setString(8, p.getDemo());
        ps.setString(9, p.getImagen());
        ps.setString(10, p.getEstado() != null ? p.getEstado() : "en_desarrollo");
        if (p.getFechaInicio() != null) ps.setDate(11, Date.valueOf(p.getFechaInicio()));
        else                             ps.setNull(11, Types.DATE);
        if (p.getFechaFin() != null)    ps.setDate(12, Date.valueOf(p.getFechaFin()));
        else                             ps.setNull(12, Types.DATE);
        ps.setInt(13,    p.getOrden() > 0 ? p.getOrden() : 1);
        ps.setBoolean(14, p.isActivo());
        if (esUpdate) ps.setInt(15, p.getIdProyecto());
    }

    private Proyecto mapearResultSet(ResultSet rs) throws SQLException {
        Proyecto p = new Proyecto();
        p.setIdProyecto(rs.getInt("id_proyecto"));
        p.setNombre(rs.getString("nombre"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setObjetivo(rs.getString("objetivo"));
        p.setProblema(rs.getString("problema"));
        p.setArquitectura(rs.getString("arquitectura"));
        p.setFuncionalidades(rs.getString("funcionalidades"));
        p.setRepositorio(rs.getString("repositorio"));
        p.setDemo(rs.getString("demo"));
        p.setImagen(rs.getString("imagen"));
        p.setEstado(rs.getString("estado"));
        Date di = rs.getDate("fecha_inicio");
        if (di != null) p.setFechaInicio(di.toLocalDate());
        Date df = rs.getDate("fecha_fin");
        if (df != null) p.setFechaFin(df.toLocalDate());
        p.setOrden(rs.getInt("orden"));
        p.setActivo(rs.getBoolean("activo"));
        Timestamp ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) p.setFechaCreacion(ts.toLocalDateTime());
        return p;
    }
}
