package com.despensapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MiNevera extends AppCompatActivity implements View.OnClickListener {

    Button button1;
    ListView listasCompra;
    ArrayAdapter adaptador;
    List listaArticulos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_nevera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button1= (Button)findViewById(R.id.button_articluoNevera);
        button1.setOnClickListener(this);


        List<String> listItem = new ArrayList<String>();
        List<Integer> listCantidad = new ArrayList<Integer>();
        listaArticulos = new ArrayList<ENArticulo>();
        ArrayList<ENArticulo> miLista = DespenBD.getArticulosNevera();

        for (ENArticulo lista: miLista) {

            listaArticulos.add(0, lista);
            listItem.add(lista.getNombre());
            listCantidad.add(lista.getCantidadActual());

            //misListas.add(lc);
            System.out.println("nombre: " + lista.getNombre() + "cantidad: " + lista.getCantidadActual());
        }

        System.out.println("?????????????????"+listaArticulos.toString());
        ArrayAdapter<ENArticulo> adapter = new ArrayAdapter<ENArticulo>(this, R.layout.items_nevera, listaArticulos);

        ListView list = (ListView)findViewById(R.id.listView2);
        list.setAdapter(new MyListAdapter(this, R.layout.items_nevera, listaArticulos));

    }


    private void buttonClick(){

        startActivity(new Intent("despensapp.articuloNevera"));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_articluoNevera:
                buttonClick();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
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
                viewHowder.nombreArticulo = (TextView) convertView.findViewById(R.id.nombre_item_nevera);
                viewHowder.cantidadArticulo = (TextView) convertView.findViewById(R.id.cantidad_item_nevera);
                viewHowder.masArticulo = (Button) convertView.findViewById(R.id.button_masItemNevera);
                viewHowder.masArticulo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Aumentar cantidad", Toast.LENGTH_SHORT).show();

                    }
                });

                viewHowder.menosArticulo = (Button) convertView.findViewById(R.id.button_menosItemNevera);
                viewHowder.menosArticulo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"Disminuir cantidad", Toast.LENGTH_SHORT).show();
                    }
                });

                convertView.setTag(viewHowder);

            }
            else{
                mainViewHolder = (ViewHowder) convertView.getTag();
                mainViewHolder.nombreArticulo.setText(getItem(position).getNombre());
                mainViewHolder.cantidadArticulo.setText(String.valueOf(getItem(position).getCantidadActual()));
            }

            return convertView;
        }
    }

    public class ViewHowder {

        TextView nombreArticulo;
        TextView cantidadArticulo;
        Button masArticulo;
        Button menosArticulo;
    }
}
