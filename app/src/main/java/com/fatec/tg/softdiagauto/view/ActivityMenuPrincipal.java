package com.fatec.tg.softdiagauto.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fatec.tg.softdiagauto.R;
import com.fatec.tg.softdiagauto.util.BluetoothDiag;

public class ActivityMenuPrincipal extends Activity {
    private static final int PEDIDO_CONEXAO_SEGURA = 1;
    private static final int PEDIDO_CONEXAO_INSEGURA = 2;
    private static final int PEDIDO_HABILITAR_BT = 3;
    private TextView txtSaudacao;
    final Context context = this;
    BluetoothDiag bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        // this.txtSaudacao=(TextView)findViewById(R.id.txtSaudacao);
        //verificarUsuario();
    }

    // Método usado para chamar a tela de informações
    public void informacoesCentral(View v){
        startActivity(new Intent(this,ActivityInformacoesVeiculo.class));
    }

    public void telaChat(View v){
        startActivity(new Intent(this,ActivityTelaChat.class));
    }

    // Método usado para chamar a tela de parâmetros
    public void parametros(View v){
        startActivity(new Intent(this,ActivityParametros.class));
    }

    // Método usado para chamar a tela de falhas
    public void falhas(View v){
        startActivity(new Intent(this,ActivityFalhas.class));
    }

    // Método usado para chamar a tela Sobre
    public void sobre(View v){
        startActivity(new Intent(this,ActivitySobre.class));
    }

    public void confirmarSaida(View v) { // Método para verificar se o usuário deseja realmente sair
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Você está saindo");
        alertDialogBuilder.setMessage("Deseja realmente sair?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                }).setNegativeButton("Não",	new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //action para "não" ou apenas fechar a janela
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    //Verificar o nome do usuário.
    //Deve ser executada somente ao iniciar o aplicativo.
    public void verificarUsuario() {
        SharedPreferences pref = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        String nome = pref.getString("Nome", "");
        if (nome=="") {
            perguntarNome();
        } else {
            txtSaudacao.setText("Bem vindo, " + nome + "!");
        }
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
                    txtSaudacao.setText("Bem vindo, " + nome + "!");
                }
            }
        });
        inputAlert.create().show();
    }

    //Fun��o para alterar o nome do usu�rio.
    public void alterarNome() {
        SharedPreferences pref = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.remove("Nome");
        editor.commit();
        perguntarNome();
    }

    //Função para inicio da conexão com o leitor.
    public void conectarLeitor(View v) {
        String msg="";
        BluetoothAdapter meuBT = BluetoothAdapter.getDefaultAdapter();
        //Verificar se o dispositivo suporta a tecnologia Bluetooth.
        if (meuBT == null) {
            msg = "Seu dispositivo Android não suporta a tecnologia Bluetooth!";
        } else {
            if (!(meuBT.isEnabled())) {
                msg = "É necessário ligar o serviço Bluetooth!";
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult (enableBtIntent, PEDIDO_HABILITAR_BT);
            } else {
                configurarLeitor();

                Log.i("MAIN","Listar BT");
                Intent serverIntent = new Intent(this, ActivityListagemBluetooth.class);
                startActivityForResult(serverIntent, PEDIDO_CONEXAO_INSEGURA);
            }
        }

        if(msg.length() > 0) {
            exibirToast(msg);
        }
    }


    public void configurarLeitor() {
        Log.i("MAIN", "CONFIGURAÇÃO BT");
        bt = new BluetoothDiag(this);
    }

    //Verificar o retorno da Activity.
    protected void onActivityResult(int requestCode , int resultCode , Intent data ) {
        switch (requestCode) {
            case PEDIDO_CONEXAO_SEGURA:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    bt.conectarDispositivo(data, true);
                }
                break;
            case PEDIDO_CONEXAO_INSEGURA:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    bt.conectarDispositivo(data, false);
                }
                break;
            case PEDIDO_HABILITAR_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    configurarLeitor();

                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d("MAIN", "BT não habilitado");
                    exibirToast("Erro ao Habilitar o bluetooth");
                }
        }
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
