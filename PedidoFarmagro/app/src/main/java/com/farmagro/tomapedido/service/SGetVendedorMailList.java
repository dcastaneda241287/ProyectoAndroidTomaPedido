package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.VendedorCorrelativo;
import com.farmagro.tomapedido.modelo.VendedorMail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetVendedorMailList extends RequestGet {
    private Usuario usuario;

    public SGetVendedorMailList(Application app, Usuario usuario2) {
        super(app);
        this.usuario = usuario2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("iCodVendedor", usuario.getClave());

        setOperacionEntidad(json,"/ListarVendedorMail","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<VendedorMail> listVendedorMail = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteVendedorMail();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            VendedorMail bean = new VendedorMail();
            bean.setiCodUsu(jsonobject.getString("iCodUsu"));
            bean.setvMail(jsonobject.getString("vMail"));

            listVendedorMail.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveVendedorMail(listVendedorMail);
        return Integer.valueOf(listVendedorMail.size());
    }
}