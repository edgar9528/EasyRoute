package com.tdt.easyroute.Ventanas.Configuracion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;

public class UtilidadFragment extends Fragment {


    public UtilidadFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_utilidad, container, false);
        // Inflate the layout for this fragment

        //Log.d("salida", "SE CREO FAGMENT utilidad");

        return view;
    }


}
