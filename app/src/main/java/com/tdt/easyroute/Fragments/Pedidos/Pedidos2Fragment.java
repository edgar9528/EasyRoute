package com.tdt.easyroute.Fragments.Pedidos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.CardViews.Adapter.PedidosAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Model.PedidosCardView;
import com.tdt.easyroute.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Pedidos2Fragment extends Fragment {


    public Pedidos2Fragment() {
        // Required empty public constructor
    }


    public static Pedidos2Fragment newInstance(String param1, String param2) {
        Pedidos2Fragment fragment = new Pedidos2Fragment();
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
        View view = inflater.inflate(R.layout.fragment_pedidos2, container, false);


        crearPedidos(view);


        return view;
    }

    private void crearPedidos(View view)
    {
        RecyclerView pedidosRecyclerView = view.findViewById(R.id.pedidosRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        pedidosRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<PedidosCardView> pedidosCardViews = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            pedidosCardViews.add( new PedidosCardView("cve"+i,"nombre"+i) );
        }

        PedidosAdapterRecyclerView pedidosAdapterRecyclerView = new PedidosAdapterRecyclerView(pedidosCardViews,R.layout.cardview_pedidos,getActivity());



    }


}
