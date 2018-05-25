package com.example.fernando.aeroproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

public class TipoAviones extends AppCompatActivity {
    TableLayout tlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_aviones);

        tlayout = (TableLayout)findViewById(R.id.tablelayout);
        
    }
}
