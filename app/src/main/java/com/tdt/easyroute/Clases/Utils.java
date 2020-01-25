package com.tdt.easyroute.Clases;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.Creditos;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Modelos;
import com.tdt.easyroute.Model.Preventa;
import com.tdt.easyroute.Model.PreventaDet;
import com.tdt.easyroute.Model.PreventaEnvase;
import com.tdt.easyroute.Model.PreventaPagos;
import com.tdt.easyroute.Model.VisitaPrev;
import com.tdt.easyroute.R;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

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

    public static String numFormat(double numero) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(numero);
    }

    public static String strToNum(String numeroStr) {
        try {
            double numero = Double.parseDouble(numeroStr);
            DecimalFormat df = new DecimalFormat("00.00");
            return df.format(numero);
        } catch (Exception e) {
            return "";
        }
    }

    public static VisitaPrev ObtenerVisitaPrevBebidas(long cliente,Context context)
    {
        VisitaPrev vis = new VisitaPrev();

        vis.setCli_cve_n(cliente);

        String json;

        DataTableWS.Clientes rcli=null;

        try
        {
            json= BaseLocal.Select(string.formatSql("select * from clientes where cli_cve_n={0}", String.valueOf(cliente)),context);
            ArrayList<DataTableWS.Clientes> dtCli = ConvertirRespuesta.getClientesJson(json);

            if(dtCli!=null&&dtCli.size()>0)
                rcli = dtCli.get(0);

            json = BaseLocal.Select(  string.formatSql("Select * from preventa where cli_cve_n={0}", String.valueOf(cliente) )  ,context);
            ArrayList<DataTableWS.Preventa> dtVen = ConvertirRespuesta.getPreventaJson(json);

            if(dtVen!=null && dtVen.size()>0)
            {
                vis.Preventas = new Preventa[dtVen.size()];

                for(int k=0; k<dtVen.size(); k++)
                {
                    DataTableWS.Preventa rv = dtVen.get(k);

                    Preventa vta = null;

                    vta = new Preventa( rv.getPrev_folio_str(), Long.parseLong( rv.getCli_cve_n () ) , Integer.parseInt( rv.getRut_cve_n () ) , rv.getPrev_fecha_dt() ,
                        Integer.parseInt( rv.getLpre_cve_n() ), Integer.parseInt(rv.getDir_cve_n()) , rv.getUsu_cve_str(), getBool( rv.getPrev_condicionada_n() )  ,
                        rv.getPrev_coordenada_str());

                    String consulta= string.formatSql("select d.* from preventadet d  " +
                            "inner join productos p on d.prod_cve_n=p.prod_cve_n " +
                            "inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                            "where d.prev_folio_str='{0}' and c.cat_desc_str not in ('AGUA','REFRESCO','BEBIDA ENERGIZANTE','JUGO')", vta.getPrev_folio_str());

                    json = BaseLocal.Select(consulta,context);

                    ArrayList<DataTableWS.PreventaDet> dtVenDet = ConvertirRespuesta.getPreventaDetJson(json);

                    if(dtVenDet!=null && dtVenDet.size()>0)
                    {
                        vta.setDetalles( new PreventaDet[dtVenDet.size()] );

                        for(int j=0; j<dtVenDet.size();j++)
                        {
                            DataTableWS.PreventaDet rd = dtVenDet.get(j);

                            vta.detalles[j] = new PreventaDet( rd.getPrev_folio_str() ,
                                   Integer.parseInt(rd.getPrev_num_n()) , Long.parseLong( rd.getProd_cve_n() ) , rd.getProd_sku_str(), getBool(rd.getProd_envase_n()) ,
                                Double.parseDouble(rd.getProd_cant_n() ) , Double.parseDouble(rd.getLpre_base_n()) , Double.parseDouble(rd.getLpre_cliente_n()) , Double.parseDouble(rd.getLpre_promo_n()) ,
                                Double.parseDouble( rd.getLpre_precio_n() ) , getBool(rd.getProd_promo_n()) , Long.parseLong(rd.getProm_cve_n())   , Double.parseDouble(rd.getProd_subtotal_n())  );
                        }
                    }

                    //-------------  Bebidas -----------------------

                    consulta = string.formatSql( "select d.* from preventadet d  " +
                            "inner join productos p on d.prod_cve_n=p.prod_cve_n " +
                            "inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                            "where d.prev_folio_str='{0}' and c.cat_desc_str in ('AGUA','REFRESCO','BEBIDA ENERGIZANTE','JUGO')" ,vta.getPrev_folio_str());

                    json = BaseLocal.Select(consulta,context);

                    ArrayList<DataTableWS.PreventaDet> dtVenBeb = ConvertirRespuesta.getPreventaDetJson(json);

                    if(dtVenBeb!=null && dtVenBeb.size()>0)
                    {
                        vta.setBebidas( new PreventaDet[dtVenBeb.size()] );

                        for(int j=0; j<dtVenBeb.size();j++)
                        {
                            DataTableWS.PreventaDet rd = dtVenBeb.get(j);

                            vta.Bebidas[j] = new PreventaDet(rd.getPrev_folio_str(),
                                 Integer.parseInt(rd.getPrev_num_n()) , Long.parseLong(rd.getProd_cve_n()) ,  rd.getProd_sku_str()  ,
                                 getBool( rd.getProd_envase_n() ) , Double.parseDouble(rd.getProd_cant_n()) , Double.parseDouble(rd.getLpre_base_n())  ,
                                 Double.parseDouble(rd.getLpre_cliente_n()) , Double.parseDouble(rd.getLpre_promo_n()) , Double.parseDouble(rd.getLpre_precio_n())  ,
                                 getBool( rd.getProd_promo_n() )  ,
                                 Long.parseLong( rd.getProm_cve_n() ) , Double.parseDouble( rd.getProd_subtotal_n() )  );

                        }
                    }

                    vis.Preventas[k] = vta;
                }
            }



            if(vis.getPreventas()!=null)
            {
                String consulta = string.formatSql("Select pv.*,fp.fpag_desc_str fpag_desc_str from preventapagos pv inner join formaspago fp " +
                            "on pv.fpag_cve_n=fp.fpag_cve_n where prev_folio_str='{0}'", vis.Preventas[0].prev_folio_str);

                json = BaseLocal.Select(consulta,context);
                ArrayList<DataTableLC.PreventaPagos> dtp = ConvertirRespuesta.getPreventaPagosLCJson(json);

                if(dtp!=null)
                {
                    vis.setCobranza( new PreventaPagos[dtp.size()] );

                    for(int j=0; j<dtp.size(); j++)
                    {
                        DataTableLC.PreventaPagos rp = dtp.get(j);

                        vis.Cobranza[j] = new PreventaPagos( rp.getPrev_folio_str() , Byte.parseByte( rp.getPpag_num_n() )   ,
                           getBool( rp.getPpag_cobranza_n() ), Integer.parseInt(rp.getFpag_cve_n()) ,  rp.getFpag_desc_str(), Double.parseDouble( rp.getFpag_cant_n() )  );
                    }
                }
            }



            String consulta = string.formatSql("Select * from creditos where cli_cve_n={0} and cred_esenvase_n=0 and cred_especial_n=0", String.valueOf(cliente));
            json = BaseLocal.Select(consulta,context);
            ArrayList<DataTableLC.Creditos> dtcr = ConvertirRespuesta.getCreditos2Json(json);

            if(dtcr!=null)
            {
                vis.setCreditos( new Creditos[dtcr.size()] );

                for(int j=0; j<dtcr.size();j++)
                {
                    DataTableLC.Creditos rp = dtcr.get(j);

                    vis.Creditos[j] = new Creditos( Integer.parseInt( rp.getCred_cve_n() )  , rp.getCred_referencia_str()  , Long.parseLong( rp.getCli_cve_n() )  ,
                         rp.getUsu_cve_str(),  rp.getCred_fecha_dt() ,  rp.getCred_descripcion_str(),
                         rp.getCred_vencimiento_dt() , Double.parseDouble(rp.getCred_monto_n()) , Double.parseDouble(rp.getCred_abono_n())   ,
                         Boolean.parseBoolean( rp.getCred_engestoria_n() ), Boolean.parseBoolean(rp.getCred_esenvase_n()) , Boolean.parseBoolean( rp.getCred_especial_n() ) ,
                         Long.parseLong( rp.getProd_cve_n() ), rp.getProd_sku_str() ,
                         Double.parseDouble(rp.getProd_precio_n()), Double.parseDouble( rp.getProd_cant_n())  ,
                         Double.parseDouble( rp.getProd_cantabono_n() ),  Byte.parseByte(rp.getTrans_est_n()) ,
                         rp.getTrans_fecha_dt() );
                }
            }


            if(vis.Preventas!=null)
            {
                consulta = string.formatSql("Select * from preventaenv where prev_folio_str='{0}'", vis.Preventas[0].prev_folio_str);
                json = BaseLocal.Select(consulta,context);
                ArrayList<DataTableWS.PreventaEnv> dtEnvPrev = ConvertirRespuesta.getPreventaEnvJson(json);

                if(dtEnvPrev!=null && dtEnvPrev.size()>0)
                {
                    vis.setEnvase(new PreventaEnvase[dtEnvPrev.size()]);

                    for(int j=0; j<dtEnvPrev.size();j++)
                    {
                        DataTableWS.PreventaEnv rp = dtEnvPrev.get(j);

                        vis.Envase[j] = new PreventaEnvase(rp.getPrev_folio_str() , Long.parseLong( rp.getProd_cve_n() )  ,
                             rp.getProd_sku_str(), Double.parseDouble( rp.getProd_inicial_n()) , Double.parseDouble(rp.getProd_cargo_n())  ,
                             Double.parseDouble( rp.getProd_abono_n() ),  Double.parseDouble( rp.getProd_regalo_n() ) , Double.parseDouble( rp.getProd_venta_n() )  ,
                             Double.parseDouble( rp.getProd_final_n() ), Double.parseDouble( rp.getLpre_base_n() )  , Double.parseDouble( rp.getLpre_precio_n() ) ) ;
                    }
                }
            }
            else
            {
                consulta= string.formatSql("select '0' prev_folio_str ,s1.prod_cve_n,s1.prod_sku_str," +
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
                        "group by prod_cve_n,prod_sku_str ) s2 on s1.prod_cve_n=s2.prod_cve_n", String.valueOf( cliente ) , String.valueOf( rcli.getLpre_cve_n() ) );

                json = BaseLocal.Select(consulta,context);

                ArrayList<DataTableWS.PreventaEnv2> dtEnvPrev = ConvertirRespuesta.getPreventaEnv2Json(json);

                if(dtEnvPrev!=null && dtEnvPrev.size()>0)
                {
                    for(int i=0; i<dtEnvPrev.size();i++)
                    {
                        DataTableWS.PreventaEnv2 p = dtEnvPrev.get(i);

                        double proFin = Double.parseDouble( p.getProd_saldo_n() ) + Double.parseDouble( p.getProd_cargo_n() ) - Double.parseDouble( p.getProd_abono_n() ) - Double.parseDouble( p.getProd_venta_n());

                        double proSub = proFin * Double.parseDouble(p.getLpre_precio_n());

                        dtEnvPrev.get(i).setProd_final_n( String.valueOf(proFin) );
                        dtEnvPrev.get(i).setProd_subtotal_n( String.valueOf(proSub) );
                    }


                    for(int j=0; j<dtEnvPrev.size();j++)
                    {
                        DataTableWS.PreventaEnv2 rp = dtEnvPrev.get(j);

                        vis.Envase[j] = new PreventaEnvase(rp.getPrev_folio_str() , Long.parseLong( rp.getProd_cve_n() )  ,
                                rp.getProd_sku_str(), Double.parseDouble( rp.getProd_inicial_n()) , Double.parseDouble(rp.getProd_cargo_n())  ,
                                Double.parseDouble( rp.getProd_abono_n() ),  /*Double.parseDouble( rp.getProd_regalo_n() )*/ 0 , Double.parseDouble( rp.getProd_venta_n() )  ,
                                Double.parseDouble( rp.getProd_final_n() ), Double.parseDouble( rp.getLpre_base_n() )  , Double.parseDouble( rp.getLpre_precio_n() ) ) ;
                    }
                }
            }


        }
        catch (Exception e)
        {
            Log.d("salida","Error Utils ObtenerVisitaPrevBebidas: "+e.getMessage());
            Toast.makeText(context, "Error Utils ObtenerVisitaPrevBebidas: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return vis;
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

}
