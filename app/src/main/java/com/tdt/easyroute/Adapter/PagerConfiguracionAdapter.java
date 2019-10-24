package com.tdt.easyroute.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.Configuracion.ServidorFragment;
import com.tdt.easyroute.Ventanas.Configuracion.ServidorrutaFragment;
import com.tdt.easyroute.Ventanas.Configuracion.UtilidadFragment;

public class PagerConfiguracionAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerConfiguracionAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ServidorrutaFragment tab1 = new ServidorrutaFragment();
                return tab1;
            case 1:
                ServidorFragment tab2 = new ServidorFragment();
                return tab2;
            case 2:
                UtilidadFragment tab3 = new UtilidadFragment();
                return tab3;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
