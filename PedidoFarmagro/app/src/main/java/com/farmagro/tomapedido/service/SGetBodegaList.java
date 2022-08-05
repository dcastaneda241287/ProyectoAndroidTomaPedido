package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Bodega;
import com.farmagro.tomapedido.modelo.Producto;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetBodegaList extends RequestGet {
    public SGetBodegaList(Application app) {
        super(app);
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        //json.put("iCodVendedor", this.usuario.getClave());

        setOperacionEntidad(json,"/ListarBodega","GET");
    }

    public Object readResponse(String result) throws Exception {
        List<Bodega> listBodegas = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteBodega();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Bodega bean = new Bodega();
            bean.setvCodbodega(jsonobject.getString("vCodbodega"));
            bean.setNombre(jsonobject.getString("vNombre"));
            listBodegas.add(bean);
        }

        /*List<Bodega> listBodegas = new ArrayList<>();
        String[] registros = Util.split(result, Constante._TKREGISTRO);
        this.sqLibraryApp.getDataManager().deleteBodega();
        for (String split : registros) {
            String[] campos = Util.split(split, Constante._TKCAMPO);
            if (campos.length == 2) {
                Bodega bean = new Bodega();
                bean.setNombre(campos[0]);
                listBodegas.add(bean);
            }
        }*/
        this.sqLibraryApp.getDataManager().saveBodega(listBodegas);
        return Integer.valueOf(listBodegas.size());
    }
}
