package com.example.praxis;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.praxis.entidades.Usuarios;
import com.example.praxis.entidadesdao.UsuariosDao;

//Esta clase debera definir todas las entidades
@Database(entities = {Usuarios.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UsuariosDao usuariosDao();
}

