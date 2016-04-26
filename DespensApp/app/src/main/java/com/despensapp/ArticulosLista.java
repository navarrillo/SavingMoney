package com.despensapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArticulosLista extends AppCompatActivity implements View.OnClickListener {


    Button button1;
    String value=null;
    String value2=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos_lista);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("key");
            value2 = extras.getString("key2");
        }

        setContentView(R.layout.activity_articulos_lista);
        TextView detail = (TextView)findViewById(R.id.textView3);
        detail.setText(value+"  "+value2);


        button1= (Button)findViewById(R.id.button4);
        button1.setOnClickListener(this);


        List<String> listItem = new ArrayList<String>();
        List<Integer> listCantidad = new ArrayList<Integer>();
        ArrayList listaArticulos = new ArrayList<ENArticulo>();
        ArrayList<ENArticulo> miLista = DespenBD.getListaNomFecha(value,value2);


        for (ENArticulo lista: miLista) {

                listaArticulos.add(0, lista);
                listItem.add(lista.getNombre());
                listCantidad.add(lista.getCantidadActual());
            //misListas.add(lc);
            System.out.println("nombre: " + lista.getNombre() + "cantidad: " + lista.getCantidadActual());
        }

        ArrayAdapter<ENArticulo> adapter = new ArrayAdapter<ENArticulo>(this, R.layout.items_lista, listaArticulos);

        ListView list = (ListView)findViewById(R.id.listView3);
        list.setAdapter(new MyListAdapter(this, R.layout.items_lista, listaArticulos));

    }

    private class MyListAdapter extends ArrayAdapter<ENArticulo> {

        private int layout;
        public MyListAdapter(Context context, int resource, List<ENArticulo> objects) {
            super(context, resource, objects);
            layout=resource;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHowder mainViewHolder = null;
            if(convertView == null){

                LayoutInflater inflater = (LayoutInflater) getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(layout, parent, false);
                ViewHowder viewHowder = new ViewHowder();
                viewHowder.nombreArticulo = (TextView) convertView.findViewById(R.id.nombre_item_lista);
                viewHowder.cantidadArticulo = (TextView) convertView.findViewById(R.id.cantidad_item_lista);
                viewHowder.fechaArticulo = (TextView) convertView.findViewById(R.id.fecha_item_lista);

                convertView.setTag(viewHowder);

            }
            else{
                mainViewHolder = (ViewHowder) convertView.getTag();
                mainViewHolder.nombreArticulo.setText(getItem(position).getNombre());
                mainViewHolder.cantidadArticulo.setText(String.valueOf(getItem(position).getCantidadActual()));
                mainViewHolder.fechaArticulo.setText(String.valueOf(getItem(position).getFechaArt()));
            }

            return convertView;
        }
    }

    public class ViewHowder {

        TextView nombreArticulo;
        TextView cantidadArticulo;
        TextView fechaArticulo;

    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ListasCompra.class);

        startActivity(i);
        finish();
    }

    private void buttonClick(){

        Intent myIntent = new Intent(this, AddArticuloLista.class);
        myIntent.putExtra("key", value);
        myIntent.putExtra("key2", value2);
        startActivity(myIntent);



    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button4:
                buttonClick();
                break;
        }
    }


}
