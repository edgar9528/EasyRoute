package com.tdt.easyroute.Ventanas.Pedidos.DetallesCliente;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    private TextView tv_calle, tv_numExt, tv_numInt, tv_colonia, tv_municipio, tv_tel1, tv_tel2,tv_coordenada;
    private String ubiCliente;
    private DetallesCliVM detallesCliVM;

    private GoogleMap map;

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

            Button button_salir, button_ruta;

            tv_calle = view.findViewById(R.id.tv_calle);
            tv_numExt = view.findViewById(R.id.tv_noExt);
            tv_numInt = view.findViewById(R.id.tv_noInt);
            tv_colonia = view.findViewById(R.id.tv_colonia);
            tv_municipio = view.findViewById(R.id.tv_municipio);
            tv_tel1 = view.findViewById(R.id.tv_telefono1);
            tv_tel2 = view.findViewById(R.id.tv_telefono2);
            tv_coordenada = view.findViewById(R.id.tv_coordenada);

            button_salir = view.findViewById(R.id.button_salir);
            button_ruta = view.findViewById(R.id.button_ruta);
            sp_direcciones = view.findViewById(R.id.spinnerDireccion);

            button_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        detallesCliVM.getUbiCliente().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String ubicacion) {
                ubiCliente = ubicacion;
                cargarMapa();
            }
        });

        detallesCliVM.getBt_ruta().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean btRuta) {
                mostrarRuta();
            }
        });
    }

    private void cargarMapa()
    {
        try {
            float lat = 0, lon = 0;
            if (!string.EsNulo(ubiCliente)) {
                String[] latLong = ubiCliente.split(",");

                if (!latLong[0].equals("null") && !latLong[1].equals("null")) {
                    lat = Float.parseFloat(latLong[0]);
                    lon = Float.parseFloat(latLong[1]);
                }
            }

            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
            final float finalLon = lon;
            final float finalLat = lat;
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    mMap.clear(); //clear old markers

                    int zoom = 10;
                    if (finalLat == 0 && finalLon == 0)
                        zoom = 0;

                    CameraPosition googlePlex = CameraPosition.builder()
                            .target(new LatLng(finalLat, finalLon))
                            .zoom(zoom)
                            .tilt(10)
                            .build();

                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null);

                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(finalLat, finalLon)));
                }
            });
        }catch (Exception e)
        {
            Log.d("salida","ERROR MAPA: "+e.getMessage());
            Utils.msgError(getContext(), getString(R.string.err_ped5), e.getMessage());
        }
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

            tv_calle.setText( d.getDir_calle_str().equals("null")?"":d.getDir_calle_str() );
            tv_numExt.setText( d.getDir_noext_str().equals("null")?"":d.getDir_noext_str() );
            tv_numInt.setText( d.getDir_noint_str().equals("null")?"":d.getDir_noint_str() );
            tv_colonia.setText( d.getDir_colonia_str().equals("null")?"":d.getDir_colonia_str() );
            tv_municipio.setText( d.getDir_municipio_str().equals("null")?"":d.getDir_municipio_str() );
            tv_tel1.setText( d.getDir_tel1_str().equals("null")?"":d.getDir_tel1_str() );
            tv_tel2.setText( d.getDir_tel2_str().equals("null")?"":d.getDir_tel2_str() );
            tv_coordenada.setText( d.getDir_coordenada_str().equals("null")?"":d.getDir_coordenada_str() );

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped3), e.getMessage() );
        }
    }

    private void mostrarRuta()
    {
        try
        {
            /*String cadena = "http://maps.google.com/maps?q=loc:{0}";
            String strUri = string.formatSql(cadena, ubiCliente);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);*/

            String cadena = "http://maps.google.com/maps?daddr={0}";

            Log.d("salida",ubiCliente);

            if( string.EsNulo(ubiCliente) )
            {
                Toast.makeText(getContext(), getString(R.string.tt_detPed1), Toast.LENGTH_SHORT).show();
            }
            else
            {
                String strUri = string.formatSql(cadena, ubiCliente);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_ubicacion),e.getMessage());
        }
    }


}
