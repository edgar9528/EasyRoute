package com.tdt.easyroute.Ventanas.Configuracion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.R;

import java.util.ArrayList;

public class ServidorFragment extends Fragment {

    public ServidorFragment() {
        // Required empty public constructor
    }
    private View viewGral;

    TableLayout tableLayout;
    LayoutInflater layoutInflater;
    ArrayList<String> lista_catalogos;
    boolean[] rbSeleccionados;
    Button button_selec,button_deselec,button_sinc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_servidor, container, false);
        viewGral = view;
        layoutInflater = inflater;

        try{

            inicializar();


            button_selec = view.findViewById(R.id.button_selec);
            button_deselec = view.findViewById(R.id.button_desSelec);
            button_sinc = view.findViewById(R.id.button_sincronizar);

            button_selec.setOnClickListener(btListener);
            button_deselec.setOnClickListener(btListener);

            button_sinc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });




        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }


        return view;
    }

    public void inicializar()
    {
        try {

            ConfiguracionActivity activity = (ConfiguracionActivity) getActivity();
            lista_catalogos = activity.getCatalogos(); //obtener la lista de catalagos de la activity
            rbSeleccionados = new boolean[lista_catalogos.size()]; //para saber que rb estan seleccionados porque son dinamicos

            tableLayout = (TableLayout) viewGral.findViewById(R.id.tableLayout);
            tableLayout.removeAllViews();
            TableRow tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

            for (int i = 0; i < lista_catalogos.size(); i++) {
                tr = (TableRow) layoutInflater.inflate(R.layout.tabla_catalogos, null);

                RadioButton rb = tr.findViewById(R.id.tabla_radio);
                rb.setId(i);
                rb.setTag(i);
                rb.setOnClickListener(rbListener);

                ((TextView) tr.findViewById(R.id.tabla_catalogo)).setText(lista_catalogos.get(i));
                ((TextView) tr.findViewById(R.id.tabla_estado)).setText("ok");

                tableLayout.addView(tr);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("salida","Error: "+e.getMessage());
        }

    }


    //seleccionar o deseleccionar radiobutton cuando se le da clic
    private View.OnClickListener rbListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = ((RadioButton) view);
            int id =  rb.getId();

            if(rbSeleccionados[id])
            {
                rb.setChecked(false);
                rbSeleccionados[id]=false;
            }
            else
            {
                rb.setChecked(true);
                rbSeleccionados[id]=true;
            }

        }
    };

    //seleccionar o deseleccionar todos los radiobutton
    private View.OnClickListener btListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button bt = ((Button) view);
            String op= bt.getTag().toString();
            boolean seleccionar;

            if(op.equals("selec"))
                seleccionar=true;
            else
                seleccionar=false;

            for(int i=0; i<rbSeleccionados.length;i++)
            {
                rbSeleccionados[i]=seleccionar;
                RadioButton rb = (RadioButton) viewGral.findViewWithTag(i);
                rb.setChecked( seleccionar );
            }

        }
    };


}
