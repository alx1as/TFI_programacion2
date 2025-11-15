package dao;

import config.DatabaseConnection;
import models.IngresarSistema;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IngresarSistemaDao implements GenericDao<IngresarSistema> {

    /* ====================================================
       MÉTODO CREAR (CONEXIÓN INTERNA)
       ==================================================== */
    @Override
    public void crear(IngresarSistema ingreso) throws Exception {
        String sql = "INSERT INTO ingresarSistema (id_credencial, fecha_hora_ingreso, resultado) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ingreso.getIdCredencial());

            // LocalDateTime → java.sql.Timestamp
            stmt.setTimestamp(2, Timestamp.valueOf(ingreso.getFechaHoraIngreso()));

            stmt.setString(3, ingreso.getResultado());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) ingreso.setIdIngreso(rs.getInt(1));
            }
        }
    }

    /* ====================================================
       MÉTODO CREAR (CONEXIÓN EXTERNA → TRANSACCIÓN)
       ==================================================== */
    public void crear(IngresarSistema ingreso, Connection conn) throws Exception {
        String sql = "INSERT INTO ingresarSistema (id_credencial, fecha_hora_ingreso, resultado) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ingreso.getIdCredencial());
            stmt.setTimestamp(2, Timestamp.valueOf(ingreso.getFechaHoraIngreso()));
            stmt.setString(3, ingreso.getResultado());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) ingreso.setIdIngreso(rs.getInt(1));
            }
        }
    }


    /* ====================================================
       MÉTODO LEER POR ID
       ==================================================== */
    @Override
    public IngresarSistema leer(Long id) throws Exception {
        String sql = "SELECT * FROM ingresarSistema WHERE id_ingreso = ? AND eliminado = FALSE";

        IngresarSistema ing = null;

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    ing = new IngresarSistema();
                    ing.setIdIngreso(rs.getInt("id_ingreso"));
                    ing.setIdCredencial(rs.getInt("id_credencial"));

                    // Timestamp → LocalDateTime
                    ing.setFechaHoraIngreso(rs.getTimestamp("fecha_hora_ingreso").toLocalDateTime());

                    ing.setResultado(rs.getString("resultado"));
                    ing.setEliminado(rs.getBoolean("eliminado"));
                }
            }
        }

        return ing;
    }


    /* ====================================================
       MÉTODO LEER TODOS
       ==================================================== */
    @Override
    public List<IngresarSistema> leerTodos() throws Exception {
        String sql = "SELECT * FROM ingresarSistema WHERE eliminado = FALSE";

        List<IngresarSistema> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                IngresarSistema ing = new IngresarSistema();
                ing.setIdIngreso(rs.getInt("id_ingreso"));
                ing.setIdCredencial(rs.getInt("id_credencial"));

                ing.setFechaHoraIngreso(rs.getTimestamp("fecha_hora_ingreso").toLocalDateTime());

                ing.setResultado(rs.getString("resultado"));
                ing.setEliminado(rs.getBoolean("eliminado"));

                lista.add(ing);
            }
        }

        return lista;
    }


    /* ====================================================
       MÉTODO ACTUALIZAR
       ==================================================== */
    @Override
    public void actualizar(IngresarSistema ingreso) throws Exception {
        String sql = "UPDATE ingresarSistema SET id_credencial=?, fecha_hora_ingreso=?, resultado=? WHERE id_ingreso=?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ingreso.getIdCredencial());
            stmt.setTimestamp(2, Timestamp.valueOf(ingreso.getFechaHoraIngreso()));
            stmt.setString(3, ingreso.getResultado());
            stmt.setInt(4, ingreso.getIdIngreso());

            stmt.executeUpdate();
        }
    }


    /* ====================================================
       MÉTODO ELIMINAR (BAJA LÓGICA)
       ==================================================== */
    @Override
    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE ingresarSistema SET eliminado = TRUE WHERE id_ingreso = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }
}
