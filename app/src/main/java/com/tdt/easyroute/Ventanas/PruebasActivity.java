package com.tdt.easyroute.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import java.util.ArrayList;

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

                ArrayList<DataTableWS.Clientes> clientes ;

                String json = BaseLocal.Select( "select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str,0 visitado,0 conventa,0 concobranza,0 noventa from clientes",getApplicationContext() );

                clientes = ConvertirRespuesta.getClientesJson(json);

                DataTableWS.Clientes cliente;

                for(int i=0; i<clientes.size(); i++)
                {

                }




            }
        });

    }
}
