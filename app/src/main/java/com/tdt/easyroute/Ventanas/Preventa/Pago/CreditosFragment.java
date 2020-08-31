package com.tdt.easyroute.Ventanas.Preventa.Pago;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tdt.easyroute.CardViews.Adapter.CreditosPrevAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Adapter.PagosPrevAdapterRecyclerView;
import com.tdt.easyroute.CardViews.PagosSwipeController;
import com.tdt.easyroute.CardViews.SwipeControllerActions;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Preventa.PreventaActivity;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class CreditosFragment extends Fragment {

    private PedidosVM pedidosVM;
    private CreditosPrevAdapterRecyclerView pagosAdapterRecyclerView;
    private ArrayList<DataTableLC.ProductosPed> dgProd2;
    private ArrayList<DataTableWS.FormasPago> formasPago;
    private ArrayList<DataTableLC.DgPagos> dgPagos;
    private DataTableLC.DtCliVentaNivel rc;
    private PreventaActivity pedidosActivity;

    private String estadoCredito;

    private TextView tv_kit;
    private TextView tv_saldo;

    public CreditosFragment() {
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
        View view= inflater.inflate(R.layout.fragment_creditos, container, false);

        try {

            pedidosActivity = (PreventaActivity) getActivity();

            rc = pedidosActivity.getRC();

            tv_kit = view.findViewById(R.id.tv_kit);
            tv_saldo = view.findViewById(R.id.tv_saldo);


            tv_saldo.setText(string.FormatoPesos(0));
            tv_kit.setText(string.FormatoPesos(0));
            //pedidosActivity.setTextKit(tv_kit.getText().toString());

            pedidosVM.setTxtSaldo( tv_saldo.getText().toString() );

            dgPagos = new ArrayList<>();
            dgProd2 = new ArrayList<>();

            RecyclerView pagosRecyclerView = view.findViewById(R.id.pagosRecycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            pagosRecyclerView.setLayoutManager(linearLayoutManager);

            pagosAdapterRecyclerView = new CreditosPrevAdapterRecyclerView(dgPagos, R.layout.cardview_pagos, this);
            pagosRecyclerView.setAdapter(pagosAdapterRecyclerView);

            PagosSwipeController carritoSwipeController = new PagosSwipeController(new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {
                    pagosAdapterRecyclerView.eliminarItem(position);
                }
            }, getActivity());

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(carritoSwipeController);
            itemTouchhelper.attachToRecyclerView(pagosRecyclerView);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped32) ,e.getMessage());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        pedidosVM.getFormasPago().observe(getActivity(), new Observer< ArrayList<DataTableWS.FormasPago>  >() {
            @Override
            public void onChanged( ArrayList<DataTableWS.FormasPago>  formas) {
                formasPago = formas;
            }
        });


    }

    private void actualizarTotales()
    {
        try
        {


        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_actInfo) ,e.getMessage());
        }

    }



    public void ActualizarDgPagos(ArrayList<DataTableLC.DgPagos> dgRecycler)
    {
        try {
            dgPagos = dgRecycler;

            double DescKit = Double.parseDouble(string.DelCaracteres(tv_kit.getText().toString()));

            double totPagos = 0;
            for (DataTableLC.DgPagos p : dgPagos)
                totPagos += Double.parseDouble(string.DelCaracteres(p.getFpag_cant_n()));

            //double txtVenta = Double.parseDouble(string.DelCaracteres(tv_totalVenta.getText().toString()));
            //double tot = txtVenta - DescKit - totPagos;

            tv_saldo.setText(string.FormatoPesos(0));
            pedidosVM.setTxtSaldo( tv_saldo.getText().toString() );
            pedidosVM.setDgPagos( dgPagos );

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped33) ,e.getMessage());
        }
    }

}