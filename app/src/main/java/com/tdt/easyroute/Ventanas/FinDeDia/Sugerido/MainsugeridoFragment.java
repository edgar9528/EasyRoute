package com.tdt.easyroute.Ventanas.FinDeDia.Sugerido;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Adapter.PagerSugeridoAdapter;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.SugeridoVM;

public class MainsugeridoFragment extends Fragment {

    private static Usuario user;
    private ViewPager viewPager;

    private SugeridoVM sugeridoVM;

    private MainActivity mainActivity;

    public static MainsugeridoFragment newInstance(Usuario u) {
        MainsugeridoFragment fragment = new MainsugeridoFragment();
        Bundle args = new Bundle();
        user = u;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.sugerido_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        sugeridoVM = ViewModelProviders.of ( this ).get(SugeridoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mainsugerido, container, false);

        try {

            mainActivity = (MainActivity) getActivity();

            //CONFIGURACION DE LAS TABS
            TabLayout tabLayout = view.findViewById(R.id.tab_layout);

            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tl_sug1)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tl_sug2)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tl_sug3)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tl_sug4)));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            viewPager = view.findViewById(R.id.pager);

            final PagerSugeridoAdapter adapter = new PagerSugeridoAdapter(getChildFragmentManager(), tabLayout.getTabCount());

            viewPager.setOffscreenPageLimit(3);

            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            //TERMINA CONFIGURACION DE LAS TABS


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    verificarInicio();
                }
            }, 150);

            sugeridoVM.setOpcion("Recibiendo información");

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug1),e.getMessage());
        }

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.op_guardar:
                sugeridoVM.setOpcion("guardar");
                return true;
            case R.id.op_enviar:
                sugeridoVM.setOpcion("enviar");
                return true;
            case R.id.op_imprimir:
                sugeridoVM.setOpcion("imprimir");
                return true;
            case R.id.op_salir:
                sugeridoVM.setOpcion("salir");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void verificarInicio()
    {
        try {
            Configuracion conf;
            conf = Utils.ObtenerConf(getActivity().getApplication());

            if (conf.getPreventa() == 2) {
                Toast.makeText(getContext(), getString(R.string.err_sug6), Toast.LENGTH_LONG).show();
                Utils.RegresarInicio(getActivity());
            }
            if (conf.getPreventa() == 1) {
                Toast.makeText(getContext(), getString(R.string.err_sug7), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug1),e.getMessage());
        }
    }

    public Usuario getUserMainSugerido()
    {
        return user;
    }

    public void goFamilia()
    {
        viewPager.setCurrentItem(0);
    }

    public void goPresentacion()
    {
        viewPager.setCurrentItem(1);
    }

    public void goProducto()
    {
        viewPager.setCurrentItem(2);
    }

    public void goSugerido()
    {
        viewPager.setCurrentItem(3);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mainActivity.validarMenu();
        Log.d("salida","ENTRO ACTUALIZAR MENU");

    }

}
