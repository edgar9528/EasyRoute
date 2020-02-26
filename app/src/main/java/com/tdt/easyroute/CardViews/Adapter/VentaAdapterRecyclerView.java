package com.tdt.easyroute.CardViews.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdt.easyroute.CardViews.Model.CarteraCardView;
import com.tdt.easyroute.CardViews.Model.VentaCardView;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class VentaAdapterRecyclerView extends RecyclerView.Adapter<VentaAdapterRecyclerView.VentaViewHolder> {

    private ArrayList<VentaCardView> ventaCardViews;
    private int resource;

    public VentaAdapterRecyclerView(ArrayList<VentaCardView> ventaCardViews, int resource) {
        this.ventaCardViews = ventaCardViews;
        this.resource = resource;
    }

    @NonNull
    @Override
    public VentaAdapterRecyclerView.VentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        return new VentaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VentaAdapterRecyclerView.VentaViewHolder ventaViewHolder, int position) {
        final VentaCardView ventaCardView = ventaCardViews.get(position);

        ventaViewHolder.tv_sku.setText( ventaCardView.getSku() );
        ventaViewHolder.tv_precio.setText( ventaCardView.getPrecio() );
        ventaViewHolder.tv_producto.setText( ventaCardView.getProducto() );
        ventaViewHolder.tv_cantidad.setText( ventaCardView.getCantidad() );
        ventaViewHolder.tv_subtotal.setText( ventaCardView.getSubtotal() );

    }

    @Override
    public int getItemCount() {
        return ventaCardViews.size();
    }

    public class VentaViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_sku;
        private TextView tv_precio;
        private TextView tv_producto;
        private TextView tv_cantidad;
        private TextView tv_subtotal;

        public VentaViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_sku = itemView.findViewById(R.id.tv_sku);
            tv_precio = itemView.findViewById(R.id.tv_precio);
            tv_producto = itemView.findViewById(R.id.tv_producto);
            tv_cantidad = itemView.findViewById(R.id.tv_cantidad);
            tv_subtotal = itemView.findViewById(R.id.tv_subtotal);

        }
    }
}
