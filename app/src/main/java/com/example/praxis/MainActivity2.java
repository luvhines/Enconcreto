package com.example.praxis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praxis.entidades.Productos;
import com.example.praxis.fragmentos.PrimerFragment;
import com.example.praxis.fragmentos.SegundoFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    //variebles de clase para el primer fragment
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    String usuarioNombre;
    String usuarioMail;

    ArrayList<Productos> listaProductos;

    TextView campoNombreUsuario, campoMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //personalizamos el toolbar
        crearToolbar("Enconcreto");


        usuarioNombre = getIntent().getStringExtra("nombre");
        usuarioMail = getIntent().getStringExtra("mail");


        /*Inicializamos y enlazamos los componentes
         open y close son recursos String que deben ser creados*/
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation_view);

        campoNombreUsuario = navigationView.getHeaderView(0).findViewById(R.id.text_usuario_nombre);
        campoMail = navigationView.getHeaderView(0).findViewById(R.id.text_usuario_mail);
        campoNombreUsuario.setText(usuarioNombre);
        campoMail.setText(usuarioMail);

        campoNombreUsuario = (TextView) findViewById(R.id.text_usuario_nombre);
        campoMail = (TextView) findViewById(R.id.text_usuario_mail);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                null,
                (R.string.open),
                (R.string.close)
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //cargar el primer fragment
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("productos", listaProductos);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragmento = new PrimerFragment();
//        fragmento.setArguments(bundle);
        fragmentTransaction.add(R.id.contenedor_fragmentos, fragmento).commit();

        //programar evento onclik en el navigationView que contiene el menu
        navigationView.setNavigationItemSelectedListener(this);

    }

    public void crearToolbar(String titulo){
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setTitle(titulo);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Bundle bundle = new Bundle();

        switch (item.getItemId())
        {

            case R.id.item_productos:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedor_fragmentos, new PrimerFragment());
                fragmentTransaction.commit();
                ;break;
            case R.id.item_servicios:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.contenedor_fragmentos, new SegundoFragment());
                fragmentTransaction.commit();
                ;break;
            case R.id.item_pol_uso:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragmentoPolUso = new PoliticaUsoFragment();
                bundle.putBoolean("desdeAct2", true);
                fragmentoPolUso.setArguments(bundle);
                fragmentTransaction.replace(R.id.contenedor_fragmentos, fragmentoPolUso);
                fragmentTransaction.addToBackStack("politicaUso").commit();
                ;break;
            case R.id.item_por_privacidad:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragmentoPolPrivacidad = new PoliticaPrivacidadFragment();
                bundle.putBoolean("desdeAct2", true);
                fragmentoPolPrivacidad.setArguments(bundle);
                fragmentTransaction.replace(R.id.contenedor_fragmentos, fragmentoPolPrivacidad);
                fragmentTransaction.addToBackStack("politicaPrivacidad").commit();
                ;break;

            case R.id.item_salir:
                finish();
                ;break;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                ;break;
        }

        return super.onOptionsItemSelected(item);
    }

}