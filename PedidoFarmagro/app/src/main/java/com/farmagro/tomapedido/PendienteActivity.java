package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.farmagro.tomapedido.adapters.PendienteListAdapter;
import com.farmagro.tomapedido.modelo.CabPedido;
import com.farmagro.tomapedido.modelo.DetPedido;
import com.farmagro.tomapedido.modelo.Respuesta;
import com.farmagro.tomapedido.modelo.Usuario;
import com.farmagro.tomapedido.service.SSentPedido;
import com.farmagro.tomapedido.util.Constante;

import org.json.JSONObject;

import java.util.List;

public class PendienteActivity extends BaseActivity {
    private static final int ERROR_ENVIO = 0;
    private static final int SUCCESS_ENVIO = 1;
    /* access modifiers changed from: private */
    public ListView listPendientes;
    /* access modifiers changed from: private */
    public ProgressDialog progressSave;
    private Respuesta rpta;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.pendiente);
        this.ab.setTitle((CharSequence) "PENDIENTES");
        this.listPendientes = (ListView) findViewById(R.id.listPendientes);
        this.listPendientes.setChoiceMode(2);
        this.listPendientes.setAdapter(new PendienteListAdapter(this, R.layout.pendiente_item, this.sqLibraryApp.getDataManager().getPedidosPendList()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pendiente, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.opcBackPPendiente){
            salirDetalle();
        }
        if (item.getItemId() == R.id.opcEliminar) {
            if (validaSeleccion()) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar un item", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage("Esta seguro de eliminar el pedido?").setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        SparseBooleanArray seleccionados = PendienteActivity.this.listPendientes.getCheckedItemPositions();
                        int len = PendienteActivity.this.listPendientes.getCount();
                        for (int i = 0; i < len; i++) {
                            if (seleccionados.get(i)) {
                                PendienteActivity.this.sqLibraryApp.getDataManager().deletePedidoById(((CabPedido) PendienteActivity.this.listPendientes.getItemAtPosition(i)).getId().longValue());
                            }
                        }
                        PendienteActivity.this.finish();
                    }
                }).setNegativeButton(R.string.lblCancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create().show();
            }
        }
        if (item.getItemId() != R.id.opcEnviar) {
            return true;
        }
        if (validaSeleccion()) {
            Toast.makeText(getApplicationContext(), "Debe seleccionar un item", Toast.LENGTH_SHORT).show();
            return true;
        }
        new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage("Esta seguro de enviar el pedido?").setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                new EnviarPedidoTask().execute(new Void[0]);
            }
        }).setNegativeButton(R.string.lblCancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        }).create().show();
        return true;
    }

    public class EnviarPedidoTask extends AsyncTask<Void, String, Integer> {
        public EnviarPedidoTask() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            PendienteActivity.this.progressSave = ProgressDialog.show(PendienteActivity.this, "Pedido", "Grabando...", true);
            PendienteActivity.this.progressSave.show();
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... arg0) {
            Usuario beanUsuario = PendienteActivity.this.sqLibraryApp.getDataManager().getUsuario();
            SparseBooleanArray seleccionados = PendienteActivity.this.listPendientes.getCheckedItemPositions();
            int len = PendienteActivity.this.listPendientes.getCount();
            boolean flag = false;
            int i = 0;
            while (true) {
                if (i >= len) {
                    break;
                }
                if (seleccionados.get(i)) {
                    CabPedido currentPedido = (CabPedido) PendienteActivity.this.listPendientes.getItemAtPosition(i);
                    List<DetPedido> lstbeanDet = PendienteActivity.this.sqLibraryApp.getDataManager().getDetPedidoByIdList(currentPedido.getId().longValue());
                    try {
                        String resp = new SSentPedido(PendienteActivity.this.getApplication(), beanUsuario, currentPedido, lstbeanDet).doRequest();
                        JSONObject jo = new JSONObject(resp);
                        rpta = new Respuesta();
                        rpta.setiCodRespuesta(Integer.parseInt(jo.getString("iCodRespuesta")));
                        rpta.setsRespuesta(jo.getString("sRespuesta"));

                        PendienteActivity.this.sqLibraryApp.getDataManager().deletePedidoById(currentPedido.getId().longValue());

                    } catch (Exception e) {
                        flag = true;
                    }
                }
                i++;
            }
            if (flag) {
                return -1;
            }
            return 1;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer result) {
            if (PendienteActivity.this.progressSave.isShowing()) {
                PendienteActivity.this.progressSave.dismiss();
            }
            switch (result.intValue()) {
                case -1:
                    PendienteActivity.this.showDialog(0);
                    return;
                case 1:
                    PendienteActivity.this.showDialog(1);
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblErrorEnvio).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(PendienteActivity.this, MenuAlterno.class);
                        intent.putExtra("page", 3);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendienteActivity.this.startActivity(intent);
                        PendienteActivity.this.finish();
                    }
                }).create();
            case 1:
                return new AlertDialog.Builder(this).setIcon(R.drawable.ic_alert_dialog).setTitle(R.string.lblMensaje).setMessage(R.string.lblSuccessEnvio).setPositiveButton(R.string.lblAceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(PendienteActivity.this, MenuAlterno.class);
                        intent.putExtra("page", 0);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendienteActivity.this.startActivity(intent);
                        PendienteActivity.this.finish();
                    }
                }).create();
            default:
                return null;
        }
    }

    private boolean validaSeleccion() {
        int count = 0;
        SparseBooleanArray seleccionados = this.listPendientes.getCheckedItemPositions();
        int len = this.listPendientes.getCount();
        int i = 0;
        while (true) {
            if (i >= len) {
                break;
            } else if (seleccionados.get(i)) {
                count = 0 + 1;
                break;
            } else {
                i++;
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }

    private void salirDetalle() {
        Intent intent = new Intent(this, MenuAlterno.class);
        intent.putExtra("page", 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        this.finish();
    }
}
