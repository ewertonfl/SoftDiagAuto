package com.fatec.tg.softdiagauto.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.fatec.tg.softdiagauto.util.BluetoothDiag;
import com.fatec.tg.softdiagauto.R;


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
        edtTx = (EditText) findViewById(R.id.txtTX);
        bt = new BluetoothDiag(this);
        edtChat = (EditText) findViewById(R.id.txtChat);
    }

    public void onBtnTxClick(View v){
        String vlr = edtTx.getText().toString();
        bt.enviarValor(vlr);
        Log.i("TELA", "Valor enviado: " + vlr);
        edtChat.setText(edtChat.getText() + "\n>" + vlr);
        boolean t = false;
        while ((bt.getRxSize() == 0) && (!t)){
            while (bt.getRxSize() > 0){
                edtChat.setText(edtChat.getText() + "\n<" + bt.getRx());
                Log.i("TELA", "Valor recebido: " + bt.getRx());
                t = !t;
            }
        }

    }

}
