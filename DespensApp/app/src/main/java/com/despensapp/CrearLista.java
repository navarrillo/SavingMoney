package com.despensapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class CrearLista extends AppCompatActivity {

    EditText crearLista;
    Calendar fecha;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_lista);

        crearLista = (EditText) findViewById(R.id.et_crearLista);
        error = (TextView) findViewById(R.id.txt_error_crearLista);
    }


    public void crearLista(View v){

        fecha = Calendar.getInstance();

        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int mes = fecha.get(Calendar.MONTH)+1;
        int anyo = fecha.get(Calendar.YEAR);

        if(crearLista.getText().toString().length() > 0) {
            DespenBD.insertaLista(crearLista.getText().toString(), "" + dia + "/" + mes + "/" + anyo);
            Intent i = new Intent(this, ListasCompra.class );
            startActivity(i);

            finish();
        }else{
            error.setVisibility(View.VISIBLE);
        }

    }

    public void cancelarLista(View v){
        Intent i = new Intent(this, ListasCompra.class );
        startActivity(i);

        finish();
    }
}
