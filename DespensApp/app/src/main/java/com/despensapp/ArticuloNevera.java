package com.despensapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ArticuloNevera extends Activity implements View.OnClickListener {

    Button button1;
    Button button2;
    EditText articuloNuevo,cantidadArticulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_nevera);

        button1= (Button)findViewById(R.id.button_crearArticuloNevera);
        button1.setOnClickListener(this);

        button2= (Button)findViewById(R.id.btn_cancelarArticuloNevera);
        button2.setOnClickListener(this);

        articuloNuevo = (EditText) findViewById(R.id.et_crearArticuloNevera);
        cantidadArticulo = (EditText) findViewById(R.id.et_cantidadArticuloNevera);


    }

    public void crearArticuloNevera(){

        int cantidad=0;
        cantidad= Integer.parseInt(cantidadArticulo.getText().toString());
        if(articuloNuevo.getText().toString().length() > 0) {
            DespenBD.insertaArticulo(articuloNuevo.getText().toString(),10,cantidad,"nev");
            Intent i = new Intent(this, MiNevera.class);
            startActivity(i);

            finish();
        }else{
            crearArticuloNevera();
        }


    }

    private void buttonClick() {
        crearArticuloNevera();
    }

    private void buttonClickCancelar() {
        Intent i = new Intent(this, MiNevera.class);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_crearArticuloNevera:
                buttonClick();
                break;
            case R.id.btn_cancelarArticuloNevera:
                buttonClickCancelar();
                break;
        }
    }
}