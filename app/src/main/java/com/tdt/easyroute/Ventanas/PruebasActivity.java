package com.tdt.easyroute.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Pedidos.ClientesPedFragment;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class PruebasActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pruebas);

        Button prueba = findViewById(R.id.prueba);

        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InicializarAsync inicializar = new InicializarAsync();
                inicializar.execute();
            }
        });






    }


    private class InicializarAsync extends AsyncTask<Boolean,Integer,Boolean>
    {

        public InicializarAsync() {
        }

        private String mensaje="";

        private ProgressDialog progreso;

        @Override protected void onPreExecute() {
            progreso = new ProgressDialog(PruebasActivity.this);
            progreso.setMessage(getString(R.string.msg_cargando));
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {

            try
            {
                String msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r";
                msg+=msg="HOLA   PRRO    \r\n\n";

                imprimir(msg);

                return true;
            }catch (Exception e)
            {
                return false;
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progreso.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            progreso.dismiss();

            if(result)
            {


            }
            else
            {

            }
        }
    }


    private static BluetoothAdapter mBluetoothAdapter;
    private static BluetoothDevice mmDevice;
    private static BluetoothSocket mmSocket;
    private static OutputStream mmOut;

    public boolean imprimir(String msg)
    {
        try
        {
            Log.d("impresora","1 Se mando a llamar a imprimir");
            String mma= Utils.LeefConfig("imp", this);

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
                Toast.makeText(this, "Error al buscar impresora", Toast.LENGTH_SHORT).show();
                return false;
            }

            conec(mmDevice);
            Log.d("impresora","4 se conecto impresora");
            Thread.sleep(500);

            try
            {
                Log.d("impresora","5 comenzo a imprimir");
                mmOut.write(msg.getBytes());
                mmOut.flush();
                mmOut.close();
                Log.d("impresora","6 Termino de imprimir");
                return true;
            } catch (Exception e)
            {
                Toast.makeText(this, "Impresora NO encontrada", Toast.LENGTH_SHORT).show();
                return false;
            }

        } catch (Exception ex) {
            Toast.makeText(this, "[EX] " + ex.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void conec(BluetoothDevice device) {
        // si la impresora no estÃ¡ vinculada pide autorizacion y pin para vincular
        try {
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            //mmSocket=device.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOut = mmSocket.getOutputStream();
        } catch (Exception e) {
            Toast.makeText(this, "Error 3:" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
