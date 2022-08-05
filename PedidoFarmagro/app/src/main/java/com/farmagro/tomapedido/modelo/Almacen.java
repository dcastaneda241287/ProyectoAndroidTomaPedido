package com.farmagro.tomapedido.modelo;

public class Almacen {
    private String codigo;
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
        return this.descripcion;
    }
}