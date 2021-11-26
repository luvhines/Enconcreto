package com.example.praxis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText campoUsuario;
    private EditText campoPasswd;
    private Button btnEnviar;

    private String usuario = "admin";
    private String passwd = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoUsuario = (EditText) findViewById(R.id.act1_campo1);
        campoPasswd = (EditText) findViewById(R.id.act1_campo2);
        btnEnviar = (Button) findViewById(R.id.act1_btn1);
    }

    public void eventoBoton(View view){

        if ((campoUsuario.getText().toString().equals(usuario)) && (campoPasswd.getText().toString().equals(passwd)))
        {
            final Intent intento = new Intent(this,MainActivity2.class);
            startActivity(intento);
        }
        else{
            Toast.makeText(this,"Datos Erroneos",Toast.LENGTH_SHORT).show();
        }
    }
}