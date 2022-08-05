package com.farmagro.tomapedido.modelo;

public class FacturaVendedor {
    private String dFecha;
    private String vPedido;
    private String vEstado;
    private String vFactura;
    private String dTotVenta;
    private String vNomcli;

    public String getdFecha() {
        return dFecha;
    }

    public void setdFecha(String dFecha) {
        this.dFecha = dFecha;
    }

    public String getvPedido() {
        return vPedido;
    }

    public void setvPedido(String vPedido) {
        this.vPedido = vPedido;
    }

    public String getvEstado() {
        return vEstado;
    }

    public void setvEstado(String vEstado) {
        this.vEstado = vEstado;
    }

    public String getvFactura() {
        return vFactura;
    }

    public void setvFactura(String vFactura) {
        this.vFactura = vFactura;
    }

    public String getdTotVenta() {
        return dTotVenta;
    }

    public void setdTotVenta(String dTotVenta) {
        this.dTotVenta = dTotVenta;
    }

    public String getvNomcli() {
        return vNomcli;
    }

    public void setvNomcli(String vNomcli) {
        this.vNomcli = vNomcli;
    }
}
