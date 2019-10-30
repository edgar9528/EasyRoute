package com.tdt.easyroute.ModelTablas;

public class InfoRuta {

    private String rut_cve_n;
    private String rut_desc_str;
    private String rut_orden_n;
    private String trut_cve_n;
    private String asesor_cve_str;
    private String gerente_cve_str;
    private String supervisor_cve_str;
    private String est_cve_str;
    private String tco_cve_n;
    private String rut_prev_n;
    private String rut_capcamion_n;
    private String rut_idcteesp_n;
    private String rut_tel_str;
    private String rut_invalidafrecuencia_n;
    private String rut_productividad_n;
    private String rut_efectividad_n;
    private String rut_mayorista_n;
    private String rut_fiestasyeventos_n;
    private String rut_auditoria_n;
    private String rut_promoce_n;


    public InfoRuta() {

        rut_cve_n="null";
        rut_desc_str="null";
        rut_orden_n="null";
        trut_cve_n="null";
        asesor_cve_str="null";
        gerente_cve_str="null";
        supervisor_cve_str="null";
        est_cve_str="null";
        tco_cve_n="null";
        rut_prev_n="null";
        rut_capcamion_n="null";
        rut_idcteesp_n="null";
        rut_tel_str="null";
        rut_invalidafrecuencia_n="null";
        rut_productividad_n="null";
        rut_efectividad_n="null";
        rut_mayorista_n="null";
        rut_fiestasyeventos_n="null";
        rut_auditoria_n="null";
        rut_promoce_n="null";
        
    }

    public String getTco_cve_n() {
        return tco_cve_n;
    }

    public void setTco_cve_n(String tco_cve_n) {
        if(tco_cve_n.contains("anyType{}"))
            this.tco_cve_n = "";
        else
            this.tco_cve_n = tco_cve_n;
    }

    public String getRut_cve_n() {
        return rut_cve_n;
    }

    public void setRut_cve_n(String rut_cve_n) {
        if(rut_cve_n.contains("anyType{}"))
            this.rut_cve_n = "";
        else
            this.rut_cve_n = rut_cve_n;
    }

    public String getRut_desc_str() {
        return rut_desc_str;
    }

    public void setRut_desc_str(String rut_desc_str) {
        if(rut_desc_str.contains("anyType{}"))
            this.rut_desc_str = "";
        else
            this.rut_desc_str = rut_desc_str;
    }

    public String getRut_orden_n() {
        return rut_orden_n;
    }

    public void setRut_orden_n(String rut_orden_n) {
        if(rut_orden_n.contains("anyType{}"))
            this.rut_orden_n = "";
        else
            this.rut_orden_n = rut_orden_n;
    }

    public String getTrut_cve_n() {
        return trut_cve_n;
    }

    public void setTrut_cve_n(String trut_cve_n) {
        if(trut_cve_n.contains("anyType{}"))
            this.trut_cve_n = "";
        else
            this.trut_cve_n = trut_cve_n;
    }

    public String getAsesor_cve_str() {
        return asesor_cve_str;
    }

    public void setAsesor_cve_str(String asesor_cve_str) {
        if(asesor_cve_str.contains("anyType{}"))
            this.asesor_cve_str = "";
        else
            this.asesor_cve_str = asesor_cve_str;
    }

    public String getGerente_cve_str() {
        return gerente_cve_str;
    }

    public void setGerente_cve_str(String gerente_cve_str) {
        if(gerente_cve_str.contains("anyType{}"))
            this.gerente_cve_str = "";
        else
            this.gerente_cve_str = gerente_cve_str;
    }

    public String getSupervisor_cve_str() {
        return supervisor_cve_str;
    }

    public void setSupervisor_cve_str(String supervisor_cve_str) {
        if(supervisor_cve_str.contains("anyType{}"))
            this.supervisor_cve_str = "";
        else
            this.supervisor_cve_str = supervisor_cve_str;
    }

    public String getEst_cve_str() {
        return est_cve_str;
    }

    public void setEst_cve_str(String est_cve_str) {
        if(est_cve_str.contains("anyType{}"))
            this.est_cve_str = "";
        else
            this.est_cve_str = est_cve_str;
    }

    public String getRut_prev_n() {
        return rut_prev_n;
    }

    public void setRut_prev_n(String rut_prev_n) {
        if(rut_prev_n.contains("anyType{}"))
            this.rut_prev_n = "";
        else
            this.rut_prev_n = rut_prev_n;
    }

    public String getRut_capcamion_n() {
        return rut_capcamion_n;
    }

    public void setRut_capcamion_n(String rut_capcamion_n) {
        if(rut_capcamion_n.contains("anyType{}"))
            this.rut_capcamion_n = "";
        else
            this.rut_capcamion_n = rut_capcamion_n;
    }

    public String getRut_idcteesp_n() {
        return rut_idcteesp_n;
    }

    public void setRut_idcteesp_n(String rut_idcteesp_n) {
        if(rut_idcteesp_n.contains("anyType{}"))
            this.rut_idcteesp_n = "";
        else
            this.rut_idcteesp_n = rut_idcteesp_n;

    }

    public String getRut_tel_str() {
        return rut_tel_str;
    }

    public void setRut_tel_str(String rut_tel_str) {
        if(rut_tel_str.contains("anyType{}"))
            this.rut_tel_str = "";
        else
            this.rut_tel_str = rut_tel_str;
    }

    public String getRut_invalidafrecuencia_n() {
        return rut_invalidafrecuencia_n;
    }

    public void setRut_invalidafrecuencia_n(String rut_invalidafrecuencia_n) {
        if(rut_invalidafrecuencia_n.contains("anyType{}"))
            this.rut_invalidafrecuencia_n = "";
        else
            this.rut_invalidafrecuencia_n = rut_invalidafrecuencia_n;
    }

    public String getRut_productividad_n() {
        return rut_productividad_n;
    }

    public void setRut_productividad_n(String rut_productividad_n) {
        if(rut_productividad_n.contains("anyType{}"))
            this.rut_productividad_n = "";
        else
            this.rut_productividad_n = rut_productividad_n;
    }

    public String getRut_efectividad_n() {
        return rut_efectividad_n;
    }

    public void setRut_efectividad_n(String rut_efectividad_n) {
        this.rut_efectividad_n = rut_efectividad_n;
    }

    public String getRut_mayorista_n() {
        return rut_mayorista_n;
    }

    public void setRut_mayorista_n(String rut_mayorista_n) {
        if(rut_mayorista_n.contains("anyType{}"))
            this.rut_mayorista_n = "";
        else
            this.rut_mayorista_n = rut_mayorista_n;
    }

    public String getRut_fiestasyeventos_n() {
        return rut_fiestasyeventos_n;
    }

    public void setRut_fiestasyeventos_n(String rut_fiestasyeventos_n) {
        if(rut_fiestasyeventos_n.contains("anyType{}"))
            this.rut_fiestasyeventos_n = "";
        else
            this.rut_fiestasyeventos_n = rut_fiestasyeventos_n;
    }

    public String getRut_auditoria_n() {
        return rut_auditoria_n;
    }

    public void setRut_auditoria_n(String rut_auditoria_n) {
        if(rut_auditoria_n.contains("anyType{}"))
            this.rut_auditoria_n = "";
        else
            this.rut_auditoria_n = rut_auditoria_n;
    }

    public String getRut_promoce_n() {
        return rut_promoce_n;
    }

    public void setRut_promoce_n(String rut_promoce_n) {
        if(rut_promoce_n.contains("anyType{}"))
            this.rut_promoce_n = "";
        else
            this.rut_promoce_n = rut_promoce_n;
    }
}
