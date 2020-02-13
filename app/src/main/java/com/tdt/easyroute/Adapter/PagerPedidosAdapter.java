package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Ventanas.Configuracion.ServidorFragment;
import com.tdt.easyroute.Ventanas.Configuracion.ServidorrutaFragment;
import com.tdt.easyroute.Ventanas.Configuracion.UtilidadFragment;
import com.tdt.easyroute.Ventanas.Ventas.Credito.CreditomainFragment;
import com.tdt.easyroute.Ventanas.Ventas.Envase.EnvaseFragment;
import com.tdt.easyroute.Ventanas.Ventas.Pago.PagoFragment;
import com.tdt.easyroute.Ventanas.Ventas.Venta.VentamainFragment;

public class PagerPedidosAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    public PagerPedidosAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                CreditomainFragment tab1 = new CreditomainFragment();
                return tab1;
            case 1:
                VentamainFragment tab2 = new VentamainFragment();
                return tab2;
            case 2:
                EnvaseFragment tab3 = new EnvaseFragment();
                return tab3;
            case 3:
                PagoFragment tab4 = new PagoFragment();
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
