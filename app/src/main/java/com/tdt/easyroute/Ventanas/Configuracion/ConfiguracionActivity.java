package com.tdt.easyroute.Ventanas.Configuracion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tdt.easyroute.Adapter.PagerConfiguracionAdapter;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableWS;
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
    boolean tabs[];
    String cat="Empresas,Estatus,Roles,RolesModulos,Modulos,Usuarios,TipoRutas,Rutas";

    ArrayList<DataTableWS.Ruta> lista_rutas;
    ArrayList<String> lista_catalogos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        this.setTitle("Configuración");

        try {

            Intent intent = getIntent();
            user = (Usuario) intent.getSerializableExtra("usuario");
            admin = intent.getBooleanExtra("admin", false);
            catalogos = intent.getBooleanExtra("catalogos", false);
            tabs = new boolean[3];
            tabs[0] = true;
            tabs[1] = true;
            tabs[2] = true;

            if (!admin)
                mnGuardar = false;


            Log.d("salida", "es admin:" + admin);


            inicializar();


            //CONFIGURACION DE LAS TABS
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

            if (tabs[0])
                tabLayout.addTab(tabLayout.newTab().setText("Servidor, ruta e impresor"));
            if (tabs[1])
                tabLayout.addTab(tabLayout.newTab().setText("Servidor"));
            if (tabs[2])
                tabLayout.addTab(tabLayout.newTab().setText("Utilidad"));

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
            final PagerConfiguracionAdapter adapter = new PagerConfiguracionAdapter
                    (getSupportFragmentManager(), tabLayout.getTabCount(), tabs);

            viewPager.setOffscreenPageLimit(3);

            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                    //Log.d("salida", "TAB SELECCIONADA: "+ tab.getText().toString() );
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            //TERMINA CONFIGURACION DE LAS TABS

        }catch (Exception e)
        {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.toString());
        }


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
                tabs[0]=false;
                tabs[2]=false;

                cat = "CondicionesVenta,Productos,Rutas,ListaPrecios,PrecioProductos,FormasPago,FrecuenciasVisita,Categorias,Familias,Presentaciones,Promociones,PromocionesKit";

                catalogos();
            }
            else
            {
                catalogos();
                obtenerRutas();

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
                            {
                                mnGuardar = true;
                                Log.d("salida","usuario con permiso de configuracion");
                            }
                            else
                            {
                                mnGuardar = false;
                                Log.d("salida","usuario sin permiso de configuracion");
                            }
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
                        tabs[0]=false;
                        tabs[1]=false;
                        Log.d("salida","AUDITORIA");
                    }
                }
                else
                {
                    if(!admin)
                    {
                        //BLOQUEA BOTON MENU -> SERVIDOR,RUTA|UTILIDAD

                        tabs[0]=false;
                        tabs[2]=false;

                        catalogos=true;
                        Log.d("salida","NO ES ADMIN");
                    }
                    else
                    {
                        Log.d("salida","ES ADMINISTRADOR");
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
        lista_catalogos = new ArrayList<>();

        //AGREGAR LOS CATALOGOS AL ARRAY
        for (int i=0; i<s.length;i++)
        {
            lista_catalogos.add(s[i]);
        }

        Log.d("salida","Catalogos agregados");

    }

    public void obtenerRutas()
    {
        try{

            DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), getString(R.string.nombreBD), null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            String con = "Select rut_cve_n,rut_desc_str from rutas order by rut_orden_n";

            Cursor cursor = bd.rawQuery(con, null);

            if (cursor.getCount() >0) {

                lista_rutas = new ArrayList<>();

                while (cursor.moveToNext()) {
                    DataTableWS.Ruta infoRuta = new DataTableWS.Ruta();
                    infoRuta.setRut_desc_str( cursor.getString(cursor.getColumnIndex("rut_desc_str")) );
                    infoRuta.setRut_cve_n( cursor.getString(cursor.getColumnIndex("rut_cve_n")));
                    lista_rutas.add(infoRuta);
                }
                Log.d("salida","se encontraron rutas guardadas");
            } else {
                Log.d("salida", "No se encontraron rutas guardadas");
            }

            bd.close();

        }
        catch (Exception e)
        {
            Log.d("salida",e.toString());
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<DataTableWS.Ruta> getRutas()
    {
        return lista_rutas;
    }

    public ArrayList<String> getCatalogos()
    {
        return lista_catalogos;
    }

    public Configuracion getConf()
    {
        return conf;
    }

    public boolean getCatalogosBool()
    {
        return  catalogos;
    }

    public boolean getCrut()
    {
        return crut;
    }

    public Usuario getUser()
    {
        return user;
    }


}
