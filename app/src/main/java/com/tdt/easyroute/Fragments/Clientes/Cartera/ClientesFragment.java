package com.tdt.easyroute.Fragments.Clientes.Cartera;


import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.ClientesVM;

import java.util.ArrayList;


public class ClientesFragment extends Fragment {

    Button b_imprimir,b_salir;
    TextView tv_ruta;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;
    String cveSeleccionada=""; //inicia con la seleccion de filas vacia
    CarteraFragment fragment;

    Usuario user;

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

        MainActivity mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUsuario();

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
                imprimir();
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

                tv_ruta.setText("Saldo ruta: $"+Utils.numFormat(rs));
                mostrarCartera();
            }
            else
            {
                tv_ruta.setText("Saldo ruta: $0.00");
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

            ((TextView) tr.findViewById(R.id.t_cliente)).setText(d.getCli_cveext_str());
            ((TextView) tr.findViewById(R.id.t_negocio)).setText(d.getCli_nombrenegocio_str());
            ((TextView) tr.findViewById(R.id.t_saldo)).setText( "$"+Utils.numFormat(d.getSaldo())  );
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
                clientesVM.setNegocio(  ((TextView) tr.findViewById(R.id.t_negocio)).getText().toString() );
                clientesVM.setRazonSocial( ((TextView) tr.findViewById(R.id.t_razon)).getText().toString()  );
                clientesVM.setCle_cve(tag);
                fragment.goSaldosFragment();
            }
            else
            {
                //si no coincide, pinta todas de blanca
                for(int i=0; i<dgCartera.size();i++)
                {
                    TableRow row = (TableRow)vista.findViewWithTag(dgCartera.get(i).getCli_cve_n());
                    row.setBackgroundColor( getResources().getColor(R.color.bgDefault) );
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                cveSeleccionada=tag;
            }

        }
    };

    private void imprimir()
    {
        String menImp;
        try
        {
            Configuracion conf = Utils.ObtenerConf2(getActivity().getApplication());

            String rutaDes = BaseLocal.SelectDato(string.formatSql("select rut_desc_str from rutas where rut_cve_n={0}", String.valueOf(conf.getRuta())  ),getContext());

            menImp = "RUTA: "+rutaDes+"\n\n";
            menImp += string.formatSql("ASESOR: {0} {1} {2}",user.getNombre(), user.getAppat(), user.getApmat())+"\n\n";

            menImp += "FECHA DE IMPRESION: "+ Utils.FechaLocal() + " "+ Utils.HoraLocal()+"\n\n";

            menImp += "C A R T E R A\n\n";

            menImp+="CLIENTE      NEGOCIO      SALDO\n\n";


            Double TotCartera=0.0;
            String negocio;
            for(int i=0; i<dgCartera.size();i++)
            {
                DataTableLC.ClientesSaldo r = dgCartera.get(i);
                negocio = r.getCli_nombrenegocio_str();

                if(negocio.length()>13)
                    negocio=negocio.substring(0,13);

                menImp+= string.formatSql("{0} {1} {2}",r.getCli_cveext_str(),negocio, Utils.numFormat(r.getSaldo()) )+"\n";
                TotCartera+=r.getSaldo();
            }

            menImp+= "\nSaldo Total Ruta: $"+ Utils.numFormat(TotCartera);


            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle("¿Imprimir inventario?");
            dialogo1.setMessage(menImp);
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                }
            });
            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //cancelar();
                }
            });
            dialogo1.show();



        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
