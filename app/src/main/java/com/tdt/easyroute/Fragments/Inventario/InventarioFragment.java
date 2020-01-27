package com.tdt.easyroute.Fragments.Inventario;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.LoginActivity;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Modelos;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class InventarioFragment extends Fragment implements AsyncResponseJSON {

    private static Usuario user;
    private static boolean descarga;

    private Button button_imprimir,button_salir;

    private Configuracion conf=null;

    private ArrayList<DataTableLC.EnvaseAut> al_envaseAut;
    private ArrayList<DataTableLC.InvP> al_invP;
    private ArrayList<DataTableLC.Inv> al_inv;

    private String peticion,nombreBase;
    private boolean botonDescargar=false;

    private ArrayList<DataTableLC.InvP> dtVta = new ArrayList<>();
    private String folio, idCteEsp = "";

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;

    private MainActivity mainActivity;

    private boolean entrarDescarga;

    public InventarioFragment() {
        // Required empty public constructor
    }

    public static InventarioFragment newInstance(Usuario u, boolean desc) {
        InventarioFragment fragment = new InventarioFragment();
        Bundle args = new Bundle();
        user=u;
        descarga=desc;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);

        try {

            layoutInflater = inflater;
            mainActivity = (MainActivity) getActivity();

            button_imprimir = view.findViewById(R.id.button_imprimir);
            button_salir = view.findViewById(R.id.button_salir);
            tableLayout = view.findViewById(R.id.tableLayout);
            nombreBase = getActivity().getString(R.string.nombreBD);

            if (descarga) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_inventario1));
                button_imprimir.setText(getString(R.string.bt_descargar));
            } else {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.title_inventario2));
                button_imprimir.setText(getString(R.string.bt_imprimir));
            }

            conf = Utils.ObtenerConf(getActivity().getApplication());
            crearEnvaseAut();
            cargarInventario(conf.getRuta());


            button_imprimir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    verificarImpresion();
                }
            });

            button_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.RegresarInicio(getActivity());
                }
            });

            if (descarga) {
                validarProductosMismoDia();
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_inv8), e.getMessage());
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if( descarga && !entrarDescarga)
        {
            //Utils.RegresarInicio(getActivity());
        }
    }

    private void validarProductosMismoDia()
    {
        try {

            float CantVta = 0;

            for (int i = 0; i < al_invP.size(); i++) {
                if (Integer.parseInt(al_invP.get(i).getProd_vtamismodia_n()) == 1 && Integer.parseInt(al_invP.get(i).getProd_cant_n()) > 0) {
                    dtVta.add(al_invP.get(i));
                    if (al_invP.get(i).getProd_cant_n() != null)
                        CantVta += Float.parseFloat(al_invP.get(i).getProd_cant_n());
                }
            }

            if (dtVta.size() > 0) {
                Toast.makeText(getContext(), string.formatSql(getString(R.string.tt_inv2), String.valueOf(CantVta)), Toast.LENGTH_LONG).show();

                //Obtener la ruta actual
                DataTableWS.Ruta dtRut = null;
                String jsonRuta = BaseLocal.Select(string.formatSql("select * from rutas where rut_cve_n={0}", String.valueOf(conf.getRuta())), getContext());
                if (ConvertirRespuesta.getRutasJson(jsonRuta) != null && ConvertirRespuesta.getRutasJson(jsonRuta).size() > 0)
                    dtRut = ConvertirRespuesta.getRutasJson(jsonRuta).get(0);

                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                try {
                    String fecha = Utils.FechaLocal().replace("-", "");
                    String rut = String.valueOf(conf.getRuta());
                    String ceros = "";
                    if (rut.length() < 3) {
                        for (int i = rut.length(); i < 3; i++) ceros = ceros.concat("0");
                        rut = ceros + rut;
                    }

                    Cursor cursor = db.rawQuery("select max(ven_folio_str) from ventas", null);
                    if (cursor.moveToFirst() && cursor.getString(0) != null) {
                        folio = cursor.getString(0);
                        folio = folio.substring(11);
                        folio = String.valueOf(Integer.parseInt(folio) + 1);

                        folio = fecha + rut + folio;
                    } else {
                        folio = fecha + rut + "001";
                    }

                    if (dtRut != null) {
                        idCteEsp = dtRut.getRut_idcteesp_n();
                    }

                    db.close();

                    obtenerUbicacion("VentaAutomatica");

                } catch (Exception e) {
                    Utils.msgError(getContext(), getString(R.string.err_inv7), e.getMessage());
                    Log.d("salida", "Error: " + e.getMessage());
                } finally {
                    db.close();
                }
            }

            verficarDescarga();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_inv7), e.getMessage());
        }

    }

    private void verficarDescarga()
    {
        try
        {
            int H = Integer.parseInt(Utils.HoraLocal().substring(0, 2));

            if (H < 14) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle(getString(R.string.msg_importante));
                dialogo1.setMessage(getString(R.string.msg_confDescarga));
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        abrirDescarga();
                    }
                });
                dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Utils.RegresarInicio(getActivity());
                    }
                });
                dialogo1.show();
            } else {
                abrirDescarga();
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

    private void abrirDescarga()
    {
        try
        {
            String[] dias = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};

            Modelos.Indicadores ip;

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            String dia = dias[cal.get(Calendar.DAY_OF_WEEK) - 1];


            if (dia.equals("Domingo")) {
                ip = new Modelos.Indicadores();
                ip.setPorcentajeRequerido(0);
                ip.setPorcentajeRealizado(1);
                ip.setPorcentajeRequeridoVentas(0);
                ip.setPorcentajeRealizadoVentas(1);
            } else {
                ip = Utils.ObtenerProductividad(conf.getRuta(), getActivity().getApplication());
            }


            String mensaje = "";
            entrarDescarga = true;

            if (ip.getPorcentajeRealizado() < ip.getPorcentajeRequerido()) {
                mensaje = string.formatSql(getString(R.string.tt_inv3), String.valueOf(ip.getVisitasRequeridas() - ip.getVisitasClientes()));
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                entrarDescarga = false;
            } else if (ip.getPorcentajeRealizadoVentas() < ip.getPorcentajeRequeridoVentas()) {
                mensaje = string.formatSql(getString(R.string.tt_inv4), String.valueOf(ip.getVentasRequeridas() - ip.getConVenta()));
                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                entrarDescarga = false;
            }

            if (entrarDescarga) {
                if (conf.isDescarga()) {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                    dialogo1.setTitle(getString(R.string.msg_importante));
                    dialogo1.setMessage(getString(R.string.msg_descargaDos));
                    dialogo1.setCancelable(false);
                    dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            entrarDescarga = true;
                        }
                    });
                    dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            entrarDescarga = false;
                        }
                    });
                    dialogo1.show();
                }
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(descarga && !entrarDescarga)
        {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Utils.RegresarInicio(getActivity());
                }
            }, 300);
        }
    }

    private void ventaAutomatica(String coordenada)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try
        {
            db.beginTransaction();

            db.execSQL( string.formatSql( Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), String.valueOf(conf.getRuta()), idCteEsp,"NO LECTURA","SIN CODIGO",coordenada ) );
            db.execSQL( string.formatSql( Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), String.valueOf(conf.getRuta()), idCteEsp,"APERTURA DE CLIENTE","SIN CODIGO",coordenada ) );


            float subtotal = 0;
            float subt = 0;

            for(int i=0; i<dtVta.size(); i++)
            {
                subt = Float.parseFloat(dtVta.get(i).getProd_cant_n()) * Float.parseFloat(dtVta.get(i).getLpre_precio_n());
                db.execSQL( string.formatSql(Querys.Ventas.InsVentaDet,folio, String.valueOf(i+1),dtVta.get(i).getProd_cve_n(),
                                            dtVta.get(i).getProd_sku_str(),"0",dtVta.get(i).getProd_cant_n(),"0",dtVta.get(i).getLpre_precio_n(),dtVta.get(i).getLpre_precio_n(),
                                            "0",dtVta.get(i).getLpre_precio_n(),String.valueOf(subt),"0","0","0","0","0") );

                subtotal+=subt;

                db.execSQL(string.formatSql(Querys.Inventario.ActualizaInvVen, "+", dtVta.get(i).getProd_cant_n(),String.valueOf(conf.getRuta()), dtVta.get(i).getProd_cve_n() ));

                String con = string.formatSql( Querys.Inventario.InsertMovimiento, String.valueOf(conf.getRuta()), dtVta.get(i).getProd_cve_n(),
                                               idCteEsp, conf.getUsuario(),dtVta.get(i).getProd_cant_n(), dtVta.get(i).getProd_cant_n(),"0",
                                               String.valueOf(   Float.parseFloat(dtVta.get(i).getProd_cant_n()) - Float.parseFloat(dtVta.get(i).getProd_cant_n())  ),"Vendido ticket: " + folio );

                db.execSQL(con);

            }

            String con= string.formatSql2(Querys.Pagos.InsPago,
                    String.valueOf(conf.getId() ), folio, String.valueOf(subtotal) ,"0","0",
                    "null","null","1",null,null,
                    idCteEsp, String.valueOf(conf.getRuta()),conf.getUsuario(),"0",null,
                    "0",coordenada);

            db.execSQL( con );

            con = string.formatSql(Querys.Ventas.InsVentas2, folio,idCteEsp, String.valueOf(conf.getRuta()),"11","0",conf.getUsuario().toUpperCase(), coordenada,null,"0","VENTA AUTOMATICA AL DESCARGAR");
            db.execSQL(con);

            con = string.formatSql(Querys.Trabajo.InsertBitacoraHHPedido,conf.getUsuario(), String.valueOf(conf.getRuta()),idCteEsp,"CON VENTA","VISITA CON VENTA",coordenada);
            db.execSQL(con);

            con= string.formatSql( Querys.Trabajo.InsertBitacoraHHPedido, conf.getUsuario(), String.valueOf( conf.getRuta()), idCteEsp,"CIERRE CLIENTE","CIERRE CON VENTA", coordenada   );
            db.execSQL(con);

            con = string.formatSql( Querys.Trabajo.InsertVisita, conf.getUsuario(),idCteEsp,"null","null","CON VENTA","VISITA CON VENTA",coordenada );
            db.execSQL(con);

            db.setTransactionSuccessful();

            db.endTransaction();
            db.close();


            Toast.makeText(getContext(), getString(R.string.tt_infoActu), Toast.LENGTH_SHORT).show();

            enviarCambios();

            Utils.RegresarInicio(getActivity());

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_inv6), e.getMessage());
            Log.d("salida", "Error: "+e.getMessage() );
        }
        finally
        {
            if(db.isOpen())
            {
                db.endTransaction();
                db.close();
            }
        }

    }

    private void crearEnvaseAut()
    {
        try
        {
            String res= BaseLocal.Select("select p.prod_cve_n,p.prod_sku_str,0 prod_cant_n from productos p inner join categorias c on p.cat_cve_n=c.cat_cve_n where c.cat_desc_str='ENVASE'",getContext());
            al_envaseAut = ConvertirRespuesta.getEnvaseAutJson(res);

            if(al_envaseAut!=null && !(al_envaseAut.size()>0))
            {
                al_envaseAut=null;
            }

        }catch (Exception e)
        {
            al_envaseAut=null;
            Log.d("salida","Error tabla envase automatico: "+e.getMessage());
            Utils.msgError(getContext(), getString(R.string.error_cargarInfo), e.getMessage());
        }

    }

    private void cargarInventario(int rut)
    {
        try
        {
            String res = BaseLocal.Select(Querys.Inventario.LayoutInventario,getContext());
            al_invP = ConvertirRespuesta.getInvPJson(res);
            Log.d("salida",res);

            res = BaseLocal.Select(string.formatSql(Querys.Inventario.ConsultaInventario, String.valueOf(rut)),getContext() );
            al_inv = ConvertirRespuesta.getInvJson(res);
            Log.d("salida",res);

            if( (al_invP!=null && al_invP.size()>0) && (al_inv!=null && al_inv.size()>0)  )
            {
                for(int i=0; i<al_invP.size();i++)
                {
                    DataTableLC.InvP r = al_invP.get(i);

                    DataTableLC.Inv in = null;
                    for(int j=0; j<al_inv.size();j++) //obtener todos los que coinciden (el select)
                    {

                        if(al_inv.get(j).getProd_cve_n().equals(r.getProd_cve_n()))
                        {
                            in= al_inv.get(j);
                        }
                    }

                    if(in!=null)
                    {
                        al_invP.get(i).setProd_cant_n( in.getProd_cant_n() );
                        al_invP.get(i).setProd_devuelto_n(  in.getInv_devuelto_n()  );
                        al_invP.get(i).setProd_recuperado_n( in.getInv_recuperado_n() );
                        al_invP.get(i).setProd_prestado_n( in.getInv_prestado_n()  );

                        if( Integer.parseInt(in.getInv_cancelado_n())  > Integer.parseInt(in.getProd_cant_n())  )
                        {
                            al_invP.get(i).setProd_cancelado_n( in.getProd_cant_n() );
                        }
                        else
                        {
                            al_invP.get(i).setProd_cancelado_n( in.getInv_cancelado_n() );
                        }
                    }
                }

                mostrarInventario();

            }

        }catch (Exception e)
        {
            Log.d("salida","Error al cargar el inventario: "+e.toString());
            Utils.msgError(getContext(), getString(R.string.err_inv5), e.getMessage());
        }
    }

    private void mostrarInventario()
    {
        try
        {
            tableLayout.removeAllViews();
            TableRow tr;
            DataTableLC.InvP inv;

            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_inventario, null);

            ((TextView) tr.findViewById(R.id.ti_sku)).setTypeface(((TextView) tr.findViewById(R.id.ti_sku)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.ti_producto)).setTypeface(((TextView) tr.findViewById(R.id.ti_producto)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.ti_inv)).setTypeface(((TextView) tr.findViewById(R.id.ti_inv)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.ti_dev)).setTypeface(((TextView) tr.findViewById(R.id.ti_dev)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.ti_can)).setTypeface(((TextView) tr.findViewById(R.id.ti_can)).getTypeface(), Typeface.BOLD);

            tableLayout.addView(tr);


            if (al_invP != null)
                for (int i = 0; i < al_invP.size(); i++) {

                    tr = (TableRow) layoutInflater.inflate(R.layout.tabla_inventario, null);
                    inv = al_invP.get(i);

                    ((TextView) tr.findViewById(R.id.ti_sku)).setText(inv.getProd_sku_str());
                    ((TextView) tr.findViewById(R.id.ti_producto)).setText(inv.getProd_desc_str());
                    ((TextView) tr.findViewById(R.id.ti_inv)).setText(inv.getProd_cant_n());
                    ((TextView) tr.findViewById(R.id.ti_dev)).setText(inv.getProd_devuelto_n());
                    ((TextView) tr.findViewById(R.id.ti_can)).setText(inv.getProd_cancelado_n());

                    tableLayout.addView(tr);
                }
        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_mostrarInfo), e.getMessage());
        }
    }

    private void verificarImpresion()
    {
        try
        {
            if (button_imprimir.getText().toString().equals(getString(R.string.bt_imprimir))) {
                imprimir();
            } else if (button_imprimir.getText().toString().equals(getString(R.string.bt_descargar))) {
                try {
                    botonDescargar = true;
                    enviarCambios();
                    //al terminar enviar cambios, se ejecuta peticionDescarga();

                } catch (Exception e) {
                    Log.d("salida", "Error: " + e.getMessage());
                    Utils.msgError(getContext(), getString(R.string.error_imprimir), e.getMessage());
                }
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_imprimir), e.getMessage());
        }
    }

    private void imprimir()
    {
        try
        {
            String mensajeImp = "\n";
            String rutaCve = Utils.LeefConfig("ruta", getContext());
            String rutaDes = BaseLocal.SelectDato(string.formatSql("select rut_desc_str from rutas where rut_cve_n={0}", rutaCve), getContext());

            mensajeImp += "Ruta: " + rutaDes + "\n";

            mensajeImp += string.formatSql("ASESOR: {0} {1} {2}\r\n", user.getNombre(), user.getAppat(), user.getApmat());

            mensajeImp += "FECHA IMPRESION: " + Utils.FechaLocal() + " " + Utils.HoraLocal() + "\n\n";
            mensajeImp += "I N V E N T A R I O\n\n";

            ArrayList<DataTableLC.InvP> dgInventario = al_invP;
            ArrayList<DataTableLC.InvP> dtProdAux = new ArrayList<>();
            ArrayList<DataTableLC.InvP> dtVacio = new ArrayList<>();
            DataTableLC.InvP dg;
            String des;

            final int maxCaracteres = 14;
            long cerv = 0;
            long env = 0;

            mensajeImp += string.formatSql("{0} {1} {2} {3}", "  SKU ", "    PRODUCTO       ", "CANT", " CAN") + "\n";

            for (int i = 0; i < dgInventario.size(); i++) {
                dg = dgInventario.get(i);
                if (Integer.parseInt(dg.getProd_cant_n()) > 0 && !dg.getCat_desc_str().equals("ENVASE")) {
                    dtProdAux.add(dg);
                    des = dg.getProd_desc_str();
                    if (des.length() > maxCaracteres)
                        des = des.substring(0, maxCaracteres);
                    else {
                        for (int j = des.length(); j < maxCaracteres; j++) {
                            des = des.concat(" ");
                        }
                    }
                    mensajeImp += string.formatSql("{0}    {1}   {2}    {3}", dg.getProd_sku_str(), des, dg.getProd_cant_n(), dg.getProd_cancelado_n()) + "\n";

                    cerv += Integer.parseInt(dg.getProd_cant_n());
                }
            }


            mensajeImp += "\n\n" + string.formatSql("{0} {1} {2} {3} {4}", "SKU  ", " ENVASE     ", "LLENO", "VACIO", "CAN") + "\n";

            for (int i = 0; i < dgInventario.size(); i++) {
                dg = dgInventario.get(i);
                if (dg.getCat_desc_str().equals("ENVASE")) {
                    dtVacio.add(dg);
                    des = dg.getProd_desc_str();
                    if (des.length() > maxCaracteres) {
                        des = des.substring(0, maxCaracteres);
                    } else {
                        for (int j = des.length(); j < maxCaracteres; j++) {
                            des = des.concat(" ");
                        }
                    }
                    mensajeImp += string.formatSql("{0}   {1}  {2}   {3}   {4}", dg.getProd_sku_str(), des, "0", "0", dg.getProd_cancelado_n()) + "\n";

                    env += Integer.parseInt(dg.getProd_cant_n());
                }
            }

            mensajeImp += "\n";


            mensajeImp += "TOTAL CERVEZA: " + cerv + "\n";
            mensajeImp += "TOTAL ENVASE: " + env;

            mensajeImp += "\n\n";

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle(getString(R.string.msg_impInv));
            dialogo1.setMessage(mensajeImp);
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    obtenerUbicacion("InsertBitacora");
                }
            });
            dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //cancelar();
                }
            });
            dialogo1.show();

            Log.d("salida", mensajeImp);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_imprimir), e.getMessage());
        }

    }

    private void obtenerUbicacion(final String accion)
    {
        try {

            final MainActivity mainActivity = (MainActivity) getActivity();
            final String[] ubi = new String[1];
            final ProgressDialog progress = new ProgressDialog(getContext());
            progress.setTitle(getString(R.string.msg_cargando));
            progress.setMessage(getString(R.string.msg_espera));
            progress.show();
            progress.setCancelable(false);

            mainActivity.enableLocationUpdates();

            ubi[0] = mainActivity.getLatLon();
            Log.d("salida", "Ubicacion anterior: " + ubi[0]);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.cancel();
                    mainActivity.disableLocationUpdates();
                    ubi[0] = mainActivity.getLatLon();

                    if(accion.equals("InsertBitacora"))
                    {
                        BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, conf.getUsuario(), "INVENTARIO", "IMPRESION DE INVENTARIO", String.valueOf( conf.getRuta() ) , ubi[0]),getContext());
                    }
                    else
                    if(accion.equals("VentaAutomatica"))
                    {
                        ventaAutomatica(ubi[0]);
                    }

                }
            }, 3000);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_ubicacion), e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }

    }

    private void enviarCambios()
    {
        String ds;
        try
        {
            // Establecer ventas a enviar
            BaseLocal.Insert("update ventas set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Ventas
            String ventas = BaseLocal.SelectUpload("select * from ventas where trans_est_n=1",getContext());
            // Leer Detalle Ventas
            String ventasDet = BaseLocal.SelectUpload("select * from ventasdet where ven_folio_str in (select ven_folio_str from ventas where trans_est_n=1)",getContext() );
            // Leer Venta Envase
            String ventasEnv=BaseLocal.SelectUpload("select * from ventaenv where ven_folio_str in (select ven_folio_str from ventas where trans_est_n=1)",getContext());
            // Establecer Creditos a enviar
            BaseLocal.Insert("update creditos set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Creditos
            String creditos = BaseLocal.SelectUpload("select * from creditos where trans_est_n=1",getContext());
            // Establecer Pagos a enviar
            BaseLocal.Insert("update Pagos set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Pagos
            String pagos = BaseLocal.SelectUpload("select * from Pagos where trans_est_n=1",getContext());
            // Establecer Visitas a enviar
            BaseLocal.Insert("update visitas set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Visitas
            String visitas= BaseLocal.SelectUpload("select * from visitas where trans_est_n=1",getContext());
            // Establecer Bitacora a enviar
            BaseLocal.Insert("update BitacoraHH set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Bitacora
            String bitacoraHH = BaseLocal.SelectUpload ("select * from BitacoraHH where trans_est_n=1",getContext());

            //ds = ventas+"|"+ventasDet+"|"+ventasEnv+"|"+creditos+"|"+pagos+"|"+visitas+"|"+bitacoraHH;

            ds = ventas+"|"+ventasDet+"|"+ventasEnv+"|"+creditos+"|"+pagos+"|"+visitas+"|"+bitacoraHH;

            //ds = "[]"+"|"+"[]"+"|"+"[]"+"|"+"[]"+"|"+pagos+"|"+visitas+"|"+bitacoraHH;

            //----- Preventa ------
            if (conf.getPreventa() == 1)
            {
                String preventa= BaseLocal.SelectUpload("select * from preventa",getContext());
                String preventaDet= BaseLocal.SelectUpload("select * from preventadet",getContext());
                String preventaEnv= BaseLocal.SelectUpload("select * from preventaenv",getContext());
                String preventaPagos= BaseLocal.SelectUpload("select * from preventapagos",getContext());
                String visitaPreventa= BaseLocal.SelectUpload("select * from visitapreventa",getContext());

                ds+="|"+preventa+"|"+preventaDet+"|"+preventaEnv+"|"+preventaPagos+"|"+visitaPreventa;
            }

            Log.d("salida","ds: "+ds);

            peticion="RecibirDatos";

            //parametros del metodo
            ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
            PropertyInfo piUser = new PropertyInfo();
            piUser.setName("ds");
            piUser.setValue(ds);
            propertyInfos.add(piUser);

            PropertyInfo piPass = new PropertyInfo();
            piPass.setName("ruta");
            piPass.setValue(conf.getRuta());
            propertyInfos.add(piPass);

            //conexion con el metodo
            ConexionWS_JSON conexionWS = new ConexionWS_JSON(getContext(), "RecibirDatos2J");
            conexionWS.delegate = InventarioFragment.this;
            conexionWS.propertyInfos = propertyInfos;
            conexionWS.execute();


        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Utils.msgError(getContext(), getString(R.string.error_intPeticion), e.getMessage());
        }
    }

    private void peticionDescarga()
    {
        try {
            peticion = "RecibirDescarga";

            String dtJson = new Gson().toJson(al_invP);

            //parametros del metodo
            ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
            PropertyInfo piTable = new PropertyInfo();
            piTable.setName("dt");
            piTable.setValue(dtJson);
            propertyInfos.add(piTable);

            PropertyInfo piRuta = new PropertyInfo();
            piRuta.setName("ruta");
            piRuta.setValue(conf.getRuta());
            propertyInfos.add(piRuta);

            PropertyInfo piUser = new PropertyInfo();
            piUser.setName("usuario");
            piUser.setValue(conf.getUsuario());
            propertyInfos.add(piUser);

            //conexion con el metodo
            ConexionWS_JSON conexionWS = new ConexionWS_JSON(getContext(), "RecibirDescarga2J");
            conexionWS.delegate = InventarioFragment.this;
            conexionWS.propertyInfos = propertyInfos;
            conexionWS.execute();
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_intPeticion), e.getMessage());
        }
    }

    private void recibirDescarga(final String mensaje)
    {
        try {

            final MainActivity mainActivity = (MainActivity) getActivity();
            final String[] ubi = new String[1];
            final ProgressDialog progress = new ProgressDialog(getContext());
            progress.setTitle(getString(R.string.msg_cargando));
            progress.setMessage(getString(R.string.msg_espera));
            progress.show();
            progress.setCancelable(false);

            mainActivity.enableLocationUpdates();

            ubi[0] = mainActivity.getLatLon();
            Log.d("salida", "Ubicacion anterior: " + ubi[0]);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progress.cancel();
                    mainActivity.disableLocationUpdates();
                    ubi[0] = mainActivity.getLatLon();

                    //ACTUALIZAR LA INFORMACIÓN

                    BaseLocal.Insert(Querys.ConfiguracionHH.DesactivarPedidos,getContext());
                    BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, conf.getUsuario(), "INVENTARIO", "DESCARGA DE INVENTARIO", String.valueOf(conf.getRuta()) , ubi[0]),getContext());
                    Toast.makeText(getContext(), getString(R.string.tt_infoActu)+". "+ mensaje, Toast.LENGTH_LONG).show();


                }
            }, 3000);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_almacenar), e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }

    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta)
    {
        try {
            if (estado) {
                //recibio información
                if (respuesta != null) {
                    if (peticion.equals("RecibirDatos")) {
                        Log.d("salida", "resWS: " + respuesta);
                        DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                        if (retVal != null) {
                            if (retVal.getRet().equals("true"))
                                establecerEnviado(retVal.getMsj());
                            else
                                establecerNoEnviado(retVal.getMsj());
                        } else {
                            establecerNoEnviado(getString(R.string.err_invNoinfo));
                        }

                        if (botonDescargar) {
                            botonDescargar = false;
                            peticionDescarga();
                        }

                    } else if (peticion.equals("RecibirDescarga")) {
                        Log.d("salida", "resWS: " + respuesta);

                        DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                        if (retVal != null) {
                            if (retVal.getRet().equals("true")) {
                                recibirDescarga(retVal.getMsj());
                            } else {
                                Toast.makeText(getContext(), retVal.getMsj(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getContext(), getString(R.string.err_inv9), Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.err_inv9), Toast.LENGTH_LONG).show();
                    if (peticion.equals("RecibirDatos"))
                        establecerNoEnviado(getString(R.string.err_invNoinfo));
                }
            } else {
                Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();
                if (peticion.equals("RecibirDatos"))
                    establecerNoEnviado(respuesta);
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_peticion), e.getMessage() );
        }
    }

    private void establecerEnviado(String men)
    {
        try
        {
            // Establecer ventas como enviadas
            BaseLocal.Insert("update ventas set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1", getContext());
            // Establecer Creditos como enviados
            BaseLocal.Insert("update creditos set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1", getContext());
            // Establecer Pagos como enviados
            BaseLocal.Insert("update Pagos set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1", getContext());
            // Establecer Visitas como enviadas
            BaseLocal.Insert("update visitas set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1", getContext());
            // Establecer Bitacora como enviadas
            BaseLocal.Insert("update BitacoraHH set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1", getContext());

            Toast.makeText(getContext(),  getString(R.string.tt_menServido)+" " + men, Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_almacenar),e.getMessage());
        }
    }

    private void establecerNoEnviado(String men)
    {
        try {
            // Establecer ventas como no enviadas
            BaseLocal.Insert("update ventas set trans_est_n=0 where trans_est_n=1", getContext());
            // Establecer Creditos como no enviados
            BaseLocal.Insert("update creditos set trans_est_n=0 where trans_est_n=1", getContext());
            // Establecer Pagos como no enviados
            BaseLocal.Insert("update Pagos set trans_est_n=0 where trans_est_n=1", getContext());
            // Establecer Visitas como no enviadas
            BaseLocal.Insert("update visitas set trans_est_n=0 where trans_est_n=1", getContext());
            // Establecer Bitacora como no enviadas
            BaseLocal.Insert("update BitacoraHH set trans_est_n=0 where trans_est_n=1", getContext());
            Toast.makeText(getContext(), getString(R.string.tt_menServido)+" " + men, Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_almacenar),e.getMessage());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mainActivity.validarMenu();
        Log.d("salida","ENTRO ACTUALIZAR MENU");

    }

}
