package com.tdt.easyroute;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {

    EditText et_servidor,et_time;
    Button button_regresar,button_guardar;
    String servidor,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        this.setTitle("Configuración");

        et_servidor = findViewById(R.id.et_servidor);
        et_time = findViewById(R.id.et_timeout);

        button_guardar = findViewById(R.id.button_guardar);
        button_regresar = findViewById(R.id.button_regresar);

        verificarServidor();

        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servidor = et_servidor.getText().toString();
                time = et_time.getText().toString();
                if(!servidor.isEmpty()&&!time.isEmpty())
                {
                    guardarServidor();
                    Toast.makeText(getApplicationContext(), "Información guardada", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Rellena todos los campos", Toast.LENGTH_LONG).show();
            }
        });

        button_regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void guardarServidor()
    {
        SharedPreferences sharedPref = getSharedPreferences("ServidorPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("servidor",servidor);
        editor.putString("timeout", time);
        editor.apply();
    }

    public void verificarServidor()
    {
        SharedPreferences sharedPref = getSharedPreferences("ServidorPreferences",Context.MODE_PRIVATE);
        servidor = sharedPref.getString("servidor","null");
        time =  sharedPref.getString("timeout","null");

        if(!servidor.equals("null"))
        {
            et_servidor.setText(servidor);
            et_time.setText(time);
        }

    }


}
