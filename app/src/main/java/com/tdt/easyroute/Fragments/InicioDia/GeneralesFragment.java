package com.tdt.easyroute.Fragments.InicioDia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.ViewModel.StartdayVM;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class GeneralesFragment extends Fragment implements AsyncResponseJSON {

    EditText et_empresa, et_ruta, et_tRuta, et_vendedor, et_fecha,et_hora,et_camion,et_km;
    Button bt_validar,bt_salir;

    ArrayList<DataTableLC.RutaTipo> lista_rutas;
    ArrayList<DataTableWS.Empresa> lista_empresas;
    DataTableWS.Empresa dt_empresa;
    DataTableLC.RutaTipo dt_rutaTipo;
    ArrayList<String> rutas,empresas;
    SpinnerDialog sdRutas,sdEmpresas;
    String peticion,tipoRuta,ruta,empresa,tiporuta_cve;
    Usuario user;
    String[] campos;

    String latLon;

    boolean sincro=false;

    Handler handler=null;
    Runnable myRunnable;

    private StartdayVM startdayVM;

    public GeneralesFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startdayVM = ViewModelProviders.of( getParentFragment()).get(StartdayVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generales, container, false);
        // Inflate the layout for this fragment
        MainActivity mainActivity = (MainActivity) getActivity();

        user = mainActivity.getUsuario();

        et_empresa = view.findViewById(R.id.et_empresa);
        et_ruta = view.findViewById(R.id.et_ruta);
        et_tRuta = view.findViewById(R.id.et_truta);
        et_vendedor = view.findViewById(R.id.et_vendedor);
        et_fecha = view.findViewById(R.id.et_fecha);
        et_hora = view.findViewById(R.id.et_hora);
        et_camion = view.findViewById(R.id.et_camion);
        et_km = view.findViewById(R.id.et_kilometraje);
        bt_validar = view.findViewById(R.id.button_validar);
        bt_salir = view.findViewById(R.id.button_salir);

        inicializar();

        et_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sdEmpresas!=null)
                    sdEmpresas.showSpinerDialog();
                else
                    Toast.makeText(getContext(), "No se encontraron empresas", Toast.LENGTH_LONG).show();
            }
        });

        et_ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sdRutas!=null)
                    sdRutas.showSpinerDialog();
                else
                    Toast.makeText(getContext(), "No se encontraron rutas", Toast.LENGTH_LONG).show();
            }
        });


        bt_validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
            }
        });

        bt_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startdayVM.getSincro().observe(getParentFragment(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean s) {
                sincro=s;
                Log.d("salida","Boolean sincro: "+sincro);
            }
        });

    }

    private void inicializar()
    {
        campos = new String[8];
        et_vendedor.setText(user.getUsuario());
        buscarEmpresaRuta();
        obtenerRutas();
        obtenerEmpresas();

        mostrarFechaHora();

    }

    private void mostrarFechaHora()
    {
        et_fecha.setText(Utils.FechaLocal());

        handler = new Handler();

        myRunnable = new Runnable() {
            @Override
            public void run() {
                et_hora.setText(Utils.HoraLocal());
                handler.postDelayed(this, 1000);
                //Log.d("salida",et_hora.getText().toString());
            }
        };

        handler.post(myRunnable);

        //h= new Handler();
        /*h.post(new Runnable() {
            @Override
            public void run() {

                et_hora.setText(Utils.HoraLocal());
                Log.d("salida",et_hora.getText().toString());

                h.postDelayed(this, 1000);
            }
        });*/

    }

    private void buscarEmpresaRuta()
    {
        try {
            String emp = Utils.LeefConfig("empresa", getActivity().getApplication());
            String rut = Utils.LeefConfig("ruta", getActivity().getApplication());
            ruta = rut;
            String json = BaseLocal.Select("Select emp_nom_str from empresas where emp_cve_n=" + emp, getContext());
            if (ConvertirRespuesta.getEmpresasJson(json) != null) {
                dt_empresa = ConvertirRespuesta.getEmpresasJson(json).get(0);
                et_empresa.setText(dt_empresa.getEmp_nom_str());
                empresa = emp;

                Log.d("salida","emp: "+empresa);
            }

            json = BaseLocal.Select("Select r.rut_desc_str,tr.trut_desc_str,r.trut_cve_n,r.rut_cve_n,asesor_cve_str from rutas r inner join tiporutas tr on r.trut_cve_n=tr.trut_cve_n where rut_cve_n=" + rut, getContext());


            if (ConvertirRespuesta.getRutaTipoJson(json) != null) {
                dt_rutaTipo = ConvertirRespuesta.getRutaTipoJson(json).get(0);
                et_ruta.setText(dt_rutaTipo.getRut_desc_str());
                et_tRuta.setText(dt_rutaTipo.getTrut_desc_str());
                tipoRuta = dt_rutaTipo.getTrut_desc_str();
                tiporuta_cve = dt_rutaTipo.getRut_cve_n();

            }

            actualizaCatalogos();
            startdayVM.setTipoRuta(tipoRuta);
            startdayVM.setRuta(ruta);

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error al buscar datos de empresa y ruta: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }

    }

    private void actualizaCatalogos()
    {
        String catalogos="LimpiarDatos,Empresas,Estatus,Roles,RolesModulos,Modulos,Usuarios,TipoRutas,Rutas," +
                "Marcas,Unidades,CondicionesVenta,Productos,ListaPrecios,PrecioProductos,NivelCliente,FormasPago," +
                "FrecuenciasVisita,MotivosNoVenta,MotivosNoLectura,Categorias,Familias,Presentaciones,Clientes," +
                "Creditos,Direcciones,ClientesVentaMes,Promociones,PromocionesKit,Consignas";

        if(tipoRuta.equals("REPARTO"))
            catalogos += ",Preventa";

        startdayVM.setCatalogos(catalogos);
    }

    private void obtenerRutas()
    {
        String con = "Select r.rut_cve_n,r.rut_desc_str,tr.trut_cve_n,tr.trut_desc_str,asesor_cve_str from rutas r inner join tiporutas tr on r.trut_cve_n=tr.trut_cve_n where r.est_cve_str='A'";
        String json = BaseLocal.Select(con,getContext());
        lista_rutas = ConvertirRespuesta.getRutaTipoJson(json);

        if(lista_rutas!=null)
        {
            rutas = new ArrayList<>();
            for (int i = 0; i < lista_rutas.size(); i++)
                rutas.add( lista_rutas.get(i).getRut_desc_str() );

            //SE CREA SPINNER Y SU METODO ONCLICK
            sdRutas=new SpinnerDialog(getActivity(),rutas,"Seleccione una ruta","Cerrar");
            //spinnerDialog=new SpinnerDialog(getActivity(),rutas,"Selecciona una ruta",R.style.DialogAnimations_SmileWindow,"Cerrar");

            sdRutas.setCancellable(true); // for cancellable
            sdRutas.setShowKeyboard(false);// for open keyboard by default

            sdRutas.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    obtenerDatosRuta(position);
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService (Context.INPUT_METHOD_SERVICE); mgr.hideSoftInputFromWindow (getView().getWindowToken (), 0);
                    //Toast.makeText(getContext(), item + "  " + position+"", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void obtenerDatosRuta(int i)
    {
        et_ruta.setText(lista_rutas.get(i).getRut_desc_str());
        et_tRuta.setText(lista_rutas.get(i).getTrut_desc_str());

        ruta= lista_rutas.get(i).getRut_cve_n();
        tipoRuta = lista_rutas.get(i).getTrut_desc_str();
        tiporuta_cve = lista_rutas.get(i).getRut_cve_n();

        startdayVM.setTipoRuta(tipoRuta);
        startdayVM.setRuta(ruta);

        actualizaCatalogos();

    }

    private void obtenerEmpresas()
    {
        String con = "Select *from empresas where est_cve_str='A'";
        String json = BaseLocal.Select(con,getContext());
        lista_empresas = ConvertirRespuesta.getEmpresasJson(json);

        if(lista_empresas!=null)
        {
            empresas = new ArrayList<>();
            for (int i = 0; i < lista_empresas.size(); i++)
                empresas.add( lista_empresas.get(i).getEmp_nom_str());

            //SE CREA SPINNER Y SU METODO ONCLICK
            sdEmpresas=new SpinnerDialog(getActivity(),empresas,"Seleccione una empresa","Cerrar");
            //spinnerDialog=new SpinnerDialog(getActivity(),rutas,"Selecciona una ruta",R.style.DialogAnimations_SmileWindow,"Cerrar");

            sdEmpresas.setCancellable(true); // for cancellable
            sdEmpresas.setShowKeyboard(false);// for open keyboard by default

            sdEmpresas.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    et_empresa.setText(item);

                    for(int i=0; i<lista_empresas.size();i++)
                    {
                        if(lista_empresas.get(i).getEmp_nom_str().equals(item))
                        {
                            empresa = lista_empresas.get(i).getEmp_cve_n();
                        }
                    }

                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService (Context.INPUT_METHOD_SERVICE); mgr.hideSoftInputFromWindow (getView().getWindowToken (), 0);
                    //Toast.makeText(getContext(), item + "  " + position+"", Toast.LENGTH_SHORT).show();
                }
            });
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
                    crearConfiguracion(ubi[0]);
                }
            }, 3000);

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void validar()
    {
        campos[0]=et_empresa.getText().toString();
        campos[1]=et_ruta.getText().toString();
        campos[2]=et_tRuta.getText().toString();
        campos[3]=et_vendedor.getText().toString();
        campos[4]=et_fecha.getText().toString();
        campos[5]=et_hora.getText().toString();
        campos[6]=et_camion.getText().toString();
        campos[7]=et_km.getText().toString();

        if(string.CamposLlenos(campos))
        {
            if(sincro)
            {
                peticionFecha();
            }
            else
                Toast.makeText(getContext(), "No ha realizado la sincronizacion", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(getContext(), "Rellena todos los campos", Toast.LENGTH_SHORT).show();


    }

    private void peticionFecha()
    {
        //obtener fecha
        peticion="Fecha";
        ConexionWS_JSON conexionWS = new ConexionWS_JSON(getContext(),"HoraActual");
        conexionWS.delegate = GeneralesFragment.this;
        conexionWS.execute();
    }

    private void peticionInicioDia()
    {
        peticion="InicioDia";

        //parametros del metodo
        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
        PropertyInfo pi_ruta = new PropertyInfo();
        pi_ruta.setName("ruta");
        pi_ruta.setValue(ruta);
        propertyInfos.add(pi_ruta);

        PropertyInfo pi_usu = new PropertyInfo();
        pi_usu.setName("usu");
        pi_usu.setValue(user.getUsuario());
        propertyInfos.add(pi_usu);

        PropertyInfo pi_version = new PropertyInfo();
        pi_version.setName("version");
        pi_version.setValue(Utils.Version());
        propertyInfos.add(pi_version);

        Log.d("salida",ruta+" "+user.getUsuario()+" "+Utils.Version());

        ConexionWS_JSON conexionWSJ = new ConexionWS_JSON(getContext(),"InicioDiaJ");
        conexionWSJ.propertyInfos= propertyInfos;
        conexionWSJ.delegate = GeneralesFragment.this;
        conexionWSJ.execute();
    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

        if(estado)
        {
            //recibio información
            if (respuesta != null)
            {
                if(peticion.equals("Fecha"))
                {
                    String fechaLocal = et_fecha.getText().toString() +" "+et_hora.getText().toString();
                    String fechaWS = Utils.FechaWS(respuesta);

                    if(Utils.FechasIguales(fechaLocal,fechaWS))
                    {
                        peticionInicioDia();
                    }
                    else
                    {
                        fechaIncorrecta();
                    }
                }
                else
                if(peticion.equals("InicioDia"))
                {
                    DataTableWS.RetValInicioDia retVal = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                    if(retVal!=null)
                    {
                        if(retVal.getRet().equals("true"))
                        {
                            //UNA VEZ QUE SE OBTIENE LA UBICACION, SE CREA LA CONGIFURACION HH
                            obtenerUbicacion();
                        }
                        else
                        {
                            Toast.makeText(getContext(), "Error al iniciar el dia en el servidor por favor comuniquese a sistemas.\n"+retVal.getMsj(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Log.d("salida","retval nulo");

                }
            }
            else
            {
                Toast.makeText(getContext(), "No se encontro información", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();
        }

    }

    private void fechaIncorrecta()
    {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Aviso");
        dialogo1.setMessage("Para continuar debe configurar correctamente la fecha/hora del dispositivo");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Configurar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
            }
        });
        dialogo1.show();

    }

    public void crearConfiguracion(String posicion)
    {

        BaseLocal.Insert(Querys.Inventario.DesactivaCarga,getContext());

        BaseLocal.Insert("update ConfiguracionHH set est_cve_str='I'",getContext());

        String consulta = string.formatSql(Querys.ConfiguracionHH.InsertConfiguracion,ruta,empresa,campos[3],campos[6],campos[7],tiporuta_cve);
        BaseLocal.Insert(consulta,getContext());

        BaseLocal.Insert(string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(),"INICIO DIA", "SE CREO CONFIGURACION DE INICIO DE DIA",ruta,posicion),getContext());

        Log.d("salida","Configuracaion HH creada");
        Toast.makeText(getContext(), "Información guardada", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(myRunnable);
    }

}

