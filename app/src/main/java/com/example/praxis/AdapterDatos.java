package com.example.praxis;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praxis.entidades.Productos;
import com.example.praxis.fragmentos.SegundoFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos>{

    //Se define la lista que esta clase adaptadora recibe
    ArrayList<Productos> listaProductos;
    Context context;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public AdapterDatos(ArrayList<Productos> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vista_productos, null, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.titulo.setText(listaProductos.get(position).getTitulo());
        holder.contenido.setText(listaProductos.get(position).getContenido());
        //holder.campo_imagen.setImageResource(listaProductos.get(position).getImagen());

        Picasso.get()
                .load(listaProductos.get(position).getImagen())
                .into(holder.campo_imagen);

        holder.setOnclickListeners();
    }

    @Override
    public int getItemCount()  {
        return listaProductos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        //Se crea el componente al que se hara referencia
        TextView titulo, contenido;
        ImageView campo_imagen;


        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            //Se inicializa el componente con una instancia a la referencia
            titulo = (TextView) itemView.findViewById(R.id.texto_titulo);
            contenido = (TextView) itemView.findViewById(R.id.texto_contenido);
            campo_imagen = (ImageView) itemView.findViewById(R.id.campo_imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msj = titulo.getText().toString();
                    Toast.makeText(v.getContext(),"Tocaste el "+msj,Toast.LENGTH_SHORT).show();


                    //Toast.makeText(itemView.getContext(), titulo.getText().toString(), Toast.LENGTH_SHORT).show();

                  /*  switch (titulo.getText().toString())
                    {
                        case "Titulo 1":
                            Toast.makeText(itemView.getContext(), "item1!!", Toast.LENGTH_SHORT).show();
                            break;
                        case "Titulo 2":
                            Toast.makeText(itemView.getContext(), "item2!", Toast.LENGTH_SHORT).show();
                            break;
                        case "Titulo 3":
                            Toast.makeText(itemView.getContext(), "item3!!!", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(itemView.getContext(), "otro...", Toast.LENGTH_SHORT).show();
                            break;
                    }*/
                }
            });
        }

        public void setOnclickListeners() {

        }
    }
}
