/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


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
    public void setIdUsuario(Integer id) { setId(id); }
    public Integer getIdUsuario() { return getId(); }

    public void setIdCredencial(Integer id) { setId(id); }
public Integer getIdCredencial() { return getId(); }

    @Override
    public String toString() {
        return "Usuario [id=" + getId() + ", nombre=" + nombre + ", email=" + email + ", usuario=" + usuario + "]";
    }
}

