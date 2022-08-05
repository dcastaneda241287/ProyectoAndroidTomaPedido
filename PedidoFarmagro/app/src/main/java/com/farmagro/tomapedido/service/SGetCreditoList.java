package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Contado;
import com.farmagro.tomapedido.modelo.Credito;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetCreditoList extends RequestGet {
    public SGetCreditoList(Application app) {
        super(app);
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("cConPagoTipo", "CR");

        setOperacionEntidad(json,"/ListarCondicionPagoPorTipo","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<Credito> listCreditos = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteCredito();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Credito bean = new Credito();
            bean.setCodcondicion(jsonobject.getString("vCodConPago"));
            bean.setDescondicion(jsonobject.getString("vDescripcion"));
            bean.setDiasneto(jsonobject.getString("vDiaNeto"));
            listCreditos.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveCredito(listCreditos);
        return Integer.valueOf(listCreditos.size());
    }
}
