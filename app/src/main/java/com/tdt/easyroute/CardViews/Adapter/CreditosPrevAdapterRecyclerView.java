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
import com.tdt.easyroute.Ventanas.Preventa.Pago.CreditosFragment;

import java.util.ArrayList;

public class CreditosPrevAdapterRecyclerView extends RecyclerView.Adapter<CreditosPrevAdapterRecyclerView.PagosViewHolder> {

    private ArrayList<DataTableLC.Creditos> pagoCardViews;
    private int resource;
    private CreditosFragment creditosFragment;

    public CreditosPrevAdapterRecyclerView(ArrayList<DataTableLC.Creditos> pagoCardViews, int resource, CreditosFragment creditosFragment) {
        this.pagoCardViews = pagoCardViews;
        this.resource = resource;
        this.creditosFragment = creditosFragment;
    }

    @NonNull
    @Override
    public CreditosPrevAdapterRecyclerView.PagosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        return new PagosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CreditosPrevAdapterRecyclerView.PagosViewHolder ventaViewHolder, int position) {
        final DataTableLC.Creditos pagoCardView = pagoCardViews.get(position);

        ventaViewHolder.tv_saldo.setText( pagoCardView.getCred_referencia_str()  );
        String s = ventaViewHolder.tv_fecha.getText() + ": "+ string.FormatoPesos( pagoCardView.getCred_saldo_n() );
        ventaViewHolder.tv_fecha.setText(   s );
        ventaViewHolder.tv_referencia.setText(pagoCardView.getCred_fecha_dt());
        ventaViewHolder.tv_abono.setText( string.FormatoPesos( pagoCardView.getCred_abono_n() ) );
        ventaViewHolder.tv_monto.setText( string.FormatoPesos( pagoCardView.getCred_monto_n() ) );

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

    private void click(CreditosPrevAdapterRecyclerView.PagosViewHolder ventaViewHolder, View v)
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

        private TextView tv_saldo;
        private TextView tv_fecha;
        private TextView tv_referencia;

        private TextView tv_abono;
        private TextView tv_monto;

        private TextView tv_referencia_title;
        private TextView tv_abono_title;
        private TextView tv_monto_title;

        public PagosViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            linearLayout = itemView.findViewById(R.id.linearOcultar);
            ib_icono = itemView.findViewById(R.id.ib_icono);

            tv_saldo = itemView.findViewById(R.id.tv_saldo);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);

            tv_referencia = itemView.findViewById(R.id.tv_referencia);
            tv_abono = itemView.findViewById(R.id.tv_abono);
            tv_monto = itemView.findViewById(R.id.tv_monto);

            tv_referencia_title = itemView.findViewById(R.id.tv_referenciaTitle);
            tv_abono_title = itemView.findViewById(R.id.tv_abonoTitle);
            tv_monto_title = itemView.findViewById(R.id.tv_montoTitle);

            tv_referencia_title.setText(R.string.tv_fecha);
            tv_fecha.setText( R.string.tv_saldo );

        }
    }

    public void agregarItems( DataTableLC.Creditos  pagoCardView)
    {
        pagoCardViews.add(getItemCount(),pagoCardView);
        notifyItemInserted(getItemCount());
    }

}
