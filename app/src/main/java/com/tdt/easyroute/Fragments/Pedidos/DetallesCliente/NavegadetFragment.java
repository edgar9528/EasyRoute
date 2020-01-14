package com.tdt.easyroute.Fragments.Pedidos.DetallesCliente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;


public class NavegadetFragment extends Fragment {

    DataTableLC.PedidosLv rc;

    public static NavegadetFragment newInstance(String param1, String param2) {
        NavegadetFragment fragment = new NavegadetFragment();
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
        View view = inflater.inflate(R.layout.fragment_navegadet, container, false);

        MainDetallesActivity mainDetallesActivity = (MainDetallesActivity) getActivity();
        rc = mainDetallesActivity.getCliente();



        return view;
    }


}
