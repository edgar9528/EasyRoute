package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.R;


public class EnvasecredFragment extends Fragment {

    TextView tv_saldoDeudaEnv;

    public EnvasecredFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_envasecred, container, false);

        tv_saldoDeudaEnv = view.findViewById(R.id.tv_saldoDeudaEnv);
        tv_saldoDeudaEnv.setText(string.FormatoPesos(0));



        return view;
    }

}
