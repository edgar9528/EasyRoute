package com.tdt.easyroute.Ventanas.Clientes.Cartera;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerCarteraAdapter;
import com.tdt.easyroute.R;

public class CarteraFragment extends Fragment {

    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cartera, container, false);

        //CONFIGURACION DE LAS TABS
        TabLayout tabLayout =  view.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_cart1) ));
        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_cart2) ));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = view.findViewById(R.id.pager);

        final PagerCarteraAdapter adapter = new PagerCarteraAdapter(getChildFragmentManager(), tabLayout.getTabCount());

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
