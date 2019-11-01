package com.tdt.easyroute.Ventanas.Configuracion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
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

    EditText et_servidor,et_time,et_imp,et_ruta; //edittext
    Button button_salir,button_guardar,button_listar,button_pruWs,button_pruImp; //botones
    String servidor,time,empresa,ruta,imp; //variables a almacenar
    String rutaSelec_des,rutaSelec_clv; //variables para obtener la ruta que selecciono el usuario
    String nombreBase,peticion,mensajeET="Clic para seleccionar ruta";

    ArrayList<DataTableWS.Ruta> lista_rutas=null;
    ArrayList<String> rutas;

    SpinnerDialog spinnerDialog;

    boolean crut=false;


    public ServidorrutaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_servidorruta, container, false);
        nombreBase = getString( R.string.nombreBD );

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

                String ser =et_servidor.getText().toString();
                String time = et_time.getText().toString();

                if( !ser.isEmpty() && !time.isEmpty() )
                {
                    peticion="prueba";
                    ConexionWS_JSON ws = new ConexionWS_JSON(getContext(), "Prueba");
                    ws.delegate = ServidorrutaFragment.this;
                    ws.propertyInfos=null;
                    ws.execute();
                }
                else
                {
                    Toast.makeText(getContext(), "Ingresa un servidor y TimeOut", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servidor = et_servidor.getText().toString();
                time = et_time.getText().toString();
                imp = et_imp.getText().toString();
                empresa="1";

                Log.d("salida","COINCIDIO:|"+et_ruta.getText()+"|"+mensajeET+"|");
                if(et_ruta.getText().toString().equals(mensajeET))
                {
                    ruta="";
                }
                else
                {
                    ruta= getCveRuta( et_ruta.getText().toString());
                }


                if(!servidor.isEmpty()&&!time.isEmpty()&&!ruta.isEmpty()&&!imp.isEmpty())
                {
                    Utils.CreafConfig(servidor,time,empresa,ruta,imp,getActivity().getApplication());
                    Toast.makeText(getContext(), "Información guardada", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getContext(), "Rellena todos los campos", Toast.LENGTH_LONG).show();
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
                if(spinnerDialog!=null)
                    spinnerDialog.showSpinerDialog();
                else
                {
                    Toast.makeText(getContext(), "Primero listar información", Toast.LENGTH_LONG).show();
                }
            }
        });



        return view;
    }

    public void inicializar()
    {
        //Mostrar valores almacenados
        et_servidor.setText(Utils.LeefConfig("servidor",getActivity().getApplication()));
        et_time.setText(Utils.LeefConfig("timeout",getActivity().getApplication()));
        et_imp.setText(Utils.LeefConfig("imp",getActivity().getApplication()));

        ConfiguracionActivity activity = (ConfiguracionActivity) getActivity();
        lista_rutas= activity.getRutas();

        et_ruta.setText(mensajeET);

        cargarRutas();

    }

    public void listarRutas()
    {
        crut = false;
        peticion="listarRutas";

        ConexionWS_JSON conexionWS_json = new ConexionWS_JSON(getContext(),"ObtenerRutasJ");
        conexionWS_json.delegate = ServidorrutaFragment.this;
        conexionWS_json.propertyInfos=null;
        conexionWS_json.execute();

    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

        //Se realizo la conexion con el ws
        if(estado)
        {
            //recibio información
            if (respuesta != null)
            {
                if (peticion.equals("listarRutas"))
                {
                    lista_rutas=null;
                    lista_rutas = ConvertirRespuesta.getRutasJson(respuesta);

                    guardarRutas();
                    cargarRutas();
                }
                if(peticion.equals("prueba"))
                {
                    Toast.makeText(getContext(), respuesta, Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(getContext(), "No se encontro información", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();
        }

    }

    public String getCveRuta(String rut_des)
    {
        String cve="";
        int p = rutas.indexOf(rut_des);

        cve = lista_rutas.get(p).getRut_cve_n();

        Log.d("salida","cve: "+cve+" | des:"+rut_des);

        return cve;
    }

    public String getDescRuta(String rut_cve)
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

    public void guardarRutas()
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

                        //Log.d("salida","m:"+r.getRut_cve_n()+" "+r.getRut_desc_str()+" "+r.getRut_orden_n()+" "+r.getTrut_cve_n()+" "+r.getAsesor_cve_str()+" "+r.getGerente_cve_str()+" "+r.getSupervisor_cve_str()+" "+r.getEst_cve_str()+" "+r.getTco_cve_n()+" "+r.getRut_prev_n());

                        bd.execSQL(string.formatSql(con,r.getRut_cve_n(),r.getRut_desc_str(),r.getRut_orden_n(),r.getTrut_cve_n(),r.getAsesor_cve_str(),r.getGerente_cve_str(),r.getSupervisor_cve_str(),r.getEst_cve_str(),r.getTco_cve_n(),r.getRut_prev_n()));

                    }
                    Log.d("salida","guardar rutas descargadas");
                }

                bd.close();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("salida","Error:"+e.getMessage());
        }
    }

    public void cargarRutas()
    {
        if(lista_rutas!=null)
        {
            rutas = new ArrayList<>();
            for (int i = 0; i < lista_rutas.size(); i++)
                rutas.add( lista_rutas.get(i).getRut_desc_str() );

            //Mostrar la ruta almacenada
            String cve_ruta = Utils.LeefConfig("ruta",getActivity().getApplication());
            String des_ruta = getDescRuta(cve_ruta);
            if(!des_ruta.isEmpty())
                et_ruta.setText(des_ruta);


            //SE CREA SPINNER Y SU METODO ONCLICK
            spinnerDialog=new SpinnerDialog(getActivity(),rutas,"Seleccione una ruta","Cerrar");
            //spinnerDialog=new SpinnerDialog(getActivity(),rutas,"Selecciona una ruta",R.style.DialogAnimations_SmileWindow,"Cerrar");

            spinnerDialog.setCancellable(true); // for cancellable
            spinnerDialog.setShowKeyboard(false);// for open keyboard by default

            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    et_ruta.setText(item);
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService (Context.INPUT_METHOD_SERVICE); mgr.hideSoftInputFromWindow (getView().getWindowToken (), 0);
                    //Toast.makeText(getContext(), item + "  " + position+"", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }



}