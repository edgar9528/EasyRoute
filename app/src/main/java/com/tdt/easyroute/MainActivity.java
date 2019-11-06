package com.tdt.easyroute;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Adapter.CustomExpandableListAdapter;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Helper.FragmentNavigationManager;
import com.tdt.easyroute.Interface.NavigationManager;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.Model.Variables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String,List<String>> lstChild;
    private NavigationManager navigationManager;
    List<String> menuMostrar;
    private TextView tv_nombre,tv_ruta;
    Usuario usuario;

    private Variables.Startday varStarday = new Variables.Startday();

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿Desea salir de la app?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        dialogo1.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Log.d("salida","ENTRO MAIN ACTIVITY");
            Intent intent = getIntent();
            usuario = (Usuario) intent.getSerializableExtra("usuario");


            //init view
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            expandableListView= findViewById(R.id.navList);
            navigationManager= FragmentNavigationManager.getmInstance(this,this,usuario);


            View listHeaderView = getLayoutInflater().inflate(R.layout.nav_header,null,false);
            expandableListView.addHeaderView(listHeaderView);

            validarInicio();
            inicializar();
            validarVacioMenu();

            addDrawersItem();
            setupDrawer();

            if(savedInstanceState== null)
            {
                selectFirsItemAsDefault();
            }

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);


        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Eror: "+e.toString(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.toString());
        }

    }


    //CONFIGURACION DEL MENU

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirsItemAsDefault() {
        if(navigationManager!= null)
        {
            String firtsItem = "Inicio de día | Inicio";
            navigationManager.showFragment(firtsItem);
            getSupportActionBar().setTitle(firtsItem);
        }
    }

    private void addDrawersItem() {

        //ACCIONES AL ABRIR, CERRAR O INTERACTUAR CON MENÚ

        adapter = new CustomExpandableListAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                //set title for toolbar
                //getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString());
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                //getSupportActionBar().setTitle("Inicio");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String selectedItem = ( (List) (lstChild.get(lstTitle.get(groupPosition))) )
                        .get(childPosition).toString();

                String clave= lstTitle.get(groupPosition)  +" | "+selectedItem;
                Log.d("Salida", clave);

                getSupportActionBar().setTitle(clave);

                navigationManager.showFragment(clave);

                mDrawerLayout.closeDrawer(GravityCompat.START);

                expandableListView.collapseGroup(groupPosition);

                return false;
            }
        });

    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("Inicio2");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void validarVacioMenu() {

        //OPCIONES QUE SE MOSTRARAN EN EL MENU


        List<String> title = Arrays.asList("Inicio de día", "Inventario", "Pedidos", "Entregas", "Reportes", "Clientes", "Fin de día", "Catálogos");

        List<String> iniciodia = Arrays.asList("Inicio");
        List<String> inventario = Arrays.asList("Inventario", "Carga inicial", "Recarga", "Devoluciones", "Descarga");
        List<String> pedidos = Arrays.asList("Clientes");
        List<String> entregas = Arrays.asList("Consigna", "Pedido", "Devolución");
        List<String> reportes = Arrays.asList("Arqueo", "Ventas día");
        List<String> clientes = Arrays.asList("Cartera", "Ord. Clientes", "Busq. Clientes");
        List<String> findia = Arrays.asList("Sugerido", "Transmitir", "Borrar datos", "Fin de ventas");
        List<String> catalogos = Arrays.asList("Configuración");

        lstChild = new LinkedHashMap<>();
        lstChild.put(title.get(0), iniciodia);
        lstChild.put(title.get(1), inventario);
        lstChild.put(title.get(2), pedidos);
        lstChild.put(title.get(3), entregas);
        lstChild.put(title.get(4), reportes);
        lstChild.put(title.get(5), clientes);
        lstChild.put(title.get(6), findia);
        lstChild.put(title.get(7), catalogos);

        lstTitle = new ArrayList<>(lstChild.keySet());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    //TERMINA CONFIGURACION DEL MENU


    public Usuario getUsuario()
    {
        return usuario;
    }

    public void inicializar()
    {
        try{

            tv_nombre = findViewById(R.id.textViewNombre);
            tv_ruta = findViewById(R.id.textViewRuta);

            tv_nombre.setText(usuario.getNombre()+" "+usuario.getAppat());


            String rutaCve = Utils.LeefConfig("ruta",getApplication());
            String rutaDes = BaseLocal.SelectDato(string.formatSql("select rut_desc_str from rutas where rut_cve_n={0}",rutaCve),getApplicationContext());

            if(rutaDes!=null)
                tv_ruta.setText("Ruta: "+rutaDes);
            else
                tv_ruta.setText("SIN RUTA");

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }



    }

    public void validarInicio()
    {
        //menuMostrar = Arrays.asList("Inicio de día", "Inventario", "Pedidos", "Entregas", "Reportes", "Clientes", "Fin de día", "Catálogos");
        try
        {
            Configuracion conf = null;
            conf = Utils.ObtenerConf(getApplication());

            if(conf!=null)
            {
                menuMostrar = new ArrayList<>();
                if(conf.getPreventa()!=1)
                    menuMostrar.add("Inventario");

                menuMostrar.add( "Fin de día");
                menuMostrar.add( "Reportes" );

                if(!conf.isDescarga())
                    menuMostrar.add( "Pedidos" );
            }
            else
            {
                menuMostrar = Arrays.asList("Inicio de día","Reportes");
            }


        }catch (Exception e)
        {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public Variables.Startday getVarStarday() {
        return varStarday;
    }

    public void setVarStarday(Variables.Startday varStarday) {
        this.varStarday = varStarday;
    }
}
