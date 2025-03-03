package repository;

import config.CreateTable;
import config.DatabaseConfig;
import java.sql.*;
import java.util.Map;

public class DatabaseRepository {
    CreateTable createTable = new CreateTable();
    public void insertar(String tabla, Map<String, Object> datos) throws SQLException {
        if (!createTable.verificarTablaExiste(tabla)) {
            System.err.println("La tabla " + tabla + " no existe. Creándola...");
            createTable.crearTabla(tabla);
        }
        StringBuilder columnas = new StringBuilder();
        StringBuilder valores = new StringBuilder();
        for (String columna : datos.keySet()) {
            columnas.append(columna).append(", ");
            valores.append("?, ");
        }
        columnas.setLength(columnas.length() - 2);
        valores.setLength(valores.length() - 2);
        String sql = "INSERT INTO " + tabla + " (" + columnas + ") VALUES (" + valores + ")";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object valor : datos.values()) {
                stmt.setObject(index++, valor);
            }
            stmt.executeUpdate();
            System.out.println("Datos insertados correctamente en la tabla " + tabla);
        } catch (SQLException e) {
            System.err.println("Error al insertar datos en la tabla " + tabla + ": " + e.getMessage());
            throw new SQLException("No se pudo insertar datos en la tabla " + tabla, e);
        }
    }

    public void actualizar(String tabla, Map<String, Object> datos, String condicion) throws SQLException {
        StringBuilder setClause = new StringBuilder();
        for (String columna : datos.keySet()) {
            setClause.append(columna).append(" = ?, ");
        }
        setClause.setLength(setClause.length() - 2);
        String sql = "UPDATE " + tabla + " SET " + setClause + " WHERE " + condicion;
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object valor : datos.values()) {
                stmt.setObject(index++, valor);
            }
            stmt.executeUpdate();
            System.out.println("Datos actualizados correctamente en la tabla " + tabla);
        } catch (SQLException e) {
            System.err.println("Error al actualizar datos en la tabla " + tabla + ": " + e.getMessage());
            throw new SQLException("No se pudo actualizar los datos en la tabla " + tabla, e);
        }
    }

    public void eliminar(String tabla, String condicion) throws SQLException {
        String sql = "DELETE FROM " + tabla + " WHERE " + condicion;
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("Datos eliminados correctamente de la tabla " + tabla);
        } catch (SQLException e) {
            System.err.println("Error al eliminar datos en la tabla " + tabla + ": " + e.getMessage());
            throw new SQLException("No se pudo eliminar los datos en la tabla " + tabla, e);
        }
    }

    public ResultSet buscar(String tabla, String condicion) throws SQLException {
        String sql = "SELECT * FROM " + tabla + " WHERE " + condicion;
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            return stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error al buscar datos en la tabla " + tabla + ": " + e.getMessage());
            throw new SQLException("No se pudo buscar los datos en la tabla " + tabla, e);
        }
    }

}
