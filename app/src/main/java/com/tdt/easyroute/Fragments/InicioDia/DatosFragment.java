package com.tdt.easyroute.Fragments.InicioDia;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.ParametrosWS;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.ViewModel.StartdayVM;
import com.tdt.easyroute.R;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DatosFragment extends Fragment {

    private String catalogos;

    private ArrayList<String> al_catalogos;
    private String[] arr_estadoCat;

    private Button b_sincronizar,b_selec,b_deselec,b_salir;

    private boolean[] rbSeleccionados;
    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View viewGral;

    private String nombreBase,tipoRuta,ruta_cve;
    ArrayList<String> metodosWS;

    private MainActivity mainActivity;

    private StartdayVM startdayVM;
    private boolean actualizoRutaEmpresa=false;

    private StartdayFragment startdayFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getParentFragment()!=null)
            startdayVM = ViewModelProviders.of(getParentFragment()).get(StartdayVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_datos, container, false);
        layoutInflater = inflater;
        viewGral = view;

        mainActivity = (MainActivity) getActivity();
        startdayFragment = (StartdayFragment) getParentFragment();

        b_selec = view.findViewById(R.id.button_selec);
        b_deselec = view.findViewById(R.id.button_desSelec);
        b_sincronizar = view.findViewById(R.id.button_sincronizar);
        b_salir = view.findViewById(R.id.button_salir);

        nombreBase = getActivity().getString( R.string.nombreBD );

        b_sincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sincronizar();
            }
        });

        b_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startdayVM.getCatalogos().observe(getParentFragment(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                catalogos = s;
                obtenerCatalogos();
                mostrarCatalogos();
            }
        });

        startdayVM.getTipoRuta().observe(getParentFragment(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tipoRuta=s;
            }
        });

        startdayVM.getRuta().observe(getParentFragment(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                ruta_cve=s;
            }
        });


    }

    private void obtenerCatalogos()
    {
        String[] s = catalogos.split(",");
        al_catalogos = new ArrayList<>();

        //AGREGAR LOS CATALOGOS AL ARRAY
        Collections.addAll(al_catalogos, s);

        arr_estadoCat = new String[al_catalogos.size()];
        rbSeleccionados = new boolean[al_catalogos.size()];
    }

    private void mostrarCatalogos()
    {

        if( !Boolean.getBoolean(String.valueOf(startdayVM.getActualizoRutaEmpresa()))  ) {

            tableLayout = viewGral.findViewById(R.id.tableLayout);
            tableLayout.removeAllViews();
            TableRow tr;

            for (int i = 0; i < al_catalogos.size(); i++) {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

                RadioButton rb = tr.findViewById(R.id.tabla_radio);
                rb.setId(i);
                rb.setTag(i);
                rb.setOnClickListener(rbListener);
                rb.setChecked(true);

                ((TextView) tr.findViewById(R.id.tabla_catalogo)).setText(al_catalogos.get(i));
                ((TextView) tr.findViewById(R.id.tabla_estado)).setText(arr_estadoCat[i]);

                ((TextView) tr.findViewById(R.id.tabla_catalogo)).setTypeface(Typeface.DEFAULT);
                ((TextView) tr.findViewById(R.id.tabla_estado)).setTypeface(Typeface.DEFAULT);

                tableLayout.addView(tr);
                rbSeleccionados[i] = true;
            }
        }

    }

    private void sincronizar()
    {
        if(metodosWS!=null)
            metodosWS.clear();
        metodosWS = new ArrayList<>();
        boolean rbSelec=false;
        for(int i=0; i<rbSeleccionados.length;i++)
        {
            if(rbSeleccionados[i])
            {
                rbSelec=true;

                if(tipoRuta.equals("PREVENTA"))
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

    private class ConexionWS extends AsyncTask<String,Integer,Boolean> {

        private Context context;
        private ParametrosWS parametrosWS;
        private String resultado;
        boolean almacenado;

        //region Lista de arrayList para descargar

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
        String consignas=null;
        ArrayList<DataTableWS.Consignas> al_consignas=null;
        ArrayList<DataTableWS.ConsignasDet> al_consignasDet=null;
        ArrayList<DataTableWS.VisitaPreventa> al_visitaPreventa=null;
        String preventa=null;
        ArrayList<DataTableWS.Preventa> al_preventa=null;
        ArrayList<DataTableWS.PreventaDet> al_preventaDet=null;
        ArrayList<DataTableWS.PreventaEnv> al_preventaEnv=null;
        ArrayList<DataTableWS.PreventaPagos> al_preventaPagos=null;

        //endregion

        public ConexionWS(Context context) {
            this.context = context;
            almacenado=false;
        }

        private ProgressDialog progreso;

        @Override protected void onPreExecute() {
            progreso = new ProgressDialog(context);
            progreso.setMessage(getString(R.string.msg_webserviceDescarga));
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
                                parametrosWS.getMETODO().contains("Consignas") ||
                                parametrosWS.getMETODO().contains("Preventa"))
                        {
                            PropertyInfo piCliente = new PropertyInfo();
                            piCliente.setName("ruta");
                            piCliente.setValue(ruta_cve);
                            Solicitud.addProperty(piCliente);

                            Log.d("salida","RUTA BUSCADA EN WS: "+ruta_cve);

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
                    mostrarCatalogos();


                    if(actualizoRutaEmpresa)
                    {
                        startdayVM.setActualizoRutaEmpresa(true);
                        actualizoRutaEmpresa=false;
                    }

                    startdayVM.setSincro(true); //actualiza el valor de la variable startday

                    Toast.makeText(context, "Información actualizada correctamente", Toast.LENGTH_SHORT).show();

                    startdayFragment.goGenerales();

                }
                else
                {
                    Toast.makeText(context, resultado, Toast.LENGTH_LONG).show();
                    startdayVM.setSincro(false);
                }
            }
            else
            {
                Toast.makeText(context, resultado, Toast.LENGTH_LONG).show();
                startdayVM.setSincro(false);
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
                    break;
                case "ObtenerConsignasJ":
                    consignas=json;
                    break;
                case "ObtenerPreventaJ":
                    preventa=json;
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

                    actualizaEstado("LimpiarDatos",true);
                }

                if(al_empresas!=null)
                {
                    if(al_empresas.size()>0)
                    {
                        db.execSQL(Querys.Empresas.DelEmpresas);
                        Log.d("salida", "eliminar empresa");
                        for (int i = 0; i < al_empresas.size(); i++) {
                            consulta = string.formatSql(Querys.Empresas.InsEmpresas, al_empresas.get(i).getEmp_cve_n(), al_empresas.get(i).getEmp_nom_str(), al_empresas.get(i).getEmp_contacto_str(), al_empresas.get(i).getEmp_rfc_str(), al_empresas.get(i).getEmp_colonia_str(), al_empresas.get(i).getEmp_ciudad_str(), al_empresas.get(i).getEmp_telefono1_str(), al_empresas.get(i).getEst_cve_str());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "agregar empresas");
                        actualizaEstado("Empresas",true);
                        actualizoRutaEmpresa=true;
                    }
                    else
                    {
                        actualizaEstado("Empresas",false);
                    }
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
                        actualizoRutaEmpresa=true;
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
                        Log.d("salida", "agregar rutas");
                        actualizaEstado("Rutas",true);
                        actualizoRutaEmpresa=true;
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

                if(al_marcas!=null)
                {
                    if(al_marcas.size()>0) {
                        db.execSQL(Querys.Marcas.DelMarcas);
                        Log.d("salida", "Eliminar marcas");
                        for (int i = 0; i < al_marcas.size(); i++) {
                            db.execSQL(string.formatSql(Querys.Marcas.InsMarcas, al_marcas.get(i).getMar_cve_n(), al_marcas.get(i).getMar_desc_str(), al_marcas.get(i).getEst_cve_str()));
                        }
                        Log.d("salida", "Agregar marcas");
                        actualizaEstado("Marcas",true);
                    }
                    else
                        actualizaEstado("Marcas",false);
                }

                if(al_unidades!=null)
                {
                    if(al_unidades.size()>0) {
                        db.execSQL(Querys.Unidades.DelUnidades);
                        Log.d("salida", "Eliminar unidades");

                        for (int i = 0; i < al_unidades.size(); i++) {
                            DataTableWS.Unidades u = al_unidades.get(i);
                            db.execSQL(string.formatSql(Querys.Unidades.InsUnidades, u.getUni_cve_n(), u.getUni_simbolo_str(), u.getUni_desc_str(), getBool(u.getUni_divisible_n()), getBool(u.getUni_pesable_n()), u.getEst_cve_str()));
                        }
                        Log.d("salida", "Agregar unidades");
                        actualizaEstado("Unidades",true);
                    }
                    else
                        actualizaEstado("Unidades",false);
                }

                if(al_condicionesVenta!=null)
                {
                    if(al_condicionesVenta.size()>0) {
                        db.execSQL(Querys.CondicionesVenta.DelCondicionesVenta);
                        Log.d("salida", "Eliminar condiciones venta");
                        for (int i = 0; i < al_condicionesVenta.size(); i++) {
                            DataTableWS.CondicionesVenta cv = al_condicionesVenta.get(i);
                            db.execSQL(string.formatSql(Querys.CondicionesVenta.InsCondicionesVenta, cv.getCov_cve_n(), cv.getCov_desc_str(), getBool(cv.getCov_restringido_n()), cv.getCov_familias_str(), cv.getEst_cve_str()));
                        }
                        Log.d("salida", "Agregar condiciones venta");
                        actualizaEstado("CondicionesVenta",true);
                    }
                    else
                        actualizaEstado("CondicionesVenta",false);
                }

                if(al_nivelCliente!=null)
                {
                    if(al_nivelCliente.size()>0) {
                        db.execSQL(Querys.NivelCliente.DelNivelCliente);
                        Log.d("salida", "Eliminar nivel cliente");
                        for (int i = 0; i < al_nivelCliente.size(); i++) {
                            DataTableWS.NivelCliente nc = al_nivelCliente.get(i);
                            db.execSQL(string.formatSql(Querys.NivelCliente.InsNivelCliente, nc.getNvc_cve_n(), nc.getNvc_desc_str(), nc.getEst_cve_str(), nc.getNvc_nivel_n()));
                        }
                        Log.d("salida", "Agregar nivel cliente");
                        actualizaEstado("NivelCliente",true);
                    }
                    else
                        actualizaEstado("NivelCliente",false);
                }

                if(al_motivosNoVenta!=null)
                {
                    if(al_motivosNoVenta.size()>0) {
                        db.execSQL(Querys.MotivosNoVenta.DelMotivosNoVenta);
                        Log.d("salida", "Eliminar motivos no venta");
                        for (int i = 0; i < al_motivosNoVenta.size(); i++) {
                            db.execSQL(string.formatSql(Querys.MotivosNoVenta.InsMotivosNoVenta, al_motivosNoVenta.get(i).getMnv_cve_n(), al_motivosNoVenta.get(i).getMnv_desc_str()));
                        }
                        Log.d("salida", "Agregar motivos no venta");
                        actualizaEstado("MotivosNoVenta",true);
                    }
                    else
                        actualizaEstado("MotivosNoVenta",false);
                }

                if(al_motivosNoLectura!=null)
                {
                    if(al_motivosNoLectura.size()>0) {
                        db.execSQL(Querys.MotivosNoLectura.DelMotivosNoLectura);
                        Log.d("salida", "Eliminar motivos no lectura");
                        for (int i = 0; i < al_motivosNoLectura.size(); i++) {
                            db.execSQL(string.formatSql(Querys.MotivosNoLectura.InsMotivosNoLectura, al_motivosNoLectura.get(i).getMnl_cve_n(), al_motivosNoLectura.get(i).getMnl_desc_str()));
                        }
                        Log.d("salida", "Agregar motivos no lectura");
                        actualizaEstado("MotivosNoLectura",true);
                    }
                    else
                        actualizaEstado("MotivosNoLectura",false);
                }

                if(al_clientesVenta!=null)
                {
                    if(al_clientesVenta.size()>0) {
                        db.execSQL(Querys.ClientesVentaMes.DelClientesVentaMes);
                        Log.d("salida", "Eliminar clientes venta mes");
                        for (int i = 0; i < al_clientesVenta.size(); i++) {
                            db.execSQL(string.formatSql(Querys.ClientesVentaMes.InsClientesVentaMes, al_clientesVenta.get(i).getRut_cve_n(), al_clientesVenta.get(i).getCli_cve_n(), al_clientesVenta.get(i).getCvm_vtaacum_n()));
                        }
                        Log.d("salida", "Agregar clientes venta mes");
                        actualizaEstado("ClientesVentaMes",true);
                    }
                    else
                        actualizaEstado("ClientesVentaMes",false);
                }

                if(al_clientes!=null)
                {
                    if(al_clientes.size()>0) {
                        db.execSQL(Querys.Clientes.DelClientes);
                        Log.d("salida", "Eliminar clientes");
                        for (int i = 0; i < al_clientes.size(); i++) {
                            DataTableWS.Clientes c = al_clientes.get(i);
                            consulta = string.formatSql(Querys.Clientes.InsClientes4, c.getCli_cve_n(), c.getCli_cveext_str(), getBool(c.getCli_padre_n()), c.getCli_cvepadre_n(), c.getCli_razonsocial_str(), c.getCli_rfc_Str(), getBool(c.getCli_reqfact_n()), c.getCli_nombrenegocio_str(), c.getCli_nom_str(), c.getCli_apm_str(),
                                    c.getCli_apm_str(), getDate(c.getCli_fnac_dt()), c.getCli_genero_str(), c.getLpre_cve_n(), c.getNota_cve_n(), c.getFpag_cve_n(), getBool(c.getCli_consigna_n()), getBool(c.getCli_credito_n()), c.getCli_montocredito_n(), c.getCli_plazocredito_n(),
                                    c.getCli_credenvases_n(), c.getCli_estcredito_str(), getBool(c.getCli_fba_n()), c.getCli_porcentajefba_n(), c.getRut_cve_n(), c.getNvc_cve_n(), c.getGiro_cve_n(), c.getCli_email_str(), c.getCli_dirfact_n(),
                                    c.getCli_dirent_n(), c.getCli_Tel1_str(), c.getCli_tel2_str(), c.getEmp_cve_n(), c.getCli_coordenadaini_str(), c.getEst_cve_str(), c.getTcli_cve_n(), c.getCli_lun_n(), c.getCli_mar_n(), c.getCli_mie_n(), c.getCli_jue_n(),
                                    c.getCli_vie_n(), c.getCli_sab_n(), c.getCli_dom_n(), c.getFrec_cve_n(), getBool(c.getCli_especial_n()), getBool(c.getCli_esvallejo_n()), c.getNpro_cve_n(), c.getCli_huixdesc_n(), getBool(c.getCli_eshuix_n()), getBool(c.getCli_prospecto_n()),
                                    getBool(c.getCli_invalidafrecuencia_n()), getBool(c.getCli_invalidagps_n()), getBool(c.getCli_dobleventa_n()), getBool(c.getCli_comodato_n()), c.getSeg_cve_n(), getBool(c.getCli_dispersion_n()), c.getCli_dispersioncant_n(), c.getCli_limitemes_n());
                            db.execSQL(consulta);
                        }
                        Log.d("salida", "Agregar clientes");
                        actualizaEstado("Clientes",true);
                    }
                    else
                        actualizaEstado("Clientes",false);
                }

                if(al_creditos!=null)
                {
                    if(al_creditos.size()>0) {
                        db.execSQL(string.formatSql(Querys.Trabajo.DelCreditos));
                        Log.d("Salida", "Eliminar creditos");
                        for (int i = 0; i < al_creditos.size(); i++) {
                            DataTableWS.Creditos c = al_creditos.get(i);

                            consulta = string.formatSql(Querys.Trabajo.InsCreditos, c.getCred_referencia_str(), c.getCli_cve_n(), c.getUsu_cve_str(), getDate(c.getCred_fecha_dt()), c.getCred_descripcion_str(), getDate(c.getCred_vencimiento_dt()), c.getCred_monto_n(),
                                    c.getCred_abono_n(), getBool(c.getCred_engestoria_n()), getBool(c.getCred_esenvase_n()), getBool(c.getCred_especial_n()), c.getProd_cve_n(), c.getProd_sku_str(), c.getProd_precio_n(), c.getProd_cant_n(),
                                    c.getProd_cantabono_n(), "3", null);
                            db.execSQL(consulta);
                        }
                        Log.d("Salida", "Agregar creditos");
                        actualizaEstado("Creditos",true);
                    }
                    else
                        actualizaEstado("Creditos",false);
                }

                if(al_direcciones!=null)
                {
                    if(al_direcciones.size()>0) {
                        db.execSQL(string.formatSql(Querys.Direcciones.DelDirecciones));
                        Log.d("Salida", "Eliminar direcciones");
                        for (int i = 0; i < al_direcciones.size(); i++) {
                            DataTableWS.Direcciones d = al_direcciones.get(i);

                            consulta = string.formatSql(Querys.Direcciones.InsDirecciones2, d.getDir_cve_n(), d.getCli_cve_n(), d.getDir_alias_str(), d.getDir_calle_str(), d.getDir_noext_str(), d.getDir_noint_str(), d.getDir_entrecalle1_str(),
                                    d.getDir_entrecalle2_str(), d.getDir_colonia_str(), d.getDir_municipio_str(), d.getDir_estado_str(), d.getDir_pais_str(), d.getDir_codigopostal_str(),
                                    d.getDir_referencia_str(), d.getEst_cve_str(), d.getUsu_cve_str(), getDate(d.getDir_falta_dt()), d.getDir_tel1_str(), d.getDir_tel2_str(), d.getDir_encargado_str(), d.getDir_coordenada_str());
                            db.execSQL(consulta);
                        }
                        Log.d("Salida", "Agregar direcciones");
                        actualizaEstado("Direcciones",true);
                    }
                    else
                        actualizaEstado("Direcciones",false);
                }

                if(consignas!=null)
                {
                    Log.d("salida","Cons: "+consignas);
                    String[] tablas = consignas.split("\\|");

                    al_consignas = ConvertirRespuesta.getConsignasJson(tablas[0]);
                    al_consignasDet = ConvertirRespuesta.getConsignasDetJson(tablas[0]);

                    DataTableWS.Consignas c;
                    DataTableWS.ConsignasDet d;

                    if(al_consignas.size()>0)
                    {
                        db.execSQL(Querys.Consignas.DeleteConsignas);
                        db.execSQL(Querys.Consignas.DeleteConsignasDet);

                        Log.d("salida","Eliminar consignas/detalles");

                        for(int i=0; i<al_consignas.size();i++)
                        {
                            c=al_consignas.get(i);

                            for(int j=0; j<al_consignasDet.size();j++)
                            {
                                d=al_consignasDet.get(i);
                                if(d.getCsgn_cve_str().equals(c.getCsgn_cve_str()) &&  d.getCsgn_entrega_n().equals(c.getCsgn_entrega_n()) )
                                {
                                    consulta = string.formatSql(Querys.Consignas.InsertConsignasDet,d.getCsgn_cve_str(),d.getCsgn_entrega_n(),d.getProd_cve_n(),d.getProd_sku_str(),d.getProd_cant_n(),d.getProd_vendido_n(),d.getProd_devuelto_n(),d.getProd_danado_n(),d.getProd_pagado_n());
                                    db.execSQL(consulta);
                                }
                            }

                            consulta= string.formatSql( Querys.Consignas.InsertConsignas, c.getCsgn_cve_str(),c.getCsgn_entrega_n(),c.getEmp_cve_n(),c.getAlm_cve_n(),c.getCli_cve_n(),c.getCli_cveext_str(),c.getRut_cve_n(),
                                                                                          c.getCli_nom_str(),c.getCli_app_str(),c.getCli_apm_str(),c.getCli_tel1_str(),c.getCli_tel2_str(),c.getDir_calle_str(),c.getDir_noext_str(),
                                                                                          c.getDir_noint_str(),c.getDir_entrecalle1_str(),c.getDir_entrecalle2_str(),c.getDir_colonia_str(),c.getDir_municipio_str(),
                                                                                          c.getDir_estado_str(),c.getDir_pais_str(),c.getDir_codigopostal_str(),c.getDir_referencia_str(),c.getDir_encargado_str(),
                                                                                          c.getCsgn_coordenada_str(),c.getUsu_solicita_str(), getDate( c.getCsgn_fsolicitud_dt() ) ,c.getEst_cve_str(), getDate(c.getCsgn_finicio_dt()) ,
                                                                                          getDate(c.getCsgn_ffin_dt()) , getDate( c.getCsgn_fcobro_dt() ) , getDate( c.getCsgn_fprorroga_dt() ) ,c.getUsu_autoriza_str(), getDate(c.getCsgn_fautorizacion_dt()) ,
                                                                                          c.getCsgn_identificacion_str(),c.getCsgn_compdom_str(),c.getCsgn_pagare_str(),c.getUsu_modifica_str(), getDate(c.getCsgn_fmodificacion_dt()) ,c.getCsgn_montopagare_n(),c.getCsgn_observaciones_str()  );
                            db.execSQL(consulta);
                        }

                        Log.d("salida","Agregar consignas/detalles");
                        actualizaEstado("Consignas",true);
                    }
                    else
                        actualizaEstado("Consignas",false);
                }

                if(preventa!=null)
                {
                    String[] tablas = preventa.split("\\|");

                    al_visitaPreventa = ConvertirRespuesta.getVisitaPreventaJson(tablas[0]);
                    al_preventa = ConvertirRespuesta.getPreventaJson(tablas[1]);
                    al_preventaDet = ConvertirRespuesta.getPreventaDetJson(tablas[2]);
                    al_preventaEnv = ConvertirRespuesta.getPreventaEnvJson(tablas[3]);
                    al_preventaPagos = ConvertirRespuesta.getPreventaPagosJson(tablas[4]);

                    if(al_visitaPreventa.size()>0)
                    {
                        db.execSQL(Querys.Preventa.DelVisitaPreventa);
                        Log.d("salida","Eliminar consignas/detalles");

                        DataTableWS.VisitaPreventa vp;
                        for(int i=0; i<al_visitaPreventa.size();i++)
                        {
                            vp= al_visitaPreventa.get(i);
                            consulta= Querys.Preventa.InsertVisitaPrev2;
                            db.execSQL( string.formatSql(consulta, vp.getVisp_folio_str(),vp.getCli_cve_n(),vp.getRut_cve_n(),getDate( vp.getVisp_fecha_dt()),vp.getVisp_coordenada_str(),vp.getUsu_cve_str() ) );
                        }

                        db.execSQL(Querys.Preventa.DelPreventa);
                        DataTableWS.Preventa p;
                        for(int i=0; i<al_preventa.size();i++)
                        {
                            p = al_preventa.get(i);
                            consulta= Querys.Preventa.InsPreventaSinc;
                            db.execSQL(string.formatSql(consulta, p.getPrev_folio_str(),p.getCli_cve_n(),p.getRut_cve_n(), getDate( p.getPrev_fecha_dt() ), p.getLpre_cve_n(),p.getDir_cve_n(),p.getUsu_cve_str(),p.getPrev_coordenada_str()   ));
                        }


                        db.execSQL(Querys.Preventa.DelPreventaDet);
                        DataTableWS.PreventaDet pd;
                        for(int i=0; i<al_preventaDet.size();i++)
                        {
                            consulta= Querys.Preventa.InsPreventaDet2;
                            pd=al_preventaDet.get(i);
                            db.execSQL(string.formatSql(consulta, pd.getPrev_folio_str(),pd.getPrev_num_n(),pd.getProd_cve_n(),pd.getProd_sku_str(),pd.getProd_envase_n(),pd.getProd_cant_n(),pd.getLpre_base_n(),
                                                                  pd.getLpre_cliente_n(),pd.getLpre_promo_n(),pd.getLpre_precio_n(),pd.getProd_promo_n(),pd.getProm_cve_n(),pd.getProd_subtotal_n(),pd.getPrev_kit_n()));
                        }

                        db.execSQL(Querys.Preventa.DelPreventaEnv);
                        DataTableWS.PreventaEnv pe;
                        for(int i=0; i<al_preventaEnv.size();i++)
                        {
                            pe = al_preventaEnv.get(i);
                            consulta= Querys.Preventa.InsPreventaEnv2;

                            db.execSQL(string.formatSql(consulta, pe.getPrev_folio_str(),pe.getProd_cve_n(),pe.getProd_sku_str(), pe.getProd_inicial_n(), pe.getProd_cargo_n(), pe.getProd_abono_n(),pe.getProd_regalo_n(),pe.getProd_venta_n(),
                                                                  pe.getProd_final_n(),pe.getLpre_base_n(),pe.getLpre_precio_n()));
                        }

                        db.execSQL(Querys.Preventa.DelPreventaPagos);
                        DataTableWS.PreventaPagos pp;
                        for(int i=0; i<al_preventaPagos.size();i++)
                        {
                            pp= al_preventaPagos.get(i);
                            consulta = Querys.Preventa.InsPreventaPagos2;
                            db.execSQL(string.formatSql(consulta, pp.getPrev_folio_str(),pp.getPpag_num_n(),pp.getPpag_cobranza_n(),pp.getFpag_cve_n(),pp.getFpag_cant_n() ));
                        }

                        Log.d("salida","Agregar preventas");
                        actualizaEstado("Preventa",true);
                    }
                    else
                        actualizaEstado("Preventa",false);

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
                if(db.isOpen())
                {
                    db.endTransaction();
                    db.close();
                }
            }
        }

        private void actualizaEstado(String cat, boolean est)
        {
            for(int i=0; i<al_catalogos.size();i++)
            {
                if(al_catalogos.get(i).equals(cat))
                {
                    if(est)
                    {
                        if(cat.equals("LimpiarDatos"))
                            arr_estadoCat[i] = "Realizado";
                        else
                            arr_estadoCat[i] = "Descargado";
                    }
                    else
                    {
                        arr_estadoCat[i] = "Sin datos";
                    }
                    i=al_catalogos.size(); //finaliza ciclo
                }
            }
        }

        private String getBool(String cad)
        {
            try {
                if (cad.equals("true")) {
                    cad = "1";
                } else {
                    cad = "0";
                }
                return cad;
            }catch (Exception e)
            {
                return null;
            }
        }

        private String getDate(String cad)
        {
            String originalStringFormat = "yyyy-MM-dd'T'HH:mm:ss";
            String desiredStringFormat = "yyyy-MM-dd HH:mm:ss";

            SimpleDateFormat readingFormat = new SimpleDateFormat(originalStringFormat);
            SimpleDateFormat outputFormat = new SimpleDateFormat(desiredStringFormat);

            try {
                Date date = readingFormat.parse(cad);
                cad = outputFormat.format(date);
            } catch (Exception e) {
                cad=null;
                e.printStackTrace();
            }
            return cad;
        }

    }

}
