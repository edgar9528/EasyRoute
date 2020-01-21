package com.tdt.easyroute.CardViews.Model;

public class CarteraCardView {

    private String credito;
    private String monto;
    private String abono;
    private String saldo;

    public CarteraCardView(String credito, String monto, String abono, String saldo) {
        this.credito = credito;
        this.monto = monto;
        this.abono = abono;
        this.saldo = saldo;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
