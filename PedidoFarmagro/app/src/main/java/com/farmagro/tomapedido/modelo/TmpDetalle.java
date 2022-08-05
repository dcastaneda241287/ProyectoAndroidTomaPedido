package com.farmagro.tomapedido.modelo;


import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

import java.io.Serializable;

@Table(name = "TMPDETALLE")
public class TmpDetalle implements Serializable {
    @Column(name = "cantidad")
    private Float cantidad;
    @Column(name = "codproducto")
    private String codproducto;
    @Column(name = "descuento")
    private Float descuento;
    @Column(name = "desproducto")
    private String desproducto;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = false)
    private Long id;
    @Column(name = "igv")
    private Float igv;
    @Column(name = "precio")
    private Float precio;
    @Column(name = "total")
    private Float total;
    @Column(name = "undventa")
    private String undventa;
    @Column(name = "vcodalmacen")
    private String vcodalmacen;
    @Column(name = "descuentoestructurado")
    private String descuentoestructurado;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
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

    public String getVcodalmacen() {
        return vcodalmacen;
    }

    public void setVcodalmacen(String vcodalmacen) {
        this.vcodalmacen = vcodalmacen;
    }

    public String getDescuentoestructurado() {
        return descuentoestructurado;
    }

    public void setDescuentoestructurado(String descuentoestructurado) {
        this.descuentoestructurado = descuentoestructurado;
    }
}
