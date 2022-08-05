package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Credito;
import com.farmagro.tomapedido.modelo.Letra;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetLetraList extends RequestGet {
    public SGetLetraList(Application app) {
        super(app);
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("cConPagoTipo", "CL");

        setOperacionEntidad(json,"/ListarCondicionPagoPorTipo","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<Letra> listLetras = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteLetra();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Letra bean = new Letra();
            bean.setCodcondicion(jsonobject.getString("vCodConPago"));
            bean.setDescondicion(jsonobject.getString("vDescripcion"));
            bean.setDiasneto(jsonobject.getString("vDiaNeto"));
            listLetras.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveLetra(listLetras);
        return Integer.valueOf(listLetras.size());
    }
}
