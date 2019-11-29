package com.tdt.easyroute.Fragments.Clientes.Cartera;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerCarteraAdapter;
import com.tdt.easyroute.R;

public class CarteraFragment extends Fragment {

    public ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartera, container, false);

        //CONFIGURACION DE LAS TABS
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Clientes"));
        tabLayout.addTab(tabLayout.newTab().setText("Saldos"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) view.findViewById(R.id.pager);

        final PagerCarteraAdapter adapter = new PagerCarteraAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                //Log.d("salida", "TAB SELECCIONADA: "+ tab.getText().toString() );
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //TERMINA CONFIGURACION DE LAS TABS


        return view;
    }

    public void goClientesFragment()
    {
        viewPager.setCurrentItem(0);
    }

    public void goSaldosFragment()
    {
        viewPager.setCurrentItem(1);
    }

}
