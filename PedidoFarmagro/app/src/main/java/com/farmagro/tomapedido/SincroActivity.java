package com.farmagro.tomapedido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.farmagro.tomapedido.application.SQLibraryApp;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.service.RequestGet;
import com.farmagro.tomapedido.service.SGGetDescuentoCliente;
import com.farmagro.tomapedido.service.SGetBodegaList;
import com.farmagro.tomapedido.service.SGetClienteList;
import com.farmagro.tomapedido.service.SGetContadoList;
import com.farmagro.tomapedido.service.SGetCreditoList;
import com.farmagro.tomapedido.service.SGetExistenciaList;
import com.farmagro.tomapedido.service.SGetLetraList;
import com.farmagro.tomapedido.service.SGetProductoList;
import com.farmagro.tomapedido.service.SGetProveedorList;
import com.farmagro.tomapedido.service.SGetVendedorBodegaList;
import com.farmagro.tomapedido.service.SGetVendedorCorrelativoList;
import com.farmagro.tomapedido.service.SGetVendedorMailList;
import com.farmagro.tomapedido.service.SGetZona;
import com.farmagro.tomapedido.service.SGetZonaPorVendedor;
import com.farmagro.tomapedido.util.Constante;

import java.util.ArrayList;
import java.util.List;

public class SincroActivity extends Fragment {

    private static final int ERROR_APP = 2;
    private static final int ERROR_CONEXION = 1;
    private static final int FALTA_SELECCIONAR = 4;
    private static final int SINCRO_SUCCESS = 3;
    private boolean flag;
    /* access modifiers changed from: private */
    public ListView listSincro;
    private List<String> menu;
    /* access modifiers changed from: private */
    protected SQLibraryApp sqLibraryApp;
    public ProgressDialog progressProcess;

    public static final String ARG_ARTICLES_NUMBER = "articles_number";

