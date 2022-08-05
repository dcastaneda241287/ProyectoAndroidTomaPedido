package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "EXISTENCIA")
public class Existencia {
    @Column(name = "dCanDisponible")
    private String dCanDisponible;
    @Column(name = "cantTransito")
    private String cantTransito;
    @Column(name = "codProducto")
    private String codProducto;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "unidadVenta")
    private String unidadVenta;
    @Column(name = "vCodBodega")
    private String vCodBodega;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String getCodProducto() {
        return this.codProducto;
    }

    public void setCodProducto(String codProducto2) {
        this.codProducto = codProducto2;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion2) {
        this.descripcion = descripcion2;
    }

    public String getUnidadVenta() {
        return this.unidadVenta;
    }

    public void setUnidadVenta(String unidadVenta2) {
        this.unidadVenta = unidadVenta2;
    }

    public String getCantTransito() {
        return this.cantTransito;
    }

    public void setCantTransito(String cantTransito2) {
        this.cantTransito = cantTransito2;
    }

    public String getdCanDisponible() {
        return dCanDisponible;
    }

    public void setdCanDisponible(String dCanDisponible) {
        this.dCanDisponible = dCanDisponible;
    }

    public String getvCodBodega() {
        return vCodBodega;
    }

    public void setvCodBodega(String vCodBodega) {
        this.vCodBodega = vCodBodega;
    }
}

