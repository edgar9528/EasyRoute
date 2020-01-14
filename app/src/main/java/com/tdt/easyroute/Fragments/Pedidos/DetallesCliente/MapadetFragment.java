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


public class MapadetFragment extends Fragment {

    DataTableLC.PedidosLv rc;

    public MapadetFragment() {
        // Required empty public constructor
    }
    public static MapadetFragment newInstance(String param1, String param2) {
        MapadetFragment fragment = new MapadetFragment();
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
        View view= inflater.inflate(R.layout.fragment_mapadet, container, false);

        MainDetallesActivity mainDetallesActivity = (MainDetallesActivity) getActivity();
        rc = mainDetallesActivity.getCliente();




        return view;
    }
}
