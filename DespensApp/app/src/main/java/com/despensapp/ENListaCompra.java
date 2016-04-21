package com.despensapp;

import java.util.ArrayList;

/**
 * Created by Adrián on 07/03/2016.
 */
public class ENListaCompra {

    private String nombre;
    private String fecha;
    private Boolean visible;
    private ArrayList<ENArticulo> articulos;

    public ENListaCompra(String nombre,String fecha,Boolean visible){
        this.nombre = nombre;
        this.fecha = fecha;
        this.visible = visible;
        this.articulos = new ArrayList<>();

    }

    public ENListaCompra(String nombre,String fecha){
        this.nombre = nombre;
        this.fecha = fecha;
        this.visible = true;
        this.articulos = new ArrayList<>();
    }

    public void add(ENArticulo a){
        this.articulos.add(a);
    }

    public void setNombre(String nombre){
        //DespenBD.editarLista(this.nombre,this.fecha,nombre,this.fecha);
        this.nombre = nombre;
    }

    public void setFecha(String fecha){
       // DespenBD.editarLista(this.nombre,this.fecha,this.nombre,fecha);
        this.fecha = fecha;
    }
    public void setVisible(Boolean visible) {
        //DespenBD.ListaSetVisible(nombre,fecha,visible);
        this.visible = visible;
    }

    public boolean getVisible(){
        return visible;
    }

    public String getNombre(){
        return nombre;
    }

    public String getFecha(){
        return fecha;
    }

    public ArrayList<ENArticulo> getArticulos(){
        return articulos;
    }

    public int getNumArticulos(){
        if(articulos == null || articulos.isEmpty()) return 0;
        else return ENArticulo.getNumArticulos();
    }

    @Override
    public String toString() {return nombre+","+fecha;}

}
