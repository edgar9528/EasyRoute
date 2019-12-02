package com.tdt.easyroute.Model;

public class VisitaPrev {

    public long cli_cve_n;
    public String cli_cveext_str;
    public Preventa[] Preventas;
    public PreventaEnvase[] Envase;
    public Creditos[] Creditos;
    public PreventaPagos[] Cobranza;
    public Creditos[] CreditosEnv;

    public VisitaPrev() {
    }

    public VisitaPrev(long cli_cve_n, String cli_cveext_str) {
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

    public Preventa[] getPreventas() {
        return Preventas;
    }

    public void setPreventas(Preventa[] preventas) {
        Preventas = preventas;
    }

    public PreventaEnvase[] getEnvase() {
        return Envase;
    }

    public void setEnvase(PreventaEnvase[] envase) {
        Envase = envase;
    }

    public com.tdt.easyroute.Model.Creditos[] getCreditos() {
        return Creditos;
    }

    public void setCreditos(com.tdt.easyroute.Model.Creditos[] creditos) {
        Creditos = creditos;
    }

    public PreventaPagos[] getCobranza() {
        return Cobranza;
    }

    public void setCobranza(PreventaPagos[] cobranza) {
        Cobranza = cobranza;
    }

    public com.tdt.easyroute.Model.Creditos[] getCreditosEnv() {
        return CreditosEnv;
    }

    public void setCreditosEnv(com.tdt.easyroute.Model.Creditos[] creditosEnv) {
        CreditosEnv = creditosEnv;
    }
}
