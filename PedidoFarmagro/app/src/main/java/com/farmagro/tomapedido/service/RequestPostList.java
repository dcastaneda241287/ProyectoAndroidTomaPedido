package com.farmagro.tomapedido.service;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.RespuestaService;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.util.Constante;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class RequestPostList extends Thread {
    private Application application;
    private String ipServer = Constante.IP;
    private String operation;
    private Usuario objusuario;
    private String wsURL;
    protected SQLibraryApp sqLibraryApp;
    private String wsMetodo;
    private String wsTipo;
    private JSONObject json;

    public abstract String readResponse(String str) throws Exception;

    public abstract void writeRequest() throws Exception;

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation2) {
        this.operation = operation2;
    }

    public Usuario getOperacionEntidad() {
        return this.objusuario;
    }

    public void setOperacionEntidad(JSONObject entidad, String wsmetodo, String wstipo) {
        this.json = entidad;
        this.wsMetodo = wsmetodo;
        this.wsTipo = wstipo;
    }

    private String getServerUrl() {
        return this.ipServer;
    }

    public RequestPostList(Application app) {
        this.application = app;
        this.sqLibraryApp = (SQLibraryApp) this.application;
    }

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = ((ConnectivityManager) this.application.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    public String doRequest() throws IOException {
        InputStream instream = null;
        /*List<RespuestaService> lstResp = new ArrayList<>();
        RespuestaService objResp = new RespuestaService();*/

        if (!isNetworkAvailable()) {
            /*objResp.setIcodrespuesta(0);
            objResp.setsRespuesta(Constante.ERROR);
            lstResp.add(objResp);*/
            return Constante.ERROR;
        }

        try {
            writeRequest();
            if(this.wsTipo == "POST"){
                instream = InvocaServicioPost();
            }else if(this.wsTipo == "GET"){
                instream = InvocaServicioGet();
            }

            if (instream != null) {
                return convertStreamToString(instream);
            }
            return Constante.ERROR;
        } catch (Exception e) {
            return Constante.ERROR;
        }
    }

    private InputStream InvocaServicioGet(){
        InputStream instr = null;
        try {
            //writeRequest();
            Usuario obj = new Usuario();
            obj = getOperacionEntidad();

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 6000);
            HttpConnectionParams.setSoTimeout(httpParameters, 300000);
            HttpClient client = new DefaultHttpClient(httpParameters);

            instr = client.execute(new HttpGet(String.valueOf(Constante.IP + this.wsMetodo))).getEntity().getContent();

        }catch (Exception e) {

        }
        return instr;
    }

    private InputStream InvocaServicioPost(){
        InputStream instr = null;
        String a;
        try {
            writeRequest();
            Usuario obj = new Usuario();
            obj = getOperacionEntidad();

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 6000);
            HttpConnectionParams.setSoTimeout(httpParameters, 300000);
            HttpClient client = new DefaultHttpClient(httpParameters);

            HttpPost request = new HttpPost(Constante.IP + this.wsMetodo);


            ByteArrayEntity entity = new ByteArrayEntity(this.json.toString().getBytes("UTF-8"));
            entity.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            entity.setContentType("application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            HttpEntity entityReturn = response.getEntity();
            instr = entityReturn.getContent();
            //String str = convertInputStreamToString(instream);


        }catch (Exception e) {
            a = e.toString();
        }
        return instr;
    }


    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String result = "";
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                inputStream.close();
                return result;
            }
            result = String.valueOf(result) + line;
        }
    }
    private static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
