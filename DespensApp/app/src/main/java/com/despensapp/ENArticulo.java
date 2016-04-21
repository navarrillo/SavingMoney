package com.despensapp;

/**
 * Created by Adrián on 07/03/2016.
 */

/**
 * La clase ENArticulo almacena la imformación sobre un articulo de la bd
 */
public class ENArticulo {

   //numArticulos es una variable común a tódos los artículos y que contendrá el número de articulos que hay en total
    private static int numArticulos = 0;
    //identificador de un articulo
    private int id;
    private String nombre;
    private float precio;
    //cantidad actual en la nevera o despensa
    private int cantidadActual;
    //cantidad de veces que se ha comprado dicho articulo
    private int cantidadComnprada;
    //string que indica si se encuentra en la nevera o despensa
    String estado;
    String fechaArt;
    //(estado puede ser = des, nev, nvi "NO VISIBLE", esp (SIN NEVERA NI DESPENSA, ESPERA)
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor de clase, cuando se añade un articulo se suma 1 a numArticulos
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ENArticulo(String nombre, float precio, int cantidadComnprada, String estado,String fecha){

        this.id = numArticulos;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadComnprada = cantidadComnprada;
        this.cantidadActual = cantidadComnprada;
        this.estado = estado;
        numArticulos += 1;
        this.fechaArt = fecha;
    }

    public ENArticulo(int id,String nombre, float precio, int cantidadComnprada, String estado, int cantidadActual,String fechaArt){

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadComnprada = cantidadComnprada;
        this.cantidadActual = cantidadActual;
        this.estado = estado;
        this.fechaArt = fechaArt;
        numArticulos += 1;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SETTERS
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setPrecio(float precio){
        this.precio = precio;
    }

    public void setCantidadActual(int ca){
        cantidadActual = ca;
    }

    public void setEstado(String estado){
        this.estado = estado;
    }

    public void setCantidadComnprada(int cc){
        cantidadComnprada = cc;
    }
    public int getId(){
        return id;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GETTERS
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getNombre(){
        return nombre;
    }

    public float getPrecio(){
        return precio;
    }

    public String getFechaArt() { return fechaArt; }
    public java.lang.String getEstado() {
        return estado;
    }

    public int getCantidadActual(){
        return cantidadActual;
    }

    public int getCantidadComnprada(){
        return cantidadComnprada;
    }

    public static int getNumArticulos(){
        return numArticulos;
    }

    public static void setNumArticulos(int num){
        numArticulos = num;
    }
}
