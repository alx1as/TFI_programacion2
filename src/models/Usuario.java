package models;

/**
 *
 * @author Facundo Auciello (Comisión Ag25-2C 07)
 * @author Ayelen Etchegoyen (Comisión Ag25-2C 07)
 * @author Alexia Rubin (Comisión Ag25-2C 05)
 * @author María Victoria Volpe (Comisión Ag25-2C 09)
 */

public class Usuario extends Base {

    private String nombre;
    private String apellido;
    private Integer edad;
    private String email;
    private String usuario;
    private CredencialAcceso credencial; // relación 1:1

    public Usuario(String nombre, String apellido, Integer edad, String email, String usuario) {
        super(); // id lo asigna Base.java
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.email = email;
        this.usuario = usuario;
    }

    public Usuario() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public CredencialAcceso getCredencial() {
        return credencial;
    }

    public void setCredencial(CredencialAcceso credencial) {
        this.credencial = credencial;
    }

    public void setIdUsuario(Integer id) {
        setId(id);
    }

    public Integer getIdUsuario() {
        return getId();
    }

    @Override
    public String toString() {
        return "Usuario [id=" + getId() + ", nombre=" + nombre + ", email=" + email + ", usuario=" + usuario + "]";
    }
}
