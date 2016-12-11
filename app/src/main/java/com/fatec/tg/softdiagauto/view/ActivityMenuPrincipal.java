package com.fatec.tg.softdiagauto.view;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.fatec.tg.softdiagauto.R;
import com.fatec.tg.softdiagauto.controller.Diagnostico;
import com.fatec.tg.softdiagauto.util.BluetoothDiag;
import com.fatec.tg.softdiagauto.util.BluetoothService;
import com.fatec.tg.softdiagauto.util.Constantes;

import java.util.Timer;
import java.util.TimerTask;

import static com.fatec.tg.softdiagauto.util.Constantes.rData;

public class ActivityMenuPrincipal extends Activity {
    final Context context = this;
    private BluetoothAdapter BA;
    private final int REQUEST_ENABLE_BT = 1; // Código padrão para o requerimento em tempo de execuxão.
    private final String[] PermissionsLocation = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}; //Array de permissões relacionadas ao Bluetooth no Android 6.0 ou maior
    private final int ResquestLocationId = 0; // Código padrão para o requerimento em tempo de execução.
    private IntentFilter it = null;
    private boolean procurar = true;
    private boolean conectado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        while(true) {
            it = new IntentFilter(); // Instancia o filtro declarado logo após o onCreate().
            it.addAction(BluetoothDevice.ACTION_FOUND);
            it.addCategory(Intent.CATEGORY_DEFAULT);
            context.registerReceiver(mReceiver, it); // Registra um Receiver para o App.
            break;
        }

        BA = BluetoothAdapter.getDefaultAdapter();
        if (BA == null) {
            Toast.makeText(context, "Seu dispositivo Android não suporta Bluetooth!",Toast.LENGTH_SHORT).show();
        } else {
            BtEnable();
            Diagnostico diag = new Diagnostico(this);
            diag.execute();
        }
    }

    private BluetoothService conexao;

    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // Quando a ação "discover" achar um dispositivo

            if (procurar) {
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    Log.i("MAIN", "Found");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    try{
                        if(device.getName().trim().equals(Constantes.nomeDispositivo)) {
                            conexao = BluetoothService.getInstance(device, true);

                            if(conexao.isConnected()) {
                                Toast.makeText(context, "Conectado com sucesso ao dispositivo!" + device.getName(), Toast.LENGTH_SHORT).show();
                                instanciaValor();
                                conectado=true;
                            } else {
                                conectado=false;
                                procurar=false;
                                Toast.makeText(context, "Falha ao conectar ao dispositivo." + device.getName(), Toast.LENGTH_SHORT).show();
                                if (BA.isEnabled()) {
                                    BA.disable();
                                }
                            }
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(context, "Erro na tentativa de se conectar ao dispositivo.", Toast.LENGTH_SHORT).show();
                }
            } else {
                conectado=false;
                Toast.makeText(context, "Dispositivo não encontrado.", Toast.LENGTH_SHORT).show();
                if (BA.isEnabled()) {
                    BA.disable();
                }
            }
        }
    };

    public void instanciaValor(){
        rData = new BluetoothDiag(this);
    }

    public void conectarLeitor(View v){
        if (!BA.isEnabled())
            BtEnable();

        if (conectado) {
            exibirToast("Dispositivo já está conectado.");
        } else {
            lookFor();
        }
    }

    public void BtEnable(){
        //liga o bluetooth
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, REQUEST_ENABLE_BT);
            Log.i("MAIN", "Ligando BT");
            while (!BA.isEnabled()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                }
            }
            Log.i("MAIN", "BT Ligado!");

            lookFor();
        } else {
            lookFor();
        }
        // Essa if em especial, verifica se a versão Android é 6.0 ou maior, pois caso seja, uma permissão para localização, além das relacionadas ao Bluetooth, sao necessárias.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(PermissionsLocation,ResquestLocationId);
            }
        }
    }

    protected void lookFor() { // Procura por dispositivos
        Log.i("MAIN", "Procurando DISPOSITIVO!!");
        Toast.makeText(ActivityMenuPrincipal.this, "Procurando dispositivo, aguarde por favor.", Toast.LENGTH_LONG).show();
//        if(BA.startDiscovery()){}
//        else;
        procurar = true;
        BA.startDiscovery();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (conectado==false) {
                    procurar=false;
//                    if (BA.isEnabled()) {
//                        BA.disable();
//                    }
                    ActivityMenuPrincipal.this.runOnUiThread(new Runnable() {
                        public void run() {
                            exibirToast("O dispositivo não foi localizado!");
                        }
                    });
                } else {
                    //timer.cancel();
                }
            }
        }, 20*1000);
    }

    // Método usado para chamar a tela de informações
    public void informacoesCentral(View v){
        startActivity(new Intent(this,ActivityInformacoesVeiculo.class));
    }

    public void telaChat(View v){
        Intent chat = new Intent(this,ActivityTelaChat.class);
        startActivity(chat);
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
            //txtSaudacao.setText("Bem vindo, " + nome + "!");
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

                }
            }
        });
        inputAlert.create().show();
    }

    public void alterarNome() {
        SharedPreferences pref = getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.remove("Nome");
        editor.commit();
        perguntarNome();
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
