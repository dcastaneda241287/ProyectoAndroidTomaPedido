package com.farmagro.tomapedido.modelo;

import com.farmagro.tomapedido.droidpersistence.annotation.Column;
import com.farmagro.tomapedido.droidpersistence.annotation.PrimaryKey;
import com.farmagro.tomapedido.droidpersistence.annotation.Table;

import java.io.Serializable;

@Table(name = "CABPEDIDO")
public class CabPedido implements Serializable {
    @Column(name = "almacen")
    private String almacen;
    @Column(name = "cliente")
    private String cliente;
    @Column(name = "coddefault")
    private String coddefault;
    @Column(name = "codigotransp")
    private String codigotransp;
    @Column(name = "condicionpago")
    private String condicionpago;
    @Column(name = "dialibre")
    private String dialibre;
    @Column(name = "direcccobro")
    private String direcccobro;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "documento")
    private String documento;
    @Column(name = "estado")
    private String estado;
    @Column(name = "fechaorden")
    private String fechaorden;
    @Column(name = "fechaprom")
    private String fechaprom;
    @Column(name = "formapago")
    private String formapago;
    @Column(name = "foto")
    private String foto;
    @Column(name = "id")
    @PrimaryKey(autoIncrement = true)
    private Long id;
    @Column(name = "inicioletra")
    private String inicioletra;
    @Column(name = "latitud")
    private String latitud;
    @Column(name = "letras")
    private String letras;
    @Column(name = "longitud")
    private String longitud;
    @Column(name = "moneda")
    private String moneda;
    @Column(name = "monflete")
    private String monflete;
    @Column(name = "nivelprecio")
    private String nivelprecio;
    @Column(name = "nombcliente")
    private String nombcliente;
    @Column(name = "nombretransp")
    private String nombretransp;
    @Column(name = "numero")
    private String numero;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "ordencompra")
    private String ordencompra;
    @Column(name = "requiereoc")
    private String requiereoc;
    @Column(name = "separacion")
    private String separacion;
    @Column(name = "tipoventa")
    private String tipoventa;
    @Column(name = "totalfacturar")
    private String totalfacturar;
    @Column(name = "totaligv")
    private String totaligv;
    @Column(name = "totalmercaderia")
    private String totalmercaderia;
    @Column(name = "totalunidad")
    private String totalunidad;
    @Column(name = "tpedido")
    private String tpedido;
    @Column(name = "zona")
    private String zona;
    @Column(name = "descuento")
    private String descuento;
    @Column(name = "direcciongps")
    private String direcciongps;

    public String getTotalmercaderia() {
        return this.totalmercaderia;
    }

    public void setTotalmercaderia(String totalmercaderia2) {
        this.totalmercaderia = totalmercaderia2;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id2) {
        this.id = id2;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero2) {
        this.numero = numero2;
    }

    public String getCliente() {
        return this.cliente;
    }

    public void setCliente(String cliente2) {
        this.cliente = cliente2;
    }

    public String getNombcliente() {
        return this.nombcliente;
    }

    public void setNombcliente(String nombcliente2) {
        this.nombcliente = nombcliente2;
    }

    public String getFechaprom() {
        return this.fechaprom;
    }

    public void setFechaprom(String fechaprom2) {
        this.fechaprom = fechaprom2;
    }

    public String getTipoventa() {
        return this.tipoventa;
    }

    public void setTipoventa(String tipoventa2) {
        this.tipoventa = tipoventa2;
    }

    public String getFormapago() {
        return this.formapago;
    }

    public void setFormapago(String formapago2) {
        this.formapago = formapago2;
    }

    public String getDocumento() {
        return this.documento;
    }

    public void setDocumento(String documento2) {
        this.documento = documento2;
    }

    public String getMoneda() {
        return this.moneda;
    }

    public void setMoneda(String moneda2) {
        this.moneda = moneda2;
    }

    public String getNivelprecio() {
        return this.nivelprecio;
    }

    public void setNivelprecio(String nivelprecio2) {
        this.nivelprecio = nivelprecio2;
    }

    public String getAlmacen() {
        return this.almacen;
    }

    public void setAlmacen(String almacen2) {
        this.almacen = almacen2;
    }

    public String getCondicionpago() {
        return this.condicionpago;
    }

    public void setCondicionpago(String condicionpago2) {
        this.condicionpago = condicionpago2;
    }

    public String getLetras() {
        return this.letras;
    }

    public void setLetras(String letras2) {
        this.letras = letras2;
    }

    public String getInicioletra() {
        return this.inicioletra;
    }

    public void setInicioletra(String inicioletra2) {
        this.inicioletra = inicioletra2;
    }

    public String getSeparacion() {
        return this.separacion;
    }

    public void setSeparacion(String separacion2) {
        this.separacion = separacion2;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion2) {
        this.direccion = direccion2;
    }

    public String getCoddefault() {
        return this.coddefault;
    }

    public void setCoddefault(String coddefault2) {
        this.coddefault = coddefault2;
    }

    public String getCodigotransp() {
        return this.codigotransp;
    }

    public void setCodigotransp(String codigotransp2) {
        this.codigotransp = codigotransp2;
    }

    public String getNombretransp() {
        return this.nombretransp;
    }

    public void setNombretransp(String nombretransp2) {
        this.nombretransp = nombretransp2;
    }

    public String getDirecccobro() {
        return this.direcccobro;
    }

    public void setDirecccobro(String direcccobro2) {
        this.direcccobro = direcccobro2;
    }

    public String getMonflete() {
        return this.monflete;
    }

    public void setMonflete(String monflete2) {
        this.monflete = monflete2;
    }

    public String getOrdencompra() {
        return this.ordencompra;
    }

    public void setOrdencompra(String ordencompra2) {
        this.ordencompra = ordencompra2;
    }

    public String getFechaorden() {
        return this.fechaorden;
    }

    public void setFechaorden(String fechaorden2) {
        this.fechaorden = fechaorden2;
    }

    public String getObservacion() {
        return this.observacion;
    }

    public void setObservacion(String observacion2) {
        this.observacion = observacion2;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado2) {
        this.estado = estado2;
    }

    public String getTotaligv() {
        return this.totaligv;
    }

    public void setTotaligv(String totaligv2) {
        this.totaligv = totaligv2;
    }

    public String getTotalfacturar() {
        return this.totalfacturar;
    }

    public void setTotalfacturar(String totalfacturar2) {
        this.totalfacturar = totalfacturar2;
    }

    public String getTotalunidad() {
        return this.totalunidad;
    }

    public void setTotalunidad(String totalunidad2) {
        this.totalunidad = totalunidad2;
    }

    public String getDialibre() {
        return this.dialibre;
    }

    public void setDialibre(String dialibre2) {
        this.dialibre = dialibre2;
    }

    public String getZona() {
        return this.zona;
    }

    public void setZona(String zona2) {
        this.zona = zona2;
    }

    public String getRequiereoc() {
        return this.requiereoc;
    }

    public void setRequiereoc(String requiereoc2) {
        this.requiereoc = requiereoc2;
    }

    public String getLatitud() {
        return this.latitud;
    }

    public void setLatitud(String latitud2) {
        this.latitud = latitud2;
    }

    public String getLongitud() {
        return this.longitud;
    }

    public void setLongitud(String longitud2) {
        this.longitud = longitud2;
    }

    public String getTpedido() {
        return this.tpedido;
    }

    public void setTpedido(String tpedido2) {
        this.tpedido = tpedido2;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto2) {
        this.foto = foto2;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getDirecciongps() {
        return direcciongps;
    }

    public void setDirecciongps(String direcciongps) {
        this.direcciongps = direcciongps;
    }
}
