package com.tdt.easyroute.Clases;

import android.app.Activity;
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
import android.widget.Toast;

import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Modelos;
import com.tdt.easyroute.R;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static String LeefConfig(String valor, Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("ConfiguracionPreferences", Context.MODE_PRIVATE);

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

    public static void ActualizaConf(String config,String valor,Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences("ConfiguracionPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (config)
        {
            case "servidor":
                editor.putString("servidor",valor);
                break;
            case "timeout":
                editor.putString("timeout",valor);
                break;
            case "empresa":
                editor.putString("empresa",valor);
                break;
            case "ruta":
                editor.putString("ruta",valor);
                break;
        }

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
                Log.d("salida", "cursor con info, existe ConfiguracionHH");

                cf = new Configuracion();

                while (cursor.moveToNext()) {
                    cf = new Configuracion();

                    cf.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("conf_cve_n"))));
                    cf.setRuta(Integer.parseInt(cursor.getString(cursor.getColumnIndex("rut_cve_n"))));
                    cf.setPreventa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("conf_preventa_n"))));
                    cf.setAuditoria(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("conf_auditoria_n"))));
                    cf.setUsuario(cursor.getString(cursor.getColumnIndex("usu_cve_str")));

                    if (cursor.getString(cursor.getColumnIndex("conf_descarga_dt")) != null) {
                        cf.setDescarga(true);
                    }

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

    public static Configuracion ObtenerConf2(Application app)
    {
        Configuracion cf=null;
        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(app, app.getString(R.string.nombreBD), null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            String qry = "Select * from ConfiguracionHH where est_cve_str='A'";
            Cursor cursor = bd.rawQuery(qry, null);

            if (cursor.getCount() >0) {
                Log.d("salida", "cursor con info, existe ConfiguracionHH 2");

                cf = new Configuracion();

                while (cursor.moveToNext()) {
                    cf = new Configuracion();

                    cf.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("conf_cve_n"))));
                    cf.setRuta(Integer.parseInt(cursor.getString(cursor.getColumnIndex("rut_cve_n"))));
                    cf.setPreventa(Integer.parseInt(cursor.getString(cursor.getColumnIndex("conf_preventa_n"))));
                    cf.setUsuario(cursor.getString(cursor.getColumnIndex("usu_cve_str")));

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

    public static void RegresarInicio(Activity activity)
    {
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.regresarInicio();
    }

    public static String CalcularDia(Application application)
    {
        String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};

        Calendar cal= Calendar.getInstance();
        cal.setTime(new Date());
        int numeroDia = cal.get(Calendar.DAY_OF_WEEK)-1;

        Configuracion conf = Utils.ObtenerConf(application);

        String filtro = "";

        String dia = dias[numeroDia];

        if(conf.getPreventa()==1)
        {
            if(dias[numeroDia].equals("Sábado"))
            {
                dia = "Lunes";
            }
            else
                dia= dias[numeroDia+1];
        }


        switch (dia) {
            case "Lunes":
                filtro = "cli_lun_n";
                break;
            case "Martes":
                filtro = "cli_mar_n";
                break;
            case "Miércoles":
                filtro = "cli_mie_n";
                break;
            case "Jueves":
                filtro = "cli_jue_n";
                break;
            case "Viernes":
                filtro = "cli_vie_n";
                break;
            case "Sábado":
                filtro = "cli_sab_n";
                break;
            case "Domingo":
                filtro = "cli_dom_n";
                break;
        }

        return filtro;
    }

    public static Modelos.Indicadores ObtenerProductividad(int ruta, Application application)
    {
        Modelos.Indicadores ip = new Modelos.Indicadores();
        ArrayList<DataTableWS.Ruta> Rutas;
        ArrayList<DataTableLC.Clientes1> clientes;
        DataTableWS.Ruta cr;

        String WorkDay = CalcularDia(application);

        try
        {

            String json = BaseLocal.Select(string.formatSql("Select * from rutas where rut_cve_n={0}",String.valueOf(ruta)),application);
            Rutas = ConvertirRespuesta.getRutasJson(json);

            if(Rutas!=null && Rutas.size()>0)
            {
                cr = Rutas.get(0);
                int calculo;

                ip.setPorcentajeRequerido( Integer.parseInt( cr.getRut_productividad_n() )    );
                ip.setPorcentajeRequeridoVentas(  Integer.parseInt(  cr.getRut_efectividad_n() ) );

                clientes = ListarClientes(true,application);

                ip.setValorMercado(clientes.size());


                //clientes
                calculo = 0;
                for(int i=0; i<clientes.size();i++)
                    if(clientes.get(i).getCli_prospecto_n().equals("0")) calculo++;

                ip.setClientes(calculo);

                //prospectos
                calculo=0;
                for(int i=0; i<clientes.size();i++)
                    if(clientes.get(i).getCli_prospecto_n().equals("1")) calculo++;
                ip.setProspectos(calculo);


                //VisitasRequeridas
                int vr = (ip.getClientes() * ip.getPorcentajeRequerido()) / 100;
                if (ip.getClientes() > 0)
                {
                    if ((vr * 100 / ip.getClientes()) < ip.getPorcentajeRequerido())
                        vr++;
                }
                else
                    vr = 0;

                ip.setVisitasRequeridas(vr);


                //VentasRequeridas
                int vtar = (ip.getClientes() * ip.getPorcentajeRequeridoVentas()) / 100;
                if (ip.getClientes() > 0)
                {
                    if ((vtar * 100 / ip.getClientes()) < ip.getPorcentajeRequeridoVentas())
                        vtar++;
                }
                else
                    vtar = 0;
                ip.setVentasRequeridas(vtar);


                //Calcular productividad
                ArrayList<DataTableLC.BitacoraHH> bitacora;

                json= BaseLocal.Select( string.formatSql( "select b.* from bitacorahh b inner join clientes c on b.cli_cve_n=c.cli_cve_n where {0}>0",WorkDay ),application );
                bitacora = ConvertirRespuesta.getBitacoraHHJson(json);


                ArrayList<DataTableLC.BitacoraHH> res=null;
                DataTableLC.BitacoraHH b;
                for(int i=0; i<clientes.size();i++)
                {
                    DataTableLC.Clientes1 r = clientes.get(i);

                    //Con venta
                    res= new ArrayList<>();
                    for(int j=0; j<bitacora.size();j++)
                    {
                        b= bitacora.get(i);
                        if( b.getCli_cve_n().equals(r.getCli_cve_n()) && (  b.getBit_comentario_str().equals("VISITA CON VENTA") || b.getBit_comentario_str().equals("VISITA CON PREVENTA") )  )
                            res.add(b);
                    }

                    if (res.size() > 0)
                    {
                        clientes.get(i).setVisitado("1");
                        clientes.get(i).setConventa("1");
                    }

                    //Cobranza
                    res= new ArrayList<>();
                    for(int j=0; j<bitacora.size();j++)
                    {
                        b= bitacora.get(i);
                        if( b.getCli_cve_n().equals(r.getCli_cve_n()) &&   b.getBit_comentario_str().equals("VISITA CON COBRANZA")  )
                            res.add(b);
                    }

                    if (res.size() > 0)
                    {
                        clientes.get(i).setVisitado("1");
                        clientes.get(i).setConcobranza("1");
                    }

                    //No Venta

                    res= new ArrayList<>();
                    for(int j=0; j<bitacora.size();j++)
                    {
                        b= bitacora.get(i);
                        if( b.getCli_cve_n().equals(r.getCli_cve_n()) &&   b.getBit_operacion_str().equals("NO VENTA")  )
                            res.add(b);
                    }

                    if (res.size() > 0)
                    {
                        clientes.get(i).setVisitado("1");
                        clientes.get(i).setNoventa("1");
                    }

                }


                //Clientes Visitados
                calculo=0;
                for(int i=0; i<clientes.size();i++)
                    if(clientes.get(i).getCli_prospecto_n().equals("0") &&  Integer.parseInt( clientes.get(i).getVisitado() )>0  ) calculo++;
                ip.setProspectos(calculo);

                //Clientes con venta
                calculo=0;
                for(int i=0; i<clientes.size();i++)
                    if(clientes.get(i).getCli_prospecto_n().equals("0") &&  Integer.parseInt( clientes.get(i).getConventa() )>0  ) calculo++;
                ip.setConVenta(calculo);


                //Clientes con cobranza
                calculo=0;
                for(int i=0; i<clientes.size();i++)
                    if(clientes.get(i).getCli_prospecto_n().equals("0") &&  Integer.parseInt( clientes.get(i).getConcobranza() )>0  ) calculo++;
                ip.setConCobranza(calculo);


                //Clientes con noventa
                calculo=0;
                for(int i=0; i<clientes.size();i++)
                    if(clientes.get(i).getCli_prospecto_n().equals("0") &&  Integer.parseInt( clientes.get(i).getNoventa() )>0  ) calculo++;
                ip.setConCobranza(calculo);

                //Prospectos Visitados
                calculo=0;
                for(int i=0; i<clientes.size();i++)
                    if(clientes.get(i).getCli_prospecto_n().equals("1") &&  Integer.parseInt( clientes.get(i).getVisitado() )>0  ) calculo++;
                ip.setConCobranza(calculo);


                if (ip.getClientes() > 0)
                {
                    ip.setPorcentajeRealizado( ip.getVisitasClientes()*100 / ip.getClientes()  );
                    ip.setPorcentajeRealizadoVentas( ip.getConVenta() * 100 / ip.getClientes() );
                }
                else
                {
                    ip.setPorcentajeRealizado(0);
                    ip.setPorcentajeRealizadoVentas(0);

                }
            }


        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            return null;
        }

        return ip;
    }

    public static ArrayList<DataTableLC.Clientes1> ListarClientes(boolean dia, Application application)
    {
        Configuracion conf = Utils.ObtenerConf(application);

        String filtro= CalcularDia(application);

        String qry;

        if(dia)
        {
            if (conf.getPreventa() == 1)
                qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str," +
                        "0 visitado,0 conventa,0 concobranza,0 noventa from clientes where {0}>0 and rut_cve_n in " +
                        "(select rut_cve_n from rutas where rut_prev_n={1}) and est_cve_str<>'B' order by {0}", filtro, String.valueOf(conf.getRuta() )  ,filtro);
            else
                qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str," +
                        "0 visitado,0 conventa,0 concobranza,0 noventa from clientes where {0}>0 and rut_cve_n={1} and est_cve_str<>'B' order by {0}", filtro, String.valueOf(conf.getRuta()),filtro);

        }
        else
        {
            if (conf.getPreventa() == 1)
                qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str," +
                        "0 visitado,0 conventa,0 concobranza,0 noventa from clientes where rut_cve_n in " +
                        "(select rut_cve_n from rutas where rut_prev_n={1}) est_cve_str<>'B' order by {0}", String.valueOf(conf.getRuta()), filtro );
            else
                qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str," +
                        "0 visitado,0 conventa,0 concobranza,0 noventa from clientes where rut_cve_n={0}  and est_cve_str<>'B' order by {0}", String.valueOf(conf.getRuta()), filtro);
        }

        ArrayList<DataTableLC.Clientes1> clientes;

        String json = BaseLocal.Select(qry,application);
        clientes = ConvertirRespuesta.getClientes1Json(json);

        return clientes;
    }


}
