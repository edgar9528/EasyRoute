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

    private EditText et_folio,et_pagos,et_credito;
    private ArrayList<DataTableLC.VentasDet> ventasDet;
    private DataTableLC.Pagos pagos;
    private DataTableLC.Creditos creditos;
    private String nombreBase;
    private String folio;

    private Usuario user;
    private Configuracion conf=null;


    public UtilidadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_utilidad, container, false);
        Button bt_ver,bt_realizar,bt_radia,bt_preventa,bt_venta,bt_rapedido,bt_aauditoria,bt_dauditoria,bt_salir;
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
                        Toast.makeText(getContext(), getString(R.string.tt_camposVacios), Toast.LENGTH_SHORT).show();
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

            Utils.msgError(getContext(), getString(R.string.err_conf14), e.getMessage() );
        }

        return view;
    }

    public void inicializar()
    {
        ConfiguracionActivity activity = (ConfiguracionActivity) getActivity();
        user = activity.getUser();
        conf = activity.getConf(); //obtener la configuracion
    }

    private void realizar()
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

                Toast.makeText(getContext(), getString(R.string.tt_infoActu), Toast.LENGTH_SHORT).show();
                
            }catch (Exception e)
            {
                Log.d("salida","Error: "+e.toString());
                Utils.msgError(getContext(), getString(R.string.err_cargarInfo),e.getMessage());
            }
            finally
            {
                db.endTransaction();
                db.close();
            }

        }
        else
        {
            Toast.makeText(getContext(), getString(R.string.tt_realizaBusqueda), Toast.LENGTH_SHORT).show();
        }
    }


    private void ver_click()
    {
        try {
            String json = BaseLocal.Select(string.formatSql("Select * from ventasdet where ven_folio_str='{0}'", folio), getContext());
            ventasDet = ConvertirRespuesta.getVentasDetJson(json);

            String json2 = BaseLocal.Select(string.formatSql("select * from pagos where pag_referencia_str='{0}'", folio), getContext());
            pagos = ConvertirRespuesta.getPagosJson(json2);

            String json3 = BaseLocal.Select(string.formatSql("select * from creditos where cred_referencia_str='{0}'", folio), getContext());
            creditos = ConvertirRespuesta.getCreditosJson(json3);


            if (pagos != null) {
                et_pagos.setText(pagos.getPag_abono_n());
            } else
                et_pagos.setText("0");

            if (creditos != null) {
                et_credito.setText(creditos.getCred_monto_n());
            } else
                et_credito.setText("0");

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_cargarInfo),e.getMessage() );
        }

    }

    private void reactivarDia()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.ReactivarDia,getContext());

            String consulta = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), "CIERRE DE DIA", "DIA REACTIVADO POR SISTEMAS", String.valueOf(conf.getRuta()) , "Posicion" );
            BaseLocal.Insert(consulta,getContext());
            Toast.makeText(getContext(), getString(R.string.tt_reacDia), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf13),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void preventa()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.ActivarPreventa,getContext());

            Toast.makeText(getContext(), getString(R.string.tt_prevenAct), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf12),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    public void venta()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.DesactivarPreventa,getContext());

            Toast.makeText(getContext(), getString(R.string.tt_prevenDes), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf11),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void reactivarPedido()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.ReactivarPedidos,getContext());
            String con = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), string.formatSql("REACTIVAR PEDIDOS", Utils.Version()), "", String.valueOf( conf.getRuta() ) , "");
            BaseLocal.Insert(con,getContext());

            Toast.makeText(getContext(), getString(R.string.tt_pedidosAct), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf10),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void activarAuditoria()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.ActivarAuditoria,getContext());
            String con = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), string.formatSql("MODO AUDITORIA", Utils.Version()), "ACTIVO", String.valueOf( conf.getRuta() ) , "");
            BaseLocal.Insert(con,getContext());

            Toast.makeText(getContext(), getString(R.string.tt_audiAct), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf9),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }

    private void desactivarAuditoria()
    {
        try{

            BaseLocal.Insert(Querys.ConfiguracionHH.DesactivarAuditoria,getContext());
            String con = string.formatSql(Querys.Trabajo.InsertBitacoraHHSesion, user.getUsuario(), string.formatSql("MODO AUDITORIA", Utils.Version()), "INACTIVO", String.valueOf( conf.getRuta() ) , "");
            BaseLocal.Insert(con,getContext());

            Toast.makeText(getContext(), getString(R.string.tt_audiDes), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_conf8),e.getMessage());
            Log.d("salida","Error: "+e.getMessage());
        }
    }



}
