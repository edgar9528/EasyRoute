package com.tdt.easyroute.Model;

public class Venta {

    DataTableLC.Venta venta;
    DataTableLC.VentasDet[] Detalles;
    DataTableLC.VentasDet[] Bebidas;
    DataTableLC.VentasEnv [] Envase;

    public Venta() {
    }

    public Venta(DataTableLC.Venta venta) {
        this.venta = venta;
    }

    public DataTableLC.Venta getVenta() {
        return venta;
    }

    public void setVenta(DataTableLC.Venta venta) {
        this.venta = venta;
    }

    public DataTableLC.VentasDet[] getDetalles() {
        return Detalles;
    }

    public void setDetalles(DataTableLC.VentasDet[] detalles) {
        Detalles = detalles;
    }

    public DataTableLC.VentasDet[] getBebidas() {
        return Bebidas;
    }

    public void setBebidas(DataTableLC.VentasDet[] bebidas) {
        Bebidas = bebidas;
    }

    public DataTableLC.VentasEnv[] getEnvase() {
        return Envase;
    }

    public void setEnvase(DataTableLC.VentasEnv[] envase) {
        Envase = envase;
    }
}
