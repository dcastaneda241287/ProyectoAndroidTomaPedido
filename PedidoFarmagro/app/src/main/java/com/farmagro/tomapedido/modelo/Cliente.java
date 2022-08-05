package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

import java.io.Serializable;

@Table(name = "CLIENTE")
public class Cliente implements Serializable {
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "condicionPago")
    private String condicionPago;
    @Column(name = "diasLibre")
    private String diasLibre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "dirembarque")
    private String dirembarque;
    @Column(name = "docGenerar")
    private String docGenerar;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "limiteCredido")
    private String limiteCredido;
    @Column(name = "monedaNivel")
    private String monedaNivel;
    @Column(name = "nivelPrecio")
    private String nivelPrecio;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "requiereOC")
    private String requiereOC;
    @Column(name = "rubro10")
    private String rubro10;
    @Column(name = "rubro8")
    private String rubro8;
    @Column(name = "rubro9")
    private String rubro9;
    @Column(name = "saldo")
    private String saldo;
    @Column(name = "saldoDolar")
    private String saldoDolar;
    @Column(name = "vNomZona")
    private String vNomZona;
    @Column(name = "vEstado")
    private String vEstado;
    @Column(name = "vCobJudicial")
    private String vCobJudicial;
    @Column(name = "dLimCreDolar")
    private String dLimCreDolar;
    @Column(name = "dLimCreLocal")
    private String dLimCreLocal;
    @Column(name = "dLimCreInsDolar")
    private String dLimCreInsDolar;
    @Column(name = "dSalVenDol")
    private String dSalVenDol;
    @Column(name = "iCantLetraPendiente")
    private String iCantLetraPendiente;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo2) {
        this.codigo = codigo2;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre2) {
        this.nombre = nombre2;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion2) {
        this.direccion = direccion2;
    }

    public String getDirembarque() {
        return this.dirembarque;
    }

    public void setDirembarque(String dirembarque2) {
        this.dirembarque = dirembarque2;
    }

    public String getSaldo() {
        return this.saldo;
    }

    public void setSaldo(String saldo2) {
        this.saldo = saldo2;
    }

    public String getSaldoDolar() {
        return this.saldoDolar;
    }

    public void setSaldoDolar(String saldoDolar2) {
        this.saldoDolar = saldoDolar2;
    }

    public String getLimiteCredido() {
        return this.limiteCredido;
    }

    public void setLimiteCredido(String limiteCredido2) {
        this.limiteCredido = limiteCredido2;
    }

    public String getCondicionPago() {
        return this.condicionPago;
    }

    public void setCondicionPago(String condicionPago2) {
        this.condicionPago = condicionPago2;
    }

    public String getNivelPrecio() {
        return this.nivelPrecio;
    }

    public void setNivelPrecio(String nivelPrecio2) {
        this.nivelPrecio = nivelPrecio2;
    }

    public String getDocGenerar() {
        return this.docGenerar;
    }

    public void setDocGenerar(String docGenerar2) {
        this.docGenerar = docGenerar2;
    }

    public String getMonedaNivel() {
        return this.monedaNivel;
    }

    public void setMonedaNivel(String monedaNivel2) {
        this.monedaNivel = monedaNivel2;
    }

    public String getDiasLibre() {
        return this.diasLibre;
    }

    public void setDiasLibre(String diasLibre2) {
        this.diasLibre = diasLibre2;
    }

    public String getRubro8() {
        return this.rubro8;
    }

    public void setRubro8(String rubro82) {
        this.rubro8 = rubro82;
    }

    public String getRubro9() {
        return this.rubro9;
    }

    public void setRubro9(String rubro92) {
        this.rubro9 = rubro92;
    }

    public String getRubro10() {
        return this.rubro10;
    }

    public void setRubro10(String rubro102) {
        this.rubro10 = rubro102;
    }

    public String getRequiereOC() {
        return this.requiereOC;
    }

    public void setRequiereOC(String requiereOC2) {
        this.requiereOC = requiereOC2;
    }

    public String getvNomZona() {
        return vNomZona;
    }

    public void setvNomZona(String vNomZona) {
        this.vNomZona = vNomZona;
    }

    public String getvEstado() {
        return vEstado;
    }

    public void setvEstado(String vEstado) {
        this.vEstado = vEstado;
    }

    public String getvCobJudicial() {
        return vCobJudicial;
    }

    public void setvCobJudicial(String vCobJudicial) {
        this.vCobJudicial = vCobJudicial;
    }

    public String getdLimCreDolar() {
        return dLimCreDolar;
    }

    public void setdLimCreDolar(String dLimCreDolar) {
        this.dLimCreDolar = dLimCreDolar;
    }

    public String getdLimCreLocal() {
        return dLimCreLocal;
    }

    public void setdLimCreLocal(String dLimCreLocal) {
        this.dLimCreLocal = dLimCreLocal;
    }

    public String getdLimCreInsDolar() {
        return dLimCreInsDolar;
    }

    public void setdLimCreInsDolar(String dLimCreInsDolar) {
        this.dLimCreInsDolar = dLimCreInsDolar;
    }

    public String getdSalVenDol() {
        return dSalVenDol;
    }

    public void setdSalVenDol(String dSalVenDol) {
        this.dSalVenDol = dSalVenDol;
    }

    public String getiCantLetraPendiente() {
        return iCantLetraPendiente;
    }

    public void setiCantLetraPendiente(String iCantLetraPendiente) {
        this.iCantLetraPendiente = iCantLetraPendiente;
    }
}

