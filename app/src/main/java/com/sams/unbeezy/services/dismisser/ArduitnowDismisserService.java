package com.sams.unbeezy.services.dismisser;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Richard on 03-Apr-18.
 */

public class ArduitnowDismisserService extends PanicDismisserService {
    public static final char START_ALARM_CODE = 'n';
    public static final char END_ALARM_CODE = 'g';
    public static final String ARDUINO_BLUETOOTH_DEVICE_NAME = "HC-05";
    private final String LOG_TAG = this.getClass().getSimpleName();
    private final int handlerState = 0;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice arduinoDevice;
    private BluetoothSocket arduinoSocket;

    private ArduinoThread arduinoThread;

    private Handler arduinoHandler;

    private StringBuilder stringBuilder = new StringBuilder();

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public ArduitnowDismisserService() {
        super("ArduitnowDismisserService", "Ar-du-it-now!", "Press the push button 20 times!");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "Service Created");

        setArduinoHandler();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {
            setArduinoDevice();
        }
        setArduinoSocket();
        connectToArduino();

        ArduinoThread arduinoThread = new ArduinoThread(arduinoSocket);
        arduinoThread.start();

        arduinoThread.write(String.format("%c", START_ALARM_CODE));
    }

    @SuppressLint("HandlerLeak")
    private void setArduinoHandler() {
        arduinoHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    stringBuilder.append(readMessage);
                    if (stringBuilder.charAt(0) == END_ALARM_CODE) {
                        dismiss();
                    }
                    removeCallbacksAndMessages(null);
                }
            }
        };
    }

    private void setArduinoSocket() {
        try {
            arduinoSocket =  arduinoDevice.createRfcommSocketToServiceRecord(BTMODULEUUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setArduinoDevice() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device: pairedDevices) {
                if (device.getName().equals(ARDUINO_BLUETOOTH_DEVICE_NAME)) {
                    arduinoDevice = device;
                    break;
                }
            }
        }
    }

    private void connectToArduino() {
        try {
            arduinoSocket.connect();
        } catch (IOException e) {
            try {
                arduinoSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {

        }
    }

    private class ArduinoThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ArduinoThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer); // read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    arduinoHandler.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        // write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
            }
        }
    }
}
