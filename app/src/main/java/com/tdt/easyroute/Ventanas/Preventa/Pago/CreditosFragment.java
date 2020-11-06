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
import java.util.Date;


public class CreditosFragment extends Fragment {

    private PedidosVM pedidosVM;

    private DataTableLC.DtCliVentaNivel rc;
    private PreventaActivity pedidosActivity;
    private CreditosPrevAdapterRecyclerView creditoAdapter;
    private ArrayList<DataTableLC.Creditos>  dgCreditos;

    private TextView tv_limite;
    private TextView tv_disponible;
    private double LimCred = 0;

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

            LimCred = Double.parseDouble(rc.getCli_montocredito_n());

            tv_limite = view.findViewById(R.id.tv_kit);
            tv_disponible = view.findViewById(R.id.tv_saldo);

            tv_limite.setText( string.FormatoPesos( rc.getCli_montocredito_n() ) );
            tv_disponible.setText(string.FormatoPesos(0));

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
                    CalcularSaldoCredito();
                }
            });

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_prev) ,e.getMessage());
        }

    }

    private void CalcularSaldoCredito()
    {
        try
        {
            double Saldo = 0;
            double Vencido = 0;
            double Disponible = 0;

            for(int i=0; i< dgCreditos.size();i++)
            {
                Saldo += Double.parseDouble(dgCreditos.get(i).getCred_saldo_n());
            }

            int diascred = 0;

            diascred = Integer.parseInt(rc.getCli_plazocredito_n());

            for(int i=0; i<dgCreditos.size();i++)
            {
                Date f1 = Utils.FechaDT( dgCreditos.get(i).getCred_fecha_dt() );
                Date f2 = Utils.FechaDT( Utils.FechaModificarDias( Utils.FechaLocal(),-diascred ) );
                if(f1.compareTo(f2)<=0)
                    Vencido += Double.parseDouble( dgCreditos.get(i).getCred_saldo_n() );
            }

            Disponible = LimCred - Saldo;

            pedidosVM.setTxtSaldoCredito(string.FormatoPesos(Saldo));
            pedidosVM.setTxtVencido(string.FormatoPesos(Vencido));
            tv_disponible.setText(string.FormatoPesos(Disponible));


        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_prev) ,e.getMessage());
        }
    }

}