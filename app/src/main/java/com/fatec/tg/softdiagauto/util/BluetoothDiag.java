package com.fatec.tg.softdiagauto.util;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import com.fatec.tg.softdiagauto.R;
import com.fatec.tg.softdiagauto.view.ActivityListagemBluetooth;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Erick on 08/11/2016.
 */

public class BluetoothDiag {

    private static final String TAG = "BluetoothDiag";


    // Nome do dispoitivo conectado
    private String mNomeDispositivo = null;
    //Array adapter para a thread de conversa
    private ArrayList<String> rx;
     //Buffer String para outgoing messages(SERVE PRA NADA ESSA POHA)
    private StringBuffer mOutStringBuffer;
    //Local Bluetooth adapter, usado para conexão
    private BluetoothAdapter mBluetoothAdapter = null;
    //Variavel de referencia ao BluetoothService
    private BluetoothService mBluetoothService = null;
    //Variavel pra acessar a Activity... sera q uma activity vai acessar aqui msm?
    private Context context;

    public BluetoothDiag(Context context){
        rx = new ArrayList<String>();
        mBluetoothService = new BluetoothService(context, mHandler);
        mOutStringBuffer = new StringBuffer("");
        this.context = context;

        if (mBluetoothService != null) {
            if (mBluetoothService.getState() == BluetoothService.STATE_NONE) {
                mBluetoothService.start();
            }
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }
    }

    public void enviaValor(String valor){
        sendMessage(valor);
    }

    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mBluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(context, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mBluetoothService.write(send);
            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }

    private void setStatus(CharSequence subTitle) {
        Activity activity = (Activity) context;
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    private void setStatus(int resId) {
        Activity activity = (Activity) context;
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //Activity activity = getActivity();
            Activity activity = (Activity) context;
            switch (msg.what) {
                case Constantes.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            setStatus(R.string.title_connected_to + mNomeDispositivo);
                            rx.clear();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constantes.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    //rx.add("Me:  " + writeMessage);
                    break;
                case Constantes.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    rx.add(readMessage);
                    break;
                case Constantes.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mNomeDispositivo = msg.getData().getString(Constantes.DEVICE_NAME);
                    if (null != activity) {
                        Toast.makeText(activity, "Conecatado à "
                                + mNomeDispositivo, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constantes.MESSAGE_TOAST:
                    if (null != activity) {
                        Toast.makeText(activity, msg.getData().getString(Constantes.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        //mConversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.message);
        //mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the BluetoothChatService to perform bluetooth connections
        mBluetoothService = new BluetoothService(context, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    public void conectarDispositivo(Intent data, boolean secure) {
        // Pegao endereço MAC do dispositivo
        String endereco = data.getExtras().getString(ActivityListagemBluetooth.EXTRA_DEVICE_ADDRESS);
        Log.i("BT", "Endereço MAC a ser conectado: " + endereco);
        // Pega o objeto BluetoothDevice
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(endereco);
        // Tentativa realizar a conexão com dispositivo
        mBluetoothService.connect(device, secure);
    }

    public int getRxSize(){
        return rx.size();
    }

    public String getRx(){
        if (rx.size() == 0)
            return "NULL";
        String sRx = rx.get(rx.size()-1);
        Log.i(TAG, "Valore pego: " + sRx);
        Log.i(TAG, "Indice removido do Buffer de resposta BT: " + (rx.size()-1));
        rx.remove(rx.size()-1);
        return sRx;
    }

    public void enviarValor(String valor){
        Log.i(TAG, valor);
        sendMessage(valor);
    }
    //public void setActivity(Activity activity) {
      //  this.activity = activity;
    //}
}
