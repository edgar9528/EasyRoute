package com.tdt.easyroute.Fragments.Pedidos.DetallesCliente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;

import java.util.ArrayList;


public class DatosdetFragment extends Fragment {

    DataTableLC.PedidosLv rc;
    ArrayList<DataTableLC.Estatus> dtEst=null;

    Spinner sp_estado;

    public DatosdetFragment() {
        // Required empty public constructor
    }

    public static DatosdetFragment newInstance() {
        DatosdetFragment fragment = new DatosdetFragment();
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
        View view= inflater.inflate(R.layout.fragment_datosdet, container, false);
        MainDetallesActivity mainDetallesActivity = (MainDetallesActivity) getActivity();
        rc = mainDetallesActivity.getCliente();

        sp_estado = view.findViewById(R.id.spinnerEstadoCred);
        sp_estado.setEnabled(false);


        inicializar();




        return view;
    }

    private void inicializar()
    {

        try {

            String json,con;

            con= "select est_cve_str,est_desc_str from estatus where est_cve_str in ('A','I','B')";
            json = BaseLocal.Select(con,getContext());
            dtEst = ConvertirRespuesta.getEstatusDetJson(json);
            if(dtEst!=null)
            {
                ArrayList<String> estatus = new ArrayList<>();
                for(int i=0; i<dtEst.size();i++)
                    estatus.add(dtEst.get(i).getEst_desc_str());

                sp_estado.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, estatus));

            }

        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
