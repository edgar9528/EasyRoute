package com.tdt.easyroute.Ventanas.Clientes.OrdenaCliente;

import java.io.Serializable;

public class CamposEditar implements Serializable {

    private String estado;
    private String frecuencia;
    private String coordenadas;
    private String frecuencia_desc;

    public CamposEditar() {
    }

    public CamposEditar(String estado, String frecuencia, String coordenadas) {
        this.estado = estado;
        this.frecuencia = frecuencia;
        this.coordenadas = coordenadas;
    }

    public String getFrecuencia_desc() {
        return frecuencia_desc;
    }

    public void setFrecuencia_desc(String frecuencia_desc) {
        this.frecuencia_desc = frecuencia_desc;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
