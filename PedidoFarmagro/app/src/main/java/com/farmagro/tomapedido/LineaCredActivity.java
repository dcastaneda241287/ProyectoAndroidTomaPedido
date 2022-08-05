package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.farmagro.tomapedido.modelo.Cliente;

public class LineaCredActivity extends BaseActivity {
    private Button btnAceptar;
    private Cliente currentCliente;
    private TextView txtCodigo;
    private TextView txtLabel;
    private TextView txtLimCred;
    private TextView txtRazon;
    private TextView txtSaldoDol;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_linea_cred);
        this.ab.setTitle((CharSequence) "Clientes/Linea de credtio");
        this.currentCliente = (Cliente) getIntent().getExtras().getSerializable("currentCliente");
        this.txtCodigo = (TextView) findViewById(R.id.txtCodigo);
        this.txtLabel = (TextView) findViewById(R.id.txtLabel);
        this.txtRazon = (TextView) findViewById(R.id.txtRazon);
        this.txtLimCred = (TextView) findViewById(R.id.txtLimCred);
        this.txtSaldoDol = (TextView) findViewById(R.id.txtSaldoDol);
        this.btnAceptar = (Button) findViewById(R.id.btnAceptar);
        if (this.currentCliente.getCodigo().length() > 8) {
            this.txtLabel.setText("Razon social:");
        } else {
            this.txtLabel.setText("Nombre:");
        }
        this.txtCodigo.setText(this.currentCliente.getCodigo());
        this.txtRazon.setText(this.currentCliente.getNombre());
        this.txtSaldoDol.setText(this.currentCliente.getSaldoDolar());
        this.txtLimCred.setText(this.currentCliente.getLimiteCredido());
        this.btnAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                LineaCredActivity.this.finish();
            }
        });
    }
}