package com.tdt.easyroute.Model;

public class VisitaPrev
{
    public long cli_cve_n;
    public String cli_cveext_str;
    public Preventa[] Preventas;
    public DataTableWS.PreventaEnv[] Envase;
    public DataTableLC.Creditos[] Creditos;
    public DataTableLC.PreventaPagos[] Cobranza;
    public DataTableLC.Creditos[] CreditosEnv;

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

    public DataTableWS.PreventaEnv[] getEnvase() {
        return Envase;
    }

    public void setEnvase(DataTableWS.PreventaEnv[] envase) {
        Envase = envase;
    }

    public DataTableLC.Creditos[] getCreditos() {
        return Creditos;
    }

    public void setCreditos(DataTableLC.Creditos[] creditos) {
        Creditos = creditos;
    }

    public DataTableLC.PreventaPagos[] getCobranza() {
        return Cobranza;
    }

    public void setCobranza(DataTableLC.PreventaPagos[] cobranza) {
        Cobranza = cobranza;
    }

    public DataTableLC.Creditos[] getCreditosEnv() {
        return CreditosEnv;
    }

    public void setCreditosEnv(DataTableLC.Creditos[] creditosEnv) {
        CreditosEnv = creditosEnv;
    }
}
