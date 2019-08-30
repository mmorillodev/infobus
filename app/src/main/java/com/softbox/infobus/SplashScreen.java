package com.softbox.infobus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread splash = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2000);

                    Intent abreTelaInicial = new Intent(SplashScreen.this,TelaSemLogin.class );
                    startActivity(abreTelaInicial);
                    finish();
                }
                catch (InterruptedException e){

                }
            }
        };
        splash.start();
    }
}
