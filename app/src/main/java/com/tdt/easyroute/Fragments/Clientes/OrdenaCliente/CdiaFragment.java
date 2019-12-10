package com.tdt.easyroute.Fragments.Clientes.OrdenaCliente;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
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
    String clienteSeleccionado="",diaSeleccionado,nombreBase;

    ClientesNodia clientesNodia;
    ClientesDia clientesDia;

    EditText et_filtro;
    Button b_buscar, b_quitar, b_editar, b_subir, b_bajar, b_salir,b_enviar;
    Spinner sp_dias;

    ScrollView scrollView;
    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;
    OrdenacliFragment fragment;

    boolean cambioFuera=false;

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
        fragment =  (OrdenacliFragment) getParentFragment();

        nombreBase = getContext().getString( R.string.nombreBD );

        et_filtro = view.findViewById(R.id.et_filtro);
        b_subir = view.findViewById(R.id.b_subir);
        b_bajar = view.findViewById(R.id.b_bajar);
        b_buscar = view.findViewById(R.id.button_buscar);
        b_salir = view.findViewById(R.id.b_salir);
        b_enviar = view.findViewById(R.id.b_enviar);
        b_quitar = view.findViewById(R.id.b_quitar);
        b_editar = view.findViewById(R.id.b_editar);
        sp_dias = view.findViewById(R.id.sp_dia);
        scrollView = view.findViewById(R.id.scrollView);
        tableLayout = view.findViewById(R.id.tableLayout);
        layoutInflater = inflater;

        sp_dias.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, dias));


        sp_dias.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                diaSeleccionado = diasCve[i];

                if(!cambioFuera)
                    ordenaClientesVM.setSelectItemDia(i);
                else
                    cambioFuera=false;

                ConsultarClientes consultarClientes = new ConsultarClientes(i);
                consultarClientes.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b_salir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });

        b_quitar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                quitarItem();
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
                buscarItem(et_filtro.getText().toString());
            }
        });

        b_editar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                editarItem();
            }
        });

        int dia = Utils.diaActualL_D();
        sp_dias.setSelection(dia);

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ordenaClientesVM.getSelectItemFuera().observe(getParentFragment(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                cambioFuera=true;
                sp_dias.setSelection(integer);
            }
        });

        ordenaClientesVM.getMoverItem().observe(getParentFragment(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                agregarItem(integer);
            }
        });


    }

    private class ConsultarClientes extends AsyncTask<Boolean, Integer, Boolean> {

        private ProgressDialog progreso;
        private int i;

        public ConsultarClientes(int i) {
            this.i = i;
        }

        @Override
        protected void onPreExecute() {
            progreso = new ProgressDialog(getContext());
            progreso.setMessage("Cargando...");
            progreso.setCancelable(false);
            progreso.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            try
            {
                listarClientesDia(i);
                listarClientesNodia(i);
                return true;
            }catch (Exception e)
            {
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

            if(result)
            {
                cargarClientesDia();
                ordenaClientesVM.setClientesNodia(  clientesNodia );
                guardar();
            }
            else
            {
                Toast.makeText(getContext(), "Error al cargar clientes", Toast.LENGTH_SHORT).show();
            }
            progreso.dismiss();
        }
    }

    private void listarClientesNodia(int dia)
    {
        String filtro = diasCve[dia];
        String qry,json;
        ArrayList<DataTableLC.FrecPunteo> dt2 = null;
        ArrayList<DataTableLC.ClientesOrdenar> clientes=null;
        boolean conDatos=false;
        clienteSeleccionado="";

        json = BaseLocal.Select( string.formatSql("SELECT * FROM FRECPUNTEO WHERE DIASEM = '{0}' and sec=0 ", filtro),getContext() ) ;
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
                    "on fp.frec_cve_n=fv.frec_cve_n where fp.sec=0 and diasem='{0}' order by fp.sec",filtro);

            json = BaseLocal.Select(qry,getContext());

            clientes = ConvertirRespuesta.getClientesOrdenarJson(json);

            conDatos = true;
        }

        Log.d("salida","Clientes fuera Condatos: "+conDatos);

        clientesNodia = new ClientesNodia(clientes,conDatos,filtro);

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
                    "on fp.frec_cve_n=fv.frec_cve_n where fp.sec>0 and diasem='{0}' order by fp.sec",filtro);

            json = BaseLocal.Select(qry,getContext());

            clientes = ConvertirRespuesta.getClientesOrdenarJson(json);

            conDatos = true;

        }

        Log.d("salida","Clientes dia Condatos: "+conDatos);

        clientesDia = new ClientesDia(clientes,conDatos,filtro);
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

            for(int i=0; i<dt.size();i++)
            {
                if(conDatos)
                {
                    DataTableLC.ClientesOrdenar r = dt.get(i);
                    mostrarClientesDia(r.getCli_cveext_str(),r.getCli_nombrenegocio_str(),r.getSec(),r.getFrec_desc_str(),r.getEst_cve_n(),r.getCoor(),r.getDiasem());
                }
                else
                {
                    clientesDia.getClientes().get(i).setSec( String.valueOf( frec ) );
                    clientesDia.getClientes().get(i).setDiasem( diasem );
                    clientesDia.getClientes().get(i).setCoor("0");

                    DataTableLC.ClientesOrdenar r = dt.get(i);
                    mostrarClientesDia(r.getCli_cveext_str(),r.getCli_nombrenegocio_str(),r.getSec(),r.getFrec_desc_str(),r.getEst_cve_n(),r.getCoor(),r.getDiasem());
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
            int posicion= getClienteSeleccionado();

            if(posicion<clientesDia.getClientes().size()-1)
            {
                DataTableLC.ClientesOrdenar cliUp = clientesDia.getClientes().get(posicion);
                DataTableLC.ClientesOrdenar cliDown = clientesDia.getClientes().get(posicion+1);

                String vUp =  cliUp.getSec();
                String vDown = cliDown.getSec();

                clientesDia.getClientes().set(posicion+1, cliUp);
                clientesDia.getClientes().set(posicion, cliDown);

                clientesDia.getClientes().get(posicion+1).setSec( vDown  );
                clientesDia.getClientes().get(posicion).setSec(  vUp );

                actualizarPosicionBase(vUp,vDown,posicion,false);
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
            int posicion= getClienteSeleccionado();

            if(posicion>0)
            {
                DataTableLC.ClientesOrdenar cliUp = clientesDia.getClientes().get(posicion - 1);
                DataTableLC.ClientesOrdenar cliDown = clientesDia.getClientes().get(posicion);

                String vUp =  cliUp.getSec();
                String vDown = cliDown.getSec();

                clientesDia.getClientes().set(posicion - 1, cliDown);
                clientesDia.getClientes().set(posicion, cliUp);

                clientesDia.getClientes().get(posicion-1).setSec( vUp  );
                clientesDia.getClientes().get(posicion).setSec(  vDown );

                actualizarPosicionBase(vUp,vDown,posicion,true);
                cargarClientesDia();
            }
        }
        else
            Toast.makeText(getContext(), "Selecciona un cliente", Toast.LENGTH_SHORT).show();
    }

    private int getClienteSeleccionado()
    {
        int p=-1;
        for(int i=0; i<clientesDia.getClientes().size(); i++)
        {
            if( clienteSeleccionado.equals( clientesDia.getClientes().get(i).getCli_cveext_str() ) )
            {
                p=i;
                i=clientesDia.getClientes().size()+1;
            }
        }
        return p;
    }

    private void actualizarPosicionBase(String vUp, String vDown,int posicion, boolean up)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try{
            db.beginTransaction();

            if(up)
            {
                db.execSQL(string.formatSql("update frecpunteo set sec={0} where cli_cveext_str='{1}' and diasem='{2}'",
                        vUp, clientesDia.getClientes().get(posicion-1).getCli_cveext_str(), clientesDia.getClientes().get(posicion-1).getDiasem() ));
                db.execSQL(string.formatSql("update frecpunteo set sec={0} where cli_cveext_str='{1}' and diasem='{2}'",
                        vDown, clientesDia.getClientes().get(posicion).getCli_cveext_str(), clientesDia.getClientes().get(posicion).getDiasem() ));
            }
            else
            {
                db.execSQL(string.formatSql("update frecpunteo set sec={0} where cli_cveext_str='{1}' and diasem='{2}'",
                        vDown, clientesDia.getClientes().get(posicion+1).getCli_cveext_str(), clientesDia.getClientes().get(posicion+1).getDiasem() ));
                db.execSQL(string.formatSql("update frecpunteo set sec={0} where cli_cveext_str='{1}' and diasem='{2}'",
                        vUp, clientesDia.getClientes().get(posicion).getCli_cveext_str(), clientesDia.getClientes().get(posicion).getDiasem() ));
            }

            db.setTransactionSuccessful();

            Log.d("salida","Posicion actualizada en bd");

        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getContext(), "Error: "+e.toString(), Toast.LENGTH_LONG).show();
            db.close();
        }
        finally {
            db.endTransaction();
            db.close();
        }

    }

    private void buscarItem(String palabra)
    {
        String cliente;
        String nombre;
        boolean encontrado=false;

        if(!palabra.isEmpty()) {
            for (int i = 0; i < clientesDia.getClientes().size(); i++) {
                TableRow row = (TableRow) vista.findViewWithTag(clientesDia.getClientes().get(i).getCli_cveext_str());

                cliente = ((TextView) row.findViewById(R.id.t_cliente)).getText().toString();
                nombre = ((TextView) row.findViewById(R.id.t_nombre)).getText().toString();
                palabra = palabra.toLowerCase();

                if (cliente.toLowerCase().contains(palabra) || nombre.toLowerCase().contains(palabra)) {
                    for (int j = 0; j < clientesDia.getClientes().size(); j++) {
                        TableRow tr = (TableRow) vista.findViewWithTag(clientesDia.getClientes().get(j).getCli_cveext_str());
                        tr.setBackgroundColor(Color.WHITE);
                    }

                    row.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    clienteSeleccionado = cliente;
                    i = clientesDia.getClientes().size();

                    scrollView.scrollTo(0, row.getTop());

                    encontrado = true;
                }
            }

            if (!encontrado)
                Toast.makeText(getContext(), "Cliente no encontrado en 'Clientes del dia'", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getContext(), "Escriba el texto a buscar", Toast.LENGTH_SHORT).show();

    }

    private void quitarItem()
    {
        if(!clienteSeleccionado.isEmpty())
        {
            int posicion = getClienteSeleccionado();

            DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), nombreBase, null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            try{
                db.beginTransaction();

                db.execSQL(string.formatSql("update frecpunteo set sec={0} where cli_cveext_str='{1}' and diasem='{2}'",
                        "0", clientesDia.getClientes().get(posicion).getCli_cveext_str(), clientesDia.getClientes().get(posicion).getDiasem()) );
                db.execSQL(string.formatSql("update frecpunteo set sec=sec-100 where sec>={0} and diasem='{1}'",
                        clientesDia.getClientes().get(posicion).getSec(), clientesDia.getClientes().get(posicion).getDiasem() ));

                db.setTransactionSuccessful();

                String sec;
                for(int i=posicion; i<clientesDia.getClientes().size();i++)
                {
                    sec = String.valueOf( Integer.parseInt( clientesDia.getClientes().get(i).getSec() ) -100 );
                    clientesDia.getClientes().get(i).setSec( sec );
                }

                clientesDia.getClientes().get(posicion).setSec("0");
                clientesNodia.getClientes().add( clientesDia.getClientes().get(posicion) );
                clientesDia.getClientes().remove(posicion);

                clienteSeleccionado="";

                cargarClientesDia();
                ordenaClientesVM.setClientesNodia(  clientesNodia );

            }
            catch (Exception e)
            {
                Log.d("salida","Error: "+e.getMessage());
                Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                db.endTransaction();
                db.close();
            }
            finally {
                db.endTransaction();
                db.close();
            }

        }
        else
            Toast.makeText(getContext(), "Selecciona un cliente", Toast.LENGTH_SHORT).show();
    }

    private void agregarItem(int indice)
    {
        if(!clienteSeleccionado.isEmpty())
        {
            int posicion = getClienteSeleccionado();

            DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), nombreBase, null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            try{
                db.beginTransaction();

                db.execSQL(string.formatSql("update frecpunteo set sec=sec+100 where sec>={0} and diasem='{1}'",
                        clientesDia.getClientes().get(posicion).getSec(),clientesDia.getClientes().get(posicion).getDiasem()));
                db.execSQL(string.formatSql("update frecpunteo set sec={0} where cli_cveext_str='{1}' and diasem='{2}'",
                        clientesDia.getClientes().get(posicion).getSec(),clientesNodia.getClientes().get(indice).getCli_cveext_str(), clientesNodia.getClientes().get(indice).getDiasem() ));

                db.setTransactionSuccessful();

                clientesNodia.getClientes().get(indice).setSec(  clientesDia.getClientes().get(posicion).getSec()  );

                clientesDia.getClientes().add(posicion, clientesNodia.getClientes().get(indice));

                String sec;
                for(int i=posicion+1;i<clientesDia.getClientes().size();i++)
                {
                    sec = String.valueOf( Integer.parseInt( clientesDia.getClientes().get(i).getSec() ) + 100 );
                    clientesDia.getClientes().get(i).setSec(  sec     );
                }

                clientesNodia.getClientes().remove(indice);

                cargarClientesDia();
                ordenaClientesVM.setClientesNodia(  clientesNodia );

                fragment.goClientesDiaFragment();
                buscarItem( clientesDia.getClientes().get(posicion).getCli_cveext_str() );
            }
            catch (Exception e)
            {
                Log.d("salida","Error: "+e.getMessage());
                Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                db.endTransaction();
                db.close();
            }
            finally {
                db.endTransaction();
                db.close();
            }

        }
        else
            Toast.makeText(getContext(), "Selecciona un cliente del día", Toast.LENGTH_SHORT).show();
    }

    private void editarItem()
    {
        if(!clienteSeleccionado.isEmpty())
        {
            int id = getClienteSeleccionado();

            DataTableLC.ClientesOrdenar cli = clientesDia.getClientes().get(id);

            Intent intent = new Intent(getContext(), EditarclienteActivity.class);
            CamposEditar camposEditar = new CamposEditar( cli.getEst_cve_n(), cli.getFrec_cve_n(), cli.getCoor()  );
            intent.putExtra("CamposEditar",camposEditar);
            startActivityForResult(intent,0);

        }
        else
            Toast.makeText(getContext(), "Selecciona un cliente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode== Activity.RESULT_OK)
        {
            try
            {
                CamposEditar camposEditar = (CamposEditar) data.getExtras().getSerializable("CamposEditar");
                int i = getClienteSeleccionado();

                //actualizar información en la lista
                clientesDia.getClientes().get(i).setFrec_cve_n(camposEditar.getFrecuencia());
                clientesDia.getClientes().get(i).setFrec_desc_str(camposEditar.getFrecuencia_desc());
                clientesDia.getClientes().get(i).setCoor( camposEditar.getCoordenadas() );
                clientesDia.getClientes().get(i).setEst_cve_n(camposEditar.getEstado());

                DataTableLC.ClientesOrdenar cli = clientesDia.getClientes().get(i);

                //actualizar información que se muestra
                TableRow row = (TableRow) vista.findViewWithTag(clienteSeleccionado);
                ((TextView) row.findViewById(R.id.t_frec)).setText(camposEditar.getFrecuencia_desc());
                ((TextView) row.findViewById(R.id.t_estatus)).setText(camposEditar.getEstado());

                //Actualizar información en la bd
                String qry = string.formatSql( "SELECT * FROM FRECPUNTEO WHERE CLI_CVEEXT_STR = '{0}' AND DIASEM= '{1}'", cli.getCli_cveext_str(), cli.getDiasem()  );

                ArrayList<DataTableLC.FrecPunteo> dt=null;
                String json = BaseLocal.Select(qry,getContext());
                dt = ConvertirRespuesta.getFrecPunteoJson(json);

                if(dt!=null)
                {
                    qry = "UPDATE FRECPUNTEO SET SEC={0},FREC_CVE_N= {1},EST_CVE_N = '{2}',COOR = '{3}' WHERE CLI_CVEEXT_STR = '{4}' AND DIASEM= '{5}'";
                    qry = string.formatSql(qry, cli.getSec(), cli.getFrec_cve_n(), cli.getEst_cve_n(),cli.getCoor(), cli.getCli_cveext_str(), cli.getDiasem() );
                    BaseLocal.Insert(qry,getContext());
                }
                else
                {
                    qry = "INSERT INTO FRECPUNTEO VALUES ('{0}',{1},{2},'{3}','{4}','{5}')";
                    qry = string.formatSql( qry, cli.getCli_cveext_str(), cli.getSec(), cli.getFrec_cve_n(), cli.getEst_cve_n(), cli.getCoor(), cli.getDiasem() );

                    BaseLocal.Insert(qry,getContext());
                }

                Toast.makeText(getContext(), "Cliente actualizado", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Log.d("salida",e.getMessage());
                Toast.makeText(getContext(), "Error al actualizar cliente", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), "Actualización cancelada", Toast.LENGTH_SHORT).show();
        }

    }

    private void guardar()
    {
        String query,json;
        String qry;

        ArrayList<DataTableLC.ClientesOrdenar> lvClientes = clientesDia.getClientes();
        ArrayList<DataTableLC.ClientesOrdenar> lvClientesNoDia = clientesNodia.getClientes();

        try
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), nombreBase, null, 1);
            SQLiteDatabase bd = databaseHelper.getWritableDatabase();

            for(int i=0; i<lvClientes.size();i++)
            {
                qry = "SELECT * FROM FRECPUNTEO WHERE CLI_CVEEXT_STR = '{0}' AND DIASEM= '{1}'";

                DataTableLC.ClientesOrdenar lv = lvClientes.get(i);
                json= BaseLocal.Select( string.formatSql( qry, lv.getCli_cveext_str(), lv.getDiasem() ) ,getContext());
                ArrayList<DataTableLC.FrecPunteo> dt = ConvertirRespuesta.getFrecPunteoJson(json);

                if(dt!=null)
                {
                    query = "UPDATE FRECPUNTEO SET SEC={0},FREC_CVE_N= {1},EST_CVE_N = '{2}',COOR = '{3}' WHERE CLI_CVEEXT_STR = '{4}' AND DIASEM= '{5}'";
                    bd.execSQL( string.formatSql(query,lv.getSec(),lv.getFrec_cve_n(),lv.getEst_cve_n(),lv.getCoor(),lv.getCli_cveext_str(),lv.getDiasem()) );
                }
                else
                {
                    query = "INSERT INTO FRECPUNTEO (CLI_CVEEXT_STR,SEC,FREC_CVE_N,EST_CVE_N,COOR,DIASEM) VALUES ('{0}',{1},{2},'{3}','{4}','{5}')";
                    bd.execSQL(string.formatSql( query, lv.getCli_cveext_str(), lv.getSec()  , lv.getFrec_cve_n()  ,lv.getEst_cve_n(),lv.getCoor(),lv.getDiasem() ));
                }
            }

            for(int i=0; i<lvClientesNoDia.size();i++)
            {
                qry = "SELECT * FROM FRECPUNTEO WHERE CLI_CVEEXT_STR = '{0}' AND DIASEM= '{1}'";

                DataTableLC.ClientesOrdenar lv = lvClientesNoDia.get(i);
                json= BaseLocal.Select( string.formatSql( qry, lv.getCli_cveext_str(), lv.getDiasem() ) ,getContext());
                ArrayList<DataTableLC.FrecPunteo> dt = ConvertirRespuesta.getFrecPunteoJson(json);

                if(dt!=null)
                {
                    query = "UPDATE FRECPUNTEO SET SEC={0},FREC_CVE_N= {1},EST_CVE_N = '{2}',COOR = '{3}' WHERE CLI_CVEEXT_STR = '{4}' AND DIASEM= '{5}'";
                    bd.execSQL(string.formatSql(query,lv.getSec(),lv.getFrec_cve_n(),lv.getEst_cve_n(),lv.getCoor(),lv.getCli_cveext_str(),lv.getDiasem()));
                }
                else
                {
                    query = "INSERT INTO FRECPUNTEO (CLI_CVEEXT_STR,SEC,FREC_CVE_N,EST_CVE_N,COOR,DIASEM) VALUES ('{0}',{1},{2},'{3}','{4}','{5}')";
                    bd.execSQL(  string.formatSql( query, lv.getCli_cveext_str(), lv.getSec(), lv.getFrec_cve_n() ,lv.getEst_cve_n(),lv.getCoor(),lv.getDiasem() )  );
                }
            }

            bd.close();
            Log.d("salida","Base de datos actualizada");
        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
