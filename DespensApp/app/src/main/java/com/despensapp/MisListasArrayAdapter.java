package com.despensapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Sergio on 24/02/2016.
 */

public class MisListasArrayAdapter<T> extends ArrayAdapter<T> {

    public MisListasArrayAdapter(Context context,@LayoutRes int res, List<T> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con two_line_list_item.xml
            listItemView = inflater.inflate(
                    R.layout.items_mis_listas,
                    parent,
                    false);
        }

        //Obteniendo instancias de los text views





        TextView nombre = (TextView) listItemView.findViewById(R.id.nombre_item_mis_lista);
        TextView fecha = (TextView) listItemView.findViewById(R.id.fecha_item_mis_lista);
        //ImageView categoria = (ImageView) listItemView.findViewById(R.id.categoria);

        //Obteniendo instancia de la Tarea en la posición actual
        T item = (T) getItem(position);

        //Dividir la cadena en Nombre y Hora
        String cadenaBruta;
        String subCadenas[];
        String delimitador = ",";

        cadenaBruta = item.toString();
        subCadenas = cadenaBruta.split(delimitador, 2);

        System.out.println("SUBCADENAS 0: "+subCadenas[0]);
        System.out.println("SUBCADENAS 1: " + subCadenas[1]);


       //nombre.setText("Prueba1");
       // fecha.setText("20/5/2015");
        nombre.setText(subCadenas[0]);
        fecha.setText(subCadenas[1]);

        //Devolver al ListView la fila creada
        return listItemView;

    }

}








