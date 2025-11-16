/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

/**
 *
 * @author alexia
 */

public class CredencialAcceso extends Base {

    private Integer idCredencial;
    private Integer idUsuario;
    private Date fechaCreacion;
    private String contrasenia;
    private Boolean eliminado;

    public CredencialAcceso(Integer idCredencial, Integer idUsuario,
            Date fechaCreacion,
            String contrasenia) {
        super(idCredencial, false);
        this.idUsuario = idUsuario;
        this.fechaCreacion = fechaCreacion;
        this.contrasenia = contrasenia;
    }

    public CredencialAcceso() {
        super();
    }

    public Integer getIdCredencial() {
        return idCredencial;
    }

    public void setIdCredencial(Integer idCredencial) {
        this.idCredencial = idCredencial;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

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
