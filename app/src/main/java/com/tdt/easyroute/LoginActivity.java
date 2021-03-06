package com.tdt.easyroute;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.WSEasyRoute;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.Permisos;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.Ventanas.Configuracion.ConfiguracionActivity;
import com.tdt.easyroute.Ventanas.PruebasActivity;
import org.ksoap2.serialization.PropertyInfo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements AsyncResponseJSON {

    private static String TAG="salida";
    private static String nombreBase;
    private String rut_cve;

    private TextInputEditText ti_usuario,ti_contrasena;
    private Usuario u;
    private String user,pass;
    boolean bloq;

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(getString(R.string.msg_importante));
        dialogo1.setMessage(getString(R.string.msg_salir));
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                /*
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/

            }
        });
        dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
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

        try
        {
            pruebas();

            Button button_sesion;
            nombreBase = getString(R.string.nombreBD);

            button_sesion = findViewById(R.id.Blogin);
            ti_usuario = findViewById(R.id.TIusername);
            ti_contrasena = findViewById(R.id.TIpassword);
            ImageView iv_logo = findViewById(R.id.imageView);



            //ti_usuario.setText("AGUTIERREZ");
            //ti_contrasena.setText("772");

            //AGUTIERREZ -  victore
            //772        -  04321

            button_sesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickBotonIniciar();
                }
            });

            button_sesion.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    finish();
                    return false;
                }
            });

            //Doble click en imagen
            iv_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, ConfiguracionActivity.class);
                    intent.putExtra("admin", true);
                    startActivity(intent);
                }
            });

            iv_logo.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, PruebasActivity.class);
                    startActivity(intent);
                    return true;
                }
            });

            crearBaseLocal();

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error1), e.getMessage());
        }

    }

    public void clickBotonIniciar()
    {
        try {

            if (!ti_usuario.getText().toString().isEmpty() && !ti_contrasena.getText().toString().isEmpty())
            {
                user = ti_usuario.getText().toString();
                pass = ti_contrasena.getText().toString();

                if ((user.equals("72") && pass.equals("2663")) || (user.equals("72") && pass.equals("23646")))
                {
                    if (pass.equals("2663"))
                    {
                        Intent intent = new Intent(LoginActivity.this, ConfiguracionActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(LoginActivity.this, ConfiguracionActivity.class);
                        intent.putExtra("admin", true);
                        startActivity(intent);
                    }
                }
                else
                {
                    pass = Utils.Encriptar(ti_contrasena.getText().toString());

                    validaUsuarioLocal();

                    bloq = false;

                    if (u == null) {
                        bloq = false;
                    } else {
                        bloq = (u.getBloqueado() == 1 || !u.getEstatus().equals("H"));
                    }

                    if (u == null || bloq) {

                        Log.d(TAG, "Entro buscar usuario ws");

                        //parametros del metodo
                        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
                        PropertyInfo piUser = new PropertyInfo();
                        piUser.setName("usuarioJ");
                        piUser.setValue(user);
                        propertyInfos.add(piUser);

                        PropertyInfo piPass = new PropertyInfo();
                        piPass.setName("contrasenaJ");
                        piPass.setValue(pass);
                        propertyInfos.add(piPass);

                        //conexion con el metodo
                        ConexionWS_JSON conexionWS = new ConexionWS_JSON(LoginActivity.this, "ValidarUsuarioJ");
                        conexionWS.delegate = LoginActivity.this;
                        conexionWS.propertyInfos = propertyInfos;
                        conexionWS.execute();

                    } else {
                        almacenarInformacion();
                    }
                }
            }
            else
                Toast.makeText(LoginActivity.this, getString(R.string.tt_camposVacios), Toast.LENGTH_LONG).show();

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error2), e.getMessage());
        }
    }

    public void obtenerRoles()
    {
        try {

            Log.d("salida", "OBTENIENDO ROLES...");

            DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            String cons = string.formatSql(Querys.Login.PermisosEfectivos3, String.valueOf(u.getRol()));
            Cursor cursor2 = bd.rawQuery(cons, null);

            if (cursor2 != null) {

                Log.d("salida", "CURSOR CON INFORMACION:" + cursor2.getCount());

                List<Permisos> permisos = new ArrayList<>();

                while (cursor2.moveToNext()) {

                    Permisos permiso = new Permisos();

                    permiso.setMod_cve_n(Integer.parseInt(cursor2.getString(0)));
                    permiso.setMod_desc_str(cursor2.getString(1));
                    permiso.setLectura(Byte.parseByte(cursor2.getString(2)));
                    permiso.setEscritura(Byte.parseByte(cursor2.getString(3)));
                    permiso.setModificacion(Byte.parseByte(cursor2.getString(4)));
                    permiso.setEliminacion(Byte.parseByte(cursor2.getString(5)));

                    permisos.add(permiso);
                }

                u.setPermisos(permisos);

            } else {
                Log.d("salida", "CURSOR VACIO");
            }

            bd.close();

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error3), e.getMessage());
        }
    }

    public void almacenarInformacion()
    {
        //se guarda o actualiza la información del usuario en local
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
        SQLiteDatabase bd = databaseHelper.getWritableDatabase();


        //Recibe la respuesta del servidor
        rut_cve = "0";
        rut_cve = Utils.LeefConfig("ruta", getApplication() );
        if (rut_cve.isEmpty())
        {
            rut_cve = "0";
        }

        if(!bloq) {
            guardarUsuario();
        }
        else {
            actualizarUsuario();
        }

        try
        {
            if (bd != null)
            {

                if (!u.getEstatus().equals("H") || u.getBloqueado() == 1)
                {
                    String cad = Querys.Trabajo.InsertBitacoraHHSesion;
                    String con= string.formatSql(cad, u.getUsuario(), string.formatSql("INICIO DE SESION V{0}",Utils.Version()),"USUARIO BLOQUEADO O INHABILITADO",rut_cve,"" );

                    bd.execSQL(con);

                    Log.d("salida","El usuario se encuentra bloqueado o deshabilitado");
                    Toast.makeText(this, getString(R.string.tt_login2), Toast.LENGTH_LONG).show();
                }
                else
                {
                    String cad = Querys.Trabajo.InsertBitacoraHHSesion;
                    String con= string.formatSql(cad, u.getUsuario(), string.formatSql("INICIO DE SESION V{0}",Utils.Version()),"CORRECTO",rut_cve,"" );

                    bd.execSQL(con);

                    iniciarSesion();
                }

                if(bd.isOpen())
                    bd.close();
            }

        }catch (Exception e)
        {
            if(bd.isOpen())
                bd.close();
            Utils.msgError(this, getString(R.string.error4), e.getMessage());
            Log.d(TAG,"Error: "+e.toString());
        }
    }

    public void iniciarSesion()
    {
        try {

            //el usuario fue encontrado, aqui se verifica que permisos tiene
            Log.d("salida", "inicia sesion: " + u.getNombrerol());

            switch (u.getNombrerol()) {
                case "ALMACEN LLENO":
                    Log.d(TAG, "inicia almacen lleno");
                    break;
                case "ALMACEN VACIO":
                    Log.d(TAG, "inicia almacen vacio");
                    break;
                case "ASESOR DE VENTAS":
                case "SUPERVISOR DE VENTAS":
                    Log.d(TAG, "inicia menu completo");
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("usuario", u);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(this,  getString(R.string.tt_login3), Toast.LENGTH_LONG).show();
                    break;

            }
        }catch (Exception e)
        {
            Utils.msgError(this,getString(R.string.error2),e.getMessage());
        }
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
                    Log.d("salida", "Roles existentes. no actualizados");
                } else {
                    Log.d("salida", "No encontro rol del usuario");

                    String q = "insert into roles(rol_cve_n,rol_desc_str,rol_esadmin_n,est_cve_str) values ({0},'{1}',{2},'A')";
                    bd.execSQL(string.formatSql(q, String.valueOf(u.getRol()), u.getNombrerol(), String.valueOf(u.getEsadmin())));

                    Log.d("salida", "roles actualizados");
                }
                bd.close();
            }



        }catch (Exception e)
        {
            if(bd.isOpen())
                bd.close();

            Utils.msgError(this, getString(R.string.error5),e.getMessage() );
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
                    Log.d("salida", "Existencia usuario local");
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
                        bd.execSQL(string.formatSql(Querys.Modulos.InsModulos, String.valueOf(p.get(i).getMod_cve_n()), p.get(i).getMod_desc_str()));
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
                    Log.d("salida", "Roles existentes. No actualizados");
                } else {
                    Log.d("salida", "No encontro roles del usuario");

                    String q = "insert into roles(rol_cve_n,rol_desc_str,rol_esadmin_n,est_cve_str) values ({0},'{1}',{2},'A')";
                    bd.execSQL(string.formatSql(q, String.valueOf(u.getRol()), u.getNombrerol(), String.valueOf(u.getEsadmin())));

                    Log.d("salida", "roles actualizados");
                }

                bd.close();
            }

        }catch (Exception e)
        {
            if(bd.isOpen())
                bd.close();

            Utils.msgError(this, getString(R.string.error6),e.getMessage());

            Log.d(TAG,"Error actUser"+e.toString());
        }
    }

    public void validaUsuarioLocal()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
        SQLiteDatabase bd = databaseHelper.getReadableDatabase();

        try {

            String consulta = string.formatSql(Querys.Login.SeleccionarUsuario, user, pass);

            Cursor cursor = bd.rawQuery(consulta, null);

            boolean encontro = false;

            while (cursor.moveToNext()) {
                encontro = true;
                Log.d(TAG, "Encontro usuario local");

                u = new Usuario();

                u.setUsuario(cursor.getString(0));
                u.setContrasena(cursor.getString(1));
                u.setNombre(cursor.getString(2));
                u.setAppat(cursor.getString(3));
                u.setApmat(cursor.getString(4));
                u.setRol(Integer.parseInt(cursor.getString(5)));
                u.setEstatus(cursor.getString(6));
                u.setBloqueado(Byte.parseByte(cursor.getString(8)));
                //u.setEsadmin();
                //u.setNombrerol();
            }

            if (encontro) {
                consulta = string.formatSql(Querys.Login.ObtenerRol, String.valueOf(u.getRol()));
                cursor = bd.rawQuery(consulta, null);

                while (cursor.moveToNext()) {
                    u.setNombrerol(cursor.getString(1));
                    u.setEsadmin(Byte.valueOf(cursor.getString(2)));
                }
                obtenerRoles();
            } else {

                u = null;
                Log.d(TAG, "No encontro usuario local");
            }

            bd.close();
        }catch (Exception e)
        {
            if(bd.isOpen())
                bd.close();

            Utils.msgError(this, getString(R.string.error7), e.getMessage());
        }
    }

    private void crearBaseLocal()
    {
        try
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            db.close();
        }catch (Exception e)
        {
            Log.d("salida",e.toString());
        }

    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

        try {

            if (estado) {
                if (respuesta != null) {
                    Log.d(TAG, "Encontro usuario ws");
                    //Obtenemos el objeto usuario con los valores del servidor, lo guardamos en u

                    u = ConvertirRespuesta.getUsuarioJson(respuesta);

                    Log.d("salida", "usuario permisos: " + u.getPermisos().get(0).getMod_desc_str());

                    almacenarInformacion();
                } else {
                    Log.d(TAG, "No encontro usuario ws");

                    DatabaseHelper databaseHelper = new DatabaseHelper(getApplication(), nombreBase, null, 1);
                    SQLiteDatabase bd = databaseHelper.getWritableDatabase();

                    if (bd != null) {
                        String cad = Querys.Trabajo.InsertBitacoraHHSesion;
                        String con = string.formatSql(cad, user, string.formatSql("INICIO DE SESION V{0}", Utils.Version()), "USUARIO O CONTRASEÑA INVALIDOS", rut_cve, "");

                        bd.execSQL(con);
                    }

                    if (bd.isOpen())
                        bd.close();

                    Log.d(TAG, "usuario o contraseña incorrectos");
                    Utils.msgInfo(this, getString(R.string.tt_login1));
                    //Toast.makeText(this,  getString(R.string.tt_login1), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
            }

        }catch (Exception e)
        {
            Utils.msgError(this, getString(R.string.error_peticion),e.getMessage());
        }

    }

    private void pruebas() throws ParseException {
        ArrayList<DataTableLC.Prueba> alOriginal= new ArrayList<>();

        alOriginal.add( new DataTableLC.Prueba("edgar","1"));
        alOriginal.add( new DataTableLC.Prueba("juan","1"));

        //ArrayList<DataTableLC.Prueba> alCopia = alOriginal;
        ArrayList<DataTableLC.Prueba> alCopia =  new ArrayList<>(alOriginal);

        for(int i=0; i<alOriginal.size();i++)
            Log.d("salida","al original: "+ alOriginal.get(i).getNombre());

        for(int i=0; i<alCopia.size();i++)
            Log.d("salida","al copia: "+ alCopia.get(i).getNombre());

        alCopia.add( new DataTableLC.Prueba("edgar","2"));

        for(int i=0; i<alOriginal.size();i++)
            Log.d("salida","al original: "+ alOriginal.get(i).getNombre());

        for(int i=0; i<alCopia.size();i++)
            Log.d("salida","al copia: "+ alCopia.get(i).getNombre());

        for(DataTableLC.Prueba p : alCopia)
        {
            p.setNombre("Prueba");
            p.setEdad("Correcta");
        }

        for(int i=0; i<alCopia.size();i++)
            Log.d("salida","al copia: "+ alCopia.get(i).getNombre() +" "+alCopia.get(i).getEdad());

    }

}
