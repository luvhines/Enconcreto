package com.example.praxis;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InicioFragment extends Fragment implements View.OnClickListener{

    private EditText campoUsuario;
    private EditText campoPasswd;
    private Button btnEnviar;

    //creamos una variable para referenciar el enlace
    private TextView link_registro;

    //datos temporales de inicio
    private String usuario = "admin";
    private String passwd = "1234";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_inicio, container, false);

        campoUsuario = (EditText) fragmento.findViewById(R.id.act1_campo1);
        campoPasswd = (EditText) fragmento.findViewById(R.id.act1_campo2);
        btnEnviar = (Button) fragmento.findViewById(R.id.act1_btn1);

        //referenciamos el objeto
        link_registro = (TextView) fragmento.findViewById(R.id.link_registro);
        btnEnviar.setOnClickListener(this);
        link_registro.setOnClickListener(this);

        return fragmento;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.act1_btn1:
                if ((campoUsuario.getText().toString().equals(usuario)) && (campoPasswd.getText().toString().equals(passwd)))
                {
                    final Intent intento = new Intent(this.getContext(),MainActivity2.class);
                    startActivity(intento);
                    getActivity().finish();
                }
                else{
                    Toast.makeText(this.getContext(),"Datos Erroneos",Toast.LENGTH_SHORT).show();
                };
                break;

            case R.id.link_registro:
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container_1, RegistroFragment.class, null,"addf1")
                        .addToBackStack("RegistroFragment")
                        .commit();
                break;
        }

    }

}