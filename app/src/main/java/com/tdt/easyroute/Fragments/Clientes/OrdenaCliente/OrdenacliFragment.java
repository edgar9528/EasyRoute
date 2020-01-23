package com.tdt.easyroute.Fragments.Clientes.OrdenaCliente;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerOrdenacliAdapter;
import com.tdt.easyroute.R;


public class OrdenacliFragment extends Fragment {

    public static OrdenacliFragment newInstance() {
        OrdenacliFragment fragment = new OrdenacliFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ordenacli, container, false);



        //CONFIGURACION DE LAS TABS
        TabLayout tabLayout =  view.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(  getResources().getString(R.string.tl_orde1)  ));
        tabLayout.addTab(tabLayout.newTab().setText(  getResources().getString(R.string.tl_orde2) ));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager =  view.findViewById(R.id.pager);

        final PagerOrdenacliAdapter adapter = new PagerOrdenacliAdapter(getChildFragmentManager(), tabLayout.getTabCount());

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

    public void goClientesDiaFragment()
    {
        viewPager.setCurrentItem(0);
    }

}
