package com.tdt.easyroute.CardViews.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tdt.easyroute.CardViews.Model.PedidosCardView;
import com.tdt.easyroute.Fragments.Pedidos.PedidosFragment;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class PedidosAdapterRecyclerView extends RecyclerView.Adapter<PedidosAdapterRecyclerView.PedidosViewHolder>
{

    private ArrayList<PedidosCardView> pedidosCardViews;
    private int resource;
    private PedidosFragment pedidosFragment;


    public PedidosAdapterRecyclerView(ArrayList<PedidosCardView> pedidosCardViews, int resource, PedidosFragment pedidosFragment) {
        this.pedidosCardViews = pedidosCardViews;
        this.resource = resource;
        this.pedidosFragment = pedidosFragment;
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
                pedidosFragment.actualizaCliente(pedidosCardView.getCliente().getCli_cve_n());
            }
        });

        pedidosViewHolder.b_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidosFragment.abrirDetalles(pedidosCardView.getCliente().getCli_cve_n());
            }
        });

        pedidosViewHolder.b_noVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedidosFragment.noVisita(pedidosCardView.getCliente());
            }
        });

        pedidosViewHolder.b_imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidosFragment.imprimir(pedidosCardView.getCliente());
            }
        });

        pedidosViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        private ImageButton  b_imp;
        private ConstraintLayout constraintLayout;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);

            et_clave= itemView.findViewById(R.id.cv_pedidos_clave);
            et_nombre= itemView.findViewById(R.id.cv_pedidos_nombre);
            b_actualizar = itemView.findViewById(R.id.cv_bActualizar);
            b_detalles = itemView.findViewById(R.id.cv_bDetalles);
            b_noVenta = itemView.findViewById(R.id.cv_bNoVenta);
            b_imp = itemView.findViewById(R.id.cv_bImp);
            constraintLayout = itemView.findViewById(R.id.constraint);

        }
    }



}
