package com.tdt.easyroute.Fragments.Clientes.Cartera;

import android.content.Context;
import android.graphics.Typeface;
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

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.ClientesVM;

import java.util.ArrayList;

public class SaldosFragment extends Fragment {

    private ClientesVM clientesVM;
    private CarteraFragment fragment;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;

    ArrayList<DataTableLC.Saldos> dgSaldos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientesVM = ViewModelProviders.of ( getParentFragment() ).get(ClientesVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saldos, container, false);
        fragment = (CarteraFragment) getParentFragment();
        layoutInflater = inflater;

        tableLayout = view.findViewById(R.id.tableLayout);


        mostrarTitulo();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clientesVM.getCli_cve().observe(getParentFragment(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                cargarSaldos(s);
            }
        });
    }



    private void cargarSaldos(String cve)
    {
        try
        {
            String consulta = string.formatSql( "select cr.cli_cve_n,c.cli_cveext_str,cr.cred_referencia_str, " +
                    "(cr.cred_monto_n-cr.cred_abono_n) cred_monto_n, coalesce(pag.abono,0) abono from creditos cr inner join clientes c " +
                    "on cr.cli_cve_n=c.cli_cve_n left join (select cli_cve_n,pag_referencia_str,sum(pag_abono_n) abono from pagos " +
                    "where pag_envase_n=0 and pag_cobranza_n=1 group by cli_cve_n,pag_referencia_str) pag on cr.cred_referencia_str=pag.pag_referencia_str " +
                    "where cr.cred_esenvase_n=0 and cr.cli_cve_n={0}",cve );

            String json = BaseLocal.Select(consulta,getContext());

            dgSaldos = ConvertirRespuesta.getSaldosJson(json);

            if(dgSaldos!=null)
            {
                mostrarSaldos();
            }

        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_saldos, null);

        ((TextView) tr.findViewById(R.id.t_cliente)).setTypeface( ((TextView) tr.findViewById(R.id.t_cliente)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_credito)).setTypeface( ((TextView) tr.findViewById(R.id.t_credito)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_monto)).setTypeface( ((TextView) tr.findViewById(R.id.t_monto)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_abono)).setTypeface( ((TextView) tr.findViewById(R.id.t_abono)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_saldo)).setTypeface( ((TextView) tr.findViewById(R.id.t_saldo)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);

    }

    private void mostrarSaldos()
    {
        mostrarTitulo();
        TableRow tr;

        for(int i=0; i<dgSaldos.size();i++)
        {
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_saldos, null);
            DataTableLC.Saldos s = dgSaldos.get(i);

            ((TextView) tr.findViewById(R.id.t_cliente)).setText(s.getCli_cve_n());
            ((TextView) tr.findViewById(R.id.t_credito)).setText(s.getCred_referencia_str());
            ((TextView) tr.findViewById(R.id.t_monto)).setText(s.getCred_monto_n());
            ((TextView) tr.findViewById(R.id.t_abono)).setText(s.getAbono());
            ((TextView) tr.findViewById(R.id.t_saldo)).setText(s.getSaldo());

            tableLayout.addView(tr);
        }

    }


}
