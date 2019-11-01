package com.tdt.easyroute.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Fragments.FragmentCerrar;
import com.tdt.easyroute.Fragments.InicioDia.DatosFragment;
import com.tdt.easyroute.Fragments.InicioDia.GeneralesFragment;
import com.tdt.easyroute.Ventanas.Configuracion.ServidorFragment;
import com.tdt.easyroute.Ventanas.Configuracion.ServidorrutaFragment;
import com.tdt.easyroute.Ventanas.Configuracion.UtilidadFragment;

public class PagerIniciodiaAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerIniciodiaAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GeneralesFragment tab1 = new GeneralesFragment();
                    return tab1;
            case 1:
                DatosFragment tab2 = new DatosFragment();
                    return tab2;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
