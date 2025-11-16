package dao;

import config.DatabaseConnection;
import models.CredencialAcceso;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CredencialAccesoDao implements GenericDao<CredencialAcceso> {

    /* ====================================================
       MÉTODO CREAR (CONEXIÓN INTERNA)
       ==================================================== */
    @Override
    public void crear(CredencialAcceso cred) throws Exception {
        String sql = "INSERT INTO credencialAcceso (id_usuario, fecha_creacion, contrasenia) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cred.getIdUsuario());

            // LocalDate → java.sql.Date
            stmt.setDate(2, new java.sql.Date(cred.getFechaCreacion().getTime()));

            stmt.setString(3, cred.getContrasenia());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) cred.setIdCredencial(rs.getInt(1));
            }
        }
    }

    /* ====================================================
       MÉTODO CREAR (CONEXIÓN EXTERNA → TRANSACCIÓN)
       ==================================================== */
    public void crear(CredencialAcceso cred, Connection conn) throws Exception {
        String sql = "INSERT INTO credencialAcceso (id_usuario, fecha_creacion, contrasenia) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cred.getIdUsuario());

            // LocalDate → java.sql.Date
            stmt.setDate(2,  new java.sql.Date(cred.getFechaCreacion().getTime()));

            stmt.setString(3, cred.getContrasenia());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) cred.setIdCredencial(rs.getInt(1));
            }
        }
    }


    /* ====================================================
       MÉTODO LEER POR ID
       ==================================================== */
    @Override
    public CredencialAcceso leer(Long id) throws Exception {
        String sql = "SELECT * FROM credencialAcceso WHERE id_credencial = ? AND eliminado = FALSE";
        CredencialAcceso c = null;

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    c = new CredencialAcceso();
                    c.setIdCredencial(rs.getInt("id_credencial"));
                    c.setIdUsuario(rs.getInt("id_usuario"));

                    // java.sql.Date → LocalDate
                    c.setFechaCreacion(rs.getDate("fecha_creacion"));

                    c.setContrasenia(rs.getString("contrasenia"));
                    c.setEliminado(rs.getBoolean("eliminado"));
                }
            }
        }
        return c;
    }


    /* ====================================================
       MÉTODO LEER TODOS
       ==================================================== */
    @Override
    public List<CredencialAcceso> leerTodos() throws Exception {
        String sql = "SELECT * FROM credencialAcceso WHERE eliminado = FALSE";
        List<CredencialAcceso> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CredencialAcceso c = new CredencialAcceso();
                c.setIdCredencial(rs.getInt("id_credencial"));
                c.setIdUsuario(rs.getInt("id_usuario"));

                // java.sql.Date → LocalDate
                c.setFechaCreacion(rs.getDate("fecha_creacion"));

                c.setContrasenia(rs.getString("contrasenia"));
                c.setEliminado(rs.getBoolean("eliminado"));

                lista.add(c);
            }
        }
        return lista;
    }


    /* ====================================================
       MÉTODO ACTUALIZAR
       ==================================================== */
    @Override
    public void actualizar(CredencialAcceso cred) throws Exception {
        String sql = "UPDATE credencialAcceso SET id_usuario=?, fecha_creacion=?, contrasenia=? WHERE id_credencial=?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cred.getIdUsuario());

            // LocalDate → java.sql.Date
            stmt.setDate(2,  new java.sql.Date(cred.getFechaCreacion().getTime()));

            stmt.setString(3, cred.getContrasenia());
            stmt.setInt(4, cred.getIdCredencial());

            stmt.executeUpdate();
        }
    }


    /* ====================================================
       MÉTODO ELIMINAR (BAJA LÓGICA)
       ==================================================== */
    @Override
    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE credencialAcceso SET eliminado = TRUE WHERE id_credencial = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
    /* ====================================================
       MÉTODO RECUPERAR 
       ==================================================== */
    
    @Override
    public void recuperar (Long id) throws Exception {
        String sql = "UPDATE credencialAcceso SET eliminado = FALSE WHERE id_credencial = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
