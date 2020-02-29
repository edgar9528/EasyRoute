package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.Ventas.Venta.FamiliavenFragment;
import com.tdt.easyroute.Ventanas.Ventas.Venta.VentavenFragment;

public class PagerVentaAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerVentaAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FamiliavenFragment tab1 = new FamiliavenFragment();
                    return tab1;
            case 1:
                VentavenFragment tab4 = new VentavenFragment();
                return tab4;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
