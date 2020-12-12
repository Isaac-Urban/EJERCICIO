package com.example.ejercicios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = (EditText)findViewById(R.id.et_number);
        et_descripcion = (EditText)findViewById(R.id.et_descripcion);
        et_precio = (EditText)findViewById(R.id.et_precio);

    }


//Dar de registro los productos
    public void Registrar (View view){
        AdminSQL admin = new AdminSQL(this, "Administracion", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();                                              //abrir base de datos en lectura y escritura

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

//        Validacion
        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            BaseDatos.insert("articulos", null, registro);
            BaseDatos.close();
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this, "Por favor, llena los campos", Toast.LENGTH_SHORT).show();
        }

    }


//    metodo de consulta
    public void Search (View view){
        AdminSQL admin = new AdminSQL(this, "Administracion", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();                                                            //recuperar valor con el que buscamos valores
        if (!codigo.isEmpty()){
            Cursor fila = BaseDatos.rawQuery("select descripcion, precio from articulos where codigo =" + codigo, null);      //apoya a aplicar un select

            if (fila.moveToFirst()){                                                                                 //permite identificar esta situacion, revisar si nuesrta consulta tiene valores
                et_descripcion.setText(fila.getString(0));                                             //el primer valor siempre debe ser un cero
                et_precio.setText(fila.getString(1));
                BaseDatos.close();                                                                                  //importante cerrarla porque ya viene abierta
            }else {
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
        }else {
           Toast.makeText(this, "Ingresa el código del producto", Toast.LENGTH_SHORT).show();
        }
    }


//    metodo de eliminar
    public void Delete (View view){
        AdminSQL admin = new AdminSQL(this, "Administracion", null, 1);
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();                                                             //recuperamos el codgio

        if (!codigo.isEmpty()){                                                                                    //validacion
            int cantidad = BaseDatos.delete("articulos", "codigo=" + codigo, null);   //codigo=, se situa allí y lo borra, retorna un valor entero
            BaseDatos.close();

            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            if (cantidad == 1){                                                                                      //se elimino el produucto                                                                            //mensaje de eliminacion
                Toast.makeText(this, "Eliminación exitosa", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Este producto no existe", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Debes ingresar el código del producto", Toast.LENGTH_SHORT).show();
        }
    }

//    metodo para modificar
    public void Change (View view){
        AdminSQL admin = new AdminSQL(this, "Administracion", null,1 );
        SQLiteDatabase BaseDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad = BaseDatos.update("articulos", registro, "codigo=" + codigo, null);
            BaseDatos.close();

            if (cantidad == 1){
                Toast.makeText(this, "Modificación exitosa", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Este producto NO existe", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
