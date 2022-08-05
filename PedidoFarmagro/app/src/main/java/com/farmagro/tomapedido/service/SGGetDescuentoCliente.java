package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.DescuentoCliente;
import com.farmagro.tomapedido.modelo.Zona;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGGetDescuentoCliente extends RequestGet {

    public SGGetDescuentoCliente(Application app) {
        super(app);
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        //json.put("iCodTrabajador", usuario.getClave());

        setOperacionEntidad(json,"/ListarDescuentoCliente","GET");
    }

    public Object readResponse(String result) throws Exception {
        List<DescuentoCliente> lstdescliente = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteDescuentoCliente();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            DescuentoCliente bean = new DescuentoCliente();
            bean.setiCodDescuento(jsonobject.getString("iCodDescuento"));
            bean.setvCodCliente(jsonobject.getString("vCodCliente"));
            bean.setvDescripcion(jsonobject.getString("vDescripcion"));
            bean.setDeDescuento(jsonobject.getString("deDescuento"));
            lstdescliente.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveDescuentoCliente(lstdescliente);
        return Integer.valueOf(lstdescliente.size());
    }
}