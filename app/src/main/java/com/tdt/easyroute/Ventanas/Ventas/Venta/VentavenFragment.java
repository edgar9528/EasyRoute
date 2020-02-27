package com.tdt.easyroute.Ventanas.Ventas.Venta;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;

import com.tdt.easyroute.CardViews.Adapter.VentaAdapterRecyclerView;
import com.tdt.easyroute.CardViews.Model.VentaCardView;
import com.tdt.easyroute.CardViews.SwipeController;
import com.tdt.easyroute.CardViews.SwipeControllerActions;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.ViewModel.PedidosVM;

import java.util.ArrayList;


public class VentavenFragment extends Fragment {

    private PedidosVM pedidosVM;

    private View vista;
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
        vista=view;
        et_total = view.findViewById(R.id.et_total);


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
        DataTableLC.ProductosPed re = null;

        re = BuscarProducto(cve_producto, dgProd2);
        if(re!=null)
        {
            int indice = dgProd2.indexOf(re);
            ingresarCantidad(indice,re);
        }
        else
        {
            re= BuscarProducto(cve_producto, dtProductos);
            if(re!=null)
            {
                ingresarCantidad(-1,re);
            }
        }
    }

    private DataTableLC.ProductosPed BuscarProducto(String cve, ArrayList<DataTableLC.ProductosPed> arrayList)
    {
        DataTableLC.ProductosPed producto = null;
        for(int i=0; i<arrayList.size();i++)
        {
            if(cve.equals( arrayList.get(i).getProd_cve_n() ))
            {
                producto= arrayList.get(i);
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
                    .setNegativeButton(R.string.bt_cancelar, null)
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
            dgProd2.get(indice).setProd_cant_n(lectura);
        }
        else
        {
            prod.setProd_cant_n(lectura);
            dgProd2.add(prod);
            pedidosVM.setDgPro2(dgProd2);
        }
        mostrarProductos();

    }

    private void mostrarProductos()
    {
        if (dgProd2 != null)
        {


            final RecyclerView ventasRecyclerView = vista.findViewById(R.id.ventasRecycler);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            ventasRecyclerView.setLayoutManager(linearLayoutManager);
            ArrayList<VentaCardView> ventasCardViews = new ArrayList<>();


            SwipeController swipeController = new SwipeController();
            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
            itemTouchhelper.attachToRecyclerView(ventasRecyclerView);

            double total =0;

            for (DataTableLC.ProductosPed p: dgProd2)
            {
                double subtotal = Double.parseDouble(p.getLpre_precio_n())* Double.parseDouble(p.getProd_cant_n());
                ventasCardViews.add(new VentaCardView(
                        p.getProd_cve_n(),
                        p.getProd_sku_str(),
                        p.getProd_desc_str(),
                        "$"+Utils.numFormatStr( p.getLpre_precio_n() ) ,
                        p.getProd_cant_n(),
                        "$"+Utils.numFormat(subtotal),
                        p.getProd_cantiv_n()));

                total+= subtotal;
            }

            ventaAdapterRecyclerView = new VentaAdapterRecyclerView(ventasCardViews, R.layout.cardview_venta);
            ventasRecyclerView.setAdapter(ventaAdapterRecyclerView);
            et_total.setText("$"+ Utils.numFormat(total));

        }
    }



}
