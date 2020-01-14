package com.tdt.easyroute.Fragments.Pedidos.DetallesCliente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;


public class DomiciliodetFragment extends Fragment {

    DataTableLC.PedidosLv rc;

    public static DomiciliodetFragment newInstance() {
        DomiciliodetFragment fragment = new DomiciliodetFragment();
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
        View view= inflater.inflate(R.layout.fragment_domiciliodet, container, false);

        MainDetallesActivity mainDetallesActivity = (MainDetallesActivity) getActivity();
        rc = mainDetallesActivity.getCliente();





        return view;
    }

    private void cargarDirecciones()
    {
        try
        {
            String con, json;

            con= string.formatSql( "select * from direcciones where cli_cve_n={0}", rc.getCli_cve_n() );
            json= BaseLocal.Select(con,getContext());


        }
        catch (Exception ex)
        {
            Log.d("salida", "Error"+ ex.getMessage());
            Toast.makeText(getContext(), "Error al mostrar direcciones: "+ ex.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

}
