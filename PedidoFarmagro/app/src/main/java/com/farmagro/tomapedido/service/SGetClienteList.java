package com.farmagro.tomapedido.service;

import android.app.Application;

import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.Producto;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SGetClienteList extends RequestGet {
    private Usuario usuario;

    public SGetClienteList(Application app, Usuario usuario2) {
        super(app);
        this.usuario = usuario2;
    }

    public void writeRequest() throws Exception {
        JSONObject json = new JSONObject();
        json.put("iCodVendedor", this.usuario.getClave());

        setOperacionEntidad(json,"/ListarClientePorVendedor","POST");
    }

    public Object readResponse(String result) throws Exception {
        List<Cliente> listClientes = new ArrayList<>();

        this.sqLibraryApp.getDataManager().deleteCliente();
        JSONArray jsonarray = new JSONArray(result);
        for(int i=0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            Cliente bean = new Cliente();
            bean.setCodigo(jsonobject.getString("vCodCliente"));
            bean.setNombre(jsonobject.getString("vNombre"));
            bean.setDireccion(jsonobject.getString("vDireccion"));
            bean.setDirembarque(jsonobject.getString("vDirEmbDefault"));
            bean.setSaldo(jsonobject.getString("dSaldo"));
            bean.setSaldoDolar(jsonobject.getString("dSalDolar"));
            bean.setLimiteCredido(jsonobject.getString("dLimCredito"));
            bean.setCondicionPago(jsonobject.getString("vConPago"));
            bean.setNivelPrecio(jsonobject.getString("vNivPrecio"));
            bean.setDocGenerar(jsonobject.getString("vDocGenerar"));
            bean.setMonedaNivel(jsonobject.getString("vMonNivel"));
            bean.setDiasLibre(jsonobject.getString("vRubCli7"));
            bean.setRubro8(jsonobject.getString("vRubCli8"));
            bean.setRubro9(jsonobject.getString("vRubCli9"));
            bean.setRubro10(jsonobject.getString("vRubCli10"));
            bean.setRequiereOC(jsonobject.getString("vReqOc"));

            bean.setvNomZona(jsonobject.getString("vNomZona"));
            bean.setvEstado(jsonobject.getString("vEstado"));
            bean.setvCobJudicial(jsonobject.getString("vCobJudicial"));
            bean.setdLimCreDolar(jsonobject.getString("dLimCreDolar"));
            bean.setdLimCreLocal(jsonobject.getString("dLimCreLocal"));
            bean.setdLimCreInsDolar(jsonobject.getString("dLimCreInsDolar"));
            bean.setdSalVenDol(jsonobject.getString("dSalVenDol"));
            bean.setiCantLetraPendiente(jsonobject.getString("iCantLetraPendiente"));
            listClientes.add(bean);
        }
        this.sqLibraryApp.getDataManager().saveCliente(listClientes);
        return Integer.valueOf(listClientes.size());
    }
}
