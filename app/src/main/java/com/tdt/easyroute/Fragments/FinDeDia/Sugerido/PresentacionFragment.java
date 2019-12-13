package com.tdt.easyroute.Fragments.FinDeDia.Sugerido;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.SugeridoVM;

import java.util.ArrayList;


public class PresentacionFragment extends Fragment {

    ArrayList<DataTableLC.Productos_Sug> dtProd=null;
    ArrayList<String> lvPresentacion=null;
    String familiaSelec;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;
    MainsugeridoFragment fragmentMain;
    SugeridoVM sugeridoVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sugeridoVM = ViewModelProviders.of ( getParentFragment() ).get(SugeridoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_familia, container, false);

        layoutInflater = inflater;
        vista=view;
        fragmentMain = (MainsugeridoFragment) getParentFragment();
        tableLayout = view.findViewById(R.id.tableLayout);


        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sugeridoVM.getDtProd().observe(getParentFragment(), new Observer<ArrayList<DataTableLC.Productos_Sug>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.Productos_Sug> dtprod) {
                dtProd = dtprod;
            }
        });

        sugeridoVM.getFamilia().observe(getParentFragment(), new Observer< String >() {
            @Override
            public void onChanged(String familia) {
                familiaSelec=familia;
                obtenerPresentaciones();
            }
        });

    }


    private void obtenerPresentaciones()
    {
        if(dtProd!=null)
        {
            lvPresentacion = new ArrayList<>();
            tableLayout.removeAllViews();
            String presentacion;

            for (int i = 0; i < dtProd.size(); i++)
            {
                if (familiaSelec.equals(dtProd.get(i).getFam_desc_str()))
                {
                    presentacion = dtProd.get(i).getPres_desc_str();

                    if(!lvPresentacion.contains( presentacion ))
                    {
                        lvPresentacion.add(presentacion);

                        TableRow tr;

                        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido1, null);
                        ((TextView) tr.findViewById(R.id.t_titulo)).setText( presentacion );
                        tr.setTag(presentacion);
                        tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
                        tableLayout.addView(tr);
                    }
                }
            }

        }
    }


    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            String presentacion = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            for(int i=0; i<lvPresentacion.size();i++)
            {
                TableRow row = (TableRow)vista.findViewWithTag(lvPresentacion.get(i));
                row.setBackgroundColor( getResources().getColor(R.color.bgDefault) );
            }

            //pinta de azul la fila y actualiza la cve de la fila seccionada
            tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );

            sugeridoVM.setPresentacion(presentacion);
            fragmentMain.goProducto();
        }
    };

}
