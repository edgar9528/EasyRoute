package com.tdt.easyroute.Clases;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Modelos;
import com.tdt.easyroute.Model.Preventa;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.Model.Venta;
import com.tdt.easyroute.Model.Visita;
import com.tdt.easyroute.Model.VisitaPrev;
import com.tdt.easyroute.R;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Utils {

    public static String Encriptar(String _cadenaAencriptar) {
        byte[] bytes = _cadenaAencriptar.getBytes(StandardCharsets.UTF_16LE);
        String base64 = android.util.Base64.encodeToString(bytes, Base64.NO_WRAP);

        return base64;
    }

    /// Esta función desencripta la cadena que le envíamos en el parámentro de entrada.
    public static String DesEncriptar(String _cadenaAdesencriptar) {
        byte[] data1 = Base64.decode(_cadenaAdesencriptar, Base64.NO_WRAP);
        String text1 = null;
        try {
            text1 = new String(data1, StandardCharsets.UTF_16LE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text1;
    }

    public static String LeefConfig(String valor, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("ConfiguracionPreferences", Context.MODE_PRIVATE);

        switch (valor) {
            case "servidor":
                valor = sharedPref.getString("servidor", "http://192.168.0.95/EasyRoute.asmx");
                break;
            case "timeout":
                valor = sharedPref.getString("timeout", "15000");
                break;
            case "empresa":
                valor = sharedPref.getString("empresa", "1");
                break;
            case "ruta":
                valor = sharedPref.getString("ruta", "0");
                break;
            case "imp":
                valor = sharedPref.getString("imp", "COM1");
                break;
            default:

                break;
        }
        return valor;
    }

    public static void CreafConfig(String servidor, String timeout, String empresa, String ruta, String imp, Application app) {
        SharedPreferences sharedPref = app.getSharedPreferences("ConfiguracionPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("servidor", servidor);
        editor.putString("timeout", timeout);
        editor.putString("empresa", empresa);
        editor.putString("ruta", ruta);
        editor.putString("imp", imp);

        editor.apply();
    }

    public static void guardarImpresora(String imp, Application app) {
        SharedPreferences sharedPref = app.getSharedPreferences("ConfiguracionPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("imp", imp);
        editor.apply();
    }

    public static void ActualizaConf(String config, String valor, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("ConfiguracionPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        switch (config) {
            case "servidor":
                editor.putString("servidor", valor);
                break;
            case "timeout":
                editor.putString("timeout", valor);
                break;
            case "empresa":
                editor.putString("empresa", valor);
                break;
            case "ruta":
                editor.putString("ruta", valor);
                break;
        }

        editor.apply();
    }

    public static String Version() {
        String version = "1.0.1";

        return version;
    }

    public static Configuracion ObtenerConf(Application app) {
        Configuracion cf = null;
        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(app, app.getString(R.string.nombreBD), null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            String qry = "Select * from ConfiguracionHH where est_cve_str='A'  and conf_fechafin_dt is null";
            Cursor cursor = bd.rawQuery(qry, null);

            if (cursor.getCount() > 0) {
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

        } catch (Exception e) {
            Log.d("salida", "error utils" + e.toString());
            return cf;
        }
    }

    public static Configuracion ObtenerConf2(Application app) {
        Configuracion cf = null;
        try {

            DatabaseHelper databaseHelper = new DatabaseHelper(app, app.getString(R.string.nombreBD), null, 1);
            SQLiteDatabase bd = databaseHelper.getReadableDatabase();

            String qry = "Select * from ConfiguracionHH where est_cve_str='A'";
            Cursor cursor = bd.rawQuery(qry, null);

            if (cursor.getCount() > 0) {
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

        } catch (Exception e) {
            Log.d("salida", "error utils" + e.toString());
            return cf;
        }
    }

    public static boolean getBool(String cad) {
        boolean var;

        try {
            if (cad.equals("true") || cad.equals("1")) {
                var = true;
            } else {
                var = false;
            }
        }catch (Exception e)
        {
            var=false;
        }

        return var;
    }

    public static String FechaLocal() {
        Date d = new Date();
        CharSequence s = DateFormat.format("yyyy-MM-dd", d.getTime());
        return s.toString();
    }

    public static String FechaFormato(String fecha) {
        try
        {
            SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatter1.parse(fecha);
            CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
            return s.toString();
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String FechaHoraFormato(String fecha) {
        try
        {
            SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.getDefault());
            Date date = formatter1.parse(fecha);
            CharSequence s = DateFormat.format("yyyy-MM-dd H:mm:ss", date.getTime());
            return s.toString();
        } catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static String FechaModificarDias(String fecha, int dias)
    {
        try {
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = formatter1.parse(fecha);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0

            CharSequence s = DateFormat.format("yyyy-MM-dd", calendar.getTime());

            return s.toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Date FechaDT(String fecha)
    {
        try
        {
            SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            return formatter1.parse(fecha);

        }catch (Exception e)
        {
            return null;
        }
    }

    public static String HoraLocal() {
        Date d = new Date();
        CharSequence s = DateFormat.format("HH:mm:ss", d.getTime());
        return s.toString();
    }

    public static String FechaWS(String fechaWS) {
        String fecha = "";
        String originalStringFormat = "yyyy-MM-dd'T'HH:mm:ss";
        String desiredStringFormat = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat readingFormat = new SimpleDateFormat(originalStringFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(desiredStringFormat);

        try {
            Date date = readingFormat.parse(fechaWS);
            fecha = outputFormat.format(date);

        } catch (Exception e) {
            Log.d("salida", "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return fecha;
    }

    public static boolean FechasIguales(String local, String ws) {
        boolean iguales;

        try {

            local = local.substring(0, local.length() - 3);
            ws = ws.substring(0, ws.length() - 3);

            if (local.equals(ws))
                iguales = true;
            else
                iguales = false;

            Log.d("salida", "tlo: " + local);
            Log.d("salida", "tws: " + ws);

        } catch (Exception e) {
            Log.d("salida", "FechasIguales: " + e.getMessage());
            iguales = false;
        }

        return iguales;
    }

    public static void RegresarInicio(Activity activity) {
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.regresarInicio();
    }

    public static String CalcularDia(Application application) {
        String[] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int numeroDia = cal.get(Calendar.DAY_OF_WEEK) - 1;

        Configuracion conf = Utils.ObtenerConf(application);

        String filtro = "";

        String dia = dias[numeroDia];

        if (conf.getPreventa() == 1) {
            if (dias[numeroDia].equals("Sábado")) {
                dia = "Lunes";
            } else
                dia = dias[numeroDia + 1];
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

    public static int diaActualL_D()
    {
        //regresa el numero de dia, comenzando de lunes a domingo
        String[] d ={"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        List<String> dias = Arrays.asList(d);

        String[] dB ={"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado","Domingo"};
        List<String> diasB = Arrays.asList(dB);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int numeroDia = cal.get(Calendar.DAY_OF_WEEK) - 1;



        String dia = dias.get(numeroDia);

        Log.d("salida","dia actual: "+dia);

        return diasB.indexOf(dia);
    }

    public static String DiaActual()
    {
        String[] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int numeroDia = cal.get(Calendar.DAY_OF_WEEK) - 1;

        return dias[numeroDia];
    }

    public static Modelos.Indicadores ObtenerProductividad(int ruta, Application application) {
        Modelos.Indicadores ip = new Modelos.Indicadores();
        ArrayList<DataTableWS.Ruta> Rutas;
        ArrayList<DataTableLC.Clientes1> clientes;
        DataTableWS.Ruta cr;

        String WorkDay = CalcularDia(application);

        try {

            String json = BaseLocal.Select(string.formatSql("Select * from rutas where rut_cve_n={0}", String.valueOf(ruta)), application);
            Rutas = ConvertirRespuesta.getRutasJson(json);

            if (Rutas != null && Rutas.size() > 0) {
                cr = Rutas.get(0);
                int calculo;

                ip.setPorcentajeRequerido(Integer.parseInt(cr.getRut_productividad_n()));
                ip.setPorcentajeRequeridoVentas(Integer.parseInt(cr.getRut_efectividad_n()));

                clientes = ListarClientes(true, application);

                ip.setValorMercado(clientes.size());


                //clientes
                calculo = 0;
                for (int i = 0; i < clientes.size(); i++)
                    if (clientes.get(i).getCli_prospecto_n().equals("0")) calculo++;

                ip.setClientes(calculo);

                //prospectos
                calculo = 0;
                for (int i = 0; i < clientes.size(); i++)
                    if (clientes.get(i).getCli_prospecto_n().equals("1")) calculo++;
                ip.setProspectos(calculo);


                //VisitasRequeridas
                int vr = (ip.getClientes() * ip.getPorcentajeRequerido()) / 100;
                if (ip.getClientes() > 0) {
                    if ((vr * 100 / ip.getClientes()) < ip.getPorcentajeRequerido())
                        vr++;
                } else
                    vr = 0;

                ip.setVisitasRequeridas(vr);


                //VentasRequeridas
                int vtar = (ip.getClientes() * ip.getPorcentajeRequeridoVentas()) / 100;
                if (ip.getClientes() > 0) {
                    if ((vtar * 100 / ip.getClientes()) < ip.getPorcentajeRequeridoVentas())
                        vtar++;
                } else
                    vtar = 0;
                ip.setVentasRequeridas(vtar);


                //Calcular productividad
                ArrayList<DataTableLC.BitacoraHH> bitacora;

                json = BaseLocal.Select(string.formatSql("select b.* from bitacorahh b inner join clientes c on b.cli_cve_n=c.cli_cve_n where {0}>0", WorkDay), application);
                bitacora = ConvertirRespuesta.getBitacoraHHJson(json);


                ArrayList<DataTableLC.BitacoraHH> res = null;
                DataTableLC.BitacoraHH b;
                for (int i = 0; i < clientes.size(); i++) {
                    DataTableLC.Clientes1 r = clientes.get(i);

                    //Con venta
                    res = new ArrayList<>();
                    for (int j = 0; j < bitacora.size(); j++) {
                        b = bitacora.get(j);
                        if (b.getCli_cve_n().equals(r.getCli_cve_n()) && (b.getBit_comentario_str().equals("VISITA CON VENTA") || b.getBit_comentario_str().equals("VISITA CON PREVENTA")))
                            res.add(b);
                    }

                    if (res.size() > 0) {
                        clientes.get(i).setVisitado("1");
                        clientes.get(i).setConventa("1");
                    }

                    //Cobranza
                    res = new ArrayList<>();
                    for (int j = 0; j < bitacora.size(); j++) {
                        b = bitacora.get(j);
                        if (b.getCli_cve_n().equals(r.getCli_cve_n()) && b.getBit_comentario_str().equals("VISITA CON COBRANZA"))
                            res.add(b);
                    }

                    if (res.size() > 0) {
                        clientes.get(i).setVisitado("1");
                        clientes.get(i).setConcobranza("1");
                    }

                    //No Venta

                    res = new ArrayList<>();
                    for (int j = 0; j < bitacora.size(); j++) {
                        b = bitacora.get(j);
                        if (b.getCli_cve_n().equals(r.getCli_cve_n()) && b.getBit_operacion_str().equals("NO VENTA"))
                            res.add(b);
                    }

                    if (res.size() > 0) {
                        clientes.get(i).setVisitado("1");
                        clientes.get(i).setNoventa("1");
                    }

                }


                //Clientes Visitados
                calculo = 0;
                for (int i = 0; i < clientes.size(); i++)
                    if (clientes.get(i).getCli_prospecto_n().equals("0") && Integer.parseInt(clientes.get(i).getVisitado()) > 0)
                        calculo++;
                ip.setProspectos(calculo);

                //Clientes con venta
                calculo = 0;
                for (int i = 0; i < clientes.size(); i++)
                    if (clientes.get(i).getCli_prospecto_n().equals("0") && Integer.parseInt(clientes.get(i).getConventa()) > 0)
                        calculo++;
                ip.setConVenta(calculo);


                //Clientes con cobranza
                calculo = 0;
                for (int i = 0; i < clientes.size(); i++)
                    if (clientes.get(i).getCli_prospecto_n().equals("0") && Integer.parseInt(clientes.get(i).getConcobranza()) > 0)
                        calculo++;
                ip.setConCobranza(calculo);


                //Clientes con noventa
                calculo = 0;
                for (int i = 0; i < clientes.size(); i++)
                    if (clientes.get(i).getCli_prospecto_n().equals("0") && Integer.parseInt(clientes.get(i).getNoventa()) > 0)
                        calculo++;
                ip.setConCobranza(calculo);

                //Prospectos Visitados
                calculo = 0;
                for (int i = 0; i < clientes.size(); i++)
                    if (clientes.get(i).getCli_prospecto_n().equals("1") && Integer.parseInt(clientes.get(i).getVisitado()) > 0)
                        calculo++;
                ip.setConCobranza(calculo);


                if (ip.getClientes() > 0) {
                    ip.setPorcentajeRealizado(ip.getVisitasClientes() * 100 / ip.getClientes());
                    ip.setPorcentajeRealizadoVentas(ip.getConVenta() * 100 / ip.getClientes());
                } else {
                    ip.setPorcentajeRealizado(0);
                    ip.setPorcentajeRealizadoVentas(0);
                }
            }


        } catch (Exception e) {
            Log.d("salida", "Error: " + e.toString());
            return null;
        }

        return ip;
    }

    public static ArrayList<DataTableLC.Clientes1> ListarClientes(boolean dia, Application application) {
        Configuracion conf = Utils.ObtenerConf(application);

        String filtro = CalcularDia(application);

        String qry;

        if (dia) {
            if (conf.getPreventa() == 1)
                qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str," +
                        "0 visitado,0 conventa,0 concobranza,0 noventa from clientes where {0}>0 and rut_cve_n in " +
                        "(select rut_cve_n from rutas where rut_prev_n={1}) and est_cve_str<>'B' order by {0}", filtro, String.valueOf(conf.getRuta()), filtro);
            else
                qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str," +
                        "0 visitado,0 conventa,0 concobranza,0 noventa from clientes where {0}>0 and rut_cve_n={1} and est_cve_str<>'B' order by {0}", filtro, String.valueOf(conf.getRuta()), filtro);

        } else {
            if (conf.getPreventa() == 1)
                qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str," +
                        "0 visitado,0 conventa,0 concobranza,0 noventa from clientes where rut_cve_n in " +
                        "(select rut_cve_n from rutas where rut_prev_n={1}) est_cve_str<>'B' order by {0}", String.valueOf(conf.getRuta()), filtro);
            else
                qry = string.formatSql("select cli_cve_n,cli_cveext_str,cli_prospecto_n,est_cve_str," +
                        "0 visitado,0 conventa,0 concobranza,0 noventa from clientes where rut_cve_n={0}  and est_cve_str<>'B' order by {0}", String.valueOf(conf.getRuta()), filtro);
        }

        ArrayList<DataTableLC.Clientes1> clientes;

        String json = BaseLocal.Select(qry, application);
        clientes = ConvertirRespuesta.getClientes1Json(json);

        return clientes;
    }

    public static void msgError(Context context,String mensaje, String exception)
    {
        String mensajeStr = mensaje +"\n\n"+
                            context.getResources().getString(R.string.title_detalles)
                            +": "+exception;

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
        dialogo1.setTitle(context.getResources().getString(R.string.msg_error));
        dialogo1.setMessage(mensajeStr);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(context.getString(R.string.bt_aceptar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }

    public static void msgInfo(Context context,String mensaje)
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
        dialogo1.setMessage(mensaje);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(context.getString(R.string.bt_aceptar), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialogo1.show();
    }

    public static double Distancia(String gps1, String gps2)
    {
        try
        {
            if (gps1.equals("NO VALIDA") || gps2.equals("NO VALIDA"))
                return -1;

            if (gps1.isEmpty() || gps2.isEmpty())
                return -2;

            double Longitud1;
            double Latitud1;
            double Longitud2;
            double Latitud2;

            Latitud1 = Double.parseDouble(gps1.split(",")[0]);
            Latitud2 = Double.parseDouble(gps2.split(",")[0]);

            Longitud1 = Double.parseDouble(gps1.split(",")[1]);
            Longitud2 = Double.parseDouble(gps2.split(",")[1]);

            double lats = (double) (Latitud1 - Latitud2);
            double lngs = (double) (Longitud1 - Longitud2);

            //Paso a metros
            double latm = lats * 60 * 1852;
            double lngm = (lngs * Math.cos((double) Latitud1 * Math.PI / 180)) * 60 * 1852;
            double distInMeters = Math.sqrt(Math.pow(latm, 2) + Math.pow(lngm, 2));

            Log.d("salida","LA DISTANCIA ES: "+distInMeters);

            return distInMeters;

        }catch (Exception e)
        {
            return -1;
        }
    }

    public static Visita ObtenerVisitaBebidas(long cliente, Context context)
    {
        Visita vis = null;
        vis = new Visita();
        vis.cli_cve_n = cliente;

        try
        {
            String sql, json;
            ArrayList<DataTableLC.Venta> dtVen = new ArrayList<>();
            sql = string.formatSql2("Select * from ventas where cli_cve_n={0}", String.valueOf(cliente) );
            json = BaseLocal.Select(sql, context);
            dtVen = ConvertirRespuesta.getVentaJson(json);

            if(dtVen!=null)
            {
                Venta[] ventasConsulta = new Venta[dtVen.size()];

                for (int i = 0; i < dtVen.size(); i++)
                {
                    Venta vta = new Venta(dtVen.get(i));

                    ArrayList<DataTableLC.VentasDet> dtVenDet = new ArrayList<>();
                    sql = string.formatSql2("select d.* from ventasdet d  " +
                            "inner join productos p on d.prod_cve_n=p.prod_cve_n " +
                            "inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                            "where d.ven_folio_str='{0}' and c.cat_desc_str not in ('AGUA','REFRESCO','BEBIDA ENERGIZANTE','JUGO')", vta.getVenta().getVen_folio_str());
                    json = BaseLocal.Select(sql, context);
                    dtVenDet = ConvertirRespuesta.getVentasDetJson(json);

                    if (dtVenDet != null) {
                        DataTableLC.VentasDet[] Detalles = new DataTableLC.VentasDet[dtVenDet.size()];
                        for (int j = 0; j < dtVenDet.size(); j++) {
                            Detalles[j] = dtVenDet.get(j);
                        }
                        vta.setDetalles(Detalles);
                    }


                    //--------------- Bebidas ------------------
                    ArrayList<DataTableLC.VentasDet> dtVenBeb = new ArrayList<>();

                    sql = string.formatSql2("select d.* from ventasdet d " +
                            "inner join productos p on d.prod_cve_n=p.prod_cve_n " +
                            "inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                            "where d.ven_folio_str='{0}' and c.cat_desc_str in ('AGUA','REFRESCO','BEBIDA ENERGIZANTE','JUGO')", vta.getVenta().getVen_folio_str());
                    json = BaseLocal.Select(sql, context);
                    dtVenBeb = ConvertirRespuesta.getVentasDetJson(json);

                    if (dtVenBeb != null) {
                        DataTableLC.VentasDet[] Bebidas = new DataTableLC.VentasDet[dtVenBeb.size()];
                        for (int j = 0; j < dtVenBeb.size(); j++) {
                            Bebidas[j] = dtVenBeb.get(j);
                        }
                        vta.setBebidas(Bebidas);
                    }

                    //--------------- Envases ------------------//
                    ArrayList<DataTableLC.VentasEnv> dtVenEnv = new ArrayList<>();

                    sql = string.formatSql2("select * from ventaenv where ven_folio_str='{0}'", vta.getVenta().getVen_folio_str());
                    json = BaseLocal.Select(sql, context);
                    dtVenEnv = ConvertirRespuesta.getDtVentasEnv(json);

                    if (dtVenEnv != null) {
                        DataTableLC.VentasEnv[] Envases = new DataTableLC.VentasEnv[dtVenEnv.size()];

                        for (int j = 0; j < dtVenEnv.size(); j++) {
                            Envases[j] = dtVenEnv.get(j);
                        }
                        vta.setEnvase(Envases);
                    }

                    ventasConsulta[i] = vta;
                }

                vis.setVentas(ventasConsulta);
            }

            ArrayList<DataTableLC.Pagos> dtp = new ArrayList<>();
            sql = string.formatSql2("Select * from pagos where cli_cve_n={0} and pag_cobranza_n=0", String.valueOf(cliente) );
            json = BaseLocal.Select(sql,context);
            dtp = ConvertirRespuesta.getPagosALJson(json);

            if(dtp!=null)
            {
                DataTableLC.Pagos[] pagos = new DataTableLC.Pagos[dtp.size()];

                for(int i=0; i<dtp.size();i++)
                    pagos[i] = dtp.get(i);

                vis.setPagos(pagos);
            }

            ArrayList<DataTableLC.Pagos> dtc = new ArrayList<>();

            sql = string.formatSql2("Select * from pagos where cli_cve_n={0} and conf_cve_n in " +
                    "(select conf_cve_n from configuracionhh where est_cve_str='A') and pag_envase_n=0 and pag_cobranza_n=1 ", String.valueOf(cliente));
            json = BaseLocal.Select(sql,context);
            dtc = ConvertirRespuesta.getPagosALJson(json);

            if(dtc!=null)
            {
                DataTableLC.Pagos[] cobranza = new DataTableLC.Pagos[dtc.size()];
                for(int i=0; i<dtc.size();i++)
                    cobranza[i]= dtc.get(i);
                vis.setCobranza(cobranza);
            }

            ArrayList<DataTableLC.Creditos> dtcr = new ArrayList<>();

            sql = string.formatSql2("Select * from creditos where cli_cve_n={0} and cred_esenvase_n=0 and cred_especial_n=0", String.valueOf(cliente));
            json = BaseLocal.Select(sql, context);
            dtcr = ConvertirRespuesta.getCreditos2Json(json);

            if(dtcr!=null)
            {
                DataTableLC.Creditos[] creditos = new DataTableLC.Creditos[dtcr.size()];
                for(int i=0; i<dtcr.size();i++)
                {
                    creditos[i] = dtcr.get(i);
                }
                vis.setCreditos(creditos);
            }

            ArrayList<DataTableLC.Creditos> dtcres = new ArrayList<>();
            sql = string.formatSql2("Select * from creditos where cli_cve_n={0} and cred_esenvase_n=0 and cred_especial_n=1", String.valueOf(cliente));
            json = BaseLocal.Select(sql, context);
            dtcres = ConvertirRespuesta.getCreditos2Json(json);

            if(dtcres!=null)
            {
                DataTableLC.Creditos[] creditose = new DataTableLC.Creditos[dtcres.size()];
                for(int i=0; i< dtcres.size();i++)
                {
                    creditose[i] = dtcres.get(i);
                }
                vis.setCreditose(creditose);
            }

            ArrayList<DataTableLC.Creditos> dtcrenv = new ArrayList<>();
            sql = string.formatSql2("Select * from creditos where cli_cve_n={0} and cred_esenvase_n=1 and cred_especial_n=0", String.valueOf(cliente));
            json = BaseLocal.Select(sql, context);
            dtcrenv = ConvertirRespuesta.getCreditos2Json(json);

            if(dtcrenv!=null)
            {
                DataTableLC.Creditos[] credenv = new DataTableLC.Creditos[dtcrenv.size()];
                for(int i=0; i<dtcrenv.size(); i++)
                {
                    credenv[i]= dtcrenv.get(i);
                }
                vis.setCredenv(credenv);
            }

            ArrayList<DataTableLC.Pagos> dtdv = new ArrayList<>();

            sql = string.formatSql2("Select * from pagos where cli_cve_n={0} and conf_cve_n in " +
                    "(select conf_cve_n from configuracionhh where est_cve_str='A') and pag_envase_n=1 and pag_cobranza_n=1 ", String.valueOf(cliente));
            json = BaseLocal.Select(sql, context);
            dtdv = ConvertirRespuesta.getPagosALJson(json);

            if(dtdv!=null)
            {
                DataTableLC.Pagos[] devenvase = new DataTableLC.Pagos[dtdv.size()];
                for(int i=0; i<dtdv.size();i++)
                {
                    devenvase[i]= dtdv.get(i);
                }
                vis.setDevenvase(devenvase);
            }


        }catch (Exception e)
        {
            Utils.msgError(context, context.getString(R.string.err_utils1), e.getMessage());
            Log.d("salida","EROR: "+ e.getMessage());
        }

        return vis;
    }


    public static void ImprimirVentaBebidas(Visita v, boolean ReImp, Context context, Configuracion conf) throws ParseException {
        int c = 38;
        String ruta = "";
        String vendedor = "";
        String direccion = "";
        String p="";
        int pc = 0;

        String sql = string.formatSql2("Select rut_desc_str from rutas where rut_cve_n={0}", conf.getRutaStr());
        ruta = BaseLocal.SelectDato(sql, context);

        p+= "RUTA: "+ ruta.toUpperCase() +"\n\n";

        sql=string.formatSql2("Select * from usuarios where usu_cve_str='{0}'", conf.getUsuario());
        String json = BaseLocal.Select(sql, context);

        DataTableWS.Usuarios usu = ConvertirRespuesta.getUsuarioLocal(json);

        if(usu!=null)
            p+= string.formatSql("ASESOR: \n{0} {1} {2}\n\n",usu.getUsu_nom_str(),usu.getUsu_app_str(),usu.getUsu_apm_str());

        ArrayList<DataTableWS.Clientes> alCli;
        DataTableWS.Clientes dtc;

        sql = string.formatSql("Select * from clientes where cli_cve_n={0}", String.valueOf(v.getCli_cve_n()) );
        json = BaseLocal.Select(sql,context);
        alCli = ConvertirRespuesta.getClientesJson(json);

        if(alCli.size()>0)
        {
            dtc=alCli.get(0);

            pc = Integer.parseInt(dtc.getCli_plazocredito_n());

            p+= "CLIENTE: " + dtc.getCli_cveext_str()+"\n\n";
            p+= "RAZON SOCIAL: \n" + dtc.getCli_nombrenegocio_str()+"\n\n";
            p+= "NEGOCIO: \n" + dtc.getCli_razonsocial_str()+"\n\n";

            ArrayList<DataTableWS.Direcciones>  dtd;
            sql = string.formatSql("Select * from direcciones where cli_cve_n={0}   and dir_cve_n={1}",
                   String.valueOf( v.getCli_cve_n() ) , dtc.getCli_dirent_n());
            json = BaseLocal.Select(sql, context);
            dtd = ConvertirRespuesta.getDireccionesJson(json);

            if(dtd.size()>0)
            {
                DataTableWS.Direcciones r = dtd.get(0);
                p+=string.formatSql("DIRECCION: \n{0} {1} {2} {3} {4} C.P. {5} \n\n", r.getDir_calle_str(),
                        r.getDir_noext_str(), r.getDir_colonia_str(), r.getDir_municipio_str(), r.getDir_estado_str(),
                        r.getDir_codigopostal_str());
            }

            if(dtc.getCli_Tel1_str()!=null && !dtc.getCli_Tel1_str().equals("0"))
                p+="TEL: "+dtc.getCli_Tel1_str()+"\n";

        }

        p+= "FECHA IMP: " + Utils.FechaLocal()+" "+Utils.HoraLocal()+"\n\n";
        p+= "       MENSAJE IMPORTANTE\n";

        p+= " RECUERDE SIEMPRE SOLICITAR SU\n";
        p+= "TICKET DE COMPRA A SU ASESOR DE\n";
        p+= "  VENTAS, EL MISMO INCLUYE SU \n";
        p+= "         SALDO AL DIA.\n\n";

        if (ReImp)
        {
            p+= "       *** C O P I A ***\n\n";
        }
        else
        {
            p+= "    *** O R I G I N A L ***\n\n";
        }

        ArrayList<DataTableWS.Productos> dtp = new ArrayList<>();

        json = BaseLocal.Select( "Select * from productos", context );
        dtp = ConvertirRespuesta.getProductosJson(json);

        ArrayList<DataTableWS.FormasPago> dtfp = new ArrayList<>();
        json = BaseLocal.Select("Select * from formaspago",context );
        dtfp = ConvertirRespuesta.getFormasPagoJson(json);

        ArrayList<DataTableLC.Pagos> dtPagos = new ArrayList<>();
        sql =string.formatSql("select * from pagos where cli_cve_n={0}", String.valueOf( v.getCli_cve_n() )  );
        json = BaseLocal.Select(sql, context);
        dtPagos = ConvertirRespuesta.getPagosALJson(json);

        double SubtotalVenta = 0;
        double SubtotalVtaEnv = 0;
        double SubTotalBase = 0;

        if(v.ventas!=null)
        {
            for(Venta vta : v.ventas)
            {
                p+="FECHA VENTA: " + Utils.FechaHoraFormato( vta.getVenta().getVen_fecha_dt() ) +"\n" ;
                p+="FOLIO: " + vta.getVenta().getVen_folio_str()+"\n\n";

                double descvta = 0;

                if (vta.getDetalles() != null || vta.getBebidas()!=null)
                {
                    double SubTotalF = 0;
                    double SubTotalCvz = 0;
                    double SubTotalBeb = 0;

                    p+="             VENTA\n";
                    p+="* SKU    DESC  CAN PRECIO SUBTOT\n\n";

                    String aah = "";
                    if (vta.getDetalles() != null)
                    {
                        p+="------------CERVEZA------------\n";

                        for (int i = 0; i < vta.getDetalles().length; i++)
                        {
                            String desc = "";
                            if( !Utils.getBool( vta.getDetalles()[i].getProd_envase_n() )  )
                            {
                                DataTableWS.Productos r=null;
                                for(int j=0; j< dtp.size();j++)
                                    if(vta.getDetalles()[i].getProd_cve_n().equals( dtp.get(j).getProd_cve_n()) )
                                        r=dtp.get(j);

                                if(r!=null)
                                {
                                    desc = r.getProd_desc_str();
                                }

                                if( Utils.getBool( vta.getDetalles()[i].getProd_promo_n() ) ||
                                    ( Double.parseDouble( vta.getDetalles()[i].getLpre_precio_n() )  < Double.parseDouble( vta.getDetalles()[i].getLpre_base_n() )  )  )
                                {
                                    aah = "*";
                                    descvta += ( Double.parseDouble( vta.getDetalles()[i].getLpre_base_n() )  - Double.parseDouble(vta.getDetalles()[i].getLpre_precio_n())) * Double.parseDouble(vta.getDetalles()[i].getProd_cant_n());
                                }
                                else
                                    aah = " ";

                                p+=aah+" ";
                                p+= Impresora.DarTamañoDer(vta.getDetalles()[i].getProd_sku_str(),6 ) +" ";
                                p+= Impresora.DarTamañoDer(desc,5)+" ";

                                p+= Impresora.DarTamañoIzq( vta.getDetalles()[i].getProd_cant_n(),3 )+" ";
                                p+= Impresora.DarTamañoIzq( vta.getDetalles()[i].getLpre_precio_n(), 6 )  +" ";
                                p+= Impresora.DarTamañoIzq( vta.getDetalles()[i].getProd_subtotal_n(), 6 )  +"\n";

                                SubTotalBase += ( Double.parseDouble(vta.getDetalles()[i].getLpre_base_n())* Double.parseDouble( vta.getDetalles()[i].getProd_cant_n() )  ) ;
                                SubTotalF += Double.parseDouble(vta.getDetalles()[i].getProd_subtotal_n());
                                SubTotalCvz += Double.parseDouble(vta.getDetalles()[i].getProd_subtotal_n());
                            }
                        }
                        p+="TOTAL CERVEZA: "+ string.FormatoPesos(SubTotalCvz)+"\n\n";
                    }

                    //-------------------- Bebidas ----------------------------

                    if ( vta.getBebidas()!= null)
                    {
                        p+="---------OTRAS BEBIDAS---------\n";
                        for (int i = 0; i < vta.getBebidas().length; i++)
                        {
                            String desc = "";
                            if ( !Utils.getBool(vta.getBebidas()[i].getProd_envase_n()))
                            {
                                DataTableWS.Productos r=null;
                                for(int j=0; j< dtp.size();j++)
                                    if(vta.getBebidas()[i].getProd_cve_n().equals( dtp.get(j).getProd_cve_n()) )
                                        r=dtp.get(j);

                                if (r!=null)
                                {
                                    desc = r.getProd_desc_str();
                                }

                                if( Utils.getBool( vta.getBebidas()[i].getProd_promo_n() ) ||
                                        ( Double.parseDouble( vta.getBebidas()[i].getLpre_precio_n() )  < Double.parseDouble( vta.getBebidas()[i].getLpre_base_n() )  )  )
                                {
                                    aah = "*";
                                    descvta += ( Double.parseDouble( vta.getBebidas()[i].getLpre_base_n() )  - Double.parseDouble(vta.getBebidas()[i].getLpre_precio_n())) * Double.parseDouble(vta.getBebidas()[i].getProd_cant_n());
                                }
                                else
                                    aah = " ";


                                p+=aah+" ";
                                p+= Impresora.DarTamañoDer(vta.getBebidas()[i].getProd_sku_str(),6 )+" ";
                                p+= Impresora.DarTamañoDer(desc,5)+" ";

                                p+= Impresora.DarTamañoIzq( vta.getBebidas()[i].getProd_cant_n(),3 )+" ";
                                p+= Impresora.DarTamañoIzq( vta.getBebidas()[i].getLpre_precio_n(), 6 )  +" ";
                                p+= Impresora.DarTamañoIzq( vta.getBebidas()[i].getProd_subtotal_n(), 6 )  +"\n";

                                SubTotalBase += ( Double.parseDouble(vta.getBebidas()[i].getLpre_base_n())* Double.parseDouble( vta.getBebidas()[i].getProd_cant_n() )  ) ;
                                SubTotalF += Double.parseDouble(vta.getBebidas()[i].getProd_subtotal_n());
                                SubTotalBeb += Double.parseDouble(vta.getBebidas()[i].getProd_subtotal_n());

                            }
                        }
                        p+="TOTAL OTRAS BEBIDAS: "+ string.FormatoPesos(SubTotalBeb)+"\n\n";
                    }
                    //-------------------- Bebidas ------------------------//
                    SubtotalVenta += SubTotalF;

                    p+="TOTAL FACTURA: " + string.FormatoPesos(SubTotalF)+"\n\n";

                    if (descvta > 0)
                    {
                        p+="ESTIMADO CLIENTE CON ESTA COMPRA\n";
                        p+= Impresora.Centrar( "USTED AHORRO "+ string.FormatoPesos(descvta) +" PESOS" ) +"\n";
                        p+= Impresora.Centrar( "EQUIVALENTE A "+ String.format("%.2f", descvta / SubTotalBase) +" DE AHORRO" ) +"\n\n" ;
                    }

                    double SubTotalE = 0;

                    if(vta.getEnvase()!=null)
                    {
                        p+= Impresora.Centrar("ENVASE")+"\n";
                        p+="SKU    DESC CAR ABO PRO VTA FIN"+"\n\n";

                        String desc = "";
                        for (int i = 0; i < vta.getEnvase().length; i++)
                        {
                            DataTableWS.Productos r=null;

                            for(int j=0; j< dtp.size();j++)
                                if(vta.getEnvase()[i].getProd_cve_n().equals( dtp.get(j).getProd_cve_n()) )
                                    r=dtp.get(j);

                            if (r!=null)
                            {
                                desc = r.getProd_desc_str();

                                p+= Impresora.DarTamañoDer( vta.getEnvase()[i].getProd_sku_str(),6 )+" ";
                                p+= Impresora.DarTamañoDer( desc,4 )+" ";
                                p+= Impresora.DarTamañoDer( vta.getEnvase()[i].getVen_cargo_n() ,3 )+" ";
                                p+= Impresora.DarTamañoDer( vta.getEnvase()[i].getVen_abono_n() ,3)+" ";
                                p+= Impresora.DarTamañoDer( vta.getEnvase()[i].getVen_regalo_n() ,3)+" ";
                                p+= Impresora.DarTamañoDer( vta.getEnvase()[i].getVen_venta_n() ,3)+" ";
                                p+= Impresora.DarTamañoDer( vta.getEnvase()[i].getVen_prestamo_n() ,3)+"\n";

                                SubTotalE += Double.parseDouble ( vta.getEnvase()[i].getVen_prestamo_n() ) * Double.parseDouble(vta.getEnvase()[i].getLpre_precio_n());
                            }
                        }
                        p+="TOTAL ENVASE: "+ String.format("%.2f",SubTotalE)+"\n\n";
                    }

                    SubtotalVtaEnv += SubTotalE;
                }
            }
        }
        else
        {
            p+= Impresora.Centrar("SIN VENTA")+"\n\n";
        }

        double SubTotalP = 0;
        double Pagos = 0;

        if (v.pagos != null)
        {
            p+=Impresora.Centrar("PAGOS")+"\n\n";
            p+="REF            PAGO        FORMA\n";
            String fp = "";

            for (int i = 0; i < v.pagos.length; i++)
            {
                fp = "";

                DataTableWS.FormasPago pf=null;
                for(int j=0; j < dtfp.size();j++)
                    if(v.pagos[i].getFpag_cve_n().equals( dtfp.get(j).getFpag_cve_n() ))
                    {
                        pf = dtfp.get(j);
                        j = dtfp.size();
                    }

                if (pf!=null)
                    fp = pf.getFpag_desc_str();

                p+= Impresora.DarTamañoDer( v.pagos[i].getPag_referencia_str(),14 ) + " ";
                p+= Impresora.DarTamañoIzq( string.FormatoPesos( v.pagos[i].getPag_abono_n() ) ,11 ) + " ";
                p+= Impresora.DarTamañoDer( fp,5 ) + "\n";

                Pagos += Double.parseDouble( v.pagos[i].getPag_abono_n() ) ;
                SubTotalP += Double.parseDouble( v.pagos[i].getPag_abono_n() );
            }

            p+="TOTAL PAGOS: "+ string.FormatoPesos( SubTotalP )+"\n\n";
        }
        else
        {
            p+= Impresora.Centrar("SIN PAGOS")+"\n\n";
        }

        double SubTotalC = 0;
        double Cobranza = 0;

        if (v.cobranza != null)
        {
            p+=Impresora.Centrar("COBRANZA")+"\n\n";
            p+="REF            PAGO        FORMA\n";
            String fp = "";

            for(int i=0; i< v.cobranza.length; i++)
            {
                fp = "";

                DataTableWS.FormasPago pf=null;
                for(int j=0; j < dtfp.size();j++)
                    if(v.cobranza[i].getFpag_cve_n().equals( dtfp.get(j).getFpag_cve_n() ))
                    {
                        pf = dtfp.get(j);
                        j = dtfp.size();
                    }

                if (pf!=null)
                    fp = pf.getFpag_desc_str();

                p+= Impresora.DarTamañoDer( v.cobranza[i].getPag_referencia_str(),14 ) + " ";
                p+= Impresora.DarTamañoIzq( string.FormatoPesos( v.cobranza[i].getPag_abono_n() ) ,11 ) + " ";
                p+= Impresora.DarTamañoDer( fp,5 ) + "\n";

                SubTotalC += Double.parseDouble( v.cobranza[i].getPag_abono_n() ) ;
                Cobranza += Double.parseDouble( v.cobranza[i].getPag_abono_n() );
            }

            p+="TOTAL COBRANZA: "+ string.FormatoPesos( SubTotalC )+"\n\n";

        }
        else
        {
            p+= Impresora.Centrar("SIN COBRANZA")+"\n\n";
        }

        if (v.devenvase != null)
        {
            p+=Impresora.Centrar("DEV. ENVASE")+"\n\n";

            double CantEnvDev = 0;

            p+="E SKU    ABONADO  REFERENCIA    \n";

            for (int i = 0; i < v.devenvase.length; i++)
            {
                p+= Utils.getBool( v.devenvase[i].getPag_especie_n() )?"*":" " + " ";
                p+= Impresora.DarTamañoDer( v.devenvase[i].getProd_sku_str(),6 )+" ";
                p+= Impresora.DarTamañoDer( v.devenvase[i].getProd_abono_n(),8 )+" ";
                p+= Impresora.DarTamañoDer( v.devenvase[i].getPag_referencia_str(),14 )+"\n";

                CantEnvDev += Double.parseDouble(v.devenvase[i].getProd_abono_n());
            }

            p+= "TOTAL DE ENVASE DEVUELTO: "+ String.format("%.2f",CantEnvDev);
        }
        else
        {
            p+= Impresora.Centrar("SIN DEVOLUCION DE ENVASE")+"\n\n";
        }

        double SubTotalCred = 0;
        double InicialCred = 0;
        boolean vencido = false;

        if (v.creditos != null)
        {
            p+=Impresora.Centrar("CREDITOS")+"\n\n";

            p+="REF            SALDO_AN SALDO_AC\n";

            for (int i = 0; i < v.creditos.length; i++)
            {
                ArrayList<DataTableLC.Pagos>  pf = new ArrayList<>();
                for(int j=0; j< dtPagos.size();j++)
                {
                    if( dtPagos.get(j).getPag_referencia_str().equals( v.creditos[i].getCred_referencia_str() )
                        && dtPagos.get(j).getPag_envase_n().equals("0")
                        && dtPagos.get(j).getPag_cobranza_n().equals("1"))
                    {
                        pf.add( dtPagos.get(j) ) ;
                    }
                }

                double saldoant = Double.parseDouble(v.creditos[i].getCred_monto_n()) - Double.parseDouble( v.creditos[i].getCred_abono_n() );
                double AbonoActCred = 0;

                for (DataTableLC.Pagos pfi : pf)
                {
                    AbonoActCred += Double.parseDouble(pfi.getPag_abono_n());
                }

                double saldo = Double.parseDouble( v.creditos[i].getCred_monto_n() )  - Double.parseDouble( v.creditos[i].getCred_abono_n() )  - AbonoActCred;

                if (v.creditos[i].getTrans_est_n() != null)
                    if (v.creditos[i].getTrans_est_n().equals("3"))
                        InicialCred += saldoant;

                p+= Impresora.DarTamañoDer(v.creditos[i].getCred_referencia_str(),14);
                double salAnt = Double.parseDouble( v.creditos[i].getCred_monto_n() ) - Double.parseDouble( v.creditos[i].getCred_abono_n() );
                p+= Impresora.DarTamañoDer( String.format("%.2f",salAnt ),8 ) +" ";
                p+= Impresora.DarTamañoDer( String.format("%.2f",saldo ),8 ) +"\n";

                SubTotalCred += saldo;


                SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.getDefault());
                Date fechaAc = formato.parse(Utils.FechaLocal()+" "+Utils.HoraLocal());
                Date fechaBd= formato.parse(v.creditos[i].getCred_fecha_dt());
                int dias=(int) ((fechaAc.getTime()-fechaBd.getTime())/86400000);

                if (dias > pc && saldo > 0)
                {
                    vencido = true;
                }
            }

            p+= "TOTAL CREDITOS: "+ string.FormatoPesos(SubTotalCred)+"\n\n";

            if (vencido)
            {
                p+= Impresora.Centrar( "*SU CUENTA TIENE SALDO VENCIDO*" )+ "\n\n";
            }
        }
        else
        {
            p+= Impresora.Centrar("SIN CREDITOS")+"\n\n";
        }

        double SubTotalCredEsp = 0;

        double SubTotalCredEnv = 0;
        double SaldoEnv = 0;
        vencido = false;

        if (v.credenv != null)
        {
            p+=Impresora.Centrar("CREDITOS DE ENVASE")+"\n\n";

            p+="REF            INI   ABO   FIN  \n";

            for (int i = 0; i < v.credenv.length; i++)
            {
                ArrayList<DataTableLC.Pagos>  pf = new ArrayList<>();
                for(int j=0; j< dtPagos.size();j++)
                {
                    if( dtPagos.get(j).getPag_referencia_str().equals( v.credenv[i].getCred_referencia_str() )
                            && dtPagos.get(j).getPag_envase_n().equals("1")
                            && dtPagos.get(j).getPag_cobranza_n().equals("1"))
                    {
                        pf.add( dtPagos.get(j) ) ;
                    }
                }

                double AboEnvDia = 0;

                for (DataTableLC.Pagos pfi : pf)
                {
                    AboEnvDia += Double.parseDouble(pfi.getProd_abono_n());
                }

                double saldo = Double.parseDouble( v.credenv[i].getProd_cant_n() ) - Double.parseDouble( v.credenv[i].getProd_cantabono_n() ) -  AboEnvDia;

                int can = Integer.parseInt( v.credenv[i].getProd_cant_n() ) - Integer.parseInt(v.credenv[i].getProd_cantabono_n());

                p+= Impresora.DarTamañoDer( v.credenv[i].getCred_referencia_str(), 14 ) + " ";
                p+= Impresora.DarTamañoIzq( String.valueOf(can),5 )+" ";
                p+= Impresora.DarTamañoIzq( String.valueOf(AboEnvDia),5 )+" ";
                p+= Impresora.DarTamañoIzq( String.valueOf(saldo),5 )+"\n";

                SubTotalCredEnv += saldo;
                SaldoEnv += saldo * Double.parseDouble( v.credenv[i].getProd_precio_n() );

                SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd H:mm:ss", Locale.getDefault());
                Date fechaAc = formato.parse(Utils.FechaLocal()+" "+Utils.HoraLocal());
                Date fechaBd= formato.parse(v.credenv[i].getCred_fecha_dt());
                int dias=(int) ((fechaAc.getTime()-fechaBd.getTime())/86400000);

                if (dias > pc && saldo > 0)
                {
                    vencido = true;
                }
            }


            p+="TOTAL DEUDA ENV CANT: " + SubTotalCredEnv+"\n\n";
            p+="TOTAL DEUDA ENV: " + SaldoEnv+"\n\n";
            if (vencido)
            {
                p+= Impresora.Centrar( "*SU CUENTA TIENE SALDO VENCIDO*" )+ "\n\n";
            }
        }
        else
        {
            p+= Impresora.Centrar("SIN CREDITOS DE ENVASE")+"\n\n";
        }

        p+= Impresora.Centrar("SALDOS")+"\n\n";

        p+= "+SALDO INICIAL: "+ string.FormatoPesos( InicialCred ) +"\n";
        p+= "+VENTA DEL DIA: "+ string.FormatoPesos( SubtotalVenta ) +"\n";
        p+= "-PAGOS: "+ string.FormatoPesos( Pagos )+"\n";
        p+= "-ABONOS: "+ string.FormatoPesos( Cobranza ) +"\n";
        p+= "=SALDO FINAL: "+ string.FormatoPesos (InicialCred + SubtotalVenta - Cobranza - Pagos)+"\n\n";
        p+= "SALDO EN EFECTIVO: "+ string.FormatoPesos (InicialCred + SubtotalVenta - Cobranza - Pagos)+"\n";
        p+= "SALDO EN ENVASE: "+ string.FormatoPesos(SaldoEnv)+"\n";
        p+= "SALDO TOTAL: "+ string.FormatoPesos (InicialCred + SubtotalVenta - Cobranza - Pagos + SaldoEnv)+"\n";

        p+="\n\n";
        p= p.replace("\n","\r");
        Impresora.imprimir(p,context);

    }


    public static VisitaPrev ObtenerVisitaPrevBebidas(long cliente, Context context)
    {
        VisitaPrev vis = null;
        vis = new VisitaPrev();
        vis.cli_cve_n = cliente;

        try
        {
            ArrayList<DataTableWS.Clientes>  dtCli = new ArrayList<>();
            DataTableWS.Clientes rcli= null;

            String sql, json;
            sql = string.formatSql("select * from clientes where cli_cve_n={0}", String.valueOf( cliente ) );
            json = BaseLocal.Select(sql, context);
            dtCli = ConvertirRespuesta.getClientesJson(json);

            if (dtCli.size() > 0)
                rcli = dtCli.get(0);

            ArrayList<DataTableWS.Preventa> dtVen;

            sql= string.formatSql2("Select * from preventa where cli_cve_n={0}", String.valueOf( cliente ) );
            json = BaseLocal.Select(sql,context);
            dtVen = ConvertirRespuesta.getPreventaJson(json);

            if (dtVen.size() > 0 )
            {
                Preventa[] Preventas = new Preventa[dtVen.size()];
                for (int k = 0; k < dtVen.size(); k++)
                {
                    DataTableWS.Preventa rv = dtVen.get(k);

                    Preventa vta = new Preventa( rv );

                    ArrayList<DataTableWS.PreventaDet> dtVenDet;

                    sql =string.formatSql("select d.* from preventadet d  " +
                            "inner join productos p on d.prod_cve_n=p.prod_cve_n " +
                            "inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                            "where d.prev_folio_str='{0}' and c.cat_desc_str not in ('AGUA','REFRESCO','BEBIDA ENERGIZANTE','JUGO')", vta.getPreventa().getPrev_folio_str());

                    json = BaseLocal.Select(sql, context);
                    dtVenDet = ConvertirRespuesta.getPreventaDetJson(json);

                    if(dtVenDet.size()>0)
                    {
                        DataTableWS.PreventaDet[] detalles = new DataTableWS.PreventaDet[dtVenDet.size()];
                        for(int j=0; j< dtVenDet.size();j++)
                        {
                            detalles[j] = dtVenDet.get(j);
                        }
                        vta.setDetalles(detalles);
                    }


                    //-------------  Bebidas -----------------------
                    ArrayList<DataTableWS.PreventaDet> dtVenBeb;
                    sql = string.formatSql("select d.* from preventadet d  " +
                            "inner join productos p on d.prod_cve_n=p.prod_cve_n " +
                            "inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                            "where d.prev_folio_str='{0}' and c.cat_desc_str in ('AGUA','REFRESCO','BEBIDA ENERGIZANTE','JUGO')",  vta.getPreventa().getPrev_folio_str());

                    json = BaseLocal.Select(sql, context);

                    dtVenBeb = ConvertirRespuesta.getPreventaDetJson(json);

                    if (dtVenBeb.size() > 0)
                    {
                        DataTableWS.PreventaDet[] bebidas = new DataTableWS.PreventaDet[dtVenBeb.size()];
                        for(int j=0; j< dtVenBeb.size();j++)
                        {
                            bebidas[j] = dtVenBeb.get(j);
                        }
                        vta.setBebidas(bebidas);
                    }
                    Preventas[k] = vta;
                }
                vis.setPreventas(Preventas);
            }


            if (vis.getPreventas() != null)
            {
                ArrayList<DataTableLC.PreventaPagos> dtp = null;
                sql = string.formatSql("Select pv.*,fp.fpag_desc_str from preventapagos pv inner join formaspago fp " +
                        "on pv.fpag_cve_n=fp.fpag_cve_n where prev_folio_str='{0}'",  vis.getPreventas()[0].getPreventa().getPrev_folio_str());
                json = BaseLocal.Select(sql, context);
                dtp= ConvertirRespuesta.getPreventaPagosLCJson(json);

                if (dtp!=null)
                {
                    DataTableLC.PreventaPagos[] Cobranza = new DataTableLC.PreventaPagos[dtp.size()];
                    for (int j = 0; j < dtp.size(); j++)
                    {
                        Cobranza[j] = dtp.get(j);
                    }
                    vis.setCobranza(Cobranza);
                }
            }

            ArrayList<DataTableLC.Creditos> dtcr = null;
            sql = string.formatSql("Select * from creditos where cli_cve_n={0} and cred_esenvase_n=0 and cred_especial_n=0", String.valueOf(cliente) );
            json = BaseLocal.Select(sql, context);
            dtcr = ConvertirRespuesta.getCreditos2Json(json);

            if(dtcr!=null)
            {
                DataTableLC.Creditos[] Creditos = new DataTableLC.Creditos[dtcr.size()];
                for(int j=0; j<dtcr.size();j++)
                {
                    Creditos[j] = dtcr.get(j);
                }
                vis.setCreditos(Creditos);
            }

            ArrayList<DataTableLC.Creditos> dtcrenv = null;
            sql= string.formatSql("Select * from creditos where cli_cve_n={0} and cred_esenvase_n=1 and cred_especial_n=0", String.valueOf(cliente) );
            json = BaseLocal.Select(sql, context);
            dtcrenv = ConvertirRespuesta.getCreditos2Json(json);

            if (dtcrenv!=null)
            {
                DataTableLC.Creditos[] CreditosEnv = new DataTableLC.Creditos[dtcrenv.size()];
                for(int j=0; j<dtcrenv.size();j++)
                    CreditosEnv[j] = dtcrenv.get(j);
                vis.setCreditosEnv(CreditosEnv);
            }

            if (vis.getPreventas() != null)
            {
                ArrayList<DataTableWS.PreventaEnv> dtEnvPrev;
                sql = string.formatSql("Select * from preventaenv where prev_folio_str='{0}'", vis.getPreventas()[0].getPreventa().getPrev_folio_str());
                json = BaseLocal.Select(sql, context);
                dtEnvPrev = ConvertirRespuesta.getPreventaEnvJson(json);

                if (dtEnvPrev.size()>0)
                {
                    DataTableWS.PreventaEnv[] Envase = new DataTableWS.PreventaEnv[dtEnvPrev.size()];

                    for (int j = 0; j < dtEnvPrev.size(); j++)
                    {
                        Envase[j] = dtEnvPrev.get(j);
                    }
                    vis.setEnvase(Envase);
                }
            }
            else
            {
                ArrayList<DataTableWS.PreventaEnv> dtEnvPrev;

                sql = string.formatSql("select '0' prev_folio_str ,s1.prod_cve_n,s1.prod_sku_str," +
                        "coalesce(s2.prod_precio_n,s1.lpre_precio_n) lpre_base_n,s1.lpre_precio_n," +
                        "coalesce(s2.prod_cant_n,0.0)prod_inicial_n,coalesce(s2.prod_cantabono_n,0.0) prod_cantabono_n, " +
                        "coalesce(s2.prod_saldo_n,0.0) prod_saldo_n,coalesce(s2.prod_abono_n,0.0) prod_abono_n, " +
                        "coalesce(s2.prod_venta_n,0.0) prod_venta_n,coalesce(s2.prod_cargo_n,0.0) prod_cargo_n  from " +
                        "(select p.prod_cve_n,p.prod_sku_str,pp.lpre_precio_n from productos p inner join categorias c " +
                        "on p.cat_cve_n=c.cat_cve_n inner join precioproductos pp " +
                        "on p.prod_cve_n=pp.prod_cve_n where c.cat_desc_str='ENVASE' and pp.lpre_cve_n={1}) s1 left join (" +
                        "select prod_cve_n,prod_sku_str,max(prod_precio_n) prod_precio_n,sum(prod_cant_n) prod_cant_n," +
                        "sum(prod_cantabono_n) prod_cantabono_n,sum(prod_cant_n-prod_cantabono_n) prod_saldo_n,0.0 prod_abono_n," +
                        "0.0 prod_venta_n,0.0 prod_cargo_n from creditos where cli_cve_n={0} and cred_esenvase_n=1  " +
                        "group by prod_cve_n,prod_sku_str ) s2 on s1.prod_cve_n=s2.prod_cve_n", String.valueOf(cliente) ,  rcli.getLpre_cve_n());
                json = BaseLocal.Select(sql, context);
                dtEnvPrev = ConvertirRespuesta.getPreventaEnvJson(json);

                if(dtEnvPrev.size()>0)
                {
                    DataTableWS.PreventaEnv[] Envase = new DataTableWS.PreventaEnv[dtEnvPrev.size()];
                    for(int j=0; j< dtEnvPrev.size(); j++)
                    {
                        Envase[j] = dtEnvPrev.get(j);
                    }
                    vis.setEnvase(Envase);
                }
            }
        }
        catch (Exception e)
        {
            Utils.msgError(context, context.getString(R.string.err_utils2), e.getMessage());
            Log.d("salida","EROR: "+ e.getMessage());
        }

        return vis;
    }

    public static void ImprimirPreVentaBebidas(VisitaPrev v, boolean ReImp, String copia,Configuracion conf, Context context)
    {
        String sql = string.formatSql2("Select rut_desc_str from rutas where rut_cve_n={0}", conf.getRutaStr());
        String ruta = BaseLocal.SelectDato(sql, context);
        String p ="";

        p+= "RUTA: "+ ruta.toUpperCase() +"\n\n";

        sql=string.formatSql2("Select * from usuarios where usu_cve_str='{0}'", conf.getUsuario());
        String json = BaseLocal.Select(sql, context);

        DataTableWS.Usuarios usu = ConvertirRespuesta.getUsuarioLocal(json);

        if(usu!=null)
            p+= string.formatSql("ASESOR:\n{0} {1} {2}\n\n",usu.getUsu_nom_str(),usu.getUsu_app_str(),usu.getUsu_apm_str());

        ArrayList<DataTableWS.Clientes> alCli;
        DataTableWS.Clientes dtc;

        sql = string.formatSql("Select * from clientes where cli_cve_n={0}", String.valueOf(v.getCli_cve_n()) );
        json = BaseLocal.Select(sql,context);
        alCli = ConvertirRespuesta.getClientesJson(json);

        if(alCli.size()>0)
        {
            dtc=alCli.get(0);

            p+= "CLIENTE: " + dtc.getCli_cveext_str()+"\n\n";
            p+= "RAZON SOCIAL: \n" + dtc.getCli_nombrenegocio_str()+"\n\n";
            p+= "NEGOCIO: \n" + dtc.getCli_razonsocial_str()+"\n\n";

            ArrayList<DataTableWS.Direcciones>  dtd;
            sql = string.formatSql("Select * from direcciones where cli_cve_n={0}   and dir_cve_n={1}",
                    String.valueOf( v.getCli_cve_n() ) , dtc.getCli_dirent_n());
            json = BaseLocal.Select(sql, context);
            dtd = ConvertirRespuesta.getDireccionesJson(json);

            if(dtd.size()>0)
            {
                DataTableWS.Direcciones r = dtd.get(0);
                p+=string.formatSql("DIRECCION: \n{0} {1} {2} {3} {4} C.P. {5} \n\n", r.getDir_calle_str(),
                        r.getDir_noext_str(), r.getDir_colonia_str(), r.getDir_municipio_str(), r.getDir_estado_str(),
                        r.getDir_codigopostal_str());
            }

            if(dtc.getCli_Tel1_str()!=null && !dtc.getCli_Tel1_str().equals("0"))
                p+="TEL: "+dtc.getCli_Tel1_str()+"\n";
        }


        p+= "FECHA IMP: " + Utils.FechaLocal()+" "+Utils.HoraLocal()+"\n\n";

        if (ReImp)
        {
            p+= Impresora.Centrar( string.formatSql( "* C O P I A {0} *", copia.toUpperCase() ) )   +"\n\n";
        }
        else
        {
            p+= Impresora.Centrar( string.formatSql( "*O R I G I N A L {0} *", copia.toUpperCase() ) ) +  "\n\n";
        }

        ArrayList<DataTableWS.Productos> dtp = new ArrayList<>();
        json = BaseLocal.Select( "Select * from productos", context );
        dtp = ConvertirRespuesta.getProductosJson(json);

        ArrayList<DataTableWS.FormasPago> dtfp = new ArrayList<>();
        json = BaseLocal.Select("Select * from formaspago",context );
        dtfp = ConvertirRespuesta.getFormasPagoJson(json);

        double SubtotalVenta = 0;
        double SubtotalDeudaEnv = 0;
        double SubtotalVtaEnv = 0;
        double SubTotalDE = 0;
        double SubTotalE = 0;
        double TotPzasEnv = 0;

        if (v.getPreventas() != null)
        {
            for(Preventa vta : v.getPreventas())
            {
                p+="FECHA PREVENTA: \n" + Utils.FechaHoraFormato( vta.getPreventa().getPrev_fecha_dt()) +"\n\n";
                p+="FOLIO: " +  vta.getPreventa().getPrev_folio_str()+"\n\n";

                if (vta.getDetalles() != null || vta.getBebidas()!=null)
                {
                    double SubTotalF = 0;
                    double SubTotalCvz = 0;
                    double SubTotalBeb = 0;

                    p+= Impresora.Centrar( "PREVENTA" ) +"\n\n" ;

                    p+="* SKU    DESC  CAN PRECIO SUBTOT\n\n";

                    if (vta.getDetalles() != null)
                    {
                        p+="------------CERVEZA------------\n";

                        for (int i = 0; i < vta.getDetalles().length; i++)
                        {
                            String desc = "";
                            if( !Utils.getBool( vta.getDetalles()[i].getProd_envase_n() )  )
                            {
                                DataTableWS.Productos r=null;
                                for(int j=0; j< dtp.size();j++)
                                    if(vta.getDetalles()[i].getProd_cve_n().equals( dtp.get(j).getProd_cve_n()) )
                                        r=dtp.get(j);

                                if(r!=null)
                                {
                                    desc = r.getProd_desc_str();
                                }

                                String aah = Utils.getBool(vta.getDetalles()[i].getProd_promo_n() )?"P":" ";

                                p+=aah+" ";
                                p+= Impresora.DarTamañoDer(vta.getDetalles()[i].getProd_sku_str(),6 ) +" ";
                                p+= Impresora.DarTamañoDer(desc,5)+" ";

                                p+= Impresora.DarTamañoIzq( vta.getDetalles()[i].getProd_cant_n(),3 )+" ";
                                p+= Impresora.DarTamañoIzq( vta.getDetalles()[i].getLpre_precio_n(), 6 )  +" ";
                                p+= Impresora.DarTamañoIzq( vta.getDetalles()[i].getProd_subtotal_n(), 6 )  +"\n";

                                SubTotalF += Double.parseDouble(vta.getDetalles()[i].getProd_subtotal_n());
                                SubTotalCvz += Double.parseDouble(vta.getDetalles()[i].getProd_subtotal_n());
                            }
                        }
                        p+="TOTAL CERVEZA: "+ string.FormatoPesos(SubTotalCvz)+"\n\n";
                    }


                    //-------------------- Bebidas ----------------------------

                    if ( vta.getBebidas()!= null)
                    {
                        p+="---------OTRAS BEBIDAS---------\n";
                        for (int i = 0; i < vta.getBebidas().length; i++)
                        {
                            String desc = "";
                            if ( !Utils.getBool(vta.getBebidas()[i].getProd_envase_n()))
                            {
                                DataTableWS.Productos r=null;
                                for(int j=0; j< dtp.size();j++)
                                    if(vta.getBebidas()[i].getProd_cve_n().equals( dtp.get(j).getProd_cve_n()) )
                                        r=dtp.get(j);

                                if (r!=null)
                                {
                                    desc = r.getProd_desc_str();
                                }

                                String aah = Utils.getBool( vta.getBebidas()[i].getProd_promo_n() )?"P":" ";


                                p+=aah+" ";
                                p+= Impresora.DarTamañoDer(vta.getBebidas()[i].getProd_sku_str(),6 )+" ";
                                p+= Impresora.DarTamañoDer(desc,5)+" ";

                                p+= Impresora.DarTamañoIzq( vta.getBebidas()[i].getProd_cant_n(),3 )+" ";
                                p+= Impresora.DarTamañoIzq( vta.getBebidas()[i].getLpre_precio_n(), 6 )  +" ";
                                p+= Impresora.DarTamañoIzq( vta.getBebidas()[i].getProd_subtotal_n(), 6 )  +"\n";

                                SubTotalF += Double.parseDouble(vta.getBebidas()[i].getProd_subtotal_n());
                                SubTotalBeb += Double.parseDouble(vta.getBebidas()[i].getProd_subtotal_n());

                            }
                        }
                        p+="TOTAL OTRAS BEBIDAS: "+ string.FormatoPesos(SubTotalBeb)+"\n\n";
                    }
                    //-------------------- Bebidas ------------------------//
                    SubtotalVenta += SubTotalF;

                    p+="TOTAL FACTURA: " + string.FormatoPesos(SubTotalF)+"\n\n";


                    if(v.getEnvase()!=null)
                    {
                        p+= Impresora.Centrar("ENVASE")+"\n";
                        p+="SKU    INI. CAR ABO PRO VTA FIN"+"\n";

                        String desc = "";
                        for (int i = 0; i < v.getEnvase().length; i++)
                        {
                            DataTableWS.Productos r=null;

                            for(int j=0; j< dtp.size();j++)
                                if(v.getEnvase()[i].getProd_cve_n().equals( dtp.get(j).getProd_cve_n()) )
                                    r=dtp.get(j);

                            if (r!=null)
                            {
                                desc = r.getProd_desc_str();

                                p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_sku_str(),6 )+" ";
                                p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_inicial_n(),4 )+" ";
                                p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_cargo_n() ,3 )+" ";
                                p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_abono_n() ,3)+" ";
                                p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_regalo_n() ,3)+" ";
                                p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_venta_n() ,3)+" ";
                                p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_final_n() ,3)+"\n";

                                SubTotalDE += Double.parseDouble(v.getEnvase()[i].getProd_final_n()) * Double.parseDouble(v.getEnvase()[i].getLpre_precio_n());
                                SubTotalE += Double.parseDouble( v.getEnvase()[i].getProd_venta_n() ) * Double.parseDouble( v.getEnvase()[i].getLpre_precio_n() );
                                TotPzasEnv += Double.parseDouble( v.getEnvase()[i].getProd_final_n() );

                            }
                        }

                        p+="TOTAL ENVASE PZAS: "+ String.format("%.2f",TotPzasEnv)+"\n";
                        p+="TOTAL ENVASE: "+ string.FormatoPesos(SubTotalDE)+"\n\n";
                    }

                    SubtotalDeudaEnv += SubTotalDE;
                    SubtotalVtaEnv += SubTotalE;
                }
            }
        }
        else
        {
            p+= Impresora.Centrar("SIN PREVENTA")+"\n\n";

            if(v.getEnvase()!=null)
            {
                p+= Impresora.Centrar("ENVASE")+"\n";
                p+="SKU    INI. CAR ABO PRO VTA FIN"+"\n\n";

                String desc = "";
                for (int i = 0; i < v.getEnvase().length; i++)
                {
                    DataTableWS.Productos r=null;

                    for(int j=0; j< dtp.size();j++)
                        if(v.getEnvase()[i].getProd_cve_n().equals( dtp.get(j).getProd_cve_n()) )
                            r=dtp.get(j);

                    if (r!=null)
                    {
                        desc = r.getProd_desc_str();

                        p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_sku_str(),6 )+" ";
                        p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_inicial_n(),4 )+" ";
                        p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_cargo_n() ,3 )+" ";
                        p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_abono_n() ,3)+" ";
                        p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_regalo_n() ,3)+" ";
                        p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_venta_n() ,3)+" ";
                        p+= Impresora.DarTamañoDer( v.getEnvase()[i].getProd_final_n() ,3)+"\n";

                        SubTotalDE += Double.parseDouble(v.getEnvase()[i].getProd_final_n()) * Double.parseDouble(v.getEnvase()[i].getLpre_precio_n());
                        SubTotalE += Double.parseDouble( v.getEnvase()[i].getProd_venta_n() ) * Double.parseDouble( v.getEnvase()[i].getLpre_precio_n() );
                        TotPzasEnv += Double.parseDouble( v.getEnvase()[i].getProd_final_n() );

                    }
                }

                p+="TOTAL ENVASE PZAS: "+ String.format("%.2f",TotPzasEnv)+"\n\n";
                p+="TOTAL ENVASE: "+ String.format("%.2f",SubTotalDE)+"\n\n";
            }

            SubtotalDeudaEnv += SubTotalDE;
            SubtotalVtaEnv += SubTotalE;
        }

        double SubTotalP = 0;
        double Pagos = 0;
        double PagoCredito = 0;

        if (v.getCobranza() != null)
        {
            //p+=Impresora.Centrar("COBRANZA")+"\n\n";
            //p+="REF            PAGO        FORMA\n";
            String fp = "";

            for(int i=0; i< v.getCobranza().length; i++)
            {
                if( !Utils.getBool( v.getCobranza()[i].getPpag_cobranza_n() ) )
                {
                    fp = "";
                    DataTableWS.FormasPago pf = null;
                    for (int j = 0; j < dtfp.size(); j++)
                        if (v.getCobranza()[i].getFpag_cve_n().equals(dtfp.get(j).getFpag_cve_n())) {
                            pf = dtfp.get(j);
                            j = dtfp.size();
                        }

                    if (pf != null)
                        fp = pf.getFpag_desc_str();

                    if (fp.equals("CREDITO"))
                        PagoCredito = Double.parseDouble(v.getCobranza()[i].getFpag_cant_n());
                    else
                    {

                        Pagos += Double.parseDouble(v.getCobranza()[i].getFpag_cant_n());
                        SubTotalP += Double.parseDouble(v.getCobranza()[i].getFpag_cant_n());
                    }
                }
            }
        }
        else
        {
            //p+= Impresora.Centrar("SIN COBRANZA")+"\n\n";
        }

        double SubTotalC = 0;
        double Cobranza = 0;


        if (v.getCobranza() != null)
        {
            //p+=Impresora.Centrar("COBRANZA")+"\n\n";
            //p+="REF            PAGO        FORMA\n";
            String fp = "";

            for(int i=0; i< v.getCobranza().length; i++)
            {
                if( Utils.getBool( v.getCobranza()[i].getPpag_cobranza_n() ) )
                {
                    fp = "";
                    DataTableWS.FormasPago pf = null;
                    for (int j = 0; j < dtfp.size(); j++)
                        if (v.getCobranza()[i].getFpag_cve_n().equals(dtfp.get(j).getFpag_cve_n())) {
                            pf = dtfp.get(j);
                            j = dtfp.size();
                        }

                    if (pf != null)
                        fp = pf.getFpag_desc_str();

                    SubTotalC += Double.parseDouble(v.getCobranza()[i].getFpag_cant_n());
                    Cobranza += Double.parseDouble(v.getCobranza()[i].getFpag_cant_n());

                }
            }
        }
        else
        {
            //p+= Impresora.Centrar("SIN COBRANZA")+"\n\n";
        }

        double SubTotalCred = 0;
        double InicialCred = 0;

        if (v.getCreditos() != null)
        {
            for (int i = 0; i < v.getCreditos().length; i++)
            {

                double saldoant = Double.parseDouble(v.getCreditos()[i].getCred_monto_n()) - Double.parseDouble( v.getCreditos()[i].getCred_abono_n() );
                double AbonoActCred = 0;

                double saldo = Double.parseDouble( v.getCreditos()[i].getCred_monto_n() )  - Double.parseDouble( v.getCreditos()[i].getCred_abono_n() )  - AbonoActCred;

                if (v.getCreditos()[i].getTrans_est_n() != null)
                    if (v.getCreditos()[i].getTrans_est_n().equals("3"))
                        InicialCred += saldoant;


                SubTotalCred += saldo;

            }
        }
        else
        {
           // p+= Impresora.Centrar("SIN CREDITOS")+"\n\n";
        }

        double SubTotalCredEsp = 0;
        double SubTotalCredEnv = 0;
        double SaldoEnv = 0;

        if (v.getCreditosEnv() != null)
        {
            for (int i = 0; i < v.getCreditosEnv().length; i++)
            {
                double can = Double.parseDouble(v.getCreditosEnv()[i].getProd_cant_n()) - Double.parseDouble(v.getCreditosEnv()[i].getProd_cantabono_n());
                if (can != 0)
                {
                    double AboEnvDia = 0;
                    double saldo = Double.parseDouble( v.getCreditosEnv()[i].getProd_cant_n() ) - Double.parseDouble( v.getCreditosEnv()[i].getProd_cantabono_n() ) -  AboEnvDia;
                    SubTotalCredEnv += saldo;
                    SaldoEnv += saldo * Double.parseDouble( v.getCreditosEnv()[i].getProd_precio_n() );
                }
            }
        }
        else
        {
            //p+= Impresora.Centrar("SIN CREDITOS DE ENVASE")+"\n\n";
        }


        p+= Impresora.Centrar("SALDOS")+"\n\n";

        p+= "+SALDO INICIAL: "+ string.FormatoPesos( InicialCred ) +"\n";
        p+= "+VENTA DEL DIA: "+ string.FormatoPesos( SubtotalVenta ) +"\n";
        p+= "+VENTA DE ENVASE DEL DIA: "+ string.FormatoPesos( SubTotalE )+"\n";
        p+= "=SALDO FINAL PREVENTA: "+ string.FormatoPesos( InicialCred + SubtotalVenta + SubTotalE )+"\n\n";

        p+= "+SALDO INICIAL: "+ string.FormatoPesos( InicialCred )+"\n";
        p+= "+VENTA DEL DIA: "+ string.FormatoPesos( SubtotalVenta )+"\n";
        p+= "+VENTA DE ENVASE DEL DIA: "+ string.FormatoPesos( SubTotalE )+"\n";
        p+= "=SALDO FINAL PREVENTA: "+ string.FormatoPesos( (InicialCred + SubtotalVenta + SubTotalE) )+"\n\n";
        p+= "SALDO EFECTIVO PREV: "+ string.FormatoPesos( InicialCred + SubtotalVenta + SubTotalE )+"\n";

        p+="\n\n";
        p= p.replace("\n","\r");
        Impresora.imprimir(p,context);

    }


}
