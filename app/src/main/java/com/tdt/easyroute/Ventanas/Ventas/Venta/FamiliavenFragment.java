package com.tdt.easyroute.Ventanas.Ventas.Venta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.tdt.easyroute.CardViews.Adapter.ProductosThreeListAdapter;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;


public class FamiliavenFragment extends Fragment {

    PedidosVM pedidosVM;
    private View vista;
    private VentamainFragment ventamainFragment;

    ExpandableListView expandableListView;
    AutoCompleteTextView tv_buscar;

    private ArrayList<DataTableLC.ProductosPed> dtProductos;
    private ArrayList<String> familiasAL=null;
    private ArrayList<String> presentacionesAL=null;
    private ArrayList<String> productosAL=null;
    private ArrayList<String> productosTotalAL;

    public FamiliavenFragment() {
        // Required empty public constructor
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

        tv_buscar = view.findViewById(R.id.tv_buscar);
        tv_buscar.setThreshold(1);
        tv_buscar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_buscar.setText("");
                String item = parent.getItemAtPosition(position).toString();
                ProductoSeleccionado(item);
            }
        });

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
                crearRecycler();
            }
        });

    }

    private void crearRecycler()
    {
        productosTotalAL= new ArrayList<>();
        List<String[]> secondLevel = new ArrayList<>();
        LinkedHashMap<String, String[]> thirdLevel;
        List<LinkedHashMap<String, String[]>> data = new ArrayList<>();

        ObtenerFamilias();
        String[] parent = familiasAL.toArray(new String[familiasAL.size()]);

        for(String f : familiasAL)
        {
            ObtenerPresentaciones(f);
            String[] second = presentacionesAL.toArray(new String[presentacionesAL.size()]);
            secondLevel.add(second);

            thirdLevel = new LinkedHashMap<>();
            for(String p: presentacionesAL)
            {
                ObtenerProductos(f,p);
                String[] third = productosAL.toArray(new String[productosAL.size()]);
                thirdLevel.put(p,third);
            }
            data.add(thirdLevel);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, productosTotalAL);
        tv_buscar.setAdapter(adapter);

        // expandable listview
        expandableListView = (ExpandableListView) vista.findViewById(R.id.expandible_listview);

        // parent adapter
        ProductosThreeListAdapter productosThreeListAdapterAdapter = new ProductosThreeListAdapter(getContext(), parent, secondLevel, data,this);

        // set adapter
        expandableListView.setAdapter(productosThreeListAdapterAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }

    private void ObtenerFamilias()
    {
        try
        {
            familiasAL = new ArrayList<>();

            DataTableLC.ProductosPed p;
            for (int i = 0; i < dtProductos.size(); i++)
            {
                p = dtProductos.get(i);
                if (!familiasAL.contains(p.getFam_desc_str()))
                {
                    familiasAL.add(p.getFam_desc_str());
                }
            }
            Collections.sort(familiasAL);

        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped21), e.getMessage());
        }
    }

    private void ObtenerPresentaciones(String fam)
    {
        try
        {
            presentacionesAL = new ArrayList<>();

            for (DataTableLC.ProductosPed p : dtProductos) {
                if (p.getFam_desc_str().equals(fam))
                    if (!presentacionesAL.contains(p.getPres_desc_str()))
                        presentacionesAL.add(p.getPres_desc_str());
            }

            Collections.sort(presentacionesAL);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped22), e.getMessage());
        }
    }

    private void ObtenerProductos(String fam, String pres)
    {
        try {
            productosAL = new ArrayList<>();

            for (DataTableLC.ProductosPed p : dtProductos) {
                if (p.getFam_desc_str().equals(fam) && p.getPres_desc_str().equals(pres))
                {
                    productosAL.add(p.getProd_sku_str() + "\n" + p.getProd_desc_str());
                    productosTotalAL.add( p.getProd_sku_str() + "\n" + p.getProd_desc_str() );
                }
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped23), e.getMessage());
        }
    }

    public void ProductoSeleccionado(String producto)
    {
        String cve = getCveProducto(producto);
        pedidosVM.setProducto(cve);
        ventamainFragment.goVenta();
    }

    private String getCveProducto(String producto)
    {
        String cve ="";
        String[] datos = producto.split("\n");

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
