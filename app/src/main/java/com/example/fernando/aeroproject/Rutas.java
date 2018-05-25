package com.example.fernando.aeroproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

public class Rutas extends AppCompatActivity {
TableLayout tblayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rutas);
    tblayout = (TableLayout)findViewById(R.id.tablerutas);
    }
}
