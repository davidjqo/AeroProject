package com.example.fernando.aeroproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class Horarios extends AppCompatActivity {
EditText dia, hora, ruta ,precio;
Button agregarhorario;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
         
        dia = (EditText)findViewById(R.id.horario1);
        hora = (EditText)findViewById(R.id.horario2);
        ruta = (EditText)findViewById(R.id.ruta);
        precio = (EditText)findViewById(R.id.precio);

    }
}
