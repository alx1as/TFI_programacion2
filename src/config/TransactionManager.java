package config;
 /*
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 * */

import java.sql.Connection;
import java.sql.SQLException;

/* administra transacciones jdbc: inicio, commit, rollback y cierre seguro */
public class TransactionManager implements AutoCloseable {

    private Connection conn;                 // conexión activa a la bd
    private boolean transactionActive;       // indica si hay una transacción en curso

    public TransactionManager(Connection conn) throws SQLException {
        if (conn == null) {                 // evita crear el manager sin conexión
            throw new IllegalArgumentException("la conexión no puede ser null");
        }
        this.conn = conn;                   // guarda la conexión
        this.transactionActive = false;     // al inicio no hay transacción activa
    }

    public Connection getConnection() {
        return conn;                        // expone la conexión al dao o service
    }

    public void startTransaction() throws SQLException {
        if (conn == null) {                 // si no hay conexión, no se puede iniciar
            throw new SQLException("no se puede iniciar transacción: conexión no disponible");
        }
        if (conn.isClosed()) {              // si está cerrada, tampoco
            throw new SQLException("no se puede iniciar transacción: conexión cerrada");
        }
        conn.setAutoCommit(false);          // desactiva autocommit para controlar cambios
        transactionActive = true;           // marca que empezó una transacción
    }

    public void commit() throws SQLException {
        if (conn == null) {                 // valida conexión
            throw new SQLException("error al hacer commit: no hay conexión");
        }
        if (!transactionActive) {           // commit solo si hay transacción activa
            throw new SQLException("no hay una transacción activa para hacer commit");
        }
        conn.commit();                      // confirma cambios en la bd
        transactionActive = false;          // ya no hay transacción activa
    }

    public void rollback() {
        if (conn != null && transactionActive) {    // rollback solo si procede
            try {
                conn.rollback();                    // revierte los cambios
                transactionActive = false;          // termina la transacción
            } catch (SQLException e) {
                System.err.println("error durante rollback: " + e.getMessage());
            }
        }
    }

    @Override
    public void close() {
        if (conn != null) {                         // cuando termina el bloque try-with-resources
            try {
                if (transactionActive) {            // si quedó activa, se revierte
                    rollback();
                }
                conn.setAutoCommit(true);           // restaura el modo por defecto
                conn.close();                       // cierra la conexión
            } catch (SQLException e) {
                System.err.println("error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public boolean isTransactionActive() {
        return transactionActive;                   // permite verificar el estado
    }
}
