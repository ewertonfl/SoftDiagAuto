package com.fatec.tg.softdiagauto.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;
import com.fatec.tg.softdiagauto.R;
import com.fatec.tg.softdiagauto.util.BluetoothDiag;

import java.io.Serializable;

public class ActivityTelaChat extends Activity {

    EditText edtTx;
    EditText edtChat;
    BluetoothDiag bt;
    ListView l1;
    String[] t1 = {"P0001", "P0002"};
    String[] d1 = {"Falha no sensor de aceleração", "Falha no sensor de rotação°"};
    int[]  i1= {0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_chat);
        Log.i("TelaCHAT", "Entro aqui!");
        edtTx = (EditText) findViewById(R.id.txtTX);
        edtChat = (EditText) findViewById(R.id.txtChat);
        Bundle args = getIntent().getBundleExtra("ARGS");
        if (args != null){
            Log.i("TelaCHAT", "Parametro recebido!");
            bt = (BluetoothDiag) args.getSerializable("BT");
        }

        /*new Thread(new Runnable() { // Thread recebe!
            @Override
            public void run() {
                if (bt.getRxSize() > 0) {
                    edtChat.setText(edtChat.getText() + "\n<" + bt.getRx());
                    Log.i("TELA", "Valor recebido: " + bt.getRx());
                }
            }
        }).start();*/
    }

    public void onBtnTxClick(View v){
        String vlr = edtTx.getText().toString();
        bt.tx(vlr);
        Log.i("TELA", "Valor enviado: " + vlr);
        edtChat.setText(edtChat.getText() + "\n>" + vlr);
    }

}
