package com.tdt.easyroute.Fragments.Clientes;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;

public class BuscarclientesFragment extends Fragment {

    private static boolean buscar;

    public BuscarclientesFragment() {
        // Required empty public constructor
    }

    public static BuscarclientesFragment newInstance(boolean Buscar) {
        BuscarclientesFragment fragment = new BuscarclientesFragment();
        Bundle args = new Bundle();
        buscar=Buscar;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buscarclientes, container, false);



        return  view;
    }

}
