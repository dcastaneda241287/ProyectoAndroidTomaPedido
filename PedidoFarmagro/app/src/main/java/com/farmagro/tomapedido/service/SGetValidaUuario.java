package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.Util;

import org.json.JSONObject;

public class SGetValidaUuario extends RequestGet{
    private Usuario usuario;

    public SGetValidaUuario(Application app, Usuario usuario) {
        super(app);
        this.usuario = usuario;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("vNomUsu", this.usuario.getCodusuario());
        json.put("vContrasena", this.usuario.getClave());

        setOperacionEntidad(json,"/ValidaLogeo","POST");
    }

    public Object readResponse(String result) throws Exception {
        String[] campos = Util.split(result, Constante._TKCAMPO);

        JSONObject myAwway = new JSONObject(result);
        String icodusu = myAwway.getString("iCodUsu");
        String b = myAwway.getString("vNombre");
        String d = myAwway.getString("vNomusu");
        String codCobrador = myAwway.getString("iCodCobrador");
        String codusu = myAwway.getString("vContrasena");

        if (b == "null") {
            return Constante.NO;
        }
        this.sqLibraryApp.getDataManager().deleteUsuario();
        Usuario usuario2 = new Usuario();
        usuario2.setId(String.valueOf("1"));
        usuario2.setCodusuario(d);
        usuario2.setClave(icodusu);
        usuario2.setNombreusuario(b);
        usuario2.setCobrador(codCobrador);
        usuario2.setCodusu(codusu);
        /*usuario2.setCobrador(campos[3]);
        usuario2.setBodega1(campos[6]);

        usuario2.setPuntofactura(campos[10]);
        usuario2.setDespuntofactura(campos[11]);
        usuario2.setDeszona1(campos[12]);
        usuario2.setDesbodega(campos[14]);*/
        usuario2.setFecha(Util.getDate());
        this.sqLibraryApp.getDataManager().saveUsuario(usuario2);
        return "1";
    }
}
