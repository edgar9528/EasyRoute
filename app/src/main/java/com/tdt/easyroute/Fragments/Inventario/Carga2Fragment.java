package com.tdt.easyroute.Fragments.Inventario;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Objects;

public class Carga2Fragment extends Fragment implements AsyncResponseJSON {

    private static boolean recarga= false;
    private static boolean cautivo=false;
    Configuracion conf = null;

    Spinner sp_recargas;

    ArrayList<DataTableWS.Recargas> al_recargas;
    ArrayList<DataTableWS.RecargasDet> al_recargasDet;
    ArrayList<DataTableLC.Inventario> al_inventario;
    ArrayList<String> recargas;
    int indiceFolio=-1;

    TextView tv1,tv2,tv3,tv4,tv5;

    TableLayout tableLayout;
    View vista;
    LayoutInflater layoutInflater;

    Button button_buscar,button_aplicar,button_salir;
    String peticion,mensaje,nombreBase,cve,folio,rectxt;

    public Carga2Fragment() {
        // Required empty public constructor
    }

    public static Carga2Fragment newInstance (boolean Recarga, boolean Cautivo)
    {
        Carga2Fragment fragment = new Carga2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        recarga = Recarga;
        cautivo = Cautivo;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carga2, container, false);
        vista = view;
        layoutInflater = inflater;

        conf = Utils.ObtenerConf(getActivity().getApplication());
        nombreBase = getActivity().getString( R.string.nombreBD );

