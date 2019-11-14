package com.tdt.easyroute.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.R;

public class PruebasActivity extends AppCompatActivity {

    Button button;
    TextView tv_lat,tv_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        button = findViewById(R.id.button_prueba);
        tv_lat = findViewById(R.id.tv_latitud);
        tv_lon = findViewById(R.id.tv_longitud);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }
}
