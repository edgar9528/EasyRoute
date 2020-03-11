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
import com.tdt.easyroute.Ventanas.Ventas.PedidosActivity;
import com.tdt.easyroute.ViewModel.PedidosVM;


public class EnvasecredFragment extends Fragment {


    PedidosVM pedidosVM;
    private PedidosActivity pedidosActivity;
    TextView tv_saldoDeudaEnv;

    public EnvasecredFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedidosVM = ViewModelProviders.of(  getActivity()).get(PedidosVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_envasecred, container, false);

        pedidosActivity = (PedidosActivity) getActivity();
        tv_saldoDeudaEnv = view.findViewById(R.id.tv_saldoDeudaEnv);
        tv_saldoDeudaEnv.setText(string.FormatoPesos(0));
        pedidosVM.setTxtSaldoDeudaEnv( tv_saldoDeudaEnv.getText().toString() );

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getTxtSaldoDeudaEnv().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String saldoDeudaEnv) {
                tv_saldoDeudaEnv.setText( string.FormatoPesos(saldoDeudaEnv) );
            }
        });

    }
}
