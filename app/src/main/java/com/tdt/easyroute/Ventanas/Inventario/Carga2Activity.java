package com.tdt.easyroute.Ventanas.Inventario;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class Carga2Activity extends AppCompatActivity implements AsyncResponseJSON,
                                                                GoogleApiClient.OnConnectionFailedListener,
                                                                GoogleApiClient.ConnectionCallbacks,
                                                                LocationListener {

    private boolean recarga= false;
    private boolean cautivo=false;
    private Configuracion conf = null;

    private Spinner sp_recargas;

    private ArrayList<DataTableWS.Recargas> al_recargas;
    private ArrayList<DataTableWS.RecargasDet> al_recargasDet;
    private ArrayList<DataTableLC.Inventario> al_inventario;
    private ArrayList<String> recargas;
    private int indiceFolio=-1;

    private TextView tv1,tv2,tv3,tv4,tv5;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;

    private String peticion,mensaje,nombreBase,cve,folio,rectxt,spTitulo,mensaje2;

    //region VARIABLES PARA UBICACION
    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;
    private GoogleApiClient apiClient;
    private LocationRequest locRequest;
    String latLon=null;
    //endregion


    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carga2);

        try
        {
            recarga=true;
            cautivo=true;

            //CONFIGURACION DE UBICACION

            //Construcción cliente API Google
            apiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addApi(LocationServices.API)
                    .build();

            layoutInflater =  getLayoutInflater();

            Button button_buscar, button_aplicar, button_salir;

            conf = Utils.ObtenerConf(getApplication());
            nombreBase = getString(R.string.nombreBD);

            if (recarga) {
                mensaje = getString(R.string.mHijo_recarga).toLowerCase();
                mensaje2="Recargas";
            }
            else {
                mensaje = getString(R.string.mHijo_cargaIni).toLowerCase();
                mensaje2="Carga Inicial";
            }

            Log.d("salida","LA RECARGA ES:"+recarga);

            spTitulo = getString(R.string.sp_carga) + " " + mensaje;

            button_buscar = findViewById(R.id.button_buscar);
            button_aplicar = findViewById(R.id.button_aplicar);
            button_salir = findViewById(R.id.button_salir);
            tv1 = findViewById(R.id.tv_rec_cve_n);
            tv2 = findViewById(R.id.tv_rec_folio_str);
            tv3 = findViewById(R.id.tv_rec_falta_dt);
            tv4 = findViewById(R.id.tv_usu_solicita_str);
            tv5 = findViewById(R.id.tv_rec_observaciones_str);
            sp_recargas = findViewById(R.id.spinnerRecargas);
            tableLayout = findViewById(R.id.tableLayout);

            button_buscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buscarRecargas();
                }
            });

            button_aplicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aplicar();
                }
            });

            button_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    salir();
                }
            });

            sp_recargas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = sp_recargas.getSelectedItem().toString();
                    if (!item.equals(spTitulo)) {
                        mostrarRecarga(i - 1); //menos 1 porque el primero es el mensaje
                        buscarRecargaDet(i - 1);
                        indiceFolio = i - 1;
                    } else {
                        tv1.setText("");
                        tv2.setText("");
                        tv3.setText("");
                        tv4.setText("");
                        tv5.setText("");
                        indiceFolio = -1;
                        al_recargasDet = null;
                        mostrarRecargaDet();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            buscarRecargas();

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_inv1), e.getMessage() );
        }


    }

    private void buscarRecargas()
    {
        try
        {
            peticion = "Recargas";
            //parametros del metodo
            ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
            PropertyInfo piUser = new PropertyInfo();
            piUser.setName("ruta");
            piUser.setValue(conf.getRuta());
            propertyInfos.add(piUser);

            PropertyInfo piPass = new PropertyInfo();
            piPass.setName("recarga");
            piPass.setValue(recarga ? 1 : 0);
            propertyInfos.add(piPass);

            Log.d("salida", "ruta: " + conf.getRuta() + " recarga: " + String.valueOf(recarga ? 1 : 0));

            //conexion con el metodo
            ConexionWS_JSON conexionWS = new ConexionWS_JSON(Carga2Activity.this, "ObtenerRecargasJ");
            conexionWS.delegate = Carga2Activity.this;
            conexionWS.propertyInfos = propertyInfos;
            conexionWS.execute();

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_intPeticion), e.getMessage());
        }
    }

    private void aplicar()
    {
        try
        {
            if (indiceFolio != -1) {
                folio = al_recargas.get(indiceFolio).getRec_folio_str();
                cve = al_recargas.get(indiceFolio).getRec_cve_n();

                if (al_recargasDet != null && al_recargasDet.size() > 0) {
                    String men = string.formatSql(getString(R.string.msg_aplicarCarga), mensaje, folio);

                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Carga2Activity.this);
                    dialogo1.setTitle(getString(R.string.msg_importante));
                    dialogo1.setMessage(men);
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            obtenerUbicacion(); //EN ESTA FUNCION SE LLAMA A aplicarCargaInicial();
                        }
                    });
                    dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            //cancelar();
                        }
                    });
                    dialogo1.show();

                } else
                    Toast.makeText(Carga2Activity.this, string.formatSql( getString(R.string.tt_selecInv), mensaje ), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(Carga2Activity.this, string.formatSql( getString(R.string.tt_selecInv), mensaje ), Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Utils.msgError(Carga2Activity.this, getString(R.string.err_inv2), e.getMessage() );
        }
    }

    private void obtenerUbicacion()
    {
        try
        {
            final String[] ubi = new String[1];
            final ProgressDialog progress = new ProgressDialog(Carga2Activity.this);
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
                    aplicarCargaInicial(ubi[0]);
                }
            }, 3000);

        }catch (Exception e)
        {
            Utils.msgError(Carga2Activity.this, getString(R.string.error_ubicacion), e.getMessage() );
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void aplicarCargaInicial(String ubicacion)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(Carga2Activity.this, nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try
        {
            String con = string.formatSql("Select prod_cve_n, inv_recarga_n from inventario where rut_cve_n={0}", String.valueOf(conf.getRuta()));
            String res = BaseLocal.Select(con, Carga2Activity.this);
            al_inventario = ConvertirRespuesta.getInventarioJson(res);

            db.beginTransaction();

            String col= recarga ? "inv_recarga_n" : "inv_inicial_n";

            Log.d("salida", "COL: "+col);

            String qi = "insert into inventario(rut_cve_n,prod_cve_n," + col + ") values ({0},{1},{2})";
            //String qu = "update inventario set " + col + "=" + col + "+{0} where " + "rut_cve_n={1} and prod_cve_n={2}";
            String qu = "update inventario set " + col + "=" + col + "+{2} where " + "rut_cve_n={0} and prod_cve_n={1}";


            if (!recarga)
            {
                db.execSQL(  string.formatSql("delete from inventario where rut_cve_n={0}", String.valueOf( conf.getRuta()) ) ) ;
                Log.d("salida","entro !recarga : eliminar inventario");
            }


            for(int i=0; i<al_recargasDet.size();i++)
            {
                DataTableWS.RecargasDet in = al_recargasDet.get(i);
                DataTableLC.Inventario k=null;

                for(int j=0; j<al_inventario.size();j++)
                {
                    if(al_recargasDet.get(i).getProd_cve_n().equals( al_inventario.get(j).getProd_cve_n() ))
                    {
                        k=al_inventario.get(j);
                        break;
                    }
                }

                if(k!=null && recarga )
                {
                    Log.d("salida","ENTRO AQUI, ACT INVENTARIO");
                    db.execSQL( string.formatSql( qu,  in.getProd_cant_n(), String.valueOf( conf.getRuta() )  , in.getProd_cve_n()) );

                    String suma = String.valueOf(  Float.parseFloat( k.getInv_recarga_n() )  + Float.parseFloat( in.getProd_cant_n() ) );
                    db.execSQL( string.formatSql(Querys.Inventario.InsertMovimiento,String.valueOf( conf.getRuta() ), in.getProd_cve_n(), null, conf.getUsuario(), k.getInv_recarga_n(),"0", in.getProd_cant_n(), suma,mensaje2 ) );
                    Log.d("salida", "Actualizar inventario");
                }
                else
                {
                    Log.d("salida","ENTRO AQUI, INS INVENTARIO");
                    db.execSQL( string.formatSql( qi,  String.valueOf( conf.getRuta() ), in.getProd_cve_n()  , in.getProd_cant_n()  ) );
                    db.execSQL( string.formatSql(Querys.Inventario.InsertMovimiento,String.valueOf( conf.getRuta() ), in.getProd_cve_n(), null, conf.getUsuario(), "0", "0",in.getProd_cant_n(),in.getProd_cant_n(),mensaje2 ) );
                    Log.d("salida", "Insertar inventario");
                }
            }

            Log.d("salida","ENTRO AQUI: SALIR DEL FOR 0");

            if (!recarga)
            {
                db.execSQL(string.formatSql(Querys.Inventario.DesactivaCarga));
                db.execSQL(string.formatSql(Querys.Inventario.InsertCarga, "1", conf.getUsuario(), "A", mensaje2.toUpperCase()));
            }

            rectxt = recarga ? "RECARGA" : "CARGA INICIAL";

            db.execSQL(string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion,conf.getUsuario(), rectxt,string.formatSql("FOLIO: {0}",folio), String.valueOf( conf.getRuta() ) , ubicacion));

            db.setTransactionSuccessful();

            Log.d("salida","ENTRO AQUI: TERMINAR FOR INVENTARIO");

            peticionProcesarRecarga();

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_inv2), e.getMessage() );
            Log.d("salida", "Error: "+e.getMessage() );
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
    }

    private void peticionProcesarRecarga()
    {
        try
        {
            peticion = "ProcesarRecarga";

            //parametros del metodo
            ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
            PropertyInfo pi_recarga = new PropertyInfo();
            pi_recarga.setName("recarga");
            pi_recarga.setValue(cve);
            propertyInfos.add(pi_recarga);

            PropertyInfo pi_usu = new PropertyInfo();
            pi_usu.setName("usu_aplica_str");
            pi_usu.setValue(conf.getUsuario());
            propertyInfos.add(pi_usu);

            Log.d("salida", "parametros: cve: " + cve + "  usu: " + conf.getUsuario());

            ConexionWS_JSON conexionWSJ = new ConexionWS_JSON(Carga2Activity.this, "ProcesarRecargaJ");
            conexionWSJ.propertyInfos = propertyInfos;
            conexionWSJ.delegate = Carga2Activity.this;
            conexionWSJ.execute();

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_intPeticion), e.getMessage());
        }

    }

    private void procesarRecarga()
    {
        try
        {
            String consulta = "Select p.prod_cve_n from productos p inner join categorias c on p.cat_cve_n=c.cat_cve_n where c.cat_desc_str='ENVASE' and p.prod_cve_n not in (Select p.prod_cve_n from inventario p inner join categorias c on p.cat_cve_n=c.cat_cve_n where c.cat_desc_str='ENVASE')";
            String res = BaseLocal.Select(consulta, Carga2Activity.this);

            ArrayList<DataTableLC.DtEnv> dtEnvs;
            dtEnvs = ConvertirRespuesta.getDtEnvJson(res);

            DatabaseHelper databaseHelper = new DatabaseHelper(Carga2Activity.this, nombreBase, null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            try {
                db.beginTransaction();

                if (dtEnvs != null && dtEnvs.size() > 0) {
                    for (int i = 0; i < dtEnvs.size(); i++) {
                        db.execSQL(string.formatSql(Querys.Inventario.InsertInventario2enCero, String.valueOf(conf.getRuta()), dtEnvs.get(i).getProd_cve_n()));
                    }
                }
                Log.d("salida", "inserto el proceso de recarga");

                db.setTransactionSuccessful();

                Toast.makeText(Carga2Activity.this, getString(R.string.tt_infoActu), Toast.LENGTH_LONG).show();

                db.endTransaction();
                db.close();

                if (!cautivo)
                    finish();
                else
                    buscarRecargas();

            } catch (Exception e) {
                Utils.msgError(this, getString(R.string.err_inv3), e.getMessage());
                Log.d("salida", "Error: " + e.getMessage());
            } finally {
                if (db.isOpen()) {
                    db.endTransaction();
                    db.close();
                }
            }

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_inv3), e.getMessage());
        }
    }

    private void llenarSpinnerRecargas()
    {
        try
        {
            recargas = new ArrayList<>();
            recargas.add(spTitulo);
            for (int i = 0; i < al_recargas.size(); i++)
                recargas.add((i + 1 + " - " + al_recargas.get(i).getRec_folio_str()));

            sp_recargas.setAdapter(new ArrayAdapter<>(Carga2Activity.this, R.layout.spinner_item, recargas));

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

    private void mostrarRecarga(int indiceRecarga)
    {
        try
        {
            tv1.setText(al_recargas.get(indiceRecarga).getRec_cve_n());
            tv2.setText(al_recargas.get(indiceRecarga).getRec_folio_str());
            String fecha = Utils.FechaWS(al_recargas.get(indiceRecarga).getRec_falta_dt());
            tv3.setText(fecha);
            tv4.setText(al_recargas.get(indiceRecarga).getUsu_solicita_str());
            tv5.setText(al_recargas.get(indiceRecarga).getRec_observaciones_str());

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_mostrarInfo), e.getMessage());
        }
    }

    private void buscarRecargaDet(int indiceRecarga)
    {
        try {
            //parametros del metodo
            ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();

            PropertyInfo piPass = new PropertyInfo();
            piPass.setName("recarga");
            piPass.setValue(al_recargas.get(indiceRecarga).getRec_cve_n());
            propertyInfos.add(piPass);

            peticion = "RecargasDet";

            //conexion con el metodo
            ConexionWS_JSON conexionWS = new ConexionWS_JSON(Carga2Activity.this, "ObtenerRecargaDetJ");
            conexionWS.delegate = Carga2Activity.this;
            conexionWS.propertyInfos = propertyInfos;
            conexionWS.execute();
        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_intPeticion), e.getMessage());
        }
    }

    private void mostrarRecargaDet()
    {
        try
        {
            tableLayout.removeAllViews();
            TableRow tr;
            DataTableWS.RecargasDet rd;

            if (al_recargasDet != null)
                for (int i = 0; i < al_recargasDet.size(); i++) {
                    tr = (TableRow) layoutInflater.inflate(R.layout.tabla_recargasdet, null);
                    rd = al_recargasDet.get(i);
                    int can = (int) Float.parseFloat(rd.getProd_cant_n());

                    ((TextView) tr.findViewById(R.id.t_prod_sku_str)).setText(rd.getProd_sku_str());
                    ((TextView) tr.findViewById(R.id.t_prod_desc_str)).setText(rd.getProd_desc_str());
                    ((TextView) tr.findViewById(R.id.t_prod_cant_n)).setText(String.valueOf(can));

                    tableLayout.addView(tr);
                }
        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_mostrarInfo), e.getMessage());
        }
    }

    private void salir()
    {
        try
        {
            if (cautivo) {
                if (al_recargas != null && al_recargas.size() > 0)
                    Toast.makeText(Carga2Activity.this, getString(R.string.tt_inv1), Toast.LENGTH_SHORT).show();
                else
                {
                    Intent i = getIntent();
                    setResult(RESULT_OK, i);
                    finish();
                }
            } else
                finish();
        }catch (Exception e)
        {
            Utils.msgError(Carga2Activity.this, getString(R.string.error_salir), e.getMessage());
        }
    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta)
    {
        try {
            if (estado) {
                //recibio información
                if (respuesta != null) {
                    if (peticion.equals("Recargas")) {
                        al_recargas = ConvertirRespuesta.getRecargasJson(respuesta);

                        if (al_recargas.size() > 0) {
                            llenarSpinnerRecargas();
                        }
                    } else if (peticion.equals("RecargasDet")) {
                        al_recargasDet = ConvertirRespuesta.getRecargasDetJson(respuesta);
                        if (al_recargasDet.size() > 0) {
                            mostrarRecargaDet();
                        }
                    } else if (peticion.equals("ProcesarRecarga")) {
                        DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                        if (retVal != null) {
                            if (retVal.getRet().equals("true")) {
                                procesarRecarga();
                            } else {
                                Toast.makeText(Carga2Activity.this, retVal.getMsj(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Carga2Activity.this, getString(R.string.tt_noInformacion), Toast.LENGTH_SHORT).show();
                            Log.d("salida", "retval nulo");
                        }
                    }
                } else {
                    Toast.makeText(Carga2Activity.this, getString(R.string.tt_noInformacion), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(Carga2Activity.this, respuesta, Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Utils.msgError(Carga2Activity.this, getString(R.string.error_peticion), e.getMessage() );
        }

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
                            status.startResolutionForResult(Carga2Activity.this, PETICION_CONFIG_UBICACION);
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
        if (ActivityCompat.checkSelfPermission(Carga2Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, Carga2Activity.this);
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
