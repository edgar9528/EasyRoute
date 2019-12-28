package com.tdt.easyroute.Fragments.Reportes.Venta;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.VentasDiaVM;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class ProductosvenFragment extends Fragment {

    ArrayList<DataTableLC.Dtproven> dtprodvenlist= null;
    ArrayList<DataTableLC.Dtprogral> dtProdVenGral= null;
    ArrayList<DataTableLC.Dtcobros> dtcobros= null;

    TextView tv_cerveza, tv_otros;

    Configuracion conf;
    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    VentasDiaVM ventasDiaVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ventasDiaVM = ViewModelProviders.of ( this ).get(VentasDiaVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productosven, container, false);

        layoutInflater = inflater;
        tableLayout = view.findViewById(R.id.tableLayout);
        tv_cerveza = view.findViewById(R.id.tv_cerveza);
        tv_otros = view.findViewById(R.id.tv_otros);

        conf = Utils.ObtenerConf(getActivity().getApplication());


        mostrarTitulo();

        cargarVendidos();
        cargarPagos();

        return view;
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

                ventasDiaVM.setDtCobros(dtcobros);

            }

        }catch (Exception e)
        {

        }

    }


}
