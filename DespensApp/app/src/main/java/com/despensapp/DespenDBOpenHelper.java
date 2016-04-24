package com.despensapp;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

/**
 * Created by Adrián on 25/02/2016.
 */

//Clase para la creación de la base de datos
public class DespenDBOpenHelper extends SQLiteOpenHelper {

    //sentencias para crear tablas para la base de datos
   // final String k_ini = "CREATE TABLE INI(listas number(1), arts number(1), CONSTRAINT PK_INI PRIMARY KEY(listas))";
   // final String ini = "INSERT INTO INI VALUES (0,0)";
    final String  k_articulo = "CREATE TABLE ARTICULO(id integer," +
                                                "nombre varchar(50) ," +
                                                "precio number(6,2) ," +
                                                "cantidadActual integer," +
                                                "cantidadComprada integer  ," +
                                                "estado varchar(3) ," +
                                                "nombreLista varchar(50) ," +
                                                "fecha DATE ," +
                                                "fechaArt DATE ," +
                                                "CONSTRAINT PK_ARTICULO PRIMARY KEY(id) ," +
                                                "CONSTRAINT FK_ARTICULO_LISTACOMPRA FOREIGN KEY(nombre,fecha) references LISTACOMPRA)";
    final String  k_lista ="CREATE TABLE LISTACOMPRA(nombre VARCHAR(50)," +
                                                "fecha DATE," +
                                                "visible Boolean," +
                                                "CONSTRAINT PK_LISTACOMPRA PRIMARY KEY(nombre,fecha))";

    public DespenDBOpenHelper(Context contexto, String nombre,SQLiteDatabase.CursorFactory factory,int version){
        super(contexto,nombre,factory,version);

    }
    //método que se ejecuta para crear la base de datos si aún no esta creada
    @Override
    public void onCreate(SQLiteDatabase bd){
        //TODO TABLAS PARA BASE DE DATOS
        try {
            bd.execSQL(k_articulo);
            bd.execSQL(k_lista);
           // bd.execSQL((k_ini));
           // bd.execSQL(ini);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd,int anteriorversion,int nuevaversion){
        try {
            if (anteriorversion < 2) {
                String cadena = "ALTER TABLE ARTICULO ADD fechaArt Date;";
                bd.execSQL((cadena));
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

}
