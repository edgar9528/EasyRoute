package com.tdt.easyroute.Ventanas.Preventa.Venta;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerPreventaAdapter;
import com.tdt.easyroute.Clases.ViewPagerNonSwipable;
import com.tdt.easyroute.R;


public class VentamainFragment extends Fragment {

    private ViewPagerNonSwipable viewPager;

    public VentamainFragment() {
        // Required empty public constructor
    }

    public static VentamainFragment newInstance(String param1, String param2) {
        VentamainFragment fragment = new VentamainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ventamain, container, false);

        //CONFIGURACION DE LAS TABS
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_sug3) ));
        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_ped2) ));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager =  view.findViewById(R.id.pager);

        final PagerPreventaAdapter adapter = new PagerPreventaAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

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

    public void goVenta()
    {
        viewPager.setCurrentItem(1);
    }


}
