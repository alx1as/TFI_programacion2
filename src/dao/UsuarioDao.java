package dao;

import config.DatabaseConnection;
import java.util.List;

import models.CredencialAcceso;
import models.Usuario;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 */

public class UsuarioDao implements GenericDao<Usuario> {
    // Insertar un usuario en la base de datos

    @Override
    public void crear(Usuario usuario) throws Exception {
        try (Connection conn = DatabaseConnection.conectar()) {
            crear(usuario, conn);
        }
    }

    /*
     * ====================================================
     * MÉTODO CREAR (CONEXIÓN EXTERNA → TRANSACCIÓN)
     * ====================================================
     */
    public void crear(Usuario usuario, Connection conn) throws Exception {
        String sql = "INSERT INTO usuarios (nombre, apellido, edad, email, usuario) VALUES (?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setInt(3, usuario.getEdad());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getUsuario());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
            }
        }
    }

    /*
     * ====================================================
     * MÉTODO LEER POR ID
     * ====================================================
     */
    public Usuario leer(Long id) throws Exception {
        String sql = "SELECT * FROM usuarios u LEFT JOIN credencialAcceso c ON c.id_usuario = u.id_usuario AND c.eliminado = FALSE WHERE u.id_usuario = ? AND u.eliminado = FALSE";
        Usuario u = null;

        try (Connection conn = DatabaseConnection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setEdad(rs.getInt("edad"));
                    u.setEmail(rs.getString("email"));
                    u.setUsuario(rs.getString("usuario"));

                    int credencialId = rs.getInt("id_credencial");
                    if (credencialId > 0 && !rs.wasNull()) {
                        CredencialAcceso credencialAcceso = new CredencialAcceso();
                        credencialAcceso.setIdCredencial(credencialId);
                        credencialAcceso.setIdUsuario(rs.getInt("id_usuario"));
                        credencialAcceso.setFechaCreacion(rs.getDate("fecha_creacion"));
                        credencialAcceso.setContrasenia(rs.getString("contrasenia"));
                        u.setCredencial(credencialAcceso);
                    }
                }
            }
        }
        return u;
    }

    /*
     * ====================================================
     * MÉTODO LEER TODOS
     * ====================================================
     */
    @Override
    public List<Usuario> leerTodos() throws Exception {
        String sql = "SELECT * FROM usuarios u LEFT JOIN credencialAcceso c ON c.id_usuario = u.id_usuario AND c.eliminado = FALSE WHERE u.eliminado = FALSE";

        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEdad(rs.getInt("edad"));
                u.setEmail(rs.getString("email"));
                u.setUsuario(rs.getString("usuario"));

                int credencialId = rs.getInt("id_credencial");
                if (credencialId > 0 && !rs.wasNull()) {
                    CredencialAcceso credencialAcceso = new CredencialAcceso();
                    credencialAcceso.setIdCredencial(credencialId);
                    credencialAcceso.setIdUsuario(rs.getInt("id_usuario"));
                    credencialAcceso.setFechaCreacion(rs.getDate("fecha_creacion"));
                    credencialAcceso.setContrasenia(rs.getString("contrasenia"));
                    u.setCredencial(credencialAcceso);
                }
                lista.add(u);
            }
        }
        return lista;
    }

    /*
     * ====================================================
     * MÉTODO ACTUALIZAR
     * ====================================================
     */
    @Override
    public void actualizar(Usuario usuario) throws Exception {
        String sql = "UPDATE usuarios SET nombre=?, apellido=?, edad=?, email=?, usuario=? WHERE id_usuario=?";

        try (Connection conn = DatabaseConnection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setInt(3, usuario.getEdad());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getUsuario());
            stmt.setInt(6, usuario.getIdUsuario());

            stmt.executeUpdate();
        }
    }

    /*
     * ====================================================
     * MÉTODO ELIMINAR (BAJA LÓGICA)
     * ====================================================
     */
    @Override
    public void eliminar(Long id) throws Exception {
        String sql = "UPDATE usuarios SET eliminado = TRUE WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    /*
     * ====================================================
     * MÉTODO RECUPERAR
     * ====================================================
     */
    @Override
    public void recuperar(Long id) throws Exception {
        String sql = "UPDATE usuarios SET eliminado = FALSE WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorUsuario(String campoUsuario) throws Exception {
        String sql = "SELECT * FROM usuarios u LEFT JOIN credencialAcceso c ON c.id_usuario = u.id_usuario AND c.eliminado = FALSE WHERE u.usuario = ? AND u.eliminado = FALSE";
        Usuario u = null;

        try (Connection conn = DatabaseConnection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, campoUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setEdad(rs.getInt("edad"));
                    u.setEmail(rs.getString("email"));
                    u.setUsuario(rs.getString("usuario"));

                    int credencialId = rs.getInt("id_credencial");
                    if (credencialId > 0 && !rs.wasNull()) {
                        CredencialAcceso credencialAcceso = new CredencialAcceso();
                        credencialAcceso.setIdCredencial(credencialId);
                        credencialAcceso.setIdUsuario(rs.getInt("id_usuario"));
                        credencialAcceso.setFechaCreacion(rs.getDate("fecha_creacion"));
                        credencialAcceso.setContrasenia(rs.getString("contrasenia"));
                        u.setCredencial(credencialAcceso);
                    }
                }
            }
        }
        return u;
    }

    public Usuario buscarPorEmail(String email) throws Exception {
        String sql = "SELECT * FROM usuarios u LEFT JOIN credencialAcceso c ON c.id_usuario = u.id_usuario AND c.eliminado = FALSE WHERE u.email = ? AND u.eliminado = FALSE";
        Usuario u = null;

        try (Connection conn = DatabaseConnection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setEdad(rs.getInt("edad"));
                    u.setEmail(rs.getString("email"));
                    u.setUsuario(rs.getString("usuario"));

                    int credencialId = rs.getInt("id_credencial");
                    if (credencialId > 0 && !rs.wasNull()) {
                        CredencialAcceso credencialAcceso = new CredencialAcceso();
                        credencialAcceso.setIdCredencial(credencialId);
                        credencialAcceso.setIdUsuario(rs.getInt("id_usuario"));
                        credencialAcceso.setFechaCreacion(rs.getDate("fecha_creacion"));
                        credencialAcceso.setContrasenia(rs.getString("contrasenia"));
                        u.setCredencial(credencialAcceso);
                    }
                }
            }
        }
        return u;
    }

    public List<Usuario> buscarPorNombreApellido(String filtro) throws Exception {
        String sql = "SELECT * FROM usuarios u LEFT JOIN credencialAcceso c ON c.id_usuario = u.id_usuario AND c.eliminado = FALSE WHERE u.nombre LIKE ? OR u.apellido LIKE ? AND u.eliminado = FALSE";

        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = DatabaseConnection.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + filtro + "%");
            stmt.setString(2, "%" + filtro + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setEdad(rs.getInt("edad"));
                    u.setEmail(rs.getString("email"));
                    u.setUsuario(rs.getString("usuario"));

                    int credencialId = rs.getInt("id_credencial");
                    if (credencialId > 0 && !rs.wasNull()) {
                        CredencialAcceso credencialAcceso = new CredencialAcceso();
                        credencialAcceso.setIdCredencial(credencialId);
                        credencialAcceso.setIdUsuario(rs.getInt("id_usuario"));
                        credencialAcceso.setFechaCreacion(rs.getDate("fecha_creacion"));
                        credencialAcceso.setContrasenia(rs.getString("contrasenia"));
                        u.setCredencial(credencialAcceso);
                    }
                    lista.add(u);
                }
            }
        }
        return lista;
    }
}
