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

public class ServidorFragment extends Fragment {

    public ServidorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servidor, container, false);

        Log.d("salida", "SE CREO FAGMENT SERVIDOR");

        return view;
    }

}
