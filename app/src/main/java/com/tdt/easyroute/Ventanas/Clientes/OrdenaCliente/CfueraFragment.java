package com.tdt.easyroute.Ventanas.Clientes.OrdenaCliente;

import android.graphics.Typeface;
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

    private String[] dias;
    private String clienteSeleccionado="";
    private ClientesNodia clientesNodia;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;

    private Spinner sp_dias;
    private boolean primerCambio=true,cambioDia=false;
    private EditText et_filtro;
    private ScrollView scrollView;

    private OrdenaClientesVM ordenaClientesVM;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordenaClientesVM = ViewModelProviders.of( getParentFragment()).get(OrdenaClientesVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cfuera, container, false);

        try {

            vista = view;
            String strDias = getResources().getString(R.string.diasSemana);
            dias = strDias.split(",");

            Button b_buscar, b_agregar, b_salir, b_enviar;

            et_filtro = view.findViewById(R.id.et_filtro);
            b_buscar = view.findViewById(R.id.button_buscar);
            b_agregar = view.findViewById(R.id.b_agregar);
            b_salir = view.findViewById(R.id.b_salir);
            b_enviar = view.findViewById(R.id.b_enviar);
            sp_dias = view.findViewById(R.id.sp_dia);
            tableLayout = view.findViewById(R.id.tableLayout);
            layoutInflater = inflater;
            scrollView = view.findViewById(R.id.scrollView);

            sp_dias.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, dias));

            sp_dias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (!primerCambio) {
                        if (!cambioDia)
                            ordenaClientesVM.setSelectItemFuera(i);
                        else
                            cambioDia = false;
                    } else {
                        primerCambio = false;
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

            b_agregar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    agregarItem();
                }
            });

            b_enviar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ordenaClientesVM.setEnviar(true);
                }
            });

            b_salir.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.RegresarInicio(getActivity());
                }
            });


            mostrarTitulo();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ord6), e.getMessage() );
        }

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
        try {
            tableLayout.removeAllViews();

            TableRow tr;

            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_ordclientes, null);

            ((TextView) tr.findViewById(R.id.t_cliente)).setTypeface(((TextView) tr.findViewById(R.id.t_cliente)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_nombre)).setTypeface(((TextView) tr.findViewById(R.id.t_nombre)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_secu)).setTypeface(((TextView) tr.findViewById(R.id.t_secu)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_frec)).setTypeface(((TextView) tr.findViewById(R.id.t_frec)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_estatus)).setTypeface(((TextView) tr.findViewById(R.id.t_estatus)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_coor)).setTypeface(((TextView) tr.findViewById(R.id.t_coor)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_dia)).setTypeface(((TextView) tr.findViewById(R.id.t_dia)).getTypeface(), Typeface.BOLD);

            tableLayout.addView(tr);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_mostrarInfo), e.getMessage() );
        }
    }

    private void cargarClientesNoDia()
    {
        boolean conDatos = clientesNodia.isConDatos();
        String diasem = clientesNodia.getFiltro();
        ArrayList<DataTableLC.ClientesOrdenar> dt = clientesNodia.getClientes();
        clienteSeleccionado="";

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
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage() );
        }
    }

    private void mostrarClientesDia(String cliente, String nombre, String secu, String frec, String status, String coor, String dia)
    {
        try {
            TableRow tr;
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_ordclientes, null);

            ((TextView) tr.findViewById(R.id.t_cliente)).setText(cliente);
            ((TextView) tr.findViewById(R.id.t_nombre)).setText(nombre);
            ((TextView) tr.findViewById(R.id.t_secu)).setText(secu);
            ((TextView) tr.findViewById(R.id.t_frec)).setText(frec);
            ((TextView) tr.findViewById(R.id.t_estatus)).setText(status);
            ((TextView) tr.findViewById(R.id.t_coor)).setText(coor);
            ((TextView) tr.findViewById(R.id.t_dia)).setText(dia);

            tr.setTag(cliente);
            tr.setOnClickListener(tableListener);

            tableLayout.addView(tr);
        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_mostrarInfo), e.getMessage() );
        }
    }

    private void buscarItem(String palabra)
    {
        try {
            String cliente;
            String nombre;
            boolean encontrado = false;

            if (!palabra.isEmpty()) {
                for (int i = 0; i < clientesNodia.getClientes().size(); i++) {
                    TableRow row = vista.findViewWithTag(clientesNodia.getClientes().get(i).getCli_cveext_str());

                    cliente = ((TextView) row.findViewById(R.id.t_cliente)).getText().toString();
                    nombre = ((TextView) row.findViewById(R.id.t_nombre)).getText().toString();
                    palabra = palabra.toLowerCase();

                    if (cliente.toLowerCase().contains(palabra) || nombre.toLowerCase().contains(palabra)) {
                        for (int j = 0; j < clientesNodia.getClientes().size(); j++) {
                            TableRow tr = vista.findViewWithTag(clientesNodia.getClientes().get(j).getCli_cveext_str());
                            tr.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                        }

                        row.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        clienteSeleccionado = cliente;
                        i = clientesNodia.getClientes().size();

                        scrollView.scrollTo(0, row.getTop());

                        encontrado = true;
                    }
                }

                if (!encontrado)
                    Toast.makeText(getContext(), getString(R.string.tt_noEncontrado), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(getContext(), getString(R.string.tt_camposVacios), Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_buscarInfo), e.getMessage() );
        }

    }

    private void agregarItem()
    {
        try
        {
            if (!clienteSeleccionado.isEmpty()) {
                int indice = getClienteSeleccionado();
                ordenaClientesVM.setMoverItem(indice);
            } else
                Toast.makeText(getContext(), getString(R.string.tt_seleccionarUno), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ord4), e.getMessage() );
        }
    }

    private int getClienteSeleccionado()
    {
        int p=-1;
        for(int i=0; i<clientesNodia.getClientes().size(); i++)
        {
            if( clienteSeleccionado.equals( clientesNodia.getClientes().get(i).getCli_cveext_str() ) )
            {
                p=i;
                i=clientesNodia.getClientes().size()+1;
            }
        }
        return p;
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
                    TableRow row =  vista.findViewWithTag(clientesNodia.getClientes().get(i).getCli_cveext_str());
                    row.setBackgroundColor( getResources().getColor(R.color.bgDefault) );
                }
                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                clienteSeleccionado = tag;
            }
        }
    };


}
