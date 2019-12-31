package com.tdt.easyroute.Fragments.Clientes;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.ParametrosWS;
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
import java.util.Calendar;
import java.util.Date;

public class BuscarclientesFragment extends Fragment implements AsyncResponseJSON {

    private static boolean buscar;
    public static BuscarclientesFragment newInstance(boolean Buscar) {
        BuscarclientesFragment fragment = new BuscarclientesFragment();
        Bundle args = new Bundle();
        buscar=Buscar;
        fragment.setArguments(args);
        return fragment;
    }

    Button button_buscar, button_borrar, button_reimp, button_act, button_salir,button_seleccionar;
    EditText et_filtro;

    Configuracion conf;

    String cli_cve_nSelec="";

    private String Day="";

    ArrayList<DataTableWS.Clientes> bsClientes = null;
    ArrayList<DataTableWS.Clientes> dgClientes = null;
    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buscarclientes, container, false);
        layoutInflater=inflater;
        vista=view;


        tableLayout = view.findViewById(R.id.tableLayout);
        button_buscar = view.findViewById(R.id.button_buscar);
        button_borrar = view.findViewById(R.id.button_borrar);
        button_reimp = view.findViewById(R.id.button_reimp);
        button_act = view.findViewById(R.id.button_actualizar);
        button_seleccionar = view.findViewById(R.id.button_seleccionar);
        button_salir = view.findViewById(R.id.button_salir);
        et_filtro = view.findViewById(R.id.et_filtro);


        button_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
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

        inicializar();


        return  view;
    }

    private void inicializar()
    {
        conf = Utils.ObtenerConf2(getActivity().getApplication());
        Day = diaActual();
        mostrarTitulo();
        cargarClientes();
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

            String json = BaseLocal.Select( consulta ,getContext());
            bsClientes = ConvertirRespuesta.getClientesJson(json);

            mostrarClientes("");

        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
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

    //evento del clic a la fila
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
                    TableRow row = (TableRow)vista.findViewWithTag(dgClientes.get(i).getCli_cve_n());
                    row.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                cli_cve_nSelec=tag;

            }

        }
    };

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
                Toast.makeText(getContext(), "No ha seleccionado ningún cliente", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

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
                json = BaseLocal.Select(consulta, getContext());

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
                            Toast.makeText(getContext(), "El cliente no se visita hoy", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                Utils.RegresarInicio(getActivity());

            } else {
                Toast.makeText(getContext(), "Debe seleccionar un cliente", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Log.d( "salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void obtenerUbicacion(final String consulta)
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

                    BaseLocal.Insert( string.formatSql(consulta, ubi[0])  ,getContext());
                }
            }, 3000);

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

                ConexionWS_JSON cws = new ConexionWS_JSON(getContext(),"ActualizarClienteJ");
                cws.propertyInfos = propertyInfos;
                cws.delegate = BuscarclientesFragment.this;
                cws.execute();
            }
            else
            {
                Toast.makeText(getContext(), "Debe seleccionar un cliente", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Log.d( "salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
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

                        BaseLocal.Insert( consulta,getContext());

                        Toast.makeText(getContext(), "Cliente actualizado", Toast.LENGTH_SHORT).show();
                        Utils.RegresarInicio(getActivity());

                    }
                    catch (Exception e)
                    {
                        Log.d("salida", "Error: "+e.getMessage());
                        Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else
            {
                Toast.makeText(getContext(), "No se encontro el cliente", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), respuesta, Toast.LENGTH_LONG).show();
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
}
