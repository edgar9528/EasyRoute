package com.tdt.easyroute.Ventanas.Ventas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerConfiguracionAdapter;
import com.tdt.easyroute.Adapter.PagerPedidosAdapter;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class PedidosActivity extends AppCompatActivity {

    private boolean esventa;
    private String noCli;
    private String Cliente;
    private boolean ConVenta;
    private boolean PorEscanear;

    Configuracion conf;
    DataTableLC.DtCliVentaNivel rc;
    ArrayList<DataTableLC.ProductosPed> dtProductos;
    boolean RutaMayorista = false;
    boolean RutaPromoCe = false;

    int catenv= -1;
    int catcvz = -1;
    int catvasos = -1;
    int cathielo = -1;
    int lpre = -1;
    int lpBase = -1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ordenes_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        setTitle(getString(R.string.title_pedidos));

        esventa = getIntent().getBooleanExtra("venta",false);
        noCli = getIntent().getStringExtra("idcli");
        Cliente = getIntent().getStringExtra("idext");
        ConVenta = getIntent().getBooleanExtra("conventa", false);
        PorEscanear = getIntent().getBooleanExtra("porEscanear",false);


        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped3));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tl_ped4));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager =  findViewById(R.id.pager);
        final PagerPedidosAdapter adapter = new PagerPedidosAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setOffscreenPageLimit(4);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        inicializar();

        //TERMINA CONFIGURACION DE LAS TABS
    }

    private void inicializar()
    {
        Log.d("salida","entro a inicializar");

        conf = Utils.ObtenerConf(getApplication());
        rc = ObtenerCliente();

        catenv = ObtenerIdCat("ENVASE");
        catcvz = ObtenerIdCat("CERVEZA");
        catvasos = ObtenerIdCat("VASOS");
        cathielo = ObtenerIdCat("HIELO");

        lpre = ObtenerListaPrecio();
        lpBase = ObtenerListaPrecio("BASE");

        ObtenerProductos();
        //ListarEnvase(lpBase);

    }

    private DataTableLC.DtCliVentaNivel ObtenerCliente()
    {
        DataTableLC.DtCliVentaNivel cli=null;

        try
        {
            String json = BaseLocal.Select(string.formatSql(Querys.ClientesVentaMes.SelClienteConVtaMesNivel, noCli), getApplicationContext());
            ArrayList<DataTableLC.DtCliVentaNivel> cliVenta = ConvertirRespuesta.getDtCliVentaNivelJson(json);

            if (cliVenta != null)
            {
                cli = cliVenta.get(0);
            }

            String mayorista = BaseLocal.SelectDato(string.formatSql("select rut_mayorista_n from rutas where rut_cve_n={0}", cli.getRut_cve_n()), getApplicationContext());
            String promoce = BaseLocal.SelectDato(string.formatSql("select rut_promoce_n from rutas where rut_cve_n={0}", cli.getRut_cve_n()), getApplicationContext());

            RutaMayorista = mayorista.equals("1");
            RutaPromoCe = promoce.equals("1");

            return cli;

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_cargarInfo), e.getMessage());
            Log.d("salida","entro a catch obtener clientes");
            return null;
        }
    }

    private int ObtenerIdCat(String cat)
    {
        int id;
        try
        {
            String idStr = BaseLocal.SelectDato( string.formatSql("select cat_cve_n from categorias where cat_desc_str='{0}'", cat), getApplicationContext() );
            if(idStr!=null)
                id = Integer.parseInt(idStr);
            else
                id = -1;
        }
        catch (Exception ex)
        {
            id = -1;
        }

        return id;
    }

    private int ObtenerListaPrecio()
    {
        int lp = -1;
        try
        {
            String lpStr = BaseLocal.SelectDato(string.formatSql("select lpre_cve_n from clientes where cli_cve_n={0}", noCli),getApplicationContext());

            if(lpStr!=null)
                lp = Integer.parseInt(lpStr);
            else
                lp = -1;

        }
        catch (Exception ex)
        {
            lp = -1;
        }
        return lp;
    }

    private int ObtenerListaPrecio(String Lista)
    {
        int lp = -1;
        try
        {
            String lpStr = BaseLocal.SelectDato( string.formatSql("select lpre_cve_n from listaprecios where lpre_desc_str='{0}'", Lista), getApplicationContext()  );

            if (lpStr!=null)
                lp = Integer.parseInt( lpStr);
            else
                lp = -1;
        }
        catch (Exception ex)
        {
            lp = -1;
        }
        return lp;
    }

    private void ObtenerProductos()
    {
        try
        {
            dtProductos=null;
            String json= BaseLocal.Select(string.formatSql2(Querys.ListaPrecio.ListaPreciosRepLpreNota2, rc.getLpre_cve_n(), rc.getNota_cve_n(), String.valueOf(catenv),conf.getRutaStr()),getApplicationContext());
            dtProductos = ConvertirRespuesta.getProductosPedJson(json);



            if( !Utils.getBool(rc.getCli_fba_n()) && !RutaMayorista && !Utils.getBool(rc.getCli_especial_n()) && !RutaPromoCe)
            {
                ArrayList<DataTableLC.Promociones5> promo = ObtenerPromociones5( rc.getNvc_nivel_n(), rc.getLpre_cve_n(), rc.getSeg_cve_n(),
                                                                                 rc.getGiro_cve_n(), rc.getTcli_cve_n(), rc.getNvc_nivel_n());
                DataTableLC.Promociones5 r;
                for(int i=0; i< promo.size();i++)
                {
                    r=promo.get(i);

                    switch ( Integer.parseInt( r.getTprom_cve_n() )  )
                    {
                        case 1:
                            DataTableLC.ProductosPed ri=null;

                            for(int j=0; j< dtProductos.size();j++)
                            {
                                if(dtProductos.get(i).getProd_cve_n().equals(r.getProd_cve_n()))
                                {
                                    ri= dtProductos.get(i);
                                }
                            }

                            break;
                    }
                }

            }



        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_ped6), e.getMessage() );
        }
    }

    private void ListarEnvase(int lb)
    {
        String qry = "select p.prod_cve_n,p.prod_sku_str,p.prod_desc_str,lp.lpre_precio_n,(coalesce(iv.inv_inicial_n,0) " +
                "+coalesce(iv.inv_recarga_n,0)-coalesce(iv.inv_devuelto_n,0)-coalesce(iv.inv_vendido_n,0) " +
                "-coalesce(iv.inv_prestado_n,0)+coalesce(iv.inv_recuperado_n,0)) prod_cantiv_n, " +
                "0 Entregado,0 Recibido,0 Regalo,0 Venta,coalesce(lpb.lpre_precio_n,0) lpre_base_n " +
                "from precioproductos lp inner join productos p on lp.prod_cve_n=p.prod_cve_n " +
                "left join inventario iv on p.prod_cve_n=iv.prod_cve_n and iv.rut_cve_n={2} " +
                "left join precioproductos lpb on lp.prod_cve_n=lpb.prod_cve_n  and lpb.lpre_cve_n={3} " +
                "where lp.lpre_cve_n={0} and cat_cve_n={1}";

        String con = string.formatSql2(qry, String.valueOf(lpre) , String.valueOf(catenv) ,conf.getRutaStr(), String.valueOf(lb) );

        String json = BaseLocal.Select(con,getApplicationContext());

        ArrayList<DataTableLC.EnvasesPed> envases = ConvertirRespuesta.getEnvasesPedJson(json);

        Log.d("salida","LISTAR ENVASE CON: "+ con);
        Log.d("salida","LISTAR ENVASE JSON: "+ json);


    }

    private ArrayList<DataTableLC.Promociones5> ObtenerPromociones5(String nivel, String lpre, String segmento, String giro, String tcli, String nivelhl)
    {
        try
        {
            if (segmento == null || segmento.isEmpty())
                segmento = "null";
            if (giro == null || giro.isEmpty())
                giro = "null";
            if (tcli == null || tcli.isEmpty())
                tcli = "null";

            String json= BaseLocal.Select(string.formatSql2("select p.*,coalesce(nvc.nvc_nivel_n,0) nvc_nivel_n, " +
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
                            nivel, lpre, segmento, giro, tcli,nivelhl),getApplicationContext());

            ArrayList<DataTableLC.Promociones5> promociones = ConvertirRespuesta.getPromociones5Json(json);

            return promociones;


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
}
