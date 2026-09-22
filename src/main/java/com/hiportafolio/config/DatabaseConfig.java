package com.hiportafolio.config;

import com.hiportafolio.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConfig {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConfig.class.getName());
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String DB_HOST;
    private static final String DB_PORT;
    private static final String DB_NAME;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String JDBC_URL;

    private static DatabaseConfig instance;

    static {
        DB_HOST     = cleanConfigValue(getEnvOrFallback("DB_HOST", "MYSQLHOST", "localhost"), "DB_HOST", "MYSQLHOST");
        DB_PORT     = cleanConfigValue(getEnvOrFallback("DB_PORT", "MYSQLPORT", "3306"), "DB_PORT", "MYSQLPORT");
        DB_NAME     = cleanConfigValue(getEnvOrFallback("DB_NAME", "MYSQLDATABASE", "railway"), "DB_NAME", "MYSQLDATABASE");
        DB_USER     = cleanConfigValue(getEnvOrFallback("DB_USER", "MYSQLUSER", "root"), "DB_USER", "MYSQLUSER");
        DB_PASSWORD = cleanConfigValue(getEnvOrFallback("DB_PASSWORD", "MYSQLPASSWORD", ""), "DB_PASSWORD", "MYSQLPASSWORD");

        JDBC_URL = String.format(
            "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=UTF-8" +
            "&useSSL=false&serverTimezone=America/Lima&allowPublicKeyRetrieval=true" +
            "&autoReconnect=true",
            DB_HOST, DB_PORT, DB_NAME
        );

        try {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Driver MySQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "No se pudo cargar el driver MySQL: " + e.getMessage(), e);
        }
    }

    private DatabaseConfig() {}

    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

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

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error al cerrar la conexión: " + e.getMessage(), e);
            }
        }
    }

    public boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (DatabaseException | SQLException e) {
            LOGGER.log(Level.WARNING, "Test de conexión fallido: " + e.getMessage(), e);
            return false;
        }
    }

    private static String getEnvOrDefault(String envVar, String defaultValue) {
        String value = System.getenv(envVar);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value.trim();
    }

    private static String cleanConfigValue(String value, String primaryVar, String fallbackVar) {
        String cleanedValue = value.trim();
        String primaryPrefix = primaryVar + "=";
        String fallbackPrefix = fallbackVar + "=";
        if (cleanedValue.startsWith(primaryPrefix)) {
            return cleanedValue.substring(primaryPrefix.length()).trim();
        }
        if (cleanedValue.startsWith(fallbackPrefix)) {
            return cleanedValue.substring(fallbackPrefix.length()).trim();
        }
        return cleanedValue;
    }

    private static String getEnvOrFallback(String preferredVar, String fallbackVar, String defaultValue) {
        String preferred = System.getenv(preferredVar);
        if (preferred != null && !preferred.trim().isEmpty()) return preferred.trim();
        return getEnvOrDefault(fallbackVar, defaultValue);
    }

    public String getDbHost()  { return DB_HOST; }
    public String getDbPort()  { return DB_PORT; }
    public String getDbName()  { return DB_NAME; }
    public String getDbUser()  { return DB_USER; }
    public String getJdbcUrl() { return JDBC_URL; }
}