package com.tdt.easyroute.Ventanas.Configuracion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tdt.easyroute.Adapter.PagerConfiguracionAdapter;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.Permisos;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class ConfiguracionActivity extends AppCompatActivity {

    Configuracion conf=null;
    Usuario user;
    boolean admin=false;
    boolean catalogos = false;
    boolean mnGuardar = true;
    boolean crut = false;
    String cat="Empresas,Estatus,Roles,RolesModulos,Modulos,Usuarios,TipoRutas,Rutas";

    ArrayList<String> rutas_des;
    ArrayList<String> rutas_cla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        this.setTitle("Configuración");
        Intent intent = getIntent();
        user = (Usuario) intent.getSerializableExtra("usuario");
        admin = intent.getBooleanExtra("admin", false);
        catalogos = intent.getBooleanExtra("catalogos",false);

        if(!admin)
            mnGuardar=false;



        inicializar();


        //CONFIGURACION DE LAS TABS
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Servidor, ruta e impresor"));
        tabLayout.addTab(tabLayout.newTab().setText("Servidor"));
        tabLayout.addTab(tabLayout.newTab().setText("Utilidad"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerConfiguracionAdapter adapter = new PagerConfiguracionAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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


    }

    public void inicializar()
    {

        try {
            conf = Utils.ObtenerConf(getApplication());

            if (conf == null) {
                Log.d("salida", "conf es null");
            } else {
                Log.d("salida", "conf tiene valores");
            }

            //VERIFICA QUE SE HAYA INGRESADO CON UN USUARIO
            if(user!=null)
            {
                Log.d("salida","Usuario con datos");
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), getString(R.string.nombreBD), null, 1);
                SQLiteDatabase bd = databaseHelper.getReadableDatabase();

                if(conf!=null)
                {
                    Log.d("salida","conf con datos");
                    String cad = Querys.Trabajo.InsertBitacoraHHSesion;
                    String con = string.formatSql(cad, user.getUsuario(), string.formatSql("INICIO DE SESION V{0}",Utils.Version()), "ABRE CONFIGURACION", String.valueOf(conf.getRuta()) , "");

                    if(bd!=null)
                        bd.execSQL(con);
                }
                else
                {
                    Log.d("salida","conf nula");
                    String cad = Querys.Trabajo.InsertBitacoraHHSesion;
                    String con = string.formatSql(cad, user.getUsuario(), string.formatSql("INICIO DE SESION V{0} SIN RUTA",Utils.Version()), "ABRE CONFIGURACION", "0" , "");

                    if(bd!=null)
                        bd.execSQL(con);
                }
                bd.close();
            }


            //VERIFICA SI VIENE DEL MENU CATALOGOS
            if(catalogos)
            {
                mnGuardar = false;
                //BLOQUEAR TABS  ServidorRuta, Utilidad

                cat = "CondicionesVenta,Productos,Rutas,ListaPrecios,PrecioProductos,FormasPago,FrecuenciasVisita,Categorias,Familias,Presentaciones,Promociones,PromocionesKit";
                catalogos();
            }
            else
            {
                catalogos();
                cargarRutas();


                Permisos p = null;
                try
                {
                    if(user!=null)
                    {
                        for (int i = 0; i < user.getPermisos().size(); i++) {
                            if (user.getPermisos().get(i).getMod_desc_str().equals("CONFIGURACION")) {
                                p = user.getPermisos().get(i);
                                i = user.getPermisos().size();
                            }
                        }

                        if (p != null) {
                            if (p.getEscritura() > 0)
                                mnGuardar = true;
                            else
                                mnGuardar = false;
                        }
                    }
                }
                catch (Exception e)
                {
                    Log.d("salida","errorPermisos:"+e.toString());
                }


                if(user!=null)
                {
                    if(user.getNombrerol().equals("AUDITORIA"))
                    {
                        //BLOQUEA BOTON MENU -> SERVIDOR|SERVIDOR,RUTA
                    }
                }
                else
                {
                    if(!admin)
                    {
                        //BLOQUEA BOTON MENU -> SERVIDOR,RUTA|UTILIDAD
                        catalogos=true;
                    }
                }

            }


        }catch (Exception e)
        {
            Log.d("salida",e.toString());
        }

    }

    public void catalogos()
    {
        //lvCat.Items.Clear(); donde se muestran los catalogos
        String[] s = cat.split(",");

        //AGREGAR LOS ITEMS AL PANEL
        for (int i=0; i<s.length;i++)
        {
            Log.d("salida"," . "+s[i]+" . ");
        }

    }

    public void cargarRutas()
    {
        try{

            int k=-1;

            DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), getString(R.string.nombreBD), null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            String con = "Select rut_cve_n,rut_desc_str from rutas order by rut_orden_n";

            Cursor cursor = bd.rawQuery(con, null);

            if (cursor.getCount() >0) {

                rutas_des = new ArrayList<>();
                rutas_cla = new ArrayList<>();

                while (cursor.moveToNext()) {
                    rutas_des.add( cursor.getString(cursor.getColumnIndex("rut_desc_str")) );
                    rutas_cla.add( cursor.getString(cursor.getColumnIndex("rut_cve_n")) );
                }
            } else {
                Log.d("salida", "Cursor sin info");
            }

            bd.close();

        }
        catch (Exception e)
        {
            Log.d("salida",e.toString());
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
