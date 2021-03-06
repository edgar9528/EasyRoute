package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.Clientes.Cartera.ClientesCarFragment;
import com.tdt.easyroute.Ventanas.Clientes.Cartera.SaldosFragment;

public class PagerCarteraAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerCarteraAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ClientesCarFragment tab1 = new ClientesCarFragment();
                    return tab1;
            case 1:
                SaldosFragment tab2 = new SaldosFragment();
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
