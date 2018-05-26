package com.example.fernando.aeroproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AgregarAviones extends AppCompatActivity {

    EditText identificador, tipo_avion;
    Button registraravion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_aviones);

        identificador = (EditText)findViewById(R.id.identificador);
        tipo_avion = (EditText)findViewById(R.id.tipo_avion);

        registraravion = (Button)findViewById(R.id.registroavion);
    }
}
