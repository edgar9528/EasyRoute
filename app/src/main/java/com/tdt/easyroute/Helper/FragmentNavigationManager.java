package com.tdt.easyroute.Helper;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tdt.easyroute.BuildConfig;
import com.tdt.easyroute.Ventanas.Clientes.BuscarClientesActivity;
import com.tdt.easyroute.Ventanas.Clientes.Cartera.CarteraFragment;
import com.tdt.easyroute.Ventanas.Clientes.OrdenaCliente.OrdenacliFragment;
import com.tdt.easyroute.Ventanas.FinDeDia.FindiaFragment;
import com.tdt.easyroute.Ventanas.FinDeDia.Sugerido.MainsugeridoFragment;
import com.tdt.easyroute.Ventanas.Inventario.DevolucionesFragment;
import com.tdt.easyroute.Ventanas.Pedidos.ClientesPedFragment;
import com.tdt.easyroute.Ventanas.PrincipalFragment;
import com.tdt.easyroute.Ventanas.InicioDia.StartdayFragment;
import com.tdt.easyroute.Ventanas.Inventario.Carga2Fragment;
import com.tdt.easyroute.Ventanas.Inventario.InventarioFragment;
import com.tdt.easyroute.Ventanas.Reportes.ArqueoFragment;
import com.tdt.easyroute.Ventanas.Reportes.Venta.MainVentaFragment;
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
        this.mainActivity=mainActivity;
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
            case "0|1":
                StartdayFragment startdayFragment= new StartdayFragment();
                ft= fm.beginTransaction().replace(R.id.container, startdayFragment);
                break;

            case "1|0":
                ft= fm.beginTransaction().replace(R.id.container,Carga2Fragment.newInstance(false,false));
                break;

            case "1|1":
                ft= fm.beginTransaction().replace(R.id.container, InventarioFragment.newInstance(usuario,false));
                break;

            case "1|2":
                ft= fm.beginTransaction().replace(R.id.container,Carga2Fragment.newInstance(true,false));
                break;

            case "1|3":
                ft= fm.beginTransaction().replace(R.id.container, DevolucionesFragment.newInstance(usuario));
                break;

            case "1|4":
                ft= fm.beginTransaction().replace(R.id.container, InventarioFragment.newInstance(usuario,true));
                break;


            case "2|0":
                ft= fm.beginTransaction().replace(R.id.container, ClientesPedFragment.newInstance());
                break;

            case "4|0":
                ft= fm.beginTransaction().replace(R.id.container, ArqueoFragment.newInstance());
                break;

            case "4|1":
                ft= fm.beginTransaction().replace(R.id.container, MainVentaFragment.newInstance());
                break;

            case "5|0":
                CarteraFragment carteraFragment = new CarteraFragment();
                ft= fm.beginTransaction().replace(R.id.container, carteraFragment);
                break;

            case "5|1":
                ft= fm.beginTransaction().replace(R.id.container, OrdenacliFragment.newInstance());
                break;

            case "5|2":
                esFragment=false;
                Intent intent1 = new Intent(activity, BuscarClientesActivity.class);
                activity.startActivity(intent1);
                break;

            case "6|0":
                ft= fm.beginTransaction().replace(R.id.container, MainsugeridoFragment.newInstance(usuario));
                break;

            case "6|1":
                ft= fm.beginTransaction().replace(R.id.container, FindiaFragment.newInstance(0));
                break;

            case "6|2":
                ft= fm.beginTransaction().replace(R.id.container, FindiaFragment.newInstance(1));
                break;

            case "6|3":
                ft= fm.beginTransaction().replace(R.id.container, FindiaFragment.newInstance(2));
                break;
            case "7|0":
                esFragment=false;
                Intent intent = new Intent(activity, ConfiguracionActivity.class);
                intent.putExtra("catalogos", true);
                intent.putExtra("usuario", usuario);
                activity.startActivity(intent);
                break;
            case "7|1":
                esFragment=false;
                mainActivity.cerrarSesion();
                break;

            default:
                PrincipalFragment fragmentPrincipal = new PrincipalFragment();
                ft= fm.beginTransaction().replace(R.id.container,fragmentPrincipal);
                break;
        }

        if(esFragment)
        {
            ft.addToBackStack(null);
            if (!BuildConfig.DEBUG)
                ft.commitAllowingStateLoss();
            else
                ft.commit();
            fm.executePendingTransactions();
        }

    }

}
