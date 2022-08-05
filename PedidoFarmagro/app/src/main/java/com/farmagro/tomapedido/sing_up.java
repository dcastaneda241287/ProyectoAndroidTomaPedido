package com.farmagro.tomapedido;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class sing_up extends AppCompatActivity {

    Button btn_ir_inicioSesion,crear_cuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        btn_ir_inicioSesion=findViewById(R.id.btn_ir_inicioSesion);
        crear_cuenta=findViewById(R.id.crear_cuenta);



        btn_ir_inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sing_up.this,LoginActivity.class));
                finish();
            }
        });

        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(sing_up.this, "Registrar Usuario", Toast.LENGTH_SHORT).show();
                //Aquí codigo
            }
        });
    }
}