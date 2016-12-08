package com.fatec.tg.softdiagauto.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;


public class BluetoothService {

    private static BluetoothService conexao;
    private final BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothSocket BTSocket;
    private final UUID activeUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //ID padrão da porta serial.
    private final int REQUEST_ENABLE_BT = 1;
    private BluetoothDevice BTDevice;
    private BufferedReader mBufferedReader = null;
    private boolean isConnected = true;

    private BluetoothService(BluetoothDevice device) {
        BTDevice = device;
        BluetoothSocket temp = null;

        try {
            temp = BTDevice.createRfcommSocketToServiceRecord(activeUUID);
        } catch (IOException io) {
            Log.i("LOG", "Erro IO");
        }
        BTSocket = temp;

        if (BTAdapter.isDiscovering())
            BTAdapter.cancelDiscovery();

        try {

            InputStream aStream = null;
            InputStreamReader aReader = null;
            BTSocket.connect();

            aStream = BTSocket.getInputStream();
            aReader = new InputStreamReader(aStream);
            mBufferedReader = new BufferedReader(aReader);
        } catch (IOException e) {
            isConnected = false;
            return;
        }
    }

    public static BluetoothService getInstance(BluetoothDevice d, boolean subrescrever) {
        if (conexao == null)
            conexao = new BluetoothService(d);
        else if (subrescrever) {
            conexao = new BluetoothService(d);
            Log.i("BluetoothService", "Sobrescreveu a conexão");
        }
        return conexao;
    }

    public BufferedReader getmBufferedReader() {
        return mBufferedReader;
    }

    public boolean isConnected() {
        return BTAdapter.isEnabled();
    }


    public BluetoothSocket getConection() {
        return BTSocket;
    }

    public boolean finish() {
        try {
            BTSocket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


<<<<<<< HEAD
}
=======
                connectionFailed();
                Log.i(TAG, "ERRO AO CONECTAR BT: " + e.getMessage());
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice, mSocketType);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (mState == STATE_CONNECTED) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    // Send the obtained bytes to the UI Activity
                    mHandler.obtainMessage(Constantes.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    // Start the service over to restart listening mode
                    BluetoothService.this.start();
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(Constantes.MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}
>>>>>>> origin/master
