package com.tdt.easyroute.Clases;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;

import com.tdt.easyroute.R;

import java.nio.charset.StandardCharsets;

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

    public static String LeefConfig(String valor, Activity activity)
    {
        SharedPreferences sharedPref = activity.getSharedPreferences("ConfiguracionPreferences", Context.MODE_PRIVATE);

        switch (valor)
        {
            case "servidor":
                valor = sharedPref.getString("servidor","null");
                break;
            case "ruta":
                valor = sharedPref.getString("ruta","0");
                break;
            case "timeoute":
                valor= sharedPref.getString("timeout","null");

            default:

                break;
        }

        return valor;
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


}
