package com.despensapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditarLista extends AppCompatActivity {

    EditText etNombre, etFecha;
    TextView tvModificar;
    int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_lista);



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        etNombre = (EditText) findViewById(R.id.et_nombreLista);
        etFecha = (EditText) findViewById(R.id.et_fechaLista);
        tvModificar = (TextView) findViewById(R.id.tv_Modificar);


        Bundle bundle=getIntent().getExtras();
        posicion = bundle.getInt("posicion");
        etNombre.setText(bundle.getString("nombre"));
        etFecha.setText((bundle.getString("fecha")));

        System.out.println("Posicion: " +posicion);

        tvModificar.setText("Modificar "+bundle.getString("nombre"));

    }

    public void guardarCambios(View v){

       // Button guardar = (Button) findViewById(R.id.btn_guardarLista);

        ArrayList<ENListaCompra> miL = DespenBD.getListasVisibles();
        ENListaCompra lCompra = miL.get(miL.size()-posicion-1);

        System.out.println("Posician: "+posicion);
        System.out.println("Nombre: "+lCompra.getNombre());

        DespenBD.editarLista(lCompra.getNombre(),lCompra.getFecha(),etNombre.getText().toString(), etFecha.getText().toString());
        //lCompra.setNombre();

       /* for (ENListaCompra lc: miL) {
            //misListas.add(0, new MisListas(""+lc.getNombre(),""+lc.getFecha()));
            System.out.println("imprime: " + lc.getNombre());
        }*/

        Intent i = new Intent(this, ListasCompra.class );
        //i.putExtra("nombre", "" + etNombre.getText());
        //i.putExtra("accion", "editar");
        startActivity(i);

        finish();

    }

    public void cancelarCambios(View v){
        Intent i = new Intent ( this, ListasCompra.class);
        startActivity(i);

        finish();
    }

    public void borrarLista(View v) {

        ArrayList<ENListaCompra> miL = DespenBD.getListasVisibles();
        ENListaCompra lCompra = miL.get(miL.size()-posicion-1);

        DespenBD.ListaSetVisible(lCompra.getNombre(),lCompra.getFecha(),false);

        Intent i = new Intent(this, ListasCompra.class );
      //  i.putExtra("posicion",posicion);
       // i.putExtra("accion", "borrar");
        startActivity(i);

        finish();

    }
}
