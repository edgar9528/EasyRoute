package com.tdt.easyroute;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tdt.easyroute.Interface.AsyncResponse;
import com.tdt.easyroute.Clases.ConexionWS;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.ParametrosWS;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.Permisos;
import com.tdt.easyroute.Model.Usuario;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements AsyncResponse{

    private static String TAG="salida";
    private static String nombreBase="baseLocal.db";

    Button button_sesion;
    TextInputEditText ti_usuario,ti_contrasena;
    Usuario u;
    String user,pass;
    boolean bloq;

    ImageView iv_logo;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Importante");
        dialogo1.setMessage("¿Desea salir de la app?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

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
        setContentView(R.layout.activity_login);

        crearBaseLocal();

        button_sesion = (Button) findViewById(R.id.Blogin);
        ti_usuario = (TextInputEditText) findViewById(R.id.TIusername);
        ti_contrasena = (TextInputEditText) findViewById(R.id.TIpassword);
        iv_logo = findViewById(R.id.logo);

        ti_usuario.setText("victore");
        ti_contrasena.setText("04321");

        button_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (!ti_usuario.getText().toString().isEmpty() && !ti_contrasena.getText().toString().isEmpty())
                    {
                        user = ti_usuario.getText().toString();
                        //pass = ti_contrasena.getText().toString();
                        pass = Utils.Encriptar(ti_contrasena.getText().toString());
                        validaUsuarioLocal();

                        bloq = false;

                        if (u == null) {
                            bloq = false;
                        } else {
                            bloq = (u.getBloqueado() == 1 || u.getEstatus().equals("H"));
                        }

                        if (u == null || bloq) {

                            Log.d(TAG, "Entro buscar usuario ws");

                            //parametros del metodo
                            ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
                            PropertyInfo piUser = new PropertyInfo();
                            piUser.setName("usuario");
                            piUser.setValue(user);
                            propertyInfos.add(piUser);

                            PropertyInfo piPass = new PropertyInfo();
                            piPass.setName("contrasena");
                            piPass.setValue(pass);
                            propertyInfos.add(piPass);

                            //victore
                            //MAA0ADMAMgAxAA==

                            //conexion con el metodo
                            ParametrosWS parametrosWS = new ParametrosWS("ValidarUsuario",getApplicationContext());
                            ConexionWS conexionWS = new ConexionWS(LoginActivity.this, LoginActivity.this,parametrosWS);
                            conexionWS.delegate = LoginActivity.this;
                            conexionWS.propertyInfos = propertyInfos;
                            conexionWS.execute();
                        }

                    }

                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG,e.getMessage());
                }


            }
        });

        //Doble click en imagen
        iv_logo.setOnTouchListener(new View.OnTouchListener() {
            private GestureDetector gestureDetector = new GestureDetector(LoginActivity.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {

                    Intent intent = new Intent(LoginActivity.this, ConfiguracionActivity.class);
                    startActivity(intent);

                    return super.onDoubleTap(e);
                }
            });

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //un click
                gestureDetector.onTouchEvent(event);
                return true;
            }});

    }




    @Override
    public void processFinish(SoapObject output) {

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();

        String rut_cve = "0";
        rut_cve = Utils.LeefConfig("ruta");
        if (rut_cve.isEmpty())
        {
            rut_cve = "0";
        }

        if(output!=null)
        {
            Log.d(TAG,"Encontro usuario ws");
            ConvertirRespuesta cr = new ConvertirRespuesta(output);
            u = cr.getUsuario();

            if(!bloq) {
                guardarUsuario();
            }
            else {
                actualizarUsuario();
            }

            try {

                if (bd != null) {

                    if (!u.getEstatus().equals("H") || u.getBloqueado() == 1)
                    {
                        String cad = Querys.Trabajo.InsertBitacoraHHSesion;
                        String con= string.formatSql(cad, u.getUsuario(), string.formatSql("INICIO DE SESION V{0}",Utils.Version()),"USUARIO BLOQUEADO O INHABILITADO",rut_cve,"" );

                        bd.execSQL(con);

                        Log.d("salida","El usuario se encuentra bloqueado o deshabilitado");
                        Toast.makeText(this, "El usuario se encuentra bloqueado o deshabilitado", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String cad = Querys.Trabajo.InsertBitacoraHHSesion;
                        String con= string.formatSql(cad, u.getUsuario(), string.formatSql("INICIO DE SESION V{0}",Utils.Version()),"CORRECTO",rut_cve,"" );

                        bd.execSQL(con);

                        Toast.makeText(this, "INICIO DE SESION CORRECTO", Toast.LENGTH_SHORT).show();

                        iniciarSesion();

                    }
                }

            }catch (Exception e)
            {
                Log.d(TAG,"Error: "+e.toString());
                Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            Log.d(TAG,"No encontro usuario ws");

            if(bd!=null)
            {
                String cad = Querys.Trabajo.InsertBitacoraHHSesion;
                String con= string.formatSql(cad, user, string.formatSql("INICIO DE SESION V{0}",Utils.Version()),"USUARIO O CONTRASEÑA INVALIDOS",rut_cve,"" );

                bd.execSQL(con);
            }

            Log.d(TAG,"usuario o contraseña incorrectos");
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }

        bd.close();
    }

    public void iniciarSesion()
    {
        Log.d("salida","inicia sesion: "+u.getNombrerol());
        switch (u.getNombrerol())
        {
            case "ALMACEN LLENO":
                    Log.d(TAG,"inicia almacen lleno");
                break;
            case "ALMACEN VACIO":
                Log.d(TAG,"inicia almacen vacio");
                break;
            case "ASESOR DE VENTAS":
            case "SUPERVISOR DE VENTAS":
                Log.d(TAG,"inicia menu completo");
                break;
            default:
                break;
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    public void actualizarUsuario()
    {
        Log.d(TAG,"Actualizar usuario");

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();

        try {

            if (bd != null) {

                String con1 = "update usuarios set usu_pwd_str='{1}',usu_bloqueado_n={2},est_cve_str='{3}',rol_Cve_n={4} where usu_cve_str='{0}'";
                String con2 = "delete from rolesmodulos where rol_cve_n={0}";

                bd.execSQL(string.formatSql(con1, u.getContrasena(), String.valueOf(u.getBloqueado()), u.getEstatus(), String.valueOf(u.getRol()), u.getUsuario()));
                bd.execSQL(string.formatSql(con2, String.valueOf(u.getRol())));


                String qry = "insert into rolesmodulos(rol_cve_n,mod_cve_n,rm_lectura_n,rm_escritura_n,rm_modificacion_n,rm_eliminacion_n) values ({0},{1},{2},{3},{4},{5})";

                //obtener los permisos para ingresarlos
                List<Permisos> p = u.getPermisos();
                for (int i = 0; i < u.getPermisos().size(); i++) {
                    String[] dato = new String[6];

                    dato[0] = String.valueOf(u.getRol());
                    dato[1] = String.valueOf(p.get(i).getMod_cve_n());
                    dato[2] = String.valueOf(p.get(i).getLectura());
                    dato[3] = String.valueOf(p.get(i).getEscritura());
                    dato[4] = String.valueOf(p.get(i).getModificacion());
                    dato[5] = String.valueOf(p.get(i).getEliminacion());

                    bd.execSQL(string.formatSql(qry, dato[0], dato[1], dato[2], dato[3], dato[4], dato[5]));
                }


                bd.execSQL(Querys.Modulos.DelModulos);

                for (int i = 0; i < u.getPermisos().size(); i++) {
                    bd.execSQL(string.formatSql(Querys.Modulos.InsModulos, String.valueOf(p.get(i).getMod_cve_n()), p.get(i).getMod_desc_str()));
                }

                String con = string.formatSql("select * from roles where rol_cve_n={0}", String.valueOf(u.getRol()));
                Cursor cursor = bd.rawQuery(con, null);

                boolean encontroRol = false;
                while (cursor.moveToNext()) {
                    encontroRol = true;
                    break;
                }

                if (encontroRol) {
                    Log.d("salida", "Encontro rol del usuario");
                    Log.d("salida", "roles no actualizados");
                } else {
                    Log.d("salida", "No encontro rol del usuario");

                    String q = "insert into roles(rol_cve_n,rol_desc_str,rol_esadmin_n,est_cve_str) values ({0},'{1}',{2},'A')";
                    bd.execSQL(string.formatSql(q, String.valueOf(u.getRol()), u.getNombrerol(), String.valueOf(u.getEsadmin())));

                    Log.d("salida", "roles actualizados");
                }

            }

            bd.close();

        }catch (Exception e)
        {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error actUser"+e.toString());
        }

    }

    public void guardarUsuario()
    {
        Log.d(TAG,"Guardar usuario");

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();

        try {


            if(bd!=null)
            {
                String consulta = string.formatSql("select * from usuarios where usu_cve_str='{0}'", u.getUsuario());

                Cursor cursor = bd.rawQuery(consulta, null);

                boolean encontroUsuario = false;

                while (cursor.moveToNext()) {
                    Log.d("salida", "Encontro usuario local despues de ws");
                    encontroUsuario = true;
                    break;
                }

                if (encontroUsuario) {
                    String cad = "update usuarios set usu_pwd_str='{1}',usu_bloqueado_n={2},est_cve_str='{3}',rol_Cve_n={4} where usu_cve_str='{0}'";
                    String con = string.formatSql(cad, u.getContrasena(), String.valueOf(u.getBloqueado()), u.getEstatus(), String.valueOf(u.getRol()), u.getUsuario());
                    bd.execSQL(con);

                    Log.d("salida", "usuario actualizado");
                } else {

                    String cad = "insert into usuarios(usu_cve_str,usu_pwd_str,usu_nom_str,usu_app_str,usu_apm_str,rol_cve_n,usu_bloqueado_n,est_cve_str) values ('{0}','{1}','{2}','{3}','{4}',{5},{6},'{7}')";
                    String con = string.formatSql(cad, u.getUsuario(), u.getContrasena(), u.getNombre(), u.getAppat(), u.getApmat(), String.valueOf(u.getRol()), String.valueOf(u.getBloqueado()), u.getEstatus());

                    bd.execSQL(con);

                    Log.d("salida", "usuario agregado");
                }

                bd.execSQL(string.formatSql("delete from rolesmodulos where rol_cve_n={0}", String.valueOf(u.getRol())));

                String qry = "insert into rolesmodulos(rol_cve_n,mod_cve_n,rm_lectura_n,rm_escritura_n,rm_modificacion_n,rm_eliminacion_n) values ({0},{1},{2},{3},{4},{5})";

                //obtener los permisos para ingresarlos
                List<Permisos> p = u.getPermisos();

                for (int i = 0; i < u.getPermisos().size(); i++) {
                    String[] dato = new String[6];

                    dato[0] = String.valueOf(u.getRol());
                    dato[1] = String.valueOf(p.get(i).getMod_cve_n());
                    dato[2] = String.valueOf(p.get(i).getLectura());
                    dato[3] = String.valueOf(p.get(i).getEscritura());
                    dato[4] = String.valueOf(p.get(i).getModificacion());
                    dato[5] = String.valueOf(p.get(i).getEliminacion());

                    bd.execSQL(string.formatSql(qry, dato[0], dato[1], dato[2], dato[3], dato[4], dato[5]));
                }

                bd.execSQL(Querys.Modulos.DelModulos);

                for (int i = 0; i < u.getPermisos().size(); i++) {
                    if (bd != null) {
                        bd.execSQL(string.formatSql(Querys.Modulos.InsModulos, String.valueOf(p.get(i).getMod_cve_n()), p.get(i).getMod_desc_str()));
                    }
                }

                String con = string.formatSql("select * from roles where rol_cve_n={0}", String.valueOf(u.getRol()));
                cursor = bd.rawQuery(con, null);

                boolean encontroRol = false;

                while (cursor.moveToNext()) {
                    encontroRol = true;
                    break;
                }

                if (encontroRol)
                {
                    Log.d("salida", "Encontro rol del usuario");
                    Log.d("salida", "roles no actualizados");
                } else {
                    Log.d("salida", "No encontro rol del usuario");

                    String q = "insert into roles(rol_cve_n,rol_desc_str,rol_esadmin_n,est_cve_str) values ({0},'{1}',{2},'A')";
                    bd.execSQL(string.formatSql(q, String.valueOf(u.getRol()), u.getNombrerol(), String.valueOf(u.getEsadmin())));

                    Log.d("salida", "roles actualizados");
                }
            }

            bd.close();

        }catch (Exception e)
        {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Error actUser"+e.toString());
        }

    }

    public void validaUsuarioLocal()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();

        String consulta = string.formatSql(Querys.Login.SeleccionarUsuario,user,pass);

        Cursor cursor = bd.rawQuery(consulta,null);

        boolean encontro=false;

        while(cursor.moveToNext()){
            encontro=true;
        }

        if(!encontro)
        {
            u=null;
            Log.d(TAG,"No encontro usuario local");
        }
        else
        {
            Log.d(TAG,"Encontro usuario local");
        }

        bd.close();
    }

    private void crearBaseLocal()
    {
        try
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);

            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            Log.d("salida","bd creada");

        }catch (Exception e)
        {
            Log.d("salida",e.toString());
            Log.d("salida",e.getMessage().toString());
            Toast.makeText(this, "Error al crear base", Toast.LENGTH_SHORT).show();
        }

    }

}
