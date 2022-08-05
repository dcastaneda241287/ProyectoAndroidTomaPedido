package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.FacturaVendedor;
import com.farmagro.tomapedido.modelo.RespuestaArchivo;
import com.farmagro.tomapedido.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SPosLineaCreditoList extends RequestPostList{
    private Usuario usuario;
    private Cliente cliente;

    public SPosLineaCreditoList(Application app, Usuario usuario2, Cliente cliente2) {
        super(app);
        this.usuario = usuario2;
        this.cliente = cliente2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("sUsario", this.usuario.getClave());
        json.put("sCliente", this.cliente.getCodigo());


        setOperacionEntidad(json,"/ReporteCobranza","POST");
    }

    public String readResponse(String result) throws Exception {
        RespuestaArchivo objRes = new RespuestaArchivo();
        JSONObject jsonobject = new JSONObject(result);
        objRes.setsContenido(jsonobject.getString("sContenido"));
        objRes.setsNomArchivo(jsonobject.getString("sNomArchivo"));
        return "1";
    }
}
