package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.Preventa.Envase.EnvaseFragment;
import com.tdt.easyroute.Ventanas.Preventa.Pago.AbonosFragment;
import com.tdt.easyroute.Ventanas.Preventa.Pago.CreditosFragment;
import com.tdt.easyroute.Ventanas.Preventa.Pago.PagoFragment;
import com.tdt.easyroute.Ventanas.Preventa.Venta.VentamainFragment;

public class PagerPrevPedidosAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    boolean removerVenta;
    public PagerPrevPedidosAdapter(FragmentManager fm, int NumOfTabs, boolean removerVenta) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.removerVenta = removerVenta;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CreditosFragment tab1 = new CreditosFragment();
                return tab1;
            case 1:
                AbonosFragment tab2 = new AbonosFragment();
                return tab2;
            case 2:
                if(removerVenta)
                {
                    EnvaseFragment tab4 = new EnvaseFragment();
                    return tab4;
                }
                else
                {
                    VentamainFragment tab3 = new VentamainFragment();
                    return tab3;
                }
            case 3:
                if(removerVenta)
                {
                    PagoFragment tab5 = new PagoFragment();
                    return tab5;
                }
                else
                {
                    EnvaseFragment tab4 = new EnvaseFragment();
                    return tab4;
                }
            case 4:
                PagoFragment tab5 = new PagoFragment();
                return tab5;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}
