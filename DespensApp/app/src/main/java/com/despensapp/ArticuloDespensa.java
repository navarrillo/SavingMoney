package com.despensapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Iskren on 16/03/2016.
 */
public class ArticuloDespensa extends Activity implements View.OnClickListener {

    Button button1;
    Button button2;
    EditText articuloNuevo,cantidadArticulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo_despensa);

        button1= (Button)findViewById(R.id.button_crearArticuloDespensa);
        button1.setOnClickListener(this);

        button2= (Button)findViewById(R.id.btn_cancelarArticuloDespensa);
        button2.setOnClickListener(this);

        articuloNuevo = (EditText) findViewById(R.id.et_crearArticuloDespensa);
        cantidadArticulo = (EditText) findViewById(R.id.et_cantidadArticuloDespensa);


    }

    public void crearArticuloDespensa(){

        int cantidad=0;
        cantidad= Integer.parseInt(cantidadArticulo.getText().toString());
        if(articuloNuevo.getText().toString().length() > 0) {
            DespenBD.insertaArticulo(articuloNuevo.getText().toString(),10,cantidad,"des");
            Intent i = new Intent(this, MiDespensa.class);
            startActivity(i);

            finish();
        }else{
            crearArticuloDespensa();
        }


    }

    private void buttonClick() {
        crearArticuloDespensa();
    }

    private void buttonClickCancelar() {
        Intent i = new Intent(this, MiDespensa.class);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_crearArticuloDespensa:
                buttonClick();
                break;
            case R.id.btn_cancelarArticuloDespensa:
                buttonClickCancelar();
                break;
        }
    }
}