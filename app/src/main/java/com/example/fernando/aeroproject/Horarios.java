package com.example.fernando.aeroproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Horarios extends AppCompatActivity {

    private EditText dia, hora, ruta, precio;
    private String d;
    private String h;
    private int r;
    private int p;
    private Button agregarhorario;
    Coneccion coneccion = new Coneccion();
    Horarios.Tarea tarea;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        dia = (EditText) findViewById(R.id.horario1);
        hora = (EditText) findViewById(R.id.horario2);
        ruta = (EditText) findViewById(R.id.ruta);
        precio = (EditText) findViewById(R.id.precio);
        agregarhorario = findViewById(R.id.registrohorario);

        agregarhorario.setOnClickListener(new View.OnClickListener() {
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
            tarea.execute("registrarHorario", d, h, String.valueOf(r), String.valueOf(p));
        }
    }

    public boolean validar() {
        boolean valid = true;
        if (d.isEmpty()) {
            dia.setError("Ingrese la fecha");
            valid = false;
        }
        if (h.isEmpty()) {
            hora.setError("Ingrese la hora");
            valid = false;
        }
        if (r <= 0) {
            ruta.setError("Ingrese la ruta mayor que 0");
            valid = false;
        }
        if (p <= 0) {
            precio.setError("Ingrese el precio mayor que 0");
            valid = false;
        }
        return valid;
    }

    public void inicializar() {
        d = dia.getText().toString().trim();
        h = hora.getText().toString().trim();
        r = Integer.parseInt(ruta.getText().toString().trim());
        p = Integer.parseInt(precio.getText().toString().trim());
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
                case "registrarHorario":
                    text += "&dia=" + values[1];
                    text += "&hora=" + values[2];
                    text += "&ruta=" + values[2];
                    text += "&precio=" + values[2];
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
                case "registrarHorario":
                    Toast.makeText(context, "Horario agregado con éxito", Toast.LENGTH_SHORT).show();
                    break;
            }
            progressDialog.dismiss();
        }
    }
}
