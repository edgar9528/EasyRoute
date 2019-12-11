package com.tdt.easyroute.Fragments.FinDeDia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;

public class FindiaFragment extends Fragment {

    private static int opcion;

    public static FindiaFragment newInstance(int op) {
        FindiaFragment fragment = new FindiaFragment();
        Bundle args = new Bundle();
        opcion = op;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_findia, container, false);

        //transmitir 0
        //borrar datos 1
        //fin de ventas 2





        return view;
    }


}
