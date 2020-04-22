package com.tdt.easyroute.CardViews.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Ventas.Credito.AbonoscredFragment;
import com.tdt.easyroute.Ventanas.Ventas.Pago.PagoFragment;

import java.util.ArrayList;

public class AbonosAdapterRecyclerView extends RecyclerView.Adapter<AbonosAdapterRecyclerView.PagosViewHolder> {

    private ArrayList<DataTableLC.DgPagos> pagoCardViews;
    private int resource;
    AbonoscredFragment abonosFragment;

    public AbonosAdapterRecyclerView(ArrayList<DataTableLC.DgPagos> pagoCardViews, int resource, AbonoscredFragment abonosFragment) {
        this.pagoCardViews = pagoCardViews;
        this.resource = resource;
        this.abonosFragment = abonosFragment;
    }

    @NonNull
    @Override
    public AbonosAdapterRecyclerView.PagosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);



        return new PagosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AbonosAdapterRecyclerView.PagosViewHolder ventaViewHolder, int position) {
        final DataTableLC.DgPagos pagoCardView = pagoCardViews.get(position);

        ventaViewHolder.tv_numero.setText( pagoCardView.getNoPago());
        ventaViewHolder.tv_idPago.setText( pagoCardView.getFpag_cve_n() );
        ventaViewHolder.tv_forma.setText(pagoCardView.getFpag_desc_str());
        ventaViewHolder.tv_cantidad.setText(pagoCardView.getFpag_cant_n());
        ventaViewHolder.tv_banco.setText(pagoCardView.getBancoP());
        ventaViewHolder.tv_referencia.setText(pagoCardView.getReferenciaP());

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

    private void click(AbonosAdapterRecyclerView.PagosViewHolder ventaViewHolder, View v)
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
        abonosFragment.ActualizarDgPagos(pagoCardViews);
    }

    public void actualizarItem(int item, String cantidad)
    {
        pagoCardViews.get(item).setFpag_cant_n(string.FormatoPesos(cantidad) );
        notifyItemChanged(item);
        abonosFragment.ActualizarDgPagos(pagoCardViews);
    }

    public void agregarItem(DataTableLC.DgPagos pagoCardView)
    {
        pagoCardViews.add(getItemCount(),pagoCardView);
        notifyItemInserted(getItemCount());
        abonosFragment.ActualizarDgPagos(pagoCardViews);
    }


}
