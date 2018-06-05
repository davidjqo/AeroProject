package com.example.fernando.aeroproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MetodoPago extends AppCompatActivity {
    Coneccion coneccion = new Coneccion();
    Tarea tarea;
    private Context context = this;
    int cant=0,total=0,vuelo=0;
    EditText tnumero,tseguridad;
    String metodo="MasterCard";
    Button finalizar,cancelar;
    ImageButton mastercard;
    ImageButton visa;
    ImageButton amex;
    ImageButton paypal;
    Spinner syear;
    Spinner smonth;
    ArrayList<Integer> annos;
    ArrayList<String> meses;
    private TextView vueloTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodo_pago);
        vueloTxt = findViewById(R.id.vueloTxt);
        try {
            JSONObject jsnobject = new JSONObject(getIntent().getStringExtra("Vuelo"));
            cant=getIntent().getIntExtra("cantidad",0);
            total=cant*Integer.parseInt(jsnobject.getString("precio"));
            vuelo=Integer.parseInt(jsnobject.getString("id"));
            vueloTxt.setText(cant+"-Pasajes-"+jsnobject.getString("titulo")+" Costo-Unitario:"+jsnobject.getString("precio")+"$ Total:"+total+"$");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setTitle(Usuario.getInstance().nombre);
        tnumero=findViewById(R.id.tnumero);
        tseguridad=findViewById(R.id.tseguridad);
        finalizar=findViewById(R.id.finalizarBtn);
        cancelar=findViewById(R.id.cancelarBtn);
        mastercard = (ImageButton)findViewById(R.id.mastercard);
        visa = (ImageButton)findViewById(R.id.visa);
        amex = (ImageButton)findViewById(R.id.amex);
        paypal = (ImageButton)findViewById(R.id.paypal);
        syear = (Spinner)findViewById(R.id.syear);
        smonth = (Spinner)findViewById(R.id.smonth);
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
                metodo="MasterCard";
                Toast.makeText(MetodoPago.this, "MasterCard", Toast.LENGTH_LONG).show();
            }
        });
        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodo="Visa";
                Toast.makeText(MetodoPago.this, "Visa", Toast.LENGTH_LONG).show();
            }
        });
        amex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodo="Amex";
                Toast.makeText(MetodoPago.this, "Amex", Toast.LENGTH_LONG).show();
            }
        });
        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                metodo="Paypal";
                Toast.makeText(MetodoPago.this, "Paypal", Toast.LENGTH_LONG).show();
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exp=syear.getSelectedItem().toString()+'/'+smonth.getSelectedItem().toString()+"/01";
                tarea = new Tarea();
                tarea.execute("realizarPagoIda",Usuario.getInstance().id+"",vuelo+"",cant+"",tnumero.getText().toString(),metodo,exp,tseguridad.getText().toString());
            }
        });

        ArrayAdapter<Integer> adapterAnnos = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, annos);
        ArrayAdapter<String> adapterMeses = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, meses);

        syear.setAdapter(adapterAnnos);
        smonth.setAdapter(adapterMeses);
    }
    class Tarea extends AsyncTask<String, Void, String> {
        String accion = "";
        /**
         * Ventana que bloqueara la pantalla del movil hasta recibir respuesta del servidor
         */
        ProgressDialog progressDialog;

        /**
         * muestra una ventana emergente
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Connecting to server");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        /**
         * Envia los parametros al servlet
         * los parametros se deben nombrar igual que como los va a recibir el servlet
         */
        @Override
        protected String doInBackground(String... values) {
            accion = values[0];
            String text = "?accion=" + values[0];
            switch (accion) {
                case "realizarPagoIda":
                    text += "&idUsuario=" + values[1];
                    text += "&idVuelo=" + values[2];
                    text += "&asientos=" + values[3];
                    text += "&numTarjeta=" + values[4];
                    text += "&nombTarjeta=" + values[5];
                    text += "&expiracion=" + values[6];
                    text += "&seguridad=" + values[7];
                    break;
            }
            return coneccion.getResultFromServlet(text);
        }

        /**
         * Oculta ventana emergente y muestra resultado en pantalla
         */
        @Override
        protected void onPostExecute(String value) {
            switch (accion) {
                case "realizarPagoIda":
                    String vuelo="";
                    try {
                        JSONArray arr = new JSONArray(value);
                        if(arr.length()>0) {
                            for (int i = 0; i < arr.length(); i++) {
                                vuelo = arr.getJSONObject(i).toString();
                            }
                            Toast.makeText(context, "Reserva efectuada", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(context, "Reserva no efectuada", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            progressDialog.dismiss();
        }
    }
}
