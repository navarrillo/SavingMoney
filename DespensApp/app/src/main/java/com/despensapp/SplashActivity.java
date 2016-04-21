package com.despensapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sergio on 22/02/2016.
 */
public class SplashActivity extends Activity {

    // Duración de la splash screen en "ms"
    private static final long SPLASH_SCREEN_DELAY = 100; //3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Definimos la pantalla como vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Escondemos el título de la APP
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_screen);
        //carga de datos
        DespenBD despenbd = new DespenBD(this);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Ejecutar la siguiente Activity
                Intent mainIntent = new Intent().setClass(
                        SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Cerramos la activity para que el usuario no pueda volver atrás pulsando el botón back
                finish();
            }
        };

        // Simular la carga en la ejecución de la APP.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);


    }
}
