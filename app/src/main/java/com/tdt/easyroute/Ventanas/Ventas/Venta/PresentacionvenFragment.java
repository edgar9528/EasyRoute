package com.tdt.easyroute.Ventanas.Ventas.Venta;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;


public class PresentacionvenFragment extends Fragment {


    public PresentacionvenFragment() {
        // Required empty public constructor
    }


    public static PresentacionvenFragment newInstance(String param1, String param2) {
        PresentacionvenFragment fragment = new PresentacionvenFragment();
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
        View view = inflater.inflate(R.layout.fragment_familiaven, container, false);



        return view;
    }

}
