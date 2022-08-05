package com.farmagro.tomapedido.service;
import android.app.Application;

import com.farmagro.tomapedido.modelo.Existencia;
import com.farmagro.tomapedido.modelo.Proveedor;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.ZonaVendedor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetZonaPorVendedor extends RequestGet {
    private Usuario usuario;

    public SGetZonaPorVendedor(Application app, Usuario usuario2) {
        super(app);
        this.usuario = usuario2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("iCodTrabajador", usuario.getClave());

        setOperacionEntidad(json,"/ListarZonaVendedor","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<ZonaVendedor> listzonavendedor = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteZonaVendedor();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            ZonaVendedor bean = new ZonaVendedor();
            bean.setiCodUsu(jsonobject.getString("iCodUsu"));
            bean.setvDescripcion(jsonobject.getString("vDescripcion"));
            bean.setvCodZona(jsonobject.getString("vCodZona"));
            bean.setvNombre(jsonobject.getString("vNombre"));

            listzonavendedor.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveZonaVendedor(listzonavendedor);
        return Integer.valueOf(listzonavendedor.size());
    }
}
