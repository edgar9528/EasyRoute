package com.tdt.easyroute.Model;

public class Visita {

    public long cli_cve_n;
    public String cli_cveext_str;
    public Venta[] ventas;
    public DataTableLC.Creditos[] creditos;
    public DataTableLC.Creditos[] creditose;
    public DataTableLC.Pagos[] pagos;
    public DataTableLC.Pagos[] cobranza;
    public DataTableLC.Pagos[] devenvase;
    public DataTableLC.Creditos[] credenv;

    public Visita() {
    }

    public Visita(long cli_cve_n, String cli_cveext_str) {
        this.cli_cve_n = cli_cve_n;
        this.cli_cveext_str = cli_cveext_str;
    }

    public long getCli_cve_n() {
        return cli_cve_n;
    }

    public void setCli_cve_n(long cli_cve_n) {
        this.cli_cve_n = cli_cve_n;
    }

    public String getCli_cveext_str() {
        return cli_cveext_str;
    }

    public void setCli_cveext_str(String cli_cveext_str) {
        this.cli_cveext_str = cli_cveext_str;
    }

    public Venta[] getVentas() {
        return ventas;
    }

    public void setVentas(Venta[] ventas) {
        this.ventas = ventas;
    }

    public DataTableLC.Creditos[] getCreditos() {
        return creditos;
    }

    public void setCreditos(DataTableLC.Creditos[] creditos) {
        this.creditos = creditos;
    }

    public DataTableLC.Creditos[] getCreditose() {
        return creditose;
    }

    public void setCreditose(DataTableLC.Creditos[] creditose) {
        this.creditose = creditose;
    }

    public DataTableLC.Pagos[] getPagos() {
        return pagos;
    }

    public void setPagos(DataTableLC.Pagos[] pagos) {
        this.pagos = pagos;
    }

    public DataTableLC.Pagos[] getCobranza() {
        return cobranza;
    }

    public void setCobranza(DataTableLC.Pagos[] cobranza) {
        this.cobranza = cobranza;
    }

    public DataTableLC.Pagos[] getDevenvase() {
        return devenvase;
    }

    public void setDevenvase(DataTableLC.Pagos[] devenvase) {
        this.devenvase = devenvase;
    }

    public DataTableLC.Creditos[] getCredenv() {
        return credenv;
    }

    public void setCredenv(DataTableLC.Creditos[] credenv) {
        this.credenv = credenv;
    }
}
