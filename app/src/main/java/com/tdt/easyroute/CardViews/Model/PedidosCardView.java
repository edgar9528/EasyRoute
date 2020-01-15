package com.tdt.easyroute.CardViews.Model;

import com.tdt.easyroute.Model.DataTableLC;

public class PedidosCardView {

    private String clave;
    private String nombre;
    private DataTableLC.PedidosLv cliente;

    public PedidosCardView(String clave, String nombre, DataTableLC.PedidosLv cliente) {
        this.clave = clave;
        this.nombre = nombre;
        this.cliente = cliente;
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
