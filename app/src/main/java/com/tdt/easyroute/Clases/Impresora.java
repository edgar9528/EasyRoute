package com.tdt.easyroute.Clases;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.OutputStream;
import java.util.UUID;

public class Impresora {

    private static BluetoothAdapter mBluetoothAdapter;
    private static BluetoothDevice mmDevice;
    private static BluetoothSocket mmSocket;
    private static OutputStream mmOut;
    private static Context context;
    private static int charMax = 32;

    public static void imprimir(String msg, Context context1)
    {
        context=context1;
        try
        {
            String mma= Utils.LeefConfig("imp",context);

            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
                try {
                    mmOut.wait(4000);
                } catch (Exception e) {

                }
            }
            try {
                mmDevice = mBluetoothAdapter.getRemoteDevice(mma);
            } catch (Exception e) {
                Toast.makeText(context, "Error al buscar impresora", Toast.LENGTH_SHORT).show();
                return;
            }
            conec(mmDevice);
            try{
                Thread.sleep(500);
            }catch (Exception ex){

            }

            try {
                mmOut.write(msg.getBytes());
                mmOut.flush();
                mmOut.close();
            } catch (Exception e) {
                Toast.makeText(context, "Impresora NO encontrada", Toast.LENGTH_SHORT).show();
                return;
            }

        } catch (Exception ex) {
            Toast.makeText(context, "[EX] " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void conec(BluetoothDevice device) {
        // si la impresora no estÃ¡ vinculada pide autorizacion y pin para vincular
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            //mmSocket=device.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOut = mmSocket.getOutputStream();
        } catch (Exception e) {
            Toast.makeText(context, "Error 3:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public static String CrearFila(String fila, int cantidadColumnas )
    {
        try
        {
            String[] palabras = ObtenerPalabras(fila, cantidadColumnas);

            if(palabras!=null)
            {
                try
                {
                    String filaAux = "";
                    int limite = (charMax / cantidadColumnas);

                    for (int i = 0; i < cantidadColumnas; i++)
                    {
                        if (palabras[i].length() > limite)
                            palabras[i] = palabras[i].substring(0, limite-1);

                        for (int j = palabras[i].length(); j < limite; j++)
                            palabras[i] = palabras[i] + " ";

                        filaAux += palabras[i];
                    }

                    return filaAux+"\n";

                }catch (Exception e)
                {
                    return fila;
                }
            }
            else
                return fila;

        }catch (Exception e)
        {
            return fila;
        }
    }

    private static String[] ObtenerPalabras(String fila,int cantidadColumnas)
    {
        try
        {
            fila = fila.trim();
            String palabras[] = new String[cantidadColumnas];
            String palabra = "";
            int can = 0;
            for (int i = 0; i < fila.length(); i++) {
                if (fila.charAt(i) == ' ') {
                    if (palabra.length() > 0) {
                        palabras[can] = palabra;
                        palabra = "";
                        can++;
                    }
                } else {
                    palabra += fila.charAt(i);
                    if (fila.length() - 1 == i)
                        palabras[can] = palabra;
                }
            }
            return palabras;

        }catch (Exception e)
        {
            return null;
        }
    }

    public static String DarTamañoDer(String palabra,int tamaño)
    {
        try {
            if (palabra.length() > tamaño)
                palabra = palabra.substring(0, tamaño);
            else if (palabra.length() < tamaño)
                for (int i = palabra.length(); i < tamaño; i++)
                    palabra += " ";
            return palabra;
        }catch (Exception e)
        {
            return palabra;
        }
    }

    public static String DarTamañoIzq(String palabra, int tamaño)
    {
        if( palabra.length() <tamaño)
            for(int i=palabra.length(); i<tamaño;i++)
                palabra= " "+palabra;
        else
            palabra=palabra.substring(0,tamaño);

        return palabra;
    }

    public static String Centrar(String texto)
    {
        if(texto.length()<=charMax)
        {
            int espacios = ( charMax-texto.length() )/2;
            for(int i=0; i<espacios;i++)
            {
                texto= " "+texto+" ";
            }
        }
        return texto;
    }

}
