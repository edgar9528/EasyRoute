package com.tdt.easyroute.Ventanas.Preventa.Pago;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tdt.easyroute.CardViews.Adapter.CreditoLiqAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Adapter.CreditosPrevAdapterRecyclerView;
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

    private CreditosPrevAdapterRecyclerView creditoAdapter;
    private ArrayList<DataTableLC.DgPagos> dgPagos;
    private ArrayList<DataTableLC.Creditos>  dgCreditos;

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

            tv_kit = view.findViewById(R.id.tv_kit);
            tv_saldo = view.findViewById(R.id.tv_saldo);


            tv_saldo.setText(string.FormatoPesos(0));
            tv_kit.setText(string.FormatoPesos(0));

            pedidosVM.setTxtSaldo( tv_saldo.getText().toString() );

            dgPagos = new ArrayList<>();
            dgCreditos = new ArrayList<>();

            RecyclerView creditosRecyclerView = view.findViewById(R.id.pagosRecycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            creditosRecyclerView.setLayoutManager(linearLayoutManager);

            creditoAdapter = new CreditosPrevAdapterRecyclerView(dgCreditos, R.layout.cardview_credito_liq, this);
            creditosRecyclerView.setAdapter(creditoAdapter);


        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_prev) ,e.getMessage());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try
        {
            pedidosVM.getDgCreditos().observe(getActivity(), new Observer<ArrayList<DataTableLC.Creditos>>() {
                @Override
                public void onChanged(ArrayList<DataTableLC.Creditos> creditos) {
                    dgCreditos = creditos;
                    for(int i=0; i<dgCreditos.size();i++)
                        creditoAdapter.agregarItems(dgCreditos.get(i));
                }
            });

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_prev) ,e.getMessage());
        }

    }

}