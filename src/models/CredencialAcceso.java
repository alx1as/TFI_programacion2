/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author alexia
 */
import java.time.LocalDate;

public class CredencialAcceso {
    private Integer idCredencial;
    private Integer idUsuario;
    private LocalDate fechaCreacion;
    private String contrasenia;
    private Boolean eliminado;


    public CredencialAcceso() {}

    public CredencialAcceso(Integer idCredencial, Integer idUsuario, LocalDate fechaCreacion, String contrasenia, Boolean eliminado) {
        this.idCredencial = idCredencial;
        this.idUsuario = idUsuario;
        this.fechaCreacion = fechaCreacion;
        this.contrasenia = contrasenia;
        this.eliminado = eliminado;
    }

    public Integer getIdCredencial() { return idCredencial; }
    public void setIdCredencial(Integer idCredencial) { this.idCredencial = idCredencial; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    public Boolean getEliminado() {
    return eliminado;
}

    public void setEliminado(Boolean eliminado) {
    this.eliminado = eliminado;
}
    @Override
    public String toString() {
        return "CredencialAcceso [id=" + idCredencial + ", idUsuario=" + idUsuario + "]";
    }
}
