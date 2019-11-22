package com.tdt.easyroute.Fragments.Inventario;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class InventarioFragment extends Fragment implements AsyncResponseJSON {

    private static Usuario user;
    private static boolean descarga;

    private Button button_imprimir,button_salir;

    private Configuracion conf=null;

    private ArrayList<DataTableLC.EnvaseAut> al_envaseAut;
    private ArrayList<DataTableLC.InvP> al_invP;
    private ArrayList<DataTableLC.Inv> al_inv;

    String peticion;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;

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
        layoutInflater = inflater;

        button_imprimir = view.findViewById(R.id.button_imprimir);
        button_salir = view.findViewById(R.id.button_salir);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);


        if(descarga)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Descarga de inventario");
            button_imprimir.setText("Descargar");
        }
        else
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Inventario actual");
            button_imprimir.setText("Imprimir");
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

        return view;
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
            Toast.makeText(getContext(), "Error tabla envase automatico: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void cargarInventario(int rut)
    {
        try{
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
            Toast.makeText(getContext(), "Error al cargar el inventario: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void mostrarInventario()
    {
        tableLayout.removeAllViews();
        TableRow tr;
        DataTableLC.InvP inv;

        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_inventario, null);

        ((TextView) tr.findViewById(R.id.ti_sku)).setText("SKU");
        ((TextView) tr.findViewById(R.id.ti_sku)).setTypeface( ((TextView) tr.findViewById(R.id.ti_sku)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.ti_producto)).setText("Producto");
        ((TextView) tr.findViewById(R.id.ti_producto)).setTypeface( ((TextView) tr.findViewById(R.id.ti_producto)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.ti_inv)).setText("Inv.");
        ((TextView) tr.findViewById(R.id.ti_inv)).setTypeface( ((TextView) tr.findViewById(R.id.ti_inv)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.ti_dev)).setText("Dev.");
        ((TextView) tr.findViewById(R.id.ti_dev)).setTypeface( ((TextView) tr.findViewById(R.id.ti_dev)).getTypeface(), Typeface.BOLD);
        ((TextView) tr.findViewById(R.id.ti_can)).setText("Can.");
        ((TextView) tr.findViewById(R.id.ti_can)).setTypeface( ((TextView) tr.findViewById(R.id.ti_can)).getTypeface(), Typeface.BOLD);

        tableLayout.addView(tr);


        if(al_invP!=null)
            for(int i=0; i<al_invP.size();i++)
            {

                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_inventario, null);
                inv=al_invP.get(i);

                ((TextView) tr.findViewById(R.id.ti_sku)).setText(inv.getProd_sku_str());
                ((TextView) tr.findViewById(R.id.ti_producto)).setText(inv.getProd_desc_str());
                ((TextView) tr.findViewById(R.id.ti_inv)).setText(inv.getProd_cant_n());
                ((TextView) tr.findViewById(R.id.ti_dev)).setText(inv.getProd_devuelto_n());
                ((TextView) tr.findViewById(R.id.ti_can)).setText(inv.getProd_cancelado_n());

                tableLayout.addView(tr);
            }
    }

    private void verificarImpresion()
    {
        if(button_imprimir.getText().toString().equals("Imprimir"))
        {
            imprimir();
        }
        else if(button_imprimir.getText().toString().equals("Descargar"))
        {
            try
            {
                enviarCambios();
                peticionDescarga();

            }catch (Exception e)
            {
                Log.d("salida","Error: "+ e.getMessage());
                Toast.makeText(getContext(), "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void imprimir()
    {
        String mensajeImp="\n";
        String rutaCve = Utils.LeefConfig("ruta",getContext());
        String rutaDes = BaseLocal.SelectDato(string.formatSql("select rut_desc_str from rutas where rut_cve_n={0}",rutaCve),getContext());

        mensajeImp+= "Ruta: "+ rutaDes+"\n";

        mensajeImp+= string.formatSql("ASESOR: {0} {1} {2}\r\n", user.getNombre(), user.getAppat(),user.getApmat());

        mensajeImp+= "FECHA IMPRESION: " +Utils.FechaLocal()+" "+Utils.HoraLocal()+"\n\n";
        mensajeImp+= "I N V E N T A R I O\n\n";

        ArrayList<DataTableLC.InvP> dgInventario = al_invP;
        ArrayList<DataTableLC.InvP> dtProdAux = new ArrayList<>();
        ArrayList<DataTableLC.InvP> dtVacio = new ArrayList<>();
        DataTableLC.InvP dg; String des;

        final int maxCaracteres=14;
        long cerv = 0;
        long env = 0;

        mensajeImp+= string.formatSql("{0} {1} {2} {3}", "  SKU ", "    PRODUCTO       ", "CANT", " CAN") + "\n";

        for(int i=0; i<dgInventario.size();i++)
        {
            dg=dgInventario.get(i);
            if( Integer.parseInt( dg.getProd_cant_n() )>0  &&  !dg.getCat_desc_str().equals("ENVASE") )
            {
                dtProdAux.add(dg);
                des = dg.getProd_desc_str();
                if(des.length()>maxCaracteres)
                    des = des.substring(0,maxCaracteres);
                else
                {
                    for(int j=des.length();j<maxCaracteres;j++) { des=des.concat(" "); }
                }
                mensajeImp+= string.formatSql("{0}    {1}   {2}    {3}",dg.getProd_sku_str(),des,dg.getProd_cant_n(),dg.getProd_cancelado_n())+"\n";

                cerv+= Integer.parseInt( dg.getProd_cant_n() );
            }
        }


        mensajeImp+= "\n\n"+ string.formatSql("{0} {1} {2} {3} {4}", "SKU  ", " ENVASE     ","LLENO", "VACIO", "CAN")+"\n";

        for(int i=0; i<dgInventario.size();i++)
        {
            dg=dgInventario.get(i);
            if( dg.getCat_desc_str().equals("ENVASE") )
            {
                dtVacio.add(dg);
                des = dg.getProd_desc_str();
                if(des.length()>maxCaracteres) {
                    des = des.substring(0, maxCaracteres);
                }
                else
                {
                    for(int j=des.length();j<maxCaracteres;j++) { des=des.concat(" "); }
                }
                mensajeImp+= string.formatSql("{0}   {1}  {2}   {3}   {4}",dg.getProd_sku_str(),des,"0","0",dg.getProd_cancelado_n())+"\n";

                env+= Integer.parseInt( dg.getProd_cant_n() );
            }
        }

        mensajeImp+= "\n";


        mensajeImp+= "TOTAL CERVEZA: "+cerv+"\n";
        mensajeImp+= "TOTAL ENVASE: "+env;

        mensajeImp+= "\n\n";

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("¿Imprimir inventario?");
        dialogo1.setMessage(mensajeImp);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                obtenerUbicacion("InsertBitacora");
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        dialogo1.show();

        Log.d("salida",mensajeImp);

    }

    private void obtenerUbicacion(final String accion)
    {
        try {

            final MainActivity mainActivity = (MainActivity) getActivity();
            final String[] ubi = new String[1];
            final ProgressDialog progress = new ProgressDialog(getContext());
            progress.setTitle("Actualizando");
            progress.setMessage("Por favor espere");
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

                }
            }, 3000);

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
            String ventas = BaseLocal.Select("select * from ventas where trans_est_n=1",getContext());
            // Leer Detalle Ventas
            String ventasDet = BaseLocal.Select("select * from ventasdet where ven_folio_str in (select ven_folio_str from ventas where trans_est_n=1)",getContext() );
            // Leer Venta Envase
            String ventasEnv=BaseLocal.Select("select * from ventaenv where ven_folio_str in (select ven_folio_str from ventas where trans_est_n=1)",getContext());
            // Establecer Creditos a enviar
            BaseLocal.Insert("update creditos set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Creditos
            String creditos = BaseLocal.Select("select * from creditos where trans_est_n=1",getContext());
            // Establecer Pagos a enviar
            BaseLocal.Insert("update Pagos set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Pagos
            String pagos = BaseLocal.Select("select * from Pagos where trans_est_n=1",getContext());
            // Establecer Visitas a enviar
            BaseLocal.Insert("update visitas set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Visitas
            String visitas= BaseLocal.Select("select * from visitas where trans_est_n=1",getContext());
            // Establecer Bitacora a enviar
            BaseLocal.Insert("update BitacoraHH set trans_est_n=1 where trans_est_n=0",getContext());
            // Leer Bitacora
            String bitacoraHH = BaseLocal.Select ("select * from BitacoraHH where trans_est_n=1",getContext());

            ds = ventas+"|"+ventasDet+"|"+ventasEnv+"|"+creditos+"|"+pagos+"|"+visitas+"|"+bitacoraHH;

            //----- Preventa ------
            if (conf.getPreventa() == 1)
            {
                String preventa= BaseLocal.Select("select * from preventa",getContext());
                String preventaDet= BaseLocal.Select("select * from preventadet",getContext());
                String preventaEnv= BaseLocal.Select("select * from preventaenv",getContext());
                String preventaPagos= BaseLocal.Select("select * from preventapagos",getContext());
                String visitaPreventa= BaseLocal.Select("select * from visitapreventa",getContext());

                ds+="|"+preventa+"|"+preventaDet+"|"+preventaEnv+"|"+preventaPagos+"|"+visitaPreventa;
            }

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
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void peticionDescarga()
    {
        peticion="RecibirDescarga";

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
    }

    private void recibirDescarga(final String mensaje)
    {
        try {

            final MainActivity mainActivity = (MainActivity) getActivity();
            final String[] ubi = new String[1];
            final ProgressDialog progress = new ProgressDialog(getContext());
            progress.setTitle("Actualizando");
            progress.setMessage("Por favor espere");
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
                    Toast.makeText(getContext(), "Descarga completada. " + mensaje, Toast.LENGTH_LONG).show();


                }
            }, 3000);

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }

    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {
        if(estado)
        {
            //recibio información
            if (respuesta != null)
            {
                if(peticion.equals("RecibirDatos"))
                {
                    Log.d("salida","resWS: "+respuesta);
                    DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                    if(retVal!=null)
                    {
                        if(retVal.getRet().equals("true"))
                            establecerEnviado(retVal.getMsj());
                        else
                            establecerNoEnviado(retVal.getMsj());
                    }
                    else
                    {
                        establecerNoEnviado("No se encontro información");
                    }
                }
                else
                if(peticion.equals("RecibirDescarga"))
                {
                    Log.d("salida","resWS: "+respuesta);

                    DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                    if(retVal!=null)
                    {
                        if(retVal.getRet().equals("true"))
                        {
                            recibirDescarga(retVal.getMsj());
                        }
                        else
                        {
                            Toast.makeText(getContext(), retVal.getMsj(), Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Error al descargar inventario", Toast.LENGTH_LONG).show();
                    }

                }

            }
            else
            {
                Toast.makeText(getContext(), "Error al descargar inventario", Toast.LENGTH_LONG).show();
                if(peticion.equals("RecibirDatos"))
                    establecerNoEnviado("No se encontro información");
            }
        }
        else
        {
            Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();
            if(peticion.equals("RecibirDatos"))
                establecerNoEnviado(respuesta);
        }
    }

    public void establecerEnviado(String men)
    {
        // Establecer ventas como enviadas
        BaseLocal.Insert("update ventas set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1",getContext());
        // Establecer Creditos como enviados
        BaseLocal.Insert("update creditos set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1",getContext());
        // Establecer Pagos como enviados
        BaseLocal.Insert("update Pagos set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1",getContext());
        // Establecer Visitas como enviadas
        BaseLocal.Insert("update visitas set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1",getContext());
        // Establecer Bitacora como enviadas
        BaseLocal.Insert("update BitacoraHH set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1",getContext());

        Toast.makeText(getContext(), "Mensaje del servidor: "+men, Toast.LENGTH_SHORT).show();
    }

    public void establecerNoEnviado(String men)
    {
        // Establecer ventas como no enviadas
        BaseLocal.Insert("update ventas set trans_est_n=0 where trans_est_n=1",getContext());
        // Establecer Creditos como no enviados
        BaseLocal.Insert("update creditos set trans_est_n=0 where trans_est_n=1",getContext());
        // Establecer Pagos como no enviados
        BaseLocal.Insert("update Pagos set trans_est_n=0 where trans_est_n=1",getContext());
        // Establecer Visitas como no enviadas
        BaseLocal.Insert("update visitas set trans_est_n=0 where trans_est_n=1",getContext());
        // Establecer Bitacora como no enviadas
        BaseLocal.Insert("update BitacoraHH set trans_est_n=0 where trans_est_n=1",getContext());
        Toast.makeText(getContext(), "Mensaje del servidor: "+men, Toast.LENGTH_SHORT).show();
    }

}
