package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.InicioDia.DatosFragment;
import com.tdt.easyroute.Ventanas.InicioDia.GeneralesFragment;
import com.tdt.easyroute.Ventanas.Ventas.Credito.AbonoscredFragment;
import com.tdt.easyroute.Ventanas.Ventas.Credito.EnvasecredFragment;
import com.tdt.easyroute.Ventanas.Ventas.Credito.LiquidocreFragment;

public class PagerCreditoAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerCreditoAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LiquidocreFragment tab1 = new LiquidocreFragment();
                    return tab1;
            case 1:
                EnvasecredFragment tab2 = new EnvasecredFragment();
                    return tab2;
            case 2:
                AbonoscredFragment tab3 = new AbonoscredFragment();
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
