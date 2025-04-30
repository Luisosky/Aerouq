package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConfig {
    private static final String URL = "jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true";
    private static final String DATABASE_NAME = "AeroUQ";
    private static final String USER = "tu_usuario";  // Considera usar variables de entorno o un archivo de configuración
    private static final String PASSWORD = "tu_contraseña";  // Considera usar variables de entorno o un archivo de configuración

    // Método para inicializar la base de datos
    public static void inicializarBaseDatos() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Verificar si la base de datos existe, si no, crearla
            String sql = "IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = '" + DATABASE_NAME + "') " +
                    "BEGIN CREATE DATABASE " + DATABASE_NAME + " END";
            stmt.executeUpdate(sql);
            System.out.println("Base de datos verificada/creada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            throw new SQLException("No se pudo crear/verificar la base de datos", e);
        }
    }

    // Método para obtener la conexión a la base de datos
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL + ";databaseName=" + DATABASE_NAME, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            throw new SQLException("No se pudo conectar a la base de datos", e);
        }
    }
}
