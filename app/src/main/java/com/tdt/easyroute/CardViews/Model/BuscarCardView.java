package com.tdt.easyroute.CardViews.Model;

public class BuscarCardView {

    private String idExt;
    private String cli_cve_n;
    private String negocio;
    private String razonSocial;
    int indice;

    public BuscarCardView(String idExt, String negocio, String razonSocial, int indice, String cli_cve_n) {
        this.idExt = idExt;
        this.negocio = negocio;
        this.razonSocial = razonSocial;
        this.indice = indice;
        this.cli_cve_n = cli_cve_n;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public String getIdExt() {
        return idExt;
    }

    public void setIdExt(String idExt) {
        this.idExt = idExt;
    }

    public String getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCli_cve_n() {
        return cli_cve_n;
    }

    public void setCli_cve_n(String cli_cve_n) {
        this.cli_cve_n = cli_cve_n;
    }
}
