package com.tdt.easyroute.CardViews.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdt.easyroute.CardViews.Model.PedidosCardView;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class PedidosAdapterRecyclerView extends RecyclerView.Adapter<PedidosAdapterRecyclerView.PedidosViewHolder>
{

    private ArrayList<PedidosCardView> pedidosCardViews;
    private int resource;
    private Activity activity;


    public PedidosAdapterRecyclerView(ArrayList<PedidosCardView> pedidosCardViews, int resource, Activity activity) {
        this.pedidosCardViews = pedidosCardViews;
        this.resource = resource;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PedidosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource,viewGroup,false);

        return new PedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosViewHolder pedidosViewHolder, int i) {
        PedidosCardView pedidosCardView = pedidosCardViews.get(i);

        pedidosViewHolder.clave.setText( pedidosCardView.getClave() );
        pedidosViewHolder.nombre.setText( pedidosCardView.getNombre() );



        /*
        peliculasViewHolder.tituloCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,DetallePeliculaActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("idUsuarioRecibido", id_usuarioEnviar);
                activity.startActivity(intent);
            }
        });

        peliculasViewHolder.tituloCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,DetallePeliculaActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("idUsuarioRecibido", id_usuarioEnviar);
                activity.startActivity(intent);
            }
        });

        peliculasViewHolder.tituloCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,DetallePeliculaActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("idUsuarioRecibido", id_usuarioEnviar);
                activity.startActivity(intent);
            }
        });
         */
    }

    @Override
    public int getItemCount() {
        return pedidosCardViews.size();
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder{

        private TextView  clave;
        private TextView  nombre;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);

            clave= itemView.findViewById(R.id.cv_pedidos_clave);
            nombre= itemView.findViewById(R.id.cv_pedidos_nombre);

        }
    }



}
