package com.tdt.easyroute.Fragments.Clientes.OrdenaCliente;

import java.io.Serializable;

public class CamposEditar implements Serializable {

    private String estado;
    private String frecuencia;
    private String coordenadas;

    public CamposEditar() {
    }

    public CamposEditar(String estado, String frecuencia, String coordenadas) {
        this.estado = estado;
        this.frecuencia = frecuencia;
        this.coordenadas = coordenadas;
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
