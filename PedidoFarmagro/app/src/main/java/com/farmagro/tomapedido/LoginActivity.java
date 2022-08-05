package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.util.Constante;

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

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import com.farmagro.tomapedido.service.SGetValidaUuario;
import com.farmagro.tomapedido.util.Util;


public class LoginActivity extends BaseActivity {
    public Button btn_ir_crearCuenta,iniciar_sesion2;
    public EditText edt_usu, edt_contrasena;
    public ProgressDialog progressLogin;
    //protected SQLibraryApp sqLibraryApp;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        this.edt_usu = findViewById(R.id.et_usuario);
        this.edt_contrasena = findViewById(R.id.et_contrasena);
        //this.sqLibraryApp = (SQLibraryApp) getApplication();

        Util.createDirIfNotExists("farmagro");
        this.iniciar_sesion2= findViewById(R.id.iniciar_sesion);

        this.iniciar_sesion2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //String url = "http://192.168.100.18/ServicioGestionMovil/ServicioGestionMovil.svc/ValidaLogeo";
                //String res;
                //LeerWs();
                //LeerWs(edt_usu.getText().toString(), edt_contrasena.getText().toString(), url);
                //Toast.makeText(LoginActivity.this, "Bienvendio Usuario:" + res, Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(LoginActivity.this,MenuPrincipal.class));
                //finish();
                String aa,bb,cc,dd;
                aa = (edt_usu.getText().toString() +"-"+ edt_contrasena.getText().toString()).toUpperCase();
                bb = edt_contrasena.getText().toString();
                if (LoginActivity.this.edt_usu.getText().toString().trim().equals("")) {
                    LoginActivity.this.showDialog(1);
                } else if (LoginActivity.this.edt_contrasena.getText().toString().trim().equals("")) {
                    LoginActivity.this.showDialog(2);
                } else {
                    Usuario bean = LoginActivity.this.sqLibraryApp.getDataManager().getUsuario();
                    if (bean == null) {
                        new ServicioSeguridad().execute(new Void[0]);
                    } else {
                        cc = (bean.getCodusuario() + "-" + bean.getCodusu()).toUpperCase();
                        dd = bean.getCodusu();
                        if (aa.equals(cc)) {
                            LoginActivity.this.showDialog(6);
                        }
                        else
                        {
                            new ServicioSeguridad().execute(new Void[0]);
                        }
                    }
                }

            }
        });

    }


    public class ServicioSeguridad extends AsyncTask<Void, String, Integer> {
        //variables del hilo
        private Context httpContext;//contexto
        ProgressDialog progressDialog;//dialogo cargando
        public String resultadoapi="";
        public String linkrequestAPI="";//link  para consumir el servicio rest
        public String susu="";
        public String spass="";
        //constructor del hilo (Asynctask)
        public ServicioSeguridad(){
        }

        protected void onPreExecute() {
            //LoginActivity.this.progressLogin = ProgressDialog.show(LoginActivity.this, "Login", "Validando...", true);
            //LoginActivity.this.progressLogin.show();
            progressLogin = new ProgressDialog(LoginActivity.this);
            progressLogin.show();
            progressLogin.setContentView(R.layout.custom_progress_dialog);
            //se ppdrá cerrar simplemente pulsando back
            progressLogin.setCancelable(true);
            progressLogin.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        }


        protected Integer doInBackground(Void... arg0) {
            int i;
            Usuario beanUsuario = new Usuario();
            beanUsuario.setCodusuario(LoginActivity.this.edt_usu.getText().toString());
            beanUsuario.setClave(LoginActivity.this.edt_contrasena.getText().toString());
            try {
                Object resp = new SGetValidaUuario(LoginActivity.this.getApplication(), beanUsuario).doRequest();
                if (resp.toString().equals(Constante.ERROR)) {
                    i = 1;
                } else if (resp.toString().equals(Constante.NO)) {
                    i = 2;
                    System.gc();
                } else {
                    //LoginActivity.this.sqLibraryApp.getDataManager().deleteBodega();
                    i = 3;
                    System.gc();
                }
            } catch (Exception e) {
                i = -1;
            } finally {
                System.gc();
            }
            return i;
        }
        protected void onPostExecute(Integer result) {
            if (LoginActivity.this.progressLogin.isShowing()) {
                LoginActivity.this.progressLogin.dismiss();
            }
            switch (result.intValue()) {
                case -1:
                    LoginActivity.this.showDialog(5);
                    return;
                case 1:
                    LoginActivity.this.showDialog(4);
                    return;
                case 2:
                    LoginActivity.this.showDialog(3);
                    return;
                case 3:
                    LoginActivity.this.showDialog(6);
                    return;
                default:
                    return;
            }
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

        /*static String convertStreamToString(InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }*/
    }

    /*private void LeerWs(String sNomUsu, String sContrasena, String wsURL) {
        ServicioSeguridad servicioTask = new ServicioSeguridad(this, wsURL, sNomUsu,sContrasena);
        servicioTask.execute();
    }*/

    private String leerWSEjem(String sNomUsu, String sContrasena, String wsURL) {
        String result;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            JSONObject json = new JSONObject();
            json.put("vNomUsu", sNomUsu);
            json.put("vContrasena", sContrasena);

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
            Double d = myAwway.getDouble("vNombre");

            result = new String("Respuesta: NomUsu" + b + " Nombre " + d.toString());
            //tvrespuesta.setText(result);

        } catch (Throwable t) {
            result = new String("Error: " + t.toString());
        }
        return result;
    }

    static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_alert_dialog)
                        .setTitle(R.string.lblMensaje)
                        .setMessage(R.string.lblUserMissing)
                        .setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 2:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblPwdMissing).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 3:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblUserFail).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 4:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblErrorConnection).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 5:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblErrorApp).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 6:
                //Usuario beanUsuario = this.sqLibraryApp.getDataManager().getUsuario();
                //beanUsuario.getNombreusuario()
                // + "\nPunto de facturacion:" + beanUsuario.getDespuntofactura()
                Intent intent = new Intent(LoginActivity.this, MenuAlterno.class);
                intent.putExtra("page", 4);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
                /*return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage("Bienvenido:\n" + beanUsuario.getNombreusuario()).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(LoginActivity.this, MenuAlterno.class);
                        intent.putExtra("page", 4);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        LoginActivity.this.startActivity(intent);
                        LoginActivity.this.finish();


                    }
                }).create();*/
            default:
                return null;
        }
    }

}
