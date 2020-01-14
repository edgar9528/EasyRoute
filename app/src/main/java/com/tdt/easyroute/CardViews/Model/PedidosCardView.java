package com.tdt.easyroute.CardViews.Model;

public class PedidosCardView {

    private String clave;
    private String nombre;

    public PedidosCardView(String clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
