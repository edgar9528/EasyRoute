package com.tdt.easyroute.Fragments.Inventario;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.tdt.easyroute.Clases.ConexionWS_JSON;
import com.tdt.easyroute.Clases.Configuracion;
import com.tdt.easyroute.Clases.ConvertirRespuesta;
import com.tdt.easyroute.Clases.Utils;
import com.tdt.easyroute.Interface.AsyncResponseJSON;
import com.tdt.easyroute.Model.DataTableWS;
import com.tdt.easyroute.R;

import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Objects;

public class Carga2Fragment extends Fragment implements AsyncResponseJSON {

    private static boolean recarga= false;
    private static boolean cautivo=false;
    Configuracion conf = null;

    Spinner sp_recargas;

    ArrayList<DataTableWS.Recargas> al_recargas;
    ArrayList<DataTableWS.RecargasDet> al_recargasDet;
    ArrayList<String> recargas;

    TextView tv1,tv2,tv3,tv4,tv5;

    TableLayout tableLayout;
    View vista;
    LayoutInflater layoutInflater;

    Button button_buscar;
    String peticion;

    public Carga2Fragment() {
        // Required empty public constructor
    }

    public static Carga2Fragment newInstance (boolean Recarga, boolean Cautivo)
    {
        Carga2Fragment fragment = new Carga2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        recarga = Recarga;
        cautivo = Cautivo;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carga2, container, false);
        vista = view;
        layoutInflater = inflater;

        conf = Utils.ObtenerConf(getActivity().getApplication());

        if(recarga)
        {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Inventario | Recarga");
        }

        button_buscar = view.findViewById(R.id.button_buscar);
        tv1 = view.findViewById(R.id.tv_rec_cve_n);
        tv2 = view.findViewById(R.id.tv_rec_folio_str);
        tv3 = view.findViewById(R.id.tv_rec_falta_dt);
        tv4 = view.findViewById(R.id.tv_usu_solicita_str);
        tv5 = view.findViewById(R.id.tv_rec_observaciones_str);
        sp_recargas = (Spinner) view.findViewById(R.id.spinnerRecargas);
        tableLayout = (TableLayout) view.findViewById(R.id.tableLayout);

        button_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarRecargas();
            }
        });

        sp_recargas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = sp_recargas.getSelectedItem().toString();
                if(!item.equals("Seleccione una carga"))
                {
                    mostrarRecarga(i-1);
                    buscarRecargaDet(i-1); //menos 1 porque el primero es el mensaje
                }
                else
                {
                    tv1.setText(""); tv2.setText("");
                    tv3.setText(""); tv4.setText("");
                    tv5.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        inicializar();




        return view;
    }

    public void inicializar()
    {
        recargas = new ArrayList<>();
        recargas.add("Seleccione una carga");
        sp_recargas.setAdapter(new ArrayAdapter<String>( getContext(), R.layout.spinner_item, recargas));
    }

    public void buscarRecargas()
    {
        //parametros del metodo
        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();
        PropertyInfo piUser = new PropertyInfo();
        piUser.setName("ruta");
        piUser.setValue(conf.getRuta());
        propertyInfos.add(piUser);

        PropertyInfo piPass = new PropertyInfo();
        piPass.setName("recarga");
        piPass.setValue(recarga?1:0);
        propertyInfos.add(piPass);

        peticion="Recargas";

        //conexion con el metodo
        ConexionWS_JSON conexionWS = new ConexionWS_JSON(getContext(), "ObtenerRecargasJ");
        conexionWS.delegate = Carga2Fragment.this;
        conexionWS.propertyInfos = propertyInfos;
        conexionWS.execute();
    }

    public void buscarRecargaDet(int indiceRecarga)
    {
        //parametros del metodo
        ArrayList<PropertyInfo> propertyInfos = new ArrayList<>();

        PropertyInfo piPass = new PropertyInfo();
        piPass.setName("recarga");
        piPass.setValue( al_recargas.get(indiceRecarga).getRec_cve_n() );
        propertyInfos.add(piPass);

        peticion="RecargasDet";

        //conexion con el metodo
        ConexionWS_JSON conexionWS = new ConexionWS_JSON(getContext(), "ObtenerRecargaDetJ");
        conexionWS.delegate = Carga2Fragment.this;
        conexionWS.propertyInfos = propertyInfos;
        conexionWS.execute();
    }

    public void llenarSpinnerRecargas()
    {
        recargas = new ArrayList<>();
        recargas.add("Seleccione una carga");
        for(int i=0; i<al_recargas.size();i++)
            recargas.add(("Carga "+i+1));

        sp_recargas.setAdapter(new ArrayAdapter<String>( getContext(), R.layout.spinner_item, recargas));

    }

    public void mostrarRecarga(int indiceRecarga)
    {
        tv1.setText(al_recargas.get(indiceRecarga).getRec_cve_n());
        tv2.setText(al_recargas.get(indiceRecarga).getRec_folio_str());
        String fecha = Utils.FechaWS( al_recargas.get(indiceRecarga).getRec_falta_dt() );
        tv3.setText(fecha);
        tv4.setText(al_recargas.get(indiceRecarga).getUsu_solicita_str());
        tv5.setText(al_recargas.get(indiceRecarga).getRec_observaciones_str());
    }

    public void mostrarRecargaDet()
    {
        tableLayout.removeAllViews();
        TableRow tr;
        DataTableWS.RecargasDet rd;

        for(int i=0; i<al_recargasDet.size();i++)
        {
            tr = (TableRow) layoutInflater.inflate(R.layout.tabla_recargasdet, null);
            rd=al_recargasDet.get(i);

            ((TextView) tr.findViewById(R.id.t_rec_cve_n)).setText(rd.getRec_cve_n());
            ((TextView) tr.findViewById(R.id.t_prod_cve_n)).setText(rd.getProd_cve_n());
            ((TextView) tr.findViewById(R.id.t_prod_sku_str)).setText(rd.getProd_sku_str());
            ((TextView) tr.findViewById(R.id.t_prod_desc_str)).setText(rd.getProd_desc_str());
            ((TextView) tr.findViewById(R.id.t_prod_cant_n)).setText(rd.getProd_cant_n());

            tableLayout.addView(tr);
        }
    }


    @Override
    public void recibirPeticion(boolean estado, String respuesta) {

        if(estado)
        {
            //recibio información
            if (respuesta != null)
            {
                if(peticion.equals("Recargas"))
                {
                    al_recargas = ConvertirRespuesta.getRecargasJson(respuesta);
                    if(al_recargas.size()>0)
                    {
                        llenarSpinnerRecargas();
                    }
                }
                else
                if(peticion.equals("RecargasDet"))
                {
                    al_recargasDet = ConvertirRespuesta.getRecargasDetJson(respuesta);
                    if(al_recargasDet.size()>0)
                    {
                        mostrarRecargaDet();
                    }
                }
            }
            else
            {
                Toast.makeText(getContext(), "No se encontro información", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();
        }

    }
}
