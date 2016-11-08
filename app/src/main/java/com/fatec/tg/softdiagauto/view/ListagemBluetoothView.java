package com.fatec.tg.softdiagauto.view;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.fatec.tg.softdiagauto.util.dataListAdapter;

import com.fatec.tg.softdiagauto.R;

public class ListagemBluetoothView extends AppCompatActivity {

    ListView l1;
    String[] t1={"t1","t2","t3"};
    String[] d1={"d1","d2","d3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conexao_hardware);

        ActionBar act = getActionBar();

        if(act == null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        else
            act.setDisplayHomeAsUpEnabled(true);

        int[] icon = {R.drawable.bt_icon_item, R.drawable.bt_icon_item, R.drawable.bt_icon_item};
        l1=(ListView)findViewById(R.id.listViewBt);
        l1.setAdapter(new dataListAdapter(this,t1,d1,icon));

    }

    //Função para exibir um prompt para o usuário digitar o nome.
    public void perguntarNome() {
        AlertDialog.Builder inputAlert = new AlertDialog.Builder(this);
        inputAlert.setTitle("Bem vindo!");
        inputAlert.setMessage("Digite seu nome, por favor.");
        final EditText userInput = new EditText(this);
        inputAlert.setView(userInput);
        inputAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = userInput.getText().toString();
                if((nome.trim().length()==0) || (nome==null)) {
                    perguntarNome();
                } else {
                    SharedPreferences pref = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=pref.edit();
                    editor.putString("Nome", nome);
                    editor.commit();
                }
            }
        });
        inputAlert.create().show();
    }

    //Função para alterar o nome do usuário.
    public void alterarNome() {
        SharedPreferences pref = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.remove("Nome");
        editor.commit();
        perguntarNome();
    }

    //Sensor 1.
    public void iniciarSensor1(View v) {
        exibirAlerta("Sensor 1");
    }
    //Sensor 2.
    public void iniciarSensor2(View v) {
        exibirAlerta("Sensor 2");
    }
    //Sensor 3.
    public void iniciarSensor3(View v) {
        exibirAlerta("Sensor 3");
    }
    //Sensor 4.
    public void iniciarSensor4(View v) {
        exibirAlerta("Sensor 4");
    }
    //Sensor 5.
    public void iniciarSensor5(View v) {
        exibirAlerta("Sensor 5");
    }
    //Sensor 6.
    public void iniciarSensor6(View v) {
        exibirAlerta("Sensor 6");
    }
    //Sensor 7.
    public void iniciarSensor7(View v) {
        exibirAlerta("Sensor 7");
    }

    //Exibir uma mensagem para o usuário, utilizando AlertDialog.
    public void exibirAlerta(String msg) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Aviso");
        alerta.setMessage(msg);
        alerta.setPositiveButton("OK", null);
        alerta.create().show();
    }

    //Exibir uma mensagem para o usuário, utilizando Toast.
    public void exibirToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}
