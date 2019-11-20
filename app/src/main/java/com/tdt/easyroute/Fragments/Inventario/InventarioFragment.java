package com.tdt.easyroute.Fragments.Inventario;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Querys;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Clases.string;
import com.tdt.easyroute.Model.DataTableLC;
import com.tdt.easyroute.Model.Usuario;
import com.tdt.easyroute.R;

import java.util.ArrayList;

public class InventarioFragment extends Fragment {

    private static Usuario user;
    private static boolean descarga;

    private Button button_finalizar,button_salir;

    private Configuracion conf=null;

    private ArrayList<DataTableLC.EnvaseAut> al_envaseAut;
    private ArrayList<DataTableLC.InvP> al_invP;
    private ArrayList<DataTableLC.Inv> al_inv;

    public InventarioFragment() {
        // Required empty public constructor
    }

    public static InventarioFragment newInstance(Usuario u, boolean desc) {
        InventarioFragment fragment = new InventarioFragment();
        Bundle args = new Bundle();
        user=u;
        descarga=desc;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);


        button_finalizar = view.findViewById(R.id.button_finalizar);
        button_salir = view.findViewById(R.id.button_salir);


        if(descarga)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Descarga de inventario");
            button_finalizar.setText("Descargar");
        }
        else
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Inventario actual");
            button_finalizar.setText("Imprimir");
        }

        conf = Utils.ObtenerConf(getActivity().getApplication());
        crearEnvaseAut();
        cargarInventario(conf.getRuta());

        button_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.RegresarInicio(getActivity());
            }
        });


        return view;
    }

    private void crearEnvaseAut()
    {
        try
        {
            String res= BaseLocal.Select("select p.prod_cve_n,p.prod_sku_str,0 prod_cant_n from productos p inner join categorias c on p.cat_cve_n=c.cat_cve_n where c.cat_desc_str='ENVASE'",getContext());
            al_envaseAut = ConvertirRespuesta.getEnvaseAutJson(res);

            if(al_envaseAut.size()>0)
            {
                Log.d("salida","tam: "+al_envaseAut.size());
            }

        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void cargarInventario(int rut)
    {
        try{
            String res = BaseLocal.Select(Querys.Inventario.LayoutInventario,getContext());
            al_invP = ConvertirRespuesta.getInvPJson(res);

            res = BaseLocal.Select(string.formatSql(Querys.Inventario.ConsultaInventario, String.valueOf(rut)),getContext() );
            al_inv = ConvertirRespuesta.getInvJson(res);

            if(al_invP!=null && al_invP.size()>0)
            {
                for(int i=0; i<al_invP.size();i++)
                {
                    DataTableLC.InvP r = al_invP.get(i);

                    ArrayList<DataTableLC.Inv> in = new ArrayList<>();
                    for(int j=0; j<al_inv.size();j++) //obtener todos los que coinciden (el select)
                    {
                        if(al_inv.get(i).getProd_cve_n().equals(r.getProd_cve_n()))
                            in.add(al_inv.get(i));
                    }

                    if(in.size()>0)
                    {
                        al_invP.get(i).setProd_cant_n( in.get(0).getProd_cant_n() );
                        al_invP.get(i).setProd_devuelto_n(  in.get(0).getInv_devuelto_n()  );
                        al_invP.get(i).setProd_recuperado_n( in.get(0).getInv_recuperado_n() );
                        al_invP.get(i).setProd_prestado_n( in.get(0).getInv_prestado_n()  );

                        if( Integer.parseInt(in.get(0).getInv_cancelado_n())  > Integer.parseInt(in.get(0).getProd_cant_n())  )
                        {
                            al_invP.get(i).setProd_cancelado_n( in.get(0).getProd_cant_n() );
                        }
                        else
                        {
                            al_invP.get(i).setProd_cancelado_n( in.get(0).getInv_cancelado_n() );
                        }

                    }

                }

            }



        }catch (Exception e)
        {
            Log.d("salida","Error: "+e.getMessage());
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
