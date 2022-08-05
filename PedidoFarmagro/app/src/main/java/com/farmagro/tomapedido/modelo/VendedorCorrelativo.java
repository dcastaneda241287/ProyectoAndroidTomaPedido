package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "VendedorCorrelativo")
public class VendedorCorrelativo {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "iCodUsu")
    private String iCodUsu;
    @Column(name = "vSerie")
    private String vSerie;
    @Column(name = "iCorrelativo")
    private String iCorrelativo;

    public String getiCodUsu() {
        return iCodUsu;
    }

    public void setiCodUsu(String iCodUsu) {
        this.iCodUsu = iCodUsu;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getvSerie() {
        return vSerie;
    }

    public void setvSerie(String vSerie) {
        this.vSerie = vSerie;
    }

    public String getiCorrelativo() {
        return iCorrelativo;
    }

    public void setiCorrelativo(String iCorrelativo) {
        this.iCorrelativo = iCorrelativo;
    }
}

