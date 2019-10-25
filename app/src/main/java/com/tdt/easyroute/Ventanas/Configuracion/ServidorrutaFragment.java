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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.tdt.easyroute.Clases.ConexionWS;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.ParametrosWS;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponse;
import com.tdt.easyroute.LoginActivity;
import com.tdt.easyroute.Model.InfoRuta;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.SoapObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ServidorrutaFragment extends Fragment implements AsyncResponse {

    EditText et_servidor,et_time,et_imp,et_ruta;
    Button button_salir,button_guardar,button_listar,button_pruWs,button_pruImp;
    String servidor,time,com,rutaSelec_des,rutaSelec_clv;
    String nombreBase,peticion;

    ArrayList<InfoRuta> lista_rutas=null;
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

        //Mostrar valores almacenados
        et_servidor.setText(Utils.LeefConfig("servidor",getActivity().getApplication()));
        et_time.setText(Utils.LeefConfig("timeout",getActivity().getApplication()));
        et_imp.setText(Utils.LeefConfig("imp",getActivity().getApplication()));


        button_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servidor = et_servidor.getText().toString();
                time = et_time.getText().toString();
                if(!servidor.isEmpty()&&!time.isEmpty())
                {

                    Toast.makeText(getContext(), "Información guardada", Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();
                }
                else
                    Toast.makeText(getContext(), "Rellena todos los campos", Toast.LENGTH_LONG).show();
            }
        });

        button_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
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

    public void listarRutas()
    {
        crut = false;
        peticion="listarRutas";

        ParametrosWS parametrosWS = new ParametrosWS("ObtenerRutas",  getActivity().getApplicationContext());
        ConexionWS conexionWS = new ConexionWS(getContext(), getActivity(), parametrosWS);
        conexionWS.delegate = ServidorrutaFragment.this;
        conexionWS.propertyInfos = null;
        conexionWS.execute();

    }


    @Override
    public void processFinish(SoapObject output, String conexion)
    {
        //Se realizo la conexion con el ws
        if(conexion.equals("true"))
        {
            //recibio información
            if (output != null)
            {
                if (peticion.equals("listarRutas"))
                {
                    lista_rutas = ConvertirRespuesta.getRutas(output);

                    guardarRutas();
                    cargarRutas();
                }
            }
            else
            {
                Toast.makeText(getContext(), "No se encontro información", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), conexion, Toast.LENGTH_SHORT).show();
        }

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
                        InfoRuta r = lista_rutas.get(i);
                        String con = Querys.Rutas.InsRutas;

                        Log.d("salida","m:"+r.getRut_cve_n()+" "+r.getRut_desc_str()+" "+r.getRut_orden_n()+" "+r.getTrut_cve_n()+" "+r.getAsesor_cve_str()+" "+r.getGerente_cve_str()+" "+r.getSupervisor_cve_str()+" "+r.getEst_cve_str()+" "+r.getTco_cve_n()+" "+r.getRut_prev_n());

                        bd.execSQL(string.formatSql(con,r.getRut_cve_n(),r.getRut_desc_str(),r.getRut_orden_n(),r.getTrut_cve_n(),r.getAsesor_cve_str(),r.getGerente_cve_str(),r.getSupervisor_cve_str(),r.getEst_cve_str(),r.getTco_cve_n(),r.getRut_prev_n()));
                        Log.d("salida","Ruta guardada: "+i);
                    }
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

            //SE CREA SPINNER Y SU METODO ONCLICK
            spinnerDialog=new SpinnerDialog(getActivity(),rutas,"Selecciona una ruta","Cerrar");
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
