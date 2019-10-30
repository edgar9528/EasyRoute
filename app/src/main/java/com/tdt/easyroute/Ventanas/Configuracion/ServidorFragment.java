package com.tdt.easyroute.Ventanas.Configuracion;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.ParametrosWS;
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

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    ArrayList<String> lista_catalogos;
    ArrayList<String> metodosWS;
    boolean[] rbSeleccionados;
    Button button_selec,button_deselec,button_sinc;


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
                            metodosWS.add("Obtener"+lista_catalogos.get(i)+"J");
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

            boolean result = true;
            resultado=null;

            try
            {

                //for(int i=0; i<metodosWS.size();i++)
                //{

                    parametrosWS = new ParametrosWS("ObtenerPromocionesKitJ", getActivity().getApplicationContext());
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
                    }
                //}

            }catch (Exception e)
            {
                Log.d("salida","error: "+e.getMessage());
                result=false;
                resultado= "Error: "+e.getMessage();
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
                almacenarLocal();
                Toast.makeText(context, "Información descargada", Toast.LENGTH_SHORT).show();
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




        }

        private void almacenarLocal()
        {

        }

    }




}
