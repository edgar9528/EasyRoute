package com.tdt.easyroute.Model;

public class Preventa {

    public DataTableWS.Preventa preventa;
    public DataTableWS.PreventaDet[] detalles;
    public DataTableWS.PreventaDet[] Bebidas;

    public Preventa() {
    }

    public Preventa(DataTableWS.Preventa preventa) {
        this.preventa = preventa;
    }

    public DataTableWS.Preventa getPreventa() {
        return preventa;
    }

    public void setPreventa(DataTableWS.Preventa preventa) {
        this.preventa = preventa;
    }

    public DataTableWS.PreventaDet[] getDetalles() {
        return detalles;
    }

    public void setDetalles(DataTableWS.PreventaDet[] detalles) {
        this.detalles = detalles;
    }

    public DataTableWS.PreventaDet[] getBebidas() {
        return Bebidas;
    }

    public void setBebidas(DataTableWS.PreventaDet[] bebidas) {
        Bebidas = bebidas;
    }
}
