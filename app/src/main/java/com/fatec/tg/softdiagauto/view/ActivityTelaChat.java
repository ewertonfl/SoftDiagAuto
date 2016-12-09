package com.fatec.tg.softdiagauto.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import com.fatec.tg.softdiagauto.R;
import com.fatec.tg.softdiagauto.util.BluetoothDiag;

import java.io.Serializable;

import static com.fatec.tg.softdiagauto.util.Constantes.rData;

public class ActivityTelaChat extends Activity {

    EditText edtTx;
    EditText edtChat;
    ListView l1;
    String[] t1 = {"P0001", "P0002"};
    String[] d1 = {"Falha no sensor de aceleração", "Falha no sensor de rotação°"};
    int[]  i1= {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_chat);

        edtTx = (EditText) findViewById(R.id.txtTX);
        edtChat = (EditText) findViewById(R.id.txtChat);

        if (rData != null){
            Log.i("CHAT", "Software já conectado!");
        }

        new Thread(new Runnable() { // Thread recebe!
            @Override
            public void run() {
                Log.i("CHAT", "Start THREAD de recepção!");

                while (true) {
                    if (rData.getRxSize() > 0) {
                        Log.i("CHAT", "Start THREAD de recepção!");
                        String vlr = rData.getRx();
                        setTextoChat(vlr);
                        Log.i("TELA", "Valor recebido: " + vlr);
                    }
                }
            }
        }).start();
    }

    public void setTextoChat(String vlr){
        edtChat.setText(edtChat.getText() + "\n<" + vlr);
    }

    public void onBtnTxClick(View v){

        String vlr = edtTx.getText().toString();
        rData.tx(vlr);
        Log.i("TELA", "Valor enviado: " + vlr);
        edtChat.setText(edtChat.getText() + "\n>" + vlr);
    }

}
