package com.tdt.easyroute.Helper;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tdt.easyroute.BuildConfig;
import com.tdt.easyroute.Fragments.FragmentCerrar;
import com.tdt.easyroute.Interface.NavigationManager;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.R;

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;
    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;

    public static FragmentNavigationManager getmInstance(MainActivity mainActivity)
    {
        if(mInstance== null)
        {
            mInstance= new FragmentNavigationManager();
        }
        mInstance.configure(mainActivity);
        return mInstance;
    }

    private void configure(MainActivity mainActivity) {
        mainActivity=mainActivity;
        mFragmentManager = mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {

        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = null;
        
        switch (title)
        {
            case "Categorías|Principal":
            case "Categorías|Acción":
            case "Categorías|Documentales":
            case "Categorías|Fantasía":
            case "Categorías|Infantiles":
            case "Categorías|Romanticas":
            case "Categorías|Terror":
            case "Categorías|Otros":
                ft= fm.beginTransaction().replace(R.id.container, FragmentCerrar.newInstance(title));
                break;
             default:
                 ft= fm.beginTransaction().replace(R.id.container,FragmentCerrar.newInstance(title));
                 break;
        }

        ft.addToBackStack(null);
        if(false  || !BuildConfig.DEBUG)
            ft.commitAllowingStateLoss();
        else
            ft.commit();
        fm.executePendingTransactions();

    }

}
