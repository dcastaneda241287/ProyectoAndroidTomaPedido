package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

import java.io.Serializable;

@Table(name = "PRODUCTO")
public class Producto implements Serializable {
    @Column(name = "codproducto")
    private String codproducto;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "igvdol")
    private String igvdol;
    @Column(name = "igvsol")
    private String igvsol;
    @Column(name = "preciodol")
    private String preciodol;
    @Column(name = "preciorealdol")
    private String preciorealdol;
    @Column(name = "preciorealsol")
    private String preciorealsol;
    @Column(name = "preciosol")
    private String preciosol;
    @Column(name = "stockproducto")
    private String stockproducto;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "unidadventa")
    private String unidadventa;
    @Column(name = "vCodBodega")
    private String vCodBodega;

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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion2) {
        this.descripcion = descripcion2;
    }

    public String getStockproducto() {
        return this.stockproducto;
    }

    public void setStockproducto(String stockproducto2) {
        this.stockproducto = stockproducto2;
    }

    public String getPreciodol() {
        return this.preciodol;
    }

    public void setPreciodol(String preciodol2) {
        this.preciodol = preciodol2;
    }

    public String getPreciosol() {
        return this.preciosol;
    }

    public void setPreciosol(String preciosol2) {
        this.preciosol = preciosol2;
    }

    public String getIgvdol() {
        return this.igvdol;
    }

    public void setIgvdol(String igvdol2) {
        this.igvdol = igvdol2;
    }

    public String getIgvsol() {
        return this.igvsol;
    }

    public void setIgvsol(String igvsol2) {
        this.igvsol = igvsol2;
    }

    public String getUnidadventa() {
        return this.unidadventa;
    }

    public void setUnidadventa(String unidadventa2) {
        this.unidadventa = unidadventa2;
    }

    public String getPreciorealdol() {
        return this.preciorealdol;
    }

    public void setPreciorealdol(String preciorealdol2) {
        this.preciorealdol = preciorealdol2;
    }

    public String getPreciorealsol() {
        return this.preciorealsol;
    }

    public void setPreciorealsol(String preciorealsol2) {
        this.preciorealsol = preciorealsol2;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo2) {
        this.tipo = tipo2;
    }

    public String getvCodBodega() {
        return vCodBodega;
    }

    public void setvCodBodega(String vCodBodega) {
        this.vCodBodega = vCodBodega;
    }
}
