package com.tdt.easyroute.Fragments.Pedidos.DetallesCliente;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import java.util.ArrayList;


public class DomiciliodetFragment extends Fragment {

    DataTableLC.DtCliVenta rc;
    Spinner sp_direcciones;
    ArrayList<DataTableWS.Direcciones> al_direcciones;

    public static DomiciliodetFragment newInstance() {
        DomiciliodetFragment fragment = new DomiciliodetFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_domiciliodet, container, false);

        try {

            MainDetallesActivity mainDetallesActivity = (MainDetallesActivity) getActivity();
            rc = mainDetallesActivity.getCliente();

            Button button_salir, button_ubicacion, button_ruta;

            button_salir = view.findViewById(R.id.button_salir);
            button_ubicacion = view.findViewById(R.id.button_ubicacion);
            button_ruta = view.findViewById(R.id.button_ruta);
            sp_direcciones = view.findViewById(R.id.spinnerDireccion);

            button_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });

            button_ubicacion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarUbicacion();
                }
            });

            button_ruta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mostrarRuta();
                }
            });

            cargarDirecciones();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_det1), e.getMessage());
        }

        return view;
    }

    private void cargarDirecciones()
    {
        try
        {
            String con, json;

            con= string.formatSql( "select * from direcciones where cli_cve_n={0}", rc.getCli_cve_n() );
            json= BaseLocal.Select(con,getContext());
            al_direcciones = ConvertirRespuesta.getDireccionesJson(json);

            ArrayList<String> direcciones = new ArrayList<>();
            if(al_direcciones!=null)
            {
                for(int i=0; i<al_direcciones.size();i++)
                {
                    direcciones.add(al_direcciones.get(i).getDir_alias_str());
                }

                sp_direcciones.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, direcciones));

            }


        }
        catch (Exception ex)
        {
            Log.d("salida", "Error"+ ex.getMessage());
            Toast.makeText(getContext(), "Error al mostrar direcciones: "+ ex.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void mostrarUbicacion()
    {
        //19.4335754,-99.1913711
        String strUri = "http://maps.google.com/maps?q=loc:" + "19.4335754" + "," + "-99.1913711" + " (Ubicación actual)";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);

    }

    private void mostrarRuta()
    {

        //tdt 19.431145,-99.1833078
        //casa 19.3490827,-99.1129385
        //metro 19.4335754,-99.1913711
        String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+"19.431145"+","+"-99.1833078"+"&daddr="+"19.4335754"+","+"-99.1913711";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(Intent.createChooser(intent, "Select an application"));
    }

}
