package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.content.Context;
import android.net.Uri;
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
import com.tdt.easyroute.CardViews.Adapter.AbonosAdapterRecyclerView;
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


public class AbonoscredFragment extends Fragment {

    private PedidosVM pedidosVM;
    private TextView tv_totAbono,tv_saldoCredito,tv_saldoEnvase;
    private FloatingActionButton fab;
    private DataTableLC.DtCliVentaNivel rc;
    PedidosActivity pedidosActivity;
    ArrayList<DataTableWS.FormasPago>  formasPago;
    private ArrayList<DataTableLC.DgPagos> dgAbonos;
    private AbonosAdapterRecyclerView abonosAdapterRecyclerView;

    public AbonoscredFragment() {
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
        View view= inflater.inflate(R.layout.fragment_abonoscred, container, false);

        tv_totAbono= view.findViewById(R.id.tv_totAbono);
        tv_saldoCredito = view.findViewById(R.id.tv_saldoCredito);
        tv_saldoEnvase = view.findViewById(R.id.tv_saldoEnvase);
        fab = view.findViewById(R.id.fab);

        dgAbonos = new ArrayList<>();

        pedidosActivity = (PedidosActivity) getActivity();
        rc = pedidosActivity.getRC();

        tv_saldoCredito.setText(string.FormatoPesos(0) );
        tv_saldoEnvase.setText(string.FormatoPesos(0) );

        tv_totAbono.setText(string.FormatoPesos(0) );
        pedidosVM.setTotAbono( tv_totAbono.getText().toString() );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresarPago();
            }
        });

        RecyclerView abonosRecyclerView = view.findViewById(R.id.abonosRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        abonosRecyclerView.setLayoutManager(linearLayoutManager);

        abonosAdapterRecyclerView = new AbonosAdapterRecyclerView(dgAbonos, R.layout.cardview_pagos, this);
        abonosRecyclerView.setAdapter(abonosAdapterRecyclerView);

        PagosSwipeController carritoSwipeController = new PagosSwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                abonosAdapterRecyclerView.eliminarItem(position);
            }
        }, getActivity());

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(carritoSwipeController);
        itemTouchhelper.attachToRecyclerView(abonosRecyclerView);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getTxtSaldoCredito().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_saldoCredito.setText( string.FormatoPesos(s) );
            }
        });

        pedidosVM.getFormasPago().observe(getActivity(), new Observer< ArrayList<DataTableWS.FormasPago>  >() {
            @Override
            public void onChanged( ArrayList<DataTableWS.FormasPago>  formas) {
                formasPago = formas;
            }
        });


    }

    private Double AdeudoAnt;
    private void ingresarPago()
    {
        try
        {
            pedidosActivity.ValidaCredito();

            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.dialog_pago, null);
            final Spinner sp_forma = view.findViewById(R.id.spinnerTipoPago);
            final EditText ti_cantidad = view.findViewById(R.id.ti_cantidad);
            final EditText ti_banco = view.findViewById(R.id.ti_banco);
            final EditText ti_referencia = view.findViewById(R.id.ti_referencia);

            Double saldoCredito = Double.parseDouble( string.DelCaracteres(tv_saldoCredito.getText().toString() ) );
            Double totalAbono = Double.parseDouble( string.DelCaracteres( tv_totAbono.getText().toString() )  );

            AdeudoAnt =  saldoCredito - totalAbono;

            ti_cantidad.setText( String.valueOf( AdeudoAnt )  );


            String fpag="";
            int fpagIndice=-1;

            if(rc!=null)
                fpag = rc.getFpag_cve_n();

            ArrayList<String> formas = new ArrayList<>();
            for(int i=0; i<formasPago.size();i++)
            {
                if(fpag.equals(formasPago.get(i).getFpag_cve_n()))
                    fpagIndice= i;
                formas.add(formasPago.get(i).getFpag_desc_str());
            }

            sp_forma.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.spinner_item, formas));

            sp_forma.setSelection(fpagIndice);

            final boolean[] reqBan = {false};
            final boolean[] reqRef = {false};
            final int[] Item = {-1};

            sp_forma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
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

                    /*double cantidad = Double.parseDouble( string.DelCaracteres( tv_saldo.getText().toString() )  );
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
                     */
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            final AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.msg_agregarAbono)+" "+string.FormatoPesos(AdeudoAnt))
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

            int k = dgAbonos.size()+1;

            DataTableWS.FormasPago r = formasPago.get(indice);

            double ab =0;
            try
            {
                ab = Double.parseDouble(cantidad);
                if (ab <= 0) {
                    Utils.msgInfo(getContext(), getString(R.string.msg_abo2));
                    return false;
                }
                if (ab > AdeudoAnt) {
                    Utils.msgInfo(getContext(), getString(R.string.msg_abo1));
                    return false;
                }

            } catch (Exception ex) {
                Utils.msgError(getContext(), getString(R.string.err_ped28), ex.getMessage());
                return false;
            }

            DataTableLC.DgPagos ri = null;
            int rk = -1;
            String pagCve = formasPago.get(indice).getFpag_cve_n();

            for (int i = 0; i < dgAbonos.size(); i++)
                if (pagCve.equals(dgAbonos.get(i).getFpag_cve_n()))
                {
                    ri = dgAbonos.get(i);
                    rk=i;
                    i = dgAbonos.size();
                }

            if (ri != null)
            {
                double nuevaCan = Double.parseDouble( string.DelCaracteres(ri.getFpag_cant_n()) ) +ab;
                String nuevaCanStr = String.valueOf(nuevaCan);
                abonosAdapterRecyclerView.actualizarItem(rk,nuevaCanStr);
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

                abonosAdapterRecyclerView.agregarItem(pago);

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
            dgAbonos = dgRecycler;

            double totPagos = 0;
            for (DataTableLC.DgPagos p : dgAbonos)
                totPagos += Double.parseDouble(string.DelCaracteres(p.getFpag_cant_n()));

            tv_totAbono.setText(string.FormatoPesos(totPagos));
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_ped33) ,e.getMessage());
        }
    }


}
