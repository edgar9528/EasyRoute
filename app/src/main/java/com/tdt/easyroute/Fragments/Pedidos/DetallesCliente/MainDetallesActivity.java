package com.tdt.easyroute.Fragments.Pedidos.DetallesCliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerDetallesCliAdapter;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class MainDetallesActivity extends AppCompatActivity {

    DataTableLC.DtCliVenta cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detalles);

        try
        {
            this.setTitle(getString(R.string.title_detalles));

            if(getSupportActionBar()!=null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            Intent intent = getIntent();
            cliente = (DataTableLC.DtCliVenta) intent.getSerializableExtra("cliente");

            Log.d("salida","Detalles de los clientes: "+cliente.getCli_cve_n());

            //CONFIGURACION DE LAS TABS
            TabLayout tabLayout =  findViewById(R.id.tab_layout);

            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tl_datos)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tl_domicilio)));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager =  findViewById(R.id.pager);
            final PagerDetallesCliAdapter adapter = new PagerDetallesCliAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount());

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

            //TERMINA CONFIGURACION DE LAS TABS
        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.err_det1),e.getMessage());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public DataTableLC.DtCliVenta getCliente()
    {
        return cliente;
    }

}
