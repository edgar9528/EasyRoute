package com.tdt.easyroute.Model;

import java.io.Serializable;

public class Permisos implements Serializable {

    private int mod_cve_n;
    private String mod_desc_str;
    private byte lectura;
    private byte escritura;
    private byte modificacion;
    private byte eliminacion;

    public Permisos() {
    }

    public int getMod_cve_n() {
        return mod_cve_n;
    }

    public void setMod_cve_n(int mod_cve_n) {
        this.mod_cve_n = mod_cve_n;
    }

    public String getMod_desc_str() {
        return mod_desc_str;
    }

    public void setMod_desc_str(String mod_desc_str) {
        this.mod_desc_str = mod_desc_str;
    }

    public byte getLectura() {
        return lectura;
    }

    public void setLectura(byte lectura) {
        this.lectura = lectura;
    }

    public byte getEscritura() {
        return escritura;
    }

    public void setEscritura(byte escritura) {
        this.escritura = escritura;
    }

    public byte getModificacion() {
        return modificacion;
    }

    public void setModificacion(byte modificacion) {
        this.modificacion = modificacion;
    }

    public byte getEliminacion() {
        return eliminacion;
    }

    public void setEliminacion(byte eliminacion) {
        this.eliminacion = eliminacion;
    }
}
