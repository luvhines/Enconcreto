package com.example.praxis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praxis.entidades.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InicioFragment extends Fragment implements View.OnClickListener , FragmentResultListener{

    private EditText campoUsuario;
    private EditText campoPasswd;
    private Button btnEnviar, btnGoogle;

    //creamos una variable para referenciar el enlace
    private TextView link_registro;

    //datos temporales de inicio
    private Usuarios usuarioRegistro;

    int contador =0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("registro", this, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_inicio, container, false);

        campoUsuario = fragmento.findViewById(R.id.act1_campo1);
        campoPasswd = fragmento.findViewById(R.id.act1_campo2);
        btnEnviar = fragmento.findViewById(R.id.act1_btn1);
        btnGoogle = fragmento.findViewById(R.id.act1_btn2);

        //referenciamos el objeto
        link_registro = fragmento.findViewById(R.id.link_registro);
        btnEnviar.setOnClickListener(this);
        link_registro.setOnClickListener(this);

        btnGoogle.setOnClickListener(this);

        return fragmento;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.act1_btn1:
                contador = 1;
                consultarUsuario();
                break;

            case R.id.link_registro:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container_1, RegistroFragment.class, null,"addf1")
                        .addToBackStack("RegistroFragment")
                        .commit();
                break;

            case R.id.act1_btn2:
                String msj="";
                if (usuarioRegistro != null) {
                    msj += "ID: " + usuarioRegistro.getId() + "\n"
                            + "Nombre: " + usuarioRegistro.getUsrNombre() + "\n"
                            + "Apellido: " + usuarioRegistro.getUsrApellido() + "\n"
                            + "Email: " + usuarioRegistro.getUsrMail() + "\n" + "\n";

                    Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
                }
                ;break;
        }

    }

    private void consultarUsuario(){
        Task<QuerySnapshot> task= MainActivity.firestoreDB.collection("USUARIOS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        buscarUsuario(task);
                    }
                });
    }
    private void buscarUsuario(Task<QuerySnapshot> task){
        String nombreUsSesion = "";
        String mailUsSesion = "";
        if (task.isSuccessful()){
            for (QueryDocumentSnapshot document: task.getResult()) {
                String nombre = document.getString("nombre");
                String apellido = document.getString("apellido");
                String mail = document.getString("mail");
                String password = document.getString("password");

                if (campoUsuario.getText().toString().equals(mail) &&
                        campoPasswd.getText().toString().equals(password))
                {
                    contador = 2;
                    nombreUsSesion = nombre;
                    mailUsSesion = mail;
                }
            }

            if (contador == 2){
                Toast.makeText(getContext(), "Bienvenido " + nombreUsSesion, Toast.LENGTH_SHORT).show();
                final Intent intento = new Intent(this.getContext(), MainActivity2.class);
                intento.putExtra("nombre",nombreUsSesion);
                intento.putExtra("mail", mailUsSesion);
                startActivity(intento);
                getActivity().finish();
                contador = 0;
            }else{
                Toast.makeText(getContext(),  "Error de inicio ", Toast.LENGTH_SHORT).show();
                contador = 0;
            }
        }
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

        if (requestKey.equals("registro") && result != null) {
            usuarioRegistro = (Usuarios) result.getSerializable("usuario");
            campoUsuario.setText(usuarioRegistro.getUsrMail());
            campoPasswd.setText(usuarioRegistro.getUsrPassword());
        }


    }
}