package com.tdt.easyroute.Clases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.tdt.easyroute.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class BaseLocal {

    public static String Select(String consulta, Context context)
    {
        String nombreBase = context.getString( R.string.nombreBD );
        String json=null;

        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(context, nombreBase, null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            Cursor cursor = bd.rawQuery(consulta, null);

            if(cursor.getCount()>0)
                Log.d("salida","encontro info en la bd (Base local) "+consulta);

            json = cur2Json(cursor);

            bd.close();

        }
        catch (Exception e)
        {
            Log.d("salida","Error baseLocal Select: "+e.toString());
            json=null;
        }

        return json;
    }

    public static void Insert(String consulta,Context context)
    {
        try {
            String nombreBase = context.getString(R.string.nombreBD);

            DatabaseHelper databaseHelper = new DatabaseHelper(context, nombreBase, null, 1);
            SQLiteDatabase bd = databaseHelper.getWritableDatabase();

            bd.execSQL(consulta);

            bd.close();
        }
        catch (Exception e)
        {
            Log.d("salida","Error baseLocal Insert "+ e.getMessage());
        }

    }

    public static String SelectDato(String consulta, Context context)
    {
        String nombreBase = context.getString( R.string.nombreBD );
        String datoReturn=null;

        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(context, nombreBase, null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            Cursor cursor = bd.rawQuery(consulta, null);

            if(cursor.moveToNext())
            {
                datoReturn= cursor.getString(0 );
            }

            bd.close();

        }
        catch (Exception e)
        {
            Log.d("salida","Error baseL: "+e.toString());
            datoReturn=null;
        }

        return datoReturn;
    }

    public static String SelectFrecPunteo(Context context)
    {
        String consulta = "Select * from frecpunteo";
        String nombreBase = context.getString( R.string.nombreBD );
        String json=null;

        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(context, nombreBase, null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            Cursor cursor = bd.rawQuery(consulta, null);

            if(cursor.getCount()>0)
                Log.d("salida","encontro info en la bd (Base local) "+consulta);

            json = cur2JsonFrec(cursor);

            bd.close();

        }
        catch (Exception e)
        {
            Log.d("salida","Error baseLocal Select: "+e.toString());
            json=null;
        }

        return json;
    }

    public static String SelectUpload(String consulta, Context context)
    {
        String nombreBase = context.getString( R.string.nombreBD );
        String json=null;

        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(context, nombreBase, null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            Cursor cursor = bd.rawQuery(consulta, null);

            if(cursor.getCount()>0)
                Log.d("salida","encontro info en la bd (Base local) "+consulta);

            json = cur2JsonUpload(cursor);

            bd.close();

        }
        catch (Exception e)
        {
            Log.d("salida","Error baseLocal Select: "+e.toString());
            json=null;
        }

        return json;
    }

    private static String cur2Json(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {

                        if(cursor.getString(i)==null)
                            rowObject.put(cursor.getColumnName(i), "");
                        else
                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));

                    } catch (Exception e) {
                        Log.d("salida", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet.toString();
    }

    private static String cur2JsonFrec(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            int orden[] = {0,2,1,3,4,5};

            for (int i = 0; i < totalColumn; i++) {

                if (cursor.getColumnName(i) != null) {
                    try {

                        if(cursor.getString(orden[i])==null)
                            rowObject.put(cursor.getColumnName(i), "");
                        else
                            rowObject.put(cursor.getColumnName(i), cursor.getString(orden[i]));

                    } catch (Exception e) {
                        Log.d("salida", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet.toString();
    }

    private static String cur2JsonUpload(Cursor cursor) {

        String[] x = {"ven_credito_n","ven_consigna_n",
                      "prod_envase_n","prod_promo_n","prod_regalo_n","prod_cobranza_n","vdet_dia_n","vdet_kit_n",
                      "cred_engestoria_n","cred_esenvase_n","cred_especial_n",
                      "pag_cobranza_n","pag_fba_n","pag_envase_n","pag_especie_n",

                      };
        ArrayList<String> camposBoolean = new ArrayList<String>(Arrays.asList(x));



        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {

                        if(camposBoolean.contains(cursor.getColumnName(i)))
                        {
                            if(cursor.getString(i)==null)
                                rowObject.put(cursor.getColumnName(i), false);
                            else
                            {
                                if(cursor.getString(i).equals("1"))
                                    rowObject.put(cursor.getColumnName(i), true);
                                else
                                    rowObject.put(cursor.getColumnName(i), false);
                            }
                        }
                        else
                        {
                            if(cursor.getString(i)==null)
                                rowObject.put(cursor.getColumnName(i), "");
                            else
                                rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        }

                    } catch (Exception e) {
                        Log.d("salida", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet.toString();
    }


}
