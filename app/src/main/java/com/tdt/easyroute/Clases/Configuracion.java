package com.tdt.easyroute.Clases;

public class Configuracion {

    private int id;
    private int ruta;
    private int preventa;
    private String usuario;
    private boolean descarga;
    private boolean auditoria;

    public Configuracion()
    {
        id = -1;
        ruta = -1;
        preventa = -1;
        usuario = "";
        descarga = false;
        auditoria = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRuta() {
        return ruta;
    }

    public void setRuta(int ruta) {
        this.ruta = ruta;
    }

    public int getPreventa() {
        return preventa;
    }

    public void setPreventa(int preventa) {
        this.preventa = preventa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isDescarga() {
        return descarga;
    }

    public void setDescarga(boolean descarga) {
        this.descarga = descarga;
    }

    public boolean isAuditoria() {
        return auditoria;
    }

    public void setAuditoria(boolean auditoria) {
        this.auditoria = auditoria;
    }
}
