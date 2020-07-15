package com.tdt.easyroute.Ventanas.Preventa;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import com.tdt.easyroute.Adapter.PagerPrevPedidosAdapter;
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
import com.tdt.easyroute.Ventanas.Pedidos.DetallesCliente.MainDetallesActivity;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;
import java.util.Date;

import static com.tdt.easyroute.Clases.ConvertirRespuesta.getDtEnvasesPreventa;

public class PreventaActivity extends AppCompatActivity implements         GoogleApiClient.OnConnectionFailedListener,
                    GoogleApiClient.ConnectionCallbacks,
                    LocationListener {

    private boolean esventa;
    private String noCli;
    private String Cliente;
    private boolean ConVenta;
    private boolean Visitado = false;
    private String PositionStr;
    private String comentario = "";
    private boolean removerVenta = false;
    private String _tv_adeudoN;
    private String _tv_totAbono;
    private String _txtKit;
    private String _txtSaldoDeudaEnv;
    private String _txtVenta;
    private String _txtSaldo;
    private String folio = "";
    private long cliente = 0;
    private int result = 0;


    Configuracion conf;
    DataTableLC.DtCliVentaNivel rc;
    ArrayList<DataTableLC.ProductosPed> dtProductos;
    ArrayList<DataTableLC.ProductosPed> dgProd2;
    ArrayList<DataTableLC.CompPrevDet> ListaPrev;
    ArrayList<DataTableLC.DgAbonos> dgAbonos;
    ArrayList<DataTableLC.DgPagos> dgPagos;
    ArrayList<DataTableLC.EnvasesAdeudo> dgDeudaEnv;

    ArrayList<DataTableLC.PedPromocionesKit> dtKits;
    ArrayList<DataTableLC.EnvasesPreventa> dgEnvase;
    ArrayList<DataTableWS.FormasPago> formasPago;
    ArrayList<DataTableWS.FormasPago> formasPago2;
    ArrayList<DataTableLC.Creditos> dgCreditos;

    boolean RutaMayorista = false;
    boolean RutaPromoCe = false;

    int catenv = -1;
    int catcvz = -1;
    int catvasos = -1;
    int cathielo = -1;
    int lpre = -1;
    int lpBase = -1;

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

        setTitle(getString(R.string.title_preventa));


        noCli = getIntent().getStringExtra("idcli");
        cliente = Long.parseLong(noCli);

        inicializar();

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped6));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_cred3));
        if (!removerVenta)
            tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped3));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped4));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPagerNonSwipable viewPager = findViewById(R.id.pager);
        final PagerPrevPedidosAdapter adapter = new PagerPrevPedidosAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        ValidaCredito();
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

        //CONFIGURACION DE UBICACION
        //Construcción cliente API Google
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    protected void onResume()
    {
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

        String sql = string.formatSql2("select visp_folio_str from visitapreventa " +
                "where cli_cve_n={0} and strftime('%d-%m-%Y', visp_fecha_dt)= strftime('%d-%m-%Y', datetime('now','localtime'))", String.valueOf(cliente));
        folio = BaseLocal.SelectDato(sql,getApplicationContext());

        if(folio!=null)
        {
            verificarModificar();
        }
        else
        {
            String dtf = BaseLocal.SelectDato("select max(visp_folio_str) from visitapreventa where strftime('%d-%m-%Y', visp_fecha_dt)= strftime('%d-%m-%Y', datetime('now','localtime'))", getApplicationContext());

            if (dtf != null)
            {
                String fecha= Utils.FechaLocal().replace("-","");

                String ruta = conf.getRutaStr();
                String rutaN = "";
                int ceros = 3-ruta.length();
                for(int i=0; i<ceros;i++) rutaN+="0";
                ruta = rutaN+ruta;

                String fol = dtf.substring(11);
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
        }

        _tv_adeudoN="$0.00";
        _tv_totAbono="$0.00";

        dgPagos = new ArrayList<>();
        dgAbonos = new ArrayList<>();

        ListarFormaPago();
        ListarFormaPago2();
        ObtenerCreditos();
        ObtenerEnvase();
        ObtenerProductos();

        lpre = ObtenerListaPrecio();
        lpBase = ObtenerListaPrecio("BASE");

        if (Visitado &&  rc.getEst_cve_str().equals("A"))
        {
            CargarDatos();
        }

        ValidaClienteInactivo();
    }

    private void verificarModificar()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getString(R.string.msg_prev7));
        dialogo1.setMessage(getString(R.string.msg_prev6));
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Visitado = true;
            }
        });
        dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                finish();
            }
        });
        dialogo1.show();
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
            String json = BaseLocal.Select(string.formatSql2(Querys.ListaPrecio.ListaPreciosPreLpreNota2, rc.getLpre_cve_n(), rc.getNota_cve_n(), String.valueOf(catenv)), getApplicationContext());
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

    private void ObtenerEnvase()
    {

        ArrayList<DataTableWS.ListaPrecios> dtLpre;
        ArrayList<DataTableLC.EnvasesPreventa> dtEnv;

        String json = BaseLocal.Select( string.formatSql2("select * from listaprecios where lpre_cve_n={0}", rc.getLpre_cve_n()), getApplicationContext() );
        dtLpre = ConvertirRespuesta.getListaPreciosJson(json);

        Double lpreb=0.0;
        if (dtLpre.size() > 0)
        {
            if ( dtLpre.get(0).getLpre_base_n().equals("0")  )
                lpreb =  Double.parseDouble(rc.getLpre_cve_n()) ;
            else
                lpreb =  Double.parseDouble(dtLpre.get(0).getLpre_base_n()) ;
        }
        else
            lpreb = Double.parseDouble(rc.getLpre_cve_n()) ;

        json = BaseLocal.Select( string.formatSql2(Querys.Preventa.ObtenerEnvasePreventa2, String.valueOf(cliente) ,  String.valueOf(lpreb), rc.getLpre_cve_n()), getApplicationContext() );
        dtEnv = ConvertirRespuesta.getDtEnvasesPreventa(json);

        if(dtEnv!=null)
        {
            for(int i=0; i<dtEnv.size();i++)
            {
                DataTableLC.EnvasesPreventa e = dtEnv.get(i);

                double sum = Double.parseDouble(e.getProd_saldo_n()) + Double.parseDouble(e.getProd_cargo_n()) - Double.parseDouble(e.getProd_abono_n())-Double.parseDouble(e.getProd_regalo_n())-Double.parseDouble(e.getProd_venta_n());
                dtEnv.get(i).setProd_final_n( String.valueOf(sum) );

                double sum2 = Double.parseDouble(e.getProd_venta_n()) * Double.parseDouble(e.getLpre_precio_n());
                dtEnv.get(i).setProd_subtotal_n( String.valueOf(sum2) );
            }
        }

        dgEnvase= dtEnv;
        pedidosVM.setDgEnvasePrev(dgEnvase);

    }

    private void ObtenerCreditos()
    {
        ArrayList<DataTableLC.Creditos> dtcr;

        String json = BaseLocal.Select  (string.formatSql2("select cred_cve_n,cred_referencia_str,cred_fecha_dt,cred_monto_n" +
                ",cred_abono_n from creditos where cli_cve_n={0} and cred_esenvase_n=0 ", String.valueOf(cliente)),getApplicationContext());

        dtcr = ConvertirRespuesta.getCreditos2Json(json);

        if(dtcr!=null)
        {
            for (int i = 0; i < dtcr.size(); i++) {
                String saldo = String.valueOf(Double.parseDouble(dtcr.get(i).getCred_monto_n()) - Double.parseDouble(dtcr.get(i).getCred_abono_n()));
                dtcr.get(i).setCred_saldo_n(saldo);
            }
        }
        else
        {
            dtcr = new ArrayList<>();
        }

        dgCreditos = dtcr;
        pedidosVM.setDgCreditos(dgCreditos);
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

    private void CargarDatos()
    {

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

    public void CalcularEnvase()
    {
        try {
            boolean HayNegativos = false;

            for (DataTableLC.EnvasesPreventa r : dgEnvase) {
                double can = 0;
                for (DataTableLC.ProductosPed p : dgProd2) {
                    if (p.getId_envase_n().equals(r.getProd_cve_n()))
                        can += Double.parseDouble(p.getProd_cant_n());
                }
                r.setProd_cargo_n(String.valueOf(can));

                /*if (Integer.parseInt(r.getRestante()) < 0) {
                    r.setRecibido("0");
                    r.setVenta("0");
                    HayNegativos = true;
                }*/
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
                    for(DataTableLC.EnvasesPreventa r : dgEnvase)
                        if( r.getProd_sku_str().equals("264") || r.getProd_sku_str().equals("432") )
                            r.setProd_regalo_n( r.getProd_cargo_n() );
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
                        for(DataTableLC.EnvasesPreventa r : dgEnvase)
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
                                        r.setProd_regalo_n(String.valueOf(kp));
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
                            for(DataTableLC.EnvasesPreventa r : dgEnvase )
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
                                    r.setProd_regalo_n( String.valueOf(kp) );
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

            pedidosVM.setEstadoCredito("Al Corriente");

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
            for(DataTableLC.Creditos an: dgCreditos)
            {
                Date f1 = Utils.FechaDT( an.getCred_fecha_dt() );
                Date f2 = Utils.FechaDT( Utils.FechaModificarDias( Utils.FechaLocal(),-diascred ) );

                if(f1.compareTo(f2)<=0)
                    saldoVencido+= Double.parseDouble( an.getCred_saldo_n() );
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
                dialogo1.setMessage( string.formatSql( getString(R.string.msg_prev1), String.valueOf( NotaP )  ) );
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

        if(  Double.parseDouble( string.DelCaracteres(_txtVenta ) ) <=0   )
        {
            AlertDialog.Builder dialogo2 = new AlertDialog.Builder(this);
            dialogo2.setTitle(getString(R.string.msg_importante));
            dialogo2.setMessage(getString(R.string.msg_prev2));
            dialogo2.setCancelable(false);
            dialogo2.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    verificarVisitado();
                }
            });
            dialogo2.setNegativeButton(getString(R.string.msg_no), null);
            dialogo2.show();
        }
        else
        {
            verificarVisitado();
        }
    }

    private void verificarVisitado()
    {
        if(Double.parseDouble( string.DelCaracteres( _txtVenta) ) >0 &&
                Double.parseDouble( string.DelCaracteres(_txtSaldo ) ) >0 )
        {
            Utils.msgInfo(this, getString(R.string.msg_prev3) );
            return;
        }

        if(Double.parseDouble( string.DelCaracteres(_txtVenta) ) >0 &&
                Double.parseDouble( string.DelCaracteres(_txtSaldo) ) <0 )
        {
            Utils.msgInfo(this, getString(R.string.msg_ped14) );
            return;
        }

        try {
            boolean guardado = false;

            if (!Visitado)
                guardado = guardar2();
            else
                guardado = Actualizar();

            if(guardado)
            {
                Log.d("salida","IMPRIMIR TICKET");
                Utils.msgInfo(this, "IMPRIMIR TICKET");

                Intent i = getIntent();
                i.putExtra("idcli", rc.getCli_cve_n());
                i.putExtra("result", result);
                setResult(RESULT_OK, i);
                finish();
            }

        }
        catch (Exception e)
        {
            Utils.msgError(this,getString(R.string.errorPrev1), e.getMessage());
        }

    }

    private boolean guardar2()
    {
        try
        {
            boolean conventa = false;
            boolean concobranza = false;

            String json = BaseLocal.Select( string.formatSql("Select * from visitapreventa where visp_folio_str='{0}'",folio),this );
            ArrayList<DataTableWS.VisitaPreventa> dtPrevExist;
            dtPrevExist = ConvertirRespuesta.getDtVisitaPreventa(json);

            DatabaseHelper databaseHelper = new DatabaseHelper(this, getString(R.string.nombreBD), null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try
            {
                db.beginTransaction();
                String ubicacion = getLatLon();

                if (dtPrevExist==null)
                {
                    String hora = Utils.FechaLocal()+"T"+Utils.HoraLocal();
                    db.execSQL( string.formatSql2(Querys.Preventa.InsertVisitaPrev, folio, String.valueOf( cliente ) , conf.getRutaStr(),  hora,  ubicacion, conf.getUsuario() ));
                }

                String sql = string.formatSql2(Querys.Preventa.InsPreventa, folio, String.valueOf(cliente),
                        conf.getRutaStr(), rc.getLpre_cve_n(), rc.getCli_dirfact_n(), conf.getUsuario().toUpperCase(),
                        ubicacion,null,comentario.toUpperCase() );


                db.execSQL(sql);

                //Detalle Preventa
                if(dgProd2.size()>0)
                    conventa = true;

                int k = 1;
                for (DataTableLC.ProductosPed ri : dgProd2)
                {
                    double CantKit = 0;
                    double PreKit = 0;
                    boolean kit = false;

                    if ( Double.parseDouble( ri.getProm_kit_n() ) > 0  )
                    {
                        ArrayList<DataTableLC.PedPromocionesKit> rki = new ArrayList<>();

                        for(DataTableLC.PedPromocionesKit pk : dtKits)
                            if(pk.getProm_cve_n().equals(ri.getProm_kit_n())  &&  pk.getProd_cve_n().equals(ri.getProd_cve_n()) )
                                rki.add(pk);

                        if (rki.size() > 0)
                        {
                            //Verifica si el precio de promocion es menor que el del cliente
                            if ( Double.parseDouble( rki.get(0).getLpre_precio_n() )  < Double.parseDouble( ri.getLpre_precio_n() ) )
                            {
                                kit = true;
                                CantKit = Double.parseDouble(rki.get(0).getProd_cant_n()) * Double.parseDouble(rki.get(0).getProm_veces_n());
                                PreKit = Double.parseDouble(rki.get(0).getLpre_precio_n());
                            }
                        }
                    }

                    if(Double.parseDouble(ri.getProd_cant_n())>CantKit)
                    {
                        String c1 = String.valueOf(Double.parseDouble(ri.getProd_cant_n()) - CantKit);
                        String c2 = String.valueOf((Double.parseDouble(ri.getProd_cant_n()) - CantKit) * Double.parseDouble(ri.getLpre_precio_n()));

                        sql = string.formatSql2(Querys.Preventa.InsPreventaDet, folio,
                                String.valueOf(k), ri.getProd_cve_n(), ri.getProd_sku_str(), "0",
                                c1, ri.getLpre_base_n(), ri.getLpre_cliente_n(),
                                ri.getLpre_promo_n(), ri.getLpre_precio_n(), ri.getProd_promo_n(), ri.getProm_cve_n(),
                                c2, "0");

                        db.execSQL(sql);
                    }

                    if (kit)
                    {
                        if (Double.parseDouble(ri.getProd_cant_n()) > CantKit)
                            k++;

                        sql= string.formatSql2(Querys.Preventa.InsPreventaDet, folio,
                                String.valueOf(k), ri.getProd_cve_n(),  ri.getProd_sku_str(), "0",
                                String.valueOf( CantKit ), ri.getLpre_base_n(), ri.getLpre_cliente_n(),
                                ri.getLpre_promo_n(), String.valueOf( PreKit ), ri.getProd_promo_n(),
                                ri.getProm_cve_n(), String.valueOf( PreKit*CantKit ) , "1");

                        db.execSQL(sql);
                    }
                    k++;
                }

                //MessageBox.Show("insert detalle preventa");
                //Envases

                for (int i = 0; i < dgEnvase.size(); i++)
                {
                    DataTableLC.EnvasesPreventa ri = dgEnvase.get(i);

                    sql = string.formatSql2(Querys.Preventa.InsPreventaEnv, folio,
                            ri.getProd_cve_n(), ri.getProd_sku_str(),  ri.getProd_saldo_n(),
                            ri.getProd_cargo_n(),  ri.getProd_abono_n(), ri.getProd_regalo_n(), ri.getProd_venta_n(),
                            ri.getProd_final_n(),  ri.getProd_precio_n(), ri.getLpre_precio_n());
                    db.execSQL(sql);
                }

                //MessageBox.Show("insert envase preventa");
                //PromesasAbono

                if ( dgAbonos.size()>0 )
                    concobranza = true;

                for (int i = 0; i < dgAbonos.size(); i++ )
                {
                    DataTableLC.DgAbonos ri = dgAbonos.get(i);
                    sql = string.formatSql2(Querys.Preventa.InsPreventaPagos, folio, ri.getNoAbono(),"1", ri.getFpag_cve_n(),  ri.getFpag_cant_n());
                    db.execSQL(sql);
                }

                //MessageBox.Show("insert abono preventa");
                //PromesasPago

                for (int i = 0; i < dgPagos.size(); i++)
                {
                    DataTableLC.DgPagos ri = dgPagos.get(i);
                    sql= string.formatSql2(Querys.Preventa.InsPreventaPagos, folio, ri.getNoPago(), "0", ri.getFpag_cve_n(), ri.getFpag_cant_n());
                    db.execSQL(sql);

                }

                String CoorPre = getLatLon();

                //GUARDAR LOG
                if (!conventa && !concobranza)
                {
                    result = 0;
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente) , "SIN PREVENTA", "VISITA SIN PREVENTA", CoorPre));
                    db.execSQL( string.formatSql2(Querys.Trabajo.InsertVisita, conf.getUsuario(), String.valueOf(cliente) , "null", "null", "SIN PREVENTA", "VISITA SIN PREVENTA", CoorPre) );
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente) , "CIERRE CLIENTE", "CIERRE SIN PREVENTA", CoorPre));
                }

                if (!conventa && concobranza)
                {
                    result = 2;
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente), "COBRANZA", "COBRANZA", CoorPre));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertVisita, conf.getUsuario(), String.valueOf(cliente) , "null", "null", "COBRANZA", "COBRANZA", CoorPre));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente), "CIERRE CLIENTE", "CIERRE CON COBRANZA", CoorPre));
                }

                if (conventa)
                {
                    result = 1;
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente), "CON PREVENTA", "VISITA CON PREVENTA", CoorPre));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertVisita, conf.getUsuario(), String.valueOf(cliente) , "null", "null", "CON PREVENTA", "VISITA CON PREVENTA", CoorPre));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente), "CIERRE CLIENTE", "CIERRE CON PREVENTA", CoorPre));
                }

                db.setTransactionSuccessful();

                Utils.msgInfo(this, getString(R.string.msg_prev4));

                if(db.isOpen())
                {
                    db.endTransaction();
                    db.close();
                }

                return true;
            }catch (Exception e)
            {
                Utils.msgError(this, getString(R.string.error_almacenar), e.getMessage());

                if(db.isOpen())
                {
                    db.endTransaction();
                    db.close();
                }
                return false;
            }
        }
        catch (Exception e)
        {
            Utils.msgError(this,getString(R.string.errorPrev1), e.getMessage());
            return false;
        }
    }

    private boolean Actualizar()
    {
        try
        {
            boolean conventa = false;
            boolean concobranza = false;


            ArrayList<DataTableWS.Preventa>  dtcp = new ArrayList<>();

            String res= BaseLocal.Select( string.formatSql2("select * from preventa where prev_folio_str='{0}'", folio), getApplicationContext() );
            dtcp = ConvertirRespuesta.getPreventaJson(res);

            if ( dtcp==null  || dtcp.size()<=0)
            {
                String sql = string.formatSql2(Querys.Preventa.InsPreventa, folio, String.valueOf(cliente),
                        conf.getRutaStr(), rc.getLpre_cve_n(), rc.getCli_dirfact_n(), conf.getUsuario().toUpperCase(),
                        getLatLon(), null, comentario.toUpperCase());
                BaseLocal.Insert(sql, getApplicationContext());
            }

            DatabaseHelper databaseHelper = new DatabaseHelper(this, getString(R.string.nombreBD), null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            try
            {
                db.beginTransaction();

                //Detalle Preventa
                db.execSQL(string.formatSql2("delete from preventadet where prev_folio_str='{0}'", folio));
                if (  dgProd2.size()>0)
                    conventa = true;

                //----- Proceso Promociones Kit
                int k = 1;
                for(DataTableLC.ProductosPed ri : dgProd2)
                {
                    double CantKit = 0;
                    double PreKit = 0;
                    boolean kit = false;

                    if( Double.parseDouble( ri.getProm_kit_n() )   > 0)
                    {
                        ArrayList<DataTableLC.PedPromocionesKit> rki = new ArrayList<>();
                        for(DataTableLC.PedPromocionesKit p : dtKits)
                        {
                            if(p.getProm_cve_n().equals(ri.getProm_kit_n())  &&  p.getProd_cve_n().equals(ri.getProd_cve_n())  )
                                rki.add(p);
                        }

                        if (rki.size() > 0)
                        {
                            //Verifica si el precio de promocion es menor que el del cliente
                            if( Double.parseDouble(rki.get(0).getLpre_precio_n())  < Double.parseDouble(ri.getLpre_precio_n()) )
                            {
                                kit = true;

                                CantKit = Double.parseDouble( rki.get(0).getProd_cant_n() )
                                        * Double.parseDouble( rki.get(0).getProm_veces_n() );

                                PreKit = Double.parseDouble( rki.get(0).getLpre_precio_n() );
                            }
                        }
                    }

                    if (  Double.parseDouble(ri.getProd_cant_n()) > CantKit )
                    {
                        String sql = string.formatSql2(Querys.Preventa.InsPreventaDet, String.valueOf( folio ),
                                String.valueOf(k),ri.getProd_cve_n(), ri.getProd_sku_str(), "0",
                                String.valueOf( Double.parseDouble(ri.getProd_cant_n())-CantKit )  ,
                                ri.getLpre_base_n(),  ri.getLpre_cliente_n(),
                               ri.getLpre_promo_n(), ri.getLpre_precio_n(), ri.getProd_promo_n() ,
                                ri.getProm_cve_n(),
                                String.valueOf ( (Double.parseDouble(ri.getProd_cant_n())-CantKit) * Double.parseDouble(ri.getLpre_precio_n()) )  ,
                                "0");

                        db.execSQL(sql);
                    }

                    if (kit)
                    {
                        if(Double.parseDouble(ri.getProd_cant_n()) > CantKit)
                            k++;

                        String sql =string.formatSql2(Querys.Preventa.InsPreventaDet, folio,
                                String.valueOf(k),  ri.getProd_cve_n(), ri.getProd_sku_str(), "0",
                                String.valueOf(CantKit) , ri.getLpre_base_n(),  ri.getLpre_cliente_n(),
                                 ri.getLpre_promo_n(), String.valueOf(PreKit), ri.getProd_promo_n(),
                                 ri.getProm_cve_n(), String.valueOf( PreKit * CantKit ) , "1");

                        db.execSQL(sql);
                    }

                    k++;
                }

                //Envases
                db.execSQL(  string.formatSql2("delete from preventaenv where prev_folio_str='{0}'", folio) );

                for (int i = 0; i < dgEnvase.size(); i++)
                {
                    DataTableLC.EnvasesPreventa ri = dgEnvase.get(i);

                    String sql = string.formatSql2(Querys.Preventa.InsPreventaEnv, folio,
                            ri.getProd_cve_n(),  ri.getProd_sku_str(), ri.getProd_saldo_n(),
                            ri.getProd_cargo_n(), ri.getProd_abono_n(), ri.getProd_regalo_n(), ri.getProd_venta_n(),
                            ri.getProd_final_n(), ri.getProd_precio_n(), ri.getLpre_precio_n());
                    db.execSQL(sql);

                }

                //PromesasAbono

                db.execSQL(string.formatSql2("delete from preventapagos where prev_folio_str='{0}'", folio));

                if ( dgAbonos.size() >0 )
                    concobranza = true;

                for (int i = 0; i < dgAbonos.size() ; i++)
                {
                    DataTableLC.DgAbonos ri = dgAbonos.get(i);
                    String sql = string.formatSql2(Querys.Preventa.InsPreventaPagos, folio,  ri.getNoAbono(), "1",  ri.getFpag_cve_n(), ri.getFpag_cant_n());
                    db.execSQL(sql);
                }

                //PromesasPago
                for (int i = 0; i < dgPagos.size(); i++)
                {
                    DataTableLC.DgPagos ri = dgPagos.get(i);
                    String sql = string.formatSql2(Querys.Preventa.InsPreventaPagos, folio, ri.getNoPago(), "0", ri.getFpag_cve_n(), ri.getFpag_cant_n());
                    db.execSQL(sql);
                }

                //GUARDAR LOG
                if (!conventa && !concobranza)
                {
                    result = 0;
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf( cliente ) , "SIN VENTA", "VISITA SIN VENTA", ""));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertVisita, conf.getUsuario(), String.valueOf( cliente ), "null", "null", "SIN VENTA", "VISITA SIN VENTA", getLatLon()));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf( cliente ), "CIERRE CLIENTE", "CIERRA SIN VENTA", ""));
                }

                if (!conventa && concobranza)
                {
                    result = 2;
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente) , "COBRANZA", "COBRANZA", ""));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertVisita, conf.getUsuario(), String.valueOf(cliente), "null", "null", "COBRANZA", "COBRANZA", getLatLon()));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente), "CIERRE CLIENTE", "CIERRE CON COBRANZA", ""));
                }

                if (conventa)
                {
                    result = 1;
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente), "CON PREVENTA", "VISITA CON PREVENTA", getLatLon()));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertVisita, conf.getUsuario(), String.valueOf(cliente), "null", "null", "CON PREVENTA", "VISITA CON PREVENTA", getLatLon()));
                    db.execSQL(string.formatSql2(Querys.Trabajo.InsertBitacoraHHPedido,conf.getUsuario(), conf.getRutaStr(), String.valueOf(cliente), "CIERRE CLIENTE", "CIERRE CON PREVENTA", ""));
                }

                db.setTransactionSuccessful();

                Utils.msgInfo(this, getString(R.string.msg_prev5));

                if(db.isOpen())
                {
                    db.endTransaction();
                    db.close();
                }

                return true;
            }catch (Exception e)
            {
                Utils.msgError(this, getString(R.string.error_almacenar), e.getMessage());
                if(db.isOpen())
                {
                    db.endTransaction();
                    db.close();
                }
                return false;
            }

        }
        catch (Exception e)
        {
            Utils.msgError(this,getString(R.string.errorPrev1), e.getMessage());
            return false;
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
                if( Utils.getBool( p.getCov_reStringido_n() ) &&  Integer.parseInt( p.getProd_cant_n() ) >0  )
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
                if(Utils.getBool( rc.getCli_especial_n() ) )
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

    private void clickDetalles()
    {
        DataTableLC.DtCliVenta cli = getDetcli( rc.getCli_cve_n());
        Intent intent = new Intent(this, MainDetallesActivity.class);
        intent.putExtra("cliente",cli);
        startActivity(intent);
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
                            status.startResolutionForResult(PreventaActivity.this, PETICION_CONFIG_UBICACION);
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
        if (ActivityCompat.checkSelfPermission(PreventaActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, PreventaActivity.this);
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
