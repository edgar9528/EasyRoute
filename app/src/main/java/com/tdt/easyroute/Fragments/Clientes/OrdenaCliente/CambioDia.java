package com.tdt.easyroute.Fragments.Clientes.OrdenaCliente;

public class CambioDia {

    private String fragment;
    private int dia;

    public CambioDia() {
    }

    public CambioDia(String fragment, int dia) {
        this.fragment = fragment;
        this.dia = dia;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }
}
