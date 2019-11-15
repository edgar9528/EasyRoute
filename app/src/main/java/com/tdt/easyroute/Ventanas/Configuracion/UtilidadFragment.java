package com.tdt.easyroute.Ventanas.Configuracion;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class UtilidadFragment extends Fragment {

    EditText et_folio,et_pagos,et_credito;
    Button bt_ver,bt_realizar,bt_radia,bt_preventa,bt_venta,bt_rapedido,bt_aauditoria,bt_dauditoria,bt_salir;

    ArrayList<DataTableLC.VentasDet> ventasDet;
    DataTableLC.Pagos pagos;
    DataTableLC.Creditos creditos;
    String nombreBase;
    String folio;

    Usuario user;
    Configuracion conf=null;


    public UtilidadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_utilidad, container, false);
        nombreBase =  getContext().getString( R.string.nombreBD );

        try
        {
            bt_ver = view.findViewById(R.id.button_ver);
            bt_realizar = view.findViewById(R.id.button_realizar);
            bt_radia = view.findViewById(R.id.button_radia);
            bt_preventa = view.findViewById(R.id.button_preventa);
            bt_venta = view.findViewById(R.id.button_venta);
            bt_rapedido = view.findViewById(R.id.button_rapedido);
            bt_aauditoria = view.findViewById(R.id.button_aauditoria);
            bt_dauditoria = view.findViewById(R.id.button_dauditoria);
            bt_salir = view.findViewById(R.id.button_salir);

            et_folio= view.findViewById(R.id.et_folio);
            et_pagos= view.findViewById(R.id.et_pagos);
            et_credito = view.findViewById(R.id.et_credito);

            bt_ver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    folio = et_folio.getText().toString();
                    if(!folio.isEmpty())
                    {
                        ver_click();
                    }
                    else
                        Toast.makeText(getContext(), "Ingresa un folio", Toast.LENGTH_SHORT).show();
                }
            });


            bt_realizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    realizar();
                }
            });

            bt_radia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reactivarDia();
                }
            });

            bt_preventa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    preventa();
                }
            });

            bt_venta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    venta();
                }
            });

            bt_rapedido.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reactivarPedido();
                }
            });

            bt_aauditoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activarAuditoria();
                }
            });

            bt_dauditoria.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    desactivarAuditoria();
                }
            });

            bt_salir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });


            inicializar();


        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.toString());
            Toast.makeText(getContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    public void inicializar()
    {
        ConfiguracionActivity activity = (ConfiguracionActivity) getActivity();
        user = activity.getUser();
        conf = activity.getConf(); //obtener la configuracion
    }

    public void realizar()
    {
        if(ventasDet!=null)
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(getActivity(), nombreBase, null, 1);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            try
            {
                String qry = "update inventario set inv_vendido_n=inv_vendido_n-{1} where prod_cve_n={0}";
                db.beginTransaction();

                for(int i=0; i<ventasDet.size();i++)
                {
                    if( !Utils.getBool( ventasDet.get (i).getProd_cobranza_n() ) )
                    {
                        db.execSQL(string.formatSql(qry, ventasDet.get(i).getProd_cant_n(), ventasDet.get(i).getProd_cve_n()));
                    }
                }

                db.execSQL(string.formatSql("delete from ventasdet where ven_folio_str='{0}'", folio));
                db.execSQL(string.formatSql("update ventas set ven_est_str='C' where ven_folio_str='{0}'", folio));
                db.execSQL(string.formatSql("delete from pagos where pag_referencia_str='{0}'", folio));
                db.execSQL(string.formatSql("delete from creditos where cred_referencia_str='{0}'", folio));
                db.execSQL(string.formatSql("delete from ventaenv where ven_folio_str='{0}'", folio));
                db.execSQL(string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), string.formatSql("ELIMINACION DE VENTA", Utils.Version()), string.formatSql("VENTA: {0}",folio), String.valueOf(conf.getRuta()) , ""));
                
                db.setTransactionSuccessful();

                Toast.makeText(getContext(), "Proceso terminado", Toast.LENGTH_SHORT).show();
                
            }catch (Exception e)
            {
                Log.d("salida","Error: "+e.toString());
                Toast.makeText(getContext(), "Error: "+e.toString(), Toast.LENGTH_SHORT).show();
            }
            finally
            {
                db.endTransaction();
                db.close();
            }

        }
        else
        {
            Toast.makeText(getContext(), "Primero realice una busqueda", Toast.LENGTH_SHORT).show();
        }
    }


    public void ver_click()
    {
        String json = BaseLocal.Select(string.formatSql("Select * from ventasdet where ven_folio_str='{0}'",folio),getContext());
        ventasDet = ConvertirRespuesta.getVentasDetJson(json);

        String json2 = BaseLocal.Select(string.formatSql("select * from pagos where pag_referencia_str='{0}'",folio),getContext());
        pagos = ConvertirRespuesta.getPagosJson(json2);

        String json3 = BaseLocal.Select(string.formatSql("select * from creditos where cred_referencia_str='{0}'",folio),getContext());
        creditos = ConvertirRespuesta.getCreditosJson(json3);


        if(pagos!=null)
        {
            et_pagos.setText(pagos.getPag_abono_n());
        }
        else
            et_pagos.setText("0");

        if(creditos!=null)
        {
            et_credito.setText(creditos.getCred_monto_n());
        }
        else
            et_credito.setText("0");


    }

    public void reactivarDia()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.ReactivarDia,getContext());

            String consulta = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), "CIERRE DE DIA", "DIA REACTIVADO POR SISTEMAS", String.valueOf(conf.getRuta()) , "Posicion" );

            BaseLocal.Insert(consulta,getContext());
            Toast.makeText(getContext(), "Día Reactivado", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void preventa()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.ActivarPreventa,getContext());

            Toast.makeText(getContext(), "Preventa activada", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void venta()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.DesactivarPreventa,getContext());

            Toast.makeText(getContext(), "Preventa desactivada", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void reactivarPedido()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.ReactivarPedidos,getContext());
            String con = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), string.formatSql("REACTIVAR PEDIDOS", Utils.Version()), "", String.valueOf( conf.getRuta() ) , "");
            BaseLocal.Insert(con,getContext());

            Toast.makeText(getContext(), "Pedidos reactivados", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void activarAuditoria()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.ActivarAuditoria,getContext());
            String con = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), string.formatSql("MODO AUDITORIA", Utils.Version()), "ACTIVO", String.valueOf( conf.getRuta() ) , "");
            BaseLocal.Insert(con,getContext());

            Toast.makeText(getContext(), "Auditoria activada", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }


    public void desactivarAuditoria()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.DesactivarAuditoria,getContext());
            String con = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), string.formatSql("MODO AUDITORIA", Utils.Version()), "INACTIVO", String.valueOf( conf.getRuta() ) , "");
            BaseLocal.Insert(con,getContext());

            Toast.makeText(getContext(), "Auditoria activada", Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }
    }



}
