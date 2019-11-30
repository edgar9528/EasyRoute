package com.tdt.easyroute.Fragments.Clientes;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    TableLayout tableLayout;
    LayoutInflater layoutInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buscarclientes, container, false);
        layoutInflater=inflater;


        tableLayout = view.findViewById(R.id.tableLayout);

        mostrarTitulo();


        return  view;
    }


    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        for(int i=0; i<5;i++) {
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_buscarclientes, null);

            ((TextView) tr.findViewById(R.id.t_idExt)).setTypeface(((TextView) tr.findViewById(R.id.t_idExt)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_Negocio)).setTypeface(((TextView) tr.findViewById(R.id.t_Negocio)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_Razon)).setTypeface(((TextView) tr.findViewById(R.id.t_Razon)).getTypeface(), Typeface.BOLD);

            tableLayout.addView(tr);
        }

    }

}
