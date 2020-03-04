package com.tdt.easyroute.CardViews.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.tdt.easyroute.CardViews.Model.PagoCardView;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class PagosAdapterRecyclerView extends RecyclerView.Adapter<PagosAdapterRecyclerView.PagosViewHolder> {

    private ArrayList<PagoCardView> pagoCardViews;
    private int resource;

    public PagosAdapterRecyclerView( ArrayList<PagoCardView> pagoCardViews, int resource) {
        this.pagoCardViews = pagoCardViews;
        this.resource = resource;
    }

    @NonNull
    @Override
    public PagosAdapterRecyclerView.PagosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);



        return new PagosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PagosAdapterRecyclerView.PagosViewHolder ventaViewHolder, int position) {
        final PagoCardView pagoCardView = pagoCardViews.get(position);

        ventaViewHolder.tv_numero.setText( pagoCardView.getNumero() );
        ventaViewHolder.tv_idPago.setText( pagoCardView.getIdPago() );
        ventaViewHolder.tv_forma.setText(pagoCardView.getForma());
        ventaViewHolder.tv_cantidad.setText(pagoCardView.getCantidad());
        ventaViewHolder.tv_banco.setText(pagoCardView.getBanco());
        ventaViewHolder.tv_referencia.setText(pagoCardView.getReferencia());

        ventaViewHolder.linearLayout.setVisibility( View.GONE );

        ventaViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(ventaViewHolder, v);
            }
        });

        ventaViewHolder.ib_icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(ventaViewHolder,v);
            }
        });

    }

    private void click(PagosAdapterRecyclerView.PagosViewHolder ventaViewHolder, View v)
    {
        if (ventaViewHolder.linearLayout.getVisibility() == View.VISIBLE)
        {
            ventaViewHolder.linearLayout.setVisibility( View.GONE );
            ventaViewHolder.ib_icono.setImageResource(R.drawable.ic_expand_more_black_36dp);
        }
        else
        {
            ventaViewHolder.linearLayout.setVisibility( View.VISIBLE );
            ventaViewHolder.ib_icono.setImageResource(R.drawable.ic_expand_less_black_36dp);
        }
    }

    @Override
    public int getItemCount() {
        return pagoCardViews.size();
    }

    public class PagosViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private LinearLayout linearLayout;
        private ImageButton ib_icono;

        private TextView tv_numero;
        private TextView tv_cantidad;
        private TextView tv_forma;

        private TextView tv_idPago;
        private TextView tv_banco;
        private TextView tv_referencia;

        public PagosViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            linearLayout = itemView.findViewById(R.id.linearOcultar);
            ib_icono = itemView.findViewById(R.id.ib_icono);

            tv_numero = itemView.findViewById(R.id.tv_numero);
            tv_cantidad = itemView.findViewById(R.id.tv_cantidad);
            tv_forma = itemView.findViewById(R.id.tv_formaPago);

            tv_idPago = itemView.findViewById(R.id.tv_idPago);
            tv_banco = itemView.findViewById(R.id.tv_banco);
            tv_referencia = itemView.findViewById(R.id.tv_referencia);

        }
    }

    public void eliminarItem(int indice)
    {
        pagoCardViews.remove(indice);
        notifyItemRemoved(indice);
    }

    public void agregarItem(PagoCardView pagoCardView)
    {
        pagoCardViews.add(getItemCount(),pagoCardView);
        notifyItemInserted(getItemCount());
    }


}
