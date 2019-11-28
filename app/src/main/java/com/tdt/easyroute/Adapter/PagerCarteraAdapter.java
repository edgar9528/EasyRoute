package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Fragments.Clientes.Cartera.ClientesFragment;
import com.tdt.easyroute.Fragments.Clientes.Cartera.SaldosFragment;
import com.tdt.easyroute.Fragments.InicioDia.DatosFragment;
import com.tdt.easyroute.Fragments.InicioDia.GeneralesFragment;

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
                ClientesFragment tab1 = new ClientesFragment();
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
