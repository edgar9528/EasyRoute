package com.tdt.easyroute.CardViews.Model;

public class VentaCardView
{
    private String cve_producto;
    private String sku;
    private String producto;
    private String precio;
    private String cantidad;
    private String subtotal;

    public VentaCardView(String cve_producto, String sku, String producto, String precio, String cantidad, String subtotal) {
        this.cve_producto = cve_producto;
        this.sku = sku;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCve_producto() {
        return cve_producto;
    }

    public void setCve_producto(String cve_producto) {
        this.cve_producto = cve_producto;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
