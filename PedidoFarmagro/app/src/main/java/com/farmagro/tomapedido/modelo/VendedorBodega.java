package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "VendedorBodega")
public class VendedorBodega {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "iCodUsu")
    private String iCodUsu;
    @Column(name = "vCodBodega")
    private String vCodBodega;
    @Column(name = "vDescripcion")
    private String vDescripcion;

    public String getiCodUsu() {
        return iCodUsu;
    }

    public void setiCodUsu(String iCodUsu) {
        this.iCodUsu = iCodUsu;
    }

    public String getvCodBodega() {
        return vCodBodega;
    }

    public void setvCodBodega(String vCodBodega) {
        this.vCodBodega = vCodBodega;
    }

    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
