package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Bodega;
import com.farmagro.tomapedido.modelo.Contado;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetContadoList extends RequestGet {
    public SGetContadoList(Application app) {
        super(app);
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("cConPagoTipo", "CO");

        setOperacionEntidad(json,"/ListarCondicionPagoPorTipo","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<Contado> listContados = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteContado();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Contado bean = new Contado();
            bean.setCodcondicion(jsonobject.getString("vCodConPago"));
            bean.setDescondicion(jsonobject.getString("vDescripcion"));
            bean.setDiasneto(jsonobject.getString("vDiaNeto"));
            listContados.add(bean);
        }

        this.sqLibraryApp.getDataManager().saveContado(listContados);
        return Integer.valueOf(listContados.size());
    }
}

