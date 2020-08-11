package com.tdt.easyroute.Ventanas.Ventas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerPedidosAdapter;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.ViewPagerNonSwipable;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Clientes.BuscarClientesActivity;
import com.tdt.easyroute.Ventanas.Pedidos.DetallesCliente.MainDetallesActivity;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;
import java.util.Date;

public class PedidosActivity extends AppCompatActivity implements         GoogleApiClient.OnConnectionFailedListener,
                    GoogleApiClient.ConnectionCallbacks,
                    LocationListener {

    private boolean esventa;
    private String noCli;
    private String Cliente;
    private boolean ConVenta;
    private boolean PorEscanear;
    private String PositionStr;
    private String comentario = "";
    private boolean removerVenta = false;
    private String _tv_adeudoN;
    private String _tv_totAbono;
    private String _txtKit;
    private String _txtSaldoDeudaEnv;
    private String _txtVenta;
    private String _txtSaldo;

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
    ArrayList<DataTableLC.AdeudoNormal> dgANormal;
    ArrayList<DataTableLC.AdeudoNormal> dgAEspecial;

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


        //CONFIGURACION DE UBICACION
        //Construcción cliente API Google
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        //TERMINA CONFIGURACION DE LAS TABS
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

        pedidosVM.getTxtSaldoDeudaEnv().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                _txtSaldoDeudaEnv=s;
            }
        });

        pedidosVM.getTxtVenta().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                _txtVenta=s;
            }
        });

        pedidosVM.getTxtSaldo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                _txtSaldo=s;
            }
        });

        pedidosVM.getDgPagos().observe(this, new Observer<ArrayList<DataTableLC.DgPagos>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.DgPagos> dgPagosNew) {
                dgPagos = dgPagosNew;
            }
        });

        pedidosVM.getDgAbonos().observe(this, new Observer<ArrayList<DataTableLC.DgAbonos>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.DgAbonos> dgAbonosNew) {
                dgAbonos = dgAbonosNew;
            }
        });

        pedidosVM.getDgDeudaEnv().observe(this, new Observer<ArrayList<DataTableLC.EnvasesAdeudo>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.EnvasesAdeudo> envasesAdeudos) {
                dgDeudaEnv = envasesAdeudos;
            }
        });

    }

    private void inicializar()
    {
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

        dgANormal = ListarAdeudoNormal();
        dgAEspecial = ListarAdeudoEspecial();

        pedidosVM.setDgANormal(dgANormal);
        pedidosVM.setDgAEspecial(dgAEspecial);

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
            pedidosVM.setDgEnvase(dgEnvase);

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

            String json = BaseLocal.Select(string.formatSql2(con, String.valueOf( lpre ) , String.valueOf(catenv) , conf.getRutaStr()), getApplicationContext());

            ArrayList<DataTableLC.EnvasesAdeudo> dt = ConvertirRespuesta.getEnvasesAdeudoJson(json);

            dt = actualizarAdeudoEnvase(dt);

            con = "select cli_cve_n,prod_cve_n,sum(prod_cant_n) adeudo,sum(prod_cantabono_n) abono " +
                    "from creditos where cli_cve_n={0} and prod_cve_n={1} and cred_esenvase_n=1 " +
                    "group by cli_cve_n,prod_cve_n";

            DataTableLC.EnvasesAdeudo r;
            for (int i = 0; i < dt.size(); i++)
            {
                r = dt.get(i);
                json = BaseLocal.Select(string.formatSql2(con, noCli, r.getProd_cve_n()), getApplicationContext());

                ArrayList<DataTableLC.EnvasesAdeudo2> dtAdEnv = ConvertirRespuesta.getEnvasesAdeudo2Json(json);

                if (dtAdEnv != null)
                {
                    DataTableLC.EnvasesAdeudo2 rae;
                    for (int j = 0; j < dtAdEnv.size(); j++)
                    {
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

            dt = actualizarAdeudoEnvase(dt);

            dgDeudaEnv = dt;
            pedidosVM.setDgDeudaEnv(dgDeudaEnv);

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped11), e.getMessage());
        }
    }

    private ArrayList<DataTableLC.EnvasesAdeudo> actualizarAdeudoEnvase(ArrayList<DataTableLC.EnvasesAdeudo> al)
    {
        double adeudo,abono,venta,lprePrecio;
        for(int i=0; i<al.size(); i++)
        {
            adeudo = Double.parseDouble(al.get(i).getAdeudo());
            abono = Double.parseDouble(al.get(i).getAbono());
            venta = Double.parseDouble(al.get(i).getVenta());
            lprePrecio = Double.parseDouble(al.get(i).getLpre_precio_n());

            al.get(i).setSaldo( String.valueOf( adeudo-abono-venta  ) );
            al.get(i).setSubPagoEnv( String.valueOf( abono+venta ) );
            al.get(i).setSubTotal( String.valueOf( lprePrecio*venta ) );
        }
        return al;
    }

    private ArrayList<DataTableLC.AdeudoNormal> ListarAdeudoNormal()
    {
        try {
            double limCred = Double.parseDouble(rc.getCli_montocredito_n());
            pedidosVM.setTxtLimCred( String.valueOf( limCred ) ); //txtLimCred.Text = limCred.ToString("c");

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


            if (dtANormal != null)
            {
                int diascred = Integer.parseInt(rc.getCli_plazocredito_n());
                double res = 0;
                for (int i = 0; i < dtANormal.size(); i++) {
                    Date fecha1 = Utils.FechaDT(dtANormal.get(i).getCred_fecha_dt());
                    Date fecha2 = Utils.FechaDT(Utils.FechaModificarDias(Utils.FechaLocal(), -diascred));

                    if (fecha1.compareTo(fecha2) < 0) {
                        res += Double.parseDouble(dtANormal.get(i).getSaldo());
                    }
                }

                pedidosVM.setTxtVencido(String.valueOf(res));

                /*if (res <= 0)
                {
                    //txtVencido.BackColor = Color.LightSalmon;
                    //txtVencido.Text = string.Format("{0:C}", res);
                } else
                    {
                    //txtVencido.BackColor = Color.White;
                    //txtVencido.Text = (0).ToString("C");
                }*/
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
                    pedidosVM.setDgPagos(dgPagos);
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
            DescKit = Double.parseDouble( string.DelCaracteres(_txtKit) ) ; //decimal.Parse(txtKit.Text.Replace("$", ""));

            for (DataTableLC.ProductosPed p : dgProd2)
                calculo += Double.parseDouble(p.getSubtotal());

            pedidosVM.setTxtSubtotal2( String.valueOf(calculo) ); //txtSubtotal2.Text = string.Format("{0:C}", decimal.Parse(calculo.ToString()));

            calculo = 0;
            for (DataTableLC.ProductosPed p : dgProd2)
                if (p.getProd_vtaefectivo_n().equals("1") || (p.getProm_contado_n().equals("1") && p.getProd_promo_n().equals("1")))
                    calculo += Double.parseDouble(p.getSubtotal());


            double ventaEA = Double.parseDouble( string.FormatoPesos(_txtSaldoDeudaEnv) ) ; //decimal.Parse(txtSaldoDeudaEnv.Text.Replace("$", ""));
            double ContEsp = calculo+ventaEA; //decimal.Parse(calculo.ToString())+ventaEA;

            if (ContEsp >= DescKit)
                ContEsp -= DescKit;
            else
                ContEsp = 0;

            pedidosVM.setTxtContado( String.valueOf(ContEsp) ); //txtContado.Text = string.Format("{0:C}", ContEsp);

        } catch (Exception e) {
            Utils.msgError(this, getString(R.string.err_ped16), e.getMessage());
        }
    }

    public void CalcularEnvase()
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

    public void CalcularTotales()
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
            for(DataTableLC.AdeudoNormal an: dgANormal)
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
                pedidosVM.setEstadoCredito("Vencido");
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
                obtenerUbicacion(1);
                return true;
            case R.id.action_detalles:
                clickDetalles();
                return true;
            case R.id.action_buscar:
                 //clickBuscar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void obtenerUbicacion(final int clave)
    {
        try {
            final String[] ubi = new String[1];
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle(getString(R.string.msg_cargando));
            progress.setMessage(getString(R.string.msg_espera));
            progress.show();
            progress.setCancelable(false);

            enableLocationUpdates();

            Log.d("salida", "Ubicacion anterior: Pedidos: " + ubi[0]);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.cancel();
                    disableLocationUpdates();
                    Log.d("salida", "Ubicacion nueva: Pedidos: " + getLatLon());
                    if(clave==1)
                        clickBTguardar();

                }
            }, 3000);

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_ubicacion),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void clickBTguardar()
    {
        DataTableLC.DtCliVenta cli = getDetcli( rc.getCli_cve_n());
        if(!validaDistancia(cli,true))
        {
            Utils.msgInfo(this, getString(R.string.msg_ped4) );
            return;
        }

        ArrayList<DataTableLC.ProductosPed> dtAuxN = dgProd2;

        ArrayList<DataTableLC.ProductosPed> ntr = new ArrayList<>();

        for(DataTableLC.ProductosPed p : dtAuxN)
        {
            if( Double.parseDouble( p.getProd_cant_n() ) > 0 && Double.parseDouble( p.getLpre_nota_n() )>0   )
                ntr.add(p);
        }

        if(ntr.size()>0)
        {
            double NotaP = 0;
            int CantN = 0;

            for(DataTableLC.ProductosPed rt : ntr)
            {
                NotaP += Double.parseDouble( rt.getLpre_precio_n() ) - Double.parseDouble( rt.getLpre_nota_n() ) * Integer.parseInt(rt.getProd_cant_n()) ;

                if (Long.parseLong( rt.getProm_kit_n() ) >0)
                {
                    ArrayList<DataTableLC.PedPromocionesKit> rk = new ArrayList<>();
                    for(DataTableLC.PedPromocionesKit pk : dtKits  )
                        if(pk.getProd_cve_n().equals( rt.getProd_cve_n() ))
                            rk.add(pk);

                    if(rk.size()>0)
                    {
                        if( Double.parseDouble(rt.getLpre_precio_n()) > Double.parseDouble( rk.get(0).getLpre_precio_n() ) &&
                            Double.parseDouble( rt.getLpre_nota_n() ) < Double.parseDouble(rk.get(0).getLpre_precio_n()) )
                        {
                            NotaP -= Double.parseDouble( rt.getLpre_precio_n() ) - Double.parseDouble( rk.get(0).getLpre_precio_n() ) * Integer.parseInt(rk.get(0).getProm_veces_n()) ;
                        }
                        // Precio de Cliente mayor que precio de kit
                        // && Precio nota mayor que precio de kit elimina pesos

                        if( Double.parseDouble( rt.getLpre_precio_n()) > Double.parseDouble( rk.get(0).getLpre_precio_n() ) &&
                            Double.parseDouble( rt.getLpre_nota_n() ) > Double.parseDouble( rk.get(0).getLpre_precio_n())    )
                        {
                            NotaP -= Double.parseDouble(rt.getLpre_precio_n()) - Double.parseDouble( rt.getLpre_nota_n() ) * Integer.parseInt(rk.get(0).getProm_veces_n());
                        }
                    }
                }

                CantN += Integer.parseInt( rt.getProd_cant_n() );
            }

            int sobrante = Integer.parseInt( rc.getCvm_vtaacum_n() ) + CantN - Integer.parseInt( rc.getCli_limitemes_n() );

            if(sobrante > 0 && Integer.parseInt( rc.getCli_limitemes_n() ) > 0 )
            {
                String msg = getString(R.string.msg_ped5) +"\n"+
                             getString(R.string.msg_ped51) +" "+rc.getCli_limitemes_n() + "\n"+
                             getString(R.string.msg_ped52) +" "+sobrante;

                Utils.msgInfo(this, msg);
                return;
            }

            if (NotaP > 0)
            {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
                dialogo1.setTitle(getString(R.string.msg_importante));
                dialogo1.setMessage( string.formatSql( getString(R.string.msg_ped6), String.valueOf( NotaP )  ) );
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        guardar();
                    }
                });
                dialogo1.setNegativeButton(getString(R.string.msg_no),  null);
                dialogo1.show();
            }
            else
            {
                guardar();
            }
        }
        else
        {
            guardar();
        }

    }

    private void guardar()
    {
        if (!ValidarCondicionesVenta())
            return;

        double VentaE=0;
        double VentaED=0;

        for(DataTableLC.EnvasesAdeudo dg: dgDeudaEnv)
            VentaE += Double.parseDouble( dg.getVenta() );

        for(DataTableLC.EnvasesPed ep : dgEnvase)
            VentaED += Double.parseDouble( ep.getVenta() );

        if (!ValidarEnvase())
            return;

        if(  Double.parseDouble( string.DelCaracteres(_txtVenta ) ) <=0   )
        {
            final Context context= this;
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
            dialogo1.setTitle(getString(R.string.msg_importante));
            dialogo1.setMessage(getString(R.string.msg_ped12));
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {

                    AlertDialog.Builder dialogo2 = new AlertDialog.Builder(context);
                    dialogo2.setTitle(getString(R.string.msg_importante));
                    dialogo2.setMessage(getString(R.string.msg_ped3));
                    dialogo2.setCancelable(false);
                    dialogo2.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            GuardarVisitaSinVenta2();
                        }
                    });
                    dialogo2.setNegativeButton(getString(R.string.msg_no), null);
                    dialogo2.show();

                }
            });
            dialogo1.setNegativeButton(getString(R.string.msg_no), null);
            dialogo1.show();
        }
        else
        {
            if(Double.parseDouble( string.DelCaracteres( _txtVenta) ) >0 &&
               Double.parseDouble( string.DelCaracteres(_txtSaldo ) ) >0 )
            {
                Utils.msgInfo(this, getString(R.string.msg_ped13) );
                return;
            }

            if(Double.parseDouble( string.DelCaracteres(_txtVenta) ) >0 &&
               Double.parseDouble( string.DelCaracteres(_txtSaldo) ) <0 )
            {
                Utils.msgInfo(this, getString(R.string.msg_ped14) );
                return;
            }

            GuardarVenta2();
        }


    }

    private void GuardarVenta2()
    {
        boolean consigna = false;
        boolean credito = false;
        String folio=null;
        String coordenada = "NO VALIDA";
        //if (Utils.position.PositionValid)
            //coordenada = Utils.position.PositionStr;
        try
        {
            //---------  Obtener adeudos y cobranza de envase ---------------

            String con= (string.formatSql ("select cr.*,coalesce(pag.prod_abono_n,0) prod_abono_n," +
                    "cr.prod_cant_n-(cr.prod_cantabono_n+coalesce(pag.prod_abono_n,0)) prod_saldo_n from " +
                    "(select * from creditos where cli_cve_n={0} and cred_esenvase_n=1 " +
                    "and prod_cant_n>prod_cantabono_n) cr left join " +
                    "(select pag_referencia_str,sum(prod_abono_n) prod_abono_n from pagos " +
                    "where cli_cve_n={1} and pag_envase_n=1 and pag_cobranza_n=1 " +
                    "group by pag_referencia_str) pag on cr.cred_referencia_str =pag.pag_referencia_str " +
                    "order by cr.cred_fecha_dt", noCli,noCli));
            String res= BaseLocal.Select(con, this);
            ArrayList<DataTableLC.EnvAjustados> dtCredEnvAjustados = ConvertirRespuesta.getEnvAjustadosJson(res);
            //---------  Obtener adeudos y cobranza de envase ---------------//



            // -----------  Organizar movimientos de envase ------------------//
            ArrayList<DataTableLC.MovimientosEnv> vme = new ArrayList<>();
            for (DataTableLC.EnvasesPed ed : dgEnvase) {
                for (DataTableLC.EnvasesAdeudo ea : dgDeudaEnv) {
                    if (ed.getProd_sku_str().equals(ea.getProd_sku_str())) {
                        DataTableLC.MovimientosEnv o = new DataTableLC.MovimientosEnv();

                        o.setProd_cve_n(ed.getProd_cve_n());
                        o.setProd_sku_str(ed.getProd_sku_str());
                        o.setProd_recibidodia_n(ed.getRecibido());
                        o.setProd_recibido_n(String.valueOf(Integer.parseInt(ed.getRecibido()) + Integer.parseInt(ea.getAbono())));
                        o.setProd_abono_n(ea.getAbono());
                        o.setProd_venta_n(String.valueOf(Integer.parseInt(ed.getVenta()) + Integer.parseInt(ea.getVenta())));
                        o.setProd_ventaant_n(ea.getVenta());
                        o.setProd_ventadia_n(ed.getVenta());
                        o.setProd_regalo_n(ed.getRegalo());
                        o.setProd_prestado_n(ed.getRestante());
                        o.setProd_cantiv_n(ed.getProd_cantiv_n());
                        o.setProd_pago_n(String.valueOf(Integer.parseInt(ea.getAbono()) + Integer.parseInt(ea.getVenta())));
                        o.setLpre_base_n(ed.getLpre_base_n());
                        o.setLpre_precio_n(ed.getLpre_precio_n());

                        vme.add(o);
                    }
                }
            }
            // -----------  Organizar movimientos de envase ------------------//

            String ven = BaseLocal.SelectDato("select max(ven_folio_str) from ventas", this);
            if(ven!=null)
            {
                String fecha=Utils.FechaLocal().replace("-","");

                String ruta = conf.getRutaStr();
                String rutaN = "";
                int ceros = 3-ruta.length();
                for(int i=0; i<ceros;i++) rutaN+="0";
                ruta = rutaN+ruta;

                String fol = ven.substring(11);
                int num = Integer.parseInt(fol)+1;
                String numFol ="";
                String folNum = String.valueOf(num);
                int cerosN = 3-folNum.length();
                for(int i=0; i<cerosN;i++) numFol+="0";
                folNum = numFol + folNum;

                folio = fecha + ruta + folNum;
            }
            else
            {
                String fecha=Utils.FechaLocal().replace("-","");

                String ruta = conf.getRutaStr();
                String rutaN = "";
                int ceros = 3-ruta.length();
                for(int i=0; i<ceros;i++) rutaN+="0";
                ruta = rutaN+ruta;

                folio = fecha + ruta +"001";

            }

            int k = 1;

            DatabaseHelper databaseHelper = new DatabaseHelper(this, getString(R.string.nombreBD), null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try
            {
                db.beginTransaction();

                //-------   Se registra la diferencia con preventa ------

                if(folio!=null)
                {
                    for(DataTableLC.CompPrevDet ia : ListaPrev)
                    {
                        DataTableLC.ProductosPed pi =null;
                        for(DataTableLC.ProductosPed pp : dgProd2)
                        {
                            if(pp.getProd_cve_n().equals( ia.getProd_cve_n() ))
                            {
                                pi = pp;
                                break;
                            }
                        }

                        if (pi != null)
                            ia.setVta_cant_n( pi.getProd_cant_n() );
                        else
                            ia.setVta_cant_n("0");

                        if (!Utils.getBool( ia.getSin_inv() ))
                        {
                            String sql = string.formatSql2(Querys.Inventario.ActualizaCancelado, conf.getRutaStr(), ia.getProd_cve_n(),
                                            String.valueOf(  Integer.parseInt(ia.getDisp_cant_n()) - Integer.parseInt(ia.getVta_cant_n())   ) );
                            db.execSQL(sql);
                        }
                    }

                    ArrayList< DataTableLC.EnvFal > envfal = new ArrayList<>();
                    ArrayList< String > ids = new ArrayList<>();

                    for (DataTableLC.CompPrevDet ei: ListaPrev)
                    {
                        if( Integer.parseInt( ei.getId_envase_n() ) !=0 &&
                            !Utils.getBool(ei.getSin_inv()) )
                        {
                            if(ids.contains( ei.getId_envase_n() ))
                            {
                                int indice = ids.indexOf( ei.getId_envase_n() );
                                int canN =Integer.parseInt( ei.getDisp_cant_n() ) - Integer.parseInt(ei.getVta_cant_n());
                                int canA = Integer.parseInt( envfal.get(indice).getCant() );
                                envfal.get(indice).setCant( String.valueOf( canN+canA ) );

                            }
                            else
                            {
                                DataTableLC.EnvFal ef = new DataTableLC.EnvFal();
                                ef.setId_env( ei.getId_envase_n() );
                                ef.setCant(  String.valueOf(  Integer.parseInt( ei.getDisp_cant_n() ) - Integer.parseInt(ei.getVta_cant_n())  )     );
                                envfal.add(ef);
                                ids.add(ei.getId_envase_n());
                            }
                        }
                    }


                    for(DataTableLC.EnvFal it : envfal)
                    {
                        String sql= string.formatSql2(Querys.Inventario.ActualizaCancelado, conf.getRutaStr(), it.getId_env(), it.getCant() );
                        db.execSQL(sql);
                    }
                }

                //--------   Registro de diferencia con preventa -------- //


                //---------------   Registro de la venta de productos -----------------------


                for(DataTableLC.ProductosPed r : dgProd2)
                {
                    if ( Integer.parseInt( r.getProd_cant_n() ) > 0)
                    {
                        double CantKit = 0;
                        double PreKit = 0;
                        boolean kit = false;

                        if ( Double.parseDouble( r.getProm_kit_n() )  > 0)
                        {
                            DataTableLC.PedPromocionesKit rki = null;
                            for(DataTableLC.PedPromocionesKit dt : dtKits)
                            {
                                if( dt.getProm_cve_n().equals( r.getProm_kit_n() )  &&
                                    dt.getProd_cve_n().equals( r.getProd_cve_n() ) )
                                {
                                    rki = dt;
                                }
                            }

                            if (rki!=null)
                            {
                                //Verifica si el precio de promocion es menor que el del cliente
                                if( Double.parseDouble( rki.getLpre_precio_n() ) < Double.parseDouble( r.getLpre_precio_n() ) )
                                {
                                    kit = true;
                                    CantKit = Double.parseDouble( rki.getProd_cant_n() ) * Double.parseDouble( rki.getProm_veces_n() );
                                    PreKit = Double.parseDouble ( rki.getLpre_precio_n() );
                                }
                            }
                        }

                        if( Double.parseDouble( r.getProd_cant_n() ) > CantKit )
                        {
                            String sql = string.formatSql2(  Querys.Ventas.InsVentaDet, folio, String.valueOf(k), r.getProd_cve_n(),
                                    r.getProd_sku_str(), "0",
                                    String.valueOf( Double.parseDouble( r.getProd_cant_n() )-CantKit ) ,
                                    "0", r.getLpre_base_n(), r.getLpre_cliente_n(),  r.getLpre_promo_n(),  r.getLpre_precio_n(),
                                    String.valueOf (( Double.parseDouble(r.getProd_cant_n())-CantKit ) * Double.parseDouble( r.getLpre_precio_n() )),
                                    Utils.getBool(r.getProd_promo_n())?"1":"0", r.getProm_cve_n(),"0","0","0");

                            db.execSQL(sql);
                        }

                        if (kit)
                        {
                            if ( Double.parseDouble( r.getProd_cant_n() )  > CantKit)
                                k++;

                            String sql = string.formatSql2(Querys.Ventas.InsVentaDet, folio, String.valueOf(k),  r.getProd_cve_n(),
                                    r.getProd_sku_str(), "0", String.valueOf( CantKit ) , "0", r.getLpre_base_n() , r.getLpre_cliente_n() ,
                                     r.getLpre_promo_n(), String.valueOf( PreKit ) , String.valueOf( CantKit*PreKit ) , Utils.getBool(r.getProd_promo_n())?"1":"0",
                                     r.getProm_kit_n(), "0", "0","1");

                            db.execSQL(sql);
                        }

                        if( Utils.getBool(r.getProd_manejainv_n())  && esventa)
                        {
                            String sql =string.formatSql2(Querys.Inventario.ActualizaInvVen, conf.getRutaStr(),
                                    r.getProd_cve_n() , "+", r.getProd_cant_n());
                            db.execSQL(sql);

                            sql= string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(),
                                    r.getProd_cve_n(), noCli, conf.getUsuario(), r.getProd_cantiv_n(), r.getProd_cant_n() , "0",
                                    String.valueOf(  Double.parseDouble( r.getProd_cantiv_n() ) - Double.parseDouble( r.getProd_cant_n() )  ) ,
                                    "Vendido ticket: " + folio);

                            db.execSQL(sql);
                        }
                        k++;
                    }
                }


                //------------- Registro del movimiento de envase de la venta -----------
                for(DataTableLC.EnvasesPed r : dgEnvase)
                {
                    if( Double.parseDouble( r.getEntregado() ) >0  )
                    {
                        String sql = string.formatSql2(Querys.Ventas.InsVentaEnv, noCli, rc.getCli_cveext_str(),
                                folio,  r.getProd_cve_n(), r.getProd_sku_str(),  r.getEntregado() , r.getRecibido(), r.getRegalo(),
                                r.getVenta(), "0",  r.getRestante(), r.getLpre_base_n() , r.getLpre_precio_n() );

                        db.execSQL(sql);
                    }
                }
                //------------- Registro del movimiento de envase de la venta -----------//

                //--------------- Trabajo con envase  --------------------------- //
                for(DataTableLC.MovimientosEnv i : vme)
                {
                    //-----  venta de envase -------
                    if ( Double.parseDouble( i.getProd_venta_n() )  > 0)
                    {
                        if (esventa)
                        {
                            if (Double.parseDouble(i.getProd_ventadia_n()) > 0)
                            {
                                String sql = string.formatSql2(Querys.Ventas.InsVentaDet, folio, String.valueOf(k) , i.getProd_cve_n(),
                                        i.getProd_sku_str(), "0", i.getProd_ventadia_n(), "0", i.getLpre_base_n(), i.getLpre_precio_n(),
                                        "0", i.getLpre_precio_n(),
                                        String.valueOf( Double.parseDouble(i.getProd_ventadia_n())  *  Double.parseDouble(i.getLpre_precio_n() ) )
                                        , "0", "0", "0", "0","0");
                                db.execSQL(sql);

                                k++;

                                sql = string.formatSql2(Querys.Inventario.ActualizaInvVen, conf.getRutaStr(),
                                        i.getProd_cve_n(), "+", i.getProd_ventadia_n());
                                db.execSQL(sql);

                                sql= string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(),
                                        i.getProd_cve_n(), noCli, conf.getUsuario(), i.getProd_cantiv_n(), i.getProd_ventadia_n(), "0",
                                        String.valueOf( Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble( i.getProd_ventadia_n() ) ),
                                        "Envase vendido ticket: " + folio);
                                db.execSQL(sql);

                            }
                        }

                        if (Double.parseDouble( i.getProd_ventaant_n() ) >0  )
                        {
                            String sql= string.formatSql2(Querys.Ventas.InsVentaDetEnv, folio, String.valueOf(k), i.getProd_cve_n(),
                                    i.getProd_sku_str(), "0", i.getProd_ventaant_n(), "0", i.getLpre_base_n(), i.getLpre_precio_n(),
                                    "0", i.getLpre_precio_n(),
                                    String.valueOf( Double.parseDouble( i.getProd_ventaant_n() )  * Double.parseDouble( i.getLpre_precio_n() ) )  ,
                                    "0", "0", "0", "1", "0");

                            db.execSQL(sql);
                        }
                        k++;
                    }
                    //-----  venta de envase -------//

                    //-----  Regalo de envase -------
                    if (Double.parseDouble(i.getProd_regalo_n()) > 0)
                    {
                        //----- Se vende el envase regalado ---------------
                        String sql= string.formatSql2(Querys.Ventas.InsVentaDet, folio, String.valueOf(k), i.getProd_cve_n(),
                                i.getProd_sku_str(), "0", i.getProd_regalo_n(), "0", i.getLpre_base_n(), i.getLpre_precio_n() ,
                                "0", "0", "0", "1", "0", "1", "0","0");
                        db.execSQL(sql);

                        //----- Se actualiza el inventario
                        sql= string.formatSql2(Querys.Inventario.ActualizaInvVen, conf.getRutaStr(),
                                i.getProd_cve_n(), "+", i.getProd_regalo_n());
                        db.execSQL(sql);

                        sql=string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(),
                                i.getProd_cve_n(), noCli, conf.getUsuario(),
                                String.valueOf(  Double.parseDouble( i.getProd_cantiv_n() ) - Double.parseDouble( i.getProd_ventadia_n() ) ) ,
                                i.getProd_regalo_n(), "0",
                                String.valueOf(  Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble(i.getProd_ventadia_n()) - Double.parseDouble(i.getProd_regalo_n())  ) ,
                                "Regalado ticket: " + folio);
                        db.execSQL(sql);

                        k++;
                    }
                    //-----  Regalo de envase -------//


                    //------ Pago de envase anterior ------
                    if (Double.parseDouble(i.getProd_pago_n()) > 0)
                    {
                        int acum = 0;
                        int AcumAbo = 0;
                        int AcumVta = 0;
                        int Saldo = 0;
                        int SaldoAbo = 0;
                        int SaldoVta = 0;

                        for (DataTableLC.EnvAjustados rep : dtCredEnvAjustados) {
                            if (Double.parseDouble(rep.getProd_saldo_n()) > 0 && rep.getProd_cve_n().equals(i.getProd_cve_n())) {
                                if (acum >= Double.parseDouble(i.getProd_pago_n()))
                                    break;

                                Saldo = Integer.parseInt(rep.getProd_saldo_n());

                                if ((Double.parseDouble(i.getProd_pago_n()) - acum) < Saldo)
                                    Saldo = (Integer.parseInt(i.getProd_pago_n()) - acum);

                                if ((Double.parseDouble(i.getProd_abono_n()) - AcumAbo) >= Saldo) {
                                    SaldoAbo = Saldo;
                                    SaldoVta = 0;
                                } else {
                                    SaldoAbo = (Integer.parseInt(i.getProd_abono_n()) - AcumAbo);
                                    SaldoVta = Saldo - SaldoAbo;
                                }

                                if (SaldoAbo > 0) {

                                    String sql = string.formatSql2(Querys.Pagos.InsPagoEnv, String.valueOf(conf.getId()), rep.getCred_referencia_str(),
                                            "0", "0", "1", i.getProd_cve_n(), String.valueOf(SaldoAbo), "null", "",
                                            "", noCli, conf.getRutaStr(), conf.getUsuario(), "0", i.getProd_sku_str(), "1", coordenada, "1");
                                    db.execSQL(sql);

                                    AcumAbo += SaldoAbo;
                                }

                                if (SaldoVta > 0) {
                                    String sql = string.formatSql2(Querys.Pagos.InsPagoEnv, String.valueOf(conf.getId()), rep.getCred_referencia_str(),
                                            "0", "0", "1", i.getProd_cve_n(), String.valueOf(SaldoVta), "null", "",
                                            "", noCli, conf.getRutaStr(), conf.getUsuario(), "0", i.getProd_sku_str(), "1", coordenada, "0");

                                    db.execSQL(sql);

                                    AcumVta += SaldoVta;
                                }

                                acum += Saldo;
                            }
                        }

                        if (Double.parseDouble(i.getProd_abono_n()) > 0)
                        {
                            String sql = string.formatSql2(Querys.Inventario.ActualizaRecuperado, conf.getRutaStr(), i.getProd_cve_n(), i.getProd_abono_n());
                            db.execSQL(sql);

                            sql= string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(), i.getProd_cve_n(),
                                    noCli, conf.getUsuario(),
                                    String.valueOf(  Double.parseDouble(i.getProd_cantiv_n()) -  Double.parseDouble(i.getProd_ventadia_n()) -  Double.parseDouble(i.getProd_regalo_n())  ) ,
                                    "0", i.getProd_abono_n(),
                                    String.valueOf(  ( Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble(i.getProd_ventadia_n()) - Double.parseDouble(i.getProd_regalo_n()) ) + Double.parseDouble( i.getProd_abono_n())   ),
                                    string.formatSql("Devolución de {0} envases {1} del Cliente: {2}",  i.getProd_abono_n(), i.getProd_sku_str(), Cliente));
                            db.execSQL(sql);

                        }

                        if (Double.parseDouble(i.getProd_ventaant_n()) >0 )
                        {
                            String sql = string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(), i.getProd_cve_n(),
                            noCli, conf.getUsuario(),
                            String.valueOf( Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble(i.getProd_ventadia_n()) - Double.parseDouble(i.getProd_regalo_n()) ) ,
                            i.getProd_ventaant_n(), "0",
                                    String.valueOf( Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble(i.getProd_ventadia_n()) - Double.parseDouble(i.getProd_regalo_n()) ) ,
                            string.formatSql("Venta de {0} envases {1} del Cliente: {2}", i.getProd_ventaant_n(), i.getProd_sku_str(), Cliente));

                            db.execSQL(sql);
                        }
                    }
                    //------ Pago de envase anterior ------//

                    //------ Pago del envase del dia -------
                    if (Double.parseDouble( i.getProd_recibidodia_n() ) > 0)
                    {
                        //------ dentro de las pruebas el envase devuelto no juega en el inventario ya que duplica el vacio
                        //cx.ActualizarDatos(string.Format(Querys.Inventario.ActualizaRecuperado, conf.ruta, i.prod_cve_n, i.prod_recibidodia_n), t);
                        String sql = string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(), i.getProd_cve_n(),
                                noCli, conf.getUsuario(),
                                String.valueOf( (Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble(i.getProd_ventadia_n()) - Double.parseDouble(i.getProd_regalo_n()) ) + Double.parseDouble(i.getProd_abono_n())  ),
                                "0", i.getProd_recibidodia_n(),
                                String.valueOf( (Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble(i.getProd_ventadia_n()) - Double.parseDouble(i.getProd_regalo_n()) ) + Double.parseDouble(i.getProd_abono_n())  ),
                                string.formatSql("Devolución de {0} envases {1} mismo día del Cliente: {2}", i.getProd_recibidodia_n(), i.getProd_sku_str(), Cliente));
                        db.execSQL(sql);
                    }
                    //------ Pago del envase del dia -------//

                    if (Double.parseDouble(i.getProd_prestado_n()) > 0)
                    {
                        //------------- Se vende a precio 0 el envase prestado
                        String sql = string.formatSql2(Querys.Ventas.InsVentaDet, folio, String.valueOf(k) , i.getProd_cve_n(),
                                i.getProd_sku_str(), "1",  i.getProd_prestado_n(), "0", i.getLpre_base_n(), i.getLpre_precio_n(),
                                "0", "0", "0", "0", "0", "0", "0","0");
                        db.execSQL(sql);

                        k++;

                        //-------------- Generacion del credito para envase prestado
                        if (consigna)
                        {
                            sql = string.formatSql2(Querys.Trabajo.InsConsignaPago,
                           string.formatSql("{0}-{1}", folio, i.getProd_sku_str()), noCli, conf.getUsuario(), "ENVASE EN CONSIGNA",
                                    String.valueOf( Double.parseDouble(i.getProd_prestado_n()) * Double.parseDouble(i.getLpre_precio_n()) ) ,
                                    "1", i.getProd_cve_n(), i.getLpre_precio_n() , i.getProd_prestado_n(), "30",  i.getProd_sku_str());
                            db.execSQL(sql);
                        }
                        else
                        {
                            sql= string.formatSql2(Querys.Trabajo.InsCreditosPago3,
                                        string.formatSql ("{0}-{1}", folio, i.getProd_sku_str()), noCli, conf.getUsuario(), "ENVASE A CREDITO",
                                        String.valueOf( Double.parseDouble(i.getLpre_precio_n())* Double.parseDouble( i.getProd_prestado_n() ) ) ,
                                        "1", i.getProd_cve_n(), i.getLpre_precio_n(), i.getProd_prestado_n(), i.getProd_sku_str(), rc.getCli_plazocredito_n());
                            db.execSQL(sql);
                        }

                        //Baja de inventario prestados
                        sql= string.formatSql2(Querys.Inventario.ActualizaPrestado, conf.getRutaStr(),
                                i.getProd_cve_n(), i.getProd_prestado_n());
                        db.execSQL(sql);

                        sql = string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(), i.getProd_cve_n(),
                                noCli, conf.getUsuario(),
                                String.valueOf (( Double.parseDouble( i.getProd_cantiv_n() ) - Double.parseDouble( i.getProd_ventadia_n() ) - Double.parseDouble( i.getProd_regalo_n() ) ) + Double.parseDouble(i.getProd_abono_n()) ),
                                i.getProd_prestado_n(), "0",
                                String.valueOf( ( Double.parseDouble( i.getProd_cantiv_n() ) - Double.parseDouble( i.getProd_ventadia_n() ) - Double.parseDouble( i.getProd_regalo_n() ) ) + Double.parseDouble(i.getProd_abono_n())- Double.parseDouble(i.getProd_prestado_n())  ),
                                string.formatSql("Prestamo de {0} envases {1} mismo día del Cliente: {2}", i.getProd_prestado_n(), i.getProd_sku_str(), Cliente));
                        db.execSQL(sql);

                    }
                }
                //--------------- Trabajo con envase  ---------------------//


                String sql = string.formatSql2(Querys.Ventas.InsVentas2, folio, noCli,
                        conf.getRutaStr(), String.valueOf(lpre) , "1", conf.getUsuario().toUpperCase(), coordenada,
                        FolioPreventa,  rc.getCli_dispersion_n(),comentario);
                db.execSQL(sql);

                sql = string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), noCli, "CON VENTA", "VISITA CON VENTA", coordenada);
                db.execSQL(sql);

                sql= string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), noCli, "CIERRE CLIENTE", "CIERRE CON VENTA", coordenada);
                db.execSQL(sql);

                sql=string.formatSql2(Querys.Trabajo.InsertVisita, conf.getUsuario(),
                        noCli, "null", "null", "CON VENTA", "VISITA CON VENTA", coordenada);
                db.execSQL(sql);

                sql= string.formatSql2(Querys.Preventa.UpdateVisitaReparto, FolioVisita);
                db.execSQL(sql);


                // ------ Pago venta actual -----------------
                for(DataTableLC.DgPagos r : dgPagos)
                {
                    if ( r.getFpag_desc_str().equals("CREDITO") )
                    {
                        sql = string.formatSql2(Querys.Trabajo.InsCreditosPago2,
                                folio, noCli, conf.getUsuario(), "VENTA A CREDITO", rc.getCli_plazocredito_n() ,  r.getFpag_cant_n(), "0",
                                "null", "", "null", "null");
                        db.execSQL(sql);
                        credito = true;
                    }
                    else
                    {
                        if (r.getFpag_desc_str().equals("CONSIGNA") )
                        {
                            sql= string.formatSql2(Querys.Trabajo.InsConsignaPago,
                                    folio, noCli, conf.getUsuario(), "CONSIGNA", r.getFpag_cant_n(), "0", "null", "null", "null", "30", "");
                            db.execSQL(sql);

                            consigna = true;
                        }
                        else
                        {
                            sql=string.formatSql2(Querys.Pagos.InsPago, String.valueOf( conf.getId() ) , folio,
                                    r.getFpag_cant_n(), "0", "0", "null", "null", r.getFpag_cve_n(), r.getReferenciaP(),
                                    r.getBancoP(), noCli, conf.getRutaStr(), conf.getUsuario(), "0", "", "0", coordenada);
                            db.execSQL(sql);
                        }
                    }
                }
                // --------  Pago venta actual --------------//

                // ------------- Cobranza -------------------------
                if (  dgAbonos.size() >0   )
                {
                    double abonado = 0;
                    double acumulado = 0;
                    double saldo = 0;

                    for(DataTableLC.DgAbonos rp : dgAbonos)
                    {
                        abonado = Double.parseDouble(rp.getFpag_cant_n());
                        acumulado = 0;

                        for(DataTableLC.AdeudoNormal r : dgANormal)
                        {
                            if( Double.parseDouble( r.getSaldo() ) >0  )
                            {
                                if (abonado - acumulado > 0)
                                {
                                    saldo = Double.parseDouble(r.getSaldo());
                                    if (saldo <= (abonado - acumulado))
                                    {
                                        sql= string.formatSql2(Querys.Pagos.InsPago, String.valueOf(conf.getId()), r.getCred_referencia_str(),
                                                String.valueOf(saldo), "0", "0", "null", "null", rp.getFpag_cve_n() , rp.getReferenciaA(),
                                                rp.getBancoA(), noCli, conf.getRutaStr(), conf.getUsuario(), "0", "", "1", coordenada);
                                        db.execSQL(sql);

                                        acumulado += saldo;
                                    }
                                    else
                                    {
                                        saldo = (abonado - acumulado);
                                        sql=string.formatSql2(Querys.Pagos.InsPago, String.valueOf(conf.getId()), r.getCred_referencia_str(),
                                                String.valueOf(saldo), "0", "0", "null", "null", rp.getFpag_cve_n(), rp.getReferenciaA(),
                                                rp.getBancoA(), noCli, conf.getRutaStr(), conf.getUsuario(), "0", "", "1", coordenada);
                                        db.execSQL(sql);
                                        acumulado += saldo;
                                    }
                                    r.setAbono( String.valueOf( Double.parseDouble(r.getAbono()) + saldo  ) );
                                }
                                else
                                    break;
                            }
                        }
                    }
                }
                // ------------- Cobranza -------------------------

                sql=string.formatSql2("update ventas set ven_credito_n={1},ven_consigna_n={2} " +
                        "where ven_folio_str='{0}'", folio, credito ? "1" : "0", consigna ? "1" : "0");
                db.execSQL(sql);

                db.setTransactionSuccessful();

                if(db.isOpen())
                {
                    db.endTransaction();
                    db.close();
                }

                Utils.ImprimirVentaBebidas(Utils.ObtenerVisitaBebidas( Long.parseLong(noCli) ,this), false, this, conf);

                String sqlA = string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), noCli, "TICKET IMPRESO", "TICKET CLIENTE ORIGINAL", coordenada);

                BaseLocal.Insert(sqlA, getApplicationContext() );

                Utils.ImprimirVentaBebidas(Utils.ObtenerVisitaBebidas( Long.parseLong(noCli ),this ), false,this,conf);
                sqlA =  string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), noCli, "TICKET IMPRESO", "TICKET ASESOR ORIGINAL", coordenada);
                BaseLocal.Insert(sqlA, getApplicationContext() );

                Intent i = getIntent();
                i.putExtra("estado", "true");
                i.putExtra("cve", rc.getCli_cve_n());
                setResult(RESULT_OK, i);
                finish();

            }catch (Exception e)
            {
                Utils.msgError(this, getString(R.string.error_almacenar), e.getMessage());
            }
            finally
            {
                if(db.isOpen())
                {
                    db.endTransaction();
                    db.close();
                }
            }

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_ped1), e.getMessage());
        }

    }

    private void GuardarVisitaSinVenta2()
    {
        try
        {
            boolean cobranza = false;
            String coordenada = "NO VALIDA";
            //if (Utils.position.PositionValid)
            //  coordenada = Utils.position.PositionStr;

            // -----------  Organizar movimientos de envase ------------------//
            ArrayList<DataTableLC.MovimientosEnv> vme = new ArrayList<>();
            for (DataTableLC.EnvasesPed ed : dgEnvase) {
                for (DataTableLC.EnvasesAdeudo ea : dgDeudaEnv) {
                    if (ed.getProd_sku_str().equals(ea.getProd_sku_str())) {
                        DataTableLC.MovimientosEnv o = new DataTableLC.MovimientosEnv();

                        o.setProd_cve_n(ed.getProd_cve_n());
                        o.setProd_sku_str(ed.getProd_sku_str());
                        o.setProd_recibidodia_n(ed.getRecibido());
                        o.setProd_recibido_n(String.valueOf(Integer.parseInt(ed.getRecibido()) + Integer.parseInt(ea.getAbono())));
                        o.setProd_abono_n(ea.getAbono());
                        o.setProd_venta_n(String.valueOf(Integer.parseInt(ed.getVenta()) + Integer.parseInt(ea.getVenta())));
                        o.setProd_ventaant_n(ea.getVenta());
                        o.setProd_ventadia_n(ed.getVenta());
                        o.setProd_regalo_n(ed.getRegalo());
                        o.setProd_prestado_n(ed.getRestante());
                        o.setProd_cantiv_n(ed.getProd_cantiv_n());
                        o.setProd_pago_n(String.valueOf(Integer.parseInt(ea.getAbono()) + Integer.parseInt(ea.getVenta())));
                        o.setLpre_base_n(ed.getLpre_base_n());
                        o.setLpre_precio_n(ed.getLpre_precio_n());

                        vme.add(o);
                    }
                }
            }
            // -----------  Organizar movimientos de envase ------------------//


            //---------  Obtener adeudos y cobranza de envase ---------------

            String con= (string.formatSql ("select cr.*,coalesce(pag.prod_abono_n,0) prod_abono_n," +
                    "cr.prod_cant_n-(cr.prod_cantabono_n+coalesce(pag.prod_abono_n,0)) prod_saldo_n from " +
                    "(select * from creditos where cli_cve_n={0} and cred_esenvase_n=1 " +
                    "and prod_cant_n>prod_cantabono_n) cr left join " +
                    "(select pag_referencia_str,sum(prod_abono_n) prod_abono_n from pagos " +
                    "where cli_cve_n={1} and pag_envase_n=1 and pag_cobranza_n=1 " +
                    "group by pag_referencia_str) pag on cr.cred_referencia_str =pag.pag_referencia_str " +
                    "order by cr.cred_fecha_dt", noCli,noCli));
            String res= BaseLocal.Select(con, this);
            ArrayList<DataTableLC.EnvAjustados> dtCredEnvAjustados = ConvertirRespuesta.getEnvAjustadosJson(res);

            //---------  Obtener adeudos y cobranza de envase ---------------//

            DatabaseHelper databaseHelper = new DatabaseHelper(this, getString(R.string.nombreBD), null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try
            {
                db.beginTransaction();

                // ------------- Cobranza -------------------------

                if ( dgAbonos.size() > 0)
                {
                    cobranza = true;
                    double abonado = 0;
                    double acumulado = 0;
                    double saldo = 0;

                    for(DataTableLC.DgAbonos rp: dgAbonos )
                    {
                        abonado = Double.parseDouble (rp.getFpag_cant_n());
                        acumulado = 0;
                        for(DataTableLC.AdeudoNormal r: dgANormal)
                        {
                            if( Double.parseDouble( r.getSaldo() ) >0  )
                            {
                                if (abonado - acumulado > 0)
                                {
                                    saldo = Double.parseDouble(r.getSaldo());
                                    if (saldo <= (abonado - acumulado))
                                    {

                                        con = string.formatSql2(
                                                Querys.Pagos.InsPago, String.valueOf(conf.getId() ),r.getCred_referencia_str(),
                                                String.valueOf(saldo), "0", "0", "null", "null", rp.getFpag_cve_n(), rp.getReferenciaA(),
                                                rp.getBancoA(), noCli, conf.getRutaStr(), conf.getUsuario(), "0", "", "1", coordenada);
                                        db.execSQL(con);

                                        acumulado += saldo;
                                    }
                                    else
                                    {
                                        saldo = (abonado - acumulado);

                                        con=string.formatSql2(Querys.Pagos.InsPago, String.valueOf(conf.getId() ), r.getCred_referencia_str(),
                                                String.valueOf(saldo ), "0","0", "null", "null", rp.getFpag_cve_n() , rp.getReferenciaA(),
                                                rp.getBancoA(), noCli, conf.getRutaStr(), conf.getUsuario(), "0", "", "1",coordenada);
                                        db.execSQL(con);

                                        acumulado += saldo;
                                    }
                                    r.setAbono( String.valueOf( Double.parseDouble(r.getAbono()) + saldo ) );
                                }
                                else
                                {
                                    break;
                                }
                            }
                        }
                    }
                }
                //----------- Cobranza -----------------//

                //--------------- Trabajo con envase  ---------------------------//
                for(DataTableLC.MovimientosEnv i : vme)
                {
                    //------ Pago de envase anterior ------
                    if ( Double.parseDouble( i.getProd_pago_n() )  > 0)
                    {
                        cobranza = true;
                        int acum = 0;
                        int AcumAbo = 0;
                        int AcumVta = 0;
                        int Saldo = 0;
                        int SaldoAbo = 0;
                        int SaldoVta = 0;

                        for(DataTableLC.EnvAjustados rep : dtCredEnvAjustados)
                        {
                            if( Double.parseDouble( rep.getProd_saldo_n()) >0 && rep.getProd_cve_n().equals( i.getProd_cve_n() )  )
                            {

                                if (acum >= Double.parseDouble( i.getProd_pago_n() ) )
                                    break;

                                Saldo = Integer.parseInt( rep.getProd_saldo_n() );
                                if (( Double.parseDouble(i.getProd_pago_n())  - acum) < Saldo)
                                    Saldo = ( Integer.parseInt(i.getProd_pago_n()) - acum);


                                if (( Double.parseDouble(i.getProd_abono_n())  - AcumAbo) >= Saldo)
                                {
                                    SaldoAbo = Saldo;
                                    SaldoVta = 0;
                                }
                                else
                                {
                                    SaldoAbo = ( Integer.parseInt(i.getProd_abono_n())  - AcumAbo);
                                    SaldoVta = Saldo - SaldoAbo;
                                }

                                if (SaldoAbo > 0)
                                {
                                    con = string.formatSql2(Querys.Pagos.InsPagoEnv, String.valueOf(conf.getId()), rep.getCred_referencia_str(),
                                            "0", "0", "1", i.getProd_cve_n() , String.valueOf( SaldoAbo ) , "null", "",
                                            "", String.valueOf( noCli) , conf.getRutaStr(), conf.getUsuario(), "0", i.getProd_sku_str(), "1", coordenada, "1");
                                    db.execSQL(con);
                                    AcumAbo += SaldoAbo;
                                }

                                if (SaldoVta > 0)
                                {
                                    con= string.formatSql2(Querys.Pagos.InsPagoEnv, String.valueOf(conf.getId()), rep.getCred_referencia_str(),
                                            "0", "0", "1", i.getProd_cve_n() , String.valueOf( SaldoVta ) , "null", "",
                                            "", String.valueOf(noCli ), conf.getRutaStr(), conf.getUsuario(), "0", i.getProd_sku_str(), "1", coordenada, "0");
                                    db.execSQL(con);
                                    AcumVta += SaldoVta;
                                }
                                acum += Saldo;
                            }
                        }

                        //----for pagos envase envase anterior ---///
                        if ( Integer.parseInt( i.getProd_abono_n()) >0 )
                        {
                            con= string.formatSql2(Querys.Inventario.ActualizaRecuperado, conf.getRutaStr(), i.getProd_cve_n(), i.getProd_abono_n());

                            db.execSQL(con);

                            con= string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(), i.getProd_cve_n(),
                                    String.valueOf(noCli ), conf.getUsuario(),
                                    String.valueOf( Double.parseDouble( i.getProd_cantiv_n() )  - Double.parseDouble(i.getProd_ventadia_n()) - Double.parseDouble(i.getProd_regalo_n()) ) ,
                                    "0", i.getProd_abono_n(),
                                    String.valueOf ( ( Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble(i.getProd_ventadia_n())  - Double.parseDouble(i.getProd_regalo_n()) ) + Double.parseDouble(i.getProd_abono_n()) ),
                                    string.formatSql("Devolución de {0} envases {1} del Cliente: {2}",  i.getProd_abono_n(), i.getProd_sku_str(), Cliente) );
                            db.execSQL(con);
                        }

                        if (Integer.parseInt( i.getProd_ventaant_n()) >0)
                        {
                            con= string.formatSql2(Querys.Inventario.InsertMovimiento, conf.getRutaStr(), i.getProd_cve_n(),
                                    noCli, conf.getUsuario(),
                                    String.valueOf(  Double.parseDouble(i.getProd_cantiv_n()) - Double.parseDouble(i.getProd_ventadia_n()) - Double.parseDouble(i.getProd_regalo_n())  )   ,
                                    i.getProd_ventaant_n(), "0",
                                    String.valueOf(   Double.parseDouble(i.getProd_cantiv_n())  - Double.parseDouble(i.getProd_ventadia_n())  - Double.parseDouble( i.getProd_regalo_n() )   )   ,
                                    string.formatSql("Venta de {0} envases {1} del Cliente: {2}", i.getProd_ventaant_n(), i.getProd_sku_str(), Cliente));
                            db.execSQL(con);
                        }
                    }
                    //------ Pago de envase anterior ------//

                }
                //--------------- Trabajo con envase  ---------------------//



                con= string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), noCli, "CON COBRANZA", "VISITA CON COBRANZA", coordenada);
                db.execSQL(con);

                con= string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), noCli, "CIERRE CLIENTE", "CIERRE SIN VENTA", coordenada);
                db.execSQL(con);

                con= string.formatSql2(Querys.Trabajo.InsertVisita,
                        conf.getUsuario(), noCli, "null","null", "CON COBRANZA", "VISITA CON VENTA", coordenada);
                db.execSQL(con);

                con= string.formatSql2(Querys.Preventa.UpdateVisitaReparto, FolioVisita);
                db.execSQL(con);

                db.setTransactionSuccessful();

            }catch (Exception e)
            {
                Utils.msgError(this, getString(R.string.error_almacenar), e.getMessage());
            }
            finally
            {
                if(db.isOpen())
                {
                    db.endTransaction();
                    db.close();
                }
            }

            if(cobranza)
            {
                Utils.msgInfo(this,"IMPRIMIR TICKET");
            }

            if (ConVenta)
            {
                Intent i = getIntent();
                i.putExtra("estado", "true");
                i.putExtra("cve", rc.getCli_cve_n());
                setResult(RESULT_OK, i);
                finish();
            }
            else
            {
                Intent i = getIntent();
                i.putExtra("estado", "false");
                i.putExtra("cve", rc.getCli_cve_n());
                setResult(RESULT_OK, i);
                finish();
            }

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_ped36), e.getMessage());
        }
    }

    private boolean ValidarCondicionesVenta()
    {
        try
        {
            ArrayList<DataTableLC.ProductosPed> vta = dgProd2;
            ArrayList<DataTableLC.ProdCondiciones> ProdsCond = new ArrayList<>();

            for(DataTableLC.ProductosPed p : dgProd2)
            {
                if( Utils.getBool( p.getCov_reStringido_n() ) )
                {
                    DataTableLC.ProdCondiciones np = new DataTableLC.ProdCondiciones();
                    np.setProd_cve_n( p.getProd_cve_n() );
                    np.setProd_sku_str( p.getProd_sku_str() );
                    np.setProd_desc_str( p.getProd_desc_str() );
                    np.setFams( p.getCov_familias_str() );
                    ProdsCond.add(np);
                }
            }

            if(ProdsCond.size()>0)
            {
                if(Utils.getBool( rc.getCli_especial_n() ) && !RutaPromoCe )
                {
                    Utils.msgInfo(this, getString(R.string.msg_ped7) );
                    return false;
                }

                for(DataTableLC.ProdCondiciones pc : ProdsCond)
                {
                    ArrayList<String> FamCumple = new ArrayList<>();

                    for(DataTableLC.ProductosPed fc : vta)
                    {
                        if( pc.getFams().equals( fc.getFam_cve_n() ) &&
                            Integer.parseInt( fc.getProd_cant_n() ) >0)
                        {
                            FamCumple.add(  fc.getProd_sku_str() );
                        }
                    }

                    if (FamCumple.size()>0)
                    {
                        String msg= string.formatSql( getString(R.string.msg_ped8), pc.getProd_sku_str(), pc.getProd_desc_str() );
                        Utils.msgInfo(this, msg);
                        return false;
                    }
                }
            }
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    private boolean ValidarEnvase()
    {
        double cant = 0;
        double total = 0;
        double EnvAct = 0;

        for(DataTableLC.EnvasesPed er : dgEnvase)
        {
            if(Integer.parseInt( er.getRestante() ) < 0)
            {
                Utils.msgInfo(this, getString(R.string.msg_ped9));
                return false;
            }
        }

        for(DataTableLC.EnvasesAdeudo ea : dgDeudaEnv)
        {
            if(ea.getProd_sku_str().equals("264") || ea.getProd_sku_str().equals("432") )
                cant+= Double.parseDouble( ea.getSaldo() );
        }
        total = cant;

        cant=0;
        for(DataTableLC.EnvasesPed er : dgEnvase)
        {
            if(  er.getProd_sku_str().equals("264") || er.getProd_sku_str().equals("432") )
                cant+= Double.parseDouble( er.getRestante() );
        }

        EnvAct = cant;
        total += cant;

        double EnvPermitido = 0;

        if ( Utils.getBool( rc.getCli_especial_n() ) )
            return true;

        EnvPermitido = Double.parseDouble( rc.getCli_credenvases_n() );

        if (EnvPermitido < 10)
            EnvPermitido = 10;
        if (EnvPermitido >= total)
            return true;
        else
        {
            if (EnvAct == 0 && total > 0)
            {
                String men = string.formatSql( getString(R.string.msg_ped10), String.valueOf( EnvPermitido  ) , String.valueOf(total)  );
                Utils.msgInfo(this, men );
                return true;
            }
            String men = string.formatSql( getString(R.string.msg_ped11), String.valueOf(EnvPermitido), String.valueOf( total-EnvPermitido ) );
            Utils.msgInfo(this, men );
            return false;
        }

    }

    private void clickDetalles()
    {
        DataTableLC.DtCliVenta cli = getDetcli( rc.getCli_cve_n());
        Intent intent = new Intent(this, MainDetallesActivity.class);
        intent.putExtra("cliente",cli);
        startActivity(intent);
    }

    private void clickBuscar()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getString(R.string.msg_importante));
        dialogo1.setMessage(getString(R.string.msg_ped2));
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                alerta2();
            }
        });
        dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        dialogo1.show();

    }

    private void alerta2()
    {
        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(this);
        dialogo2.setTitle(getString(R.string.msg_importante));
        dialogo2.setMessage(getString(R.string.msg_ped3));
        dialogo2.setCancelable(false);
        dialogo2.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Intent i = getIntent();
                i.putExtra("estado", "false");
                i.putExtra("cve", rc.getCli_cve_n());
                setResult(RESULT_OK, i);
                finish();
            }
        });
        dialogo2.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        dialogo2.show();
    }

    private DataTableLC.DtCliVenta getDetcli(String cliente)
    {
        String json;
        json=BaseLocal.Select( string.formatSql(Querys.ClientesVentaMes.SelClienteConVtaMes,cliente), getApplicationContext() );
        ArrayList<DataTableLC.DtCliVenta> cliVenta = ConvertirRespuesta.getDtCliVentaJson(json);
        DataTableLC.DtCliVenta cli=null;

        if(cliVenta!=null)
            cli = cliVenta.get(0);

        return cli;
    }

    public boolean validaDistancia(DataTableLC.DtCliVenta c, boolean cierre)
    {
        try
        {
            String cini = c.getCli_coordenadaini_str();
            String clicve = c.getCli_cve_n();
            Configuracion conf = Utils.ObtenerConf(getApplication());

            short k = 0;
            String ActPos = "NO VALIDA";
            double DistLimite = 20;
            double DistAlerta = 10;
            String ValidarGPS = "";

            if (!cierre)
                ValidarGPS = "VALIDAR DISTANCIA GPS";
            else
                ValidarGPS = "VALIDAR DISTANCIA GPS AL CIERRE";

            ActPos = getLatLon();

            double distancia = Utils.Distancia(cini, ActPos);

            if (c.getCli_invalidagps_n().equals("1")) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), c.getCli_cve_n(), ValidarGPS, string.formatSql("INVALIDADO POR SISTEMAS: {0} MTS", String.valueOf(distancia)), ActPos), getApplicationContext());
                return true;
            }

            if (conf.isAuditoria()) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), c.getCli_cve_n(), ValidarGPS, string.formatSql("INVALIDADO POR AUDITORIA: {0} MTS", String.valueOf(distancia)), ActPos), getApplicationContext());
                return true;
            }

            if (cini == null || cini.isEmpty()) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, "CLIENTE SIN COORDENADA INICIAL", ActPos), getApplicationContext());
                return true;
            }

            if (cini.length() <= 5) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, "CLIENTE CON COORDENADA INICIAL NO VALIDA: " + cini, ActPos), getApplicationContext());
                return true;
            }

            if (ActPos.equals("NO VALIDA")) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, "NO SE LOGRO OBTENER GPS ACTUAL", ActPos), getApplicationContext());
                return true;
            }

            if (distancia >= 0 && distancia <= DistAlerta) {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, string.formatSql("DISTANCIA DENTRO DEL PARAMETRO {0} MTS", String.valueOf(distancia)), ActPos), getApplicationContext());
                return true;
            }

            if (distancia >= 0 && distancia <= DistLimite)
            {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, string.formatSql("DISTANCIA FUERA DEL PARAMETRO {0} MTS", String.valueOf(distancia) ), ActPos), getApplicationContext());
                return true;
            }

            if (distancia >= 0 && distancia > DistLimite)
            {
                BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,
                        conf.getUsuario(), conf.getRutaStr(), clicve, ValidarGPS, string.formatSql("POSICION ACTUAL INVALIDA FUERA DE PARAMETRO {0} MTS", String.valueOf(distancia)), ActPos), getApplicationContext());
                return false;
            }

            return false;
        }catch (Exception e)
        {
            Log.d("salida","error al validar disstancia: "+e.getMessage());
            return false;
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
    public void setTextKit(String kit)
    {
        _txtKit = kit;
    }

    //endregion

    //region VARIABLES PARA UBICACION
    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;
    private GoogleApiClient apiClient;
    private LocationRequest locRequest;
    String latLon=null;
    //endregion
    //region Obtener ubicación actual

    public String getLatLon()
    {
        return latLon;
    }

    public void enableLocationUpdates() {

        locRequest = new LocationRequest();
        locRequest.setInterval(2000);
        locRequest.setFastestInterval(1000);
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locRequest)
                        .build();

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        apiClient, locSettingsRequest);

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        Log.i(LOGTAG, "Configuración correcta");
                        startLocationUpdates();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.i(LOGTAG, "Se requiere actuación del usuario");
                            status.startResolutionForResult(PedidosActivity.this, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            //BLOQUEAR OPCIONES POR UBICACION
                            Log.i(LOGTAG, "Error al intentar solucionar configuración de ubicación");
                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(LOGTAG, "No se puede cumplir la configuración de ubicación necesaria");
                        //BLOQUEAR OPCIONES POR UBICACION
                        break;
                }
            }
        });

        Log.d("salida","Se ha activado la ubicacion");
    }

    public void disableLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                apiClient, this);

        Log.d("salida","Se ha desactivado la ubicacion");

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(PedidosActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, PedidosActivity.this);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Conectado correctamente a Google Play Services

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Se ha interrumpido la conexión con Google Play Services

        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) {
        if (loc != null) {
            latLon = loc.getLatitude()+","+loc.getLongitude();
        } else {
            latLon = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateUI(lastLocation);

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e(LOGTAG, "Permiso denegado");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case PETICION_CONFIG_UBICACION:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(LOGTAG, "El usuario no ha realizado los cambios de configuración necesarios");
                        //BLOQUEAR OPCIONES POR UBICACION
                        break;
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(LOGTAG, "Recibida nueva ubicación!");
        //Mostramos la nueva ubicación recibida
        updateUI(location);
    }


    //endregion


}
