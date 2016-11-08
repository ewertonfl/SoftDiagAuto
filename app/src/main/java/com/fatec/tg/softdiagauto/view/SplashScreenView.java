package com.fatec.tg.softdiagauto.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fatec.tg.softdiagauto.R;

/**
 * Created by Gabriel Rubio on 16/10/2016.
 */

public class SplashScreenView extends Activity {

    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        iniciaSplash();
    }

    public void iniciaSplash(){

        setContentView(R.layout.splash_screen);


        new Thread(new Runnable() {

            @Override
            public void run() {

               try{
                    while(cont == 1 || cont <= 5){

                        Thread.sleep(100);
                        cont++;
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                if(cont == 6){

                    finish();

                    Intent it = new Intent(SplashScreenView.this,MenuPrincipalView.class);
                    startActivity(it);
                    cont++;
                }
            }
        }).start();{

        }

    }
}
