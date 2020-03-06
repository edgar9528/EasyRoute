package com.tdt.easyroute.Ventanas.Ventas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerConfiguracionAdapter;
import com.tdt.easyroute.Adapter.PagerPedidosAdapter;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.ViewPagerNonSwipable;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class PedidosActivity extends AppCompatActivity {

    private boolean esventa;
    private String noCli;
    private String Cliente;
    private boolean ConVenta;
    private boolean PorEscanear;
    private String PositionStr;
    private boolean removerVenta = false;
    private String _tv_adeudoN;
    private String _tv_totAbono;

    Configuracion conf;
    DataTableLC.DtCliVentaNivel rc;
    ArrayList<DataTableLC.ProductosPed> dtProductos;
    ArrayList<DataTableLC.ProductosPed> dgProd2;
    ArrayList<DataTableLC.CompPrevDet> ListaPrev;
    ArrayList<DataTableLC.DgAbonos> dgAbonos;
    ArrayList<DataTableLC.DgPagos> dgPagos;
    ArrayList<DataTableLC.EnvasesAdeudo> dgDeudaEnv;

    ArrayList<DataTableLC.PedPromocionesKit> dtKits;
    ArrayList<DataTableLC.EnvasesPed> dgEnvase;
    ArrayList<DataTableLC.Pagos> dtPagos;
    ArrayList<DataTableWS.FormasPago> formasPago;
    ArrayList<DataTableWS.FormasPago> formasPago2;
    ArrayList<DataTableLC.AdeudoNormal> dtAN;
    ArrayList<DataTableLC.AdeudoNormal> dtAE;

    boolean RutaMayorista = false;
    boolean RutaPromoCe = false;

    int catenv = -1;
    int catcvz = -1;
    int catvasos = -1;
    int cathielo = -1;
    int lpre = -1;
    int lpBase = -1;

    String FolioVisita = "";
    String FolioPreventa = "";
    String FolioConsigna = "";
    String FolioPedido = "";
    boolean ReqRefencia = false;

    boolean lpreCO = false;

    PedidosVM pedidosVM;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ordenes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        pedidosVM = ViewModelProviders.of( this).get(PedidosVM.class);

        setTitle(getString(R.string.title_pedidos));

        esventa = getIntent().getBooleanExtra("venta", false);
        noCli = getIntent().getStringExtra("idcli");
        Cliente = getIntent().getStringExtra("idext");
        ConVenta = getIntent().getBooleanExtra("conventa", false);
        PorEscanear = getIntent().getBooleanExtra("porEscanear", false);
        PositionStr = getIntent().getStringExtra("PositionStr");

        inicializar();


        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped1));
        if (!removerVenta)
            tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped3));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped4));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPagerNonSwipable viewPager = findViewById(R.id.pager);
        final PagerPedidosAdapter adapter = new PagerPedidosAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ValidaCredito();
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //TERMINA CONFIGURACION DE LAS TABS
        ObtenerPromoEnvase("1","1","1","1","1","1");

    }

    @Override
    protected void onResume() {
        super.onResume();

        pedidosVM.getDgPro2().observe(this, new Observer<ArrayList<DataTableLC.ProductosPed>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.ProductosPed> productosPeds) {
                dgProd2 = productosPeds;
            }
        });

        pedidosVM.getAdeudoN().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                _tv_adeudoN = s;
            }
        });

        pedidosVM.getTotAbono().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                _tv_totAbono = s;
            }
        });

    }

    private void inicializar()
    {
        Log.d("salida", "entro a inicializar");

        ListaPrev = new ArrayList<>();

        conf = Utils.ObtenerConf(getApplication());
        rc = ObtenerCliente();

        catenv = ObtenerIdCat("ENVASE");
        catcvz = ObtenerIdCat("CERVEZA");
        catvasos = ObtenerIdCat("VASOS");
        cathielo = ObtenerIdCat("HIELO");

        lpre = ObtenerListaPrecio();
        lpBase = ObtenerListaPrecio("BASE");

        ObtenerProductos();

        ListarEnvase(lpBase);

        _tv_adeudoN="$0.00";
        _tv_totAbono="$0.00";

        dgPagos = new ArrayList<>();
        dgAbonos = new ArrayList<>();

        ObtenerPagos();
        ListarFormaPago();
        ListarFormaPago2();

        ListarAdeudoEnvase();

        dtAN = ListarAdeudoNormal();
        dtAE = ListarAdeudoEspecial();


        ValidaClienteInactivo();
        ValidarSegundaVenta();

        if(conf.getPreventa()==2 && rc.getEst_cve_str().equals("A") )
            CargarPreventa();

    }

    private DataTableLC.DtCliVentaNivel ObtenerCliente()
    {
        DataTableLC.DtCliVentaNivel cli = null;

        try {
            String json = BaseLocal.Select(string.formatSql(Querys.ClientesVentaMes.SelClienteConVtaMesNivel, noCli), getApplicationContext());
            ArrayList<DataTableLC.DtCliVentaNivel> cliVenta = ConvertirRespuesta.getDtCliVentaNivelJson(json);

            if (cliVenta != null) {
                cli = cliVenta.get(0);
            }

            String mayorista = BaseLocal.SelectDato(string.formatSql("select rut_mayorista_n from rutas where rut_cve_n={0}", cli.getRut_cve_n()), getApplicationContext());
            String promoce = BaseLocal.SelectDato(string.formatSql("select rut_promoce_n from rutas where rut_cve_n={0}", cli.getRut_cve_n()), getApplicationContext());

            RutaMayorista = mayorista.equals("1");
            RutaPromoCe = promoce.equals("1");

            return cli;

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.error_cargarInfo), e.getMessage());
            Log.d("salida", "entro a catch obtener clientes");
            return null;
        }
    }

    private int ObtenerIdCat(String cat)
    {
        int id;
        try {
            String idStr = BaseLocal.SelectDato(string.formatSql("select cat_cve_n from categorias where cat_desc_str='{0}'", cat), getApplicationContext());
            if (idStr != null)
                id = Integer.parseInt(idStr);
            else
                id = -1;
        } catch (Exception ex) {
            id = -1;
        }

        return id;
    }

    private int ObtenerListaPrecio()
    {
        int lp = -1;
        try {
            String lpStr = BaseLocal.SelectDato(string.formatSql("select lpre_cve_n from clientes where cli_cve_n={0}", noCli), getApplicationContext());

            if (lpStr != null)
                lp = Integer.parseInt(lpStr);
            else
                lp = -1;

        } catch (Exception ex) {
            lp = -1;
        }
        return lp;
    }

    private int ObtenerListaPrecio(String Lista)
    {
        int lp = -1;
        try {
            String lpStr = BaseLocal.SelectDato(string.formatSql("select lpre_cve_n from listaprecios where lpre_desc_str='{0}'", Lista), getApplicationContext());

            if (lpStr != null)
                lp = Integer.parseInt(lpStr);
            else
                lp = -1;
        } catch (Exception ex) {
            lp = -1;
        }
        return lp;
    }

    private void ObtenerProductos()
    {
        try {
            dtProductos = null;
            String json = BaseLocal.Select(string.formatSql2(Querys.ListaPrecio.ListaPreciosRepLpreNota2, rc.getLpre_cve_n(), rc.getNota_cve_n(), String.valueOf(catenv), conf.getRutaStr()), getApplicationContext());
            dtProductos = ConvertirRespuesta.getProductosPedJson(json);

            for (int i = 0; i < dtProductos.size(); i++) {
                dtProductos.get(i).setProm_kit_n("0");

                String subtotal = String.valueOf(Double.parseDouble(dtProductos.get(i).getLpre_precio_n()) * Double.parseDouble(dtProductos.get(i).getProd_cant_n()));
                dtProductos.get(i).setSubtotal(subtotal);
            }
            if (!Utils.getBool(rc.getCli_fba_n()) && !RutaMayorista && !Utils.getBool(rc.getCli_especial_n()) && !RutaPromoCe) {
                ArrayList<DataTableLC.Promociones5> promo = ObtenerPromociones5(rc.getNvc_nivel_n(), rc.getLpre_cve_n(), rc.getSeg_cve_n(),
                        rc.getGiro_cve_n(), rc.getTcli_cve_n(), rc.getNvc_nivel_n());

                dtKits = ObtenerPromocionesKit(rc.getNvc_nivel_n(), rc.getLpre_cve_n(), rc.getSeg_cve_n(),
                        rc.getGiro_cve_n(), rc.getTcli_cve_n(), rc.getNvc_nivel_n());

                if (promo != null && dtProductos != null) {
                    DataTableLC.Promociones5 r;
                    for (int i = 0; i < promo.size(); i++) {
                        r = promo.get(i);

                        switch (Integer.parseInt(r.getTprom_cve_n())) {
                            case 1:
                                DataTableLC.ProductosPed ri = null;
                                int k = -1;
                                for (int j = 0; j < dtProductos.size(); j++) {
                                    if (dtProductos.get(i).getProd_cve_n().equals(r.getProd_cve_n())) {
                                        ri = dtProductos.get(i);
                                        k = j;
                                    }
                                }

                                if (ri != null) {
                                    if (Double.parseDouble(r.getLpre_precio_n()) < Double.parseDouble(dtProductos.get(k).getLpre_cliente_n())) {
                                        dtProductos.get(k).setLpre_promo_n(r.getLpre_precio_n());
                                        dtProductos.get(k).setLpre_precio_n(r.getLpre_precio_n());
                                        dtProductos.get(k).setLpre_precio2_n(r.getLpre_precio2_n() == null ? r.getLpre_precio_n() : r.getLpre_precio2_n());
                                        dtProductos.get(k).setProd_promo_n("1");
                                        dtProductos.get(k).setProm_cve_n(r.getProm_cve_n());
                                        dtProductos.get(k).setProm_contado_n(r.getProm_contado_n());
                                    } else {
                                        if (r.getLpre_precio2_n() != null) {
                                            if (Double.parseDouble(r.getLpre_precio2_n()) < Double.parseDouble(dtProductos.get(k).getLpre_cliente_n())) {
                                                dtProductos.get(k).setLpre_promo_n(dtProductos.get(k).getLpre_cliente_n());
                                                dtProductos.get(k).setLpre_precio_n(dtProductos.get(k).getLpre_cliente_n());
                                                dtProductos.get(k).setLpre_precio2_n(r.getLpre_precio2_n() == null ? dtProductos.get(k).getLpre_cliente_n() : r.getLpre_precio2_n());
                                                dtProductos.get(k).setProd_promo_n("1");
                                                dtProductos.get(k).setProm_cve_n(r.getProm_cve_n());
                                                dtProductos.get(k).setProm_contado_n(r.getProm_contado_n());
                                            }
                                        }
                                    }
                                }

                                break;
                        }
                    }
                }

            }

            pedidosVM.setDtProductos(dtProductos);
            dgProd2= new ArrayList<>();
            pedidosVM.setDgPro2(dgProd2);

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped6), e.getMessage());
        }
    }

    private void ObtenerPagos()
    {
        try
        {
            String con = string.formatSql2("select * from pagos where cli_cve_n={0} and pag_cobranza_n=1", noCli);
            String json = BaseLocal.Select(con, getApplicationContext());

            dtPagos = ConvertirRespuesta.getPagosALJson(json);
        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped9), e.getMessage());
        }
    }

    private void ListarFormaPago()
    {
        try
        {
            String con = "select fpag_cve_n,fpag_desc_str,fpag_reqbanco_n,fpag_reqreferencia_n,est_cve_str from formaspago where est_cve_str='A'";
            String json = BaseLocal.Select(con, getApplicationContext());
            formasPago = ConvertirRespuesta.getFormasPagoJson(json);
            pedidosVM.setFormasPago(formasPago);
        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped10), e.getMessage());
        }
    }

    private void ListarFormaPago2()
    {
        try
        {
            String con = "select fpag_cve_n,fpag_desc_str,fpag_reqbanco_n,fpag_reqreferencia_n,est_cve_str from formaspago where fpag_desc_str<>'CREDITO' and est_cve_str='A'";
            String json = BaseLocal.Select(con, getApplicationContext());
            formasPago2 = ConvertirRespuesta.getFormasPagoJson(json);
        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped10), e.getMessage());
        }
    }

    private void ListarEnvase(int lb)
    {
        try
        {
            String qry = "select p.prod_cve_n,p.prod_sku_str,p.prod_desc_str,lp.lpre_precio_n,(coalesce(iv.inv_inicial_n,0) " +
                    "+coalesce(iv.inv_recarga_n,0)-coalesce(iv.inv_devuelto_n,0)-coalesce(iv.inv_vendido_n,0) " +
                    "-coalesce(iv.inv_prestado_n,0)+coalesce(iv.inv_recuperado_n,0)) prod_cantiv_n, " +
                    "0 Entregado,0 Recibido,0 Regalo,0 Venta,coalesce(lpb.lpre_precio_n,0) lpre_base_n " +
                    "from precioproductos lp inner join productos p on lp.prod_cve_n=p.prod_cve_n " +
                    "left join inventario iv on p.prod_cve_n=iv.prod_cve_n and iv.rut_cve_n={2} " +
                    "left join precioproductos lpb on lp.prod_cve_n=lpb.prod_cve_n  and lpb.lpre_cve_n={3} " +
                    "where lp.lpre_cve_n={0} and cat_cve_n={1}";

            String con = string.formatSql2(qry, String.valueOf(lpre), String.valueOf(catenv), conf.getRutaStr(), String.valueOf(lb));

            String json = BaseLocal.Select(con, getApplicationContext());

            ArrayList<DataTableLC.EnvasesPed> envases = ConvertirRespuesta.getEnvasesPedJson(json);

            DataTableLC.EnvasesPed e;
            String restante, subAbo, subTot;
            for (int i = 0; i < envases.size(); i++) {
                e = envases.get(i);

                restante = String.valueOf(Integer.parseInt(e.getEntregado()) - Integer.parseInt(e.getRecibido()) - Integer.parseInt(e.getRegalo()) - Integer.parseInt(e.getVenta()));
                subAbo = String.valueOf(Integer.parseInt(e.getRecibido()) + Integer.parseInt(e.getRegalo()) + Integer.parseInt(e.getVenta()));
                subTot = String.valueOf(Double.parseDouble(e.getLpre_precio_n()) * Double.parseDouble(e.getVenta()));

                envases.get(i).setRestante(restante);
                envases.get(i).setSubAbonoEnv(subAbo);
                envases.get(i).setSubtotal(subTot);
            }

            dgEnvase= envases;

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped8), e.getMessage());
        }

    }

    private void ListarAdeudoEnvase()
    {
        try
        {
            String con = "select p.prod_cve_n,p.prod_sku_str,p.prod_desc_str,lp.lpre_precio_n," +
                    "(coalesce(iv.inv_inicial_n,0)+coalesce(iv.inv_recarga_n,0)" +
                    "+coalesce(iv.inv_devuelto_n,0)-coalesce(iv.inv_vendido_n,0)) prod_cantiv_n," +
                    "0 Adeudo,0 Abono,0 Venta from precioproductos lp inner join productos p " +
                    "on lp.prod_cve_n=p.prod_cve_n left join inventario iv on p.prod_cve_n=iv.prod_cve_n " +
                    "and iv.rut_cve_n={2} where lp.lpre_cve_n={0} and p.cat_cve_n={1}";

            con = string.formatSql2(con, String.valueOf(lpre), String.valueOf(catenv), conf.getRutaStr());

            String json = BaseLocal.Select(string.formatSql2(con, String.valueOf( lpre ) , String.valueOf(catenv) , conf.getRutaStr()), getApplicationContext());

            ArrayList<DataTableLC.EnvasesAdeudo> dt = ConvertirRespuesta.getEnvasesAdeudoJson(json);


            con = "select cli_cve_n,prod_cve_n,sum(prod_cant_n) adeudo,sum(prod_cantabono_n) abono " +
                    "from creditos where cli_cve_n={0} and prod_cve_n={1} and cred_esenvase_n=1 " +
                    "group by cli_cve_n,prod_cve_n";

            DataTableLC.EnvasesAdeudo r;
            for (int i = 0; i < dt.size(); i++) {
                r = dt.get(i);
                json = BaseLocal.Select(string.formatSql2(con, noCli, r.getProd_cve_n()), getApplicationContext());

                ArrayList<DataTableLC.EnvasesAdeudo2> dtAdEnv = ConvertirRespuesta.getEnvasesAdeudo2Json(json);

                if (dtAdEnv != null)
                {
                    DataTableLC.EnvasesAdeudo2 rae;
                    for (int j = 0; j < dtAdEnv.size(); j++) {
                        rae = dtAdEnv.get(j);

                        dt.get(i).setAdeudo(String.valueOf(Integer.parseInt(rae.getAdeudo()) - Integer.parseInt(rae.getAbono())));
                    }

                    ArrayList<DataTableLC.EnvasesAdeudo3> dtAbonoEnv;
                    con = "select cli_cve_n,prod_cve_n,sum(prod_abono_n) abono from pagos where prod_cve_n={0} and cli_cve_n={1} and pag_envase_n=1 and trans_est_n<>3 group by cli_cve_n,prod_cve_n";

                    json = BaseLocal.Select(string.formatSql2(con, r.getProd_cve_n(), noCli ), getApplicationContext());
                    dtAbonoEnv = ConvertirRespuesta.getEnvasesAdeudo3Json(json);

                    double abonoAct = 0;

                    if(dtAbonoEnv!=null)
                    for (int j = 0; j < dtAbonoEnv.size(); j++) {
                        abonoAct += Integer.parseInt(dtAbonoEnv.get(j).getAbono());
                    }

                    dt.get(i).setAdeudo(String.valueOf(Integer.parseInt(dt.get(i).getAdeudo()) - abonoAct));
                }
            }

            dgDeudaEnv = dt;

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped11), e.getMessage());
        }
    }

    private ArrayList<DataTableLC.AdeudoNormal> ListarAdeudoNormal()
    {
        try {
            double limCred = Double.parseDouble(rc.getCli_montocredito_n());

            String con = "select cred_cve_n,cred_referencia_str,cred_fecha_dt,cred_monto_n,cred_abono_n," +
                    "0.0 abono from creditos where cli_cve_n={0} and cred_esenvase_n=0 and cred_especial_n=0";

            Log.d("salida", "Consulta normal: " + string.formatSql2(con, noCli));

            String json = BaseLocal.Select(string.formatSql2(con, noCli), getApplicationContext());

            ArrayList<DataTableLC.AdeudoNormal> dtANormal = ConvertirRespuesta.getAdeudoNormalJson(json);

            if (dtANormal != null) {
                DataTableLC.AdeudoNormal r;
                for (int i = 0; i < dtANormal.size(); i++) {
                    r = dtANormal.get(i);
                    if (dtPagos != null) {
                        ArrayList<DataTableLC.Pagos> items = new ArrayList<>();
                        for (int j = 0; j < dtPagos.size(); j++) {
                            if (dtPagos.get(j).getPag_referencia_str().equals(r.getCred_referencia_str()))
                                items.add(dtPagos.get(j));
                        }

                        if (items.size() > 0) {
                            for (int j = 0; j < items.size(); j++) {
                                String abono = String.valueOf(Double.parseDouble(r.getAbono()) + Double.parseDouble(items.get(j).getPag_abono_n()));
                                dtANormal.get(i).setAbono(abono);
                            }
                        }
                    }

                    double saldo = Double.parseDouble(dtANormal.get(i).getCred_monto_n()) - Double.parseDouble(dtANormal.get(i).getCred_abono_n()) - Double.parseDouble(dtANormal.get(i).getAbono());
                    String saldoStr = String.valueOf(saldo);
                    dtANormal.get(i).setSaldo(saldoStr);
                }

            }


            if (dtANormal != null) {
                int diascred = Integer.parseInt(rc.getCli_plazocredito_n());
                double res = 0;
                for (int i = 0; i < dtANormal.size(); i++) {
                    Date fecha1 = Utils.FechaDT(dtANormal.get(i).getCred_fecha_dt());
                    Date fecha2 = Utils.FechaDT(Utils.FechaModificarDias(Utils.FechaLocal(), -diascred));

                    if (fecha1.compareTo(fecha2) < 0) {
                        res += Double.parseDouble(dtANormal.get(i).getSaldo());
                    }
                }

                if (res <= 0) {
                    //txtVencido.BackColor = Color.LightSalmon;
                    //txtVencido.Text = string.Format("{0:C}", res);
                } else {
                    //txtVencido.BackColor = Color.White;
                    //txtVencido.Text = (0).ToString("C");
                }
            }

            if(dtANormal==null)
                dtANormal = new ArrayList<>();

            return dtANormal;

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped12), e.getMessage());
            return null;
        }
    }

    private ArrayList<DataTableLC.AdeudoNormal> ListarAdeudoEspecial()
    {
        try {
            String con = "select cred_cve_n,cred_referencia_str,cred_fecha_dt,cred_monto_n,cred_abono_n," +
                    "cred_abono_n abono from creditos where cli_cve_n={0} and cred_esenvase_n=0 and cred_especial_n=1";

            String json = BaseLocal.Select(string.formatSql2(con, noCli), getApplicationContext());

            ArrayList<DataTableLC.AdeudoNormal> dtAE = ConvertirRespuesta.getAdeudoNormalJson(json);

            if (dtAE != null)
            {
                DataTableLC.AdeudoNormal r;
                for (int i = 0; i < dtAE.size(); i++) {
                    r = dtAE.get(i);

                    if (dtPagos != null) {
                        ArrayList<DataTableLC.Pagos> items = new ArrayList<>();
                        for (int j = 0; j < dtPagos.size(); j++) {
                            if (r.getCred_referencia_str().equals(dtPagos.get(j).getPag_referencia_str())) {
                                items.add(dtPagos.get(j));
                            }
                        }
                        if (items.size() > 0) {
                            for (int j = 0; j < items.size(); j++) {
                                double abono = Double.parseDouble(r.getAbono()) + Double.parseDouble(items.get(i).getPag_abono_n());
                                dtAE.get(i).setAbono(String.valueOf(abono));
                            }
                        }
                    }
                }
            }
            else
                dtAE = new ArrayList<>();

            return dtAE;
        } catch (Exception e) {
            return null;
        }
    }

    private void ValidaClienteInactivo()
    {
        try {
            if (rc.getEst_cve_str().equals("I")) {
                removerVenta = true;

                String con = string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), rc.getCli_cve_n(), "CLIENTE INACTIVO", "CLIENTE INACTIVO ABIERTO SOLO PARA ABONO", PositionStr);

                BaseLocal.Insert(con, getApplicationContext());

                Toast.makeText(getApplicationContext(), getString(R.string.tt_ped9), Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            Utils.msgError(this, getString(R.string.err_ped13), ex.getMessage());
        }
    }

    private void ValidarSegundaVenta()
    {
        try {
            String lpreinvalidas = "8,9,10,16,17,18,20,21,22,23,24,26,27,28,29,30,32,33,35,36,37,43,51,52";

            String con = string.formatSql("select * from ventas where cli_cve_n={0} and lpre_cve_n in ({1})", rc.getCli_cve_n(), lpreinvalidas);
            String json = BaseLocal.Select(con, getApplicationContext());
            ArrayList<DataTableLC.Venta> dt = ConvertirRespuesta.getVentaJson(json);

            if (dt != null) {
                if (Utils.getBool(rc.getCli_dobleventa_n())) {
                    con = string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido,
                            conf.getUsuario(), conf.getRutaStr(), rc.getCli_cve_n(), "VALIDAR DOBLE VENTA", "INVALIDADO POR SISTEMAS", PositionStr);
                    BaseLocal.Insert(con, getApplicationContext());

                } else {
                    con = string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido,
                            conf.getUsuario(), conf.getRutaStr(), rc.getCli_cve_n(), "VALIDAR DOBLE VENTA", "CLIENTE ABIERTO SOLO PARA ABONO", PositionStr);
                    BaseLocal.Insert(con, getApplicationContext());

                    removerVenta = true;

                    Toast.makeText(getApplicationContext(), getString(R.string.tt_ped10), Toast.LENGTH_LONG).show();

                }
            }

        } catch (Exception ex) {
            Utils.msgError(this, getString(R.string.err_ped14), ex.getMessage());
        }
    }

    private void CargarPreventa()
    {
        try {
            String con = string.formatSql("Select * from visitapreventa where cli_cve_n={0}", rc.getCli_cve_n());
            String json = BaseLocal.Select(con, getApplicationContext());

            ArrayList<DataTableWS.VisitaPreventa> dtVisPre = ConvertirRespuesta.getVisitaPreventaJson(json);

            if (dtVisPre != null && dtVisPre.size() > 0) {
                FolioVisita = dtVisPre.get(0).getVisp_folio_str();

                String fechaAyerStr = Utils.FechaLocal();

                if (Utils.DiaActual().equals("Lunes"))
                    fechaAyerStr = Utils.FechaModificarDias(fechaAyerStr, -2);
                else
                    fechaAyerStr = Utils.FechaModificarDias(fechaAyerStr, -1);

                con = string.formatSql2("select d.prev_folio_str,d.prod_cve_n,d.prod_sku_str, " +
                        "d.prod_envase_n,sum(d.prod_cant_n) prod_cant_n,max(d.lpre_base_n) lpre_base_n, " +
                        "max(d.lpre_cliente_n) lpre_cliente_n,max(d.lpre_promo_n) lpre_promo_n, " +
                        "max(d.lpre_precio_n) lpre_precio_n,d.prod_promo_n,p.id_envase_n from preventa v " +
                        "inner join preventadet d on v.prev_folio_str=d.prev_folio_str " +
                        "inner join productos p on d.prod_cve_n=p.prod_cve_n " +
                        "where v.cli_cve_n={0} and (v.prev_fecha_dt>='{1} 00:00:00' " +
                        "and v.prev_fecha_dt<='{2} 23:59:59') and v.trans_est_n is null " +
                        "group by d.prev_folio_str,d.prod_cve_n,d.prod_sku_str, " +
                        "d.prod_envase_n,p.id_envase_n,d.prod_promo_n", rc.getCli_cve_n(), fechaAyerStr, fechaAyerStr);


                json = BaseLocal.Select(con, getApplicationContext());

                ArrayList<DataTableLC.PreventaPedidos> dtPrev = ConvertirRespuesta.getPreventaPedidosJson(json);

                if (dtPrev != null)
                {
                    FolioPreventa = dtPrev.get(0).getPrev_folio_str();

                    con = string.formatSql("Select ven_folio_str from ventas where prev_folio_str='{0}'", FolioPreventa);

                    String ven_folio = BaseLocal.SelectDato(con, getApplicationContext());

                    if (ven_folio != null) {
                        FolioPreventa = "";
                        Utils.msgInfo(this, getString(R.string.tt_ped11));
                        return;
                    }

                    //Carga de la preventa
                    boolean invp = false;
                    boolean invf = false;
                    double cantp = 0;

                    for (DataTableLC.PreventaPedidos r : dtPrev) {
                        invp = false;
                        invf = false;
                        cantp = 0;

                        ArrayList<DataTableLC.ProductosPed> ri = new ArrayList<>();
                        for (DataTableLC.ProductosPed item : dtProductos) {
                            if (r.getProd_sku_str().equals(item.getProd_sku_str()))
                                ri.add(item);
                        }

                        if (ri.size() > 0) {
                            invp = false;
                            invf = false;

                            dgProd2.add(ri.get(0));
                            int ra = dgProd2.size() - 1;

                            double CantSol = Double.parseDouble(r.getProd_cant_n());
                            double CantAct = Double.parseDouble(ri.get(0).getProd_cantiv_n());

                            if (CantAct >= CantSol)
                                cantp = CantSol;
                            else {
                                cantp = CantAct;
                                invf = true;
                            }

                            dgProd2.get(ra).setProd_cant_n(String.valueOf(cantp));
                            dgProd2.get(ra).setProd_promo_n(String.valueOf(r.getProd_promo_n()));

                        } else {
                            invp = true;
                            cantp = 0;
                        }

                        ListaPrev.add(new DataTableLC.CompPrevDet(
                                r.getProd_cve_n(), r.getProd_sku_str(), r.getProd_cant_n(), String.valueOf(cantp),
                                "0", String.valueOf(invp), String.valueOf(invf), r.getId_envase_n() == null ? r.getId_envase_n() : "0"
                        ));

                    }

                    if (dgProd2.size() > 0) {
                        CalcularVenta();
                        CalcularEnvase();
                    }

                    //Carga cobranza

                    con = string.formatSql2("select p.*,f.fpag_desc_str," +
                            "f.fpag_reqreferencia_n,f.fpag_reqbanco_n from preventapagos p " +
                            "inner join formaspago f on p.fpag_cve_n=f.fpag_cve_n " +
                            "where prev_folio_str='{0}'", FolioPreventa);
                    json = BaseLocal.Select(con, getApplicationContext());

                    ArrayList<DataTableLC.PreventaPagos> dtPagPrev = ConvertirRespuesta.getPreventaPagosLCJson(json);

                    int kp = 1;
                    int kc = 1;

                    if (dtPagPrev != null) {
                        for (DataTableLC.PreventaPagos r : dtPagPrev) {
                            if (Utils.getBool(r.getPpag_cobranza_n())) {
                                DataTableLC.DgAbonos nc = new DataTableLC.DgAbonos();

                                nc.setNoAbono(String.valueOf(kc));
                                nc.setFpag_cve_n(r.getFpag_cve_n());
                                nc.setFpag_desc_str(r.getFpag_desc_str());
                                nc.setFpag_cant_n(r.getFpag_cant_n());
                                nc.setBancoA("");
                                if (Utils.getBool(r.getFpag_reqbanco_n()) || Utils.getBool(r.getFpag_reqreferencia_n()))
                                    nc.setBancoA("Falta Ref");

                                nc.setReferenciaA("");

                                dgAbonos.add(nc);
                                kc++;
                            } else {
                                DataTableLC.DgPagos np = new DataTableLC.DgPagos();

                                np.setNoPago(String.valueOf(kp));
                                np.setFpag_cve_n(r.getFpag_cve_n());
                                np.setFpag_desc_str(r.getFpag_desc_str());
                                np.setFpag_cant_n(r.getFpag_cant_n());
                                np.setBancoP("");

                                if (Utils.getBool(r.getFpag_reqbanco_n()) || Utils.getBool(r.getFpag_reqreferencia_n()))
                                    np.setBancoP("Falta Ref");

                                np.setReferenciaP("");

                                dgPagos.add(np);
                                kp++;
                            }

                            if (Utils.getBool(r.getFpag_reqbanco_n()) || Utils.getBool(r.getFpag_reqreferencia_n())) {
                                ReqRefencia = true;
                            }

                        }
                    }

                    //Carga Envases preventa
                    con = string.formatSql("select * from preventaenv where prev_folio_str='{0}'", FolioPreventa);
                    json = BaseLocal.Select(con, getApplicationContext());

                    ArrayList<DataTableWS.PreventaEnv> dtCobranzaEnv = ConvertirRespuesta.getPreventaEnvJson(json);

                    if (dtCobranzaEnv != null) {
                        for (DataTableWS.PreventaEnv r : dtCobranzaEnv) {
                            DataTableLC.EnvasesAdeudo re = null;
                            DataTableLC.EnvasesPed rea = null;

                            if (dgDeudaEnv != null)
                                for (int i = 0; i < dgDeudaEnv.size(); i++)
                                    if (dgDeudaEnv.get(i).getProd_sku_str().equals(r.getProd_sku_str())) {
                                        re = dgDeudaEnv.get(i);
                                    }

                            if (dgEnvase != null)
                                for (int i = 0; i < dgEnvase.size(); i++)
                                    if (dgEnvase.get(i).getProd_sku_str().equals(r.getProd_sku_str())) {
                                        rea = dgEnvase.get(i);
                                    }

                            int adeudo = 0;
                            int entregado = 0;
                            int abonoPre = 0;
                            int ventaPre = 0;
                            int SaldoTotal = 0;
                            int AbonoTotal = 0;
                            int sabonoPre = 0;
                            int sventaPre = 0;

                            if (re != null) {
                                adeudo = Integer.parseInt(re.getAdeudo());
                                entregado = Integer.parseInt(rea.getEntregado());
                                SaldoTotal = adeudo + entregado;
                                abonoPre = Integer.parseInt(r.getProd_abono_n());
                                ventaPre = Integer.parseInt(r.getProd_venta_n());
                                AbonoTotal = abonoPre + ventaPre;

                                if (SaldoTotal >= AbonoTotal) {
                                    if (adeudo >= AbonoTotal) {
                                        re.setAbono(String.valueOf(abonoPre));
                                        re.setVenta(String.valueOf(ventaPre));
                                    } else {
                                        if (adeudo >= abonoPre) {
                                            re.setAbono(String.valueOf(abonoPre));
                                            sventaPre = adeudo - abonoPre;
                                            ventaPre = ventaPre - sventaPre;

                                            re.setVenta(String.valueOf(sventaPre));
                                            if (entregado >= ventaPre)
                                                rea.setVenta(String.valueOf(ventaPre));
                                            else
                                                rea.setVenta(String.valueOf(entregado));
                                        } else {
                                            sabonoPre = abonoPre - adeudo;
                                            re.setAbono(String.valueOf(adeudo));
                                            if (entregado >= sabonoPre) {
                                                if (entregado >= (sabonoPre + ventaPre)) {
                                                    rea.setRecibido(String.valueOf(sabonoPre));
                                                    rea.setVenta(String.valueOf(ventaPre));
                                                } else {
                                                    rea.setRecibido(String.valueOf(sabonoPre));
                                                    rea.setVenta(String.valueOf(ventaPre - sabonoPre));
                                                }
                                            } else {
                                                rea.setRecibido(String.valueOf(entregado));
                                            }
                                        }
                                    }
                                } else {
                                    sabonoPre = abonoPre - adeudo;
                                    re.setAbono(String.valueOf(adeudo));
                                    if (entregado >= sabonoPre) {
                                        if (entregado >= (sabonoPre + ventaPre)) {
                                            rea.setRecibido(String.valueOf(sabonoPre));
                                            rea.setVenta(String.valueOf(ventaPre));
                                        } else {
                                            rea.setRecibido(String.valueOf(sabonoPre));
                                            rea.setVenta(String.valueOf(ventaPre - sabonoPre));
                                        }
                                    } else {
                                        rea.setRecibido(String.valueOf(entregado));

                                    }
                                }
                            }
                        }

                        CalcularTotales();
                        ValidaCredito();
                    }

                    pedidosVM.setDgPro2( dgProd2);
                }
            } else
                Toast.makeText(this, getString(R.string.tt_ped12), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped15), e.getMessage());
        }
    }

    private void CalcularVenta()
    {
        try {
            double calculo = 0;
            double DescKit = 0;
            DescKit = 0;//decimal.Parse(txtKit.Text.Replace("$", ""));

            for (DataTableLC.ProductosPed p : dgProd2)
                calculo += Double.parseDouble(p.getSubtotal());

            //txtSubtotal2.Text = string.Format("{0:C}", decimal.Parse(calculo.ToString()));

            calculo = 0;
            for (DataTableLC.ProductosPed p : dgProd2)
                if (p.getProd_vtaefectivo_n().equals("1") || (p.getProm_contado_n().equals("1") && p.getProd_promo_n().equals("1")))
                    calculo += Double.parseDouble(p.getSubtotal());


            double ventaEA = 0; //decimal.Parse(txtSaldoDeudaEnv.Text.Replace("$", ""));
            double ContEsp = 0; //decimal.Parse(calculo.ToString())+ventaEA;

            if (ContEsp >= DescKit)
                ContEsp -= DescKit;
            else
                ContEsp = 0;

            //txtContado.Text = string.Format("{0:C}", ContEsp);

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped16), e.getMessage());
        }
    }

    private void CalcularEnvase()
    {
        try {
            boolean HayNegativos = false;

            for (DataTableLC.EnvasesPed r : dgEnvase) {
                double can = 0;
                for (DataTableLC.ProductosPed p : dgProd2) {
                    if (p.getId_envase_n().equals(r.getProd_cve_n()))
                        can += Double.parseDouble(p.getProd_cant_n());
                }
                r.setEntregado(String.valueOf(can));

                if (Integer.parseInt(r.getRestante()) < 0) {
                    r.setRecibido("0");
                    r.setVenta("0");
                    HayNegativos = true;
                }
            }

            ArrayList<DataTableLC.PromocionesEnv> PromoEnv = ObtenerPromoEnvase( rc.getNvc_nivel_n(),
                    rc.getLpre_cve_n(), rc.getSeg_cve_n(), rc.getGiro_cve_n(), rc.getTcli_cve_n(), rc.getNvc_nivel_n());

            if(PromoEnv!=null)
            {
                boolean hayPromoEnv=false;
                for(DataTableLC.PromocionesEnv p : PromoEnv)
                {
                    if(p.getTprom_cve_n().equals("7"))
                    {
                        hayPromoEnv=true;
                        break;
                    }
                }

                if(hayPromoEnv)
                {
                    for(DataTableLC.EnvasesPed r : dgEnvase)
                        if( r.getProd_sku_str().equals("264") || r.getProd_sku_str().equals("432") )
                            r.setRegalo( r.getEntregado() );
                }
                else
                {
                    hayPromoEnv=false;
                    for(DataTableLC.PromocionesEnv p : PromoEnv)
                    {
                        if(p.getTprom_cve_n().equals("6"))
                        {
                            hayPromoEnv=true;
                            break;
                        }
                    }

                    if(hayPromoEnv)
                    {
                        for(DataTableLC.EnvasesPed r : dgEnvase)
                        {
                            if (r.getProd_sku_str().equals("264") || r.getProd_sku_str().equals("432"))
                            {
                                ArrayList<DataTableLC.ProductosPed> gb = new ArrayList<>();
                                ArrayList<String> clavesGroupby = new ArrayList<>();

                                for (DataTableLC.ProductosPed p : dgProd2)
                                {
                                    //filtro de id_envase = null
                                    if (p.getId_envase_n() != null && !p.getId_envase_n().isEmpty())
                                    {
                                        //filtro de groupby
                                        String cve = p.getFam_cve_n() + "_" + p.getId_envase_n();
                                        if (!clavesGroupby.contains(cve))
                                        {
                                            gb.add(p);
                                            clavesGroupby.add(cve);
                                        }
                                    }
                                }

                                int kp = 0;

                                for(DataTableLC.ProductosPed x : gb)
                                {
                                    if(x.getId_envase_n().equals( r.getProd_cve_n() ))
                                    {

                                        ArrayList<DataTableLC.PromocionesEnv> rp = new ArrayList<>();

                                        for(DataTableLC.PromocionesEnv pe : PromoEnv)
                                        {
                                            if( pe.getFam_cve_n().equals( x.getFam_cve_n() ) &&
                                                pe.getTprom_cve_n().equals("6") &&
                                                pe.getProd_regalo_n().equals( x.getId_envase_n() ))
                                                rp.add(pe);
                                        }

                                        for(DataTableLC.PromocionesEnv p : rp)
                                        {
                                            int m = Integer.parseInt(p.getProm_m_n());
                                            int n = Integer.parseInt( p.getProm_n_n() );
                                            int k = Integer.parseInt(x.getProd_cant_n());
                                            kp += (k / m) * n;
                                        }
                                        r.setRegalo(String.valueOf(kp));
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        hayPromoEnv=false;
                        for(DataTableLC.PromocionesEnv p : PromoEnv)
                        {
                            if(p.getTprom_cve_n().equals("5"))
                            {
                                hayPromoEnv=true;
                                break;
                            }
                        }

                        if(hayPromoEnv)
                        {
                            for(DataTableLC.EnvasesPed r : dgEnvase )
                            {
                                if (r.getProd_sku_str().equals("264") || r.getProd_sku_str().equals("432"))
                                {
                                    int kp = 0;

                                    ArrayList<DataTableLC.ProductosPed> rv = new ArrayList<>();
                                    for (DataTableLC.ProductosPed p : dgProd2)
                                    {
                                        if (p.getId_envase_n().equals( r.getProd_cve_n() ) )
                                            rv.add(p);
                                    }

                                    for(DataTableLC.ProductosPed rea : rv)
                                    {
                                        ArrayList<DataTableLC.PromocionesEnv> rp = new ArrayList<>();
                                        for(DataTableLC.PromocionesEnv p : PromoEnv)
                                        {
                                            if( p.getTprom_cve_n().equals("5") &&
                                                p.getProd_regalo_n().equals(r.getProd_cve_n()) &&
                                                p.getProd_cve_n().equals( rea.getProd_cve_n()))
                                            {
                                                rp.add(p);
                                            }
                                        }

                                        for(DataTableLC.PromocionesEnv p : rp)
                                        {
                                            int m = Integer.parseInt(p.getProm_m_n() );
                                            int n = Integer.parseInt(p.getProm_n_n());
                                            int k = Integer.parseInt(rea.getProd_cant_n());
                                            kp += (k/m)*n;
                                        }
                                    }
                                    r.setRegalo( String.valueOf(kp) );
                                }
                            }
                        }
                    }
                }
            }

            if (HayNegativos)
            {
                Toast.makeText(this, getString(R.string.tt_ped13), Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped17), e.getMessage());
        }
    }

    private void CalcularTotales()
    {
        try
        {
            // DescKit = decimal.Parse(txtKit.Text.Replace("$", ""));

            /*txtVenta.Text = (decimal.Parse(this.txtSubtotal2.Text.Replace("$", "")) +
                    decimal.Parse(txtSubEnv.Text.Replace("$", "")) +
                    decimal.Parse(txtSaldoDeudaEnv.Text.Replace("$", ""))).ToString("c");*/
            if ( dgPagos.size() >0)
            {
                double suma=0;

                for(DataTableLC.DgPagos p : dgPagos)
                {
                    suma += Double.parseDouble( p.getFpag_cant_n() );
                }

                //txtSaldo.Text = (decimal.Parse(txtVenta.Text.Replace("$", "")) - DescKit - SUMA

            }
            //else
                //txtSaldo.Text = string.Format("{0:c}",decimal.Parse(txtVenta.Text.Replace("$",""))-DescKit);

        }
        catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_ped18), e.getMessage());
        }
    }

    public void ValidaCredito()
    {
        try
        {
            if ((!Utils.getBool(rc.getCli_eshuix_n()) && ActivarCredito()) || Utils.getBool(rc.getCli_eshuix_n()) && lpreCO)
            {
                int indice = 0;
                for (DataTableWS.FormasPago fp : formasPago)
                {
                    if (fp.getFpag_desc_str().equals("CREDITO"))
                    {
                        formasPago.remove(indice);
                        break;
                    }
                    indice++;
                }
                pedidosVM.setFormasPago(formasPago);
            }
        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_ped20), e.getMessage());
        }
    }

    double totDeuda = 0, totAbono = 0, montoCredito = 0, disponible = 0;
    double totUtilizado = 0;
    double saldoVencido = 0;
    private boolean ActivarCredito()
    {
        try
        {
            ListarFormaPago();

            boolean SinCredito = false;
            totDeuda = Double.parseDouble( string.DelCaracteres( _tv_adeudoN )  ) ; //decimal.Parse(txtAdeudoN.Text.Replace("$", ""));
            totAbono = Double.parseDouble( string.DelCaracteres( _tv_totAbono)  ); //decimal.Parse(txtTotAbono.Text.Replace("$", ""));
            montoCredito = Double.parseDouble(rc.getCli_montocredito_n());

            disponible = montoCredito - (totDeuda - totAbono);

            totUtilizado=0;
            for(DataTableLC.DgPagos dg : dgPagos)
            {
                if(dg.getFpag_desc_str().equals("CREDITO"))
                    totUtilizado += Double.parseDouble( dg.getFpag_cant_n()  );
            }

            disponible -= totUtilizado;

            int diascred =  Integer.parseInt( rc.getCli_plazocredito_n() );

            saldoVencido=0;
            for(DataTableLC.AdeudoNormal an: dtAN )
            {
                Date f1 = Utils.FechaDT( an.getCred_fecha_dt() );
                Date f2 = Utils.FechaDT( Utils.FechaModificarDias( Utils.FechaLocal(),-diascred ) );

                if(f1.compareTo(f2)<=0)
                    saldoVencido+= Double.parseDouble( an.getSaldo() );
            }

            if (  !Utils.getBool( rc.getCli_credito_n()))
            {
                SinCredito = true;
                pedidosVM.setEstadoCredito("Sin Crédito");
                return SinCredito;
            }

            if ( !rc.getCli_estcredito_str().equals("A"))
            {
                SinCredito = true;
                pedidosVM.setEstadoCredito("Cancelado");
                return SinCredito;
            }

            if (disponible <= 0)
            {
                SinCredito = true;
                pedidosVM.setEstadoCredito("Sin disponible");
                return SinCredito;
            }

            if (saldoVencido - totAbono > 0)
            {
                SinCredito = true;
                pedidosVM.setEstadoCredito("vencido");
                return SinCredito;
            }

            return SinCredito;

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_ped19), e.getMessage());
            return false;
        }
    }

    private ArrayList<DataTableLC.Promociones5> ObtenerPromociones5(String nivel, String lpre, String segmento, String giro, String tcli, String nivelhl)
    {
        try {
            if (segmento == null || segmento.isEmpty())
                segmento = "null";
            if (giro == null || giro.isEmpty())
                giro = "null";
            if (tcli == null || tcli.isEmpty())
                tcli = "null";

            String json = BaseLocal.Select(string.formatSql2("select p.*,coalesce(nvc.nvc_nivel_n,0) nvc_nivel_n, " +
                            "case when {0}>=coalesce(nvc.nvc_nivel_n,0) then 0 else 1 end prom_contado_n from promociones p " +
                            "left join nivelcliente nvc on p.nvc_cve_n=nvc.nvc_cve_n " +
                            "left join nivelcliente ncl on p.nvc_cvehl_n=ncl.nvc_cve_n " +
                            "where datetime('now','localtime') between p.prom_fini_dt and p.prom_ffin_dt " +
                            "and (lpre_cve_n={1} or lpre_cve_n=0) " +
                            "and (seg_cve_n={2} or seg_cve_n=0) " +
                            "and (giro_cve_n={3} or giro_cve_n=0) " +
                            "and (tcli_cve_n={4} or tcli_cve_n=0) " +
                            "and ({5}<=coalesce(ncl.nvc_nivel_n,0) or coalesce(ncl.nvc_nivel_n,0)=0) " +
                            "and tprom_cve_n in (1,2) and prom_envase_n=0 and prom_kit_n=0",
                    nivel, lpre, segmento, giro, tcli, nivelhl), getApplicationContext());

            ArrayList<DataTableLC.Promociones5> promociones = ConvertirRespuesta.getPromociones5Json(json);

            return promociones;


        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped7), e.getMessage());
            return null;
        }
    }

    private ArrayList<DataTableLC.PedPromocionesKit> ObtenerPromocionesKit(String nivel, String lpre, String segmento, String giro, String tcli, String nivelhl)
    {
        try {
            if (segmento == null || segmento.isEmpty())
                segmento = "null";
            if (giro == null || giro.isEmpty())
                giro = "null";
            if (tcli == null || tcli.isEmpty())
                tcli = "null";

            String con = string.formatSql2("select p.prom_cve_n,p.prom_folio_str," +
                            "p.prom_desc_str,p.tprom_cve_n,p.prom_falta_dt,p.prom_fini_dt," +
                            "p.prom_ffin_dt,p.est_cve_str,p.usu_cve_str,p.usu_modificacion_str, " +
                            "p.prom_fmodificacion_dt,k.prod_cve_n,k.prod_sku_str,k.prod_cant_n, " +
                            "k.lpre_precio_n,p.prom_nivel_n,p.lpre_cve_n,p.nvc_cve_n,p.nvc_cvehl_n, " +
                            "p.fam_cve_n,p.seg_cve_n,p.giro_cve_n,p.tcli_cve_n,p.prom_story_n, " +
                            "p.prom_proveedor_n,coalesce(nvc.nvc_nivel_n,0) nvc_nivel_n, " +
                            "case when {0}>=coalesce(nvc.nvc_nivel_n,0) then 0 else 1 end prom_contado_n, " +
                            "0 prom_veces_n from promociones p " +
                            "inner join PromocionesKit k on p.prom_cve_n=k.prom_cve_n " +
                            "left join nivelcliente nvc on p.nvc_cve_n=nvc.nvc_cve_n " +
                            "left join nivelcliente ncl on p.nvc_cvehl_n=ncl.nvc_cve_n " +
                            "where datetime('now','localtime') between p.prom_fini_dt and p.prom_ffin_dt " +
                            "and (lpre_cve_n={1} or lpre_cve_n=0) " +
                            "and (seg_cve_n={2} or seg_cve_n=0) " +
                            "and (giro_cve_n={3} or giro_cve_n=0) " +
                            "and (tcli_cve_n={4} or tcli_cve_n=0) " +
                            "and ({5}<=coalesce(ncl.nvc_nivel_n,0) or coalesce(ncl.nvc_nivel_n,0)=0) " +
                            "and tprom_cve_n in (8) and prom_envase_n=0 and prom_kit_n=1",
                    nivel, lpre, segmento, giro, tcli, nivelhl);

            String json = BaseLocal.Select(con, getApplicationContext());

            ArrayList<DataTableLC.PedPromocionesKit> promKit = ConvertirRespuesta.getPedPromocionesKitJson(json);

            return promKit;

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped7), e.getMessage());
            return null;
        }
    }

    private ArrayList<DataTableLC.PromocionesEnv> ObtenerPromoEnvase(String nivel, String lpre, String segmento, String giro, String tcli, String nivelhl)
    {
        try {

            if (segmento == null || segmento.isEmpty())
                segmento = "null";
            if (giro == null || giro.isEmpty())
                giro = "null";
            if (tcli == null || tcli.isEmpty())
                tcli = "null";

            String con = string.formatSql2("select p.*,coalesce(nvc.nvc_nivel_n,0) nvc_nivel_n, " +
                    "case when {0}>=coalesce(nvc.nvc_nivel_n,0) then 0 else 1 end prom_contado_n from promociones p " +
                    "left join nivelcliente nvc on p.nvc_cve_n=nvc.nvc_cve_n " +
                    "left join nivelcliente ncl on p.nvc_cvehl_n=ncl.nvc_cve_n " +
                    "where datetime('now','localtime') between p.prom_fini_dt and p.prom_ffin_dt " +
                    "and (lpre_cve_n={1} or lpre_cve_n=0) " +
                    "and (seg_cve_n={2} or seg_cve_n=0) " +
                    "and (giro_cve_n={3} or giro_cve_n=0) " +
                    "and (tcli_cve_n={4} or tcli_cve_n=0) " +
                    "and ({5}<=coalesce(ncl.nvc_nivel_n,0) or coalesce(ncl.nvc_nivel_n,0)=0) " +
                    "and tprom_cve_n in (7,6,5) and prom_envase_n=1", nivel, lpre, segmento, giro, tcli, nivelhl);

            String json = BaseLocal.Select(con, getApplicationContext());

            ArrayList<DataTableLC.PromocionesEnv> dtPromo = ConvertirRespuesta.getPromocionesEnvJson(json);

            return dtPromo;

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_ped7), e.getMessage());
            return null;
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_guardar:
                Log.d("salida","Presiono boton guardar");
                return true;
            case R.id.action_detalles:
                Log.d("salida","Presiono boton detalles");
                return true;
            case R.id.action_buscar:
                Log.d("salida","Presiono boton buscar");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //region variables compartidas

    public boolean getEsVenta()
    {
        return esventa;
    }
    public DataTableLC.DtCliVentaNivel getRC()
    {
        return rc;
    };
    public double getDisponible()
    {
        return disponible;
    };

    //endregion

}
