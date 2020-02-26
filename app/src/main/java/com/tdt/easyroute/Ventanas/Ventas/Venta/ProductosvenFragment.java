package com.tdt.easyroute.Ventanas.Ventas.Venta;

import android.content.Context;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class ProductosvenFragment extends Fragment {

    private PedidosVM pedidosVM;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;

    private VentamainFragment ventamainFragment;

    private ArrayList<DataTableLC.ProductosPed> dtProductos;
    private ArrayList<String> productosAL=null;

    public ProductosvenFragment() {
        // Required empty public constructor
    }

    public static ProductosvenFragment newInstance(String param1, String param2) {
        ProductosvenFragment fragment = new ProductosvenFragment();
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

        Button b_borrar = view.findViewById(R.id.button_borrar);

        b_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

        pedidosVM.getPresentacion().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                String[] datos = s.split("-");
                ObtenerProductos( datos[0],datos[1] );
            }
        });

    }

    private void ObtenerProductos(String fam, String pres)
    {
        try {
            tableLayout.removeAllViews();
            productosAL = new ArrayList<>();

            for (DataTableLC.ProductosPed p : dtProductos) {
                if (p.getFam_desc_str().equals(fam) && p.getPres_desc_str().equals(pres))
                    productosAL.add(p.getProd_sku_str() + "  " + p.getProd_desc_str());
            }

            TableRow tr;
            for (int i = 0; i < productosAL.size(); i++) {

                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido1, null);
                ((TextView) tr.findViewById(R.id.t_titulo)).setText(productosAL.get(i));
                tr.setTag(productosAL.get(i));
                tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
                tableLayout.addView(tr);
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped23), e.getMessage());
        }
    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            try
            {
                TableRow tr = ((TableRow) view);
                String producto = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

                //si no coincide, pinta todas de blanco
                for (int i = 0; i < productosAL.size(); i++)
                {
                    TableRow row = (TableRow) vista.findViewWithTag(productosAL.get(i));
                    row.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                String cve=getCveProducto(producto);

                pedidosVM.setProducto(cve);
                ventamainFragment.goVenta();


            }catch (Exception e)
            {
                Utils.msgError(getContext(), getString(R.string.err_ped23), e.getMessage());
            }
        }
    };


    private String getCveProducto(String producto)
    {
        String cve ="";
        String[] datos = producto.split("  ");

        for(DataTableLC.ProductosPed p: dtProductos)
        {
            if( datos[0].equals(p.getProd_sku_str()) )
            {
                cve = p.getProd_cve_n();
                break;
            }
        }
        return cve;
    }

}
