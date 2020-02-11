package com.tdt.easyroute.Ventanas.InicioDia;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tdt.easyroute.Adapter.PagerIniciodiaAdapter;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.R;

public class StartdayFragment extends Fragment {

    public StartdayFragment() {
        // Required empty public constructor
    }

    private MainActivity mainActivity;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_startday, container, false);
        Log.d("salida","stardayFragment");

        mainActivity = (MainActivity) getActivity();

        //CONFIGURACION DE LAS TABS
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_iniDia1) ));
        tabLayout.addTab(tabLayout.newTab().setText( getResources().getString(R.string.tl_iniDia2) ));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager =  view.findViewById(R.id.pager);

        final PagerIniciodiaAdapter adapter = new PagerIniciodiaAdapter(getChildFragmentManager(), tabLayout.getTabCount());

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mainActivity.validarMenu();
        Log.d("salida","ENTRO ACTUALIZAR MENU");

    }

    public void goDatos()
    {
        viewPager.setCurrentItem(1);
    }

    public void goGenerales()
    {
        viewPager.setCurrentItem(0);
    }

}
