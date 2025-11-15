/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;



/**
 *
 * @author alexia
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class TestLectura {
    public static void main(String[] args) {

        Connection conn = DatabaseConnection.conectar();

        if (conn == null) {
            System.out.println("No se pudo conectar.");
            return;
        }

        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id_usuario") + " | " +
                    rs.getString("nombre") + " | " +
                    rs.getString("apellido")
                );
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
