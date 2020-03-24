package com.tdt.easyroute.Ventanas.Ventas.Credito;

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
import android.widget.Toast;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Ventas.PedidosActivity;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class EnvasecredFragment extends Fragment {


    PedidosVM pedidosVM;
    private PedidosActivity pedidosActivity;
    TextView tv_saldoDeudaEnv;
    ArrayList<DataTableLC.EnvasesAdeudo> dgDeudaEnv;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;
    private TextView tv_anterior;

    public EnvasecredFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedidosVM = ViewModelProviders.of(  getActivity()).get(PedidosVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_envasecred, container, false);

        pedidosActivity = (PedidosActivity) getActivity();
        tv_saldoDeudaEnv = view.findViewById(R.id.tv_saldoDeudaEnv);
        tv_saldoDeudaEnv.setText(string.FormatoPesos(0));
        pedidosVM.setTxtSaldoDeudaEnv( tv_saldoDeudaEnv.getText().toString() );

        tableLayout = view.findViewById(R.id.tableLayout);
        layoutInflater = inflater;
        vista=view;
        tv_anterior = new TextView(getContext());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getTxtSaldoDeudaEnv().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String saldoDeudaEnv) {
                tv_saldoDeudaEnv.setText( string.FormatoPesos(saldoDeudaEnv) );
            }
        });

        pedidosVM.getDgDeudaEnv().observe(getActivity(), new Observer<ArrayList<DataTableLC.EnvasesAdeudo>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.EnvasesAdeudo> envasesAdeudos) {
                dgDeudaEnv = envasesAdeudos;
                mostrarEnvases();
            }
        });

    }

    private void mostrarEnvases()
    {
        try {
            if(dgDeudaEnv!=null)
            {
                TableRow tr;

                for(int i=0; i<dgDeudaEnv.size();i++)
                {
                    tr = (TableRow) layoutInflater.inflate(R.layout.tabla_credenvases, null);
                    DataTableLC.EnvasesAdeudo e = dgDeudaEnv.get(i);
                    TextView abono = (TextView) tr.findViewById(R.id.dat2);
                    TextView venta = (TextView) tr.findViewById(R.id.dat3);

                    ((TextView) tr.findViewById(R.id.t_sku)).setText(e.getProd_sku_str());
                    ((TextView) tr.findViewById(R.id.dat1)).setText(e.getAbono());
                    abono.setText(e.getAdeudo());
                    venta.setText(e.getProd_cantiv_n());
                    ((TextView) tr.findViewById(R.id.dat4)).setText(e.getSubTotal());

                    abono.setTag("1");
                    venta.setTag("2");
                    abono.setId(i);
                    venta.setId(i);

                   abono.setOnClickListener(elementoClick);
                   venta.setOnClickListener(elementoClick);

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


    private View.OnClickListener elementoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int fila = v.getId();
            int tipo = Integer.parseInt( v.getTag().toString() );
            TextView tv_actual =  (TextView) v ;

            if(tv_anterior.getId() == tv_actual.getId() && tv_anterior.getTag() == tv_actual.getTag())
            {
                Toast.makeText(getContext(), "Click venta fila: "+fila+" columna: "+tipo, Toast.LENGTH_SHORT).show();
            }
            else
            {
                tv_anterior.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                tv_actual.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                tv_anterior = tv_actual;
            }
        }
    };


}
