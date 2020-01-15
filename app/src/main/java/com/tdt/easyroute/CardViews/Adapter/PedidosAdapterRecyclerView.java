package com.tdt.easyroute.CardViews.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tdt.easyroute.CardViews.Model.PedidosCardView;
import com.tdt.easyroute.Fragments.Pedidos.Pedidos2Fragment;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class PedidosAdapterRecyclerView extends RecyclerView.Adapter<PedidosAdapterRecyclerView.PedidosViewHolder>
{

    private ArrayList<PedidosCardView> pedidosCardViews;
    private int resource;
    private Pedidos2Fragment pedidos2Fragment;


    public PedidosAdapterRecyclerView(ArrayList<PedidosCardView> pedidosCardViews, int resource, Pedidos2Fragment pedidos2Fragment) {
        this.pedidosCardViews = pedidosCardViews;
        this.resource = resource;
        this.pedidos2Fragment = pedidos2Fragment;
    }

    @NonNull
    @Override
    public PedidosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource,viewGroup,false);

        return new PedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosViewHolder pedidosViewHolder, int i) {
        final PedidosCardView pedidosCardView = pedidosCardViews.get(i);

        pedidosViewHolder.et_clave.setText( pedidosCardView.getClave() );
        pedidosViewHolder.et_nombre.setText( pedidosCardView.getNombre() );

        pedidosViewHolder.b_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidos2Fragment.actualizaCliente(pedidosCardView.getCliente().getCli_cve_n());
            }
        });

        pedidosViewHolder.b_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidos2Fragment.abrirDetalles(pedidosCardView.getCliente().getCli_cve_n());
            }
        });

        pedidosViewHolder.b_noVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidos2Fragment.noVisita(pedidosCardView.getCliente());
            }
        });

        pedidosViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidos2Fragment.mensaje();
            }
        });


    }

    @Override
    public int getItemCount() {
        return pedidosCardViews.size();
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder{

        private TextView  et_clave;
        private TextView  et_nombre;
        private ImageButton  b_actualizar;
        private ImageButton  b_detalles;
        private ImageButton  b_noVenta;
        private ConstraintLayout constraintLayout;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);

            et_clave= itemView.findViewById(R.id.cv_pedidos_clave);
            et_nombre= itemView.findViewById(R.id.cv_pedidos_nombre);
            b_actualizar = itemView.findViewById(R.id.cv_bActualizar);
            b_detalles = itemView.findViewById(R.id.cv_bDetalles);
            b_noVenta = itemView.findViewById(R.id.cv_bNoVenta);
            constraintLayout = itemView.findViewById(R.id.constraint);

        }
    }



}
