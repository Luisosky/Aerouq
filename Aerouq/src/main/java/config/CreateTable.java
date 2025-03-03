package config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public boolean verificarTablaExiste(String tabla) throws SQLException {
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tabla);
            return stmt.executeQuery().next();
        }
    }

    public void crearTabla(String tabla) throws SQLException {
        String sql = switch (tabla) {
            case "Profesores" -> "CREATE TABLE Profesores (id INT PRIMARY KEY IDENTITY(1,1), nombre VARCHAR(50) NOT NULL, apellido VARCHAR(50) NOT NULL, especialidad VARCHAR(100) NOT NULL)";
            case "Cursos" -> "CREATE TABLE Cursos (id INT PRIMARY KEY IDENTITY(1,1), nombre VARCHAR(100) NOT NULL, id_profesor INT, FOREIGN KEY (id_profesor) REFERENCES Profesores(id))";
            case "Estudiantes" -> "CREATE TABLE Estudiantes (id INT PRIMARY KEY IDENTITY(1,1), nombre VARCHAR(50) NOT NULL, apellido VARCHAR(50) NOT NULL, edad INT NOT NULL, grado VARCHAR(10) NOT NULL, id_curso INT, FOREIGN KEY (id_curso) REFERENCES Cursos(id))";
            case "Matriculas" -> "CREATE TABLE Matriculas (id INT PRIMARY KEY IDENTITY(1,1), id_estudiante INT, id_curso INT, fecha DATE NOT NULL, FOREIGN KEY (id_estudiante) REFERENCES Estudiantes(id), FOREIGN KEY (id_curso) REFERENCES Cursos(id))";
            case "Notas" -> "CREATE TABLE Notas (id INT PRIMARY KEY IDENTITY(1,1), id_estudiante INT, id_curso INT, nota DECIMAL(4,2) NOT NULL, fecha DATE NOT NULL, FOREIGN KEY (id_estudiante) REFERENCES Estudiantes(id), FOREIGN KEY (id_curso) REFERENCES Cursos(id))";
            case "Usuarios" ->"CREATE TABLE Usuarios (id INT PRIMARY KEY IDENTITY(1,1), username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL)";
            default -> throw new SQLException("No hay una estructura definida para la tabla " + tabla);
        };
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabla " + tabla + " creada correctamente.");
        }
    }
}
