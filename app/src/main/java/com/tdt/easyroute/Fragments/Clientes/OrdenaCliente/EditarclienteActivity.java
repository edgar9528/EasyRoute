package com.tdt.easyroute.Fragments.Clientes.OrdenaCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class EditarclienteActivity extends AppCompatActivity {

    Button b_salir,b_guardar;
    Spinner sp_estado, sp_frecuencia;
    TextView tv_coor;
    int indice_estado=-1,indice_frecu=-1;

    ArrayList<DataTableWS.Estatus> dt_estatus=null;
    ArrayList<String> estadoDesc=null;

    ArrayList<DataTableWS.FrecuenciasVisita> dt_frecuencias=null;
    ArrayList<String> frecuenciaDesc=null;

    CamposEditar camposEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarcliente);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Editar cliente");

        camposEditar= (CamposEditar)getIntent().getExtras().getSerializable("CamposEditar");

        b_salir = findViewById(R.id.b_salir);
        b_guardar = findViewById(R.id.b_guardar);
        tv_coor = findViewById(R.id.tv_coor);
        sp_estado = findViewById(R.id.sp_estado);
        sp_frecuencia = findViewById(R.id.sp_frecuencia);

        b_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardar();
            }
        });

        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sp_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indice_estado = i;
                Log.d("salida","cambio estado: "+i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_frecuencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indice_frecu=i;
                Log.d("salida","cambio frecuencia: "+i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        obtieneEstados();
        obtieneFrecuencias();
        tv_coor.setText( camposEditar.getCoordenadas() );

    }

    private void obtieneEstados()
    {
        String qry = "SELECT EST_CVE_STR,EST_DESC_STR FROM ESTATUS WHERE EST_PROV_N=1";

        try
        {
            String json = BaseLocal.Select(qry,getApplicationContext());
            dt_estatus = ConvertirRespuesta.getEstatusJson(json);

            if(dt_estatus!=null && dt_estatus.size()>0 )
            {
                estadoDesc = new ArrayList<>();
                int item=0;
                for(int i=0; i<dt_estatus.size();i++)
                {
                    estadoDesc.add(dt_estatus.get(i).getEst_desc_str());

                    if(dt_estatus.get(i).getEst_cve_str().equals( camposEditar.getEstado() ))
                        item=i;
                }

                sp_estado.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, estadoDesc));
                sp_estado.setSelection(item);

            }
        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void obtieneFrecuencias()
    {
        String qry = "SELECT frec_cve_n,frec_desc_str FROM frecuenciasvisita";

        try
        {
            String json = BaseLocal.Select(qry,getApplicationContext());
            dt_frecuencias = ConvertirRespuesta.getFrecuenciasVisitaJson(json);

            if(dt_frecuencias!=null && dt_frecuencias.size()>0 )
            {
                frecuenciaDesc = new ArrayList<>();
                int item=0;
                for(int i=0; i<dt_frecuencias.size();i++)
                {
                    frecuenciaDesc.add(dt_frecuencias.get(i).getFrec_desc_str());
                    if(dt_frecuencias.get(i).getFrec_cve_n().equals( camposEditar.getFrecuencia() ))
                        item=i;
                }

                sp_frecuencia.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, frecuenciaDesc));
                sp_frecuencia.setSelection(item);

            }
        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void guardar()
    {

        try {
            String estado = dt_estatus.get(indice_estado).getEst_cve_str();
            String frecuencia = dt_frecuencias.get(indice_frecu).getFrec_cve_n();
            String frecuencia_des = dt_frecuencias.get(indice_frecu).getFrec_desc_str();
            String coor = tv_coor.getText().toString();

            camposEditar.setEstado(estado);
            camposEditar.setFrecuencia(frecuencia);
            camposEditar.setCoordenadas(coor);
            camposEditar.setFrecuencia_desc(frecuencia_des);

            Intent i = getIntent();
            i.putExtra("CamposEditar", camposEditar);
            setResult(RESULT_OK, i);
            finish();

        }catch (Exception e)
        {

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
