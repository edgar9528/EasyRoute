package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.FinDeDia.Sugerido.FamiliaFragment;
import com.tdt.easyroute.Ventanas.FinDeDia.Sugerido.PresentacionFragment;
import com.tdt.easyroute.Ventanas.FinDeDia.Sugerido.ProductoFragment;
import com.tdt.easyroute.Ventanas.FinDeDia.Sugerido.SugeridoFragment;

public class PagerSugeridoAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerSugeridoAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                    FamiliaFragment tab1 = new FamiliaFragment();
                    return tab1;
            case 1:
                    PresentacionFragment tab2 = new PresentacionFragment();
                    return tab2;
            case 2:
                ProductoFragment tab3 = new ProductoFragment();
                return tab3;
            case 3:
                SugeridoFragment tab4 = new SugeridoFragment();
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
