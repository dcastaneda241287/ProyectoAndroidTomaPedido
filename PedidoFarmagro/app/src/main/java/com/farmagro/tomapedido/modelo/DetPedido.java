package com.farmagro.tomapedido.modelo;


import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.ForeignKey;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "DETPEDIDO")
public class DetPedido {
    @Column(name = "cantidad")
    private Float cantidad;
    @Column(name = "codproducto")
    private String codproducto;
    @Column(name = "descuento")
    private Float descuento;
    @Column(name = "desproducto")
    private String desproducto;
    @Column(name = "idDetalle")
    @PrimaryKey(autoIncrement = true)
    private Long idDetalle;
    @Column(name = "idPedido")
    @ForeignKey(columnReference = "id", onDeleteCascade = true, tableReference = "CABPEDIDO")
    private Long idPedido;
    @Column(name = "igv")
    private Float igv;
    @Column(name = "precio")
    private Float precio;
    @Column(name = "total")
    private Float total;
    @Column(name = "undventa")
    private String undventa;
    @Column(name = "codbodega")
    private String codbodega;

    public Long getIdDetalle() {
        return this.idDetalle;
    }

    public void setIdDetalle(Long idDetalle2) {
        this.idDetalle = idDetalle2;
    }

    public Long getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(Long idPedido2) {
        this.idPedido = idPedido2;
    }

    public String getCodproducto() {
        return this.codproducto;
    }

    public void setCodproducto(String codproducto2) {
        this.codproducto = codproducto2;
    }

    public String getDesproducto() {
        return this.desproducto;
    }

    public void setDesproducto(String desproducto2) {
        this.desproducto = desproducto2;
    }

    public Float getPrecio() {
        return this.precio;
    }

    public void setPrecio(Float precio2) {
        this.precio = precio2;
    }

    public Float getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Float cantidad2) {
        this.cantidad = cantidad2;
    }

    public Float getDescuento() {
        return this.descuento;
    }

    public void setDescuento(Float descuento2) {
        this.descuento = descuento2;
    }

    public Float getTotal() {
        return this.total;
    }

    public void setTotal(Float total2) {
        this.total = total2;
    }

    public String getUndventa() {
        return this.undventa;
    }

    public void setUndventa(String undventa2) {
        this.undventa = undventa2;
    }

    public Float getIgv() {
        return this.igv;
    }

    public void setIgv(Float igv2) {
        this.igv = igv2;
    }

    public String getCodbodega() {
        return codbodega;
    }

    public void setCodbodega(String codbodega) {
        this.codbodega = codbodega;
    }


}
