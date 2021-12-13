package com.example.praxis.entidades;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Usuarios implements Serializable{
    //establecemos una llave primaria autoincremental
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "usr_nombre")
    String usrNombre;

    @ColumnInfo(name = "usr_apellido")
    String usrApellido;

    @ColumnInfo(name = "usr_mail")
    String usrMail;

    @ColumnInfo(name = "usr_password")
    String usrPassword;

    public  Usuarios(){}

    public Usuarios(String usrNombre, String usrApellido, String usrMail ,String usrPassword) {
        //this.id = id;
        this.usrNombre = usrNombre;
        this.usrApellido = usrApellido;
        this.usrMail = usrMail;
        this.usrPassword = usrPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsrNombre() {
        return usrNombre;
    }

    public void setUsrNombre(String usrNombre) {
        this.usrNombre = usrNombre;
    }

    public String getUsrApellido() {
        return usrApellido;
    }

    public void setUsrApellido(String usrApellido) {
        this.usrApellido = usrApellido;
    }

    public String getUsrMail() {
        return usrMail;
    }

    public void setUsrMail(String usrMail) {
        this.usrMail = usrMail;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

}
