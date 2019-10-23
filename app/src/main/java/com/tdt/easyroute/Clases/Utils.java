package com.tdt.easyroute.Clases;

import android.util.Base64;

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

    public static String LeefConfig(String ruta)
    {

        return "0";
    }

    public static String Version()
    {
        String version = "1.0.1";

        return version;
    }
    
}
