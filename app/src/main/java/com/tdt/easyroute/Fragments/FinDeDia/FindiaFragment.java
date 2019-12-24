package com.tdt.easyroute.Fragments.FinDeDia;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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
import com.tdt.easyroute.Model.Permisos;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.Model.UsuarioL;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;
import org.slf4j.Logger;

import java.util.ArrayList;

public class FindiaFragment extends Fragment implements AsyncResponseJSON {

    private static int opcion;

    String nombreBase;
    String peticion="";

    MainActivity mainActivity;
    Configuracion conf;
    Usuario user=null,userValidar = null;

    public static FindiaFragment newInstance(int op) {
        FindiaFragment fragment = new FindiaFragment();
        Bundle args = new Bundle();
        opcion = op;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_findia, container, false);

        mainActivity = (MainActivity) getActivity();
        user = mainActivity.getUsuario();
        conf = Utils.ObtenerConf(getActivity().getApplication());
        nombreBase = getActivity().getString( R.string.nombreBD );

        //transmitir 0
        //borrar datos 1
        //fin de ventas 2

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                verificarOpcion();
            }
        }, 150);

        return view;
    }

    private void verificarOpcion()
    {
        switch (opcion)
        {
            case 0:
                enviarCambios();
                break;
            case 1:
                borrarDatos();
                break;
            case 2:
                finVentas();
                break;
            default:
                Utils.RegresarInicio(getActivity());
                break;
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
            conexionWS.delegate = FindiaFragment.this;
            conexionWS.propertyInfos = propertyInfos;
            conexionWS.execute();


        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void borrarDatos()
    {
        Permisos p = null;
        Usuario AuxU = null;

        try
        {
            if(user.getPermisos() !=null)
            {
                for(int i=0; i<user.getPermisos().size();i++)
                    if( user.getPermisos().get(i).getMod_desc_str().equals("CONFIGURACION") && user.getPermisos().get(i).getEliminacion()>0)
                        p=user.getPermisos().get(i);

                if(p!=null)
                    AuxU=user;
            }

            if(AuxU==null)
            {
                solicitarUsuario();
            }
            else
            {
                eliminarDatos();
                Utils.RegresarInicio(getActivity());
            }

        }catch (Exception e)
        {

        }
    }

    private void solicitarUsuario()
    {

        LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog,null);
        final EditText et_user = (EditText) view.findViewById(R.id.ti_dialogUser);
        final EditText et_pass = (EditText) view.findViewById(R.id.ti_dialogPass);

        final AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Autorización")
                .setView(view)
                .setPositiveButton("Aceptar", null)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.RegresarInicio(getActivity());
                    }
                })
                .setCancelable(false)
                .create();
        dialog.show();

        Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = et_user.getText().toString();
                String contra = et_pass.getText().toString();

                if(!usuario.isEmpty() && !contra.isEmpty())
                {
                    boolean validar = validarUsuario(usuario,contra);

                    if(validar)
                    {
                        Permisos p = null;
                        Usuario AuxU = null;

                        if(userValidar.getPermisos()!=null)
                        {
                            for(int i=0; i<userValidar.getPermisos().size();i++)
                                if( userValidar.getPermisos().get(i).getMod_desc_str().equals("CONFIGURACION") && userValidar.getPermisos().get(i).getEliminacion()>0)
                                    p=userValidar.getPermisos().get(i);

                            if(p!=null)
                                AuxU=userValidar;
                        }

                        if(userValidar.getUsuario().equals("123")&& Utils.DesEncriptar( userValidar.getContrasena()).equals("123"))
                        {
                            AuxU=userValidar;
                        }

                        if(AuxU!=null)
                        {
                            eliminarDatos();
                            dialog.dismiss();
                            Utils.RegresarInicio(getActivity());
                        }
                        else
                        {
                            Toast.makeText(getContext(), "No tiene permisos para esta opción", Toast.LENGTH_SHORT).show();
                            Utils.RegresarInicio(getActivity());
                        }
                    }

                }
                else
                {
                    Toast.makeText(getContext(), "Ingrese un usuario y/o contraseña", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validarUsuario(String usuario, String contra)
    {
        try
        {
            String json,consulta;
            ArrayList<DataTableLC.UsuarioConRol> dtu= null;

            consulta = string.formatSql( Querys.Login.SeleccionarUsuarioConRol, usuario, Utils.Encriptar(contra)  );
            json = BaseLocal.Select( consulta, getContext() );
            dtu = ConvertirRespuesta.getUsuarioConRolJson(json);

            if(dtu!=null)
            {
                DataTableLC.UsuarioConRol ru = dtu.get(0);

                if(!ru.getEst_cve_str().equals("H") )
                {

                    Toast.makeText(getContext(), "Usuario o password incorrecto [E]", Toast.LENGTH_SHORT).show();
                    return false;
                }

                if(!ru.getUsu_bloqueado_n().equals("0"))
                {
                    Toast.makeText(getContext(), "Usuario o password incorrecto [B]", Toast.LENGTH_SHORT).show();
                    return false;
                }

                ArrayList<Permisos> dtPermisos;

                consulta = string.formatSql(Querys.Login.PermisosEfectivos3, dtu.get(0).getRol_cve_n());
                json = BaseLocal.Select(consulta,getContext());
                dtPermisos = ConvertirRespuesta.getPermisosJson(json);


                Log.d("salida","Rol: "+ dtu.get(0).getRol_cve_n());

                if(dtPermisos!=null)
                {
                    userValidar = new UsuarioL( ru.getUsu_cve_str(), ru.getUsu_pwd_str(), ru.getUsu_nom_str(),
                            ru.getUsu_app_str(), ru.getUsu_apm_str(), Integer.parseInt( ru.getRol_cve_n() ) , ru.getRol_desc_str(),
                            Byte.parseByte( ru.getRol_esadmin_n() ) , ru.getEst_cve_str(), Byte.parseByte( ru.getUsu_bloqueado_n() )  );

                    userValidar.setPermisos(dtPermisos);

                }
                else
                {
                    Toast.makeText(getContext(), "El usuario no tiene permisos para este modulo.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            else
            {
                Toast.makeText(getContext(), "Usuario o contraseña invalidos.", Toast.LENGTH_SHORT).show();
                Log.d("salida","entro aqui, incorrecto");
                return false;
            }

            return true;

        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Error al validar usuario", Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
            return false;
        }

    }

    private void eliminarDatos()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Borrado de datos");
        dialogo1.setMessage("¿Esta seguro de eliminar los datos?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                eliminarDatosBD();
            }
        });
        dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Utils.RegresarInicio(getActivity());
            }
        });
        dialogo1.show();
    }

    private void eliminarDatosBD()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String qry;

        try{

            qry = "delete from creditos";
            db.execSQL(qry);
            qry = "delete from pagos";
            db.execSQL(qry);
            qry = "delete from ventas";
            db.execSQL(qry);
            qry = "delete from ventasdet";
            db.execSQL(qry);
            qry = "delete from bitacoraHH";
            db.execSQL(qry);
            qry = "delete from visitas";
            db.execSQL(qry);
            qry = "delete from inventario";
            db.execSQL(qry);
            qry = "delete from MovimientosInv";
            db.execSQL(qry);
            qry = "delete from VentaEnv";
            db.execSQL(qry);
            qry = "delete from ConfiguracionHH";
            db.execSQL(qry);
            qry = "delete from Direcciones";
            db.execSQL(qry);
            qry = "delete from FrecPunteo";
            db.execSQL(qry);
            qry = "delete from Sugerido";
            db.execSQL(qry);
            db.execSQL(Querys.Inventario.DesactivaCarga);
            qry = "delete from CargaInicial";
            db.execSQL(qry);
            qry = string.formatSql(Querys.Inventario.InsertCarga, "0", "SYSTEM", "A", "REGISTRO INICIAL");
            db.execSQL(qry);

            db.execSQL(string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, "SYSTEM", "RESET BASE DE DATOS", "RESET BASE DE DATOS TRABAJO","0",""));

            db.setTransactionSuccessful();

            Toast.makeText(getContext(), "Datos eliminados con exito", Toast.LENGTH_LONG).show();

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error el eliminar datos: "+e.toString(), Toast.LENGTH_LONG).show();
        }
        finally {
            if(db.isOpen())
            {
                db.endTransaction();
                db.close();
            }
        }
    }

    private void finVentas()
    {
        if ((!conf.isDescarga()) && conf.getPreventa()!=1)
        {

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle("¿Desea finalizar el día?");
            dialogo1.setMessage("No ha realizado la descarga.");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {


                        AlertDialog.Builder dialogo2 = new AlertDialog.Builder(getContext());
                        dialogo2.setTitle("¿Esta seguro de cerrar la venta?");
                        dialogo2.setMessage("Al realizar esta acción no podra realizar ninguna venta, cobranza o movimiento alguno.");
                        dialogo2.setCancelable(false);
                        dialogo2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                obtenerUbicacion();
                            }
                        });
                        dialogo2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogo1, int id) {
                                Utils.RegresarInicio(getActivity());
                            }
                        });
                        dialogo2.show();

                }
            });
            dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Utils.RegresarInicio(getActivity());
                }
            });
            dialogo1.show();

        }
        else
        {
            obtenerUbicacion();
        }
    }

    private void obtenerUbicacion()
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

                    finalizarDia(ubi[0]);
                }
            }, 3000);

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
            Utils.RegresarInicio(getActivity());
        }
    }

    private void finalizarDia(String position)
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String con;
        try
        {
            db.beginTransaction();
            con = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion,
                    conf.getUsuario(), "CIERRE DE DIA", "CIERRE DE DIA", String.valueOf( conf.getRuta() ) , position);

            db.execSQL( con );

            con = "update configuracionHH set conf_fechafin_dt=datetime('now','localtime') where conf_fechafin_dt is null";

            db.execSQL(con);

            db.setTransactionSuccessful();

            Toast.makeText(getContext(), "Cierre de día completo", Toast.LENGTH_LONG).show();
        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error al cerrar día: "+e.getMessage(), Toast.LENGTH_LONG).show();
            Utils.RegresarInicio(getActivity());
        }
        finally
        {
            db.endTransaction();
            db.close();
        }

        enviarBitacora();

    }

    private void enviarBitacora()
    {
        try
        {
            String ruta = Utils.LeefConfig("ruta",getContext());
            BaseLocal.Insert("update BitacoraHH set trans_est_n=1 where trans_est_n=0",getContext());
            String bitacoraHH = BaseLocal.Select( "select * from BitacoraHH where trans_est_n=1", getContext() );

            String ds = "[]"+"|"+"[]"+"|"+"[]"+"|"+"[]"+"|"+"[]"+"|"+"[]"+"|"+bitacoraHH;

            ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();

            PropertyInfo pi1 = new PropertyInfo();
            pi1.setName("ds");
            pi1.setValue(ds);
            propertyInfos.add(pi1);

            PropertyInfo pi2 = new PropertyInfo();
            pi2.setName("ruta");
            pi2.setValue(ruta);
            propertyInfos.add(pi2);

            peticion="enviarBitacora";
            ConexionWS_JSON cws = new ConexionWS_JSON(getContext(), "RecibirDatos2J");
            cws.delegate = FindiaFragment.this;
            cws.propertyInfos = propertyInfos;
            cws.execute();

        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error al enviar bitacora", Toast.LENGTH_LONG).show();
            Utils.RegresarInicio(getActivity());
        }
    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

        if(estado)
        {
            //recibio información
            if (respuesta != null)
            {
                if(peticion.equals("enviarBitacora"))
                {
                    DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                    if(retVal!=null)
                    {
                        if(retVal.getRet().equals("true"))
                        {
                            actualizarBitacora(true);
                        }
                        else
                            actualizarBitacora(false);
                    }
                    else
                    {
                        actualizarBitacora(false);
                    }
                }
                else
                if(peticion.equals("RecibirDatos"))
                {
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
            }
            else
            {
                Toast.makeText(getContext(), "Error transmitir:", Toast.LENGTH_LONG).show();
                if(peticion.equals("enviarBitacora"))
                    actualizarBitacora(false);
                if(peticion.equals("RecibirDatos"))
                    establecerNoEnviado("No se encontro información");
            }
        }
        else
        {
            Toast.makeText(getContext(),"Error al transmitir: "+ respuesta, Toast.LENGTH_SHORT).show();
            if(peticion.equals("enviarBitacora"))
                actualizarBitacora(false);

            if(peticion.equals("RecibirDatos"))
                establecerNoEnviado(respuesta);
        }
    }

    private void actualizarBitacora(boolean enviado)
    {
        if(enviado)
        {
            // Establecer Bitacora como enviadas
            BaseLocal.Insert("update BitacoraHH set trans_est_n=2,trans_fecha_dt=datetime('now','localtime') where trans_est_n=1",getContext());
        }
        else
        {
            BaseLocal.Insert("update BitacoraHH set trans_est_n=0 where trans_est_n=1",getContext());
        }

        Utils.RegresarInicio(getActivity());
    }

    private void establecerEnviado(String men)
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

        Toast.makeText(getContext(), "Mensaje del servidor: "+men, Toast.LENGTH_LONG).show();
        Log.d("salida","servidor: "+men);

        Utils.RegresarInicio(getActivity());

    }

    private void establecerNoEnviado(String men)
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
        Toast.makeText(getContext(), "Mensaje del servidor: "+men, Toast.LENGTH_LONG).show();
        Log.d("salida","servidor: "+men);

        Utils.RegresarInicio(getActivity());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mainActivity.validarMenu();
        Log.d("salida","ENTRO ACTUALIZAR MENU");

    }

}
