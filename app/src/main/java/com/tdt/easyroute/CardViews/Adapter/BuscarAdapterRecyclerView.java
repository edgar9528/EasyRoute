package com.tdt.easyroute.CardViews.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tdt.easyroute.CardViews.Model.BuscarCardView;
import com.tdt.easyroute.Ventanas.Clientes.BuscarClientesActivity;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class BuscarAdapterRecyclerView extends RecyclerView.Adapter<BuscarAdapterRecyclerView.BuscarViewHolder> {

    private ArrayList<BuscarCardView> buscarCardViews;
    private int resource;
    private BuscarClientesActivity buscarClientesActivity;


    public BuscarAdapterRecyclerView(ArrayList<BuscarCardView> buscarCardViews, int resource, BuscarClientesActivity buscarClientesActivity) {
        this.buscarCardViews = buscarCardViews;
        this.resource = resource;
        this.buscarClientesActivity = buscarClientesActivity;
    }

    @NonNull
    @Override
    public BuscarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource,viewGroup,false);
        return new BuscarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscarViewHolder buscarViewHolder, int i)
    {
        final BuscarCardView buscarCardView = buscarCardViews.get(i);

        buscarViewHolder.tv_idExt.setText( buscarCardView.getIdExt() );
        buscarViewHolder.tv_negocio.setText( buscarCardView.getNegocio() );
        buscarViewHolder.tv_razon.setText( buscarCardView.getRazonSocial() );

        buscarViewHolder.ib_selec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarClientesActivity.seleccionar(buscarCardView.getIndice());
            }
        });

        buscarViewHolder.ib_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarClientesActivity.actualizaCliente(buscarCardView.getIndice());
            }
        });

        buscarViewHolder.ib_imp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarClientesActivity.imprimir(buscarCardView.getIndice());
            }
        });

    }

    @Override
    public int getItemCount() {
        return buscarCardViews.size();
    }

    public class BuscarViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_idExt,tv_negocio,tv_razon;
        private ImageButton ib_act, ib_imp, ib_selec;

        public BuscarViewHolder(@NonNull View itemView) {
            super(itemView);

             tv_idExt = itemView.findViewById(R.id.cv_idext);
             tv_negocio= itemView.findViewById(R.id.cv_negocio);
             tv_razon= itemView.findViewById(R.id.cv_razonSoc);
             ib_act = itemView.findViewById(R.id.cv_btAct);
             ib_imp = itemView.findViewById(R.id.cv_btImp);
             ib_selec = itemView.findViewById(R.id.cv_btSelec);
            
        }
    }
}
