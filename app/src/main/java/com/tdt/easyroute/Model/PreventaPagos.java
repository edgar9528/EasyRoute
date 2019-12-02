package com.tdt.easyroute.Model;

public class PreventaPagos {

    public String prev_folio_str;
    public byte ppag_num_n;
    public boolean ppag_cobranza_n;
    public int fpag_cve_n;
    public String fpag_desc_str;
    public double fpag_cant_n;

    public PreventaPagos(String prev_folio_str, byte ppag_num_n, boolean ppag_cobranza_n, int fpag_cve_n, String fpag_desc_str, double fpag_cant_n) {
        this.prev_folio_str = prev_folio_str;
        this.ppag_num_n = ppag_num_n;
        this.ppag_cobranza_n = ppag_cobranza_n;
        this.fpag_cve_n = fpag_cve_n;
        this.fpag_desc_str = fpag_desc_str;
        this.fpag_cant_n = fpag_cant_n;
    }

    public String getPrev_folio_str() {
        return prev_folio_str;
    }

    public void setPrev_folio_str(String prev_folio_str) {
        this.prev_folio_str = prev_folio_str;
    }

    public byte getPpag_num_n() {
        return ppag_num_n;
    }

    public void setPpag_num_n(byte ppag_num_n) {
        this.ppag_num_n = ppag_num_n;
    }

    public boolean isPpag_cobranza_n() {
        return ppag_cobranza_n;
    }

    public void setPpag_cobranza_n(boolean ppag_cobranza_n) {
        this.ppag_cobranza_n = ppag_cobranza_n;
    }

    public int getFpag_cve_n() {
        return fpag_cve_n;
    }

    public void setFpag_cve_n(int fpag_cve_n) {
        this.fpag_cve_n = fpag_cve_n;
    }

    public String getFpag_desc_str() {
        return fpag_desc_str;
    }

    public void setFpag_desc_str(String fpag_desc_str) {
        this.fpag_desc_str = fpag_desc_str;
    }

    public double getFpag_cant_n() {
        return fpag_cant_n;
    }

    public void setFpag_cant_n(double fpag_cant_n) {
        this.fpag_cant_n = fpag_cant_n;
    }
}
