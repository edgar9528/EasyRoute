package com.tdt.easyroute.Adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.Reportes.Venta.ProductosvenFragment;
import com.tdt.easyroute.Ventanas.Reportes.Venta.TotalFragment;

public class PagerReporteventaAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerReporteventaAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        try {
            switch (position) {
                case 0:
                    ProductosvenFragment tab1 = new ProductosvenFragment();
                    return tab1;
                case 1:
                    TotalFragment tab2 = new TotalFragment();
                    return tab2;
                default:
                    return null;
            }
        }catch (Exception e)
        {
            return  null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
