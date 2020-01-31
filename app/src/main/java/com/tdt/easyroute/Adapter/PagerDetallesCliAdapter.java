package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Fragments.Pedidos.DetallesCliente.DatosdetFragment;
import com.tdt.easyroute.Fragments.Pedidos.DetallesCliente.DomiciliodetFragment;

public class PagerDetallesCliAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerDetallesCliAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DatosdetFragment tab1 = new DatosdetFragment();
                return tab1;
            case 1:
                DomiciliodetFragment tab2 = new DomiciliodetFragment();
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
