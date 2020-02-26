package com.tdt.easyroute.Ventanas.Ventas.Venta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.tdt.easyroute.CardViews.Adapter.VentaAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Model.VentaCardView;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class VentavenFragment extends Fragment {

    private PedidosVM pedidosVM;

    private View vista;
    private VentamainFragment ventamainFragment;

    private ArrayList<DataTableLC.ProductosPed> dtProductos;
    private ArrayList<DataTableLC.ProductosPed> dgProd2;

    VentaAdapterRecyclerView ventaAdapterRecyclerView;

    public VentavenFragment() {
        // Required empty public constructor
    }

    public static VentavenFragment newInstance(String param1, String param2) {
        VentavenFragment fragment = new VentavenFragment();
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
        View view = inflater.inflate(R.layout.fragment_ventaven, container, false);
        ventamainFragment = (VentamainFragment) getParentFragment();

        vista=view;


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

        pedidosVM.getDgPro2().observe(getActivity(), new Observer<ArrayList<DataTableLC.ProductosPed>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.ProductosPed> DgProd2) {
                dgProd2 = DgProd2;
            }
        });

        pedidosVM.getProducto().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String cve_prod) {
                AgregarProducto(cve_prod);
            }
        });
    }


    private void AgregarProducto(String cve_producto)
    {
        DataTableLC.ProductosPed re = null;

        re = BuscarProducto(cve_producto, dgProd2);
        if(re!=null)
        {
            Log.d("salida", "ya existe en el arreglo");
            actualizarProductos();
        }
        else
        {
            re= BuscarProducto(cve_producto, dtProductos);
            if(re!=null)
            {
                dgProd2.add(re);
                pedidosVM.setDgPro2(dgProd2);

                actualizarProductos();
            }
        }

    }

    private DataTableLC.ProductosPed BuscarProducto(String cve, ArrayList<DataTableLC.ProductosPed> arrayList)
    {
        DataTableLC.ProductosPed producto = null;
        for(int i=0; i<arrayList.size();i++)
        {
            if(cve.equals( arrayList.get(i).getProd_cve_n() ))
            {
                producto= arrayList.get(i);
                i=arrayList.size();
            }
        }
        return producto;
    }

    private void actualizarProductos()
    {
        if (dgProd2 != null)
        {
            RecyclerView ventasRecyclerView = vista.findViewById(R.id.ventasRecycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            ventasRecyclerView.setLayoutManager(linearLayoutManager);
            ArrayList<VentaCardView> ventasCardViews = new ArrayList<>();

            for (DataTableLC.ProductosPed p: dgProd2)
            {
                ventasCardViews.add(new VentaCardView(p.getProd_cve_n(),
                        p.getProd_sku_str(),
                        p.getProd_desc_str(),
                        p.getLpre_precio_n(),
                        p.getProd_cant_n(),
                        String.valueOf(  Double.parseDouble(p.getLpre_precio_n())* Double.parseDouble(p.getProd_cant_n()) )));
            }

            ventaAdapterRecyclerView = new VentaAdapterRecyclerView(ventasCardViews, R.layout.cardview_venta);

            ventasRecyclerView.setAdapter(ventaAdapterRecyclerView);

        }
    }



}
