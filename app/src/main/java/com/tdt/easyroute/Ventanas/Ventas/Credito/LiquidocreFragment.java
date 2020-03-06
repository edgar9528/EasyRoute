package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;


public class LiquidocreFragment extends Fragment {

    PedidosVM pedidosVM;
    TextView tv_adeudoN;

    public LiquidocreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedidosVM = ViewModelProviders.of( getActivity()).get(PedidosVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_liquidocre, container, false);

        tv_adeudoN = view.findViewById(R.id.tv_adeudoN);
        tv_adeudoN.setText(string.FormatoPesos(0));
        pedidosVM.setAdeudoN( tv_adeudoN.getText().toString() );




        return view;
    }
}
