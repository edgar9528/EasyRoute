package com.tdt.easyroute.Fragments.FinDeDia.Sugerido;

import android.content.Context;
import android.net.Uri;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.SugeridoVM;

import java.util.ArrayList;

public class ProductoFragment extends Fragment {

    ArrayList<DataTableLC.Productos_Sug> dtProd;
    ArrayList<DataTableLC.ProductosTable> lvProductos=null;
    String familiaSelec, presenSelec;

    String[] strBuscar =  new String[2];
    EditText et_sku,et_cant;
    Button b_buscar,b_borrar;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    View vista;
    MainsugeridoFragment fragmentMain;

    SugeridoVM sugeridoVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sugeridoVM = ViewModelProviders.of ( getParentFragment() ).get(SugeridoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_familia, container, false);

        layoutInflater = inflater;
        vista=view;

        fragmentMain = (MainsugeridoFragment) getParentFragment();
        tableLayout = view.findViewById(R.id.tableLayout);


        et_sku = view.findViewById(R.id.et_sku);
        et_cant = view.findViewById(R.id.et_cant);
        b_buscar = view.findViewById(R.id.button_buscar);
        b_borrar = view.findViewById(R.id.button_borrar);

        b_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sku = et_sku.getText().toString();
                String can= et_cant.getText().toString();

                if(!sku.isEmpty())
                {
                    strBuscar[0] = sku;
                    strBuscar[1] = can;

                    et_sku.setText("");
                    et_cant.setText("");

                    sugeridoVM.setStrBuscar( strBuscar );
                }
                else
                    Toast.makeText(getContext(), "Ingresa un sku a buscar", Toast.LENGTH_SHORT).show();
            }
        });

        b_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sugeridoVM.setBorrar(true);
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sugeridoVM.getDtProd().observe(getParentFragment(), new Observer<ArrayList<DataTableLC.Productos_Sug>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.Productos_Sug> dtprod) {
                dtProd = dtprod;
            }
        });

        sugeridoVM.getFamilia().observe(getParentFragment(), new Observer< String >() {
            @Override
            public void onChanged(String familia) {
                familiaSelec=familia;
                tableLayout.removeAllViews();
            }
        });

        sugeridoVM.getPresentacion().observe(getParentFragment(), new Observer< String >() {
            @Override
            public void onChanged(String presen) {
                presenSelec=presen;
                obtenerProductos();
            }
        });
    }


    private void obtenerProductos()
    {
        if(dtProd!=null)
        {
            lvProductos = new ArrayList<>();
            DataTableLC.ProductosTable pt;
            tableLayout.removeAllViews();
            String producto;

            for (int i = 0; i < dtProd.size(); i++)
            {
                DataTableLC.Productos_Sug r = dtProd.get(i);

                if (familiaSelec.equals( r.getFam_desc_str() ) && presenSelec.equals( r.getPres_desc_str() ) )
                {
                    pt= new DataTableLC.ProductosTable();

                    pt.setProd_cve_n( r.getProd_cve_n() );
                    pt.setProd_sku_str( r.getProd_sku_str() );
                    pt.setProd_desc_str( r.getProd_desc_str()  );
                    pt.setId_envase_n( r.getId_envase_n() );
                    pt.setProd_sug_n( "0" );

                    lvProductos.add(pt);

                    producto = r.getProd_desc_str();
                    if(producto.length()>=30)
                        producto = producto.substring(0,27)+"...";
                    else
                        for(int j=producto.length();j<30;j++) producto=producto.concat(" ");

                    TableRow tr;

                    tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido2, null);
                    ((TextView) tr.findViewById(R.id.t_sku)).setText( r.getProd_sku_str() );
                    ((TextView) tr.findViewById(R.id.t_producto)).setText( producto );
                    ((TextView) tr.findViewById(R.id.t_sugerido)).setText( "0" );
                    tr.setTag(r.getProd_sku_str());
                    tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
                    tableLayout.addView(tr);

                }
            }
        }
    }


    //evento del clic a la fila
    private View.OnClickListener tableListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            TableRow tr = ((TableRow) view);
            String productoSKU = (String) tr.getTag(); //se obtiene la fila y tag de la seleccion

            for(int i=0; i<lvProductos.size();i++)
            {
                TableRow row = (TableRow)vista.findViewWithTag(lvProductos.get(i).getProd_sku_str());
                row.setBackgroundColor( getResources().getColor(R.color.bgDefault) );
            }

            //pinta de azul la fila y actualiza la cve de la fila seccionada
            tr.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );

            sugeridoVM.setProductoSKU(productoSKU);
        }
    };


}
