package config;

/**
 *
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/usuariocredencial";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = ""; // Cambiar el password segun el caso

    public static Connection conectar() {
        try {
            /*
             * DriverManager.getConnection(...) establece la conexión la base de datos,
             * necesita .jar
             */
            return DriverManager.getConnection(URL, USUARIO, CONTRASENIA); // abre la conexión
        } catch (SQLException e) {
            /*
             * método de la clase SQLException-> devuelve un mensaje de error asociado a la
             * excepción que ocurrió
             */
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) { // método de prueba opcional
        Connection conn = conectar();
        if (conn != null) {
            System.out.println("Conexión exitosa.");
        } else {
            System.out.println("Conexión fallida.");
        }
    }
}
