package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.FacturaVendedor;
import com.farmagro.tomapedido.modelo.Usuario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetFacturaVendedor extends RequestPostList{
    private Usuario usuario;

    public SGetFacturaVendedor(Application app, Usuario usuario2 ) {
        super(app);
        this.usuario = usuario2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("vVendedor", this.usuario.getClave());
        json.put("dFecIni", "20220511");
        json.put("dFecFin", "20220511");

        setOperacionEntidad(json,"/ListarFacturaVendedor","POST");
    }

    public String readResponse(String result) throws Exception {
        List<FacturaVendedor> listFact = new ArrayList<>();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            FacturaVendedor bean = new FacturaVendedor();
            bean.setdFecha(jsonobject.getString("dFecha"));
            bean.setvPedido(jsonobject.getString("vPedido"));
            bean.setvEstado(jsonobject.getString("vEstado"));
            bean.setvFactura(jsonobject.getString("vFactura"));
            bean.setdTotVenta(jsonobject.getString("dTotVenta"));
            bean.setvNomcli(jsonobject.getString("vNomcli"));
            listFact.add(bean);
        }
        //return Integer.valueOf(listFact.size());
        return "1";
    }
}
