package com.tdt.easyroute.Model;

public class PreventaEnvase {

    public String prev_folio_str;
    public long prod_cve_n;
    public String prod_sku_str;
    public double prod_inicial_n;
    public double prod_cargo_n;
    public double prod_abono_n;
    public double prod_regalo_n;
    public double prod_venta_n;
    public double prod_final_n;
    public double lpre_base_n;
    public double lpre_precio_n;

    public PreventaEnvase(String prev_folio_str, long prod_cve_n, String prod_sku_str, double prod_inicial_n, double prod_cargo_n, double prod_abono_n, double prod_regalo_n, double prod_venta_n, double prod_final_n, double lpre_base_n, double lpre_precio_n) {
        this.prev_folio_str = prev_folio_str;
        this.prod_cve_n = prod_cve_n;
        this.prod_sku_str = prod_sku_str;
        this.prod_inicial_n = prod_inicial_n;
        this.prod_cargo_n = prod_cargo_n;
        this.prod_abono_n = prod_abono_n;
        this.prod_regalo_n = prod_regalo_n;
        this.prod_venta_n = prod_venta_n;
        this.prod_final_n = prod_final_n;
        this.lpre_base_n = lpre_base_n;
        this.lpre_precio_n = lpre_precio_n;
    }


    public String getPrev_folio_str() {
        return prev_folio_str;
    }

    public void setPrev_folio_str(String prev_folio_str) {
        this.prev_folio_str = prev_folio_str;
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

    public double getProd_inicial_n() {
        return prod_inicial_n;
    }

    public void setProd_inicial_n(double prod_inicial_n) {
        this.prod_inicial_n = prod_inicial_n;
    }

    public double getProd_cargo_n() {
        return prod_cargo_n;
    }

    public void setProd_cargo_n(double prod_cargo_n) {
        this.prod_cargo_n = prod_cargo_n;
    }

    public double getProd_abono_n() {
        return prod_abono_n;
    }

    public void setProd_abono_n(double prod_abono_n) {
        this.prod_abono_n = prod_abono_n;
    }

    public double getProd_regalo_n() {
        return prod_regalo_n;
    }

    public void setProd_regalo_n(double prod_regalo_n) {
        this.prod_regalo_n = prod_regalo_n;
    }

    public double getProd_venta_n() {
        return prod_venta_n;
    }

    public void setProd_venta_n(double prod_venta_n) {
        this.prod_venta_n = prod_venta_n;
    }

    public double getProd_final_n() {
        return prod_final_n;
    }

    public void setProd_final_n(double prod_final_n) {
        this.prod_final_n = prod_final_n;
    }

    public double getLpre_base_n() {
        return lpre_base_n;
    }

    public void setLpre_base_n(double lpre_base_n) {
        this.lpre_base_n = lpre_base_n;
    }

    public double getLpre_precio_n() {
        return lpre_precio_n;
    }

    public void setLpre_precio_n(double lpre_precio_n) {
        this.lpre_precio_n = lpre_precio_n;
    }
}
