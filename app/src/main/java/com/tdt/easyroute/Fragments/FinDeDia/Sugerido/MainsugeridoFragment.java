package com.tdt.easyroute.Fragments.FinDeDia.Sugerido;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerSugeridoAdapter;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;


public class MainsugeridoFragment extends Fragment {

    private static Usuario user;

    public static MainsugeridoFragment newInstance(Usuario u) {
        MainsugeridoFragment fragment = new MainsugeridoFragment();
        Bundle args = new Bundle();
        user = u;
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mainsugerido, container, false);

        //CONFIGURACION DE LAS TABS
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Familia"));
        tabLayout.addTab(tabLayout.newTab().setText("Presentación"));
        tabLayout.addTab(tabLayout.newTab().setText("Producto"));
        tabLayout.addTab(tabLayout.newTab().setText("Sugerido"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);

        final PagerSugeridoAdapter adapter = new PagerSugeridoAdapter(getChildFragmentManager(), tabLayout.getTabCount());

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

}
