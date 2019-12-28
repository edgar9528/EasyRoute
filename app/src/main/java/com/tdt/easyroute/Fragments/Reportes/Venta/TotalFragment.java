package com.tdt.easyroute.Fragments.Reportes.Venta;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.VentasDiaVM;

import java.util.ArrayList;

public class TotalFragment extends Fragment {

    ArrayList<DataTableLC.Dtcobros> dtcobros= null;

    VentasDiaVM ventasDiaVM;
    TableLayout tableLayout;
    LayoutInflater layoutInflater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ventasDiaVM = ViewModelProviders.of ( this ).get(VentasDiaVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);

        layoutInflater = inflater;
        tableLayout = view.findViewById(R.id.tableLayout);


        mostrarTitulo();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ventasDiaVM.getDtCobros().observe(getParentFragment(), new Observer< ArrayList<DataTableLC.Dtcobros>  >() {
            @Override
            public void onChanged( ArrayList<DataTableLC.Dtcobros> dtCobros) {
                dtcobros = dtCobros;
                mostrarCobros();
            }
        });
    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_ventasdinero, null);

        ((TextView) tr.findViewById(R.id.t_pago)).setTypeface( ((TextView) tr.findViewById(R.id.t_pago)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_monto)).setTypeface( ((TextView) tr.findViewById(R.id.t_monto)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);
    }

    private void mostrarCobros()
    {
        if(dtcobros!=null)
        {
            mostrarTitulo();

            TableRow tr;
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_ventasdinero, null);

            for(int i=0; i<dtcobros.size();i++)
            {
                ((TextView) tr.findViewById(R.id.t_pago)).setText(dtcobros.get(i).getTpago());
                ((TextView) tr.findViewById(R.id.t_monto)).setText(dtcobros.get(i).getMonto());
                tableLayout.addView(tr);
            }
        }
    }
}