        if(recarga)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Inventario | Recarga");
            mensaje="Recargas";
        }
        else
            mensaje="Carga Inicial";

        button_buscar = view.findViewById(R.id.button_buscar);
        button_aplicar = view.findViewById(R.id.button_aplicar);
        button_salir = view.findViewById(R.id.button_salir);
        tv1 = view.findViewById(R.id.tv_rec_cve_n);
        tv2 = view.findViewById(R.id.tv_rec_folio_str);
        tv3 = view.findViewById(R.id.tv_rec_falta_dt);
        tv4 = view.findViewById(R.id.tv_usu_solicita_str);
        tv5 = view.findViewById(R.id.tv_rec_observaciones_str);
        sp_recargas = (Spinner) view.findViewById(R.id.spinnerRecargas);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);

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
                Utils.RegresarInicio(getActivity());
            }
        });

        sp_recargas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = sp_recargas.getSelectedItem().toString();
                if(!item.equals("Seleccione una carga"))
                {
                    mostrarRecarga(i-1); //menos 1 porque el primero es el mensaje
                    buscarRecargaDet(i-1);
                    indiceFolio=i-1;
                }
                else
                {
                    tv1.setText(""); tv2.setText("");
                    tv3.setText(""); tv4.setText("");
                    tv5.setText("");
                    indiceFolio=-1;
                    al_recargasDet=null;
                    mostrarRecargaDet();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inicializar();

        return view;
    }

    public void inicializar()
    {
        recargas = new ArrayList<>();
        recargas.add("Seleccione una carga");
        sp_recargas.setAdapter(new ArrayAdapter<String>( getContext(), R.layout.spinner_item, recargas));

        buscarRecargas();
    }

    public void aplicar()
    {
        if(indiceFolio!=-1)
        {
            folio = al_recargas.get(indiceFolio).getRec_folio_str();
            cve = al_recargas.get(indiceFolio).getRec_cve_n();

            if(al_recargasDet!=null&&al_recargasDet.size()>0)
            {
                String men = string.formatSql( "¿Esta seguro de aplicar la {0} con folio {1}?",mensaje,folio );

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle("Importante");
                dialogo1.setMessage(men);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        obtenerUbicacion(); //EN ESTA FUNCION SE LLAMA A aplicarCargaInicial();
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        //cancelar();
                    }
                });
                dialogo1.show();

            }
            else
                Toast.makeText(getContext(), "Por favor seleccione una carga con productos para poder continuar.", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(getContext(), "Por favor seleccione una carga para poder continuar.", Toast.LENGTH_SHORT).show();
        }

    }

    private void obtenerUbicacion()
    {
        try {

            final MainActivity mainActivity = (MainActivity) getActivity();
            final String[] ubi = new String[1];
            final ProgressDialog progress = new ProgressDialog(getContext());
            progress.setTitle("Actualizando");
            progress.setMessage("Por favor espere");
            progress.show();
            progress.setCancelable(false);

            mainActivity.enableLocationUpdates();

            ubi[0] = mainActivity.getLatLon();
            Log.d("salida", "Ubicacion anterior: " + ubi[0]);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.cancel();
                    mainActivity.disableLocationUpdates();
                    ubi[0] = mainActivity.getLatLon();
                    Log.d("salida", "Ubicacion nueva: " + ubi[0]);
                    aplicarCargaInicial(ubi[0]);
                }
            }, 3000);

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void aplicarCargaInicial(String ubicacion)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try
        {
            String con = string.formatSql("Select prod_cve_n, inv_recarga_n from inventario where rut_cve_n={0}", String.valueOf(conf.getRuta()));
            String res = BaseLocal.Select(con, getContext());
            al_inventario = ConvertirRespuesta.getInventarioJson(res);

            db.beginTransaction();

            String col= recarga ? "inv_recarga_n" : "inv_inicial_n";

            String qi = "insert into inventario(rut_cve_n,prod_cve_n," + col + ") values ({0},{1},{2})";
            //String qu = "update inventario set " + col + "=" + col + "+{0} where " + "rut_cve_n={1} and prod_cve_n={2}";
            String qu = "update inventario set " + col + "=" + col + "+{2} where " + "rut_cve_n={0} and prod_cve_n={1}";


            if (!recarga)
            {
                db.execSQL(  string.formatSql("delete from inventario where rut_cve_n={0}", String.valueOf( conf.getRuta()) ) ) ;
            }

            for(int i=0; i<al_recargasDet.size();i++)
            {
                DataTableWS.RecargasDet in = al_recargasDet.get(i);
                DataTableLC.Inventario k=null;

                for(int j=0; j<al_inventario.size();i++)
                {
                    if(al_recargasDet.get(i).getProd_cve_n().equals( al_inventario.get(i).getProd_cve_n() ))
                    {
                        k=al_inventario.get(i);
                        break;
                    }
                }

                if(k!=null && recarga )
                {
                    db.execSQL( string.formatSql( qu,  in.getProd_cant_n(), String.valueOf( conf.getRuta() )  , in.getProd_cve_n()) );

                    String suma = String.valueOf(  Float.parseFloat( k.getInv_recarga_n() )  + Float.parseFloat( in.getProd_cant_n() ) );
                    db.execSQL( string.formatSql(Querys.Inventario.InsertMovimiento,String.valueOf( conf.getRuta() ), in.getProd_cve_n(), null, conf.getUsuario(), k.getInv_recarga_n(),"0", in.getProd_cant_n(), suma,mensaje ) );
                    Log.d("salida", "Actualizar inventario");

                }
                else
                {
                    db.execSQL( string.formatSql( qi,  String.valueOf( conf.getRuta() ), in.getProd_cve_n()  , in.getProd_cant_n()  ) );
                    db.execSQL( string.formatSql(Querys.Inventario.InsertMovimiento,String.valueOf( conf.getRuta() ), in.getProd_cve_n(), null, conf.getUsuario(), "0", "0",in.getProd_cant_n(),in.getProd_cant_n(),mensaje ) );
                    Log.d("salida", "Insertar inventario");
                }

            }

            if (!recarga)
            {
                db.execSQL(string.formatSql(Querys.Inventario.DesactivaCarga));
                db.execSQL(string.formatSql(Querys.Inventario.InsertCarga, "1", conf.getUsuario(), "A", mensaje.toUpperCase()));
            }

            rectxt = recarga ? "RECARGA" : "CARGA INICIAL";

            db.execSQL(string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion,conf.getUsuario(), rectxt,string.formatSql("FOLIO: {0}",folio), String.valueOf( conf.getRuta() ) , ubicacion));

            db.setTransactionSuccessful();

            peticionProcesarRecarga();

        }catch (Exception e)
        {

            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida", "Error: "+e.getMessage() );
        }
        finally
        {
            db.endTransaction();
            db.close();
        }
    }

    public void peticionProcesarRecarga()
    {
        peticion="ProcesarRecarga";

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

        Log.d("salida","parametros: cve: "+cve+"  usu: "+conf.getUsuario());

        ConexionWS_JSON conexionWSJ = new ConexionWS_JSON(getContext(),"ProcesarRecargaJ");
        conexionWSJ.propertyInfos= propertyInfos;
        conexionWSJ.delegate = Carga2Fragment.this;
        conexionWSJ.execute();

    }

    public void procesarRecarga()
    {
        String consulta ="Select p.prod_cve_n from productos p inner join categorias c on p.cat_cve_n=c.cat_cve_n where c.cat_desc_str='ENVASE' and p.prod_cve_n not in (Select p.prod_cve_n from inventario p inner join categorias c on p.cat_cve_n=c.cat_cve_n where c.cat_desc_str='ENVASE')";
        String res= BaseLocal.Select(consulta,getContext());

        ArrayList<DataTableLC.DtEnv> dtEnvs;
        dtEnvs = ConvertirRespuesta.getDtEnvJson(res);

        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try
        {
            db.beginTransaction();

            if(dtEnvs!=null&&dtEnvs.size()>0)
            {
                for(int i=0; i<dtEnvs.size();i++)
                {
                    db.execSQL(string.formatSql(Querys.Inventario.InsertInventario2enCero, String.valueOf( conf.getRuta() ) , dtEnvs.get(i).getProd_cve_n()));
                }
            }
            Log.d("salida","inserto el proceso de recarga");

            db.setTransactionSuccessful();

            Toast.makeText(getContext(), mensaje + " completada con exito.", Toast.LENGTH_LONG).show();

            db.endTransaction();
            db.close();

            Utils.RegresarInicio(getActivity());

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("salida", "Error: "+e.getMessage() );
        }
        finally
        {
            if(db.isOpen())
            {
                db.endTransaction();
                db.close();
            }
        }

    }

    public void buscarRecargas()
    {
        peticion="Recargas";
        //parametros del metodo
        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
        PropertyInfo piUser = new PropertyInfo();
        piUser.setName("ruta");
        piUser.setValue(conf.getRuta());
        propertyInfos.add(piUser);

        PropertyInfo piPass = new PropertyInfo();
        piPass.setName("recarga");
        piPass.setValue(recarga?1:0);
        propertyInfos.add(piPass);

        Log.d("salida","ruta: "+conf.getRuta() +" recarga: "+ String.valueOf( recarga?1:0) );

        //conexion con el metodo
        ConexionWS_JSON conexionWS = new ConexionWS_JSON(getContext(), "ObtenerRecargasJ");
        conexionWS.delegate = Carga2Fragment.this;
        conexionWS.propertyInfos = propertyInfos;
        conexionWS.execute();
    }

    public void llenarSpinnerRecargas()
    {
        recargas = new ArrayList<>();
        recargas.add("Seleccione una carga");
        for(int i=0; i<al_recargas.size();i++)
            recargas.add((i+1+" - "+al_recargas.get(i).getRec_folio_str()));

        sp_recargas.setAdapter(new ArrayAdapter<String>( getContext(), R.layout.spinner_item, recargas));

    }

    public void mostrarRecarga(int indiceRecarga)
    {
        tv1.setText(al_recargas.get(indiceRecarga).getRec_cve_n());
        tv2.setText(al_recargas.get(indiceRecarga).getRec_folio_str());
        String fecha = Utils.FechaWS( al_recargas.get(indiceRecarga).getRec_falta_dt() );
        tv3.setText(fecha);
        tv4.setText(al_recargas.get(indiceRecarga).getUsu_solicita_str());
        tv5.setText(al_recargas.get(indiceRecarga).getRec_observaciones_str());
    }

    public void buscarRecargaDet(int indiceRecarga)
    {
        //parametros del metodo
        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();

        PropertyInfo piPass = new PropertyInfo();
        piPass.setName("recarga");
        piPass.setValue( al_recargas.get(indiceRecarga).getRec_cve_n() );
        propertyInfos.add(piPass);

        peticion="RecargasDet";

        //conexion con el metodo
        ConexionWS_JSON conexionWS = new ConexionWS_JSON(getContext(), "ObtenerRecargaDetJ");
        conexionWS.delegate = Carga2Fragment.this;
        conexionWS.propertyInfos = propertyInfos;
        conexionWS.execute();
    }

    public void mostrarRecargaDet()
    {
        tableLayout.removeAllViews();
        TableRow tr;
        DataTableWS.RecargasDet rd;

        if(al_recargasDet!=null)
            for(int i=0; i<al_recargasDet.size();i++)
            {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_recargasdet, null);
                rd=al_recargasDet.get(i);

                ((TextView) tr.findViewById(R.id.t_prod_sku_str)).setText(rd.getProd_sku_str());
                ((TextView) tr.findViewById(R.id.t_prod_desc_str)).setText(rd.getProd_desc_str());
                ((TextView) tr.findViewById(R.id.t_prod_cant_n)).setText(rd.getProd_cant_n());

                tableLayout.addView(tr);
            }
    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

        if(estado)
        {
            //recibio información
            if (respuesta != null)
            {
                if(peticion.equals("Recargas"))
                {
                    al_recargas = ConvertirRespuesta.getRecargasJson(respuesta);

                    if(al_recargas.size()>0)
                    {
                        llenarSpinnerRecargas();
                    }
                }
                else
                if(peticion.equals("RecargasDet"))
                {
                    al_recargasDet = ConvertirRespuesta.getRecargasDetJson(respuesta);
                    if(al_recargasDet.size()>0)
                    {
                        mostrarRecargaDet();
                    }
                }
                else
                if(peticion.equals("ProcesarRecarga"))
                {
                    DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                    if(retVal!=null)
                    {
                        if(retVal.getRet().equals("true"))
                        {
                            procesarRecarga();
                        }
                        else
                        {
                            Toast.makeText(getContext(), retVal.getMsj(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                        Log.d("salida", "retval nulo");
                    }
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
}
