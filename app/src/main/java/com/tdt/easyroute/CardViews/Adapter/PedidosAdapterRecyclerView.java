package com.tdt.easyroute.CardViews.Adapter;

import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.RecyclerView;

import com.tdt.easyroute.CardViews.Model.PedidosCardView;
import com.tdt.easyroute.Ventanas.Pedidos.ClientesPedFragment;
import com.tdt.easyroute.R;

import java.util.ArrayList;
import java.util.Collections;

public class PedidosAdapterRecyclerView extends RecyclerView.Adapter<PedidosAdapterRecyclerView.PedidosViewHolder>
{
    private ArrayList<PedidosCardView> pedidosCardViews;
    private int resource;
    private ClientesPedFragment clientesPedFragment;

    public PedidosAdapterRecyclerView(ArrayList<PedidosCardView> pedidosCardViews, int resource, ClientesPedFragment clientesPedFragment)
    {
        this.pedidosCardViews = pedidosCardViews;
        this.resource = resource;
        this.clientesPedFragment = clientesPedFragment;
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
        final int indice =i;

        pedidosViewHolder.iv_icono.setImageDrawable( pedidosCardView.getIcono() );
        pedidosViewHolder.et_clave.setText( pedidosCardView.getClave() );
        pedidosViewHolder.et_nombre.setText( pedidosCardView.getNombre() );

        pedidosViewHolder.b_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientesPedFragment.actualizaCliente(pedidosCardView.getCliente().getCli_cve_n());
            }
        });

        pedidosViewHolder.b_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientesPedFragment.abrirDetalles(pedidosCardView.getCliente().getCli_cve_n());
            }
        });

        pedidosViewHolder.b_noVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientesPedFragment.noVisita(pedidosCardView.getCliente(), indice);
            }
        });

        pedidosViewHolder.b_imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientesPedFragment.imprimir(pedidosCardView.getCliente());
            }
        });

        pedidosViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientesPedFragment.seleccionar(pedidosCardView.getCliente());
            }
        });
    }

    @Override
    public int getItemCount() {
        return pedidosCardViews.size();
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_icono;
        private TextView  et_clave;
        private TextView  et_nombre;
        private ImageButton  b_actualizar;
        private ImageButton  b_detalles;
        private ImageButton  b_noVenta;
        private ImageButton  b_imp;
        private ConstraintLayout constraintLayout;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_icono = itemView.findViewById(R.id.icono_estado);
            et_clave= itemView.findViewById(R.id.cv_pedidos_clave);
            et_nombre= itemView.findViewById(R.id.cv_pedidos_nombre);
            b_actualizar = itemView.findViewById(R.id.cv_bActualizar);
            b_detalles = itemView.findViewById(R.id.cv_bDetalles);
            b_noVenta = itemView.findViewById(R.id.cv_bNoVenta);
            b_imp = itemView.findViewById(R.id.cv_bImp);
            constraintLayout = itemView.findViewById(R.id.constraint);
        }
    }

    public void actualiza(int item, int iconoNum, Drawable icono)
    {
        pedidosCardViews.get(item).getCliente().setIcono(iconoNum);
        pedidosCardViews.get(item).setIcono(icono);
        notifyItemChanged(item);
    }

}
