package com.tdt.easyroute.Fragments.Inventario;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class DevolucionesFragment extends Fragment implements AsyncResponseJSON {

    private static Usuario user;
    private String msj,peticion,folio,nombreBase;
    private Configuracion conf;
    private int id_devSeleccionada;
    private long cve = 0;

    private Button button_salir,button_buscar,button_aplicar;
    private TextView tv1,tv2,tv3,tv4,tv5;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private Spinner sp_devoluciones;

    private ArrayList<DataTableWS.Devoluciones> dgDevolucion=null;
    private ArrayList<DataTableWS.DevolucionesDet> dgDevolucionDet=null;

    private MainActivity mainActivity;

    public DevolucionesFragment() {
        // Required empty public constructor
    }

    public static DevolucionesFragment newInstance(Usuario usu) {
        DevolucionesFragment fragment = new DevolucionesFragment();
        Bundle args = new Bundle();
        user=usu;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devoluciones, container, false);
        layoutInflater = inflater;

        mainActivity = (MainActivity) getActivity();

        nombreBase = getActivity().getString( R.string.nombreBD );

        button_salir = view.findViewById(R.id.button_salir);
        button_buscar = view.findViewById(R.id.button_buscar);
        button_aplicar = view.findViewById(R.id.button_aplicar);

        tv1 = view.findViewById(R.id.tv_dev_cve_n);
        tv2 = view.findViewById(R.id.tv_dev_folio_str);
        tv3 = view.findViewById(R.id.tv_dev_fecha_dt);
        tv4 = view.findViewById(R.id.tv_usu_solicita_str);
        tv5 = view.findViewById(R.id.tv_dev_observaciones_str);

        tableLayout = view.findViewById(R.id.tableLayout);

        sp_devoluciones =  view.findViewById(R.id.spinnerDevoluciones);

        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peticionDevoluciones();
            }
        });

        button_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });

        button_aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarDevolucion();
            }
        });

        sp_devoluciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0) //0 es el primero, solo muestra el texto "selecciona una opcion"
                {
                    id_devSeleccionada=i-1;
                    mostrarDevolucion(i-1); //menos 1 porque el primero es el mensaje
                    peticionDevolucionDet(i-1);
                }
                else
                {
                    AsignaVacio();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inicializar();


        return view;
    }

    private void inicializar()
    {
        conf= Utils.ObtenerConf(getActivity().getApplication());
        msj = "Devolución";
        AsignaVacio();
        llenarSpinnerDevoluciones();

        peticionDevoluciones();
    }

    private void AsignaVacio()
    {
        id_devSeleccionada=-1;
        dgDevolucionDet=null;

        mostrarTitulo();

        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        tv4.setText("");
        tv5.setText("");
    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_devolucionesdet, null);

        ((TextView) tr.findViewById(R.id.t_dev_cve_n)).setTypeface( ((TextView) tr.findViewById(R.id.t_dev_cve_n)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_prod_cve_n)).setTypeface( ((TextView) tr.findViewById(R.id.t_prod_cve_n)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_prod_sku_str)).setTypeface( ((TextView) tr.findViewById(R.id.t_prod_sku_str)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_prod_desc_str)).setTypeface( ((TextView) tr.findViewById(R.id.t_prod_desc_str)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_prod_cant_n)).setTypeface( ((TextView) tr.findViewById(R.id.t_prod_cant_n)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);
    }

    private void peticionDevoluciones()
    {
        peticion="Devoluciones";

        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
        PropertyInfo pi = new PropertyInfo();
        pi.setName("ruta");
        pi.setValue(conf.getRuta());
        propertyInfos.add(pi);

        ConexionWS_JSON cws = new ConexionWS_JSON(getContext(),"ObtenerDevolucionesJ");
        cws.propertyInfos = propertyInfos;
        cws.delegate = DevolucionesFragment.this;
        cws.execute();
    }

    private void mostrarDevolucion(int id_dev)
    {
        tv1.setText(dgDevolucion.get(id_dev).getDev_cve_n());
        tv2.setText(dgDevolucion.get(id_dev).getDev_folio_str());
        String fecha = Utils.FechaWS( dgDevolucion.get(id_dev).getDev_fecha_dt() );
        tv3.setText(fecha);
        tv4.setText(dgDevolucion.get(id_dev).getUsu_cve_str());
        tv5.setText(dgDevolucion.get(id_dev).getDev_observaciones_str());
    }

    private void peticionDevolucionDet(int id_dev)
    {
        peticion="DevolucionesDet";

        String id = dgDevolucion.get(id_dev).getDev_cve_n();

        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
        PropertyInfo pi = new PropertyInfo();
        pi.setName("devolucion");
        pi.setValue(id);
        propertyInfos.add(pi);

        ConexionWS_JSON cws = new ConexionWS_JSON(getContext(),"ObtenerDevolucionDetJ");
        cws.propertyInfos = propertyInfos;
        cws.delegate = DevolucionesFragment.this;
        cws.execute();
    }

    private void mostrarDevolucionDet()
    {
        mostrarTitulo();
        TableRow tr;
        DataTableWS.DevolucionesDet d;

        if(dgDevolucionDet!=null)
        {
            for (int i = 0; i < dgDevolucionDet.size(); i++) {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_devolucionesdet, null);
                d = dgDevolucionDet.get(i);

                ((TextView) tr.findViewById(R.id.t_dev_cve_n)).setText(d.getDev_cve_n());
                ((TextView) tr.findViewById(R.id.t_prod_cve_n)).setText(d.getProd_cve_n());
                ((TextView) tr.findViewById(R.id.t_prod_sku_str)).setText(d.getProd_sku_str());
                ((TextView) tr.findViewById(R.id.t_prod_desc_str)).setText(d.getProd_desc_str());
                ((TextView) tr.findViewById(R.id.t_prod_cant_n)).setText(d.getProd_cant_n());

                tableLayout.addView(tr);
            }
        }
        else
        {
            Toast.makeText(getContext(), "No hay detalles de la devolución", Toast.LENGTH_SHORT).show();
            mostrarTitulo();
        }

    }

    private void llenarSpinnerDevoluciones()
    {
        ArrayList<String> lista_devoluciones;
        String spTitulo = getString(R.string.sp_carga) +" "+ getString(R.string.mHijo_devolucion).toLowerCase();
        if( dgDevolucion!=null)
        {
            lista_devoluciones = new ArrayList<>();
            lista_devoluciones.add(spTitulo);
            for(int i=0; i<dgDevolucion.size();i++)
                lista_devoluciones.add((i+1+" - "+dgDevolucion.get(i).getDev_cve_n()));

            sp_devoluciones.setAdapter(new ArrayAdapter<>( getContext(), R.layout.spinner_item, lista_devoluciones));
        }
        else
        {
            lista_devoluciones = new ArrayList<>();
            lista_devoluciones.add(spTitulo);
            sp_devoluciones.setAdapter(new ArrayAdapter<>( getContext(), R.layout.spinner_item, lista_devoluciones));
        }

    }

    private void verificarDevolucion()
    {
        if(id_devSeleccionada==-1)
        {
            Toast.makeText(getContext(), "Por favor seleccione una carga para poder continuar.", Toast.LENGTH_LONG).show();
        }
        else
        {
            if (dgDevolucionDet == null)
            {
                Toast.makeText(getContext(), "Por favor seleccione una carga con productos para poder continuar.", Toast.LENGTH_LONG).show();
            }
            else
            {
                folio =  dgDevolucion.get(id_devSeleccionada).getDev_folio_str();
                cve =  Long.parseLong( dgDevolucion.get(id_devSeleccionada).getDev_cve_n() );

                String men = string.formatSql( getString(R.string.msg_aplicarCarga),getString(R.string.mHijo_devolucion).toLowerCase(),folio );

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle(getString(R.string.msg_importante));
                dialogo1.setMessage(string.formatSql(men));
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        peticionDevolucion();
                    }
                });
                dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        //cancelar();
                    }
                });
                dialogo1.show();
            }
        }
    }

    private void peticionDevolucion()
    {
        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
        PropertyInfo pi1 = new PropertyInfo();
        pi1.setName("devolucion");
        pi1.setValue(cve);
        propertyInfos.add(pi1);

        PropertyInfo pi2 = new PropertyInfo();
        pi2.setName("usu_aplica_str");
        pi2.setValue(conf.getUsuario().toUpperCase());
        propertyInfos.add(pi2);

        peticion="ProcesarDevolucion";
        ConexionWS_JSON cws = new ConexionWS_JSON(getContext(),"ProcesarDevolucionJ");
        cws.propertyInfos = propertyInfos;
        cws.delegate = DevolucionesFragment.this;
        cws.execute();

    }

    private void aplicarDevolucion(DataTableWS.RetValInicioDia ret)
    {
        if(ret!=null)
        {
            if(ret.getRet().equals("true"))
            {

                ArrayList<DataTableLC.Inventario> di;

                String json = BaseLocal.Select(string.formatSql("Select prod_cve_n, inv_devuelto_n from inventario where rut_cve_n={0}", String.valueOf(conf.getRuta()) ),getContext());
                di = ConvertirRespuesta.getInventarioJson(json);

                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                try
                {
                    db.beginTransaction();

                    String col = "inv_devuelto_n";
                    String qi = "insert into inventario(rut_cve_n,prod_cve_n," + col + ") values ({0},{1},{2})";
                    String qu = "update inventario set " + col + "=" + col + "+{2} where rut_cve_n={0} and prod_cve_n={1}";

                    Log.d("salida","Entro al for");
                    for(int j=0; j< dgDevolucionDet.size();j++)
                    {
                        DataTableWS.DevolucionesDet i = dgDevolucionDet.get(j);
                        DataTableLC.Inventario k=null;
                        for(int l=0; l<di.size();l++)
                        {
                            if(di.get(l).getProd_cve_n().equals(i.getProd_cve_n()))
                            {
                                k=di.get(l); break;
                            }
                        }

                        db.execSQL(string.formatSql(qu,i.getProd_cant_n(), String.valueOf(conf.getRuta()) , i.getProd_cve_n()  ));
                        String con = string.formatSql(Querys.Inventario.InsertMovimiento,
                                String.valueOf(conf.getRuta()),i.getProd_cve_n(),"null",conf.getUsuario(),k.getInv_devuelto_n(),
                                "0",i.getProd_cant_n(), String.valueOf( Float.parseFloat(k.getInv_devuelto_n()) + Float.parseFloat(i.getProd_cant_n()) )  );

                    }
                    Log.d("salida","Salio del for");


                    db.setTransactionSuccessful();


                }catch (Exception e)
                {
                    if(db.isOpen())
                        db.close();

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

                String js= BaseLocal.Select( "Select p.prod_cve_n from productos p inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                        "where c.cat_desc_str='ENVASE' and p.prod_cve_n not in " +
                        "(Select p.prod_cve_n from inventario p inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                        "where c.cat_desc_str='ENVASE')" ,getContext());

                ArrayList<DataTableWS.Productos> dtEnv = ConvertirRespuesta.getProductosJson(js);

                if(dtEnv!=null)
                {
                    String consulta;
                    for(int i=0; i<dtEnv.size();i++)
                    {
                        DataTableWS.Productos ri = dtEnv.get(i);
                        consulta= string.formatSql(Querys.Inventario.InsertInventario2enCero, String.valueOf(conf.getRuta()) , ri.getProd_cve_n() );
                        BaseLocal.Insert(consulta,getContext());
                    }
                }

                Toast.makeText(getContext(), msj + " completada con exito.", Toast.LENGTH_SHORT).show();

                peticionDevoluciones();

            }
            else
                Toast.makeText(getContext(), "Error: "+ret.getMsj(), Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext(), "Error al procesar la devolución.", Toast.LENGTH_SHORT).show();
    }




    @Override
    public void recibirPeticion(boolean estado, String respuesta) {
        if(estado)
        {
            if (respuesta != null)
            {
                if(peticion.equals("Devoluciones"))
                {
                    dgDevolucion = ConvertirRespuesta.getDevolucionesJson(respuesta);
                    llenarSpinnerDevoluciones();
                }
                else
                if(peticion.equals("DevolucionesDet"))
                {
                    dgDevolucionDet = ConvertirRespuesta.getDevolucionesDetJson(respuesta);
                    mostrarDevolucionDet();
                }
                if(peticion.equals("ProcesarDevolucion"))
                {
                    DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);
                    aplicarDevolucion(retVal);
                }
            }
            else
            {
                Toast.makeText(getContext(), "No se encontro información", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), respuesta, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mainActivity.validarMenu();
        Log.d("salida","ENTRO ACTUALIZAR MENU");

    }
}
