package com.tdt.easyroute.Model;

public class Creditos {

    public int cred_cve_n;
    public String cred_referencia_str;
    public long cli_cve_n;
    public String usu_cve_str;
    public String cred_fecha_dt;
    public String cred_descripcion_str;
    public String cred_vencimiento_dt;
    public Double cred_monto_n;
    public Double cred_abono_n;
    public boolean cred_engestoria_n;
    public boolean cred_esenvase_n;
    public boolean cred_especial_n;
    public long prod_cve_n;
    public String prod_sku_str;
    public Double prod_precio_n;
    public Double prod_cant_n;
    public Double prod_cantabono_n;
    public byte trans_est_n;
    public String trans_fecha_dt;

    public Creditos() {
    }

    public Creditos(int cred_cve_n, String cred_referencia_str, long cli_cve_n, String usu_cve_str, String cred_fecha_dt, String cred_descripcion_str, String cred_vencimiento_dt, Double cred_monto_n, Double cred_abono_n, boolean cred_engestoria_n, boolean cred_esenvase_n, boolean cred_especial_n, long prod_cve_n, String prod_sku_str, Double prod_precio_n, Double prod_cant_n, Double prod_cantabono_n, byte trans_est_n, String trans_fecha_dt) {
        this.cred_cve_n = cred_cve_n;
        this.cred_referencia_str = cred_referencia_str;
        this.cli_cve_n = cli_cve_n;
        this.usu_cve_str = usu_cve_str;
        this.cred_fecha_dt = cred_fecha_dt;
        this.cred_descripcion_str = cred_descripcion_str;
        this.cred_vencimiento_dt = cred_vencimiento_dt;
        this.cred_monto_n = cred_monto_n;
        this.cred_abono_n = cred_abono_n;
        this.cred_engestoria_n = cred_engestoria_n;
        this.cred_esenvase_n = cred_esenvase_n;
        this.cred_especial_n = cred_especial_n;
        this.prod_cve_n = prod_cve_n;
        this.prod_sku_str = prod_sku_str;
        this.prod_precio_n = prod_precio_n;
        this.prod_cant_n = prod_cant_n;
        this.prod_cantabono_n = prod_cantabono_n;
        this.trans_est_n = trans_est_n;
        this.trans_fecha_dt = trans_fecha_dt;
    }


    public int getCred_cve_n() {
        return cred_cve_n;
    }

    public void setCred_cve_n(int cred_cve_n) {
        this.cred_cve_n = cred_cve_n;
    }

    public String getCred_referencia_str() {
        return cred_referencia_str;
    }

    public void setCred_referencia_str(String cred_referencia_str) {
        this.cred_referencia_str = cred_referencia_str;
    }

    public long getCli_cve_n() {
        return cli_cve_n;
    }

    public void setCli_cve_n(long cli_cve_n) {
        this.cli_cve_n = cli_cve_n;
    }

    public String getUsu_cve_str() {
        return usu_cve_str;
    }

    public void setUsu_cve_str(String usu_cve_str) {
        this.usu_cve_str = usu_cve_str;
    }

    public String getCred_fecha_dt() {
        return cred_fecha_dt;
    }

    public void setCred_fecha_dt(String cred_fecha_dt) {
        this.cred_fecha_dt = cred_fecha_dt;
    }

    public String getCred_descripcion_str() {
        return cred_descripcion_str;
    }

    public void setCred_descripcion_str(String cred_descripcion_str) {
        this.cred_descripcion_str = cred_descripcion_str;
    }

    public String getCred_vencimiento_dt() {
        return cred_vencimiento_dt;
    }

    public void setCred_vencimiento_dt(String cred_vencimiento_dt) {
        this.cred_vencimiento_dt = cred_vencimiento_dt;
    }

    public Double getCred_monto_n() {
        return cred_monto_n;
    }

    public void setCred_monto_n(Double cred_monto_n) {
        this.cred_monto_n = cred_monto_n;
    }

    public Double getCred_abono_n() {
        return cred_abono_n;
    }

    public void setCred_abono_n(Double cred_abono_n) {
        this.cred_abono_n = cred_abono_n;
    }

    public boolean isCred_engestoria_n() {
        return cred_engestoria_n;
    }

    public void setCred_engestoria_n(boolean cred_engestoria_n) {
        this.cred_engestoria_n = cred_engestoria_n;
    }

    public boolean isCred_esenvase_n() {
        return cred_esenvase_n;
    }

    public void setCred_esenvase_n(boolean cred_esenvase_n) {
        this.cred_esenvase_n = cred_esenvase_n;
    }

    public boolean isCred_especial_n() {
        return cred_especial_n;
    }

    public void setCred_especial_n(boolean cred_especial_n) {
        this.cred_especial_n = cred_especial_n;
    }

    public long getProd_cve_n() {
        return prod_cve_n;
    }

    public void setProd_cve_n(long prod_cve_n) {
        this.prod_cve_n = prod_cve_n;
    }

    public String getProd_sku_str() {
        return prod_sku_str;
    }

    public void setProd_sku_str(String prod_sku_str) {
        this.prod_sku_str = prod_sku_str;
    }

    public Double getProd_precio_n() {
        return prod_precio_n;
    }

    public void setProd_precio_n(Double prod_precio_n) {
        this.prod_precio_n = prod_precio_n;
    }

    public Double getProd_cant_n() {
        return prod_cant_n;
    }

    public void setProd_cant_n(Double prod_cant_n) {
        this.prod_cant_n = prod_cant_n;
    }

    public Double getProd_cantabono_n() {
        return prod_cantabono_n;
    }

    public void setProd_cantabono_n(Double prod_cantabono_n) {
        this.prod_cantabono_n = prod_cantabono_n;
    }

    public byte getTrans_est_n() {
        return trans_est_n;
    }

    public void setTrans_est_n(byte trans_est_n) {
        this.trans_est_n = trans_est_n;
    }

    public String getTrans_fecha_dt() {
        return trans_fecha_dt;
    }

    public void setTrans_fecha_dt(String trans_fecha_dt) {
        this.trans_fecha_dt = trans_fecha_dt;
    }
}
