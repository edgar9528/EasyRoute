package com.tdt.easyroute.Helper;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tdt.easyroute.BuildConfig;
import com.tdt.easyroute.Fragments.FragmentCerrar;
import com.tdt.easyroute.Fragments.InicioDia.StartdayFragment;
import com.tdt.easyroute.Interface.NavigationManager;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Configuracion.ConfiguracionActivity;

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager mInstance;
    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;
    private Activity activity;
    private Usuario usuario;

    public static FragmentNavigationManager getmInstance(MainActivity mainActivity, Activity act, Usuario user)
    {

        if(mInstance== null)
        {
            mInstance= new FragmentNavigationManager();
        }
        mInstance.configure(mainActivity,act,user);
        return mInstance;
    }

    private void configure(MainActivity mainActivity,Activity act,Usuario user) {
        mainActivity=mainActivity;
        activity=act;
        usuario=user;
        mFragmentManager = mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {

        FragmentManager fm = mFragmentManager;
        FragmentTransaction ft = null;

        boolean esFragment=true;
        
        switch (title)
        {
            case "Inicio de día | Inicio":
                StartdayFragment startdayFragment= new StartdayFragment();
                ft= fm.beginTransaction().replace(R.id.container, startdayFragment);
                break;

            case "Catálogos | Configuración":
                esFragment=false;
                Intent intent = new Intent(activity, ConfiguracionActivity.class);
                intent.putExtra("catalogos", true);
                intent.putExtra("usuario", usuario);
                activity.startActivity(intent);
                break;
            default:
                 ft= fm.beginTransaction().replace(R.id.container,FragmentCerrar.newInstance(title));
                 break;
        }

        if(esFragment)
        {

            ft.addToBackStack(null);
            if (false || !BuildConfig.DEBUG)
                ft.commitAllowingStateLoss();
            else
                ft.commit();
            fm.executePendingTransactions();
        }

    }

}
