package com.example.praxis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praxis.entidades.Usuarios;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment implements View.OnClickListener{

    TextView terminosUso, politicaPrivacidad , campoNombre, campoApellido, campoEmail, campoPassword;
    CheckBox ckPrivacidadAceptar , ckPrivacidadNegar;
    private boolean aceptarPoliticas;

    Button btnCrearCuenta;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
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
        View fragmento =  inflater.inflate(R.layout.fragment_registro, container, false);


        //Campos que deben ser guardados en la base de datos
        campoNombre = (TextView) fragmento.findViewById(R.id.reg_nombre);
        campoApellido = (TextView) fragmento.findViewById(R.id.reg_apellido);
        campoEmail = (TextView) fragmento.findViewById(R.id.reg_email);
        campoPassword = (TextView) fragmento.findViewById(R.id.reg_password);
        btnCrearCuenta = (Button) fragmento.findViewById(R.id.btn_crear_registrp);

        //enlaces a los fragmentos de politicas
        terminosUso = (TextView) fragmento.findViewById(R.id.reg_term_uso);
        politicaPrivacidad = (TextView) fragmento.findViewById(R.id.reg_pol_privacidad);

        //escuchadores
        btnCrearCuenta.setOnClickListener(this);
        terminosUso.setOnClickListener(this);
        politicaPrivacidad.setOnClickListener(this);

        return fragmento;
    }

    @Override
    public void onClick(View v) {
        //Creamosuna isntancia a la bd
        AppDataBase db;

        switch (v.getId())
        {
            case R.id.btn_crear_registrp:
                //Guardamos los datos en la base de datos
                db = Room.databaseBuilder(this.getContext(), AppDataBase.class,
                        "enconcretoDB").allowMainThreadQueries().build();
                Usuarios usuario = new Usuarios(campoNombre.getText().toString(),
                        campoApellido.getText().toString(),
                        campoEmail.getText().toString(),
                        campoPassword.getText().toString());
                db.usuariosDao().insert(usuario);

                Toast.makeText(this.getContext(), "Datos Agregados", Toast.LENGTH_LONG).show();
                ;break;
            case R.id.reg_term_uso:

                ;break;
            case R.id.reg_pol_privacidad:
                //Cargamos el fragment de politica de privacidad
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container_1, PoliticaPrivacidadFragment.class, null,"politicaPrivacidad")
                        .addToBackStack("politicaPrivacidad")
                        .commit();
                ;break;
        }
    }
}