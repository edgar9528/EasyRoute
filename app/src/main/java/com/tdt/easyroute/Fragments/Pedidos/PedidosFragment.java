package com.tdt.easyroute.Fragments.Pedidos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tdt.easyroute.CardViews.Adapter.PedidosAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Model.PedidosCardView;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.ParametrosWS;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Fragments.Clientes.BuscarClientesActivity;
import com.tdt.easyroute.Fragments.Pedidos.DetallesCliente.MainDetallesActivity;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class PedidosFragment extends Fragment implements AsyncResponseJSON {

    private ArrayList<DataTableLC.PedidosLv> lvClientes = null;
    private Configuracion conf;
    private View vista;
    private int tipo = 0;// 1.- no venta 2.- no lectura
    private boolean MensajeAbierto=false;
    ArrayList<String> metodosWS;

    private Toolbar toolbar;
    private String nombreBase;

    private PieChart pieChartVisitas,pieChartEfectividad;

    public PedidosFragment() {
        // Required empty public constructor
    }

    public static PedidosFragment newInstance() {
        PedidosFragment fragment = new PedidosFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.pedidos_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);

        try
        {
            toolbar = view.findViewById(R.id.toolbarPedidos);
            toolbar.inflateMenu(R.menu.pedidos_menu);

            pieChartVisitas = view.findViewById(R.id.pieChart1);
            pieChartEfectividad = view.findViewById(R.id.pieChart2);

            nombreBase = getContext().getString(R.string.nombreBD);

            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return clickItemToolbar(item);
                }
            });

            conf = Utils.ObtenerConf(getActivity().getApplication());
            vista = view;

            graficoVisitas(0, 0);
            graficoEfectividad(0, 0 );

            InicializarAsync inicializar = new InicializarAsync();
            inicializar.execute();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped1), e.getMessage());
        }

        return view;
    }

    private void CalcularEfectividad()
    {
        int visitas = 0;
        int ConVenta = 0;
        DataTableLC.PedidosLv i;

        if (lvClientes != null)
            for (int j = 0; j < lvClientes.size(); j++)
            {
                i = lvClientes.get(j);
                if (i.getIconoInt() == 1)
                {
                    ConVenta++;
                    visitas++;
                }
                else
                {
                    if (i.getIconoInt() == 2 || i.getIconoInt() == 3)
                        visitas++;
                }
            }

        try
        {
            if (lvClientes.size() > 0)
            {
                graficoVisitas(visitas, lvClientes.size());
                graficoEfectividad(ConVenta, visitas );

                //et_efectividad.setText((ConVenta / lvClientes.size()) + " %");
            }
            else
            {
                graficoVisitas(visitas, 0);
                graficoEfectividad(ConVenta, visitas );
                //et_efectividad.setText(ConVenta + "/0");
            }

        } catch (Exception e) {
            Log.d("salida", "Error: " + e);
            Utils.msgError(getContext(), getString(R.string.err_ped2),e.getMessage());
        }

    }

    private void graficoVisitas(int visCumplidas, int visTotal)
    {
        try
        {
            ArrayList<PieEntry> entries = new ArrayList<>();
            PieDataSet pieDataSet;
            PieData pieData;

            int visRestantes = visTotal-visCumplidas;

            float porcentaje;

            if(visTotal!=0)
                porcentaje= (visCumplidas*100) / visTotal;
            else
                porcentaje=0;

            entries.add(new PieEntry(visCumplidas, "Cumplidas"));
            entries.add(new PieEntry(visRestantes, "Faltantes"));

            pieDataSet = new PieDataSet(entries, "");

            pieData = new PieData(pieDataSet);

            pieDataSet.setColors( new int[] { R.color.graficaOk, R.color.graficaNo}, getContext() );

            pieChartVisitas.setData(pieData);

            Description description = new Description();
            description.setText(getString(R.string.graf_ventas));
            description.setTextColor(ColorTemplate.rgb("ffffff"));
            description.setTextSize(18);

            pieChartVisitas.setCenterText(porcentaje + "%");
            pieChartVisitas.setCenterTextSize(18);
            pieChartVisitas.setCenterTextColor(ColorTemplate.rgb("ffffff"));

            pieChartVisitas.getLegend().setEnabled(false);
            pieChartVisitas.setDrawEntryLabels(false);
            pieChartVisitas.setDescription(description);
            pieChartVisitas.setHoleColor(ColorTemplate.COLOR_NONE);
            pieChartVisitas.animateY(500);
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_graficas), e.getMessage());
        }
    }

    private void graficoEfectividad(int conVenta, int total)
    {
        try
        {
            ArrayList<PieEntry> entries = new ArrayList<>();
            PieDataSet pieDataSet;
            PieData pieData;

            int sinVenta= total-conVenta;

            float porcentaje;
            if(total!=0)
                 porcentaje= (conVenta*100) / total;
            else
                porcentaje=0;

            entries.add(new PieEntry(conVenta, "Con venta"));
            entries.add(new PieEntry(sinVenta, "Sin venta"));

            pieDataSet = new PieDataSet(entries, "");

            pieData = new PieData(pieDataSet);

            pieDataSet.setColors( new int[] { R.color.graficaOk, R.color.graficaNo}, getContext() );

            pieChartEfectividad.setData(pieData);

            Description description = new Description();
            description.setText(getString(R.string.graf_efectividad));
            description.setTextColor(ColorTemplate.rgb("ffffff"));
            description.setTextSize(18);

            pieChartEfectividad.setCenterText(porcentaje + "%");
            pieChartEfectividad.setCenterTextSize(18);
            pieChartEfectividad.setCenterTextColor(ColorTemplate.rgb("ffffff"));

            pieChartEfectividad.getLegend().setEnabled(false);
            pieChartEfectividad.setDrawEntryLabels(false);
            pieChartEfectividad.setDescription(description);
            pieChartEfectividad.setHoleColor(ColorTemplate.COLOR_NONE);
            pieChartEfectividad.animateY(500);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_graficas), e.getMessage());
        }
    }

    private void imprimir()
    {

    }

    private boolean clickItemToolbar(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_imprimir:
                imprimir();
                return true;
            case R.id.action_buscar:
                Intent intent = new Intent(getContext(), BuscarClientesActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.action_actualizar:
                actualizaClientes();
                return true;
            case R.id.action_salir:
                Utils.RegresarInicio(getActivity());
                return true;
            default:
                return false;
        }
    }

    private class InicializarAsync extends AsyncTask<Boolean,Integer,Boolean>
    {

        public InicializarAsync() {
        }

        private String mensaje="";

        private ProgressDialog progreso;

        @Override protected void onPreExecute() {
            progreso = new ProgressDialog(getContext());
            progreso.setMessage(getString(R.string.msg_cargando));
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {

            try
            {
                listarClientes(true);
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
                mostrarClientes();
                CalcularEfectividad();
            }
            else
            {
                Utils.msgError(getContext(), "Error", mensaje);
            }
        }
    }

    private void listarClientes(boolean dia)
    {
        try
        {
            String filtro = "";
            String qry = "";
            String qryc = "";

            if (conf.getPreventa() == 2)
                qryc = "or cli_cve_n in (select cli_cve_n from visitapreventa)";

            String[] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int numeroDia = cal.get(Calendar.DAY_OF_WEEK) - 1;

            String hoy = dias[numeroDia];

            if (conf.getPreventa() == 1) {
                if (dias[numeroDia].equals("Sábado")) {
                    hoy = "Lunes";
                } else
                    hoy = dias[numeroDia + 1];
            }

            if (dia) {
                switch (hoy) {
                    case "Lunes":
                        filtro = "cli_lun_n";
                        break;
                    case "Martes":
                        filtro = "cli_mar_n";
                        break;
                    case "Miércoles":
                        filtro = "cli_mie_n";
                        break;
                    case "Jueves":
                        filtro = "cli_jue_n";
                        break;
                    case "Viernes":
                        filtro = "cli_vie_n";
                        break;
                    case "Sábado":
                        filtro = "cli_sab_n";
                        break;
                    case "Domingo":
                        filtro = "cli_dom_n";
                        break;
                }

                if (conf.getPreventa() == 1)
                    qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str," +
                            "cli_especial_n,cli_prospecto_n,est_cve_str from clientes where {0}>0 and rut_cve_n in " +
                            "(select rut_cve_n from rutas where rut_prev_n={1}) and est_cve_str<>'B' order by {0}", filtro, conf.getRutaStr(), filtro);
                else
                    qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str," +
                            "cli_especial_n,cli_prospecto_n,est_cve_str from clientes where {0}>0 and rut_cve_n={1} and " +
                            "est_cve_str<>'B' {2} order by {0}", filtro, conf.getRutaStr(), qryc, filtro);
            } else {
                if (conf.getPreventa() == 1)
                    qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str," +
                            "cli_especial_n,cli_prospecto_n,est_cve_str from clientes where rut_cve_n in " +
                            "(select rut_cve_n from rutas where rut_prev_n={1}) est_cve_str<>'B' order by {0}", conf.getRutaStr(), conf.getRutaStr());
                else
                    qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str," +
                            "cli_especial_n,cli_prospecto_n,est_cve_str from clientes where rut_cve_n={0} and " +
                            "est_cve_str<>'B' order by {0}", conf.getRutaStr(), conf.getRutaStr());
            }


            try {
                BaseLocal.Select("select pag_especie_n from pagos", getContext());
            } catch (Exception ex) {
                BaseLocal.Insert("alter table pagos add pag_especie_n bit not null default 0", getContext());
            }

            try {
                BaseLocal.Select("select vdet_kit_n from ventasdet", getContext());
            } catch (Exception ex) {
                BaseLocal.Insert("alter table ventasdet add vdet_kit_n bit not null default 0", getContext());
            }

            ArrayList<DataTableLC.PedidosClientes> dt = null;
            String json = BaseLocal.Select(qry, getContext());
            dt = ConvertirRespuesta.getPedidosClientesJson(json);

            if (dia) {
                cargarClientes(dt);
            }
        } catch (Exception e) {
            Log.d("salida", "Error: " + e.toString());
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

    private void cargarClientes(ArrayList<DataTableLC.PedidosClientes> dt)
    {
        try
        {
            ArrayList<DataTableLC.PedidosVisitas> dtCli;
            ArrayList<DataTableLC.PedidosVisitas> dtCli2;
            ArrayList<DataTableLC.PedidosVisPre> dtVisPre;

            int l = 0;
            String cveext = "";

            String json = BaseLocal.Select("Select * from visitapreventa", getContext());
            dtVisPre = ConvertirRespuesta.getPedidosVisPreJson(json);

            if (dt != null) {
                lvClientes = null;
                lvClientes = new ArrayList<>();

                String icono, cli_cve_n, cli_est;
                String cli_cveext_n, cli_nombre, cli_especial_n;

                DataTableLC.PedidosClientes r;
                for (int i = 0; i < dt.size(); i++) {
                    r = dt.get(i);
                    l = r.getCli_cveext_str().length() - 6;

                    //if(l>=0)
                    //{
                    cli_cve_n = r.getCli_cve_n();
                    cli_especial_n = r.getCli_especial_n();

                    if (r.getCli_prospecto_n() == null || r.getCli_prospecto_n().isEmpty())
                        cli_est = "P";
                    else
                        cli_est = r.getCli_cveext_str().substring(0, 1);

                    cli_cveext_n = String.valueOf(Long.parseLong(r.getCli_cveext_str().replace("PR", "").replace("C", "").replace("P", "").replace("V", "")));

                    if (r.getCli_nombrenegocio_str() == null || r.getCli_nombrenegocio_str().isEmpty())
                        cli_nombre = r.getCli_razonsocial_str();
                    else
                        cli_nombre = r.getCli_nombrenegocio_str();

                    //}

                    icono = "0";

                    if (r.getEst_cve_str().equals("I"))
                        icono = "4";

                    dtCli = null;
                    json = BaseLocal.Select(string.formatSql("select * from visitas where cli_cve_n={0} and " +
                            "(upper(vis_operacion_str)='CON VENTA' or upper(vis_operacion_str)='CON PREVENTA') order by vis_fecha_dt desc", r.getCli_cve_n()), getContext());
                    dtCli = ConvertirRespuesta.getPedidosVisitasJson(json);

                    DataTableLC.PedidosVisPre vp = null;
                    if (dtVisPre != null)
                        for (int j = 0; j < dtVisPre.size(); j++) {
                            if (r.getCli_cve_n().equals(dtVisPre.get(j).getCli_cve_n())) {
                                vp = dtVisPre.get(j);
                            }
                        }

                    if (vp != null && conf.getPreventa() == 2) {
                        icono = "5";
                    }

                    if (dtCli != null) {

                        icono = "1";
                    } else {
                        dtCli2 = null;
                        json = BaseLocal.Select(string.formatSql("select * from visitas where cli_cve_n={0} and vis_operacion_str<>'CON VENTA' order by vis_fecha_dt desc", r.getCli_cve_n()), getContext());
                        dtCli2 = ConvertirRespuesta.getPedidosVisitasJson(json);

                        if (dtCli2 != null) {
                            String op = dtCli2.get(0).getVis_operacion_str();
                            if (op.equals("NO VENTA"))
                                icono = "3";
                            if (op.equals("CON VENTA") || op.equals("CON PREVENTA"))
                                icono = "1";
                            if (op.equals("SIN VENTA") || op.equals("CON COBRANZA"))
                                icono = "2";
                        }
                    }

                    lvClientes.add(new DataTableLC.PedidosLv(icono, cli_cve_n, cli_est, cli_cveext_n, cli_nombre, cli_especial_n));
                }
            }

        } catch (Exception e) {
            Log.d("salida", "Error: " + e.toString());
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

    private void mostrarClientes()
    {
        try {

            Drawable[] iconos = new Drawable[6];
            iconos[0] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
            iconos[1] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
            iconos[2] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
            iconos[3] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
            iconos[4] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
            iconos[5] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);

            if (lvClientes != null) {

                RecyclerView pedidosRecyclerView = vista.findViewById(R.id.pedidosRecycler);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                pedidosRecyclerView.setLayoutManager(linearLayoutManager);
                ArrayList<PedidosCardView> pedidosCardViews = new ArrayList<>();

                String clave, nombre;
                for (int i = 0; i < lvClientes.size(); i++) {

                    clave = lvClientes.get(i).getCli_est() + " | " + lvClientes.get(i).getCli_cveext_n();
                    nombre = lvClientes.get(i).getCli_nombre();
                    if (nombre.length() > 20)
                        nombre = nombre.substring(0, 20) + "...";

                    pedidosCardViews.add(new PedidosCardView(clave, nombre, lvClientes.get(i)));
                }

                PedidosAdapterRecyclerView pedidosAdapterRecyclerView = new PedidosAdapterRecyclerView(pedidosCardViews, R.layout.cardview_pedidos, this);
                pedidosRecyclerView.setAdapter(pedidosAdapterRecyclerView);
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_mostrarInfo), e.getMessage());
        }

    }

    public void actualizaCliente(String cliente)
    {
        try
        {
                ArrayList<PropertyInfo> propertyInfos  =new ArrayList<>();

                PropertyInfo pi = new PropertyInfo();
                pi.setName("IdCliente");
                pi.setValue(cliente);
                propertyInfos.add(pi);

                ConexionWS_JSON cws = new ConexionWS_JSON(getContext(),"ActualizarClienteJ");
                cws.propertyInfos = propertyInfos;
                cws.delegate = PedidosFragment.this;
                cws.execute();

        }catch (Exception e)
        {
            Log.d( "salida","Error: "+e.getMessage());
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

    public void abrirDetalles(String cliente)
    {
        try {

            int indice=-1;
            for(int i=0; i<lvClientes.size();i++)
            {
                if(cliente.equals( lvClientes.get(i).getCli_cve_n()  ))
                {
                    indice=i;
                    i=lvClientes.size();
                }
            }

            DataTableLC.DtCliVenta rc = getDetcli(lvClientes.get(indice).getCli_cve_n());

            Intent intent = new Intent(getContext(), MainDetallesActivity.class);
            intent.putExtra("cliente",rc);
            startActivity(intent);
        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

    public void noVisita(DataTableLC.PedidosLv item)
    {
        try {
            if (item.getIconoInt() == 1) {
                Toast.makeText(getContext(), getString(R.string.tt_ped1), Toast.LENGTH_LONG).show();
                return;
            } else if (item.getIconoInt() == 2) {
                Toast.makeText(getContext(), getString(R.string.tt_ped2), Toast.LENGTH_LONG).show();
                return;
            } else if (item.getIconoInt() == 3) {
                Toast.makeText(getContext(), getString(R.string.tt_ped3), Toast.LENGTH_LONG).show();
                return;
            }
            tipo = 1;
            mostrarMsj(item);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_almacenar), e.getMessage());
        }
    }

    private void mostrarMsj(DataTableLC.PedidosLv item)
    {
        if (MensajeAbierto)
            return;

        DataTableLC.DtCliVenta rc = getDetcli(item.getCli_cve_n());

        String men="",q="";
        if(tipo==1)
        {
            men = getString(R.string.msg_noVenta);
            q = string.formatSql("select mnv_cve_n mov_cve_n,mnv_desc_str mov_desc_str from motivosnoventa where " +
                    "(mnv_desc_str='PROSPECTO' and {0}=1) or (mnv_desc_str<>'PROSPECTO' and {0}=0)", rc.getCli_prospecto_n(),rc.getCli_prospecto_n() );
        }
        else
        if(tipo==2)
        {
            men = getString(R.string.msg_noLectura);
            q = "select mnl_cve_n mov_cve_n,mnl_desc_str mov_desc_str from motivosnolectura";
        }

        String json = BaseLocal.Select(q,getContext());
        ArrayList<DataTableLC.Motivos> al_motivos = ConvertirRespuesta.getMotivosJson(json);

        final String[] motivos = new String[al_motivos.size()];
        for(int i=0; i<al_motivos.size();i++)
            motivos[i] = al_motivos.get(i).getMov_desc_str();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(men);
        builder.setCancelable(false);

        builder.setItems(motivos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int indice) {

            }
        });
        builder.setNegativeButton(getString(R.string.bt_cancelar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int indice) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private DataTableLC.DtCliVenta getDetcli(String cliente)
    {
        String json;
        json=BaseLocal.Select( string.formatSql(Querys.ClientesVentaMes.SelClienteConVtaMes,cliente),getContext() );
        ArrayList<DataTableLC.DtCliVenta> cliVenta = ConvertirRespuesta.getDtCliVentaJson(json);
        DataTableLC.DtCliVenta cli=null;

        if(cliVenta!=null)
            cli = cliVenta.get(0);

        return cli;
    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta)
    {

        try {

            if (estado) {
                if (respuesta != null) {
                    ArrayList<DataTableWS.Clientes> clientes;
                    clientes = ConvertirRespuesta.getClientesJson(respuesta);

                    if (clientes != null && clientes.size() > 0) {
                        DataTableWS.Clientes r = clientes.get(0);

                        try {

                            String consulta = string.formatSql(Querys.Clientes.UpClientes4, r.getCli_cveext_str(), getBool(r.getCli_padre_n()),
                                    r.getCli_cvepadre_n(), r.getCli_razonsocial_str(), r.getCli_rfc_Str(), getBool(r.getCli_reqfact_n()), r.getCli_nombrenegocio_str(),
                                    r.getCli_nom_str(), r.getCli_app_str(), r.getCli_apm_str(), r.getCli_fnac_dt(), r.getCli_genero_str(), r.getLpre_cve_n(),
                                    r.getNota_cve_n(), r.getFpag_cve_n(), getBool(r.getCli_consigna_n()), getBool(r.getCli_credito_n()), r.getCli_montocredito_n(),
                                    r.getCli_plazocredito_n(), r.getCli_credenvases_n(), r.getCli_estcredito_str(), getBool(r.getCli_fba_n()),
                                    r.getCli_porcentajefba_n(), r.getRut_cve_n(), r.getNvc_cve_n(), r.getGiro_cve_n(), r.getCli_email_str(),
                                    r.getCli_dirfact_n(), r.getCli_dirent_n(), r.getCli_Tel1_str(), r.getCli_tel2_str(), r.getEmp_cve_n(),
                                    r.getCli_coordenadaini_str(), r.getEst_cve_str(), r.getTcli_cve_n(), r.getCli_lun_n(),
                                    r.getCli_mar_n(), r.getCli_mie_n(), r.getCli_jue_n(), r.getCli_vie_n(), r.getCli_sab_n(), r.getCli_dom_n(),
                                    r.getFrec_cve_n(), getBool(r.getCli_especial_n()), getBool(r.getCli_esvallejo_n()), r.getNpro_cve_n(), r.getCli_huixdesc_n(),
                                    getBool(r.getCli_eshuix_n()), getBool(r.getCli_prospecto_n()), getBool(r.getCli_invalidafrecuencia_n()), getBool(r.getCli_invalidagps_n()),
                                    getBool(r.getCli_dobleventa_n()), getBool(r.getCli_comodato_n()), r.getSeg_cve_n(), getBool(r.getCli_dispersion_n()),
                                    r.getCli_dispersioncant_n(), r.getCli_limitemes_n(), r.getCli_cve_n());

                            BaseLocal.Insert(consulta, getContext());

                            Toast.makeText(getContext(), getString(R.string.tt_infoActu), Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Log.d("salida", "Error: " + e.getMessage());
                            Utils.msgError(getContext(), getString(R.string.error_almacenar), e.getMessage());
                        }
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.tt_noInformacion), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), respuesta, Toast.LENGTH_LONG).show();
            }

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_peticion), e.getMessage());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (resultCode == Activity.RESULT_OK) {
            try {
                //String editar = (String) data.getExtras().getSerializable("CamposEditar");
                String editar = (String) data.getStringExtra("clave");
                Toast.makeText(getContext(), "Cliente a editar: " + editar, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.d("salida", e.getMessage());
                Utils.msgError(getContext(), getString(R.string.error_buscarInfo), e.getMessage());
            }
        }

    }

    private void actualizaClientes()
    {
        try {
            metodosWS = null;
            metodosWS = new ArrayList<>();

            if (conf.getPreventa() == 1) {
                metodosWS.add("Obtener" + "ClientesPreventa" + "J");
                metodosWS.add("Obtener" + "CreditosPreventa" + "J");
                metodosWS.add("Obtener" + "DireccionesPreventa" + "J");
            } else {
                metodosWS.add("ObtenerClientesJ");
                metodosWS.add("ObtenerCreditosJ");
                metodosWS.add("ObtenerDireccionesJ");
            }

            ConexionWS conexionWS = new ConexionWS(getContext());
            conexionWS.execute();
        } catch (Exception e) {
            Log.d("salida", "Error al actualizar: " + e.getMessage());
            Toast.makeText(getContext(), getString(R.string.error_cargarInfo), Toast.LENGTH_SHORT).show();
        }

    }

    private class ConexionWS extends AsyncTask<String,Integer,Boolean>
    {

        private Context context;
        private ParametrosWS parametrosWS;
        private String resultado;
        boolean almacenado=false;

        //region Lista de arrayList para descargar

        ArrayList<DataTableWS.Clientes> al_clientes=null;
        ArrayList<DataTableWS.Creditos> al_creditos=null;
        ArrayList<DataTableWS.Direcciones> al_direcciones=null;

        //endregion

        public ConexionWS(Context context) {
            this.context = context;
        }

        private ProgressDialog progreso;

        @Override protected void onPreExecute() {
            progreso = new ProgressDialog(context);
            progreso.setMessage(getString(R.string.msg_webservice));
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            boolean result = false;
            resultado=null;

            try
            {
                for(int i=0; i<metodosWS.size();i++)
                {
                    parametrosWS = new ParametrosWS(metodosWS.get(i), getActivity().getApplicationContext());

                    //Metodo al que se accede
                    SoapObject Solicitud = new SoapObject(parametrosWS.getNAMESPACES(), parametrosWS.getMETODO());

                    PropertyInfo piCliente = new PropertyInfo();
                    piCliente.setName("ruta");
                    piCliente.setValue(conf.getRuta());
                    Solicitud.addProperty(piCliente);

                    Log.d("salida","RUTA BUSCADA EN WS: "+conf.getRuta());

                    //CONFIGURACION DE LA PETICION
                    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    Envoltorio.dotNet = true;
                    Envoltorio.setOutputSoapObject(Solicitud);
                    HttpTransportSE TransporteHttp = new HttpTransportSE(parametrosWS.getURL(), parametrosWS.getTIMEOUT());

                    //SE REALIZA LA PETICIÓN
                    try {
                        TransporteHttp.call(parametrosWS.getSOAP_ACTION(), Envoltorio);
                        SoapPrimitive response = (SoapPrimitive) Envoltorio.getResponse();

                        if (response != null && !response.toString().equals("null"))
                        {
                            obtenerResultado(response.toString(), parametrosWS.getMETODO());
                        }

                        result = true;

                    } catch (Exception e) {
                        Log.d("salida", "error: " + e.getMessage());
                        result = false;
                        resultado = e.getMessage();
                        i = metodosWS.size(); //terminar ciclo
                    }

                }

            }catch (Exception e)
            {
                Log.d("salida","error: "+e.getMessage());
                result=false;
                resultado= e.getMessage();
            }

            if(result)
            {
                almacenarLocal();
            }

            return result;
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
                if(almacenado)
                {
                    Toast.makeText(context,  getString(R.string.tt_infoActu), Toast.LENGTH_LONG).show();
                    Utils.RegresarInicio(getActivity());
                }
                else
                {
                    Toast.makeText(context, getString(R.string.error_almacenar)+" "+ resultado, Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(context, getString(R.string.error_almacenar)+" "+ resultado, Toast.LENGTH_LONG).show();
            }
        }

        private void obtenerResultado(String json, String metodo)
        {
            switch (metodo)
            {
                case "ObtenerClientesJ":
                case "ObtenerClientesPreventaJ":
                    al_clientes = ConvertirRespuesta.getClientesJson(json);
                    break;
                case "ObtenerCreditosJ":
                case "ObtenerCreditosPreventaJ":
                    al_creditos = ConvertirRespuesta.getCreditosWSJson(json);
                    break;
                case "ObtenerDireccionesJ":
                case "ObtenerDireccionesPreventaJ":
                    al_direcciones = ConvertirRespuesta.getDireccionesJson(json);
                    break;
            }

            Log.d("salida","Descargo de ws: "+metodo);

        }

        private void almacenarLocal()
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            String consulta;

            try
            {
                db.beginTransaction();

                if(al_clientes!=null)
                {
                    if(al_clientes.size()>0) {
                        db.execSQL(Querys.Clientes.DelClientes);
                        Log.d("salida", "Eliminar clientes");
                        for (int i = 0; i < al_clientes.size(); i++) {
                            DataTableWS.Clientes c = al_clientes.get(i);
                            consulta = string.formatSql(Querys.Clientes.InsClientes4, c.getCli_cve_n(), c.getCli_cveext_str(), getBool(c.getCli_padre_n()), c.getCli_cvepadre_n(), c.getCli_razonsocial_str(), c.getCli_rfc_Str(), getBool(c.getCli_reqfact_n()), c.getCli_nombrenegocio_str(), c.getCli_nom_str(), c.getCli_apm_str(),
                                    c.getCli_apm_str(), getDate(c.getCli_fnac_dt()), c.getCli_genero_str(), c.getLpre_cve_n(), c.getNota_cve_n(), c.getFpag_cve_n(), getBool(c.getCli_consigna_n()), getBool(c.getCli_credito_n()), c.getCli_montocredito_n(), c.getCli_plazocredito_n(),
                                    c.getCli_credenvases_n(), c.getCli_estcredito_str(), getBool(c.getCli_fba_n()), c.getCli_porcentajefba_n(), c.getRut_cve_n(), c.getNvc_cve_n(), c.getGiro_cve_n(), c.getCli_email_str(), c.getCli_dirfact_n(),
                                    c.getCli_dirent_n(), c.getCli_Tel1_str(), c.getCli_tel2_str(), c.getEmp_cve_n(), c.getCli_coordenadaini_str(), c.getEst_cve_str(), c.getTcli_cve_n(), c.getCli_lun_n(), c.getCli_mar_n(), c.getCli_mie_n(), c.getCli_jue_n(),
                                    c.getCli_vie_n(), c.getCli_sab_n(), c.getCli_dom_n(), c.getFrec_cve_n(), getBool(c.getCli_especial_n()), getBool(c.getCli_esvallejo_n()), c.getNpro_cve_n(), c.getCli_huixdesc_n(), getBool(c.getCli_eshuix_n()), getBool(c.getCli_prospecto_n()),
                                    getBool(c.getCli_invalidafrecuencia_n()), getBool(c.getCli_invalidagps_n()), getBool(c.getCli_dobleventa_n()), getBool(c.getCli_comodato_n()), c.getSeg_cve_n(), getBool(c.getCli_dispersion_n()), c.getCli_dispersioncant_n(), c.getCli_limitemes_n());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "Agregar clientes");
                    }
                }

                if(al_creditos!=null)
                {
                    if(al_creditos.size()>0) {
                        db.execSQL(string.formatSql(Querys.Trabajo.DelCreditos));
                        Log.d("Salida", "Eliminar creditos");
                        for (int i = 0; i < al_creditos.size(); i++) {
                            DataTableWS.Creditos c = al_creditos.get(i);

                            consulta = string.formatSql(Querys.Trabajo.InsCreditos, c.getCred_referencia_str(), c.getCli_cve_n(), c.getUsu_cve_str(), getDate(c.getCred_fecha_dt()), c.getCred_descripcion_str(), getDate(c.getCred_vencimiento_dt()), c.getCred_monto_n(),
                                    c.getCred_abono_n(), getBool(c.getCred_engestoria_n()), getBool(c.getCred_esenvase_n()), getBool(c.getCred_especial_n()), c.getProd_cve_n(), c.getProd_sku_str(), c.getProd_precio_n(), c.getProd_cant_n(),
                                    c.getProd_cantabono_n(), "3", null);
                            db.execSQL(consulta);
                        }
                        Log.d("Salida", "Agregar creditos");
                    }
                }

                if(al_direcciones!=null)
                {
                    if(al_direcciones.size()>0) {
                        db.execSQL(string.formatSql(Querys.Direcciones.DelDirecciones));
                        Log.d("Salida", "Eliminar direcciones");
                        for (int i = 0; i < al_direcciones.size(); i++) {
                            DataTableWS.Direcciones d = al_direcciones.get(i);

                            consulta = string.formatSql(Querys.Direcciones.InsDirecciones2, d.getDir_cve_n(), d.getCli_cve_n(), d.getDir_alias_str(), d.getDir_calle_str(), d.getDir_noext_str(), d.getDir_noint_str(), d.getDir_entrecalle1_str(),
                                    d.getDir_entrecalle2_str(), d.getDir_colonia_str(), d.getDir_municipio_str(), d.getDir_estado_str(), d.getDir_pais_str(), d.getDir_codigopostal_str(),
                                    d.getDir_referencia_str(), d.getEst_cve_str(), d.getUsu_cve_str(), getDate(d.getDir_falta_dt()), d.getDir_tel1_str(), d.getDir_tel2_str(), d.getDir_encargado_str(), d.getDir_coordenada_str());
                            db.execSQL(consulta);
                        }
                        Log.d("Salida", "Agregar direcciones");
                    }
                }

                db.setTransactionSuccessful();
                almacenado=true;
            }catch (Exception e)
            {
                Log.d("salida","Error: "+e.toString());
                resultado = "Error: "+e.toString();
                almacenado=false;
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
                return null;
            }
        }

        private String getDate(String cad)
        {
            String originalStringFormat = "yyyy-MM-dd'T'HH:mm:ss";
            String desiredStringFormat = "yyyy-MM-dd HH:mm:ss";

            SimpleDateFormat readingFormat = new SimpleDateFormat(originalStringFormat);
            SimpleDateFormat outputFormat = new SimpleDateFormat(desiredStringFormat);

            try {
                Date date = readingFormat.parse(cad);
                cad = outputFormat.format(date);
            } catch (Exception e) {
                cad=null;
                e.printStackTrace();
            }
            return cad;
        }

    }


}
