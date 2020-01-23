package com.tdt.easyroute.Fragments.Clientes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BuscarClientesActivity extends AppCompatActivity implements AsyncResponseJSON,
                                                                        GoogleApiClient.OnConnectionFailedListener,
                                                                        GoogleApiClient.ConnectionCallbacks,
                                                                        LocationListener {

    Button button_buscar, button_borrar, button_reimp, button_act, button_salir,button_seleccionar;
    EditText et_filtro;
    boolean buscar = false;

    Configuracion conf;

    String cli_cve_nSelec="";

    private String Day="";

    ArrayList<DataTableWS.Clientes> bsClientes = null;
    ArrayList<DataTableWS.Clientes> dgClientes = null;
    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;

    //region VARIABLES PARA UBICACION
    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;
    private GoogleApiClient apiClient;
    private LocationRequest locRequest;
    String latLon=null;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_clientes);

        this.setTitle( getResources().getString(R.string.title_buscli));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layoutInflater= getLayoutInflater();
        vista = getWindow().getDecorView().getRootView();

        tableLayout = findViewById(R.id.tableLayout);
        button_buscar = findViewById(R.id.button_buscar);
        button_borrar = findViewById(R.id.button_borrar);
        button_reimp = findViewById(R.id.button_reimp);
        button_act = findViewById(R.id.button_actualizar);
        button_seleccionar = findViewById(R.id.button_seleccionar);
        button_salir = findViewById(R.id.button_salir);
        et_filtro = findViewById(R.id.et_filtro);

        button_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro= et_filtro.getText().toString();
                mostrarClientes(filtro.toUpperCase());
            }
        });

        button_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_filtro.setText("");
                mostrarClientes("");
            }
        });

        button_reimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imprimir();
            }
        });

        button_seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionar(cli_cve_nSelec);
            }
        });

        button_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizaCliente();
            }
        });

        //CONFIGURACION DE UBICACION

        //Construcción cliente API Google
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();


        inicializar();

    }

    private void inicializar()
    {
        conf = Utils.ObtenerConf2(getApplication());
        Day = diaActual();

        mostrarTitulo();

        InicializarAsync inicializarAsync = new InicializarAsync();
        inicializarAsync.execute();

        if (buscar) {
            button_seleccionar.setEnabled(false);
        }
    }

    private String diaActual()
    {
        String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};

        Calendar cal= Calendar.getInstance();
        cal.setTime(new Date());
        int numeroDia = cal.get(Calendar.DAY_OF_WEEK)-1;

        String dia = dias[numeroDia];
        String FieldDay="";

        if(conf.getPreventa()==1)
        {
            if(dias[numeroDia].equals("Sábado"))
            {
                dia = "Lunes";
            }
            else
                dia= dias[numeroDia+1];
        }


        switch (dia) {
            case "Lunes":
                FieldDay = "cli_lun_n";
                break;
            case "Martes":
                FieldDay = "cli_mar_n";
                break;
            case "Miércoles":
                FieldDay = "cli_mie_n";
                break;
            case "Jueves":
                FieldDay = "cli_jue_n";
                break;
            case "Viernes":
                FieldDay = "cli_vie_n";
                break;
            case "Sábado":
                FieldDay = "cli_sab_n";
                break;
            case "Domingo":
                FieldDay = "cli_dom_n";
                break;
        }

        return FieldDay;

    }

    private void cargarClientes()
    {

        String consulta;
        try
        {
            if(conf.getPreventa()==1)
            {
                consulta = string.formatSql("Select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str,cli_especial_n " +
                        "from clientes where rut_cve_n in (select rut_cve_n from rutas where rut_prev_n={0}) and est_cve_str='A'", String.valueOf(conf.getRuta()));
            }
            else
            {
                consulta = string.formatSql("Select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str,cli_especial_n " +
                        "from clientes where rut_cve_n={0} and est_cve_str='A'", String.valueOf(conf.getRuta()));

            }

            String json = BaseLocal.Select( consulta ,getApplicationContext());
            bsClientes = ConvertirRespuesta.getClientesJson(json);

        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getApplicationContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_buscarclientes, null);

        ((TextView) tr.findViewById(R.id.t_idExt)).setTypeface(((TextView) tr.findViewById(R.id.t_idExt)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_Negocio)).setTypeface(((TextView) tr.findViewById(R.id.t_Negocio)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_Razon)).setTypeface(((TextView) tr.findViewById(R.id.t_Razon)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);
    }

    private void mostrarClientes(String filtro)
    {
        mostrarTitulo();
        dgClientes=null;
        cli_cve_nSelec="";
        if(bsClientes!=null && bsClientes.size()>0)
        {
            TableRow tr;
            DataTableWS.Clientes c;

            dgClientes = new ArrayList<>();

            for(int i=0; i<bsClientes.size();i++)
            {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_buscarclientes, null);
                c= bsClientes.get(i);

                if(!filtro.equals(""))
                {

                    if(c.getCli_cveext_str().toUpperCase().contains(filtro) ||
                            c.getCli_nombrenegocio_str().toUpperCase().contains(filtro) ||
                            c.getCli_razonsocial_str().toUpperCase().contains(filtro)   )
                    {

                        ((TextView) tr.findViewById(R.id.t_idExt)).setText(c.getCli_cveext_str());
                        ((TextView) tr.findViewById(R.id.t_Negocio)).setText(c.getCli_nombrenegocio_str());
                        ((TextView) tr.findViewById(R.id.t_Razon)).setText(c.getCli_razonsocial_str());
                        tr.setTag( c.getCli_cve_n());
                        tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

                        tableLayout.addView(tr);
                        dgClientes.add(c);
                    }
                }
                else
                {
                    ((TextView) tr.findViewById(R.id.t_idExt)).setText(c.getCli_cveext_str());
                    ((TextView) tr.findViewById(R.id.t_Negocio)).setText(c.getCli_nombrenegocio_str());
                    ((TextView) tr.findViewById(R.id.t_Razon)).setText(c.getCli_razonsocial_str());
                    tr.setTag( c.getCli_cve_n());
                    tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

                    tableLayout.addView(tr);
                    dgClientes.add(c);
                }


            }
        }
    }

    //region evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            String tag = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            if(cli_cve_nSelec.equals(tag)) //si coincide con el anterior, quiere decir que dio doble clic
            {
                seleccionar(cli_cve_nSelec);
            }
            else
            {
                //si no coincide, pinta todas de blanca
                for(int i=0; i<dgClientes.size();i++)
                {
                    TableRow row = vista.findViewWithTag(dgClientes.get(i).getCli_cve_n());
                    row.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                cli_cve_nSelec=tag;

            }

        }
    };
    //endregion

    private void seleccionar(String cle_cve_n)
    {
        try {

            String consulta, json;
            if (!cle_cve_n.isEmpty())
            {
                DataTableWS.Clientes dgCliente = null;

                for (int i = 0; i < dgClientes.size(); i++)
                    if (dgClientes.get(i).getCli_cve_n().equals(cli_cve_nSelec))
                        dgCliente = dgClientes.get(i);


                String cliente = dgCliente.getCli_cve_n();
                String IdExt = dgCliente.getCli_cveext_str();

                boolean cli_especial_n = Utils.getBool(dgCliente.getCli_especial_n());

                ArrayList<DataTableWS.Clientes2> dtca = null;
                DataTableWS.Clientes2 rc = null;

                consulta = string.formatSql("select c.*,r.rut_invalidafrecuencia_n from clientes c  left join rutas r on c.rut_cve_n=r.rut_cve_n where c.cli_cve_n={1}", cliente);
                json = BaseLocal.Select(consulta, getApplicationContext());

                Log.d("salida",json);

                dtca = ConvertirRespuesta.getClientes2Json(json);

                if (dtca != null) {
                    rc = dtca.get(0);
                }

                if (Utils.getBool(rc.getCli_invalidafrecuencia_n()))
                {
                    consulta = string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), String.valueOf(conf.getRuta()), rc.getCli_cve_n(), "VALIDAR FRECUENCIA", "INVALIDADO POR SISTEMAS", "{0}");
                    obtenerUbicacion(consulta);
                } else if (Utils.getBool(rc.getRut_invalidafrecuencia_n()))
                {
                    consulta = string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), String.valueOf(conf.getRuta()), rc.getCli_cve_n(), "VALIDAR FRECUENCIA", "INVALIDADO POR SISTEMAS NIVEL RUTA", "{0}");
                    obtenerUbicacion(consulta);
                }
                else
                {
                    if (conf.isAuditoria())
                    {
                        consulta = string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), String.valueOf(conf.getRuta()), rc.getCli_cve_n(), "VALIDAR FRECUENCIA", "INVALIDADO POR AUDITORIA", "{0}");
                        obtenerUbicacion(consulta);
                    } else {

                        int diaVisitar = 0;
                        switch (Day) {
                            case "cli_lun_n":
                                diaVisitar = Integer.parseInt(rc.getCli_lun_n());
                                break;
                            case "cli_mar_n":
                                diaVisitar = Integer.parseInt(rc.getCli_mar_n());
                                break;
                            case "cli_mie_n":
                                diaVisitar = Integer.parseInt(rc.getCli_mie_n());
                                break;
                            case "cli_jue_n":
                                diaVisitar = Integer.parseInt(rc.getCli_jue_n());
                                break;
                            case "cli_vie_n":
                                diaVisitar = Integer.parseInt(rc.getCli_vie_n());
                                break;
                            case "cli_sab_n":
                                diaVisitar = Integer.parseInt(rc.getCli_sab_n());
                                break;
                            case "cli_dom_n":
                                diaVisitar = Integer.parseInt(rc.getCli_dom_n());
                                break;
                        }

                        if (diaVisitar <= 0) {
                            consulta = string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), String.valueOf(conf.getRuta()), rc.getCli_cve_n(), "VALIDAR FRECUENCIA", "EL CLIENTE NO SE VISITA HOY", "{0}");
                            obtenerUbicacion(consulta);
                            Toast.makeText(getApplicationContext(), "El cliente no se visita hoy", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                Intent i = getIntent();
                i.putExtra("clave", dgCliente.getCli_nombrenegocio_str());
                setResult(RESULT_OK, i);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Debe seleccionar un cliente", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Log.d( "salida","Error: "+e.getMessage());
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void obtenerUbicacion(final String consulta)
    {
        try {
            final String[] ubi = new String[1];
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle(getString(R.string.msg_cargando));
            progress.setMessage(getString(R.string.msg_espera));
            progress.show();
            progress.setCancelable(false);

            enableLocationUpdates();

            ubi[0] = getLatLon();
            Log.d("salida", "Ubicacion anterior: " + ubi[0]);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.cancel();
                    disableLocationUpdates();
                    ubi[0] = getLatLon();
                    Log.d("salida", "Ubicacion nueva: " + ubi[0]);

                    BaseLocal.Insert( string.formatSql(consulta, ubi[0])  ,getApplicationContext());
                }
            }, 3000);

        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void imprimir()
    {
        try
        {

            if(cli_cve_nSelec!="")
            {
                DataTableWS.Clientes rc=null;

                for(int i=0; i<dgClientes.size(); i++)
                    if(dgClientes.get(i).getCli_cve_n().equals(cli_cve_nSelec))
                        rc= dgClientes.get(i);

                String cliente = rc.getCli_cve_n();
                String IdExt = rc.getCli_cveext_str();

                if (conf.getPreventa()==1);
                //Utils.ImprimirPreVentaBebidas(Utils.ObtenerVisitaPrevBebidas(cliente), true, "a s e s o r");
                //else
                //Utils.ImprimirVentaBebidas(Utils.ObtenerVisitaBebidas(cliente), true);


            }
            else
                Toast.makeText(getApplicationContext(), "No ha seleccionado ningún cliente", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void actualizaCliente()
    {
        try {

            if (!cli_cve_nSelec.isEmpty())
            {

                DataTableWS.Clientes dgCliente = null;

                for (int i = 0; i < dgClientes.size(); i++)
                    if (dgClientes.get(i).getCli_cve_n().equals(cli_cve_nSelec))
                        dgCliente = dgClientes.get(i);


                String cliente = dgCliente.getCli_cve_n();
                String IdExt = dgCliente.getCli_cveext_str();


                ArrayList<PropertyInfo> propertyInfos  =new ArrayList<>();

                PropertyInfo pi = new PropertyInfo();
                pi.setName("IdCliente");
                pi.setValue(cliente);
                propertyInfos.add(pi);

                ConexionWS_JSON cws = new ConexionWS_JSON(BuscarClientesActivity.this,"ActualizarClienteJ");
                cws.propertyInfos = propertyInfos;
                cws.delegate = BuscarClientesActivity.this;
                cws.execute();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Debe seleccionar un cliente", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Log.d( "salida","Error: "+e.getMessage());
            Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class InicializarAsync extends AsyncTask<Boolean,Integer,Boolean> {

        public InicializarAsync() {
        }

        private String mensaje="";

        private ProgressDialog progreso;

        @Override protected void onPreExecute() {
            progreso = new ProgressDialog(BuscarClientesActivity.this);
            progreso.setMessage(getString(R.string.msg_cargando));
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {

            try
            {
                cargarClientes();
                return true;
            }catch (Exception e)
            {
                mensaje = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progreso.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            progreso.dismiss();

            if(result)
            {
                mostrarClientes("");
            }
            else
            {
                Toast.makeText(getApplication(), "Error: "+mensaje, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

        if(estado)
        {
            if (respuesta != null)
            {
                ArrayList<DataTableWS.Clientes> clientes;
                clientes = ConvertirRespuesta.getClientesJson(respuesta);

                if(clientes!=null && clientes.size()>0)
                {
                    DataTableWS.Clientes r = clientes.get(0);

                    try {

                        String consulta=string.formatSql(Querys.Clientes.UpClientes4, r.getCli_cveext_str() , getBool( r.getCli_padre_n() ),
                                r.getCli_cvepadre_n(),r.getCli_razonsocial_str(), r.getCli_rfc_Str() , getBool( r.getCli_reqfact_n() ) ,r.getCli_nombrenegocio_str(),
                                r.getCli_nom_str(), r.getCli_app_str(),r.getCli_apm_str(),r.getCli_fnac_dt(), r.getCli_genero_str(),r.getLpre_cve_n(),
                                r.getNota_cve_n(), r.getFpag_cve_n(), getBool( r.getCli_consigna_n() ) , getBool( r.getCli_credito_n() ) ,r.getCli_montocredito_n(),
                                r.getCli_plazocredito_n(),r.getCli_credenvases_n(),r.getCli_estcredito_str(), getBool( r.getCli_fba_n()) ,
                                r.getCli_porcentajefba_n(),r.getRut_cve_n(),r.getNvc_cve_n(),r.getGiro_cve_n(),r.getCli_email_str(),
                                r.getCli_dirfact_n(), r.getCli_dirent_n(), r.getCli_Tel1_str(),r.getCli_tel2_str(),r.getEmp_cve_n(),
                                r.getCli_coordenadaini_str(),r.getEst_cve_str(),r.getTcli_cve_n(),r.getCli_lun_n(),
                                r.getCli_mar_n(), r.getCli_mie_n(), r.getCli_jue_n(), r.getCli_vie_n(),r.getCli_sab_n(),r.getCli_dom_n(),
                                r.getFrec_cve_n(), getBool( r.getCli_especial_n() ) , getBool( r.getCli_esvallejo_n() ) , r.getNpro_cve_n(),r.getCli_huixdesc_n(),
                                getBool( r.getCli_eshuix_n() ) , getBool( r.getCli_prospecto_n() ) , getBool( r.getCli_invalidafrecuencia_n() ) , getBool( r.getCli_invalidagps_n() ) ,
                                getBool( r.getCli_dobleventa_n() ), getBool(r.getCli_comodato_n()) , r.getSeg_cve_n(), getBool( r.getCli_dispersion_n() ) ,
                                r.getCli_dispersioncant_n(), r.getCli_limitemes_n(), r.getCli_cve_n());

                        BaseLocal.Insert( consulta,getApplicationContext());

                        Toast.makeText(getApplicationContext(), "Cliente actualizado", Toast.LENGTH_SHORT).show();
                        onBackPressed();

                    }
                    catch (Exception e)
                    {
                        Log.d("salida", "Error: "+e.getMessage());
                        Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No se encontro el cliente", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), respuesta, Toast.LENGTH_LONG).show();
        }
    }

    private String getBool(String cad)
    {
        try {
            if (cad.equals("true")) {
                cad = "1";
            } else {
                cad = "0";
            }
            return cad;
        }catch (Exception e)
        {
            return "0";
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //region Obtener ubicación actual

    public String getLatLon()
    {
        return latLon;
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
                            status.startResolutionForResult(BuscarClientesActivity.this, PETICION_CONFIG_UBICACION);
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
        if (ActivityCompat.checkSelfPermission(BuscarClientesActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, BuscarClientesActivity.this);
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
