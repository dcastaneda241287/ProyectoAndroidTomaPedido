package com.farmagro.tomapedido.util;

import androidx.annotation.Nullable;

public class Framework {

    private int img;
    private String nombre;
    private int contenido;
    private String valor;

    public Framework(int img, String nombre, int contenido, @Nullable String valor) {
        this.img = img;
        this.nombre = nombre;
        this.contenido = contenido;
        this.valor = valor;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getContenido() {
        return contenido;
    }

    public void setContenido(int contenido) {
        this.contenido = contenido;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
