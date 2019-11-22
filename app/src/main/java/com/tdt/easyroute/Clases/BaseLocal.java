package com.tdt.easyroute.Clases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.tdt.easyroute.R;

import org.json.JSONArray;
import org.json.JSONObject;

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
            Log.d("salida","Error baseL: "+e.toString());
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
            Log.d("salida","Error BASELOCAL INSERT "+ e.getMessage());
        }

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



}
