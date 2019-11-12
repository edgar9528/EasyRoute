package com.tdt.easyroute.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tdt.easyroute.R;

public class PruebasActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        button = findViewById(R.id.button_prueba);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PruebasActivity.this, "SOY LA PRUEBA", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
