package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;
import java.io.Serializable;

@Table(name = "DIRECCIONEMBARQUE")
public class DireccionEmbarque implements Serializable {
    @Column(name = "vCodCliente")
    private String vCodCliente;
    @Column(name = "vDireccion")
    private String vDireccion;
    @Column(name = "iDetDireccion")
    private String iDetDireccion;
    @Column(name = "vDescripcion")
    private String vDescripcion;
    @Column(name = "vContacto")
    private String vContacto;
    @Column(name = "vCargo")
    private String vCargo;
    @Column(name = "vTelefUno")
    private String vTelefUno;
    @Column(name = "vMail")
    private String vMail;
    @Column(name = "dtFecCrea")
    private String dtFecCrea;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    
    private Long id;

    public String getvCodCliente() {
        return vCodCliente;
    }

    public void setvCodCliente(String vCodCliente) {
        this.vCodCliente = vCodCliente;
    }

    public String getvDireccion() {
        return vDireccion;
    }

    public void setvDireccion(String vDireccion) {
        this.vDireccion = vDireccion;
    }

    public String getiDetDireccion() {
        return iDetDireccion;
    }

    public void setiDetDireccion(String iDetDireccion) {
        this.iDetDireccion = iDetDireccion;
    }

    public String getvDescripcion() {
        return vDescripcion;
    }

    public void setvDescripcion(String vDescripcion) {
        this.vDescripcion = vDescripcion;
    }

    public String getvContacto() {
        return vContacto;
    }

    public void setvContacto(String vContacto) {
        this.vContacto = vContacto;
    }

    public String getvCargo() {
        return vCargo;
    }

    public void setvCargo(String vCargo) {
        this.vCargo = vCargo;
    }

    public String getvTelefUno() {
        return vTelefUno;
    }

    public void setvTelefUno(String vTelefUno) {
        this.vTelefUno = vTelefUno;
    }

    public String getvMail() {
        return vMail;
    }

    public void setvMail(String vMail) {
        this.vMail = vMail;
    }

    public String getDtFecCrea() {
        return dtFecCrea;
    }

    public void setDtFecCrea(String dtFecCrea) {
        this.dtFecCrea = dtFecCrea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
