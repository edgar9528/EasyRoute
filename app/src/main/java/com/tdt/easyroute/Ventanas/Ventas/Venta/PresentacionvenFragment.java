package com.tdt.easyroute.Ventanas.Ventas.Venta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class PresentacionvenFragment extends Fragment {

    private PedidosVM pedidosVM;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;

    private VentamainFragment ventamainFragment;

    private String familia;
    private ArrayList<DataTableLC.ProductosPed> dtProductos;
    private ArrayList<String> presentacionesAL=null;

    public PresentacionvenFragment() {
        // Required empty public constructor
    }

    public static PresentacionvenFragment newInstance(String param1, String param2) {
        PresentacionvenFragment fragment = new PresentacionvenFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedidosVM = ViewModelProviders.of(  getActivity()).get(PedidosVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_familiaven, container, false);

        tableLayout = view.findViewById(R.id.tableLayout);
        layoutInflater = getLayoutInflater();
        vista=view;

        ventamainFragment = (VentamainFragment) getParentFragment();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getDtProductos().observe(getActivity(), new Observer<ArrayList<DataTableLC.ProductosPed>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.ProductosPed> productosPeds) {
                dtProductos = productosPeds;
            }
        });

        pedidosVM.getFamilia().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                familia=s;
                ObtenerPresentaciones(s);
            }
        });

    }

    private void ObtenerPresentaciones(String fam)
    {
        try {
            tableLayout.removeAllViews();
            presentacionesAL = new ArrayList<>();

            for (DataTableLC.ProductosPed p : dtProductos) {
                if (p.getFam_desc_str().equals(fam))
                    if (!presentacionesAL.contains(p.getPres_desc_str()))
                        presentacionesAL.add(p.getPres_desc_str());
            }

            Collections.sort(presentacionesAL);

            TableRow tr;
            for (int i = 0; i < presentacionesAL.size(); i++) {

                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido1, null);
                ((TextView) tr.findViewById(R.id.t_titulo)).setText(presentacionesAL.get(i));
                tr.setTag(presentacionesAL.get(i));
                tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
                tableLayout.addView(tr);
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped22), e.getMessage());
        }
    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            try
            {
                TableRow tr = ((TableRow) view);
                String presentacion = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

                //si no coincide, pinta todas de blanco
                for (int i = 0; i < presentacionesAL.size(); i++) {
                    TableRow row = (TableRow) vista.findViewWithTag(presentacionesAL.get(i));
                    row.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                pedidosVM.setPresentacion(familia+"-"+presentacion);
                ventamainFragment.goProductos();


            }catch (Exception e)
            {
                Utils.msgError(getContext(), getString(R.string.err_ped22), e.getMessage());
            }
        }
    };

}
