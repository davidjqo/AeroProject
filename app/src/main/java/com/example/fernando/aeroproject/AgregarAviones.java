package com.example.fernando.aeroproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class AgregarAviones extends AppCompatActivity {

    private EditText identificador, tipo_avion;
    private Button registraravion;
    private String id, tipo;
    Coneccion coneccion = new Coneccion();
    AgregarAviones.Tarea tarea;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_aviones);

        identificador = (EditText) findViewById(R.id.identificador);
        tipo_avion = (EditText) findViewById(R.id.tipo_avion);
        registraravion = (Button) findViewById(R.id.registroavion);
        registraravion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });
    }

    //Validación de los campos de registro.
    public void registrar() {
        inicializar();
        if (!validar()) {
            Toast.makeText(this, "Registro ha fallado", Toast.LENGTH_SHORT).show();
        } else {
            tarea = new Tarea();
            tarea.execute("registrarAvion", id, tipo);
        }
    }

    public boolean validar() {
        boolean valid = true;
        if (id.isEmpty()) {
            identificador.setError("Ingrese el ID");
            valid = false;
        }
        if (tipo.isEmpty()) {
            tipo_avion.setError("Ingrese el ID del tipo");
            valid = false;
        }
        return valid;
    }

    public void inicializar() {
        id = identificador.getText().toString().trim();
        tipo = tipo_avion.getText().toString().trim();
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
                case "registrarAvion":
                    text += "&id=" + values[1];
                    text += "&idTipo=" + values[2];
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
                case "registrarAvion":
                    Toast.makeText(context, "Avión agregado con éxito", Toast.LENGTH_SHORT).show();
                    break;
            }
            progressDialog.dismiss();
        }
    }
}
