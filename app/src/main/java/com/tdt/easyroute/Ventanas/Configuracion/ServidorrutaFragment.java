package com.tdt.easyroute.Ventanas.Configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Impresora;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ServidorrutaFragment extends Fragment implements AsyncResponseJSON {

    private EditText et_servidor,et_time,et_imp,et_ruta; //edittext
    private String servidor,time,empresa,ruta,imp; //variables a almacenar
    private String nombreBase,peticion,mensajeET="Clic para seleccionar ruta";

    private ArrayList<DataTableWS.Ruta> lista_rutas=null;
    private ArrayList<String> rutas;

    private SpinnerDialog spinnerDialog;

    private boolean crut=false;


    public ServidorrutaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_servidorruta, container, false);

        try {

            nombreBase = getString(R.string.nombreBD);

            Button button_salir, button_guardar, button_listar, button_pruWs, button_pruImp;

            button_guardar = view.findViewById(R.id.button_guardar);
            button_salir = view.findViewById(R.id.button_regresar);
            button_listar = view.findViewById(R.id.button_listar);
            button_pruWs = view.findViewById(R.id.button_pruebaWs);
            button_pruImp = view.findViewById(R.id.button_pruebaImp);

            et_servidor = view.findViewById(R.id.et_servidor);
            et_time = view.findViewById(R.id.et_timeout);
            et_imp = view.findViewById(R.id.et_comImpresora);
            et_ruta = view.findViewById(R.id.et_ruta);

            inicializar();

            button_pruWs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    servidor = et_servidor.getText().toString();
                    time = et_time.getText().toString();

                    if (!servidor.isEmpty() && !time.isEmpty()) {
                        peticion = "prueba";
                        ConexionWS_JSON ws = new ConexionWS_JSON(getContext(), "Prueba");
                        ws.delegate = ServidorrutaFragment.this;
                        ws.propertyInfos = null;
                        ws.servidor = servidor;
                        ws.timeout = Integer.parseInt(time);
                        ws.execute();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.tt_camposVacios), Toast.LENGTH_LONG).show();
                    }
                }
            });

            button_pruImp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pruebaImpresora();
                }
            });

            button_guardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    servidor = et_servidor.getText().toString();
                    time = et_time.getText().toString();
                    imp = et_imp.getText().toString();
                    empresa = "1";

                    Log.d("salida", "COINCIDIO:|" + et_ruta.getText() + "|" + mensajeET + "|");
                    if (et_ruta.getText().toString().equals(mensajeET)) {
                        ruta = "0";
                    } else {
                        ruta = getCveRuta(et_ruta.getText().toString());
                    }

                    if (!servidor.isEmpty() && !time.isEmpty() && !ruta.isEmpty() && !imp.isEmpty()) {
                        Utils.CreafConfig(servidor, time, empresa, ruta, imp, getActivity().getApplication());
                        Toast.makeText(getContext(), getString(R.string.tt_infoActu), Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    } else
                        Toast.makeText(getContext(), getString(R.string.tt_camposVacios), Toast.LENGTH_LONG).show();
                }
            });

            button_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });


            button_listar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listarRutas();
                }
            });


            et_ruta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (spinnerDialog != null)
                        spinnerDialog.showSpinerDialog();
                    else {
                        Toast.makeText(getContext(), getString(R.string.tt_listarInfo), Toast.LENGTH_LONG).show();
                    }
                }
            });

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf7),e.getMessage());
        }

        return view;
    }

    public void inicializar()
    {
        try {
            //Mostrar valores almacenados
            et_servidor.setText(Utils.LeefConfig("servidor", getActivity().getApplication()));
            et_time.setText(Utils.LeefConfig("timeout", getActivity().getApplication()));
            et_imp.setText(Utils.LeefConfig("imp", getActivity().getApplication()));

            ConfiguracionActivity activity = (ConfiguracionActivity) getActivity();
            lista_rutas = activity.getRutas();

            et_ruta.setText(mensajeET);

            cargarRutas();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf7), e.getMessage() );
        }

    }

    private void listarRutas()
    {
        try {
            crut = false;

            servidor = et_servidor.getText().toString();
            time = et_time.getText().toString();

            if (!servidor.isEmpty() && !time.isEmpty()) {
                peticion = "listarRutas";
                ConexionWS_JSON conexionWS_json = new ConexionWS_JSON(getContext(), "ObtenerRutasJ");
                conexionWS_json.delegate = ServidorrutaFragment.this;
                conexionWS_json.propertyInfos = null;
                conexionWS_json.servidor = servidor;
                conexionWS_json.timeout = Integer.parseInt(time);
                conexionWS_json.execute();
            } else {
                Toast.makeText(getContext(), getString(R.string.tt_camposVacios), Toast.LENGTH_LONG).show();
            }

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf15), e.getMessage());
        }

    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

        try {
            //Se realizo la conexion con el ws
            if (estado) {
                //recibio información
                if (respuesta != null) {
                    if (peticion.equals("listarRutas")) {
                        lista_rutas = null;
                        lista_rutas = ConvertirRespuesta.getRutasJson(respuesta);

                        guardarRutas();
                        cargarRutas();
                    }
                    if (peticion.equals("prueba")) {
                        Toast.makeText(getContext(), respuesta, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.tt_noInformacion), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_peticion),e.getMessage());
        }
    }

    private String getCveRuta(String rut_des)
    {
        String cve;
        int p = rutas.indexOf(rut_des);

        cve = lista_rutas.get(p).getRut_cve_n();

        Log.d("salida","cve: "+cve+" | des:"+rut_des);

        return cve;
    }

    private String getDescRuta(String rut_cve)
    {
        String des="";

        for(int i=0; i< lista_rutas.size();i++)
        {
            if( lista_rutas.get(i).getRut_cve_n().equals(rut_cve) )
            {
                des = lista_rutas.get(i).getRut_desc_str();
                i=lista_rutas.size();
            }
        }

        return des;
    }

    private void guardarRutas()
    {
        try {
            if (lista_rutas != null)
            {
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), nombreBase, null, 1);
                SQLiteDatabase bd = databaseHelper.getWritableDatabase();

                if (bd != null) {

                    bd.execSQL(Querys.Rutas.DelRutas);
                    Log.d("salida","Eliminar rutas almacenadas");
                    for(int i=0; i<lista_rutas.size();i++)
                    {
                        DataTableWS.Ruta r = lista_rutas.get(i);
                        String con = Querys.Rutas.InsRutas;

                        bd.execSQL(string.formatSql(con,r.getRut_cve_n(),r.getRut_desc_str(),r.getRut_orden_n(),r.getTrut_cve_n(),r.getAsesor_cve_str(),r.getGerente_cve_str(),r.getSupervisor_cve_str(),r.getEst_cve_str(),r.getTco_cve_n(),r.getRut_prev_n()));

                    }
                    Log.d("salida","guardar rutas descargadas");
                }

                if(bd.isOpen())
                    bd.close();
            }
        }
        catch (Exception e)
        {
            Utils.msgError(getContext(),getString(R.string.error_almacenar),e.getMessage());
            Log.d("salida","Error:"+e.getMessage());
        }
    }

    private void cargarRutas()
    {
        try {

            if (lista_rutas != null) {
                rutas = new ArrayList<>();
                for (int i = 0; i < lista_rutas.size(); i++)
                    rutas.add(lista_rutas.get(i).getRut_desc_str());

                //Mostrar la ruta almacenada
                String cve_ruta = Utils.LeefConfig("ruta", getActivity().getApplication());
                String des_ruta = getDescRuta(cve_ruta);
                if (!des_ruta.isEmpty())
                    et_ruta.setText(des_ruta);


                //SE CREA SPINNER Y SU METODO ONCLICK
                spinnerDialog = new SpinnerDialog(getActivity(), rutas, getResources().getString(R.string.sp_selecRuta), getResources().getString(R.string.bt_cancelar));
                spinnerDialog.setCancellable(true); // for cancellable
                spinnerDialog.setShowKeyboard(true);// for open keyboard by default

                spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        et_ruta.setText(item);

                        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                    }
                });

            }

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf15),e.getMessage());
        }

    }

    private void pruebaImpresora()
    {
        String macAddress = et_imp.getText().toString();
        String mma;

        if(macAddress.length() == 12){
            mma = macAddress;
            mma = mma.substring(0, 2) + ":" + mma.substring(2, 4) + ":" + mma.substring(4, 6) + ":" + mma.substring(6, 8) + ":" + mma.substring(8, 10) + ":" + mma.substring(10, 12);
        }
        else
            if(macAddress.length()!=17)
            {
                Toast.makeText(getContext(),getString(R.string.err_mac),Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                mma = macAddress;
            }

        Utils.guardarImpresora( mma, getActivity().getApplication());

        String pruebaImpresora="********************************\r\r\r";
        pruebaImpresora+="Leyendo informacion...\r\r\r";
        pruebaImpresora+="Version de software: "+ Utils.Version() + "\r\r\r";
        pruebaImpresora+="Fecha: "+ Utils.FechaLocal() + "\r\r\r";
        pruebaImpresora+="Hora: "+ Utils.HoraLocal() + "\r\r\r";
        pruebaImpresora+="**Prueba de impresion exitosa**\r\r\r";
        pruebaImpresora+="********************************\r\r\r";

        Impresora.imprimir(pruebaImpresora, getContext());

    }


}