package com.tdt.easyroute.Model;

public class Preventa {

    public String prev_folio_str;
    public long cli_cve_n;
    public int rut_cve_n;
    public String prev_fecha_dt;
    public int lpre_cve_n;
    public int dir_cve_n;
    public String usu_cve_str;
    public boolean prev_condicionada_n;
    public String prev_coordenada_str;
    public PreventaDet[] detalles;
    public PreventaDet[] Bebidas;

    public Preventa(String prev_folio_str, long cli_cve_n, int rut_cve_n, String prev_fecha_dt, int lpre_cve_n, int dir_cve_n, String usu_cve_str, boolean prev_condicionada_n, String prev_coordenada_str) {
        this.prev_folio_str = prev_folio_str;
        this.cli_cve_n = cli_cve_n;
        this.rut_cve_n = rut_cve_n;
        this.prev_fecha_dt = prev_fecha_dt;
        this.lpre_cve_n = lpre_cve_n;
        this.dir_cve_n = dir_cve_n;
        this.usu_cve_str = usu_cve_str;
        this.prev_condicionada_n = prev_condicionada_n;
        this.prev_coordenada_str = prev_coordenada_str;
    }

    public String getPrev_folio_str() {
        return prev_folio_str;
    }

    public void setPrev_folio_str(String prev_folio_str) {
        this.prev_folio_str = prev_folio_str;
    }

    public long getCli_cve_n() {
        return cli_cve_n;
    }

    public void setCli_cve_n(long cli_cve_n) {
        this.cli_cve_n = cli_cve_n;
    }

    public int getRut_cve_n() {
        return rut_cve_n;
    }

    public void setRut_cve_n(int rut_cve_n) {
        this.rut_cve_n = rut_cve_n;
    }

    public String getPrev_fecha_dt() {
        return prev_fecha_dt;
    }

    public void setPrev_fecha_dt(String prev_fecha_dt) {
        this.prev_fecha_dt = prev_fecha_dt;
    }

    public int getLpre_cve_n() {
        return lpre_cve_n;
    }

    public void setLpre_cve_n(int lpre_cve_n) {
        this.lpre_cve_n = lpre_cve_n;
    }

    public int getDir_cve_n() {
        return dir_cve_n;
    }

    public void setDir_cve_n(int dir_cve_n) {
        this.dir_cve_n = dir_cve_n;
    }

    public String getUsu_cve_str() {
        return usu_cve_str;
    }

    public void setUsu_cve_str(String usu_cve_str) {
        this.usu_cve_str = usu_cve_str;
    }

    public boolean isPrev_condicionada_n() {
        return prev_condicionada_n;
    }

    public void setPrev_condicionada_n(boolean prev_condicionada_n) {
        this.prev_condicionada_n = prev_condicionada_n;
    }

    public String getPrev_coordenada_str() {
        return prev_coordenada_str;
    }

    public void setPrev_coordenada_str(String prev_coordenada_str) {
        this.prev_coordenada_str = prev_coordenada_str;
    }

    public PreventaDet[] getDetalles() {
        return detalles;
    }

    public void setDetalles(PreventaDet[] detalles) {
        this.detalles = detalles;
    }

    public PreventaDet[] getBebidas() {
        return Bebidas;
    }

    public void setBebidas(PreventaDet[] bebidas) {
        Bebidas = bebidas;
    }
}
