package com.farmagro.tomapedido;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class ServicioSeguridad extends AsyncTask<Void, Void, String> {
    //variables del hilo
    private Context httpContext;//contexto
    ProgressDialog progressDialog;//dialogo cargando
    public String resultadoapi="";
    public String linkrequestAPI="";//link  para consumir el servicio rest
    public String susu="";
    public String spass="";
    //constructor del hilo (Asynctask)
    public ServicioSeguridad(Context ctx, String linkAPI, String susuario, String scontrasena){
        this.httpContext=ctx;
        this.linkrequestAPI=linkAPI;
        this.susu = susuario;
        this.spass = scontrasena;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(httpContext, "Procesando Solicitud", "por favor, espere");
    }

    @Override
    protected String doInBackground(Void... params) {
        String result= null;

        String wsURL = linkrequestAPI;
        URL url = null;

        try {

            JSONObject json = new JSONObject();
            json.put("vNomUsu", susu);
            json.put("vContrasena", spass);

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
            HttpConnectionParams.setSoTimeout(httpParams, 30000);
            HttpClient client = new DefaultHttpClient(httpParams);

            HttpPost request = new HttpPost(wsURL);

            ByteArrayEntity entity = new ByteArrayEntity(json.toString().getBytes("UTF-8"));
            entity.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            entity.setContentType("application/json");
            request.setEntity(entity);

            HttpResponse response = client.execute(request);
            HttpEntity entityReturn = response.getEntity();
            InputStream instream = entityReturn.getContent();
            String str = convertStreamToString(instream);

            JSONObject myAwway = new JSONObject(str);
            String b = myAwway.getString("vNomusu");
            String d = myAwway.getString("vNombre");

            result = new String("Respuesta: NomUsu" + b + " Nombre " + d.toString());
            //tvrespuesta.setText(result);

        } catch (Throwable t) {
            result= new String("Error: "+ t.toString());
        }
        return  result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        resultadoapi=s;
        Toast.makeText(httpContext,resultadoapi,Toast.LENGTH_LONG).show();//mostrara una notificacion con el resultado del request
    }

    //FUNCIONES----------------------------------------------------------------------
    //Transformar JSON Obejct a String *******************************************
    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

    static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
