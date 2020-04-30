package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
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
import java.util.Date;


public class EnvasecredFragment extends Fragment {


    private PedidosVM pedidosVM;
    private PedidosActivity pedidosActivity;
    private TextView tv_saldoDeudaEnv;
    private ArrayList<DataTableLC.EnvasesAdeudo> dgDeudaEnv;

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
                tableLayout.removeAllViews();
                TableRow tr;

                for(int i=0; i<dgDeudaEnv.size();i++)
                {
                    tr = (TableRow) layoutInflater.inflate(R.layout.tabla_credenvases, null);
                    DataTableLC.EnvasesAdeudo e = dgDeudaEnv.get(i);
                    TextView abono = (TextView) tr.findViewById(R.id.dat2);
                    TextView venta = (TextView) tr.findViewById(R.id.dat3);

                    ((TextView) tr.findViewById(R.id.t_sku)).setText( string.FormatoEntero(e.getProd_sku_str()) );
                    ((TextView) tr.findViewById(R.id.dat1)).setText( string.FormatoEntero( e.getSaldo() ) );
                    abono.setText( string.FormatoEntero( e.getAbono() ) );
                    venta.setText( string.FormatoEntero( e.getVenta() ) );
                    ((TextView) tr.findViewById(R.id.dat4)).setText( string.FormatoEntero( e.getSaldo() ) );

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
                cambiarValor(fila,tipo,tv_actual.getText().toString());
            }
            else
            {
                tv_anterior.setBackgroundColor(getResources().getColor(R.color.bgDefault));
                tv_actual.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                tv_anterior = tv_actual;
            }
        }
    };

    private void cambiarValor(final int fila,final int tipo,final String aux)
    {
        try
        {
            String msg = getResources().getString(R.string.msg_envCred1) + " " + aux;

            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.dialog_sugerido, null);
            final EditText editText = (EditText) view.findViewById(R.id.ti_sugerido);

            final AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("")
                    .setMessage(msg)
                    .setView(view)
                    .setPositiveButton(R.string.bt_aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String lectura = String.valueOf(editText.getText());
                            actualizarProductos(fila,tipo,lectura);
                        }
                    })
                    .setNegativeButton(R.string.bt_cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            dialog.show();

            editText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        String lectura = String.valueOf(editText.getText());
                        //actualizarProductos(indice,lectura,prod);
                        if (dialog.isShowing())
                            dialog.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug11), e.getMessage());
        }

    }

    private void actualizarProductos(int fila, int tipo,  String lectura)
    {
        try {

            if(tipo ==1) {

                double resta = Double.parseDouble(dgDeudaEnv.get(fila).getAdeudo()) - Double.parseDouble(dgDeudaEnv.get(fila).getVenta());

                if (resta < Integer.parseInt(lectura))
                {
                    Utils.msgInfo(getContext(), getString(R.string.msg_envCred2));
                    return;
                }
                else
                    dgDeudaEnv.get(fila).setAbono(lectura);
            }
            else
            {
                double resta= Double.parseDouble( dgDeudaEnv.get(fila).getAdeudo() ) - Double.parseDouble( dgDeudaEnv.get(fila).getAbono() );

                if(resta<Double.parseDouble(lectura))
                {
                    Utils.msgInfo(getContext(), getString(R.string.msg_envCred2));
                    return;
                }
                else
                    dgDeudaEnv.get(fila).setVenta(lectura);
            }

            dgDeudaEnv = actualizarAdeudoEnvase(dgDeudaEnv);

            double total =0;
            for(DataTableLC.EnvasesAdeudo e : dgDeudaEnv)
                total +=  Double.parseDouble( e.getSubTotal());

            tv_saldoDeudaEnv.setText( string.FormatoPesos( total) );
            pedidosVM.setTxtSaldoDeudaEnv( String.valueOf(total)  );
            pedidosVM.setDgDeudaEnv(dgDeudaEnv);

            pedidosActivity.CalcularEnvase();
            pedidosActivity.CalcularTotales();

        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_actInfo), e.getMessage());
        }
    }

    private ArrayList<DataTableLC.EnvasesAdeudo> actualizarAdeudoEnvase(ArrayList<DataTableLC.EnvasesAdeudo> al)
    {
        double adeudo,abono,venta,lprePrecio;
        for(int i=0; i<al.size(); i++)
        {
            adeudo = Double.parseDouble(al.get(i).getAdeudo());
            abono = Double.parseDouble(al.get(i).getAbono());
            venta = Double.parseDouble(al.get(i).getVenta());
            lprePrecio = Double.parseDouble(al.get(i).getLpre_precio_n());

            al.get(i).setSaldo( String.valueOf( adeudo-abono-venta  ) );
            al.get(i).setSubPagoEnv( String.valueOf( abono+venta ) );
            al.get(i).setSubTotal( String.valueOf( lprePrecio*venta ) );
        }
        return al;
    }


}
