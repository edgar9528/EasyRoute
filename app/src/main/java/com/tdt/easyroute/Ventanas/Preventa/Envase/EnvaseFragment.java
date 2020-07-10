package com.tdt.easyroute.Ventanas.Preventa.Envase;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class EnvaseFragment extends Fragment {

    private PedidosVM pedidosVM;
    private ArrayList<DataTableLC.EnvasesPreventa> dgEnvase;
    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;

    public EnvaseFragment() {
        // Required empty public constructor
    }
    public static EnvaseFragment newInstance(String param1, String param2) {
        EnvaseFragment fragment = new EnvaseFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedidosVM = ViewModelProviders.of(  getActivity()).get(PedidosVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_envase, container, false);

        tableLayout = view.findViewById(R.id.tableLayout);
        layoutInflater = inflater;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getDgEnvasePrev().observe(getActivity(), new Observer<ArrayList<DataTableLC.EnvasesPreventa>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.EnvasesPreventa> envasesPreventas) {
                dgEnvase = envasesPreventas;
                mostrarEnvases();
            }
        });

    }

    private void mostrarEnvases()
    {
        try {
            if(dgEnvase!=null)
            {
                tableLayout.removeAllViews();
                TableRow tr;

                for(int i=0; i<dgEnvase.size();i++)
                {
                    tr = (TableRow) layoutInflater.inflate(R.layout.tabla_envases, null);
                    DataTableLC.EnvasesPreventa e = dgEnvase.get(i);
                    //TextView abono = (TextView) tr.findViewById(R.id.dat2);
                    //TextView venta = (TextView) tr.findViewById(R.id.dat3);

                    ((TextView) tr.findViewById(R.id.t_sku)).setText( string.FormatoEntero(e.getProd_sku_str()) );
                    ((TextView) tr.findViewById(R.id.dat1)).setText( string.FormatoEntero( e.getProd_venta_n() ) );
                    ((TextView) tr.findViewById(R.id.dat2)).setText( string.FormatoEntero( e.getProd_precio_n() ) );
                    ((TextView) tr.findViewById(R.id.dat3)).setText( string.FormatoEntero( e.getProd_cargo_n() ) );
                    ((TextView) tr.findViewById(R.id.dat4)).setText( string.FormatoEntero( e.getProd_regalo_n() ) );
                    ((TextView) tr.findViewById(R.id.dat5)).setText( string.FormatoEntero( e.getProd_subtotal_n() ) );
                    //abono.setText( string.FormatoEntero( e.getAbono() ) );
                    //venta.setText( string.FormatoEntero( e.getVenta() ) );

                    /*abono.setTag("1");
                    venta.setTag("2");
                    abono.setId(i);
                    venta.setId(i);*/

                    //abono.setOnClickListener(elementoClick);
                    //venta.setOnClickListener(elementoClick);

                    tableLayout.addView(tr);
                }
            }
            Log.d("salida","ENTRO A LISTAR ENVASES");
        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_mostrarInfo), e.getMessage());
        }
    }

}
