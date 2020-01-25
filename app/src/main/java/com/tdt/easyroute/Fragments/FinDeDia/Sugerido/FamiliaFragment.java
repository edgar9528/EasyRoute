package com.tdt.easyroute.Fragments.FinDeDia.Sugerido;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.SugeridoVM;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.Inflater;

public class FamiliaFragment extends Fragment {

    private ArrayList<DataTableLC.Productos_Sug> dtProd=null;
    private ArrayList<DataTableLC.Sugerido> dgSugerido=null;
    private ArrayList<String> lvFamilias=null;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;

    private MainsugeridoFragment fragmentMain;
    private Configuracion conf;

    private String consulta,json;
    private String[] strBuscar =  new String[2];

    private EditText et_sku,et_cant;
    private SugeridoVM sugeridoVM;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.sugerido_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        sugeridoVM = ViewModelProviders.of ( getParentFragment() ).get(SugeridoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_familia, container, false);

        try
        {
            layoutInflater = inflater;
            vista = view;

            Button b_buscar, b_borrar;

            conf = Utils.ObtenerConf(getActivity().getApplication());
            fragmentMain = (MainsugeridoFragment) getParentFragment();

            et_sku = view.findViewById(R.id.et_sku);
            et_cant = view.findViewById(R.id.et_cant);
            b_buscar = view.findViewById(R.id.button_buscar);
            b_borrar = view.findViewById(R.id.button_borrar);

            tableLayout = view.findViewById(R.id.tableLayout);

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

            dgSugerido = new ArrayList<>();

            b_buscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buscar();
                }
            });

            b_borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sugeridoVM.setBorrar(true);
                }
            });

            cargarProductos();
            obtenerFamilias();

            if (conf.getPreventa() == 1) {
                creaSugeridoPrev();
            } else {
                recuperarSugerido();
            }

            sugeridoVM.setDtProd(dtProd);

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug2), e.getMessage());
        }

        return view;
    }

    private void buscar()
    {
        try
        {
            String sku = et_sku.getText().toString();
            String can = et_cant.getText().toString();

            if (!sku.isEmpty()) {
                strBuscar[0] = sku;
                strBuscar[1] = can;

                et_sku.setText("");
                et_cant.setText("");

                fragmentMain.goSugerido();

                sugeridoVM.setStrBuscar(strBuscar);
            } else
                Toast.makeText(getContext(), getString(R.string.tt_camposVacios), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_buscarInfo), e.getMessage());
        }
    }

    private void cargarProductos()
    {
        try{

            consulta= "select p.prod_cve_n,p.prod_sku_str,p.prod_desc_str,p.id_envase_n, " +
                    "f.fam_desc_str,pr.pres_desc_str,c.cat_desc_str from productos p " +
                    "inner join familias f on p.fam_cve_n=f.fam_cve_n " +
                    "inner join categorias c on p.cat_cve_n=c.cat_cve_n " +
                    "inner join presentaciones pr on pr.pres_cve_n=p.pres_cve_n";

            json = BaseLocal.Select(consulta,getContext());
            dtProd = ConvertirRespuesta.getProductos_SugJson(json);

        }
        catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error al cargar productos", Toast.LENGTH_SHORT).show();
        }

    }

    private void obtenerFamilias()
    {
        if(dtProd!=null)
        {
            lvFamilias= new ArrayList<>();
            String fam;
            TableRow tr;

            for(int i=0; i< dtProd.size();i++)
            {
                if(!lvFamilias.contains( dtProd.get(i).getFam_desc_str() ))
                {
                    fam= dtProd.get(i).getFam_desc_str();

                    lvFamilias.add(fam);

                    tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido1, null);
                    ((TextView) tr.findViewById(R.id.t_titulo)).setText( fam );
                    tr.setTag(fam);
                    tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
                    tableLayout.addView(tr);
                }
            }
        }
    }

    private void creaSugeridoPrev()
    {
        ArrayList<DataTableLC.SugPreventa> dts = null;

        json = BaseLocal.Select("select prod_cve_n,sum(prod_cant_n) prod_sug_n from preventadet group by prod_cve_n",getContext());
        dts = ConvertirRespuesta.getSugPreventaJson(json);

        if(dts!=null)
        {
            for(int i=0; i<dts.size();i++)
            {
                //para hacer la consulta
                DataTableLC.Productos_Sug dri=null;
                for(int j=0; j < dtProd.size();j++)
                {
                    if(dtProd.get(j).getProd_cve_n().equals(dts.get(i).getProd_cve_n()))
                    {
                         dri= dtProd.get(j);
                         j = dtProd.size();
                    }
                }

                if(dri!=null)
                {
                    DataTableLC.Sugerido ri = new DataTableLC.Sugerido();

                    ri.setProd_cve_n( dri.getProd_cve_n() );
                    ri.setProd_sku_str( dri.getProd_sku_str() );
                    ri.setProd_desc_str( dri.getProd_desc_str() );
                    ri.setId_envase_n( dri.getId_envase_n() );
                    ri.setProd_sug_n(  dts.get(i).getProd_sug_n()  );
                    ri.setCat_desc_str( dri.getCat_desc_str() );

                    dgSugerido.add(ri);
                }
            }

            calcularEnvase();
            sugeridoVM.setDgSugerido( dgSugerido );
            fragmentMain.goSugerido();
        }
    }

    private void calcularEnvase()
    {
        for(int i=0; i< dtProd.size(); i++)
        {
            if(dtProd.get(i).getCat_desc_str().equals("ENVASE"))
            {

                int k=-1;
                int o=0;
                DataTableLC.Sugerido e=null;
                for(int j=0; j<dgSugerido.size();j++)
                {
                    if(dgSugerido.get(j).getProd_cve_n().equals(dtProd.get(i).getProd_cve_n()))
                    {
                        e = dgSugerido.get(j);
                        k=j;
                    }

                    if(dgSugerido.get(j).getId_envase_n().equals(  dtProd.get(i).getProd_cve_n()  ))
                    {
                        o = o + Integer.parseInt(  dgSugerido.get(j).getProd_sug_n()  );
                    }

                }

                if(e!=null)
                {

                }
                else
                {
                    DataTableLC.Sugerido ri = new DataTableLC.Sugerido();
                    ri.setProd_cve_n( dtProd.get(i).getProd_cve_n() );
                    ri.setProd_sku_str( dtProd.get(i).getProd_sku_str() );
                    ri.setProd_desc_str( dtProd.get(i).getProd_desc_str()  );
                    ri.setId_envase_n( dtProd.get(i).getId_envase_n() );
                    ri.setProd_sug_n("0");
                    ri.setCat_desc_str( dtProd.get(i).getCat_desc_str() );
                    dgSugerido.add(ri);

                    k = dgSugerido.size()-1;

                }
                dgSugerido.get(k).setProd_sug_n( String.valueOf(o) );
            }
        }
    }

    private void recuperarSugerido()
    {
        try
        {
            ArrayList<DataTableLC.SugeridoTable> dts = null;

            json = BaseLocal.Select("select * from sugerido", getContext());
            dts = ConvertirRespuesta.getSugeridoTableJson( json );

            if(dts!=null)
            {
                for(int i=0; i<dts.size();i++)
                {
                    DataTableLC.SugeridoTable r = dts.get(i);
                    DataTableLC.Productos_Sug i2 = null;

                    for(int j=0; j<dtProd.size();j++)
                    {
                        if(dtProd.get(j).getProd_cve_n().equals( r.getProd_cve_n() ) )
                        {
                            i2 = dtProd.get(j);
                            j= dtProd.size();
                        }
                    }

                    if(i2!=null)
                    {
                        DataTableLC.Sugerido ri = new DataTableLC.Sugerido();

                        ri.setProd_cve_n( i2.getProd_cve_n() );
                        ri.setProd_sku_str( i2.getProd_sku_str() );
                        ri.setProd_desc_str( i2.getProd_desc_str() );
                        ri.setId_envase_n( i2.getId_envase_n() );
                        ri.setProd_sug_n(  r.getProd_sug_n()  );
                        ri.setCat_desc_str( i2.getCat_desc_str() );

                        dgSugerido.add(ri);
                    }
                }

                calcularEnvase();
                sugeridoVM.setDgSugerido( dgSugerido );
                fragmentMain.goSugerido();
                Log.d("salida","sugerido creado con exito");
            }

        }
        catch (Exception e)
        {
            Log.d("salida","Error cargar sugerido: "+e.getMessage());
            Toast.makeText(getContext(), "Error al cargar sugerido", Toast.LENGTH_SHORT).show();
        }
    }

    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            String familia = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            //si no coincide, pinta todas de blanco
            for(int i=0; i<lvFamilias.size();i++)
            {
                TableRow row = (TableRow)vista.findViewWithTag(lvFamilias.get(i));
                row.setBackgroundColor( getResources().getColor(R.color.bgDefault) );
            }

            //pinta de azul la fila y actualiza la cve de la fila seccionada
            tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );

            sugeridoVM.setFamilia(familia);
            fragmentMain.goPresentacion();
        }
    };




}
