package com.tdt.easyroute.Fragments.Clientes.OrdenaCliente;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.OrdenaClientesVM;
import com.tdt.easyroute.ViewModel.StartdayVM;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CdiaFragment extends Fragment {

    String dias[] = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
    String diasCve[] = {"cli_lun_n","cli_mar_n","cli_mie_n","cli_jue_n","cli_vie_n","cli_sab_n","cli_dom_n"};
    String clienteSeleccionado="";

    ClientesNodia clientesNodia;
    ClientesDia clientesDia;

    Button b_buscar, b_quitar, b_editar, b_subir, b_bajar;
    Spinner sp_dias;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;

    boolean actualizoItem=false;

    public OrdenaClientesVM ordenaClientesVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ordenaClientesVM = ViewModelProviders.of( getParentFragment()).get(OrdenaClientesVM.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cdia, container, false);
        vista=view;

        b_subir = view.findViewById(R.id.b_subir);
        b_bajar = view.findViewById(R.id.b_bajar);
        sp_dias = view.findViewById(R.id.sp_dia);
        tableLayout = view.findViewById(R.id.tableLayout);
        layoutInflater = inflater;

        sp_dias.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, dias));

        int dia = Utils.diaActualL_D();
        sp_dias.setSelection(dia);

        sp_dias.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listarClientesDia(i);
                listarClientesNodia(i);
                if(!actualizoItem)
                    ordenaClientesVM.setSelectItemDia(i);
                actualizoItem=false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b_subir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                subirItem();
            }
        });

        b_bajar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bajarItem();
            }
        });

        b_buscar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarItem();
            }
        });

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ordenaClientesVM.getSelectItemFuera().observe(getParentFragment(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                actualizoItem=true;
                sp_dias.setSelection(s);
                Log.d("salida","cambio item fragment fuera: "+s);
            }
        });
    }

    private void listarClientesNodia(int dia)
    {
        String filtro = diasCve[dia];
        String qry,json;
        ArrayList<DataTableLC.FrecPunteo> dt2 = null;
        ArrayList<DataTableLC.ClientesOrdenar> clientes=null;
        boolean conDatos=false;
        clienteSeleccionado="";

        json = BaseLocal.Select( string.formatSql("SELECT * FROM FRECPUNTEO WHERE DIASEM = '{0}' and frec_cve_n=0 ", filtro),getContext() ) ;
        dt2 = ConvertirRespuesta.getFrecPunteoJson(json);

        if(dt2==null)
        {
            qry = string.formatSql("SELECT CLI.CLI_CVEEXT_STR,CLI.CLI_RAZONSOCIAL_STR,cli.cli_nombrenegocio_str,CLI.FREC_CVE_N, " +
                    "FV.FREC_DESC_STR,FV.FREC_DIAS_N,CLI.EST_CVE_STR est_cve_n " +
                    "FROM CLIENTES CLI LEFT JOIN FRECUENCIASVISITA FV ON " +
                    "FV.FREC_CVE_N = CLI.FREC_CVE_N " +
                    "WHERE CLI.{0} = 0 " +
                    "ORDER BY CLI.CLI_CVEEXT_STR ", filtro);

            json = BaseLocal.Select(qry,getContext());

            clientes = ConvertirRespuesta.getClientesOrdenarJson(json);

            conDatos = false;
        }
        else
        {
            qry = string.formatSql("select fp.*,c.cli_razonsocial_str,c.cli_nombrenegocio_str,fv.frec_desc_str,fv.frec_dias_n " +
                    "from frecpunteo fp left join clientes c " +
                    "on fp.cli_cveext_str=c.cli_cveext_str inner join frecuenciasvisita fv " +
                    "on fp.sec=fv.frec_cve_n where fp.frec_cve_n=0 and diasem='{0}' order by fp.frec_cve_n",filtro);

            json = BaseLocal.Select(qry,getContext());

            clientes = ConvertirRespuesta.getClientesOrdenarJson(json);

            conDatos = true;
        }

        clientesNodia = new ClientesNodia(clientes,conDatos,filtro);
        ordenaClientesVM.setClientesNodia(  clientesNodia );

    }

    private void listarClientesDia(int dia)
    {
        String filtro = diasCve[dia];
        String qry,json;
        ArrayList<DataTableLC.FrecPunteo> dt2 = null;
        ArrayList<DataTableLC.ClientesOrdenar> clientes=null;
        boolean conDatos=false;
        clienteSeleccionado="";

        json = BaseLocal.Select( string.formatSql("SELECT * FROM FRECPUNTEO WHERE DIASEM ='{0}' ", filtro),getContext() ) ;
        dt2 = ConvertirRespuesta.getFrecPunteoJson(json);

        if(dt2==null)
        {
            qry = string.formatSql("SELECT CLI.CLI_CVEEXT_STR,CLI.CLI_RAZONSOCIAL_STR,cli.cli_nombrenegocio_str,CLI.FREC_CVE_N, " +
                    "FV.FREC_DESC_STR,FV.FREC_DIAS_N,CLI.EST_CVE_STR est_cve_n " +
                    "FROM CLIENTES CLI LEFT JOIN FRECUENCIASVISITA FV ON " +
                    "FV.FREC_CVE_N = CLI.FREC_CVE_N " +
                    "WHERE CLI.{0} > 0 " +
                    "ORDER BY CLI.CLI_CVEEXT_STR ", filtro);

            json = BaseLocal.Select(qry,getContext());

            clientes = ConvertirRespuesta.getClientesOrdenarJson(json);

            conDatos = false;
        }
        else
        {
            qry = string.formatSql("select fp.*,c.cli_razonsocial_str,c.cli_nombrenegocio_str,fv.frec_desc_str,fv.frec_dias_n " +
                    "from frecpunteo fp left join clientes c " +
                    "on fp.cli_cveext_str=c.cli_cveext_str inner join frecuenciasvisita fv " +
                    "on fp.sec=fv.frec_cve_n where fp.frec_cve_n>0 and diasem='{0}' order by fp.frec_cve_n",filtro);

            json = BaseLocal.Select(qry,getContext());

            clientes = ConvertirRespuesta.getClientesOrdenarJson(json);

            conDatos = true;
        }

        if(conDatos)
        {
            clientesDia = new ClientesDia(clientes,conDatos,filtro);
            cargarClientesDia();
        }
        else
        {
            clientesDia = new ClientesDia(clientes,conDatos,filtro);
            cargarClientesDia();
        }
    }

    private void cargarClientesDia()
    {
        try
        {
            boolean conDatos = clientesDia.isConDatos();
            ArrayList<DataTableLC.ClientesOrdenar> dt = clientesDia.getClientes();
            String diasem = clientesDia.getFiltro();

            mostrarTitulo();
            int frec=100;

            if (conDatos)
            {
                for(int i=0; i<dt.size();i++)
                {
                    DataTableLC.ClientesOrdenar r = dt.get(i);

                    mostrarClientesDia(r.getCli_cveext_str(),r.getCli_nombrenegocio_str(),r.getSec(),r.getFrec_desc_str(),r.getEst_cve_n(),r.getCoor(),r.getDiasem());
                    frec = frec + 100;
                }

            }
            else
            {
                for(int i=0; i<dt.size();i++)
                {
                    DataTableLC.ClientesOrdenar r = dt.get(i);

                    mostrarClientesDia(r.getCli_cveext_str(),r.getCli_nombrenegocio_str(),String.valueOf(frec),r.getFrec_desc_str(),r.getEst_cve_n(),"0",diasem);
                    frec = frec + 100;
                }
            }
        }catch (Exception e)
        {
            Log.d("salida","Error: cargar"+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();

        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_ordclientes, null);

        ((TextView) tr.findViewById(R.id.t_cliente)).setTypeface( ((TextView) tr.findViewById(R.id.t_cliente)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_nombre)).setTypeface( ((TextView) tr.findViewById(R.id.t_nombre)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_secu)).setTypeface( ((TextView) tr.findViewById(R.id.t_secu)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_frec)).setTypeface( ((TextView) tr.findViewById(R.id.t_frec)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_estatus)).setTypeface( ((TextView) tr.findViewById(R.id.t_estatus)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_coor)).setTypeface( ((TextView) tr.findViewById(R.id.t_coor)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_dia)).setTypeface( ((TextView) tr.findViewById(R.id.t_dia)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);
    }

    private void mostrarClientesDia(String cliente, String nombre, String secu, String frec, String status, String coor, String dia)
    {
        TableRow tr;
        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_ordclientes, null);

        ((TextView) tr.findViewById(R.id.t_cliente)).setText(cliente);
        ((TextView) tr.findViewById(R.id.t_nombre)).setText(nombre);
        ((TextView) tr.findViewById(R.id.t_secu)).setText(secu);
        ((TextView) tr.findViewById(R.id.t_frec)).setText(frec);
        ((TextView) tr.findViewById(R.id.t_estatus)).setText( status);
        ((TextView) tr.findViewById(R.id.t_coor)).setText( coor );
        ((TextView) tr.findViewById(R.id.t_dia)).setText( dia);

        tr.setTag(cliente);
        tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

        if(cliente.equals(clienteSeleccionado))
            tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        tableLayout.addView(tr);
    }

    private void bajarItem()
    {
        if(!clienteSeleccionado.isEmpty())
        {
            int posicion=-1;
            for(int i=0; i<clientesDia.getClientes().size(); i++)
            {
                if( clienteSeleccionado.equals( clientesDia.getClientes().get(i).getCli_cveext_str() ) )
                {
                    posicion=i;
                    i=clientesDia.getClientes().size()+1;
                }
            }

            if(posicion<clientesDia.getClientes().size()-1)
            {
                DataTableLC.ClientesOrdenar cliUp = clientesDia.getClientes().get(posicion);
                DataTableLC.ClientesOrdenar cliDown = clientesDia.getClientes().get(posicion+1);

                clientesDia.getClientes().set(posicion+1, cliUp);
                clientesDia.getClientes().set(posicion, cliDown);

                cargarClientesDia();
            }
        }
        else
            Toast.makeText(getContext(), "Selecciona un cliente", Toast.LENGTH_SHORT).show();
    }

    private void subirItem()
    {
        if(!clienteSeleccionado.isEmpty())
        {
            int posicion=-1;
            for(int i=0; i<clientesDia.getClientes().size(); i++)
            {
                if( clienteSeleccionado.equals( clientesDia.getClientes().get(i).getCli_cveext_str() ) )
                {
                    posicion=i;
                    i=clientesDia.getClientes().size()+1;
                }
            }

            if(posicion>0)
            {
                DataTableLC.ClientesOrdenar cliUp = clientesDia.getClientes().get(posicion - 1);
                DataTableLC.ClientesOrdenar cliDown = clientesDia.getClientes().get(posicion);

                clientesDia.getClientes().set(posicion - 1, cliDown);
                clientesDia.getClientes().set(posicion, cliUp);

                cargarClientesDia();
            }
        }
        else
            Toast.makeText(getContext(), "Selecciona un cliente", Toast.LENGTH_SHORT).show();
    }

    private void buscarItem()
    {
        if(!clienteSeleccionado.equals(tag))
        {
            String cliente;
            String nombre;

            for (int i = 0; i < clientesDia.getClientes().size(); i++)
            {
                TableRow row = (TableRow) vista.findViewWithTag(clientesDia.getClientes().get(i).getCli_cveext_str());


                cliente =  ((TextView) row.findViewById(R.id.t_cliente)).getText().toString();
                nombre = clientesDia.getClientes().get(i).getCli_nombrenegocio_str();


            }
            //pinta de azul la fila y actualiza la cve de la fila seccionada

            clienteSeleccionado = tag;
        }
    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            String tag = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            if(!clienteSeleccionado.equals(tag))
            {
                for (int i = 0; i < clientesDia.getClientes().size(); i++) {
                    TableRow row = (TableRow) vista.findViewWithTag(clientesDia.getClientes().get(i).getCli_cveext_str());
                    row.setBackgroundColor(Color.WHITE);
                }
                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                clienteSeleccionado = tag;
            }
        }
    };



}
