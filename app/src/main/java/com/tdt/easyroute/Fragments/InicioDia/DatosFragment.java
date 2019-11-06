package com.tdt.easyroute.Fragments.InicioDia;

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

import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.ParametrosWS;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Variables;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Configuracion.ServidorFragment;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class DatosFragment extends Fragment {

    String catalogos="LimpiarDatos,Empresas,Estatus,Roles,RolesModulos,Modulos,Usuarios,TipoRutas,Rutas," +
            "Marcas,Unidades,CondicionesVenta,Productos,ListaPrecios,PrecioProductos,NivelCliente,FormasPago," +
            "FrecuenciasVisita,MotivosNoVenta,MotivosNoLectura,Categorias,Familias,Presentaciones,Clientes," +
            "Creditos,Direcciones,ClientesVentaMes,Promociones,PromocionesKit,Consignas";

    ArrayList<String> al_catalogos;

    Variables.Startday varStartday;

    Button b_sincronizar,b_selec,b_deselec;

    boolean[] rbSeleccionados;
    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View viewGral;

    String nombreBase;
    ArrayList<String> metodosWS;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_datos, container, false);
        layoutInflater = inflater;
        viewGral = view;

        b_selec = view.findViewById(R.id.button_selec);
        b_deselec = view.findViewById(R.id.button_desSelec);
        b_sincronizar = view.findViewById(R.id.button_sincronizar);

        b_sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(metodosWS!=null)
                    metodosWS.clear();
                metodosWS = new ArrayList<>();
                actulizarStartday();
                boolean rbSelec=false;
                for(int i=0; i<rbSeleccionados.length;i++)
                {
                    if(rbSeleccionados[i])
                    {
                        rbSelec=true;

                        if(varStartday.getTipoRuta().equals("PREVENTA"))
                        {
                            switch (al_catalogos.get(i))
                            {
                                case "Clientes":
                                    metodosWS.add("Obtener"+"ClientesPreventa"+"J");
                                    break;
                                case "Creditos":
                                    metodosWS.add("Obtener"+"CreditosPreventa"+"J");
                                    break;
                                case "Direcciones":
                                    metodosWS.add("Obtener"+"DireccionesPreventa"+"J");
                                    break;
                                default:
                                    metodosWS.add("Obtener"+al_catalogos.get(i)+"J");
                                    break;
                            }

                        }
                        else
                        {
                            metodosWS.add("Obtener"+al_catalogos.get(i)+"J");
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

        b_selec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<rbSeleccionados.length;i++)
                {
                    rbSeleccionados[i]=true;
                    RadioButton rb = (RadioButton) viewGral.findViewWithTag(i);
                    rb.setChecked( true );
                }
            }
        });

        b_deselec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0; i<rbSeleccionados.length;i++)
                {
                    rbSeleccionados[i]=false;
                    RadioButton rb = (RadioButton) viewGral.findViewWithTag(i);
                    rb.setChecked( false );
                }

            }
        });


        nombreBase = getActivity().getString( R.string.nombreBD );
        obtenerCatalogos();


        return view;
    }

    private void obtenerCatalogos()
    {
        String[] s = catalogos.split(",");
        al_catalogos = new ArrayList<>();

        //AGREGAR LOS CATALOGOS AL ARRAY
        for (int i=0; i<s.length;i++)
        {
            al_catalogos.add(s[i]);
        }

        rbSeleccionados = new boolean[al_catalogos.size()];

        tableLayout = (TableLayout) viewGral.findViewById(R.id.tableLayout);
        tableLayout.removeAllViews();
        TableRow tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

        for (int i = 0; i < al_catalogos.size(); i++) {
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

            RadioButton rb = tr.findViewById(R.id.tabla_radio);
            rb.setId(i);
            rb.setTag(i);
            rb.setOnClickListener(rbListener);

            ((TextView) tr.findViewById(R.id.tabla_catalogo)).setText(al_catalogos.get(i));
            ((TextView) tr.findViewById(R.id.tabla_estado)).setText("ok");

            tableLayout.addView(tr);
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

    private void actulizarStartday()
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        varStartday = mainActivity.getVarStarday();
        Log.d("salida", "RUTA: " + varStartday.getRuta() +" "+varStartday.getTipoRuta());
    }

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
        ArrayList<DataTableWS.Marcas> al_marcas=null;
        ArrayList<DataTableWS.Unidades> al_unidades=null;
        ArrayList<DataTableWS.NivelCliente> al_nivelCliente=null;
        ArrayList<DataTableWS.MotivosNoVenta> al_motivosNoVenta=null;
        ArrayList<DataTableWS.MotivosNoLectura> al_motivosNoLectura=null;
        ArrayList<DataTableWS.Clientes> al_clientes=null;
        ArrayList<DataTableWS.Creditos> al_creditos=null;
        ArrayList<DataTableWS.Direcciones> al_direcciones=null;
        ArrayList<DataTableWS.ClientesVentaMes> al_clientesVenta=null;
        ArrayList<DataTableWS.Consignas> al_consignas=null;

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
                    if(!metodosWS.get(i).equals("ObtenerLimpiarDatosJ"))
                    {
                        parametrosWS = new ParametrosWS(metodosWS.get(i), getActivity().getApplicationContext());

                        //Metodo al que se accede
                        SoapObject Solicitud = new SoapObject(parametrosWS.getNAMESPACES(), parametrosWS.getMETODO());

                        if (parametrosWS.getMETODO().contains("ObtenerClientes") ||
                                parametrosWS.getMETODO().contains("ObtenerCreditos") ||
                                parametrosWS.getMETODO().contains("ObtenerDirecciones") ||
                                parametrosWS.getMETODO().contains("ClientesVentaMes") ||
                                parametrosWS.getMETODO().contains("Consignas"))
                        {
                            PropertyInfo piCliente = new PropertyInfo();
                            piCliente.setName("ruta");
                            piCliente.setValue(varStartday.getRuta());
                            Solicitud.addProperty(piCliente);

                        }

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
                            i = metodosWS.size(); //terminar ciclo
                        }
                    }
                }

            }catch (Exception e)
            {
                Log.d("salida","error: "+e.getMessage());
                result=false;
                resultado= "Error: "+e.getMessage();
            }

            if(result ||
                 metodosWS.contains("ObtenerLimpiarDatosJ") ||
                 metodosWS.contains("ObtenerConsignasJ")||
                 metodosWS.contains("ObtenerPreventaJ"))
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
                case "ObtenerMarcasJ":
                    al_marcas = ConvertirRespuesta.getMarcasJson(json);
                    break;
                case "ObtenerUnidadesJ":
                    al_unidades = ConvertirRespuesta.getUnidadesJson(json);
                    break;
                case "ObtenerNivelClienteJ":
                    al_nivelCliente = ConvertirRespuesta.getNivelCJson(json);
                    break;
                case "ObtenerMotivosNoVentaJ":
                    al_motivosNoVenta = ConvertirRespuesta.getMotivosNoVentaJson(json);
                    break;
                case "ObtenerMotivosNoLecturaJ":
                    al_motivosNoLectura = ConvertirRespuesta.getMotivosNoLecturaJson(json);
                    break;
                case "ObtenerClientesJ":
                case "ObtenerClientesPreventaJ":
                    al_clientes = ConvertirRespuesta.getClientesJson(json);
                    break;
                case "ObtenerCreditosJ":
                case "ObtenerCreditosPreventaJ":
                    al_creditos = ConvertirRespuesta.getCreditosWSJson(json);
                    break;
                case "ObtenerDireccionesJ":
                case "ObtenerDireccionesPreventaJ":
                    al_direcciones = ConvertirRespuesta.getDireccionesJson(json);
                    break;
                case "ObtenerClientesVentaMesJ":
                    al_clientesVenta = ConvertirRespuesta.getClientesVentaJson(json);
                case "ObtenerConsignasJ":
                    al_consignas = ConvertirRespuesta.getConsignasJson(json);

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

                if(al_rutas!=null)
                {
                    db.execSQL(Querys.Rutas.DelRutas);
                    Log.d("salida","eliminar rutas");

                    DataTableWS.Ruta r;
                    for(int i=0; i<al_rutas.size();i++)
                    {
                        r = al_rutas.get(i);
                        consulta = string.formatSql(Querys.Rutas.InsRutas3, r.getRut_cve_n(),r.getRut_desc_str(),r.getRut_orden_n(),r.getTrut_cve_n(),r.getAsesor_cve_str(),r.getGerente_cve_str(),r.getSupervisor_cve_str(),r.getEst_cve_str(),r.getTco_cve_n(), r.getRut_prev_n(),r.getRut_idcteesp_n(), getBool( r.getRut_invalidafrecuencia_n() ) ,r.getRut_productividad_n(),r.getRut_tel_str(),r.getRut_efectividad_n(), getBool( r.getRut_mayorista_n() ), getBool( r.getRut_fiestasyeventos_n() ), getBool( r.getRut_auditoria_n() ) , getBool( r.getRut_promoce_n() )  );
                        db.execSQL(consulta);
                    }


                    Log.d("salida","agregar rutas");
                }


                if(al_condicionesVenta!=null)
                {
                    db.execSQL(Querys.CondicionesVenta.DelCondicionesVenta);
                    Log.d("salida","eliminar condiciones venta");
                    DataTableWS.CondicionesVenta cv;
                    for(int i=0; i<al_condicionesVenta.size();i++)
                    {
                        cv = al_condicionesVenta.get(i);
                        consulta = string.formatSql(Querys.CondicionesVenta.InsCondicionesVenta, cv.getCov_cve_n(),cv.getCov_desc_str(), getBool( cv.getCov_restringido_n() ),cv.getCov_familias_str(),cv.getEst_cve_str()  );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar condiciones venta");
                }

                if(al_productos!=null)
                {
                    db.execSQL(Querys.Productos.DelProductos);
                    Log.d("salida","eliminar productos");
                    DataTableWS.Productos p;
                    for(int i=0; i<al_productos.size();i++)
                    {
                        p = al_productos.get(i);
                        consulta = string.formatSql(Querys.Productos.InsProductos,p.getProd_cve_n(),p.getProd_sku_str(),p.getProd_desc_str(),p.getProd_dscc_str(),p.getCat_cve_n(),p.getRetor_cve_str(), getBool( p.getProd_reqenv_n() ) ,p.getId_envase_n(),p.getProd_unicompra_n(),p.getProd_factcompra_n(),p.getProd_univenta_n(),p.getProd_factventa_n(), getBool( p.getProd_escompuesto_n() ) ,  getBool( p.getProd_manejainv_n() ),p.getProd_est_str(),p.getMar_cve_n(),p.getPres_cve_n(),p.getProd_ean_str(),p.getFam_cve_n(),p.getProd_orden_n(), getBool( p.getProd_vtamismodia_n() ) , getBool( p.getProd_vtaefectivo_n() ) , getBool( p.getProd_vtavolumen_n() ) ,p.getCov_cve_n());
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar productos");
                }

                if(al_listaPrecios!=null)
                {
                    db.execSQL(Querys.ListaPrecio.DelListaPrecios);
                    Log.d("salida","eliminar lista precios");
                    DataTableWS.ListaPrecios lp;
                    for(int i=0; i<al_listaPrecios.size();i++)
                    {
                        lp = al_listaPrecios.get(i);
                        consulta = string.formatSql(Querys.ListaPrecio.InsListaPrecios,lp.getLpre_cve_n(),lp.getLpre_desc_str(),lp.getEst_cve_str(), getBool( lp.getLpre_esbase_n() ) ,lp.getLpre_base_n(), getBool( lp.getLpre_nota_n() ) );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar lista precios");
                }


                if(al_precioProductos!=null)
                {
                    db.execSQL(Querys.PrecioProductos.DelPrecioProductos);
                    Log.d("salida","eliminar precio productos");
                    DataTableWS.PrecioProductos pp;
                    for(int i=0; i<al_precioProductos.size();i++)
                    {
                        pp = al_precioProductos.get(i);
                        consulta = string.formatSql(Querys.PrecioProductos.InsPrecioProductos, pp.getLpre_cve_n(),pp.getProd_cve_n(),pp.getLpre_precio_n(),pp.getLpre_volumen_n() );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar precio productos");
                }

                if(al_formasPago!=null)
                {
                    db.execSQL(Querys.FormasPago.DelFormasPago);
                    Log.d("salida","eliminar formas de pago");
                    for(int i=0; i<al_formasPago.size();i++)
                    {
                        consulta = string.formatSql(Querys.FormasPago.InsFormasPago, al_formasPago.get(i).getFpag_cve_n(), al_formasPago.get(i).getFpag_desc_str(), getBool( al_formasPago.get(i).getFpag_reqbanco_n() ) , getBool( al_formasPago.get(i).getFpag_reqreferencia_n() ) ,al_formasPago.get(i).getEst_cve_str() );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar formas pago");
                }

                if(al_frecuenciaVisi!=null)
                {
                    db.execSQL(Querys.FrecuenciasVisita.DelFrecuenciasVisita);
                    Log.d("salida","eliminar frecuencias visita");
                    for(int i=0; i<al_frecuenciaVisi.size();i++)
                    {
                        consulta = string.formatSql(Querys.FrecuenciasVisita.InsFrecuenciasVisita,al_frecuenciaVisi.get(i).getFrec_cve_n(),al_frecuenciaVisi.get(i).getFrec_desc_str(),al_frecuenciaVisi.get(i).getFrec_dias_n(),al_frecuenciaVisi.get(i).getEst_cve_str());
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar frecuencias visita");
                }

                if(al_categorias!=null)
                {
                    db.execSQL(Querys.Categorias.DelCategorias);
                    Log.d("salida","eliminar categorias");
                    for(int i=0; i<al_categorias.size();i++)
                    {
                        consulta = string.formatSql(Querys.Categorias.InsCategorias, al_categorias.get(i).getCat_cve_n(), al_categorias.get(i).getCat_desc_str() );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar categorias");
                }

                if(al_familias!=null)
                {
                    db.execSQL(Querys.Familias.DelFamilias);
                    Log.d("salida","eliminar familias");
                    for(int i=0; i<al_familias.size();i++)
                    {
                        consulta = string.formatSql(Querys.Familias.InsFamilias, al_familias.get(i).getFam_cve_n(), al_familias.get(i).getFam_desc_str(),al_familias.get(i).getFam_orden_n() );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar familias");
                }


                if(al_presentaciones!=null)
                {
                    db.execSQL(Querys.Presentaciones.DelPresentaciones);
                    Log.d("salida","eliminar presentaciones");
                    for(int i=0; i<al_presentaciones.size();i++)
                    {
                        consulta = string.formatSql( Querys.Presentaciones.InsPresentaciones, al_presentaciones.get(i).getPres_cve_n(), al_presentaciones.get(i).getPres_desc_str(), al_presentaciones.get(i).getPres_hectolitros_n(),al_presentaciones.get(i).getEst_cve_str() );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar presentaciones");
                }

                if(al_promociones!=null)
                {
                    db.execSQL(Querys.Promociones.DelPromociones);
                    Log.d("salida","eliminar promociones");
                    DataTableWS.Promociones p;
                    for(int i=0; i<al_promociones.size();i++)
                    {
                        p= al_promociones.get(i);
                        consulta = string.formatSql( Querys.Promociones.InsPromociones,p.getProm_cve_n(),p.getProm_folio_str(),p.getProm_desc_str(),p.getTprom_cve_n(),p.getProm_falta_dt(),p.getProm_fini_dt(),p.getProm_ffin_dt(),p.getEst_cve_str(),p.getUsu_cve_str(),p.getUsu_modificacion_str(),p.getProm_fmodificacion_dt(),p.getProd_cve_n(),p.getProd_sku_str(),p.getProm_nivel_n(),p.getLpre_cve_n(),p.getNvc_cve_n(),p.getNvc_cvehl_n(),p.getFam_cve_n(),p.getSeg_cve_n(),p.getGiro_cve_n(),p.getTcli_cve_n(),p.getLpre_precio_n(),p.getLpre_precio2_n(),p.getLpre_desc_n(),p.getProm_n_n(),p.getProm_m_n(),p.getProd_regalo_n(),p.getProd_skureg_str(), getBool( p.getProm_story_n() )  , getBool( p.getProm_proveedor_n() ) , getBool( p.getProm_envase_n() ) , getBool( p.getProm_kit_n() ) );
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar promociones");
                }

                if(al_promocionesKit!=null)
                {
                    db.execSQL(Querys.Promociones.DeletePromocionesKit);
                    Log.d("salida","eliminar promociones kid");
                    DataTableWS.PromocionesKit p;
                    for(int i=0; i<al_promocionesKit.size();i++)
                    {
                        p= al_promocionesKit.get(i);
                        consulta = string.formatSql( Querys.Promociones.InsertPromocionesKit,p.getProm_cve_n(),p.getProd_cve_n(),p.getProd_sku_str(), p.getProd_cant_n() ,p.getLpre_precio_n());
                        db.execSQL(consulta);
                    }
                    Log.d("salida","agregar promociones kid");
                }


                if(metodosWS.contains("ObtenerLimpiarDatosJ"))
                {
                    db.execSQL(Querys.Creditos.DeleteCreditos);
                    db.execSQL(Querys.Pagos.DelPagos);
                    db.execSQL(Querys.Ventas.DelVentas);
                    db.execSQL(Querys.VentasDet.DelVentasDet);
                    db.execSQL(Querys.VentasEnv.DelVentaEnv);
                    db.execSQL(Querys.BitacoraHH.DelBitacoraHH);
                    db.execSQL(Querys.Visitas.DelVisitas);
                    db.execSQL(Querys.Inventario.DelInventario);
                    db.execSQL(Querys.MovimientosInventario.DelMovimientosInventario);
                    db.execSQL(Querys.Direcciones.DelDirecciones);
                    db.execSQL(Querys.FrecuenciaPunteo.DelFrecPun);
                    db.execSQL(Querys.Sugerido.DelSugerido);
                    db.execSQL(Querys.Preventa.DelPreventa);
                    db.execSQL(Querys.Preventa.DelPreventaDet);
                    db.execSQL(Querys.Preventa.DelPreventaEnv);
                    db.execSQL(Querys.Preventa.DelPreventaPagos);
                    db.execSQL(Querys.Preventa.DelVisitaPreventa);
                    db.execSQL(Querys.ClientesVentaMes.DelClientesVentaMes);
                    Log.d("salida","Limpiar datos - Información eliminada");
                }

                if(al_marcas!=null)
                {
                    db.execSQL(Querys.Marcas.DelMarcas);
                    Log.d("salida","Eliminar marcas");
                    for(int i=0; i<al_marcas.size();i++)
                    {
                        db.execSQL( string.formatSql(Querys.Marcas.InsMarcas,al_marcas.get(i).getMar_cve_n(),al_marcas.get(i).getMar_desc_str(),al_marcas.get(i).getEst_cve_str() ) );
                    }
                    Log.d("salida","Agregar marcas");
                }

                if(al_unidades!=null)
                {
                    db.execSQL(Querys.Unidades.DelUnidades);
                    Log.d("salida","Eliminar unidades");

                    for(int i=0; i<al_unidades.size();i++)
                    {
                        DataTableWS.Unidades u = al_unidades.get(i);
                        db.execSQL( string.formatSql(Querys.Unidades.InsUnidades,u.getUni_cve_n(),u.getUni_simbolo_str(),u.getUni_desc_str(), getBool( u.getUni_divisible_n() ) , getBool( u.getUni_pesable_n() ) ,u.getEst_cve_str()) );
                    }
                    Log.d("salida","Agregar unidades");
                }

                if(al_condicionesVenta!=null)
                {
                    db.execSQL(Querys.CondicionesVenta.DelCondicionesVenta);
                    Log.d("salida","Eliminar condiciones venta");
                    for(int i=0; i<al_condicionesVenta.size();i++)
                    {
                        DataTableWS.CondicionesVenta cv = al_condicionesVenta.get(i);
                        db.execSQL(string.formatSql(Querys.CondicionesVenta.InsCondicionesVenta,cv.getCov_cve_n(),cv.getCov_desc_str(), getBool( cv.getCov_restringido_n() ) ,cv.getCov_familias_str(),cv.getEst_cve_str()));
                    }
                    Log.d("salida","Agregar condiciones venta");
                }

                if(al_nivelCliente!=null)
                {
                    db.execSQL(Querys.NivelCliente.DelNivelCliente);
                    Log.d("salida","Eliminar nivel cliente");
                    for(int i=0; i<al_nivelCliente.size();i++)
                    {
                        DataTableWS.NivelCliente nc = al_nivelCliente.get(i);
                        db.execSQL(string.formatSql(Querys.NivelCliente.InsNivelCliente,nc.getNvc_cve_n(),nc.getNvc_desc_str(),nc.getEst_cve_str(),nc.getNvc_nivel_n()));
                    }
                    Log.d("salida","Agregar nivel cliente");
                }

                if(al_motivosNoVenta!=null)
                {
                    db.execSQL( Querys.MotivosNoVenta.DelMotivosNoVenta );
                    Log.d("salida","Eliminar motivos no venta");
                    for(int i=0; i< al_motivosNoVenta.size();i++)
                    {
                        db.execSQL( string.formatSql(Querys.MotivosNoVenta.InsMotivosNoVenta,al_motivosNoVenta.get(i).getMnv_cve_n(),al_motivosNoVenta.get(i).getMnv_desc_str()) );
                    }
                    Log.d("salida","Agregar motivos no venta");
                }

                if(al_motivosNoLectura!=null)
                {
                    db.execSQL( Querys.MotivosNoLectura.DelMotivosNoLectura);
                    Log.d("salida","Eliminar motivos no lectura");
                    for(int i=0; i< al_motivosNoLectura.size();i++)
                    {
                        db.execSQL( string.formatSql(Querys.MotivosNoLectura.InsMotivosNoLectura,al_motivosNoLectura.get(i).getMnl_cve_n(),al_motivosNoLectura.get(i).getMnl_desc_str() ) );
                    }
                    Log.d("salida","Agregar motivos no lectura");
                }

                if(al_clientesVenta!=null)
                {
                    db.execSQL(Querys.ClientesVentaMes.DelClientesVentaMes);
                    Log.d("salida","Eliminar clientes venta mes");
                    for(int i=0; i<al_clientesVenta.size();i++)
                    {
                        db.execSQL(string.formatSql(Querys.ClientesVentaMes.InsClientesVentaMes,al_clientesVenta.get(i).getRut_cve_n(),al_clientesVenta.get(i).getCli_cve_n(),al_clientesVenta.get(i).getCvm_vtaacum_n()));
                    }
                    Log.d("salida","Agregar clientes venta mes");

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
