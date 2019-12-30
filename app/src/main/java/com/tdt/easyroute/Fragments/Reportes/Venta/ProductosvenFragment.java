package com.tdt.easyroute.Fragments.Reportes.Venta;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.VentasDiaVM;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class ProductosvenFragment extends Fragment {

    ArrayList<DataTableLC.Dtproven> dtprodvenlist= null;
    ArrayList<DataTableLC.Dtprogral> dtProdVenGral= null;
    ArrayList<DataTableLC.Dtcobros> dtcobros= null;

    Button b_salir,b_impPre, b_impSku;
    TextView tv_cerveza, tv_otros;

    Configuracion conf;
    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    VentasDiaVM ventasDiaVM;

    Usuario user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ventasDiaVM = ViewModelProviders.of ( getParentFragment() ).get(VentasDiaVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productosven, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUsuario();

        layoutInflater = inflater;
        tableLayout = view.findViewById(R.id.tableLayout);
        tv_cerveza = view.findViewById(R.id.tv_cerveza);
        tv_otros = view.findViewById(R.id.tv_otros);

        b_salir = view.findViewById(R.id.button_salir);
        b_impPre = view.findViewById(R.id.button_imprimir1);
        b_impSku = view.findViewById(R.id.button_imprimir2);


        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });

        b_impSku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imprimirSKU();
            }
        });

        b_impPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imprimirPre();
            }
        });

        conf = Utils.ObtenerConf(getActivity().getApplication());
        mostrarTitulo();
        cargarVendidos();
        cargarPagos();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ventasDiaVM.getBotonClick().observe(getParentFragment(), new Observer<String >() {
            @Override
            public void onChanged( String Boton) {
                switch (Boton)
                {
                    case "sku":
                        imprimirSKU();
                        break;
                    case "pre":
                        imprimirPre();
                        break;
                }
            }
        });

    }

    private void mostrarTitulo()
    {
        tableLayout.removeAllViews();
        TableRow tr;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_ventasdia, null);

        ((TextView) tr.findViewById(R.id.t_sku)).setTypeface( ((TextView) tr.findViewById(R.id.t_sku)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_descripcion)).setTypeface( ((TextView) tr.findViewById(R.id.t_descripcion)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_lp)).setTypeface( ((TextView) tr.findViewById(R.id.t_lp)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.t_total)).setTypeface( ((TextView) tr.findViewById(R.id.t_total)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);
    }

    private void cargarVendidos()
    {
        try{

            String json,con;
            if(conf.getPreventa()==1)
            {
                con = "SELECT cat.cat_desc_str,VD.PROD_SKU_STR Sku,P.PROD_DESC_STR Descr,LP.LPRE_DESC_STR Descr_LPre, " +
                        "CAST(SUM(VD.PROD_CANT_N) AS DECIMAL(18,4)) Total " +
                        "FROM  PREVENTADET VD LEFT JOIN  " +
                        "PREVENTA VT ON VD.PREV_FOLIO_STR=VT.PREV_FOLIO_STR " +
                        "LEFT JOIN LISTAPRECIOS LP ON " +
                        "VT.LPRE_CVE_N= LP.LPRE_CVE_N " +
                        "LEFT JOIN PRODUCTOS P ON " +
                        "P.PROD_CVE_N= VD.PROD_CVE_N " +
                        "LEFT JOIN CATEGORIAS CAT ON " +
                        "CAT.CAT_CVE_N= P.CAT_CVE_N " +
                        "WHERE CAT.CAT_DESC_STR <> 'ENVASE' " +
                        "GROUP BY cat.cat_desc_str,VD.PROD_SKU_STR,P.PROD_DESC_STR,LP.LPRE_DESC_STR ORDER BY VD.PROD_SKU_STR";

                json = BaseLocal.Select(con,getContext());
                dtprodvenlist = ConvertirRespuesta.getDtprovenJson(json);


                con="SELECT cat.cat_desc_str,VD.PROD_SKU_STR Sku,P.PROD_DESC_STR Descr, " +
                        "CAST(SUM(VD.PROD_CANT_N) AS DECIMAL(18,4)) Total " +
                        "FROM  PREVENTADET VD LEFT JOIN  " +
                        "PREVENTA VT ON VD.PREV_FOLIO_STR=VT.PREV_FOLIO_STR " +
                        "LEFT JOIN PRODUCTOS P ON " +
                        "P.PROD_CVE_N= VD.PROD_CVE_N " +
                        "LEFT JOIN CATEGORIAS CAT ON " +
                        "CAT.CAT_CVE_N= P.CAT_CVE_N " +
                        "WHERE CAT.CAT_DESC_STR <> 'ENVASE' " +
                        "GROUP BY cat.cat_desc_str,VD.PROD_SKU_STR,P.PROD_DESC_STR ORDER BY VD.PROD_SKU_STR";

                json = BaseLocal.Select(con,getContext());
                dtProdVenGral = ConvertirRespuesta.getDtprogralJson(json);
            }
            else
            {
                con = "SELECT cat.cat_desc_str,VD.PROD_SKU_STR Sku,P.PROD_DESC_STR Descr,LP.LPRE_DESC_STR Descr_LPre, " +
                        "CAST(SUM(VD.PROD_CANT_N) AS decimal(18,4)) Total " +
                        "FROM  VENTASDET VD LEFT JOIN  " +
                        "VENTAS VT ON VD.VEN_FOLIO_STR=VT.VEN_FOLIO_STR " +
                        "LEFT JOIN LISTAPRECIOS LP ON " +
                        "VT.LPRE_CVE_N= LP.LPRE_CVE_N " +
                        "LEFT JOIN PRODUCTOS P ON " +
                        "P.PROD_CVE_N= VD.PROD_CVE_N " +
                        "LEFT JOIN CATEGORIAS CAT ON " +
                        "CAT.CAT_CVE_N= P.CAT_CVE_N " +
                        "WHERE CAT.CAT_DESC_STR <> 'ENVASE' " +
                        "GROUP BY cat.cat_desc_str,VD.PROD_SKU_STR,P.PROD_DESC_STR,LP.LPRE_DESC_STR ORDER BY VD.PROD_SKU_STR";

                json = BaseLocal.Select(con,getContext());
                dtprodvenlist = ConvertirRespuesta.getDtprovenJson(json);


                con = "SELECT cat.cat_desc_str,VD.PROD_SKU_STR Sku,P.PROD_DESC_STR Descr, " +
                        "CAST(SUM(VD.PROD_CANT_N) AS DECIMAL(18,4)) Total " +
                        "FROM  VENTASDET VD LEFT JOIN  " +
                        "VENTAS VT ON VD.VEN_FOLIO_STR=VT.VEN_FOLIO_STR " +
                        "LEFT JOIN PRODUCTOS P ON " +
                        "P.PROD_CVE_N= VD.PROD_CVE_N " +
                        "LEFT JOIN CATEGORIAS CAT ON " +
                        "CAT.CAT_CVE_N= P.CAT_CVE_N " +
                        "WHERE CAT.CAT_DESC_STR <> 'ENVASE' " +
                        "GROUP BY cat.cat_desc_str,VD.PROD_SKU_STR,P.PROD_DESC_STR ORDER BY VD.PROD_SKU_STR";

                json = BaseLocal.Select(con,getContext());

                dtProdVenGral = ConvertirRespuesta.getDtprogralJson(json);


                double suma = 0.00;
                double suma2 = 0.00;

                if(dtprodvenlist!=null)
                {
                    for (int i = 0; i < dtprodvenlist.size(); i++) {
                        if (dtprodvenlist.get(i).getCat_desc_str().equals("CERVEZA"))
                            suma += Double.parseDouble(dtprodvenlist.get(i).getTotal());
                        else
                            suma2 += Double.parseDouble(dtprodvenlist.get(i).getTotal());
                    }
                }

                tv_cerveza.setText( "CERVEZA: "+ suma );

                tv_otros.setText("OTROS: "+suma2);

                mostrarVentas();
            }


        }
        catch (Exception e)
        {

        }

    }

    private void mostrarVentas()
    {
        if(dtprodvenlist!=null)
        {
            mostrarTitulo();

            TableRow tr;
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_ventasdia, null);
            DataTableLC.Dtproven dt;
            for (int i = 0; i < dtprodvenlist.size(); i++)
            {
                dt= dtprodvenlist.get(i);
                ((TextView) tr.findViewById(R.id.t_sku)).setText(dt.getSku());
                ((TextView) tr.findViewById(R.id.t_descripcion)).setText(dt.getDescr());
                ((TextView) tr.findViewById(R.id.t_lp)).setText(dt.getDescr_LPre());
                ((TextView) tr.findViewById(R.id.t_total)).setText(dt.getTotal());
                tableLayout.addView(tr);
            }

        }
    }

    private void cargarPagos()
    {
        try
        {
            String json,con;
            if(conf.getPreventa()==1)
            {
                con= "SELECT fp.fpag_desc_str Tpago,SUM (p.fpag_cant_n) Monto from preventapagos p " +
                        "inner join formaspago fp on fp.fpag_cve_n=p.fpag_cve_n " +
                        "group by fp.fpag_desc_str";
                json = BaseLocal.Select(con,getContext());
                dtcobros = ConvertirRespuesta.getDtcobrosJson(json);
            }
            else
            {
                con ="SELECT fp.fpag_desc_str Tpago,SUM (p.pag_abono_n) Monto from pagos p " +
                        "inner join formaspago fp on fp.fpag_cve_n=p.fpag_cve_n " +
                        "group by fp.fpag_desc_str";

                json= BaseLocal.Select(con,getContext());
                dtcobros = ConvertirRespuesta.getDtcobrosJson(json);
            }

            ventasDiaVM.setDtCobros(dtcobros);


        }catch (Exception e)
        {

        }

    }

    private void imprimirPre()
    {

        try {
            String imp = "", json, con;
            String ruta = "";
            String asesor = "";

            ArrayList<DataTableLC.Env> dtEnv = null;
            ArrayList<DataTableLC.EnvPrev> dtEnvPrev = null;
            ArrayList<DataTableLC.SaldoEnvase> dtSalEnv = null;

            con = "Select p.prod_cve_n,p.prod_sku_str,p.prod_desc_str," +
                    "coalesce(iv.inv_inicial_n,0) inv_inicial_n, 0 inv_cargo_n, 0 inv_abono_n," +
                    "0 inv_venta_n from productos p inner join " +
                    "categorias c on p.cat_cve_n=c.cat_cve_n " +
                    "left join inventario iv on p.prod_cve_n=iv.prod_cve_n " +
                    "where c.cat_desc_str='ENVASE'";

            json = BaseLocal.Select(con, getContext());
            dtEnv = ConvertirRespuesta.getEnvJson(json);

            con = "select prod_sku_str,sum(prod_inicial_n) INI,sum(prod_cargo_n) CAR," +
                    "sum(prod_abono_n) ABO,sum(prod_venta_n) VEN,sum(prod_final_n) FIN from preventaenv " +
                    "group by prod_sku_str";
            json = BaseLocal.Select(con, getContext());
            dtEnvPrev = ConvertirRespuesta.getEnvPrevJson(json);


            con = "select prod_cve_n,sum(ven_cargo_n) ven_cargo_n," +
                    "sum(ven_abono_n) ven_abono_n,sum(ven_venta_n) ven_venta_n from ventaenv group by prod_cve_n";
            json = BaseLocal.Select(con, getContext());
            dtSalEnv = ConvertirRespuesta.getSaldoEnvaseJson(json);

            if(dtEnv!=null)
                for (int i = 0; i < dtEnv.size(); i++)
                {
                    if(dtSalEnv!=null)
                        for (int j = 0; j < dtSalEnv.size(); j++)
                        {
                            if (dtEnv.get(i).getProd_cve_n().equals(dtSalEnv.get(j).getProd_cve_n()))
                            {
                                dtEnv.get(i).setInv_cargo_n(dtSalEnv.get(j).getVen_cargo_n());
                                dtEnv.get(i).setInv_abono_n(dtSalEnv.get(j).getVen_abono_n());
                                dtEnv.get(i).setInv_venta_n(dtSalEnv.get(j).getVen_venta_n());
                            }
                        }

                    int fin = Integer.parseInt(dtEnv.get(i).getInv_inicial_n()) +
                            Integer.parseInt(dtEnv.get(i).getInv_abono_n()) -
                            Integer.parseInt(dtEnv.get(i).getInv_cargo_n());

                    dtEnv.get(i).setInv_fin_n(  String.valueOf(fin) );

                }

            ruta = BaseLocal.SelectDato(string.formatSql("Select rut_desc_str from rutas where rut_cve_n={0}", conf.getRutaStr()), getContext());
            asesor = "ASESOR: " + string.formatSql("{0} {1} {2}", user.getNombre(), user.getAppat(), user.getApmat());

            imp += "RUTA: " + ruta + "\n";
            imp += asesor + "\n";
            imp += "FECHA: " + Utils.FechaLocal() + " " + Utils.HoraLocal() + "\n\n";


            if(conf.getPreventa()==1)
                imp+="P R E V E N T A\n";
            else
                imp+="V E N T A S\n";

            imp+=string.formatSql("{0} {1} {2}", "   SKU  ", " VTA ", "  PRECIO  \n");

            double TotPza=0;

            if(dtprodvenlist!=null)
                for(int i=0; i< dtprodvenlist.size();i++)
                {
                    imp+= dtprodvenlist.get(i).getSku() + "  " + dtprodvenlist.get(i).getTotal()+"  "+ dtprodvenlist.get(i).getDescr_LPre()+"\n";
                    TotPza += Double.parseDouble( dtprodvenlist.get(i).getTotal() );
                }

            imp+= "TOTAL PZAS VENDIDAS: "+ TotPza+"\n\n";

            imp+="M O V I M I E N T O S\n";
            imp+= string.formatSql("{0} {1}", "  CANTIDAD  ", "      FORMA      \n");

            double Cobranza = 0;
            if(dtcobros!=null)
                for(int i=0; i<dtcobros.size();i++)
                {
                    imp+= dtcobros.get(i).getMonto() + "  "+ dtcobros.get(i).getTpago()+"\n";
                    Cobranza += Double.parseDouble( dtcobros.get(i).getMonto() );
                }
            imp+="TOTAL COBRANZA: $"+Cobranza+"\n\n";


            imp+= "E N V A S E\n";
            imp+=string.formatSql("{0}{1}{2}{3}{4}{5}\n", " SKU "," INI "," CAR "," ABO "," VEN "," FIN ");

            DataTableLC.EnvPrev r;
            DataTableLC.Env r2;
            if (conf.getPreventa() == 1)
            {
                if(dtEnvPrev!=null)
                    for(int i=0; i<dtEnvPrev.size();i++)
                    {
                        r = dtEnvPrev.get(i);
                        imp+=string.formatSql("{0}   {1}   {2}   {3}   {4}   {5} \n",r.getProd_sku_str(), r.getINI(), r.getCAR(), r.getABO(), r.getVEN(),r.getFIN());
                    }
            }
            else
            {
                if(dtEnv!=null)
                    for(int i=0; i<dtEnv.size();i++)
                    {
                        r2 = dtEnv.get(i);
                        imp+=string.formatSql("{0}   {1}   {2}   {3}   {4}   {5} \n",r2.getProd_sku_str(), r2.getInv_inicial_n(), r2.getInv_cargo_n(),
                                r2.getInv_abono_n(), r2.getInv_venta_n(), r2.getInv_fin_n());
                    }
            }


            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle("¿Imprimir por precio?");
            dialogo1.setMessage(imp);
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
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void imprimirSKU()
    {
        try {
            String imp = "", json, con;
            String ruta = "";
            String asesor = "";

            ArrayList<DataTableLC.Env> dtEnv = null;
            ArrayList<DataTableLC.EnvPrev> dtEnvPrev = null;
            ArrayList<DataTableLC.SaldoEnvase> dtSalEnv = null;

            con = "Select p.prod_cve_n,p.prod_sku_str,p.prod_desc_str," +
                    "coalesce(iv.inv_inicial_n,0) inv_inicial_n, 0 inv_cargo_n, 0 inv_abono_n," +
                    "0 inv_venta_n from productos p inner join " +
                    "categorias c on p.cat_cve_n=c.cat_cve_n " +
                    "left join inventario iv on p.prod_cve_n=iv.prod_cve_n " +
                    "where c.cat_desc_str='ENVASE'";

            json = BaseLocal.Select(con, getContext());
            dtEnv = ConvertirRespuesta.getEnvJson(json);

            con = "select prod_sku_str,sum(prod_inicial_n) INI,sum(prod_cargo_n) CAR," +
                    "sum(prod_abono_n) ABO,sum(prod_venta_n) VEN,sum(prod_final_n) FIN from preventaenv " +
                    "group by prod_sku_str";
            json = BaseLocal.Select(con, getContext());
            dtEnvPrev = ConvertirRespuesta.getEnvPrevJson(json);


            con = "select prod_cve_n,sum(ven_cargo_n) ven_cargo_n," +
                    "sum(ven_abono_n) ven_abono_n,sum(ven_venta_n) ven_venta_n from ventaenv group by prod_cve_n";
            json = BaseLocal.Select(con, getContext());
            dtSalEnv = ConvertirRespuesta.getSaldoEnvaseJson(json);

            if (dtEnv != null)
                for (int i = 0; i < dtEnv.size(); i++) {
                    if (dtSalEnv != null)
                        for (int j = 0; j < dtSalEnv.size(); j++) {
                            if (dtEnv.get(i).getProd_cve_n().equals(dtSalEnv.get(j).getProd_cve_n())) {
                                dtEnv.get(i).setInv_cargo_n(dtSalEnv.get(j).getVen_cargo_n());
                                dtEnv.get(i).setInv_abono_n(dtSalEnv.get(j).getVen_abono_n());
                                dtEnv.get(i).setInv_venta_n(dtSalEnv.get(j).getVen_venta_n());
                            }
                        }

                    int fin = Integer.parseInt(dtEnv.get(i).getInv_inicial_n()) +
                            Integer.parseInt(dtEnv.get(i).getInv_abono_n()) -
                            Integer.parseInt(dtEnv.get(i).getInv_cargo_n());

                    dtEnv.get(i).setInv_fin_n(String.valueOf(fin));

                }

            ruta = BaseLocal.SelectDato(string.formatSql("Select rut_desc_str from rutas where rut_cve_n={0}", conf.getRutaStr()), getContext());
            asesor = "ASESOR: " + string.formatSql("{0} {1} {2}", user.getNombre(), user.getAppat(), user.getApmat());

            imp += "RUTA: " + ruta + "\n";
            imp += asesor + "\n";
            imp += "FECHA: " + Utils.FechaLocal() + " " + Utils.HoraLocal() + "\n\n";


            if (conf.getPreventa() ==1)
                imp+="P R E V E N T A\n";
            else
                imp+="V E N T A S \n";


            imp+=string.formatSql("{0}{1}{2} \n", "   SKU  ", "        DESCRIPCION       ", " VTA");

            double TotPza = 0;
            DataTableLC.Dtprogral r;
            for(int i=0; i<dtProdVenGral.size();i++)
            {
                r=dtProdVenGral.get(i);
                imp+= string.formatSql("{0}{1}{2} \n", r.getSku(), r.getDescr(), r.getTotal());
                TotPza += Double.parseDouble( r.getTotal() );
            }
            imp+="TOTAL PZAS VENDIDAS: "+TotPza+"\n";


            imp+="M O V I M I E N T O S\n";
            imp+= string.formatSql("{0} {1}", "  CANTIDAD  ", "      FORMA      \n");

            double Cobranza = 0;
            if(dtcobros!=null)
                for(int i=0; i<dtcobros.size();i++)
                {
                    imp+= dtcobros.get(i).getMonto() + "  "+ dtcobros.get(i).getTpago()+"\n";
                    Cobranza += Double.parseDouble( dtcobros.get(i).getMonto() );
                }
            imp+="TOTAL COBRANZA: $"+Cobranza+"\n\n";




            imp+= "E N V A S E\n";
            imp+=string.formatSql("{0}{1}{2}{3}{4}{5}\n", " SKU "," INI "," CAR "," ABO "," VEN "," FIN ");

            DataTableLC.EnvPrev r1;
            DataTableLC.Env r2;
            if (conf.getPreventa() == 1)
            {
                if(dtEnvPrev!=null)
                    for(int i=0; i<dtEnvPrev.size();i++)
                    {
                        r1 = dtEnvPrev.get(i);
                        imp+=string.formatSql("{0}   {1}   {2}   {3}   {4}   {5} \n",r1.getProd_sku_str(), r1.getINI(), r1.getCAR(), r1.getABO(), r1.getVEN(),r1.getFIN());
                    }
            }
            else
            {
                if(dtEnv!=null)
                    for(int i=0; i<dtEnv.size();i++)
                    {
                        r2 = dtEnv.get(i);
                        imp+=string.formatSql("{0}   {1}   {2}   {3}   {4}   {5} \n",r2.getProd_sku_str(), r2.getInv_inicial_n(), r2.getInv_cargo_n(),
                                r2.getInv_abono_n(), r2.getInv_venta_n(), r2.getInv_fin_n());
                    }
            }


            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle("¿Imprimir por sku?");
            dialogo1.setMessage(imp);
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
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

}
