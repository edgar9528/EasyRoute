package com.tdt.easyroute.Fragments.Pedidos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.R;

public class PedidosFragment extends Fragment {

    Button b_actClientes, b_reimp, b_actCliente, b_buscar,
           b_selec, b_detalle, b_noven,b_salir;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;

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


        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });

        cargarClientes();


        return view;

    }

    private void cargarClientes()
    {
        TableRow tr;

        for(int i=0; i< 25;i++)
        {
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_visitacli, null);
            ((TextView) tr.findViewById(R.id.t_estado)).setText( "C" );
            ((TextView) tr.findViewById(R.id.t_cve)).setText( "2367" );
            ((TextView) tr.findViewById(R.id.t_nombre)).setText( "CREMERIA LUCERITO" );

            //tr.setTag(fam);
            //tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
            tableLayout.addView(tr);

        }
    }

}
