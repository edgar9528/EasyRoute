package com.tdt.easyroute.CardViews.Model;

public class PagoCardView {

    private String numero;
    private String idPago;
    private String forma;
    private String cantidad;
    private String banco;
    private String referencia;

    public PagoCardView(String numero, String idPago, String forma, String cantidad, String banco, String referencia) {
        this.numero = numero;
        this.idPago = idPago;
        this.forma = forma;
        this.cantidad = cantidad;
        this.banco = banco;
        this.referencia = referencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
