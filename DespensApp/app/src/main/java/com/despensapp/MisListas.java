package com.despensapp;

import android.widget.ArrayAdapter;

/**
 * Created by Sergio on 24/02/2016.
 */
public class MisListas {

    private String nombre;
    private String fecha;

    public MisListas(String nombre, String fecha){

        this.nombre=nombre;
        this.fecha=fecha;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() { return nombre;}
    public String getFecha() { return fecha; }

    @Override
    public String toString() {return nombre+","+fecha;}
}

