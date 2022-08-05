package com.farmagro.tomapedido.modelo;

import java.io.Serializable;
import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "Zona")
public class Zona implements Serializable {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "descripcion")
    private String descripcion;

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

    public String toString() {
        return String.valueOf(this.codigo) + " " + this.descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}