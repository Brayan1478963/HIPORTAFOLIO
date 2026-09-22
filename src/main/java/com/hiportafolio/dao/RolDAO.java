package com.hiportafolio.dao;

import com.hiportafolio.config.DatabaseConfig;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * RolDAO - Acceso a datos para la entidad Rol.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class RolDAO {

    private static final Logger LOGGER = Logger.getLogger(RolDAO.class.getName());
    private final DatabaseConfig db = DatabaseConfig.getInstance();

    private static final String SQL_LISTAR =
        "SELECT id_rol, nombre, descripcion, estado FROM roles ORDER BY id_rol";

    private static final String SQL_BUSCAR_POR_ID =
        "SELECT id_rol, nombre, descripcion, estado FROM roles WHERE id_rol = ?";

    /**
     * Retorna todos los roles del sistema.
     */
    public List<Rol> listar() throws DatabaseException {
        List<Rol> lista = new ArrayList<>();
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar roles", e);
            throw new DatabaseException("Error al obtener los roles.", e);
        }
        return lista;
    }

    /**
     * Busca un rol por su ID.
     */
    public Rol buscarPorId(int idRol) throws DatabaseException {
        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_BUSCAR_POR_ID)) {

            ps.setInt(1, idRol);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearResultSet(rs);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar rol por id: " + idRol, e);
            throw new DatabaseException("Error al buscar el rol.", e);
        }
        return null;
    }

    private Rol mapearResultSet(ResultSet rs) throws SQLException {
        Rol r = new Rol();
        r.setIdRol(rs.getInt("id_rol"));
        r.setNombre(rs.getString("nombre"));
        r.setDescripcion(rs.getString("descripcion"));
        r.setEstado(rs.getBoolean("estado"));
        return r;
    }
}
