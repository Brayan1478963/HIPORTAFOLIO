package com.hiportafolio.config;

import com.hiportafolio.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DatabaseConfig - Configuración centralizada de conexión a MySQL.
 *
 * Responsabilidad: Proveer conexiones JDBC a la base de datos.
 * Compatible con Railway (MYSQLHOST, MYSQLPORT, MYSQLDATABASE, MYSQLUSER, MYSQLPASSWORD)
 * y entorno local (DB_HOST, DB_PORT, DB_NAME, DB_USER, DB_PASSWORD).
 *
 * Patrón: Singleton para la configuración, Factory Method para conexiones.
 *
 * @author HiPortafolio
 * @version 1.2
 */
public class DatabaseConfig {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConfig.class.getName());

    // Driver JDBC para MySQL 8+
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Valores de configuración leídos desde variables de entorno
    private static final String DB_HOST;
    private static final String DB_PORT;
    private static final String DB_NAME;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    // URL de conexión JDBC construida dinámicamente
    private static final String JDBC_URL;

    // Instancia Singleton
    private static DatabaseConfig instance;

    /*
     * Bloque estático: se ejecuta una sola vez al cargar la clase.
     * Lee las variables de entorno de forma limpia y construye la URL de conexión.
     */
    static {
        DB_HOST     = getCleanEnv("MYSQLHOST", "DB_HOST", "localhost");
        DB_PORT     = getCleanEnv("MYSQLPORT", "DB_PORT", "3306");
        DB_NAME     = getCleanEnv("MYSQLDATABASE", "DB_NAME", "hiportafolio");
        DB_USER     = getCleanEnv("MYSQLUSER", "DB_USER", "root");
        DB_PASSWORD = getCleanEnv("MYSQLPASSWORD", "DB_PASSWORD", "");

        // Parámetros de conexión: UTF-8, zona horaria, SSL desactivado para desarrollo
        JDBC_URL = String.format(
            "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8" +
            "&useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true" +
            "&autoReconnect=true",
            DB_HOST, DB_PORT, DB_NAME
        );

        // Registrar el driver JDBC
        try {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "No se pudo cargar el driver MySQL: " + e.getMessage(), e);
        }
    }

    /**
     * Constructor privado (Singleton).
     */
    private DatabaseConfig() {}

    /**
     * Retorna la instancia única de DatabaseConfig.
     */
    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    /**
     * Obtiene una nueva conexión JDBC a la base de datos.
     */
    public Connection getConnection() throws DatabaseException {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos: " + e.getMessage(), e);
            throw new DatabaseException(
                "No se pudo establecer conexión con la base de datos. " +
                "Verifique que MySQL esté activo y las credenciales sean correctas.",
                e
            );
        }
    }

    /**
     * Cierra una conexión de manera segura.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar la conexión: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Verifica si la conexión está activa y disponible.
     */
    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (DatabaseException | SQLException e) {
            LOGGER.log(Level.WARNING, "Test de conexión fallido: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Limpia la variable de entorno por si contiene prefijos accidentales (ej. DB_HOST=...).
     */
    private static String getCleanEnv(String preferredVar, String fallbackVar, String defaultValue) {
        String value = System.getenv(preferredVar);
        if (value == null || value.trim().isEmpty()) {
            value = System.getenv(fallbackVar);
        }
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        
        value = value.trim();
        // Si por error se pasó con formato "KEY=valor", extraemos solo el valor
        if (value.contains("=")) {
            String[] parts = value.split("=", 2);
            if (parts.length > 1) {
                value = parts[1].trim();
            }
        }
        return value;
    }

    // Getters de solo lectura
    public String getDbHost()    { return DB_HOST; }
    public String getDbPort()    { return DB_PORT; }
    public String getDbName()    { return DB_NAME; }
    public String getDbUser()    { return DB_USER; }
    public String getJdbcUrl()   { return JDBC_URL; }
}