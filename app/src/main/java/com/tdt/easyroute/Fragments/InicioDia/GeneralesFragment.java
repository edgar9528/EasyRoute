package com.tdt.easyroute.Fragments.InicioDia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tdt.easyroute.Clases.BaseLocal;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.DatabaseHelper;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class GeneralesFragment extends Fragment {

    EditText et_empresa, et_ruta, et_tRuta, et_vendedor, et_fecha,et_hora,et_camion,et_km;

    ArrayList<DataTableWS.Ruta> lista_rutas;
    ArrayList<String> rutas;
    SpinnerDialog spinnerDialog;

    public GeneralesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generales, container, false);
        // Inflate the layout for this fragment

        et_empresa = view.findViewById(R.id.et_empresa);
        et_ruta = view.findViewById(R.id.et_ruta);
        et_tRuta = view.findViewById(R.id.et_truta);
        et_vendedor = view.findViewById(R.id.et_vendedor);
        et_fecha = view.findViewById(R.id.et_fecha);
        et_hora = view.findViewById(R.id.et_hora);
        et_camion = view.findViewById(R.id.et_camion);
        et_km = view.findViewById(R.id.et_kilometraje);

        inicializar();

        et_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        et_ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarRutas();
            }
        });






        
        return view;
    }

    private void inicializar()
    {
        obtenerRutas();
    }

    private void obtenerRutas()
    {

        String con = "Select * from rutas order by rut_orden_n";
        String json = BaseLocal.Select(con,getContext());

        Log.d("salida","json:"+json);

        lista_rutas = ConvertirRespuesta.getRutasJson(json);


    }

    private void listarRutas()
    {
        Log.d("salida","entro listar rutas");
        if(lista_rutas!=null)
        {
            rutas = new ArrayList<>();
            for (int i = 0; i < lista_rutas.size(); i++)
                rutas.add( lista_rutas.get(i).getRut_desc_str() );


            //SE CREA SPINNER Y SU METODO ONCLICK
            spinnerDialog=new SpinnerDialog(getActivity(),rutas,"Seleccione una ruta","Cerrar");
            //spinnerDialog=new SpinnerDialog(getActivity(),rutas,"Selecciona una ruta",R.style.DialogAnimations_SmileWindow,"Cerrar");

            spinnerDialog.setCancellable(true); // for cancellable
            spinnerDialog.setShowKeyboard(false);// for open keyboard by default

            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    et_ruta.setText(item);
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService (Context.INPUT_METHOD_SERVICE); mgr.hideSoftInputFromWindow (getView().getWindowToken (), 0);
                    //Toast.makeText(getContext(), item + "  " + position+"", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
