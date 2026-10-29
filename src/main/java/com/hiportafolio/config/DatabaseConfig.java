package com.hiportafolio.config;

import com.hiportafolio.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConfig {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConfig.class.getName());
    private static final String JDBC_DRIVER = "org.postgresql.Driver";

    private static final String DB_HOST;
    private static final String DB_PORT;
    private static final String DB_NAME;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String JDBC_URL;

    private static DatabaseConfig instance;

    static {
        DB_HOST     = cleanConfigValue(getEnvOrFallback("DB_HOST", "PGHOST", "localhost"), "DB_HOST", "PGHOST");
        DB_PORT     = cleanConfigValue(getEnvOrFallback("DB_PORT", "PGPORT", "5432"), "DB_PORT", "PGPORT");
        DB_NAME     = cleanConfigValue(getEnvOrFallback("DB_NAME", "PGDATABASE", "hiportafolio"), "DB_NAME", "PGDATABASE");
        DB_USER     = cleanConfigValue(getEnvOrFallback("DB_USER", "PGUSER", "postgres"), "DB_USER", "PGUSER");
        DB_PASSWORD = cleanConfigValue(getEnvOrFallback("DB_PASSWORD", "PGPASSWORD", ""), "DB_PASSWORD", "PGPASSWORD");

        JDBC_URL = String.format(
            "jdbc:postgresql://%s:%s/%s?currentSchema=public",
            DB_HOST, DB_PORT, DB_NAME
        );

        try {
            Class.forName(JDBC_DRIVER);
            LOGGER.info("Driver PostgreSQL cargado correctamente.");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "No se pudo cargar el driver PostgreSQL: " + e.getMessage(), e);
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
                "No se pudo establecer conexión con la base de datos PostgreSQL. " +
                "Verifique que las credenciales y el servicio estén activos.",
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