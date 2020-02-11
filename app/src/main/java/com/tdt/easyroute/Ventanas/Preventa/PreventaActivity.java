package com.tdt.easyroute.Ventanas.Preventa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tdt.easyroute.R;

public class PreventaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventa);

        setTitle(getString(R.string.title_preventa));

        Button b_salir = findViewById(R.id.button_salir);

        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                i.putExtra("porEscanear", true);
                setResult(RESULT_OK, i);
                finish();

            }
        });


    }
}
