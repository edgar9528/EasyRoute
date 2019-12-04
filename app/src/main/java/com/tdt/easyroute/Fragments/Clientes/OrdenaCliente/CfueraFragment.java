package com.tdt.easyroute.Fragments.Clientes.OrdenaCliente;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.OrdenaClientesVM;

import java.util.ArrayList;

public class CfueraFragment extends Fragment {

    String dias[] = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
    String diasCve[] = {"cli_lun_n","cli_mar_n","cli_mie_n","cli_jue_n","cli_vie_n","cli_sab_n","cli_dom_n"};

    TableLayout tableLayout;
    LayoutInflater layoutInflater;

    Spinner sp_dias;

    boolean actualizoItem=false;

    public OrdenaClientesVM ordenaClientesVM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordenaClientesVM = ViewModelProviders.of( getParentFragment()).get(OrdenaClientesVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cfuera, container, false);

        sp_dias = view.findViewById(R.id.sp_dia);
        tableLayout = view.findViewById(R.id.tableLayout);
        layoutInflater = inflater;

        sp_dias.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, dias));

        sp_dias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(!actualizoItem)
                    ordenaClientesVM.setSelectItemFuera(i);

                actualizoItem=false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        int dia = Utils.diaActualL_D();
        sp_dias.setSelection(dia);

        mostrarTitulo();


        return view;
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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ordenaClientesVM.getClientesNodia().observe(getParentFragment(), new Observer<ClientesNodia>() {
            @Override
            public void onChanged(ClientesNodia s) {
                cargarClientesNoDia(s);
            }
        });

        ordenaClientesVM.getSelectItemDia().observe(getParentFragment(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                actualizoItem=true;
                sp_dias.setSelection(s);
                Log.d("salida","cambio item fragment dia: "+s);
            }
        });

    }


    private void cargarClientesNoDia(ClientesNodia clientesNodia)
    {
        boolean conDatos = clientesNodia.isConDatos();
        String diasem = clientesNodia.getFiltro();
        ArrayList<DataTableLC.ClientesOrdenar> dt = clientesNodia.getClientes();

        try
        {
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

        tableLayout.addView(tr);
    }



}
