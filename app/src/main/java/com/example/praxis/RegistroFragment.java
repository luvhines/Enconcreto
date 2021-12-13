package com.example.praxis;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.room.Room;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praxis.entidades.Usuarios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistroFragment extends Fragment implements View.OnClickListener, FragmentResultListener, OnSuccessListener ,Runnable{

    TextView terminosUso, politicaPrivacidad , campoNombre, campoApellido, campoEmail, campoPassword;
    private boolean flagPrivacidad , flagUso;

    //Creamosuna variable de tipo a la bd
    AppDataBase db;

    Button btnCrearCuenta ,btnCargar;
    int flagHilo = 0;

    ExecutorService executor;
    Handler handler;

    String cadena  = "";

    Context context;
    FragmentManager fragmentManager;

    Usuarios datosUsuario;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        fragmentManager = getParentFragmentManager();

        getParentFragmentManager().setFragmentResultListener("privacidad", this, this);
        getParentFragmentManager().setFragmentResultListener("uso", this, this);

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmento =  inflater.inflate(R.layout.fragment_registro, container, false);

        //Campos que deben ser guardados en la base de datos
        campoNombre =  fragmento.findViewById(R.id.reg_nombre);
        campoApellido = fragmento.findViewById(R.id.reg_apellido);
        campoEmail = fragmento.findViewById(R.id.reg_email);
        campoPassword = fragmento.findViewById(R.id.reg_password);
        btnCrearCuenta =fragmento.findViewById(R.id.btn_crear_registrp);
        btnCargar = fragmento.findViewById(R.id.cargar);

        //enlaces a los fragmentos de politicas
        terminosUso = fragmento.findViewById(R.id.reg_term_uso);
        politicaPrivacidad =  fragmento.findViewById(R.id.reg_pol_privacidad);

        //escuchadores
        btnCrearCuenta.setOnClickListener(this);
        btnCargar.setOnClickListener(this);
        terminosUso.setOnClickListener(this);
        politicaPrivacidad.setOnClickListener(this);


        return fragmento;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_crear_registrp:
                if (!(campoNombre.getText().toString().equals(""))
                        & !(campoApellido.getText().toString().equals(""))
                        & !(campoEmail.getText().toString().equals(""))
                        & !(campoPassword.getText().toString().equals(""))
                ){
                    if (flagUso && flagPrivacidad == true){
                        flagHilo=1;
                        //Activamos el hilo
                        executor.execute(this);
                    }else{
                        Toast.makeText(this.getContext(), "Por favor acepte nuestras politicas", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(this.getContext(), "¡Hay campos vacios¡", Toast.LENGTH_LONG).show();
                    resaltarVacios();
                }
                break;

            case R.id.reg_term_uso:
                //Cargamos el fragment de politica de uso
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container_1, PoliticaUsoFragment.class, null,"politicaUso")
                        .addToBackStack("politicaUso")
                        .commit();
                break;

            case R.id.reg_pol_privacidad:
                //Cargamos el fragment de politica de privacidad
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.container_1, PoliticaPrivacidadFragment.class, null,"politicaPrivacidad")
                        .addToBackStack("politicaPrivacidad")
                        .commit();
                break;

            case R.id.cargar:
                flagHilo=2;
                executor.execute(this);
                break;
        }
    }

    @Override
    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
        String dato ="";
        switch (requestKey)
        {
            case "privacidad":
                dato = result.getString("dato");
                flagPrivacidad = result.getBoolean("ckPrivacidad");
                break;

            case "uso":
                dato = result.getString("dato");
                flagUso = result.getBoolean("ckUso");
                break;
        }

    }

    @Override
    public void run() {

        switch (flagHilo){
            case 1:
                //Guardamos los datos en la base de datos
                db = Room.databaseBuilder(context, AppDataBase.class,
                        "enconcretoDB").build();

                Usuarios usuario = new Usuarios(campoNombre.getText().toString(),
                        campoApellido.getText().toString(),
                        campoEmail.getText().toString(),
                        campoPassword.getText().toString());

                //primero buscamos el usuario validamos que no exista en la base de datos
                Usuarios usuarioAcomparar = db.usuariosDao().findByMail(usuario.getUsrMail());
                if (usuarioAcomparar == null){
                    //Enviamos el usuario ala base de datos
                    db.usuariosDao().insert(usuario);
                    flagHilo = 0;
                    //guardar en la nube

                    //leemos el ultimo usuario de la base de datos local para acceder a su id
                    List<Usuarios> listaLocal = db.usuariosDao().getAll();
                    int index = listaLocal.size();

                    //Enviamos el usuario a firebase
                    guardareEnNube(listaLocal.get(index-1));

                    //Por medio de un handler accedemos a la ui para mostrar un mensaje
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Cuenta creada", Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    //Por medio de un handler accedemos a la ui para mostrar un mensaje
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "Este usurio ya existe", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                break;

            case 2:
                //leemos todos los usuarios en la base de datos local
                db = Room.databaseBuilder(context, AppDataBase.class,
                        "enconcretoDB").build();

                List<Usuarios> lista = db.usuariosDao().getAll();

                for (Usuarios objUsuario: lista) {
                    cadena +=  "ID: "+objUsuario.getId()+"\n"
                            +   "Nombre: "+objUsuario.getUsrNombre()+"\n"
                            +   "Apellido: "+objUsuario.getUsrApellido()+"\n"
                            +   "Email: "+objUsuario.getUsrMail()+"\n"+"\n";
                }
                flagHilo = 0;

                //Por medio de un handler accedemos a la ui
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), cadena, Toast.LENGTH_SHORT).show();
                        cadena = "";
                        getContext().deleteDatabase("enconcretoDB");
                    }
                });
                break;
        }
    }

    private void guardareEnNube(Usuarios usuario) {
        //enviamos los datos a la nube

        Map<String, Object> user = new HashMap<>();
        user.put("nombre", usuario.getUsrNombre());
        user.put("apellido", usuario.getUsrApellido());
        user.put("mail", usuario.getUsrMail());
        user.put("password", usuario.getUsrPassword());

        MainActivity.firestoreDB.collection("USUARIOS")
                .add(user)
                .addOnSuccessListener(this);

        datosUsuario = usuario;
    }

    private void resaltarVacios()
    {
       if (campoNombre.getText().toString().equals("")) {
           campoNombre.setHintTextColor(getResources().getColor(R.color.red_500));
       }
       if (campoApellido.getText().toString().equals("")){
           campoApellido.setHintTextColor(getResources().getColor(R.color.red_500));
       }
       if (campoEmail.getText().toString().equals("")) {
           campoEmail.setHintTextColor(getResources().getColor(R.color.red_500));
       }
       if (campoPassword.getText().toString().equals("")) {
           campoPassword.setHintTextColor(getResources().getColor(R.color.red_500));
       }
    }


    @Override
    public void onSuccess(Object o) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("usuario", datosUsuario);
        fragmentManager.setFragmentResult("registro", bundle);

        Activity actividad = getActivity();
        if (actividad != null)
            getActivity().onBackPressed();
    }
}