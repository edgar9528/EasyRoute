package com.tdt.easyroute.Fragments.Pedidos.DetallesCliente;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.DetallesCliVM;

import java.util.ArrayList;


public class DomiciliodetFragment extends Fragment {

    private DataTableLC.DtCliVenta rc;
    private Spinner sp_direcciones;
    private ArrayList<DataTableWS.Direcciones> al_direcciones;
    private EditText et_calle, et_numExt, et_numInt, et_colonia, et_municipio, et_tel1, et_tel2,et_coordenada;
    private String ubiCliente;
    private DetallesCliVM detallesCliVM;

    public static DomiciliodetFragment newInstance() {
        DomiciliodetFragment fragment = new DomiciliodetFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detallesCliVM = ViewModelProviders.of ( getActivity() ).get(DetallesCliVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_domiciliodet, container, false);

        try
        {
            MainDetallesActivity mainDetallesActivity = (MainDetallesActivity) getActivity();
            rc = mainDetallesActivity.getCliente();

            Button button_salir, button_ubicacion, button_ruta;


            et_calle = view.findViewById(R.id.et_calle);
            et_numExt = view.findViewById(R.id.et_noExt);
            et_numInt = view.findViewById(R.id.et_noInt);
            et_colonia = view.findViewById(R.id.et_colonia);
            et_municipio = view.findViewById(R.id.et_municipio);
            et_tel1 = view.findViewById(R.id.et_telefono1);
            et_tel2 = view.findViewById(R.id.et_telefono2);
            et_coordenada = view.findViewById(R.id.et_coordenada);


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

            sp_direcciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mostrarDireccion(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            cargarDirecciones();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_det1), e.getMessage());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detallesCliVM.getUbiCliente().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String ubicacion) {
                ubiCliente = ubicacion;
            }
        });

        detallesCliVM.getBt_ruta().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean btRuta) {
                mostrarRuta();
            }
        });

        detallesCliVM.getBt_ubicacion().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean btRuta) {
                mostrarUbicacion();
            }
        });

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
        catch (Exception e)
        {
            Log.d("salida", "Error"+ e.getMessage());
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

    private void mostrarDireccion(int posicion)
    {
        try
        {
            DataTableWS.Direcciones d = al_direcciones.get(posicion);
            et_calle.setText( d.getDir_calle_str() );
            et_numExt.setText( d.getDir_noext_str() );
            et_numInt.setText( d.getDir_noint_str() );
            et_colonia.setText( d.getDir_colonia_str() );
            et_municipio.setText( d.getDir_municipio_str() );
            et_tel1.setText( d.getDir_tel1_str() );
            et_tel2.setText( d.getDir_tel2_str() );
            et_coordenada.setText( d.getDir_coordenada_str() );

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped3), e.getMessage() );
        }
    }

    private void mostrarUbicacion()
    {
        try {
            String cadena = "http://maps.google.com/maps?q=loc:{0}";
            String strUri = string.formatSql(cadena, ubiCliente);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_ubicacion),e.getMessage());
        }
    }

    private void mostrarRuta()
    {
        try
        {
            /*String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+"19.431145"+","+"-99.1833078"+"&daddr="+"19.4335754"+","+"-99.1913711";
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(Intent.createChooser(intent, "Select an application"));*/

            String cadena = "http://maps.google.com/maps?q=loc:{0}";
            String strUri = string.formatSql(cadena, ubiCliente);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_ubicacion),e.getMessage());
        }
    }

}
