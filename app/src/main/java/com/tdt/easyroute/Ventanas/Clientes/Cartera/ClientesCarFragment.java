package com.tdt.easyroute.Ventanas.Clientes.Cartera;


import android.content.DialogInterface;
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

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Impresora;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.ClientesVM;

import java.util.ArrayList;


public class ClientesCarFragment extends Fragment {

    private TextView tv_ruta;
    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;
    private String cveSeleccionada=""; //inicia con la seleccion de filas vacia
    private CarteraFragment fragment;

    private Usuario user;
    private ArrayList<DataTableLC.ClientesSaldo> dgCartera=null;
    private ClientesVM clientesVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientesVM = ViewModelProviders.of ( getParentFragment() ).get(ClientesVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_clientescar, container, false);

        try {

            MainActivity mainActivity = (MainActivity) getActivity();
            user = mainActivity.getUsuario();

            Button b_imprimir, b_salir;

            vista = view;
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

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_cart1), e.getMessage() );
        }

        return view;
    }

    private void cargarCartera()
    {
        try
        {
            String json= BaseLocal.Select("select cr.*,coalesce(pag.abono,0) pag_abono_n, (cr.cred_monto_n-coalesce(pag.abono,0)) saldo from (select cr.cli_cve_n,c.cli_cveext_str,cli_nombrenegocio_str,cli_razonsocial_str,sum(cr.cred_monto_n-cr.cred_abono_n) cred_monto_n from creditos cr inner join clientes c on cr.cli_cve_n=c.cli_cve_n and cred_esenvase_n=0 group by  cr.cli_cve_n,c.cli_cveext_str,cli_nombrenegocio_str,cli_razonsocial_str ) cr left join (select cli_cve_n,sum(pag_abono_n) abono from pagos where pag_envase_n=0 and pag_cobranza_n=1 group by cli_cve_n) pag on cr.cli_cve_n=pag.cli_cve_n order by cr.cli_cveext_str",getContext());
            dgCartera = ConvertirRespuesta.getClientesSaldoJson(json);

            double rs=0.00;
            String saldoStr;
            if(dgCartera!=null)
            {
                for(int i=0; i<dgCartera.size();i++)
                {
                    rs+= dgCartera.get(i).getSaldo();
                }
                saldoStr = getResources().getString(R.string.tv_saldoRuta) +" "+  string.FormatoPesos(rs);
                tv_ruta.setText(saldoStr);
                mostrarCartera();
            }
            else
            {
                saldoStr = getResources().getString(R.string.tv_saldoRuta) +" "+ string.FormatoPesos(rs);
                tv_ruta.setText(saldoStr);
            }


        }catch (Exception e)
        {
            Log.d("Salida","Error:"+e.toString());
            Utils.msgError(getContext(), getString(R.string.err_cart2), e.getMessage() );
        }
    }

    private void mostrarTitulo()
    {
        try {
            tableLayout.removeAllViews();
            TableRow tr;

            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_cartera, null);

            ((TextView) tr.findViewById(R.id.t_cliente)).setTypeface(((TextView) tr.findViewById(R.id.t_cliente)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_negocio)).setTypeface(((TextView) tr.findViewById(R.id.t_negocio)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_saldo)).setTypeface(((TextView) tr.findViewById(R.id.t_saldo)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_razon)).setTypeface(((TextView) tr.findViewById(R.id.t_razon)).getTypeface(), Typeface.BOLD);

            tableLayout.addView(tr);
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_cart3), e.getMessage() );
        }
    }

    private void mostrarCartera()
    {
        try {
            mostrarTitulo();

            TableRow tr;
            DataTableLC.ClientesSaldo d;
            cveSeleccionada = "";

            for (int i = 0; i < dgCartera.size(); i++) {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_cartera, null);
                d = dgCartera.get(i);

                ((TextView) tr.findViewById(R.id.t_cliente)).setText(d.getCli_cveext_str());
                ((TextView) tr.findViewById(R.id.t_negocio)).setText(d.getCli_nombrenegocio_str());
                ((TextView) tr.findViewById(R.id.t_saldo)).setText( string.FormatoPesos(d.getSaldo()) );
                ((TextView) tr.findViewById(R.id.t_razon)).setText(d.getCli_razonsocial_str());
                tr.setTag(d.getCli_cve_n()); //se le asigna el cve a la final

                tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

                tableLayout.addView(tr);
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_cart3), e.getMessage() );
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
                    TableRow row = vista.findViewWithTag(dgCartera.get(i).getCli_cve_n());
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

            menImp+="CLIENTE    NEGOCIO    SALDO\n\n";


            double TotCartera=0.0;
            String negocio,cvecli,fsaldo;
            for(int i=0; i<dgCartera.size();i++)
            {
                DataTableLC.ClientesSaldo r = dgCartera.get(i);

                cvecli= Impresora.DarTamaño( r.getCli_cveext_str(),11 );
                negocio = Impresora.DarTamaño( r.getCli_nombrenegocio_str().replace(" ","_"),10 )+" ";
                fsaldo = Impresora.DarTamaño( string.FormatoPesos(r.getSaldo()), 10 );

                menImp+= cvecli+negocio+fsaldo+"\n";

                TotCartera+=r.getSaldo();
            }

            menImp+= "\nSaldo Total Ruta: "+ string.FormatoPesos(TotCartera);
            menImp+= "\n\n";

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle(getString(R.string.msg_impInv));
            dialogo1.setMessage(menImp);
            dialogo1.setCancelable(false);
            final String finalMenImp = menImp;
            dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Impresora.imprimir(finalMenImp.replace("\n","\r"), getContext());
                }
            });
            dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //cancelar();
                }
            });
            dialogo1.show();



        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Utils.msgError(getContext(), getString(R.string.error_imprimir), e.getMessage() );
        }
    }
}
