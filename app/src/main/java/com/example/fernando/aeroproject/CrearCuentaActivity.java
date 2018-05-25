package com.example.fernando.aeroproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CrearCuentaActivity extends AppCompatActivity {
    private EditText cedulatxt, nombretxt, emailtxt, telefonotxt, contrasenatxt, confirmarcontrasenatxt;
    private String cedula, nombre, email, telefono, contrasena, confirmcontrasena;
    Button regbtn;
    Coneccion coneccion = new Coneccion();
    Tarea tarea;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);
        cedulatxt = (EditText) findViewById(R.id.cedula);
        nombretxt = (EditText) findViewById(R.id.nombre);
        emailtxt = (EditText) findViewById(R.id.email);
        telefonotxt = (EditText) findViewById(R.id.contrasena);
        contrasenatxt = (EditText) findViewById(R.id.confirm);
        confirmarcontrasenatxt = (EditText) findViewById(R.id.telefono);
        regbtn = (Button) findViewById(R.id.registrobtn);
        regbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                registrarse();
            }
        });
    }

    //Validación de los campos de registro.
    public void registrarse() {
        inicializar();
        if (!validar()) {
            Toast.makeText(this, "Registro ha fallado", Toast.LENGTH_SHORT).show();
        } else {
            tarea = new Tarea();
            tarea.execute("registrarUsuario", cedula, nombre, contrasena, email, telefono, "1");
        }
    }

    public boolean validar() {
        boolean valid = true;
        if (cedula.length() != 9 || !cedula.matches("[0-9]+")) {
            cedulatxt.setError("Por favor digite una cedula valida, solo numeros");
            valid = false;
        }
        if (nombre.isEmpty() || nombre.length() > 32) {
            nombretxt.setError("Por favor digitar un nombre válido");
            valid = false;
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailtxt.setError("Por favor digitar e-mail válido");
            valid = false;
        }
        if (contrasena.isEmpty()) {
            contrasenatxt.setError("Digite la contraseña");
            valid = false;
        }
        if (confirmcontrasena.isEmpty()) {
            confirmarcontrasenatxt.setError("Confirme la contraseña");
            valid = false;
        }
        if (!confirmcontrasena.matches(contrasena)) {
            confirmarcontrasenatxt.setError("Contraseña y confirmar son distintas");
            valid = false;
        }
        if (telefono.isEmpty()) {
            telefonotxt.setError("Digite número de teléfono");
        }

        return valid;
    }

    public void inicializar() {
        cedula = cedulatxt.getText().toString().trim();
        nombre = nombretxt.getText().toString().trim();
        email = emailtxt.getText().toString().trim();
        contrasena = contrasenatxt.getText().toString().trim();
        confirmcontrasena = confirmarcontrasenatxt.getText().toString().trim();
        telefono = telefonotxt.getText().toString().trim();
    }

    private void EjemplollenarTabla(JSONArray arr) { //************no se usa, es un ejemplo
        //TableLayout tl = (TableLayout) findViewById(R.id.tablaProductos);
        TableLayout tl = new TableLayout(null);
        while (tl.getChildCount() > 1)//vacia la table, excepto el encabezado
            tl.removeViewAt(1);
        for (int i = 0; i < arr.length(); i++) {//recorre el arreglo y llena la tabla
            try {
                JSONObject obj = arr.getJSONObject(i);
                TableRow tr = new TableRow(this);
                tr.setGravity(Gravity.CENTER);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                TextView nombre = new TextView(getApplicationContext());
                nombre.setText(obj.getString("nombre"));
                nombre.setTextColor(Color.BLACK);
                nombre.setTextSize(16f);
                tr.addView(nombre);

                TextView importado = new TextView(getApplicationContext());
                importado.setText(obj.getString("importado"));
                importado.setTextColor(Color.BLACK);
                importado.setTextSize(16f);
                tr.addView(importado);

                TextView precio = new TextView(getApplicationContext());
                precio.setText(obj.getString("precio"));
                precio.setTextColor(Color.BLACK);
                precio.setTextSize(16f);
                tr.addView(precio);

                TextView tipo = new TextView(getApplicationContext());
                tipo.setText(obj.getString("tipo"));
                tipo.setTextColor(Color.BLACK);
                tipo.setTextSize(16f);
                tr.addView(tipo);

                TextView preciof = new TextView(getApplicationContext());
                preciof.setText(obj.getString("precio"));
                preciof.setTextColor(Color.BLACK);
                preciof.setTextSize(16f);
                tr.addView(preciof);

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
                case "registrarUsuario":
                    text += "&id=" + values[1];
                    text += "&nombre=" + values[2];
                    text += "&contra=" + values[3];
                    text += "&corr=" + values[4];
                    text += "&tel=" + values[5];
                    text += "&rol=" + values[6];
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
                case "registrarUsuario":
                    Toast.makeText(context, "Usuario agregado con éxito", Toast.LENGTH_SHORT).show();
                    break;
                case "ejemplo si devuelve algo": //si devuelve algo se guarda en un arreglo json
                    JSONArray jsonResponse;
                    try {
                        jsonResponse = new JSONArray(value);
                        EjemplollenarTabla(jsonResponse);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            progressDialog.dismiss();
        }
    }
}
