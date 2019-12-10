package com.tdt.easyroute.Ventanas.Configuracion;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.ParametrosWS;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

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
    Button button_selec,button_deselec,button_sinc,button_salir;
    Configuracion conf=null;
    boolean precioPreventa=false;
    boolean catalogos;
    boolean crut;
    Usuario user;
    String[] arr_estadoCat;



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
            button_salir = view.findViewById(R.id.button_salir);

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

            button_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
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
            catalogos = activity.getCatalogosBool();
            crut = activity.getCrut();
            user = activity.getUser();
            nombreBase = getActivity().getString( R.string.nombreBD );

            rbSeleccionados = new boolean[lista_catalogos.size()]; //para saber que rb estan seleccionados porque son dinamicos
            arr_estadoCat = new String[lista_catalogos.size()];

            mostrarCatalogos();

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

    public void mostrarCatalogos()
    {
        tableLayout = (TableLayout) viewGral.findViewById(R.id.tableLayout);
        tableLayout.removeAllViews();
        TableRow tr;

        for (int i = 0; i < lista_catalogos.size(); i++) {
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

            RadioButton rb = tr.findViewById(R.id.tabla_radio);
            rb.setId(i);
            rb.setTag(i);
            rb.setOnClickListener(rbListener);

            ((TextView) tr.findViewById(R.id.tabla_catalogo)).setText(lista_catalogos.get(i));
            ((TextView) tr.findViewById(R.id.tabla_estado)).setText(arr_estadoCat[i]);

            ((TextView) tr.findViewById(R.id.tabla_catalogo)).setTypeface(Typeface.DEFAULT);
            ((TextView) tr.findViewById(R.id.tabla_estado)).setTypeface(Typeface.DEFAULT);

            tableLayout.addView(tr);
            rbSeleccionados[i]=false;
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

        ArrayList<DataTableWS.Empresa> al_empresas=null;
        ArrayList<DataTableWS.Estatus> al_estatus=null;
        ArrayList<DataTableWS.Roles> al_roles=null;
        ArrayList<DataTableWS.RolesModulos> al_rolesModulos=null;
        ArrayList<DataTableWS.Modulos> al_modulos=null;
        ArrayList<DataTableWS.Usuarios> al_usuarios=null;
        ArrayList<DataTableWS.TipoRuta> al_tipoRutas=null;
        ArrayList<DataTableWS.Ruta> al_rutas=null;
        ArrayList<DataTableWS.CondicionesVenta> al_condicionesVenta=null;
        ArrayList<DataTableWS.Productos> al_productos=null;
        ArrayList<DataTableWS.ListaPrecios> al_listaPrecios=null;
        ArrayList<DataTableWS.PrecioProductos> al_precioProductos=null;
        ArrayList<DataTableWS.FormasPago> al_formasPago=null;
        ArrayList<DataTableWS.FrecuenciasVisita> al_frecuenciaVisi=null;
        ArrayList<DataTableWS.Categorias> al_categorias=null;
        ArrayList<DataTableWS.Familias> al_familias=null;
        ArrayList<DataTableWS.Presentaciones> al_presentaciones=null;
        ArrayList<DataTableWS.Promociones> al_promociones=null;
        ArrayList<DataTableWS.PromocionesKit> al_promocionesKit=null;

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
                        else
                        {
                            String cat = parametrosWS.getMETODO().replace("Obtener","");
                            cat = cat.replace("J","");
                            actualizaEstado(cat,false);
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
                    actualizaEstadoError();
                }
            }
            else
            {
                Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show();
                actualizaEstadoError();
            }

            mostrarCatalogos();

        }

        private void actualizaEstado(String cat, boolean est)
        {
            for(int i=0; i<lista_catalogos.size();i++)
            {
                if(lista_catalogos.get(i).equals(cat))
                {
                    if(est)
                    {
                        arr_estadoCat[i] = "Descargado";
                    }
                    else
                    {
                        arr_estadoCat[i] = "Sin datos";
                    }
                    i=lista_catalogos.size(); //finaliza ciclo
                }
            }
        }

        private void actualizaEstadoError()
        {
            for(int i=0; i<metodosWS.size();i++)
            {
                String cat = metodosWS.get(i).replace("Obtener","");
                cat = cat.replace("J","");

                for(int j=0; j<lista_catalogos.size();j++)
                {
                    if(lista_catalogos.get(j).equals(cat))
                    {
                        arr_estadoCat[j] = "Error";
                        j=lista_catalogos.size(); //finaliza ciclo
                    }
                }
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
                    if(al_empresas.size()>0) {
                        db.execSQL(Querys.Empresas.DelEmpresas);
                        Log.d("salida", "eliminar empresa");
                        for (int i = 0; i < al_empresas.size(); i++) {
                            consulta = string.formatSql(Querys.Empresas.InsEmpresas, al_empresas.get(i).getEmp_cve_n(), al_empresas.get(i).getEmp_nom_str(), al_empresas.get(i).getEmp_contacto_str(), al_empresas.get(i).getEmp_rfc_str(), al_empresas.get(i).getEmp_colonia_str(), al_empresas.get(i).getEmp_ciudad_str(), al_empresas.get(i).getEmp_telefono1_str(), al_empresas.get(i).getEst_cve_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar empresas");
                        actualizaEstado("Empresas",true);
                    }
                    else
                        actualizaEstado("Empresas",false);
                }

                if(al_estatus!=null)
                {
                    if(al_estatus.size()>0) {
                        db.execSQL(Querys.Estatus.DelEstatus);
                        Log.d("salida", "eliminar estatus");
                        for (int i = 0; i < al_estatus.size(); i++) {
                            consulta = string.formatSql(Querys.Estatus.InsEstatus, al_estatus.get(i).getEst_cve_str(), al_estatus.get(i).getEst_desc_str(), getBool(al_estatus.get(i).getEst_usu_n()), getBool(al_estatus.get(i).getEst_ped_n()), getBool(al_estatus.get(i).getEst_prod_n()), getBool(al_estatus.get(i).getEst_prov_n()));
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar estatus");
                        actualizaEstado("Estatus",true);
                    }
                    else
                        actualizaEstado("Estatus",false);
                }

                if(al_roles!=null)
                {
                    if(al_roles.size()>0) {
                        db.execSQL(Querys.Roles.DelRoles);
                        Log.d("salida", "eliminar roles");
                        for (int i = 0; i < al_roles.size(); i++) {
                            consulta = string.formatSql(Querys.Roles.InsRoles, al_roles.get(i).getRol_cve_n(), al_roles.get(i).getRol_desc_str(), al_roles.get(i).getRol_esadmin_n(), al_roles.get(i).getEst_cve_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar roles");
                        actualizaEstado("Roles",true);
                    }
                    else
                        actualizaEstado("Roles",false);
                }

                if(al_rolesModulos!=null)
                {
                    if(al_rolesModulos.size()>0) {
                        db.execSQL(Querys.RolesModulos.DelRolesModulos);
                        Log.d("salida", "eliminar rolesModulos");
                        for (int i = 0; i < al_rolesModulos.size(); i++) {
                            consulta = string.formatSql(Querys.RolesModulos.InsRolesModulos, al_rolesModulos.get(i).getRol_cve_n(), al_rolesModulos.get(i).getMod_cve_n(), al_rolesModulos.get(i).getRm_lectura_n(), al_rolesModulos.get(i).getRm_escritura_n(), al_rolesModulos.get(i).getRm_modificacion_n(), al_rolesModulos.get(i).getRm_eliminacion_n());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar rolesModulos");
                        actualizaEstado("RolesModulos",true);
                    }
                    else
                        actualizaEstado("RolesModulos",false);
                }

                if(al_modulos!=null)
                {
                    if(al_modulos.size()>0) {
                        db.execSQL(Querys.Modulos.DelModulos);
                        Log.d("salida", "eliminar modulos");
                        for (int i = 0; i < al_modulos.size(); i++) {
                            consulta = string.formatSql(Querys.Modulos.InsModulos, al_modulos.get(i).getMod_cve_n(), al_modulos.get(i).getMod_desc_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar modulos");
                        actualizaEstado("Modulos",true);
                    }
                    else
                        actualizaEstado("Modulos",false);
                }


                if(al_usuarios!=null)
                {
                    if(al_usuarios.size()>0) {
                        db.execSQL(Querys.Usuarios.DelUsuarios);
                        Log.d("salida", "eliminar usuarios");
                        for (int i = 0; i < al_usuarios.size(); i++) {
                            consulta = string.formatSql(Querys.Usuarios.InsUsuarios, al_usuarios.get(i).getUsu_cve_str(), al_usuarios.get(i).getUsu_pwd_str(), al_usuarios.get(i).getUsu_nom_str(), al_usuarios.get(i).getUsu_app_str(), al_usuarios.get(i).getUsu_apm_str(), al_usuarios.get(i).getRol_cve_n(), al_usuarios.get(i).getEst_cve_str(), al_usuarios.get(i).getUsu_bloqueado_n(), al_usuarios.get(i).getUsu_fbloqueo_dt());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar usuarios");
                        actualizaEstado("Usuarios",true);
                    }
                    else
                        actualizaEstado("Usuarios",false);
                }

                if(al_tipoRutas!=null)
                {
                    if(al_tipoRutas.size()>0) {
                        db.execSQL(Querys.TipoRutas.DelTipoRutas);
                        Log.d("salida", "eliminar tipo rutas");
                        for (int i = 0; i < al_tipoRutas.size(); i++) {
                            consulta = string.formatSql(Querys.TipoRutas.InsTipoRutas, al_tipoRutas.get(i).getTrut_cve_n(), al_tipoRutas.get(i).getTrut_desc_str(), al_tipoRutas.get(i).getEst_cve_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar  tipo rutas");
                        actualizaEstado("TipoRutas",true);
                    }
                    else
                        actualizaEstado("TipoRutas",false);
                }

                if(al_rutas!=null)
                {
                    if(al_rutas.size()>0) {
                        db.execSQL(Querys.Rutas.DelRutas);
                        Log.d("salida", "eliminar rutas");

                        DataTableWS.Ruta r;
                        for (int i = 0; i < al_rutas.size(); i++) {
                            r = al_rutas.get(i);
                            consulta = string.formatSql(Querys.Rutas.InsRutas3, r.getRut_cve_n(), r.getRut_desc_str(), r.getRut_orden_n(), r.getTrut_cve_n(), r.getAsesor_cve_str(), r.getGerente_cve_str(), r.getSupervisor_cve_str(), r.getEst_cve_str(), r.getTco_cve_n(), r.getRut_prev_n(), r.getRut_idcteesp_n(), getBool(r.getRut_invalidafrecuencia_n()), r.getRut_productividad_n(), r.getRut_tel_str(), r.getRut_efectividad_n(), getBool(r.getRut_mayorista_n()), getBool(r.getRut_fiestasyeventos_n()), getBool(r.getRut_auditoria_n()), getBool(r.getRut_promoce_n()));
                            db.execSQL(consulta);
                        }

                        if (!catalogos)
                            crut = true;
                        Log.d("salida", "agregar rutas");
                        actualizaEstado("Rutas",true);
                    }
                    else
                        actualizaEstado("Rutas",false);
                }


                if(al_condicionesVenta!=null)
                {
                    if(al_condicionesVenta.size()>0) {
                        db.execSQL(Querys.CondicionesVenta.DelCondicionesVenta);
                        Log.d("salida", "eliminar condiciones venta");
                        DataTableWS.CondicionesVenta cv;
                        for (int i = 0; i < al_condicionesVenta.size(); i++) {
                            cv = al_condicionesVenta.get(i);
                            consulta = string.formatSql(Querys.CondicionesVenta.InsCondicionesVenta, cv.getCov_cve_n(), cv.getCov_desc_str(), getBool(cv.getCov_restringido_n()), cv.getCov_familias_str(), cv.getEst_cve_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar condiciones venta");
                        actualizaEstado("CondicionesVenta",true);
                    }
                    else
                        actualizaEstado("CondicionesVenta",false);
                }

                if(al_productos!=null)
                {
                    if(al_productos.size()>0) {
                        db.execSQL(Querys.Productos.DelProductos);
                        Log.d("salida", "eliminar productos");
                        DataTableWS.Productos p;
                        for (int i = 0; i < al_productos.size(); i++) {
                            p = al_productos.get(i);
                            consulta = string.formatSql(Querys.Productos.InsProductos, p.getProd_cve_n(), p.getProd_sku_str(), p.getProd_desc_str(), p.getProd_dscc_str(), p.getCat_cve_n(), p.getRetor_cve_str(), getBool(p.getProd_reqenv_n()), p.getId_envase_n(), p.getProd_unicompra_n(), p.getProd_factcompra_n(), p.getProd_univenta_n(), p.getProd_factventa_n(), getBool(p.getProd_escompuesto_n()), getBool(p.getProd_manejainv_n()), p.getProd_est_str(), p.getMar_cve_n(), p.getPres_cve_n(), p.getProd_ean_str(), p.getFam_cve_n(), p.getProd_orden_n(), getBool(p.getProd_vtamismodia_n()), getBool(p.getProd_vtaefectivo_n()), getBool(p.getProd_vtavolumen_n()), p.getCov_cve_n());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar productos");
                        actualizaEstado("Productos",true);
                    }
                    else
                        actualizaEstado("Productos",false);
                }

                if(al_listaPrecios!=null)
                {
                    if(al_listaPrecios.size()>0) {
                        db.execSQL(Querys.ListaPrecio.DelListaPrecios);
                        Log.d("salida", "eliminar lista precios");
                        DataTableWS.ListaPrecios lp;
                        for (int i = 0; i < al_listaPrecios.size(); i++) {
                            lp = al_listaPrecios.get(i);
                            consulta = string.formatSql(Querys.ListaPrecio.InsListaPrecios, lp.getLpre_cve_n(), lp.getLpre_desc_str(), lp.getEst_cve_str(), getBool(lp.getLpre_esbase_n()), lp.getLpre_base_n(), getBool(lp.getLpre_nota_n()));
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar lista precios");
                        actualizaEstado("ListaPrecios",true);
                    }
                    else
                        actualizaEstado("ListaPrecios",false);
                }


                if(al_precioProductos!=null)
                {
                    if(al_precioProductos.size()>0) {
                        db.execSQL(Querys.PrecioProductos.DelPrecioProductos);
                        Log.d("salida", "eliminar precio productos");
                        DataTableWS.PrecioProductos pp;
                        for (int i = 0; i < al_precioProductos.size(); i++) {
                            pp = al_precioProductos.get(i);
                            consulta = string.formatSql(Querys.PrecioProductos.InsPrecioProductos, pp.getLpre_cve_n(), pp.getProd_cve_n(), pp.getLpre_precio_n(), pp.getLpre_volumen_n());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar precio productos");
                        actualizaEstado("PrecioProductos",true);
                    }
                    else
                        actualizaEstado("PrecioProductos",false);
                }

                if(al_formasPago!=null)
                {
                    if(al_formasPago.size()>0) {
                        db.execSQL(Querys.FormasPago.DelFormasPago);
                        Log.d("salida", "eliminar formas de pago");
                        for (int i = 0; i < al_formasPago.size(); i++) {
                            consulta = string.formatSql(Querys.FormasPago.InsFormasPago, al_formasPago.get(i).getFpag_cve_n(), al_formasPago.get(i).getFpag_desc_str(), getBool(al_formasPago.get(i).getFpag_reqbanco_n()), getBool(al_formasPago.get(i).getFpag_reqreferencia_n()), al_formasPago.get(i).getEst_cve_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar formas pago");
                        actualizaEstado("FormasPago",true);
                    }
                    else
                        actualizaEstado("FormasPago",false);
                }

                if(al_frecuenciaVisi!=null)
                {
                    if(al_frecuenciaVisi.size()>0) {
                        db.execSQL(Querys.FrecuenciasVisita.DelFrecuenciasVisita);
                        Log.d("salida", "eliminar frecuencias visita");
                        for (int i = 0; i < al_frecuenciaVisi.size(); i++) {
                            consulta = string.formatSql(Querys.FrecuenciasVisita.InsFrecuenciasVisita, al_frecuenciaVisi.get(i).getFrec_cve_n(), al_frecuenciaVisi.get(i).getFrec_desc_str(), al_frecuenciaVisi.get(i).getFrec_dias_n(), al_frecuenciaVisi.get(i).getEst_cve_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar frecuencias visita");
                        actualizaEstado("FrecuenciasVisita",true);
                    }
                    else
                        actualizaEstado("FrecuenciasVisita",false);
                }

                if(al_categorias!=null)
                {
                    if(al_categorias.size()>0) {
                        db.execSQL(Querys.Categorias.DelCategorias);
                        Log.d("salida", "eliminar categorias");
                        for (int i = 0; i < al_categorias.size(); i++) {
                            consulta = string.formatSql(Querys.Categorias.InsCategorias, al_categorias.get(i).getCat_cve_n(), al_categorias.get(i).getCat_desc_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar categorias");
                        actualizaEstado("Categorias",true);
                    }
                    else
                        actualizaEstado("Categorias",false);
                }

                if(al_familias!=null)
                {
                    if(al_familias.size()>0) {
                        db.execSQL(Querys.Familias.DelFamilias);
                        Log.d("salida", "eliminar familias");
                        for (int i = 0; i < al_familias.size(); i++) {
                            consulta = string.formatSql(Querys.Familias.InsFamilias, al_familias.get(i).getFam_cve_n(), al_familias.get(i).getFam_desc_str(), al_familias.get(i).getFam_orden_n());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar familias");
                        actualizaEstado("Familias",true);
                    }
                    else
                        actualizaEstado("Familias",false);
                }


                if(al_presentaciones!=null)
                {
                    if(al_presentaciones.size()>0) {
                        db.execSQL(Querys.Presentaciones.DelPresentaciones);
                        Log.d("salida", "eliminar presentaciones");
                        for (int i = 0; i < al_presentaciones.size(); i++) {
                            consulta = string.formatSql(Querys.Presentaciones.InsPresentaciones, al_presentaciones.get(i).getPres_cve_n(), al_presentaciones.get(i).getPres_desc_str(), al_presentaciones.get(i).getPres_hectolitros_n(), al_presentaciones.get(i).getEst_cve_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar presentaciones");
                        actualizaEstado("Presentaciones",true);
                    }
                    else
                        actualizaEstado("Presentaciones",false);
                }

                if(al_promociones!=null)
                {
                    if(al_promociones.size()>0) {
                        db.execSQL(Querys.Promociones.DelPromociones);
                        Log.d("salida", "eliminar promociones");
                        DataTableWS.Promociones p;
                        for (int i = 0; i < al_promociones.size(); i++) {
                            p = al_promociones.get(i);
                            consulta = string.formatSql(Querys.Promociones.InsPromociones, p.getProm_cve_n(), p.getProm_folio_str(), p.getProm_desc_str(), p.getTprom_cve_n(), p.getProm_falta_dt(), p.getProm_fini_dt(), p.getProm_ffin_dt(), p.getEst_cve_str(), p.getUsu_cve_str(), p.getUsu_modificacion_str(), p.getProm_fmodificacion_dt(), p.getProd_cve_n(), p.getProd_sku_str(), p.getProm_nivel_n(), p.getLpre_cve_n(), p.getNvc_cve_n(), p.getNvc_cvehl_n(), p.getFam_cve_n(), p.getSeg_cve_n(), p.getGiro_cve_n(), p.getTcli_cve_n(), p.getLpre_precio_n(), p.getLpre_precio2_n(), p.getLpre_desc_n(), p.getProm_n_n(), p.getProm_m_n(), p.getProd_regalo_n(), p.getProd_skureg_str(), getBool(p.getProm_story_n()), getBool(p.getProm_proveedor_n()), getBool(p.getProm_envase_n()), getBool(p.getProm_kit_n()));
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar promociones");
                        actualizaEstado("Promociones",true);
                    }
                    else
                        actualizaEstado("Promociones",false);
                }


                if(al_promocionesKit!=null)
                {
                    if(al_promocionesKit.size()>0) {
                        db.execSQL(Querys.Promociones.DeletePromocionesKit);
                        Log.d("salida", "eliminar promociones kid");
                        DataTableWS.PromocionesKit p;
                        for (int i = 0; i < al_promocionesKit.size(); i++) {
                            p = al_promocionesKit.get(i);
                            consulta = string.formatSql(Querys.Promociones.InsertPromocionesKit, p.getProm_cve_n(), p.getProd_cve_n(), p.getProd_sku_str(), p.getProd_cant_n(), p.getLpre_precio_n());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar promociones kid");
                        actualizaEstado("PromocionesKit",true);
                    }
                    else
                        actualizaEstado("PromocionesKit",false);
                }

                db.setTransactionSuccessful();
                almacenado=true;
            }catch (Exception e)
            {
                Log.d("salida","Error: "+e.toString());
                resultado = "Error: "+e.toString();
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
