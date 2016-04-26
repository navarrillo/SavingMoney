package com.despensapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

public class AddArticuloLista extends AppCompatActivity implements View.OnClickListener {

    String nombre,fecha;
    Button button1;
    Button button2;
    EditText articuloNuevo,cantidadArticulo,precioArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_articulo_lista);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        button1= (Button)findViewById(R.id.button9);
        button1.setOnClickListener(this);

        button2= (Button)findViewById(R.id.button10);
        button2.setOnClickListener(this);

        articuloNuevo = (EditText) findViewById(R.id.editText);
        cantidadArticulo = (EditText) findViewById(R.id.editText2);
        precioArticulo = (EditText) findViewById(R.id.editText3);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre = extras.getString("key");
            fecha = extras.getString("key2");
        }

    }

    public void crearArticuloLista(){

        Calendar fechaa = Calendar.getInstance();

        ENArticulo articulo = new ENArticulo(
                articuloNuevo.getText().toString(),
                Integer.parseInt(precioArticulo.getText().toString()),
                Integer.parseInt(cantidadArticulo.getText().toString()),
                "esp",
                String.valueOf(fechaa.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(fechaa.get(Calendar.MONTH) + 1) + "/" +
                String.valueOf(fechaa.get(Calendar.YEAR))
                );

        if(articuloNuevo.getText().toString().length() > 0) {

            ArrayList<ENListaCompra> miL = DespenBD.getListas();
            for (ENListaCompra lc: miL) {

                System.out.println(nombre+"---"+fecha);
                System.out.println(lc.getNombre()+"---"+lc.getFecha());
                if(lc.getNombre().equals(nombre) && lc.getFecha().equals(fecha)){
                    lc.add(articulo);
                }
            }

            Intent myIntent = new Intent(this, ArticulosLista.class);
            myIntent.putExtra("key", nombre);
            myIntent.putExtra("key2", fecha);
            startActivity(myIntent);
            finish();

        }else{
            crearArticuloLista();
        }


    }

    private void buttonClick() {
        crearArticuloLista();
    }

    private void buttonClickCancelar() {

        Intent myIntent = new Intent(this, ArticulosLista.class);
        myIntent.putExtra("key", nombre);
        myIntent.putExtra("key2", fecha);
        startActivity(myIntent);
        finish();

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button9:
                buttonClick();
                break;
            case R.id.button10:
                buttonClickCancelar();
                break;
        }
    }
}
