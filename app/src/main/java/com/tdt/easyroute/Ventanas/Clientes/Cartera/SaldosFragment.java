package com.tdt.easyroute.Ventanas.Clientes.Cartera;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.TextView;

import com.tdt.easyroute.CardViews.Adapter.CarteraAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Model.CarteraCardView;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.ClientesVM;

import java.util.ArrayList;

public class SaldosFragment extends Fragment {

    private ClientesVM clientesVM;
    private CarteraFragment fragment;
    private TextView tv_cliente,tv_negocio,tv_razon,tv_cve;
    private String negocio,razonSocial;

    private RecyclerView pedidosRecyclerView;
    private ArrayList<DataTableLC.Saldos> dgSaldos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientesVM = ViewModelProviders.of ( getParentFragment() ).get(ClientesVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saldos, container, false);

        try {

            fragment = (CarteraFragment) getParentFragment();
            Button b_imprimir, b_salir;

            tv_cve = view.findViewById(R.id.tv_cve);
            tv_razon = view.findViewById(R.id.tv_razon);
            tv_negocio = view.findViewById(R.id.tv_negocio);
            tv_cliente = view.findViewById(R.id.tv_cliente);
            b_imprimir = view.findViewById(R.id.button_imprimir);
            b_salir = view.findViewById(R.id.button_salir);

            b_imprimir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    regresar();
                }
            });

            b_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RegresarInicio(getActivity());
                }
            });


            pedidosRecyclerView = view.findViewById(R.id.carteraRecycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            pedidosRecyclerView.setLayoutManager(linearLayoutManager);
        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_cart4), e.getMessage() );
        }

        return view;
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clientesVM.getNegocio().observe(getParentFragment(), new Observer<String>() {
            @Override
            public void onChanged(String response) {
                negocio = response;
            }
        });

        clientesVM.getRazonSocial().observe(getParentFragment(), new Observer<String>() {
            @Override
            public void onChanged(String response) {
                razonSocial = response;
            }
        });

        clientesVM.getCli_cve().observe(getParentFragment(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                cargarSaldos(s);
            }
        });
    }


    private void cargarSaldos(String cve)
    {
        try
        {
            String consulta = string.formatSql( "select cr.cli_cve_n,c.cli_cveext_str,cr.cred_referencia_str, " +
                    "(cr.cred_monto_n-cr.cred_abono_n) cred_monto_n, coalesce(pag.abono,0) abono from creditos cr inner join clientes c " +
                    "on cr.cli_cve_n=c.cli_cve_n left join (select cli_cve_n,pag_referencia_str,sum(pag_abono_n) abono from pagos " +
                    "where pag_envase_n=0 and pag_cobranza_n=1 group by cli_cve_n,pag_referencia_str) pag on cr.cred_referencia_str=pag.pag_referencia_str " +
                    "where cr.cred_esenvase_n=0 and cr.cli_cve_n={0}",cve );

            String json = BaseLocal.Select(consulta,getContext());

            dgSaldos = ConvertirRespuesta.getSaldosJson(json);

            if(dgSaldos!=null)
            {
                mostrarSaldos(cve);
            }

        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Utils.msgError(getContext(), getString(R.string.err_cart5), e.getMessage() );
        }

    }


    private void mostrarSaldos(String cve)
    {
        try {

            String credito, monto, abono, saldoStr;
            double rsc = 0;

            ArrayList<CarteraCardView> carteraCardViews = new ArrayList<>();
            for (int i = 0; i < dgSaldos.size(); i++) {
                DataTableLC.Saldos s = dgSaldos.get(i);

                double saldo = Double.parseDouble(s.getCred_monto_n()) - Double.parseDouble(s.getAbono());
                rsc += saldo;

                credito = s.getCred_referencia_str();
                monto = string.FormatoPesos( s.getCred_monto_n() );
                abono = string.FormatoPesos(s.getAbono());
                saldoStr = string.FormatoPesos(saldo);

                carteraCardViews.add(new CarteraCardView(credito, monto, abono, saldoStr));
            }

            CarteraAdapterRecyclerView carteraAdapterRecyclerView = new CarteraAdapterRecyclerView(carteraCardViews, R.layout.cardview_cartera);
            pedidosRecyclerView.setAdapter(carteraAdapterRecyclerView);

            tv_cve.setText(dgSaldos.get(0).getCli_cveext_str());
            tv_negocio.setText(negocio);
            tv_razon.setText(razonSocial);
            String saldoC = getResources().getString(R.string.tv_saldoCliente) + string.FormatoPesos(rsc);
            tv_cliente.setText(saldoC);
        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_mostrarInfo), e.getMessage() );
        }

    }

    private void regresar()
    {
        fragment.goClientesFragment();
    }



}
