package com.farmagro.tomapedido.service;


import android.app.Application;

import com.farmagro.tomapedido.modelo.Contado;
import com.farmagro.tomapedido.modelo.Existencia;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetExistenciaList extends RequestGet {
    public SGetExistenciaList(Application app) {
        super(app);
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        //json.put("cConPagoTipo", "CR");

        setOperacionEntidad(json,"/ListarExistencia","GET");
    }

    public Object readResponse(String result) throws Exception {
        List<Existencia> listExistencias = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteExistencia();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Existencia bean = new Existencia();
            bean.setCodProducto(jsonobject.getString("vCodArticulo"));
            bean.setDescripcion(jsonobject.getString("vDescripcion"));
            bean.setUnidadVenta(jsonobject.getString("vUniVenta"));
            bean.setCantTransito(jsonobject.getString("dCanTransito"));
            bean.setdCanDisponible(jsonobject.getString("dCanDisponible"));
            bean.setvCodBodega(jsonobject.getString("vCodBodega"));
            listExistencias.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveExistencia(listExistencias);
        return Integer.valueOf(listExistencias.size());
    }
}
