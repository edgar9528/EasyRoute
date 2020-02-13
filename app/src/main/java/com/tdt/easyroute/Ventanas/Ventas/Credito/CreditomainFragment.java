package com.tdt.easyroute.Ventanas.Ventas.Credito;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerCreditoAdapter;
import com.tdt.easyroute.R;

public class CreditomainFragment extends Fragment {

    public CreditomainFragment() {
        // Required empty public constructor
    }

    public static CreditomainFragment newInstance(String param1, String param2) {
        CreditomainFragment fragment = new CreditomainFragment();
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_creditomain, container, false);


        //CONFIGURACION DE LAS TABS
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_cred1) ));
        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_cred2) ));
        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_cred3) ));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager =  view.findViewById(R.id.pager);

        final PagerCreditoAdapter adapter = new PagerCreditoAdapter(getChildFragmentManager(), tabLayout.getTabCount());

        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
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


}
