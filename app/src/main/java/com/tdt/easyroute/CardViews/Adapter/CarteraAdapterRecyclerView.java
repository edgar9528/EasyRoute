package com.tdt.easyroute.CardViews.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tdt.easyroute.CardViews.Model.CarteraCardView;
import com.tdt.easyroute.R;
import java.util.ArrayList;

public class CarteraAdapterRecyclerView extends RecyclerView.Adapter<CarteraAdapterRecyclerView.CarteraViewHolder> {

    private ArrayList<CarteraCardView> carteraCardViews;
    private int resource;

    public CarteraAdapterRecyclerView(ArrayList<CarteraCardView> carteraCardViews, int resource) {
        this.carteraCardViews = carteraCardViews;
        this.resource = resource;
    }

    @NonNull
    @Override
    public CarteraAdapterRecyclerView.CarteraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        return new CarteraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarteraAdapterRecyclerView.CarteraViewHolder carteraViewHolder, int position) {
        final CarteraCardView carteraCardView = carteraCardViews.get(position);

        carteraViewHolder.et_credito.setText( carteraCardView.getCredito()  );
        carteraViewHolder.et_monto.setText( carteraCardView.getMonto()  );
        carteraViewHolder.et_abono.setText( carteraCardView.getAbono()  );
        carteraViewHolder.et_saldo.setText( carteraCardView.getSaldo()  );

    }

    @Override
    public int getItemCount() {
        return carteraCardViews.size();
    }

    public class CarteraViewHolder extends RecyclerView.ViewHolder {

        private TextView et_credito;
        private TextView et_monto;
        private TextView et_abono;
        private TextView et_saldo;

        public CarteraViewHolder(@NonNull View itemView) {
            super(itemView);

            et_credito = itemView.findViewById(R.id.cv_credito);
            et_monto = itemView.findViewById(R.id.cv_monto);
            et_abono = itemView.findViewById(R.id.cv_abono);
            et_saldo = itemView.findViewById(R.id.cv_saldo);

        }
    }
}
