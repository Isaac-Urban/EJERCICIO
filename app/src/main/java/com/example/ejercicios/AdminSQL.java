package com.example.ejercicios;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//    Tiene como objetivo dministrar la base de datos
public class AdminSQL extends SQLiteOpenHelper {

//    constructor
    public AdminSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BD) {
        BD.execSQL("create table articulos(codigo int primary key, descripcion text, precio real)");        //creamos la tabla con el nombre de las columnas
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
