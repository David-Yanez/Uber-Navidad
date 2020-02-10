package com.example.uber_navidad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreemMain extends AppCompatActivity {
    Button pasajero, conductor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screem_main);

        pasajero = (Button)findViewById(R.id.id_pasajero);
        conductor = (Button)findViewById(R.id.id_conductor);

        pasajero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashScreemMain.this,MainActivity.class));
                //startActivity(new Intent(SplashScreemMain.this,TarjetaCredito.class));
                //startActivity(new Intent(SplashScreemMain.this,Permisos.class));
                //startActivity(new Intent(SplashScreemMain.this, GoogleMaps.class));
            }
        });

        conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( SplashScreemMain.this,RegistroRepartidor.class));
            }
        });
    }
}
