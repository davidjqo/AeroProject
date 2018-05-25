package com.example.fernando.aeroproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdministradorActivity extends AppCompatActivity {

    Button taviones, rutas, horarios, aviones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        taviones = (Button)findViewById(R.id.tipoaviones);
        rutas = (Button)findViewById(R.id.rutas);
        horarios = (Button)findViewById(R.id.horarios);
        aviones = (Button)findViewById(R.id.aviones);

        taviones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(AdministradorActivity.this, TipoAviones.class);
            startActivity(intent);
            }
        });

        rutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent2 = new Intent(AdministradorActivity.this, Rutas.class);
            startActivity(intent2);
            }
        });
        horarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        aviones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }
}
