package com.example.fernando.aeroproject;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VuelosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VuelosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VuelosFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView fechaida;
    private TextView fechavuelta;
    private Calendar mCurrentDate;
    private int day, month, year;
    private Button buscar;
    private RadioButton idaVuelta, ida;
    private TextView salida, destino, cantidad;
    private String dia, vuelta, sali, dest;
    private int cant;
    Coneccion coneccion = new Coneccion();
    VuelosFragment.Tarea tarea;
    private ViewPager viewPager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public VuelosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VuelosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VuelosFragment newInstance(String param1, String param2) {
        VuelosFragment fragment = new VuelosFragment();
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

        View view = inflater.inflate(R.layout.fragment_vuelos, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());
        viewPager.setAdapter(viewPagerAdapter);

        fechaida = (TextView) view.findViewById(R.id.dateida);
        fechavuelta = (TextView) view.findViewById(R.id.datevuelta);

        mCurrentDate = Calendar.getInstance();

        day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        month = mCurrentDate.get(Calendar.MONTH);
        year = mCurrentDate.get(Calendar.YEAR);

        month = month + 1;

        fechaida.setText(year + "/" + month + "/" + day);
        fechavuelta.setText(year + "/" + month + "/" + day);

        buscar = view.findViewById(R.id.buscarbtn);
        idaVuelta = view.findViewById(R.id.idavueltarbutton);
        ida = view.findViewById(R.id.idarbutton);
        salida = view.findViewById(R.id.salida);
        destino = view.findViewById(R.id.destino);
        cantidad = view.findViewById(R.id.cantidad);

        fechaida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        fechaida.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        fechavuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        fechavuelta.setText(year + "/" + monthOfYear + "/" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        ida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Controla que no estén dos radioButtons chequeados
                if (v == idaVuelta) {
                    ida.setChecked(false);
                }

                if (v == ida) {
                    idaVuelta.setChecked(false);
                }
            }
        });
        idaVuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Controla que no estén dos radioButtons chequeados
                if (v == idaVuelta) {
                    ida.setChecked(false);
                }

                if (v == ida) {
                    idaVuelta.setChecked(false);
                }
            }
        });
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                if(validar()==true) {
                    if (idaVuelta.isChecked()) {
                        buscarVueloIdaVuelta();
                    }
                    if (ida.isChecked()) {
                        buscarVueloIda();
                    } else if (idaVuelta.isChecked() == false && ida.isChecked() == false) {
                        CharSequence text = "Seleccione una opción...";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
                else
                    Toast.makeText(getActivity(), "Datos incorrectos", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    public void buscarVueloIda() {
        inicializarIda();
        if (!validar()) {
            Toast.makeText(getActivity(), "No se pudo buscar", Toast.LENGTH_SHORT).show();
        } else {
            tarea = new Tarea();
            tarea.execute("buscarVueloIda", dia, sali, dest, String.valueOf(cant));
        }
    }

    public void buscarVueloIdaVuelta() {
        inicializarIdaVuelta();
        if (!validar()) {
            Toast.makeText(getActivity(), "No se pudo buscar", Toast.LENGTH_SHORT).show();
        } else {
            tarea = new Tarea();
            tarea.execute("buscarVueloIda", dia, vuelta, sali, dest, String.valueOf(cant));
        }
    }

    public boolean validar() {
        boolean valid = true;
        if(Usuario.getInstance().id==-1) {
            Toast.makeText(getActivity(), "Inicie sesión primero", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(cantidad.getText().length()==0){
            cantidad.setError("Especifique la cantidad de pasajeros");
            valid = false;
        }
        if (cant > 9) {
            cantidad.setError("Sólo se pueden solicitar de 1 a 9 espacios");
            valid = false;
        }
        return valid;
    }

    public void inicializarIda() {
        dia = fechaida.getText().toString().trim();
        sali = salida.getText().toString().trim();
        dest = destino.getText().toString().trim();
        cant = Integer.parseInt(cantidad.getText().toString().trim());
    }

    public void inicializarIdaVuelta() {
        dia = fechaida.getText().toString().trim();
        vuelta = fechavuelta.getText().toString().trim();
        sali = salida.getText().toString().trim();
        dest = destino.getText().toString().trim();
        cant = Integer.parseInt(cantidad.getText().toString().trim());
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
        int cantidad=0;
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
                case "buscarVueloIda":
                    text += "&dia=" + values[1];
                    text += "&salida=" + values[2];
                    text += "&destino=" + values[3];
                    text += "&cant=" + values[4];
                    cantidad=Integer.parseInt(values[4]);
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
                case "buscarVueloIda":
                    String vuelo="";
                    try {
                        JSONArray arr = new JSONArray(value);
                        if(arr.length()>0) {
                            for (int i = 0; i < arr.length(); i++) {
                                vuelo = arr.getJSONObject(i).toString();
                            }
                            if(Usuario.getInstance().rol>-1) {
                                Toast.makeText(getActivity(), "Correcto!", Toast.LENGTH_SHORT).show();
                                Intent pagar = new Intent(getActivity(), MetodoPago.class);
                                pagar.putExtra("Vuelo", vuelo);
                                pagar.putExtra("cantidad",cantidad);
                                startActivity(pagar);
                            }
                            else{
                                Toast.makeText(getActivity(), "Inicie sesion Primero", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getActivity(), "Vuelo no encontrado", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            progressDialog.dismiss();
        }
    }
}
