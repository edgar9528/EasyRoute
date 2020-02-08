package com.tdt.easyroute.CardViews.Model;

import android.graphics.drawable.Drawable;

import com.tdt.easyroute.Model.DataTableLC;

public class PedidosCardView {

    private String clave;
    private String nombre;
    private DataTableLC.PedidosLv cliente;
    private Drawable icono;

    public PedidosCardView(String clave, String nombre, DataTableLC.PedidosLv cliente, Drawable icono) {
        this.clave = clave;
        this.nombre = nombre;
        this.cliente = cliente;
        this.icono = icono;
    }

    public Drawable getIcono() {
        return icono;
    }

    public void setIcono(Drawable icono) {
        this.icono = icono;
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

    public DataTableLC.PedidosLv getCliente() {
        return cliente;
    }

    public void setCliente(DataTableLC.PedidosLv cliente) {
        this.cliente = cliente;
    }
}
