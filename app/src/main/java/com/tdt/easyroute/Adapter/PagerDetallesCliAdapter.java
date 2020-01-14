package com.tdt.easyroute.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tdt.easyroute.Fragments.Pedidos.DetallesCliente.DatosdetFragment;
import com.tdt.easyroute.Fragments.Pedidos.DetallesCliente.DomiciliodetFragment;
import com.tdt.easyroute.Fragments.Pedidos.DetallesCliente.MapadetFragment;
import com.tdt.easyroute.Fragments.Pedidos.DetallesCliente.NavegadetFragment;
import com.tdt.easyroute.Ventanas.Configuracion.ServidorFragment;
import com.tdt.easyroute.Ventanas.Configuracion.ServidorrutaFragment;
import com.tdt.easyroute.Ventanas.Configuracion.UtilidadFragment;

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
            case 2:
                MapadetFragment tab3 = new MapadetFragment();
                return tab3;
            case 3:
                NavegadetFragment tab4 = new NavegadetFragment();
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
