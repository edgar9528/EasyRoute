package com.tdt.easyroute.Fragments.Pedidos;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PedidosFragment extends Fragment {

    Button b_actClientes, b_reimp, b_actCliente, b_buscar,
           b_selec, b_detalle, b_noven,b_salir;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;
    String cliSelec="";

    ArrayList<DataTableLC.PedidosLv> lvi=null;

    Configuracion conf;

    public static PedidosFragment newInstance() {
        PedidosFragment fragment = new PedidosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pedidos, container, false);
        vista=view;

        b_actClientes = view.findViewById(R.id.b_actClientes);
        b_reimp = view.findViewById(R.id.b_reimprimir);
        b_actCliente = view.findViewById(R.id.b_actCliente);
        b_buscar = view.findViewById(R.id.b_buscar);
        b_selec = view.findViewById(R.id.b_seleccionar);
        b_detalle = view.findViewById(R.id.b_detalle);
        b_noven = view.findViewById(R.id.b_noventa);
        b_salir = view.findViewById(R.id.b_salir);

        layoutInflater = inflater;
        tableLayout = view.findViewById(R.id.tableLayout);

        conf = Utils.ObtenerConf(getActivity().getApplication());


        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });

        listarClientes(true);

        return view;

    }

    private void listarClientes(boolean dia)
    {
        try
        {
            String filtro = "";
            String qry = "";
            String qryc = "";

            if(conf.getPreventa()==2)
                qryc="or cli_cve_n in (select cli_cve_n from visitapreventa)";

            String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
            Calendar cal= Calendar.getInstance();
            cal.setTime(new Date());
            int numeroDia = cal.get(Calendar.DAY_OF_WEEK)-1;

            String hoy = dias[numeroDia];

            if(conf.getPreventa()==1)
            {
                if(dias[numeroDia].equals("Sábado"))
                {
                    hoy = "Lunes";
                }
                else
                    hoy= dias[numeroDia+1];
            }

            if(dia)
            {
                switch (hoy)
                {
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
                            "(select rut_cve_n from rutas where rut_prev_n={1}) and est_cve_str<>'B' order by {0}", filtro, conf.getRutaStr(),filtro);
                else
                    qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str," +
                            "cli_especial_n,cli_prospecto_n,est_cve_str from clientes where {0}>0 and rut_cve_n={1} and " +
                            "est_cve_str<>'B' {2} order by {0}", filtro, conf.getRutaStr(),qryc,filtro);
            }
            else
            {
                if (conf.getPreventa() == 1)
                    qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str," +
                            "cli_especial_n,cli_prospecto_n,est_cve_str from clientes where rut_cve_n in " +
                            "(select rut_cve_n from rutas where rut_prev_n={1}) est_cve_str<>'B' order by {0}", conf.getRutaStr(),conf.getRutaStr());
                else
                    qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_razonsocial_str,cli_nombrenegocio_str," +
                            "cli_especial_n,cli_prospecto_n,est_cve_str from clientes where rut_cve_n={0} and " +
                            "est_cve_str<>'B' order by {0}", conf.getRutaStr(),conf.getRutaStr());
            }


            try
            {
                BaseLocal.Select("select pag_especie_n from pagos",getContext());
            }
            catch (Exception ex)
            {
                BaseLocal.Insert("alter table pagos add pag_especie_n bit not null default 0",getContext());
            }

            try
            {
                BaseLocal.Select("select vdet_kit_n from ventasdet",getContext());
            }
            catch (Exception ex)
            {
                BaseLocal.Insert("alter table ventasdet add vdet_kit_n bit not null default 0",getContext());
            }

            ArrayList<DataTableLC.PedidosClientes> dt =null;
            String json = BaseLocal.Select(qry,getContext());
            dt = ConvertirRespuesta.getPedidosClientesJson(json);


            if (dia)
            {
                cargarClientes(dt);
            }


        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getContext(), "Error al listar clientes: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void cargarClientes(ArrayList<DataTableLC.PedidosClientes> dt)
    {
        try
        {
            ArrayList<DataTableLC.PedidosVisitas> dtCli = null;
            ArrayList<DataTableLC.PedidosVisitas> dtCli2 = null;
            ArrayList<DataTableLC.PedidosVisPre> dtVisPre =null;

            int l = 0;
            String cveext = "";

            String json= BaseLocal.Select("Select * from visitapreventa",getContext());
            dtVisPre = ConvertirRespuesta.getPedidosVisPreJson(json);

            if(dt!=null)
            {
                lvi = new ArrayList<>();

                String icono,cli_cve_n,cli_est;
                String cli_cveext_n,cli_nombre,cli_especial_n;

                DataTableLC.PedidosClientes r;
                for(int i=0; i <dt.size();i++)
                {


                    r = dt.get(i);
                    l= r.getCli_cveext_str().length()-6;

                    //if(l>=0)
                    //{
                        cli_cve_n = r.getCli_cve_n();
                        cli_especial_n = r.getCli_especial_n();

                        if(r.getCli_prospecto_n()==null || r.getCli_prospecto_n().isEmpty() )
                            cli_est="P";
                        else
                            cli_est = r.getCli_cveext_str().substring(0,1);

                        cli_cveext_n = String.valueOf(   Long.parseLong( r.getCli_cveext_str().replace("PR","").replace("C","").replace("P","").replace("V","") )   ) ;

                        if(r.getCli_nombrenegocio_str()==null || r.getCli_nombrenegocio_str().isEmpty())
                            cli_nombre = r.getCli_razonsocial_str();
                        else
                            cli_nombre = r.getCli_nombrenegocio_str();

                    //}


                    icono = "0";

                    if (  r.getEst_cve_str().equals("I") )
                        icono = "4";

                    dtCli=null;
                    json = BaseLocal.Select( string.formatSql("select * from visitas where cli_cve_n={0} and " +
                            "(upper(vis_operacion_str)='CON VENTA' or upper(vis_operacion_str)='CON PREVENTA') order by vis_fecha_dt desc", r.getCli_cve_n() ),getContext() );
                    dtCli = ConvertirRespuesta.getPedidosVisitasJson(json);

                    DataTableLC.PedidosVisPre vp=null;
                    if(dtVisPre!=null)
                        for(int j=0; j<dtVisPre.size();j++)
                        {
                            if(r.getCli_cve_n().equals( dtVisPre.get(j).getCli_cve_n()  ))
                            {
                                vp = dtVisPre.get(j);
                            }
                        }

                    if(vp!=null && conf.getPreventa()==2)
                    {
                        icono ="5";
                    }

                    if (dtCli!=null)
                    {

                        icono="1";
                    }
                    else
                    {
                        dtCli2=null;
                        json = BaseLocal.Select(   string.formatSql("select * from visitas where cli_cve_n={0} and vis_operacion_str<>'CON VENTA' order by vis_fecha_dt desc", r.getCli_cve_n()), getContext()   );
                        dtCli2 = ConvertirRespuesta.getPedidosVisitasJson(json);

                        if(dtCli2!=null)
                        {
                            String op = dtCli2.get(0).getVis_operacion_str();
                            if(op.equals("NO VENTA"))
                                icono="3";
                            if(op.equals("CON VENTA") || op.equals("CON PREVENTA") )
                                icono="1";
                            if(op.equals("SIN VENTA") || op.equals("CON COBRANZA") )
                                icono="2";
                        }
                    }

                    lvi.add( new DataTableLC.PedidosLv(icono, cli_cve_n,cli_est,cli_cveext_n,cli_nombre, cli_especial_n) );
                }
                mostrarClientes();
            }

        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getContext(), "Error al cargar clientes: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void mostrarClientes()
    {
        Drawable[] iconos = new Drawable[6];
        iconos[0] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
        iconos[1] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
        iconos[2] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
        iconos[3] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
        iconos[4] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);
        iconos[5] = ContextCompat.getDrawable(getActivity(), R.drawable.icon_espera);

        tableLayout.removeAllViews();
        TableRow tr;
        cliSelec="";

        for(int i=0; i<lvi.size();i++)
        {
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_visitacli, null);

            ((ImageView) tr.findViewById(R.id.t_icono)).setBackground( iconos[lvi.get(i).getIconoInt()]  );
            ((TextView) tr.findViewById(R.id.t_estado)).setText(lvi.get(i).getCli_est());
            ((TextView) tr.findViewById(R.id.t_cve)).setText(lvi.get(i).getCli_cveext_n());
            ((TextView) tr.findViewById(R.id.t_nombre)).setText(lvi.get(i).getCli_nombre());

            tr.setTag(lvi.get(i).getCli_cve_n());
            tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
            tableLayout.addView(tr);
        }

    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view)
        {
            TableRow tr = ((TableRow) view);
            String tag = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            if(!tag.equals(cliSelec))
            {
                for (int i = 0; i < lvi.size(); i++) {
                    TableRow row = (TableRow) vista.findViewWithTag(lvi.get(i).getCli_cve_n());
                    row.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                }
                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cliSelec = tag;
            }
        }
    };


}
