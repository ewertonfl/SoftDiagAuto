package com.fatec.tg.softdiagauto.util;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;

import android.util.Log;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class BluetoothDiag implements Serializable{

    private BluetoothSocket mmSocket;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private Thread workerThread;
    private byte[] readBuffer;
    private int readBufferPosition;
    private volatile boolean stopWorker;
    private String Resposta;
    public Activity context;
    private ArrayList<String> rx;
    private static String TAG = "BluetoothDiag";

    private BluetoothService connection = BluetoothService.getInstance(null, false);

    public BluetoothDiag(Activity context){

        this.context = context;
        mmSocket = connection.getConection();

        try {
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
            //mmOutputStream.write("AT".getBytes());


            beginListenForData();
            //mmOutputStream.write("OK".getBytes());

            Toast.makeText(context, "Recebendo dados do Bluetooth", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tx(String vlr){
        try{
            mmOutputStream.write(vlr.getBytes());
        }catch (IOException ex){
            ex.printStackTrace();
            Toast.makeText(context, "Erro ao transmitir valor !!!", Toast.LENGTH_SHORT).show();
        }

    }

    void beginListenForData() {

        final BufferedReader reader = connection.getmBufferedReader();
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        String info = reader.readLine(); // recebe os dados da comunicação
                        setRx(info);
                        //mmOutputStream.write("OK".getBytes()); // envia dados.
                    } catch (IOException ex) {
                        Toast.makeText(context, "ERRO: " + ex, Toast.LENGTH_SHORT).show();
                        stopWorker = true;

                    }
                }
            }
        });

        workerThread.start();
    }

    public void setRx(String vlr){
        rx.add(vlr);
    }

    public String getRx(){ //Pega sempre o primeiro valor da pilha de valores recebidos
        if (rx.size() == 0)
            return "NULL";
        String sRx = rx.get(0);
        Log.i(TAG, "Valor pego: " + sRx);
        rx.remove(0);
        return sRx;
    }

    public int getRxSize(){
        return rx.size();
    }

}
