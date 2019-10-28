package com.tdt.easyroute.Ventanas.Configuracion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.R;

import java.util.ArrayList;

public class ServidorFragment extends Fragment {

    public ServidorFragment() {
        // Required empty public constructor
    }

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    ArrayList<String> lista_catalogos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servidor, container, false);
        layoutInflater = inflater;

        try{

            ConfiguracionActivity activity = (ConfiguracionActivity) getActivity();
            lista_catalogos=activity.getCatalogos();

            tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);

            tableLayout.removeAllViews();
            TableRow tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

            for(int i=0; i<lista_catalogos.size();i++)
            {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

                ((RadioButton) tr.findViewById(R.id.tabla_radio)).setText(String.valueOf(i));
                ((TextView) tr.findViewById(R.id.tabla_catalogo)).setText(lista_catalogos.get(i));
                ((TextView) tr.findViewById(R.id.tabla_estado)).setText("ok");

                tableLayout.addView(tr);
            }






        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }


        return view;
    }


    private View.OnClickListener rbListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String destino = ((RadioButton) view).getText().toString();
            Log.d("salida","seleccion:"+destino);

        }
    };

}
