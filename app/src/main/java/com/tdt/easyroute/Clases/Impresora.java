package com.tdt.easyroute.Clases;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.PruebasActivity;

import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Impresora {

    private static BluetoothAdapter mBluetoothAdapter;
    private static BluetoothDevice mmDevice;
    private static BluetoothSocket mmSocket;
    private static OutputStream mmOut;
    private static int charMax = 32;

    public static boolean imprimir(String mensajeImp, Context contextImp)
    {
        try
        {
            Log.d("impresora","1 Se mando a llamar a imprimir");

            String mma= Utils.LeefConfig("imp",contextImp);

            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!mBluetoothAdapter.isEnabled())
            {
                mBluetoothAdapter.enable();
                try {
                    mmOut.wait(4000);
                    Log.d("impresora","2 Se activo la impresora");
                } catch (Exception e) {
                    return false;
                }
            }

            try
            {
                mmDevice = mBluetoothAdapter.getRemoteDevice(mma);
                Log.d("impresora","3 Encontro impresora");
            } catch (Exception e) {
                Toast.makeText(contextImp, "Error al buscar impresora", Toast.LENGTH_SHORT).show();
                return false;
            }

            conec(mmDevice,contextImp);
            Log.d("impresora","4 se conecto impresora");
            Thread.sleep(500);

            try
            {
                Log.d("impresora","5 comenzo a imprimir");
                mmOut.write(mensajeImp.getBytes());
                mmOut.flush();
                mmOut.close();
                Log.d("impresora","6 Termino de imprimir");
                return true;
            } catch (Exception e)
            {
                Toast.makeText(contextImp, "Impresora NO encontrada", Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (Exception ex) {
            Toast.makeText(contextImp, "[EX] " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static void conec(BluetoothDevice device, Context contextImp)
    {
        // si la impresora no estÃ¡ vinculada pide autorizacion y pin para vincular
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            //mmSocket=device.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOut = mmSocket.getOutputStream();
        } catch (Exception e) {
            Toast.makeText(contextImp, "Error 3:" + e.getMessage(), Toast.LENGTH_LONG).show();
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
