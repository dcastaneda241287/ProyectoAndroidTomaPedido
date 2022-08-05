package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;


@Table(name = "VendedorMail")
public class VendedorMail {
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "iCodUsu")
    private String iCodUsu;
    @Column(name = "vMail")
    private String vMail;

    public String getiCodUsu() {
        return iCodUsu;
    }

    public void setiCodUsu(String iCodUsu) {
        this.iCodUsu = iCodUsu;
    }

    public String getvMail() {
        return vMail;
    }

    public void setvMail(String vMail) {
        this.vMail = vMail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
