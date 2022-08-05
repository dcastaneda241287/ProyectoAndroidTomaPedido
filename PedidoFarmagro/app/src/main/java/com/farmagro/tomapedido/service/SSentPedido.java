package com.farmagro.tomapedido.service;


import android.app.Application;

import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.DetPedido;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.util.Constante;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SSentPedido extends RequestPostList {
    private Usuario usuario;
    private CabPedido cabPedido;
    private List<DetPedido> lstdet;

    public SSentPedido(Application app, Usuario usuario2, CabPedido cabecerapedido, List<DetPedido> lstdetallepedido) {
        super(app);
        this.usuario = usuario2;
        this.cabPedido = cabecerapedido;
        this.lstdet = lstdetallepedido;
    }

    public void writeRequest() throws Exception {
        int i = 1;
        String codBodega;// = this.usuario.getBodega1().split("-")[0].trim();
        JSONObject json = new JSONObject();
        JSONObject js = new JSONObject();
        JSONArray jsonDet = new JSONArray();
        json.put("vVendedor", this.usuario.getClave());
        json.put("cFecPrometida", cabPedido.getFechaprom());
        json.put("vEmbarcarA", this.cabPedido.getNombcliente());
        json.put("vDirEmbarque", this.cabPedido.getCoddefault());
        json.put("vDirFactura", this.cabPedido.getDirecccobro());
        json.put("vIniPriLetra", this.cabPedido.getInicioletra());
        json.put("vDiaSeparacion", this.cabPedido.getSeparacion());
        json.put("vCanLetras", this.cabPedido.getLetras());
        json.put("vTotMercaderia", this.cabPedido.getTotalmercaderia());
        json.put("vTotImpuesto1", this.cabPedido.getTotaligv());
        json.put("vTotFacturar", this.cabPedido.getTotalfacturar());
        json.put("vTotal_unidades", this.cabPedido.getTotalunidad());
        json.put("cMonPedido", this.cabPedido.getMoneda());
        json.put("vNivPrecio", this.cabPedido.getNivelprecio());
        json.put("vConPago", this.cabPedido.getCondicionpago());
        json.put("vBodega", "B001");//this.cabPedido.getAlmacen());
        json.put("vZona", this.cabPedido.getZona());
        json.put("vCliente", this.cabPedido.getCliente());
        json.put("vTransportista2", this.cabPedido.getCodigotransp());
        json.put("vDiaLibre", this.cabPedido.getDialibre());
        json.put("vOrdCompra", this.cabPedido.getOrdencompra());
        json.put("cFecOrden", this.cabPedido.getFechaorden());
        json.put("vMonFlete", this.cabPedido.getMonflete());
        json.put("iVerNP", 0);
        json.put("cDocGenerar", this.cabPedido.getDocumento());
        json.put("cModPago", this.cabPedido.getTipoventa());
        json.put("cForPago", this.cabPedido.getFormapago());
        json.put("tObservaciones", this.cabPedido.getObservacion());
        json.put("sLatitud", this.cabPedido.getLatitud());
        json.put("sLongitud", this.cabPedido.getLongitud());
        json.put("sFoto", this.cabPedido.getFoto());
        json.put("vCobrador", this.usuario.getCobrador());

        for (DetPedido beanDet : lstdet) {
            js = new JSONObject();
            js.put("vVendedor", this.usuario.getClave());
            js.put("iPedLinea", i);
            js.put("vBodega", beanDet.getCodbodega());
            js.put("vArticulo", beanDet.getCodproducto());
            js.put("vFecPrometida", this.cabPedido.getFechaprom());
            js.put("vPreUnitario", beanDet.getPrecio());
            js.put("vCanPedida", beanDet.getCantidad());
            js.put("vPorDescuento", beanDet.getDescuento());
            js.put("vDescripcion", beanDet.getDesproducto());
            js.put("cEnvMail", "S");
            js.put("vCodPedido", "P000");
            jsonDet.put(js);
            i = i + 1;
        }

        json.put("DetPedido", jsonDet);

        setOperacionEntidad(json,"/InsertarPedido","POST");

    }

    public String readResponse(String result) throws Exception {
        if (result.compareTo(Constante.OK) == 0 || result.indexOf(Constante.OK) > -1) {
            return Constante.OK;
        }
        return Constante.FAIL;
    }

}