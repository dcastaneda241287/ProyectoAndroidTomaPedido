package com.farmagro.tomapedido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.service.RequestGet;
import com.farmagro.tomapedido.service.SGetBodegaList;
import com.farmagro.tomapedido.service.SGetProductoList;
import com.farmagro.tomapedido.util.Constante;

import java.util.ArrayList;
import java.util.List;

public class SincronizacionActivity extends BaseActivity {
    private static final int ERROR_APP = 2;
    private static final int ERROR_CONEXION = 1;
    private static final int FALTA_SELECCIONAR = 4;
    private static final int SINCRO_SUCCESS = 3;
    private boolean flag;
    /* access modifiers changed from: private */
    public ListView listSincro;
    private List<String> menu;
    /* access modifiers changed from: private */
    public ProgressDialog progressProcess;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_sincronizacion);
        this.ab.setTitle((CharSequence) "SINCRONIZACION");
        this.listSincro = (ListView) findViewById(R.id.listSincro);
        this.listSincro.setChoiceMode(2);
        this.menu = new ArrayList();
        this.menu.add(Constante.PRODUCTOS);
        this.menu.add(Constante.CLIENTES);
        this.menu.add(Constante.FORMA_PAGO);
        this.menu.add(Constante.PROVEEDORES);
        this.listSincro.setAdapter(new ArrayAdapter<>(this, R.layout.sincro_item, this.menu));
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        getMenuInflater().inflate(R.menu.menu_sincro, menu2);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcSincro) {
            new SincronizacionTask(this, (SincronizacionTask) null).execute(new Void[0]);
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

    private class SincronizacionTask extends AsyncTask<Void, String, Integer> {
        private SincronizacionTask() {
        }

        /* synthetic */ SincronizacionTask(SincronizacionActivity sincroActivity, SincronizacionTask sincronizacionTask) {
            this();
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            SincronizacionActivity.this.progressProcess = ProgressDialog.show(SincronizacionActivity.this, "Menu", "Sincronizando los datos...", true, false);
            SincronizacionActivity.this.progressProcess.show();
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(String... mensaje) {
            SincronizacionActivity.this.progressProcess.setMessage(mensaje[0]);
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... params) {

            RequestGet gateway;
            boolean producto = false;
            boolean cliente = false;
            boolean forma_pago = false;
            boolean proveedor = false;
            try {
                Usuario beanUsuario = SincronizacionActivity.this.sqLibraryApp.getDataManager().getUsuario();
                SparseBooleanArray seleccionados = SincronizacionActivity.this.listSincro.getCheckedItemPositions();
                int len = SincronizacionActivity.this.listSincro.getCount();
                for (int i = 0; i < len; i++) {
                    if (seleccionados.get(i)) {
                        if (SincronizacionActivity.this.listSincro.getItemAtPosition(i).equals(Constante.PRODUCTOS)) {
                            producto = true;
                        }
                        if (SincronizacionActivity.this.listSincro.getItemAtPosition(i).equals(Constante.CLIENTES)) {
                            cliente = true;
                        }
                        if (SincronizacionActivity.this.listSincro.getItemAtPosition(i).equals(Constante.FORMA_PAGO)) {
                            forma_pago = true;
                        }
                        if (SincronizacionActivity.this.listSincro.getItemAtPosition(i).equals(Constante.PROVEEDORES)) {
                            proveedor = true;
                        }
                    }
                }
                if (producto) {
                    publishProgress(new String[]{"Obteniendo Productos"});
                    gateway = new SGetProductoList(SincronizacionActivity.this.getApplication(), beanUsuario);
                    try {
                        if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                            return 2;
                        }

                        /*publishProgress(new String[]{"Obteniendo Bodegas"});
                        if (new SGetBodegaList(SincroActivity.this.getApplication()).doRequest().toString().equals(Constante.ERROR)) {
                            return 2;
                        }
                        publishProgress(new String[]{"Obteniendo Productos_Bodegas"});
                        gateway = new SGetExistenciaList(SincroActivity.this.getApplication());
                        if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                            return 2;
                        }*/
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
                if (cliente) {/*
                    publishProgress(new String[]{"Obteniendo Clientes"});
                    RequestGet gateway2 = new SGetClienteList(SincroActivity.this.getApplication(), beanUsuario);
                    if (gateway2.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    gateway = gateway2;*/
                }
                if (forma_pago) {
                    publishProgress(new String[]{"Obteniendo Cond. Contado"});/*
                    if (new SGetContadoList(SincroActivity.this.getApplication()).doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    publishProgress(new String[]{"Obteniendo Cond. Credito"});
                    gateway = new SGetCreditoList(SincroActivity.this.getApplication());
                    if (gateway.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    publishProgress(new String[]{"Obteniendo Cond. Letra"});
                    RequestGet gateway3 = new SGetLetraList(SincroActivity.this.getApplication());
                    if (gateway3.doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }
                    gateway = gateway3;
                }
                if (proveedor) {
                    publishProgress(new String[]{"Obteniendo Proveedores"});
                    if (new SGetProveedorList(SincroActivity.this.getApplication()).doRequest().toString().equals(Constante.ERROR)) {
                        return 2;
                    }*/
                }

                if ((!proveedor) && (((!producto) & (!cliente)) & (!forma_pago))) {
                    return 3;
                }
                return 1;
            } catch (Exception  e2) {
                return -1;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer result) {
            if (SincronizacionActivity.this.progressProcess.isShowing()) {
                SincronizacionActivity.this.progressProcess.dismiss();
            }
            switch (result.intValue()) {
                case -1:
                    SincronizacionActivity.this.showDialog(2);
                    return;
                case 1:
                    SincronizacionActivity.this.showDialog(3);
                    return;
                case 2:
                    SincronizacionActivity.this.showDialog(1);
                    return;
                case 3:
                    SincronizacionActivity.this.showDialog(4);
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
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblErrorConnection).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 2:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblErrorApp).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            case 3:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblSincroSuccess).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SincronizacionActivity.this.finish();
                    }
                }).create();
            case 4:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblFaltaSeleccionar).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
            default:
                return null;
        }
    }
}
