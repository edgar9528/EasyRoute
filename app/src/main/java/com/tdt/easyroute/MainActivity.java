package com.tdt.easyroute;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.tdt.easyroute.Adapter.CustomExpandableListAdapter;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Helper.FragmentNavigationManager;
import com.tdt.easyroute.Interface.NavigationManager;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.MenuBloqueo;
import com.tdt.easyroute.Model.Usuario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,
                                                            GoogleApiClient.ConnectionCallbacks,
                                                            LocationListener {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private NavigationManager navigationManager;
    private int ultimoGrupo=-1;

    private boolean bloquear=false;

    private TextView tv_nombre,tv_ruta;
    private Usuario usuario;
    private MenuBloqueo menuBloqueo;

    //region VARIABLES PARA UBICACION
    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;
    private GoogleApiClient apiClient;
    private LocationRequest locRequest;
    private String latLon=null;
    //endregion

    @Override
    public void onBackPressed() {
        //cerrarSesion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuBloqueo = new MenuBloqueo();

        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            Log.d("salida","ENTRO MAIN ACTIVITY");
            Intent intent = getIntent();
            usuario = (Usuario) intent.getSerializableExtra("usuario");

            //init view
            mDrawerLayout =  findViewById(R.id.drawer_layout);
            expandableListView= findViewById(R.id.navList);
            navigationManager= FragmentNavigationManager.getmInstance(this,this,usuario);

            View listHeaderView = getLayoutInflater().inflate(R.layout.nav_header,null,false);
            expandableListView.addHeaderView(listHeaderView);

            inicializar();
            crearMenu();
            validarMenu();

            addDrawersItem();
            setupDrawer();

            if(savedInstanceState== null)
            {
                selectFirsItemAsDefault();
            }

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);


            //CONFIGURACION DE UBICACION

            //Construcción cliente API Google
            apiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();

        }
        catch (Exception e)
        {
            Utils.msgError(this,getString(R.string.err_main1),e.getMessage());
            Log.d("salida","Error: "+e.toString());
        }

    }

    public void cerrarSesion()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getResources().getString(R.string.msg_importante));
        dialogo1.setMessage( getResources().getString(R.string.msg_salir) );
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getResources().getString(R.string.msg_si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialogo1.setNegativeButton(getResources().getString(R.string.msg_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        dialogo1.show();
    }

    //CONFIGURACION DEL MENU

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirsItemAsDefault() {
        if(navigationManager!= null)
        {
            String title = getString(R.string.mPadre_inicio) +" | "+ getString(R.string.mHijo_principal);
            navigationManager.showFragment("0|0");
            getSupportActionBar().setTitle(title);
        }
    }

    private void addDrawersItem() {

        //ACCIONES AL ABRIR, CERRAR O INTERACTUAR CON MENÚ

        adapter = new CustomExpandableListAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

                if(!menuBloqueo.grupos[i])
                {
                    Toast.makeText(MainActivity.this, getString(R.string.tt_opNodispo), Toast.LENGTH_SHORT).show();
                    return true;
                }
                else
                    return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //set title for toolbar
                //getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString());

                if(ultimoGrupo!=-1)
                    expandableListView.collapseGroup(ultimoGrupo);

                ultimoGrupo=groupPosition;
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                ultimoGrupo=-1;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String selectedItem = ((List) (lstChild.get(lstTitle.get(groupPosition)))).get(childPosition).toString();

                String clave = lstTitle.get(groupPosition) + " | " + selectedItem;
                String seleccion = groupPosition +"|"+ childPosition;

                Log.d("Salida", clave);

                if(!seleccion.equals("7|0") && !seleccion.equals("7|1") &&
                   !seleccion.equals("5|2") && menuBloqueo.hijos.get(groupPosition)[childPosition])
                {
                    getSupportActionBar().setTitle(clave);
                }

                if(menuBloqueo.hijos.get(groupPosition)[childPosition])
                {
                    navigationManager.showFragment(seleccion);
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    ultimoGrupo = -1;
                    expandableListView.collapseGroup(groupPosition);
                }
                else
                {
                    Toast.makeText(MainActivity.this, getString(R.string.tt_opNodispo), Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Inicio2");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(mActivityTitle);
                if(ultimoGrupo>=0)
                    expandableListView.collapseGroup(ultimoGrupo);

                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void crearMenu() {

        //OPCIONES QUE SE MOSTRARAN EN EL MENU

        List<String> title = Arrays.asList(getString(R.string.mPadre_inicio),
                                           getString(R.string.mPadre_inventario),
                                           getString(R.string.mPadre_pedidos),
                                           getString(R.string.mPadre_entregas),
                                           getString(R.string.mPadre_reportes),
                                           getString(R.string.mPadre_clientes),
                                           getString(R.string.mPadre_findia),
                                           getString(R.string.mPadre_configuracion));

        List<String> iniciodia = Arrays.asList(getString(R.string.mHijo_principal),getString(R.string.mHijo_inicioDia));

        List<String> inventario = Arrays.asList(getString(R.string.mHijo_cargaIni),
                                                getString(R.string.mHijo_inventario),
                                                getString(R.string.mHijo_recarga),
                                                getString(R.string.mHijo_devoluciones),
                                                getString(R.string.mHijo_descarga));

        List<String> pedidos = Arrays.asList(getString(R.string.mPadre_clientes));

        List<String> entregas = Arrays.asList(getString(R.string.mHijo_consigna),
                                              getString(R.string.mHijo_pedido),
                                              getString(R.string.mHijo_devolucion));

        List<String> reportes = Arrays.asList(getString(R.string.mHijo_arqueo), getString(R.string.mHijo_ventasDia));

        List<String> clientes = Arrays.asList(getString(R.string.mHijo_cartera),
                                              getString(R.string.mHijo_ordClientes),
                                              getString(R.string.mHijo_busClientes));

        List<String> findia = Arrays.asList(getString(R.string.mHijo_sugerido),
                                            getString(R.string.mHijo_transmitir),
                                            getString(R.string.mHijo_borrarDatos),
                                            getString(R.string.mHijo_finVentas));

        List<String> catalogos = Arrays.asList(getString(R.string.mHijo_catalogos),getString(R.string.bt_salir));

        lstChild = new LinkedHashMap<>();
        lstChild.put(title.get(0), iniciodia);
        lstChild.put(title.get(1), inventario);
        lstChild.put(title.get(2), pedidos);
        lstChild.put(title.get(3), entregas);
        lstChild.put(title.get(4), reportes);
        lstChild.put(title.get(5), clientes);
        lstChild.put(title.get(6), findia);
        lstChild.put(title.get(7), catalogos);

        lstTitle = new ArrayList<>(lstChild.keySet());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return false;
    }

    public void regresarInicio()
    {
        navigationManager.showFragment("0|0");
        String title = getResources().getString(R.string.mPadre_inicio) +" | "+ getResources().getString(R.string.mHijo_principal);
        getSupportActionBar().setTitle( title );
    }

    //TERMINA CONFIGURACION DEL MENU


    public void inicializar()
    {
        try{

            tv_nombre = findViewById(R.id.textViewNombre);
            tv_ruta = findViewById(R.id.textViewRuta);

            String nomUser = usuario.getNombre()+" "+usuario.getAppat();
            tv_nombre.setText(nomUser);


            String rutaCve = Utils.LeefConfig("ruta",getApplication());
            String rutaDes = BaseLocal.SelectDato(string.formatSql("select rut_desc_str from rutas where rut_cve_n={0}",rutaCve),getApplicationContext());

            String rutaMostrar;

            if(rutaDes!=null)
            {
                rutaMostrar = getResources().getString(R.string.hint_ruta) +": "+ rutaDes;
                tv_ruta.setText(rutaMostrar);
            }
            else
            {
                rutaMostrar = getResources().getString( R.string.hint_noRuta);
                tv_ruta.setText(rutaMostrar);
            }
        }
        catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_main2),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void validarMenu()
    {
        try
        {
            Configuracion conf = null;
            conf = Utils.ObtenerConf(getApplication());


            if(conf!=null)
            {
                menuBloqueo.hijos.get(0)[1]=false;

                if(conf.getPreventa()!=1)
                    menuBloqueo.grupos[1]=true;
                else
                    menuBloqueo.grupos[1]=false;

                menuBloqueo.grupos[2]=true;
                menuBloqueo.grupos[6]=true;
                menuBloqueo.grupos[4]=true;

                if(conf.isDescarga())
                    menuBloqueo.grupos[2]=false;
            }
            else
            {
                menuBloqueo.hijos.get(0)[1]=true;
                menuBloqueo.grupos[1]=false;
                menuBloqueo.grupos[2]=false;
                menuBloqueo.grupos[6]=false;
                menuBloqueo.grupos[4]=true;
            }

            validarInventario();

        }catch (Exception e)
        {
            Utils.msgError(this,getString(R.string.err_main3),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void validarInventario()
    {
        try
        {
            Configuracion conf = Utils.ObtenerConf(getApplication());
            ArrayList<DataTableLC.CargaInicial> dt =null;
            boolean carga = false;
            String json = BaseLocal.Select("Select * from cargainicial where est_cve_str='A'",getApplicationContext());
            dt = ConvertirRespuesta.getCargaInicialJson(json);


            if(dt!=null)
                carga =  dt.get(0).getCini_cargado_n().equals("1");

            if(carga)
            {
                menuBloqueo.hijos.get(1)[0]=false; //carga
                menuBloqueo.hijos.get(1)[1]=true; //inventario
                menuBloqueo.hijos.get(1)[2]=true; //recarga
                menuBloqueo.hijos.get(1)[3]=true; //devoluciones
                menuBloqueo.hijos.get(1)[4]=true; //descarga

                Log.d("salida","ENTRO A CARGA TRUE");

                if (conf!=null && conf.isDescarga())
                {
                    menuBloqueo.hijos.get(1)[2]=false; //recarga
                    menuBloqueo.hijos.get(1)[3]=false; //devoluciones
                }

            }
            else
            {
                Log.d("salida","ENTRO A CARGA FALSE");
                menuBloqueo.hijos.get(1)[0]=true; //carga
                menuBloqueo.hijos.get(1)[1]=false; //inventario
                menuBloqueo.hijos.get(1)[2]=false; //recarga
                menuBloqueo.hijos.get(1)[3]=false; //devoluciones
                menuBloqueo.hijos.get(1)[4]=false; //descarga
            }

        }catch (Exception e)
        {
            Utils.msgError(this,getString(R.string.err_main3),e.getMessage());
        }
    }



    //VARIABLES COMPARTIDAS ENTRE FRAGMENTS
    public Usuario getUsuario()
    {
        return usuario;
    }

    //region Obtener ubicación actual

    public String getLatLon()
    {
        return latLon;
    }

    public boolean validaDistancia(DataTableLC.DtCliVenta c, boolean cierre)
    {
        try
        {
            String cini = c.getCli_coordenadaini_str();
            String clicve = c.getCli_cve_n();
            Configuracion conf = Utils.ObtenerConf(getApplication());

            short k = 0;
            String ActPos = "NO VALIDA";
            double DistLimite = 20;
            double DistAlerta = 10;
            String ValidarGPS = "";

            if (!cierre)
                ValidarGPS = "VALIDAR DISTANCIA GPS";
            else
                ValidarGPS = "VALIDAR DISTANCIA GPS AL CIERRE";

            ActPos = getLatLon();

            double distancia = Utils.Distancia(cini, ActPos);

            if (c.getCli_invalidagps_n().equals("1")) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), c.getCli_cve_n(), ValidarGPS, string.formatSql("INVALIDADO POR SISTEMAS: {0} MTS", String.valueOf(distancia)), ActPos), getApplicationContext());
                return true;
            }

            if (conf.isAuditoria()) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), c.getCli_cve_n(), ValidarGPS, string.formatSql("INVALIDADO POR AUDITORIA: {0} MTS", String.valueOf(distancia)), ActPos), getApplicationContext());
                return true;
            }

            if (cini == null || cini.isEmpty()) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, "CLIENTE SIN COORDENADA INICIAL", ActPos), getApplicationContext());
                return true;
            }

            if (cini.length() <= 5) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, "CLIENTE CON COORDENADA INICIAL NO VALIDA: " + cini, ActPos), getApplicationContext());
                return true;
            }

            if (ActPos.equals("NO VALIDA")) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, "NO SE LOGRO OBTENER GPS ACTUAL", ActPos), getApplicationContext());
                return true;
            }

            if (distancia >= 0 && distancia <= DistAlerta) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, string.formatSql("DISTANCIA DENTRO DEL PARAMETRO {0} MTS", String.valueOf(distancia)), ActPos), getApplicationContext());
                return true;
            }

            if (distancia >= 0 && distancia <= DistLimite)
            {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, string.formatSql("DISTANCIA FUERA DEL PARAMETRO {0} MTS", String.valueOf(distancia) ), ActPos), getApplicationContext());
                return true;
            }

            if (distancia >= 0 && distancia > DistLimite)
            {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, string.formatSql("POSICION ACTUAL INVALIDA FUERA DE PARAMETRO {0} MTS", String.valueOf(distancia)), ActPos), getApplicationContext());
                return false;
            }


            return false;
        }catch (Exception e)
        {
            Log.d("salida","error al validar disstancia: "+e.getMessage());
            return false;
        }
    }

    public void enableLocationUpdates() {

        locRequest = new LocationRequest();
        locRequest.setInterval(2000);
        locRequest.setFastestInterval(1000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locRequest)
                        .build();

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        apiClient, locSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        Log.i(LOGTAG, "Configuración correcta");
                        startLocationUpdates();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.i(LOGTAG, "Se requiere actuación del usuario");
                            status.startResolutionForResult(MainActivity.this, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            //BLOQUEAR OPCIONES POR UBICACION
                            Log.i(LOGTAG, "Error al intentar solucionar configuración de ubicación");
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(LOGTAG, "No se puede cumplir la configuración de ubicación necesaria");
                        //BLOQUEAR OPCIONES POR UBICACION
                        break;
                }
            }
        });

        Log.d("salida","Se ha activado la ubicacion");
    }

    public void disableLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                apiClient, this);

        Log.d("salida","Se ha desactivado la ubicacion");

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, MainActivity.this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) {
        if (loc != null) {
            latLon = loc.getLatitude()+","+loc.getLongitude();
        } else {
            latLon = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateUI(lastLocation);

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e(LOGTAG, "Permiso denegado");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case PETICION_CONFIG_UBICACION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(LOGTAG, "El usuario no ha realizado los cambios de configuración necesarios");
                        //BLOQUEAR OPCIONES POR UBICACION
                        break;
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(LOGTAG, "Recibida nueva ubicación!");
        //Mostramos la nueva ubicación recibida
        updateUI(location);
    }
    //endregion

}
