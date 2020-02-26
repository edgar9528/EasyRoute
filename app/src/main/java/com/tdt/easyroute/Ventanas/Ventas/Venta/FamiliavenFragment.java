package com.tdt.easyroute.Ventanas.Ventas.Venta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
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
import java.util.Collections;


public class FamiliavenFragment extends Fragment {

    PedidosVM pedidosVM;
    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;
    private VentamainFragment ventamainFragment;

    private ArrayList<DataTableLC.ProductosPed> dtProductos;
    private ArrayList<DataTableLC.Familias> dtFam=null;
    private ArrayList<String> familiasAL=null;

    public FamiliavenFragment() {
        // Required empty public constructor
    }

    public static FamiliavenFragment newInstance(String param1, String param2) {
        FamiliavenFragment fragment = new FamiliavenFragment();
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
        View view= inflater.inflate(R.layout.fragment_familiaven, container, false);

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
                ObtenerFamilias();
            }
        });

    }



    private void ObtenerFamilias()
    {
        try
        {
            dtFam = new ArrayList<>();
            familiasAL = new ArrayList<>();

            DataTableLC.ProductosPed p;
            for (int i = 0; i < dtProductos.size(); i++) {
                p = dtProductos.get(i);
                if (!familiasAL.contains(p.getFam_desc_str())) {
                    familiasAL.add(p.getFam_desc_str());
                    dtFam.add(new DataTableLC.Familias(p.getFam_cve_n(), p.getFam_desc_str()));
                }
            }

            Collections.sort(dtFam);
            Collections.sort(familiasAL);

            TableRow tr;
            for (int i = 0; i < familiasAL.size(); i++)
            {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido1, null);
                ((TextView) tr.findViewById(R.id.t_titulo)).setText(familiasAL.get(i));
                tr.setTag(familiasAL.get(i));
                tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
                tableLayout.addView(tr);
            }

        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped21), e.getMessage());
        }
    }


    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            try
            {
                TableRow tr = ((TableRow) view);
                String familia = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

                //si no coincide, pinta todas de blanco
                for (int i = 0; i < familiasAL.size(); i++) {
                    TableRow row = (TableRow) vista.findViewWithTag(familiasAL.get(i));
                    row.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                pedidosVM.setFamilia(familia);
                ventamainFragment.goPresentacion();

            }catch (Exception e)
            {
                Utils.msgError(getContext(), getString(R.string.err_ped21), e.getMessage());
            }
        }
    };

}
