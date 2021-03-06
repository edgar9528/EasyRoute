package com.tdt.easyroute.Clases;

import android.util.Log;

import java.text.NumberFormat;
import java.util.Locale;

public class string {

    public static String formatSql(String consulta, String... parametros)
    {
        String ant="",des="",c="'";
        int con=0;

        do {
            for (int i = 0; i < consulta.length(); i++) {
                if (consulta.charAt(i) == '{') {
                    ant = consulta.substring(0, i);
                }

                if (consulta.charAt(i) == '}') {
                    des = consulta.substring(i + 1);

                    if(parametros[con]!=null && parametros[con].contains(c)) //Agregada por problema del apostrofe en nombres
                    {
                        parametros[con]=parametros[con].replace(c,"''");
                    }

                    consulta = ant + parametros[con] + des;

                    break; //se termina el ciclo
                }
            }
            con++;

        }while (con<parametros.length);

        return  consulta;
    }

    public static String formatSql2(String consulta, String... parametros)
    {
        String ant="",des="",c="'";
        int con=0;
        int n;
        String ns="";
        boolean concatenar=false;

        do {
            for (int i = 0; i < consulta.length(); i++) {
                if (consulta.charAt(i) == '{') {
                    ant = consulta.substring(0, i);
                    concatenar=true;
                    ns="";
                }

                if(concatenar)
                    ns = ns + consulta.charAt(i);

                if (consulta.charAt(i) == '}') {
                    concatenar=false;
                    ns= ns.substring(1,ns.length()-1);
                    n = Integer.parseInt(ns);

                    des = consulta.substring(i + 1);

                    if(parametros[n]!=null && parametros[n].contains(c)) //Agregada por problema del apostrofe en nombres
                    {
                        parametros[n]=parametros[n].replace(c,"''");
                    }

                    consulta = ant + parametros[n] + des;

                    break; //se termina el ciclo
                }
            }
            con++;

        }while (con<parametros.length);

        return  consulta;
    }

    public static boolean CamposLlenos (String[] edittext)
    {
        boolean llenos=true;

        for(int i=0; i<edittext.length;i++)
        {
            if(edittext[i].isEmpty())
            {
                llenos=false;
                i=edittext.length;
            }
        }

        return llenos;

    }

    public static String FormatoPesos(double cantidad)
    {
        try
        {
            Locale locale = new Locale("es", "MX");
            NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
            return nf.format(cantidad);
        }
        catch (Exception e)
        {
            return "$0.00";
        }
    }

    public static String FormatoPesos(String cantidadStr)
    {
        try
        {
            double cantidad = Double.parseDouble(cantidadStr);
            Locale locale = new Locale("es", "MX");
            NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
            return nf.format(cantidad);
        }
        catch (Exception e)
        {
            return "$0.00";
        }
    }

    public static String DelCaracteres(String cantidad)
    {
        cantidad = cantidad.replace("$","");
        cantidad = cantidad.replace(",","");
        return cantidad;
    }

    public static String FormatoEntero(String num)
    {
        try
        {
            int numero = (int) Double.parseDouble(num);
            return String.valueOf(numero);
        }catch (Exception e)
        {
            return "-1";
        }
    };

    public static boolean EsNulo(String cadena)
    {
        try
        {
            if(cadena==null)
                return true;

            if(cadena.isEmpty())
                return true;

            if(cadena.equals("null"))
                return true;

            if(cadena.equals("NULL"))
                return true;

            return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }



}
