package com.tdt.easyroute.Fragments.Pedidos.DetallesCliente;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.DetallesCliVM;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;


public class DomiciliodetFragment extends Fragment {

    private DataTableLC.DtCliVenta rc;
    private Spinner sp_direcciones;
    private ArrayList<DataTableWS.Direcciones> al_direcciones;
    private EditText et_calle, et_numExt, et_numInt, et_colonia, et_municipio, et_tel1, et_tel2,et_coordenada;
    private String ubiCliente;
    private DetallesCliVM detallesCliVM;

    private MapView mapView;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";

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
        Mapbox.getInstance(getContext(), getString(R.string.token_mapa));
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

            mapView = view.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);

            et_calle = view.findViewById(R.id.et_calle);
            et_numExt = view.findViewById(R.id.et_noExt);
            et_numInt = view.findViewById(R.id.et_noInt);
            et_colonia = view.findViewById(R.id.et_colonia);
            et_municipio = view.findViewById(R.id.et_municipio);
            et_tel1 = view.findViewById(R.id.et_telefono1);
            et_tel2 = view.findViewById(R.id.et_telefono2);
            et_coordenada = view.findViewById(R.id.et_coordenada);

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
            if (!ubiCliente.equals("null"))
            {
                String[] latLong = ubiCliente.split(",");
                float lat = 0, lon = 0;
                if (!latLong[0].equals("null") && !latLong[1].equals("null")) {
                    lat = Float.parseFloat(latLong[0]);
                    lon = Float.parseFloat(latLong[1]);
                }

                final float fLat = lat, fLong = lon;

                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull MapboxMap mapboxMap) {
                        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
                        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                                Point.fromLngLat(fLong, fLat)));

                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(fLat, fLong)) // Sets the new camera position
                                .zoom(14) // Sets the zoom
                                .tilt(30) // Set the camera tilt
                                .build(); // Creates a CameraPosition from the builder
                        mapboxMap.setCameraPosition(position);

                        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")

                                // Add the SymbolLayer icon image to the map style
                                .withImage(ICON_ID, BitmapFactory.decodeResource(
                                        getActivity().getResources(), R.drawable.pin))

                                // Adding a GeoJson source for the SymbolLayer icons.
                                .withSource(new GeoJsonSource(SOURCE_ID,
                                        FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))

                                .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                                        .withProperties(PropertyFactory.iconImage(ICON_ID),
                                                iconAllowOverlap(true),
                                                iconIgnorePlacement(true),
                                                iconOffset(new Float[]{0f, -9f}))
                                ), new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {

                            }
                        });
                    }
                });
            }
        }catch (Exception e)
        {
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

    private void mostrarRuta()
    {
        try
        {
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
