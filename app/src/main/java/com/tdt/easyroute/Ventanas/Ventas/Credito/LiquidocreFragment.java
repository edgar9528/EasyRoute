package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.graphics.Color;
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
import android.widget.TextView;

import com.tdt.easyroute.CardViews.Adapter.CreditoLiqAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Adapter.PagosAdapterRecyclerView;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class LiquidocreFragment extends Fragment {

    private PedidosVM pedidosVM;
    private TextView tv_adeudoN,tv_adeudoE,tv_adeudoE2;
    private TextView tv_limCred;
    private TextView tv_vencido;
    private ArrayList<DataTableLC.AdeudoNormal> dgANormal;
    private ArrayList<DataTableLC.AdeudoNormal> dgAEspecial;
    private CreditoLiqAdapterRecyclerView creditoNormaAdapter;
    private CreditoLiqAdapterRecyclerView creditoEspecialAdapter;
    private String vencido;
    private String txtSaldoDeudaEnv;

    public LiquidocreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedidosVM = ViewModelProviders.of( getActivity() ).get(PedidosVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_liquidocre, container, false);

        try {

            tv_adeudoN = view.findViewById(R.id.tv_adeudoN);
            tv_adeudoE = view.findViewById(R.id.tv_adeudoE);
            tv_adeudoE2 = view.findViewById(R.id.tv_adeudoE1);
            tv_limCred = view.findViewById(R.id.tv_limCredito);
            tv_vencido = view.findViewById(R.id.tv_vencido);

            tv_adeudoN.setText(string.FormatoPesos(0));
            tv_adeudoE.setText(string.FormatoPesos(0));
            vencido = "0";
            txtSaldoDeudaEnv = "0";

            pedidosVM.setAdeudoN(tv_adeudoN.getText().toString());
            pedidosVM.setAdeudoE(tv_adeudoE.getText().toString());

            tv_adeudoE.setVisibility(View.GONE);
            tv_adeudoE2.setVisibility(View.GONE);

            dgANormal = new ArrayList<>();
            dgAEspecial = new ArrayList<>();

            RecyclerView normalRecycler = view.findViewById(R.id.normalRecycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            normalRecycler.setLayoutManager(linearLayoutManager);
            creditoNormaAdapter = new CreditoLiqAdapterRecyclerView(dgANormal, R.layout.cardview_credito_liq);
            normalRecycler.setAdapter(creditoNormaAdapter);

            RecyclerView especialRecycler = view.findViewById(R.id.especialRecycler);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
            linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
            especialRecycler.setLayoutManager(linearLayoutManager2);
            creditoEspecialAdapter = new CreditoLiqAdapterRecyclerView(dgAEspecial, R.layout.cardview_credito_liq);
            especialRecycler.setAdapter(creditoEspecialAdapter);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped35), e.getMessage());
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getTxtLimCred().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_limCred.setText( string.FormatoPesos(s) );
            }
        });

        pedidosVM.getDgANormal().observe(getActivity(), new Observer<ArrayList<DataTableLC.AdeudoNormal>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.AdeudoNormal> adeudoNormals) {
                dgANormal = adeudoNormals;
                creditoNormaAdapter.actualizar(dgANormal);
                actualizarTotales();
            }
        });

        pedidosVM.getDgAEspecial().observe(getActivity(), new Observer<ArrayList<DataTableLC.AdeudoNormal>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.AdeudoNormal> adeudoNormals) {
                dgAEspecial = adeudoNormals;
                creditoEspecialAdapter.actualizar(dgAEspecial);
                actualizarTotales();
            }
        });

        pedidosVM.getTxtVencido().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                vencido = s;
                actualizarTotales();
            }
        });

        pedidosVM.getTxtSaldoDeudaEnv().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtSaldoDeudaEnv = s;
            }
        });
    }

    private void actualizarTotales()
    {
        try {

            tv_vencido.setText(string.FormatoPesos(vencido));
            double res = Double.parseDouble(vencido);
            if (res > 0) {
                tv_vencido.setBackgroundColor(getResources().getColor(R.color.graficaNo));
                tv_vencido.setTextColor(Color.WHITE);
            } else {
                tv_vencido.setBackgroundColor(Color.WHITE);
                tv_vencido.setTextColor(getResources().getColor(R.color.titulos_grey));
            }


            float sum = 0;
            for (DataTableLC.AdeudoNormal an : dgANormal)
                sum += Double.parseDouble(an.getSaldo());
            tv_adeudoN.setText(string.FormatoPesos(sum));

            sum = 0;
            for (DataTableLC.AdeudoNormal ae : dgAEspecial)
                sum += Double.parseDouble(ae.getSaldo());
            tv_adeudoE.setText(string.FormatoPesos(sum));

            double saldoDeudaEnv = Double.parseDouble(string.DelCaracteres(txtSaldoDeudaEnv));
            double adeudoN = Double.parseDouble(string.DelCaracteres(tv_adeudoN.getText().toString()));
            double adeudoE = Double.parseDouble(string.DelCaracteres(tv_adeudoE.getText().toString()));
            double saldoCredito = saldoDeudaEnv + adeudoN + adeudoE;

            pedidosVM.setTxtSaldoCredito(String.valueOf(saldoCredito));

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped34), e.getMessage());
        }

    }


}
