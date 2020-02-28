package com.tdt.easyroute.Ventanas.Ventas.Venta;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.tdt.easyroute.CardViews.Adapter.VentaAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Model.VentaCardView;
import com.tdt.easyroute.CardViews.SwipeController;
import com.tdt.easyroute.CardViews.SwipeControllerActions;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;
import java.util.Collections;


public class VentavenFragment extends Fragment {

    private PedidosVM pedidosVM;

    private VentamainFragment ventamainFragment;

    private ArrayList<DataTableLC.ProductosPed> dtProductos;
    private ArrayList<DataTableLC.ProductosPed> dgProd2;

    private VentaAdapterRecyclerView ventaAdapterRecyclerView;

    private EditText et_total;

    public VentavenFragment() {
        // Required empty public constructor
    }

    public static VentavenFragment newInstance(String param1, String param2) {
        VentavenFragment fragment = new VentavenFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pedidosVM = ViewModelProviders.of(  getActivity()).get(PedidosVM.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ventaven, container, false);
        ventamainFragment = (VentamainFragment) getParentFragment();
        et_total = view.findViewById(R.id.et_total);


        RecyclerView ventasRecyclerView = view.findViewById(R.id.ventasRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ventasRecyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<DataTableLC.ProductosPed> ventasCardViews = new ArrayList<>();

        ventaAdapterRecyclerView = new VentaAdapterRecyclerView(ventasCardViews, R.layout.cardview_venta,this);
        ventasRecyclerView.setAdapter(ventaAdapterRecyclerView);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                ventaAdapterRecyclerView.eliminarItem(position);
            }
            @Override
            public void onLeftClicked(int position)
            {
                ingresarCantidad(position,dgProd2.get(position));
            }
        },getActivity());

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(ventasRecyclerView);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosVM.getDtProductos().observe(getActivity(), new Observer<ArrayList<DataTableLC.ProductosPed>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.ProductosPed> productosPeds) {
                dtProductos = productosPeds;
            }
        });

        pedidosVM.getDgPro2().observe(getActivity(), new Observer<ArrayList<DataTableLC.ProductosPed>>() {
            @Override
            public void onChanged(ArrayList<DataTableLC.ProductosPed> DgProd2) {
                dgProd2 = DgProd2;
            }
        });

        pedidosVM.getProducto().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String cve_prod) {
                AgregarProducto(cve_prod);
            }
        });
    }


    private void AgregarProducto(String cve_producto)
    {
        ArrayList<DataTableLC.ProductosPed> re;

        re = BuscarProducto(cve_producto, dgProd2);
        if(re.size()>0)
        {
            int indice = dgProd2.indexOf(re.get(0));
            ingresarCantidad(indice,re.get(0));
        }
        else
        {
            re= BuscarProducto(cve_producto, dtProductos);
            if(re.size()>0)
            {
                ingresarCantidad(-1,re.get(0));
            }
        }
    }

    private ArrayList<DataTableLC.ProductosPed> BuscarProducto(String cve, ArrayList<DataTableLC.ProductosPed> arrayList)
    {
        ArrayList<DataTableLC.ProductosPed> producto= new ArrayList<>();

        for(int i=0; i<arrayList.size();i++)
        {
            if(cve.equals( arrayList.get(i).getProd_cve_n() ))
            {
                producto.add( arrayList.get(i) ) ;
                i=arrayList.size();
            }
        }
        return producto;
    }

    private void ingresarCantidad(final int indice, final DataTableLC.ProductosPed prod)
    {
        try
        {
            String msg = getResources().getString(R.string.msg_ventaCan) + " " + prod.getProd_cantiv_n();

            LayoutInflater layoutInflater = requireActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.dialog_sugerido, null);
            final EditText editText = (EditText) view.findViewById(R.id.ti_sugerido);

            final AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle(prod.getProd_desc_str())
                    .setMessage(msg)
                    .setView(view)
                    .setPositiveButton(R.string.bt_aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String lectura = String.valueOf(editText.getText());
                            actualizarProductos(indice,lectura,prod);
                        }
                    })
                    .setNegativeButton(R.string.bt_cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(indice!=-1)
                                ventaAdapterRecyclerView.actualizarItem(indice,null);
                        }
                    })
                    .create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            dialog.show();

            editText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        String lectura = String.valueOf(editText.getText());
                        actualizarProductos(indice,lectura,prod);
                        if (dialog.isShowing())
                            dialog.dismiss();
                        return true;
                    }
                    return false;
                }
            });

        }catch (Exception e)
        {
            Utils.msgError(getContext(), getString(R.string.err_sug11), e.getMessage());
        }
    }

    private void actualizarProductos(int indice,String lectura, final DataTableLC.ProductosPed prod)
    {
        if(lectura==null || lectura.isEmpty())
            lectura="0";

        if (indice != -1)
        {
            ventaAdapterRecyclerView.actualizarItem(indice,lectura);
        }
        else
        {
            String precio = prod.getLpre_precio_n().replace("$","");

            double subtotal = Double.parseDouble(precio)* Double.parseDouble(lectura);

            prod.setProd_cant_n(lectura);
            prod.setSubtotal( "$"+Utils.numFormat(subtotal) );
            prod.setLpre_precio_n( "$"+ Utils.numFormatStr(precio));

            ventaAdapterRecyclerView.agregarItem(prod);
        }
    }

    public void actualizarLista(ArrayList<DataTableLC.ProductosPed> productos)
    {
       dgProd2 =  productos;
       pedidosVM.setDgPro2(dgProd2);


        double subtotal,total =0;
        for (DataTableLC.ProductosPed p: dgProd2)
        {
            subtotal = Double.parseDouble(p.getLpre_precio_n().replace("$",""))* Double.parseDouble(p.getProd_cant_n());
            total+= subtotal;
        }
        et_total.setText("$"+ Utils.numFormat(total));

        Log.d("salida","Tamaño de dgprod: "+dgProd2.size());
        Log.d("salida","Tamaño de dtproduc: "+dtProductos.size());

    }

}
