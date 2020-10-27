package com.tdt.easyroute.Ventanas.FinDeDia.Sugerido;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Impresora;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.SugeridoVM;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;

public class SugeridoFragment extends Fragment implements AsyncResponseJSON {

    private ArrayList<DataTableLC.Productos_Sug> dtProd=null;
    private ArrayList<DataTableLC.Sugerido> dgSugerido=null;

    private EditText et_sku,et_cant;
    private int sugeridoIndice=-1;
    private String sugeridoSelec="",nombreBase,sugeridoAnt="";
    private Configuracion conf;
    private Usuario user;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;

    private SugeridoVM sugeridoVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sugeridoVM = ViewModelProviders.of ( getParentFragment() ).get(SugeridoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_familia, container, false);

        try {

            MainsugeridoFragment fragmentMain = (MainsugeridoFragment) getParentFragment();

            Button b_buscar, b_borrar;

            nombreBase = getContext().getString(R.string.nombreBD);
            dgSugerido = new ArrayList<>();

            conf = Utils.ObtenerConf(getActivity().getApplication());
            user = fragmentMain.getUserMainSugerido();

            layoutInflater = inflater;
            vista = view;
            tableLayout = view.findViewById(R.id.tableLayout);

            b_buscar = view.findViewById(R.id.button_buscar);
            b_borrar = view.findViewById(R.id.button_borrar);

            et_sku = view.findViewById(R.id.et_sku);
            et_cant = view.findViewById(R.id.et_cant);

            mostrarTitulo();

            b_borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrar();
                }
            });

            et_cant.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        buscar();
                        return true;
                    }
                    return false;
                }
            });

            b_buscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buscar();
                }
            });

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug10), e.getMessage());
        }

        return view;
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sugeridoVM.getDtProd().observe(getParentFragment(), new Observer<ArrayList<DataTableLC.Productos_Sug>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.Productos_Sug> dtprod) {
                dtProd = dtprod;
            }
        });

        sugeridoVM.getDgSugerido().observe(getParentFragment(), new Observer<ArrayList<DataTableLC.Sugerido>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.Sugerido> dgSug) {
                dgSugerido = dgSug;
                mostrarSugerido(-1);
            }
        });

        sugeridoVM.getProductoSKU().observe(getParentFragment(), new Observer< String >() {
            @Override
            public void onChanged(String producSku) {
                buscarSKU(producSku,"0");
            }
        });

        sugeridoVM.getStrBuscar().observe(getParentFragment(), new Observer< String[] >() {
            @Override
            public void onChanged(String strbuscar[]) {
                buscarSKU(strbuscar[0],strbuscar[1]);
            }
        });

        sugeridoVM.getBorrar().observe(getParentFragment(), new Observer< Boolean >() {
            @Override
            public void onChanged(Boolean busca) {
                if(busca)
                {
                    borrar();
                }
            }
        });


        sugeridoVM.getOpcion().observe(getParentFragment(), new Observer< String >() {
            @Override
            public void onChanged(String opcion) {
                switch (opcion)
                {
                    case "guardar":
                        guardar();
                        break;
                    case "enviar":
                        peticionEnviar();
                        break;
                    case "imprimir":
                        imprimir();
                        break;
                    case "salir":
                        Utils.RegresarInicio(getActivity());
                        break;
                }
            }
        });

    }

    private void buscar()
    {
        try
        {
            String sku = et_sku.getText().toString();
            String can = et_cant.getText().toString();

            if (!sku.isEmpty()) {
                et_sku.setText("");
                et_cant.setText("");
                buscarSKU(sku, can);
            } else
                Toast.makeText(getContext(), getString(R.string.tt_camposVacios), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_buscarInfo), e.getMessage());
        }
    }

    private void imprimir()
    {
        String mensajeImp = "\n";
        ArrayList<DataTableWS.Ruta> dtRutas = null;
        long cerv = 0, env = 0;

        try
        {
            String rutaCve = String.valueOf(conf.getRuta());
            String rutaDes = BaseLocal.SelectDato(string.formatSql("select rut_desc_str from rutas where rut_cve_n={0}", rutaCve), getContext());

            String json = BaseLocal.SelectDato(string.formatSql("select rut_cve_n from rutas where rut_prev_n={0}", String.valueOf(conf.getRuta())), getContext());
            dtRutas = ConvertirRespuesta.getRutasJson(json);

            if (dtRutas != null && dtRutas.size() == 0) dtRutas = null;

            if (rutaDes != null)
                mensajeImp += "RUTA: " + rutaDes + "\n";
            else
                mensajeImp += "RUTA: \n";

            mensajeImp += string.formatSql("ASESOR: {0} {1} {2}\r\n", user.getNombre(), user.getAppat(), user.getApmat());

            mensajeImp += "FECHA: " + Utils.FechaLocal() + " " + Utils.HoraLocal() + "\n\n";

            mensajeImp += "S U G E R I D O \n";

            mensajeImp += string.formatSql("{0}{1}{2}\n", "SKU", "       PRODUCTO", "         CANT.");

            DataTableLC.Sugerido r;
            String des,sku,sug,linea;
            for (int i = 0; i < dgSugerido.size(); i++) {
                r = dgSugerido.get(i);

                if (Double.parseDouble(r.getProd_sug_n()) > 0 && !r.getCat_desc_str().equals("ENVASE")) {
                    cerv += Long.parseLong(r.getProd_sug_n());

                    sku =r.getProd_sku_str();
                    des = r.getProd_desc_str();
                    sug = r.getProd_sug_n();

                    linea = Impresora.DarTamañoDer(sku,7) + Impresora.DarTamañoDer(des.replace(" ","_"),19)+" "+ Impresora.DarTamañoDer(sug,5)+"\n";

                    mensajeImp += linea;
                }
            }

            mensajeImp += "\nE N V A S E\n";

            mensajeImp += string.formatSql("{0} {1} {2} {3}\n", "SKU", "    ENVASE", "     LLENO", "VACIO");


            for (int i = 0; i < dgSugerido.size(); i++) {
                r = dgSugerido.get(i);

                if (r.getCat_desc_str().equals("ENVASE")) {
                    env += Long.parseLong(r.getProd_sug_n());
                    Double s = 0.0;
                    for (int j = 0; j < dgSugerido.size(); j++) {
                        if (dgSugerido.get(j).getId_envase_n().equals(r.getProd_cve_n()))
                            s = s + Double.parseDouble(dgSugerido.get(j).getProd_sug_n());
                    }


                    String[] palabras = r.getProd_desc_str().split(" ");
                    String descripcion="";
                    for(int k=0; k<palabras.length; k++)
                    {
                        if(palabras[k].length()>3)
                            descripcion += palabras[k].substring(0,3)+".";
                        else
                            descripcion += palabras[k]+".";
                    }
                    if(descripcion.length()>15)
                        descripcion = descripcion.substring(0,15);

                    linea= string.formatSql("{0} {1}", r.getProd_sku_str(), descripcion);

                    for(int k=linea.length(); k<20;k++ )
                        linea+=" ";

                    mensajeImp += linea+ string.formatSql("{2}  {3} \n", String.valueOf(s), "0.0");
                }
            }

            mensajeImp += "\nTOTAL CERVEZA: " + cerv + "\n";
            mensajeImp += "TOTAL ENVASE: " + env + "\n\n";

        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), getString(R.string.error_imprimir), Toast.LENGTH_SHORT).show();
        }


        if (conf.getPreventa() == 1 && dtRutas!=null)
        {
            for(int i=0; i <dtRutas.size();i++)
            {
                imprimirRuta( dtRutas.get(i).getRut_cve_n() );
            }
        }

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle(getString(R.string.msg_impSug));
        dialogo1.setMessage(mensajeImp);
        dialogo1.setCancelable(false);
        final String finalMensajeImp = mensajeImp;
        dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                Impresora.imprimir(finalMensajeImp.replace("\n","\r"),getContext());
            }
        });
        dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });

        AlertDialog alertDialog = dialogo1.show();
        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            textView.setTextAppearance( R.style.estiloImprimir);
        }

    }

    private void imprimirRuta(String ruta)
    {
        try
        {
            ArrayList<DataTableLC.ImprimirSugerido> dtVenta = null;

            String menImp="";
            String rut_des = BaseLocal.SelectDato( string.formatSql("Select rut_desc_str from rutas where rut_cve_n={0}", ruta),getContext() );

            menImp+= "REPARTO: "+rut_des+"\n";

            menImp += string.formatSql("ASESOR: {0} {1} {2}\r\n", user.getNombre(), user.getAppat(), user.getApmat());


            String con= string.formatSql("select f.fam_orden_n,p.prod_orden_n,p.prod_sku_str,cat.cat_desc_str,p.prod_desc_str,p.id_envase_n," +
                    "sum(pd.prod_cant_n) prod_sug_n from preventa pre inner join preventadet pd " +
                    "on pre.prev_folio_str=pd.prev_folio_str inner join productos p " +
                    "on pd.prod_cve_n=p.prod_cve_n inner join clientes c " +
                    "on pre.cli_cve_n=c.cli_cve_n inner join categorias cat " +
                    "on p.cat_cve_n =cat.cat_cve_n inner join familias f " +
                    "on p.fam_cve_n=f.fam_cve_n where c.rut_cve_n={0} " +
                    "group by f.fam_orden_n,p.prod_orden_n,p.prod_sku_str,p.prod_desc_str,p.id_envase_n,cat.cat_desc_str", ruta);

            String json = BaseLocal.Select(con,getContext());

            dtVenta = ConvertirRespuesta.getImprimirSugeridoJson(json);

            menImp += "FECHA IMPRESION: " + Utils.FechaLocal() + " " + Utils.HoraLocal() + "\n\n";

            menImp += "S U G E R I D O \n\n";

            menImp += string.formatSql("{0} {1} {2} \n", "  SKU  ", "      PRODUCTO      ", "CANT.");


            long cerv=0;

            if(dtVenta!=null)
            {
                DataTableLC.ImprimirSugerido r;
                String des;
                for(int i=0; i<dtVenta.size();i++)
                {
                    r = dtVenta.get(i);

                    des = r.getProd_desc_str();
                    if (des.length() > 20)
                        des = des.substring(0, 20);

                    if(!r.getCat_desc_str().equals("ENVASE"))
                        cerv+= Long.parseLong( r.getProd_sug_n() );

                    menImp += string.formatSql("{0} {1} {2} \n", r.getProd_sku_str(),des, r.getProd_sug_n());

                }

            }

            menImp += "\nTOTAL CERVEZA: " + cerv + "\n";

            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle(getString(R.string.msg_impSug));
            dialogo1.setMessage(menImp);
            dialogo1.setCancelable(false);
            final String finalMenImp = menImp;
            dialogo1.setPositiveButton(getString(R.string.msg_si), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Impresora.imprimir(finalMenImp.replace("\n","\r"),getContext());
                }
            });
            dialogo1.setNegativeButton(getString(R.string.msg_no), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    //cancelar();
                }
            });

            AlertDialog alertDialog = dialogo1.show();
            TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                textView.setTextAppearance( R.style.estiloImprimir);
            }


        }
        catch (Exception e)
        {
            Log.d("salida", "Error: "+e.getMessage());
            Toast.makeText(getContext(), getString(R.string.error_imprimir), Toast.LENGTH_SHORT).show();
        }
    }

    private void peticionEnviar()
    {
        try
        {
            guardar();

            String json = BaseLocal.Select("Select * from sugerido",getContext());

            ArrayList<DataTableLC.SugeridoTable> sug = ConvertirRespuesta.getSugeridoTableJson(json);

            if(sug!=null)
            {
                ArrayList< PropertyInfo > propertyInfos = new ArrayList<>();

                PropertyInfo pi1 = new PropertyInfo();
                pi1.setName("dt");
                pi1.setValue( json );
                propertyInfos.add(pi1);

                PropertyInfo pi2 = new PropertyInfo();
                pi2.setName("ruta");
                pi2.setValue(conf.getRuta());
                propertyInfos.add(pi2);

                PropertyInfo pi3 = new PropertyInfo();
                pi3.setName("usu");
                pi3.setValue(user.getUsuario().toUpperCase());
                propertyInfos.add(pi3);

                ConexionWS_JSON cws = new ConexionWS_JSON(getContext(),"RecibirSugeridoJ");
                cws.propertyInfos = propertyInfos;
                cws.delegate= SugeridoFragment.this;
                cws.execute();

            }
            else
            {
                Toast.makeText(getContext(), getString(R.string.tt_sug1), Toast.LENGTH_SHORT).show();
            }

        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
            Utils.msgError(getContext(), getString(R.string.error_intPeticion), e.getMessage());
        }


    }

    private void guardar()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(getContext(), nombreBase, null, 1);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        try
        {
            db.beginTransaction();

            db.execSQL("delete from sugerido");

            String con= "insert into sugerido(prod_cve_n,prod_sku_str,prod_sug_n) values({0},'{1}',{2})";
            DataTableLC.Sugerido r;
            for(int i=0; i< dgSugerido.size();i++)
            {
                r= dgSugerido.get(i);
                db.execSQL(string.formatSql( con, r.getProd_cve_n(), r.getProd_sku_str(), r.getProd_sug_n() ) );
            }
            db.setTransactionSuccessful();

            Toast.makeText(getContext(), getString(R.string.tt_infoActu), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Utils.msgError(getContext(), getString(R.string.error_almacenar), e.getMessage());
        }
        finally {
            db.endTransaction();
            db.close();
        }

    }

    private void borrar()
    {
        try
        {
            if (sugeridoIndice >= 0)
            {
                dgSugerido.remove(sugeridoIndice);


                if(sugeridoIndice<dgSugerido.size())
                {
                    sugeridoIndice=sugeridoIndice;
                    sugeridoSelec = dgSugerido.get(sugeridoIndice).getProd_sku_str();
                }
                else
                if(sugeridoIndice==dgSugerido.size())
                {
                    sugeridoIndice=sugeridoIndice-1;
                    sugeridoSelec = dgSugerido.get(sugeridoIndice-1).getProd_sku_str();
                }
                else
                {
                    sugeridoIndice=-1;
                    sugeridoSelec="";
                }

                calcularEnvase();
                mostrarSugerido(sugeridoIndice);

            }
            else
            {
                Toast.makeText(getContext(), getString(R.string.tt_seleccionarUno), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_borrar), e.getMessage());
        }
    }

    private void buscarSKU(String sku,String cant)
    {
        try
        {
            Log.d("salida", "SKU BUSCAR: " + sku + "   can:" + cant);
            int seleccion = -1;
            sugeridoSelec = "";
            int c = -1;

            try {
                if (cant == null || cant.isEmpty())
                    c = 0;
                else
                    c = Integer.parseInt(cant);
            } catch (Exception e) {
                Toast.makeText(getContext(), getString(R.string.tt_canCorrecta), Toast.LENGTH_SHORT).show();
                return;
            }

            int k = -1;

            DataTableLC.Sugerido r = null;

            for (int i = 0; i < dgSugerido.size(); i++) {
                if (sku.equals(dgSugerido.get(i).getProd_sku_str())) {
                    r = dgSugerido.get(i);
                    k = i;
                    i = dgSugerido.size();
                }
            }

            if (r != null) {
                et_sku.setText("");
                et_cant.setText("");

                seleccion = k;
                dgSugerido.get(k).setProd_sug_n(String.valueOf(c));
            } else {
                DataTableLC.Productos_Sug ri = null;

                for (int i = 0; i < dtProd.size(); i++) {
                    if (sku.equals(dtProd.get(i).getProd_sku_str())) {
                        ri = dtProd.get(i);
                        i = dtProd.size();
                    }
                }

                if (ri != null) {
                    et_sku.setText("");
                    et_cant.setText("");

                    DataTableLC.Sugerido sug = new DataTableLC.Sugerido();

                    sug.setProd_cve_n(ri.getProd_cve_n());
                    sug.setProd_sku_str(ri.getProd_sku_str());
                    sug.setProd_desc_str(ri.getProd_desc_str());
                    sug.setId_envase_n(ri.getId_envase_n());
                    sug.setProd_sug_n(String.valueOf(c));
                    sug.setCat_desc_str(ri.getCat_desc_str());

                    dgSugerido.add(sug);

                    seleccion = dgSugerido.size() - 1;

                    tableLayout.requestFocus();
                } else {
                    Log.d("salida", "Entro aqui, ri nulo");

                    seleccion = -1;
                    sugeridoSelec = "";
                    sugeridoIndice = -1;

                    Toast.makeText(getContext(), getString(R.string.tt_skuNo), Toast.LENGTH_SHORT).show();
                }
            }

            calcularEnvase();
            mostrarSugerido(seleccion);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_buscarInfo), e.getMessage() );
        }
    }

    private void calcularEnvase()
    {
        try {
            for (int i = 0; i < dtProd.size(); i++) {
                if (dtProd.get(i).getCat_desc_str().equals("ENVASE")) {

                    int k = -1;
                    int o = 0;
                    DataTableLC.Sugerido e = null;
                    for (int j = 0; j < dgSugerido.size(); j++) {
                        if (dgSugerido.get(j).getProd_cve_n().equals(dtProd.get(i).getProd_cve_n())) {
                            e = dgSugerido.get(j);
                            k = j;
                        }

                        if (dgSugerido.get(j).getId_envase_n().equals(dtProd.get(i).getProd_cve_n())) {
                            o = o + Integer.parseInt(dgSugerido.get(j).getProd_sug_n());
                        }

                    }

                    if (e != null) {

                    } else {
                        DataTableLC.Sugerido ri = new DataTableLC.Sugerido();
                        ri.setProd_cve_n(dtProd.get(i).getProd_cve_n());
                        ri.setProd_sku_str(dtProd.get(i).getProd_sku_str());
                        ri.setProd_desc_str(dtProd.get(i).getProd_desc_str());
                        ri.setId_envase_n(dtProd.get(i).getId_envase_n());
                        ri.setProd_sug_n("0");
                        ri.setCat_desc_str(dtProd.get(i).getCat_desc_str());
                        dgSugerido.add(ri);

                        k = dgSugerido.size() - 1;

                    }
                    dgSugerido.get(k).setProd_sug_n(String.valueOf(o));
                }
            }

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug5), e.getMessage() );
        }
    }

    private void mostrarTitulo()
    {
        try {
            tableLayout.removeAllViews();
            TableRow tr;

            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido2, null);

            ((TextView) tr.findViewById(R.id.t_sku)).setTypeface(((TextView) tr.findViewById(R.id.t_sku)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_producto)).setTypeface(((TextView) tr.findViewById(R.id.t_producto)).getTypeface(), Typeface.BOLD);
            ((TextView) tr.findViewById(R.id.t_sugerido)).setTypeface(((TextView) tr.findViewById(R.id.t_sugerido)).getTypeface(), Typeface.BOLD);

            tableLayout.addView(tr);
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_mostrarInfo), e.getMessage());
        }
    }

    private void mostrarSugerido(int seleccion)
    {
        try {
            mostrarTitulo();

            for (int i = 0; i < dgSugerido.size(); i++) {
                TableRow tr;

                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido2, null);
                ((TextView) tr.findViewById(R.id.t_sku)).setText(dgSugerido.get(i).getProd_sku_str());
                ((TextView) tr.findViewById(R.id.t_producto)).setText(dgSugerido.get(i).getProd_desc_str());
                ((TextView) tr.findViewById(R.id.t_sugerido)).setText(dgSugerido.get(i).getProd_sug_n());

                tr.setTag(dgSugerido.get(i).getProd_sku_str());
                tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila

                if (i == seleccion) {
                    tr.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    sugeridoSelec = dgSugerido.get(i).getProd_sku_str();
                    sugeridoAnt = sugeridoSelec;
                    sugeridoIndice = i;
                }

                tableLayout.addView(tr);
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_mostrarInfo), e.getMessage());
        }
    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            sugeridoSelec = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            if(sugeridoAnt.equals(sugeridoSelec))
            {
                int rowIndex = tableLayout.indexOfChild(tr)-1;
                modificarSugerido(rowIndex);
            }
            else
            {
                for(int i=0; i<dgSugerido.size();i++)
                {
                    TableRow row = (TableRow)vista.findViewWithTag(dgSugerido.get(i).getProd_sku_str());
                    row.setBackgroundColor( getResources().getColor(R.color.bgDefault) );

                    if(sugeridoSelec.equals( dgSugerido.get(i).getProd_sku_str() ))
                        sugeridoIndice = i;
                }

                //pinta de azul la fila y actualiza la cve de la fila seccionada
                tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );
                tr.requestFocus();
                sugeridoAnt=sugeridoSelec;
            }
        }
    };

    private void modificarSugerido(int index)
    {
        try
        {
            if (!dgSugerido.get(index).getCat_desc_str().equals("ENVASE")) {
                final String sku = dgSugerido.get(index).getProd_sku_str();
                String sug = dgSugerido.get(index).getProd_sug_n();
                String msg = getResources().getString(R.string.msg_actualizaSuge2) + ": " + sug;

                LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.dialog_sugerido, null);
                final EditText editText = (EditText) view.findViewById(R.id.ti_sugerido);

                final AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(R.string.msg_actualizaSuge)
                        .setMessage(msg)
                        .setView(view)
                        .setPositiveButton(R.string.bt_aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String lectura = String.valueOf(editText.getText());
                                buscarSKU(sku, lectura);
                            }
                        })
                        .setNegativeButton(R.string.bt_cancelar, null)
                        .create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                dialog.show();

                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            String lectura = String.valueOf(editText.getText());
                            buscarSKU(sku, lectura);

                            if (dialog.isShowing())
                                dialog.dismiss();

                            return true;
                        }
                        return false;
                    }
                });
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug11), e.getMessage());
        }
    }

    @Override
    public void recibirPeticion(boolean estado, String respuesta)
    {
        try {
            if (estado) {
                if (respuesta != null) {
                    //Obtenemos el objeto usuario con los valores del servidor, lo guardamos en u
                    DataTableWS.RetValInicioDia ret = ConvertirRespuesta.getRetValInicioDiaJson(respuesta);

                    if (ret.getRet().equals("true")) {
                        if (conf.getPreventa() == 1) {
                            BaseLocal.Insert(Querys.ConfiguracionHH.DesactivarPedidos, getContext());
                        }
                        Toast.makeText(getContext(), getString(R.string.tt_infoActu)+" " +ret.getMsj(), Toast.LENGTH_LONG).show();
                    } else {
                        if (conf.getPreventa() == 1 && !conf.isDescarga()) {
                            if (ret.getMsj().contains("Ya existe una descarga previa de preventa")) {
                                BaseLocal.Insert(Querys.ConfiguracionHH.DesactivarPedidos, getContext());
                            }
                        }
                        Utils.msgError(getContext(), getString(R.string.error_transmitir), ret.getMsj());

                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_transmitir), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), respuesta, Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_peticion), e.getMessage());
        }
    }
}
