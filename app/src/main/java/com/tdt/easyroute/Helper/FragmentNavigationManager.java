package com.tdt.easyroute.Helper;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tdt.easyroute.BuildConfig;
import com.tdt.easyroute.Fragments.Clientes.BuscarClientesActivity;
import com.tdt.easyroute.Fragments.Clientes.Cartera.CarteraFragment;
import com.tdt.easyroute.Fragments.Clientes.OrdenaCliente.OrdenacliFragment;
import com.tdt.easyroute.Fragments.FinDeDia.FindiaFragment;
import com.tdt.easyroute.Fragments.FinDeDia.Sugerido.MainsugeridoFragment;
import com.tdt.easyroute.Fragments.Inventario.DevolucionesFragment;
import com.tdt.easyroute.Fragments.Pedidos.PedidosFragment;
import com.tdt.easyroute.Fragments.PrincipalFragment;
import com.tdt.easyroute.Fragments.InicioDia.StartdayFragment;
import com.tdt.easyroute.Fragments.Inventario.Carga2Fragment;
import com.tdt.easyroute.Fragments.Inventario.InventarioFragment;
import com.tdt.easyroute.Fragments.Reportes.ArqueoFragment;
import com.tdt.easyroute.Fragments.Reportes.Venta.MainVentaFragment;
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
            case "Inicio | Inicio de día":
                StartdayFragment startdayFragment= new StartdayFragment();
                ft= fm.beginTransaction().replace(R.id.container, startdayFragment);
                break;

            case "Inventario | Carga inicial":
                ft= fm.beginTransaction().replace(R.id.container,Carga2Fragment.newInstance(false,false));
                break;

            case "Inventario | Recarga":
                ft= fm.beginTransaction().replace(R.id.container,Carga2Fragment.newInstance(true,false));
                break;

            case "Inventario | Inventario":
                ft= fm.beginTransaction().replace(R.id.container, InventarioFragment.newInstance(usuario,false));
                break;

            case "Inventario | Descarga":
                ft= fm.beginTransaction().replace(R.id.container, InventarioFragment.newInstance(usuario,true));
                break;

            case "Inventario | Devoluciones":
                ft= fm.beginTransaction().replace(R.id.container, DevolucionesFragment.newInstance(usuario));
                break;

            case "Pedidos | Clientes":
                ft= fm.beginTransaction().replace(R.id.container, PedidosFragment.newInstance());
                break;

            case "Reportes | Arqueo":
                ft= fm.beginTransaction().replace(R.id.container, ArqueoFragment.newInstance());
                break;

            case "Reportes | Ventas día":
                ft= fm.beginTransaction().replace(R.id.container, MainVentaFragment.newInstance());
                break;

            case "Clientes | Cartera":
                CarteraFragment carteraFragment = new CarteraFragment();
                ft= fm.beginTransaction().replace(R.id.container, carteraFragment);
                break;

            case "Clientes | Busq. Clientes":
                esFragment=false;
                Intent intent1 = new Intent(activity, BuscarClientesActivity.class);
                activity.startActivity(intent1);
                break;

            case "Clientes | Ord. Clientes":
                ft= fm.beginTransaction().replace(R.id.container, OrdenacliFragment.newInstance());
                break;

            case "Fin de día | Sugerido":
                ft= fm.beginTransaction().replace(R.id.container, MainsugeridoFragment.newInstance(usuario));
                break;

            case "Fin de día | Transmitir":
                ft= fm.beginTransaction().replace(R.id.container, FindiaFragment.newInstance(0));
                break;

            case "Fin de día | Borrar datos":
                ft= fm.beginTransaction().replace(R.id.container, FindiaFragment.newInstance(1));
                break;

            case "Fin de día | Fin de ventas":
                ft= fm.beginTransaction().replace(R.id.container, FindiaFragment.newInstance(2));
                break;
            case "Configuración | Catálogos":
                esFragment=false;
                Intent intent = new Intent(activity, ConfiguracionActivity.class);
                intent.putExtra("catalogos", true);
                intent.putExtra("usuario", usuario);
                activity.startActivity(intent);
                break;
            case "Configuración | Salir":
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
            if (false || !BuildConfig.DEBUG)
                ft.commitAllowingStateLoss();
            else
                ft.commit();
            fm.executePendingTransactions();
        }

    }

}
