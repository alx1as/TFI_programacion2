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
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/usuariocredencial";
    private static final String USUARIO = "root"; 
    private static final String CONTRASENIA = "1234"; //Cambiar  el password segun el caso

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CONTRASENIA); 
            /*DriverManager.getConnection(...) establece la conexión la base de datos, necesita .jar*/  
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage()); /*método de la clase SQLException-> devuelve un mensaje de error asociado a la excepción que ocurrió */
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = conectar();
        if (conn != null) {
            System.out.println("Conexión exitosa.");
        } else {
            System.out.println("Conexión fallida.");
        }
    }
}
