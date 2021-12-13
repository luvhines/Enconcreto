package com.example.praxis.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.praxis.AdapterDatos;
import com.example.praxis.MainActivity;
import com.example.praxis.MainActivity2;
import com.example.praxis.R;
import com.example.praxis.entidades.Productos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PrimerFragment extends Fragment {

    //creamos la lista que se envia al adaptador
    ArrayList<Productos> listaProductos;

    //creamos la referencia al reciclerView que mostrara los datos
    RecyclerView recycler;

    public PrimerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmento = inflater.inflate(R.layout.fragment_primer, container, false);

        //Inicializamos la lista
        listaProductos = new ArrayList<>();

        consultarProductos();
        return fragmento;
    }

    private void llenarDatos() {

        String Urlimagen = "https://firebasestorage.googleapis.com/v0/b/enconcretodb.appspot.com/o/imgen.png?alt=media&token=3db18fdf-65b5-4e60-b63d-94c9d5441895";

        for (int i = 0; i < 50; i++) {
            listaProductos.add(new Productos("Titulo "+i, "la ruta nos aporto otro paso natural " +
                    "la ruta nos aporto otro paso natural", Urlimagen));

        }
    }

    private void consultarProductos(){
        Task<QuerySnapshot> task= MainActivity.firestoreDB.collection("PRODUCTOS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        buscarProducto(task);
                    }
                });
    }
    private void buscarProducto(Task<QuerySnapshot> task){
        String titulo = "";
        String contenido = "";
        String urlImagen="";

        if (task.isSuccessful()){
            for (QueryDocumentSnapshot document: task.getResult()) {
                contenido=document.getString("contenido");
                urlImagen=document.getString("imagen");
                titulo=document.getString("titulo");
                Productos producto = new Productos(titulo,contenido,urlImagen);
                listaProductos.add(producto);
            }

            //inicializamos el recycler y configuramos su comportamiento
            recycler = (RecyclerView) getActivity().findViewById(R.id.recycler_ID);
            recycler.setLayoutManager(new GridLayoutManager(getContext(),2));

            // enviamos la lista con los datos ya almacenados al adaptador
            AdapterDatos adaptador = new AdapterDatos(listaProductos);

            //seteamos el recycler con este adapdador ya lleno
            recycler.setAdapter(adaptador);
        }
    }

}