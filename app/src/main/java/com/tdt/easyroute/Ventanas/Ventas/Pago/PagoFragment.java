package com.tdt.easyroute.Ventanas.Ventas.Pago;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tdt.easyroute.CardViews.Adapter.PagosAdapterRecyclerView;
import com.tdt.easyroute.CardViews.PagosSwipeController;
import com.tdt.easyroute.CardViews.SwipeControllerActions;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Ventas.PedidosActivity;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class PagoFragment extends Fragment {

    private FloatingActionButton fab;
    private PedidosVM pedidosVM;
    private PagosAdapterRecyclerView pagosAdapterRecyclerView;

    private ArrayList<DataTableLC.ProductosPed> dgProd2;
    private ArrayList<DataTableWS.FormasPago> formasPago;
    private ArrayList<DataTableLC.DgPagos> dgPagos;
    private DataTableLC.DtCliVentaNivel rc;
    private PedidosActivity pedidosActivity;

    private String estadoCredito;

    private TextView tv_credito;
    private TextView tv_totalVenta;
    private TextView tv_contadoEsp;
    private TextView tv_kit;
    private TextView tv_saldo;
    private double disponible;
    private double AdeudoAct = 0;
    private double _txtSaldoDeudaEnv=0;
    private double _txtSubEnv=0;

    public PagoFragment() {
        // Required empty public constructor
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
        View view= inflater.inflate(R.layout.fragment_pago, container, false);

        try {

            fab = view.findViewById(R.id.fab);
            pedidosActivity = (PedidosActivity) getActivity();

            rc = pedidosActivity.getRC();
            tv_credito = view.findViewById(R.id.et_credito);
            tv_totalVenta = view.findViewById(R.id.et_totalVenta);
            tv_contadoEsp = view.findViewById(R.id.et_contado);
            tv_kit = view.findViewById(R.id.tv_kit);
            tv_saldo = view.findViewById(R.id.tv_saldo);

            tv_totalVenta.setText(string.FormatoPesos(0));
            tv_saldo.setText(string.FormatoPesos(0));
            tv_contadoEsp.setText(string.FormatoPesos(0));
            tv_kit.setText(string.FormatoPesos(0));
            pedidosActivity.setTextKit(tv_kit.getText().toString());

            pedidosVM.setTxtVenta( tv_totalVenta.getText().toString() );
            pedidosVM.setTxtSaldo( tv_saldo.getText().toString() );

            dgPagos = new ArrayList<>();
            dgProd2 = new ArrayList<>();

            RecyclerView pagosRecyclerView = view.findViewById(R.id.pagosRecycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            pagosRecyclerView.setLayoutManager(linearLayoutManager);

            pagosAdapterRecyclerView = new PagosAdapterRecyclerView(dgPagos, R.layout.cardview_pagos, this);
            pagosRecyclerView.setAdapter(pagosAdapterRecyclerView);

            PagosSwipeController carritoSwipeController = new PagosSwipeController(new SwipeControllerActions() {
                @Override
                public void onRightClicked(int position) {
                    pagosAdapterRecyclerView.eliminarItem(position);
                }
            }, getActivity());

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(carritoSwipeController);
            itemTouchhelper.attachToRecyclerView(pagosRecyclerView);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ingresarPago();
                }
            });
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped32) ,e.getMessage());
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getDgPro2().observe(getActivity(), new Observer<ArrayList<DataTableLC.ProductosPed>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.ProductosPed> DgProd2) {
                dgProd2 = DgProd2;
                actualizarTotales();
            }
        });

        pedidosVM.getFormasPago().observe(getActivity(), new Observer< ArrayList<DataTableWS.FormasPago>  >() {
            @Override
            public void onChanged( ArrayList<DataTableWS.FormasPago>  formas) {
                formasPago = formas;
            }
        });

        pedidosVM.getEstadoCredito().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                estadoCredito=s;
                tv_credito.setText(estadoCredito);
            }
        });

        pedidosVM.getTxtContado().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String contado) {
                tv_contadoEsp.setText(string.FormatoPesos(contado));
            }
        });

        pedidosVM.getTxtSaldoDeudaEnv().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String saldoDeudaEnv) {
                _txtSaldoDeudaEnv = Double.parseDouble( string.DelCaracteres( saldoDeudaEnv ));
                actualizarTotales();
            }
        });

    }

    private void actualizarTotales()
    {
        try
        {

            double subtotal, total = 0;
            for (DataTableLC.ProductosPed p : dgProd2) {
                subtotal = Double.parseDouble(string.DelCaracteres(p.getLpre_precio_n())) * Double.parseDouble(p.getProd_cant_n());
                total += subtotal;
            }

            double DescKit = Double.parseDouble( string.DelCaracteres(tv_kit.getText().toString()) );

            total = total + _txtSubEnv+ _txtSaldoDeudaEnv;

            tv_totalVenta.setText(string.FormatoPesos(total));
            pedidosVM.setTxtVenta( tv_totalVenta.getText().toString() );

            double totPagos = 0;
            for (DataTableLC.DgPagos p : dgPagos)
                totPagos += Double.parseDouble(string.DelCaracteres(p.getFpag_cant_n()));

            total = total - DescKit - totPagos;

            tv_saldo.setText(string.FormatoPesos(total));
            pedidosVM.setTxtSaldo( tv_saldo.getText().toString() );

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_actInfo) ,e.getMessage());
        }

    }

    private void ingresarPago()
    {
        try
        {
            AdeudoAct = Double.parseDouble( string.DelCaracteres(tv_saldo.getText().toString()) );
            pedidosActivity.ValidaCredito();

            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.dialog_pago, null);
            final Spinner sp_forma = view.findViewById(R.id.spinnerTipoPago);
            final EditText ti_cantidad = view.findViewById(R.id.ti_cantidad);
            final EditText ti_banco = view.findViewById(R.id.ti_banco);
            final EditText ti_referencia = view.findViewById(R.id.ti_referencia);

            ArrayList<String> formas = new ArrayList<>();
            for(int i=0; i<formasPago.size();i++)
                formas.add(formasPago.get(i).getFpag_desc_str());

            sp_forma.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, formas));

            final boolean[] reqBan = {false};
            final boolean[] reqRef = {false};
            final int[] Item = {-1};

            sp_forma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Item[0] = position;
                    reqBan[0] = Utils.getBool( formasPago.get(position).getFpag_reqbanco_n() );
                    reqRef[0] = Utils.getBool( formasPago.get(position).getFpag_reqreferencia_n() );

                    if(reqBan[0])
                        ti_banco.setEnabled(true);
                    else
                    {
                        ti_banco.setEnabled(false);
                        ti_banco.setText("");
                        ti_cantidad.requestFocus();
                    }

                    if(reqRef[0])
                        ti_referencia.setEnabled(true);
                    else
                    {
                        ti_referencia.setEnabled(false);
                        ti_referencia.setText("");
                        ti_cantidad.requestFocus();
                    }

                    double cantidad = Double.parseDouble( string.DelCaracteres( tv_saldo.getText().toString() )  );
                    if(cantidad>0)
                        ti_cantidad.setText( String.valueOf(cantidad) );

                    if ( formasPago.get(position).getFpag_desc_str().equals("CREDITO") )
                    {
                        double contado = Double.parseDouble( string.DelCaracteres( tv_contadoEsp.getText().toString() )  );
                        double sal = cantidad - contado;

                        if (sal <= 0)
                            sal = 0;
                        if (  !Utils.getBool( rc.getCli_eshuix_n() )  )
                        {
                            disponible = pedidosActivity.getDisponible();
                            if (disponible < sal)
                                ti_cantidad.setText( String.valueOf( disponible ) );
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            final AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.msg_agregarPago)+" "+string.FormatoPesos(AdeudoAct))
                    .setView(view)
                    .setPositiveButton(R.string.bt_aceptar, null)
                    .setNegativeButton(R.string.bt_cancelar, null)
                    .setCancelable(false)
                    .create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            dialog.show();

            Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cantidad = ti_cantidad.getText().toString();
                    String banco = ti_banco.getText().toString();
                    String referencia = ti_referencia.getText().toString();

                    if(agregarPago(cantidad,banco,referencia,reqBan[0],reqRef[0],Item[0]))
                        dialog.dismiss();
                }
            });

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped26), e.getMessage());
        }
    }

    public boolean agregarPago(String cantidad, String banco, String referencia, boolean ReqBan,boolean ReqRef, int indice )
    {
        try {
            if (indice >= 0)
            {
                if(string.EsNulo(cantidad))
                {
                    Toast.makeText(getContext(), getString(R.string.tt_ped19), Toast.LENGTH_LONG).show();
                    return false;
                }
                if (ReqRef && string.EsNulo(referencia)) {
                    Toast.makeText(getContext(), getString(R.string.tt_ped16), Toast.LENGTH_LONG).show();
                    return false;
                }
                if (ReqBan && string.EsNulo(banco)) {
                    Toast.makeText(getContext(), getString(R.string.tt_ped17), Toast.LENGTH_LONG).show();
                    return false;
                }
            } else {
                Toast.makeText(getContext(), getString(R.string.tt_ped15), Toast.LENGTH_SHORT).show();
                return false;
            }

            int k = dgPagos.size()+1;

            double ab = 0;
            DataTableWS.FormasPago r = formasPago.get(indice);

            boolean EsCredito = false;
            if (r.getFpag_desc_str().toUpperCase().equals("CREDITO"))
                EsCredito = true;
            double AdeudoCredito = 0;
            double AbonosEfectivo = 0;
            double Contado = 0;

            Contado = Double.parseDouble(string.DelCaracteres(tv_contadoEsp.getText().toString()));

            AbonosEfectivo = 0;
            for (DataTableLC.DgPagos p : dgPagos)
                if (!p.getFpag_desc_str().equals("CREDITO"))
                    AbonosEfectivo += Double.parseDouble(string.DelCaracteres(p.getFpag_cant_n()));

            AdeudoCredito = Double.parseDouble(string.DelCaracteres(tv_totalVenta.getText().toString())) - Contado;

            try {
                ab = Double.parseDouble(cantidad);
                if (ab <= 0) {
                    Toast.makeText(getContext(), getString(R.string.tt_ped18), Toast.LENGTH_SHORT).show();
                    return false;
                }
                if ((ab > AdeudoAct && !EsCredito) || ((ab > AdeudoCredito) && EsCredito)) {
                    String s = string.formatSql("Los abonos de credito no pueden exceder la cantidad de {0}. Debido a que la cantidad {1} es obligatoria de contado.", String.valueOf(AdeudoCredito), String.valueOf(Contado));
                    if (EsCredito)
                        Utils.msgInfo(getContext(), s);
                    else
                        Utils.msgInfo(getContext(), "La cantidad de los pagos no puede exceder el saldo de la venta.");
                    return false;
                }

                if (formasPago.get(indice).getFpag_desc_str().equals("CREDITO")) {
                    if (!Utils.getBool(rc.getCli_eshuix_n())) {
                        if (ab > disponible) {
                            Utils.msgInfo(getContext(), string.formatSql("El monto del credito disponible es: {0} y no se puede exceder", String.valueOf(disponible)));
                            return false;
                        }
                    }
                }
            } catch (Exception ex) {
                Utils.msgError(getContext(), getString(R.string.err_ped28), ex.getMessage());
                return false;
            }

            DataTableLC.DgPagos ri = null;
            int rk = -1;
            String pagCve = formasPago.get(indice).getFpag_cve_n();
            for (int i = 0; i < dgPagos.size(); i++)
                if (pagCve.equals(dgPagos.get(i).getFpag_cve_n())) {
                    ri = dgPagos.get(i);
                    rk=i;
                    i = dgPagos.size();
                }

            if (ri != null)
            {
                double nuevaCan = Double.parseDouble( string.DelCaracteres(ri.getFpag_cant_n()) ) +ab;
                String nuevaCanStr = String.valueOf(nuevaCan);
                pagosAdapterRecyclerView.actualizarItem(rk,nuevaCanStr);
            }
            else
            {
                DataTableLC.DgPagos pago = new DataTableLC.DgPagos();

                pago.setNoPago( String.valueOf(k) );
                pago.setFpag_cve_n( formasPago.get(indice).getFpag_cve_n() );
                pago.setFpag_desc_str( formasPago.get(indice).getFpag_desc_str() );
                pago.setFpag_cant_n( string.FormatoPesos(ab) );
                pago.setBancoP(banco);
                pago.setReferenciaP(referencia);

                pagosAdapterRecyclerView.agregarItem(pago);

            }

            return true;
        }catch (Exception e)
        {
            Utils.msgError(getContext(),getString(R.string.err_ped27), e.getMessage());
            return false;
        }
    }

    public void ActualizarDgPagos(ArrayList<DataTableLC.DgPagos> dgRecycler)
    {
        try {
            dgPagos = dgRecycler;

            double DescKit = Double.parseDouble(string.DelCaracteres(tv_kit.getText().toString()));

            double totPagos = 0;
            for (DataTableLC.DgPagos p : dgPagos)
                totPagos += Double.parseDouble(string.DelCaracteres(p.getFpag_cant_n()));

            double txtVenta = Double.parseDouble(string.DelCaracteres(tv_totalVenta.getText().toString()));
            double tot = txtVenta - DescKit - totPagos;

            tv_saldo.setText(string.FormatoPesos(tot));
            pedidosVM.setTxtSaldo( tv_saldo.getText().toString() );

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped33) ,e.getMessage());
        }
    }

}