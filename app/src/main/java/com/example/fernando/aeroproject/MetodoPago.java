package com.example.fernando.aeroproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MetodoPago extends AppCompatActivity {
    ImageButton mastercard;
    ImageButton visa;
    ImageButton amex;
    ImageButton paypal;
    Spinner spinner;
    Spinner spinner2;
    ArrayList<Integer> annos;
    ArrayList<String> meses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_pago);

        mastercard = (ImageButton)findViewById(R.id.mastercard);
        visa = (ImageButton)findViewById(R.id.visa);
        amex = (ImageButton)findViewById(R.id.amex);
        paypal = (ImageButton)findViewById(R.id.paypal);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        annos = new ArrayList<>();
        annos.add(2018);
        annos.add(2019);
        annos.add(2020);
        annos.add(2021);
        annos.add(2022);
        annos.add(2023);
        meses = new ArrayList<>();
        meses.add("01");
        meses.add("02");
        meses.add("03");
        meses.add("04");
        meses.add("05");
        meses.add("06");
        meses.add("07");
        meses.add("08");
        meses.add("09");
        meses.add("10");
        meses.add("11");
        meses.add("12");

        mastercard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MetodoPago.this, "MasterCard", Toast.LENGTH_LONG).show();
            }
        });
        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MetodoPago.this, "Visa", Toast.LENGTH_LONG).show();
            }
        });
        amex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MetodoPago.this, "Amex", Toast.LENGTH_LONG).show();
            }
        });
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MetodoPago.this, "Paypal", Toast.LENGTH_LONG).show();
            }
        });

        ArrayAdapter<Integer> adapterAnnos = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, annos);
        ArrayAdapter<String> adapterMeses = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, meses);

        spinner.setAdapter(adapterAnnos);
        spinner2.setAdapter(adapterMeses);
    }
}
