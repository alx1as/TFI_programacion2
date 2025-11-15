/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author alexia
 */

import java.time.LocalDateTime;

public class IngresarSistema {
    private Integer idIngreso;
    private Integer idCredencial;
    private LocalDateTime fechaHoraIngreso;
    private String resultado;
    private Boolean eliminado;


    public IngresarSistema() {}

    public IngresarSistema(Integer idIngreso, Integer idCredencial, LocalDateTime fechaHoraIngreso, String resultado, Boolean eliminado) {
        this.idIngreso = idIngreso;
        this.idCredencial = idCredencial;
        this.fechaHoraIngreso = fechaHoraIngreso;
        this.resultado = resultado;
        this.eliminado = eliminado;
    }

    public Integer getIdIngreso() { return idIngreso; }
    public void setIdIngreso(Integer idIngreso) { this.idIngreso = idIngreso; }

    public Integer getIdCredencial() { return idCredencial; }
    public void setIdCredencial(Integer idCredencial) { this.idCredencial = idCredencial; }

    public LocalDateTime getFechaHoraIngreso() { return fechaHoraIngreso; }
    public void setFechaHoraIngreso(LocalDateTime fechaHoraIngreso) { this.fechaHoraIngreso = fechaHoraIngreso; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    
    public Boolean getEliminado() {
    return eliminado;
}

    public void setEliminado(Boolean eliminado) {
    this.eliminado = eliminado;
}
    @Override
    public String toString() {
        return "Ingreso [id=" + idIngreso + ", credencial=" + idCredencial + ", fechaHora=" + fechaHoraIngreso + ", resultado=" + resultado + "]";
    }
}
