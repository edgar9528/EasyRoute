package com.tdt.easyroute.Fragments.Clientes.Cartera;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.ClientesVM;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ClientesFragment extends Fragment {

    Button b_imprimir,b_salir;
    TextView tv_ruta;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;
    String cveSeleccionada=""; //inicia con la seleccion de filas vacia
    CarteraFragment fragment;

    ArrayList<DataTableLC.ClientesSaldo> dgCartera=null;

    private ClientesVM clientesVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientesVM = ViewModelProviders.of ( getParentFragment() ).get(ClientesVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_clientes, container, false);

        vista=view;
        fragment = (CarteraFragment) getParentFragment();
        layoutInflater = inflater;

        tableLayout = view.findViewById(R.id.tableLayout);
        b_imprimir = view.findViewById(R.id.button_imprimir);
        b_salir = view.findViewById(R.id.button_salir);
        tv_ruta = view.findViewById(R.id.tv_ruta);

        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });

        b_imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        mostrarTitulo();
        cargarCartera();

        return view;
    }

    private void cargarCartera()
    {
        try
        {
            String json= BaseLocal.Select("select cr.*,coalesce(pag.abono,0) pag_abono_n, (cr.cred_monto_n-coalesce(pag.abono,0)) saldo from (select cr.cli_cve_n,c.cli_cveext_str,cli_nombrenegocio_str,cli_razonsocial_str,sum(cr.cred_monto_n-cr.cred_abono_n) cred_monto_n from creditos cr inner join clientes c on cr.cli_cve_n=c.cli_cve_n and cred_esenvase_n=0 group by  cr.cli_cve_n,c.cli_cveext_str,cli_nombrenegocio_str,cli_razonsocial_str ) cr left join (select cli_cve_n,sum(pag_abono_n) abono from pagos where pag_envase_n=0 and pag_cobranza_n=1 group by cli_cve_n) pag on cr.cli_cve_n=pag.cli_cve_n order by cr.cli_cveext_str",getContext());
            dgCartera = ConvertirRespuesta.getClientesSaldoJson(json);

            double rs=0.00;

            if(dgCartera!=null)
            {
                for(int i=0; i<dgCartera.size();i++)
                {
                    rs+= dgCartera.get(i).getSaldo();
                }

                tv_ruta.setText("Saldo ruta: "+Utils.numFormat(rs));
                mostrarCartera();
            }
            else
            {
                tv_ruta.setText("Saldo ruta: 0.00");
            }


        }catch (Exception e)
        {
            Log.d("Salida","Error:"+e.toString());
            Toast.makeText(getContext(), "Error:"+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_cartera, null);

        ((TextView) tr.findViewById(R.id.t_cliente)).setTypeface( ((TextView) tr.findViewById(R.id.t_cliente)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_negocio)).setTypeface( ((TextView) tr.findViewById(R.id.t_negocio)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_saldo)).setTypeface( ((TextView) tr.findViewById(R.id.t_saldo)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_razon)).setTypeface( ((TextView) tr.findViewById(R.id.t_razon)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);

    }

    private void mostrarCartera()
    {
        mostrarTitulo();

        TableRow tr;
        DataTableLC.ClientesSaldo d;
        cveSeleccionada="";

        for(int i=0; i<dgCartera.size();i++)
        {
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_cartera, null);
            d= dgCartera.get(i);

            ((TextView) tr.findViewById(R.id.t_cliente)).setText(d.getCli_cve_n());
            ((TextView) tr.findViewById(R.id.t_negocio)).setText(d.getCli_nombrenegocio_str());
            ((TextView) tr.findViewById(R.id.t_saldo)).setText( Utils.numFormat(d.getSaldo())  );
            ((TextView) tr.findViewById(R.id.t_razon)).setText(d.getCli_razonsocial_str());
            tr.setTag(d.getCli_cve_n()); //se le asigna el cve a la final

            tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

            tableLayout.addView(tr);
        }
    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            String tag = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            if(cveSeleccionada.equals(tag)) //si coincide con el anterior, quiere decir que dio doble clic
            {
                clientesVM.setCle_cve(tag);
                fragment.goSaldosFragment();
            }
            else
            {
                //si no coincide, pinta todas de blanca
                for(int i=0; i<dgCartera.size();i++)
                {
                    TableRow row = (TableRow)vista.findViewWithTag(dgCartera.get(i).getCli_cve_n());
                    row.setBackgroundColor(Color.WHITE);
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                cveSeleccionada=tag;
            }

        }
    };



}
