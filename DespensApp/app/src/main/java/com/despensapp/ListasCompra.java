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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListasCompra extends AppCompatActivity implements AdapterView.OnItemLongClickListener{

    ListView listasCompra;
    ArrayAdapter adaptador;
    List misListas;

    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas_compra);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


            // bundle = getIntent().getExtras();

        listasCompra = (ListView) findViewById(R.id.lv_listasCompra);
        listasCompra.setOnItemLongClickListener(this);
       // misListas = new ArrayList<MisListas>();
        misListas = new ArrayList <ENListaCompra>();
            /*if(bundle == null) {




                misListas.add(0, new MisListas("Pescadería", "20/12/2015"));
                misListas.add(0, new MisListas("Gasolinera", "25/02/2015"));
                misListas.add(0, new MisListas("Super", "20/03/2015"));




            }else{

                actualizarLista();

            }*/
        ArrayList<ENListaCompra> miL = DespenBD.getListasVisibles();
        for (ENListaCompra lc: miL) {
            //DespenBD.ListaSetVisible(lc.getNombre(),lc.getFecha(),true);

           misListas.add(0, lc);
            //misListas.add(lc);
            System.out.println("imprime: " + lc.getNombre());
        }


        adaptador = new MisListasArrayAdapter<MisListas>(this, misListas);
        listasCompra.setAdapter(adaptador);
    }

    public void nuevaLista(View v)
    {

        Intent i = new Intent(this, CrearLista.class );
        startActivity(i);

       // finish();


        //DespenBD.insertaLista("15", "15");
       // DespenBD.insertaLista("16", "16");
       // DespenBD.insertaLista("17", "17");
       // misListas.add(0, new MisListas("prueba", "" + dia + "/" + mes + "/" + anyo));

       /* ArrayList<ENListaCompra> miL = DespenBD.getListasVisibles();

        for (ENListaCompra lc: miL) {
            misListas.add(0, new MisListas(""+lc.getNombre(),""+lc.getFecha()));
            //System.out.println("imprime: " + lc.getNombre());
        }


        adaptador = new MisListasArrayAdapter<MisListas>(this,misListas);
        listasCompra.setAdapter(adaptador);*/



    }

   /* public void actualizarLista()
    {
        misListas.add(0, new MisListas("Pescadería", "20/12/2015"));
        misListas.add(0, new MisListas("Gasolinera", "25/2/2015"));
        misListas.add(0, new MisListas("Super", "203/15/2015"));
        adaptador = new MisListasArrayAdapter<MisListas>(this, misListas);
        listasCompra.setAdapter(adaptador);

        switch(bundle.getString("accion"))
        {
            case "borrar":
                System.out.println("borrar "+misListas.get(0));
                misListas.remove(bundle.getInt("posicion"));
                break;
            case "editar":
                misListas.remove(bundle.getInt("posicion"));
                misListas.add(bundle.getInt("posicion"), new MisListas(bundle.getString("nombre"),"20/6/2019"));
                break;
        }

        adaptador = new MisListasArrayAdapter<MisListas>(this, misListas);
        listasCompra.setAdapter(adaptador);
      // misListas[bundle.getInt("posicion")];

    }*/



    /* public void onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        MisListas listaActual = (MisListas)adaptador.getItem(position);
        String msg = "Elegiste "+id+" la tarea:\n"+listaActual.getNombre()+"-"+listaActual.getFecha();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }*/

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //MisListas listaActual = (MisListas)adaptador.getItem(position);
       ENListaCompra listaActual = (ENListaCompra)adaptador.getItem(position);



        Intent i = new Intent(this, EditarLista.class );
        i.putExtra("nombre", ""+listaActual.getNombre());
        i.putExtra("fecha", ""+listaActual.getFecha());
        i.putExtra("posicion", position);
        startActivity(i);

        finish();
       // finish();
        /*String msg = "Elegiste "+id+" la tarea:\n"+listaActual.getNombre()+"-"+listaActual.getFecha();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();*/
        return false;
    }
}
