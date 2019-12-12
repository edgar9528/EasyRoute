package com.tdt.easyroute.Fragments.FinDeDia.Sugerido;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.SugeridoVM;

public class ProductoFragment extends Fragment {

    SugeridoVM sugeridoVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sugeridoVM = ViewModelProviders.of ( getParentFragment() ).get(SugeridoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_producto, container, false);

        Log.d("salida","creo producto");


        return view;
    }


}
