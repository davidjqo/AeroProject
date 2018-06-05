package com.example.fernando.aeroproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TipoAviones extends AppCompatActivity {
    TableLayout tlayout;
    Coneccion coneccion = new Coneccion();
    Tarea tarea;
    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_aviones);

        tlayout = (TableLayout)findViewById(R.id.tablelayout);

        tarea = new Tarea();
        tarea.execute("consultarAviones");
        
    }
    private void llenarTabla(JSONArray arr){
        TableLayout tl = tlayout;
        while(tl.getChildCount()>1)
            tl.removeViewAt(1);
        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject obj=arr.getJSONObject(i);
                TableRow tr = new TableRow(this);
                tr.setGravity(Gravity.CENTER);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView id= new TextView(getApplicationContext());
                id.setText(obj.getString("id"));
                id.setTextColor(Color.BLACK);
                id.setTextSize(16f);
                tr.addView(id);

                TextView año= new TextView(getApplicationContext());
                año.setText(obj.getString("año"));
                año.setTextColor(Color.BLACK);
                año.setTextSize(16f);
                tr.addView(año);

                TextView modelo= new TextView(getApplicationContext());
                modelo.setText(obj.getString("modelo"));
                modelo.setTextColor(Color.BLACK);
                modelo.setTextSize(16f);
                tr.addView(modelo);

                TextView marca= new TextView(getApplicationContext());
                marca.setText(obj.getString("marca"));
                marca.setTextColor(Color.BLACK);
                marca.setTextSize(16f);
                tr.addView(marca);

                TextView pasajeros= new TextView(getApplicationContext());
                pasajeros.setText(obj.getString("pasajeros"));
                pasajeros.setTextColor(Color.BLACK);
                pasajeros.setTextSize(16f);
                tr.addView(pasajeros);

                tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clase para interactuar con el servidor
     */
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
                case "consultarAviones":
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
               case "consultarAviones": //si devuelve algo se guarda en un arreglo json
                    JSONArray jsonResponse;
                    try {
                        jsonResponse = new JSONArray(value);
                        llenarTabla(jsonResponse);
                    } catch (JSONException e) {
                        Toast.makeText(context, "Problema al cargar los aviones", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    break;
            }
            progressDialog.dismiss();
        }
    }
}
