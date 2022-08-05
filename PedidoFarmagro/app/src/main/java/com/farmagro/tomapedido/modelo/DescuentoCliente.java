package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "DescuentoCliente")
public class DescuentoCliente {
    @Column(name = "iCodDescuento")
    private String iCodDescuento;
    @Column(name = "vCodCliente")
    private String vCodCliente;
    @Column(name = "vDescripcion")
    private String vDescripcion;
    @Column(name = "deDescuento")
    private String deDescuento;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;

    public String getiCodDescuento() {
        return iCodDescuento;
    }

    public void setiCodDescuento(String iCodDescuento) {
        this.iCodDescuento = iCodDescuento;
    }

    public String getvCodCliente() {
        return vCodCliente;
    }

    public void setvCodCliente(String vCodCliente) {
        this.vCodCliente = vCodCliente;
    }

    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }

    public String getDeDescuento() {
        return deDescuento;
    }

    public void setDeDescuento(String deDescuento) {
        this.deDescuento = deDescuento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

