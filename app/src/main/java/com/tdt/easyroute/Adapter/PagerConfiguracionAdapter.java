package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.Configuracion.ServidorFragment;
import com.tdt.easyroute.Ventanas.Configuracion.ServidorrutaFragment;
import com.tdt.easyroute.Ventanas.Configuracion.UtilidadFragment;

public class PagerConfiguracionAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    boolean tabs[];
    public PagerConfiguracionAdapter(FragmentManager fm, int NumOfTabs, boolean[]tab) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        tabs=tab;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(tabs[0]) {
                    ServidorrutaFragment tab1 = new ServidorrutaFragment();
                    return tab1;
                }
            case 1:
                if(tabs[1]) {
                    ServidorFragment tab2 = new ServidorFragment();
                    return tab2;
                }
            case 2:
                if(tabs[2]) {
                    UtilidadFragment tab3 = new UtilidadFragment();
                    return tab3;
                }
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
