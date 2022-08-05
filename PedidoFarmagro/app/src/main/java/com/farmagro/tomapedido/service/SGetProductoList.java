package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Producto;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.service.RequestGet;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetProductoList extends RequestGet {
    private Usuario usuario;

    public SGetProductoList(Application app, Usuario usuario2) {
        super(app);
        this.usuario = usuario2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("iCodVendedor", this.usuario.getClave());

        setOperacionEntidad(json,"/ListarExistenciaPorVendedor","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<Producto> listProductos = new ArrayList<>();
        this.sqLibraryApp.getDataManager().deleteProducto();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Producto bean = new Producto();
            bean.setCodproducto(jsonobject.getString("vCodArticulo"));
            bean.setDescripcion(jsonobject.getString("vDescripcion"));
            bean.setStockproducto(jsonobject.getString("dCanDisponible"));
            bean.setPreciodol(jsonobject.getString("dPreD"));
            bean.setPreciosol(jsonobject.getString("dPreSol"));
            bean.setIgvdol(jsonobject.getString("dIgvD"));
            bean.setIgvsol(jsonobject.getString("dIgvS"));
            bean.setUnidadventa(jsonobject.getString("vUniVenta"));
            bean.setPreciorealdol(jsonobject.getString("dPreDReal"));
            bean.setPreciorealsol(jsonobject.getString("dPreSolReal"));
            bean.setTipo(jsonobject.getString("vTipo"));
            bean.setvCodBodega(jsonobject.getString("vCodBodega"));
            listProductos.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveProducto(listProductos);
        return Integer.valueOf(listProductos.size());
    }
}
