package com.example.praxis.entidadesdao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.praxis.entidades.Usuarios;

import java.util.List;


@Dao
public interface UsuariosDao {

    @Query("SELECT * FROM Usuarios")
    List<Usuarios> getAll();

    @Query("SELECT * FROM Usuarios WHERE usr_nombre LIKE :usuario LIMIT 1")
    Usuarios findByName(String usuario);

    @Insert
    Long insert(Usuarios usuarios);

}
