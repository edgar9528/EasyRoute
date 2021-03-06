package com.tdt.easyroute.CardViews.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Ventas.Venta.VentavenFragment;

import java.util.ArrayList;

public class VentaAdapterRecyclerView extends RecyclerView.Adapter<VentaAdapterRecyclerView.VentaViewHolder> {

    private ArrayList<DataTableLC.ProductosPed> ventaCardViews;
    private int resource;
    private VentavenFragment ventavenFragment;

    public VentaAdapterRecyclerView(ArrayList<DataTableLC.ProductosPed> ventaCardViews, int resource, VentavenFragment ventavenFragment) {
        this.ventaCardViews = ventaCardViews;
        this.resource = resource;
        this.ventavenFragment = ventavenFragment;
    }

    @NonNull
    @Override
    public VentaAdapterRecyclerView.VentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        return new VentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentaAdapterRecyclerView.VentaViewHolder ventaViewHolder, final int position) {
        final DataTableLC.ProductosPed ventaCardView = ventaCardViews.get(position);

        String producto = ventaCardView.getProd_sku_str()+" - "+ ventaCardView.getProd_desc_str();
        ventaViewHolder.tv_producto.setText( producto );
        ventaViewHolder.tv_inventario.setText(  ventaCardView.getProd_cantiv_n() );
        ventaViewHolder.tv_precio.setText( string.FormatoPesos( ventaCardView.getLpre_precio_n() )  );
        ventaViewHolder.tv_cantidad.setText( String.valueOf ( (int) Float.parseFloat( ventaCardView.getProd_cant_n() ) )    );
        ventaViewHolder.tv_subtotal.setText( string.FormatoPesos(ventaCardView.getSubtotal()) );

        ventaViewHolder.cardViewVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ventavenFragment.ingresarCantidad2(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ventaCardViews.size();
    }

    public class VentaViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_producto;
        private TextView tv_inventario;
        private TextView tv_precio;
        private TextView tv_cantidad;
        private TextView tv_subtotal;
        private CardView cardViewVenta;

        public VentaViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_producto = itemView.findViewById(R.id.tv_producto);
            tv_inventario = itemView.findViewById(R.id.tv_inventario);
            tv_precio = itemView.findViewById(R.id.tv_precio);
            tv_cantidad = itemView.findViewById(R.id.tv_cantidad);
            tv_subtotal = itemView.findViewById(R.id.tv_subtotal);
            cardViewVenta = itemView.findViewById(R.id.cardViewVenta);
        }
    }

    public void agregarItem(DataTableLC.ProductosPed producto)
    {
        ventaCardViews.add(getItemCount(),producto);
        notifyItemInserted(getItemCount());

        ventavenFragment.actualizarLista(ventaCardViews);
    }

    public void eliminarItem(int indice)
    {
        ventaCardViews.remove(indice);
        notifyItemRemoved(indice);

        ventavenFragment.actualizarLista(ventaCardViews);
    }

    public void actualizarItem(int indice, String lectura)
    {
        if(lectura!=null && !lectura.isEmpty())
        {
            ventaCardViews.get(indice).setProd_cant_n(lectura);
            double subtotal = Double.parseDouble( string.DelCaracteres( ventaCardViews.get(indice).getLpre_precio_n() ) ) * Double.parseDouble(lectura);
            ventaCardViews.get(indice).setSubtotal( String.valueOf(subtotal) );
        }
        notifyItemChanged(indice);
        ventavenFragment.actualizarLista(ventaCardViews);
    }


}
