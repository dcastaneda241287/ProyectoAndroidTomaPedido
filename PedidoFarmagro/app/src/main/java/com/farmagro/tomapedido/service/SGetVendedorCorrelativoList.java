package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.VendedorBodega;
import com.farmagro.tomapedido.modelo.VendedorCorrelativo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SGetVendedorCorrelativoList extends RequestGet {
    private Usuario usuario;

    public SGetVendedorCorrelativoList(Application app, Usuario usuario2) {
        super(app);
        this.usuario = usuario2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("iCodVendedor", usuario.getClave());

        setOperacionEntidad(json,"/ListarVendedorCorrelativo","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<VendedorCorrelativo> listVendedorCorrelativo = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteVendedorCorrelativo();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            VendedorCorrelativo bean = new VendedorCorrelativo();
            bean.setiCodUsu(jsonobject.getString("iCodUsu"));
            bean.setvSerie(jsonobject.getString("vSerie"));
            bean.setiCorrelativo(jsonobject.getString("iCorrelativo"));


            listVendedorCorrelativo.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveVendedorCorrelativo(listVendedorCorrelativo);
        return Integer.valueOf(listVendedorCorrelativo.size());
    }
}