package com.tdt.easyroute.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.MainActivity;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

public class PrincipalFragment extends Fragment {

    TextView tv_usuario, tv_ruta,tv_fecha,tv_hora;
    private Handler handler=null;
    private Runnable myRunnable;

    public PrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        Usuario user = mainActivity.getUsuario();

        tv_usuario = view.findViewById(R.id.tv_iniUsuario);
        tv_ruta = view.findViewById(R.id.tv_iniRuta);
        tv_fecha = view.findViewById(R.id.tv_iniFecha);
        tv_hora = view.findViewById(R.id.tv_iniHora);

        String rutaCve = Utils.LeefConfig("ruta",getContext());
        String rutaDes = BaseLocal.SelectDato(string.formatSql("select rut_desc_str from rutas where rut_cve_n={0}",rutaCve),getContext());

        if(rutaDes!=null)
            tv_ruta.setText(rutaDes);
        else
            tv_ruta.setText("SIN RUTA");

        tv_usuario.setText(user.getNombre()+" "+user.getAppat()+" "+user.getApmat());

        mostrarFechaHora();

        return view;
    }

    private void mostrarFechaHora()
    {
        tv_fecha.setText(Utils.FechaLocal());

        handler = new Handler();

        myRunnable = new Runnable() {
            @Override
            public void run() {
                tv_hora.setText(Utils.HoraLocal());
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(myRunnable);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(myRunnable);
    }

}
