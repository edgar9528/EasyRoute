package com.tdt.easyroute.Ventanas.Ventas.Pago;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tdt.easyroute.CardViews.Adapter.PagosAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Model.PagoCardView;
import com.tdt.easyroute.CardViews.PagosSwipeController;
import com.tdt.easyroute.CardViews.SwipeControllerActions;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Ventas.PedidosActivity;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class PagoFragment extends Fragment {

    FloatingActionButton fab;
    private  PedidosVM pedidosVM;
    private ArrayList<DataTableLC.ProductosPed> dgProd2;

    private String estadoCredito;

    private TextView tv_credito;
    private TextView tv_totalVenta;
    private TextView tv_contadoEsp;
    private TextView tv_kit;
    private TextView tv_saldo;



    public PagoFragment() {
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
        View view= inflater.inflate(R.layout.fragment_pago, container, false);

        fab = view.findViewById(R.id.fab);
        PedidosActivity pedidosActivity = (PedidosActivity) getActivity();
        estadoCredito = pedidosActivity.getEstadoCredito();

        tv_credito = view.findViewById(R.id.et_credito);
        tv_totalVenta = view.findViewById(R.id.et_totalVenta);
        tv_contadoEsp = view.findViewById(R.id.et_contado);
        tv_kit = view.findViewById(R.id.tv_kit);
        tv_saldo = view.findViewById(R.id.tv_saldo);

        tv_credito.setText(estadoCredito);

        RecyclerView pagosRecyclerView = view.findViewById(R.id.pagosRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pagosRecyclerView.setLayoutManager(linearLayoutManager);
        ArrayList<PagoCardView> pagoCardViews = new ArrayList<>();

        final PagosAdapterRecyclerView pagosAdapterRecyclerView = new PagosAdapterRecyclerView(pagoCardViews, R.layout.cardview_pagos);
        pagosRecyclerView.setAdapter(pagosAdapterRecyclerView);

        PagosSwipeController carritoSwipeController = new PagosSwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                pagosAdapterRecyclerView.eliminarItem(position);
            }
        }, getActivity());

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(carritoSwipeController);
        itemTouchhelper.attachToRecyclerView(pagosRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagosAdapterRecyclerView.agregarItem(new PagoCardView("1","1","Referencia","$2324","Santander","12345"));
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getDgPro2().observe(getActivity(), new Observer<ArrayList<DataTableLC.ProductosPed>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.ProductosPed> DgProd2) {
                dgProd2 = DgProd2;
                actualizarCampos();
            }
        });
    }

    private void actualizarCampos()
    {
        if(dgProd2!=null && dgProd2.size()>0)
        {

        }
        else
        {
            tv_totalVenta.setText("$0.00");
            tv_contadoEsp.setText("$0.00");
            tv_kit.setText("$0.00");
            tv_saldo.setText("$0.00");
        }
    }


}
