package com.example.fernando.aeroproject;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SeccionPublica extends AppCompatActivity implements View.OnClickListener, AeroFragment.OnFragmentInteractionListener, VuelosFragment.OnFragmentInteractionListener,
        CuentaFragment.OnFragmentInteractionListener{

    Button aero, vuelos, contactenos, cuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccion_publica);

//Para iniciar la app con el fragmento de vuelos.
        VuelosFragment fragmento1 = new VuelosFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, fragmento1).commit();

        aero = (Button)findViewById(R.id.aero);
        vuelos = (Button)findViewById(R.id.vuelos);
        contactenos = (Button)findViewById(R.id.contactenos);
        cuenta = (Button)findViewById(R.id.cuenta);

        aero.setOnClickListener(this);
        vuelos.setOnClickListener(this);
        contactenos.setOnClickListener(this);
        cuenta.setOnClickListener(this);

    }
//Al dar click a un boton, que salga el fragmento según boton.
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.aero:
                AeroFragment aerofrag = new AeroFragment();
                FragmentTransaction trasicion1 = getSupportFragmentManager().beginTransaction();
                trasicion1.replace(R.id.contenedor, aerofrag);
                trasicion1.commit();
                break;
            case R.id.vuelos:
                VuelosFragment vuelosfrag = new VuelosFragment();
                FragmentTransaction trasicion2 = getSupportFragmentManager().beginTransaction();
                trasicion2.replace(R.id.contenedor, vuelosfrag);
                trasicion2.commit();
                break;
            case R.id.contactenos:
                break;
            case R.id.cuenta:
                CuentaFragment cuentafrag = new CuentaFragment();
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.contenedor, cuentafrag);
                transaction3.commit();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
