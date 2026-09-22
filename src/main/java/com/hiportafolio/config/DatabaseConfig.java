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
 * Las credenciales se leen desde variables de entorno para evitar
 * exponer datos sensibles en el código fuente (buena práctica de seguridad).
 *
 * Variables de entorno esperadas:
 *   DB_HOST     - Host del servidor MySQL (default: localhost)
 *   DB_PORT     - Puerto MySQL (default: 3306)
 *   DB_NAME     - Nombre de la base de datos (default: hiportafolio)
 *   DB_USER     - Usuario MySQL (default: root)
 *   DB_PASSWORD - Contraseña MySQL (default: vacío)
 *
 * Patrón: Singleton para la configuración, Factory Method para conexiones.
 *
 * @author HiPortafolio
 * @version 1.0
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
     * Lee las variables de entorno y construye la URL de conexión.
     */
    static {
        DB_HOST     = getEnvOrFallback("DB_HOST",     "MYSQLHOST",     "localhost");
        DB_PORT     = getEnvOrFallback("DB_PORT",     "MYSQLPORT",     "3307");
        DB_NAME     = getEnvOrFallback("DB_NAME",     "MYSQLDATABASE", "hiportafolio");
        DB_USER     = getEnvOrFallback("DB_USER",     "MYSQLUSER",     "root");
        DB_PASSWORD = getEnvOrFallback("DB_PASSWORD", "MYSQLPASSWORD", "");

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
     * Thread-safe con doble verificación.
     */
    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }

    /**
     * Obtiene una nueva conexión JDBC a la base de datos.
     * El llamador es responsable de cerrar la conexión después de usarla.
     *
     * @return Connection objeto de conexión activa
     * @throws DatabaseException si no se puede establecer la conexión
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
     * Cierra una conexión de manera segura sin propagar excepciones.
     * Uso idiomático en bloques finally de los DAO.
     *
     * @param connection la conexión a cerrar (puede ser null)
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
     * Útil para health checks del sistema.
     *
     * @return true si la base de datos responde correctamente
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
     * Lee una variable de entorno, retornando el valor por defecto si no existe.
     *
     * @param envVar       nombre de la variable de entorno
     * @param defaultValue valor por defecto si la variable no está definida
     * @return valor de la variable o el default
     */
    private static String getEnvOrDefault(String envVar, String defaultValue) {
        String value = System.getenv(envVar);
        if (value == null || value.trim().isEmpty()) {
            LOGGER.info("Variable de entorno '" + envVar + "' no definida. Usando valor por defecto.");
            return defaultValue;
        }
        return value.trim();
    }

    private static String getEnvOrFallback(String preferredVar, String fallbackVar,
                                           String defaultValue) {
        String preferred = System.getenv(preferredVar);
        if (preferred != null && !preferred.trim().isEmpty()) return preferred.trim();
        return getEnvOrDefault(fallbackVar, defaultValue);
    }

    // Getters de solo lectura (sin exponer contraseña)
    public String getDbHost()     { return DB_HOST; }
    public String getDbPort()     { return DB_PORT; }
    public String getDbName()     { return DB_NAME; }
    public String getDbUser()     { return DB_USER; }
    public String getJdbcUrl()    { return JDBC_URL; }
}
