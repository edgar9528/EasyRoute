package com.tdt.easyroute.Ventanas.Configuracion;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdt.easyroute.R;

public class ServidorrutaFragment extends Fragment {

    EditText et_servidor,et_time;
    Button button_regresar,button_guardar;
    String servidor,time;


    public ServidorrutaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servidorruta, container, false);

        Log.d("salida", "SE CREO FAGMENT SERVIDOR|RUTA|UTILIDAD");

        et_servidor = view.findViewById(R.id.et_servidor);
        et_time = view.findViewById(R.id.et_timeout);

        button_guardar = view.findViewById(R.id.button_guardar);
        button_regresar = view.findViewById(R.id.button_regresar);

        verificarServidor();

        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servidor = et_servidor.getText().toString();
                time = et_time.getText().toString();
                if(!servidor.isEmpty()&&!time.isEmpty())
                {
                    guardarServidor();
                    Toast.makeText(getContext(), "Información guardada", Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                }
                else
                    Toast.makeText(getContext(), "Rellena todos los campos", Toast.LENGTH_LONG).show();
            }
        });

        button_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });


        return view;
    }

    public void guardarServidor()
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ServidorPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("servidor",servidor);
        editor.putString("timeout", time);
        editor.apply();
    }

    public void verificarServidor()
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("ServidorPreferences",Context.MODE_PRIVATE);
        servidor = sharedPref.getString("servidor","null");
        time =  sharedPref.getString("timeout","null");

        if(!servidor.equals("null"))
        {
            et_servidor.setText(servidor);
            et_time.setText(time);
        }
    }

}
