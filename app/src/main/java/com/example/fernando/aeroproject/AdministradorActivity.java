package com.example.fernando.aeroproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class AdministradorActivity extends AppCompatActivity {

    Button taviones, rutas, horarios, aviones,salirBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        setTitle(Usuario.getInstance().nombre);
        salirBtn = findViewById(R.id.salirBtn);
        taviones = (Button) findViewById(R.id.tipoaviones);
        rutas = (Button) findViewById(R.id.rutas);
        horarios = (Button) findViewById(R.id.horarios);
        aviones = (Button) findViewById(R.id.aviones);

        salirBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario.reset();
                Intent intent = new Intent(AdministradorActivity.this, SeccionPublica.class);
                startActivity(intent);
            }
        });

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
                Intent intent3 = new Intent(AdministradorActivity.this, Horarios.class);
                startActivity(intent3);
            }
        });

        aviones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(AdministradorActivity.this, AgregarAviones.class);
                startActivity(intent4);
            }
        });


    }
}
