package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.DireccionEmbarque;
import com.farmagro.tomapedido.modelo.RespuestaArchivo;
import com.farmagro.tomapedido.modelo.Usuario;

import org.json.JSONObject;

public class SPOSDireccionEmbarqueList extends RequestPostList {
    private String sCodCliente;

    public SPOSDireccionEmbarqueList(Application app, String sCodCliente2) {
        super(app);
        this.sCodCliente = sCodCliente2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("vCodCliente", sCodCliente);

        setOperacionEntidad(json, "/ListarDireccionEmbarque", "POST");
    }

    public String readResponse(String result) throws Exception {
        DireccionEmbarque objRes = new DireccionEmbarque();
        JSONObject jsonobject = new JSONObject(result);
        objRes.setvCodCliente(jsonobject.getString("vCodCliente"));
        objRes.setvDireccion(jsonobject.getString("vDireccion"));
        objRes.setiDetDireccion(jsonobject.getString("iDetDireccion"));
        objRes.setvDescripcion(jsonobject.getString("vDescripcion"));
        objRes.setvContacto(jsonobject.getString("vContacto"));
        objRes.setvCargo(jsonobject.getString("vCargo"));
        objRes.setvTelefUno(jsonobject.getString("vTelefUno"));
        objRes.setvMail(jsonobject.getString("vMail"));
        objRes.setDtFecCrea(jsonobject.getString("dtFecCrea"));

        return "1";
    }
}
