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

import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.R;
import com.tdt.easyroute.Ventanas.Ventas.Pago.PagoFragment;

import java.util.ArrayList;

public class CreditoLiqAdapterRecyclerView extends RecyclerView.Adapter<CreditoLiqAdapterRecyclerView.PagosViewHolder> {

    private ArrayList<DataTableLC.AdeudoNormal> dgANormals;
    private int resource;

    public CreditoLiqAdapterRecyclerView(ArrayList<DataTableLC.AdeudoNormal> dgANormals, int resource) {
        this.dgANormals = dgANormals;
        this.resource = resource;
    }

    @NonNull
    @Override
    public CreditoLiqAdapterRecyclerView.PagosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);



        return new PagosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CreditoLiqAdapterRecyclerView.PagosViewHolder dgAnormalViewHolder, int position) {
        final DataTableLC.AdeudoNormal dgANormal = dgANormals.get(position);

        dgAnormalViewHolder.tv_numero.setText( dgANormal.getCred_cve_n() );
        dgAnormalViewHolder.tv_monto.setText(  string.FormatoPesos( dgANormal.getCred_monto_n() )  );
        dgAnormalViewHolder.tv_fecha.setText(Utils.FechaFormato(dgANormal.getCred_fecha_dt())  );
        dgAnormalViewHolder.tv_referencia.setText( dgANormal.getCred_referencia_str() );
        dgAnormalViewHolder.tv_abono.setText( string.FormatoPesos( dgANormal.getCred_abono_n() )  );
        dgAnormalViewHolder.tv_saldo.setText( string.FormatoPesos( dgANormal.getSaldo() )  );

        dgAnormalViewHolder.linearLayout.setVisibility( View.GONE );

        dgAnormalViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(dgAnormalViewHolder, v);
            }
        });

        dgAnormalViewHolder.ib_icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(dgAnormalViewHolder,v);
            }
        });

    }

    private void click(CreditoLiqAdapterRecyclerView.PagosViewHolder ventaViewHolder, View v)
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
        return dgANormals.size();
    }

    public class PagosViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private LinearLayout linearLayout;
        private ImageButton ib_icono;

        private TextView tv_numero;
        private TextView tv_monto;
        private TextView tv_fecha;
        private TextView tv_referencia;
        private TextView tv_abono;
        private TextView tv_saldo;

        public PagosViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            linearLayout = itemView.findViewById(R.id.linearOcultar);
            ib_icono = itemView.findViewById(R.id.ib_icono);

            tv_numero = itemView.findViewById(R.id.tv_numero);
            tv_monto = itemView.findViewById(R.id.tv_monto);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            tv_referencia = itemView.findViewById(R.id.tv_referencia);
            tv_abono = itemView.findViewById(R.id.tv_abono);
            tv_saldo = itemView.findViewById(R.id.tv_saldo);

        }
    }

    public void actualizar(ArrayList<DataTableLC.AdeudoNormal> dgANormalsNew)
    {
        dgANormals= dgANormalsNew;
        notifyDataSetChanged();
    }

}
