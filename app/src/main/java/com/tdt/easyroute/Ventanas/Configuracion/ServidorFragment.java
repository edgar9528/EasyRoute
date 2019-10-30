package com.tdt.easyroute.Ventanas.Configuracion;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.ParametrosWS;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTable;
import com.tdt.easyroute.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ServidorFragment extends Fragment {

    public ServidorFragment() {
        // Required empty public constructor
    }
    private View viewGral;

    String nombreBase;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    ArrayList<String> lista_catalogos;
    ArrayList<String> metodosWS;
    boolean[] rbSeleccionados;
    Button button_selec,button_deselec,button_sinc;
    Configuracion conf;
    boolean precioPreventa=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servidor, container, false);
        viewGral = view;
        layoutInflater = inflater;

        try{

            inicializar();


            button_selec = view.findViewById(R.id.button_selec);
            button_deselec = view.findViewById(R.id.button_desSelec);
            button_sinc = view.findViewById(R.id.button_sincronizar);

            button_selec.setOnClickListener(btListener);
            button_deselec.setOnClickListener(btListener);

            button_sinc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    metodosWS = new ArrayList<>();
                    boolean rbSelec=false;

                    for(int i=0; i<rbSeleccionados.length;i++)
                    {
                        if(rbSeleccionados[i])
                        {
                            rbSelec=true;

                            //OBTENER LA LISTA DE METODOS DE ACUERDO A LOS RADIOBUTTON SELECCIONADOS
                            if(lista_catalogos.get(i).equals("PrecioProductos") && precioPreventa)
                            {
                                metodosWS.add("Obtener"+"PrecioProductosPreventa"+"J");
                            }
                            else
                            {
                                metodosWS.add("Obtener"+lista_catalogos.get(i)+"J");
                            }

                        }
                    }

                    if(rbSelec)
                    {
                        //Descargar información
                        ConexionWS conexionWS = new ConexionWS(getContext());
                        conexionWS.execute();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Debe seleccionar algun elemento", Toast.LENGTH_SHORT).show();
                    }


                }
            });




        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());

        }


        return view;
    }

    public void inicializar()
    {
        try {

            ConfiguracionActivity activity = (ConfiguracionActivity) getActivity();
            lista_catalogos = activity.getCatalogos(); //obtener la lista de catalagos de la activity
            conf = activity.getConf(); //obtener la configuracion
            nombreBase = getActivity().getString( R.string.nombreBD );
            rbSeleccionados = new boolean[lista_catalogos.size()]; //para saber que rb estan seleccionados porque son dinamicos

            tableLayout = (TableLayout) viewGral.findViewById(R.id.tableLayout);
            tableLayout.removeAllViews();
            TableRow tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

            for (int i = 0; i < lista_catalogos.size(); i++) {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

                RadioButton rb = tr.findViewById(R.id.tabla_radio);
                rb.setId(i);
                rb.setTag(i);
                rb.setOnClickListener(rbListener);

                ((TextView) tr.findViewById(R.id.tabla_catalogo)).setText(lista_catalogos.get(i));
                ((TextView) tr.findViewById(R.id.tabla_estado)).setText("ok");

                tableLayout.addView(tr);
            }


            //OBTENER SI ES PRECIO PREVENTA O NO
            precioPreventa=false;
            if(conf!=null)
                if(conf.getPreventa()==1)
                    precioPreventa=true;



        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }

    }


    //seleccionar o deseleccionar radiobutton cuando se le da clic
    private View.OnClickListener rbListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = ((RadioButton) view);
            int id =  rb.getId();

            if(rbSeleccionados[id])
            {
                rb.setChecked(false);
                rbSeleccionados[id]=false;
            }
            else
            {
                rb.setChecked(true);
                rbSeleccionados[id]=true;
            }

        }
    };

    //seleccionar o deseleccionar todos los radiobutton
    private View.OnClickListener btListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button bt = ((Button) view);
            String op= bt.getTag().toString();
            boolean seleccionar;

            if(op.equals("selec"))
                seleccionar=true;
            else
                seleccionar=false;

            for(int i=0; i<rbSeleccionados.length;i++)
            {
                rbSeleccionados[i]=seleccionar;
                RadioButton rb = (RadioButton) viewGral.findViewWithTag(i);
                rb.setChecked( seleccionar );
            }

        }
    };


    private class ConexionWS extends AsyncTask<String,Integer,Boolean> {

        private Context context;
        private ParametrosWS parametrosWS;
        private String resultado;
        boolean almacenado=false;

        //Lista de tablas descargadas

        ArrayList<DataTable.Empresa> al_empresas=null;
        ArrayList<DataTable.Estatus> al_estatus=null;
        ArrayList<DataTable.Roles> al_roles=null;
        ArrayList<DataTable.RolesModulos> al_rolesModulos=null;
        ArrayList<DataTable.Modulos> al_modulos=null;
        ArrayList<DataTable.Usuarios> al_usuarios=null;
        ArrayList<DataTable.TipoRuta> al_tipoRutas=null;
        ArrayList<DataTable.Ruta> al_rutas=null;
        ArrayList<DataTable.CondicionesVenta> al_condicionesVenta=null;
        ArrayList<DataTable.Productos> al_productos=null;
        ArrayList<DataTable.ListaPrecios> al_listaPrecios=null;
        ArrayList<DataTable.PrecioProductos> al_precioProductos=null;
        ArrayList<DataTable.FormasPago> al_formasPago=null;
        ArrayList<DataTable.FrecuenciasVisita> al_frecuenciaVisi=null;
        ArrayList<DataTable.Categorias> al_categorias=null;
        ArrayList<DataTable.Familias> al_familias=null;
        ArrayList<DataTable.Presentaciones> al_presentaciones=null;
        ArrayList<DataTable.Promociones> al_promociones=null;
        ArrayList<DataTable.PromocionesKit> al_promocionesKit=null;

        public ConexionWS(Context context) {
            this.context = context;

            al_empresas=null;
            al_estatus=null;
            al_roles=null;
            al_rolesModulos=null;
            al_modulos=null;
            al_usuarios=null;
            al_tipoRutas=null;
            al_rutas=null;
            al_condicionesVenta=null;
            al_productos=null;
            al_listaPrecios=null;
            al_precioProductos=null;
            al_formasPago=null;
            al_frecuenciaVisi=null;
            al_categorias=null;
            al_familias=null;
            al_presentaciones=null;
            al_promociones=null;
            al_promocionesKit=null;

            almacenado=false;


        }

        private ProgressDialog progreso;

        @Override protected void onPreExecute() {
            progreso = new ProgressDialog(context);
            progreso.setMessage("Descargando...");
            progreso.setCancelable(false);
            progreso.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            boolean result = false;
            resultado=null;

            try
            {

                for(int i=0; i<metodosWS.size();i++)
                {

                    parametrosWS = new ParametrosWS(metodosWS.get(i), getActivity().getApplicationContext());
                    //Metodo al que se accede
                    SoapObject Solicitud = new SoapObject(parametrosWS.getNAMESPACES(), parametrosWS.getMETODO());

                    //CONFIGURACION DE LA PETICION
                    SoapSerializationEnvelope Envoltorio = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    Envoltorio.dotNet = true;
                    Envoltorio.setOutputSoapObject(Solicitud);
                    HttpTransportSE TransporteHttp = new HttpTransportSE(parametrosWS.getURL(), parametrosWS.getTIMEOUT());

                    //SE REALIZA LA PETICIÓN
                    try {
                        TransporteHttp.call(parametrosWS.getSOAP_ACTION(), Envoltorio);
                        SoapPrimitive response = (SoapPrimitive) Envoltorio.getResponse();

                        if (response != null && !response.toString().equals("null")) {
                            obtenerResultado(response.toString(), parametrosWS.getMETODO());
                        }

                        result = true;

                    } catch (Exception e) {
                        Log.d("salida", "error: " + e.getMessage());
                        result = false;
                        resultado = "Error: " + e.getMessage();
                        i=metodosWS.size(); //terminar ciclo
                    }
                }

            }catch (Exception e)
            {
                Log.d("salida","error: "+e.getMessage());
                result=false;
                resultado= "Error: "+e.getMessage();
            }


            if(result)
            {
                almacenarLocal();
            }


            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progreso.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            progreso.dismiss();

            if(result)
            {
                if(almacenado)
                {
                    Toast.makeText(context, "Información actualizada correctamente", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show();
            }

        }

        private void obtenerResultado(String json, String metodo)
        {
            switch (metodo)
            {
                case "ObtenerEmpresasJ":
                    al_empresas = ConvertirRespuesta.getEmpresasJson(json);
                    break;
                case "ObtenerEstatusJ":
                    al_estatus = ConvertirRespuesta.getEstatusJson(json);
                    break;
                case "ObtenerRolesJ":
                    al_roles = ConvertirRespuesta.getRolesJson(json);
                    break;
                case "ObtenerRolesModulosJ":
                    al_rolesModulos = ConvertirRespuesta.getRolesModulosJson(json);
                    break;
                case "ObtenerModulosJ":
                    al_modulos = ConvertirRespuesta.getModulosJson(json);
                    break;
                case "ObtenerUsuariosJ":
                    al_usuarios = ConvertirRespuesta.getUsuariosJson(json);
                    break;
                case "ObtenerTipoRutasJ":
                    al_tipoRutas = ConvertirRespuesta.getTipoRutaJson(json);
                    break;
                case "ObtenerRutasJ":
                    al_rutas = ConvertirRespuesta.getRutasJson(json);
                    break;
                case "ObtenerCondicionesVentaJ":
                    al_condicionesVenta = ConvertirRespuesta.getCondicionesVentaJson(json);
                    break;
                case "ObtenerProductosJ":
                    al_productos = ConvertirRespuesta.getProductosJson(json);
                    break;
                case "ObtenerListaPreciosJ":
                    al_listaPrecios = ConvertirRespuesta.getListaPreciosJson(json);
                    break;
                case "ObtenerPrecioProductosJ":
                    al_precioProductos = ConvertirRespuesta.getPrecioProductosJson(json);
                    break;
                case "ObtenerPrecioProductosPreventaJ":
                    al_precioProductos = ConvertirRespuesta.getPrecioProductosJson(json);
                    break;
                case "ObtenerFormasPagoJ":
                    al_formasPago = ConvertirRespuesta.getFormasPagoJson(json);
                    break;
                case "ObtenerFrecuenciasVisitaJ":
                    al_frecuenciaVisi = ConvertirRespuesta.getFrecuenciasVisitaJson(json);
                    break;
                case "ObtenerCategoriasJ":
                    al_categorias = ConvertirRespuesta.getCategoriasJson(json);
                    break;
                case "ObtenerFamiliasJ":
                    al_familias = ConvertirRespuesta.getFamiliasJson(json);
                    break;
                case "ObtenerPresentacionesJ":
                    al_presentaciones = ConvertirRespuesta.getPresentacionesJson(json);
                    break;
                case "ObtenerPromocionesJ":
                    al_promociones = ConvertirRespuesta.getPromocionesJson(json);
                    break;
                case "ObtenerPromocionesKitJ":
                    al_promocionesKit = ConvertirRespuesta.getPromocionesKitJson(json);
                    break;
            }

            Log.d("salida","Descargo de ws: "+metodo);

        }

        private void almacenarLocal()
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            String consulta;

            try
            {
                db.beginTransaction();

                if(al_empresas!=null)
                {
                    db.execSQL(Querys.Empresas.DelEmpresas);
                    Log.d("salida","eliminar empresa");
                    for(int i=0; i<al_empresas.size();i++)
                    {
                        consulta = string.formatSql(Querys.Empresas.InsEmpresas, al_empresas.get(i).getEmp_cve_n(), al_empresas.get(i).getEmp_nom_str(),al_empresas.get(i).getEmp_contacto_str(),al_empresas.get(i).getEmp_rfc_str(),al_empresas.get(i).getEmp_colonia_str(),al_empresas.get(i).getEmp_ciudad_str(),al_empresas.get(i).getEmp_telefono1_str(),al_empresas.get(i).getEst_cve_str() );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar empresas");
                }

                if(al_estatus!=null)
                {
                    db.execSQL(Querys.Estatus.DelEstatus);
                    Log.d("salida","eliminar estatus");
                    for(int i=0; i<al_estatus.size();i++)
                    {
                        consulta = string.formatSql(Querys.Estatus.InsEstatus, al_estatus.get(i).getEst_cve_str(), al_estatus.get(i).getEst_desc_str(), getBool(al_estatus.get(i).getEst_usu_n()), getBool(al_estatus.get(i).getEst_ped_n()), getBool(al_estatus.get(i).getEst_prod_n()), getBool(al_estatus.get(i).getEst_prov_n())   );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar estatus");
                }

                if(al_roles!=null)
                {
                    db.execSQL(Querys.Roles.DelRoles);
                    Log.d("salida","eliminar roles");
                    for(int i=0; i<al_roles.size();i++)
                    {
                        consulta = string.formatSql(Querys.Roles.InsRoles, al_roles.get(i).getRol_cve_n(),al_roles.get(i).getRol_desc_str(),al_roles.get(i).getRol_esadmin_n(),al_roles.get(i).getEst_cve_str()   );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar roles");
                }

                if(al_rolesModulos!=null)
                {
                    db.execSQL(Querys.RolesModulos.DelRolesModulos);
                    Log.d("salida","eliminar rolesModulos");
                    for(int i=0; i<al_rolesModulos.size();i++)
                    {
                        consulta = string.formatSql(Querys.RolesModulos.InsRolesModulos, al_rolesModulos.get(i).getRol_cve_n(),al_rolesModulos.get(i).getMod_cve_n(),al_rolesModulos.get(i).getRm_lectura_n(),al_rolesModulos.get(i).getRm_escritura_n(),al_rolesModulos.get(i).getRm_modificacion_n(),al_rolesModulos.get(i).getRm_eliminacion_n()  );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar rolesModulos");
                }

                if(al_modulos!=null)
                {
                    db.execSQL(Querys.Modulos.DelModulos);
                    Log.d("salida","eliminar modulos");
                    for(int i=0; i<al_modulos.size();i++)
                    {
                        consulta = string.formatSql(Querys.Modulos.InsModulos, al_modulos.get(i).getMod_cve_n(), al_modulos.get(i).getMod_desc_str() );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar modulos");
                }


                if(al_usuarios!=null)
                {
                    db.execSQL(Querys.Usuarios.DelUsuarios);
                    Log.d("salida","eliminar usuarios");
                    for(int i=0; i<al_usuarios.size();i++)
                    {
                        consulta = string.formatSql(Querys.Usuarios.InsUsuarios, al_usuarios.get(i).getUsu_cve_str(), al_usuarios.get(i).getUsu_pwd_str(),  al_usuarios.get(i).getUsu_nom_str(), al_usuarios.get(i).getUsu_app_str(),  al_usuarios.get(i).getUsu_apm_str(), al_usuarios.get(i).getRol_cve_n(), al_usuarios.get(i).getEst_cve_str(), al_usuarios.get(i).getUsu_bloqueado_n(), al_usuarios.get(i).getUsu_fbloqueo_dt()  );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar usuarios");
                }

                if(al_tipoRutas!=null)
                {
                    db.execSQL(Querys.TipoRutas.DelTipoRutas);
                    Log.d("salida","eliminar tipo rutas");
                    for(int i=0; i<al_tipoRutas.size();i++)
                    {
                        consulta = string.formatSql(Querys.TipoRutas.InsTipoRutas, al_tipoRutas.get(i).getTrut_cve_n(), al_tipoRutas.get(i).getTrut_desc_str(), al_tipoRutas.get(i).getEst_cve_str()  );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar  tipo rutas");
                }

                /*if(al_rutas!=null)
                {
                    db.execSQL(Querys.Rutas.DelRutas);
                    Log.d("salida","eliminar usuarios");
                    for(int i=0; i<al_rutas.size();i++)
                    {
                        consulta = string.formatSql(Querys.Rutas.InsRutas3,  );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar usuarios");
                }
                 */






                db.setTransactionSuccessful();
                almacenado=true;
            }catch (Exception e)
            {
                Log.d("salida",e.toString());
                resultado = e.toString();
                almacenado=false;
            }
            finally
            {
                db.endTransaction();
                db.close();
            }
        }

    }

    public String getBool(String cad)
    {
        if(cad.equals("true"))
        {
            cad="1";
        }
        else
        {
            cad="0";
        }

        return cad;
    }




}
