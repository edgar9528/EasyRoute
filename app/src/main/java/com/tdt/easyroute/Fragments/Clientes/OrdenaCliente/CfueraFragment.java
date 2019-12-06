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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
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
    String clienteSeleccionado="";
    ClientesNodia clientesNodia;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;

    Spinner sp_dias;

    boolean primerCambio=true,cambioDia=false;

    EditText et_filtro;
    Button b_buscar;
    ScrollView scrollView;

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
        vista=view;

        et_filtro = view.findViewById(R.id.et_filtro);
        b_buscar = view.findViewById(R.id.button_buscar);
        sp_dias = view.findViewById(R.id.sp_dia);
        tableLayout = view.findViewById(R.id.tableLayout);
        layoutInflater = inflater;
        scrollView = view.findViewById(R.id.scrollView);

        sp_dias.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, dias));

        sp_dias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(!primerCambio)
                {
                    if(!cambioDia)
                        ordenaClientesVM.setSelectItemFuera(i);
                    else
                        cambioDia=false;
                }
                else
                {
                    primerCambio=false;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        b_buscar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarItem(et_filtro.getText().toString());
            }
        });


        mostrarTitulo();


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ordenaClientesVM.getClientesNodia().observe(getParentFragment(), new Observer<ClientesNodia>() {
            @Override
            public void onChanged(ClientesNodia s) {
                clientesNodia = s;
                clienteSeleccionado="";
                cargarClientesNoDia();
            }
        });

        ordenaClientesVM.getSelectItemDia().observe(getParentFragment(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                cambioDia=true;
                sp_dias.setSelection(integer);
            }
        });

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

    private void cargarClientesNoDia()
    {
        boolean conDatos = clientesNodia.isConDatos();
        String diasem = clientesNodia.getFiltro();
        ArrayList<DataTableLC.ClientesOrdenar> dt = clientesNodia.getClientes();

        try
        {
            mostrarTitulo();

            for(int i=0; i<dt.size();i++)
            {
                if (conDatos)
                {
                    DataTableLC.ClientesOrdenar r = dt.get(i);
                    mostrarClientesDia(r.getCli_cveext_str(), r.getCli_nombrenegocio_str(), r.getSec(), r.getFrec_desc_str(), r.getEst_cve_n(), r.getCoor(), r.getDiasem());
                }
                else
                {
                    clientesNodia.getClientes().get(i).setSec("0");
                    clientesNodia.getClientes().get(i).setDiasem(diasem);
                    clientesNodia.getClientes().get(i).setCoor("0");

                    DataTableLC.ClientesOrdenar r = dt.get(i);
                    mostrarClientesDia(r.getCli_cveext_str(), r.getCli_nombrenegocio_str(), r.getSec(), r.getFrec_desc_str(), r.getEst_cve_n(), r.getCoor(), r.getDiasem());
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

        tr.setTag(cliente);
        tr.setOnClickListener(tableListener);

        tableLayout.addView(tr);
    }

    private void buscarItem(String palabra)
    {
        String cliente;
        String nombre;
        boolean encontrado=false;

        if(!palabra.isEmpty()) {
            for (int i = 0; i < clientesNodia.getClientes().size(); i++) {
                TableRow row = (TableRow) vista.findViewWithTag(clientesNodia.getClientes().get(i).getCli_cveext_str());

                cliente = ((TextView) row.findViewById(R.id.t_cliente)).getText().toString();
                nombre = ((TextView) row.findViewById(R.id.t_nombre)).getText().toString();
                palabra = palabra.toLowerCase();

                if (cliente.toLowerCase().contains(palabra) || nombre.toLowerCase().contains(palabra))
                {
                    for (int j = 0; j < clientesNodia.getClientes().size(); j++)
                    {
                        TableRow tr = (TableRow) vista.findViewWithTag(clientesNodia.getClientes().get(j).getCli_cveext_str());
                        tr.setBackgroundColor(Color.WHITE);
                    }

                    row.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    clienteSeleccionado = cliente;
                    i = clientesNodia.getClientes().size();

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

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            String tag = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            if(!clienteSeleccionado.equals(tag))
            {
                for (int i = 0; i < clientesNodia.getClientes().size(); i++) {
                    TableRow row = (TableRow) vista.findViewWithTag(clientesNodia.getClientes().get(i).getCli_cveext_str());
                    row.setBackgroundColor(Color.WHITE);
                }
                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                clienteSeleccionado = tag;
            }
        }
    };


}
