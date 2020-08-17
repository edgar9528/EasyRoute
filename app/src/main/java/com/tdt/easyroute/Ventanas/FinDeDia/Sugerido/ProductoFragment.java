package com.tdt.easyroute.Ventanas.FinDeDia.Sugerido;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.SugeridoVM;

import java.util.ArrayList;

public class ProductoFragment extends Fragment {

    private ArrayList<DataTableLC.Productos_Sug> dtProd;
    private ArrayList<DataTableLC.ProductosTable> lvProductos=null;
    private String familiaSelec, presenSelec;

    private String[] strBuscar =  new String[2];
    private EditText et_sku,et_cant;

    private TableLayout tableLayout;
    private LayoutInflater layoutInflater;
    private View vista;
    private MainsugeridoFragment fragmentMain;

    private SugeridoVM sugeridoVM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sugeridoVM = ViewModelProviders.of ( getParentFragment() ).get(SugeridoVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_familia, container, false);

        try
        {
            layoutInflater = inflater;
            vista = view;

            Button b_buscar, b_borrar;

            fragmentMain = (MainsugeridoFragment) getParentFragment();
            tableLayout = view.findViewById(R.id.tableLayout);

            et_sku = view.findViewById(R.id.et_sku);
            et_cant = view.findViewById(R.id.et_cant);
            b_buscar = view.findViewById(R.id.button_buscar);
            b_borrar = view.findViewById(R.id.button_borrar);

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

            b_borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sugeridoVM.setBorrar(true);
                }
            });

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug9), e.getMessage() );
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
                Toast.makeText(getContext(), getString(R.string.tt_camposVacios) , Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_buscarInfo), e.getMessage() );
        }
    }


    private void obtenerProductos()
    {
        try
        {
            if (dtProd != null) {
                lvProductos = new ArrayList<>();
                DataTableLC.ProductosTable pt;
                tableLayout.removeAllViews();
                String producto;

                for (int i = 0; i < dtProd.size(); i++) {
                    DataTableLC.Productos_Sug r = dtProd.get(i);

                    if (familiaSelec.equals(r.getFam_desc_str()) && presenSelec.equals(r.getPres_desc_str())) {
                        pt = new DataTableLC.ProductosTable();

                        pt.setProd_cve_n(r.getProd_cve_n());
                        pt.setProd_sku_str(r.getProd_sku_str());
                        pt.setProd_desc_str(r.getProd_desc_str());
                        pt.setId_envase_n(r.getId_envase_n());
                        pt.setProd_sug_n("0");

                        lvProductos.add(pt);

                        producto = r.getProd_desc_str();
                        if (producto.length() >= 30)
                            producto = producto.substring(0, 27) + "...";
                        else
                            for (int j = producto.length(); j < 30; j++)
                                producto = producto.concat(" ");

                        TableRow tr;

                        tr = (TableRow) layoutInflater.inflate(R.layout.tabla_sugerido2, null);
                        ((TextView) tr.findViewById(R.id.t_sku)).setText(r.getProd_sku_str());
                        ((TextView) tr.findViewById(R.id.t_producto)).setText(producto);
                        ((TextView) tr.findViewById(R.id.t_sugerido)).setText("0");
                        tr.setTag(r.getProd_sku_str());
                        tr.setOnClickListener(tableListener); //evento cuando se da clic a la fila
                        tableLayout.addView(tr);

                    }
                }
            }
        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.error_cargarProduc), e.getMessage() );
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

            fragmentMain.goSugerido();
            sugeridoVM.setProductoSKU(productoSKU);
        }
    };


}
