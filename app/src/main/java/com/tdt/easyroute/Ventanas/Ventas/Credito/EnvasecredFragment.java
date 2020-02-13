package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;


public class EnvasecredFragment extends Fragment {


    public EnvasecredFragment() {
        // Required empty public constructor
    }

    public static EnvasecredFragment newInstance(String param1, String param2) {
        EnvasecredFragment fragment = new EnvasecredFragment();
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
        View view= inflater.inflate(R.layout.fragment_envasecred, container, false);

        return view;
    }

}
