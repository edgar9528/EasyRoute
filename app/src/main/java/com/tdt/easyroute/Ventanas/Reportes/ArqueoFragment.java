package com.tdt.easyroute.Ventanas.Reportes;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Impresora;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import java.io.OutputStream;
import java.util.ArrayList;

public class ArqueoFragment extends Fragment {

    public static ArqueoFragment newInstance() {
        ArqueoFragment fragment = new ArqueoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    Configuracion conf=null;

    TextView t1,t2,t3,t4,t5;
    TextView et1,et2,et3,et4,et5;
    Button b_salir, b_imprimir;

    Usuario user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_arqueo, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUsuario();

        t1 = view.findViewById(R.id.t1);
        t2 = view.findViewById(R.id.t2);
        t3 = view.findViewById(R.id.t3);
        t4 = view.findViewById(R.id.t4);
        t5 = view.findViewById(R.id.t5);

        et1 = view.findViewById(R.id.et1);
        et2 = view.findViewById(R.id.et2);
        et3 = view.findViewById(R.id.et3);
        et4 = view.findViewById(R.id.et4);
        et5 = view.findViewById(R.id.et5);

        b_salir = view.findViewById(R.id.button_salir);
        b_imprimir = view.findViewById(R.id.button_imprimir);

        conf = Utils.ObtenerConf(getActivity().getApplication());

        if (conf!=null && conf.getPreventa()==1)
        {
            t1.setText( getString(R.string.hint_ventasDia_prev) );
            t2.setText(getString(R.string.hint_ventasContado_prev));
            t3.setText( getString(R.string.hint_ventasCredito_prev) );
            t4.setText( getString(R.string.hint_cobranza_prev) );
        }

        calcular();

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

            et1.setText(string.FormatoPesos( VentasDia ) );
            et2.setText(string.FormatoPesos( VentasContado ));
            et3.setText(string.FormatoPesos( VentasCredito ) );
            et4.setText(string.FormatoPesos( Cobranza ) );
            et5.setText( string.FormatoPesos ( VentasContado+Cobranza));

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

            imp += "RUTA: " + rutac + "\r";

            asesor = "ASESOR: " + string.formatSql("{0} {1} {2}", user.getNombre(), user.getAppat(), user.getApmat());

            imp += asesor + "\r";

            imp += "FECHA: " + Utils.FechaLocal() + " " + Utils.HoraLocal() + "\r\r";

            String con, json;


            //Ventas contado

            ArrayList<DataTableLC.Arqueo_vc> dtVC = null;
            con = "Select c.cli_cveext_str,p.pag_abono_n,fp.fpag_desc_str from Pagos p inner join clientes c " +
                    "on p.cli_cve_n=c.cli_cve_n  inner join formaspago fp on p.fpag_cve_n=fp.fpag_cve_n " +
                    "where pag_cobranza_n=0 and pag_envase_n=0";
            json = BaseLocal.Select(con, getContext());
            dtVC = ConvertirRespuesta.getArqueo_vcJson(json);

            imp += "R E S U M E N \r\r";

            double TotVC = 0;
            if (dtVC != null) {
                imp += "C O N T A D O\r";

                imp += "CLIENTE     PAGO      FORMA PAGO\r";

                for (int i = 0; i < dtVC.size(); i++) {
                    TotVC += Double.parseDouble(dtVC.get(i).getPag_abono_n());
                    imp += dtVC.get(i).getCli_cveext_str() + "  " + string.FormatoPesos(dtVC.get(i).getPag_abono_n()) ;
                    String ant = dtVC.get(i).getCli_cveext_str() + "  " + string.FormatoPesos(dtVC.get(i).getPag_abono_n()) ;
                    String agregar="";
                    for(int j=ant.length(); j<25;j++)
                            agregar+=" ";
                    agregar+=dtVC.get(i).getFpag_desc_str().substring(0,4) + "\r";
                    imp+=agregar;
                }

                imp += "TOTAL CONTADO: " + string.FormatoPesos(TotVC) +"\r\r";
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
                imp +="C R E D I T O \r";

                imp+= "CLIENTE   SALDO   REFERENCIA\r";

                for(int i=0; i< dtVCred.size();i++)
                {
                    TotVCred += Double.parseDouble(dtVCred.get(i).getCred_monto_n());
                    imp += dtVCred.get(i).getCli_cveext_str() + " "+ string.FormatoPesos(dtVCred.get(i).getCred_monto_n()) +"  "+ dtVCred.get(i).getCred_referencia_str()+"\n";
                }

                imp+="TOTAL CREDITO: "+ string.FormatoPesos(TotVCred) +"\r\r";
            }


            //Cobranza

            ArrayList<DataTableLC.Arqueo_cobranza> dtVCr=null;

            con = "Select c.cli_cveext_str,p.pag_abono_n,fp.fpag_desc_str from Pagos p inner join clientes c " +
                    "on p.cli_cve_n=c.cli_cve_n  inner join formaspago fp on p.fpag_cve_n=fp.fpag_cve_n " +
                    "where pag_cobranza_n=1 and pag_envase_n=0";

            json = BaseLocal.Select(con,getContext());
            dtVCr = ConvertirRespuesta.getArqueo_cobranzaJson(json);

            double TotCob=0.00;
            if(dtVCr!=null)
            {
                imp +="C O B R A N Z A \r";
                imp+= "CLIENTE    ABONO     FORMA PAGO\r";

                for(int i=0; i<dtVCr.size();i++)
                {
                    TotCob += Double.parseDouble( dtVCr.get(i).getPag_abono_n() );
                    imp+= dtVCr.get(i).getCli_cveext_str() + " "+ string.FormatoPesos( dtVCr.get(i).getPag_abono_n() ) ;

                    String ant = dtVCr.get(i).getCli_cveext_str() + " "+ string.FormatoPesos( dtVCr.get(i).getPag_abono_n() ) ;
                    String agregar="";
                    for(int j=ant.length(); j<25;j++)
                        agregar+=" ";
                    agregar+=dtVCr.get(i).getFpag_desc_str().substring(0,4) + "\r";
                    imp+=agregar;
                }

                imp+="TOTAL COBRANZA: "+TotCob+"\r\r";
            }


            ArrayList<DataTableLC.InvP> dtInvP=null;
            ArrayList<DataTableLC.Inv> dtInv=null;

            json = BaseLocal.Select(Querys.Inventario.LayoutInventario,getContext());
            dtInvP = ConvertirRespuesta.getInvPJson(json);

            json = BaseLocal.Select(string.formatSql(Querys.Inventario.ConsultaInventario, conf.getRutaStr() ), getContext());
            dtInv = ConvertirRespuesta.getInvJson(json);

            if( (dtInvP!=null && dtInvP.size()>0) && (dtInv!=null && dtInv.size()>0)  )
            {
                for(int i=0; i< dtInvP.size();i++)
                {
                    DataTableLC.InvP r = dtInvP.get(i);

                    DataTableLC.Inv in = null;
                    for(int j=0; j<dtInv.size();j++) //obtener todos los que coinciden (el select)
                    {
                        if(dtInv.get(j).getProd_cve_n().equals(r.getProd_cve_n()))
                            in= dtInv.get(j);
                    }

                    if(in!=null)
                        dtInvP.get(i).setProd_cant_n(  in.getProd_cant_n()  );
                }
            }

            ArrayList<DataTableLC.InvP> dtVacio= new ArrayList<>();
            ArrayList<DataTableLC.InvAdd> dtVacioAdd= new ArrayList<>();

            if(dtInvP!=null)
                for(int i=0; i<dtInvP.size();i++)
                {
                    if(dtInvP.get(i).getCat_desc_str().equals("ENVASE"))
                    {
                        dtVacio.add( dtInvP.get(i) );

                        DataTableLC.InvAdd invAdd = new DataTableLC.InvAdd();
                        invAdd.setProd_lleno_n("0");
                        invAdd.setProd_vacio_n( String.valueOf( Double.parseDouble( dtInvP.get(i).getProd_cant_n() ) - 0 ) );
                        dtVacioAdd.add(invAdd);
                    }
                }

            imp+="E N V A S E\r";

            imp+=string.formatSql("{0} {1} {2} {3}", "SKU", "    ENVASE", "    LLENO", "VACIO")+"\r";

            DataTableLC.InvP r;
            for(int i=0; i<dtVacio.size();i++)
            {
                r= dtVacio.get(i);

                double s=0;
                for(int j=0; j<dtInvP.size();j++)
                    if(dtInvP.get(j).getId_envase_n().equals( r.getId_envase_n() ))
                        s += Double.parseDouble( dtInvP.get(j).getProd_cant_n() );

                dtVacioAdd.get(i).setProd_lleno_n( String.valueOf(s));

                String[] palabras = r.getProd_desc_str().split(" ");
                String descripcion="";
                for(int k=0; k<palabras.length; k++)
                {
                    if(palabras[k].length()>3)
                        descripcion += palabras[k].substring(0,3)+".";
                    else
                        descripcion += palabras[k]+".";
                }
                if(descripcion.length()>13)
                    descripcion = descripcion.substring(0,13);

                String linea= string.formatSql("{0} {1}", r.getProd_sku_str(), descripcion);

                for(int k=linea.length(); k<19;k++ )
                    linea+=" ";

                imp+= linea+ string.formatSql("{2} {3} \r", dtVacioAdd.get(i).getProd_lleno_n(), dtVacioAdd.get(i).getProd_vacio_n());

            }

            imp+="\rT O T A L E S\r";

            imp+="TOTAL VENTAS DEL DIA: "+  string.FormatoPesos  (TotVC + TotVCred)+"\r";

            ArrayList<DataTableLC.Arqueo_rec> dtRec=null;

            con= "Select sum(p.pag_abono_n) pag_abono_n,fp.fpag_desc_str from Pagos p " +
                    " inner join formaspago fp on p.fpag_cve_n=fp.fpag_cve_n " +
                    "where pag_envase_n=0 group by fp.fpag_desc_str";

            json = BaseLocal.Select(con,getContext());

            dtRec = ConvertirRespuesta.getArqueo_recJson(json);

            double Efe = 0.00;
            double TotRec = 0.00;

            if(dtRec!=null)
            {
                DataTableLC.Arqueo_rec r2;
                for (int i = 0; i < dtRec.size(); i++)
                {
                    r2 = dtRec.get(i);
                    imp+= "TOTAL "+r2.getFpag_desc_str() +": " + string.FormatoPesos( r2.getPag_abono_n() )+"\r";

                    if(r2.getFpag_desc_str().equals("EFECTIVO"))
                    {
                        Efe += Double.parseDouble(r2.getPag_abono_n() );
                    }

                    TotRec += Double.parseDouble(r2.getPag_abono_n() );
                }
            }

            imp+= "TOTAL ENTREGAR A CAJA: " + string.FormatoPesos (TotVC + TotCob) +"\r\r";

            imp+= string.formatSql("YO {0} RESPONSABLE DE LA RUTA {1} DEBO Y PAGARE " +
                    "A LA EMPRESA _, S.A. DE C.V. LA CANTIDAD DE {2}",asesor,rutac, string.FormatoPesos (TotVC+TotCob));

            imp+="\r\r\r";

            String impAux = imp.replace("\r","\n");

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle(getString(R.string.msg_impArq));
            dialogo1.setMessage( impAux );
            dialogo1.setCancelable(false);
            final String finalImp = imp;
            dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Impresora.imprimir(finalImp, getContext());
                }
            });
            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //cancelar();
                }
            });

            AlertDialog alertDialog = dialogo1.show();

            TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextAppearance( R.style.estiloImprimir);
            }
        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error al imprimir: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
