package com.farmagro.tomapedido.modelo;


import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

import java.io.Serializable;

@Table(name = "PROVEEDOR")
public class Proveedor implements Serializable {
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "vRuc")
    private String vRuc;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo2) {
        this.codigo = codigo2;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion2) {
        this.descripcion = descripcion2;
    }

    public String getvRuc() {
        return vRuc;
    }

    public void setvRuc(String vRuc) {
        this.vRuc = vRuc;
    }
}
