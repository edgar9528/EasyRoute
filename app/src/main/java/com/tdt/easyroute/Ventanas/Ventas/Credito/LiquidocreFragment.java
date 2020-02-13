package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;


public class LiquidocreFragment extends Fragment {


    public LiquidocreFragment() {
        // Required empty public constructor
    }


    public static LiquidocreFragment newInstance(String param1, String param2) {
        LiquidocreFragment fragment = new LiquidocreFragment();
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
        View view= inflater.inflate(R.layout.fragment_liquidocre, container, false);



        return view;
    }


}
