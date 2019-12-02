package com.tdt.easyroute.Model;

public class PreventaDet {

    public String prev_folio_str;
    public int prev_num_n;
    public long prod_cve_n;
    public String prod_sku_str;
    public boolean prod_envase_n;
    public double prod_cant_n;
    public double lpre_base_n;
    public double lpre_cliente_n;
    public double lpre_promo_n;
    public double lpre_precio_n;
    public boolean prom_promo_n;
    public long prom_cve_n;
    public double prod_subtotal_n;

    public PreventaDet() {
    }

    public PreventaDet(String prev_folio_str, int prev_num_n, long prod_cve_n, String prod_sku_str, boolean prod_envase_n, double prod_cant_n, double lpre_base_n, double lpre_cliente_n, double lpre_promo_n, double lpre_precio_n, boolean prom_promo_n, long prom_cve_n, double prod_subtotal_n) {
        this.prev_folio_str = prev_folio_str;
        this.prev_num_n = prev_num_n;
        this.prod_cve_n = prod_cve_n;
        this.prod_sku_str = prod_sku_str;
        this.prod_envase_n = prod_envase_n;
        this.prod_cant_n = prod_cant_n;
        this.lpre_base_n = lpre_base_n;
        this.lpre_cliente_n = lpre_cliente_n;
        this.lpre_promo_n = lpre_promo_n;
        this.lpre_precio_n = lpre_precio_n;
        this.prom_promo_n = prom_promo_n;
        this.prom_cve_n = prom_cve_n;
        this.prod_subtotal_n = prod_subtotal_n;
    }

    public String getPrev_folio_str() {
        return prev_folio_str;
    }

    public void setPrev_folio_str(String prev_folio_str) {
        this.prev_folio_str = prev_folio_str;
    }

    public int getPrev_num_n() {
        return prev_num_n;
    }

    public void setPrev_num_n(int prev_num_n) {
        this.prev_num_n = prev_num_n;
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

    public boolean isProd_envase_n() {
        return prod_envase_n;
    }

    public void setProd_envase_n(boolean prod_envase_n) {
        this.prod_envase_n = prod_envase_n;
    }

    public double getProd_cant_n() {
        return prod_cant_n;
    }

    public void setProd_cant_n(double prod_cant_n) {
        this.prod_cant_n = prod_cant_n;
    }

    public double getLpre_base_n() {
        return lpre_base_n;
    }

    public void setLpre_base_n(double lpre_base_n) {
        this.lpre_base_n = lpre_base_n;
    }

    public double getLpre_cliente_n() {
        return lpre_cliente_n;
    }

    public void setLpre_cliente_n(double lpre_cliente_n) {
        this.lpre_cliente_n = lpre_cliente_n;
    }

    public double getLpre_promo_n() {
        return lpre_promo_n;
    }

    public void setLpre_promo_n(double lpre_promo_n) {
        this.lpre_promo_n = lpre_promo_n;
    }

    public double getLpre_precio_n() {
        return lpre_precio_n;
    }

    public void setLpre_precio_n(double lpre_precio_n) {
        this.lpre_precio_n = lpre_precio_n;
    }

    public boolean isProm_promo_n() {
        return prom_promo_n;
    }

    public void setProm_promo_n(boolean prom_promo_n) {
        this.prom_promo_n = prom_promo_n;
    }

    public long getProm_cve_n() {
        return prom_cve_n;
    }

    public void setProm_cve_n(long prom_cve_n) {
        this.prom_cve_n = prom_cve_n;
    }

    public double getProd_subtotal_n() {
        return prod_subtotal_n;
    }

    public void setProd_subtotal_n(double prod_subtotal_n) {
        this.prod_subtotal_n = prod_subtotal_n;
    }
}
