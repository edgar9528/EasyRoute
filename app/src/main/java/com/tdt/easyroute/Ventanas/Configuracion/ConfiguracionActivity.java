package com.tdt.easyroute.Ventanas.Configuracion;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tdt.easyroute.Adapter.PagerConfiguracionAdapter;
import com.tdt.easyroute.R;

public class ConfiguracionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        this.setTitle("Configuración");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Servidor, ruta e impresor"));
        tabLayout.addTab(tabLayout.newTab().setText("Servidor"));
        tabLayout.addTab(tabLayout.newTab().setText("Utilidad"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerConfiguracionAdapter adapter = new PagerConfiguracionAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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




    }




}
