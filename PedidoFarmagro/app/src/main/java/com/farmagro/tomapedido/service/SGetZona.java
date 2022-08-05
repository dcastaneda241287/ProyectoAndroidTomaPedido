package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.modelo.ZonaVendedor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetZona extends RequestGet {

    public SGetZona(Application app) {
        super(app);
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        //json.put("iCodTrabajador", usuario.getClave());

        setOperacionEntidad(json,"/ListarZona","GET");
    }

    public Object readResponse(String result) throws Exception {
        List<Zona> listzona = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteZona();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Zona bean = new Zona();
            bean.setCodigo(jsonobject.getString("vCodZona"));
            bean.setDescripcion(jsonobject.getString("vDescripcion"));

            listzona.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveZona(listzona);
        return Integer.valueOf(listzona.size());
    }
}
