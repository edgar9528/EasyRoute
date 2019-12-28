package com.tdt.easyroute.Fragments.Reportes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import java.util.ArrayList;


public class ArqueoFragment extends Fragment {

    public static ArqueoFragment newInstance() {
        ArqueoFragment fragment = new ArqueoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Configuracion conf=null;

    TextInputLayout til1,til2,til3,til4,til5;
    EditText et1,et2,et3,et4,et5;
    Button b_salir, b_imprimir, b_calcular;

    Usuario user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_arqueo, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUsuario();

        til1 = view.findViewById(R.id.til1);
        til2 = view.findViewById(R.id.til2);
        til3 = view.findViewById(R.id.til3);
        til4 = view.findViewById(R.id.til4);
        til5 = view.findViewById(R.id.til5);

        et1 = view.findViewById(R.id.et1);
        et2 = view.findViewById(R.id.et2);
        et3 = view.findViewById(R.id.et3);
        et4 = view.findViewById(R.id.et4);
        et5 = view.findViewById(R.id.et5);

        b_salir = view.findViewById(R.id.button_salir);
        b_imprimir = view.findViewById(R.id.button_imprimir);
        b_calcular = view.findViewById(R.id.button_calcular);

        conf = Utils.ObtenerConf(getActivity().getApplication());

        if (conf!=null && conf.getPreventa()==1)
        {
            til1.setHint( "Preventas del día:" );
            til2.setHint("Preventa Contado:");
            til3.setHint( "Preventa Crédito:" );
            //label4.Text = "";
            til4.setHint( "Total Preventa:" );

        }

        calcular();

        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });

        b_calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcular();
            }
        });

        b_imprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imprimir();
            }
        });

        return view;
    }


    private void calcular()
    {
        double VentasDia = 0;
        double VentasContado = 0;
        double VentasCredito = 0;
        double Cobranza = 0;

        try
        {
            if(conf!=null && conf.getPreventa()!=1)
            {
                ArrayList<DataTableLC.SaldoVentaSE> dr=null;
                ArrayList<DataTableLC.VentasContado> dr2=null;
                String json = BaseLocal.Select( string.formatSql(Querys.Liquidacion.SaldoPorVentaSinEnvase, String.valueOf( conf.getRuta() ) ), getContext() );
                dr = ConvertirRespuesta.getSaldoVentaSEJson(json);

                if(dr!=null)
                {
                    for(int i=0; i<dr.size();i++)
                        VentasDia += Double.parseDouble( dr.get(i).getTotal() );
                }

                json = BaseLocal.Select(string.formatSql(Querys.Liquidacion.VentasContado, String.valueOf(conf.getRuta()) ),getContext());
                dr2 = ConvertirRespuesta.getVentasContadoJson(json);

                if(dr2!=null)
                {
                    for(int i=0; i<dr2.size();i++)
                        VentasContado += Double.parseDouble( dr2.get(i).getTotal() );
                }



                json = BaseLocal.SelectDato(string.formatSql(Querys.Liquidacion.VentasCredito2, String.valueOf(conf.getRuta()) ),getContext());

                if(json!=null)
                {
                    VentasCredito = Double.parseDouble(json);
                }
                else
                    VentasCredito = 0.00;


                json = BaseLocal.Select(string.formatSql(Querys.Liquidacion.Cobranza, String.valueOf(conf.getRuta()) ), getContext());
                dr2 = ConvertirRespuesta.getVentasContadoJson(json);

                if(dr2!=null)
                {
                    for(int i=0; i<dr2.size();i++)
                    {
                        Cobranza+= Double.parseDouble( dr2.get(i).getTotal() );
                    }
                }

            }
            else
            {
                //Preventas del dia
                String result;
                result = BaseLocal.SelectDato(Querys.Liquidacion.PreventasTotal,getContext());

                if(result!=null)
                    VentasDia = Double.parseDouble(result);
                else
                    VentasDia=0.00;

                //Preventas de contado
                result = BaseLocal.SelectDato(Querys.Liquidacion.PreventasContado,getContext());
                if (result != null)
                    VentasContado = Double.parseDouble(result);
                else
                    VentasContado = 0.00;

                //Preventas Credito
                VentasCredito = VentasDia - VentasContado;


                //Preventas cobranza
                result = BaseLocal.SelectDato(Querys.Liquidacion.PreventasCobranza,getContext());
                if (result != null)
                    Cobranza = Double.parseDouble(result);
                else
                    Cobranza = 0.00;


            }

            et1.setText("$"+VentasDia);
            et2.setText("$"+VentasContado);
            et3.setText("$"+VentasCredito);
            et4.setText("$"+Cobranza);
            et5.setText("$"+(VentasContado+Cobranza));


        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getContext(), "Error al calcular: "+e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }

    private void imprimir()
    {
        try {
            String asesor = "";
            String rutac = "";
            String imp = "";

            rutac = BaseLocal.SelectDato(string.formatSql("Select rut_desc_str from rutas where rut_cve_n={0}", conf.getRutaStr()), getContext());

            imp += "RUTA: " + rutac + "\n";

            asesor = "ASESOR: " + string.formatSql("{0} {1} {2}", user.getNombre(), user.getAppat(), user.getApmat());

            imp += asesor + "\n";

            imp += "FECHA: " + Utils.FechaLocal() + " " + Utils.HoraLocal() + "\n\n";

            String con, json;


            //Ventas contado

            ArrayList<DataTableLC.Arqueo_vc> dtVC = null;
            con = "Select c.cli_cveext_str,p.pag_abono_n,fp.fpag_desc_str from Pagos p inner join clientes c " +
                    "on p.cli_cve_n=c.cli_cve_n  inner join formaspago fp on p.fpag_cve_n=fp.fpag_cve_n " +
                    "where pag_cobranza_n=0 and pag_envase_n=0";
            json = BaseLocal.Select(con, getContext());
            dtVC = ConvertirRespuesta.getArqueo_vcJson(json);

            imp += "R E S U M E N \n\n";

            double TotVC = 0;
            if (dtVC != null) {
                imp += "C O N T A D O\n";

                imp += "CLIENTE    PAGO    FORMA PAGO\n";

                for (int i = 0; i < dtVC.size(); i++) {
                    TotVC += Double.parseDouble(dtVC.get(i).getPag_abono_n());
                    imp += dtVC.get(i).getCli_cveext_str() + "  " + dtVC.get(i).getPag_abono_n() + "  " + dtVC.get(i).getFpag_desc_str() + "\n";
                }

                imp += "TOTAL CONTADO: " + TotVC+"\n\n";
            }



            //Credito

            ArrayList<DataTableLC.Arqueo_ccred> dtVCred = null;


            con="Select c.cli_cveext_str,cr.cred_referencia_str,cr.cred_monto_n from creditos cr inner join clientes c " +
                    "on c.cli_cve_n=cr.cli_cve_n where cr.cred_referencia_str not like '%-%' and cr.trans_est_n<>3";
            json = BaseLocal.Select(con,getContext());
            dtVCred = ConvertirRespuesta.getArqueo_ccredJson(json);

            double TotVCred = 0;

            if(dtVCred!=null)
            {
                imp +="C R E D I T O \n";

                imp+= "CLIENTE   SALDO   REFERENCIA\n";

                for(int i=0; i< dtVCred.size();i++)
                {
                    TotVCred += Double.parseDouble(dtVCred.get(i).getCred_monto_n());
                    imp += dtVCred.get(i).getCli_cveext_str() + " "+dtVCred.get(i).getCred_monto_n()+"  "+ dtVCred.get(i).getCred_referencia_str()+"\n";
                }

                imp+="TOTAL CREDITO: "+TotVCred;

            }

        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error al imprimir: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
