package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

@Table(name = "LETRA")
public class Letra {
    @Column(name = "codcondicion")
    private String codcondicion;
    @Column(name = "descondicion")
    private String descondicion;
    @Column(name = "diasneto")
    private String diasneto;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String getCodcondicion() {
        return this.codcondicion;
    }

    public void setCodcondicion(String codcondicion2) {
        this.codcondicion = codcondicion2;
    }

    public String getDescondicion() {
        return this.descondicion;
    }

    public void setDescondicion(String descondicion2) {
        this.descondicion = descondicion2;
    }

    public String getDiasneto() {
        return this.diasneto;
    }

    public void setDiasneto(String diasneto2) {
        this.diasneto = diasneto2;
    }

    public String toString() {
        return String.valueOf(this.codcondicion) + " - " + this.descondicion;
    }
}
