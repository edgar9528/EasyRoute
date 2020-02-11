package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.Clientes.OrdenaCliente.CdiaFragment;
import com.tdt.easyroute.Ventanas.Clientes.OrdenaCliente.CfueraFragment;

public class PagerOrdenacliAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerOrdenacliAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                    CdiaFragment tab1 = new CdiaFragment();
                    return tab1;
            case 1:
                    CfueraFragment tab2 = new CfueraFragment();
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
