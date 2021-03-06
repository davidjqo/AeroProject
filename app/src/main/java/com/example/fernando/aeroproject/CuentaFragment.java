package com.example.fernando.aeroproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CuentaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CuentaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuentaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText mail, contra;
    private Button ingresar;
    private String user, password;
    Coneccion coneccion = new Coneccion();
    CuentaFragment.Tarea tarea;
    public boolean bandera;

    public CuentaFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CuentaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CuentaFragment newInstance(String param1, String param2) {
        CuentaFragment fragment = new CuentaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cuenta, container, false);
        mail = view.findViewById(R.id.email);
        contra = view.findViewById(R.id.contrasena);
        ingresar = view.findViewById(R.id.entrarbtn);

        if(Usuario.getInstance().id>-1){
            mail.setText(Usuario.getInstance().mail);
            contra.setText(Usuario.getInstance().contra);
            ingresar.setText("Salir");
            ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cerrarSesion();
                }
            });
        }
        else{
            ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iniciarSesion();
                }
            });
        }
        Button crearCuentabtn = (Button) view.findViewById(R.id.registrobtn);
        crearCuentabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), CrearCuentaActivity.class);
                startActivity(in);
            }
        });
        return view;
    }
    public void cerrarSesion(){
        Usuario.reset();
        Intent in = new Intent(getActivity(), SeccionPublica.class);
        startActivity(in);
    }
    public void iniciarSesion() {
        inicializar();
        if (!validar()) {
            Toast.makeText(getActivity(), "No se ha podido iniciar la sesión", Toast.LENGTH_SHORT).show();
        } else {
            tarea = new Tarea();
            tarea.execute("iniciarSesion", user, password);
        }
    }

    public boolean validar() {
        boolean valid = true;
        if (user.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(user).matches()) {
            mail.setError("Por favor digitar e-mail válido");
            valid = false;
        }
        if (password.isEmpty()) {
            contra.setError("Digite la contraseña");
            valid = false;
        }
        return valid;
    }

    public void inicializar() {
        user = mail.getText().toString().trim();
        password = contra.getText().toString().trim();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
            progressDialog = new ProgressDialog(getActivity());
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
                case "iniciarSesion":
                    text += "&mail=" + values[1];
                    text += "&contra=" + values[2];
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
                case "iniciarSesion":
                    try {
                        JSONArray jsonArray = new JSONArray(value);

                        if (jsonArray.length()>0) {
                            Toast.makeText(getActivity(), "Se ha iniciado la sesión", Toast.LENGTH_SHORT).show();
                            Usuario.getInstance().id=jsonArray.getJSONObject(0).getInt("id");
                            Usuario.getInstance().nombre=jsonArray.getJSONObject(0).getString("nombre");
                            Usuario.getInstance().rol=jsonArray.getJSONObject(0).getInt("rol");
                            Intent admin;
                            if(Usuario.getInstance().rol==1)
                                admin = new Intent(getActivity(), AdministradorActivity.class);
                            else
                                admin = new Intent(getActivity(), SeccionPublica.class);
                            startActivity(admin);
                            } else {
                            Toast.makeText(getActivity(), "Datos no validos", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "Vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                    bandera = false;
                    break;
            }
            progressDialog.dismiss();
        }
    }
}
