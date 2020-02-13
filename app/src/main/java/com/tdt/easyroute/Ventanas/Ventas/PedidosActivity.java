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

    DataTableLC.DtCliVentaNivel rc;
    boolean RutaMayorista = false;
    boolean RutaPromoCe = false;

    int catenv= -1;
    int catcvz = -1;
    int catvasos = -1;
    int cathielo = -1;

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
        rc = ObtenerCliente();

        catenv = ObtenerIdCat("ENVASE");
        catcvz = ObtenerIdCat("CERVEZA");
        catvasos = ObtenerIdCat("VASOS");
        cathielo = ObtenerIdCat("HIELO");


    }

    private DataTableLC.DtCliVentaNivel ObtenerCliente()
    {
        DataTableLC.DtCliVentaNivel cli=null;

        try
        {
            String json = BaseLocal.Select(string.formatSql(Querys.ClientesVentaMes.SelClienteConVtaMesNivel, noCli), getApplicationContext());
            ArrayList<DataTableLC.DtCliVentaNivel> cliVenta = ConvertirRespuesta.getDtCliVentaNivelJson(json);

            String mayorista = BaseLocal.SelectDato(string.formatSql("select rut_mayorista_n from rutas where rut_cve_n={0}", rc.getRut_cve_n()), getApplicationContext());
            String promoce = BaseLocal.SelectDato(string.formatSql("select rut_promoce_n from rutas where rut_cve_n={0}", rc.getRut_cve_n()), getApplicationContext());

            RutaMayorista = mayorista.equals("1");
            RutaPromoCe = promoce.equals("1");

            if (cliVenta != null)
                cli = cliVenta.get(0);

            return cli;

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_cargarInfo), e.getMessage());
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
