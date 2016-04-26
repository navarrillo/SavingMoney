package com.despensapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Adrián on 04/03/2016.
 */

// Clase creada para controlar los datos de la Base de datos
public class DespenBD {

    private static DespenDBOpenHelper doh;
    //articulos que no estan en listas
    private static ArrayList<ENArticulo> articulos;
    //listas con sus articulos
    private static ArrayList<ENListaCompra> listas;



    ///////////////////////////////////////////////////////////////////////////////////////////
    //constructor generador de datos a traves de la bd
    ///////////////////////////////////////////////////////////////////////////////////////////

    public DespenBD(Context contexto){

        doh = new DespenDBOpenHelper(contexto,"despenBD",null,2);
        articulos = new ArrayList<>();
        listas = new ArrayList<>();

        //llamada a los métodos que rellenarán la bd

        clearAllBD(); //limpia la base de datos
        this.getListasBD();
        this.getArticulosBD();

        iniciaValores(listasIniciales(), articulosIniciales());//inicia los valores


    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // datos de articulos que se añadiran a la bd por defecto
    ///////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<String> articulosIniciales(){
        //para insertar articulos (nombre, precio,estado (esp,des,nev,nvi(no visible), nomlista, fecha) y && para separar
        String articulos = "Salchipapa 5 2 esp && " +
                "langostinos 10 1 nev Navidad 22/12/2015 && " +
                "ternera 7.2 1 nev Carniceria 20/11/2015 && " +
                "Galletas 1 4 des Supermercado 03/12/2014 && " +
                "Cereales 1.6 1 des Supermercado 03/12/2014 && " +
                "Aceite 3.2 4 des Supermercado 03/12/2014 && " +
                "Zumo 1.5 6 des Supermercado 03/12/2014 && " +
                "Uvas 3.8 4 nev Navidad 22/12/2015 && " +
                "Chuletón 12.2 4 nev Navidad 22/12/2015 && " + //
                "LataConserva 0.8 4 des Supermercado 03/12/2014 && " +
                "QuesoCurado 6.2 1 nev Carniceria 20/11/2015 && " +
                "HeladoFresa 4 2 nev Supermercado 03/12/2014 && " +
                "Cava 1.8 2 nev Navidad 22/12/2015 && " +
                "Aceitunas 0.7 1 des Navidad 22/12/2015 && " +
                "Chorizo 4.9 2 nev Carniceria 20/11/2015";


        ArrayList<String> articuloos = new ArrayList<>();

        for(String arti: articulos.split(" && ")){
            articuloos.add(arti);
        }
         return articuloos;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // datos de listas que se añadiran a la bd por defecto
    ///////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<String> listasIniciales(){

        //para añadir nuevas listas modifica el string cadena y separa con " && "
        String cadena = "Supermercado 03/12/2014  && Carniceria 20/11/2015 && Navidad 22/12/2015";
        ArrayList<String> cadenas = new ArrayList<>();

        for(String lista: cadena.split(" && ")){
            cadenas.add(lista);
        }

        return cadenas;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // añade articulos a la bd para iniciar valores (listas)
    ///////////////////////////////////////////////////////////////////////////////////////////

    private void iniciaValores(ArrayList<String> listas, ArrayList<String> articulos){

        SQLiteDatabase bd = doh.getWritableDatabase();
        Calendar fechaa = Calendar.getInstance();
        String fechaActual = String.valueOf(fechaa.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaa.get(Calendar.MONTH) + 1) + "/" +
                String.valueOf(fechaa.get(Calendar.YEAR));


        try{
            if(bd == null){
                return;
            }
            else{
                for(String lista: listas){
                    String nombre =lista.split(" ")[0];
                    String fecha = lista.split(" ")[1];
                    bd.execSQL("INSERT INTO LISTACOMPRA(nombre,fecha,visible) VALUES ('" + nombre + "','" + fecha + "','true')");
                    this.listas.add(new ENListaCompra(nombre, fecha));
                }

                for(String arti: articulos){

                    String lisn,feli,nombre,estado;
                    int length = arti.split(" ").length;
                    nombre = arti.split(" ")[0];
                    Float precio = Float.valueOf(arti.split(" ")[1]);
                    int cantdad = Integer.valueOf(arti.split(" ")[2]);
                    estado = arti.split(" ")[3];
                    ENArticulo art = new ENArticulo(nombre,precio,cantdad,estado,fechaActual);

                    if(length>4){
                        lisn = arti.split(" ")[4];
                        feli = arti.split(" ")[5];

                        bd.execSQL("INSERT INTO ARTICULO VALUES (" + art.getId() + ",'" + art.getNombre() + "','" + art.getPrecio() + "','" +
                                art.getCantidadActual() + "','" + art.getCantidadComnprada() + "','" + art.estado + "','" +
                                lisn + "','" + feli + "' , '" + art.getFechaArt() + "')");

                        this.listas.get(findLista(lisn,feli)).add(art);

                    }
                    else{
                        bd.execSQL("INSERT INTO ARTICULO VALUES (" + art.getId() + ",'" + art.getNombre() + "','" + art.getPrecio() + "','" +
                                art.getCantidadActual() + "','" + art.getCantidadComnprada() + "','" + art.estado + "', NULL, NULL, '" + art.getFechaArt() + "')");
                        this.articulos.add(art);

                    }
                    //bd.close();

                }
                doh.close();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage() + "\n Error al iniciar datos en la BD (inicia valores)");
            doh.close();
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // añade articulos en la bd que no esten en ninguna lista
    ///////////////////////////////////////////////////////////////////////////////////////////


    public static  void insertaArticulo(String nombre, float precio, int cantidadComnprada, String estado) {

        Calendar fechaa = Calendar.getInstance();
        String fechaActual = String.valueOf(fechaa.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaa.get(Calendar.MONTH) + 1) + "/" +
                String.valueOf(fechaa.get(Calendar.YEAR));
        ENArticulo art = new ENArticulo(nombre, precio, cantidadComnprada, estado,fechaActual);


        SQLiteDatabase bd = doh.getWritableDatabase();

        try {
            if (bd == null) {
                return;
            } else {
                bd.execSQL("INSERT INTO ARTICULO VALUES (" + art.getId() + ",'" + art.getNombre() + "','" + art.getPrecio() + "','" +
                        art.getCantidadActual() + "','" + art.getCantidadComnprada() + "','" + art.estado + "', NULL, NULL,  '" + art.fechaArt + "')");

                doh.close();
                articulos.add(art);

            }
        } catch (Exception e) {
            ENArticulo.setNumArticulos(ENArticulo.getNumArticulos() - 1);
            System.out.println(e.getMessage() + " insertArticulo");

        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // añadir articulo que esta en una lista
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static void insertaArticulo(String nombre, float precio, int cantidadComnprada, String estado,String lista,String fecha){

        Calendar fechaa = Calendar.getInstance();
        String fechaActual = String.valueOf(fechaa.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaa.get(Calendar.MONTH) + 1) + "/" +
                String.valueOf(fechaa.get(Calendar.YEAR));
        ENArticulo art = new ENArticulo(nombre,precio,cantidadComnprada,estado,fechaActual);


        SQLiteDatabase bd = doh.getWritableDatabase();

        try{
            if(bd == null){
                return;
            }
            else{
                bd.execSQL("INSERT INTO ARTICULO VALUES (" + art.getId() + ",'" + art.getNombre() + "','" + art.getPrecio() + "','" +
                        art.getCantidadActual() + "','" + art.getCantidadComnprada() + "','" + art.estado + "','" +
                        lista + "','" + fecha + "' , '" + fechaActual + "')");

                doh.close();
                listas.get(findLista(nombre,fecha)).add(art);

            }
        }
        catch (Exception e){
            System.out.println(" El articulo ya existe en la bd");

        }

    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // metodo privado para obtener datos de la bd
    ///////////////////////////////////////////////////////////////////////////////////////////

    private void getListasBD(){

        try{

            String consulta = "SELECT * FROM LISTACOMPRA";
            Cursor c = doh.getReadableDatabase().rawQuery(consulta, null);

            while(c.moveToNext()) {
                listas.add(new ENListaCompra(c.getString(0),c.getString(1),Boolean.parseBoolean(c.getString(2))));
            }
            doh.close();

        }
        catch(Exception e){
            System.out.println("Error de acceso BD");
            doh.close();
        }


    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // metodo privado para obtener datos de la bd
    ///////////////////////////////////////////////////////////////////////////////////////////



    private void getArticulosBD(){

        try{
            String nomLista = "";
            String fecha = "";
            String consulta = "SELECT * FROM ARTICULO";
            Cursor c = doh.getReadableDatabase().rawQuery(consulta, null);
            while(c.moveToNext()){

                if(c.getString(6) == null || c.getString(6) == ""){
                    articulos.add(new ENArticulo(c.getInt(0), c.getString(1),c.getFloat(2),c.getInt(3),c.getString(5),c.getInt(4),c.getString((6))));
                }
                else{
                    nomLista = c.getString(6);
                    listas.get(findLista(nomLista,c.getString(7))).add(new ENArticulo(c.getInt(0), c.getString(1), c.getFloat(2), c.getInt(3), c.getString(5), c.getInt(4),c.getString((6))));

                }

            }

            doh.close();
        }
        catch(Exception e){
            System.out.println("No se ha podido obtener datos de la bd");
            doh.close();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // getters
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<ENListaCompra> getListas(){
        return listas;
    }

    public static ArrayList<ENArticulo> getArticulos(){
        return articulos;
    }


    public static ArrayList<ENArticulo> getArticulosTodos(){

        ArrayList<ENArticulo> artic = new ArrayList<>();
        artic=articulos;
        for(ENArticulo art: articulos){
            if(art.getEstado().equals("des")) artic.add(art);
        }

        for(ENListaCompra lis : listas){
            for(ENArticulo art: lis.getArticulos()){
                if(art.getEstado().equals("des")) artic.add(art);
            }
        }

        for(ENArticulo art: articulos){
            if(art.getEstado().equals("nev")) artic.add(art);
        }

        for(ENListaCompra lis : listas){
            for(ENArticulo art: lis.getArticulos()){
                if(art.getEstado().equals( "nev")) artic.add(art);
            }
        }

        return artic;

    }


    public static ArrayList<ENListaCompra> getListasVisibles() {

        ArrayList<ENListaCompra> miListasVisibles = new ArrayList<>();

        for(ENListaCompra lc: listas)
        {
            if(lc.getVisible() == true)
                miListasVisibles.add(lc);
        }

        return miListasVisibles;
    }

    public static ArrayList<ENArticulo> getListaNomFecha(String nombre, String fecha){

        ArrayList<ENArticulo> lista = new ArrayList<ENArticulo>();


        ArrayList<ENListaCompra> miL = DespenBD.getListas();
        for (ENListaCompra lc: miL) {

            System.out.println(nombre+"---"+fecha);
            System.out.println(lc.getNombre()+"---"+lc.getFecha());
            if(lc.getNombre().equals(nombre) && lc.getFecha().equals(fecha)){
                 lista=lc.getArticulos();
            }
        }



        return lista;

    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    //devuelve el número de articulos de la base de datos
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static int numArticulos() {

        int aux = 0;
        if(!listas.isEmpty() && listas != null){
            for (ENListaCompra lc:listas){

                aux += lc.getNumArticulos();
            }
        }

        return ((articulos.isEmpty() || articulos == null)? 0 : articulos.size() + aux);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // modifica una lista para indicar si va a ser visible o no
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static void ListaSetVisible(String nombre, String fecha,Boolean visibilidad){
        SQLiteDatabase bd = doh.getWritableDatabase();

        try{
            if(bd == null){
                return;
            }
            else{

                bd.execSQL("UPDATE  LISTACOMPRA SET visible = '" + visibilidad + "' WHERE nombre = '" +
                        nombre + "' and fecha = '" + fecha + "'");

                listas.get(findLista(nombre,fecha)).setVisible(visibilidad);

                doh.close();
            }
        }
        catch (Exception e){
            System.out.println(e.toString() + "\n Fallo en el acceso a la BD");
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // modifica una lista
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static void editarLista(String oldname, String oldfecha, String nombre,String fecha){

        SQLiteDatabase bd = doh.getWritableDatabase();

        try{
            if(bd == null){
                return;
            }
            else{

                bd.execSQL("UPDATE  LISTACOMPRA SET nombre = '" + nombre + "', fecha = '" + fecha + "' WHERE nombre = '" +
                        oldname + "' AND fecha = '" + oldfecha + "'");
                listas.get(findLista(oldname,oldfecha)).setNombre(nombre);
                listas.get(findLista(nombre,oldfecha)).setFecha(fecha);

                doh.close();
            }
        }
        catch (Exception e){
            System.out.println(e.toString() + "\n Fallo en el acceso a la BD");
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // añade una lista
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static boolean insertaLista(String nombre, String fecha){

        SQLiteDatabase bd = doh.getWritableDatabase();

        try{
            if(bd == null){
                return false;
            }
            else{
                bd.execSQL("INSERT INTO LISTACOMPRA(nombre,fecha,visible) VALUES ('" + nombre + "','" + fecha + "','true')");
                listas.add(new ENListaCompra(nombre, fecha));
                doh.close();
                return true;
            }
        }
        catch (Exception e){
            System.out.println(e.toString() + "\n Error en el acceso a la bd");
            return false;
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // cierra la bd
    ///////////////////////////////////////////////////////////////////////////////////////////

    private void close(){
        this.doh.close();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // vacía la bd
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static void clearAllBD(){

        SQLiteDatabase bd = doh.getWritableDatabase();

        try{
            if(bd == null){
                return ;
            }
            else{
                bd.execSQL("DELETE  FROM LISTACOMPRA");
                bd.execSQL("DELETE  FROM ARTICULO");
                listas.clear();
                articulos.clear();
                doh.close();

            }
        }
        catch (Exception e){
            System.out.println(e.toString() + "\n Error en el acceso a la bd");
            doh.close();
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // devuelve el índice del array que se encuentra una lista
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static int findLista(String nombre,String fecha){

        int index = 0;
        for(ENListaCompra aux:listas){

            if(aux.getNombre().equals(nombre) && aux.getFecha().equals(fecha)){
                return index;
            }
            index+=1;
        }

        return -1;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // devuelve los articulos que pertenezcan a la despensa
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<ENArticulo> getArticulosDespensa(){

        ArrayList<ENArticulo> artDespen = new ArrayList<>();

        for(ENArticulo art: articulos){
            if(art.getEstado().equals("des")) artDespen.add(art);
        }

        for(ENListaCompra lis : listas){
            for(ENArticulo art: lis.getArticulos()){
                if(art.getEstado().equals("des")) artDespen.add(art);
            }
        }

        return artDespen;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    // devuelve los articulos que se encuentren en la nevera
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<ENArticulo> getArticulosNevera(){

        ArrayList<ENArticulo> artNev = new ArrayList<>();

        for(ENArticulo art: articulos){
            if(art.getEstado().equals("nev")) artNev.add(art);
        }

        for(ENListaCompra lis : listas){
            for(ENArticulo art: lis.getArticulos()){
                if(art.getEstado().equals( "nev")) artNev.add(art);
            }
        }

        return artNev;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // devuelve el valor de los articulos comprados hasta ahora (iniciando en una fecha)
    // ejemplo: si ahora es 14/03/2015 si llamamos con 14/02/2015 es el coste del ultimo mes
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static float costeDesde(String fechaInicial){

        float precio = 0_0;
        Calendar fecha = Calendar.getInstance();
        String fechaActual = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fecha.get(Calendar.MONTH) + 1) + "/" +
                String.valueOf(fecha.get(Calendar.YEAR));

        for(ENListaCompra lista : listas){
            if(estaListaFecha(lista,fechaInicial,fechaActual)){
                for(ENArticulo arti : lista.getArticulos()){
                    precio += arti.getPrecio() * arti.getCantidadComnprada();
                }
            }
        }

        return precio;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // Devuelve articulos en una fecha
    ///////////////////////////////////////////////////////////////////////////////////////////

     /*public static ArrayList<ENArticulo> articulosDesde(String fechaInicial){

        Calendar fecha = Calendar.getInstance();
        System.out.println(fecha.toString());
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH)+1;
        int anyo = fecha.get(Calendar.YEAR);

        return null;
*/
    ///////////////////////////////////////////////////////////////////////////////////////////
    //  devuelve boolean (se encuentra una lista en una fecha o no ( A menos, B mayor
    ///////////////////////////////////////////////////////////////////////////////////////////

     public static boolean estaListaFecha(ENListaCompra lista,String fechaInicio,String fechaFin){

         boolean mayor = false;
         boolean menor = false;
         int diaInicio = Integer.valueOf(fechaInicio.split("/")[0]);
         int mesInicio = Integer.valueOf(fechaInicio.split("/")[1]);
         int anyoInicio = Integer.valueOf(fechaInicio.split("/")[2]);
         int diaFin = Integer.valueOf(fechaFin.split("/")[0]);
         int mesFin = Integer.valueOf(fechaFin.split("/")[1]);
         int anyoFin = Integer.valueOf(fechaFin.split("/")[2]);
         int dia = Integer.valueOf(lista.getFecha().split("/")[0]);
         int mes = Integer.valueOf(lista.getFecha().split("/")[1]);
         int anyo = Integer.valueOf(lista.getFecha().split("/")[2]);

         if(anyo >= anyoInicio) mayor = true;
         else if(anyo == anyoInicio){
             if(mes >= mesInicio) mayor = true;
             else if( mes == mesInicio){
                 if(dia >= diaInicio) mayor = true;
             }
         }

         if(anyo <= anyoFin) menor = true;
         else if(anyo <= anyoFin){
             if(mes >= mesFin) menor = true;
             else if( mes == mesFin){
                 if(dia <= diaFin) menor = true;
             }
         }

        return mayor && menor;
    }

    //modifica un articulo
    public static void modificarArticulo(String name,String newName,float newPrecio){

        ENArticulo art = findArtByName(name);
        SQLiteDatabase bd = doh.getWritableDatabase();
        if(art == null || bd == null) return;
        String ant = art.getNombre();
        art.setNombre(newName);
        art.setPrecio(newPrecio);

        try{

            bd.execSQL("UPDATE  ARTICULO SET nombre = '" + art.getNombre() + "' WHERE nombre = '" +
                     ant + "'");
            bd.execSQL("UPDATE  ARTICULO SET precio = '" + art.getPrecio() + "' WHERE nombre = '" +
                    ant  + "'");


            doh.close();

        }
        catch (Exception e){
            System.out.println(e.toString() + "\n Fallo en el acceso a la BD");
        }


    }

    //Aumenta o disminuye un articulo de la bd // true = aumenta
    public static void aumDisArticulo(String nombre,boolean aum){//deberia mejorar la precision

        ENArticulo art = findArtByName(nombre);
        SQLiteDatabase bd = doh.getWritableDatabase();
        if(art == null || bd == null) return;
        if(aum) {
            art.setCantidadComnprada(art.getCantidadComnprada() + 1);
            art.setCantidadActual(art.getCantidadActual() + 1);
        }
        else{
           if(art.getCantidadActual() > 0) art.setCantidadActual(art.getCantidadActual() - 1);
        }

        try{

                bd.execSQL("UPDATE  ARTICULO SET cantidadActual = '" + art.getCantidadActual() + "' WHERE nombre = '" +
                        nombre + "'");
                bd.execSQL("UPDATE  ARTICULO SET cantidadComprada = '" + art.getCantidadComnprada() + "' WHERE nombre = '" +
                        nombre  + "'");


                doh.close();

        }
        catch (Exception e){
            System.out.println(e.toString() + "\n Fallo en el acceso a la BD");
        }

    }

    //devuelve un articulo por nombre
    public static ENArticulo findArtByName(String nombre){

        for( ENArticulo art : articulos) {
            if(art.getNombre().equals(nombre)) return art;
        }

        for( ENListaCompra lc : listas){
            for( ENArticulo art : lc.getArticulos()){
                if(art.getNombre().equals(nombre)) return art;
            }
        }

        return null;
    }

    //devuelve un array list ordenado, con los doce articulos del mes que más se han comprado ( mas a menos)
    public static ArrayList<ENArticulo> doceArticulosDelMes(){

        ArrayList<ENArticulo> doceArt = new ArrayList<>();
        Calendar fecha = Calendar.getInstance();
        String fechaActual = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fecha.get(Calendar.MONTH) + 1) + "/" +
                String.valueOf(fecha.get(Calendar.YEAR));
        int mes = fecha.get((Calendar.MONTH));
        if(mes == 0) mes = 12;
        String fehaAnt =  String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(mes) + "/" +
                String.valueOf(fecha.get(Calendar.YEAR));

        for(ENArticulo art : articulos) {
            if(estaArticuloFecha(art, fehaAnt, fechaActual)) doceArt.add(art);
        }

        for(ENListaCompra lc : listas){
            for(ENArticulo art : lc.getArticulos()) {
                if(estaArticuloFecha(art, fehaAnt, fechaActual)) doceArt.add(art);
            }
        }


        return ordenaArti(doceArt);
    }

    //devuelve si un articulo esta en una fecha
    public static boolean estaArticuloFecha(ENArticulo art,String fechaInicio,String fechaFin){

        boolean mayor = false;
        boolean menor = false;
        int diaInicio = Integer.valueOf(fechaInicio.split("/")[0]);
        int mesInicio = Integer.valueOf(fechaInicio.split("/")[1]);
        int anyoInicio = Integer.valueOf(fechaInicio.split("/")[2]);
        int diaFin = Integer.valueOf(fechaFin.split("/")[0]);
        int mesFin = Integer.valueOf(fechaFin.split("/")[1]);
        int anyoFin = Integer.valueOf(fechaFin.split("/")[2]);
        int dia = Integer.valueOf(art.getFechaArt().split("/")[0]);
        int mes = Integer.valueOf(art.getFechaArt().split("/")[1]);
        int anyo = Integer.valueOf(art.getFechaArt().split("/")[2]);

        if(anyo >= anyoInicio) mayor = true;
        else if(anyo == anyoInicio){
            if(mes >= mesInicio) mayor = true;
            else if( mes == mesInicio){
                if(dia >= diaInicio) mayor = true;
            }
        }

        if(anyo <= anyoFin) menor = true;
        else if(anyo <= anyoFin){
            if(mes >= mesFin) menor = true;
            else if( mes == mesFin){
                if(dia <= diaFin) menor = true;
            }
        }

        return mayor && menor;
    }

    private static ArrayList<ENArticulo> ordenaArti(ArrayList<ENArticulo> articuloss){

        boolean ordenado = true;
        int tam = articuloss.size();

        if(tam <= 1) return articuloss;

        for(int i = 0; i < tam ; ++i){
            if(i == tam) break;
            if(articuloss.get(i).getCantidadComnprada() < articuloss.get(i + 1).getCantidadComnprada()){
               ENArticulo mayor = articuloss.get(i + 1);
                ENArticulo menor = articuloss.get(i);
                articuloss.set(i, mayor);
                articuloss.set(i, menor);
                ordenado = false;
            }
        }

        if(ordenado && tam <= 12) return articuloss;
        else return ordenaArti(articuloss);
    }

    public static void modificarEstadoArticulo(String nombre,String estado){

        SQLiteDatabase bd = doh.getWritableDatabase();

        try{
            if(bd == null){
                return;
            }
            else{
                ENArticulo art = findArtByName(nombre);
                bd.execSQL("UPDATE  ARTICULO SET estado = '" + estado + "' WHERE id = '" +
                        art.getId() + "'");

                art.setEstado("nvi");

                doh.close();
            }
        }
        catch (Exception e){
            System.out.println(e.toString() + "\n Fallo en el acceso a la BD");
        }

    }
}
