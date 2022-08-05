package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Existencia;
import com.farmagro.tomapedido.modelo.Proveedor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetProveedorList extends RequestGet {
    public SGetProveedorList(Application app) {
        super(app);
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        //json.put("cConPagoTipo", "CR");

        setOperacionEntidad(json,"/ListarProveedor","GET");
    }

    public Object readResponse(String result) throws Exception {
        List<Proveedor> listProveedores = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteProveedor();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Proveedor bean = new Proveedor();
            bean.setCodigo(jsonobject.getString("vCodProveedor"));
            bean.setDescripcion(jsonobject.getString("vNombre"));
            bean.setvRuc(jsonobject.getString("vRuc"));

            listProveedores.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveProveedor(listProveedores);
        return Integer.valueOf(listProveedores.size());
    }
}

