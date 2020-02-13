package com.tdt.easyroute.Ventanas.Ventas.Pago;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;


public class PagoFragment extends Fragment {

    public PagoFragment() {
        // Required empty public constructor
    }


    public static PagoFragment newInstance(String param1, String param2) {
        PagoFragment fragment = new PagoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pago, container, false);



        return view;
    }


}
