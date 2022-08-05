package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.FacturaVendedor;
import com.farmagro.tomapedido.modelo.RespuestaArchivo;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.service.SGetFacturaVendedor;
import com.farmagro.tomapedido.service.SPosLineaCreditoList;
import com.farmagro.tomapedido.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class LineaCreditoActivity extends Fragment {

    private TextView txtruc;
    private TextView txtdireccion;
    private TextView txtzona;
    private TextView txtestado;
    private TextView txtcobjudicial;
    private TextView txtcondpago;
    private TextView txtletrapendiente;
    private TextView txtlimitecredito;
    private TextView txtsaldo;
    private TextView lblmoneda;
    private TextView txtnomcli;
    private TextView lblmonedalm;
    private Button btndescargar;
    public ProgressDialog progressLineaCredito;
    private Cliente currentCliente;
    private RespuestaArchivo respuestaArchivo;
    public String path = "";
    public String carpeta = "/Comrobante/";
    protected SQLibraryApp sqLibraryApp;

    Usuario bean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_linea_creadito, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }

    public void onViewCreated(View view,
                              Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_linea_credtio));
        this.currentCliente = (Cliente) getArguments().get("currentCliente");
        this.txtruc = view.findViewById(R.id.txtRuc);
        this.txtdireccion = view.findViewById(R.id.txtDirLinCred);
        this.txtzona = view.findViewById(R.id.txtZona);
        this.txtestado = view.findViewById(R.id.txtEstado);
        this.txtcobjudicial = view.findViewById(R.id.txtCobJudicial);
        this.txtcondpago = view.findViewById(R.id.txtConPago);
        this.txtletrapendiente = view.findViewById(R.id.txtLetraPendiente);
        this.txtlimitecredito = view.findViewById(R.id.txtLimiteCredito);
        this.txtsaldo = view.findViewById(R.id.txtSaldo);
        this.lblmoneda = view.findViewById(R.id.lblmoneda);
        this.txtnomcli = view.findViewById(R.id.txtNomCliente);
        this.lblmonedalm = view.findViewById(R.id.lblMonedaLM);
        this.btndescargar = view.findViewById(R.id.btnDescargar);

        if (this.currentCliente.getMonedaNivel().equals("D")) {
            this.lblmoneda.setText("USD ");
            this.lblmonedalm.setText("USD ");
        } else {
            this.lblmoneda.setText("S/ ");
            this.lblmonedalm.setText("S/ ");
        }
        this.txtnomcli.setText(this.currentCliente.getNombre());
        this.txtruc.setText(this.currentCliente.getCodigo());
        this.txtdireccion.setText(this.currentCliente.getDireccion());
        this.txtzona.setText(this.currentCliente.getvNomZona());
        this.txtestado.setText(this.currentCliente.getvEstado());
        this.txtcobjudicial.setText(this.currentCliente.getvCobJudicial());
        this.txtcondpago.setText(this.currentCliente.getCondicionPago());
        this.txtletrapendiente.setText(this.currentCliente.getiCantLetraPendiente());
        this.txtlimitecredito.setText(Util.getTwoDecimals(Float.parseFloat(this.currentCliente.getLimiteCredido())));
        this.txtsaldo.setText(Util.getTwoDecimals(Float.parseFloat(this.currentCliente.getSaldo())));
        bean = LineaCreditoActivity.this.sqLibraryApp.getDataManager().getUsuario();
        this.btndescargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ServicioDescargaReporte().execute(new Void[0]);
            }
        });
    }
    public class ServicioDescargaReporte extends AsyncTask<Void, String, Integer> {
        //variables del hilo
        private Context httpContext;//contexto
        ProgressDialog progressDialog;//dialogo cargando
        public String resultadoapi = "";
        public String linkrequestAPI = "";//link  para consumir el servicio rest
        public String susu = "";
        public String spass = "";

        //constructor del hilo (Asynctask)
        public ServicioDescargaReporte() {
        }

        protected void onPreExecute() {
            //LoginActivity.this.progressLineaCredito = ProgressDialog.show(LoginActivity.this, "Login", "Validando...", true);
            //LoginActivity.this.progressLineaCredito.show();
            progressLineaCredito = new ProgressDialog(getActivity());
            progressLineaCredito.show();
            progressLineaCredito.setContentView(R.layout.custom_progress_dialog);
            //se ppdrá cerrar simplemente pulsando back
            progressLineaCredito.setCancelable(true);
            progressLineaCredito.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        }


        protected Integer doInBackground(Void... arg0) {
            int i = 1;

            /*Usuario beanUsuario = new Usuario();
            beanUsuario.setCodusuario();
            beanUsuario.setClave(FacturaVendedorRep.this.edt_contrasena.getText().toString());*/

            try {
                String resp = new SPosLineaCreditoList(getActivity().getApplication(), bean, currentCliente).doRequest();
                respuestaArchivo = new RespuestaArchivo();
                JSONObject jsonobject = new JSONObject(resp);

                respuestaArchivo.setsContenido(jsonobject.getString("sContenido"));
                respuestaArchivo.setsNomArchivo(jsonobject.getString("sNomArchivo"));

                if (respuestaArchivo.getsNomArchivo().equals("")) {
                    i = 3;
                }
            } catch (Exception e) {
                i = -1;

            } finally {
                System.gc();
            }
            return i;
        }

        protected void onPostExecute(Integer result) {
            if (LineaCreditoActivity.this.progressLineaCredito.isShowing()) {
                LineaCreditoActivity.this.progressLineaCredito.dismiss();
            }
            if (result.intValue() == -1) {

            }
            byte[] pdf = Base64.decode(respuestaArchivo.getsContenido(), 0);
            String unused = LineaCreditoActivity.this.path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + LineaCreditoActivity.this.carpeta;
            try {
                if ("mounted".equals(Environment.getExternalStorageState())) {
                    File localfile = new File(LineaCreditoActivity.this.path);
                    if (!localfile.exists()) {
                        localfile.mkdir();
                    }
                    if (LineaCreditoActivity.this.path != null) {
                        OutputStream file = new BufferedOutputStream(new FileOutputStream(new File(LineaCreditoActivity.this.path, respuestaArchivo.getsNomArchivo()).getPath()));
                        file.write(pdf);
                        file.close();
                    }
                }
            } catch (Exception e)
            {
            }
            LineaCreditoActivity.this.viewPdf(pdf);
            switch (result.intValue()) {
                case -1:
                    probandomensaje("Hola " + bean.getNombreusuario() + ", hemos detectado que no tienes señal ó tu plan de datos no se encuentra activo. La aplicación solo te permitirá generar los pedidos como pendientes.", false);
                    //getActivity().showDialog(2);

                case 1:
                    //probandomensaje("Se encontraron registros", true);
                    return;
                case 3:
                    informativo("Hola " + bean.getNombreusuario() + ", no has generado pedidos en los últimos 5 días.");
                    return;
                default:

                    return;
            }
        }


        /*public Dialog onCreateDialog(int id) {
            switch (id) {
                case 2:
                    return new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.ic_alert_dialog)
                            .setTitle(R.string.lblMensaje)
                            .setMessage("Hola " + bean.getNombreusuario() + ", hemos detectado que no tienes señal ó tu plan de datos no se encuentra activo. La aplicación sólo te permitirá generar los pedidos como pendientes.")
                            .setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            }).create();
                default:
                    return null;
            }
        }*/

        /*static String convertStreamToString(InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }*/
    }
    public void viewPdf(byte[] pdf) {
        Intent intent = new Intent(getActivity(), PdfActivity.class);
        intent.putExtra("pdf", pdf);
        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        this.getActivity().startActivity(intent);
    }

    public void probandomensaje(String message, boolean correct) {
        PopupDialogFragment popupDialogFragment = new PopupDialogFragment();
        popupDialogFragment.setMessage(message);
        popupDialogFragment.setCorrect(correct);
        popupDialogFragment.show(getActivity().getSupportFragmentManager(), "PopDialog");
    }
    public void informativo(String message) {
        TextView continuar,informacion;
        View alertCustomdialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_popup_informatico, null);

        continuar = (TextView) alertCustomdialog.findViewById(R.id.tv_continue_button);
        informacion = (TextView) alertCustomdialog.findViewById(R.id.tv_information);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertCustomdialog);
        informacion.setText(message);
        final AlertDialog dialog = alert.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        /*PopupDialogFragment popupDialogFragment = new PopupDialogFragment();
        popupDialogFragment.setMessage(message);
        popupDialogFragment.setCorrect(true);
        popupDialogFragment.show(getActivity().getSupportFragmentManager(), "PopDialog");*/

        /*popupDialogFragmentSuccess = new PopupDialogFragment();
        popupDialogFragmentSuccess.setMessage(message);
        popupDialogFragmentSuccess.setCorrect(true);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.ll_factura_rep, popupDialogFragmentSuccess).addToBackStack(popupDialogFragmentSuccess.getTag()).commit();*/

    }
}