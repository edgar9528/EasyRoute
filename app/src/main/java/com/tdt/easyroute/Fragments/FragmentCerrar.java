package com.tdt.easyroute.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;

public class FragmentCerrar extends Fragment {

    private static final String KEY_TITLE="Perfil";
    private static String titulo;

    String user_id="",user_nombre="",user_correo="",user_contrasena="";

    public FragmentCerrar() {
        // Required empty public constructor
    }

    public static FragmentCerrar newInstance(String param1) {
        FragmentCerrar fragment = new FragmentCerrar();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, param1);
        fragment.setArguments(args);

        titulo=param1;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_cerrar, container, false);

        obtenerUsuario();




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title= getArguments().getString(KEY_TITLE);
    }

    public void obtenerUsuario()
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("LoginPreferences",Context.MODE_PRIVATE);
        user_id = sharedPref.getString("user_id","null");
        user_nombre = sharedPref.getString("user_nombre","null");
        user_correo = sharedPref.getString("user_correo","null");
        user_contrasena = sharedPref.getString("user_contrasena","null");
    }

}
