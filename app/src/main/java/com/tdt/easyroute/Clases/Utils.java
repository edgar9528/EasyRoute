package com.tdt.easyroute.Clases;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;

import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.R;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;

public class Utils {

    public static String Encriptar(String _cadenaAencriptar)
    {
        byte[] bytes = _cadenaAencriptar.getBytes(StandardCharsets.UTF_16LE);
        String base64 = android.util.Base64.encodeToString(bytes, Base64.NO_WRAP);

        return  base64;
    }

    /// Esta función desencripta la cadena que le envíamos en el parámentro de entrada.
    public static String DesEncriptar(String _cadenaAdesencriptar)
    {
        byte[] data1 = Base64.decode(_cadenaAdesencriptar, Base64.NO_WRAP);
        String text1 = null;
        try {
            text1 = new String(data1, StandardCharsets.UTF_16LE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  text1;
    }

    public static String LeefConfig(String valor, Application application)
    {
        SharedPreferences sharedPref = application.getSharedPreferences("ConfiguracionPreferences", Context.MODE_PRIVATE);

        switch (valor)
        {
            case "servidor":
                valor = sharedPref.getString("servidor","http://192.168.0.95/EasyRoute.asmx");
                break;
            case "timeout":
                valor = sharedPref.getString("timeout","15000");
                break;
            case "empresa":
                valor = sharedPref.getString("empresa","1");
                break;
            case "ruta":
                valor = sharedPref.getString("ruta","0");
                break;
            case "imp":
                valor = sharedPref.getString("imp","COM1");
                break;
            default:

                break;
        }

        return valor;
    }

    public static void CreafConfig(String servidor,String timeout,String empresa, String ruta,String imp, Application app)
    {
        SharedPreferences sharedPref = app.getSharedPreferences("ConfiguracionPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("servidor",servidor);
        editor.putString("timeout",timeout);
        editor.putString("empresa",empresa);
        editor.putString("ruta",ruta);
        editor.putString("imp",imp);

        editor.apply();
    }

    public static String Version()
    {
        String version = "1.0.1";

        return version;
    }

    public static Configuracion ObtenerConf(Application app)
    {
        Configuracion cf=null;

        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(app, app.getString(R.string.nombreBD), null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            String qry = "Select * from ConfiguracionHH where est_cve_str='A'  and conf_fechafin_dt is null";
            Cursor cursor = bd.rawQuery(qry, null);

            if (cursor.getCount() >0) {
                Log.d("salida", "cursor con info, se crea configuracion = null");

                cf = new Configuracion();

                while (cursor.moveToNext()) {
                    Log.d("salida", "se llena la info");
                    cf = new Configuracion();

                    cf.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("conf_cve_n"))));
                    cf.setRuta(Integer.parseInt(cursor.getString(cursor.getColumnIndex("rut_cve_n"))));
                    cf.setPreventa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("conf_preventa_n"))));
                    cf.setAuditoria(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("conf_auditoria_n"))));
                    cf.setUsuario(cursor.getString(cursor.getColumnIndex("usu_cve_str")));

                    if (cursor.getString(cursor.getColumnIndex("conf_descarga_dt")) != null)
                        cf.setDescarga(true);

                    Log.d("salida","VER QUE IMPRIME NULL: "+cursor.getColumnIndex("conf_descarga_dt"));

                }
            } else {
                cf = null;
                Log.d("salida", "Cursor sin info, configuracion = null");
            }

            bd.close();

            return cf;

        }catch (Exception e)
        {
            Log.d("salida","error utils"+e.toString());
            return cf;
        }

    }

    public static boolean getBool(String cad)
    {
        boolean var;
        if(cad.equals("true"))
        {
            var = true;
        }
        else
        {
            var=false;
        }

        return var;
    }

    public static String PositionStr()
    {
        String posicion = "20.945,-97.406389";
        return posicion;
    }

    public static String FechaLocal()
    {
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd", d.getTime());
        return s.toString();
    }

    public static String HoraLocal()
    {
        Date d = new Date();
        CharSequence s = DateFormat.format("HH:mm:ss", d.getTime());
        return s.toString();
    }

    public static String FechaWS(String fechaWS)
    {
        String fecha="";
        String originalStringFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String desiredStringFormat = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat readingFormat = new SimpleDateFormat(originalStringFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(desiredStringFormat);

        try {
            Date date = readingFormat.parse(fechaWS);
            fecha = outputFormat.format(date);

        } catch (Exception e) {
            Log.d("salida","Error: "+e.getMessage());
            e.printStackTrace();
        }
        return fecha;
    }

    public static boolean FechasIguales(String local, String ws) {
        boolean iguales = false;

        try {

            local = local.substring(0,local.length()-3);
            ws = ws.substring(0,ws.length()-3);

            if(local.equals(ws))
                iguales=true;
            else
                iguales=false;

            Log.d("salida","tlo: "+local);
            Log.d("salida","tws: "+ws);

        }catch (Exception e)
        {
            Log.d("salida","FechasIguales: "+e.getMessage());
            iguales=false;
        }

        return iguales;
    }


}
