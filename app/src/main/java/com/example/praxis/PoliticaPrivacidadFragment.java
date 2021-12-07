package com.example.praxis;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class PoliticaPrivacidadFragment extends Fragment implements View.OnClickListener{

    private CheckBox ckAceptar;

    private Button btnVolver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento =inflater.inflate(R.layout.fragment_politica_privacidad, container, false);

        ckAceptar = (CheckBox) fragmento.findViewById(R.id.privacidad_check_aceptar);
        btnVolver = (Button) fragmento.findViewById(R.id.priv_btn_volver);
        btnVolver.setOnClickListener(this);

        return fragmento;
    }

    @Override
    public void onClick(View v) {
        if (ckAceptar.isChecked() == true){
            Bundle result = new Bundle();
            result.putString("dato", "RETORNADO");
            result.putBoolean("ckPrivacidad", true);
            getParentFragmentManager().setFragmentResult("privacidad", result);
        }

        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}