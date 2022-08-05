package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.VendedorBodega;
import com.farmagro.tomapedido.modelo.ZonaVendedor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SGetVendedorBodegaList extends RequestGet {
    private Usuario usuario;

    public SGetVendedorBodegaList(Application app, Usuario usuario2) {
        super(app);
        this.usuario = usuario2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("iCodVendedor", usuario.getClave());

        setOperacionEntidad(json,"/ListarVendedorBodega","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<VendedorBodega> listVendedorBodega = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteVendedorBodega();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            VendedorBodega bean = new VendedorBodega();
            bean.setiCodUsu(jsonobject.getString("iCodUsu"));
            bean.setvCodBodega(jsonobject.getString("vCodBodega"));
            bean.setvDescripcion(jsonobject.getString("vDescripcion"));


            listVendedorBodega.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveVendedorBodega(listVendedorBodega);
        return Integer.valueOf(listVendedorBodega.size());
    }
}