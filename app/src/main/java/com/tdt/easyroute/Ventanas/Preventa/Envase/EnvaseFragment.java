package com.tdt.easyroute.Ventanas.Preventa.Envase;

import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Preventa.PreventaActivity;
import com.tdt.easyroute.Ventanas.Ventas.PedidosActivity;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class EnvaseFragment extends Fragment {

    private PedidosVM pedidosVM;
    private ArrayList<DataTableLC.EnvasesPreventa> dgEnvase;
    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private TextView tv_subEnv;
    private TextView tv_anterior;
    private PreventaActivity preventaActivity;

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

        preventaActivity = (PreventaActivity) getActivity();
        tableLayout = view.findViewById(R.id.tableLayout);
        layoutInflater = inflater;

        tv_subEnv = view.findViewById(R.id.tv_subEnv);

        tv_subEnv.setText(string.FormatoPesos(0));
        tv_anterior = new TextView(getContext());


        TableLayout tableTitulos = view.findViewById(R.id.tableTitulos);
        TableRow tr = (TableRow) layoutInflater.inflate(R.layout.tabla_envases_prev, null);
        ((TextView) tr.findViewById(R.id.t_sku)).setText( "SKU" );
        ((TextView) tr.findViewById(R.id.dat1)).setText( "INI" );
        ((TextView) tr.findViewById(R.id.dat2)).setText( "CAR" );
        ((TextView) tr.findViewById(R.id.dat3)).setText( "ABO" );
        ((TextView) tr.findViewById(R.id.dat4)).setText( "PRO" );
        ((TextView) tr.findViewById(R.id.dat5)).setText( "VTA" );
        ((TextView) tr.findViewById(R.id.dat6)).setText( "FIN" );
        tableTitulos.addView(tr);

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

        pedidosVM.getTxtSubEnv().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_subEnv.setText( string.FormatoPesos(s) );
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
                    tr = (TableRow) layoutInflater.inflate(R.layout.tabla_envases_prev, null);
                    DataTableLC.EnvasesPreventa e = dgEnvase.get(i);

                    ((TextView) tr.findViewById(R.id.t_sku)).setText( e.getProd_sku_str() );
                    ((TextView) tr.findViewById(R.id.dat1)).setText( string.FormatoEntero( e.getProd_saldo_n() ) );
                    ((TextView) tr.findViewById(R.id.dat2)).setText( string.FormatoEntero( e.getProd_cargo_n() ) );
                    TextView abono =  tr.findViewById(R.id.dat3);
                    ((TextView) tr.findViewById(R.id.dat4)).setText( string.FormatoEntero( e.getProd_regalo_n() ) );
                    TextView venta =  tr.findViewById(R.id.dat5);
                    ((TextView) tr.findViewById(R.id.dat6)).setText( string.FormatoEntero( e.getProd_final_n() ) );
                    abono.setText( string.FormatoEntero( e.getProd_abono_n() ) );
                    venta.setText( string.FormatoEntero( e.getProd_venta_n() ) );

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


    private void actualizarProductos(int fila, int tipo,  String lectura) {
        try {

            if (tipo == 1) {

                double resta = (Double.parseDouble(dgEnvase.get(fila).getProd_saldo_n()) +
                        Double.parseDouble(dgEnvase.get(fila).getProd_cargo_n()))
                        - Double.parseDouble(dgEnvase.get(fila).getProd_venta_n());

                if (resta < Integer.parseInt(lectura)) {
                    Utils.msgInfo(getContext(), getString(R.string.msg_envCred2));
                    return;
                } else
                    dgEnvase.get(fila).setProd_abono_n(lectura);
            } else {
                double resta = (Double.parseDouble(dgEnvase.get(fila).getProd_saldo_n())
                        + Double.parseDouble(dgEnvase.get(fila).getProd_cargo_n()))
                        - Double.parseDouble(dgEnvase.get(fila).getProd_abono_n());

                if (resta < Double.parseDouble(lectura)) {
                    Utils.msgInfo(getContext(), getString(R.string.msg_envCred2));
                    return;
                } else
                    dgEnvase.get(fila).setProd_venta_n(lectura);
            }

            preventaActivity.UpdateDgEnvase(dgEnvase);

        } catch (Exception e) {
            Utils.msgError(getContext(), getString(R.string.error_actInfo), e.getMessage());
        }

    }

}
