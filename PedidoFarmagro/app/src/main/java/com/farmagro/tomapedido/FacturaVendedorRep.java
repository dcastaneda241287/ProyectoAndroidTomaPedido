package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.farmagro.tomapedido.adapters.DetallePedidoAdapter;
import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.Cliente;
import com.farmagro.tomapedido.modelo.FacturaVendedor;
import com.farmagro.tomapedido.modelo.TmpDetalle;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.modelo.Zona;
import com.farmagro.tomapedido.modelo.ZonaVendedor;
import com.farmagro.tomapedido.service.SGetFacturaVendedor;
import com.farmagro.tomapedido.service.SGetValidaUuario;
import com.farmagro.tomapedido.util.Constante;
import com.farmagro.tomapedido.util.Framework;
import com.farmagro.tomapedido.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FacturaVendedorRep extends Fragment {
    private static final int SEARCH_PRODUCT_CODE = 9;
    private CabPedido currentPedido;
    private Zona currentZona;
    private Cliente currentCliente;
    private ListView listFactura;
    private List<FacturaVendedor> facturaVendedors;
    /* access modifiers changed from: private */
    public int selected;
    private TextView txtTotal;
    protected SQLibraryApp sqLibraryApp;
    public ProgressDialog progressLogin;
    private TextView lblMensajeDatos;
    private TextView txttotfac;
    private TextView txttotpen;
    private LinearLayout llMensaje;
    private PopupDialogFragment popupDialogFragmentSuccess;
    float itotfac = 0.00f;
    float itotpen = 0.00f;
    Usuario bean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_factura_vendedor, container, false);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
        return rootView;
    }
    public void onViewCreated(View view,
                                   Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_facvendedor));
        this.listFactura = view.findViewById(R.id.listFact);
        this.lblMensajeDatos = view.findViewById(R.id.txtMensajeDatos);
        this.llMensaje = view.findViewById(R.id.llMensajeDatos);
        this.txttotfac = view.findViewById(R.id.txtTotalFact);
        this.txttotpen = view.findViewById(R.id.txtTotalPen);

        setHasOptionsMenu(true);
        registerForContextMenu(this.listFactura);
        bean = FacturaVendedorRep.this.sqLibraryApp.getDataManager().getUsuario();
        //this.lblMensajeDatos.setText("Hola "+ bean.getNombreusuario() + ", no has generado pedidos en los últimos 5 días.");
        this.listFactura.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
                FacturaVendedorRep.this.selected = position;
                return false;
            }
        });
        new FacturaVendedorRep.ServicioFactura().execute(new Void[0]);
    }

    public class ServicioFactura extends AsyncTask<Void, String, Integer> {
        //variables del hilo
        private Context httpContext;//contexto
        ProgressDialog progressDialog;//dialogo cargando
        public String resultadoapi = "";
        public String linkrequestAPI = "";//link  para consumir el servicio rest
        public String susu = "";
        public String spass = "";

        //constructor del hilo (Asynctask)
        public ServicioFactura() {
        }

        protected void onPreExecute() {
            //LoginActivity.this.progressLogin = ProgressDialog.show(LoginActivity.this, "Login", "Validando...", true);
            //LoginActivity.this.progressLogin.show();
            progressLogin = new ProgressDialog(getActivity());
            progressLogin.show();
            progressLogin.setContentView(R.layout.custom_progress_dialog);
            //se ppdrá cerrar simplemente pulsando back
            progressLogin.setCancelable(true);
            progressLogin.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        }


        protected Integer doInBackground(Void... arg0) {
            int i = 1;

            /*Usuario beanUsuario = new Usuario();
            beanUsuario.setCodusuario();
            beanUsuario.setClave(FacturaVendedorRep.this.edt_contrasena.getText().toString());*/

            try {
                String resp = new SGetFacturaVendedor(getActivity().getApplication(), bean).doRequest();
                facturaVendedors = new ArrayList<>();
                JSONArray jsonarray = new JSONArray(resp);
                for (int j = 0; j < jsonarray.length(); j++) {
                    JSONObject jsonobject = jsonarray.getJSONObject(j);
                    FacturaVendedor beanFact = new FacturaVendedor();
                    beanFact.setdFecha(jsonobject.getString("dFecha"));
                    beanFact.setvPedido(jsonobject.getString("vPedido"));
                    beanFact.setvEstado(jsonobject.getString("vEstado"));
                    beanFact.setvFactura(jsonobject.getString("vFactura"));
                    beanFact.setdTotVenta(String.valueOf(Util.formatearDecimales(Float.parseFloat(jsonobject.getString("dTotVenta")),2)));
                    beanFact.setvNomcli(jsonobject.getString("vNomcli"));
                    facturaVendedors.add(beanFact);

                    if(jsonobject.getString("vEstado") == "Por Facturar"){
                        itotpen = Util.formatearDecimales(itotpen + Float.parseFloat(jsonobject.getString("dTotVenta")),2);
                    }else{
                        itotfac = Util.formatearDecimales(itotfac + Float.parseFloat(jsonobject.getString("dTotVenta")),2);
                    }
                }



                if (facturaVendedors.size() == 0) {
                    i = 3;
                }
                /*for (FacturaVendedor facturaV : ()resp) {
                    //Zona beanZona = new Zona();
                    //beanZona.setCodigo(zonavendedor.getvCodZona());
                    //beanZona.setDescripcion(zonavendedor.getvDescripcion());
                    //listZona.add(beanZona);
                    framework = new Framework(0 , zonavendedor.getvDescripcion(),0,zonavendedor.getvCodZona());
                    frameworks.add(framework);
                }
                facturaVendedors =  resp;
                if (resp.toString().equals(Constante.ERROR)) {
                    i = 1;
                } else if (resp.toString().equals(Constante.NO)) {
                    i = 2;
                    System.gc();
                } else {
                    //LoginActivity.this.sqLibraryApp.getDataManager().deleteBodega();
                    i = 3;
                    System.gc();
                }*/
            } catch (Exception e) {
                i = -1;

            } finally {
                System.gc();
            }
            return i;
        }

        protected void onPostExecute(Integer result) {
            if (FacturaVendedorRep.this.progressLogin.isShowing()) {
                FacturaVendedorRep.this.progressLogin.dismiss();
            }
            if (result.intValue() == -1) {

            }
            txttotfac.setText(Util.getTwoDecimals(itotfac));
            txttotpen.setText(Util.getTwoDecimals(itotpen));
            listFactura.setAdapter(new FacturaVendedorAdapter(getActivity(), R.layout.facturavendedor_item, facturaVendedors));
            switch (result.intValue()) {
                case -1:
                    llMensaje.setVisibility(LinearLayout.VISIBLE);
                    lblMensajeDatos.setTextColor(Color.RED);
                    lblMensajeDatos.setText("Sin conexión");

                    probandomensaje("Hola " + bean.getNombreusuario() + ", hemos detectado que no tienes señal ó tu plan de datos no se encuentra activo. La aplicación solo te permitirá generar los pedidos como pendientes.", false);
                    //getActivity().showDialog(2);

                case 1:
                    //probandomensaje("Se encontraron registros", true);
                    return;
                case 3:
                    llMensaje.setVisibility(LinearLayout.VISIBLE);
                    lblMensajeDatos.setText("No se encontraron registros.");
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

    public void mensajepersonalisado(String message) {
        TextView continuar,informacion;
        View alertCustomdialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_popup, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertCustomdialog);
        continuar = (TextView) alertCustomdialog.findViewById(R.id.tv_continue_button);
        informacion = (TextView) alertCustomdialog.findViewById(R.id.tv_information);

        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        informacion.setText(message);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void verifyUsernameSuccess(String message) {
        Button continuar;
        View alertCustomdialog = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_new, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertCustomdialog);
        continuar = (Button)alertCustomdialog.findViewById(R.id.btnOk);

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