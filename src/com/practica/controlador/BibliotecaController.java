package com.practica.controlador;

import com.practica.modelo.Autor;
import com.practica.modelo.Libro;
import com.practica.modelo.Prestamo;
import com.practica.utils.DatabaseConfig;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BibliotecaController {
    // Métodos para Autor
    public void agregarAutor(Autor autor) throws SQLException {
        String sql = "INSERT INTO autor (idAutor, nombre, nacionalidad, anioNacimiento) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, autor.getId());
            stmt.setString(2, autor.getNombre());
            stmt.setString(3, autor.getNacionalidad());
            stmt.setInt(4, autor.getAnioNacimiento());
            stmt.executeUpdate();
        }
    }

    public Autor buscarAutor(String id) throws SQLException {
        String sql = "SELECT * FROM autor WHERE idAutor = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Autor(
                        rs.getString("idAutor"),
                        rs.getString("nombre"),
                        rs.getString("nacionalidad"),
                        rs.getInt("anioNacimiento")
                    );
                }
            }
        }
        return null;
    }

    public List<Autor> listarAutores() throws SQLException {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM autor";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                autores.add(new Autor(
                    rs.getString("idAutor"),
                    rs.getString("nombre"),
                    rs.getString("nacionalidad"),
                    rs.getInt("anioNacimiento")
                ));
            }
        }
        return autores;
    }

    // Métodos para Libro
    public void agregarLibro(Libro libro) throws SQLException {
        String sql = "INSERT INTO libro (isbn, titulo, idAutor, anioPublicacion, disponible) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, libro.getIsbn());
            stmt.setString(2, libro.getTitulo());
            stmt.setString(3, libro.getAutor().getId());
            stmt.setInt(4, libro.getAnioPublicacion());
            stmt.setBoolean(5, libro.isDisponible());
            stmt.executeUpdate();
        }
    }

    public Libro buscarLibro(String isbn) throws SQLException {
        String sql = "SELECT * FROM libro WHERE isbn = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Autor autor = buscarAutor(rs.getString("idAutor"));
                    return new Libro(
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        autor,
                        rs.getInt("anioPublicacion")
                    );
                }
            }
        }
        return null;
    }

    public List<Libro> listarLibros() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libro";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Autor autor = buscarAutor(rs.getString("idAutor"));
                Libro libro = new Libro(
                    rs.getString("isbn"),
                    rs.getString("titulo"),
                    autor,
                    rs.getInt("anioPublicacion")
                );
                libro.setDisponible(rs.getBoolean("disponible"));
                libros.add(libro);
            }
        }
        return libros;
    }

    // Métodos para Préstamo
    public boolean prestarLibro(String idPrestamo, String isbn, String nombreUsuario, java.util.Date hoy, java.util.Date devolucion) throws SQLException {
        Libro libro = buscarLibro(isbn);
        if (libro != null && libro.isDisponible()) {
            String sql = "INSERT INTO prestamo (idPrestamo, isbn, nombreUsuario, fechaPrestamo, fechaDevolucion) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, idPrestamo);
                stmt.setString(2, isbn);
                stmt.setString(3, nombreUsuario);
                stmt.setDate(4, new java.sql.Date(hoy.getTime()));
                stmt.setDate(5, new java.sql.Date(devolucion.getTime()));
                stmt.executeUpdate();
            }
            // Marcar libro como no disponible
            String updateSql = "UPDATE libro SET disponible = false WHERE isbn = ?";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setString(1, isbn);
                stmt.executeUpdate();
            }
            return true;
        }
        return false;
    }

    public boolean devolverLibro(String idPrestamo) throws SQLException {
        String sql = "SELECT isbn FROM prestamo WHERE idPrestamo = ?";
        String isbn = null;
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPrestamo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    isbn = rs.getString("isbn");
                }
            }
        }
        if (isbn != null) {
            // Eliminar el préstamo
            String deleteSql = "DELETE FROM prestamo WHERE idPrestamo = ?";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setString(1, idPrestamo);
                stmt.executeUpdate();
            }
            // Marcar libro como disponible
            String updateSql = "UPDATE libro SET disponible = true WHERE isbn = ?";
            try (Connection conn = DatabaseConfig.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setString(1, isbn);
                stmt.executeUpdate();
            }
            return true;
        }
        return false;
    }

    public Prestamo buscarPrestamo(String idPrestamo) throws SQLException {
        String sql = "SELECT * FROM prestamo WHERE idPrestamo = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPrestamo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Libro libro = buscarLibro(rs.getString("isbn"));
                    return new Prestamo(
                        rs.getString("idPrestamo"),
                        libro,
                        rs.getString("nombreUsuario"),
                        rs.getDate("fechaPrestamo"),
                        rs.getDate("fechaDevolucion")
                    );
                }
            }
        }
        return null;
    }

    public List<Prestamo> listarPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM prestamo";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Libro libro = buscarLibro(rs.getString("isbn"));
                Prestamo prestamo = new Prestamo(
                    rs.getString("idPrestamo"),
                    libro,
                    rs.getString("nombreUsuario"),
                    rs.getDate("fechaPrestamo"),
                    rs.getDate("fechaDevolucion")
                );
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }
}