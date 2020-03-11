package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;


public class AbonoscredFragment extends Fragment {

    PedidosVM pedidosVM;
    TextView tv_totAbono,tv_saldoCredito,tv_saldoEnvase;

    public AbonoscredFragment() {
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
        View view= inflater.inflate(R.layout.fragment_abonoscred, container, false);

        tv_totAbono= view.findViewById(R.id.tv_totAbono);
        tv_saldoCredito = view.findViewById(R.id.tv_saldoCredito);
        tv_saldoEnvase = view.findViewById(R.id.tv_saldoEnvase);

        tv_saldoCredito.setText(string.FormatoPesos(0) );
        tv_saldoEnvase.setText(string.FormatoPesos(0) );

        tv_totAbono.setText(string.FormatoPesos(0) );
        pedidosVM.setTotAbono( tv_totAbono.getText().toString() );


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getTxtSaldoCredito().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_saldoCredito.setText( string.FormatoPesos(s) );
            }
        });



    }
}
