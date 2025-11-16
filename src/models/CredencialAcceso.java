/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;



public class CredencialAcceso extends Base {

    private Integer idUsuario;        // FK
    private Date fechaCreacion;
    private String contrasenia;

    public CredencialAcceso(Integer id, Integer idUsuario, Date fechaCreacion, String contrasenia, boolean eliminado) {
        super(id, eliminado); // id es el id_credencial de la BD
        this.idUsuario = idUsuario;
        this.fechaCreacion = fechaCreacion;
        this.contrasenia = contrasenia;
    }

    public CredencialAcceso() {
        super();
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
public Integer getIdCredencial() {
    return getId();
}

public void setIdCredencial(Integer id) {
    setId(id);
}

    @Override
    public String toString() {
        return "CredencialAcceso [id=" + getId() + ", idUsuario=" + idUsuario + "]";
    }
}
