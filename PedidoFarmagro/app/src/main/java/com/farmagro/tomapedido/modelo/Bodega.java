package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "BODEGA")
public class Bodega {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "vCodbodega")
    private String vCodbodega;
    @Column(name = "nombre")
    private String nombre;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre2) {
        this.nombre = nombre2;
    }

    public String getvCodbodega() {
        return vCodbodega;
    }

    public void setvCodbodega(String vCodbodega) {
        this.vCodbodega = vCodbodega;
    }
}
