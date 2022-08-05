package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "ZonaVendedor")
public class ZonaVendedor {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "iCodUsu")
    private String iCodUsu;
    @Column(name = "vNombre")
    private String vNombre;
    @Column(name = "vCodZona")
    private String vCodZona;
    @Column(name = "vDescripcion")
    private String vDescripcion;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getiCodUsu() {
        return iCodUsu;
    }

    public void setiCodUsu(String iCodUsu) {
        this.iCodUsu = iCodUsu;
    }

    public String getvNombre() {
        return vNombre;
    }

    public void setvNombre(String vNombre) {
        this.vNombre = vNombre;
    }

    public String getvCodZona() {
        return vCodZona;
    }

    public void setvCodZona(String vCodZona) {
        this.vCodZona = vCodZona;
    }

    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }
}