    public SincroActivity() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_sincronizacion, container, false);

        //int i = getArguments().getInt(ARG_ARTICLES_NUMBER);
        //String article = getResources().getStringArray(R.array.Tags)[i];

        //TextView headline = (TextView) rootView.findViewById(R.id.headline);
        //headline.append(" " + article);
        //this.getActivity().setTitle((CharSequence) "SINCRONIZACION");
        this.getActivity().setTitle((CharSequence) getString(R.string.title_bar_sincro));
        this.listSincro = rootView.findViewById(R.id.listSincro);
        this.listSincro.setChoiceMode(2);
        this.menu = new ArrayList();
        this.menu.add(Constante.PRODUCTOS);
        this.menu.add(Constante.CLIENTES);
        this.menu.add(Constante.FORMA_PAGO);
        this.menu.add(Constante.PROVEEDORES);
        this.menu.add(Constante.ZONA);
        this.menu.add(Constante.CONFVENDEDOR);
        this.menu.add(Constante.DESCUENTOCLIENTE);
        this.listSincro.setAdapter(new ArrayAdapter<>(getContext(), R.layout.sincro_item, this.menu));
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sqLibraryApp = ((SQLibraryApp) getActivity().getApplication());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sincro, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onResume() {
        super.onResume();

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcSincro) {
            new SincroActivity.SincroTask().execute(new Void[0]);
        }
        if (item.getItemId() == R.id.opcCheckall) {
            for (int i = 0; i < this.listSincro.getCount(); i++) {
                if (this.flag) {
                    this.listSincro.setItemChecked(i, false);
                } else {
                    this.listSincro.setItemChecked(i, true);
                }
            }
            if (this.flag) {
                this.flag = false;
            } else {
                this.flag = true;
            }
        }
        return true;
    }

    private class SincroTask extends AsyncTask<Void, String, Integer> {
        private SincroTask() {
        }

        /* synthetic */
        SincroTask(SincroActivity sincroActivity, SincroActivity.SincroTask sincroTask) {
            this();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            SincroActivity.this.progressProcess = ProgressDialog.show(getContext(), "Menu", "Sincronizando los datos...", true, false);
            SincroActivity.this.progressProcess.show();
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(String... mensaje) {
            SincroActivity.this.progressProcess.setMessage(mensaje[0]);
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... params) {

            RequestGet gateway;
            boolean producto = false;
            boolean cliente = false;
            boolean forma_pago = false;
            boolean proveedor = false;
            boolean ZonaVendedor = false;
            boolean Zona = false;
            boolean vendedorConfig = false;
            boolean descuentoCliente = false;
            try {
                Usuario beanUsuario = SincroActivity.this.sqLibraryApp.getDataManager().getUsuario();
                SparseBooleanArray seleccionados = SincroActivity.this.listSincro.getCheckedItemPositions();
                int len = SincroActivity.this.listSincro.getCount();
                for (int i = 0; i < len; i++) {
                    if (seleccionados.get(i)) {
                        if (SincroActivity.this.listSincro.getItemAtPosition(i).equals(Constante.PRODUCTOS)) {
                            producto = true;
                        }
                        if (SincroActivity.this.listSincro.getItemAtPosition(i).equals(Constante.CLIENTES)) {
                            cliente = true;
                        }
                        if (SincroActivity.this.listSincro.getItemAtPosition(i).equals(Constante.FORMA_PAGO)) {
                            forma_pago = true;
                        }
                        if (SincroActivity.this.listSincro.getItemAtPosition(i).equals(Constante.PROVEEDORES)) {
                            proveedor = true;
                        }
                        if (SincroActivity.this.listSincro.getItemAtPosition(i).equals(Constante.ZONA)) {
                            Zona = true;
                        }
                        if (SincroActivity.this.listSincro.getItemAtPosition(i).equals(Constante.CONFVENDEDOR)) {
                            vendedorConfig = true;
                        }
                        if (SincroActivity.this.listSincro.getItemAtPosition(i).equals(Constante.DESCUENTOCLIENTE)) {
                            descuentoCliente = true;
                        }
                    }
                }
                if (producto) {
                    publishProgress(new String[]{"Obteniendo Productos"});
                    gateway = new SGetProductoList(SincroActivity.this.sqLibraryApp, beanUsuario);
                    try {
                        if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                            return 2;
                        }

                        publishProgress(new String[]{"Obteniendo Bodegas"});
                        if (new SGetBodegaList(SincroActivity.this.sqLibraryApp).doRequest().toString().equals(Constante.ERROR)) {
                            return 2;
                        }
                        publishProgress(new String[]{"Obteniendo Productos_Bodegas"});
                        gateway = new SGetExistenciaList(SincroActivity.this.sqLibraryApp);
                        if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                            return 2;
                        }
                    } catch (Exception e) {
                        RequestGet requestGet = gateway;
                        try {
                            return -1;
                        } catch (Throwable th) {

                        }
                    } catch (Throwable th2) {

                    }
                } else {
                    gateway = null;
                }
                if (cliente) {
                    publishProgress(new String[]{"Obteniendo Clientes"});
                    RequestGet gateway2 = new SGetClienteList(SincroActivity.this.sqLibraryApp, beanUsuario);
                    if (gateway2.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    gateway = gateway2;
                }
                if (forma_pago) {
                    publishProgress(new String[]{"Obteniendo Cond. Contado"});
                    if (new SGetContadoList(SincroActivity.this.sqLibraryApp).doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    publishProgress(new String[]{"Obteniendo Cond. Credito"});
                    gateway = new SGetCreditoList(SincroActivity.this.sqLibraryApp);
                    if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    publishProgress(new String[]{"Obteniendo Cond. Letra"});
                    RequestGet gateway3 = new SGetLetraList(SincroActivity.this.sqLibraryApp);
                    if (gateway3.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    gateway = gateway3;
                }
                if (proveedor) {
                    publishProgress(new String[]{"Obteniendo Proveedores"});
                    if (new SGetProveedorList(SincroActivity.this.sqLibraryApp).doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                }
                if (Zona) {
                    publishProgress(new String[]{"Obteniendo Zona por Vendedor"});
                    if (new SGetZonaPorVendedor(SincroActivity.this.sqLibraryApp,beanUsuario).doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    publishProgress(new String[]{"Obteniendo Listado de Zonas"});
                    gateway = new SGetZona(SincroActivity.this.sqLibraryApp);
                    if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                }
                if (vendedorConfig) {
                    publishProgress(new String[]{"Obteniendo Vendedor Bodega"});
                    if (new SGetVendedorBodegaList(SincroActivity.this.sqLibraryApp, beanUsuario).doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    publishProgress(new String[]{"Obteniendo Vendedor Correlativo"});
                    gateway = new SGetVendedorCorrelativoList(SincroActivity.this.sqLibraryApp, beanUsuario);
                    if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    publishProgress(new String[]{"Obteniendo Vendedor Mail"});
                    gateway = new SGetVendedorMailList(SincroActivity.this.sqLibraryApp, beanUsuario);
                    if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                }

                if (descuentoCliente) {
                    publishProgress(new String[]{"Obteniendo Descuento por Cliente"});
                    if (new SGGetDescuentoCliente(SincroActivity.this.sqLibraryApp).doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                }

                if ((!proveedor) && (((!producto) & (!cliente)) & (!forma_pago) & (!descuentoCliente))) {
                    return 3;
                }


                return 1;
            } catch (Exception  e2) {
                return -1;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer result) {
            if (SincroActivity.this.progressProcess.isShowing()) {
                SincroActivity.this.progressProcess.dismiss();
            }
            switch (result.intValue()) {
                case -1:
                    getActivity().showDialog(2);
                    return;
                case 1:
                    getActivity().showDialog(3);
                    return;
                case 2:
                    getActivity().showDialog(1);
                    return;
                case 3:
                    getActivity().showDialog(4);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 1:
                return new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblErrorConnection).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 2:
                return new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblErrorApp).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 3:
                return new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblSincroSuccess).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        getActivity().finish();
                    }
                }).create();
            case 4:
                return new AlertDialog.Builder(getContext()).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblFaltaSeleccionar).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            default:
                return null;
        }
    }

}
